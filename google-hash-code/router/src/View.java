

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Stage;

public class View extends Canvas {

	public static final int SCREEN_W = 1280, SCREEN_H = 720;
	public static final Color COLOR_BG = Color.GRAY;
	
	public static final float DRAG_THRESHOLD = 3;

	Building env;
	Stage stage;
	
	
	double zoom;
	double posX, posY;
	Affine transform;
	double lastMouseX = -100, lastMouseY = -100;
	
	
	public View(Building e, Stage s) {
		
		super(SCREEN_W, SCREEN_H);
		
		env = e;
		stage = s;
		zoom = getInitCellSize();
		posX = env.W / 2d;
		posY = env.H / 2d;
		updateTransform();
		
		stage.setScene(new Scene(new BorderPane(this)));
		
		stage.setOnCloseRequest(event ->  {
			stage.close();
			env.loopRunning = false;
			env.outputFile("score_" + env.currentScore);
		});

		stage.setResizable(true);
		stage.sizeToScene();
		
		
		
		
		addEventFilter(ScrollEvent.SCROLL, event -> {
			double deltaY = event.getDeltaY();
			double zoomCoeff = 1.2;
			
			double oldZoom = zoom;
			if (deltaY > 0) { // zoom
				zoom *= zoomCoeff;
			}
			else if (deltaY < 0) { // unzoom
				zoom /= zoomCoeff;
			}
			zoom = Math.max(zoom, 1);

			try {
				double mouseX = event.getSceneX();
				double mouseY = event.getSceneY();
				Point2D targetZoom = transform.inverseTransform(new Point2D(mouseX, mouseY));
				Point2D newPos = new Point2D(posX, posY).subtract(targetZoom).multiply(oldZoom / zoom).add(targetZoom);
				posX = newPos.getX();
				posY = newPos.getY();
			} catch (NonInvertibleTransformException e1) {
				e1.printStackTrace();
			}
			
			
			if (zoom == 1) {
				posX = (long) posX;
				posY = (long) posY;
			}
			updateTransform();
			update();
		});
		
		setOnMouseDragged(event -> {
			double mouseX = event.getSceneX();
			double mouseY = event.getSceneY();
			double deltaX = mouseX - lastMouseX;
			double deltaY = mouseY - lastMouseY;
			if (event.isMiddleButtonDown()) {
				posX -= deltaX / zoom;
				posY -= deltaY / zoom;
				updateTransform();
			}
			lastMouseX = mouseX;
			lastMouseY = mouseY;
			update();
		});
		
		setOnMouseMoved(event -> {
			lastMouseX = event.getSceneX();
			lastMouseY = event.getSceneY();
			update();
		});
		
		setOnMousePressed(event -> {
			lastMouseX = event.getSceneX();
			lastMouseY = event.getSceneY();
			update();
		});
		
		setOnMouseClicked(event -> {
			lastMouseX = event.getSceneX();
			lastMouseY = event.getSceneY();
			Point2D currentPoint = toEnvPoint(getCurrentPoint(lastMouseX, lastMouseY));
			System.out.println("CLIC " + currentPoint);
			int r = (int)currentPoint.getY();
			int c = (int)currentPoint.getX();
			if (event.getButton() == MouseButton.PRIMARY) {
				if (env.routers[r][c]) {
					env.removeRouter(r, c);
				}
				else {
					env.placeRouterWithBackbone(r, c);
				}
			}
			else if (event.getButton() == MouseButton.SECONDARY) {
				env.addBackbone(r, c);
			}
			update();
		});
		
		setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.SPACE) {
				if (env.loopRunning)
					env.loopRunning = false;
				else
					new Thread(() -> env.fillWithRouters()).start();
			}
		});
		
		stage.show();
		
		widthProperty().bind(stage.getScene().widthProperty());
		heightProperty().bind(stage.getScene().heightProperty());
		widthProperty().addListener((p, o, n) -> {
			updateTransform();
			draw();
		});
		heightProperty().addListener((p, o, n) -> {
			updateTransform();
			draw();
		});
		
		requestFocus();

	}
	
	private double getInitCellSize() {
		return Math.max(1,
				Math.min(SCREEN_W / (double)env.W,
						SCREEN_H / (double)env.H));
	}
	
	
	private void updateTransform() {
		transform = new Affine();
		transform.appendTranslation(-posX * zoom, -posY * zoom);
		transform.appendScale(zoom, zoom);
		transform.appendTranslation(((int)getWidth()/2)/zoom, ((int)getHeight()/2)/zoom);
	}
	
	private boolean drawPending = false;
	
	public void draw() {
		GraphicsContext g = getGraphicsContext2D();
		g.clearRect(0, 0, getWidth(), getHeight());
		
		try {
			// transform according to zoom and center coordinate
			g.setTransform(transform);
			
			/*
			 * Les boucles parcoureront entre les valeurs min et max pour éviter
			 * d'avoir à parcourir toute la grille de l'environnement
			 */
			Point2D pMin = transform.inverseTransform(0, 0);
			Point2D pMax = transform.inverseTransform(getWidth(), getHeight());
			int minX = Math.max(0, (int)Math.floor(pMin.getX()));
			int maxX = Math.min(env.W, (int)Math.ceil(pMax.getX()));
			int minY = Math.max(0, (int)Math.floor(pMin.getY()));
			int maxY = Math.min(env.H, (int)Math.ceil(pMax.getY()));
			
			float cR, cG, cB;
			
			g.setFill(COLOR_BG);
			g.fillRect(0, 0, env.W, env.H);
			synchronized (env) {
				for (int c = minX; c < maxX; c++) {
					for (int r = minY; r < maxY; r++) {
						if (env.inputMap[r][c] == '#')
							cR = cG = cB = 0;
						else if (env.inputMap[r][c] == '-') {
							if (env.bDist[r][c] > 0) // skip background
								continue;
							cR = cG = cB = .6f;
						}
						else if (env.routers[r][c]) {
							cR = cB = 0; cG = 1;
						}
						else if (env.covered[r][c]) {
							cR = cB = .5f; cG = 1;
						}
						else
							cR = cG = cB = 1;
						
						if (env.bDist[r][c] == 0) {
							cR = Building.applyAlpha(cR, 0, .5f);
							cG = Building.applyAlpha(cG, 0, .5f);
							cB = Building.applyAlpha(cB, 1, .5f);
						}
						g.setFill(Color.color(cR, cG, cB));
						g.fillRect(c, r, 1, 1);
					}
				}
			}
			
			if (zoom > 2) {
				g.setStroke(Color.GRAY);
				g.setLineWidth(Math.min(zoom/100, 0.5/zoom));
				for (int x = minX; x <= maxX; x++)
					g.strokeLine(x, minY, x, maxY);
				for (int y = minY; y <= maxY; y++)
					g.strokeLine(minX, y, maxX, y);
			}
			
			g.setTransform(new Affine());
			// display infos on screen
			g.setFill(Color.BLACK);
			g.setFont(Font.getDefault());
			g.setTextAlign(TextAlignment.LEFT);
			g.setTextBaseline(VPos.TOP);
			//g.clearRect(0, 0, 300, 17 * 3);
			synchronized (env) {
				g.fillText("input{file='"+env.inputFileName+"';width="+env.W+";height="+env.H+";budget="+env.B+";routerCost="+env.Pr+";bbCost="+env.Pb+"}\n"
						+ "score=" + env.currentScore + "\n"
						+ "budget{totalSpend="+env.moneySpend+";remaining="+(env.B - env.moneySpend)+"}", 0, 0); // TODO text to display
			}
			Point2D p = transform.inverseTransform(lastMouseX, lastMouseY);
			g.clearRect(lastMouseX + 20, lastMouseY, 70, 17);
			g.fillText("(r="+((int)p.getY())+";c="+((int)p.getX())+")", lastMouseX + 20, lastMouseY, 70);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			drawPending = false;
		}
	}
	
	
	private Point2D getCurrentPoint(double guiX, double guiY) {
		try {
			return transform.inverseTransform(guiX, guiY);
		} catch (NonInvertibleTransformException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Point2D toEnvPoint(Point2D p) {
		return new Point2D(
				Math.max(0, Math.min(env.W-0.01, p.getX())),
				Math.max(0, Math.min(env.H-0.01, p.getY())));
	}
	
	
	public void update() {
		if (!drawPending)
			Platform.runLater(this::draw);
	}
	

}

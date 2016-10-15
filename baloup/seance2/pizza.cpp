#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
#include <string>

using namespace std;



string ANSI_RESET = "\x1b[0m";
string ANSI_BLACK = "\x1b[30m";
string ANSI_DARK_RED = "\x1b[31m";
string ANSI_DARK_GREEN = "\x1b[32m";
string ANSI_GOLD = "\x1b[33m";
string ANSI_DARK_BLUE = "\x1b[34m";
string ANSI_DARK_PURPLE = "\x1b[35m";
string ANSI_DARK_AQUA = "\x1b[36m";
string ANSI_GRAY = "\x1b[37m";
string ANSI_DARK_GRAY = "\x1b[30;1m";
string ANSI_RED = "\x1b[31;1m";
string ANSI_GREEN = "\x1b[32;1m";
string ANSI_YELLOW = "\x1b[33;1m";
string ANSI_BLUE = "\x1b[34;1m";
string ANSI_LIGHT_PURPLE = "\x1b[35;1m";
string ANSI_AQUA = "\x1b[36;1m";
string ANSI_WHITE = "\x1b[37;1m";
string ANSI_BOLD = "\x1b[1m";
string ANSI_CLEAR_SCREEN = "\x1b[2J\x1b[1;1H";

string ANSI_PIZZA_FREE = ANSI_RESET + "\x1b[47m" + ANSI_BLACK;

string partsColors[] = {
	ANSI_RESET + ANSI_DARK_RED,
	ANSI_RESET + ANSI_DARK_GREEN,
	ANSI_RESET + ANSI_GOLD,
	ANSI_RESET + ANSI_DARK_BLUE,
	ANSI_RESET + ANSI_DARK_PURPLE,
	ANSI_RESET + ANSI_DARK_AQUA,
	ANSI_RESET + ANSI_GRAY,
	ANSI_RESET + ANSI_DARK_GRAY,
	ANSI_RESET + ANSI_RED,
	ANSI_RESET + ANSI_GREEN,
	ANSI_RESET + ANSI_YELLOW,
	ANSI_RESET + ANSI_BLUE,
	ANSI_RESET + ANSI_LIGHT_PURPLE,
	ANSI_RESET + ANSI_AQUA
};
int nbPartsColors = 14;


typedef struct Point {
	int x, y;
} Point;




/*
	Structure de données PartRoyale
*/
typedef struct PartRoyale {
	int xMin;
	int xMax;
	int yMin;
	int yMax;
	PartRoyale(int xMin_, int xMax_, int yMin_, int yMax_) : xMin(xMin_), xMax(xMax_), yMin(yMin_), yMax(yMax_) { }
	
	void print(ostream& out) const {
		out << yMin << " " << xMin << " " << yMax << " " << xMax << endl;
	}
	
	string getColor() const {
		return partsColors[(xMin + yMin + xMax + yMax) % nbPartsColors];
	}
	
	static PartRoyale UNDEFINED;
} PartRoyale;
PartRoyale PartRoyale::UNDEFINED(-1, -1, -1, -1);

inline bool operator==(const PartRoyale& p1, const PartRoyale& p2){ return (p1.xMin == p2.xMin && p1.xMax == p2.xMax && p1.yMin == p2.yMin && p1.yMax == p2.yMax); }
inline bool operator!=(const PartRoyale& p1, const PartRoyale& p2){ return !(p1 == p2); }


/*
	Structure de données Pizza
*/
typedef struct Pizza {
	int height, width, ham, maxRoyale;
	vector<vector<bool> > matriceHam;
	vector<vector<PartRoyale> > matriceFilled;
	vector<PartRoyale> parts;
	int numberFilled;
	int numberMax;
	
	/*
	Pizza(const Pizza& copied) {
		height = copied.height;
		width = copied.width;
		ham = copied.ham;
		maxRoyale = copied.maxRoyale;
		matriceHam = copied.matriceHam;
		matriceFilled = copied.matriceFilled;
		parts = copied.parts;
		numberFilled = copied.numberFilled;
		numberMax = copied.numberMax;
	}*/
	
	Pizza(istream& in) : numberFilled(0) {
		in >> height >> width >> ham >> maxRoyale;
		
		numberMax = height * width;
		
		for (int i=0; i<height ; i++) {
			vector<bool> ligneJambon;
			vector<PartRoyale> ligneFilled;
			for (int j=0; j<width; j++) {
				char ch;
				in >> ch;
				ligneJambon.push_back(ch == 'H');
				ligneFilled.push_back(PartRoyale::UNDEFINED);
			}
			
			matriceHam.push_back(ligneJambon);
			matriceFilled.push_back(ligneFilled);
		}
	}
	
	
	bool basicCheckPart(const PartRoyale&el) {
		if (el.xMax < el.xMin || el.yMax < el.yMin
			|| (el.xMax - el.xMin + 1) * (el.yMax - el.yMin + 1) > maxRoyale
			|| el.xMin < 0 || el.yMin < 0 || el.xMax >= width || el.yMax >= height)
			return false;
		return true;
	}
	
	
	bool canPut(const PartRoyale& el, bool ignoreFilled) {
		if (!basicCheckPart(el)) return false;
		
		int nbJambon = 0;
		for (int x = el.xMin; x <= el.xMax; x++) {
			for (int y = el.yMin; y <= el.yMax; y++) {
				if (!ignoreFilled && ((matriceFilled[y][x]) != PartRoyale::UNDEFINED))
					return false;
				if (matriceHam[y][x])
					nbJambon++;
			}
		}
		
		return (nbJambon >= ham);
	}
	
	/*
		put a new PartRoyale in this Pizza structure.
		May remove interfering PartRoyale currently in
		this Pizza.
	*/
	void put(const PartRoyale el) {
		if (!basicCheckPart(el)) return;
		
		for (int x = el.xMin; x <= el.xMax; x++) {
			for (int y = el.yMin; y <= el.yMax; y++) {
				if (matriceFilled[y][x] != PartRoyale::UNDEFINED) {
					if (!remove(matriceFilled[y][x])) {
						cerr << "Can't remove PartRoyale ";
						matriceFilled[y][x].print(cerr);
						cerr << "When placing PartRoyale ";
						el.print(cerr);
						return;
					}
				}
			}
		}
		
		
		for (int x = el.xMin; x <= el.xMax; x++) {
			for (int y = el.yMin; y <= el.yMax; y++) {
				
				matriceFilled[y][x] = el;
				numberFilled++;
			}
		}
		parts.push_back(el);
		// cerr << "put -> " << numberFilled << endl;
	}
	
	/*
		remove the PartRoyale at the specified index from
		the vector of PartRoyale of this Pizza
	*/
	/*
	PartRoyale remove(int i) {
		if (i<0 || i>=parts.size()) return PartRoyale::UNDEFINED;
		PartRoyale el = parts[i];
		parts.erase(parts.begin() + i);
		for (int x = el.xMin; x <= el.xMax; x++) {
			for (int y = el.yMin; y <= el.yMax; y++) {
				matriceFilled[y][x] = PartRoyale::UNDEFINED;
				numberFilled--;
			}
		}
		cerr << "Removed PartRoyale ";
		el.print(cerr);
		return el;
	}*/
	
	/*
		remove the specified PartRoyale from this Pizza
	*/
	bool remove(PartRoyale el) {
		if (!basicCheckPart(el)) return true;
		vector<PartRoyale>::iterator pos = find_if(parts.begin(), parts.end(), [el](PartRoyale& f){
			return f == el;
		});
		if (pos == parts.end()) return false;
		parts.erase(pos);
		for (int x = el.xMin; x <= el.xMax; x++) {
			for (int y = el.yMin; y <= el.yMax; y++) {
				matriceFilled[y][x] = PartRoyale::UNDEFINED;
				numberFilled--;
			}
		}
		return true;
	}
	
	
	
	
	void outputResult(ostream& out) {
		out << parts.size() << endl;
		for (int i = 0; i < parts.size(); i++) {
			parts[i].print(out);
		}
	}
	
	
	void displayPizza(ostream& out) {
		string previousColor = ANSI_PIZZA_FREE;
		out << ANSI_PIZZA_FREE;
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				string currentColor = (matriceFilled[r][c] == PartRoyale::UNDEFINED)
						? ANSI_PIZZA_FREE : matriceFilled[r][c].getColor();
				
				if (currentColor != previousColor) {
					out << currentColor;
					previousColor = currentColor;
				}
				
				out << (matriceHam[r][c] ? "H" : "T");
			}
			out << ANSI_RESET << endl << previousColor;
		}
		out << ANSI_RESET;
		out << "Nombre de points : " << numberFilled << endl;
	}
	
	
	Point getFirstFreePoint() {
		Point p;
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				if (matriceFilled[r][c] == PartRoyale::UNDEFINED) {
					p.x = c; p.y = r;
					return p;
				}
			}
		}
		p.x = -1; p.y = -1;
		return p;
	}
	
	
} Pizza;











Pizza* bestPizza;


void recursiveFill(Pizza& pizza, const vector<PartRoyale>& possibleParts, vector<PartRoyale>::iterator start, long long* count) {
	
	// cas d'arrêt
	if (start == possibleParts.end()) {
		(*count)++;
		if ((*count) % 10000000 == 0)
			cerr << "Progress=" << *count << endl;
		if (pizza.numberFilled > bestPizza->numberFilled) {
			pizza.displayPizza(cerr);
			*bestPizza = pizza;
		}
		return;
	}
	
	for (vector<PartRoyale>::iterator it = start; it != possibleParts.end(); ++it) {
		if (pizza.canPut(*it, false)) {
			pizza.put(*it);
			recursiveFill(pizza, possibleParts, it + 1, count);
			pizza.remove(*it);
		}
		else if (it + 1 == possibleParts.end()){
			recursiveFill(pizza, possibleParts, it + 1, count);
		}
		
	}
	
}



void fillParts(Pizza& pizza) {
	
	/*
	 * Méthode random (max au bout de 5 minutes : 3400)
	 */ /*
	int windowHeight = pizza.height;
	
	int bestScore = 0;
	
	while (pizza.numberFilled < pizza.numberMax) {
		Point firstFreePoint = pizza.getFirstFreePoint();
		int minY = firstFreePoint.x;
		int maxY = minY + windowHeight;
		
		int w = (rand() % pizza.maxRoyale) + 1;
		int h = (rand() % (pizza.maxRoyale / w)) + 1;
		
		int x = rand() % (pizza.width - w);
		int y = rand() % (windowHeight - h) + minY;
		
		PartRoyale part(x, x+w-1, y, y+h-1);
		
		if (pizza.canPut(part, true))
			pizza.put(part);
		else
			continue;
		
		if (pizza.numberFilled > bestScore) {
			bestScore = pizza.numberFilled;
			pizza.displayPizza(cerr);
		}
	}
	*/
	
	
	
	
	/*
	 * Méthode : on rempli tout avec les plus petites parts possible
	 * puis on agrandit les parts existantes
	 * Max : environ 8200 selon l'ordre d'agrandissement des parts
	 */ /*
	
	
	for (int h = 1; h <= pizza.maxRoyale; h++) {
		for (int w = 1; w*h <= pizza.maxRoyale; w++) {
			for (int y = 0; y < pizza.height - h + 1; y++) {
				for (int x = 0; x < pizza.width - w + 1; x++) {
					PartRoyale el(x, x + w - 1, y, y + h - 1);
					if (pizza.canPut(el, false)) {
						pizza.put(el);
					}
				}
			}
		}
	}
	
	
	
	for (int i = 0; i < pizza.parts.size(); i++) {
		PartRoyale el = pizza.remove(0);
		
		
		PartRoyale tested(el);
		
		
		tested = el;
		do {
			el = tested;
			tested.yMin--;
		} while(pizza.canPut(tested, false));
		
		
		tested = el;
		do {
			el = tested;
			tested.xMin--;
		} while(pizza.canPut(tested, false));
		
		
		tested = el;
		do {
			el = tested;
			tested.xMax++;
		} while(pizza.canPut(tested, false));
		
		
		tested = el;
		do {
			el = tested;
			tested.yMax++;
		} while(pizza.canPut(tested, false));
		
		
		
		pizza.put(el);
		
	}
	*/
	
	
	
	
	/*
	 * Méthode glouton : on test toutes les combinaisons
	 * possibles de toutes les parts plaçables
	 */
	
	vector<PartRoyale> possibleParts;
	
	

	for (int y = 0; y < pizza.height; y++) {
		for (int x = 0; x < pizza.width; x++) {
			for (int w = 1; w <= pizza.maxRoyale; w++) {
				for (int h = 1; w*h <= pizza.maxRoyale; h++) {
					PartRoyale el(x, x + w - 1, y, y + h - 1);
					if (pizza.canPut(el, true)) {
						possibleParts.push_back(el);
					}
				}
			}
		}
	}
	
	cerr << possibleParts.size() << endl;
	
	long long count = 0;
	
	recursiveFill(pizza, possibleParts, possibleParts.begin(), &count);
	
	cerr << "End. possibleParts=" << possibleParts.size() << endl;
	cerr << "End. Progress=" << count << endl;
	
	pizza = *bestPizza;
	
}















int main() {
	
	
	srand(time(NULL));
	
	
	Pizza pizza(cin);
	
	bestPizza = new Pizza(pizza);
	
	
	fillParts(pizza);
	
	pizza.outputResult(cout);
	
	
	pizza.displayPizza(cerr);
	
	
}



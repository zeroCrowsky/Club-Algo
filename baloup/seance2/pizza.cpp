
#include <iostream>
#include <vector>
#include <cmath>

using namespace std;


typedef struct outputEl {
	int xMin;
	int xMax;
	int yMin;
	int yMax;
} outputEl;

void printOutputEl(outputEl& el) {
	cout << el.yMin << " " << el.xMin << " " << el.yMax << " " << el.xMax << endl;
}
void printErrorEl(outputEl& el) {
	cerr << el.yMin << " " << el.xMin << " " << el.yMax << " " << el.xMax << endl;
}


typedef struct input {
	int r, c, h, s;
	vector<vector<bool> > matriceJambon;
	vector<vector<bool> > matriceFilled;
} input;











bool canPutHere(input& in, outputEl& el) {
	if (el.xMax < el.xMin || el.yMax < el.yMin
		|| (el.xMax - el.xMin + 1) * (el.yMax - el.yMin + 1) > in.s
		|| el.xMin < 0 || el.yMin < 0 || el.xMax >= in.c || el.yMax >= in.r)
		return false;
	int nbJambon = 0;
	bool fillOne = false;
	for (int x = el.xMin; x <= el.xMax; x++) {
		for (int y = el.yMin; y <= el.yMax; y++) {
			if (in.matriceFilled[y][x])
				return false;
			if (in.matriceJambon[y][x])
				nbJambon++;
		}
	}
	
	return (nbJambon >= in.h);
}





void put(input& in, vector<outputEl>& out, outputEl& el) {
	out.push_back(el);
	
	for (int x = el.xMin; x <= el.xMax; x++) {
		for (int y = el.yMin; y <= el.yMax; y++) {
			in.matriceFilled[y][x] = true;
		}
	}
}


void remove(input&in, vector<outputEl>& out, int i) {
	outputEl removed = out[i];
	out.erase(out.begin() + i);
	
	for (int x = el.xMin; x <= el.xMax; x++) {
		for (int y = el.yMin; y <= el.yMax; y++) {
			in.matriceFilled[y][x] = false;
		}
	}
}





void fillOutput(input& in, vector<outputEl>& out) {
	
	for (int h = 1; h <= in.s; h++) {
		for (int w = 1; w*h <= in.s; w++) {
			for (int y = 0; y < in.r - h + 1; y++) {
				for (int x = 0; x < in.c - w + 1; x++) {
					outputEl el;
					el.xMin = x;
					el.xMax = x + w;
					el.yMin = y;
					el.yMax = y + h;
					if (canPutHere(in, el)) {
						put(in, out, el);
					}
				}
			}
		}
	}
	
	
	
}










int main() {
	input in;
	cin >> in.r >> in.c >> in.h >> in.s;
	
	for (int i=0; i<in.r ; i++) {
		vector<bool> ligneJambon;
		vector<bool> ligneFilled;
		for (int j=0; j<in.c; j++) {
			char ch;
			cin >> ch;
			ligneJambon.push_back(ch == 'H');
			ligneFilled.push_back(false);
		}
		
		in.matriceJambon.push_back(ligneJambon);
		in.matriceFilled.push_back(ligneFilled);
	}
	
	vector<outputEl> out;
	
	fillOutput(in, out);
	
	
	cout << out.size() << endl;
	
	for (int i = 0; i < out.size(); i++) {
		printOutputEl(out[i]);
	}
	
	int nbPoints = 0;
	for (int r = 0; r < in.r; r++) {
		for (int c = 0; c < in.c; c++) {
			if (!in.matriceFilled[r][c]) {
				if (in.matriceJambon[r][c])
					cerr << "H";
				else
					cerr << "T";
			}
			else {
				cerr << " ";
				nbPoints++;
			}
		}
		cerr << endl;
	}
	cerr << "Nombre de points : " << nbPoints << endl;
	
}



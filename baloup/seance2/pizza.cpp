
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


typedef struct input {
	int r, c, h, s;
	vector<vector<bool> > matriceJambon;
	vector<vector<bool> > matriceFilled;
} input;











bool canPutHere(input& in, outputEl& el) {
	if (el.xMax < el.xMin || el.yMax < el.xMin
		|| (el.xMax - el.xMin + 1) * (el.yMax - el.yMin + 1) > in.s
		|| el.xMin < 0 || el.yMin < 0 || el.xMax >= in.c || el.yMax >= in.r)
		return false;
	
	int nbJambon = 0;
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







void fillOutput(input& in, vector<outputEl>& out) {
	
	for (int y = 0; y < in.r; y++) {
		for (int x = 0; x < in.c - in.h; x++) {
			outputEl el;
			el.xMin = x;
			el.xMax = min(x+in.s-1, in.c-1);
			el.yMin = y;
			el.yMax = y;
			if (canPutHere(in, el)) {
				put(in, out, el);
				x += in.s - 1;
			}
		}
	}
	
	for (int y = 0; y < in.r; y++) {
		for (int x = 0; x < in.c - in.h; x++) {
			outputEl el;
			el.xMin = x;
			el.xMax = min(x+in.s/2-1, in.c-1);
			el.yMin = y;
			el.yMax = y+1;
			if (canPutHere(in, el)) {
				put(in, out, el);
				x += in.s - 1;
			}
		}
	}
	
	
	for (int y = 0; y < in.r; y++) {
		for (int x = 0; x < in.c - in.h; x++) {
			outputEl el;
			el.xMin = x;
			el.xMax = min(x+in.s/3-1, in.c-1);
			el.yMin = y;
			el.yMax = y+2;
			if (canPutHere(in, el)) {
				put(in, out, el);
				x += in.s - 1;
			}
		}
	}
	
	
	
	for (int y = 0; y < in.r; y++) {
		for (int x = 0; x < in.c - in.h; x++) {
			outputEl el;
			el.xMin = x;
			el.xMax = min(x+in.s/4-1, in.c-1);
			el.yMin = y;
			el.yMax = y+3;
			if (canPutHere(in, el)) {
				put(in, out, el);
				x += in.s - 1;
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
		cout << out[i].yMin << " " << out[i].xMin << " " << out[i].yMax << " " << out[i].xMax << endl;
	}
	
	
	
}



#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
#include <string>
#include <fstream>
#include <sstream>

using namespace std;




int stringToInt(string s) {
	//isstream strm()
}







enum PlaceState {EMPTY, FULL, UNAVAILABLE};

struct ServerCaract{
    int size;
    int capacity;
    
    ServerCaract(int s, int c) : size(s), capacity(c) { }

    static ServerCaract UNDEFINED;
};
ServerCaract ServerCaract::UNDEFINED = {0, 0};

struct PlacedServer{
    ServerCaract carac;
    int rangee;
    int place;
    int group;
    
    PlacedServer(ServerCaract c, int r, int p, int g) : carac(c), rangee(r), place(p), group(g) { }
    
    /**
     * Construct an unplaced server
     */
    PlacedServer(ServerCaract c) : carac(c), rangee(-1), place(-1), group(-1) { }
    
    bool isPlaced() {
		return (rangee >= 0 && place >= 0);
	}
	
    bool setPlace(int r, int p) {
		rangee = r;
		place = p;
	}

    static PlacedServer UNDEFINED;
};
PlacedServer PlacedServer::UNDEFINED(ServerCaract::UNDEFINED);

struct ServerGroup{
    vector<PlacedServer> servers;

    static ServerGroup UNDEFINED;
};

struct Datacenter{
    vector<vector<PlaceState> > rangeesStates;
    vector<vector<PlacedServer> > rangeesServers;
    vector<PlacedServer> serversToPlaces;
    vector<ServerGroup> groups;



	Datacenter(istream& in) {
        int nbRangee, nbPlace, nbIndisp, nbGroup, nbServers;
		in >> nbRangee >> nbPlace >> nbIndisp >> nbGroup >> nbServers;

		for (int i=0; i<nbRangee ; i++) {
			vector<PlaceState> ligneState;
    		vector<PlacedServer> ligneServers;
			for (int j=0; j<nbPlace; j++) {
				ligneState.push_back(EMPTY);
				ligneServers.push_back(PlacedServer::UNDEFINED);
			}

			rangeesStates.push_back(ligneState);
			rangeesServers.push_back(ligneServers);
		}

        for (int i=0; i<nbGroup; i++) {
            groups.push_back(ServerGroup());
        }

        for (int i = 0; i < nbIndisp; i++) {
            int rangee, empl;
            in >> rangee >> empl;
            rangeesStates[rangee][empl] = UNAVAILABLE;
        }
        
        for (int i = 0; i < nbServers; i++) {
            int svNbEmpl, svCap;
            in >> svNbEmpl >> svCap;
			serversToPlaces.push_back(PlacedServer(ServerCaract(svNbEmpl, svCap)));
		}




	}

	
	
	void outputResult(ostream& out) {
		
		for (vector<PlacedServer>::iterator it = serversToPlaces.begin(); it != serversToPlaces.end(); ++it) {
			if (it->isPlaced()) {
				out << it->rangee << " " << it->place << " " << it->group << endl;
			}
			else {
				out << "x" << endl;
			}
		}
		
	}
	
	
	
	
	void display(ostream& out) {
		for (int r = 0; r < rangeesStates.size(); r++) {
			for (int c = 0; c < rangeesStates[r].size(); c++) {
				switch(rangeesStates[r][c]) {
					case EMPTY:
						out << "_";
						break;
					case FULL:
						out << "#";
						break;
					case UNAVAILABLE:
						out << "X";
						break;
				}
			}
			out << endl;
		}
	}
	
	
	
	
};














int main() {
	
	ifstream data("data.in", ifstream::in);
	Datacenter datacenter(data);
	data.close();
	
	string input;
	while(1) {
		datacenter.display(cerr);
		cerr << ">";
		cerr.flush();
		getline(cin, input);
		if (input == "finish")
			break;
		
		
	}
	
	
	
	
	
	datacenter.outputResult(cout);
	
	
}

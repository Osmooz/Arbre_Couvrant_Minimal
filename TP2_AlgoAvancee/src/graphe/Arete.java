package graphe;

public class Arete {
	int poids;
	Sommet sommet1;
	Sommet sommet2;
	
	public Arete (int poids, Sommet sommet1, Sommet sommet2){
		this.poids = poids;
		this.sommet1 = sommet1;
		this.sommet2 = sommet2;
	}

	public int getPoids() {
		return poids;
	}

	public Sommet getSommet1() {
		return sommet1;
	}

	public Sommet getSommet2() {
		return sommet2;
	}

	@Override
	public String toString() {
		return "Arete de poids " + poids + " connectant le sommet : " + sommet1.toString() + " et le sommet : " + sommet2.toString();
	}
}
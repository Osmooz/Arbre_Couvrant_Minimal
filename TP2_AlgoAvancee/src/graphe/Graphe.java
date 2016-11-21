package graphe;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

public class Graphe extends JFrame {
	private static final long serialVersionUID = -8123406571694511514L;
	ArrayList<Arete> listeArete = new ArrayList();
	ArrayList<Sommet> listeSommet = new ArrayList();
	
	public Graphe() {
		super("Graphe");
	}
	
	public void createArete(int poids, int nomSommet1, int nomSommet2) {
		Sommet sommet1 = chercheSommet(nomSommet1);
		Sommet sommet2 = chercheSommet(nomSommet2);
		if (sommet1 == null || sommet2 == null) {
			System.out.println("l'un des deux sommets n'existe pas !");
			return;
		}
		Arete arete = new Arete(poids, sommet1, sommet2);
		sommet1.addArete(arete);
		sommet2.addArete(arete);
		this.listeArete.add(arete);
	}
	
	public void createAreteDist(int nomSommet1, int nomSommet2) {
		Sommet sommet1 = chercheSommet(nomSommet1);
		Sommet sommet2 = chercheSommet(nomSommet2);
		if (sommet1 == null || sommet2 == null) {
			System.out.println("l'un des deux sommets n'existe pas !");
			return;
		}
		int distanceX = (int) Math.pow((sommet1.getPosX() - sommet2.getPosX()), 2);
		int distanceY = (int) Math.pow((sommet1.getPosY() - sommet2.getPosY()), 2);
		int distance = (int) Math.sqrt(distanceX + distanceY);
		Arete arete = new Arete(distance, sommet1, sommet2);
		sommet1.addArete(arete);
		sommet2.addArete(arete);
		this.listeArete.add(arete);
	}
	
	public void createSommet(int nomSommet) {
		this.listeSommet.add(new Sommet(nomSommet));
	}
	
	public Sommet ufFind(Sommet sommet){
		return sommet.getHead();
	}
	
	public void reinitialiserSommet() {
		for(Sommet sommet : listeSommet) {
			sommet.setHeight(1);
			sommet.setHead(sommet);
		}
	}
	
	public void ufUnion(Sommet sommet1Head, Sommet sommet2Head, Arete arete){
		if(sommet1Head.getHeight() >= sommet2Head.getHeight()) {
			for(Sommet sommetIndex : listeSommet)
			{
				if(sommetIndex.getHead().getNumero() == sommet2Head.getNumero())
					sommetIndex.setHead(sommet1Head);
			}
			if(sommet2Head.getHeight() == sommet1Head.getHeight())
				arete.sommet1.setHeight(sommet1Head.getHeight() + 1);
		}
		else {
			for(Sommet sommetIndex : listeSommet)
			{
				if(sommetIndex.getHead().getNumero() == sommet2Head.getNumero())
					sommetIndex.setHead(sommet1Head);
			}
		}
	}
	public ArrayList<Arete> algoPrime(int nomSommet) {
		Sommet sommetDepart = chercheSommet(nomSommet);
		if (sommetDepart == null){
			System.out.println("le sommet de depart indiquÃ© n'existe pas !");
			return null;
		}
		ArrayList<Arete> aretes = new ArrayList();
		ArrayList<Arete> aretesResultat = new ArrayList();
		ArrayList<Sommet> sommetResultat = new ArrayList();
		Arete petiteAret;
		sommetResultat.add(sommetDepart);
		aretes.addAll(sommetDepart.listeArete);
		while(this.listeSommet.size() != sommetResultat.size()){
			petiteAret = plusPetiteArete(aretes);
			if(!sommetResultat.contains(petiteAret.sommet1)) {
				sommetResultat.add(petiteAret.sommet1);
				aretesResultat.add(petiteAret);
				aretes.addAll(petiteAret.sommet1.listeArete);
			}
			else if (!sommetResultat.contains(petiteAret.sommet2)) {
				sommetResultat.add(petiteAret.sommet2);
				aretesResultat.add(petiteAret);
				aretes.addAll(petiteAret.sommet2.listeArete);
			}
			aretes.remove(petiteAret);
		}
		return  aretesResultat;
	}
	
	public ArrayList<Arete> algoPrimeTas(int nomSommet) {
		Sommet sommetDepart = chercheSommet(nomSommet);
		if (sommetDepart == null){
			System.out.println("le sommet de depart indiquÃ© n'existe pas !");
			return null;
		}
		ArrayList<Arete> aretesResultat = new ArrayList();
		ArrayList<Sommet> sommetResultat = new ArrayList();
		Arete petiteAret;
		sommetResultat.add(sommetDepart);
		TasArrayList tas = new TasArrayList(sommetDepart.listeArete);
		tas.constructionTas();
		while(this.listeSommet.size() != sommetResultat.size()){
			petiteAret = tas.plusPetiteArete();
			ArrayList<Arete> aretesConsultables;
			if(!sommetResultat.contains(petiteAret.sommet1)) {
				sommetResultat.add(petiteAret.sommet1);
				aretesResultat.add(petiteAret);
				aretesConsultables = petiteAret.sommet1.listeArete;
				aretesConsultables.remove(petiteAret);
				tas.rajoutAreteDansTas(aretesConsultables);
			}
			else if (!sommetResultat.contains(petiteAret.sommet2)) {
				sommetResultat.add(petiteAret.sommet2);
				aretesResultat.add(petiteAret);
				aretesConsultables = petiteAret.sommet2.listeArete;
				aretesConsultables.remove(petiteAret);
				tas.rajoutAreteDansTas(aretesConsultables);
			}
		}
		return  aretesResultat;
	}
	
	public ArrayList<Arete> algoKruskal() { //retirer sommetResultat ?
		ArrayList<Arete> aretes = new ArrayList<>();
		aretes.addAll(listeArete);
		ArrayList<Arete> aretesResultat = new ArrayList();
		ArrayList<Sommet> sommetResultat = new ArrayList();
		Arete petiteAret;	
		while(aretesResultat.size() != this.listeSommet.size() - 1){
			petiteAret = plusPetiteArete(aretes);
			if(petiteAret == null)
				break;			
			System.out.println("On va tester l'ajout de l'arrete de poids : \"" + petiteAret.poids + "\" reliant les sommets : " + petiteAret.sommet1.numero + " et " + petiteAret.sommet2.numero);
			if(!(isCycle(petiteAret.sommet1, petiteAret.sommet2, aretesResultat))) {
				if(!sommetResultat.contains(petiteAret.sommet1))
					sommetResultat.add(petiteAret.sommet1);
				if(!sommetResultat.contains(petiteAret.sommet2))
					sommetResultat.add(petiteAret.sommet2);
				aretesResultat.add(petiteAret);
			}
			aretes.remove(petiteAret);
		}
		afficherGraphe(listeArete, aretesResultat);
		return aretesResultat;
	}
	
	public ArrayList<Arete> algoKruskalUnionFind() {
		reinitialiserSommet();
		ArrayList<Arete> aretes = new ArrayList<>();
		aretes.addAll(listeArete);
		ArrayList<Arete> aretesResultat = new ArrayList<>();
		ArrayList<Sommet> sommetResultat = new ArrayList<>();
		Arete petiteAret;
		
		Sommet sommet1;
		Sommet sommet2;
		
		while(aretesResultat.size() != this.listeSommet.size() - 1){
			petiteAret = plusPetiteArete(aretes);
			if(petiteAret == null)
				break;
			System.out.println("On va tester l'ajout de l'arrete de poids : \"" + petiteAret.poids + "\" reliant les sommets : " + petiteAret.sommet1.numero + " et " + petiteAret.sommet2.numero);
			sommet1 = ufFind(petiteAret.sommet1);
			sommet2 = ufFind(petiteAret.sommet2);
			aretes.remove(petiteAret);
			if(sommet1.getNumero() == sommet2.getNumero()) {
				System.out.println("Le parent de " + petiteAret.sommet1.getNumero() + " et " + petiteAret.sommet2.getNumero() + " est indentique : " + sommet1.getNumero());
				continue;
			}
			ufUnion(sommet1, sommet2, petiteAret);
			if (!sommetResultat.contains(petiteAret.sommet1))
				sommetResultat.add(petiteAret.sommet1);
			if (!sommetResultat.contains(petiteAret.sommet2))
				sommetResultat.add(petiteAret.sommet1);
			aretesResultat.add(petiteAret);
		}
		return aretesResultat;
	}
	
	public Sommet chercheSommet(int nomSommet) {
		for (int index = 0; index < listeSommet.size(); index++) {
			if (listeSommet.get(index).getNumero() == nomSommet)
				return listeSommet.get(index);
		}
		return null;
	}
	
	public Arete plusPetiteArete(ArrayList<Arete> listArete) {
		if(listArete.size() == 0)
			return null;
		Arete arete = listArete.get(0);
		for (int index = 1; index < listArete.size(); index++){
			if (listArete.get(index).poids < arete.poids)
				arete = listArete.get(index);
		}
		return arete;
	}
	
	public boolean isCycle(Sommet sommet1, Sommet sommetRecherche, ArrayList<Arete> listeArete){ //Ã  la premiere iteration, sommet1 est un des deux sommets de l'arete, et sommetRecherche est l'autre sommet de cette arete
		if(sommet1 == sommetRecherche) {
			System.out.println("IL Y A UN CYCLE !\n");
			return true;
		}
		ArrayList<Arete> listeAreteNonVerif = new ArrayList<>();
		listeAreteNonVerif.addAll(listeArete);
		for(int index = 0 ; index < listeArete.size() ; index++){
			if(listeArete.get(index).sommet1 == sommet1){
				listeAreteNonVerif.remove(listeArete.get(index));
				if(isCycle(listeArete.get(index).sommet2, sommetRecherche, listeAreteNonVerif))
					return true;
			}
			else if(listeArete.get(index).sommet2 == sommet1){
				listeAreteNonVerif.remove(listeArete.get(index));
				if(isCycle(listeArete.get(index).sommet1, sommetRecherche, listeAreteNonVerif))
					return true;
			}
		}
		System.out.println("Il n'y a pas de cycle\n"); //elever le commentaire car répétitif du fait de la recursivité
		return false;
	}
	
	public void joindreSommet() {
		int i,j;
		for(i = 0; i < this.listeSommet.size() - 1; i++){
			for(j = 1 + i; j < this.listeSommet.size(); j++){
				this.createAreteDist(listeSommet.get(i).getNumero(), listeSommet.get(j).getNumero());
			}
		}
	}
	
	public void createSommetRand(int nbSommet) {
		for(int i = 0; i < nbSommet; i++){
			this.createSommet(i);
		}
	}
	
	public void createRectangle() {
		int point1X = (int) (Math.random()*50);
		int point1Y = (int) (Math.random()*50);
		int point2X = (int) (point1X + (25+Math.random()*25));
		int point2Y = (int) (point1Y + (25+Math.random()*25));
		System.out.println("point 1 : (" + point1X + ";" + point1Y + ")");
		System.out.println("point 2 : (" + point2X + ";" + point2Y + ")");
	}
	
	public ArrayList<Sommet> sommetInTheRectangle(int p1X, int p1Y, int p2X, int p2Y) { //Créer une classe point et mettre les deux points en parametre ?
		ArrayList<Sommet> tabSommet = new ArrayList<>();
		for(Sommet sommet : listeSommet) {
			if(sommet.getPosX() >= p1X && sommet.getPosX() <= p2X) { //On vérifie les coordonnées en X
				if(sommet.getPosY() >= p1Y && sommet.getPosY() <= p2Y) { //On vérifie les coordonnées en Y
					tabSommet.add(sommet);
				}
			}
		}
		return tabSommet;
	}
	
	public ArrayList<Arete> areteInRectangle(ArrayList<Sommet> sommetRectangle) {
		ArrayList<Arete> areteRectangle = new ArrayList<>();
		
		ArrayList<Sommet> sommetsSansConcerne = new ArrayList<>();
		sommetsSansConcerne.addAll(sommetRectangle);
		for(Sommet sommet : sommetRectangle) { //parcours tous les sommets qui sont dans le rectangles
			sommetsSansConcerne.remove(sommet); //on enlève le sommet sur lequel on est
			for(Arete arete : sommet.getListeArete()) { //parcours toutes les aretes de ce sommet
				for(Sommet sommetDuRectangle : sommetsSansConcerne) { //on parcours tous les sommets du rectangle sans le sommet concerné
					if(arete.getSommet1() == sommetDuRectangle) { // si on est sur un arete reliant deux sommets presents dans le rectangle, on l'ajoute au tableau
						if(!areteRectangle.contains(arete)) {
							areteRectangle.add(arete);
						}
					}
					else if(arete.getSommet2() == sommetDuRectangle) {
						if(!areteRectangle.contains(arete)) {
							areteRectangle.add(arete);
						}
					}
				}
			}
			sommetsSansConcerne.add(sommet);
		}
		return areteRectangle;
	}
	
	public void afficherGraphe () {
		String edgeStyle = mxConstants.STYLE_ENDARROW + "=" + mxConstants.NONE;
		mxGraph graph = new mxGraph();
	    Object parent = graph.getDefaultParent();
	 
	    graph.getModel().beginUpdate();
	    try {
	    	ArrayList<Object> sommetGraphique = new ArrayList<>();
	    	for(Sommet sommet : listeSommet) {
	    		sommetGraphique.add(graph.insertVertex(parent, null, sommet.getNumero(), sommet.getPosX(), sommet.getPosY() ,25, 25));
	    	}
	    	for(Arete arete : listeArete) {
	    		graph.insertEdge(parent, null, arete.getPoids(), sommetGraphique.get(arete.sommet1.getNumero()-1), sommetGraphique.get(arete.sommet2.getNumero()-1), edgeStyle); //repose sur le fait qu'ils soient par ordre croissant dans le tableau
	    	}
	    } 
	    finally {
	    	graph.getModel().endUpdate();
	    }
	    graph.setCellsEditable(false);
	    graph.setCellsResizable(false);
	    //graph.setCellsMovable(false);
	 
	    mxGraphComponent graphComponent = new mxGraphComponent(graph);
	    getContentPane().add(graphComponent);
	}
	
	public void afficherGraphe (ArrayList<Arete> aretesGraphe) {
		String edgeStyle = mxConstants.STYLE_ENDARROW + "=" + mxConstants.NONE;
		mxGraph graph = new mxGraph();
	    Object parent = graph.getDefaultParent();
	 
	    graph.getModel().beginUpdate();
	    try {
	    	ArrayList<Object> sommetGraphique = new ArrayList<>();
	    	for(Sommet sommet : listeSommet) {
	    		sommetGraphique.add(graph.insertVertex(parent, null, sommet.getNumero(), sommet.getPosX(), sommet.getPosY() ,25, 25));
	    	}
	    	for(Arete arete : aretesGraphe) {
	    		graph.insertEdge(parent, null, arete.getPoids(), sommetGraphique.get(arete.sommet1.getNumero()-1), sommetGraphique.get(arete.sommet2.getNumero()-1), edgeStyle); //repose sur le fait qu'ils soient par ordre croissant dans le tableau
	    	}
	    } 
	    finally {
	    	graph.getModel().endUpdate();
	    }
	    graph.setCellsEditable(false);
	    graph.setCellsResizable(false);
	    //graph.setCellsMovable(false); 
	    
	    mxGraphComponent graphComponent = new mxGraphComponent(graph);
	    getContentPane().add(graphComponent);
	}
	
	public void afficherGraphe (ArrayList<Arete> aretesGraphe, ArrayList<Arete> aretesCourt) {
		String edgeStyle = mxConstants.STYLE_ENDARROW + "=" + mxConstants.NONE;
		String styleRedColor = mxConstants.STYLE_STROKECOLOR + "=#ff0000";
		mxGraph graph = new mxGraph();
	    Object parent = graph.getDefaultParent();
	 
	    graph.getModel().beginUpdate();
	    try {
	    	ArrayList<Object> sommetGraphique = new ArrayList<>();
	    	for(Sommet sommet : listeSommet) {
	    		sommetGraphique.add(graph.insertVertex(parent, null, sommet.getNumero(), sommet.getPosX(), sommet.getPosY() ,25, 25));
	    	}
	    	for(Arete arete : aretesGraphe) {
	    		if(!aretesCourt.contains(arete)) {
	    			graph.insertEdge(parent, null, arete.getPoids(), sommetGraphique.get(arete.sommet1.getNumero()-1), sommetGraphique.get(arete.sommet2.getNumero()-1), edgeStyle); //repose sur le fait qu'ils soient par ordre croissant dans le tableau
	    		}
	    		else {
	    			graph.insertEdge(parent, null, arete.getPoids(), sommetGraphique.get(arete.sommet1.getNumero()-1), sommetGraphique.get(arete.sommet2.getNumero()-1), /*edgeStyle,*/ styleRedColor); //Mettre les deux options
	    		}
	    	}
	    } 
	    finally {
	    	graph.getModel().endUpdate();
	    }
	    graph.setCellsEditable(false);
	    graph.setCellsResizable(false);
	    //graph.setCellsMovable(false); 
	    
	    mxGraphComponent graphComponent = new mxGraphComponent(graph);
	    getContentPane().add(graphComponent);
	}
	
	public static void main(String[] args) {
		Graphe graphe = new Graphe();
		graphe.createSommet(1);
		graphe.createSommet(2);
		graphe.createSommet(3);
		graphe.createSommet(4);
		graphe.createSommet(5);
		graphe.createSommet(6);
		graphe.createSommet(7);
		graphe.createSommet(8);
		graphe.createArete(5, 1, 2);
		graphe.createArete(3, 1, 6);
		graphe.createArete(7, 1, 3);
		graphe.createArete(1, 2, 3);
		graphe.createArete(3, 2, 4);
		graphe.createArete(2, 2, 5);
		graphe.createArete(4, 3, 4);
		graphe.createArete(8, 4, 5);
		graphe.createArete(1, 4, 6);
		graphe.createArete(9, 4, 7);
		graphe.createArete(2, 4, 8);
		graphe.createArete(3, 5, 8);
		graphe.createArete(1, 7, 8);
		//graphe.createArete(2, 7, 4);
		
//		ArrayList<Arete> resultat = graphe.algoKruskal();
//		for (int index = 0; index < resultat.size(); index++)
//			System.out.println(resultat.get(index).toString());
		
//		ArrayList<Arete> resultat2 = graphe.algoKruskalUnionFind();
//		for (int index = 0; index < resultat2.size(); index++)
//			System.out.println(resultat2.get(index).toString());
		
//		ArrayList<Arete> resultat2 = graphe.algoPrimeTas(4);
//		for (int index = 0; index < resultat2.size(); index++)
//			System.out.println(resultat2.get(index).toString());
		
//		graphe.createSommetRand(100);
//		graphe.joindreSommet();
//		graphe.createRectangle();

		//graphe.afficherGraphe();
		graphe.algoKruskal(); //à retirer
	    graphe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    graphe.setSize(700, 700);
	    graphe.setVisible(true);
	}
}
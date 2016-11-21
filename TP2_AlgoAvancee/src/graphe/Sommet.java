package graphe;

import java.util.ArrayList;

public class Sommet {
	int numero;
	ArrayList<Arete> listeArete = new ArrayList();
	
	Sommet head;
	int height;
	
	int posX;
	int posY;

	public Sommet(int numero){
		this.numero = numero;
		
		this.head = this;
		this.height = 1;
		
		this.posX = (int) (Math.random()*650); //100
		this.posY = (int) (Math.random()*650); //100
	}
	
	public void addArete(Arete arete){
		this.listeArete.add(arete);
	}
	
	public int getNumero() {
		return numero;
	}

	public ArrayList<Arete> getListeArete() {
		return listeArete;
	}
	
	public Sommet getHead() {
		return head;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Arete getAreteFromList(int indice) {
		return this.listeArete.get(indice);
	}
	
	public int getPosX(){
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public void setHead(Sommet head){
		this.head = head;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public String toString() {
		return "\"" + numero + "\" (" + posX + ";" + posY + ")";
	}
}
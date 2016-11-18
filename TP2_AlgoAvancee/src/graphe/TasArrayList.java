package graphe;

import java.util.ArrayList;

public class TasArrayList {

	//inverser en mettant le plus petit au debut du tas
		private ArrayList<Arete> tab;
		
		public TasArrayList() {
			this.tab = new ArrayList<Arete>();
		}
		
		public TasArrayList(ArrayList<Arete> tab) {
			this.tab = tab;
		}
		
		public void echanger(int index1, int index2)
		/* spécification : échange deux valeurs d'un tableau, prend en parametre le tableau et les indices des deux valeurs que
		 * l'on veut echanger
		 */
		{
			Arete x = this.tab.get(index1);
			this.tab.set(index1, this.tab.get(index2));
			this.tab.set(index2, x);
		}
		
		public void ajouterTas(int i, Arete nouvelleArete)
		/*spécification : prend en entrée un tableau de int, sa taille et l'element que l'on veut ajouter au tableau
		 */
		{
		    int parent = (i-1)/2;
		    if(i > 0 && nouvelleArete.getPoids() < this.tab.get(parent).getPoids()) // Changer le signe ici !
		    {
		    	this.tab.set(i, this.tab.get(parent));
		        ajouterTas(parent, nouvelleArete);
		    }
		    else if (i >= 0) {
		    	this.tab.set(i, nouvelleArete);
		    }
		}

		public void constructionTas()
		/*spécification : permet de construire un tas a partir d'un tableau et de sa taille
		 */
		{
		    for(int i = 1 ; i < this.tab.size() ; i++) {
		        ajouterTas(i , this.tab.get(i));
		    }
		}

		public void rajoutAreteDansTas(ArrayList<Arete> tab)
		/*spécification : permet de construire un tas a partir d'un tableau et de sa taille
		 */
		{
		    for(int i = 0 ; i < tab.size() ; i++) {
		    	this.tab.add(tab.get(i));
		        ajouterTas(this.tab.size()-1 , tab.get(i));
		    }
		}


		public void supprimerMaxTas() //modifier pour que ça supprime bien le dernier
		/*spécification : déplace la valeur en haut du tas a la fin de celui-ci, prends en entré un tableau et sa taille
		 */
		{
		    int i = 0, gauche = 1, droite = 2, min;
		    echanger(0, this.tab.size()-1);
		    while(droite < this.tab.size()-1)
		    {
		        if(gauche < this.tab.size()-1 && this.tab.get(gauche).poids <= this.tab.get(droite).poids) {
		        	min = gauche;
		        }
		        else {
		        	min = droite;
		        }
		        if(this.tab.get(i).poids > this.tab.get(min).poids) {
		        	echanger(i, min);
		        }
		        else
		            break;
		        i = min;
		        gauche = 2*i+1;
		        droite = gauche+1;
		    }
		    this.tab.remove(this.tab.size()-1); //On supprime la valeur
		}

		public Arete plusPetiteArete() {
			Arete plusPetiteArete = this.tab.get(0);
			supprimerMaxTas();
			return plusPetiteArete;
		}	
		
		public void afficherTas () {
			System.out.println("Le tas contient :");
			for(Arete arete : this.tab) {
				System.out.print(arete.getPoids()+ "("+arete.getSommet1().getNumero()+";"+arete.getSommet2().getNumero()+") -");
			}
			System.out.println("");
		}
}
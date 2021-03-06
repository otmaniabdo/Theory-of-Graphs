package com.jihane.algorithms;

import java.util.*;
import java.util.LinkedList;

import com.jihane.models.Graphe;


public class GraphColoring {
	private int nombreNoeuds;
	private LinkedList<Integer> voisins[];
	private LinkedList<String> couleur = new LinkedList<String>(
			Arrays.asList(
					"BLUE", "CYAN", "PINK", "YELLOW", "BLACK", "ORANGE", "RED", "GREEN", "GRAY"
					)
			);
	int result[];
	
	public GraphColoring(int nombreNoeuds)
	{
		this.nombreNoeuds = nombreNoeuds;
		result = new int[nombreNoeuds];
		voisins = new LinkedList[nombreNoeuds];
		for (int i=0; i<nombreNoeuds; ++i)
			voisins[i] = new LinkedList();
	}
	
	// Ajouter les voisions du chaque noeud � la liste voisions[]
	public void adaptGraph(Graphe g) {
		for(int i=0; i<g.getArcs().size(); i++) {
			ajouterArc(g.getArcs().get(i).getSource().getId()-1, g.getArcs().get(i).getDestination().getId()-1);
		}
	}

	void ajouterArc(int source,int destination) {
		voisins[source].add(destination);
		voisins[destination].add(source);
	}
	
	// Colorer les noeud
	public void coloriage() {
		Arrays.fill(result, -1);
		result[0] = 0;
		boolean disponible[] = new boolean[this.nombreNoeuds];
		Arrays.fill(disponible, true);
		for (int u = 1; u < this.nombreNoeuds; u++) {
			Iterator<Integer> it = voisins[u].iterator() ;
			while (it.hasNext()) {
				int i = it.next();
				if (result[i] != -1)
					disponible[result[i]] = false;
			}
			int couleur;
			for (couleur = 0; couleur < this.nombreNoeuds; couleur++){
				if (disponible[couleur])
					break;
			}
			result[u] = couleur;
			Arrays.fill(disponible, true);
		}
	}
	
	// retourner la couleur de chaque noeud
	public String getCouleur(int i) {
		return couleur.get(result[i]);
	}
}

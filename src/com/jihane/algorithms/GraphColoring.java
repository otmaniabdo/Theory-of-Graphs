package com.jihane.algorithms;

import java.util.*;
import java.util.LinkedList;

import com.jihane.models.Graphe;


public class GraphColoring {
	private int nombreArcs;
	private LinkedList<Integer> voisins[];
	private LinkedList<String> couleur = new LinkedList<String>(
			Arrays.asList(
					"RED", "GREEN", "GRAY", "YELLOW", "BLACK", "ORANGE", "BLUE", "CYAN", "PINK"
					)
			);
	int result[];

	public GraphColoring(int nombreArcs)
	{
		this.nombreArcs = nombreArcs;
		result = new int[nombreArcs];
		voisins = new LinkedList[nombreArcs];
		for (int i=0; i<nombreArcs; ++i)
			voisins[i] = new LinkedList();
	}
	
	public void adaptGraph(Graphe g) {
		for(int i=0; i<g.getArcs().size(); i++) {
			addEdge(g.getArcs().get(i).getSource().getId()-1, g.getArcs().get(i).getDestination().getId()-1);
		}
	}

	void addEdge(int source,int destination)
	{
		voisins[source].add(destination);
		voisins[destination].add(source);
	}

	public void greedyColoring() {
		Arrays.fill(result, -1);
		result[0] = 0;
		boolean disponible[] = new boolean[this.nombreArcs];
		Arrays.fill(disponible, true);
		for (int u = 1; u < this.nombreArcs; u++)	{
			Iterator<Integer> it = voisins[u].iterator() ;
			while (it.hasNext()) {
				int i = it.next();
				if (result[i] != -1)
					disponible[result[i]] = false;
			}
			int couleur;
			for (couleur = 0; couleur < this.nombreArcs; couleur++){
				if (disponible[couleur])
					break;
			}
			result[u] = couleur;
			Arrays.fill(disponible, true);
		}
	}
	
	public String getCouleur(int i) {
		return couleur.get(result[i]);
	}
}

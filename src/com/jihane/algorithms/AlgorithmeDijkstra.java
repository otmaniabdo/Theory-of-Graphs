package com.jihane.algorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.jihane.models.Arc;
import com.jihane.models.Graphe;
import com.jihane.models.Noeud;

public class AlgorithmeDijkstra {

	private Graphe graphe;
    private Set<Noeud> noeudsInclus, noeudsExclus;
    private Map<Noeud, Noeud> predecesseurs;
    private Map<Noeud, Integer> distance;
   
	public AlgorithmeDijkstra(Graphe graphe) {
		super();
		this.graphe = graphe;
	}

    public Graphe getGraphe() {
		return graphe;
	}


	public void setGraphe(Graphe graphe) {
		this.graphe = graphe;
	}
 
    private void trouverDistanceMinimal(Noeud noeud) {
        LinkedList<Noeud> adjacentNodes = getVoisions(noeud);
        for (Noeud destination : adjacentNodes) {
            if (getPlusCourteDistance(destination) > getPlusCourteDistance(noeud)
                    + getDistance(noeud, destination)) {
                distance.put(destination, getPlusCourteDistance(noeud)
                        + getDistance(noeud, destination));
                predecesseurs.put(destination, noeud);
                noeudsExclus.add(destination);
            }
        }

    }

	private int getDistance(Noeud source, Noeud destination) {
        for (Arc arc : this.getGraphe().getArcs()) {
            if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
                return arc.getPoids();
            }
        }
        return -1; // Retourner -1 en cas d'erreur
    }
	
    private LinkedList<Noeud> getVoisions(Noeud noeud) {
    	LinkedList<Noeud> voisions = new LinkedList<Noeud>();
        for (Arc arc : this.getGraphe().getArcs()) {
            if (arc.getSource().equals(noeud) && !estInclu(arc.getDestination())) {
                voisions.add(arc.getDestination());
            }
        }
        return voisions;
    }
    
    private Noeud getMinimum(Set<Noeud> noeuds) {
    	Noeud minimum = null;
        for (Noeud noeud : noeuds) {
            if (minimum == null) {
                minimum = noeud;
            } else {
                if (getPlusCourteDistance(noeud) < getPlusCourteDistance(minimum)) {
                    minimum = noeud;
                }
            }
        }
        return minimum;
    }

    private boolean estInclu(Noeud noeud) {
        return noeudsInclus.contains(noeud);
    }
    
    private int getPlusCourteDistance(Noeud destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Noeud> dessinerChemin(Noeud source, Noeud destination) {
        LinkedList<Noeud> chemin = new LinkedList<Noeud>();
        noeudsInclus = new HashSet<Noeud>();
        noeudsExclus = new HashSet<Noeud>();
        distance = new HashMap<Noeud, Integer>();
        predecesseurs = new HashMap<Noeud, Noeud>();
        distance.put(source, 0);
        noeudsExclus.add(source);
        while (noeudsExclus.size() > 0) {
            Noeud node = getMinimum(noeudsExclus);
            noeudsInclus.add(node);
            noeudsExclus.remove(node);
            trouverDistanceMinimal(node);
        }
        Noeud step = destination;
        if (predecesseurs.get(step) == null) {
            return null;
        }
        chemin.add(step);
        while (predecesseurs.get(step) != null) {
            step = predecesseurs.get(step);
            chemin.add(step);
        }
        Collections.reverse(chemin);
        return chemin;
    }
    
    public String plusCourtChemin(Noeud source, Noeud destination) {
    	return this.dessinerChemin(source, destination).toString();
    }
}

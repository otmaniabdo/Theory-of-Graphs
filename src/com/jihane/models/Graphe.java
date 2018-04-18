package com.jihane.models;
import java.util.LinkedList;

import com.jihane.models.Arc;
import com.jihane.models.Noeud;


public class Graphe {
	private LinkedList<Arc> arcs = new LinkedList<Arc>();
	private LinkedList<Noeud> noeuds = new LinkedList<Noeud>();


	public Graphe(LinkedList<Arc> arcs, LinkedList<Noeud> noeuds) {
		super();
		this.arcs = arcs;
		this.noeuds = noeuds;
	}


	public LinkedList<Arc> getArcs() {
		return arcs;
	}


	public void setArcs(LinkedList<Arc> arcs) {
		this.arcs = arcs;
	}


	public LinkedList<Noeud> getNoeuds() {
		return noeuds;
	}


	public void setNoeuds(LinkedList<Noeud> noeuds) {
		this.noeuds = noeuds;
	}
}

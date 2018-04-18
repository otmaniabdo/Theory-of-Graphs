package com.jihane.models;

import java.util.LinkedList;

public class NonOriente extends Graphe{
	private boolean orientation = false;

	
	public NonOriente(LinkedList<Arc> arcs, LinkedList<Noeud> noeuds, boolean orientation) {
		super(arcs, noeuds);
		this.orientation = orientation;
	}

	public boolean isOrientation() {
		return orientation;
	}

	public void setOrientation(boolean orientation) {
		this.orientation = orientation;
	}
}

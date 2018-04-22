package com.jihane.models;
import java.util.Collections;
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
	
	public boolean isConnexe(boolean orientation) {
		int count = 0;
		LinkedList<Integer> inclus = new LinkedList<Integer>();
		LinkedList<Integer> sources = new LinkedList<Integer>();
		LinkedList<Integer> destinations = new LinkedList<Integer>();
		if(orientation) {
			for(int i=0; i<this.getArcs().size(); i++) {
				if(!sources.contains(this.getArcs().get(i).getSource().getId())
						&& this.getArcs().get(i).getSource().getId() != this.getArcs().get(i).getDestination().getId()) {
					
					sources.add(this.getArcs().get(i).getSource().getId());
				}
				if(!destinations.contains(this.getArcs().get(i).getDestination().getId())
						&& this.getArcs().get(i).getSource().getId() != this.getArcs().get(i).getDestination().getId()) {
					
					destinations.add(this.getArcs().get(i).getDestination().getId());
				}
			}
			Collections.sort(sources);
			Collections.sort(destinations);
			System.out.println(sources.size() +" ------- "+ destinations.size());
			System.out.println(sources);
			System.out.println(destinations);
			if(sources.equals(destinations)) return true;
			else return false;
		} else {
			for(int i=0; i<this.getNoeuds().size(); i++) {
				if(!inclus.contains(this.getNoeuds().get(i).getId())) {
					inclus.add(this.getNoeuds().get(i).getId());
					count++;
				}
			}
			if(this.getNoeuds().size() == count) return true;
			else return false;
		}
	}
}

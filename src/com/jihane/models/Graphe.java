package com.jihane.models;
import java.util.Collections;
import java.util.LinkedList;

import javax.management.Descriptor;

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

	public boolean isReflexive(boolean orientation) {
		int countVertexes = 0;
		for(int i=0; i<this.getArcs().size(); i++) {
			LinkedList<Integer> vertex = new LinkedList<Integer>();
			if (this.getArcs().get(i).getSource().getId() == this.getArcs().get(i).getDestination().getId()) {
				countVertexes++;
			}
		}
		if (countVertexes == this.noeuds.size()) return true;
		else return false;
	}

	public boolean isSymmetric(boolean orientation) {
		LinkedList<LinkedList<Integer>> sources = new LinkedList<LinkedList<Integer>>();
		LinkedList<LinkedList<Integer>> destinations = new LinkedList<LinkedList<Integer>>();
		int countSource = 0;
		int countDestination = 0;
		if (!orientation) {
			if (this.isConnexe(orientation)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (this.isConnexe(orientation)) {
				for(int i=0; i<this.getArcs().size(); i++) {
					LinkedList<Integer> source = new LinkedList<Integer>();
					LinkedList<Integer> destination = new LinkedList<Integer>();
					source.add(this.getArcs().get(i).getSource().getId());
					source.add(this.getArcs().get(i).getDestination().getId());
					destination.add(this.getArcs().get(i).getDestination().getId());
					destination.add(this.getArcs().get(i).getSource().getId());
					sources.add(source);
					destinations.add(destination);
				}
				for(int i=0; i<sources.size(); i++) {
					for(int j=0; j<destinations.size(); j++) {
						if (sources.get(i).equals(destinations.get(j))) countSource++;					
					}				
				}
				for(int i=0; i<destinations.size(); i++) {
					for(int j=0; j<sources.size(); j++) {
						if (destinations.get(i).equals(sources.get(j))) countDestination++;					
					}		
				}
				if(countSource == sources.size() && countDestination == destinations.size()) return true;
				else return false;
			} else {
				return false;			
			}
		}
	}
	
	public boolean isAntisymmetric(boolean orientation) {
		LinkedList<LinkedList<Integer>> sources = new LinkedList<LinkedList<Integer>>();
		LinkedList<LinkedList<Integer>> destinations = new LinkedList<LinkedList<Integer>>();
		int countSource = 0;
		int countDestination = 0;
		if (!orientation) {
			return false;
		} else {
			if (this.isConnexe(orientation)) {
				for(int i=0; i<this.getArcs().size(); i++) {
					LinkedList<Integer> source = new LinkedList<Integer>();
					LinkedList<Integer> destination = new LinkedList<Integer>();
					source.add(this.getArcs().get(i).getSource().getId());
					source.add(this.getArcs().get(i).getDestination().getId());
					destination.add(this.getArcs().get(i).getDestination().getId());
					destination.add(this.getArcs().get(i).getSource().getId());
					sources.add(source);
					destinations.add(destination);
				}
				for(int i=0; i<sources.size(); i++) {
					for(int j=0; j<destinations.size(); j++) {
						if (sources.get(i).equals(destinations.get(j))) countSource++;					
					}				
				}
				for(int i=0; i<destinations.size(); i++) {
					for(int j=0; j<sources.size(); j++) {
						if (destinations.get(i).equals(sources.get(j))) countDestination++;					
					}		
				}
				System.out.println(sources);
				System.out.println(destinations);
				if(countSource == 0 && countDestination == 0) return true;
				else return false;
			} else {
				return false;			
			}
		}
	}
	
	public void verifyProperties(boolean orientation) {
		if (this.isConnexe(orientation)) System.out.println("Ce graphe est Connexe");
		else System.out.println("Ce graphe n'est pas Connexe");
		if (this.isReflexive(orientation)) System.out.println("Ce graphe est Reflexif");
		else System.out.println("Ce graphe n'est pas Reflexif");
		if (this.isSymmetric(orientation)) System.out.println("Ce graphe est Symétrique");
		else System.out.println("Ce graphe n'est pas Symétrique");
		if (this.isAntisymmetric(orientation)) System.out.println("Ce graphe est Antisymétrique");
		else System.out.println("Ce graphe n'est pas Antisymétrique");
	}
}

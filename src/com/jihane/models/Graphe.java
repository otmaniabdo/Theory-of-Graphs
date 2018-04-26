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
	
	public boolean isArbre(boolean orientation) {
		if(orientation) {
			return false;
		} else {
			if(this.isConnexe(orientation)) {
				for(int i=0; i<this.getArcs().size(); i++) {
					if(this.getArcs().get(i).getSource() == this.getArcs().get(i).getDestination()) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isConnexe(boolean orientation) {
		LinkedList<Integer> sources = new LinkedList<Integer>();
		LinkedList<Integer> destinations = new LinkedList<Integer>();
		LinkedList<Integer> nds = new LinkedList<Integer>();
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
		} else {
			for(int i=0; i<this.getArcs().size(); i++) {
				if(!nds.contains(this.getArcs().get(i).getSource().getId())
						&& this.getArcs().get(i).getSource().getId() != this.getArcs().get(i).getDestination().getId()) {
					
					nds.add(this.getArcs().get(i).getSource().getId());
				}
				if(!nds.contains(this.getArcs().get(i).getDestination().getId())
						&& this.getArcs().get(i).getSource().getId() != this.getArcs().get(i).getDestination().getId()) {
					
					nds.add(this.getArcs().get(i).getDestination().getId());
				}
			}
		}

		Collections.sort(sources);
		Collections.sort(destinations);

		if(nds.size() == this.getNoeuds().size())	return true;
		else if(sources.equals(destinations) && !sources.isEmpty() && !destinations.isEmpty()) return true;
		else return false;
	}

	public boolean isReflexive(boolean orientation) {
		int countVertexes = 0;
		for(int i=0; i<this.getArcs().size(); i++) {
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
				if(countSource == 0 && countDestination == 0) return true;
				else return false;
			} else {
				return false;			
			}
		}
	}
	
	public boolean isTransitive(boolean orientation) {
		if(!orientation) {
			if(this.isConnexe(orientation)) {
				int count =0;
				for(int i=0; i<this.getNoeuds().size(); i++) {
					for(int j=0; j<this.getArcs().size(); j++) {
						if(this.getArcs().get(j).getSource().getId() == this.getNoeuds().get(i).getId()) {
							count++;
						}
					}
				}
				if(count == this.getArcs().size())	return true;
				else	return false;
			} else {
				return false;
			}
		} else {
			if(this.isConnexe(orientation)) {
				LinkedList<Arc> arcs = new LinkedList<Arc>();
				arcs = this.getArcs();
				int count = 0;
				for(int i=0; i<arcs.size(); i++) {
					Arc arc1 = new Arc();
					arc1 = arcs.get(i);
					for(int j=0; j<arcs.size(); j++) {
						if(arcs.get(j).getSource().getId() == arc1.getDestination().getId()
								&& arcs.get(j).getDestination().getId() != arc1.getSource().getId()
								&& i!=j) {
							Arc arc2 = new Arc();
							arc2 = arcs.get(j);
							for(int k=0; k<arcs.size(); k++) {
								if(arcs.get(k).getSource().getId() == arc1.getSource().getId()
										&& arcs.get(k).getDestination().getId() == arc2.getDestination().getId()) {
									count++;
								}
							}
						}
					}
				}
				if(count == this.getNoeuds().size()*2)  return true;
				else	return false;
			} else {
				return false;
			}
		}

	}

	public String verifyProperties(boolean orientation) {
		String s = "";
		if (this.isConnexe(orientation)) s = s + "Ce graphe est Connexe\n";
		else s = s + "Ce graphe n'est pas Connexe\n";
		if (this.isReflexive(orientation)) s = s + "Ce graphe est Reflexif\n";
		else s = s + "Ce graphe n'est pas Reflexif\n";
		if (this.isSymmetric(orientation)) s = s + "Ce graphe est Symétrique\n";
		else s = s + "Ce graphe n'est pas Symétrique\n";
		if (this.isAntisymmetric(orientation)) s = s + "Ce graphe est Antisymétrique\n";
		else s = s + "Ce graphe n'est pas Antisymétrique\n";
		if (this.isTransitive(orientation)) s = s + "Ce graphe est Transitive\n";
		else s = s + "Ce graphe n'est pas Transitive\n";
		return s;
	}
}

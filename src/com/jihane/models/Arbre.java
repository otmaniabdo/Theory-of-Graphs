
package com.jihane.models;

import java.util.*;


public class Arbre {
    
    public LinkedList<Arc>  arcs = new LinkedList<>();
    public LinkedList<ArbreNoeud> noeuds = new LinkedList<>();
    public boolean isArbre;
    
   
    
    public Arbre(LinkedList<Arc> arcs, LinkedList<Noeud> noeuds) {
		super();
		this.arcs = arcs;
		for(Noeud n : noeuds) {
			ArbreNoeud arn = new ArbreNoeud(n.id,n.nom,false);
			arn.parent = new ArbreNoeud();
			this.noeuds.add(arn);
			
		}
		
		for (Arc arc : arcs) {
			this.noeuds.get(arc.getDestination().id-1).parent.setId(arc.getSource().id);
		}
		
		
		if(this.isArbre()) {
			this.isArbre = true;
		}else {
			this.isArbre = false;
		}
			
	}


	public LinkedList<Arc> getArcs() {
		return arcs;
	}


	public void setArcs(LinkedList<Arc> arcs) {
		this.arcs = arcs;
	}


	public LinkedList<ArbreNoeud> getNoeuds() {
		return noeuds;
	}


	public void setNoeuds(LinkedList<ArbreNoeud> noeuds) {
		this.noeuds = noeuds;
	}
        
        public int nombreNoeud(){
            return noeuds.size();
                
        }
        
    public boolean isCyclic(){
            
    	DefaultDirectedGraph<String, DefaultEdge> graphNoCycle = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    	for(Noeud n : noeuds) {
    		graphNoCycle.addVertex(Integer.toString(n.id));
    	}
    	for(Arc arc : arcs) {
    		graphNoCycle.addEdge(Integer.toString(arc.getSource().id), Integer.toString(arc.getDestination().id));
    	}
    	CycleDetector<String, DefaultEdge> cycleDetector = new CycleDetector<String, DefaultEdge>(graphNoCycle);
    	Set<String> cycleVertices = cycleDetector.findCycles();
    	if(cycleVertices.isEmpty()) {
    		return false;
    	}
    	return true;
    }
            
	public boolean isArbre(){
		
        if(!this.isCyclic() && this.isConnexe(false))
        	return true;
        return false;
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
    
}


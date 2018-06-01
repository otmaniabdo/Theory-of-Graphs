
package com.jihane.models;

import java.util.*;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import edu.uci.ics.jung.graph.UndirectedGraph;


public class Arbre {
    
    public LinkedList<Arc>  arcs = new LinkedList<>();
    public LinkedList<ArbreNoeud> noeuds = new LinkedList<>();
    public boolean isArbre;
    public LinkedList<Integer> adj[];
   
    
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
            
    	 adj= new LinkedList[noeuds.size()+1];
    	 
    	 for(Noeud N : noeuds)
             adj[N.id] = new LinkedList();
    	 
    	 for(Arc arc : arcs) {
    	        adj[arc.getSource().id].add(arc.getDestination().id);
    	        adj[arc.getDestination().id].add(arc.getSource().id);
    	 }
    	 
    	 Boolean visited[] = new Boolean[noeuds.size()+1];
         for (int i = 1; i < noeuds.size()+1; i++) {
             visited[i] = false;
         }
  
         // Call the recursive helper function to detect cycle in
         // different DFS trees
         for (int u = 1; u < noeuds.size(); u++)
             if (!visited[u]) // Don't recur for u if already visited
                 if (isCyclicUtil(u, visited, -1))
                     return true;
  
         return false;
    	 
    }
    
    Boolean isCyclicUtil(int v, Boolean visited[], int parent)
    {
        // Mark the current node as visited
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext())
        {
            i = it.next();

            // If an adjacent is not visited, then recur for that
            // adjacent
            if (!visited[i])
            {
                if (isCyclicUtil(i, visited, v)) {
                    return true;
                }

            }
 
            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            else if (i != parent) {
                return true;
            }

        }
        return false;
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


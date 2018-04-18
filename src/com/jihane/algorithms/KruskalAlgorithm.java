package com.jihane.algorithms;

import com.jihane.models.Graphe;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import com.jihane.models.Arc;


public class KruskalAlgorithm {
	
	private Graphe graphe;

	public KruskalAlgorithm(Graphe graphe){
		
		this.graphe = graphe;
		
	}
	
	public void setGraphe (Graphe graphe) {
		
		this.graphe = graphe;
		
	}
	
    public LinkedList<Arc> execute() {
    	
        LinkedList<Arc> spanningTree = new LinkedList<Arc>();
        LinkedList<Arc> arcs = graphe.getArcs();
        int nodeCount = graphe.getNoeuds().size();
        Collections.sort(arcs,new PoidsComparator());
        int i = 0;
        for (Arc arc : this.graphe.getArcs()) {
        	if(i == nodeCount || spanningTree.size() == nodeCount - 1)
        		break;
            if(arc.getSource() != arc.getDestination()){
                spanningTree.add(arc);
            } 
            i++;
        }
        
        return spanningTree;
    }
}

class PoidsComparator implements Comparator<Arc>{  
	public int compare(Arc a1,Arc a2){  
		
		if(a1.getPoids() == a2.getPoids())  
			return 0;  
		else if(a1.getPoids() > a2.getPoids())  
			return 1;  
		else  
			return -1;    
		
	}  
}  
	



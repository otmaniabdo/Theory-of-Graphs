package com.jihane.gui;

import java.util.LinkedList;

import com.jihane.models.Arc;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;


public class DrawingGraph {
	
	Graph<Integer, String> g;
	
	public DrawingGraph(LinkedList<Arc> arcs,int NbrNoeuds) {
		 g = new SparseGraph<Integer, String>();
		 if(NbrNoeuds > 0) {
			 for(int i = 1;i<=NbrNoeuds;i++) {
				 g.addVertex(i);
			 }
		 }
		 for(Arc arc : arcs) {
				 g.addEdge("Edge "+arc.getId() + "- " + arc.getPoids(),arc.getSource().getId() ,arc.getDestination().getId());

		 }
	}
	public Graph getGraph() {
		return g;
	}
	public void setGraph(Graph g) {
		this.g = g;
	}

}

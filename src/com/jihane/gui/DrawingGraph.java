package com.jihane.gui;

import java.util.LinkedList;

import com.jihane.models.Arc;
import com.jihane.models.Noeud;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;


public class DrawingGraph {
	
	Graph<Integer, String> g;
	
	public DrawingGraph(LinkedList<Arc> arcs,int NbrNoeuds,boolean orientation) {
		 g = new SparseGraph<Integer, String>();
		 if(NbrNoeuds > 0) {
			 for(int i = 1;i<=NbrNoeuds;i++) {
				 g.addVertex(i);
			 }
		 }
		 if(orientation) {
			 for(Arc arc : arcs) {
				 g.addEdge("Edge "+arc.getId() + "- " + arc.getPoids(),arc.getSource().getId() ,arc.getDestination().getId(),EdgeType.DIRECTED);

			 }			 
		 }else {
			 for(Arc arc : arcs) {
				 g.addEdge("Edge "+arc.getId() + "- " + arc.getPoids(),arc.getSource().getId() ,arc.getDestination().getId());

			 } 
		 }

	}
	public DrawingGraph(LinkedList<Arc> arcs,LinkedList<Noeud> Noeuds,boolean orientation) {
		 g = new SparseGraph<Integer, String>();
		 
		 for(Noeud N : Noeuds) {
			 g.addVertex(N.id);
		 }
				 
		 
		 if(orientation) {
			 for(Arc arc : arcs) {
				 g.addEdge("Edge "+arc.getId() + "- " + arc.getPoids(),arc.getSource().getId() ,arc.getDestination().getId(),EdgeType.DIRECTED);

			 }			 
		 }else {
			 for(Arc arc : arcs) {
				 g.addEdge("Edge "+arc.getId() + "- " + arc.getPoids(),arc.getSource().getId() ,arc.getDestination().getId());

			 } 
		 }

	}
	public Graph getGraph() {
		return g;
	}
	public void setGraph(Graph g) {
		this.g = g;
	}

}

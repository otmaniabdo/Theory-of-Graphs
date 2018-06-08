package com.jihane.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jihane.algorithms.AlgorithmeDijkstra;
import com.jihane.models.Arc;
import com.jihane.models.Graphe;
import com.jihane.models.Noeud;
import java.awt.Color;

public class DjikstraUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public DjikstraUI(AlgorithmeDijkstra ad, Graphe graphe, LinkedList<Noeud> chemin, String GrapheLayout,boolean orientation) {
		setBackground(Color.WHITE);
		setResizable(false);
		setTitle("Plus Court Chemin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 791, 537);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setToolTipText("");
		contentPane.add(panel, BorderLayout.CENTER);
//		graphe.setNoeuds(null);
//		graphe.setNoeuds(chemin);
		LinkedList<Arc> arcs = new LinkedList<Arc>();
    	int k = 1;
//    	if(orientation == false) {
//    		for(int i=0; i<graphe.getArcs().size(); i++) {
//    			int count = 0;
//    			for(int j=0; j<graphe.getNoeuds().size(); j++) {
//    				if((graphe.getArcs().get(i).getSource().getId() == graphe.getNoeuds().get(j).getId()
//    						&& chemin.contains(graphe.getArcs().get(i).getDestination()))
//    						|| (graphe.getArcs().get(i).getDestination().getId() == graphe.getNoeuds().get(j).getId()
//    						&& chemin.contains(graphe.getArcs().get(i).getSource()))) {
//    					count++;
//    				}
//    			}
//    			if(count == graphe.getNoeuds().size()) {
//    				arcs.add(graphe.getArcs().get(i));
//    				arcs.add(new Arc(graphe.getArcs().size()+k, graphe.getArcs().get(i).getPoids(), graphe.getArcs().get(i).getDestination(), graphe.getArcs().get(i).getSource()));
//    				k++;
//    			}
//    		}
//    		graphe.setArcs(null);
//    		graphe.setArcs(arcs);
//    	}
    	int id = 1;
    	for(int i = 0 ; i<chemin.size();i++) {
    		if(i+1<chemin.size()) {
    			arcs.add(new Arc(id,i,chemin.get(i),chemin.get(i+1)));
    		}
    	}
    	graphe.setArcs(null);
  		graphe.setArcs(arcs);
    	System.out.println("Arc List :"+arcs);
		dessinerLePlusCourtChemin(ad, graphe, chemin, GrapheLayout, orientation);
	}

	private void dessinerLePlusCourtChemin(AlgorithmeDijkstra ad, Graphe graphe, LinkedList<Noeud> chemin, String GrapheLayout,boolean orientation) {
		ad.setGraphe(graphe);
		contentPane.removeAll();
		contentPane.add(ad.DrawGraph(graphe, chemin, GrapheLayout, orientation));
	}
}

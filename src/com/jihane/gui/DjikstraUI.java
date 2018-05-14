package com.jihane.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jihane.algorithms.AlgorithmeDijkstra;
import com.jihane.models.Graphe;
import com.jihane.models.Noeud;

public class DjikstraUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public DjikstraUI(AlgorithmeDijkstra ad, Graphe graphe, LinkedList<Noeud> chemin, String GrapheLayout,boolean orientation) {
		setResizable(false);
		setTitle("Plus Court Chemin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 791, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setToolTipText("");
		contentPane.add(panel, BorderLayout.CENTER);
		
		dessinerLePlusCourtChemin(ad, graphe, chemin, GrapheLayout, orientation);
	}

	private void dessinerLePlusCourtChemin(AlgorithmeDijkstra ad, Graphe graphe, LinkedList<Noeud> chemin, String GrapheLayout,boolean orientation) {

		contentPane.removeAll();
		contentPane.add(ad.DrawGraph(graphe, chemin, GrapheLayout, orientation));
	}
}

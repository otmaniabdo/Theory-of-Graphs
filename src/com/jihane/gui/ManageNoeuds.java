package com.jihane.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.jihane.models.Noeud;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ManageNoeuds extends JFrame {

	JTable table = new JTable(new DefaultTableModel(new Object[]{"Noeud ID", "Nom"}, 0));
	DefaultTableModel model = (DefaultTableModel) table.getModel();

	public ManageNoeuds(int nombreNoeuds) {
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 512, 427);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		this.dessinerTable(nombreNoeuds);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(0, 42, 494, 292);
		getContentPane().add(js);

		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFrame frame;
					Main window = new Main(nommerNoeuds(table, nombreNoeuds), nombreNoeuds);
//					window.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					setVisible(false);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnValider.setBounds(0, 335, 494, 45);
		getContentPane().add(btnValider);

		JLabel lblNommezLesNoeuds = new JLabel("Nommez les noeuds");
		lblNommezLesNoeuds.setBounds(12, 13, 133, 16);
		getContentPane().add(lblNommezLesNoeuds);
	}
	
	public void dessinerTable(int nombreNoeuds) {
		for(int i=1; i<=nombreNoeuds; i++) {
			model.addRow(new Object[]{i, ""});
		}
	}
	
	public LinkedList<Noeud> nommerNoeuds(JTable table, int nombreNoeuds) {
		int count;
		LinkedList<Noeud> noeuds = new LinkedList<Noeud>();
		for(count=0; count<model.getRowCount(); count++) {
			Noeud noeud = new Noeud();
			noeud.setId(Integer.parseInt(model.getValueAt(count, 0).toString()));
			noeud.setNom(model.getValueAt(count, 1).toString());
			noeuds.add(noeud);
		}
		return noeuds;
	}
}

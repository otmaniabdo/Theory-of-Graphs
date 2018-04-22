package com.jihane.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.commons.collections15.Transformer;

import com.jihane.algorithms.AlgorithmeDijkstra;
import com.jihane.algorithms.GraphColoring;
import com.jihane.algorithms.KruskalAlgorithm;
import com.jihane.models.Arc;
import com.jihane.models.Graphe;
import com.jihane.models.Noeud;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;

import javax.swing.JSeparator;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BasicStroke;
import java.awt.Choice;
import javax.swing.JCheckBox;

public class Main extends JFrame{

	JFrame frame;
	private JTextField nombreNoeudsField;
	private JTextField nombreArcsField;
	public JPanel panel_4 = new JPanel();
	public JButton btnDjikstra = new JButton("Djikstra");
	public JButton buttonKruskal = new JButton("Kruskal");
	public JCheckBox chckbxOrient = new JCheckBox("Orient\u00E9");
	public JButton btnColoriage = new JButton("Colorier le graphe");

	JComboBox cbDjikstraDebut;
	JComboBox cbDjikstraFin;
	static LinkedList<Noeud> noeuds = new LinkedList<Noeud>();
	static LinkedList<Arc> arcs = new LinkedList<Arc>();
	static Graphe graphe;
	static boolean orientation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	public Main(LinkedList<Noeud> noeuds, int nombreNoeuds) {
		this.setNoeuds(noeuds);
		initialize();
		nombreNoeudsField.setText(Integer.toString(nombreNoeuds));
	}

	public Main(LinkedList<Arc> arcs, int nombreNoeuds, int nombreArcs) {
		this.setArcs(arcs);
		initialize();
		
		nombreNoeudsField.setText(Integer.toString(nombreNoeuds));
		nombreArcsField.setText(Integer.toString(nombreArcs));
		
		graphe = new Graphe(arcs, noeuds);
		System.out.println(graphe.isConnexe(orientation));
		System.out.println(graphe.isSymmetric(orientation));
		System.out.println(graphe.isAntisymmetric(orientation));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 0, 1093, 750);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(54, 33, 89));
		panel.setBounds(0, 0, 437, 711);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(444, 0, 643, 131);
		panel_2.setBackground(new Color(110, 89, 222));
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(85, 55, 118));
		panel_1.setBounds(10, 193, 420, 50);
		panel.add(panel_1);

		panel_4.setBounds(444, 200, 631, 500);
		frame.getContentPane().add(panel_4);

		nombreNoeudsField = new JTextField();
		nombreNoeudsField.setBorder(null);
		nombreNoeudsField.setBounds(311, 23, 140, 22);
		nombreNoeudsField.setBackground(new Color(110, 89, 222));
		panel_2.add(nombreNoeudsField);
		nombreNoeudsField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Saisissez le nombre de Noeuds :");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(12, 25, 236, 16);
		panel_2.add(lblNewLabel);
		
		JButton btnValiderNoeuds = new JButton("Valider");
		btnValiderNoeuds.setBounds(508, 23, 97, 25);
		btnValiderNoeuds.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_2.add(btnValiderNoeuds);
		
		JLabel lblSaisissezLeNombre = new JLabel("Saisissez le nombre d'arcs :");
		lblSaisissezLeNombre.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblSaisissezLeNombre.setForeground(new Color(255, 255, 255));
		lblSaisissezLeNombre.setBounds(12, 61, 200, 16);
		panel_2.add(lblSaisissezLeNombre);
		
		nombreArcsField = new JTextField();
		nombreArcsField.setBorder(null);
		nombreArcsField.setBounds(311, 59, 140, 22);
		nombreArcsField.setBackground(new Color(110, 89, 222));
		panel_2.add(nombreArcsField);
		nombreArcsField.setColumns(10);
		
		cbDjikstraDebut = new JComboBox();
		cbDjikstraDebut.setBounds(12, 14, 122, 22);
		panel_1.add(cbDjikstraDebut);
		
		cbDjikstraFin = new JComboBox();
		cbDjikstraFin.setBounds(146, 14, 122, 22);
		panel_1.add(cbDjikstraFin);

		JButton btnValiderArcs = new JButton("Valider");
		btnValiderArcs.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnValiderArcs.setEnabled(false);
		btnValiderArcs.setBounds(508, 59, 97, 25);
		panel_2.add(btnValiderArcs);
		
		Choice choice = new Choice();
		choice.setBounds(311, 99, 140, 22);
		choice.add("FRLayout");
		choice.add("CircleLayout");
		choice.add("SpringLayout");
		choice.add("SpringLayout2");
		panel_2.add(choice);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(311, 47, 140, 7);
		panel_2.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(311, 86, 140, 7);
		panel_2.add(separator_2);
		chckbxOrient.setFont(new Font("Traditional Arabic", Font.BOLD | Font.ITALIC, 18));
		chckbxOrient.setBounds(508, 91, 97, 30);
		chckbxOrient.setBackground(new Color(100, 89, 222));
		panel_2.add(chckbxOrient);

		btnValiderArcs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int nombreArcs = Integer.parseInt(nombreArcsField.getText());
				orientation = chckbxOrient.isSelected();

				new ManageArcs(noeuds, nombreArcs,choice.getSelectedItem(), orientation).setVisible(true);
				frame.setVisible(false);
			}
		});
		btnValiderNoeuds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(nombreNoeudsField.getText().equals("")) {
				}else {
					if(Integer.parseInt(nombreNoeudsField.getText()) == 0) {						
					}else {
						int nombreNoeuds = Integer.parseInt(nombreNoeudsField.getText());
						 	noeuds.clear();
						for(int i=1; i<=nombreNoeuds; i++) {
							noeuds.add(new Noeud(i, Integer.toString(i)));
						}
						btnValiderArcs.setEnabled(true);
					}

				}
					
			}
		});

		JTextArea logField = new JTextArea();
		logField.setBackground(new Color(255, 255, 204));
		logField.setForeground(new Color(0, 0, 0));
		logField.setBounds(444, 144, 631, 43);
		frame.getContentPane().add(logField);
		panel_1.setLayout(null);
		
		btnDjikstra.setEnabled(false);
		btnDjikstra.setBounds(280, 13, 128, 25);
		btnDjikstra.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_1.add(btnDjikstra);
		
		JLabel lblGraphtheoryAlgorithm = new JLabel("Graph\u2010Theory Algorithms");
		lblGraphtheoryAlgorithm.setBounds(34, 11, 299, 33);
		panel.add(lblGraphtheoryAlgorithm);
		lblGraphtheoryAlgorithm.setBackground(Color.WHITE);
		lblGraphtheoryAlgorithm.setForeground(Color.LIGHT_GRAY);
		lblGraphtheoryAlgorithm.setFont(new Font("Segoe UI", Font.BOLD, 24));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(27, 57, 358, 9);
		panel.add(separator);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(85, 55, 118));
		panel_3.setBounds(10, 253, 420, 50);
		panel.add(panel_3);
		
		buttonKruskal.setEnabled(false);
		buttonKruskal.setBounds(138, 11, 128, 25);
		buttonKruskal.setCursor(new Cursor(Cursor.HAND_CURSOR));
		buttonKruskal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Graphe graphe = new Graphe(arcs, noeuds);
				KruskalAlgorithm ks = new KruskalAlgorithm(graphe);
				logField.setText(" **** Kruskal : ");
				LinkedList<Arc> listKruskal = ks.execute();
				for (Arc arc : listKruskal) {
					logField.setText(logField.getText() + arc.toPath());
				}
				
				panel_4.removeAll();
				panel_4.add(ks.DrawGraph(noeuds,listKruskal,choice.getSelectedItem(), orientation));
			}
		});
		panel_3.add(buttonKruskal);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(85, 55, 118));
		panel_5.setBounds(10, 313, 420, 50);
		panel.add(panel_5);

		btnColoriage.setEnabled(false);
		btnColoriage.setBounds(128, 13, 145, 25);
		btnColoriage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_5.add(btnColoriage);
		btnColoriage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
			        panel_4.removeAll();
					DrawingGraph grph = new DrawingGraph(graphe.getArcs(), graphe.getNoeuds().size(), orientation);
					Layout<Integer, String> layout;
					if(choice.getSelectedItem().equals("FRLayout")) {
						layout = new FRLayout<>(grph.g);
					}else if(choice.getSelectedItem().equals("CircleLayout")) {
						layout = new CircleLayout<>(grph.g);
					}else if(choice.getSelectedItem().equals("SpringLayout")){
						layout = new SpringLayout<>(grph.g);
					}else{
						layout = new SpringLayout2<>(grph.g);
					}
					
			        layout.setSize(new Dimension(480, 480));
					BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
			        vv.setPreferredSize(new Dimension(511, 511));       
			        // Setup up a new vertex to paint transformer...
			        GraphColoring gc = new GraphColoring(graphe.getArcs().size());
			        gc.adaptGraph(graphe);
			        gc.coloriage();
			        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
			            @Override
						public Paint transform(Integer i) {
			            	switch (gc.getCouleur(i-1)) {
			            		case "GREEN" : return Color.GREEN;
			            		case "BLUE" : return Color.BLUE;
			            		case "YELLOW" : return Color.YELLOW;
			            		case "GRAY" : return Color.GRAY;
			            		case "RED" : return Color.RED;
			            		case "ORANGE" : return Color.ORANGE;
			            		case "CYAN" : return Color.CYAN;
			            		case "PINK" : return Color.PINK;
			            	}
							return Color.MAGENTA;
						}
			        };  
			        // Set up a new stroke Transformer for the edges
			        float dash[] = {10.0f};
			        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
			             BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
			        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
			            @Override
						public Stroke transform(String s) {
			                return edgeStroke;
			            }
			        };
			        
			        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
			        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
			        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
			        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
			        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); 
			        panel_4.add(vv);
					setVisible(false);
					btnDjikstra.setEnabled(true);
					buttonKruskal.setEnabled(true);
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnDjikstra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				AlgorithmeDijkstra ad = new AlgorithmeDijkstra(graphe, orientation);
				if(cbDjikstraDebut.getSelectedItem() != null) {
					if(cbDjikstraFin.getSelectedItem() != null) {
						Noeud source = noeuds.get(Integer.parseInt(cbDjikstraDebut.getSelectedItem().toString())-1);
						Noeud destination = noeuds.get(Integer.parseInt(cbDjikstraFin.getSelectedItem().toString())-1);
						LinkedList<Noeud> chemin = ad.dessinerChemin(source, destination);
						logField.setText(ad.plusCourtChemin(source, destination));
						panel_4.removeAll();
						panel_4.add(ad.DrawGraph(graphe, chemin,choice.getSelectedItem(), orientation));
					}else {
						JOptionPane.showMessageDialog(null, "Vous devez saisir le noeud destination");
					}
				}else {
					if(cbDjikstraFin.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Vous devez saisir les noeud source et destination");
					}else {
						JOptionPane.showMessageDialog(null, "Vous devez saisir le noeud source");
					}
				}
			}
		});
	}
	
	public void getArray(int nbr) {
		ArrayList<Integer> arl = new ArrayList<Integer>();
		for(int i=1; i<=nbr; i++)	arl.add(i);
		this.cbDjikstraDebut.setModel(new DefaultComboBoxModel(arl.toArray()));
		this.cbDjikstraFin.setModel(new DefaultComboBoxModel(arl.toArray()));
	}

	public LinkedList<Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(LinkedList<Noeud> noeuds) {
		Main.noeuds = noeuds;
	}

	public LinkedList<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(LinkedList<Arc> arcs) {
		Main.arcs = arcs;
	}
}

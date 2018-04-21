package com.jihane.gui;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections15.Transformer;

import com.jihane.models.Arc;
import com.jihane.models.Noeud;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;

public class ManageArcs extends JFrame {

	JTable table = new JTable(new DefaultTableModel(new Object[]{"Arc ID", "Poids", "Source", "Destination"}, 0));
	DefaultTableModel model = (DefaultTableModel) table.getModel();

	
	/**
	 * Create the frame.
	 */
	public ManageArcs(LinkedList<Noeud> noeuds, int nombreArcs, String GrapheLayout) {
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 512, 427);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		this.dessinerTable(nombreArcs);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(0, 42, 494, 292);
		getContentPane().add(js);
		
		JButton btnNewButton = new JButton("Valider");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame frame;
					LinkedList<Arc> arcs = nommerArcs(table, noeuds, nombreArcs);
					Main window = new Main(arcs, noeuds.size(), nombreArcs);
					DrawingGraph grph = new DrawingGraph(arcs,noeuds.size());
					Layout<Integer, String> layout;
					if(GrapheLayout.equals("FRLayout")) {
						layout = new FRLayout<>(grph.g);
					}else if(GrapheLayout.equals("CircleLayout")) {
						layout = new CircleLayout<>(grph.g);
					}else if(GrapheLayout.equals("SpringLayout")){
						layout = new SpringLayout<>(grph.g);
					}else{
						layout = new SpringLayout2<>(grph.g);
					}
					
			        layout.setSize(new Dimension(480, 480));
					BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
			        vv.setPreferredSize(new Dimension(511, 511));       
			        // Setup up a new vertex to paint transformer...
			        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
			            @Override
						public Paint transform(Integer i) {
			                return Color.RED;
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
			        window.panel_4.add(vv);
					setVisible(false);
					window.btnDjikstra.setEnabled(true);
					window.buttonKruskal.setEnabled(true);
					window.frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(0, 335, 494, 45);
		getContentPane().add(btnNewButton);
		
		JLabel lblNommezLesArcs = new JLabel("Nommez les arcs :");
		lblNommezLesArcs.setBounds(12, 13, 133, 16);
		getContentPane().add(lblNommezLesArcs);
	}

	public void dessinerTable(int nombreArcs) {
		for(int i=1; i<=nombreArcs; i++) {
			model.addRow(new Object[]{i, "", "", "", ""});
		}
	}
	
	public LinkedList<Arc> nommerArcs(JTable table, LinkedList<Noeud> noeuds,  int nombreArcs) {
		LinkedList<Arc> arcs = new LinkedList<Arc>();
		for(int count=0; count<model.getRowCount(); count++) {
			Arc arc = new Arc();
			arc.setId(Integer.parseInt(model.getValueAt(count, 0).toString()));
			arc.setPoids(Integer.parseInt(model.getValueAt(count, 1).toString()));
			arc.setSource(noeuds.get(Integer.parseInt(model.getValueAt(count, 2).toString())-1));
			arc.setDestination(noeuds.get(Integer.parseInt(model.getValueAt(count, 3).toString())-1));
			arcs.add(arc);
		}
		return arcs;
	}
}

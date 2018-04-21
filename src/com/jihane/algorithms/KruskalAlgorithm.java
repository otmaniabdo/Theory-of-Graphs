package com.jihane.algorithms;

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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.apache.commons.collections15.Transformer;

import com.jihane.gui.DrawingGraph;
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
    
    public BasicVisualizationServer<Integer, String> DrawGraph(LinkedList<Noeud> noeuds,LinkedList<Arc> arcs,String GrapheLayout,boolean orientation) {
    	
    	DrawingGraph grph = new DrawingGraph(arcs,noeuds.size(),orientation);
		Layout<Integer, String> layout;
		if(GrapheLayout.equals("FRLayout")) {
			layout = new FRLayout<>(grph.getGraph());
		}else if(GrapheLayout.equals("CircleLayout")) {
			layout = new CircleLayout<>(grph.getGraph());
		}else if(GrapheLayout.equals("SpringLayout")){
			layout = new SpringLayout<>(grph.getGraph());
		}else{
			layout = new SpringLayout2<>(grph.getGraph());
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
    	System.out.println("Done");

        return vv;
    }
}
class PoidsComparator implements Comparator<Arc>{  
	@Override
	public int compare(Arc a1,Arc a2){  
		
		if(a1.getPoids() == a2.getPoids())  
			return 0;  
		else if(a1.getPoids() > a2.getPoids())  
			return 1;  
		else  
			return -1;    
		
	}  
}

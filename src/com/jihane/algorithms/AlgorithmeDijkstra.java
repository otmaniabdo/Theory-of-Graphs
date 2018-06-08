package com.jihane.algorithms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import com.jihane.gui.DrawingGraph;
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

public class AlgorithmeDijkstra {

	private Graphe graphe;
    private Set<Noeud> noeudsInclus, noeudsExclus;
    private Map<Noeud, Noeud> predecesseurs;
    private Map<Noeud, Integer> distance;
    private boolean orientation;
   
    // Constructeur pour la classe Algorithme de Djikstra
	public AlgorithmeDijkstra(Graphe graphe, boolean orientation) {
		super();
		this.graphe = graphe;
		this.orientation = orientation;
	}

	// Getter pour l'attribut graphe
    public Graphe getGraphe() {
		return graphe;
	}

	// Setter pour l'attribut graphe
	public void setGraphe(Graphe graphe) {
		this.graphe = graphe;
	}
 
	// Elle retourne la distance minimale de tous les voisins de Noeud
    private void trouverDistanceMinimal(Noeud noeud) {
        LinkedList<Noeud> voisions = getVoisions(noeud);
        for (Noeud pivot : voisions) {
            if (getPlusCourteDistance(pivot) > getPlusCourteDistance(noeud)
                    + getDistance(noeud, pivot)) {
                distance.put(pivot, getPlusCourteDistance(noeud)
                        + getDistance(noeud, pivot));
                predecesseurs.put(pivot, noeud);
                noeudsExclus.add(pivot);
            }
        }

    }

    // Elle retourne le poids entre les deux noeuds
	private int getDistance(Noeud source, Noeud destination) {
        for (Arc arc : this.getGraphe().getArcs()) {
            if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
                return arc.getPoids();
            }
        }
        return -1; // Retourner -1 en cas d'erreur
    }
	
	// Elle retourne tous les voisins de noeud
    private LinkedList<Noeud> getVoisions(Noeud noeud) {
    	LinkedList<Noeud> voisions = new LinkedList<Noeud>();
    	LinkedList<Arc> arcs = new LinkedList<Arc>();
    	int i = 1;
    	// Dupliquer les arcs en cas de graphe non orienté
    	if(!orientation) {
    		for(Arc arc : this.getGraphe().getArcs()) {
    			arcs.add(arc);
    			arcs.add(new Arc(this.getGraphe().getArcs().size()+i,arc.getPoids(),arc.getDestination(),arc.getSource()));
    			i++;
    		}
    	}else {
    		arcs = this.getGraphe().getArcs();
    	}
        for (Arc arc : arcs) {
            if (arc.getSource().equals(noeud) && !estInclu(arc.getDestination())) {
                voisions.add(arc.getDestination());
            }
        }

        return voisions;
    }
    
    // Elle permet de retourner le poid minimal dans une liste de noeuds passée en paramétre
    private Noeud getMinimum(Set<Noeud> noeuds) {
    	Noeud minimum = null;
        for (Noeud noeud : noeuds) {
            if (minimum == null) {
                minimum = noeud;
            } else {
                if (getPlusCourteDistance(noeud) < getPlusCourteDistance(minimum)) {
                    minimum = noeud;
                }
            }
        }
        return minimum;
    }

    // Retourne si le Noeud "noeud" est dans la liste noeudsInclus
    private boolean estInclu(Noeud noeud) {
        return noeudsInclus.contains(noeud);
    }
    
    // Avoir la distance minimale jusqu'à le noeud destination donné en paramètre
    public int getPlusCourteDistance(Noeud destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    // Elle permet de retourner une liste des noeuds, et c'est le plus court chemin du noeud source vers le noeud destination
    public LinkedList<Noeud> dessinerChemin(Noeud source, Noeud destination) {
        LinkedList<Noeud> S = new LinkedList<Noeud>();
        noeudsInclus = new HashSet<Noeud>();
        noeudsExclus = new HashSet<Noeud>();
        distance = new HashMap<Noeud, Integer>();
        predecesseurs = new HashMap<Noeud, Noeud>();
        distance.put(source, 0);
        noeudsExclus.add(source);
        while (noeudsExclus.size() > 0) {
            Noeud node = getMinimum(noeudsExclus);
            noeudsInclus.add(node);
            noeudsExclus.remove(node);
            trouverDistanceMinimal(node);
        }
        Noeud step = destination;
        if (predecesseurs.get(step) == null) {
            return null;
        }
        S.add(step);
        while (predecesseurs.get(step) != null) {
            step = predecesseurs.get(step);
            S.add(step);
        }
        Collections.reverse(S);
        return S;
    }
    
    public String plusCourtChemin(Noeud source, Noeud destination) {
    	return this.dessinerChemin(source, destination).toString();
    }
    
    // Afficher le plus court chemin du graphe
    public BasicVisualizationServer<Integer, String> DrawGraph(Graphe graphe, LinkedList<Noeud> chemin,String GrapheLayout,boolean orientation) {
		DrawingGraph grph = new DrawingGraph(graphe.getArcs(),chemin,orientation);
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
        // définir les propriétés graphiques des noeuds lors de l'affichage.
        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
            @Override
			public Paint transform(Integer i) {
                return Color.GRAY;
            }
        };
        // définir les propriétés graphiques des arcs lors de l'affichage.
        float dash[] = {0.1f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
             BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
            @Override
			public Stroke transform(String s) {
                return edgeStroke;
            }
        };
        Transformer<Integer,Shape> vertexSize = new Transformer<Integer,Shape>(){
            public Shape transform(Integer i){
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 20, 20);
                // in this case, the vertex is twice as large
                return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); 

        return vv;
    }
    
    public boolean FindNoeuds(LinkedList<Noeud> noeuds,int i) {
    	for(int j = 0;j < noeuds.size();j++) {
    		if(i == noeuds.get(j).getId()) {
    			return true;
    		}
    	}

		return false;
    }
}

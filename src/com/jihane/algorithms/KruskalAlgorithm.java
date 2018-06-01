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
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.collections15.Transformer;

import com.jihane.gui.DrawingGraph;
import com.jihane.models.Arc;


public class KruskalAlgorithm {
	
	private Graphe graphe;
	private int graphMinPoids = 0;
	public KruskalAlgorithm(Graphe graphe){
		
		this.graphe = graphe;
		
	}
	
	public void setGraphe (Graphe graphe) {
		
		this.graphe = graphe;
		
	}
	
    public LinkedList<Arc> execute() {
		String outputMessage="";

        LinkedList<Arc> spanningTree = new LinkedList<Arc>();
        LinkedList<Arc> arcs = graphe.getArcs();
        LinkedList<Noeud> noeuds = graphe.getNoeuds();
        int nodeCount = graphe.getNoeuds().size();
        Collections.sort(arcs,new PoidsComparator());
		DisjointSet nodeSet = new DisjointSet(nodeCount+1);		//Initialize singleton sets for each node in graph. (nodeCount +1) to account for arrays indexing from 0
        for(int i=0; i<arcs.size() && spanningTree.size()<(nodeCount-1); i++){		//loop over all edges. Start @ 1 (ignore 0th as placeholder). Also early termination when number of edges=(number of nodes-1)
			Arc currentEdge = arcs.get(i);
			int root1 = nodeSet.find(currentEdge.getSource().id);		//Find root of 1 vertex of the edge
			int root2 = nodeSet.find(currentEdge.getDestination().id);
			outputMessage+="find("+currentEdge.getSource().id+") returns "+root1+", find("+currentEdge.getDestination().id+") returns "+root2;		//just print, keep on same line for union message
			String unionMessage=",\tNo union performed\n";		//assume no union is to be performed, changed later if a union DOES happen
			if(root1 != root2){			//if roots are in different sets the DO NOT create a cycle
				spanningTree.add(currentEdge);		//add the edge to the graph
				setGraphMinPoids(getGraphMinPoids() + currentEdge.getPoids());
				nodeSet.union(root1, root2);	//union the sets
				unionMessage=",\tUnion("+root1+", "+root2+") done\n";		//change what's printed if a union IS performed
			}
			outputMessage+=unionMessage;
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
                    return Color.WHITE;
            }
        };  
        Transformer<Integer,Shape> vertexSize = new Transformer<Integer,Shape>(){
            public Shape transform(Integer i){
                Ellipse2D circle = new Ellipse2D.Double(-15, -15, 20, 20);
                // in this case, the vertex is twice as large
                return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
            }
        };
        // Set up a new stroke Transformer for the edges
        float dash[] = {0.1f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
             BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
            @Override
			public Stroke transform(String s) {
                return edgeStroke;
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
    // Q ????
    public boolean isCyclic(Graphe graph, Arc arc, LinkedList<Arc> arcs) {
        LinkedList<Integer> adj[]; // Adjacency List Represntation
        adj = new LinkedList[graph.getNoeuds().size()];
        for(int i=0; i<graph.getNoeuds().size(); ++i)
            adj[i] = new LinkedList();
        
        for(Arc arc1 : arcs) {
        		adj[arc1.getSource().id].add(arc1.getDestination().id);
        		adj[arc1.getDestination().id].add(arc1.getSource().id);
        }
        
        adj[arc.getSource().id].add(arc.getDestination().id);
        System.out.println(adj[arc.getSource().id]);
        adj[arc.getDestination().id].add(arc.getSource().id);

        
           // Mark all the vertices as not visited and not part of
        // recursion stack
        Boolean visited[] = new Boolean[graph.getNoeuds().size()];
        for (int i = 0; i < graph.getNoeuds().size(); i++)
            visited[i] = false;
 
        // Call the recursive helper function to detect cycle in
        // different DFS trees
        for (int u = 0; u < graph.getNoeuds().size(); u++)
            if (!visited[u]) // Don't recur for u if already visited
                if (isCyclicUtil(u, visited, -1, adj))
                    return true;
 
    	return false;
    }
    
    Boolean isCyclicUtil(int v, Boolean visited[], int parent,LinkedList<Integer> adj[])
    {
        // Mark the current node as visited
        visited[v] = true;
        Integer i;
 
        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext())
        {
            i = it.next();
 
            // If an adjacent is not visited, then recur for that
            // adjacent
            if (!visited[i])
            {
                if (isCyclicUtil(i, visited, v, adj))
                    return true;
            }
 
            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            else if (i != parent)
                return true;
        }
        return false;
    }

	public int getGraphMinPoids() {
		return graphMinPoids;
	}

	public void setGraphMinPoids(int graphMinPoids) {
		this.graphMinPoids = graphMinPoids;
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

class DisjointSet{
	private int[] set;		//the disjoint set as an array

	public int[] getSet(){		//mostly debugging method to print array
		return set;
	}

	/**
	 * Construct the disjoint sets object.
	 * @param numElements the initial number of disjoint sets.
	 */
	public DisjointSet(int numElements) {		//constructor creates singleton sets
		set = new int [numElements];
		for(int i = 0; i < set.length; i++){		//initialize to -1 so the trees have nothing in them
			set[i] = -1;
		}
	}

	/**
	 * Union two disjoint sets using the height heuristic.
	 * For simplicity, we assume root1 and root2 are distinct
	 * and represent set names.
	 * @param root1 the root of set 1.
	 * @param root2 the root of set 2.
	 */
	public void union(int root1, int root2) {
		if(set[root2] < set[root1]){		// root2 is deeper
			set[root1] = root2;		// Make root2 new root
		}
		else {
			if(set[root1] == set[root2]){
				set[root1]--;			// Update height if same
			}
			set[root2] = root1;		// Make root1 new root
		}
	}

	/**
	 * Perform a find with path compression.
	 * Error checks omitted again for simplicity.
	 * @param x the element being searched for.
	 * @return the set containing x.
	 */
	public int find(int x) {
		if(set[x] < 0){		//If tree is a root, return its index
			return x;
		}
		int next = x;		
		while(set[next] > 0){		//Loop until we find a root
			next=set[next];
		}
		return next;
	}
	
}
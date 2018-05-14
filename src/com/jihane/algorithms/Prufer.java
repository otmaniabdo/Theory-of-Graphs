package com.jihane.algorithms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections15.Transformer;

import com.jihane.gui.DrawingGraph;
import com.jihane.models.Arbre;
import com.jihane.models.ArbreNoeud;
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

public class Prufer {
	
	public String codage(Arbre arbre) {
		if(arbre.isArbre()) {
			
			int noeudstaille = arbre.noeuds.size();
		    LinkedList<Integer> list = new LinkedList<Integer>();
			
			while(noeudstaille > 2) {
				//System.out.println("arbre size " + noeudstaille);
				LinkedList<Integer> feuitte = feuitte(arbre.arcs,arbre.noeuds);
				int min = 1000;
				for(int i : feuitte) {
					if(i < min) {
						min = i ;
					}
				}
				
				//System.out.println("Min : " + min);
				
				for(Arc arc : arbre.arcs) {
					
					if(arc.getDestination().id == min && arc.active) {
						System.out.println(arc.getSource().id+"->"+arc.getDestination().id);
						list.add(arc.getSource().id);
						arc.active = false;
					}else if(arc.getSource().id == min && arc.active){
						System.out.println(arc.getSource().id+"->"+arc.getDestination().id);
						list.add(arc.getDestination().id);
						arc.active = false;					
					}
				}
				for(ArbreNoeud n : arbre.noeuds) {
					if(n.getId() == min) {
						n.isVisited = true;
						noeudstaille--;
					}

				}
			}
			return list.toString();
		}
		return "";
	}
	
	public LinkedList<Integer> feuitte(LinkedList<Arc> arcs,LinkedList<ArbreNoeud> noeuds) {
	    LinkedList<Integer> list = new LinkedList<Integer>();
		boolean trouver = false;
		for (ArbreNoeud n : noeuds) {
			int taille = 0 ;
			for(Arc c : arcs) {
				if(c.active) {
					if(n.id == c.getDestination().id || n.id == c.getSource().id) {
						if(!n.isVisited)
							taille++;
					}	
				}

			}
			
			if(taille == 1) {
				list.add(n.id);
			}
		}
		//System.out.println("feuitte : "+list.toString());
		return list;
		
	}
	
	public DrawingGraph  decodage(String Noeuds, String codage, boolean orientation) {
		LinkedList<Noeud> noeuds = new LinkedList<Noeud>();
		LinkedList<Arc> arcs = new LinkedList<Arc>();
		LinkedList<Integer> NoeudsList = new LinkedList<Integer>();
		LinkedList<Integer> codageList = new LinkedList<Integer>();
		
		NoeudsList = ToInt(Noeuds);
		NoeudsList.sort(null);
		System.out.println(NoeudsList);

		codageList = ToInt(codage);
		System.out.println(codageList);

		for(int i : NoeudsList) {
			noeuds.add(new Noeud(i,Integer.toString(i)));
		}
		System.out.println(NoeudsList.size());
		int i = 1; 
		if(codageList.size() <=  NoeudsList.size()-2) {
			while (NoeudsList.size() > 2 && !codageList.isEmpty()) {
				int noeud = NoeudsList.getFirst();
				if(!codageList.contains(noeud)) {
					arcs.add(new Arc(i,1,new Noeud(codageList.getFirst(),Integer.toString(codageList.getFirst())),new Noeud(noeud,Integer.toString(noeud))));
					i++;
					NoeudsList.removeFirst();
					codageList.removeFirst();
					System.out.println(NoeudsList);
					System.out.println(codageList);
				}else{
					System.out.println("---- 1");
					for(int j=1;j<NoeudsList.size();j++) {
						System.out.println("---- 2");
						if(!codageList.contains(NoeudsList.get(j))) {
							System.out.println("---- 3");
							int tmp = NoeudsList.getFirst();
							NoeudsList.set(0, NoeudsList.get(j));
							NoeudsList.set(j, tmp);
							break;
						}
					}
				}
				System.out.println("------");
			}
			if(NoeudsList.size() == 2) {
				arcs.add(new Arc(i,1,new Noeud(NoeudsList.getFirst(),Integer.toString(NoeudsList.getFirst())),new Noeud(NoeudsList.getLast(),Integer.toString(NoeudsList.getLast()))));
			}
		}


		
		DrawingGraph grph = new DrawingGraph(arcs,noeuds,orientation);
		return grph;
		
	}
	
	
	public LinkedList<Integer> ToInt(String list){
		LinkedList<Integer> listInt = new LinkedList<Integer>();
		list = list.replace(" ", "");
		list = list.replace("{","");
		list = list.replace("}","");
		String expression = "[0-9]+";
		String expression2 = "[0-9]+(,[0-9]+)*";
		Pattern pattern = Pattern.compile(expression);
		Pattern pattern2 = Pattern.compile(expression2);
		Matcher m = pattern.matcher(list);
		Matcher m2 = pattern2.matcher(list);
		if(m.matches()) {
			listInt.add(Integer.parseInt(list));
			return listInt;
		}else if(m2.matches()) {
			String[] stringList = list.split(",");
			for (String s : stringList) {
				listInt.add(Integer.parseInt(s));
			}
			return listInt;
		}else {
			System.out.println("expression no valider");
		}
		return listInt;
	}
}

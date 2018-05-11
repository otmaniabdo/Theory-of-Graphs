package com.jihane.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.jihane.models.Arbre;
import com.jihane.models.ArbreNoeud;
import com.jihane.models.Arc;
import com.jihane.models.Noeud;

public class Prufer {
	
	public static String codage(Arbre arbre) {
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
	
	public static LinkedList<Integer> feuitte(LinkedList<Arc> arcs,LinkedList<ArbreNoeud> noeuds) {
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

}

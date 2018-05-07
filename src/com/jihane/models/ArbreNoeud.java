package com.jihane.models;

public class ArbreNoeud extends Noeud{
	public boolean isVisited ;
	public ArbreNoeud parent;
	
	public ArbreNoeud(int id, String nom,boolean visited) {
		this.id = id;
		this.nom = nom;
		this.isVisited = visited;
	}
	public ArbreNoeud() {
		this.id = 0;
		this.nom = "0";
		this.isVisited = false;
	}
}

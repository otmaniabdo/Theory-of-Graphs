package com.jihane.models;
import com.jihane.models.Noeud;


public class Arc {
	private int id;
	private int poids;
	private Noeud source;
	private Noeud destination;


	public Arc() {
		super();
	}

	public Arc(int id, int poids, Noeud source, Noeud destination) {
		super();
		this.id = id;
		this.poids = poids;
		this.source = source;
		this.destination = destination;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPoids() {
		return poids;
	}

	public void setPoids(int poids) {
		this.poids = poids;
	}

	public Noeud getSource() {
		return source;
	}

	public void setSource(Noeud source) {
		this.source = source;
	}

	public Noeud getDestination() {
		return destination;
	}

	public void setDestination(Noeud destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Arc [id=" + id + ", poids=" + poids + ", source=" + source + ", destination=" + destination
				+ "]";
	}
	public String toPath() {
		return "-->Arc(id ="+id+",Poid = "+poids+")";
	}
}

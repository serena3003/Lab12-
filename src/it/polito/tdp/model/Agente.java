package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;

public class Agente {
	
	public enum Stato_Agente{
		LIBERO,
		OCCUPATO
	}
	
	private int id;
	private Stato_Agente stato;
	private LatLng posizione;
	private static int idCounter = 0;
	
	public Agente(LatLng posizione) {
		this.id = ++idCounter;
		this.stato = Stato_Agente.LIBERO;
		this.posizione=posizione;
	}

	public Stato_Agente getStato() {
		return stato;
	}

	public void setStato(Stato_Agente stato) {
		this.stato = stato;
	}

	public LatLng getPosizione() {
		return posizione;
	}

	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agente other = (Agente) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Agente [id=" + id + ", stato=" + stato + ", posizione=" + posizione + "]";
	}
	
	

}

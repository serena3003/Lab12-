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
	
	

}

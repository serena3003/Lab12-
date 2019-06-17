package it.polito.tdp.model;

import java.util.PriorityQueue;

import it.polito.tdp.db.EventsDao;

public class Simulatore {
	
	private PriorityQueue<Event> queue;
	private EventsDao dao;
	
	public Simulatore() {
		queue = new PriorityQueue<Event>();
		dao = new EventsDao();
	}
	
	public void init() {
		
		//cerco il distretto con minore criminalità nell'anno in corso
		List<Event> events
	}

}

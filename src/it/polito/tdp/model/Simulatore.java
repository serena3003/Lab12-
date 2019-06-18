package it.polito.tdp.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;
import it.polito.tdp.model.Agente.Stato_Agente;

public class Simulatore {

	private PriorityQueue<EventoSimulazione> queue;
	private EventsDao dao;
	private Model model;
	private Map<Integer, District> districts;
	private List<Agente> agenti;
	private Random rand;

	private int evMalGestiti;

	public Simulatore() {
		queue = new PriorityQueue<EventoSimulazione>();
		dao = new EventsDao();
		//model = new Model();
		//districts = model.getDistrictMap();
		agenti = new ArrayList<Agente>();
		rand = new Random();
	}

	public void init(Map<Integer,District> districts, int year, int month, int day, int N) {
		this.districts = districts;
		evMalGestiti = 0;

		// cerco il distretto con minore criminalità nell'anno in corso
		District distpartenza = districts.get(dao.getDistMinorCrime(year));

		// creo gli agenti
		for (int i = 0; i < N; i++) {
			Agente a = new Agente(distpartenza.getCenter());
			agenti.add(a);
		}

		// creo la coda degli eventi
		this.queue.clear();
		for(Event e : dao.getEventDay(year, month, day)){
			queue.add(new EventoSimulazione(e));
		}

	}

	public void run() {

		while (!queue.isEmpty()) {
			EventoSimulazione evento = queue.poll();
			
			switch (evento.getTipo()) {
			
			case CRIMINE:
				Event ev = evento.getEvento();
				LatLng luogoCrimine = new LatLng(ev.getGeo_lat(), ev.getGeo_lon());
				System.out.println(luogoCrimine.toString());
				// seleziono l'agente migliore
				Agente a = getAgenteDisp(luogoCrimine);
				if(a==null) {
					evMalGestiti++;
				}
				else {a.setStato(Stato_Agente.OCCUPATO);

				// guardo se l'evento è mal gestito
				long tempo = tempoPercorrenza(luogoCrimine, a);
				LocalTime oraCrimine = ev.getReported_date().toLocalTime();
				LocalTime oraArrivoAgente = oraCrimine.plusMinutes(tempo);
				if (oraArrivoAgente.isAfter(oraCrimine.plusMinutes(15))) {
					evMalGestiti++;
				}
				a.setPosizione(luogoCrimine);
				
				//calcolo quanto ci mette l'agente a gestire l'evento
				if(ev.getOffense_category_id().equals("all-other-crimes")) {
					long tempoGestione = rand.nextInt(2)+1;
					EventoSimulazione es = new EventoSimulazione(a, oraArrivoAgente.plusHours(tempoGestione));
					queue.add(es);
				}
				else {
					EventoSimulazione es = new EventoSimulazione(a, oraArrivoAgente.plusHours(2));
					queue.add(es);
				}
				}
			break; 
				
			case AGENTE_LIBERO:
				Agente agente = evento.getAgente();
				agente.setStato(Stato_Agente.LIBERO);
			break;				
			}
		}

	}

	public Agente getAgenteDisp(LatLng luogoCrimine) {
		double distanzaMin = Double.MAX_VALUE;
		Agente res = null;
		for (Agente a : agenti) {
			if (a.getStato() == Stato_Agente.LIBERO) {
				double distance = LatLngTool.distance(luogoCrimine, a.getPosizione(), LengthUnit.KILOMETER);
				if (distance <= distanzaMin) { // caso pessimo: Double.MAX_VALUE è verificato
					distanzaMin = distance;
					res = a;
				}
			}
		}
		return res;
	}

	public long tempoPercorrenza(LatLng luogoCrimine, Agente agente) {
		double distance = LatLngTool.distance(luogoCrimine, agente.getPosizione(), LengthUnit.KILOMETER);
		return (long) (distance / 1); // 1=(60km/h)/(60min/h)

	}
	
	public int getEvMalgestiti() {
		return evMalGestiti;
	}

}

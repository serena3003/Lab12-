package it.polito.tdp.model;

import java.time.LocalTime;

public class EventoSimulazione implements Comparable<EventoSimulazione> {
	
	public enum Tipo_Evento{
		CRIMINE,
		AGENTE_LIBERO
	}
	
	private Tipo_Evento tipo;
	private Event evento;
	private Agente agente;
	private LocalTime tempo;
	
	public EventoSimulazione (Event evento) {
		this.evento = evento;
		tipo=Tipo_Evento.CRIMINE;
		this.tempo=evento.getReported_date().toLocalTime();
	}
	
	public EventoSimulazione(Agente agente, LocalTime ora) {
		this.agente=agente;
		tipo=Tipo_Evento.AGENTE_LIBERO;
		this.tempo = ora;
	}

	public Tipo_Evento getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_Evento tipo) {
		this.tipo = tipo;
	}

	public Event getEvento() {
		return evento;
	}

	public void setEvento(Event evento) {
		this.evento = evento;
	}

	public Agente getAgente() {
		return agente;
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
	}

	public LocalTime getTempo() {
		return tempo;
	}

	public void setTempo(LocalTime tempo) {
		this.tempo = tempo;
	}

	@Override
	public int compareTo(EventoSimulazione ev) {
		if(this.getTempo().isAfter(ev.getTempo()))
			return -1;
		else if(this.getTempo().isBefore(ev.getTempo()))
			return 1;
		else
			return 0;
	}	

}

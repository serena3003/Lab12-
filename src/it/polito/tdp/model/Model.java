package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<District, DefaultWeightedEdge> grafo;
	private List<District> district;
	
	
	public Model() {
		this.dao = new EventsDao();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		district = dao.getDistrict();
	}
	
	public List<Integer> selectYears(){
		List<Integer> res = dao.selectYears();
		Collections.sort(res);
		return res;
	}
	
	public List<Integer> selectMonths(){
		List<Integer> res = dao.selectMonths();
		Collections.sort(res);
		return res;
	}
	
	public List<Integer> selectDays(){
		List<Integer> res = dao.selectDays();
		Collections.sort(res);
		return res;
	}

	public void creaGrafo(int year) {
		
		//creo il grafo
		Graphs.addAllVertices(this.grafo, district);
		for(District d1 : district) {
			for(District d2 : district) {
				if(!d1.equals(d2)) {
					this.grafo.addEdge(d1, d2);
				}
			}
		}
		
		//calcolo il centro di ogni distretto
		for(District d : district) {
			d.setAvgLat(dao.getAvgLat(year, d.getId()));
			d.setAvgLon(dao.getAvgLon(year, d.getId()));
			d.newCenter();
			//System.out.println("\n distretto " + d.getId() + " - " + d.getCenter());
		}
		
		//aggiorno i pesi degli archi
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			District d1 = this.grafo.getEdgeSource(e);
			District d2 = this.grafo.getEdgeTarget(e);
			double distance = LatLngTool.distance(d1.getCenter(), d2.getCenter(), LengthUnit.KILOMETER);
			grafo.setEdgeWeight(e,distance);
		}	
		
	}
	
	public List<Neighbor> getNeighbor(District dist){
		
		List<Neighbor> result = new ArrayList<Neighbor>();
		for(District d : Graphs.neighborListOf(this.grafo, dist)) {
			DefaultWeightedEdge e = grafo.getEdge(d, dist);
			Neighbor n = new Neighbor(d, grafo.getEdgeWeight(e));
			result.add(n);
		}
		Collections.sort(result);
		return result;
	}

	public Graph<District, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public List<District> getDistrict() {
		return district;
	}
	
	
}

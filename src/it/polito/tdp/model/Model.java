package it.polito.tdp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Map<Integer, District> district;
	private Simulatore simulatore;
	
	
	public Model() {
		this.dao = new EventsDao();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		district = new HashMap<>();
		/*List<District> districtList = dao.getDistrict();
		for(District d : districtList) {
			district.put(d.getId(),d);
		}*/
		simulatore = new Simulatore();
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
		
		List<District> districtList = dao.getDistrict();
		for(District d : districtList) {
			district.put(d.getId(),d);
		}
		
		//creo il grafo
		Graphs.addAllVertices(this.grafo, district.values());
		for(District d1 : district.values()) {
			for(District d2 : district.values()) {
				if(!d1.equals(d2)) {
					this.grafo.addEdge(d1, d2);
				}
			}
		}
		
		//calcolo il centro di ogni distretto
		for(District d : district.values()) {
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

	public List<District> getDistrictList() {
		return new ArrayList<District>(district.values());
	}
	
	public Map<Integer, District> getDistrictMap(){
		return this.district;
	}

	public int doSimulazione(int year,int month,int day, int N) {
		
		simulatore.init(this.district, year, month, day, N);
		simulatore.run();
		return simulatore.getEvMalgestiti();
	}
	
	
}

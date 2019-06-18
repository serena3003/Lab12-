package it.polito.tdp.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model(); 
		
		model.creaGrafo(2015);
		System.out.println("Grafo creato. Vertici: " + model.getGrafo().vertexSet().size() + " Archi: " + model.getGrafo().edgeSet().size());
		
		int ev = model.doSimulazione(2015, 2, 2, 6);
		System.out.println(ev);

	}

}

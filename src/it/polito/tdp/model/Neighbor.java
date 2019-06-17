package it.polito.tdp.model;


public class Neighbor implements Comparable<Neighbor>{
	
	private District d;
	private Double distance;
	
	public Neighbor(District d, double distance) {
		super();
		this.d = d;
		this.distance = distance;
	}
	
	public District getD() {
		return d;
	}
	public void setD(District d) {
		this.d = d;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(Neighbor o) {
		return this.distance.compareTo(o.getDistance());
	}

	@Override
	public String toString() {
		return  d.getId() + ", distance = " + distance ;
	}
	
	

}

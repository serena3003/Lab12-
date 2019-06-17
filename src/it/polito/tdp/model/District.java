package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class District {

	private int id;
	private double avgLat;
	private double avgLon;
	private LatLng center;

	public District(int id) {
		super();
		this.id = id;
		this.avgLat=0.0;
		this.avgLon= 0.0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAvgLat() {
		return avgLat;
	}

	public void setAvgLat(double avgLat) {
		this.avgLat = avgLat;
	}

	public double getAvgLon() {
		return avgLon;
	}

	public void setAvgLon(double avgLon) {
		this.avgLon = avgLon;
	}

	public LatLng getCenter() {
		return center;
	}

	public void setCenter(LatLng center) {
		this.center = center;
	}
	
	public void newCenter() {
		if(this.avgLat==0.0 || this.avgLon==0.0) {
			System.out.println("Errore nella new del centro distretto");
		}else
			this.center = new LatLng(this.avgLat, this.avgLon);
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
		District other = (District) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}

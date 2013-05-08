package com.mlab.tesis.extractor;

public class BasicDataBundle implements DataBundle {

	protected String path, fileName;
	protected double lon, lat, alt;
	
	public double getLon() {
		return lon;
	}

	public double getLat() {
		return lat;
	}

	public double getAlt() {
		return alt;
	}

	public void setLon(double lon) {
		this.lon= lon;
	}

	public void setLat(double lat) {
		this.lat=lat;
	}

	public void setAlt(double alt) {
		this.alt=alt;
	}

	public String getPath() {
		return this.path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getLon());
		builder.append(",");
		builder.append(getLat());
		builder.append(",");
		builder.append(getFileName());
		builder.append(",");
		builder.append(getPath());
		return builder.toString();
	}


}

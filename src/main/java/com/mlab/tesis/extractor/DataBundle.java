package com.mlab.tesis.extractor;

public interface DataBundle {

	public String getPath();
	public String getFileName();
	public double getLon();
	public double getLat();
	public double getAlt();

	public void setPath(String path);
	public void setFileName(String fileName);
	public void setLon(double lon);
	public void setLat(double lat);
	public void setAlt(double alt);

}

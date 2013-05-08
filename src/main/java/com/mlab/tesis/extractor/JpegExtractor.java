package com.mlab.tesis.extractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * Solo acepta jpg
 * @author shiguera
 *
 */
public class JpegExtractor implements Extractor {
	
	private final String DEFAULT_OUTPUTFILENAME = "geodata.csv";
	

	public DataBundle parseFile(File file) {
		// Validate file extension
		if( (new Filter().accept(file.getParentFile(), file.getName())) == false) {
			System.out.println("BasicExtractor: Error, file not accepted by Filter");
			return null;
		}
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(file);			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		BasicDataBundle bundle = extractMetadata(metadata);
		if(bundle != null) {
			bundle.setPath(file.getParent());
			bundle.setFileName(file.getName());
		}
		return bundle;
	}

	public DataBundle[] parseDirectory(File dirfile) {
		File[] files = dirfile.listFiles(new Filter());
		ArrayList<DataBundle> bundles = new ArrayList<DataBundle>();
		
		for(File file: files) {
			DataBundle bundle = parseFile(file);
			if(bundle!=null) {
				bundles.add(bundle);
			}
		}
		DataBundle[] bundarray = null;
		if(bundles.size()>0) {
			bundarray = new BasicDataBundle[bundles.size()];
			bundles.toArray(bundarray);
			writeStringToFile(new File(dirfile,DEFAULT_OUTPUTFILENAME), bundlesToString(bundarray));
		}
		return bundarray;
	}
	
	private BasicDataBundle extractMetadata(Metadata metadata) {
		BasicDataBundle bundle = null;
		for(Directory dir: metadata.getDirectories()) {
			double lat = -1.0,lon=-1.0,alt=-1.0;
			for(Tag tag: dir.getTags()) {
				// String tagname = tag.getTagName().substring(4);
				if(tag.getTagName().equalsIgnoreCase("GPS Latitude")) {
					lat = parseLatlon(tag.getDescription());
					//System.out.print(lat+" ");							
				}
				if(tag.getTagName().equalsIgnoreCase("GPS Longitude")) {
					lon = parseLatlon(tag.getDescription());
					//System.out.println(lon+" ");							
				}
				if(tag.getTagName().equalsIgnoreCase("GPS Altitude")) {
//					alt = parseLatlon(tag.getDescription());
//					System.out.println(tag.getTagName()+" "+tag.getDescription());							
				}
			}
			if(lon != -1.0 && lat != -1.0) {
				bundle = new BasicDataBundle();
				bundle.setLon(lon);
				bundle.setLat(lat);
			} else {
				lon = -1.0;
				lat = -1.0;
			}
			
			if(dir.hasErrors()) {
				for(String err: dir.getErrors()) {
					System.err.println(err);
				}
			}
		}
		return bundle;
	}
	
	private String bundlesToString(DataBundle[] bundles) {
		StringBuilder builder = new StringBuilder();
		if(bundles != null) {
        	for(DataBundle bundle: bundles) {
            	builder.append(bundle.toString()); 
            	builder.append("\n");
        	}
        }
		return builder.toString();
	}
	
	public int writeStringToFile(File file, String cad) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(cad + "\n");
			writer.close();
		} catch (FileNotFoundException fe) {
			System.out.println("File " + file.getName() + " not found.\n"
					+ fe.getMessage());
			return -1;
		} catch (NumberFormatException ne) {
			System.out.println("Number format error. " + ne.getMessage());
			return -2;
		} catch (Exception e) {
			System.out.println("Unidentified error. " + e.getMessage());
			return -3;
		}
    
		return 1;
	}
	
	double parseLatlon(String cadlat) {
		String[] cads = cadlat.split(" ");
		double d = parseDouble(cads[0]);
		//System.out.println(d);
		if(d==Double.NaN) {
			return d;
		}
		double sign = (d<0?-1.0:1.0);
		d = Math.abs(d);
		double m = parseDouble(cads[1]);
		if(m == Double.NaN) {
			return m;
		}
		double s = parseDouble(cads[2]);
		if(s == Double.NaN) {
			return s;
		}
		return sign*(d+m/60.0+s/3600.0);
	}
	double parseDouble(String caddeg) {
		char c = (char)248; // Degree character
		String cad = caddeg.trim().substring(0, caddeg.length()-1);
		//System.out.println("-"+cad+"-");
		double d = Double.NaN;
		try {
			d = Double.parseDouble(cad);
			//System.out.println(d);
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
		}
		return d;
	}
	
	class Filter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			if(name.endsWith(".jpg") || name.endsWith(".JPG")) {
				return true;
			}
			return false;
		}
		
	}

	

	
}

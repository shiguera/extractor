package com.mlab.tesis.extractor;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
	//private static final String inputPath ="/home/shiguera/Dropbox/GEOVIDEO PRUEBA/VISITA INICIAL CP033 20130422/dia 22/";
	//private static final String inputPath ="/home/shiguera/Dropbox/GEOVIDEO PRUEBA/VISITA INICIAL CP033 20130422/día 23/";
	private static final String inputPath ="/home/shiguera/Dropbox/GEOVIDEO PRUEBA/VISITA INICIAL CP033 20130422/día 24/";
	//private static final String inputPath = "/home/shiguera//Dropbox/Cargas de cámara/";
	//private static final String inputFileName = "IMG-20130422-00074.jpg"; // File without data
	private static final String inputFileName = "IMG-20130422-00085.jpg"; // File with data
	private static final String outputPath = "";
	private static final String outputFileName = "";
	
    public static void main( String[] args ) {
        System.out.println( "Extractor : Geodata extractor for jpg files" );
        File file = new File (inputPath+"/"+inputFileName);
        //parseFile(file);
        File dir = null;
        if(args.length == 0) {
        	dir = new File(inputPath);
        } else {
        	dir = new File(args[0]);
        }
       	parseDirectory(dir);        
    }
    
    private static void parseFile(File file) {
    	JpegExtractor extractor = new JpegExtractor();
        DataBundle bundle = extractor.parseFile(file);
        if(bundle != null) {
        	System.out.println(bundle.toString());
        } else {
        	System.out.println("File "+ inputFileName +" doesn't have geodata");
        }
    }
    
    private static void parseDirectory(File dirfile) {
    	JpegExtractor extractor = new JpegExtractor();
        DataBundle[] bundles = extractor.parseDirectory(dirfile);
        if(bundles != null) {
        	for(DataBundle bundle: bundles) {
            	System.out.println(bundle.toString());        		
        	}
        } else {
        	System.out.println("File "+ inputFileName +" doesn't have geodata");
        }
    }
    
}

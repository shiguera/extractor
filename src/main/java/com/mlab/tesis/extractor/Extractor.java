package com.mlab.tesis.extractor;

import java.io.File;

public interface Extractor {
	
	public DataBundle parseFile(File file);
	public DataBundle[] parseDirectory(File dirfile);
	
}

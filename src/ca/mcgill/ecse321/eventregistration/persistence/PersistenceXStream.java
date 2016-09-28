package ca.mcgill.ecse321.eventregistration.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;

public class PersistenceXStream {
	private static XStream xstream = new XStream();
	private static String filename = "data.xml";
	private static File path = null;
	
	public static boolean saveToXMLwithXStream(Object obj) {
		xstream.setMode(XStream.ID_REFERENCES);
		String xml = xstream.toXML(obj); // save our xml file
		try {
			File file = new File(path, filename);				
			FileWriter writer = new FileWriter(file);
			writer.write(xml);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Object loadFromXMLwithXStream() {
		xstream.setMode(XStream.ID_REFERENCES);
		try {
			File file = new File(path, filename);				
			FileReader reader = new FileReader(file); // load our xml file
			return xstream.fromXML(reader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setAlias(String xmlTagName, Class<?> className) {
		xstream.alias(xmlTagName, className);
	}

	public static void setFilename(String fn) {
		filename = fn;
	}
	
	public static void setPath(File file) {
		path = file;
	}
}
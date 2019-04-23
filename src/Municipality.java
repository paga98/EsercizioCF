

import apxmlmanager.APXMLManagerReader;
import apxmlmanager.APXMLManagerWriter;
import apxmlmanager.IAPXMLReadable;
import apxmlmanager.IAPXMLWriteable;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Municipality implements IAPXMLWriteable<Municipality>,IAPXMLReadable<Municipality>
{
	//private properties
	private String name;
	private String code;
	
	//file w/r path
	private static String filePath = "C:\\Users\\Massimiliano\\Desktop\\Progetto Arnaldo\\CodiceFiscale\\EsercizioCF\\src\\comuni.xml";
	
	//final properties for xml
	public static final String rootName = "comuni";
	public static final String className = "comune";
	public static final String name_field = "nome";
	public static final String code_field = "codice";
	
	//getters and setters
	public String get_name() {
		return name;
	}
	public void set_name(String name) {
		this.name = name;
	}
	
	public String get_code() {
		return code;
	}
	public void set_code(String code) {
		this.code = code;
	}
	
	//constructors
	public Municipality() {
		//this.filePath = System.getProperty("user.dir") + this.filePath;
	}
	public Municipality(String code,String name) {
		set_code(code);
		set_name(name);
	}
	
	//get array list of municipality
	public static ArrayList<Municipality> getMuniciplityListFromFile(){
		IAPXMLReadable<Municipality> ir = new Municipality();
		return new APXMLManagerReader<Municipality>(ir,filePath,rootName).read();
	}
	
	public static boolean setMunicipalityListToFile(ArrayList<Municipality> list) {
		IAPXMLWriteable<Municipality> ir = new Municipality();
		return new APXMLManagerWriter<Municipality>(ir,filePath,rootName).write(list);
	}
	
	public String findCodeByNameFromFileList(String name) {
		return findCodeByName(getMuniciplityListFromFile(),name);
	}
	
	private String findCodeByName(ArrayList<Municipality> list,String name) {
		for(Municipality m : list) {
			if(m.get_name().equals(name)) return m.get_code();
		}
		return null;
	}

	//override methods
	@Override
	public ArrayList<Municipality> parse(NodeList nodes) {
		ArrayList<Municipality> list = new ArrayList<Municipality>();
		//ogni volta che il nodo ha un nome uguale a className --> Municipality
		for(int i=0;i<nodes.getLength();i++) {
			Node child = nodes.item(i);
			if (child.getNodeType() != Node.ELEMENT_NODE) {
	            continue;
	        }
			else if(child.getNodeName().equals(className)) {
				Municipality m = new Municipality();
				//prende i child del nodo corrente e cerca
				//il nodo nome e codice
				NodeList cnodes = child.getChildNodes();
				for(int j=0;j< cnodes.getLength();j++) {
					Node n = cnodes.item(j);
					if (n.getNodeType() != Node.ELEMENT_NODE) {
			            continue;
			        }
					else if(n.getNodeName().equals(name_field)) {
						m.set_name(n.getTextContent());
					}else if(n.getNodeName().equals(code_field)) {
						m.set_code(n.getTextContent());
					}
				}
				list.add(m);
			}
		}
		return list;
	}
	
	@Override
	public Document parse(Document doc, Element root, ArrayList<Municipality> list) {
		Element municipality;
		for(Municipality m : list) {
			municipality = doc.createElement(className);
			
			Element name = doc.createElement(name_field);
			name.appendChild(doc.createTextNode(m.get_name()));
			
			municipality.appendChild(name);
			Element code = doc.createElement(code_field);
			code.appendChild(doc.createTextNode(m.get_code()));
			municipality.appendChild(code);
			
			root.appendChild(municipality);
		}
        doc.appendChild(root);
        return doc;
	}
}

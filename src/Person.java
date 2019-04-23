
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import apxmlmanager.APXMLManagerReader;
import apxmlmanager.APXMLManagerWriter;
import apxmlmanager.IAPXMLReadable;
import apxmlmanager.IAPXMLWriteable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Person implements IAPXMLWriteable<Person>,IAPXMLReadable<Person>
{
	
	//private attributes
	private int id;
	private String name;
	private String surname;
	private char gender;
	private String municipality;
	private Date dateOfBirth;
	private String fiscalCode;

	//attributes for xml
	private static String filePath = "C:\\Users\\Massimiliano\\Desktop\\Progetto Arnaldo\\CodiceFiscale\\EsercizioCF\\src\\inputPersone.xml";
	private static String outputPath = "C:\\Users\\Massimiliano\\Desktop\\Progetto Arnaldo\\CodiceFiscale\\EsercizioCF\\src\\output.xml";
	public static final String rootName = "persone";
	public static final String className = "persona";
	public static final String name_field = "nome";
	public static final String surname_field = "cognome";
	public static final String gender_field = "sesso";
	public static final String municipality_field = "comune_nascita";
	public static final String dateOfBirth_field = "data_nascita";
	public static final String fiscalcode_field = "codice_fiscale";
	public static final String id_field = "id";
	

	
	public static ArrayList<Person> getInputPeopleListFromFile() {
		IAPXMLReadable<Person> ir = new Person();
		return new APXMLManagerReader<Person>(ir,filePath,rootName).read();
	}
	
	public static boolean setInputPeopleListToFile(ArrayList<Person> list) {
		IAPXMLWriteable<Person> ir = new Person();
		return new APXMLManagerWriter<Person>(ir,outputPath,rootName).write(list);
	}
	
	
	//consructors
	public Person() {}
	public Person(int id, String name, String surname, char gender, String municipality, Date dateOfBirth) {
		this.setId(id);
		this.setName(name);
		this.setSurname(surname);
		this.setGender(gender);
		this.setMunicipality(municipality);
		this.setDateOfBirth(dateOfBirth);
	}
	
	//getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		try {
			this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth); 
		}catch(Exception e){
			this.dateOfBirth = null;
		}
	}

	public String getFiscalcode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
	
	@Override
	public ArrayList<Person> parse(NodeList nodes) {
		ArrayList<Person> list = new ArrayList<Person>();
		for(int i=0;i<nodes.getLength();i++) {
			Node child = nodes.item(i);
			if (child.getNodeType() != Node.ELEMENT_NODE) {
	            continue;
	        }
			else if(child.getNodeName().equals(className)) {
				Person m = new Person();
				if(child.hasAttributes()) {
					m.setId(child.getAttributes().getNamedItem(id_field).getNodeValue());	
				}
				NodeList cnodes = child.getChildNodes();
				for(int j=0;j< cnodes.getLength();j++) {
					Node n = cnodes.item(j);
					if (n.getNodeType() != Node.ELEMENT_NODE) {
			            continue;
			        }
					else if(n.getNodeName().equals(name_field)) {
						m.setName(n.getTextContent());
					}else if(n.getNodeName().equals(surname_field)) {
						m.setSurname(n.getTextContent());
					}else if(n.getNodeName().equals(gender_field)) {
						m.setGender(n.getTextContent().charAt(0));
					}else if(n.getNodeName().equals(municipality_field)) {
						m.setMunicipality(n.getTextContent());
					}else if(n.getNodeName().equals(dateOfBirth_field)) {
						m.setDateOfBirth(n.getTextContent());
					}
				}
				list.add(m);
			}
		}
		return list;
	}

	@Override
	public Document parse(Document doc, Element root, ArrayList<Person> list) {
		Element inputPeople;
		for(Person m : list) {
			inputPeople = doc.createElement(className);
			inputPeople.setAttribute(id_field, Integer.toString(m.getId()));
			inputPeople.appendChild(createElementForParsig(doc,name_field,m.getName()));
			inputPeople.appendChild(createElementForParsig(doc,surname_field,m.getSurname()));
			inputPeople.appendChild(createElementForParsig(doc,gender_field,Character.toString(m.getGender())));
			inputPeople.appendChild(createElementForParsig(doc,municipality_field,m.getMunicipality()));
			inputPeople.appendChild(createElementForParsig(doc,dateOfBirth_field,new SimpleDateFormat("yyyy-MM-dd").format(m.getDateOfBirth())));
			root.appendChild(inputPeople);
		}
        doc.appendChild(root);
        return doc;
	}
	
	private Element createElementForParsig(Document doc,String name,String value) {
		Element e = doc.createElement(name);
		e.appendChild(doc.createTextNode(value));
		return e;
	}
}

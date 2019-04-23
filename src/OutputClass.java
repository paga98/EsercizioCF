import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import apxmlmanager.APXMLManagerWriter;
import apxmlmanager.IAPXMLWriteable;

//dato che l'outputclass non puo fare l'extends, faccio un modo alternativo
public class OutputClass implements IAPXMLWriteable<OutputClass> {
	private ArrayList<OutputPerson> listPeople;
	private ArrayList<String> invalidCodes, unpariedCodes;

	public ArrayList<OutputPerson> getListPeople() {
		return this.listPeople;
	}

	public void setListPeople(ArrayList<OutputPerson> list) {
		this.listPeople = list;
	}

	public ArrayList<String> getInvalidCodes() {
		return this.invalidCodes;
	}

	public void setInvalidCodes(ArrayList<String> list) {
		this.invalidCodes = list;
	}

	public ArrayList<String> getUnpariedCodes() {
		return this.unpariedCodes;
	}

	public void setUnpariedCodes(ArrayList<String> list) {
		this.unpariedCodes = list;
	}

	// attributes for xml
	private static String filePath = "C:\\Users\\Massimiliano\\Desktop\\Progetto Arnaldo\\CodiceFiscale\\EsercizioCF\\src\\codiciPersone.xml";
	public static final String rootName = "output";
	public static final String number_field = "numero";
	public static final String invalid_field = "invalidi";
	public static final String spaiati_field = "spaiati";

	public OutputClass() {

	}

	public OutputClass(ArrayList<OutputPerson> listPeople, ArrayList<String> invalidCodes,
			ArrayList<String> unpariedCodes) {
		setListPeople(listPeople);
		setInvalidCodes(invalidCodes);
		setUnpariedCodes(unpariedCodes);
	}

	public static boolean saveInputPeopleListToFile(ArrayList<OutputClass> list) {
		IAPXMLWriteable<OutputClass> ir = new OutputClass();
		return new APXMLManagerWriter<OutputClass>(ir, filePath, rootName).write(list);
	}

	@Override
	public Document parse(Document doc, Element root, ArrayList<OutputClass> list) {
		OutputClass currentContext = list.get(0);
		// first populate root with list of person class
		ArrayList<OutputPerson> people = currentContext.getListPeople();
		Element precElemOutputPeople = doc.createElement(Person.rootName);
		precElemOutputPeople.setAttribute(number_field, Integer.toString(currentContext.getListPeople().size()));
		Element outputPeople;
		for (OutputPerson m : people) {
			outputPeople = doc.createElement(Person.className);
			outputPeople.setAttribute(Person.id_field, Integer.toString(m.getPersona().getId()));
			outputPeople.appendChild(createElementForParsig(doc, Person.name_field, m.getPersona().getName()));
			outputPeople.appendChild(createElementForParsig(doc, Person.surname_field, m.getPersona().getSurname()));
			outputPeople.appendChild(
					createElementForParsig(doc, Person.gender_field, Character.toString(m.getPersona().getGender())));
			outputPeople.appendChild(
					createElementForParsig(doc, Person.municipality_field, m.getPersona().getMunicipality()));
			outputPeople.appendChild(
					createElementForParsig(doc, Person.dateOfBirth_field, m.getPersona().getDateOfBirth()));
			outputPeople.appendChild(createElementForParsig(doc, OutputPerson.fc_field, m.getFiscalCode()));
			precElemOutputPeople.appendChild(outputPeople);
		}
		root.appendChild(precElemOutputPeople);

		// second codes

		Element codes = doc.createElement(FiscalCode.rootName);

		// 2-1 invalid
		ArrayList<String> invalidCodes = currentContext.getInvalidCodes();
		Element invalid = doc.createElement(invalid_field);
		invalid.setAttribute(number_field, Integer.toString(invalidCodes.size()));

		Element invalidelm;
		for (String si : invalidCodes) {
			invalidelm = doc.createElement(FiscalCode.code_field);
			invalidelm.appendChild(doc.createTextNode(si));
			invalid.appendChild(invalidelm);
		}
		codes.appendChild(invalid);

		// 2-2 spaiati
		ArrayList<String> spaiatiCodes = currentContext.getUnpariedCodes();
		Element spaiati = doc.createElement(spaiati_field);
		spaiati.setAttribute(number_field, Integer.toString(spaiatiCodes.size()));

		Element spaiatilm;
		for (String ss : spaiatiCodes) {
			spaiatilm = doc.createElement(FiscalCode.code_field);
			spaiatilm.appendChild(doc.createTextNode(ss));
			spaiati.appendChild(spaiatilm);
		}
		codes.appendChild(spaiati);
		root.appendChild(codes);
		doc.appendChild(root);
		return doc;
	}

	private Element createElementForParsig(Document doc, String name, String value) {
		Element e = doc.createElement(name);
		e.appendChild(doc.createTextNode(value));
		return e;
	}
}

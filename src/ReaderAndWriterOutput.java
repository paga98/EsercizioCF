import java.util.ArrayList;

public class ReaderAndWriterOutput {
	private ArrayList<Person> listofPeople;
	private ArrayList<String> codes;
	private ArrayList<String> invalideCodes, unpariedCodes;
	private static String ABSENT = "ASSENTE";
	private FiscalCodeGenerator generator = new FiscalCodeGenerator();

	public ReaderAndWriterOutput() {
		listofPeople = Person.getInputPeopleListFromFile();
		codes = FiscalCode.getList();
	}

	public void genFiscalCodes() {
		for (Person p : listofPeople)
			generator.generateFC(p);
	}

	public void checkFiscalCodes() {
	//Rimuovo dalla lista dei codici quelli invalidi
	invalideCodes = new ArrayList<String>();
	for (String code: codes) {
		
		if(generator.isCF(code) == false) 
			// se il codice fiscale è invalido lo aggiungo agli invalidi
			invalideCodes.add(code);
	}
	
	//rimuovo dall'array di codici quelli non validi
	codes.removeAll(invalideCodes);
	System.out.println("I codici invalidi sono "+ codes.size());
	
	unpariedCodes = new ArrayList<String>();
	
	for (Person p : listofPeople) {
		String stringToCheck = p.getFiscalcode();
		if(codes.indexOf(stringToCheck) >= 0)
		//se il codice fiscale di una persona non è presente nella lista dei codici 
		//aggiungo il suddetto ad unpariedCodes e sovrascrivo il codice fiscale della persona con la dicitura ASSENTE
		{
			unpariedCodes.add(stringToCheck);
			p.setFiscalCode(ABSENT);
		}
	}
	System.out.println("I codici spaiati sono " + unpariedCodes.size());
	}

	public void writeOutput() {
		System.out.println("Scrittura file xml in corso...");
		ArrayList <OutputPerson> temp_listToPrint; 
		OutputClass printerXML;
		temp_listToPrint = new ArrayList<OutputPerson>();
		for (Person p: listofPeople) {
			//per ogni persona creo "la persona di output" la aggiungo all'array che sarà stampato
			OutputPerson pToPrint = new OutputPerson(p, p.getFiscalcode());
			temp_listToPrint.add(pToPrint);
		}
		
		printerXML = new OutputClass(temp_listToPrint, invalideCodes, unpariedCodes);
		ArrayList<OutputClass> listToPrint = new ArrayList<OutputClass>();
		listToPrint.add(printerXML);
		OutputClass.saveInputPeopleListToFile(listToPrint);
	}
}

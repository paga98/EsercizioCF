import java.util.ArrayList;

public class ReaderAndWriter {
	private ArrayList<Person> listofPeople;
	private ArrayList<String> codes;
	private ArrayList<String> invalideCodes, unpariedCodes;
	private static String ABSENT = "ASSENTE";
	private FiscalCodeGenerator generator = new FiscalCodeGenerator();

	public ReaderAndWriter() {
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
		if(generator.isCF(code) == false) {
			// se il codice fiscale è invalido lo aggiungo agli invalidi
			invalideCodes.add(code);
		}
	}
	codes.removeAll(invalideCodes);//rimuovo dall'array di codici quelli non validi
	unpariedCodes = new ArrayList<String>();
		
	for (Person p : listofPeople) {
		String stringToCheck = p.getFiscalcode();
		boolean check = false;
		//se il codice fiscale di una persona non è presente nella lista dei codici 
		//aggiungo il suddetto ad unpariedCodes e sovrascrivo il codice fiscale della persona con la dicitura ASSENTE
		for(String c: codes)
			if(c.equals(stringToCheck)) check = true; //l'elemento è presente	
			
		if(check == false) {
			unpariedCodes.add(stringToCheck);
			p.setFiscalCode(ABSENT);
			}
		}
	}

	public void writeOutput() {
		Person.setInputPeopleListToFile(listofPeople);
	}
}

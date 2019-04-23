public class OutputPerson
{
	private Person persona;
	private String fiscalCode;
	
	//attributes for xml
	public static final String rootName = "output";
	public static final String fc_field = "codice_fiscale";
	
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
	public String getFiscalCode() {
		return this.fiscalCode;
	}
	
	public Person getPersona() {
		return persona;
	}
	public void setPersona(Person persona) {
		this.persona = persona;
	}
	public OutputPerson() {
		
	}
	public OutputPerson(Person p,String fcCode) {
		setPersona(p);
		setFiscalCode(fcCode);
	}
}

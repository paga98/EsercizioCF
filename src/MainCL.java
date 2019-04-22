import java.util.Date;

public class MainCL {

	public static void main(String[] args) {
		Person massi = new Person(0, "MASSIMILIANO", "TUMMOLO", 'M', "B157", new Date("1999-12-03"));
		FiscalCodeGenerator f = new FiscalCodeGenerator();
		f.generateFC(massi);
		System.out.println(massi.getFiscalcode());
	}

}

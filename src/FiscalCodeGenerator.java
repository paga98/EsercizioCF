public class FiscalCodeGenerator {

	public static final char VOWELS[] = { 'A', 'E', 'I', 'O', 'U' };
	public static final char MONTHS[] = { 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' };
	public static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int ODDVALUES[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4,
			18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	public static final int EVENVALUES[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };

	public static final int LUNGHEZZA_CF = 16;

	//for date
	public static final int[] DAYSINMONTHS = {0, 31, 28, 31, 30, 31, 30, 
			31, 31, 30, 31, 30, 31};
	
	// GENERATION OF THE FISCAL CODE
	public void generateFC(Person p) {
		StringBuffer fc = new StringBuffer();
		genSurname(p, fc);
		genName(p, fc);
		boolean checkForCorrect = genDate(p, fc);
		genMunicipality(p, fc);
		fc.append(genVerificationCode(fc.toString()));

		if (checkForCorrect)
			p.setFiscalCode(fc.toString());
		else // if the date is not correct the fiscal code will result
				// "DATEOFBIRTHISINCORRECT"
			p.setFiscalCode("DATEOFBIRTHISINCORRECT");
	}

	private boolean isVowel(Character c) {
		for (char e : VOWELS) {
			if (c.equals(e))
				return true;
		}
		return false;
	}

	private void genSurname(Person p, StringBuffer fc) {
		char s[] = p.getSurname().toCharArray();
		StringBuffer v = new StringBuffer();
		StringBuffer c = new StringBuffer();

		for (char e : s) {
			if (isVowel(e))
				v.append(e);
			else
				c.append(e);

			if (c.length() == 3)
				break;
		}

		if (c.length() < 3) {
			int n = 3 - c.length();
			c.append(v.substring(0, n-1)); // add vowels after the consonants to reach the required length
		}

		fc.append(c);
	}

	private void genName(Person p, StringBuffer fc) {
		char s[] = p.getName().toCharArray();
		StringBuffer v = new StringBuffer();
		StringBuffer c = new StringBuffer();

		for (char e : s) {
			if (isVowel(e))
				v.append(e);
			else
				c.append(e);

			if (c.length() == 4) {
				c.deleteCharAt(1);
				break;
			}
		}

		if (c.length() < 3) {
			int n = 3 - c.length();
			c.append(v.substring(0, n)); // add vowels after the consonants to reach the required length
		}

		fc.append(c);
	}

	private boolean genDate(Person p, StringBuffer fc) {
		DateForFC dateofbirth = p.getDateOfBirth_fromInt();
		if (dateofbirth.isCorrect()) {
			if((dateofbirth.getYear() % 100)<10) fc.append(0);
			fc.append(dateofbirth.getYear() % 100); // get the last 2 digits of the birth year
			fc.append(MONTHS[dateofbirth.getMonth()-1]);//0 is January
			if (p.getGender() == 'F') {
				fc.append(dateofbirth.getDay() + 40);
			} else {
				if (dateofbirth.getDay() < 10)
					fc.append(0);
				fc.append(dateofbirth.getDay());
			}

		} else // if the date is not correct the fiscal code will result "DATEOFBIRTHINCORRECT"
		{
			return false;
		}
		return true;
	}

	private void genMunicipality(Person p, StringBuffer fc) {
		String municipalityCode = new Municipality().findCodeByNameFromFileList(p.getMunicipality());
		fc.append(municipalityCode);
	}

	private char genVerificationCode(String fc) {
		char[] s = fc.toCharArray();
		StringBuffer evens = new StringBuffer(); // digits in an even position
		StringBuffer odds = new StringBuffer(); // digits in an odd position
		int sumE = 0;
		int sumO = 0;

		for (int i = 0; i < fc.length(); i++) {
			if ((i + 1) % 2 == 0) // split the fc in 2 arrays
				evens.append(s[i]);
			else
				odds.append(s[i]);
		}

		char[] e = evens.toString().toCharArray();
		char[] o = odds.toString().toCharArray();

		for (char ch : o) {
			sumO += ODDVALUES[CHARS.indexOf(ch)]; // conversion table for odd numbers
		}

		for (char ch : e) {
			sumE += EVENVALUES[CHARS.indexOf(ch)]; // conversion table for even numbers
		}

		int leftovers = (sumE + sumO) % 26;

		return CHARS.charAt(leftovers + 10); // +10 is used to skip the numbers in the CHARS string
	}

	// CHECK IF IS CORRECT
	
	public boolean isCF(String fiscalCode) {
		//
		if (fiscalCode.length() != LUNGHEZZA_CF) {
			return false;
		}
		for (int i = 0; i < 6; i++) {
			char c = fiscalCode.charAt(i);
			if (!Character.isUpperCase(c)) {
				return false;
			}
		}
		for (int i = 6; i < 8; i++) {
			char c = fiscalCode.charAt(i);
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		// month check
		int numMonth = MONTHS.toString().indexOf(fiscalCode.charAt(8));
		if (numMonth < 0)
			// if the character at the position 8 doesn't match the characters used for
			// months the fiscal code is incorrect
			return false;
		numMonth++; // so January will result the month 01

		// check of the day
		for (int i = 9; i < 11; i++) {
			char c = fiscalCode.charAt(i);
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		// second check
		int dayNumber = Integer.parseInt(fiscalCode.substring(9, 11));
		if ((dayNumber >= 1 && dayNumber <= 31) || (dayNumber >= 41 && dayNumber <= 71)) { // male vs female differences
			// the value of the day is correct so the check can continue
		} else
			return false;

		if (dayNumber > DAYSINMONTHS[numMonth])
			// the number of days exceed the limit
			return false;

		if (!Character.isUpperCase(fiscalCode.charAt(11))) {
			return false;
		}
		for (int i = 12; i < 15; i++) {
			char c = fiscalCode.charAt(i);
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		// Check Case
		if (fiscalCode.charAt(15) != genVerificationCode(fiscalCode.substring(0, 15)))
			return false;

		// All the checks went good so the fiscal code is correct
		return true;
	}
}

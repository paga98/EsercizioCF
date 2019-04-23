
public class DateForFC {
	private int year;
	private int month;
	private int day;
	private static final String SEPARATOR = "-";
	//the first month January has 31 days, the month 0 doesn't exist in the calendar xD
	public static final int[] DAYSINMONTHS = {0, 31, 28, 31, 30, 31, 30, 
			31, 31, 30, 31, 30, 31};

	// date is a String in format for example 1999-12-03
	public DateForFC(String date) {
		String[] sdate = date.split(SEPARATOR);
		year = Integer.parseInt(sdate[0]);
		month = Integer.parseInt(sdate[1]);
		day = Integer.parseInt(sdate[2]);
	}
	
	public boolean isCorrect() {
		//The date is incorrect because the day exceed the limit of days for month
		if (day > DAYSINMONTHS[month])
				return false;	
			
		return true;
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}
}

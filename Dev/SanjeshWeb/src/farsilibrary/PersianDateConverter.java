package farsilibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PersianDateConverter {

	private static final double Solar = 365.25;
	private static final int GYearOff = 226894;
	private static final int[][] GDayTable = new int[][] {
			{ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
			{ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
	private static final int[][] JDayTable = new int[][] {
			{ 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29 },
			{ 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30 } };
	private static final String[] weekdays = new String[] { "شنبه", "یکشنبه", "دوشنبه", "سه شنبه",
			"چهارشنبه", "پنجشنبه", "جمعه" };
	//private static final String[] weekdaysabbr = new String[] { "ش", "ی", "د", "س", "چ", "پ", "ج" };

	// / <summary>
	// / Checks if a specified Persian year is a leap one.
	// / </summary>
	// / <param name="jyear"></param>
	// / <returns>returns 1 if the year is leap, otherwise returns 0.</returns>
	private static int JLeap(int jyear) {
		// Is jalali year a leap year?
		int tmp;

		tmp = jyear % 33;
		if ((tmp == 1) || (tmp == 5) || (tmp == 9) || (tmp == 13) || (tmp == 17) || (tmp == 22)
				|| (tmp == 26) || (tmp == 30)) {
			return 1;
		}

		return 0;
	}

	// / <summary>
	// / Checks if a year is a leap one.
	// / </summary>
	// / <param name="jyear">Year to check</param>
	// / <returns>true if the year is leap</returns>
	public static boolean IsJLeapYear(int jyear) {
		return JLeap(jyear) == 1;
	}

	// / <summary>
	// / Checks if a specified Gregorian year is a leap one.
	// / </summary>
	// / <param name="gyear"></param>
	// / <returns>returns 1 if the year is leap, otherwise returns 0.</returns>
	private static int GLeap(int gyear) {
		// Is gregorian year a leap year?
		int Mod4, Mod100, Mod400;

		Mod4 = gyear % 4;
		Mod100 = gyear % 100;
		Mod400 = gyear % 400;

		if (((Mod4 == 0) && (Mod100 != 0)) || (Mod400 == 0)) {
			return 1;
		}

		return 0;
	}

	private static int GregDays(int gYear, int gMonth, int gDay) {
		// Calculate total days of gregorian from calendar base
		int Div4 = (gYear - 1) / 4;
		int Div100 = (gYear - 1) / 100;
		int Div400 = (gYear - 1) / 400;
		int leap = GLeap(gYear);

		for (int i = 0; i < gMonth - 1; i++) {
			gDay = gDay + GDayTable[leap][i];
		}

		return ((gYear - 1) * 365 + gDay + Div4 - Div100 + Div400);
	}

	private static int JLeapYears(int jYear) {
		int i;
		int Div33 = jYear / 33;
		int cycle = jYear - (Div33 * 33);
		int leap = (Div33 * 8);

		if (cycle > 0) {
			for (i = 1; i <= 18; i = i + 4) {
				if (i > cycle)
					break;

				leap++;
			}
		}

		if (cycle > 21) {
			for (i = 22; i <= 31; i = i + 4) {
				if (i > cycle)
					break;

				leap++;
			}

		}
		return leap;
	}

	public static int JalaliDays(int jYear, int jMonth, int jDay) {
		// Calculate total days of jalali years from the base calendar
		int leap = JLeap(jYear);
		for (int i = 0; i < jMonth - 1; i++) {
			jDay = jDay + JDayTable[leap][i];
		}

		leap = JLeapYears(jYear - 1);
		int iTotalDays = ((jYear - 1) * 365 + leap + jDay);

		return iTotalDays;
	}

	// / <summary>Converts a Gregorian Date of type <c>System.DateTime</c> class
	// to Persian Date.</summary>
	// / <param name="date">DateTime to evaluate</param>
	// / <returns>string representation of Jalali Date</returns>
	public static PersianDate ToPersianDate(String date) throws ParseException {
		return ToPersianDate(new SimpleDateFormat().parse(date));
	}

	/*
	 * /// <summary> /// Converts a Gregorian Date of type <c>String</c> and a
	 * <c>TimeSpan</c> into a Persian Date. /// </summary> /// <param
	 * name="date"></param> /// <param name="time"></param> ///
	 * <returns></returns> public static PersianDate ToPersianDate(String date,
	 * Date time) { PersianDate pd = ToPersianDate(date); pd.Hour = time.Hours;
	 * pd.Minute = time.Minutes; pd.Second = time.Seconds;
	 * 
	 * return pd; }
	 */

	// / <summary>
	// / Converts a Gregorian Date of type <c>String</c> class to Persian Date.
	// / </summary>
	// / <param name="dt">Date to evaluate</param>
	// / <returns>string representation of Jalali Date.</returns>
	public static PersianDate ToPersianDate(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		int gyear = c.get(Calendar.YEAR);
		int gmonth = c.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based.
		int gday = c.get(Calendar.DAY_OF_MONTH);
		int i;

		// Calculate total days from the base of gregorian calendar
		int iTotalDays = GregDays(gyear, gmonth, gday);
		iTotalDays = iTotalDays - GYearOff;

		// Calculate total jalali years passed
		int jyear = (int) (iTotalDays / (Solar - 0.25 / 33));
		// Calculate passed leap years
		int leap = JLeapYears(jyear);

		// Calculate total days from the base of jalali calendar
		int jday = iTotalDays - (365 * jyear + leap);

		// Calculate the correct year of jalali calendar
		jyear++;

		if (jday == 0) {
			jyear--;
			jday = JLeap(jyear) == 1 ? 366 : 365;
		} else {
			if ((jday == 366) && (JLeap(jyear) != 1)) {
				jday = 1;
				jyear++;
			}
		}

		// Calculate correct month of jalali calendar
		leap = JLeap(jyear);
		for (i = 0; i <= 12; i++) {
			if (jday <= JDayTable[leap][i]) {
				break;
			}
			jday = jday - JDayTable[leap][i];
		}

		int iJMonth = i + 1;

		return new PersianDate(jyear, iJMonth, jday, c.get(Calendar.HOUR), c.get(Calendar.MINUTE),
				c.get(Calendar.SECOND));
	}

	// / <summary>
	// / Converts a Persian Date of type <c>String</c> to Gregorian Date of type
	// <c>DateTime</c> class.
	// / </summary>
	// / <param name="date">Date to evaluate</param>
	// / <returns>Gregorian DateTime representation of evaluated Jalali
	// Date.</returns>
	public static Date ToGregorianDateTime(String date) {
		PersianDate pd = new PersianDate(date);
		return ToGregorianDateTime(pd);
	}

	public static Date ToGregorianDateTime(PersianDate date) {
		int jyear = date.year;
		int jmonth = date.month;
		int jday = date.day;

		// Continue
		int i;

		int totalDays = JalaliDays(jyear, jmonth, jday);
		totalDays = totalDays + GYearOff;

		int gyear = (int) (totalDays / (Solar - 0.25 / 33));
		int Div4 = gyear / 4;
		int Div100 = gyear / 100;
		int Div400 = gyear / 400;
		int gdays = totalDays - (365 * gyear) - (Div4 - Div100 + Div400);
		gyear = gyear + 1;

		if (gdays == 0) {
			gyear--;
			gdays = GLeap(gyear) == 1 ? 366 : 365;
		} else {
			if (gdays == 366 && GLeap(gyear) != 1) {
				gdays = 1;
				gyear++;
			}
		}

		int leap = GLeap(gyear);
		for (i = 0; i <= 12; i++) {
			if (gdays <= GDayTable[leap][i]) {
				break;
			}

			gdays = gdays - GDayTable[leap][i];
		}

		int iGMonth = i + 1;
		int iGDay = gdays;

		return new GregorianCalendar(gyear, iGMonth-1, iGDay, date.hour, date.minute, date.second)
				.getTime();
	}

	/*
	 * /// <summary> /// Converts a Persian Date of type <c>String</c> to
	 * Gregorian Date of type <c>String</c>. /// </summary> /// <param
	 * name="date"></param> /// <returns>Gregorian DateTime representation in
	 * string format of evaluated Jalali Date.</returns> public static String
	 * ToGregorianDate(PersianDate date) { return String.format(format, args)
	 * return (Util.toDouble(iGMonth) + "/" + Util.toDouble(iGDay) + "/" + gyear
	 * + " " + Util.toDouble(date.Hour) + ":" + Util.toDouble(date.Minute) + ":"
	 * + Util.toDouble(date.Second)); }
	 */

	public static String DayOfWeek(PersianDate date) {
		if (date != null) {
			Date dt = ToGregorianDateTime(date);
			return DayOfWeek(dt);
		}

		return "";
	}

	// / <summary>
	// / Gets Persian Weekday name from specified Gregorian Date.
	// / </summary>
	// / <param name="date"></param>
	// / <returns></returns>
	public static String DayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);

		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SATURDAY:
			return weekdays[0];
		case Calendar.SUNDAY:
			return weekdays[1];
		case Calendar.MONDAY:
			return weekdays[2];
		case Calendar.TUESDAY:
			return weekdays[3];
		case Calendar.WEDNESDAY:
			return weekdays[4];
		case Calendar.THURSDAY:
			return weekdays[5];
		case Calendar.FRIDAY:
			return weekdays[6];
		default:
			return "";
		}
	}

	// / <summary>
	// / Returns number of days in specified month number.
	// / </summary>
	// / <param name="MonthNo">Month no to evaluate in integer</param>
	// / <returns>number of days in the evaluated month</returns>
	public static int MonthDays(int MonthNo) {
		return (JDayTable[1][MonthNo - 1]);
	}

}

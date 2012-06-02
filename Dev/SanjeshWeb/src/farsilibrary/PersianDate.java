package farsilibrary;

import org.apache.commons.lang3.StringUtils;

public class PersianDate {
	public int year, month, day, hour, minute, second;
	
	public PersianDate(){}
	public PersianDate(int y, int m, int d){
		setDate(y, m, d);
	}
	
	public PersianDate(int year, int month, int day, int hour, int minute, int second){
		this(year, month, day);
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public PersianDate(String value){
		String[] parts = StringUtils.split(value, '/');
		setDate(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
	}

	public void setDate(int y, int m, int d){
		this.year = y;
		this.month = m;
		this.day = d;
	}
	
	@Override
	public String toString() {
	    return String.format("%1$04d/%2$02d/%3$02d", year, month, day);
	}

}

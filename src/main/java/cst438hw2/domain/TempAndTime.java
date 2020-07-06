package cst438hw2.domain;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TempAndTime {
	public double temp;
	public long time;
	public int timezone;
	
	public TempAndTime(double temp, long time, int timezone){
		this.temp = temp;
		this.time = time;
		this.timezone = timezone;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	// Added public method to convert stored Kelvin Value to F.
	// In a real web service we would have getCelcius as well.
	// This would allow user preference for Degree scale.
	public double getFahrenheit(){
		return (double) Math.round(((this.temp - 273.15) * 9.0 / 5.0 + 32.0) * 100) / 100;
	}


	// Get Local time string used in requirement documents.
	public String getLocalTime(){
		Instant instant = Instant.ofEpochSecond(this.time);
		ZoneOffset offset = ZoneOffset.ofTotalSeconds(this.timezone);

		OffsetDateTime offsetDate = instant.atOffset(offset);

		String timeFormat = "hh:mm a";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(timeFormat);

		return offsetDate.format(dtf);
	}
}

/*
 *                        AT&T - PROPRIETARY
 *          THIS FILE CONTAINS PROPRIETARY INFORMATION OF
 *        AT&T AND IS NOT TO BE DISCLOSED OR USED EXCEPT IN
 *             ACCORDANCE WITH APPLICABLE AGREEMENTS.
 *
 *          Copyright (c) 2013 AT&T Knowledge Ventures
 *              Unpublished and Not for Publication
 *                     All Rights Reserved
 */
package com.att.research.xacml.std.datatypes;

import java.text.ParseException;

import com.att.research.xacml.api.SemanticString;

/**
 * ISO8601TimeZone represents an ISO8601 TimeZone specification.
 * 
 * @author car
 * @version $Revision: 1.1 $
 */
public class ISO8601TimeZone implements Comparable<ISO8601TimeZone>, SemanticString {
	private static final int MAX_TZOFFSET_MINUTES				= 60*24;
	private static final int MAX_NORMALIZED_TZOFFSET_MINUTES	= 60*12;
	
	private int	tzOffsetMinutes;
	
	public static final ISO8601TimeZone TIMEZONE_GMT	= new ISO8601TimeZone(0);
	
	/**
	 * Creates a new <code>ISO8601TimeZone</code> with the given time zone offset in minutes.
	 * 
	 * @param tzOffsetMinutesIn the time zone offset in minutes
	 */
	public ISO8601TimeZone(int tzOffsetMinutesIn) {
		int	absOffsetMinutes	= Math.abs(tzOffsetMinutesIn);
		if (absOffsetMinutes > MAX_TZOFFSET_MINUTES) {
			throw new IllegalArgumentException("Invalid ISO8601 timezone offset " + tzOffsetMinutesIn);
		}
		if (absOffsetMinutes <= MAX_NORMALIZED_TZOFFSET_MINUTES) {
			this.tzOffsetMinutes	= tzOffsetMinutesIn;
		} else {
			if (tzOffsetMinutesIn < 0) {
				this.tzOffsetMinutes	= MAX_TZOFFSET_MINUTES - absOffsetMinutes;
			} else {
				this.tzOffsetMinutes	= -(MAX_TZOFFSET_MINUTES - absOffsetMinutes);
			}
		}
	}
	
	public static ISO8601TimeZone fromString(String timezoneString) throws ParseException {
		if (timezoneString == null) {
			throw new NullPointerException();
		}
		if (timezoneString.length() == 0) {
			throw new ParseException("zero length string for time zone", 0);
		}
		/*
		 * Look for timezone information
		 */
		int startPos		= ParseUtils.nextNonWhite(timezoneString, 0);
		int	offsetMinutes	= 0;
		int	signPart		= 1;

		switch(timezoneString.charAt(startPos)) {
		case 'Z':
			offsetMinutes	= 0;
			startPos++;
			break;
		case '-':
			signPart	= -1;
			/*
			 * Note: Purposefully not breaking here so the remainder of timezone parsing takes place following the '+' label
			 */
		case '+':
			startPos++;
			int	hourPart	= ParseUtils.getTwoDigitValue(timezoneString, startPos);
			if (hourPart < 0 || hourPart > 24) {
				throw new ParseException("Invalid timezone", startPos);
			}
			startPos	+= 2;
			if (startPos >= timezoneString.length() || timezoneString.charAt(startPos) != ':') {
				throw new ParseException("Invalid time string", startPos);
			}
			startPos++;
			int minutePart	= ParseUtils.getTwoDigitValue(timezoneString, startPos);
			if (minutePart < 0 || minutePart >= 60) {
				throw new ParseException("Invalid timezone", startPos);
			}
			startPos	+= 2;
			
			offsetMinutes	= signPart*(hourPart*60 + minutePart);
			break;
			
		default:
			throw new ParseException("Invalid timezone", startPos);
		}
		return new ISO8601TimeZone(offsetMinutes);		
	}
	
	/**
	 * Gets the offset in minutes from GMT for this <code>TimeZone</code>.
	 * 
	 * @return the offset in minutes from GMT for this <code>TimeZone</code>>
	 */
	public int getTzOffsetMinutes() {
		return this.tzOffsetMinutes;
	}
	
	public String getTimeZoneString() {
		if (this.tzOffsetMinutes == 0) {
			return "GMT";
		} else {
			int	offsetAbs	= Math.abs(this.tzOffsetMinutes);
			String sign		= (this.tzOffsetMinutes < 0 ? "-" : "+");
			int hours		= offsetAbs / 60;
			int minutes		= offsetAbs - (60 * hours);
			return "GMT" + sign + String.format("%02d", hours) + ":" + String.format("%02d",  minutes);
		}
	}
	
	@Override
	public String stringValue() {
		int				offset			= this.getTzOffsetMinutes();
		if (offset == 0) {
			return "Z";
		}
		
		StringBuilder 	stringBuilder	= new StringBuilder();
		if (offset < 0) {
			stringBuilder.append('-');
			//
			// Fix a problem where negative offset timezones were displaying with two minuses
			// Since the minus is contained within the int, as well as being appended above
			//
			offset = offset * -1;
		} else {
			stringBuilder.append('+');
		}
		int	hourPart	= offset / 60;
		int minutePart	= offset - (hourPart * 60);
		stringBuilder.append(String.format("%02d", hourPart));
		stringBuilder.append(':');
		stringBuilder.append(String.format("%02d", minutePart));
		
		return stringBuilder.toString();		
	}
	
	@Override
	public int hashCode() {
		return this.getTzOffsetMinutes();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ISO8601TimeZone)) {
			return false;
		} else if (obj == this) {
			return true;
		} else {
			return this.getTzOffsetMinutes() == ((ISO8601TimeZone)obj).getTzOffsetMinutes();
		}
	}
	
	@Override
	public String toString() {
		return "{tzOffsetMinutes=" + this.getTzOffsetMinutes() + "}";
	}

	@Override
	public int compareTo(ISO8601TimeZone o) {
		return this.getTzOffsetMinutes() - o.getTzOffsetMinutes();
	}

}

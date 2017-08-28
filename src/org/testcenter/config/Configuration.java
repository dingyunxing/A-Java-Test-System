package org.testcenter.config;

import java.text.DateFormat;
import java.util.Locale;

public abstract class Configuration {
	public static final String[] CHOICE_LABEL=
		{"a","b","c","d","e","f","g","h","i","j","k"};

	private static final DateFormat dateFormat=
		DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL);
	public static final int MAX_LOGIN = 3;
	public static final DateFormat getDateFormat(Locale... locales){
		if(locales==null || locales.length==0)return dateFormat;
		return DateFormat.getDateTimeInstance(
					DateFormat.FULL,DateFormat.FULL,locales[0]);
	}
}




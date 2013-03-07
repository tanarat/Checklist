package org.silk.checklist;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;


public class ChecklistUtils {
	public static final String DATE_PATTERN = "dd-MM-yyyy HH:mm";
	public static String[] readToLines(InputStream ins) {
		// TODO Auto-generated method stub
		List<String> lines = new ArrayList<String>();
		Scanner scanner = new Scanner(ins);
		while(scanner.hasNextLine()){
			lines.add(scanner.nextLine());
		}
		return lines.toArray(new String[lines.size()]);
	}
	
	/*
	 * input : [x, x, x]
	 * output: x, x, x 
	 */

	public static String formatStr(String str) {
		// TODO Auto-generated method stub
		str = str.replace('[', ' ');
		str = str.replace(']', ' ');
		str = str.trim();
		return str;
	}

	public static Set<Long> xxx(String strItemIds) {
		Set<Long> retSet = new HashSet<Long>();
		StringTokenizer strToken = new StringTokenizer(strItemIds,",");
		while(strToken.hasMoreTokens()){
			String token = strToken.nextToken();
			token = token.trim();
			retSet.add(Long.parseLong(token));
		}
		return retSet;
	}

	public static String getStringDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
		return simpleDateFormat.format(date);
	}
	public static Date getDateFromString(String dateString){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
		try {
			return simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

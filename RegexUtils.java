package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	//returns a match string utilizing regex, given a certain source and pattern
	public static String regexHelper(String src, String ptrn) {
		Pattern pattern = Pattern.compile(ptrn);
		Matcher matcher = pattern.matcher(src);
		if (matcher.find()) {
			return matcher.group(1);
		} else
			return "";
	}

	//utilizing regex, traverse the html string and returns a string containing html version
	public static String getHtmlVersion(String src) {
		String html = regexHelper(src, "(?<=<!)(.*?)(?=>)");
		if (html.equalsIgnoreCase("DOCTYPE html")) // doctype html with no further specifications means that its version is html 5
			return "HTML 5";
		else {
			String html2 = regexHelper(src, "(\\w{4,5}\\s\\d\\..+?)\\W"); // if the previous match fails, html version is older than html 5
			if (!html2.isEmpty()) {
				return html2;
			} else
				return "No match";
		}
	}

	//via regex, finds the title tag and returns its content
	public static String getTitle(String src) {
		String title = regexHelper(src, "<title>(.*)</title>");
		if (!title.isEmpty()) {
			return title;
		} else {
			title = regexHelper(src, "<TITLE>(.*)</TITLE>");
			return title.isEmpty() ? "No match" : title;
		}
	}

}

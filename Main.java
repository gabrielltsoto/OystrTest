package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {
		String content = "";
		String source = "https://docs.oracle.com/en/java/"; // source url to be analysed
		if(!source.contains("https://")) {
			source = "https://" + source;
		}
		try {
			//method from java.net library that attempts connection to given url
			URL url = new URL(source);
			URLConnection urlConnection = url.openConnection();
			urlConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())); //using buffered reader, feeds content string with the html document 

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				content += inputLine + "\n";
			}
			in.close();
			String title = RegexUtils.getTitle(content);
			String htmlVersion = RegexUtils.getHtmlVersion(content);
			int externalLinkCount = 0;
			int internalLinkCount = 0;
			//via jsoup find elements in the href tags and counts into external(https) and internal links
			Document doc = Jsoup.parse(content);
			Elements elements = doc.select("a[href]");
			for (Element element : elements) {
				if (element.attr("href").contains("http"))
					externalLinkCount++;
				else
					internalLinkCount++;
			}
			System.out.println("Analysing: " + source);
			System.out.println("HTML version: " + htmlVersion);
			System.out.println("Page title: " + title);
			System.out.println("External Links: " + externalLinkCount);
			System.out.println("Internal Links: " + internalLinkCount);

		} catch (Exception e) {
			System.out.println("Failed to connect to " + source);
		}
	}
}

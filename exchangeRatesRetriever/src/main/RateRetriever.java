package main;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RateRetriever {
	private TreeMap<String, String> ratesMap;
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private String date;

	private void parseRates(String url) {
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.parse(url);
			doc.getDocumentElement().normalize();
			ratesMap = new TreeMap<String,String>();
			String currency;
			String rate;
			// loop through each exchange rate item
			NodeList rates = doc.getElementsByTagName("Cube");
			for (int i = 0; i < rates.getLength(); i++) {
				Node n = rates.item(0);
				if (n.getNodeType() != Node.ELEMENT_NODE)
					continue;
				Element e = (Element) n;

				NodeList rateList = e.getElementsByTagName("Cube");
				Element rateElem = (Element) rateList.item(i);
				if (rateElem != null) {
					if (i == 0) {
						date = rateElem.getAttribute("time");
					} else {
						currency = rateElem.getAttribute("currency").toUpperCase();
								rate = rateElem.getAttribute("rate");
								if (!(currency == null || rate == null)){
									ratesMap.put(currency, rate);
								}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printRates() {
		System.out.println("The rates for " + date + " are: \n") ;
		for(Map.Entry<String,String> entry : ratesMap.entrySet()) {
			System.out.println("For currency '" + entry.getKey() + "' rate is: " + entry.getValue() );			
		}
	}
	
	public void retrieveRates(String url) {
		parseRates(url);
		printRates();
	}
	
	public static void main(String argsv[]) {
		String url = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
		RateRetriever rrInst = new RateRetriever();
		rrInst.retrieveRates(url);
	}
}

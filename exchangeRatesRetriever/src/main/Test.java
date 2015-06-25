package main;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test {
	public static void main(String argsv[]) {
		String url = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(url);
			String date = "";
			doc.getDocumentElement().normalize();

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
						 System.out.println(rateElem.getAttribute("time"));
					} else {

						 System.out.println(rateElem.getAttribute("currency")
						 + " " + rateElem.getAttribute("rate"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

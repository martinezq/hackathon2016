package com.comarch.hackathon.c3tax2xmi.saxparser;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.comarch.hackathon.c3tax2xmi.util.RdfUtils;

public class ListUniqueSubjectTypes {

	@Test
	public void list() {
		try {
			C3TaxParser parser = new C3TaxParser();
			parser.parse(new File("q:\\hackathon\\eahackathon\\Coding Challange\\em.rdf"));
			
			Set<String> result = RdfUtils.findUniqueRdfTypes(parser.getParsedElements());
			
			for(String val: result) {
				String label = val.split("/")[6].replaceAll("Category-3A", "");
				System.out.println("public static String " + label + " = \"" + val + "\";");
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
}

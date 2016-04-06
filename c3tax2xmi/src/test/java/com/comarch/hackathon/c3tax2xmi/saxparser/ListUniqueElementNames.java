package com.comarch.hackathon.c3tax2xmi.saxparser;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.comarch.hackathon.c3tax2xmi.util.RdfUtils;

public class ListUniqueElementNames {

	@Test
	public void listJustNames() {
		try {
			C3TaxParser parser = new C3TaxParser();
			parser.parse(new File("q:\\hackathon\\eahackathon\\Coding Challange\\em.rdf"));
			
			Set<String> result = RdfUtils.findUniqueElementNames(parser.getParsedElements());
			
			for(String val: result) {
				System.out.println(val);
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void listNamesAndDataTypes() {
		try {
			C3TaxParser parser = new C3TaxParser();
			parser.parse(new File("q:\\hackathon\\eahackathon\\Coding Challange\\em.rdf"));
			
			Map<String, String> result = RdfUtils.findUniqueElementNamesAndDataTypes(parser.getParsedElements());
			
			for(String val: result.keySet()) {
				String datatype = result.get(val);
				System.out.println(val + ", " + datatype);
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}

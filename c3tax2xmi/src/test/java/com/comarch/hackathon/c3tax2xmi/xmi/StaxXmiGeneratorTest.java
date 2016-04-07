package com.comarch.hackathon.c3tax2xmi.xmi;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.comarch.hackathon.c3tax2xmi.saxparser.C3TaxParser;

public class StaxXmiGeneratorTest {

	@Test
	public void test() {
		try {
			C3TaxParser parser = new C3TaxParser();
			parser.parse(new File("q:\\hackathon\\eahackathon\\Coding Challange\\em.rdf" ));
			
			StaxXmiGenerator generator = new StaxXmiGenerator(parser.getParsedElements(), parser.getRootElement());
			
			generator.write("out/stax.xml");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
}

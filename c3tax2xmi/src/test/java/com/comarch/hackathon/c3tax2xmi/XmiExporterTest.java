package com.comarch.hackathon.c3tax2xmi;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XmiExporterTest {

	@Test
	public void testExportFromUrl() throws SAXException, IOException, ParserConfigurationException {
		String filePath = "out/xmi_" + Long.toString(System.nanoTime()) + ".xml";
		XmiExporter exporter = new XmiExporter();
		exporter.loadFromUrl("http://192.168.1.25/backups/em.rdf", "em", "em");
		exporter.exportToFile(filePath);
		File file = new File(filePath);
		
		Assert.assertTrue("File should exist", file.exists());
	}
	
}

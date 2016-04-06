package com.comarch.hackathon.c3tax2xmi;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class XmiExporterTest {

	@Test
	public void testExportFromUrl() {
		String filePath = "out/xmi_" + Long.toString(System.nanoTime()) + ".xml";
		XmiExporter exporter = new XmiExporter(filePath);
		exporter.exportFromUrl("http://192.168.1.25/backups/em.rdf", "em", "em");
		
		File file = new File(filePath);
		
		Assert.assertTrue("File should exist", file.exists());
	}
	
}

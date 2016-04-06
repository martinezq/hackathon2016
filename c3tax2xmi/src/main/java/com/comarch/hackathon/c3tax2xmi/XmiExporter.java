package com.comarch.hackathon.c3tax2xmi;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.comarch.hackathon.c3tax2xmi.saxparser.C3TaxParser;
import com.comarch.hackathon.c3tax2xmi.xmi.StaxXmiGenerator;

public class XmiExporter {

	private String xmiResultPath;
	
	public XmiExporter(String xmiResultPath) {
		this.xmiResultPath = xmiResultPath;
	}
	
	public void exportFromFile(String path) {
		File file = new File(path);
		exportFromFile(file);
	}
	
	public void exportFromUrl(String url) {
		RdfDownloader downloader = new RdfDownloader(url);
		File file = downloader.download();
		exportFromFile(file);
	}

	public void exportFromUrl(String url, String user, String pass) {
		RdfDownloader downloader = new RdfDownloader(url, user, pass);
		File file = downloader.download();
		exportFromFile(file);
	}
	
	public void exportFromFile(File file) {
		try {
			C3TaxParser parser = new C3TaxParser();
			parser.parse(file);
			
			StaxXmiGenerator generator = new StaxXmiGenerator(parser.getParsedElements());
			
			generator.write(xmiResultPath);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
}

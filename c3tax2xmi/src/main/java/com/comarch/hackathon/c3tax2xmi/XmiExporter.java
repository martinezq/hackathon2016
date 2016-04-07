package com.comarch.hackathon.c3tax2xmi;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;
import com.comarch.hackathon.c3tax2xmi.saxparser.C3TaxParser;
import com.comarch.hackathon.c3tax2xmi.xmi.StaxXmiGenerator;

public class XmiExporter {

	private C3TaxParser parser = new C3TaxParser();
	private StaxXmiGenerator generator = new StaxXmiGenerator();
	
	public XmiExporter() {
	}
	
	public void loadFromFile(String path) throws SAXException, IOException, ParserConfigurationException {
		File file = new File(path);
		loadSourceFile(file);
	}
	
	public void loadFromUrl(String url) throws SAXException, IOException, ParserConfigurationException {
		RdfDownloader downloader = new RdfDownloader(url);
		File file = downloader.download();
		loadSourceFile(file);
	}

	public void loadFromUrl(String url, String user, String pass) throws SAXException, IOException, ParserConfigurationException {
		RdfDownloader downloader = new RdfDownloader(url, user, pass);
		File file = downloader.download();
		loadSourceFile(file);
	}
	
	private void loadSourceFile(File file) throws SAXException, IOException, ParserConfigurationException {
		parser.parse(file);
	}
	
	public void exportToFile(String outFile) {
		generator.setSubjects(parser.getParsedElements());
		generator.setRoot(getRoot());
		generator.write(outFile);
	}
	
	public RdfSubject getRoot() {
		return parser.getRootElement();
	}

	public void setLimit(int limit) {
		generator.setObjectCountLimit(limit);
	}

	public void setCategoriesToExport(Collection<String> categoriesToExport) {
		generator.setCategoriesToExport(categoriesToExport);
	}
	
}

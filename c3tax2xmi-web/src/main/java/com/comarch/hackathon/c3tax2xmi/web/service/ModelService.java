package com.comarch.hackathon.c3tax2xmi.web.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;
import com.comarch.hackathon.c3tax2xmi.saxparser.C3TaxParser;
import com.comarch.hackathon.c3tax2xmi.util.RdfUtils;

@Service
public class ModelService {
	
	private Collection<RdfSubject> subjects;
	private Set<String> categories;
	private C3TaxParser parser;
	
	public ModelService() {
		loadModelFromFile("q:\\hackathon\\eahackathon\\Coding Challange\\em.rdf");
	}
	
	public void loadModelFromFile(String file) {
		try {
			parser = new C3TaxParser();
			parser.parse(new File(file));
			
			subjects = parser.getParsedElements();
			categories = RdfUtils.findUniqueRdfTypes(subjects);
			
			System.out.println("Finished loading file " + file);
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Collection<RdfSubject> getSubjects() {
		return subjects;
	}
	
	public Set<String> getCategories() {
		return categories;
	}

	public Collection<RdfSubject> findSubjectsById(List<String> ids) {
		Set<String> set = new HashSet<>(ids);
		return subjects.stream().filter(s -> set.contains(s.getExportId())).collect(Collectors.toList());
	}

	public C3TaxParser getParser() {
		return parser;
	}
	
}

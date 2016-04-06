package com.comarch.hackathon.c3tax2xmi.xmi;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class StaxXmiGenerator {
	
	public static int OBJECT_LIMIT = 10000;

	private int limit;
	private int countPackages;
	private int countClasses;
	
	XMLStreamWriter writer;
	
	Collection<RdfSubject> subjects;
	
	public static String[] LEGAL_TYPES = {
			"/Category-3ATaxonomy_Nodes", 
			"/Category-3AServices", 
			"/Category-3AApplications",
			"/Category-3ABusiness_Processes",
			"/Category-3AEquipment",
			"/Category-3AInformation_Products"
		};

	public StaxXmiGenerator(Collection<RdfSubject> subjects) {
		this.subjects = subjects;
	}
	
	private List<RdfSubject> findRoots() {
		List<RdfSubject> roots = new ArrayList<>();
		for (RdfSubject subject : subjects) {
			if ("C3 Taxonomy".equals(subject.getLabel())) {
				System.out.println("Root " + subject.getId());
				roots.add(subject);
			}
		}
		System.out.println("Found roots: " + roots.size());
		return roots;
	}

	public void write(String file) {
		try {
			FileWriter fileWriter = new FileWriter(file);

			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			writer = xmlOutputFactory.createXMLStreamWriter(fileWriter);
			
			XmiFile xmiFile = new XmiFile(writer);
			xmiFile.writeStart();
			XmiModel xmiModel = new XmiModel(writer);
			xmiModel.writeStart();
			
			limit = OBJECT_LIMIT;
			countPackages = countClasses = 0;
			
			List<RdfSubject> roots = findRoots();
			
			System.out.println("Generating objects...");
			for (RdfSubject root : roots) {
				outputPackage(root);
			}
			
			xmiModel.writeEnd();
			xmiFile.writeEnd();

			writer.flush();
			writer.close();
			System.out.println("Generated packages: " + countPackages);
			System.out.println("Generated classes: " + countClasses);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<RdfSubject> getTaxonomyChildren(RdfSubject subject) {
		List<RdfSubject> taxonomy = new ArrayList<>();
		for (RdfSubject child : subject.getChildren()) {
			if (XmiSubjectObject.shouldWrite(child)) {
				taxonomy.add(child);
			}
		}
		return taxonomy;
	}
	
	private void outputPackage(RdfSubject subject) throws XMLStreamException {
		List<RdfSubject> children = getTaxonomyChildren(subject);
		if (!children.isEmpty()) {
			XmiPackage xmiPackage = new XmiPackage(writer, subject);
			xmiPackage.writeStart();
			for (RdfSubject child : children) {
				if (--limit <= 0) {
					break;
				}
				outputPackage(child);
				countPackages++;
			}
			xmiPackage.writeEnd();
		} else {
			new XmiClass(writer, subject).serialize();
			countClasses++;
		}
	}
	
}

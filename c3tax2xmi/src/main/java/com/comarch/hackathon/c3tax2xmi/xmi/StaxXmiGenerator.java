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
	
	private int objectCountLimit = 1000;

	private int countdown;
	private int countPackages;
	private int countClasses;
	
	XMLStreamWriter writer;
	
	Collection<RdfSubject> subjects;
	Collection<XmiObject> objects;
	
	public static String[] DEFAULT_LEGAL_TYPES = {
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
			
			countdown = objectCountLimit;
			countPackages = countClasses = 0;
			
			List<RdfSubject> roots = findRoots();
			objects = new ArrayList<>();
			
			System.out.println("Generating objects...");
			for (RdfSubject root : roots) {
				outputPackage(root);
			}
			
			xmiModel.writeEnd();
			
			XmiExtension xmiExtension = new XmiExtension(writer);
			xmiExtension.writeStart();
			for (XmiObject obj : objects) {
				obj.extensionStart();
				obj.extensionEnd();
			}
			xmiExtension.writeEnd();
			
			xmiFile.writeEnd();

			writer.flush();
			writer.close();
			System.out.println("Generated packages: " + countPackages);
			System.out.println("Generated classes: " + countClasses);
			System.out.println("Finished. Total objects: " + objects.size());
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
			objects.add(xmiPackage);
			xmiPackage.writeStart();
			for (RdfSubject child : children) {
				if (--countdown <= 0) {
					break;
				}
				outputPackage(child);
				countPackages++;
			}
			xmiPackage.writeEnd();
		} else {
			XmiClass xmiClass = new XmiClass(writer, subject);
			xmiClass.serialize();
			objects.add(xmiClass);
			countClasses++;
		}
	}

	public int getObjectCountLimit() {
		return objectCountLimit;
	}

	public void setObjectCountLimit(int objectCountLimit) {
		this.objectCountLimit = objectCountLimit;
	}
	
}

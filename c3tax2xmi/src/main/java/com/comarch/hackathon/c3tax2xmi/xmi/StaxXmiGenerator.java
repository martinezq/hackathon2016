package com.comarch.hackathon.c3tax2xmi.xmi;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class StaxXmiGenerator {
	
	private int objectCountLimit = 100000;

	private int countdown;
	private int countPackages;
	private int countClasses;
	
	GeneratorConfig config = new GeneratorConfig();
	
	private Collection<RdfSubject> subjects;
	private RdfSubject root;
	private Collection<XmiObject> objects;
	private Collection<String> categoriesToExport;
	
	public StaxXmiGenerator() {
	}

	public void write(String file) {
		try {
			
			FileWriter fileWriter = new FileWriter(file);

			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			config.writer = xmlOutputFactory.createXMLStreamWriter(fileWriter);
			
			XmiFile xmiFile = new XmiFile(config);
			xmiFile.writeStart();
			XmiModel xmiModel = new XmiModel(config);
			xmiModel.writeStart();
			
			countdown = objectCountLimit;
			countPackages = countClasses = 0;
			
			objects = new ArrayList<>();
			
			System.out.println("Generating objects...");
			writePackageTree(root);
			
			xmiModel.writeEnd();
			
			XmiExtension xmiExtension = new XmiExtension(config);
			xmiExtension.writeStart();
			for (XmiObject obj : objects) {
				obj.extensionStart();
				obj.extensionEnd();
			}
			xmiExtension.writeEnd();
			
			xmiFile.writeEnd();

			config.writer.flush();
			config.writer.close();
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
			if (config.shouldWrite(child)) {
				taxonomy.add(child);
			}
		}
		return taxonomy;
	}
	
	private void writePackageTree(RdfSubject subject) throws XMLStreamException {
		List<RdfSubject> children = getTaxonomyChildren(subject);
		if (!children.isEmpty()) {
			XmiPackage xmiPackage = new XmiPackage(config, subject);
			objects.add(xmiPackage);
			xmiPackage.writeStart();
			for (RdfSubject child : children) {
				if (--countdown <= 0) {
					break;
				}
				writePackageTree(child);
				countPackages++;
			}
			xmiPackage.writeEnd();
		} else {
			XmiClass xmiClass = new XmiClass(config, subject);
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

	public Collection<RdfSubject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Collection<RdfSubject> subjects) {
		this.subjects = subjects;
	}

	public RdfSubject getRoot() {
		return root;
	}

	public void setRoot(RdfSubject root) {
		this.root = root;
	}

	public Collection<String> getCategoriesToExport() {
		return categoriesToExport;
	}

	public void setCategoriesToExport(Collection<String> categoriesToExport) {
		this.categoriesToExport = categoriesToExport;
	}
	
}

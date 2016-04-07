package com.comarch.hackathon.c3tax2xmi.xmi;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
	private boolean writeExtensions = false;
	
	GeneratorConfig config = new GeneratorConfig();
	
	private Collection<RdfSubject> subjects;
	private RdfSubject root;
	private Collection<XmiObject> objects;
	
	public StaxXmiGenerator() {
	}

	public void write(String file) {
		try {
			FileWriter fileWriter = new FileWriter(file);
			write(fileWriter);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void write(Writer argWriter) {
		try {
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			config.writer = xmlOutputFactory.createXMLStreamWriter(argWriter);
			
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
			
			if (writeExtensions) {
				XmiExtension xmiExtension = new XmiExtension(config);
				xmiExtension.writeStart();
				for (XmiObject obj : objects) {
					obj.extensionStart();
					obj.extensionEnd();
				}
				xmiExtension.writeEnd();
			}
			
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
		boolean shouldWrite = subjects.contains(subject);
		List<RdfSubject> children = getTaxonomyChildren(subject);
		if (!children.isEmpty()) {
			XmiPackage xmiPackage = new XmiPackage(config, subject);
			if (shouldWrite) {
				objects.add(xmiPackage);
				xmiPackage.writeStart();
			}
			for (RdfSubject child : children) {
				if (--countdown <= 0) {
					break;
				}
				writePackageTree(child);
				countPackages++;
			}
			if (shouldWrite) {
				xmiPackage.writeEnd();
			}
		} else {
			if (shouldWrite) {
				XmiClass xmiClass = new XmiClass(config, subject);
				xmiClass.serialize();
				objects.add(xmiClass);
				countClasses++;
			}
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
		return config.getCategoriesToExport();
	}

	public void setCategoriesToExport(Collection<String> categoriesToExport) {
		config.setCategoriesToExport(categoriesToExport);
	}

	public void setWriteExtensions(boolean b) {
		this.writeExtensions = b;
	}
	
}

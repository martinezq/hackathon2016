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

	private int limit;
	
	XMLStreamWriter writer;
	
	Collection<RdfSubject> subjects;

	public StaxXmiGenerator(Collection<RdfSubject> subjects) {
		this.subjects = subjects;
	}
	
	private List<RdfSubject> findRoots() {
		List<RdfSubject> roots = new ArrayList<>();
		for (RdfSubject subject : subjects) {
			/*if (subject.getParent() == null) {
				System.out.println("parentless " + subject.getId());
				roots.add(subject);
			}*/
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
			
			limit = 1000;
			
			List<RdfSubject> roots = findRoots();
			
			for (RdfSubject root : roots) {
				outputPackage(root);
			}
			
			xmiModel.writeEnd();
			xmiFile.writeEnd();

			writer.flush();
			writer.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<RdfSubject> getTaxonomyChildren(RdfSubject subject) {
		List<RdfSubject> taxonomy = new ArrayList<>();
		for (RdfSubject child : subject.getChildren()) {
			if (child.hasType("/Category-3ATaxonomy_Nodes")
					|| child.hasType("/Category-3AServices")
					|| child.hasType("/Category-3AApplications")) {
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
			}
			xmiPackage.writeEnd();
		} else {
			new XmiClass(writer, subject).serialize();
		}
	}
	
}

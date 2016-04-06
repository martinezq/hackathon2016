package com.comarch.hackathon.c3tax2xmi.xmi;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class StaxXmiGenerator {

	private static final String xmiNs = "http://schema.omg.org/spec/XMI/2.1";
	private static final String umlNs = "http://schema.omg.org/spec/UML/2.1";
	private static final String cmofNs = "http://schema.omg.org/spec/MOF/2.0/cmof.xml";
	private static final String eol = "\n";
	private int limit;
	
	XMLStreamWriter writer;
	
	Collection<RdfSubject> subjects;

	String modelId = UUID.randomUUID().toString();
	
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
			
			writeHeaderStart();
			writeModelStart();
			writer.writeCharacters(eol);
			
			limit = 100;
			
			List<RdfSubject> roots = findRoots();
			
			for (RdfSubject root : roots) {
				outputPackage(root);
			}
			
			writeModelEnd();
			writeHeaderEnd();

			writer.flush();
			writer.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<RdfSubject> getTaxonomyChildren(RdfSubject subject) {
		List<RdfSubject> taxonomy = new ArrayList<>();
		for (RdfSubject child : subject.getChildren()) {
			String type = child.getType();
			if ("http://192.168.1.25/em/index.php/Special:URIResolver/Category-3ATaxonomy_Nodes".equals(type)) {
				taxonomy.add(child);
			}
		}
		return taxonomy;
	}
	
	private void outputPackage(RdfSubject subject) throws XMLStreamException {
		String name = subject.buildName();
		List<RdfSubject> children = getTaxonomyChildren(subject);
		if (!children.isEmpty()) {
			//String packageId = UUID.randomUUID().toString();
			String packageId = subject.getId();
			XmiPackage.writeStart(writer, packageId, name);
			XmiObject.writeComment(writer, UUID.randomUUID().toString(), subject.getDescription(), packageId);
			for (RdfSubject child : children) {
				if (--limit <= 0) {
					break;
				}
				outputPackage(child);
			}
			XmiPackage.writeEnd(writer);
		} else {
			new XmiClass(subject).serialize(writer);
		}
	}
	
	private void writeHeaderStart() throws XMLStreamException {
		writer.writeStartDocument();
		
		writer.writeCharacters(eol);
		writer.writeStartElement("xmi", "XMI", xmiNs);
		writer.writeNamespace("xmi", xmiNs);
		writer.writeNamespace("uml", umlNs);
		writer.writeNamespace("cmof", cmofNs);
		writer.writeAttribute(xmiNs, "version", "2.1");

		writer.writeCharacters(eol);
		writer.writeStartElement(xmiNs, "Documentation");
		writer.writeAttribute("exporter", "Comarch C3Tax2XMI");
		writer.writeAttribute("exporterVersion", "0.1");
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}
	
	private void writeHeaderEnd() throws XMLStreamException {
		writer.writeEndElement();
		writer.writeCharacters(eol);
		writer.writeEndDocument();
	}
	
	private void writeModelStart() throws XMLStreamException {
		// <uml:Model xmi:type="uml:Model" xmi:id="themodel" name="sample">
		writer.writeStartElement(umlNs, "Model");
		writer.writeAttribute(xmiNs, "type", "uml:Model");
		writer.writeAttribute(xmiNs, "id", modelId);
		writer.writeAttribute("name", "C3 Taxonomy Model");
	}
	
	private void writeModelEnd() throws XMLStreamException {
		writer.writeEndElement();
	}
	
}

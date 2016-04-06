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
			writePackageStart(UUID.randomUUID().toString(), "C3 Taxonomy");
			
			for (RdfSubject subject: subjects) {
				if (subject.getTitle() == null) {
					continue;
				}
				
				outputClass(subject);
				
				if (--limit <= 0) {
					break;
				}
			}
			
			writePackageEnd();
			
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
			String packageId = UUID.randomUUID().toString();
			writePackageStart(packageId, name);
			writeComment(UUID.randomUUID().toString(), subject.getDescription(), packageId);
			for (RdfSubject child : children) {
				if (--limit <= 0) {
					break;
				}
				outputPackage(child);
			}
			writePackageEnd();
		} else {
			outputClass(subject);
		}
	}
	
	private void outputClass(RdfSubject subject) throws XMLStreamException {
		writeClassStart(UUID.randomUUID().toString(), subject);
		writeProperty(UUID.randomUUID().toString(), "Title", "uml:Property");
		writeClassEnd();
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
	
	private void writePackageStart(String id, String name) throws XMLStreamException {
		//<packagedElement xmi:type="uml:Package" xmi:id="BOUML_0x81_22" name ="sample">
		writer.writeCharacters(eol);
		writer.writeStartElement("packagedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Package");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("name", name);
	}
	
	private void writePackageEnd() throws XMLStreamException {
		writer.writeCharacters(eol);
		writer.writeEndElement();
	}
	
	private void writeClassStart(String id, RdfSubject subject) throws XMLStreamException {
		//<packagedElement xmi:type="uml:Class" name="Order" xmi:id="BOUML_0x1f498_4" visibility="package" isAbstract="false" >
		writer.writeStartElement("ownedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Class");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("name", subject.buildName());
		writer.writeAttribute("visibility", "package");
		
		writeComment(UUID.randomUUID().toString(), subject.getDescription(), id);
		
		//writer.writeStartElement("properties");
		//writer.writeAttribute("documentation", "Ala ma kota");
		
		//writer.writeEndElement();
	}
	
	private void writeClassEnd() throws XMLStreamException {
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}
	
	private void writeProperty(String id, String name, String type) throws XMLStreamException {
		/*
		 * 	<ownedAttribute xmi:type="uml:Property" name="Amount" xmi:id="BOUML_0x1f518_1" visibility="protected">
				<type xmi:type="uml:Class" xmi:idref="BOUML_datatype_0"/>
			</ownedAttribute>
		 */
		writer.writeStartElement("ownedAttribute");
		
		writer.writeAttribute(xmiNs, "type", "uml:Property");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("name", name);
		writer.writeAttribute("visibility", "public");
		
		writer.writeEndElement();
	}
	
	private void writeComment(String id, String text, String refId) throws XMLStreamException {
/*
 * 				<ownedComment xmi:type="cmof:Comment" xmi:id="Actions-CompleteActions-ReadExtentAction-_ownedComment.0" annotatedElement="cf26a852-b286-45a7-b1bf-4507eb30ee96">
					<body>A read extent action is an action that retrieves the current
						instances of a classifier.</body>
				</ownedComment>
 */
		writer.writeStartElement("ownedComment");
		writer.writeAttribute(xmiNs, "type", "cmof:Comment");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("annotatedElement", refId);
		
		writer.writeStartElement("body");
		writer.writeCharacters(text);
		writer.writeEndElement();
		
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}
	
	private void writeDependency(String id, String name, String fromRefId, String toRefId) throws XMLStreamException {
		// <packagedElement xmi:type="uml:Dependency" xmi:id="EAID_88D30676_83EA_4a1c_9A44_10FC146C0A0B" visibility="public" supplier="EAID_364D25D6_D6F9_48ee_BEA3_4293954709B9" client="EAID_29254430_1110_40dd_B1D4_90D189099EE7"/>
		writer.writeStartElement("packagedElement");
		
		writer.writeAttribute(xmiNs, "type", "uml:Dependency");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("supplier", fromRefId);
		writer.writeAttribute("client", toRefId);
		writer.writeAttribute("name", name);
		
		writer.writeEndElement();
	}
}

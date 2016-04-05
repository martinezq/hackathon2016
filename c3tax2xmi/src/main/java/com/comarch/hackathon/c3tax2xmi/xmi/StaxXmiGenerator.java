package com.comarch.hackathon.c3tax2xmi.xmi;

import java.io.FileWriter;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.saxparser.model.RdfSubject;

public class StaxXmiGenerator {

	static String xmiNs = "http://schema.omg.org/spec/XMI/2.1";
	static String umlNs = "http://schema.omg.org/spec/UML/2.1";
	
	XMLStreamWriter writer;
	
	Collection<RdfSubject> subjects;

	String modelId = UUID.randomUUID().toString();
	
	public StaxXmiGenerator(Collection<RdfSubject> subjects) {
		this.subjects = subjects;
	}

	public void write(String file) {
		try {
			FileWriter fileWriter = new FileWriter(file);

			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
			writer = xmlOutputFactory.createXMLStreamWriter(fileWriter);
			
			writeHeaderStart();
			writeModelStart();
			
			int count = 0;
			
			writePackageStart(UUID.randomUUID().toString(), "C3 Taxonomy");
			
			for(RdfSubject subject: subjects) {
				writeClassStart(UUID.randomUUID().toString(), subject.getId());
				writeClassEnd();
				
				if(count++ > 1) {
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
	
	private void writeHeaderStart() throws XMLStreamException {
		writer.writeStartDocument();
		
		writer.writeStartElement("xmi", "XMI", xmiNs);
		writer.writeNamespace("xmi", xmiNs);
		writer.writeNamespace("uml", umlNs);
		writer.writeAttribute(xmiNs, "version", "2.1");

		writer.writeStartElement(xmiNs, "Documentation");
		writer.writeAttribute("exporter", "Comarch C3Tax2XMI");
		writer.writeAttribute("exporterVersion", "0.1");
		writer.writeEndElement();
	}
	
	private void writeHeaderEnd() throws XMLStreamException {
		writer.writeEndElement();
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
		writer.writeStartElement("packagedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Package");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("name", name);		
	}
	
	private void writePackageEnd() throws XMLStreamException {
		writer.writeEndElement();
	}
	
	private void writeClassStart(String id, String name) throws XMLStreamException {
		//<packagedElement xmi:type="uml:Class" name="Order" xmi:id="BOUML_0x1f498_4" visibility="package" isAbstract="false" >
		writer.writeStartElement("packagedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Class");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("name", name);
		writer.writeAttribute("visibility", "package");
	}
	
	private void writeClassEnd() throws XMLStreamException {
		writer.writeEndElement();
	}
	
}

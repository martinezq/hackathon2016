package com.comarch.hackathon.c3tax2xmi.xmi;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class XmiPackage extends XmiObject {
	
	private RdfSubject subject;
	
	public XmiPackage(RdfSubject subject) {
		this.subject = subject;
	}

	public void serialize(XMLStreamWriter writer) throws XMLStreamException {
		String id = subject.getId();
		String name = subject.buildName();
		writeStart(writer, id, name);
	}
	
	public static void writeStart(XMLStreamWriter writer, String id, String name) throws XMLStreamException {
		//<packagedElement xmi:type="uml:Package" xmi:id="BOUML_0x81_22" name ="sample">
		writer.writeCharacters(eol);
		writer.writeStartElement("packagedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Package");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("name", name);
	}	

	public static void writeEnd(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeCharacters(eol);
		writer.writeEndElement();
	}

}

package com.comarch.hackathon.c3tax2xmi.xmi;

import java.util.UUID;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class XmiClass extends XmiObject {
	
	private RdfSubject subject;
	
	public XmiClass(RdfSubject subject) {
		this.subject = subject;
	}

	@Override
	public void serialize(XMLStreamWriter writer) throws XMLStreamException {
		writeStart(writer, UUID.randomUUID().toString(), subject);
		writeProperty(writer, UUID.randomUUID().toString(), "Title", "uml:Property");
		writeEnd(writer);
	}
	
	private void writeStart(XMLStreamWriter writer, String id, RdfSubject subject) throws XMLStreamException {
		//<packagedElement xmi:type="uml:Class" name="Order" xmi:id="BOUML_0x1f498_4" visibility="package" isAbstract="false" >
		writer.writeStartElement("ownedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Class");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("name", subject.buildName());
		writer.writeAttribute("visibility", "package");
		
		writeComment(writer, UUID.randomUUID().toString(), subject.getDescription(), id);
		
		//writer.writeStartElement("properties");
		//writer.writeAttribute("documentation", "Ala ma kota");
		
		//writer.writeEndElement();
	}

	private void writeEnd(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}

}

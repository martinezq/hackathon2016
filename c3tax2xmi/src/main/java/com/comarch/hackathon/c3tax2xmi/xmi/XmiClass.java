package com.comarch.hackathon.c3tax2xmi.xmi;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class XmiClass extends XmiObject {
	
	private RdfSubject subject;
	
	public XmiClass(XMLStreamWriter writer, RdfSubject subject) {
		super(writer);
		this.subject = subject;
	}

	@Override
	public void serialize() throws XMLStreamException {
		writeStart();
		// writeProperty(UUID.randomUUID().toString(), "Title", "uml:Property");
		writeEnd();
	}
	
	private void writeStart() throws XMLStreamException {
		//<packagedElement xmi:type="uml:Class" name="Order" xmi:id="BOUML_0x1f498_4" visibility="package" isAbstract="false" >
		writer.writeStartElement("ownedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Class");
		writer.writeAttribute(xmiNs, "id", subject.getExportId());
		writer.writeAttribute("name", subject.getExportName());
		writer.writeAttribute("visibility", "package");
		
		writeComment(subject.getDescription(), subject.getExportId());
		
		//writer.writeStartElement("properties");
		//writer.writeAttribute("documentation", "Ala ma kota");
		
		//writer.writeEndElement();
	}

	private void writeEnd() throws XMLStreamException {
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}

}

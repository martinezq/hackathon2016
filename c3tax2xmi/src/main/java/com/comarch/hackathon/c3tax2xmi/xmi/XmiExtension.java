package com.comarch.hackathon.c3tax2xmi.xmi;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XmiExtension extends XmiObject {

	public XmiExtension(XMLStreamWriter writer) {
		super(writer);
	}

	@Override
	public void serialize() throws XMLStreamException {
		// TODO Auto-generated method stub
	}

	@Override
	public void writeStart() throws XMLStreamException {
		writer.writeStartElement(xmiNs, "Extension");
		writer.writeAttribute("extender", "Enterprise Architect");
		writer.writeAttribute("extenderID", "6.5");
		writeEol();
		writer.writeStartElement("elements");
		writeEol();
	}

	@Override
	public void writeEnd() throws XMLStreamException {
		writer.writeEndElement(); // elements
		writeEol();
		writer.writeEndElement(); // Extension
		writeEol();
	}

}

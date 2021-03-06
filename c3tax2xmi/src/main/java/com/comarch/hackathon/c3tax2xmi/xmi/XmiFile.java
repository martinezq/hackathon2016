package com.comarch.hackathon.c3tax2xmi.xmi;

import javax.xml.stream.XMLStreamException;

public class XmiFile extends XmiObject {

	public XmiFile(GeneratorConfig config) {
		super(config);
	}

	@Override
	public void serialize() throws XMLStreamException {
		// TODO Auto-generated method stub
	}

	@Override
	public void writeStart() throws XMLStreamException {
		writer.writeStartDocument();
		
		writer.writeCharacters(eol);
		writer.writeStartElement("xmi", "XMI", xmiNs);
		writer.writeAttribute(xmiNs, "version", "2.1");
		writer.writeNamespace("uml", umlNs);
		writer.writeNamespace("xmi", xmiNs);
		writer.writeNamespace("cmof", cmofNs);

		writer.writeCharacters(eol);
		writer.writeStartElement(xmiNs, "Documentation");
		writer.writeAttribute("exporter", "Comarch C3Tax2XMI");
		writer.writeAttribute("exporterVersion", "0.1");
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}

	@Override
	public void writeEnd() throws XMLStreamException {
		writer.writeEndElement();
		writer.writeCharacters(eol);
		writer.writeEndDocument();
	}
	

}

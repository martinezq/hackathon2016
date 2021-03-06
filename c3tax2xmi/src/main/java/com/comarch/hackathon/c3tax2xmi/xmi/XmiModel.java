package com.comarch.hackathon.c3tax2xmi.xmi;

import javax.xml.stream.XMLStreamException;

public class XmiModel extends XmiObject {

	public XmiModel(GeneratorConfig config) {
		super(config);
	}

	@Override
	public void serialize() throws XMLStreamException {
	}

	@Override
	public void writeStart() throws XMLStreamException {
		// <uml:Model xmi:type="uml:Model" xmi:id="themodel" name="sample">
		writer.writeStartElement(umlNs, "Model");
		writer.writeAttribute(xmiNs, "type", "uml:Model");
		writer.writeAttribute(xmiNs, "id", generateUUID());
		writer.writeAttribute("name", "C3 Taxonomy Model");
		writeEol();
	}

	@Override
	public void writeEnd() throws XMLStreamException {
		writer.writeEndElement();
		writeEol();
	}

}

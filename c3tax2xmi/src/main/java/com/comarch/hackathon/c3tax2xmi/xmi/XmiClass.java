package com.comarch.hackathon.c3tax2xmi.xmi;

import javax.xml.stream.XMLStreamException;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class XmiClass extends XmiSubjectObject {
	
	public XmiClass(GeneratorConfig config, RdfSubject subject) {
		super(config, subject);
	}

	@Override
	public void serialize() throws XMLStreamException {
		writeStart();
		// writeProperty(UUID.randomUUID().toString(), "Title", "uml:Property");
		writeEnd();
	}
	
	public void writeStart() throws XMLStreamException {
		//<packagedElement xmi:type="uml:Class" name="Order" xmi:id="BOUML_0x1f498_4" visibility="package" isAbstract="false" >
		writer.writeStartElement("ownedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Class");
		writer.writeAttribute(xmiNs, "id", subject.getExportId());
		writer.writeAttribute("name", subject.getExportName());
		writer.writeAttribute("visibility", "package");
		
		writeSubjectDescription();
	}
	
	@Override
	public void writeEnd() throws XMLStreamException {
		super.writeEnd();
		writeDependencies();
	}

}

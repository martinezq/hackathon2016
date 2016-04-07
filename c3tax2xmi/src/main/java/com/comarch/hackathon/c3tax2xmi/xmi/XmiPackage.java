package com.comarch.hackathon.c3tax2xmi.xmi;

import javax.xml.stream.XMLStreamException;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class XmiPackage extends XmiSubjectObject {
	
	public XmiPackage(GeneratorConfig config, RdfSubject subject) {
		super(config, subject);
	}

	@Override
	public void serialize() throws XMLStreamException {
		writeStart();
		writeEnd();		
	}
	
	public void writeStart() throws XMLStreamException {
		//<packagedElement xmi:type="uml:Package" xmi:id="BOUML_0x81_22" name ="sample">
		writer.writeStartElement("packagedElement");
		writer.writeAttribute(xmiNs, "type", "uml:Package");
		writer.writeAttribute(xmiNs, "id", subject.getExportId());
		writer.writeAttribute("name", subject.getExportName());
		
		writeSubjectDescription();
	}	

	@Override
	public void writeEnd() throws XMLStreamException {
		super.writeEnd();
		writeDependencies();
	}
}

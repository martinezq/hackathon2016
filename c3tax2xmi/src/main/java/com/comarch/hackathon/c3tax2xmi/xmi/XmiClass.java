package com.comarch.hackathon.c3tax2xmi.xmi;

import java.util.List;
import java.util.Set;


import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class XmiClass extends XmiSubjectObject {
	
	public XmiClass(XMLStreamWriter writer, RdfSubject subject) {
		super(writer, subject);
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
		
		writeComment(XmiGeneratorUtil.link(subject.getExportName(), subject.getAbout()));
		writeTitledComment("Description", subject.getDescription());
		
		List<RdfSubject> refereces = subject.getReferecesByName("property:Is_referring_to");
		if (refereces != null) {
			StringBuilder sb = new StringBuilder();
			for (RdfSubject ref : refereces) {
				sb.append(XmiGeneratorUtil.link(ref.getLabel(), ref.getAbout()));
				sb.append("<br>");
			}
			writeTitledComment("References", sb.toString());
		}
	}
	
	@Override
	public void writeEnd() throws XMLStreamException {
		super.writeEnd();
		writeDependencies();
	}

	private void writeDependencies() throws XMLStreamException {
		Set<String> referenceNames = subject.getAllReferencesNames();
		
		for(String refName: referenceNames) {
			List<RdfSubject> list = subject.getReferecesByName(refName);
			String refDisplayName = refName.split(":")[1].replaceAll("_", " ");
			for(RdfSubject refSubject: list) {
				if(shouldWrite(refSubject)) {
					writeDependencyTo(refDisplayName, refSubject);
				}
			}
		}

	}

}

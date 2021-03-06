package com.comarch.hackathon.c3tax2xmi.xmi;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import com.comarch.hackathon.c3tax2xmi.model.RdfElement;
import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public abstract class XmiSubjectObject extends XmiObject {

	protected RdfSubject subject;
	
	public XmiSubjectObject(GeneratorConfig config, RdfSubject subject) {
		super(config);
		this.subject = subject;
	}

	protected void writeComment(String text) throws XMLStreamException {
		writeComment(text, subject.getExportId());
	}
	
	protected void writeTitledComment(String title, String text) throws XMLStreamException {
		if (text != null) {
			StringBuilder sb = new StringBuilder(text.length());
			sb.append("<b>");
			sb.append(title);
			sb.append("</b><br>");
			sb.append(text);
			sb.append("<p>");
			writeComment(sb.toString());
		}
	}
	
	protected void writeSubjectDescription() throws XMLStreamException {
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
		
		refereces = subject.getChildren();
		if (refereces != null && !refereces.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (RdfSubject ref : refereces) {
				sb.append(XmiGeneratorUtil.link(ref.getLabel(), ref.getAbout()));
				sb.append("<br>");
			}
			writeTitledComment("Children", sb.toString());
		}

		String subObjectsDescription = XmiGeneratorUtil.subObjectsDescription(subject);
		if (!subObjectsDescription.isEmpty()) {
			writeComment(subObjectsDescription);
		}
	}
	
	@Override
	public abstract void writeStart() throws XMLStreamException;
	
	@Override
	public void writeEnd() throws XMLStreamException {
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}
	
	@Override
	public void extensionStart() throws XMLStreamException {
		super.extensionStart();
		writer.writeAttribute(xmiNs, "idref", subject.getExportId());
		writer.writeAttribute(xmiNs, "type", "uml:Package");
		RdfElement modDate = subject.getFirstElement("swivt:wikiPageModificationDate");
		if (modDate != null && modDate.getValue() != null) {
			String time = "2016-01-02 00:00:00";
			writeEol();
			writer.writeStartElement("times");
			writer.writeAttribute("created", time);
			writer.writeAttribute("modified", time);
			writer.writeEndElement();
		}
	}
	
	public void writeDependencyFrom(String name, RdfSubject fromSubject) throws XMLStreamException {
		writeDependency(name, subject.getExportId(), fromSubject.getExportId());
	}
	
	public void writeDependencyTo(String name, RdfSubject toSubject) throws XMLStreamException {
		writeDependency(name, toSubject.getExportId(), subject.getExportId());
	}
	
	protected void writeDependencies() throws XMLStreamException {
		writeDependencies(subject);
	}
	
	protected void writeDependencies(RdfSubject subject) throws XMLStreamException {
		Set<String> referenceNames = subject.getAllReferencesNames();
		
		for (String refName: referenceNames) {
			List<RdfSubject> list = subject.getReferecesByName(refName);
			String refDisplayName = refName.split(":")[1].replaceAll("_", " ").toLowerCase();
			for (RdfSubject refSubject: list) {
				if (config.shouldWrite(refSubject)) {
					if(!refSubject.equals(this.subject)) {
						writeDependencyTo(refDisplayName, refSubject);
					}
				}
			}
		}
		
		Collection<RdfSubject> subObjects = subject.getReferecesByName("property:Has_subobject");
		
		if (subObjects != null) {
			for(RdfSubject subObject: subObjects) {
				writeDependencies(subObject);
			}
		}
	}
	

}

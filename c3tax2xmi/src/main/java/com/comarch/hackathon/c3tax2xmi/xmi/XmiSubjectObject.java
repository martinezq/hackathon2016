package com.comarch.hackathon.c3tax2xmi.xmi;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public abstract class XmiSubjectObject extends XmiObject {

	protected RdfSubject subject;
	
	public XmiSubjectObject(XMLStreamWriter writer, RdfSubject subject) {
		super(writer);
		this.subject = subject;
	}

	protected void writeComment(String text) throws XMLStreamException {
		writeComment(text, subject.getExportId());
	}
	
	protected void writeTitledComment(String title, String text) throws XMLStreamException {
		StringBuilder sb = new StringBuilder(text.length());
		sb.append("<b>");
		sb.append(title);
		sb.append("</b><br>");
		sb.append(text);
		sb.append("<p>");
		writeComment(sb.toString());
	}
	
	public abstract void writeStart() throws XMLStreamException;
	
	public void writeEnd() throws XMLStreamException {
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}
	
	public void writeDependencyFrom(String name, RdfSubject fromSubject) throws XMLStreamException {
		writeDependency(name, subject.getExportId(), fromSubject.getExportId());
	}
	
	public void writeDependencyTo(String name, RdfSubject toSubject) throws XMLStreamException {
		writeDependency(name, toSubject.getExportId(), subject.getExportId());
	}
}
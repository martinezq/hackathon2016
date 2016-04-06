package com.comarch.hackathon.c3tax2xmi.xmi;

import java.util.UUID;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public abstract class XmiObject {
	
	protected static final String xmiNs = "http://schema.omg.org/spec/XMI/2.1";
	protected static final String umlNs = "http://schema.omg.org/spec/UML/2.1";
	protected static final String cmofNs = "http://schema.omg.org/spec/MOF/2.0/cmof.xml";
	protected static final String eol = "\n";
	
	protected XMLStreamWriter writer;
	
	public XmiObject(XMLStreamWriter writer) {
		this.writer = writer;
	}
	
	public abstract void serialize() throws XMLStreamException;

	public void writeComment(String text, String refId) throws XMLStreamException {
/*
 * 				<ownedComment xmi:type="cmof:Comment" xmi:id="Actions-CompleteActions-ReadExtentAction-_ownedComment.0" annotatedElement="cf26a852-b286-45a7-b1bf-4507eb30ee96">
					<body>A read extent action is an action that retrieves the current
						instances of a classifier.</body>
				</ownedComment>
 */
		writer.writeStartElement("ownedComment");
		writer.writeAttribute(xmiNs, "type", "cmof:Comment");
		writer.writeAttribute(xmiNs, "id", generateUUID());
		writer.writeAttribute("annotatedElement", refId);
		
		writer.writeStartElement("body");
		writer.writeCharacters(text);
		writer.writeEndElement();
		
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}
	
	public  void writeDependency(String name, String fromRefId, String toRefId) throws XMLStreamException {
		// <packagedElement xmi:type="uml:Dependency" xmi:id="EAID_88D30676_83EA_4a1c_9A44_10FC146C0A0B" visibility="public" supplier="EAID_364D25D6_D6F9_48ee_BEA3_4293954709B9" client="EAID_29254430_1110_40dd_B1D4_90D189099EE7"/>
		writer.writeStartElement("packagedElement");
		
		writer.writeAttribute(xmiNs, "type", "uml:Dependency");
		writer.writeAttribute(xmiNs, "id", generateUUID());
		writer.writeAttribute("supplier", fromRefId);
		writer.writeAttribute("client", toRefId);
		writer.writeAttribute("name", name);
		
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}

	public  void writeProperty(String id, String name, String type) throws XMLStreamException {
		/*
		 * 	<ownedAttribute xmi:type="uml:Property" name="Amount" xmi:id="BOUML_0x1f518_1" visibility="protected">
				<type xmi:type="uml:Class" xmi:idref="BOUML_datatype_0"/>
			</ownedAttribute>
		 */
		writer.writeStartElement("ownedAttribute");
		
		writer.writeAttribute(xmiNs, "type", "uml:Property");
		writer.writeAttribute(xmiNs, "id", id);
		writer.writeAttribute("name", name);
		writer.writeAttribute("visibility", "public");
		
		writer.writeEndElement();
		writer.writeCharacters(eol);
	}
	
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
	
}

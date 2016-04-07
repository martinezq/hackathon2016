package com.comarch.hackathon.c3tax2xmi.xmi;

import java.util.Arrays;
import java.util.Collection;

import javax.xml.stream.XMLStreamWriter;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class GeneratorConfig {

	public static String[] DEFAULT_CATEGORIES = {
			"/Category-3ATaxonomy_Nodes", 
			"/Category-3AServices", 
			"/Category-3AApplications",
			"/Category-3ABusiness_Processes",
			"/Category-3AEquipment",
			"/Category-3AInformation_Products"
		};
	
	public XMLStreamWriter writer;
	
	private Collection<String> categoriesToExport = Arrays.asList(DEFAULT_CATEGORIES);

	public boolean shouldWrite(RdfSubject subject) {
		return subject.hasAnyType(categoriesToExport);
	}

	public Collection<String> getCategoriesToExport() {
		return categoriesToExport;
	}

	public void setCategoriesToExport(Collection<String> categoriesToExport) {
		this.categoriesToExport = categoriesToExport;
	}
	
}

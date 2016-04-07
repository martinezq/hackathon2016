package com.comarch.hackathon.c3tax2xmi.xmi;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.comarch.hackathon.c3tax2xmi.model.RdfElement;
import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;

public class XmiGeneratorUtil {

	public static String link(String name, String url) {
		return "<a href=\"" + url + "\">" + name + "</a>";
	}
	
	public static String subObjectsDescription(RdfSubject subject) {
		Map<String, List<RdfSubject>> subObjectGroups = new HashMap<String, List<RdfSubject>>();
		
		Collection<RdfSubject> subObjects = subject.getReferecesByName("property:Has_subobject");
		if (subObjects != null) {
			for(RdfSubject subObject: subObjects) {
				String typeGroup = subObject.getElementValue("property:Type");
				List<RdfSubject> list;
				if (!subObjectGroups.containsKey(typeGroup)) {
					list = new LinkedList<RdfSubject>();
					subObjectGroups.put(typeGroup, list);
				} else {
					list = subObjectGroups.get(typeGroup);
				}
				list.add(subObject);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		String[] groupsArray = subObjectGroups.keySet().toArray(new String[subObjectGroups.keySet().size()]); 
		Arrays.sort(groupsArray);
		
		for (String group: groupsArray) {
			List<RdfSubject> list = subObjectGroups.get(group);
			sb.append("<br>\n");
			sb.append("<b>" + group + "</b>:<br/>\n");
			
			for (RdfSubject subObject: list) {
				sb.append("  " + objectProperties(subObject));
				sb.append("<br/>");
			}
			
		}
		
		String result = sb.toString();
		
		if (result.length() < 0) {
			System.out.println(result);
			System.out.println("--");
		}
		
		return result;
	}
	
	public static String objectProperties(RdfSubject subject) {
		StringBuilder sb = new StringBuilder();
		
		for(RdfElement element: subject.getElements()) {
			String name = element.getName();
			String dataType = element.getDatatype();

			if(name.equalsIgnoreCase("swivt:wikiPageSortKey") ||
					name.equalsIgnoreCase("property:UUID") ||
					name.equalsIgnoreCase("property:Type")) {
				continue;
			}
			
			if(dataType != null && (dataType.endsWith("/XMLSchema#string") || dataType.endsWith("/XMLSchema#dateTime"))) {
				String label = element.getName().split(":")[1];
				label = label.replaceAll("_", " ");
				String value = element.getValue();
				
				value = value.replace("* ", "<br/>&nbsp;&nbsp;&nbsp;&nbsp;*&nbsp;");
				
				sb.append("&nbsp;&nbsp;<b>" + label + "</b>:&nbsp;<i>" + value + "</i><br/>");
			}

		}

		Set<String> refNames = subject.getAllReferencesNames();
		
		for(String refName: refNames) {
			if(refName.equalsIgnoreCase("swivt:masterPage")) {
				continue;
			}
			
			List<RdfSubject> list = subject.getReferecesByName(refName);
			String label = refName.split(":")[1].replaceAll("_", " ");
			
			for(RdfSubject refSubject: list) {
				sb.append("&nbsp;&nbsp;<b>" + label + "</b>:&nbsp;");
				sb.append(XmiGeneratorUtil.link(refSubject.getExportName(), refSubject.getAbout()));
				sb.append("<br/>");
			}
		}
	
		return sb.toString();
	}
}

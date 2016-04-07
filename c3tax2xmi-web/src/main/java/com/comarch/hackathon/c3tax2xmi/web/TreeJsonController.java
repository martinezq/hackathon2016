package com.comarch.hackathon.c3tax2xmi.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;
import com.comarch.hackathon.c3tax2xmi.web.service.ModelService;

@Controller
@RequestMapping("/tree")
public class TreeJsonController {

	@Autowired
	private ModelService modelService;

	@RequestMapping
	public void perform(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JSONArray result = create();
		resp.setContentType("application/json");
		result.write(resp.getWriter());
	}

	private JSONArray create() {
		Collection<RdfSubject> subjects = modelService.getSubjects();
		List<RdfSubject> roots = findRoots(subjects);

		JSONArray result = new JSONArray();

		for (RdfSubject root : roots) {
			result.put(create(root));
		}

		return result;
	}

	private JSONObject create(RdfSubject subject) {
		JSONObject obj = new JSONObject();

		obj.put("id", subject.getExportId());
		obj.put("name", subject.getExportName());
		obj.put("ref", subject.getAbout());
		
		Collection<String> categories = subject.getTypes();
		
		if(categories != null) {
			JSONArray categoriesArray = new JSONArray();
			for(String cat: categories) {
				String[] parts = cat.split("/");
				categoriesArray.put("/" + parts[parts.length - 1]);
			}
			
			obj.put("categories", categoriesArray);
		}
		

		if (!subject.getChildren().isEmpty()) {

			JSONArray children = new JSONArray();
			
			for (RdfSubject child : subject.getChildren()) {
				children.put(create(child));
			}
			
			obj.put("children", children);
		}
		
		return obj;
	}

	private static List<RdfSubject> findRoots(Collection<RdfSubject> subjects) {
		List<RdfSubject> roots = new ArrayList<>();
		for (RdfSubject subject : subjects) {
			if ("C3 Taxonomy".equals(subject.getLabel())) {
				System.out.println("Root " + subject.getId());
				roots.add(subject);
			}
		}
		System.out.println("Found roots: " + roots.size());
		return roots;
	}

}

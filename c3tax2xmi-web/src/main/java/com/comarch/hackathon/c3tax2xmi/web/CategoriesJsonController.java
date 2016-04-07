package com.comarch.hackathon.c3tax2xmi.web;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comarch.hackathon.c3tax2xmi.web.service.ModelService;

@Controller
@RequestMapping("/categories")
public class CategoriesJsonController {

	@Autowired
	private ModelService modelService;
	
	@RequestMapping
	public void perform(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JSONArray result = create();
		resp.setContentType("application/json");
		result.write(resp.getWriter());
	}
	
	private JSONArray create() {
		JSONArray array = new JSONArray();
		Set<String> categories = modelService.getCategories();
		
		for(String cat: categories) {
			JSONObject obj = new JSONObject();
			
			String[] parts = cat.split("/");
			String name = parts[parts.length - 1];
			name = name.replaceAll("3A", "").replaceAll("Category-", "").replaceAll("_", " ");
			
			obj.put("name", name);
			obj.put("id", "/" + parts[parts.length - 1]);
			obj.put("ref", cat);
			
			array.put(obj);
		}
		
		return array;
	}
	
}

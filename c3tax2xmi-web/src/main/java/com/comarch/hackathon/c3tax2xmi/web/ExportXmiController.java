package com.comarch.hackathon.c3tax2xmi.web;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comarch.hackathon.c3tax2xmi.XmiExporter;
import com.comarch.hackathon.c3tax2xmi.model.RdfSubject;
import com.comarch.hackathon.c3tax2xmi.web.service.ModelService;
import com.comarch.hackathon.c3tax2xmi.xmi.StaxXmiGenerator;

@Controller
@RequestMapping("/export")
public class ExportXmiController {

	@Autowired
	private ModelService modelService;
	
	Map<String, String> files = new HashMap<>();
	
	@RequestMapping(path = "/all", method=RequestMethod.GET)
	public void performAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/xml");
		
		StaxXmiGenerator generator = new StaxXmiGenerator();
		generator.setSubjects(modelService.getSubjects());
		//generator.setRoot(root);
		//generator.setCategoriesToExport(categoriesToExport);
		
		generator.write(resp.getWriter());
	}
	
	@RequestMapping(path = "/selected", method=RequestMethod.POST)
	public void performSelected(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		JSONTokener jsonTokener = new JSONTokener(req.getReader());
		
		JSONObject json = new JSONObject(jsonTokener);
		
		JSONArray subjects = json.getJSONArray("subjects");
		JSONArray categories = json.getJSONArray("categories");
		
		resp.setContentType("application/text");
		
		StaxXmiGenerator generator = new StaxXmiGenerator();
		generator.setSubjects(modelService.findSubjectsById(subjects.getListOfStrings()));
		generator.setCategoriesToExport(categories.getListOfStrings());
		generator.setRoot(modelService.getParser().getHandler().getRootElement());
		
		File temp = File.createTempFile("export", ".xml");
		FileWriter fw = new FileWriter(temp);
		
		generator.write(fw);
		
		String id = UUID.randomUUID().toString();
		
		files.put(id, temp.getAbsolutePath());
		
		resp.getWriter().write(id);
	}
	
	@RequestMapping(path = "/get/{id}", method=RequestMethod.GET)
	public void performget(@PathVariable("id") String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/xml");
		resp.addHeader("content-disposition", "attachment; filename=export.xmi");
		
		FileReader tempReader = new FileReader(files.get(id));
		
		IOUtils.copy(tempReader, resp.getWriter());
	}
	

	
	
}

package com.comarch.hackathon.c3tax2xmi.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comarch.hackathon.c3tax2xmi.web.service.ModelService;
import com.comarch.hackathon.c3tax2xmi.xmi.StaxXmiGenerator;

@Controller
@RequestMapping("/export")
public class ExportXmiController {

	@Autowired
	private ModelService modelService;
	
	@RequestMapping
	public void perform(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/xml");
		
		StaxXmiGenerator generator = new StaxXmiGenerator();
		generator.setSubjects(modelService.getSubjects());
		//generator.setRoot(root);
		//generator.setCategoriesToExport(categoriesToExport);
		
		generator.write(resp.getWriter());
	}
	
	
	
}

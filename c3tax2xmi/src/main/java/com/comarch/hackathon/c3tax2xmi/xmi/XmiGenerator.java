package com.comarch.hackathon.c3tax2xmi.xmi;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class XmiGenerator {
	
	private Model createModel(String name) {
		Model model = UMLFactory.eINSTANCE.createModel();
		model.setName(name);
		return model;
	}
	
	private Package createPackage(Package nestingPackage, String name) {
		Package pack = nestingPackage.createNestedPackage(name);
		return pack;
	}
	
	private Class createClass(Package pack, String name, boolean isAbstract) {
		Class cls = pack.createOwnedClass(name, isAbstract);
		return cls;
	}
	
	private void save(Package pack, URI uri) {
		Resource resource = new ResourceSetImpl().createResource(uri);
		if (resource == null) {
			System.err.println("Failed to create resource for " + uri);
			return;
		}
		resource.getContents().add(pack);
		try {
			resource.save(null);
			System.out.println("Saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generate() {
		Model model = createModel("Sample");
		Package main = createPackage(model, "C3 Taxonomy");
		createClass(main, "Test class", false);
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("xmi", new XMIResourceFactoryImpl());
		
		URI uri = URI.createURI("test").appendFileExtension("xmi");
		System.out.println("Saving to " + uri);
		save(model, uri);
	}
}

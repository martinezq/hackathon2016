package com.comarch.hackathon.c3tax2xmi;

import com.comarch.hackathon.c3tax2xmi.xmi.StaxXmiGenerator;
import com.comarch.hackathon.saxparser.C3TaxParser;
import java.io.File;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello Hackathon");
		boolean genXmi = false;
		boolean loadRdf = false;
		String rdf = null;
		String xmi = "out.xmi";
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch (arg) {
			case "--genxmi":
				genXmi = true;
				break;
			case "--loadrdf":
				if (i == args.length - 1) {
					System.err.println("Missing parameter for --loadrdf");
				} else {
					loadRdf = true;
					genXmi = true;
					i++;
					rdf = args[i];
				}
				break;
			case "--xmi":
				if (i == args.length - 1) {
					System.err.println("Missing parameter for --xmi");
				} else {
					genXmi = true;
					i++;
					xmi = args[i];
				}
				break;
			default:
				System.err.println("Invalid parameter: " + arg);
			}
		}
		if (loadRdf) {
			File file = new File(rdf);
			if (!file.exists()) {
				System.err.println("Cannot find RDF file " + rdf);
				return;
			}
			try {
				C3TaxParser parser = new C3TaxParser();
				parser.parse(file);
				//parser.getParsedElements();
				if (genXmi) {
					StaxXmiGenerator generator = new StaxXmiGenerator(parser.getParsedElements());
					generator.write(xmi);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

}

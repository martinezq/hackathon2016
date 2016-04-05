package com.comarch.hackathon.c3tax2xmi;

import com.comarch.hackathon.c3tax2xmi.xmi.XmiGenerator;
import com.comarch.hackathon.saxparser.C3TaxParser;
import java.io.File;

public class Main {


	public static void main(String[] args) {
		System.out.println("hello Hackathon");
		boolean genXmi = false;
        for (String arg : args) {
            switch (arg) {
                case "--genxmi":
                    genXmi = true;
                    break;
                default:
                    System.err.println("Invalid parameter: " + arg);
            }
        }
        if (genXmi) {
        	XmiGenerator gen = new XmiGenerator();
        	gen.generate();
        }
		


        
        try {
            C3TaxParser parser = new C3TaxParser();
            parser.parse(new File("d:\\Projekty\\Hackathon\\Codding Challange\\em_short.rdf"));
            parser.getParsedElements();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

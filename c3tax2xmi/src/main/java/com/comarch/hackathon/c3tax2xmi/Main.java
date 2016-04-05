package com.comarch.hackathon.c3tax2xmi;

import com.comarch.hackathon.c3tax2xmi.xmi.XmiGenerator;

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
		
	}

}

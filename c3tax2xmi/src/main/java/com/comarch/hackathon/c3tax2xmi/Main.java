package com.comarch.hackathon.c3tax2xmi;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

public class Main {
	
	private static final String OPT_OUT = "o";
	private static final String OPT_FILE = "f";
	private static final String OPT_URL = "w";
	private static final String OPT_BATCH = "b";
	private static final String OPT_USERNAME = "u";
	private static final String OPT_PASSWORD = "p";
	private static final String OPT_LIMIT = "l";

	public static void main(String[] args) throws ParseException, SAXException, IOException, ParserConfigurationException {
		System.out.println("Hackathon 2016 - Comarch RDF converter");
		Options options = new Options();        
		options.addOption(OPT_OUT, "out", true, "Output XMI file name");
        options.addOption(OPT_FILE, "file", true, "Load RDF from file");
        options.addOption(OPT_URL, "url", true, "Load RDF from url");
        options.addOption(OPT_BATCH, "batch", false, "Batch mode");
        options.addOption(OPT_USERNAME, "username", true, "Basic authentication username (optionally used with --url");
        options.addOption(OPT_PASSWORD, "password", true, "Basic authentication password (optionally used with --url");
        options.addOption(OPT_LIMIT, "limit", true, "Limit the number of generated objects");
        
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        XmiExporter exporter = new XmiExporter();
        
        if (cmd.hasOption(OPT_URL)) {
        	String url = cmd.getOptionValue(OPT_URL);
        	
            if (cmd.hasOption(OPT_USERNAME) && cmd.hasOption(OPT_PASSWORD)) {
            	exporter.loadFromUrl(url, cmd.getOptionValue(OPT_USERNAME), cmd.getOptionValue(OPT_PASSWORD));
            } else {
            	exporter.loadFromUrl(url);
            }
        } else if (cmd.hasOption(OPT_FILE)) {
            exporter.loadFromFile(cmd.getOptionValue("file"));
        } else if (cmd.hasOption(OPT_BATCH)) {
        	printHelp("Input not specified", options);
        	return;
        }
        
        if (cmd.hasOption(OPT_BATCH)) {
        	if (cmd.hasOption(OPT_OUT)) {
        		exporter.exportToFile(cmd.getOptionValue(OPT_OUT));
        	} else {
        		printHelp("Output file not specified", options);
            	return;
        	}
        }
        
        if (cmd.hasOption(OPT_LIMIT)) {
        	try {
        		int limit = Integer.parseInt(cmd.getOptionValue(OPT_LIMIT));
        		exporter.setLimit(limit);
        	} catch (NumberFormatException e) {
        		System.err.println("Invalid limit value (" + e.toString() + ")");
        		return;
        	}
        }
        
        // Start GUI here.

	}
	
	private static void printHelp(String msg, Options options) {
		System.err.println(msg);
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("options", options);
	}
}

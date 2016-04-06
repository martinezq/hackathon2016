package com.comarch.hackathon.c3tax2xmi;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main2 {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws ParseException {
		Options options = new Options();        
        
		options.addOption("out", true, "Output XMI file name");
        options.addOption("file", true, "Load RDF from file");
        options.addOption("url", true, "Load RDF from url");
        options.addOption("username", true, "Basic authentication username (optionally used with -url");
        options.addOption("password", true, "Basic authentication password (optionally used with -url");
        
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( options, args);
        
        if(!cmd.hasOption("out")) {
        	printHelp("out path not specified", options);
        	return;
        }
        
        XmiExporter exporter = new XmiExporter(cmd.getOptionValue("out"));
        
        if(cmd.hasOption("url")) {
        	String url = cmd.getOptionValue("url");
        	String username = cmd.getOptionValue("username");
        	String password = cmd.getOptionValue("password");
        	
            if(cmd.hasOption("username") && cmd.hasOption("password")) {
            	exporter.exportFromUrl(url, username, password);
            } else {
            	exporter.exportFromUrl(url);
            }
            
            return;
        }
        
        if(cmd.hasOption("file")) {
            exporter.exportFromFile(cmd.getOptionValue("file"));
            return;
        } 

        printHelp("Invalid use", options);
        
	}
	
	private static void printHelp(String msg, Options options) {
		System.err.println(msg);
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("options", options);
	}
}

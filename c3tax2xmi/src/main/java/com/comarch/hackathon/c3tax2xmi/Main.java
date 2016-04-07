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

import com.comarch.hackathon.c3tax2xmi.gui.ImportExportDialog;

public class Main {
	
	private static final String OPT_OUT = "o";
	private static final String OPT_FILE = "f";
	private static final String OPT_URL = "w";
	private static final String OPT_BATCH = "b";
	private static final String OPT_USERNAME = "u";
	private static final String OPT_PASSWORD = "p";
	private static final String OPT_LIMIT = "l";
	private static final String OPT_ROOT = "r";
	private static final String OPT_EXTENSIONS = "x";

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
        options.addOption(OPT_ROOT, "root", true, "Select root object");
        options.addOption(OPT_EXTENSIONS, "extensions", true, "Write extensions (ea, none)");
        
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
        
        if (cmd.hasOption(OPT_LIMIT)) {
        	try {
        		int limit = Integer.parseInt(cmd.getOptionValue(OPT_LIMIT));
        		exporter.setLimit(limit);
        	} catch (NumberFormatException e) {
        		System.err.println("Invalid limit value (" + e.toString() + ")");
        		return;
        	}
        }
        
        if (cmd.hasOption(OPT_ROOT)) {
        	exporter.setRoot(cmd.getOptionValue(OPT_ROOT));
        }
        
        if (cmd.hasOption(OPT_EXTENSIONS)) {
        	String ext = cmd.getOptionValue(OPT_EXTENSIONS);
        	if (ext.equals("ea")) {
        		exporter.setWriteExtensions(true);
        	} else if (ext.equals("none")) {
        		exporter.setWriteExtensions(false);
        	} else {
        		printHelp("Invalid value for extensions", options);
        		return;
        	}
        }
        
        if (cmd.hasOption(OPT_BATCH)) {
        	if (cmd.hasOption(OPT_OUT)) {
        		exporter.exportToFile(cmd.getOptionValue(OPT_OUT));
        	} else {
        		printHelp("Output file not specified", options);
            	return;
        	}
        }
        
        // Start GUI here.
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImportExportDialog dialog = new ImportExportDialog();
                dialog.setParser(exporter.getParser());
                if (cmd.hasOption(OPT_FILE)) {
                	dialog.getImportFilePathText().setText(cmd.getOptionValue(OPT_FILE));
                	dialog.refreshTree();
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });

	}
	
	private static void printHelp(String msg, Options options) {
		System.err.println(msg);
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("options", options);
	}
}

package com.comarch.hackathon.c3tax2xmi;

import java.io.File;
import java.io.FileOutputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class RdfDownloader {

	private String url;
	private String user;
	private String pass;
	
	public RdfDownloader(String url) {
		this.url = url;
	}

	public RdfDownloader(String url, String user, String pass) {
		this.url = url;
		this.user = user;
		this.pass = pass;
	}
	
	public File download() {
		try {
			if(user != null && pass != null) {
				System.out.println("Using basic authentication");
				Authenticator.setDefault (new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
				        return new PasswordAuthentication (user, pass.toCharArray());
				    }
				});
			}
			
			System.out.println("Downloading file from " + url);
			
			URL website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());

			File file = File.createTempFile("em_rdf_", Long.toString(System.nanoTime()));
			
			System.out.println("Writting file to " + file.getAbsolutePath() + ".xml");
			
			FileOutputStream fos = new FileOutputStream(file);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}

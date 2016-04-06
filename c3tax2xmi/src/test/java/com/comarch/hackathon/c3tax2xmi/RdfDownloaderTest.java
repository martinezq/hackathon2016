package com.comarch.hackathon.c3tax2xmi;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class RdfDownloaderTest {

	@Test
	public void testDownloader() {
		RdfDownloader downloader = new RdfDownloader("http://192.168.1.25/backups/em.rdf", "em", "em");
		File file = downloader.download();
		
		Assert.assertNotNull("File expected", file);
	}
	
}

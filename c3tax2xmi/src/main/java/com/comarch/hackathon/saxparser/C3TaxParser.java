package com.comarch.hackathon.saxparser;

import com.comarch.hackathon.saxparser.model.RdfSubject;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class C3TaxParser {
    
    private C3TaxHandler handler;
    
    public void parse(File file) throws SAXException, IOException, ParserConfigurationException {
        System.setProperty("jdk.xml.entityExpansionLimit", "0");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        //saxParser.setProperty("jdk.xml.entityExpansionLimit", value);

        long t = System.currentTimeMillis();
        System.out.println("START PARSING");
        
        handler = new C3TaxHandler();
        saxParser.parse(file, handler);
        
        System.out.println("END PARSING");
        System.out.println("TIME: " + (System.currentTimeMillis() - t) + "[ms]");
        System.out.println("TOTAL SUBJECTS: " + handler.getSubjects().size());
        
        t = System.currentTimeMillis();
        System.out.println("START FILLING REFERENCES");
        handler.fillReferences();
        System.out.println("END FILLING REFERENCES");
        System.out.println("TIME: " + (System.currentTimeMillis() - t) + "[ms]");
    }
    
    public Collection<RdfSubject> getParsedElements() {
        return handler.getSubjects();
    }

}

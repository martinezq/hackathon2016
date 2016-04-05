package com.comarch.hackathon.saxparser;

import com.comarch.hackathon.saxparser.model.RdfSubject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class C3TaxParser {

    private List<RdfSubject> subjects;
    
    public void parse(File file) throws SAXException, IOException, ParserConfigurationException {
        System.setProperty("jdk.xml.entityExpansionLimit", "0");

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        //saxParser.setProperty("jdk.xml.entityExpansionLimit", value);

        subjects = new ArrayList<>();
        DefaultHandler handler = new C3TaxHandler(subjects);
        
        saxParser.parse(file, handler);
    }
    
    public List<RdfSubject> getParsedElements() {
        return subjects;
    }

}

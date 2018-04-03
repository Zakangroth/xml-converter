package com.zakangroth.xmlconverter.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

@Service
public class XmlService {

    public void xmlToCsv() {

        // No proper error handling. Training purpose.
        try {
            File stylesheet = new File("src/main/resources/xsl/host-loads.xsl");
            File xmlSource = new File("src/main/resources/xml/host-loads.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlSource);

            StreamSource stylesource = new StreamSource(stylesheet);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
            Source source = new DOMSource(document);
            Result outputTarget = new StreamResult(new File("/tmp/x.csv"));
            transformer.transform(source, outputTarget);
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }
}

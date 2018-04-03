package com.zakangroth.xmlconverter.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;

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

    public void csvToXml(){
        ArrayList<String> hostData = new ArrayList<String>(7);

        File file = new File("/tmp/x.csv");
        BufferedReader readFile = null;
        try {
            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = df.newDocumentBuilder();

            Document document = db.newDocument();

            Element rootElement = document.createElement("Hosts");

            document.appendChild(rootElement);
            readFile = new BufferedReader(new FileReader(file));
            int line = 0;

            String information = null;
            while ((information = readFile.readLine()) != null) {

                String[] rowValues = information.split(",");

                if (line == 0) {
                    for (String columnInfo : rowValues) {
                        hostData.add(columnInfo);
                    }
                } else {
                    Element childElement = document.createElement("host");
                    rootElement.appendChild(childElement);


                    for (int columnInfo = 0; columnInfo < hostData.size(); columnInfo++) {

                        String header = hostData.get(columnInfo);
                        String value = null;

                        if (columnInfo < rowValues.length) {
                            value = rowValues[columnInfo];
                        } else {
                            value = " ";
                        }

                        Element current = document.createElement(header);
                        current.appendChild(document.createTextNode(value));
                        childElement.appendChild(current);
                    }
                }
                line++;
            }
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            Source source = new DOMSource(document);
            Result outputTarget = new StreamResult(new File("/tmp/x.xml"));
            tf.transform(source, outputTarget);

        } catch (Exception e) {

        }
    }
}

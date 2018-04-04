package com.zakangroth.xmlconverter.service;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlService {

    protected DocumentBuilderFactory domFactory = null;
    protected DocumentBuilder domBuilder = null;

    public XmlService() {
        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domBuilder = domFactory.newDocumentBuilder();
        } catch (FactoryConfigurationError | Exception exp) {
            System.err.println(exp.toString());
        }
    }

    public byte[] xmlToCsv(InputStream xmlFile, InputStream xslFile) {

        // No proper error handling. Training purpose.
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            StreamSource stylesource = new StreamSource(xslFile);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
            Source source = new DOMSource(document);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(source, result);

            String csvString = result.getWriter().toString();

            // Finally, send the response
            return csvString.getBytes(Charset.forName("UTF-8"));
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public byte[] csvToXml(InputStream csvFile, String delimiter) {

        try {
            Document newDoc = domBuilder.newDocument();
            // Root element
            Element rootElement = newDoc.createElement("XMLCreators");
            newDoc.appendChild(rootElement);

            //** Now using the OpenCSV **//
            CSVReader reader = new CSVReader(new InputStreamReader(csvFile), delimiter.charAt(0));
            String[] nextLine;
            int line = 0;
            List<String> headers = new ArrayList<String>(5);
            while ((nextLine = reader.readNext()) != null) {

                if (line == 0) { // Header row
                    for (String col : nextLine) {
                        headers.add(col);
                    }
                } else { // Data row
                    Element rowElement = newDoc.createElement("row");
                    rootElement.appendChild(rowElement);

                    int col = 0;
                    for (String value : nextLine) {
                        String header = headers.get(col);

                        Element curElement = newDoc.createElement(header);
                        curElement.appendChild(newDoc.createTextNode(value.trim()));
                        rowElement.appendChild(curElement);

                        col++;
                    }
                }
                line++;
            }
            //** End of CSV parsing**//

            try {

                TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
                aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
                aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                Source src = new DOMSource(newDoc);
                StreamResult result = new StreamResult(new StringWriter());
                aTransformer.transform(src, result);

                String xmlString = result.getWriter().toString();

                // Finally, send the response
                return xmlString.getBytes(Charset.forName("UTF-8"));

            } catch (Exception exp) {
                exp.printStackTrace();
            }

        } catch (Exception exp) {
            System.err.println(exp.toString());
        }
        return new byte[0];
    }
}

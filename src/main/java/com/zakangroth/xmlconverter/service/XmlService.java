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
import java.io.*;
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

    public void xmlToCsv(InputStream xmlFile, InputStream xslFile, String outputName) {

        // No proper error handling. Training purpose.
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            StreamSource stylesource = new StreamSource(xslFile);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);
            Source source = new DOMSource(document);
            Result outputTarget = new StreamResult(new File("/tmp/" + outputName + ".csv"));
            transformer.transform(source, outputTarget);
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public int csvToXml(InputStream csvFile, String xmlFileName, String delimiter) {

        int rowsCount = -1;
        BufferedReader csvReader;
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

            FileWriter writer = null;

            try {

                writer = new FileWriter(new File("/tmp/" + xmlFileName + ".xml"));

                TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
                aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
                aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                Source src = new DOMSource(newDoc);
                Result result = new StreamResult(writer);
                aTransformer.transform(src, result);

                writer.flush();

            } catch (Exception exp) {
                exp.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            }

            // Output to console for testing
            // Resultt result = new StreamResult(System.out);

        } catch (Exception exp) {
            System.err.println(exp.toString());
        }
        return rowsCount;
        // "XLM Document has been created" + rowsCount;
    }
}

package com.zakangroth.xmlconverter.controller;

import com.zakangroth.xmlconverter.service.XmlService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;


@RestController
public class XmlConverterController {

    @Autowired
    XmlService xmlService;

    @RequestMapping(value = {"xmlToCsv"})
    public void xmlToCsv(HttpServletRequest request, HttpServletResponse response) {

        try {
            Part xmlFilePart = request.getPart("xmlFile");
            Part xslFilePart = request.getPart("xslFile");

            InputStream xmlFile = xmlFilePart.getInputStream();
            InputStream xslFile = xslFilePart.getInputStream();

            String outputName = FilenameUtils.removeExtension(xmlFilePart.getSubmittedFileName());

            byte[] output = xmlService.xmlToCsv(xmlFile, xslFile);
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=" + outputName + ".csv");
            response.getOutputStream().write(output);

        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = {"csvToXml"})
    public void csvToXml(HttpServletRequest request, HttpServletResponse response) {

        try {
            Part csvFilePart = request.getPart("csvFile");

            InputStream csvFile = csvFilePart.getInputStream();

            String outputName = FilenameUtils.removeExtension(csvFilePart.getSubmittedFileName());

            byte[] output = xmlService.csvToXml(csvFile, ",");
            response.setContentType("text/xml");
            response.setHeader("Content-Disposition", "attachment;filename=" + outputName + ".xml");
            response.getOutputStream().write(output);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}

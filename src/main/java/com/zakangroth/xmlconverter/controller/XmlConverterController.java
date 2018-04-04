package com.zakangroth.xmlconverter.controller;

import com.zakangroth.xmlconverter.service.XmlService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;


@RestController
public class XmlConverterController {

    @Autowired
    XmlService xmlService;

    @RequestMapping(value = {"xmlToCsv"})
    public ModelAndView xmlToCsv(HttpServletRequest request) {

        try {
            Part xmlFilePart = request.getPart("xmlFile");
            Part xslFilePart = request.getPart("xslFile");

            InputStream xmlFile = xmlFilePart.getInputStream();
            InputStream xslFile = xslFilePart.getInputStream();

            String outputName = FilenameUtils.removeExtension(xmlFilePart.getSubmittedFileName());

            xmlService.xmlToCsv(xmlFile, xslFile, outputName);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = {"csvToXml"})
    public ModelAndView csvToXml(HttpServletRequest request) {

        try {
            Part csvFilePart = request.getPart("csvFile");

            InputStream csvFile = csvFilePart.getInputStream();

            String outputName = FilenameUtils.removeExtension(csvFilePart.getSubmittedFileName());

            xmlService.csvToXml(csvFile, outputName, ",");
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/home");
    }
}

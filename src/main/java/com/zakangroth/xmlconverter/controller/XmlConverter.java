package com.zakangroth.xmlconverter.controller;

import com.zakangroth.xmlconverter.service.XmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XmlConverter {

    @Autowired
    XmlService xmlService;

    @RequestMapping(value = {"xmlToCsv"})
    public void xmlToCsv() {
        xmlService.xmlToCsv();
    }

    @RequestMapping(value = {"csvToXml"})
    public void csvToXml() {
        xmlService.csvToXml();
    }
}

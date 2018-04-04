<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>XML Converter</title>
    </head>

    <body>
        <h1>Welcome to XML Converter</h1>

        <h3>Convert XML to CSV</h3>

        <form method="post" action="/xmlToCsv" enctype="multipart/form-data">
            Select XML file:<input type="file" name="xmlFile">
            <br/>
            Select XSL file:<input type="file" name="xslFile">
            <br/>
            <input type="submit" value="Convert"/>
        </form>

        <h3>Convert CSV to XML</h3>

        <form method="post" action="/csvToXml">
            <input type="submit" value="Convert"/>
        </form>
    </body>
</html>
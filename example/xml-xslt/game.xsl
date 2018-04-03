<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE xsl:stylesheet  [
        <!ENTITY nbsp   "&#160;">
        <!ENTITY copy   "&#169;">
        <!ENTITY reg    "&#174;">
        <!ENTITY trade  "&#8482;">
        <!ENTITY mdash  "&#8212;">
        <!ENTITY ldquo  "&#8220;">
        <!ENTITY rdquo  "&#8221;">
        <!ENTITY pound  "&#163;">
        <!ENTITY yen    "&#165;">
        <!ENTITY euro   "&#8364;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="utf-8" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
                doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                <title>Mise en forme avec XSLT</title>
            </head>

            <body>
                <table width="1000" border="1" cellspacing="0" cellpadding="0">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nom</th>
                        <th scope="col">Type</th>
                        <th scope="col">Price</th>
                    </tr>
                    <xsl:for-each select="test/game[name = 'Super Mario Galaxy' or name = 'Guild Wars']">
                        <tr>
                            <td>
                                <xsl:value-of select="@id"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>
                                <xsl:value-of select="type"/>
                            </td>
                            <td>
                                <xsl:value-of select="price"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <xsl:comment>
                    Testing conditional possession of game
                </xsl:comment>
                <xsl:for-each select="test/game">
                    <xsl:if test="name = 'Mario Sokoban'">
                        <p>I have Mario Sokoban !</p>
                    </xsl:if>
                    <xsl:if test="name = 'Metroid Prime'">
                        <p>I have Metroid Prime !</p>
                    </xsl:if>
                </xsl:for-each>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>


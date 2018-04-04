<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" indent="no"/>
    <xsl:template match="/">
        <xsl:text>ID,Author,Title,Genre,Price,Publish date,Description</xsl:text>
        <xsl:for-each select="//book">
            <xsl:value-of
                    select="concat('&#xA;', @id,',&quot;',author,'&quot;,&quot;',title,'&quot;,&quot;',genre,'&quot;,&quot;',price,'&quot;,&quot;',publish_date,'&quot;,&quot;',description,'&quot;')"/>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>


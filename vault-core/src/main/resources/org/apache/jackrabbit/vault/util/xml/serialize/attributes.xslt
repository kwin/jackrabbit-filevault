<?xml version="1.0"?>
<!-- https://stackoverflow.com/a/10817885 -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output version="1.0" encoding="UTF-8"/>
    <!-- for each element -->
    <xsl:template match="*">
    <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="name()"/><xsl:text>&#xd;</xsl:text>
    <xsl:for-each select="@*">
        <xsl:text>&#x9;</xsl:text><xsl:value-of select="concat(name(),'=' ,'&#x22;', . ,'&#x22;')" /><xsl:text>&#xd;</xsl:text>
    </xsl:for-each>
    <xsl:text disable-output-escaping="yes">&gt;&#xd;</xsl:text>
    <xsl:apply-templates/>
    <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="name()"/><xsl:text disable-output-escaping="yes">&gt;&#xd;</xsl:text>
</xsl:template>
</xsl:stylesheet>
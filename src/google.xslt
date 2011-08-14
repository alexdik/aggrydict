<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
		<head>
		<title>Insert title here</title>
		</head>
		<body>
			<h3><xsl:value-of select="./a/e[@class='array']/e[@class='array']/e[@type='string']"/></h3>
			<xsl:apply-templates select="./a/e[2]"/>
		</body>
		</html>
	</xsl:template>
	
	<xsl:template match="a/e">
		<xsl:apply-templates select="./e"/>
	</xsl:template>
	
	<xsl:template match="a/e/e">
		<h4><xsl:value-of select="./e[@type='string']"/></h4>
		<ul>
			<xsl:apply-templates select="./e[@class='array']/e"/>
		</ul>
	</xsl:template>
	
	<xsl:template match="a/e/e/e[@class='array']/e">
		<li>
			<xsl:value-of select="."/>
		</li>
	</xsl:template>
	
	<xsl:template match="*">
		<xsl:apply-templates />
	</xsl:template>
	<xsl:template match="text()" />
</xsl:stylesheet>
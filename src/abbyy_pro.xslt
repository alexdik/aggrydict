<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ns0="http://www.w3.org/1999/xhtml">
	<xsl:output method="xml" omit-xml-declaration="yes"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="//ns0:div[@class='suggests-translate' and @id='suggests']/ns0:ul[@class='suggests-list' and @id='ul']">
				<xsl:text>SuggestCard$</xsl:text>
				<xsl:apply-templates select="//ns0:div[@class='suggests-translate' and @id='suggests']/ns0:ul[@class='suggests-list' and @id='ul']"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<!-- <xsl:when test="ns0:html/ns0:body/ns0:div[@class='page']/ns0:div[@class='bd']/ns0:div[@class='b-general-content']/ns0:div[@class='b-search-results']/ns0:div[@class='data']/ns0:div[@class='b-left-panel']/ns0:div"> -->
					<xsl:when test="//ns0:div[@class='js-article-html'] or //ns0:div[@class='b-search-results']/ns0:div[@class='ao-u first']">
						<html>
						<head>
						</head>
						<body>
						<xsl:if test="//ns0:div[@class='js-article-html']/ns0:p[@class='P']/ns0:img[@class='transcription']/@src">
							<xsl:element name="img">
								<xsl:attribute name="src">http://lingvopro.abbyyonline.com<xsl:value-of select="//ns0:div[@class='js-article-html']/ns0:p[@class='P']/ns0:img[@class='transcription']/@src"/></xsl:attribute>
							</xsl:element>
						</xsl:if>
						<xsl:if test="//ns0:div[@class='js-article-html']">
							<xsl:apply-templates select="//ns0:div[@class='js-article-html']"/>
						</xsl:if>
						<xsl:if test="//ns0:div[@class='b-search-results']/ns0:div[@class='ao-u first']">
							<xsl:apply-templates select="//ns0:div[@class='b-search-results']/ns0:div[@class='ao-u first']"/>
						</xsl:if>
						</body>
						</html>
					</xsl:when>
					<xsl:otherwise>No word found</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- ABBYY Lingvo (En-Ru) --> 
	<xsl:template match="ns0:div[@class='js-article-html']">
		<xsl:if test="./ns0:p[@class='P1']/ns0:a or ./ns0:p[@class='P1']/ns0:span[@class='comment']">
			<h4>
				<xsl:value-of select="./ns0:span[@class='name-dictionaries']/ns0:a[@shape='rect']"/>
			</h4>
			<ul>	
				<xsl:apply-templates select="./ns0:p[(@class='P1' or @class='P2')]"/>
			</ul>
		</xsl:if>
	</xsl:template>

	<xsl:template match="ns0:p[@class='P1' or @class='P2']">
		<xsl:if test=".//*[@class='translation'] or ./ns0:span[@class='comment']">
			<li>
				<xsl:for-each select="./child::*">
					<xsl:value-of select="."/>
				</xsl:for-each>
			</li>
		</xsl:if>
	</xsl:template>
	
	<!-- ABBYY Lingvo (Ru-En) -->
	<xsl:template match="ns0:div[@class='b-search-results']/ns0:div[@class='ao-u first']">
		<h4>
			<xsl:value-of select=".//ns0:span[@class='tab']"/>
		</h4>
		<xsl:apply-templates select=".//ns0:div[@class='card block']"/>
	</xsl:template>
	
	<xsl:template match="ns0:div[@class='card block']">
		<ul>
			<xsl:apply-templates select="./ns0:ul[@class='list']/ns0:li[@class='list-item']/ns0:a"/>
		</ul>
	</xsl:template>
	
	<xsl:template match="ns0:ul[@class='list']/ns0:li[@class='list-item']/ns0:a">
		<li>
			<xsl:value-of select="."/>
		</li>
	</xsl:template>
	
	<!-- Generating suggested words list -->
	<xsl:template match="ns0:div[@class='suggests-translate' and @id='suggests']/ns0:ul[@class='suggests-list' and @id='ul']">
		<xsl:apply-templates select="./ns0:li[@class='list-item']"/>
	</xsl:template>
	
	<xsl:template match="ns0:ul[@class='suggests-list' and @id='ul']/ns0:li[@class='list-item']">
		<xsl:value-of select="./ns0:a"/><xsl:text>$</xsl:text>
	</xsl:template>
	
	<xsl:template match="*">
		<xsl:apply-templates />
	</xsl:template>
	<xsl:template match="text()" />
</xsl:stylesheet>
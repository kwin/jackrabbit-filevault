/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Sep 14, 2000:
//  Fixed problem with namespace handling. Contributed by
//  David Blondeau <blondeau@intalio.com>
// Sep 14, 2000:
//  Fixed serializer to report IO exception directly, instead at
//  the end of document processing.
//  Reported by Patrick Higgins <phiggins@transzap.com>
// Aug 21, 2000:
//  Fixed bug in startDocument not calling prepare.
//  Reported by Mikael Staldal <d96-mst-ingen-reklam@d.kth.se>
// Aug 21, 2000:
//  Added ability to omit DOCTYPE declaration.


package org.apache.jackrabbit.vault.util.xml.serialize;


import java.io.OutputStream;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * <b>ASF Note</b>: This class and dependencies were copied from the sun jdk1.5
 * source base. The original serializer is extended by a new OutputFormat
 * setting that allows to break the line for each attribute. additionally,
 * all attribute names are sorted alphabetically.
 * Some Features were removed to limit the number of dependent classes:
 * <ul>
 * <li>dom filter support</li>
 * <li>all text nodes as CDATA feature</li>
 * <li>skip attribute default values feature</li>
 * <li>entity node reference feature</li>
 * </ul>
 * <p>
 *  
 */
public class XMLSerializer implements ContentHandler {

    private final TransformerHandler transformerHandler;
    private final StreamResult result;


    /**
     * Constructs a new serializer that writes to the specified writer
     * using the specified output format. If <tt>format</tt> is null,
     * will use a default output format.
     *
     * <p>The specified writer will not be closed by this class.
     * 
     * @param writer The writer to use
     * 
     */
    public XMLSerializer(Writer writer, OutputFormat format) throws TransformerConfigurationException {
        this(new StreamResult(writer), format);
    }

    /**
     * Constructs a new serializer that writes to the specified output
     * stream using the specified output format. If <tt>format</tt>
     * is null, will use a default output format.
     * <p>The specified stream will not be closed by this class.
     * 
     * @param output The output stream to use
     */
    public XMLSerializer(OutputStream output, OutputFormat format) throws TransformerConfigurationException {
        this(new StreamResult(output), format);
    }

    /**
     * Constructs a new serializer that writes to the specified output
     * stream using the specified output format. If <tt>format</tt>
     * is null, will use a default output format.
     * <p>The specified stream will not be closed by this class.
     * 
     * @param output The output stream to use
     * @param format The output format to use, null for the default
     * @throws TransformerConfigurationException 
     */
    public XMLSerializer(StreamResult result, OutputFormat format) throws TransformerConfigurationException {
        if (format == null) {
            format = new OutputFormat();
        }
        SAXTransformerFactory fac = (SAXTransformerFactory)TransformerFactory.newInstance();
        transformerHandler = fac.newTransformerHandler(/*new StreamSource(this.getClass().getResourceAsStream("attributes.xslt")));// add xslt source*/);
        configureTransformer(transformerHandler.getTransformer(), format);
        transformerHandler.setResult(result);
        this.result = result;
    }

    private static void configureTransformer(Transformer transformer, OutputFormat format) {
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        //transformer.setOutputProperty(OutputKeys.STANDALONE, "yes"); // enforces new line after XML declaration (http://apache-xml-project.6118.n7.nabble.com/Output-a-new-line-after-the-XML-declaration-using-indent-yes-tp21021p21027.html)
        if (format.getIndentation() > 0) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(format.getIndentation()));
        }
        
        // how to add new lines between attributes https://stackoverflow.com/questions/8393370/use-xslt-to-add-newlines-after-attributes
    }
    @Override
    public void setDocumentLocator(Locator locator) {
        transformerHandler.setDocumentLocator(locator);
    }


    @Override
    public void startDocument() throws SAXException {
        transformerHandler.startDocument();
    }


    @Override
    public void endDocument() throws SAXException {
        transformerHandler.endDocument();
    }


    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        transformerHandler.startPrefixMapping(prefix, uri);
    }


    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        transformerHandler.endPrefixMapping(prefix);
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        transformerHandler.startElement(uri, localName, qName, atts);
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        transformerHandler.endElement(uri, localName, qName);
    }


    public void endElement(String tagName)
            throws SAXException {
        endElement(null, null, tagName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        transformerHandler.characters(ch, start, length);
    }


    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        transformerHandler.ignorableWhitespace(ch, start, length);
    }


    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        transformerHandler.processingInstruction(target, data);
    }


    @Override
    public void skippedEntity(String name) throws SAXException {
        transformerHandler.skippedEntity(name);
    }
}


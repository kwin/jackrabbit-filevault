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
package org.apache.jackrabbit.vault.util.xml.serialize;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

import org.apache.jackrabbit.vault.util.QNameComparator;

public class SortedAttributesXmlStreamReader extends StreamReaderDelegate implements AutoCloseable {

    
    private final class AttributeComparator implements Comparator<Attribute> {

        private final QNameComparator nameComparator;

        public AttributeComparator() {
            nameComparator = new QNameComparator();
        }

        @Override
        public int compare(Attribute o1, Attribute o2) {
            return nameComparator.compare(o1.getName(), o2.getName());
        }
    }
    private List<Attribute> attributes;
    
    public SortedAttributesXmlStreamReader(XMLStreamReader reader) {
        super(reader);
        attributes = null;
    }

    public List<Attribute> getAttributes() {
        if (attributes == null) {
            initAttributes();
        }
        return attributes;
    }
    private void initAttributes() {
        attributes = new LinkedList<>();
        for (int i=0; i<getAttributeCount(); i++) {
            Attribute attribute = new Attribute(getParent().getAttributeName(i), getParent().getAttributeValue(i), getParent().getAttributeType(i), getParent().isAttributeSpecified(i));
            attributes.add(attribute);
        }
        Collections.sort(attributes, new AttributeComparator());
    }

    private final class Attribute {
        private final QName name;
        private final String value;
        private final String type;
        private final boolean isSpecified;
        
        public Attribute(QName name, String value, String type, boolean isSpecified) {
            super();
            this.name = name;
            this.value = value;
            this.type = type;
            this.isSpecified = isSpecified;
        }

        public QName getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public String getType() {
            return type;
        }

        public boolean isSpecified() {
            return isSpecified;
        }
    }
    @Override
    public QName getAttributeName(int index) {
        return getAttributes().get(index).getName();
    }

    @Override
    public String getAttributePrefix(int index) {
        return getAttributes().get(index).getName().getPrefix();
    }

    @Override
    public String getAttributeNamespace(int index) {
        return getAttributes().get(index).getName().getNamespaceURI();
    }

    @Override
    public String getAttributeLocalName(int index) {
        return getAttributes().get(index).getName().getLocalPart();
    }

    @Override
    public String getAttributeType(int index) {
        return getAttributes().get(index).getType();
    }

    @Override
    public String getAttributeValue(int index) {
        return getAttributes().get(index).getValue();
    }

    @Override
    public boolean isAttributeSpecified(int index) {
        return getAttributes().get(index).isSpecified();
    }

    @Override
    public int next() throws XMLStreamException {
        int eventType = super.next();
        if (eventType == XMLStreamConstants.START_ELEMENT) {
            attributes = null;
        }
        return eventType;
    }
    
    
}

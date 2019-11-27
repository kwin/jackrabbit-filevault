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

public class OutputFormat {

    /**
     * The indentation level, or zero if no indentation
     * was requested.
     */
    private final int indentationSize; // number of spaces
    private final boolean splitAttributesByLineBreaks;
    private final int maxLineLength; // if 0 no limitation (TODO: currently not evaluated)

    public OutputFormat() {
        this(0, false);
    }

    public OutputFormat(int indentationSize, boolean indentAttributes) {
        this(indentationSize, indentAttributes, 72);
    }

    public OutputFormat(int indentationSize, boolean splitAttributesByLineBreaks, int maxLineLength) {
        super();
        this.indentationSize = indentationSize;
        this.splitAttributesByLineBreaks = splitAttributesByLineBreaks;
        this.maxLineLength = maxLineLength;
    }

    public String getIndent() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<indentationSize; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }

    public boolean isSplitAttributesByLineBreaks() {
        return splitAttributesByLineBreaks;
    }

    public int getMaxLineLength() {
        return maxLineLength;
    }
}

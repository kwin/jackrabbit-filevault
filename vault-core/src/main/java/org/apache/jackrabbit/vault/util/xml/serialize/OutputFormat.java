package org.apache.jackrabbit.vault.util.xml.serialize;

public class OutputFormat {

    /**
     * The indentation level, or zero if no indentation
     * was requested.
     */
    private final int indentationSize; // number of spaces
    private final boolean indentAttributes;
    private final int maxLineLength; // if 0 no limitation (TODO: currently not evaluated)

    public OutputFormat() {
        this(0, false);
    }

    public OutputFormat(int indentationSize, boolean indentAttributes) {
        this(indentationSize, indentAttributes, 72);
    }

    public OutputFormat(int indentationSize, boolean indentAttributes, int maxLineLength) {
        super();
        this.indentationSize = indentationSize;
        this.indentAttributes = indentAttributes;
        this.maxLineLength = maxLineLength;
    }

    public String getIndent() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<indentationSize; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }

    public boolean isIndentAttributes() {
        return indentAttributes;
    }
    
    
}

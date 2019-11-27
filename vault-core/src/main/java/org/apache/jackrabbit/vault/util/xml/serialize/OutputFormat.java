package org.apache.jackrabbit.vault.util.xml.serialize;

public class OutputFormat {

    /**
     * The indentation level, or zero if no indentation
     * was requested.
     */
    private final int indentation;

    public OutputFormat() {
        this(0);
    }
    
    public OutputFormat(int indentation) {
        super();
        this.indentation = indentation;
    }

    public int getIndentation() {
        return indentation;
    }
    
    
}

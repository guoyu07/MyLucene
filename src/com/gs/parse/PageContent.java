/**
 * 
 */
package com.gs.parse;



import org.htmlparser.Node;

/**
 * @author GaoShen
 * @packageName com.gs.parse
 */
public class PageContent {
	
    private StringBuffer textBuffer;
    private int number;
    private Node node;

    public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public StringBuffer getTextBuffer() {
        return textBuffer;
    }

    public void setTextBuffer(StringBuffer textBuffer) {
        this.textBuffer = textBuffer;
    }
}


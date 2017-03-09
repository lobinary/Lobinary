/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.xml.bind.helpers;

import java.net.URL;
import java.net.MalformedURLException;
import java.text.MessageFormat;

import javax.xml.bind.ValidationEventLocator;
import org.w3c.dom.Node;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

/**
 * Default implementation of the ValidationEventLocator interface.
 *
 * <p>
 * JAXB providers are allowed to use whatever class that implements
 * the ValidationEventLocator interface. This class is just provided for a
 * convenience.
 *
 * <p>
 *  ValidationEventLocator接口的默认实现。
 * 
 * <p>
 *  允许JAXB提供程序使用任何实现ValidationEventLocator接口的类。这个类只是为了方便。
 * 
 * 
 * @author <ul><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li></ul>
 * @see javax.xml.bind.Validator
 * @see javax.xml.bind.ValidationEventHandler
 * @see javax.xml.bind.ValidationEvent
 * @see javax.xml.bind.ValidationEventLocator
 * @since JAXB1.0
 */
public class ValidationEventLocatorImpl implements ValidationEventLocator
{
    /**
     * Creates an object with all fields unavailable.
     * <p>
     *  创建一个所有字段不可用的对象。
     * 
     */
    public ValidationEventLocatorImpl() {
    }

    /**
     * Constructs an object from an org.xml.sax.Locator.
     *
     * The object's ColumnNumber, LineNumber, and URL become available from the
     * values returned by the locator's getColumnNumber(), getLineNumber(), and
     * getSystemId() methods respectively. Node, Object, and Offset are not
     * available.
     *
     * <p>
     *  构造一个来自org.xml.sax.Locator的对象。
     * 
     *  对象的ColumnNumber,LineNumber和URL分别从定位器的getColumnNumber(),getLineNumber()和getSystemId()方法返回的值可用。
     * 节点,对象和偏移不可用。
     * 
     * 
     * @param loc the SAX Locator object that will be used to populate this
     * event locator.
     * @throws IllegalArgumentException if the Locator is null
     */
    public ValidationEventLocatorImpl( Locator loc ) {
        if( loc == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "loc" ) );
        }

        this.url = toURL(loc.getSystemId());
        this.columnNumber = loc.getColumnNumber();
        this.lineNumber = loc.getLineNumber();
    }

    /**
     * Constructs an object from the location information of a SAXParseException.
     *
     * The object's ColumnNumber, LineNumber, and URL become available from the
     * values returned by the locator's getColumnNumber(), getLineNumber(), and
     * getSystemId() methods respectively. Node, Object, and Offset are not
     * available.
     *
     * <p>
     *  根据SAXParseException的位置信息构造对象。
     * 
     *  对象的ColumnNumber,LineNumber和URL分别从定位器的getColumnNumber(),getLineNumber()和getSystemId()方法返回的值可用。
     * 节点,对象和偏移不可用。
     * 
     * 
     * @param e the SAXParseException object that will be used to populate this
     * event locator.
     * @throws IllegalArgumentException if the SAXParseException is null
     */
    public ValidationEventLocatorImpl( SAXParseException e ) {
        if( e == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "e" ) );
        }

        this.url = toURL(e.getSystemId());
        this.columnNumber = e.getColumnNumber();
        this.lineNumber = e.getLineNumber();
    }

    /**
     * Constructs an object that points to a DOM Node.
     *
     * The object's Node becomes available.  ColumnNumber, LineNumber, Object,
     * Offset, and URL are not available.
     *
     * <p>
     *  构造一个指向DOM节点的对象。
     * 
     *  对象的节点变得可用。 ColumnNumber,LineNumber,Object,Offset和URL不可用。
     * 
     * 
     * @param _node the DOM Node object that will be used to populate this
     * event locator.
     * @throws IllegalArgumentException if the Node is null
     */
    public ValidationEventLocatorImpl(Node _node) {
        if( _node == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "_node" ) );
        }

        this.node = _node;
    }

    /**
     * Constructs an object that points to a JAXB content object.
     *
     * The object's Object becomes available. ColumnNumber, LineNumber, Node,
     * Offset, and URL are not available.
     *
     * <p>
     *  构造指向JAXB内容对象的对象。
     * 
     *  对象的对象变为可用。 ColumnNumber,LineNumber,Node,Offset和URL不可用。
     * 
     * 
     * @param _object the Object that will be used to populate this
     * event locator.
     * @throws IllegalArgumentException if the Object is null
     */
    public ValidationEventLocatorImpl(Object _object) {
        if( _object == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "_object" ) );
        }

        this.object = _object;
    }

    /** Converts a system ID to an URL object. */
    private static URL toURL( String systemId ) {
        try {
            return new URL(systemId);
        } catch( MalformedURLException e ) {
            // TODO: how should we handle system id here?
            return null;    // for now
        }
    }

    private URL url = null;
    private int offset = -1;
    private int lineNumber = -1;
    private int columnNumber = -1;
    private Object object = null;
    private Node node = null;


    /**
    /* <p>
    /* 
     * @see javax.xml.bind.ValidationEventLocator#getURL()
     */
    public URL getURL() {
        return url;
    }

    /**
     * Set the URL field on this event locator.  Null values are allowed.
     *
     * <p>
     *  在此事件定位器上设置网址字段。允许空值。
     * 
     * 
     * @param _url the url
     */
    public void setURL( URL _url ) {
        this.url = _url;
    }

    /**
    /* <p>
    /* 
     * @see javax.xml.bind.ValidationEventLocator#getOffset()
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Set the offset field on this event locator.
     *
     * <p>
     *  在此事件定位器上设置偏移字段。
     * 
     * 
     * @param _offset the offset
     */
    public void setOffset( int _offset ) {
        this.offset = _offset;
    }

    /**
    /* <p>
    /* 
     * @see javax.xml.bind.ValidationEventLocator#getLineNumber()
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Set the lineNumber field on this event locator.
     *
     * <p>
     *  在此事件定位器上设置lineNumber字段。
     * 
     * 
     * @param _lineNumber the line number
     */
    public void setLineNumber( int _lineNumber ) {
        this.lineNumber = _lineNumber;
    }

    /**
    /* <p>
    /* 
     * @see javax.xml.bind.ValidationEventLocator#getColumnNumber()
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * Set the columnNumber field on this event locator.
     *
     * <p>
     * 在此事件定位器上设置columnNumber字段。
     * 
     * 
     * @param _columnNumber the column number
     */
    public void setColumnNumber( int _columnNumber ) {
        this.columnNumber = _columnNumber;
    }

    /**
    /* <p>
    /* 
     * @see javax.xml.bind.ValidationEventLocator#getObject()
     */
    public Object getObject() {
        return object;
    }

    /**
     * Set the Object field on this event locator.  Null values are allowed.
     *
     * <p>
     *  在此事件定位器上设置对象字段。允许空值。
     * 
     * 
     * @param _object the java content object
     */
    public void setObject( Object _object ) {
        this.object = _object;
    }

    /**
    /* <p>
    /* 
     * @see javax.xml.bind.ValidationEventLocator#getNode()
     */
    public Node getNode() {
        return node;
    }

    /**
     * Set the Node field on this event locator.  Null values are allowed.
     *
     * <p>
     *  在此事件定位器上设置节点字段。允许空值。
     * 
     * 
     * @param _node the Node
     */
    public void setNode( Node _node ) {
        this.node = _node;
    }

    /**
     * Returns a string representation of this object in a format
     * helpful to debugging.
     *
     * <p>
     *  以有助于调试的格式返回此对象的字符串表示形式。
     * 
     * @see Object#equals(Object)
     */
    public String toString() {
        return MessageFormat.format("[node={0},object={1},url={2},line={3},col={4},offset={5}]",
            getNode(),
            getObject(),
            getURL(),
            String.valueOf(getLineNumber()),
            String.valueOf(getColumnNumber()),
            String.valueOf(getOffset()));
    }
}

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

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Partial default <tt>Unmarshaller</tt> implementation.
 *
 * <p>
 * This class provides a partial default implementation for the
 * {@link javax.xml.bind.Unmarshaller}interface.
 *
 * <p>
 * A JAXB Provider has to implement five methods (getUnmarshallerHandler,
 * unmarshal(Node), unmarshal(XMLReader,InputSource),
 * unmarshal(XMLStreamReader), and unmarshal(XMLEventReader).
 *
 * <p>
 *  部分默认<tt>取消合并器</tt>实施。
 * 
 * <p>
 *  此类为{@link javax.xml.bind.Unmarshaller}接口提供了部分默认实现。
 * 
 * <p>
 *  JAXB提供程序必须实现五个方法(getUnmarshallerHandler,unmarshal(Node),unmarshal(XMLReader,InputSource),unmarshal(X
 * MLStreamReader)和unmarshal(XMLEventReader)。
 * 
 * 
 * @author <ul>
 *         <li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li>
 *         </ul>
 * @see javax.xml.bind.Unmarshaller
 * @since JAXB1.0
 */
public abstract class AbstractUnmarshallerImpl implements Unmarshaller
{
    /** handler that will be used to process errors and warnings during unmarshal */
    private ValidationEventHandler eventHandler =
        new DefaultValidationEventHandler();

    /** whether or not the unmarshaller will validate */
    protected boolean validating = false;

    /**
     * XMLReader that will be used to parse a document.
     * <p>
     *  将用于解析文档的XMLReader。
     * 
     */
    private XMLReader reader = null;

    /**
     * Obtains a configured XMLReader.
     *
     * This method is used when the client-specified
     * {@link SAXSource} object doesn't have XMLReader.
     *
     * {@link Unmarshaller} is not re-entrant, so we will
     * only use one instance of XMLReader.
     * <p>
     *  获取已配置的XMLReader。
     * 
     *  当客户指定的{@link SAXSource}对象没有XMLReader时,使用此方法。
     * 
     *  {@link Unmarshaller}不是重入的,所以我们将只使用XMLReader的一个实例。
     * 
     */
    protected XMLReader getXMLReader() throws JAXBException {
        if(reader==null) {
            try {
                SAXParserFactory parserFactory;
                parserFactory = SAXParserFactory.newInstance();
                parserFactory.setNamespaceAware(true);
                // there is no point in asking a validation because
                // there is no guarantee that the document will come with
                // a proper schemaLocation.
                parserFactory.setValidating(false);
                reader = parserFactory.newSAXParser().getXMLReader();
            } catch( ParserConfigurationException e ) {
                throw new JAXBException(e);
            } catch( SAXException e ) {
                throw new JAXBException(e);
            }
        }
        return reader;
    }

    public Object unmarshal( Source source ) throws JAXBException {
        if( source == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "source" ) );
        }

        if(source instanceof SAXSource)
            return unmarshal( (SAXSource)source );
        if(source instanceof StreamSource)
            return unmarshal( streamSourceToInputSource((StreamSource)source));
        if(source instanceof DOMSource)
            return unmarshal( ((DOMSource)source).getNode() );

        // we don't handle other types of Source
        throw new IllegalArgumentException();
    }

    // use the client specified XMLReader contained in the SAXSource.
    private Object unmarshal( SAXSource source ) throws JAXBException {

        XMLReader r = source.getXMLReader();
        if( r == null )
            r = getXMLReader();

        return unmarshal( r, source.getInputSource() );
    }

    /**
     * Unmarshals an object by using the specified XMLReader and the InputSource.
     *
     * The callee should call the setErrorHandler method of the XMLReader
     * so that errors are passed to the client-specified ValidationEventHandler.
     * <p>
     *  通过使用指定的XMLReader和InputSource来取消编组对象。
     * 
     *  被调用者应该调用XMLReader的setErrorHandler方法,以便将错误传递给客户端指定的ValidationEventHandler。
     * 
     */
    protected abstract Object unmarshal( XMLReader reader, InputSource source ) throws JAXBException;

    public final Object unmarshal( InputSource source ) throws JAXBException {
        if( source == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "source" ) );
        }

        return unmarshal( getXMLReader(), source );
    }


    private Object unmarshal( String url ) throws JAXBException {
        return unmarshal( new InputSource(url) );
    }

    public final Object unmarshal( URL url ) throws JAXBException {
        if( url == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "url" ) );
        }

        return unmarshal( url.toExternalForm() );
    }

    public final Object unmarshal( File f ) throws JAXBException {
        if( f == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "file" ) );
        }

        try {
            // copied from JAXP
            String path = f.getAbsolutePath();
            if (File.separatorChar != '/')
                path = path.replace(File.separatorChar, '/');
            if (!path.startsWith("/"))
                path = "/" + path;
            if (!path.endsWith("/") && f.isDirectory())
                path = path + "/";
            return unmarshal(new URL("file", "", path));
        } catch( MalformedURLException e ) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public final Object unmarshal( java.io.InputStream is )
        throws JAXBException {

        if( is == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "is" ) );
        }

        InputSource isrc = new InputSource( is );
        return unmarshal( isrc );
    }

    public final Object unmarshal( Reader reader ) throws JAXBException {
        if( reader == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "reader" ) );
        }

        InputSource isrc = new InputSource( reader );
        return unmarshal( isrc );
    }


    private static InputSource streamSourceToInputSource( StreamSource ss ) {
        InputSource is = new InputSource();
        is.setSystemId( ss.getSystemId() );
        is.setByteStream( ss.getInputStream() );
        is.setCharacterStream( ss.getReader() );

        return is;
    }


    /**
     * Indicates whether or not the Unmarshaller is configured to validate
     * during unmarshal operations.
     * <p>
     * <i><b>Note:</b> I named this method isValidating() to stay in-line
     * with JAXP, as opposed to naming it getValidating(). </i>
     *
     * <p>
     *  指示Unmarshaller是否配置为在取消编组操作期间进行验证。
     * <p>
     *  <i> <b>注意：</b>我命名这个方法isValidating()与JAXP保持一致,而不是命名为getValidating()。 </i>
     * 
     * 
     * @return true if the Unmarshaller is configured to validate during
     *        unmarshal operations, false otherwise
     * @throws JAXBException if an error occurs while retrieving the validating
     *        flag
     */
    public boolean isValidating() throws JAXBException {
        return validating;
    }

    /**
     * Allow an application to register a validation event handler.
     * <p>
     * The validation event handler will be called by the JAXB Provider if any
     * validation errors are encountered during calls to any of the
     * <tt>unmarshal</tt> methods.  If the client application does not register
     * a validation event handler before invoking the unmarshal methods, then
     * all validation events will be silently ignored and may result in
     * unexpected behaviour.
     *
     * <p>
     *  允许应用程序注册验证事件处理程序。
     * <p>
     * 如果在调用任何<tt> unmarshal </tt>方法时遇到任何验证错误,JAXB提供程序将调用验证事件处理程序。
     * 如果客户端应用程序在调用解组方法之前未注册验证事件处理程序,则所有验证事件将被静默忽略,并可能导致意外的行为。
     * 
     * 
     * @param handler the validation event handler
     * @throws JAXBException if an error was encountered while setting the
     *        event handler
     */
    public void setEventHandler(ValidationEventHandler handler)
        throws JAXBException {

        if( handler == null ) {
            eventHandler = new DefaultValidationEventHandler();
        } else {
            eventHandler = handler;
        }
    }

    /**
     * Specifies whether or not the Unmarshaller should validate during
     * unmarshal operations.  By default, the <tt>Unmarshaller</tt> does
     * not validate.
     * <p>
     * This method may only be invoked before or after calling one of the
     * unmarshal methods.
     *
     * <p>
     *  指定Unmarshaller在解组操作期间是否应验证。默认情况下,<tt>取消绑定器</tt>不验证。
     * <p>
     *  此方法只能在调用其中一个解组方法之前或之后调用。
     * 
     * 
     * @param validating true if the Unmarshaller should validate during
     *       unmarshal, false otherwise
     * @throws JAXBException if an error occurred while enabling or disabling
     * validation at unmarshal time
     */
    public void setValidating(boolean validating) throws JAXBException {
        this.validating = validating;
    }

    /**
     * Return the current event handler or the default event handler if one
     * hasn't been set.
     *
     * <p>
     *  返回当前事件处理程序或默认事件处理程序(如果尚未设置)。
     * 
     * 
     * @return the current ValidationEventHandler or the default event handler
     *        if it hasn't been set
     * @throws JAXBException if an error was encountered while getting the
     *        current event handler
     */
    public ValidationEventHandler getEventHandler() throws JAXBException {
        return eventHandler;
    }


    /**
     * Creates an UnmarshalException from a SAXException.
     *
     * This is an utility method provided for the derived classes.
     *
     * <p>
     * When a provider-implemented ContentHandler wants to throw a
     * JAXBException, it needs to wrap the exception by a SAXException.
     * If the unmarshaller implementation blindly wrap SAXException
     * by JAXBException, such an exception will be a JAXBException
     * wrapped by a SAXException wrapped by another JAXBException.
     * This is silly.
     *
     * <p>
     * This method checks the nested exception of SAXException
     * and reduce those excessive wrapping.
     *
     * <p>
     *  从SAXException创建UnmarshalException。
     * 
     *  这是为派生类提供的实用程序方法。
     * 
     * <p>
     *  当提供者实现的ContentHandler想要抛出一个JAXBException时,它需要通过SAXException包装异常。
     * 如果unmarshaller实现盲目地通过JAXBException包装SAXException,这样的异常将是由另一个JAXBException包装的SAXException包装的JAXBExcept
     * ion。
     *  当提供者实现的ContentHandler想要抛出一个JAXBException时,它需要通过SAXException包装异常。这是愚蠢的。
     * 
     * 
     * @return the resulting UnmarshalException
     */
    protected UnmarshalException createUnmarshalException( SAXException e ) {
        // check the nested exception to see if it's an UnmarshalException
        Exception nested = e.getException();
        if(nested instanceof UnmarshalException)
            return (UnmarshalException)nested;

        if(nested instanceof RuntimeException)
            // typically this is an unexpected exception,
            // just throw it rather than wrap it, so that the full stack
            // trace can be displayed.
            throw (RuntimeException)nested;


        // otherwise simply wrap it
        if(nested!=null)
            return new UnmarshalException(nested);
        else
            return new UnmarshalException(e);
    }

    /**
     * Default implementation of the setProperty method always
     * throws PropertyException since there are no required
     * properties. If a provider needs to handle additional
     * properties, it should override this method in a derived class.
     * <p>
     * <p>
     *  此方法检查SAXException的嵌套异常,并减少这些过度包装。
     * 
     */
    public void setProperty( String name, Object value )
        throws PropertyException {

        if( name == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "name" ) );
        }

        throw new PropertyException(name, value);
    }

    /**
     * Default implementation of the getProperty method always
     * throws PropertyException since there are no required
     * properties. If a provider needs to handle additional
     * properties, it should override this method in a derived class.
     * <p>
     *  setProperty方法的默认实现总是抛出PropertyException,因为没有必需的属性。如果提供程序需要处理其他属性,它应该在派生类中覆盖此方法。
     * 
     */
    public Object getProperty( String name )
        throws PropertyException {

        if( name == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.MUST_NOT_BE_NULL, "name" ) );
        }

        throw new PropertyException(name);
    }

    public Object unmarshal(XMLEventReader reader) throws JAXBException {

        throw new UnsupportedOperationException();
    }

    public Object unmarshal(XMLStreamReader reader) throws JAXBException {

        throw new UnsupportedOperationException();
    }

    public <T> JAXBElement<T> unmarshal(Node node, Class<T> expectedType) throws JAXBException {
        throw new UnsupportedOperationException();
    }

    public <T> JAXBElement<T> unmarshal(Source source, Class<T> expectedType) throws JAXBException {
        throw new UnsupportedOperationException();
    }

    public <T> JAXBElement<T> unmarshal(XMLStreamReader reader, Class<T> expectedType) throws JAXBException {
        throw new UnsupportedOperationException();
    }

    public <T> JAXBElement<T> unmarshal(XMLEventReader reader, Class<T> expectedType) throws JAXBException {
        throw new UnsupportedOperationException();
    }

    public void setSchema(Schema schema) {
        throw new UnsupportedOperationException();
    }

    public Schema getSchema() {
        throw new UnsupportedOperationException();
    }

    public void setAdapter(XmlAdapter adapter) {
        if(adapter==null)
            throw new IllegalArgumentException();
        setAdapter((Class)adapter.getClass(),adapter);
    }

    public <A extends XmlAdapter> void setAdapter(Class<A> type, A adapter) {
        throw new UnsupportedOperationException();
    }

    public <A extends XmlAdapter> A getAdapter(Class<A> type) {
        throw new UnsupportedOperationException();
    }

    public void setAttachmentUnmarshaller(AttachmentUnmarshaller au) {
        throw new UnsupportedOperationException();
    }

    public AttachmentUnmarshaller getAttachmentUnmarshaller() {
        throw new UnsupportedOperationException();
    }

    public void setListener(Listener listener) {
        throw new UnsupportedOperationException();
    }

    public Listener getListener() {
        throw new UnsupportedOperationException();
    }
}

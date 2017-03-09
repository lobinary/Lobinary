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

package javax.xml.bind;

import org.xml.sax.ContentHandler;

/**
 * Unmarshaller implemented as SAX ContentHandler.
 *
 * <p>
 * Applications can use this interface to use their JAXB provider as a component
 * in an XML pipeline.  For example:
 *
 * <pre>
 *       JAXBContext context = JAXBContext.newInstance( "org.acme.foo" );
 *
 *       Unmarshaller unmarshaller = context.createUnmarshaller();
 *
 *       UnmarshallerHandler unmarshallerHandler = unmarshaller.getUnmarshallerHandler();
 *
 *       SAXParserFactory spf = SAXParserFactory.newInstance();
 *       spf.setNamespaceAware( true );
 *
 *       XMLReader xmlReader = spf.newSAXParser().getXMLReader();
 *       xmlReader.setContentHandler( unmarshallerHandler );
 *       xmlReader.parse(new InputSource( new FileInputStream( XML_FILE ) ) );
 *
 *       MyObject myObject= (MyObject)unmarshallerHandler.getResult();
 * </pre>
 *
 * <p>
 * This interface is reusable: even if the user fails to unmarshal
 * an object, s/he can still start a new round of unmarshalling.
 *
 * <p>
 *  Unmarshaller实现为SAX ContentHandler。
 * 
 * <p>
 *  应用程序可以使用此接口将其JAXB提供程序用作XML管道中的组件。例如：
 * 
 * <pre>
 *  JAXBContext context = JAXBContext.newInstance("org.acme.foo");
 * 
 *  Unmarshaller unmarshaller = context.createUnmarshaller();
 * 
 *  UnmarshallerHandler unmarshallerHandler = unmarshaller.getUnmarshallerHandler();
 * 
 *  SAXParserFactory spf = SAXParserFactory.newInstance(); spf.setNamespaceAware(true);
 * 
 *  XMLReader xmlReader = spf.newSAXParser()。
 * 
 * @author <ul><li>Kohsuke KAWAGUCHI, Sun Microsystems, Inc.</li></ul>
 * @see Unmarshaller#getUnmarshallerHandler()
 * @since JAXB1.0
 */
public interface UnmarshallerHandler extends ContentHandler
{
    /**
     * Obtains the unmarshalled result.
     *
     * This method can be called only after this handler
     * receives the endDocument SAX event.
     *
     * <p>
     * getXMLReader(); xmlReader.setContentHandler(unmarshallerHandler); xmlReader.parse(new InputSource(new
     *  FileInputStream(XML_FILE)));。
     *  XMLReader xmlReader = spf.newSAXParser()。
     * 
     *  MyObject myObject =(MyObject)unmarshallerHandler.getResult();
     * </pre>
     * 
     * <p>
     * 
     * @exception IllegalStateException
     *      if this method is called before this handler
     *      receives the endDocument event.
     *
     * @exception JAXBException
     *      if there is any unmarshalling error.
     *      Note that the implementation is allowed to throw SAXException
     *      during the parsing when it finds an error.
     *
     * @return
     *      always return a non-null valid object which was unmarshalled.
     */
    Object getResult() throws JAXBException, IllegalStateException;
}

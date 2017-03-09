/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import javax.xml.transform.Source;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.activation.DataHandler;

/**
 * Disable consideration of XOP encoding for datatypes that are bound to
 * base64-encoded binary data in XML.
 *
 * <p>
 * When XOP encoding is enabled as described in {@link AttachmentMarshaller#isXOPPackage()}, this annotation disables datatypes such as {@link java.awt.Image} or {@link Source} or <tt>byte[]</tt> that are bound to base64-encoded binary from being considered for
 * XOP encoding. If a JAXB property is annotated with this annotation or if
 * the JAXB property's base type is annotated with this annotation,
 * neither
 * {@link AttachmentMarshaller#addMtomAttachment(DataHandler, String, String)}
 * nor
 * {@link AttachmentMarshaller#addMtomAttachment(byte[], int, int, String, String, String)} is
 * ever called for the property. The binary data will always be inlined.
 *
 * <p>
 *  禁止考虑以XML格式绑定到base64编码的二进制数据的数据类型的XOP编码。
 * 
 * <p>
 *  当按照{@link AttachmentMarshaller#isXOPPackage()}中所述启用XOP编码时,此注释禁用数据类型,例如{@link java.awt.Image}或{@link Source}
 * 或<tt> byte [] </tt>它们被绑定到base64编码的二进制被考虑用于XOP编码。
 * 
 * @author Joseph Fialli
 * @since JAXB2.0
 */
@Retention(RUNTIME)
@Target({FIELD,METHOD,TYPE})
public @interface XmlInlineBinaryData {
}

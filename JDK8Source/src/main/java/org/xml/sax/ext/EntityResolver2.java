/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
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

// EntityResolver2.java - Extended SAX entity resolver.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: EntityResolver2.java,v 1.2 2004/11/03 22:49:08 jsuttor Exp $

package org.xml.sax.ext;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;


/**
 * Extended interface for mapping external entity references to input
 * sources, or providing a missing external subset.  The
 * {@link XMLReader#setEntityResolver XMLReader.setEntityResolver()} method
 * is used to provide implementations of this interface to parsers.
 * When a parser uses the methods in this interface, the
 * {@link EntityResolver2#resolveEntity EntityResolver2.resolveEntity()}
 * method (in this interface) is used <em>instead of</em> the older (SAX 1.0)
 * {@link EntityResolver#resolveEntity EntityResolver.resolveEntity()} method.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * </blockquote>
 *
 * <p>If a SAX application requires the customized handling which this
 * interface defines for external entities, it must ensure that it uses
 * an XMLReader with the
 * <em>http://xml.org/sax/features/use-entity-resolver2</em> feature flag
 * set to <em>true</em> (which is its default value when the feature is
 * recognized).  If that flag is unrecognized, or its value is false,
 * or the resolver does not implement this interface, then only the
 * {@link EntityResolver} method will be used.
 * </p>
 *
 * <p>That supports three categories of application that modify entity
 * resolution.  <em>Old Style</em> applications won't know about this interface;
 * they will provide an EntityResolver.
 * <em>Transitional Mode</em> provide an EntityResolver2 and automatically
 * get the benefit of its methods in any systems (parsers or other tools)
 * supporting it, due to polymorphism.
 * Both <em>Old Style</em> and <em>Transitional Mode</em> applications will
 * work with any SAX2 parser.
 * <em>New style</em> applications will fail to run except on SAX2 parsers
 * that support this particular feature.
 * They will insist that feature flag have a value of "true", and the
 * EntityResolver2 implementation they provide  might throw an exception
 * if the original SAX 1.0 style entity resolution method is invoked.
 * </p>
 *
 * <p>
 *  扩展接口,用于将外部实体引用映射到输入源,或提供缺少的外部子集。
 *  {@link XMLReader#setEntityResolver XMLReader.setEntityResolver()}方法用于向解析器提供此接口的实现。
 * 当解析器使用此接口中的方法时,将使用{@link EntityResolver2#resolveEntity EntityResolver2.resolveEntity()}方法(在此接口中)<em>而
 * 不是</em>旧的(SAX 1.0){@link EntityResolver #resolveEntity EntityResolver.resolveEntity()}方法。
 *  {@link XMLReader#setEntityResolver XMLReader.setEntityResolver()}方法用于向解析器提供此接口的实现。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)位于公共域中,并且随附<strong>无保修</strong>。</em>
 * </blockquote>
 * 
 *  <p>如果SAX应用程序需要此界面为外部实体定义的自定义处理,则必须确保它使用具有<em> http://xml.org/sax/features/use-entity-resolver2 < / em>
 * 功能标志设置为<em> true </em>(识别功能时为默认值)。
 * 如果该标志不可识别,或其值为false,或解析器不实现此接口,则将仅使用{@link EntityResolver}方法。
 * </p>
 * 
 * <p>支持修改实体分辨率的三类应用程序。 <em>旧样式</em>应用程序将不知道此界面;他们将提供一个EntityResolver。
 *  <em>过渡模式</em>提供EntityResolver2,并且由于多态性而在支持它的任何系统(解析器或其他工具)中自动获得其方法的好处。
 *  <em>旧样式</em>和<em>过渡模式</em>应用程序将与任何SAX2解析器一起使用。 <em>新样式</em>应用程序将无法运行,除了支持此特定功能的SAX2解析器。
 * 他们将坚持特征标志具有值"true",如果调用原始的SAX 1.0样式实体解析方法,它们提供的EntityResolver2实现可能会抛出异常。
 * </p>
 * 
 * 
 * @see org.xml.sax.XMLReader#setEntityResolver
 *
 * @since SAX 2.0 (extensions 1.1 alpha)
 * @author David Brownell
 */
public interface EntityResolver2 extends EntityResolver
{
    /**
     * Allows applications to provide an external subset for documents
     * that don't explicitly define one.  Documents with DOCTYPE declarations
     * that omit an external subset can thus augment the declarations
     * available for validation, entity processing, and attribute processing
     * (normalization, defaulting, and reporting types including ID).
     * This augmentation is reported
     * through the {@link LexicalHandler#startDTD startDTD()} method as if
     * the document text had originally included the external subset;
     * this callback is made before any internal subset data or errors
     * are reported.</p>
     *
     * <p>This method can also be used with documents that have no DOCTYPE
     * declaration.  When the root element is encountered,
     * but no DOCTYPE declaration has been seen, this method is
     * invoked.  If it returns a value for the external subset, that root
     * element is declared to be the root element, giving the effect of
     * splicing a DOCTYPE declaration at the end the prolog of a document
     * that could not otherwise be valid.  The sequence of parser callbacks
     * in that case logically resembles this:</p>
     *
     * <pre>
     * ... comments and PIs from the prolog (as usual)
     * startDTD ("rootName", source.getPublicId (), source.getSystemId ());
     * startEntity ("[dtd]");
     * ... declarations, comments, and PIs from the external subset
     * endEntity ("[dtd]");
     * endDTD ();
     * ... then the rest of the document (as usual)
     * startElement (..., "rootName", ...);
     * </pre>
     *
     * <p>Note that the InputSource gets no further resolution.
     * Implementations of this method may wish to invoke
     * {@link #resolveEntity resolveEntity()} to gain benefits such as use
     * of local caches of DTD entities.  Also, this method will never be
     * used by a (non-validating) processor that is not including external
     * parameter entities. </p>
     *
     * <p>Uses for this method include facilitating data validation when
     * interoperating with XML processors that would always require
     * undesirable network accesses for external entities, or which for
     * other reasons adopt a "no DTDs" policy.
     * Non-validation motives include forcing documents to include DTDs so
     * that attributes are handled consistently.
     * For example, an XPath processor needs to know which attibutes have
     * type "ID" before it can process a widely used type of reference.</p>
     *
     * <p><strong>Warning:</strong> Returning an external subset modifies
     * the input document.  By providing definitions for general entities,
     * it can make a malformed document appear to be well formed.
     * </p>
     *
     * <p>
     *  允许应用程序为未明确定义文档的文档提供外部子集。具有省略外部子集的DOCTYPE声明的文档因此可以增加可用于验证,实体处理和属性处理(规范化,默认以及包括ID的报告类型)的声明。
     * 通过{@link LexicalHandler#startDTD startDTD()}方法报告此增强,就像文档文本最初包含外部子集一样;此回调在报告任何内部子集数据或错误之前进行。</p>。
     * 
     * <p>此方法也可用于没有DOCTYPE声明的文档。当遇到根元素,但没有看到DOCTYPE声明时,将调用此方法。
     * 如果它为外部子集返回一个值,那么该根元素被声明为根元素,从而在文档的序言结束时拼接DOCTYPE声明的效果不能以其他方式有效。在这种情况下,解析器回调的顺序在逻辑上类似于：</p>。
     * 
     * <pre>
     *  ...注释和来自prolog的PI(像往常一样)startDTD("rootName",source.getPublicId(),source.getSystemId()); startEntity(
     * "[dtd]"); ...来自外部子集的声明,注释和PI endEntity("[dtd]"); endDTD(); ...然后文档的其余部分(像往常一样)startElement(...,"rootN
     * ame",...);。
     * </pre>
     * 
     *  <p>请注意,InputSource没有得到进一步的解析。
     * 此方法的实现可能希望调用{@link #resolveEntity resolveEntity()}以获得诸如使用DTD实体的本地高速缓存的益处。
     * 此外,此方法将永远不会被不包括外部参数实体的(非验证)处理器使用。 </p>。
     * 
     * <p>此方法的使用包括在与将对外部实体总是需要不期望的网络访问的XML处理器互操作时促进数据验证,或者出于其他原因采用"无DTD"策略。非验证动机包括强制文档包括DTD,以便一致地处理属性。
     * 例如,XPath处理器需要知道哪些属性具有类型"ID",然后才能处理广泛使用的引用类型。</p>。
     * 
     * @param name Identifies the document root element.  This name comes
     *  from a DOCTYPE declaration (where available) or from the actual
     *  root element.
     * @param baseURI The document's base URI, serving as an additional
     *  hint for selecting the external subset.  This is always an absolute
     *  URI, unless it is null because the XMLReader was given an InputSource
     *  without one.
     *
     * @return An InputSource object describing the new external subset
     *  to be used by the parser, or null to indicate that no external
     *  subset is provided.
     *
     * @exception SAXException Any SAX exception, possibly wrapping
     *  another exception.
     * @exception IOException Probably indicating a failure to create
     *  a new InputStream or Reader, or an illegal URL.
     */
    public InputSource getExternalSubset (String name, String baseURI)
    throws SAXException, IOException;

    /**
     * Allows applications to map references to external entities into input
     * sources, or tell the parser it should use conventional URI resolution.
     * This method is only called for external entities which have been
     * properly declared.
     * This method provides more flexibility than the {@link EntityResolver}
     * interface, supporting implementations of more complex catalogue
     * schemes such as the one defined by the <a href=
        "http://www.oasis-open.org/committees/entity/spec-2001-08-06.html"
        >OASIS XML Catalogs</a> specification.</p>
     *
     * <p>Parsers configured to use this resolver method will call it
     * to determine the input source to use for any external entity
     * being included because of a reference in the XML text.
     * That excludes the document entity, and any external entity returned
     * by {@link #getExternalSubset getExternalSubset()}.
     * When a (non-validating) processor is configured not to include
     * a class of entities (parameter or general) through use of feature
     * flags, this method is not invoked for such entities.  </p>
     *
     * <p>Note that the entity naming scheme used here is the same one
     * used in the {@link LexicalHandler}, or in the {@link
        org.xml.sax.ContentHandler#skippedEntity
        ContentHandler.skippedEntity()}
     * method. </p>
     *
     * <p>
     * 
     *  <p> <strong>警告：</strong>返回外部子集将修改输入文档。通过提供一般实体的定义,它可以使得畸形文档看起来形成良好。
     * </p>
     * 
     * 
     * @param name Identifies the external entity being resolved.
     *  Either "[dtd]" for the external subset, or a name starting
     *  with "%" to indicate a parameter entity, or else the name of
     *  a general entity.  This is never null when invoked by a SAX2
     *  parser.
     * @param publicId The public identifier of the external entity being
     *  referenced (normalized as required by the XML specification), or
     *  null if none was supplied.
     * @param baseURI The URI with respect to which relative systemIDs
     *  are interpreted.  This is always an absolute URI, unless it is
     *  null (likely because the XMLReader was given an InputSource without
     *  one).  This URI is defined by the XML specification to be the one
     *  associated with the "&lt;" starting the relevant declaration.
     * @param systemId The system identifier of the external entity
     *  being referenced; either a relative or absolute URI.
     *  This is never null when invoked by a SAX2 parser; only declared
     *  entities, and any external subset, are resolved by such parsers.
     *
     * @return An InputSource object describing the new input source to
     *  be used by the parser.  Returning null directs the parser to
     *  resolve the system ID against the base URI and open a connection
     *  to resulting URI.
     *
     * @exception SAXException Any SAX exception, possibly wrapping
     *  another exception.
     * @exception IOException Probably indicating a failure to create
     *  a new InputStream or Reader, or an illegal URL.
     */
    public InputSource resolveEntity (
            String name,
            String publicId,
            String baseURI,
            String systemId
    ) throws SAXException, IOException;
}

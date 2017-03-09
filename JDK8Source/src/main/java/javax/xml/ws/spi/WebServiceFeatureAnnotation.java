/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws.spi;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.WebServiceRefs;
import javax.xml.ws.RespectBinding;
import javax.xml.ws.soap.Addressing;
import javax.xml.ws.soap.MTOM;

/**
 * Annotation used to identify other annotations
 * as a <code>WebServiceFeature</code>.
 * <p>
 * Each <code>WebServiceFeature</code> annotation annotated with
 * this annotation MUST contain an
 * <code>enabled</code> property of type
 * <code>boolean</code> with a default value of <code>true</code>.
 * <p>
 * JAX-WS defines the following
 * <code>WebServiceFeature</code> annotations (<code>Addressing</code>,
 * <code>MTOM</code>, <code>RespectBinding</code>), however, an implementation
 * may define vendors specific annotations for other features.
 * <p>
 * Annotations annotated with <code>WebServiceFeatureAnnotation</code> MUST
 * have the same @Target of {@link WebServiceRef} annotation, so that the resulting
 * feature annotation can be used in conjunction with the {@link WebServiceRef}
 * annotation if necessary.
 * <p>
 * If a JAX-WS implementation encounters an annotation annotated
 * with the <code>WebServiceFeatureAnnotation</code> that it does not
 * recognize/support an error MUST be given.
 * <p>
 *
 * <p>
 *  用于将其他注释标识为<code> WebServiceFeature </code>的注释。
 * <p>
 *  使用此注释批注的每个<code> WebServiceFeature </code>注释必须包含默认值为<code> true </code>的<code> boolean </code>类型的<code>
 *  enabled </code>属性。
 * <p>
 *  JAX-WS定义了以下<code> WebServiceFeature </code>注释(<code> Addressing </code>,<code> MTOM </code>,<code> R
 * espectBinding </code>),供应商特定注释其他功能。
 * <p>
 *  使用<code> WebServiceFeatureAnnotation </code>注释的注释必须具有{@link WebServiceRef}注释的@Target,以便生成的特征注释可以与{@link WebServiceRef}
 * 注释结合使用(如有必要)。
 * 
 * @see Addressing
 * @see MTOM
 * @see RespectBinding
 *
 * @since JAX-WS 2.1
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServiceFeatureAnnotation {
    /**
     * Unique identifier for the WebServiceFeature.  This
     * identifier MUST be unique across all implementations
     * of JAX-WS.
     * <p>
     * <p>
     *  如果JAX-WS实现遇到用<code> WebServiceFeatureAnnotation </code>注释的注释,它不能识别/支持错误。
     * <p>
     * 
     */
    String id();

    /**
     * The <code>WebServiceFeature</code> bean that is associated
     * with the <code>WebServiceFeature</code> annotation
     * <p>
     *  WebServiceFeature的唯一标识符。该标识符在JAX-WS的所有实现中必须是唯一的。
     * 
     */
    Class<? extends WebServiceFeature> bean();
}

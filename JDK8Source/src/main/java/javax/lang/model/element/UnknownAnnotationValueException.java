/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2009, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model.element;

import javax.lang.model.UnknownEntityException;

/**
 * Indicates that an unknown kind of annotation value was encountered.
 * This can occur if the language evolves and new kinds of annotation
 * values can be stored in an annotation.  May be thrown by an
 * {@linkplain AnnotationValueVisitor annotation value visitor} to
 * indicate that the visitor was created for a prior version of the
 * language.
 *
 * <p>
 *  表示遇到未知类型的注释值。如果语言演变并且新的注释值可以存储在注释中,则可能发生这种情况。
 * 可能会被{@linkplain AnnotationValueVisitor注释值访问者}抛出,表明访问者是为该语言的先前版本创建的。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see AnnotationValueVisitor#visitUnknown
 * @since 1.6
 */
public class UnknownAnnotationValueException extends UnknownEntityException {

    private static final long serialVersionUID = 269L;

    private transient AnnotationValue av;
    private transient Object parameter;

    /**
     * Creates a new {@code UnknownAnnotationValueException}.  The
     * {@code p} parameter may be used to pass in an additional
     * argument with information about the context in which the
     * unknown annotation value was encountered; for example, the
     * visit methods of {@link AnnotationValueVisitor} may pass in
     * their additional parameter.
     *
     * <p>
     *  创建新的{@code UnknownAnnotationValueException}。
     *  {@code p}参数可用于传递附加参数,其中包含遇到未知注释值的上下文的信息;例如,{@link AnnotationValueVisitor}的访问方法可能传递其附加参数。
     * 
     * 
     * @param av the unknown annotation value, may be {@code null}
     * @param p an additional parameter, may be {@code null}
     */
    public UnknownAnnotationValueException(AnnotationValue av, Object p) {
        super("Unknown annotation value: " + av);
        this.av = av;
        this.parameter = p;
    }

    /**
     * Returns the unknown annotation value.
     * The value may be unavailable if this exception has been
     * serialized and then read back in.
     *
     * <p>
     *  返回未知注释值。如果此异常已序列化,然后读回,该值可能不可用。
     * 
     * 
     * @return the unknown element, or {@code null} if unavailable
     */
    public AnnotationValue getUnknownAnnotationValue() {
        return av;
    }

    /**
     * Returns the additional argument.
     *
     * <p>
     *  返回附加参数。
     * 
     * @return the additional argument
     */
    public Object getArgument() {
        return parameter;
    }
}

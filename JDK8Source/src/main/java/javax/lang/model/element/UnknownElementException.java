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
 * Indicates that an unknown kind of element was encountered.  This
 * can occur if the language evolves and new kinds of elements are
 * added to the {@code Element} hierarchy.  May be thrown by an
 * {@linkplain ElementVisitor element visitor} to indicate that the
 * visitor was created for a prior version of the language.
 *
 * <p>
 *  表示遇到未知类型的元素。如果语言演变,并且将新种类的元素添加到{@code Element}层次结构中,则会发生这种情况。
 * 可能由{@linkplain ElementVisitor元素visitor}抛出,表示该访问者是为该语言的先前版本创建的。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see ElementVisitor#visitUnknown
 * @since 1.6
 */
public class UnknownElementException extends UnknownEntityException {

    private static final long serialVersionUID = 269L;

    private transient Element element;
    private transient Object parameter;

    /**
     * Creates a new {@code UnknownElementException}.  The {@code p}
     * parameter may be used to pass in an additional argument with
     * information about the context in which the unknown element was
     * encountered; for example, the visit methods of {@link
     * ElementVisitor} may pass in their additional parameter.
     *
     * <p>
     *  创建新的{@code UnknownElementException}。
     *  {@code p}参数可用于传递一个附加参数,其中包含遇到未知元素的上下文信息;例如,{@link ElementVisitor}的访问方法可能传递其附加参数。
     * 
     * 
     * @param e the unknown element, may be {@code null}
     * @param p an additional parameter, may be {@code null}
     */
    public UnknownElementException(Element e, Object p) {
        super("Unknown element: " + e);
        element = e;
        this.parameter = p;
    }

    /**
     * Returns the unknown element.
     * The value may be unavailable if this exception has been
     * serialized and then read back in.
     *
     * <p>
     *  返回未知元素。如果此异常已序列化,然后读回,该值可能不可用。
     * 
     * 
     * @return the unknown element, or {@code null} if unavailable
     */
    public Element getUnknownElement() {
        return element;
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

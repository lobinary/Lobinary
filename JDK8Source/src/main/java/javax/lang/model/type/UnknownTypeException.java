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

package javax.lang.model.type;

import javax.lang.model.UnknownEntityException;

/**
 * Indicates that an unknown kind of type was encountered.  This can
 * occur if the language evolves and new kinds of types are added to
 * the {@code TypeMirror} hierarchy.  May be thrown by a {@linkplain
 * TypeVisitor type visitor} to indicate that the visitor was created
 * for a prior version of the language.
 *
 * <p>
 *  表示遇到未知类型的类型。如果语言发展并且将新类型的类型添加到{@code TypeMirror}层次结构中,就会发生这种情况。
 * 可能被{@linkplain TypeVisitor类型访问者}抛出,表示访问者是为该语言的先前版本创建的。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see TypeVisitor#visitUnknown
 * @since 1.6
 */
public class UnknownTypeException extends UnknownEntityException {

    private static final long serialVersionUID = 269L;

    private transient TypeMirror type;
    private transient Object parameter;

    /**
     * Creates a new {@code UnknownTypeException}.The {@code p}
     * parameter may be used to pass in an additional argument with
     * information about the context in which the unknown type was
     * encountered; for example, the visit methods of {@link
     * TypeVisitor} may pass in their additional parameter.
     *
     * <p>
     *  创建一个新的{@code UnknownTypeException}。
     * {@code p}参数可用于传递一个附加参数,其中包含遇到未知类型的上下文的信息;例如,{@link TypeVisitor}的访问方法可能传递其附加参数。
     * 
     * 
     * @param t the unknown type, may be {@code null}
     * @param p an additional parameter, may be {@code null}
     */
    public UnknownTypeException(TypeMirror t, Object p) {
        super("Unknown type: " + t);
        type = t;
        this.parameter = p;
    }

    /**
     * Returns the unknown type.
     * The value may be unavailable if this exception has been
     * serialized and then read back in.
     *
     * <p>
     *  返回未知类型。如果此异常已序列化,然后读回,该值可能不可用。
     * 
     * 
     * @return the unknown type, or {@code null} if unavailable
     */
    public TypeMirror getUnknownType() {
        return type;
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

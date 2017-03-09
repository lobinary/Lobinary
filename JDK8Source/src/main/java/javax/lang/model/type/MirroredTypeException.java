/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.io.ObjectInputStream;
import java.io.IOException;
import javax.lang.model.element.Element;


/**
 * Thrown when an application attempts to access the {@link Class} object
 * corresponding to a {@link TypeMirror}.
 *
 * <p>
 *  当应用程序尝试访问与{@link TypeMirror}对应的{@link Class}对象时抛出。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see MirroredTypesException
 * @see Element#getAnnotation(Class)
 * @since 1.6
 */
public class MirroredTypeException extends MirroredTypesException {

    private static final long serialVersionUID = 269;

    private transient TypeMirror type;          // cannot be serialized

    /**
     * Constructs a new MirroredTypeException for the specified type.
     *
     * <p>
     *  为指定类型构造新的MirroredTypeException。
     * 
     * 
     * @param type  the type being accessed
     */
    public MirroredTypeException(TypeMirror type) {
        super("Attempt to access Class object for TypeMirror " + type.toString(), type);
        this.type = type;
    }

    /**
     * Returns the type mirror corresponding to the type being accessed.
     * The type mirror may be unavailable if this exception has been
     * serialized and then read back in.
     *
     * <p>
     *  返回与正在访问的类型相对应的类型镜像。如果此异常已序列化,然后读回,则类型镜像可能不可用。
     * 
     * 
     * @return the type mirror, or {@code null} if unavailable
     */
    public TypeMirror getTypeMirror() {
        return type;
    }

    /**
     * Explicitly set all transient fields.
     * <p>
     *  显式设置所有瞬态字段。
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        type = null;
        types = null;
    }
}

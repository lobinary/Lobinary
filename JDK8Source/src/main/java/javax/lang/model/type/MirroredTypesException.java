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

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.ObjectInputStream;
import java.io.IOException;
import javax.lang.model.element.Element;


/**
 * Thrown when an application attempts to access a sequence of {@link
 * Class} objects each corresponding to a {@link TypeMirror}.
 *
 * <p>
 *  当应用程序尝试访问{@link Class}对象序列(每个对应于{@link TypeMirror})时抛出。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see MirroredTypeException
 * @see Element#getAnnotation(Class)
 * @since 1.6
 */
public class MirroredTypesException extends RuntimeException {

    private static final long serialVersionUID = 269;

    transient List<? extends TypeMirror> types; // cannot be serialized

    /*
     * Trusted constructor to be called by MirroredTypeException.
     * <p>
     *  受MirroredTypeException调用的可信构造函数。
     * 
     */
    MirroredTypesException(String message, TypeMirror type) {
        super(message);
        List<TypeMirror> tmp = (new ArrayList<TypeMirror>());
        tmp.add(type);
        types = Collections.unmodifiableList(tmp);
    }

    /**
     * Constructs a new MirroredTypesException for the specified types.
     *
     * <p>
     *  为指定的类型构造新的MirroredTypesException。
     * 
     * 
     * @param types  the types being accessed
     */
    public MirroredTypesException(List<? extends TypeMirror> types) {
        super("Attempt to access Class objects for TypeMirrors " +
              (types = // defensive copy
               new ArrayList<TypeMirror>(types)).toString() );
        this.types = Collections.unmodifiableList(types);
    }

    /**
     * Returns the type mirrors corresponding to the types being accessed.
     * The type mirrors may be unavailable if this exception has been
     * serialized and then read back in.
     *
     * <p>
     *  返回与正在访问的类型相对应的类型镜像。如果此异常已序列化,然后读回,则类型镜像可能不可用。
     * 
     * 
     * @return the type mirrors in construction order, or {@code null} if unavailable
     */
    public List<? extends TypeMirror> getTypeMirrors() {
        return types;
    }

    /**
     * Explicitly set all transient fields.
     * <p>
     *  显式设置所有瞬态字段。
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        types = null;
    }
}

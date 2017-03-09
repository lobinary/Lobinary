/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.tools;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;

/**
 * Forwards calls to a given file object.  Subclasses of this class
 * might override some of these methods and might also provide
 * additional fields and methods.
 *
 * <p>
 *  向给定文件对象转发调用。此类的子类可能会覆盖这些方法中的一些,并且还可能提供其他字段和方法。
 * 
 * 
 * @param <F> the kind of file object forwarded to by this object
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public class ForwardingJavaFileObject<F extends JavaFileObject>
    extends ForwardingFileObject<F>
    implements JavaFileObject
{

    /**
     * Creates a new instance of ForwardingJavaFileObject.
     * <p>
     *  创建一个新的ForwardingJavaFileObject实例。
     * 
     * @param fileObject delegate to this file object
     */
    protected ForwardingJavaFileObject(F fileObject) {
        super(fileObject);
    }

    public Kind getKind() {
        return fileObject.getKind();
    }

    public boolean isNameCompatible(String simpleName, Kind kind) {
        return fileObject.isNameCompatible(simpleName, kind);
    }

    public NestingKind getNestingKind() { return fileObject.getNestingKind(); }

    public Modifier getAccessLevel()  { return fileObject.getAccessLevel(); }

}

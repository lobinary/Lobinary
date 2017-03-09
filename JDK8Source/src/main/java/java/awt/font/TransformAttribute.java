/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*
 * (C) Copyright Taligent, Inc. 1996 - 1997, All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998, All Rights Reserved
 *
 * The original version of this source code and documentation is
 * copyrighted and owned by Taligent, Inc., a wholly-owned subsidiary
 * of IBM. These materials are provided under terms of a License
 * Agreement between Taligent and Sun. This technology is protected
 * by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权所有Taligent,Inc. 1996  -  1997,保留所有权利(C)版权所有IBM Corp. 1996  -  1998,保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.awt.font;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.io.ObjectStreamException;

/**
 * The <code>TransformAttribute</code> class provides an immutable
 * wrapper for a transform so that it is safe to use as an attribute.
 * <p>
 *  <code> TransformAttribute </code>类为变换提供了一个不可变的包装器,因此可以安全地用作属性。
 * 
 */
public final class TransformAttribute implements Serializable {

    /**
     * The <code>AffineTransform</code> for this
     * <code>TransformAttribute</code>, or <code>null</code>
     * if <code>AffineTransform</code> is the identity transform.
     * <p>
     *  如果<code> AffineTransform </code>是标识变换,则<code> TransformAttribute </code>或<code> null </code>的<code> 
     * AffineTransform </code>。
     * 
     */
    private AffineTransform transform;

    /**
     * Wraps the specified transform.  The transform is cloned and a
     * reference to the clone is kept.  The original transform is unchanged.
     * If null is passed as the argument, this constructor behaves as though
     * it were the identity transform.  (Note that it is preferable to use
     * {@link #IDENTITY} in this case.)
     * <p>
     *  包装指定的变换。克隆变换并保留对该克隆的引用。原始变换不变。如果null作为参数传递,这个构造函数的行为就像是标识转换。 (请注意,在这种情况下最好使用{@link #IDENTITY}。)
     * 
     * 
     * @param transform the specified {@link AffineTransform} to be wrapped,
     * or null.
     */
    public TransformAttribute(AffineTransform transform) {
        if (transform != null && !transform.isIdentity()) {
            this.transform = new AffineTransform(transform);
        }
    }

    /**
     * Returns a copy of the wrapped transform.
     * <p>
     *  返回包装的变换的副本。
     * 
     * 
     * @return a <code>AffineTransform</code> that is a copy of the wrapped
     * transform of this <code>TransformAttribute</code>.
     */
    public AffineTransform getTransform() {
        AffineTransform at = transform;
        return (at == null) ? new AffineTransform() : new AffineTransform(at);
    }

    /**
     * Returns <code>true</code> if the wrapped transform is
     * an identity transform.
     * <p>
     *  如果包装的变换是标识变换,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the wrapped transform is
     * an identity transform; <code>false</code> otherwise.
     * @since 1.4
     */
    public boolean isIdentity() {
        return transform == null;
    }

    /**
     * A <code>TransformAttribute</code> representing the identity transform.
     * <p>
     *  代表身份转换的<code> TransformAttribute </code>。
     * 
     * 
     * @since 1.6
     */
    public static final TransformAttribute IDENTITY = new TransformAttribute(null);

    private void writeObject(java.io.ObjectOutputStream s)
      throws java.lang.ClassNotFoundException,
             java.io.IOException
    {
        // sigh -- 1.3 expects transform is never null, so we need to always write one out
        if (this.transform == null) {
            this.transform = new AffineTransform();
        }
        s.defaultWriteObject();
    }

    /*
    /* <p>
    /* 
     * @since 1.6
     */
    private Object readResolve() throws ObjectStreamException {
        if (transform == null || transform.isIdentity()) {
            return IDENTITY;
        }
        return this;
    }

    // Added for serial backwards compatibility (4348425)
    static final long serialVersionUID = 3356247357827709530L;

    /**
    /* <p>
    /* 
     * @since 1.6
     */
    public int hashCode() {
        return transform == null ? 0 : transform.hashCode();
    }

    /**
     * Returns <code>true</code> if rhs is a <code>TransformAttribute</code>
     * whose transform is equal to this <code>TransformAttribute</code>'s
     * transform.
     * <p>
     * 如果rhs是一个<transform> TransformAttribute </code>,其变换等于这个<code> TransformAttribute </code>的变换,则返回<code> 
     * true </code>。
     * 
     * @param rhs the object to compare to
     * @return <code>true</code> if the argument is a <code>TransformAttribute</code>
     * whose transform is equal to this <code>TransformAttribute</code>'s
     * transform.
     * @since 1.6
     */
    public boolean equals(Object rhs) {
        if (rhs != null) {
            try {
                TransformAttribute that = (TransformAttribute)rhs;
                if (transform == null) {
                    return that.transform == null;
                }
                return transform.equals(that.transform);
            }
            catch (ClassCastException e) {
            }
        }
        return false;
    }
}

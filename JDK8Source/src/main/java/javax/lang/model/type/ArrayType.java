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

package javax.lang.model.type;


/**
 * Represents an array type.
 * A multidimensional array type is represented as an array type
 * whose component type is also an array type.
 *
 * <p>
 *  表示数组类型。多维数组类型表示为数组类型,其组件类型也是数组类型。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface ArrayType extends ReferenceType {

    /**
     * Returns the component type of this array type.
     *
     * <p>
     *  返回此数组类型的组件类型。
     * 
     * @return the component type of this array type
     */
    TypeMirror getComponentType();
}

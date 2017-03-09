/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model;

/**
 * Superclass of exceptions which indicate that an unknown kind of
 * entity was encountered.  This situation can occur if the language
 * evolves and new kinds of constructs are introduced.  Subclasses of
 * this exception may be thrown by visitors to indicate that the
 * visitor was created for a prior version of the language.
 *
 * <p>A common superclass for those exceptions allows a single catch
 * block to have code handling them uniformly.
 *
 * <p>
 *  表示遇到未知类型实体的异常超类。如果语言演变和引入新的结构类型,就会出现这种情况。此异常的子类可能会被访问​​者抛出,以指示为该语言的先前版本创建了访问者。
 * 
 *  <p>这些异常的通用超类允许单个catch块使代码一致地处理它们。
 * 
 * 
 * @author Joseph D. Darcy
 * @see javax.lang.model.element.UnknownElementException
 * @see javax.lang.model.element.UnknownAnnotationValueException
 * @see javax.lang.model.type.UnknownTypeException
 * @since 1.7
 */
public class UnknownEntityException extends RuntimeException {

    private static final long serialVersionUID = 269L;

    /**
     * Creates a new {@code UnknownEntityException} with the specified
     * detail message.
     *
     * <p>
     * 
     * @param message the detail message
     */
    protected UnknownEntityException(String message) {
        super(message);
    }
}

/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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


/**
 * Represents a modifier on a program element such
 * as a class, method, or field.
 *
 * <p>Not all modifiers are applicable to all kinds of elements.
 * When two or more modifiers appear in the source code of an element
 * then it is customary, though not required, that they appear in the same
 * order as the constants listed in the detail section below.
 *
 * <p>Note that it is possible additional modifiers will be added in
 * future versions of the platform.
 *
 * <p>
 *  表示程序元素(如类,方法或字段)上的修饰符。
 * 
 *  <p>并非所有修饰符都适用于所有种类的元素。当一个元素的源代码中出现两个或多个修饰符时,尽管不是必需的,但它们以与下面详细部分中列出的常量相同的顺序出现。
 * 
 *  <p>请注意,在平台的未来版本中可能会添加其他修饰符。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */

public enum Modifier {

    // See JLS sections 8.1.1, 8.3.1, 8.4.3, 8.8.3, and 9.1.1.
    // java.lang.reflect.Modifier includes INTERFACE, but that's a VMism.

    /** The modifier {@code public} */          PUBLIC,
    /* <p>
    /* 
    /** The modifier {@code protected} */       PROTECTED,
    /* <p>
    /* 
    /** The modifier {@code private} */         PRIVATE,
    /* <p>
    /* 
    /** The modifier {@code abstract} */        ABSTRACT,
    /**
     * The modifier {@code default}
     * <p>
     *  / **修饰符{@code default}
     * 
     * 
     * @since 1.8
     */
     DEFAULT,
    /** The modifier {@code static} */          STATIC,
    /* <p>
    /* 
    /** The modifier {@code final} */           FINAL,
    /* <p>
    /* 
    /** The modifier {@code transient} */       TRANSIENT,
    /* <p>
    /* 
    /** The modifier {@code volatile} */        VOLATILE,
    /* <p>
    /* 
    /** The modifier {@code synchronized} */    SYNCHRONIZED,
    /* <p>
    /* 
    /** The modifier {@code native} */          NATIVE,
    /* <p>
    /* 
    /** The modifier {@code strictfp} */        STRICTFP;

    /**
     * Returns this modifier's name in lowercase.
     * <p>
     *  / **以小写形式返回此修饰符的名称。
     */
    public String toString() {
        return name().toLowerCase(java.util.Locale.US);
    }
}

/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 * The original version of this source code and documentation
 * is copyrighted and owned by Taligent, Inc., a wholly-owned
 * subsidiary of IBM. These materials are provided under terms
 * of a License Agreement between Taligent and Sun. This technology
 * is protected by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.util;

/**
 * Signals that a resource is missing.
 * <p>
 *  表示资源缺失。
 * 
 * 
 * @see java.lang.Exception
 * @see ResourceBundle
 * @author      Mark Davis
 * @since       JDK1.1
 */
public
class MissingResourceException extends RuntimeException {

    /**
     * Constructs a MissingResourceException with the specified information.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定信息的MissingResourceException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param s the detail message
     * @param className the name of the resource class
     * @param key the key for the missing resource.
     */
    public MissingResourceException(String s, String className, String key) {
        super(s);
        this.className = className;
        this.key = key;
    }

    /**
     * Constructs a <code>MissingResourceException</code> with
     * <code>message</code>, <code>className</code>, <code>key</code>,
     * and <code>cause</code>. This constructor is package private for
     * use by <code>ResourceBundle.getBundle</code>.
     *
     * <p>
     *  使用<code> message </code>,<code> className </code>,<code>键</code>和<code>原因</code>构造<code> MissingReso
     * urceException </code>。
     * 此构造函数是由<code> ResourceBundle.getBundle </code>使用的包私有。
     * 
     * 
     * @param message
     *        the detail message
     * @param className
     *        the name of the resource class
     * @param key
     *        the key for the missing resource.
     * @param cause
     *        the cause (which is saved for later retrieval by the
     *        {@link Throwable.getCause()} method). (A null value is
     *        permitted, and indicates that the cause is nonexistent
     *        or unknown.)
     */
    MissingResourceException(String message, String className, String key, Throwable cause) {
        super(message, cause);
        this.className = className;
        this.key = key;
    }

    /**
     * Gets parameter passed by constructor.
     *
     * <p>
     *  获取由构造函数传递的参数。
     * 
     * 
     * @return the name of the resource class
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets parameter passed by constructor.
     *
     * <p>
     *  获取由构造函数传递的参数。
     * 
     * 
     * @return the key for the missing resource
     */
    public String getKey() {
        return key;
    }

    //============ privates ============

    // serialization compatibility with JDK1.1
    private static final long serialVersionUID = -4876345176062000401L;

    /**
     * The class name of the resource bundle requested by the user.
     * <p>
     *  用户请求的资源束的类名。
     * 
     * 
     * @serial
     */
    private String className;

    /**
     * The name of the specific resource requested by the user.
     * <p>
     *  用户请求的特定资源的名称。
     * 
     * @serial
     */
    private String key;
}

/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;


/**
 * An object that stores a MIME header name and its value. One or more
 * <code>MimeHeader</code> objects may be contained in a <code>MimeHeaders</code>
 * object.
 *
 * <p>
 *  存储MIME标题名称及其值的对象。一个或多个<code> MimeHeader </code>对象可以包含在<code> MimeHeaders </code>对象中。
 * 
 * 
 * @see MimeHeaders
 */
public class MimeHeader {

   private String name;
   private String value;

   /**
    * Constructs a <code>MimeHeader</code> object initialized with the given
    * name and value.
    *
    * <p>
    *  构造使用给定的名称和值初始化的<code> MimeHeader </code>对象。
    * 
    * 
    * @param name a <code>String</code> giving the name of the header
    * @param value a <code>String</code> giving the value of the header
    */
    public MimeHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name of this <code>MimeHeader</code> object.
     *
     * <p>
     *  返回这个<code> MimeHeader </code>对象的名称。
     * 
     * 
     * @return the name of the header as a <code>String</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of this <code>MimeHeader</code> object.
     *
     * <p>
     *  返回这个<code> MimeHeader </code>对象的值。
     * 
     * @return  the value of the header as a <code>String</code>
     */
    public String getValue() {
        return value;
    }
}

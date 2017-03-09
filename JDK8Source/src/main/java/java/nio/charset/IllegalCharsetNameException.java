/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
 *
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
 *
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio.charset;


/**
 * Unchecked exception thrown when a string that is not a
 * <a href=Charset.html#names>legal charset name</a> is used as such.
 *
 * <p>
 *  如果使用非<a href=Charset.html#names>合法字符集名称</a>的字符串,则会抛出未经检查的异常。
 * 
 * 
 * @since 1.4
 */

public class IllegalCharsetNameException
    extends IllegalArgumentException
{

    private static final long serialVersionUID = 1457525358470002989L;

    private String charsetName;

    /**
     * Constructs an instance of this class.
     *
     * <p>
     *  构造此类的实例。
     * 
     * 
     * @param  charsetName
     *         The illegal charset name
     */
    public IllegalCharsetNameException(String charsetName) {
        super(String.valueOf(charsetName));
	this.charsetName = charsetName;
    }

    /**
     * Retrieves the illegal charset name.
     *
     * <p>
     *  检索非法字符集名称。
     * 
     * @return  The illegal charset name
     */
    public String getCharsetName() {
        return charsetName;
    }

}

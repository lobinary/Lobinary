/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni;

/**
 * This class is used as a structure to pass text contained in the underlying
 * character buffer of the scanner. The offset and length fields allow the
 * buffer to be re-used without creating new character arrays.
 * <p>
 * <strong>Note:</strong> Methods that are passed an XMLString structure
 * should consider the contents read-only and not make any modifications
 * to the contents of the buffer. The method receiving this structure
 * should also not modify the offset and length if this structure (or
 * the values of this structure) are passed to another method.
 * <p>
 * <strong>Note:</strong> Methods that are passed an XMLString structure
 * are required to copy the information out of the buffer if it is to be
 * saved for use beyond the scope of the method. The contents of the
 * structure are volatile and the contents of the character buffer cannot
 * be assured once the method that is passed this structure returns.
 * Therefore, methods passed this structure should not save any reference
 * to the structure or the character array contained in the structure.
 *
 * <p>
 *  此类用作传递包含在扫描器的基础字符缓冲区中的文本的结构。 offset和length字段允许重用缓冲区,而不创建新的字符数组。
 * <p>
 *  <strong>注意</strong>：传递XMLString结构的方法应将内容视为只读,而不对缓冲区的内容进行任何修改。
 * 接收此结构的方法也不应修改偏移和长度,如果此结构(或此结构的值)被传递到另一个方法。
 * <p>
 * <strong>注意</strong>：传递XMLString结构的方法需要将信息复制到缓冲区之外,如果它被保存以供超出方法范围使用。
 * 结构的内容是易变的,一旦传递此结构的方法返回,就不能确保字符缓冲区的内容。因此,传递此结构的方法不应保存对结构或结构中包含的字符数组的任何引用。
 * 
 * 
 * @author Eric Ye, IBM
 * @author Andy Clark, IBM
 *
 */
public class XMLString {

    //
    // Data
    //

    /** The character array. */
    public char[] ch;

    /** The offset into the character array. */
    public int offset;

    /** The length of characters from the offset. */
    public int length;

    //
    // Constructors
    //

    /** Default constructor. */
    public XMLString() {
    } // <init>()

    /**
     * Constructs an XMLString structure preset with the specified
     * values.
     *
     * <p>
     *  构造使用指定值预设的XMLString结构。
     * 
     * 
     * @param ch     The character array.
     * @param offset The offset into the character array.
     * @param length The length of characters from the offset.
     */
    public XMLString(char[] ch, int offset, int length) {
        setValues(ch, offset, length);
    } // <init>(char[],int,int)

    /**
     * Constructs an XMLString structure with copies of the values in
     * the given structure.
     * <p>
     * <strong>Note:</strong> This does not copy the character array;
     * only the reference to the array is copied.
     *
     * <p>
     *  构造具有给定结构中的值的副本的XMLString结构。
     * <p>
     *  <strong>注意：</strong>这不会复制字符数组;只复制对数组的引用。
     * 
     * 
     * @param string The XMLString to copy.
     */
    public XMLString(XMLString string) {
        setValues(string);
    } // <init>(XMLString)

    //
    // Public methods
    //

    /**
     * Initializes the contents of the XMLString structure with the
     * specified values.
     *
     * <p>
     *  使用指定的值初始化XMLString结构的内容。
     * 
     * 
     * @param ch     The character array.
     * @param offset The offset into the character array.
     * @param length The length of characters from the offset.
     */
    public void setValues(char[] ch, int offset, int length) {
        this.ch = ch;
        this.offset = offset;
        this.length = length;
    } // setValues(char[],int,int)

    /**
     * Initializes the contents of the XMLString structure with copies
     * of the given string structure.
     * <p>
     * <strong>Note:</strong> This does not copy the character array;
     * only the reference to the array is copied.
     *
     * <p>
     *  使用给定字符串结构的副本初始化XMLString结构的内容。
     * <p>
     *  <strong>注意：</strong>这不会复制字符数组;只复制对数组的引用。
     * 
     * 
     * @param s
     */
    public void setValues(XMLString s) {
        setValues(s.ch, s.offset, s.length);
    } // setValues(XMLString)

    /** Resets all of the values to their defaults. */
    public void clear() {
        this.ch = null;
        this.offset = 0;
        this.length = -1;
    } // clear()

    /**
     * Returns true if the contents of this XMLString structure and
     * the specified array are equal.
     *
     * <p>
     *  如果此XMLString结构和指定数组的内容相等,则返回true。
     * 
     * 
     * @param ch     The character array.
     * @param offset The offset into the character array.
     * @param length The length of characters from the offset.
     */
    public boolean equals(char[] ch, int offset, int length) {
        if (ch == null) {
            return false;
        }
        if (this.length != length) {
            return false;
        }

        for (int i=0; i<length; i++) {
            if (this.ch[this.offset+i] != ch[offset+i] ) {
                return false;
            }
        }
        return true;
    } // equals(char[],int,int):boolean

    /**
     * Returns true if the contents of this XMLString structure and
     * the specified string are equal.
     *
     * <p>
     *  如果此XMLString结构的内容和指定的字符串相等,则返回true。
     * 
     * @param s The string to compare.
     */
    public boolean equals(String s) {
        if (s == null) {
            return false;
        }
        if ( length != s.length() ) {
            return false;
        }

        // is this faster than call s.toCharArray first and compare the
        // two arrays directly, which will possibly involve creating a
        // new char array object.
        for (int i=0; i<length; i++) {
            if (ch[offset+i] != s.charAt(i)) {
                return false;
            }
        }

        return true;
    } // equals(String):boolean

    //
    // Object methods
    //

    /** Returns a string representation of this object. */
    public String toString() {
        return length > 0 ? new String(ch, offset, length) : "";
    } // toString():String

} // class XMLString

/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XString.java,v 1.2.4.1 2005/09/14 20:47:20 jeffsuttor Exp $
 * <p>
 *  $ Id：XString.java,v 1.2.4.1 2005/09/14 20:47:20 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.objects;

import java.util.Locale;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.utils.XMLCharacterRecognizer;
import com.sun.org.apache.xml.internal.utils.XMLString;
import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;

/**
 * This class represents an XPath string object, and is capable of
 * converting the string to other types, such as a number.
 * @xsl.usage general
 * <p>
 *  此类表示一个XPath字符串对象,并且能够将字符串转换为其他类型,例如数字。 @ xsl.usage general
 * 
 */
public class XString extends XObject implements XMLString
{
    static final long serialVersionUID = 2020470518395094525L;

  /** Empty string XString object */
  public static final XString EMPTYSTRING = new XString("");

  /**
   * Construct a XString object.  This constructor exists for derived classes.
   *
   * <p>
   *  构造一个XString对象。这个构造函数存在于派生类。
   * 
   * 
   * @param val String object this will wrap.
   */
  protected XString(Object val)
  {
    super(val);
  }

  /**
   * Construct a XNodeSet object.
   *
   * <p>
   *  构造一个XNodeSet对象。
   * 
   * 
   * @param val String object this will wrap.
   */
  public XString(String val)
  {
    super(val);
  }

  /**
   * Tell that this is a CLASS_STRING.
   *
   * <p>
   *  告诉这是一个CLASS_STRING。
   * 
   * 
   * @return type CLASS_STRING
   */
  public int getType()
  {
    return CLASS_STRING;
  }

  /**
   * Given a request type, return the equivalent string.
   * For diagnostic purposes.
   *
   * <p>
   *  给定一个请求类型,返回等效的字符串。用于诊断目的。
   * 
   * 
   * @return type string "#STRING"
   */
  public String getTypeString()
  {
    return "#STRING";
  }

  /**
   * Tell if this object contains a java String object.
   *
   * <p>
   *  告诉这个对象是否包含一个java String对象。
   * 
   * 
   * @return true if this XMLString can return a string without creating one.
   */
  public boolean hasString()
  {
    return true;
  }

  /**
   * Cast result object to a number.
   *
   * <p>
   *  将结果对象转换为数字。
   * 
   * 
   * @return 0.0 if this string is null, numeric value of this string
   * or NaN
   */
  public double num()
  {
    return toDouble();
  }

  /**
   * Convert a string to a double -- Allowed input is in fixed
   * notation ddd.fff.
   *
   * <p>
   *  将字符串转换为double  - 允许的输入是固定符号ddd.fff。
   * 
   * 
   * @return A double value representation of the string, or return Double.NaN
   * if the string can not be converted.
   */
  public double toDouble()
  {
    /* XMLCharacterRecognizer.isWhiteSpace(char c) methods treats the following
     * characters as white space characters.
     * ht - horizontal tab, nl - newline , cr - carriage return and sp - space
     * trim() methods by default also takes care of these white space characters
     * So trim() method is used to remove leading and trailing white spaces.
     * <p>
     * 字符作为空格字符。默认情况下,ht  - 水平制表符,nl  - 换行符,cr  - 回车和sp  -  space trim()方法也会处理这些空格字符。
     * 所以trim()方法用于删除前导和尾随的空格。
     * 
     */
        XMLString s = trim();
        double result = Double.NaN;
        for (int i = 0; i < s.length(); i++)
        {
                char c = s.charAt(i);
    if (c != '-' && c != '.' && ( c < 0X30 || c > 0x39)) {
            // The character is not a '-' or a '.' or a digit
            // then return NaN because something is wrong.
                        return result;
        }
        }
        try
        {
                result = Double.parseDouble(s.toString());
        } catch (NumberFormatException e){}

        return result;
}

  /**
   * Cast result object to a boolean.
   *
   * <p>
   *  将结果对象转换为布尔值。
   * 
   * 
   * @return True if the length of this string object is greater
   * than 0.
   */
  public boolean bool()
  {
    return str().length() > 0;
  }

  /**
   * Cast result object to a string.
   *
   * <p>
   *  将结果对象转换为字符串。
   * 
   * 
   * @return The string this wraps or the empty string if null
   */
  public XMLString xstr()
  {
    return this;
  }

  /**
   * Cast result object to a string.
   *
   * <p>
   *  将结果对象转换为字符串。
   * 
   * 
   * @return The string this wraps or the empty string if null
   */
  public String str()
  {
    return (null != m_obj) ? ((String) m_obj) : "";
  }

  /**
   * Cast result object to a result tree fragment.
   *
   * <p>
   *  将结果对象强制转换到结果树片段。
   * 
   * 
   * @param support Xpath context to use for the conversion
   *
   * @return A document fragment with this string as a child node
   */
  public int rtf(XPathContext support)
  {

    DTM frag = support.createDocumentFragment();

    frag.appendTextChild(str());

    return frag.getDocument();
  }

  /**
   * Directly call the
   * characters method on the passed ContentHandler for the
   * string-value. Multiple calls to the
   * ContentHandler's characters methods may well occur for a single call to
   * this method.
   *
   * <p>
   *  直接调用传递的ContentHandler中的字符方法的字符串值。对ContentHandler的字符方法的多次调用很可能发生在对此方法的单个调用中。
   * 
   * 
   * @param ch A non-null reference to a ContentHandler.
   *
   * @throws org.xml.sax.SAXException
   */
  public void dispatchCharactersEvents(org.xml.sax.ContentHandler ch)
          throws org.xml.sax.SAXException
  {

    String str = str();

    ch.characters(str.toCharArray(), 0, str.length());
  }

  /**
   * Directly call the
   * comment method on the passed LexicalHandler for the
   * string-value.
   *
   * <p>
   *  在传递的LexicalHandler的字符串值上直接调用注释方法。
   * 
   * 
   * @param lh A non-null reference to a LexicalHandler.
   *
   * @throws org.xml.sax.SAXException
   */
  public void dispatchAsComment(org.xml.sax.ext.LexicalHandler lh)
          throws org.xml.sax.SAXException
  {

    String str = str();

    lh.comment(str.toCharArray(), 0, str.length());
  }

  /**
   * Returns the length of this string.
   *
   * <p>
   *  返回此字符串的长度。
   * 
   * 
   * @return  the length of the sequence of characters represented by this
   *          object.
   */
  public int length()
  {
    return str().length();
  }

  /**
   * Returns the character at the specified index. An index ranges
   * from <code>0</code> to <code>length() - 1</code>. The first character
   * of the sequence is at index <code>0</code>, the next at index
   * <code>1</code>, and so on, as for array indexing.
   *
   * <p>
   *  返回指定索引处的字符。索引的范围从<code> 0 </code>到<code> length() -  1 </code>。
   * 该序列的第一个字符位于索引<code> 0 </code>,下一个位于索引<code> 1 </code>,依此类推,就像数组索引一样。
   * 
   * 
   * @param      index   the index of the character.
   * @return     the character at the specified index of this string.
   *             The first character is at index <code>0</code>.
   * @exception  IndexOutOfBoundsException  if the <code>index</code>
   *             argument is negative or not less than the length of this
   *             string.
   */
  public char charAt(int index)
  {
    return str().charAt(index);
  }

  /**
   * Copies characters from this string into the destination character
   * array.
   *
   * <p>
   *  将字符串从此字符串复制到目标字符数组中。
   * 
   * 
   * @param      srcBegin   index of the first character in the string
   *                        to copy.
   * @param      srcEnd     index after the last character in the string
   *                        to copy.
   * @param      dst        the destination array.
   * @param      dstBegin   the start offset in the destination array.
   * @exception IndexOutOfBoundsException If any of the following
   *            is true:
   *            <ul><li><code>srcBegin</code> is negative.
   *            <li><code>srcBegin</code> is greater than <code>srcEnd</code>
   *            <li><code>srcEnd</code> is greater than the length of this
   *                string
   *            <li><code>dstBegin</code> is negative
   *            <li><code>dstBegin+(srcEnd-srcBegin)</code> is larger than
   *                <code>dst.length</code></ul>
   * @exception NullPointerException if <code>dst</code> is <code>null</code>
   */
  public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin)
  {
    str().getChars(srcBegin, srcEnd, dst, dstBegin);
  }

  /**
   * Tell if two objects are functionally equal.
   *
   * <p>
   *  告诉两个对象在功能上是否相等。
   * 
   * 
   * @param obj2 Object to compare this to
   *
   * @return true if the two objects are equal
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean equals(XObject obj2)
  {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    int t = obj2.getType();
    try
    {
            if (XObject.CLASS_NODESET == t)
              return obj2.equals(this);
            // If at least one object to be compared is a boolean, then each object
            // to be compared is converted to a boolean as if by applying the
            // boolean function.
            else if(XObject.CLASS_BOOLEAN == t)
                return obj2.bool() == bool();
            // Otherwise, if at least one object to be compared is a number, then each object
            // to be compared is converted to a number as if by applying the number function.
            else if(XObject.CLASS_NUMBER == t)
                return obj2.num() == num();
    }
    catch(javax.xml.transform.TransformerException te)
    {
        throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(te);
    }

    // Otherwise, both objects to be compared are converted to strings as
    // if by applying the string function.
    return xstr().equals(obj2.xstr());
  }

   /**
   * Compares this string to the specified <code>String</code>.
   * The result is <code>true</code> if and only if the argument is not
   * <code>null</code> and is a <code>String</code> object that represents
   * the same sequence of characters as this object.
   *
   * <p>
   *  将此字符串与指定的<code> String </code>进行比较。
   * 如果且仅当参数不是<code> null </code>且是代表与此对象相同的字符序列的<code> String </code>对象,结果是<code> true </code>。
   * 
   * 
   * @param   obj2   the object to compare this <code>String</code> against.
   * @return  <code>true</code> if the <code>String</code>s are equal;
   *          <code>false</code> otherwise.
   * @see     java.lang.String#compareTo(java.lang.String)
   * @see     java.lang.String#equalsIgnoreCase(java.lang.String)
   */
  public boolean equals(String obj2) {
    return str().equals(obj2);
  }

  /**
   * Compares this string to the specified object.
   * The result is <code>true</code> if and only if the argument is not
   * <code>null</code> and is a <code>String</code> object that represents
   * the same sequence of characters as this object.
   *
   * <p>
   * 将此字符串与指定的对象进行比较。
   * 如果且仅当参数不是<code> null </code>且是代表与此对象相同的字符序列的<code> String </code>对象,结果是<code> true </code>。
   * 
   * 
   * @param obj2   the object to compare this <code>String</code>
   *                     against.
   * @return  <code>true</code> if the <code>String </code>are equal;
   *          <code>false</code> otherwise.
   * @see     java.lang.String#compareTo(java.lang.String)
   * @see     java.lang.String#equalsIgnoreCase(java.lang.String)
   */
  public boolean equals(XMLString obj2)
  {
    if (obj2 != null) {
      if (!obj2.hasString()) {
        return obj2.equals(str());
      } else {
        return str().equals(obj2.toString());
      }
    }
    return false;
  }

  /**
   * Compares this string to the specified object.
   * The result is <code>true</code> if and only if the argument is not
   * <code>null</code> and is a <code>String</code> object that represents
   * the same sequence of characters as this object.
   *
   * <p>
   *  将此字符串与指定的对象进行比较。
   * 如果且仅当参数不是<code> null </code>且是代表与此对象相同的字符序列的<code> String </code>对象,结果是<code> true </code>。
   * 
   * 
   * @param   obj2       the object to compare this <code>String</code>
   *                     against.
   * @return  <code>true</code> if the <code>String </code>are equal;
   *          <code>false</code> otherwise.
   * @see     java.lang.String#compareTo(java.lang.String)
   * @see     java.lang.String#equalsIgnoreCase(java.lang.String)
   */
  public boolean equals(Object obj2)
  {

    if (null == obj2)
      return false;

      // In order to handle the 'all' semantics of
      // nodeset comparisons, we always call the
      // nodeset function.
    else if (obj2 instanceof XNodeSet)
      return obj2.equals(this);
    else if(obj2 instanceof XNumber)
        return obj2.equals(this);
    else
      return str().equals(obj2.toString());
  }

  /**
   * Compares this <code>String</code> to another <code>String</code>,
   * ignoring case considerations.  Two strings are considered equal
   * ignoring case if they are of the same length, and corresponding
   * characters in the two strings are equal ignoring case.
   *
   * <p>
   *  将此<code> String </code>与另一个<code> String </code>进行比较,忽略大小写。
   * 如果两个字符串具有相同的长度,则两个字符串被认为是相等的忽略的情况,并且两个字符串中的相应字符是相等的忽略的情况。
   * 
   * 
   * @param   anotherString   the <code>String</code> to compare this
   *                          <code>String</code> against.
   * @return  <code>true</code> if the argument is not <code>null</code>
   *          and the <code>String</code>s are equal,
   *          ignoring case; <code>false</code> otherwise.
   * @see     #equals(Object)
   * @see     java.lang.Character#toLowerCase(char)
   * @see java.lang.Character#toUpperCase(char)
   */
  public boolean equalsIgnoreCase(String anotherString)
  {
    return str().equalsIgnoreCase(anotherString);
  }

  /**
   * Compares two strings lexicographically.
   *
   * <p>
   *  按字母顺序比较两个字符串。
   * 
   * 
   * @param   xstr   the <code>String</code> to be compared.
   *
   * @return  the value <code>0</code> if the argument string is equal to
   *          this string; a value less than <code>0</code> if this string
   *          is lexicographically less than the string argument; and a
   *          value greater than <code>0</code> if this string is
   *          lexicographically greater than the string argument.
   * @exception java.lang.NullPointerException if <code>anotherString</code>
   *          is <code>null</code>.
   */
  public int compareTo(XMLString xstr)
  {

    int len1 = this.length();
    int len2 = xstr.length();
    int n = Math.min(len1, len2);
    int i = 0;
    int j = 0;

    while (n-- != 0)
    {
      char c1 = this.charAt(i);
      char c2 = xstr.charAt(j);

      if (c1 != c2)
      {
        return c1 - c2;
      }

      i++;
      j++;
    }

    return len1 - len2;
  }

  /**
   * Compares two strings lexicographically, ignoring case considerations.
   * This method returns an integer whose sign is that of
   * <code>this.toUpperCase().toLowerCase().compareTo(
   * str.toUpperCase().toLowerCase())</code>.
   * <p>
   * Note that this method does <em>not</em> take locale into account,
   * and will result in an unsatisfactory ordering for certain locales.
   * The java.text package provides <em>collators</em> to allow
   * locale-sensitive ordering.
   *
   * <p>
   *  按字典顺序比较两个字符串,忽略情况注意事项。此方法返回一个整数,其符号为<code> this.toUpperCase()。toLowerCase()。
   * compareTo(str.toUpperCase()。toLowerCase())</code>。
   * <p>
   *  请注意,此方法不会</em>将区域设置纳入考虑,并且会导致某些区域设置的令人不满意的排序。 java.text包提供了<em> collat​​ors </em>以允许区域设置敏感的排序。
   * 
   * 
   * @param   str   the <code>String</code> to be compared.
   * @return  a negative integer, zero, or a positive integer as the
   *          the specified String is greater than, equal to, or less
   *          than this String, ignoring case considerations.
   * @see     java.text.Collator#compare(String, String)
   * @since   1.2
   */
  public int compareToIgnoreCase(XMLString str)
  {
    // %REVIEW%  Like it says, @since 1.2. Doesn't exist in earlier
    // versions of Java, hence we can't yet shell out to it. We can implement
    // it as character-by-character compare, but doing so efficiently
    // is likely to be (ahem) interesting.
    //
    // However, since nobody is actually _using_ this method yet:
    //    return str().compareToIgnoreCase(str.toString());

    throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(
      new java.lang.NoSuchMethodException(
        "Java 1.2 method, not yet implemented"));
  }

  /**
   * Tests if this string starts with the specified prefix beginning
   * a specified index.
   *
   * <p>
   *  测试此字符串是否以指定索引开头的指定前缀开头。
   * 
   * 
   * @param   prefix    the prefix.
   * @param   toffset   where to begin looking in the string.
   * @return  <code>true</code> if the character sequence represented by the
   *          argument is a prefix of the substring of this object starting
   *          at index <code>toffset</code>; <code>false</code> otherwise.
   *          The result is <code>false</code> if <code>toffset</code> is
   *          negative or greater than the length of this
   *          <code>String</code> object; otherwise the result is the same
   *          as the result of the expression
   *          <pre>
   *          this.subString(toffset).startsWith(prefix)
   *          </pre>
   * @exception java.lang.NullPointerException if <code>prefix</code> is
   *          <code>null</code>.
   */
  public boolean startsWith(String prefix, int toffset)
  {
    return str().startsWith(prefix, toffset);
  }

  /**
   * Tests if this string starts with the specified prefix.
   *
   * <p>
   *  测试此字符串是否以指定的前缀开头。
   * 
   * 
   * @param   prefix   the prefix.
   * @return  <code>true</code> if the character sequence represented by the
   *          argument is a prefix of the character sequence represented by
   *          this string; <code>false</code> otherwise.
   *          Note also that <code>true</code> will be returned if the
   *          argument is an empty string or is equal to this
   *          <code>String</code> object as determined by the
   *          {@link #equals(Object)} method.
   * @exception java.lang.NullPointerException if <code>prefix</code> is
   *          <code>null</code>.
   */
  public boolean startsWith(String prefix)
  {
    return startsWith(prefix, 0);
  }

  /**
   * Tests if this string starts with the specified prefix beginning
   * a specified index.
   *
   * <p>
   *  测试此字符串是否以指定索引开头的指定前缀开头。
   * 
   * 
   * @param   prefix    the prefix.
   * @param   toffset   where to begin looking in the string.
   * @return  <code>true</code> if the character sequence represented by the
   *          argument is a prefix of the substring of this object starting
   *          at index <code>toffset</code>; <code>false</code> otherwise.
   *          The result is <code>false</code> if <code>toffset</code> is
   *          negative or greater than the length of this
   *          <code>String</code> object; otherwise the result is the same
   *          as the result of the expression
   *          <pre>
   *          this.subString(toffset).startsWith(prefix)
   *          </pre>
   * @exception java.lang.NullPointerException if <code>prefix</code> is
   *          <code>null</code>.
   */
  public boolean startsWith(XMLString prefix, int toffset)
  {

    int to = toffset;
    int tlim = this.length();
    int po = 0;
    int pc = prefix.length();

    // Note: toffset might be near -1>>>1.
    if ((toffset < 0) || (toffset > tlim - pc))
    {
      return false;
    }

    while (--pc >= 0)
    {
      if (this.charAt(to) != prefix.charAt(po))
      {
        return false;
      }

      to++;
      po++;
    }

    return true;
  }

  /**
   * Tests if this string starts with the specified prefix.
   *
   * <p>
   *  测试此字符串是否以指定的前缀开头。
   * 
   * 
   * @param   prefix   the prefix.
   * @return  <code>true</code> if the character sequence represented by the
   *          argument is a prefix of the character sequence represented by
   *          this string; <code>false</code> otherwise.
   *          Note also that <code>true</code> will be returned if the
   *          argument is an empty string or is equal to this
   *          <code>String</code> object as determined by the
   *          {@link #equals(Object)} method.
   * @exception java.lang.NullPointerException if <code>prefix</code> is
   *          <code>null</code>.
   */
  public boolean startsWith(XMLString prefix)
  {
    return startsWith(prefix, 0);
  }

  /**
   * Tests if this string ends with the specified suffix.
   *
   * <p>
   * 测试此字符串是否以指定的后缀结尾。
   * 
   * 
   * @param   suffix   the suffix.
   * @return  <code>true</code> if the character sequence represented by the
   *          argument is a suffix of the character sequence represented by
   *          this object; <code>false</code> otherwise. Note that the
   *          result will be <code>true</code> if the argument is the
   *          empty string or is equal to this <code>String</code> object
   *          as determined by the {@link #equals(Object)} method.
   * @exception java.lang.NullPointerException if <code>suffix</code> is
   *          <code>null</code>.
   */
  public boolean endsWith(String suffix)
  {
    return str().endsWith(suffix);
  }

  /**
   * Returns a hashcode for this string. The hashcode for a
   * <code>String</code> object is computed as
   * <blockquote><pre>
   * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
   * </pre></blockquote>
   * using <code>int</code> arithmetic, where <code>s[i]</code> is the
   * <i>i</i>th character of the string, <code>n</code> is the length of
   * the string, and <code>^</code> indicates exponentiation.
   * (The hash value of the empty string is zero.)
   *
   * <p>
   *  返回此字符串的哈希码。
   * 对于<code> String </code>对象的哈希码被计算为<blockquote> <pre> s [0] * 31 ^(n-1)+ s [1] * 31 ^(n-2)+ .. 。
   * + s [n-1] </pre> </blockquote>使用<code> int </code>算术,其中<code> s [i] </code>字符串,<code> n </code>是字符串的长
   * 度,<code> ^ </code>表示取幂。
   * 对于<code> String </code>对象的哈希码被计算为<blockquote> <pre> s [0] * 31 ^(n-1)+ s [1] * 31 ^(n-2)+ .. 。
   *  (空字符串的哈希值为零。)。
   * 
   * 
   * @return  a hash code value for this object.
   */
  public int hashCode()
  {
    return str().hashCode();
  }

  /**
   * Returns the index within this string of the first occurrence of the
   * specified character. If a character with value <code>ch</code> occurs
   * in the character sequence represented by this <code>String</code>
   * object, then the index of the first such occurrence is returned --
   * that is, the smallest value <i>k</i> such that:
   * <blockquote><pre>
   * this.charAt(<i>k</i>) == ch
   * </pre></blockquote>
   * is <code>true</code>. If no such character occurs in this string,
   * then <code>-1</code> is returned.
   *
   * <p>
   *  返回此字符串中指定字符第一次出现的索引。
   * 如果在由<code> String </code>对象表示的字符序列中出现值为<code> ch </code>的字符,则返回第一次出现的索引 - i> k </i>使得：<blockquote> <pre>
   *  this.charAt(<k> k </i>)== ch </pre> </blockquote>是<code> true </code>。
   *  返回此字符串中指定字符第一次出现的索引。如果在这个字符串中没有这样的字符,则返回<code> -1 </code>。
   * 
   * 
   * @param   ch   a character.
   * @return  the index of the first occurrence of the character in the
   *          character sequence represented by this object, or
   *          <code>-1</code> if the character does not occur.
   */
  public int indexOf(int ch)
  {
    return str().indexOf(ch);
  }

  /**
   * Returns the index within this string of the first occurrence of the
   * specified character, starting the search at the specified index.
   * <p>
   * If a character with value <code>ch</code> occurs in the character
   * sequence represented by this <code>String</code> object at an index
   * no smaller than <code>fromIndex</code>, then the index of the first
   * such occurrence is returned--that is, the smallest value <i>k</i>
   * such that:
   * <blockquote><pre>
   * (this.charAt(<i>k</i>) == ch) && (<i>k</i> >= fromIndex)
   * </pre></blockquote>
   * is true. If no such character occurs in this string at or after
   * position <code>fromIndex</code>, then <code>-1</code> is returned.
   * <p>
   * There is no restriction on the value of <code>fromIndex</code>. If it
   * is negative, it has the same effect as if it were zero: this entire
   * string may be searched. If it is greater than the length of this
   * string, it has the same effect as if it were equal to the length of
   * this string: <code>-1</code> is returned.
   *
   * <p>
   *  返回指定字符第一次出现的此字符串中的索引,开始在指定索引处搜索。
   * <p>
   * 如果在由<code> String </code>对象表示的字符序列中,在不小于<code> fromIndex </code>的索引处出现值<code> ch </code>的字符,首先返回这样的出现
   *  - 即最小值<k>,使得：<blockquote> <pre>(this.charAt(<k> k)== ch)&& <i> k </i>> = fromIndex)</pre> </blockquote>
   * 为true。
   * 如果此字符串在<code> fromIndex </code>位置或之后没有出现,则返回<code> -1 </code>。
   * <p>
   *  对<code> fromIndex </code>的值没有限制。如果它是负数,它具有与它为零相同的效果：可以搜索整个字符串。
   * 如果它大于此字符串的长度,它具有与等于此字符串的长度相同的效果：返回<code> -1 </code>。
   * 
   * 
   * @param   ch          a character.
   * @param   fromIndex   the index to start the search from.
   * @return  the index of the first occurrence of the character in the
   *          character sequence represented by this object that is greater
   *          than or equal to <code>fromIndex</code>, or <code>-1</code>
   *          if the character does not occur.
   */
  public int indexOf(int ch, int fromIndex)
  {
    return str().indexOf(ch, fromIndex);
  }

  /**
   * Returns the index within this string of the last occurrence of the
   * specified character. That is, the index returned is the largest
   * value <i>k</i> such that:
   * <blockquote><pre>
   * this.charAt(<i>k</i>) == ch
   * </pre></blockquote>
   * is true.
   * The String is searched backwards starting at the last character.
   *
   * <p>
   *  返回此字符串中指定字符的最后一次出现的索引。
   * 也就是说,返回的索引是最大的值k,使得：<blockquote> <pre> this.charAt(<k> k </i>)== ch </pre> </blockquote >是真的。
   * 从最后一个字符开始向后搜索字符串。
   * 
   * 
   * @param   ch   a character.
   * @return  the index of the last occurrence of the character in the
   *          character sequence represented by this object, or
   *          <code>-1</code> if the character does not occur.
   */
  public int lastIndexOf(int ch)
  {
    return str().lastIndexOf(ch);
  }

  /**
   * Returns the index within this string of the last occurrence of the
   * specified character, searching backward starting at the specified
   * index. That is, the index returned is the largest value <i>k</i>
   * such that:
   * <blockquote><pre>
   * this.charAt(k) == ch) && (k <= fromIndex)
   * </pre></blockquote>
   * is true.
   *
   * <p>
   *  返回此字符串中指定字符的最后一次出现的索引,从指定索引开始向后搜索。
   * 也就是说,返回的索引是最大值k,使得：<blockquote> <pre> this.charAt(k)== ch)&&(k <= fromIndex)</pre> blockquote>为true。
   * 
   * 
   * @param   ch          a character.
   * @param   fromIndex   the index to start the search from. There is no
   *          restriction on the value of <code>fromIndex</code>. If it is
   *          greater than or equal to the length of this string, it has
   *          the same effect as if it were equal to one less than the
   *          length of this string: this entire string may be searched.
   *          If it is negative, it has the same effect as if it were -1:
   *          -1 is returned.
   * @return  the index of the last occurrence of the character in the
   *          character sequence represented by this object that is less
   *          than or equal to <code>fromIndex</code>, or <code>-1</code>
   *          if the character does not occur before that point.
   */
  public int lastIndexOf(int ch, int fromIndex)
  {
    return str().lastIndexOf(ch, fromIndex);
  }

  /**
   * Returns the index within this string of the first occurrence of the
   * specified substring. The integer returned is the smallest value
   * <i>k</i> such that:
   * <blockquote><pre>
   * this.startsWith(str, <i>k</i>)
   * </pre></blockquote>
   * is <code>true</code>.
   *
   * <p>
   * 返回此字符串中指定子字符串第一次出现的索引。
   * 返回的整数是最小值<i> k </i>,以便：<blockquote> <pre> this.startsWith(str,<i> k </i>)</pre> </blockquote> is <code >
   *  true </code>。
   * 返回此字符串中指定子字符串第一次出现的索引。
   * 
   * 
   * @param   str   any string.
   * @return  if the string argument occurs as a substring within this
   *          object, then the index of the first character of the first
   *          such substring is returned; if it does not occur as a
   *          substring, <code>-1</code> is returned.
   * @exception java.lang.NullPointerException if <code>str</code> is
   *          <code>null</code>.
   */
  public int indexOf(String str)
  {
    return str().indexOf(str);
  }

  /**
   * Returns the index within this string of the first occurrence of the
   * specified substring. The integer returned is the smallest value
   * <i>k</i> such that:
   * <blockquote><pre>
   * this.startsWith(str, <i>k</i>)
   * </pre></blockquote>
   * is <code>true</code>.
   *
   * <p>
   *  返回此字符串中指定子字符串第一次出现的索引。
   * 返回的整数是最小值<i> k </i>,以便：<blockquote> <pre> this.startsWith(str,<i> k </i>)</pre> </blockquote> is <code >
   *  true </code>。
   *  返回此字符串中指定子字符串第一次出现的索引。
   * 
   * 
   * @param   str   any string.
   * @return  if the string argument occurs as a substring within this
   *          object, then the index of the first character of the first
   *          such substring is returned; if it does not occur as a
   *          substring, <code>-1</code> is returned.
   * @exception java.lang.NullPointerException if <code>str</code> is
   *          <code>null</code>.
   */
  public int indexOf(XMLString str)
  {
    return str().indexOf(str.toString());
  }

  /**
   * Returns the index within this string of the first occurrence of the
   * specified substring, starting at the specified index. The integer
   * returned is the smallest value <i>k</i> such that:
   * <blockquote><pre>
   * this.startsWith(str, <i>k</i>) && (<i>k</i> >= fromIndex)
   * </pre></blockquote>
   * is <code>true</code>.
   * <p>
   * There is no restriction on the value of <code>fromIndex</code>. If
   * it is negative, it has the same effect as if it were zero: this entire
   * string may be searched. If it is greater than the length of this
   * string, it has the same effect as if it were equal to the length of
   * this string: <code>-1</code> is returned.
   *
   * <p>
   *  返回指定子字符串第一次出现的此字符串中的索引,从指定的索引开始。
   * 返回的整数是最小值<i> k </i>,使得：<blockquote> <pre> this.startsWith(str,<k>)&&(<k> = fromIndex)</pre> </blockquote>
   * 是<code> true </code>。
   *  返回指定子字符串第一次出现的此字符串中的索引,从指定的索引开始。
   * <p>
   *  对<code> fromIndex </code>的值没有限制。如果它是负数,它具有与它为零相同的效果：可以搜索整个字符串。
   * 如果它大于此字符串的长度,它具有与等于此字符串的长度相同的效果：返回<code> -1 </code>。
   * 
   * 
   * @param   str         the substring to search for.
   * @param   fromIndex   the index to start the search from.
   * @return  If the string argument occurs as a substring within this
   *          object at a starting index no smaller than
   *          <code>fromIndex</code>, then the index of the first character
   *          of the first such substring is returned. If it does not occur
   *          as a substring starting at <code>fromIndex</code> or beyond,
   *          <code>-1</code> is returned.
   * @exception java.lang.NullPointerException if <code>str</code> is
   *          <code>null</code>
   */
  public int indexOf(String str, int fromIndex)
  {
    return str().indexOf(str, fromIndex);
  }

  /**
   * Returns the index within this string of the rightmost occurrence
   * of the specified substring.  The rightmost empty string "" is
   * considered to occur at the index value <code>this.length()</code>.
   * The returned index is the largest value <i>k</i> such that
   * <blockquote><pre>
   * this.startsWith(str, k)
   * </pre></blockquote>
   * is true.
   *
   * <p>
   *  返回此字符串中指定子字符串最右侧出现的索引。最右边的空字符串""被认为发生在索引值<code> this.length()</code>。
   * 返回的索引是最大值<k> </i>,使得<blockquote> <pre> this.startsWith(str,k)</pre> </blockquote>。
   * 
   * 
   * @param   str   the substring to search for.
   * @return  if the string argument occurs one or more times as a substring
   *          within this object, then the index of the first character of
   *          the last such substring is returned. If it does not occur as
   *          a substring, <code>-1</code> is returned.
   * @exception java.lang.NullPointerException  if <code>str</code> is
   *          <code>null</code>.
   */
  public int lastIndexOf(String str)
  {
    return str().lastIndexOf(str);
  }

  /**
   * Returns the index within this string of the last occurrence of
   * the specified substring.
   *
   * <p>
   * 返回此字符串中指定子字符串的最后一次出现的索引。
   * 
   * 
   * @param   str         the substring to search for.
   * @param   fromIndex   the index to start the search from. There is no
   *          restriction on the value of fromIndex. If it is greater than
   *          the length of this string, it has the same effect as if it
   *          were equal to the length of this string: this entire string
   *          may be searched. If it is negative, it has the same effect
   *          as if it were -1: -1 is returned.
   * @return  If the string argument occurs one or more times as a substring
   *          within this object at a starting index no greater than
   *          <code>fromIndex</code>, then the index of the first character of
   *          the last such substring is returned. If it does not occur as a
   *          substring starting at <code>fromIndex</code> or earlier,
   *          <code>-1</code> is returned.
   * @exception java.lang.NullPointerException if <code>str</code> is
   *          <code>null</code>.
   */
  public int lastIndexOf(String str, int fromIndex)
  {
    return str().lastIndexOf(str, fromIndex);
  }

  /**
   * Returns a new string that is a substring of this string. The
   * substring begins with the character at the specified index and
   * extends to the end of this string. <p>
   * Examples:
   * <blockquote><pre>
   * "unhappy".substring(2) returns "happy"
   * "Harbison".substring(3) returns "bison"
   * "emptiness".substring(9) returns "" (an empty string)
   * </pre></blockquote>
   *
   * <p>
   *  返回一个新的字符串,它是此字符串的子字符串。子字符串以指定索引处的字符开始,并延伸到此字符串的结尾。
   *  <p>示例：<blockquote> <pre>"unhappy".substring(2)返回"happy""Harbison".substring(3)返回"bison""emptiness".s
   * ubstring )</pre> </blockquote>。
   *  返回一个新的字符串,它是此字符串的子字符串。子字符串以指定索引处的字符开始,并延伸到此字符串的结尾。
   * 
   * 
   * @param      beginIndex   the beginning index, inclusive.
   * @return     the specified substring.
   * @exception  IndexOutOfBoundsException  if
   *             <code>beginIndex</code> is negative or larger than the
   *             length of this <code>String</code> object.
   */
  public XMLString substring(int beginIndex)
  {
    return new XString(str().substring(beginIndex));
  }

  /**
   * Returns a new string that is a substring of this string. The
   * substring begins at the specified <code>beginIndex</code> and
   * extends to the character at index <code>endIndex - 1</code>.
   * Thus the length of the substring is <code>endIndex-beginIndex</code>.
   *
   * <p>
   *  返回一个新的字符串,它是此字符串的子字符串。子字符串从指定的<code> beginIndex </code>开始,并延伸到索引<code> endIndex  -  1 </code>的字符。
   * 因此,子串的长度为<code> endIndex-beginIndex </code>。
   * 
   * 
   * @param      beginIndex   the beginning index, inclusive.
   * @param      endIndex     the ending index, exclusive.
   * @return     the specified substring.
   * @exception  IndexOutOfBoundsException  if the
   *             <code>beginIndex</code> is negative, or
   *             <code>endIndex</code> is larger than the length of
   *             this <code>String</code> object, or
   *             <code>beginIndex</code> is larger than
   *             <code>endIndex</code>.
   */
  public XMLString substring(int beginIndex, int endIndex)
  {
    return new XString(str().substring(beginIndex, endIndex));
  }

  /**
   * Concatenates the specified string to the end of this string.
   *
   * <p>
   *  将指定的字符串连接到此字符串的结尾。
   * 
   * 
   * @param   str   the <code>String</code> that is concatenated to the end
   *                of this <code>String</code>.
   * @return  a string that represents the concatenation of this object's
   *          characters followed by the string argument's characters.
   * @exception java.lang.NullPointerException if <code>str</code> is
   *          <code>null</code>.
   */
  public XMLString concat(String str)
  {

    // %REVIEW% Make an FSB here?
    return new XString(str().concat(str));
  }

  /**
   * Converts all of the characters in this <code>String</code> to lower
   * case using the rules of the given <code>Locale</code>.
   *
   * <p>
   *  将<code> String </code>中的所有字符转换为小写,使用给定的<code>语言环境</code>的规则。
   * 
   * 
   * @param locale use the case transformation rules for this locale
   * @return the String, converted to lowercase.
   * @see     java.lang.Character#toLowerCase(char)
   * @see     java.lang.String#toUpperCase(Locale)
   */
  public XMLString toLowerCase(Locale locale)
  {
    return new XString(str().toLowerCase(locale));
  }

  /**
   * Converts all of the characters in this <code>String</code> to lower
   * case using the rules of the default locale, which is returned
   * by <code>Locale.getDefault</code>.
   * <p>
   *
   * <p>
   *  将<code> String </code>中的所有字符转换为小写,使用默认语言环境的规则,由<code> Locale.getDefault </code>返回。
   * <p>
   * 
   * 
   * @return  the string, converted to lowercase.
   * @see     java.lang.Character#toLowerCase(char)
   * @see     java.lang.String#toLowerCase(Locale)
   */
  public XMLString toLowerCase()
  {
    return new XString(str().toLowerCase());
  }

  /**
   * Converts all of the characters in this <code>String</code> to upper
   * case using the rules of the given locale.
   * <p>
   *  将<code> String </code>中的所有字符转换为大写,使用给定语言环境的规则。
   * 
   * 
   * @param locale use the case transformation rules for this locale
   * @return the String, converted to uppercase.
   * @see     java.lang.Character#toUpperCase(char)
   * @see     java.lang.String#toLowerCase(Locale)
   */
  public XMLString toUpperCase(Locale locale)
  {
    return new XString(str().toUpperCase(locale));
  }

  /**
   * Converts all of the characters in this <code>String</code> to upper
   * case using the rules of the default locale, which is returned
   * by <code>Locale.getDefault</code>.
   *
   * <p>
   * If no character in this string has a different uppercase version,
   * based on calling the <code>toUpperCase</code> method defined by
   * <code>Character</code>, then the original string is returned.
   * <p>
   * Otherwise, this method creates a new <code>String</code> object
   * representing a character sequence identical in length to the
   * character sequence represented by this <code>String</code> object and
   * with every character equal to the result of applying the method
   * <code>Character.toUpperCase</code> to the corresponding character of
   * this <code>String</code> object. <p>
   * Examples:
   * <blockquote><pre>
   * "Fahrvergn&uuml;gen".toUpperCase() returns "FAHRVERGN&Uuml;GEN"
   * "Visit Ljubinje!".toUpperCase() returns "VISIT LJUBINJE!"
   * </pre></blockquote>
   *
   * <p>
   *  将<code> String </code>中的所有字符转换为大写,使用默认语言环境的规则,由<code> Locale.getDefault </code>返回。
   * 
   * <p>
   * 如果此字符串中没有字符具有不同的大写版本,则基于调用由<code> Character </code>定义的<code> toUpperCase </code>方法,返回原始字符串。
   * <p>
   *  否则,此方法创建一个新的<code> String </code>对象,该对象表示长度与由此<code> String </code>对象表示的字符序列相同的字符序列,并且每个字符等于应用方法<code>
   *  Character.toUpperCase </code>到此<code> String </code>对象的相应字符。
   *  <p>示例：<blockquote> <pre>"Fahrvergn&uuml; gen".toUpperCase()返回"FAHRVERGN&Uuml; GEN""访问Ljubinje！"toUpp
   * erCase()返回"VISIT LJUBINJE！ </pre> </blockquote>。
   * 
   * 
   * @return  the string, converted to uppercase.
   * @see     java.lang.Character#toUpperCase(char)
   * @see     java.lang.String#toUpperCase(Locale)
   */
  public XMLString toUpperCase()
  {
    return new XString(str().toUpperCase());
  }

  /**
   * Removes white space from both ends of this string.
   *
   * <p>
   *  从此字符串的两端删除空格。
   * 
   * 
   * @return  this string, with white space removed from the front and end.
   */
  public XMLString trim()
  {
    return new XString(str().trim());
  }

  /**
   * Returns whether the specified <var>ch</var> conforms to the XML 1.0 definition
   * of whitespace.  Refer to <A href="http://www.w3.org/TR/1998/REC-xml-19980210#NT-S">
   * the definition of <CODE>S</CODE></A> for details.
   * <p>
   *  返回指定的<var> ch </var>是否符合空格的XML 1.0定义。
   * 有关详细信息,请参阅<A href="http://www.w3.org/TR/1998/REC-xml-19980210#NT-S"> <CODE> S </CODE> </A>的定义。
   * 
   * 
   * @param   ch      Character to check as XML whitespace.
   * @return          =true if <var>ch</var> is XML whitespace; otherwise =false.
   */
  private static boolean isSpace(char ch)
  {
    return XMLCharacterRecognizer.isWhiteSpace(ch);  // Take the easy way out for now.
  }

  /**
   * Conditionally trim all leading and trailing whitespace in the specified String.
   * All strings of white space are
   * replaced by a single space character (#x20), except spaces after punctuation which
   * receive double spaces if doublePunctuationSpaces is true.
   * This function may be useful to a formatter, but to get first class
   * results, the formatter should probably do it's own white space handling
   * based on the semantics of the formatting object.
   *
   * <p>
   *  有条件地修剪指定字符串中的所有前导和尾随空格。所有空白字符串都由单个空格字符(#x20)替换,除非在doublePunctuationSpaces为true时接收双空格的空格。
   * 这个函数可能对格式化程序很有用,但是为了获得第一类结果,格式化程序应该基于格式化对象的语义来进行自己的空白处理。
   * 
   * @param   trimHead    Trim leading whitespace?
   * @param   trimTail    Trim trailing whitespace?
   * @param   doublePunctuationSpaces    Use double spaces for punctuation?
   * @return              The trimmed string.
   */
  public XMLString fixWhiteSpace(boolean trimHead, boolean trimTail,
                                 boolean doublePunctuationSpaces)
  {

    // %OPT% !!!!!!!
    int len = this.length();
    char[] buf = new char[len];

    this.getChars(0, len, buf, 0);

    boolean edit = false;
    int s;

    for (s = 0; s < len; s++)
    {
      if (isSpace(buf[s]))
      {
        break;
      }
    }

    /* replace S to ' '. and ' '+ -> single ' '. */
    int d = s;
    boolean pres = false;

    for (; s < len; s++)
    {
      char c = buf[s];

      if (isSpace(c))
      {
        if (!pres)
        {
          if (' ' != c)
          {
            edit = true;
          }

          buf[d++] = ' ';

          if (doublePunctuationSpaces && (s != 0))
          {
            char prevChar = buf[s - 1];

            if (!((prevChar == '.') || (prevChar == '!')
                  || (prevChar == '?')))
            {
              pres = true;
            }
          }
          else
          {
            pres = true;
          }
        }
        else
        {
          edit = true;
          pres = true;
        }
      }
      else
      {
        buf[d++] = c;
        pres = false;
      }
    }

    if (trimTail && 1 <= d && ' ' == buf[d - 1])
    {
      edit = true;

      d--;
    }

    int start = 0;

    if (trimHead && 0 < d && ' ' == buf[0])
    {
      edit = true;

      start++;
    }

    XMLStringFactory xsf = XMLStringFactoryImpl.getFactory();

    return edit ? xsf.newstr(new String(buf, start, d - start)) : this;
  }

  /**
  /* <p>
  /* 
  /* 
   * @see com.sun.org.apache.xpath.internal.XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
        visitor.visitStringLiteral(owner, this);
  }

}

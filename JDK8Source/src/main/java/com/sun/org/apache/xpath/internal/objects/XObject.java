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
 * $Id: XObject.java,v 1.2.4.1 2005/09/14 20:34:45 jeffsuttor Exp $
 * <p>
 *  $ Id：XObject.java,v 1.2.4.1 2005/09/14 20:34:45 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.objects;

import java.io.Serializable;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.utils.XMLString;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.NodeSetDTM;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathException;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

/**
 * This class represents an XPath object, and is capable of
 * converting the object to various types, such as a string.
 * This class acts as the base class to other XPath type objects,
 * such as XString, and provides polymorphic casting capabilities.
 * @xsl.usage general
 * <p>
 *  此类表示XPath对象,并且能够将对象转换为各种类型,例如字符串。此类充当其他XPath类型对象(如XString)的基类,并提供多态转换功能。 @ xsl.usage general
 * 
 */
public class XObject extends Expression implements Serializable, Cloneable
{
    static final long serialVersionUID = -821887098985662951L;

  /**
   * The java object which this object wraps.
   * <p>
   *  此对象包装的java对象。
   * 
   * 
   *  @serial
   */
  protected Object m_obj;  // This may be NULL!!!

  /**
   * Create an XObject.
   * <p>
   *  创建XObject。
   * 
   */
  public XObject(){}

  /**
   * Create an XObject.
   *
   * <p>
   *  创建XObject。
   * 
   * 
   * @param obj Can be any object, should be a specific type
   * for derived classes, or null.
   */
  public XObject(Object obj)
  {
    setObject(obj);
  }

  protected void setObject(Object obj) {
      m_obj = obj;
  }

  /**
   * For support of literal objects in xpaths.
   *
   * <p>
   *  用于支持xpath中的文字对象。
   * 
   * 
   * @param xctxt The XPath execution context.
   *
   * @return This object.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
    return this;
  }

  /**
   * Specify if it's OK for detach to release the iterator for reuse.
   * This function should be called with a value of false for objects that are
   * stored in variables.
   * Calling this with a value of false on a XNodeSet will cause the nodeset
   * to be cached.
   *
   * <p>
   *  指定detach是否可以释放迭代器以便重用。对于存储在变量中的对象,应调用此函数的值为false。在XNodeSet上使用false值调用此方法将导致节点集被缓存。
   * 
   * 
   * @param allowRelease true if it is OK for detach to release this iterator
   * for pooling.
   */
  public void allowDetachToRelease(boolean allowRelease){}

  /**
   * Detaches the <code>DTMIterator</code> from the set which it iterated
   * over, releasing any computational resources and placing the iterator
   * in the INVALID state. After <code>detach</code> has been invoked,
   * calls to <code>nextNode</code> or <code>previousNode</code> will
   * raise a runtime exception.
   * <p>
   * 从迭代的集合中分离<code> DTMIterator </code>,释放任何计算资源并将迭代器置于INVALID状态。
   * 调用<code> detach </code>后,对<code> nextNode </code>或<code> previousNode </code>的调用将引发运行时异常。
   * 
   */
  public void detach(){}

  /**
   * Forces the object to release it's resources.  This is more harsh than
   * detach().
   * <p>
   *  强制对象释放其资源。这比detach()更严酷。
   * 
   */
  public void destruct()
  {

    if (null != m_obj)
    {
      allowDetachToRelease(true);
      detach();

      setObject(null);
    }
  }

  /**
   * Reset for fresh reuse.
   * <p>
   *  重置为重新使用。
   * 
   */
  public void reset()
  {
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
    xstr().dispatchCharactersEvents(ch);
  }

  /**
   * Create the right XObject based on the type of the object passed.  This
   * function can not make an XObject that exposes DOM Nodes, NodeLists, and
   * NodeIterators to the XSLT stylesheet as node-sets.
   *
   * <p>
   *  基于传递的对象的类型创建正确的XObject。此函数不能创建将DOM节点,节点列表和NodeIterator作为节点集公开到XSLT样式表的XObject。
   * 
   * 
   * @param val The java object which this object will wrap.
   *
   * @return the right XObject based on the type of the object passed.
   */
  static public XObject create(Object val)
  {
    return XObjectFactory.create(val);
  }

  /**
   * Create the right XObject based on the type of the object passed.
   * This function <emph>can</emph> make an XObject that exposes DOM Nodes, NodeLists, and
   * NodeIterators to the XSLT stylesheet as node-sets.
   *
   * <p>
   *  基于传递的对象的类型创建正确的XObject。此函数<emph>可以</emph>制作一个XObject,将DOM节点,NodeLists和NodeIterator暴露给XSLT样式表作为节点集。
   * 
   * 
   * @param val The java object which this object will wrap.
   * @param xctxt The XPath context.
   *
   * @return the right XObject based on the type of the object passed.
   */
  static public XObject create(Object val, XPathContext xctxt)
  {
    return XObjectFactory.create(val, xctxt);
  }

  /** Constant for NULL object type */
  public static final int CLASS_NULL = -1;

  /** Constant for UNKNOWN object type */
  public static final int CLASS_UNKNOWN = 0;

  /** Constant for BOOLEAN  object type */
  public static final int CLASS_BOOLEAN = 1;

  /** Constant for NUMBER object type */
  public static final int CLASS_NUMBER = 2;

  /** Constant for STRING object type */
  public static final int CLASS_STRING = 3;

  /** Constant for NODESET object type */
  public static final int CLASS_NODESET = 4;

  /** Constant for RESULT TREE FRAGMENT object type */
  public static final int CLASS_RTREEFRAG = 5;

  /** Represents an unresolved variable type as an integer. */
  public static final int CLASS_UNRESOLVEDVARIABLE = 600;

  /**
   * Tell what kind of class this is.
   *
   * <p>
   *  告诉这是什么类的类。
   * 
   * 
   * @return CLASS_UNKNOWN
   */
  public int getType()
  {
    return CLASS_UNKNOWN;
  }

  /**
   * Given a request type, return the equivalent string.
   * For diagnostic purposes.
   *
   * <p>
   *  给定一个请求类型,返回等效的字符串。用于诊断目的。
   * 
   * 
   * @return type string "#UNKNOWN" + object class name
   */
  public String getTypeString()
  {
    return "#UNKNOWN (" + object().getClass().getName() + ")";
  }

  /**
   * Cast result object to a number. Always issues an error.
   *
   * <p>
   *  将结果对象转换为数字。始终发出错误。
   * 
   * 
   * @return 0.0
   *
   * @throws javax.xml.transform.TransformerException
   */
  public double num() throws javax.xml.transform.TransformerException
  {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NUMBER,
          new Object[]{ getTypeString() });  //"Can not convert "+getTypeString()+" to a number");

    return 0.0;
  }

  /**
   * Cast result object to a number, but allow side effects, such as the
   * incrementing of an iterator.
   *
   * <p>
   *  将结果对象转换为数字,但允许出现副作用,例如迭代器的递增。
   * 
   * 
   * @return numeric value of the string conversion from the
   * next node in the NodeSetDTM, or NAN if no node was found
   */
  public double numWithSideEffects()  throws javax.xml.transform.TransformerException
  {
    return num();
  }

  /**
   * Cast result object to a boolean. Always issues an error.
   *
   * <p>
   *  将结果对象转换为布尔值。始终发出错误。
   * 
   * 
   * @return false
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean bool() throws javax.xml.transform.TransformerException
  {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NUMBER,
          new Object[]{ getTypeString() });  //"Can not convert "+getTypeString()+" to a number");

    return false;
  }

  /**
   * Cast result object to a boolean, but allow side effects, such as the
   * incrementing of an iterator.
   *
   * <p>
   *  将结果对象转换为布尔值,但允许副作用,例如迭代器的递增。
   * 
   * 
   * @return True if there is a next node in the nodeset
   */
  public boolean boolWithSideEffects() throws javax.xml.transform.TransformerException
  {
    return bool();
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
    return XMLStringFactoryImpl.getFactory().newstr(str());
  }

  /**
   * Cast result object to a string.
   *
   * <p>
   *  将结果对象转换为字符串。
   * 
   * 
   * @return The object as a string
   */
  public String str()
  {
    return (m_obj != null) ? m_obj.toString() : "";
  }

  /**
   * Return the string representation of the object
   *
   *
   * <p>
   * 返回对象的字符串表示形式
   * 
   * 
   * @return the string representation of the object
   */
  public String toString()
  {
    return str();
  }

  /**
   * Cast result object to a result tree fragment.
   *
   * <p>
   *  将结果对象强制转换到结果树片段。
   * 
   * 
   * @param support XPath context to use for the conversion
   *
   * @return the objec as a result tree fragment.
   */
  public int rtf(XPathContext support)
  {

    int result = rtf();

    if (DTM.NULL == result)
    {
      DTM frag = support.createDocumentFragment();

      // %OPT%
      frag.appendTextChild(str());

      result = frag.getDocument();
    }

    return result;
  }

  /**
   * Cast result object to a result tree fragment.
   *
   * <p>
   *  将结果对象强制转换到结果树片段。
   * 
   * 
   * @param support XPath context to use for the conversion
   *
   * @return the objec as a result tree fragment.
   */
  public DocumentFragment rtree(XPathContext support)
  {
    DocumentFragment docFrag = null;
    int result = rtf();

    if (DTM.NULL == result)
    {
      DTM frag = support.createDocumentFragment();

      // %OPT%
      frag.appendTextChild(str());

      docFrag = (DocumentFragment)frag.getNode(frag.getDocument());
    }
    else
    {
      DTM frag = support.getDTM(result);
      docFrag = (DocumentFragment)frag.getNode(frag.getDocument());
    }

    return docFrag;
  }


  /**
   * For functions to override.
   *
   * <p>
   *  用于覆盖的功能。
   * 
   * 
   * @return null
   */
  public DocumentFragment rtree()
  {
    return null;
  }

  /**
   * For functions to override.
   *
   * <p>
   *  用于覆盖的功能。
   * 
   * 
   * @return null
   */
  public int rtf()
  {
    return DTM.NULL;
  }

  /**
   * Return a java object that's closest to the representation
   * that should be handed to an extension.
   *
   * <p>
   *  返回最接近应该传递给扩展的表示的java对象。
   * 
   * 
   * @return The object that this class wraps
   */
  public Object object()
  {
    return m_obj;
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * <p>
   *  将结果对象转换为节点列表。始终发出错误。
   * 
   * 
   * @return null
   *
   * @throws javax.xml.transform.TransformerException
   */
  public DTMIterator iter() throws javax.xml.transform.TransformerException
  {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NODELIST,
          new Object[]{ getTypeString() });  //"Can not convert "+getTypeString()+" to a NodeList!");

    return null;
  }

  /**
   * Get a fresh copy of the object.  For use with variables.
   *
   * <p>
   *  获取对象的新副本。用于变量。
   * 
   * 
   * @return This object, unless overridden by subclass.
   */
  public XObject getFresh()
  {
    return this;
  }


  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * <p>
   *  将结果对象转换为节点列表。始终发出错误。
   * 
   * 
   * @return null
   *
   * @throws javax.xml.transform.TransformerException
   */
  public NodeIterator nodeset() throws javax.xml.transform.TransformerException
  {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NODELIST,
          new Object[]{ getTypeString() });  //"Can not convert "+getTypeString()+" to a NodeList!");

    return null;
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * <p>
   *  将结果对象转换为节点列表。始终发出错误。
   * 
   * 
   * @return null
   *
   * @throws javax.xml.transform.TransformerException
   */
  public NodeList nodelist() throws javax.xml.transform.TransformerException
  {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_NODELIST,
          new Object[]{ getTypeString() });  //"Can not convert "+getTypeString()+" to a NodeList!");

    return null;
  }


  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * <p>
   *  将结果对象转换为节点列表。始终发出错误。
   * 
   * 
   * @return The object as a NodeSetDTM.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public NodeSetDTM mutableNodeset()
          throws javax.xml.transform.TransformerException
  {

    error(XPATHErrorResources.ER_CANT_CONVERT_TO_MUTABLENODELIST,
          new Object[]{ getTypeString() });  //"Can not convert "+getTypeString()+" to a NodeSetDTM!");

    return (NodeSetDTM) m_obj;
  }

  /**
   * Cast object to type t.
   *
   * <p>
   *  将对象强制转换为类型t。
   * 
   * 
   * @param t Type of object to cast this to
   * @param support XPath context to use for the conversion
   *
   * @return This object as the given type t
   *
   * @throws javax.xml.transform.TransformerException
   */
  public Object castToType(int t, XPathContext support)
          throws javax.xml.transform.TransformerException
  {

    Object result;

    switch (t)
    {
    case CLASS_STRING :
      result = str();
      break;
    case CLASS_NUMBER :
      result = new Double(num());
      break;
    case CLASS_NODESET :
      result = iter();
      break;
    case CLASS_BOOLEAN :
      result = new Boolean(bool());
      break;
    case CLASS_UNKNOWN :
      result = m_obj;
      break;

    // %TBD%  What to do here?
    //    case CLASS_RTREEFRAG :
    //      result = rtree(support);
    //      break;
    default :
      error(XPATHErrorResources.ER_CANT_CONVERT_TO_TYPE,
            new Object[]{ getTypeString(),
                          Integer.toString(t) });  //"Can not convert "+getTypeString()+" to a type#"+t);

      result = null;
    }

    return result;
  }

  /**
   * Tell if one object is less than the other.
   *
   * <p>
   *  告诉一个对象是否小于另一个对象。
   * 
   * 
   * @param obj2 Object to compare this to
   *
   * @return True if this object is less than the given object
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean lessThan(XObject obj2)
          throws javax.xml.transform.TransformerException
  {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.  Because the arguments
    // are backwards, we call the opposite comparison
    // function.
    if (obj2.getType() == XObject.CLASS_NODESET)
      return obj2.greaterThan(this);

    return this.num() < obj2.num();
  }

  /**
   * Tell if one object is less than or equal to the other.
   *
   * <p>
   *  告诉一个对象是否小于或等于另一个对象。
   * 
   * 
   * @param obj2 Object to compare this to
   *
   * @return True if this object is less than or equal to the given object
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean lessThanOrEqual(XObject obj2)
          throws javax.xml.transform.TransformerException
  {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.  Because the arguments
    // are backwards, we call the opposite comparison
    // function.
    if (obj2.getType() == XObject.CLASS_NODESET)
      return obj2.greaterThanOrEqual(this);

    return this.num() <= obj2.num();
  }

  /**
   * Tell if one object is greater than the other.
   *
   * <p>
   *  告诉一个对象是否大于另一个对象。
   * 
   * 
   * @param obj2 Object to compare this to
   *
   * @return True if this object is greater than the given object
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean greaterThan(XObject obj2)
          throws javax.xml.transform.TransformerException
  {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.  Because the arguments
    // are backwards, we call the opposite comparison
    // function.
    if (obj2.getType() == XObject.CLASS_NODESET)
      return obj2.lessThan(this);

    return this.num() > obj2.num();
  }

  /**
   * Tell if one object is greater than or equal to the other.
   *
   * <p>
   *  告诉一个对象是否大于或等于另一个对象。
   * 
   * 
   * @param obj2 Object to compare this to
   *
   * @return True if this object is greater than or equal to the given object
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean greaterThanOrEqual(XObject obj2)
          throws javax.xml.transform.TransformerException
  {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.  Because the arguments
    // are backwards, we call the opposite comparison
    // function.
    if (obj2.getType() == XObject.CLASS_NODESET)
      return obj2.lessThanOrEqual(this);

    return this.num() >= obj2.num();
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
   * @return True if this object is equal to the given object
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean equals(XObject obj2)
  {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    if (obj2.getType() == XObject.CLASS_NODESET)
      return obj2.equals(this);

    if (null != m_obj)
    {
      return m_obj.equals(obj2.m_obj);
    }
    else
    {
      return obj2.m_obj == null;
    }
  }

  /**
   * Tell if two objects are functionally not equal.
   *
   * <p>
   *  告诉两个对象在功能上是否不相等。
   * 
   * 
   * @param obj2 Object to compare this to
   *
   * @return True if this object is not equal to the given object
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean notEquals(XObject obj2)
          throws javax.xml.transform.TransformerException
  {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    if (obj2.getType() == XObject.CLASS_NODESET)
      return obj2.notEquals(this);

    return !equals(obj2);
  }

  /**
   * Tell the user of an error, and probably throw an
   * exception.
   *
   * <p>
   *  告诉用户一个错误,并可能抛出异常。
   * 
   * 
   * @param msg Error message to issue
   *
   * @throws javax.xml.transform.TransformerException
   */
  protected void error(String msg)
          throws javax.xml.transform.TransformerException
  {
    error(msg, null);
  }

  /**
   * Tell the user of an error, and probably throw an
   * exception.
   *
   * <p>
   *  告诉用户一个错误,并可能抛出异常。
   * 
   * 
   * @param msg Error message to issue
   * @param args Arguments to use in the message
   *
   * @throws javax.xml.transform.TransformerException
   */
  protected void error(String msg, Object[] args)
          throws javax.xml.transform.TransformerException
  {

    String fmsg = XSLMessages.createXPATHMessage(msg, args);

    // boolean shouldThrow = support.problem(m_support.XPATHPROCESSOR,
    //                                      m_support.ERROR,
    //                                      null,
    //                                      null, fmsg, 0, 0);
    // if(shouldThrow)
    {
      throw new XPathException(fmsg, this);
    }
  }


  /**
   * XObjects should not normally need to fix up variables.
   * <p>
   *  XObjects通常不需要修复变量。
   * 
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {
    // no-op
  }


  /**
   * Cast result object to a string.
   *
   *
   * <p>
   *  将结果对象转换为字符串。
   * 
   * 
   * NEEDSDOC @param fsb
   * @return The string this wraps or the empty string if null
   */
  public void appendToFsb(com.sun.org.apache.xml.internal.utils.FastStringBuffer fsb)
  {
    fsb.append(str());
  }

  /**
  /* <p>
  /* 
   * @see com.sun.org.apache.xpath.internal.XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
        assertion(false, "callVisitors should not be called for this object!!!");
  }
  /**
  /* <p>
  /* 
   * @see Expression#deepEquals(Expression)
   */
  public boolean deepEquals(Expression expr)
  {
        if(!isSameClass(expr))
                return false;

        // If equals at the expression level calls deepEquals, I think we're
        // still safe from infinite recursion since this object overrides
        // equals.  I hope.
        if(!this.equals((XObject)expr))
                return false;

        return true;
  }

}

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
 * $Id: Arg.java,v 1.1.2.1 2005/08/01 01:30:11 jeffsuttor Exp $
 * <p>
 *  $ Id：Arg.java,v 1.1.2.1 2005/08/01 01:30:11 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import com.sun.org.apache.xml.internal.utils.QName;
import com.sun.org.apache.xpath.internal.objects.XObject;
import java.util.Objects;

/**
 * This class holds an instance of an argument on
 * the stack. The value of the argument can be either an
 * XObject or a String containing an expression.
 * @xsl.usage internal
 * <p>
 *  这个类保存了栈上一个参数的实例。参数的值可以是XObject或包含表达式的字符串。 @ xsl.usage internal
 * 
 */
public class Arg
{

  /** Field m_qname: The name of this argument, expressed as a QName
   * (Qualified Name) object.
   * <p>
   *  (限定名称)对象。
   * 
   * 
   * @see getQName
   * @see setQName
   *  */
  private QName m_qname;

  /**
   * Get the qualified name for this argument.
   *
   * <p>
   *  获取此参数的限定名称。
   * 
   * 
   * @return QName object containing the qualified name
   */
  public final QName getQName()
  {
    return m_qname;
  }

  /**
   * Set the qualified name for this argument.
   *
   * <p>
   *  设置此参数的限定名称。
   * 
   * 
   * @param name QName object representing the new Qualified Name.
   */
  public final void setQName(QName name)
  {
    m_qname = name;
  }

  /** Field m_val: Stored XObject value of this argument
  /* <p>
  /* 
   * @see #getVal()
   * @see #setVal()
   */
  private XObject m_val;

  /**
   * Get the value for this argument.
   *
   * <p>
   *  获取此参数的值。
   * 
   * 
   * @return the argument's stored XObject value.
   * @see #setVal(XObject)
   */
  public final XObject getVal()
  {
    return m_val;
  }

  /**
   * Set the value of this argument.
   *
   * <p>
   *  设置此参数的值。
   * 
   * 
   * @param val an XObject representing the arguments's value.
   * @see #getVal()
   */
  public final void setVal(XObject val)
  {
    m_val = val;
  }

  /**
   * Have the object release it's resources.
   * Call only when the variable or argument is going out of scope.
   * <p>
   *  让对象释放它的资源。只有当变量或参数超出范围时才调用。
   * 
   */
  public void detach()
  {
    if(null != m_val)
    {
      m_val.allowDetachToRelease(true);
      m_val.detach();
    }
  }


  /** Field m_expression: Stored expression value of this argument.
  /* <p>
  /* 
   * @see #setExpression
   * @see #getExpression
   * */
  private String m_expression;

  /**
   * Get the value expression for this argument.
   *
   * <p>
   *  获取此参数的值表达式。
   * 
   * 
   * @return String containing the expression previously stored into this
   * argument
   * @see #setExpression
   */
  public String getExpression()
  {
    return m_expression;
  }

  /**
   * Set the value expression for this argument.
   *
   * <p>
   *  设置此参数的值表达式。
   * 
   * 
   * @param expr String containing the expression to be stored as this
   * argument's value.
   * @see #getExpression
   */
  public void setExpression(String expr)
  {
    m_expression = expr;
  }

  /**
   * True if this variable was added with an xsl:with-param or
   * is added via setParameter.
   * <p>
   *  如果此变量添加了xsl：with-param或通过setParameter添加,则为true。
   * 
   */
  private boolean m_isFromWithParam;

  /**
   * Tell if this variable is a parameter passed with a with-param or as
   * a top-level parameter.
   * <p>
   *  告诉这个变量是使用with-param还是作为顶级参数传递的参数。
   * 
   */
   public boolean isFromWithParam()
   {
    return m_isFromWithParam;
   }

  /**
   * True if this variable is currently visible.  To be visible,
   * a variable needs to come either from xsl:variable or be
   * a "received" parameter, ie one for which an xsl:param has
   * been encountered.
   * Set at the time the object is constructed and updated as needed.
   * <p>
   * 如果此变量当前可见,则为true。为了可见,变量需要来自xsl：variable或者是"received"参数,即遇到xsl：param的参数。在对象根据需要构造和更新时设置。
   * 
   */
  private boolean m_isVisible;

  /**
   * Tell if this variable is currently visible.
   * <p>
   *  告诉这个变量目前是否可见。
   * 
   */
   public boolean isVisible()
   {
    return m_isVisible;
   }

  /**
   * Update visibility status of this variable.
   * <p>
   *  更新此变量的可见性状态。
   * 
   */
   public void setIsVisible(boolean b)
   {
    m_isVisible = b;
   }

  /**
   * Construct a dummy parameter argument, with no QName and no
   * value (either expression string or value XObject). isVisible
   * defaults to true.
   * <p>
   *  构造一个伪参数参数,没有QName和没有值(表达式字符串或值XObject)。 isVisible默认为true。
   * 
   */
  public Arg()
  {

    m_qname = new QName("");
       // so that string compares can be done.
    m_val = null;
    m_expression = null;
    m_isVisible = true;
    m_isFromWithParam = false;
  }

  /**
   * Construct a parameter argument that contains an expression.
   *
   * <p>
   *  构造包含表达式的参数参数。
   * 
   * 
   * @param qname Name of the argument, expressed as a QName object.
   * @param expression String to be stored as this argument's value expression.
   * @param isFromWithParam True if this is a parameter variable.
   */
  public Arg(QName qname, String expression, boolean isFromWithParam)
  {

    m_qname = qname;
    m_val = null;
    m_expression = expression;
    m_isFromWithParam = isFromWithParam;
    m_isVisible = !isFromWithParam;
  }

  /**
   * Construct a parameter argument which has an XObject value.
   * isVisible defaults to true.
   *
   * <p>
   *  构造具有XObject值的参数参数。 isVisible默认为true。
   * 
   * 
   * @param qname Name of the argument, expressed as a QName object.
   * @param val Value of the argument, expressed as an XObject
   */
  public Arg(QName qname, XObject val)
  {

    m_qname = qname;
    m_val = val;
    m_isVisible = true;
    m_isFromWithParam = false;
    m_expression = null;
  }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.m_qname);
    }

  /**
   * Equality function specialized for the variable name.  If the argument
   * is not a qname, it will deligate to the super class.
   *
   * <p>
   *  专用于变量名的等式函数。如果参数不是一个qname,它将分配到超类。
   * 
   * 
   * @param   obj   the reference object with which to compare.
   * @return  <code>true</code> if this object is the same as the obj
   *          argument; <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if(obj instanceof QName)
    {
      return m_qname.equals(obj);
    }
    else
      return super.equals(obj);
  }

  /**
   * Construct a parameter argument.
   *
   * <p>
   *  构造参数参数。
   * 
   * @param qname Name of the argument, expressed as a QName object.
   * @param val Value of the argument, expressed as an XObject
   * @param isFromWithParam True if this is a parameter variable.
   */
  public Arg(QName qname, XObject val, boolean isFromWithParam)
  {

    m_qname = qname;
    m_val = val;
    m_isFromWithParam = isFromWithParam;
    m_isVisible = !isFromWithParam;
    m_expression = null;
  }
}

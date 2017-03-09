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
 * $Id: Variable.java,v 1.2.4.1 2005/09/14 21:24:33 jeffsuttor Exp $
 * <p>
 *  $ Id：Variable.java,v 1.2.4.1 2005/09/14 21:24:33 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.operations;

import javax.xml.transform.TransformerException;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.utils.QName;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPath;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.axes.PathComponent;
import com.sun.org.apache.xpath.internal.axes.WalkerFactory;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;


/**
 * The variable reference expression executer.
 * <p>
 *  变量引用表达式执行器。
 * 
 */
public class Variable extends Expression implements PathComponent
{
    static final long serialVersionUID = -4334975375609297049L;
  /** Tell if fixupVariables was called.
  /* <p>
  /* 
   *  @serial   */
  private boolean m_fixUpWasCalled = false;

  /** The qualified name of the variable.
  /* <p>
  /* 
   *  @serial   */
  protected QName m_qname;

  /**
   * The index of the variable, which is either an absolute index to a
   * global, or, if higher than the globals area, must be adjusted by adding
   * the offset to the current stack frame.
   * <p>
   *  变量的索引(它是对全局的绝对索引),或者,如果高于全局变量区域,必须通过将偏移量添加到当前堆栈帧来调整。
   * 
   */
  protected int m_index;

  /**
   * Set the index for the variable into the stack.  For advanced use only. You
   * must know what you are doing to use this.
   *
   * <p>
   *  将变量的索引设置为堆栈。仅供高级使用。你必须知道你在做什么才能使用它。
   * 
   * 
   * @param index a global or local index.
   */
  public void setIndex(int index)
  {
        m_index = index;
  }

  /**
   * Set the index for the variable into the stack.  For advanced use only.
   *
   * <p>
   *  将变量的索引设置为堆栈。仅供高级使用。
   * 
   * 
   * @return index a global or local index.
   */
  public int getIndex()
  {
        return m_index;
  }

  /**
   * Set whether or not this is a global reference.  For advanced use only.
   *
   * <p>
   *  设置是否为全局引用。仅供高级使用。
   * 
   * 
   * @param isGlobal true if this should be a global variable reference.
   */
  public void setIsGlobal(boolean isGlobal)
  {
        m_isGlobal = isGlobal;
  }

  /**
   * Set the index for the variable into the stack.  For advanced use only.
   *
   * <p>
   *  将变量的索引设置为堆栈。仅供高级使用。
   * 
   * 
   * @return true if this should be a global variable reference.
   */
  public boolean getGlobal()
  {
        return m_isGlobal;
  }





  protected boolean m_isGlobal = false;

  /**
   * This function is used to fixup variables from QNames to stack frame
   * indexes at stylesheet build time.
   * <p>
   *  此函数用于在样式表构建时将QNames中的变量固定到堆栈帧索引。
   * 
   * 
   * @param vars List of QNames that correspond to variables.  This list
   * should be searched backwards for the first qualified name that
   * corresponds to the variable reference qname.  The position of the
   * QName in the vector from the start of the vector will be its position
   * in the stack frame (but variables above the globalsTop value will need
   * to be offset to the current stack frame).
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {
    m_fixUpWasCalled = true;
    int sz = vars.size();

    for (int i = vars.size()-1; i >= 0; i--)
    {
      QName qn = (QName)vars.elementAt(i);
      // System.out.println("qn: "+qn);
      if(qn.equals(m_qname))
      {

        if(i < globalsSize)
        {
          m_isGlobal = true;
          m_index = i;
        }
        else
        {
          m_index = i-globalsSize;
        }

        return;
      }
    }

    java.lang.String msg = XSLMessages.createXPATHMessage(XPATHErrorResources.ER_COULD_NOT_FIND_VAR,
                                             new Object[]{m_qname.toString()});

    TransformerException te = new TransformerException(msg, this);

    throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(te);

  }


  /**
   * Set the qualified name of the variable.
   *
   * <p>
   *  设置变量的限定名。
   * 
   * 
   * @param qname Must be a non-null reference to a qualified name.
   */
  public void setQName(QName qname)
  {
    m_qname = qname;
  }

  /**
   * Get the qualified name of the variable.
   *
   * <p>
   * 获取变量的限定名称。
   * 
   * 
   * @return A non-null reference to a qualified name.
   */
  public QName getQName()
  {
    return m_qname;
  }

  /**
   * Execute an expression in the XPath runtime context, and return the
   * result of the expression.
   *
   *
   * <p>
   *  在XPath运行时上下文中执行一个表达式,并返回表达式的结果。
   * 
   * 
   * @param xctxt The XPath runtime context.
   *
   * @return The result of the expression in the form of a <code>XObject</code>.
   *
   * @throws javax.xml.transform.TransformerException if a runtime exception
   *         occurs.
   */
  public XObject execute(XPathContext xctxt)
    throws javax.xml.transform.TransformerException
  {
        return execute(xctxt, false);
  }


  /**
   * Dereference the variable, and return the reference value.  Note that lazy
   * evaluation will occur.  If a variable within scope is not found, a warning
   * will be sent to the error listener, and an empty nodeset will be returned.
   *
   *
   * <p>
   *  取消引用变量,并返回参考值。注意,会发生延迟评估。如果未找到范围内的变量,将向错误侦听器发送警告,并返回空节点集。
   * 
   * 
   * @param xctxt The runtime execution context.
   *
   * @return The evaluated variable, or an empty nodeset if not found.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt, boolean destructiveOK) throws javax.xml.transform.TransformerException
  {
    com.sun.org.apache.xml.internal.utils.PrefixResolver xprefixResolver = xctxt.getNamespaceContext();

    XObject result;
    // Is the variable fetched always the same?
    // XObject result = xctxt.getVariable(m_qname);
    if(m_fixUpWasCalled)
    {
      if(m_isGlobal)
        result = xctxt.getVarStack().getGlobalVariable(xctxt, m_index, destructiveOK);
      else
        result = xctxt.getVarStack().getLocalVariable(xctxt, m_index, destructiveOK);
    }
    else {
        result = xctxt.getVarStack().getVariableOrParam(xctxt,m_qname);
    }

      if (null == result)
      {
        // This should now never happen...
        warn(xctxt, XPATHErrorResources.WG_ILLEGAL_VARIABLE_REFERENCE,
             new Object[]{ m_qname.getLocalPart() });  //"VariableReference given for variable out "+
  //      (new RuntimeException()).printStackTrace();
  //      error(xctxt, XPATHErrorResources.ER_COULDNOT_GET_VAR_NAMED,
  //            new Object[]{ m_qname.getLocalPart() });  //"Could not get variable named "+varName);

        result = new XNodeSet(xctxt.getDTMManager());
      }

      return result;
//    }
//    else
//    {
//      // Hack city... big time.  This is needed to evaluate xpaths from extensions,
//      // pending some bright light going off in my head.  Some sort of callback?
//      synchronized(this)
//      {
//              com.sun.org.apache.xalan.internal.templates.ElemVariable vvar= getElemVariable();
//              if(null != vvar)
//              {
//          m_index = vvar.getIndex();
//          m_isGlobal = vvar.getIsTopLevel();
//          m_fixUpWasCalled = true;
//          return execute(xctxt);
//              }
//      }
//      throw new javax.xml.transform.TransformerException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_VAR_NOT_RESOLVABLE, new Object[]{m_qname.toString()})); //"Variable not resolvable: "+m_qname);
//    }
  }

  /**
   * Get the XSLT ElemVariable that this sub-expression references.  In order for
   * this to work, the SourceLocator must be the owning ElemTemplateElement.
   * <p>
   *  获取此子表达式引用的XSLT ElemVariable。为了使这个工作,SourceLocator必须是拥有ElemTemplateElement。
   * 
   * 
   * @return The dereference to the ElemVariable, or null if not found.
   */
  // J2SE does not support Xalan interpretive
  /*
  public com.sun.org.apache.xalan.internal.templates.ElemVariable getElemVariable()
  {

    // Get the current ElemTemplateElement, and then walk backwards in
    // document order, searching
    // for an xsl:param element or xsl:variable element that matches our
    // qname.  If we reach the top level, use the StylesheetRoot's composed
    // list of top level variables and parameters.

    com.sun.org.apache.xalan.internal.templates.ElemVariable vvar = null;
    com.sun.org.apache.xpath.internal.ExpressionNode owner = getExpressionOwner();

    if (null != owner && owner instanceof com.sun.org.apache.xalan.internal.templates.ElemTemplateElement)
    {

      com.sun.org.apache.xalan.internal.templates.ElemTemplateElement prev =
        (com.sun.org.apache.xalan.internal.templates.ElemTemplateElement) owner;

      if (!(prev instanceof com.sun.org.apache.xalan.internal.templates.Stylesheet))
      {
        while ( prev != null && !(prev.getParentNode() instanceof com.sun.org.apache.xalan.internal.templates.Stylesheet) )
        {
          com.sun.org.apache.xalan.internal.templates.ElemTemplateElement savedprev = prev;

          while (null != (prev = prev.getPreviousSiblingElem()))
          {
            if(prev instanceof com.sun.org.apache.xalan.internal.templates.ElemVariable)
            {
              vvar = (com.sun.org.apache.xalan.internal.templates.ElemVariable) prev;

              if (vvar.getName().equals(m_qname))
              {
                return vvar;
              }
              vvar = null;
            }
          }
          prev = savedprev.getParentElem();
        }
      }
      if (prev != null)
        vvar = prev.getStylesheetRoot().getVariableOrParamComposed(m_qname);
    }
    return vvar;

  }
  /* <p>
  /*  public com.sun.org.apache.xalan.internal.templates.ElemVariable getElemVariable(){
  /* 
  /*  //获取当前ElemTemplateElement,然后以//文档顺序向后走,搜索与我们的// qname匹配的xsl：param元素或xsl：variable元素。
  /* 如果我们达到顶级,使用StylesheetRoot的组成的顶级变量和参数的列表。
  /* 
  /*  com.sun.org.apache.xalan.internal.templates.ElemVariable vvar = null; com.sun.org.apache.xpath.inter
  /* nal.ExpressionNode owner = getExpressionOwner();。
  /* 
  /*  if(null！= owner && owner instanceof com.sun.org.apache.xalan.internal.templates.ElemTemplateElement)
  /* {。
  /* 
  /*  com.sun.org.apache.xalan.internal.templates.ElemTemplateElement prev =(com.sun.org.apache.xalan.inte
  /* rnal.templates.ElemTemplateElement)owner;。
  /* 
  /* if(！(prev instanceof com.sun.org.apache.xalan.internal.templates.Stylesheet)){while(prev！= null &&！(prev.getParentNode()instanceof com.sun.org.apache.xalan.internal .templates.Stylesheet)){com.sun.org.apache.xalan.internal.templates.ElemTemplateElement savedprev = prev;。
  /* 
  /*  while(null！=(prev = prev.getPreviousSiblingElem())){if(previous instanceof com.sun.org.apache.xalan.internal.templates.ElemVariable){vvar =(com.sun.org.apache.xalan。
  /*  internal.templates.ElemVariable)prev;。
  /* 
  /*  if(vvar.getName()。
  /* equals(m_qname)){return vvar; } vvar = null; }} prev = savedprev.getParentElem(); }} if(prev！= null)v
  /* var = prev.getStylesheetRoot()。
  /*  if(vvar.getName()。getVariableOrParamComposed(m_qname); } return vvar;。
  */
  /**
   * Tell if this expression returns a stable number that will not change during
   * iterations within the expression.  This is used to determine if a proximity
   * position predicate can indicate that no more searching has to occur.
   *
   *
   * <p>
   * 
   *  }}
   * 
   * 
   * @return true if the expression represents a stable number.
   */
  public boolean isStableNumber()
  {
    return true;
  }

  /**
   * Get the analysis bits for this walker, as defined in the WalkerFactory.
   * <p>
   *  告诉这个表达式是否返回一个稳定的数字,在表达式的迭代期间不会改变。这用于确定接近位置谓词是否可以指示不必进行更多搜索。
   * 
   * 
   * @return One of WalkerFactory#BIT_DESCENDANT, etc.
   */
  public int getAnalysisBits()
  {

    // J2SE does not support Xalan interpretive
    /*
        com.sun.org.apache.xalan.internal.templates.ElemVariable vvar = getElemVariable();
        if(null != vvar)
        {
                XPath xpath = vvar.getSelect();
                if(null != xpath)
                {
                        Expression expr = xpath.getExpression();
                        if(null != expr && expr instanceof PathComponent)
                        {
                                return ((PathComponent)expr).getAnalysisBits();
                        }
                }
        }
    /* <p>
    /*  获取WalkerFactory中定义的此Walker的分析位。
    /* 
    */

    return WalkerFactory.BIT_FILTER;
  }


  /**
  /* <p>
  /*  com.sun.org.apache.xalan.internal.templates.ElemVariable vvar = getElemVariable(); if(null！= vvar){XPath xpath = vvar.getSelect(); if(null！= xpath){expression expr = xpath.getExpression(); if(null！= expr && expr instanceof PathComponent){return((PathComponent)expr).getAnalysisBits(); }
  /* }}。
  /* 
  /* 
   * @see com.sun.org.apache.xpath.internal.XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
        visitor.visitVariableRef(owner, this);
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

        if(!m_qname.equals(((Variable)expr).m_qname))
                return false;

    // J2SE does not support Xalan interpretive
    /*
        // We have to make sure that the qname really references
        // the same variable element.
    if(getElemVariable() != ((Variable)expr).getElemVariable())
        return false;
    /* <p>
        */

        return true;
  }

  static final java.lang.String PSUEDOVARNAMESPACE = "http://xml.apache.org/xalan/psuedovar";

  /**
   * Tell if this is a psuedo variable reference, declared by Xalan instead
   * of by the user.
   * <p>
   *  //我们必须确保qname真的引用了//同一个变量元素。
   *  if(getElemVariable()！=((Variable)expr).getElemVariable())return false;。
   * 
   */
  public boolean isPsuedoVarRef()
  {
        java.lang.String ns = m_qname.getNamespaceURI();
        if((null != ns) && ns.equals(PSUEDOVARNAMESPACE))
        {
                if(m_qname.getLocalName().startsWith("#"))
                        return true;
        }
        return false;
  }


}

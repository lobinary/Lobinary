/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: VariableRefBase.java,v 1.5 2005/09/28 13:48:18 pvedula Exp $
 * <p>
 *  $ Id：VariableRefBase.java,v 1.5 2005/09/28 13:48:18 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import java.util.Objects;

/**
/* <p>
/* 
 * @author Morten Jorgensen
 * @author Santiago Pericas-Geertsen
 */
class VariableRefBase extends Expression {

    /**
     * A reference to the associated variable.
     * <p>
     *  对相关变量的引用。
     * 
     */
    protected VariableBase _variable;

    /**
     * A reference to the enclosing expression/instruction for which a
     * closure is needed (Predicate, Number or Sort).
     * <p>
     *  引用需要关闭的封闭表达式/指令(谓词,数字或排序)。
     * 
     */
    protected Closure _closure = null;

    public VariableRefBase(VariableBase variable) {
        _variable = variable;
        variable.addReference(this);
    }

    public VariableRefBase() {
        _variable = null;
    }

    /**
     * Returns a reference to the associated variable
     * <p>
     *  返回对关联变量的引用
     * 
     */
    public VariableBase getVariable() {
        return _variable;
    }

    /**
     * If this variable reference is in a top-level element like
     * another variable, param or key, add a dependency between
     * that top-level element and the referenced variable. For
     * example,
     *
     *   <xsl:variable name="x" .../>
     *   <xsl:variable name="y" select="$x + 1"/>
     *
     * and assuming this class represents "$x", add a reference
     * between variable y and variable x.
     * <p>
     *  如果此变量引用位于顶级元素(如另一个变量,参数或键)中,则在该顶级元素和引用的变量之间添加一个依赖关系。例如,
     * 
     * <xsl:variable name="x" .../>
     * <xsl:variable name="y" select="$x + 1"/>
     * 
     *  并假设此类表示"$ x",则在变量y和变量x之间添加引用。
     * 
     */
    public void addParentDependency() {
        SyntaxTreeNode node = this;
        while (node != null && node instanceof TopLevelElement == false) {
            node = node.getParent();
        }

        TopLevelElement parent = (TopLevelElement) node;
        if (parent != null) {
            VariableBase var = _variable;
            if (_variable._ignore) {
                if (_variable instanceof Variable) {
                    var = parent.getSymbolTable()
                                .lookupVariable(_variable._name);
                } else if (_variable instanceof Param) {
                    var = parent.getSymbolTable().lookupParam(_variable._name);
                }
            }

            parent.addDependency(var);
        }
    }

    /**
     * Two variable references are deemed equal if they refer to the
     * same variable.
     * <p>
     */
    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof VariableRefBase)
            && (_variable == ((VariableRefBase) obj)._variable);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this._variable);
    }

    /**
     * Returns a string representation of this variable reference on the
     * format 'variable-ref(<var-name>)'.
     * <p>
     *  如果两个变量引用相同的变量,则它们被认为是相等的。
     * 
     * 
     * @return Variable reference description
     */
    @Override
    public String toString() {
        return "variable-ref("+_variable.getName()+'/'+_variable.getType()+')';
    }

    @Override
    public Type typeCheck(SymbolTable stable)
        throws TypeCheckError
    {
        // Returned cached type if available
        if (_type != null) return _type;

        // Find nearest closure to add a variable reference
        if (_variable.isLocal()) {
            SyntaxTreeNode node = getParent();
            do {
                if (node instanceof Closure) {
                    _closure = (Closure) node;
                    break;
                }
                if (node instanceof TopLevelElement) {
                    break;      // way up in the tree
                }
                node = node.getParent();
            } while (node != null);

            if (_closure != null) {
                _closure.addVariable(this);
            }
        }

        // Attempt to get the cached variable type
        _type = _variable.getType();

        // If that does not work we must force a type-check (this is normally
        // only needed for globals in included/imported stylesheets
        if (_type == null) {
            _variable.typeCheck(stable);
            _type = _variable.getType();
        }

        // If in a top-level element, create dependency to the referenced var
        addParentDependency();

        // Return the type of the referenced variable
        return _type;
    }

}

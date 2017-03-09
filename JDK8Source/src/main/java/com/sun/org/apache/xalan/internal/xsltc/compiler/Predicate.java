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
 * $Id: Predicate.java,v 1.2.4.1 2005/09/12 11:02:18 pvedula Exp $
 * <p>
 *  $ Id：Predicate.java,v 1.2.4.1 2005/09/12 11:02:18 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.FilterGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NumberType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TestGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class Predicate extends Expression implements Closure {

    /**
     * The predicate's expression.
     * <p>
     *  谓词的表达式。
     * 
     */
    private Expression _exp = null;

    /**
     * This flag indicates if optimizations are turned on. The
     * method <code>dontOptimize()</code> can be called to turn
     * optimizations off.
     * <p>
     *  此标志指示是否启用优化。可以调用方法<code> dontOptimize()</code>关闭优化。
     * 
     */
    private boolean _canOptimize = true;

    /**
     * Flag indicatig if the nth position optimization is on. It
     * is set in <code>typeCheck()</code>.
     * <p>
     *  标志指示如果第n个位置优化开启。它设置在<code> typeCheck()</code>中。
     * 
     */
    private boolean _nthPositionFilter = false;

    /**
     * Flag indicatig if the nth position descendant is on. It
     * is set in <code>typeCheck()</code>.
     * <p>
     *  标志指示第n个位置后代是否开启。它设置在<code> typeCheck()</code>中。
     * 
     */
    private boolean _nthDescendant = false;

    /**
     * Cached node type of the expression that owns this predicate.
     * <p>
     *  拥有此谓词的表达式的高速缓存节点类型。
     * 
     */
    int _ptype = -1;

    /**
     * Name of the inner class.
     * <p>
     *  内部类的名称。
     * 
     */
    private String _className = null;

    /**
     * List of variables in closure.
     * <p>
     *  闭包中的变量列表。
     * 
     */
    private ArrayList _closureVars = null;

    /**
     * Reference to parent closure.
     * <p>
     *  引用父闭包。
     * 
     */
    private Closure _parentClosure = null;

    /**
     * Cached value of method <code>getCompareValue()</code>.
     * <p>
     *  方法的缓存值<code> getCompareValue()</code>。
     * 
     */
    private Expression _value = null;

    /**
     * Cached value of method <code>getCompareValue()</code>.
     * <p>
     *  方法的缓存值<code> getCompareValue()</code>。
     * 
     */
    private Step _step = null;

    /**
     * Initializes a predicate.
     * <p>
     *  初始化谓词。
     * 
     */
    public Predicate(Expression exp) {
        _exp = exp;
        _exp.setParent(this);

    }

    /**
     * Set the parser for this expression.
     * <p>
     *  设置此表达式的解析器。
     * 
     */
    public void setParser(Parser parser) {
        super.setParser(parser);
        _exp.setParser(parser);
    }

    /**
     * Returns a boolean value indicating if the nth position optimization
     * is on. Must be call after type checking!
     * <p>
     * 返回一个布尔值,表示第n个位置优化是否开启。必须在类型检查后调用！
     * 
     */
    public boolean isNthPositionFilter() {
        return _nthPositionFilter;
    }

    /**
     * Returns a boolean value indicating if the nth descendant optimization
     * is on. Must be call after type checking!
     * <p>
     *  返回一个布尔值,指示是否启用了第n个子级优化。必须在类型检查后调用！
     * 
     */
    public boolean isNthDescendant() {
        return _nthDescendant;
    }

    /**
     * Turns off all optimizations for this predicate.
     * <p>
     *  关闭此谓词的所有优化。
     * 
     */
    public void dontOptimize() {
        _canOptimize = false;
    }

    /**
     * Returns true if the expression in this predicate contains a call
     * to position().
     * <p>
     *  如果此谓词中的表达式包含对position()的调用,则返回true。
     * 
     */
    public boolean hasPositionCall() {
        return _exp.hasPositionCall();
    }

    /**
     * Returns true if the expression in this predicate contains a call
     * to last().
     * <p>
     *  如果此谓词中的表达式包含对last()的调用,则返回true。
     * 
     */
    public boolean hasLastCall() {
        return _exp.hasLastCall();
    }

    // -- Begin Closure interface --------------------

    /**
     * Returns true if this closure is compiled in an inner class (i.e.
     * if this is a real closure).
     * <p>
     *  如果此闭包在内部类中编译(即如果这是一个真正的闭包),则返回true。
     * 
     */
    public boolean inInnerClass() {
        return (_className != null);
    }

    /**
     * Returns a reference to its parent closure or null if outermost.
     * <p>
     *  返回对其父闭包的引用,如果最外层则返回null。
     * 
     */
    public Closure getParentClosure() {
        if (_parentClosure == null) {
            SyntaxTreeNode node = getParent();
            do {
                if (node instanceof Closure) {
                    _parentClosure = (Closure) node;
                    break;
                }
                if (node instanceof TopLevelElement) {
                    break;      // way up in the tree
                }
                node = node.getParent();
            } while (node != null);
        }
        return _parentClosure;
    }

    /**
     * Returns the name of the auxiliary class or null if this predicate
     * is compiled inside the Translet.
     * <p>
     *  返回辅助类的名称,如果此谓词在Translet中编译,则返回null。
     * 
     */
    public String getInnerClassName() {
        return _className;
    }

    /**
     * Add new variable to the closure.
     * <p>
     *  向闭包添加新变量。
     * 
     */
    public void addVariable(VariableRefBase variableRef) {
        if (_closureVars == null) {
            _closureVars = new ArrayList();
        }

        // Only one reference per variable
        if (!_closureVars.contains(variableRef)) {
            _closureVars.add(variableRef);

            // Add variable to parent closure as well
            Closure parentClosure = getParentClosure();
            if (parentClosure != null) {
                parentClosure.addVariable(variableRef);
            }
        }
    }

    // -- End Closure interface ----------------------

    /**
     * Returns the node type of the expression owning this predicate. The
     * return value is cached in <code>_ptype</code>.
     * <p>
     *  返回拥有此谓词的表达式的节点类型。返回值缓存在<code> _ptype </code>中。
     * 
     */
    public int getPosType() {
        if (_ptype == -1) {
            SyntaxTreeNode parent = getParent();
            if (parent instanceof StepPattern) {
                _ptype = ((StepPattern)parent).getNodeType();
            }
            else if (parent instanceof AbsoluteLocationPath) {
                AbsoluteLocationPath path = (AbsoluteLocationPath)parent;
                Expression exp = path.getPath();
                if (exp instanceof Step) {
                    _ptype = ((Step)exp).getNodeType();
                }
            }
            else if (parent instanceof VariableRefBase) {
                final VariableRefBase ref = (VariableRefBase)parent;
                final VariableBase var = ref.getVariable();
                final Expression exp = var.getExpression();
                if (exp instanceof Step) {
                    _ptype = ((Step)exp).getNodeType();
                }
            }
            else if (parent instanceof Step) {
                _ptype = ((Step)parent).getNodeType();
            }
        }
        return _ptype;
    }

    public boolean parentIsPattern() {
        return (getParent() instanceof Pattern);
    }

    public Expression getExpr() {
        return _exp;
    }

    public String toString() {
        return "pred(" + _exp + ')';
    }

    /**
     * Type check a predicate expression. If the type of the expression is
     * number convert it to boolean by adding a comparison with position().
     * Note that if the expression is a parameter, we cannot distinguish
     * at compile time if its type is number or not. Hence, expressions of
     * reference type are always converted to booleans.
     *
     * This method may be called twice, before and after calling
     * <code>dontOptimize()</code>. If so, the second time it should honor
     * the new value of <code>_canOptimize</code>.
     * <p>
     *  类型检查谓词表达式。如果表达式的类型为数字,则通过与position()进行比较来将其转换为布尔值。注意,如果表达式是一个参数,我们不能在编译时区分它的类型是否是数字。
     * 因此,引用类型的表达式总是转换为布尔值。
     * 
     *  此方法可以调用两次,之前和之后调用<code> dontOptimize()</code>。如果是这样,第二次它应该尊重<code> _canOptimize </code>的新值。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        Type texp = _exp.typeCheck(stable);

        // We need explicit type information for reference types - no good!
        if (texp instanceof ReferenceType) {
            _exp = new CastExpr(_exp, texp = Type.Real);
        }

        // A result tree fragment should not be cast directly to a number type,
        // but rather to a boolean value, and then to a numer (0 or 1).
        // Ref. section 11.2 of the XSLT 1.0 spec
        if (texp instanceof ResultTreeType) {
            _exp = new CastExpr(_exp, Type.Boolean);
            _exp = new CastExpr(_exp, Type.Real);
            texp = _exp.typeCheck(stable);
        }

        // Numerical types will be converted to a position filter
        if (texp instanceof NumberType) {
            // Cast any numerical types to an integer
            if (texp instanceof IntType == false) {
                _exp = new CastExpr(_exp, Type.Int);
            }

            if (_canOptimize) {
                // Nth position optimization. Expression must not depend on context
                _nthPositionFilter =
                    !_exp.hasLastCall() && !_exp.hasPositionCall();

                // _nthDescendant optimization - only if _nthPositionFilter is on
                if (_nthPositionFilter) {
                    SyntaxTreeNode parent = getParent();
                    _nthDescendant = (parent instanceof Step) &&
                        (parent.getParent() instanceof AbsoluteLocationPath);
                    return _type = Type.NodeSet;
                }
            }

           // Reset optimization flags
            _nthPositionFilter = _nthDescendant = false;

           // Otherwise, expand [e] to [position() = e]
           final QName position =
                getParser().getQNameIgnoreDefaultNs("position");
           final PositionCall positionCall =
                new PositionCall(position);
           positionCall.setParser(getParser());
           positionCall.setParent(this);

           _exp = new EqualityExpr(Operators.EQ, positionCall,
                                    _exp);
           if (_exp.typeCheck(stable) != Type.Boolean) {
               _exp = new CastExpr(_exp, Type.Boolean);
           }
           return _type = Type.Boolean;
        }
        else {
            // All other types will be handled as boolean values
            if (texp instanceof BooleanType == false) {
                _exp = new CastExpr(_exp, Type.Boolean);
            }
            return _type = Type.Boolean;
        }
    }

    /**
     * Create a new "Filter" class implementing
     * <code>CurrentNodeListFilter</code>. Allocate registers for local
     * variables and local parameters passed in the closure to test().
     * Notice that local variables need to be "unboxed".
     * <p>
     * 创建一个新的"Filter"类,实现<code> CurrentNodeListFilter </code>。为局部变量和在闭包中传递的局部参数分配寄存器到test()。
     * 注意局部变量需要"unboxed"。
     * 
     */
    private void compileFilter(ClassGenerator classGen,
                               MethodGenerator methodGen) {
        TestGenerator testGen;
        LocalVariableGen local;
        FilterGenerator filterGen;

        _className = getXSLTC().getHelperClassName();
        filterGen = new FilterGenerator(_className,
                                        "java.lang.Object",
                                        toString(),
                                        ACC_PUBLIC | ACC_SUPER,
                                        new String[] {
                                            CURRENT_NODE_LIST_FILTER
                                        },
                                        classGen.getStylesheet());

        final ConstantPoolGen cpg = filterGen.getConstantPool();
        final int length = (_closureVars == null) ? 0 : _closureVars.size();

        // Add a new instance variable for each var in closure
        for (int i = 0; i < length; i++) {
            VariableBase var = ((VariableRefBase) _closureVars.get(i)).getVariable();

            filterGen.addField(new Field(ACC_PUBLIC,
                                        cpg.addUtf8(var.getEscapedName()),
                                        cpg.addUtf8(var.getType().toSignature()),
                                        null, cpg.getConstantPool()));
        }

        final InstructionList il = new InstructionList();
        testGen = new TestGenerator(ACC_PUBLIC | ACC_FINAL,
                                    com.sun.org.apache.bcel.internal.generic.Type.BOOLEAN,
                                    new com.sun.org.apache.bcel.internal.generic.Type[] {
                                        com.sun.org.apache.bcel.internal.generic.Type.INT,
                                        com.sun.org.apache.bcel.internal.generic.Type.INT,
                                        com.sun.org.apache.bcel.internal.generic.Type.INT,
                                        com.sun.org.apache.bcel.internal.generic.Type.INT,
                                        Util.getJCRefType(TRANSLET_SIG),
                                        Util.getJCRefType(NODE_ITERATOR_SIG)
                                    },
                                    new String[] {
                                        "node",
                                        "position",
                                        "last",
                                        "current",
                                        "translet",
                                        "iterator"
                                    },
                                    "test", _className, il, cpg);

        // Store the dom in a local variable
        local = testGen.addLocalVariable("document",
                                         Util.getJCRefType(DOM_INTF_SIG),
                                         null, null);
        final String className = classGen.getClassName();
        il.append(filterGen.loadTranslet());
        il.append(new CHECKCAST(cpg.addClass(className)));
        il.append(new GETFIELD(cpg.addFieldref(className,
                                               DOM_FIELD, DOM_INTF_SIG)));
        local.setStart(il.append(new ASTORE(local.getIndex())));

        // Store the dom index in the test generator
        testGen.setDomIndex(local.getIndex());

        _exp.translate(filterGen, testGen);
        il.append(IRETURN);

        filterGen.addEmptyConstructor(ACC_PUBLIC);
        filterGen.addMethod(testGen);

        getXSLTC().dumpClass(filterGen.getJavaClass());
    }

    /**
     * Returns true if the predicate is a test for the existance of an
     * element or attribute. All we have to do is to get the first node
     * from the step, check if it is there, and then return true/false.
     * <p>
     *  如果谓词是对元素或属性的存在性的测试,则返回true。我们所要做的就是从步骤中获取第一个节点,检查它是否存在,然后返回true / false。
     * 
     */
    public boolean isBooleanTest() {
        return (_exp instanceof BooleanExpr);
    }

    /**
     * Method to see if we can optimise the predicate by using a specialised
     * iterator for expressions like '/foo/bar[@attr = $var]', which are
     * very common in many stylesheets
     * <p>
     *  查看是否可以通过对诸如'/ foo / bar [@attr = $ var]'等表达式使用专用迭代器来优化谓词的方法,这在很多样式表中非常常见
     * 
     */
    public boolean isNodeValueTest() {
        if (!_canOptimize) return false;
        return (getStep() != null && getCompareValue() != null);
    }

   /**
     * Returns the step in an expression of the form 'step = value'.
     * Null is returned if the expression is not of the right form.
     * Optimization if off if null is returned.
     * <p>
     *  返回形式为"step = value"的表达式中的步长。如果表达式不是正确的形式,则返回Null。如果返回null则返回优化。
     * 
     */
    public Step getStep() {
        // Returned cached value if called more than once
        if (_step != null) {
            return _step;
        }

        // Nothing to do if _exp is null
        if (_exp == null) {
            return null;
        }

        // Ignore if not equality expression
        if (_exp instanceof EqualityExpr) {
            EqualityExpr exp = (EqualityExpr)_exp;
            Expression left = exp.getLeft();
            Expression right = exp.getRight();

            // Unwrap and set _step if appropriate
            if (left instanceof CastExpr) {
                left = ((CastExpr) left).getExpr();
            }
            if (left instanceof Step) {
                _step = (Step) left;
            }

            // Unwrap and set _step if appropriate
            if (right instanceof CastExpr) {
                right = ((CastExpr)right).getExpr();
            }
            if (right instanceof Step) {
                _step = (Step)right;
            }
        }
        return _step;
    }

    /**
     * Returns the value in an expression of the form 'step = value'.
     * A value may be either a literal string or a variable whose
     * type is string. Optimization if off if null is returned.
     * <p>
     *  返回'step = value'形式的表达式中的值。值可以是文字字符串或类型为字符串的变量。如果返回null则返回优化。
     * 
     */
    public Expression getCompareValue() {
        // Returned cached value if called more than once
        if (_value != null) {
            return _value;
        }

        // Nothing to to do if _exp is null
        if (_exp == null) {
            return null;
        }

        // Ignore if not an equality expression
        if (_exp instanceof EqualityExpr) {
            EqualityExpr exp = (EqualityExpr) _exp;
            Expression left = exp.getLeft();
            Expression right = exp.getRight();

            // Return if left is literal string
            if (left instanceof LiteralExpr) {
                _value = left;
                return _value;
            }
            // Return if left is a variable reference of type string
            if (left instanceof VariableRefBase &&
                left.getType() == Type.String)
            {
                _value = left;
                return _value;
            }

            // Return if right is literal string
            if (right instanceof LiteralExpr) {
                _value = right;
                return _value;
            }
            // Return if left is a variable reference whose type is string
            if (right instanceof VariableRefBase &&
                right.getType() == Type.String)
            {
                _value = right;
                return _value;
            }
        }
        return null;
    }

    /**
     * Translate a predicate expression. This translation pushes
     * two references on the stack: a reference to a newly created
     * filter object and a reference to the predicate's closure.
     * <p>
     *  翻译谓词表达式。这个翻译在堆栈上推送两个引用：对新创建的过滤器对象的引用和对谓词的闭包的引用。
     * 
     */
    public void translateFilter(ClassGenerator classGen,
                                MethodGenerator methodGen)
    {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // Compile auxiliary class for filter
        compileFilter(classGen, methodGen);

        // Create new instance of filter
        il.append(new NEW(cpg.addClass(_className)));
        il.append(DUP);
        il.append(new INVOKESPECIAL(cpg.addMethodref(_className,
                                                     "<init>", "()V")));

        // Initialize closure variables
        final int length = (_closureVars == null) ? 0 : _closureVars.size();

        for (int i = 0; i < length; i++) {
            VariableRefBase varRef = (VariableRefBase) _closureVars.get(i);
            VariableBase var = varRef.getVariable();
            Type varType = var.getType();

            il.append(DUP);

            // Find nearest closure implemented as an inner class
            Closure variableClosure = _parentClosure;
            while (variableClosure != null) {
                if (variableClosure.inInnerClass()) break;
                variableClosure = variableClosure.getParentClosure();
            }

            // Use getfield if in an inner class
            if (variableClosure != null) {
                il.append(ALOAD_0);
                il.append(new GETFIELD(
                    cpg.addFieldref(variableClosure.getInnerClassName(),
                        var.getEscapedName(), varType.toSignature())));
            }
            else {
                // Use a load of instruction if in translet class
                il.append(var.loadInstruction());
            }

            // Store variable in new closure
            il.append(new PUTFIELD(
                    cpg.addFieldref(_className, var.getEscapedName(),
                        varType.toSignature())));
        }
    }

    /**
     * Translate a predicate expression. If non of the optimizations apply
     * then this translation pushes two references on the stack: a reference
     * to a newly created filter object and a reference to the predicate's
     * closure. See class <code>Step</code> for further details.
     * <p>
     *  翻译谓词表达式。如果没有优化应用,那么这个翻译在栈上推送两个引用：对新创建的过滤器对象的引用和对谓词的闭包的引用。有关详细信息,请参见<code> Step </code>。
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {

        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        if (_nthPositionFilter || _nthDescendant) {
            _exp.translate(classGen, methodGen);
        }
        else if (isNodeValueTest() && (getParent() instanceof Step)) {
            _value.translate(classGen, methodGen);
            il.append(new CHECKCAST(cpg.addClass(STRING_CLASS)));
            il.append(new PUSH(cpg, ((EqualityExpr)_exp).getOp()));
        }
        else {
            translateFilter(classGen, methodGen);
        }
    }
}

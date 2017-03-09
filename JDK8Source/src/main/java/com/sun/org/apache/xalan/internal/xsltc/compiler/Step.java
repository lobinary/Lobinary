/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2005 The Apache Software Foundation.
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
 *  版权所有2001-2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: Step.java,v 1.6 2006/06/06 22:34:34 spericas Exp $
 * <p>
 *  $ Id：Step.java,v 1.6 2006/06/06 22:34:34 spericas Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.ICONST;
import com.sun.org.apache.bcel.internal.generic.ILOAD;
import com.sun.org.apache.bcel.internal.generic.ISTORE;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.org.apache.xml.internal.dtm.Axis;
import com.sun.org.apache.xml.internal.dtm.DTM;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class Step extends RelativeLocationPath {

    /**
     * This step's axis as defined in class Axis.
     * <p>
     *  此步骤的轴在Axis类中定义。
     * 
     */
    private int _axis;

    /**
     * A vector of predicates (filters) defined on this step - may be null
     * <p>
     *  在此步骤中定义的谓词(过滤器)向量可以为null
     * 
     */
    private Vector _predicates;

    /**
     * Some simple predicates can be handled by this class (and not by the
     * Predicate class) and will be removed from the above vector as they are
     * handled. We use this boolean to remember if we did have any predicates.
     * <p>
     *  一些简单的谓词可以由这个类处理(而不是由谓词类),并且将在处理它们时从上述向量中删除。我们使用这个布尔值来记住我们是否有任何谓词。
     * 
     */
    private boolean _hadPredicates = false;

    /**
     * Type of the node test.
     * <p>
     *  节点测试的类型。
     * 
     */
    private int _nodeType;

    public Step(int axis, int nodeType, Vector predicates) {
        _axis = axis;
        _nodeType = nodeType;
        _predicates = predicates;
    }

    /**
     * Set the parser for this element and all child predicates
     * <p>
     *  设置此元素和所有子谓词的解析器
     * 
     */
    public void setParser(Parser parser) {
        super.setParser(parser);
        if (_predicates != null) {
            final int n = _predicates.size();
            for (int i = 0; i < n; i++) {
                final Predicate exp = (Predicate)_predicates.elementAt(i);
                exp.setParser(parser);
                exp.setParent(this);
            }
        }
    }

    /**
     * Define the axis (defined in Axis class) for this step
     * <p>
     *  定义此步骤的轴(在Axis类中定义)
     * 
     */
    public int getAxis() {
        return _axis;
    }

    /**
     * Get the axis (defined in Axis class) for this step
     * <p>
     *  获取此步骤的轴(在Axis类中定义)
     * 
     */
    public void setAxis(int axis) {
        _axis = axis;
    }

    /**
     * Returns the node-type for this step
     * <p>
     *  返回此步骤的节点类型
     * 
     */
    public int getNodeType() {
        return _nodeType;
    }

    /**
     * Returns the vector containing all predicates for this step.
     * <p>
     *  返回包含此步骤的所有谓词的向量。
     * 
     */
    public Vector getPredicates() {
        return _predicates;
    }

    /**
     * Returns the vector containing all predicates for this step.
     * <p>
     *  返回包含此步骤的所有谓词的向量。
     * 
     */
    public void addPredicates(Vector predicates) {
        if (_predicates == null) {
            _predicates = predicates;
        }
        else {
            _predicates.addAll(predicates);
        }
    }

    /**
     * Returns 'true' if this step has a parent pattern.
     * This method will return 'false' if this step occurs on its own under
     * an element like <xsl:for-each> or <xsl:apply-templates>.
     * <p>
     * 如果此步骤具有父模式,则返回'true'。如果此步骤在<xsl：for-each>或<xsl：apply-templates>之类的元素下单独发生,此方法将返回'false'。
     * 
     */
    private boolean hasParentPattern() {
        final SyntaxTreeNode parent = getParent();
        return (parent instanceof ParentPattern ||
                parent instanceof ParentLocationPath ||
                parent instanceof UnionPathExpr ||
                parent instanceof FilterParentPath);
    }

    /**
     * Returns 'true' if this step has a parent location path.
     * <p>
     *  如果此步骤具有父位置路径,则返回'true'。
     * 
     */
    private boolean hasParentLocationPath() {
        return getParent() instanceof ParentLocationPath;
    }

    /**
     * Returns 'true' if this step has any predicates
     * <p>
     *  如果此步骤有任何谓词,则返回'true'
     * 
     */
    private boolean hasPredicates() {
        return _predicates != null && _predicates.size() > 0;
    }

    /**
     * Returns 'true' if this step is used within a predicate
     * <p>
     *  如果此步骤在谓词中使用,则返回'true'
     * 
     */
    private boolean isPredicate() {
        SyntaxTreeNode parent = this;
        while (parent != null) {
            parent = parent.getParent();
            if (parent instanceof Predicate) return true;
        }
        return false;
    }

    /**
     * True if this step is the abbreviated step '.'
     * <p>
     *  如果此步骤是缩写的步骤"。
     * 
     */
    public boolean isAbbreviatedDot() {
        return _nodeType == NodeTest.ANODE && _axis == Axis.SELF;
    }


    /**
     * True if this step is the abbreviated step '..'
     * <p>
     *  如果此步骤是缩写的步骤"..",则为True
     * 
     */
    public boolean isAbbreviatedDDot() {
        return _nodeType == NodeTest.ANODE && _axis == Axis.PARENT;
    }

    /**
     * Type check this step. The abbreviated steps '.' and '@attr' are
     * assigned type node if they have no predicates. All other steps
     * have type node-set.
     * <p>
     *  类型检查此步骤。缩写的步骤"。和'@attr'如果没有谓词,则分配类型节点。所有其他步骤都具有类型node-set。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {

        // Save this value for later - important for testing for special
        // combinations of steps and patterns than can be optimised
        _hadPredicates = hasPredicates();

        // Special case for '.'
        //   in the case where '.' has a context such as book/.
        //   or .[false()] we can not optimize the nodeset to a single node.
        if (isAbbreviatedDot()) {
            _type = (hasParentPattern() || hasPredicates() || hasParentLocationPath()) ?
                Type.NodeSet : Type.Node;
        }
        else {
            _type = Type.NodeSet;
        }

        // Type check all predicates (expressions applied to the step)
        if (_predicates != null) {
            final int n = _predicates.size();
            for (int i = 0; i < n; i++) {
                final Expression pred = (Expression)_predicates.elementAt(i);
                pred.typeCheck(stable);
            }
        }

        // Return either Type.Node or Type.NodeSet
        return _type;
    }

    /**
     * Translate a step by pushing the appropriate iterator onto the stack.
     * The abbreviated steps '.' and '@attr' do not create new iterators
     * if they are not part of a LocationPath and have no filters.
     * In these cases a node index instead of an iterator is pushed
     * onto the stack.
     * <p>
     *  通过将适当的迭代器推送到堆栈来翻译一个步骤。缩写的步骤"。和'@attr'不创建新的迭代器,如果它们不是LocationPath的一部分,并且没有过滤器。
     * 在这些情况下,节点索引而不是迭代器被推送到堆栈。
     * 
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        translateStep(classGen, methodGen, hasPredicates() ? _predicates.size() - 1 : -1);
    }

    private void translateStep(ClassGenerator classGen,
                               MethodGenerator methodGen,
                               int predicateIndex) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        if (predicateIndex >= 0) {
            translatePredicates(classGen, methodGen, predicateIndex);
        } else {
            int star = 0;
            String name = null;
            final XSLTC xsltc = getParser().getXSLTC();

            if (_nodeType >= DTM.NTYPES) {
                final Vector ni = xsltc.getNamesIndex();

                name = (String)ni.elementAt(_nodeType-DTM.NTYPES);
                star = name.lastIndexOf('*');
            }

            // If it is an attribute, but not '@*', '@pre:*' or '@node()',
            // and has no parent
            if (_axis == Axis.ATTRIBUTE && _nodeType != NodeTest.ATTRIBUTE
                && _nodeType != NodeTest.ANODE && !hasParentPattern()
                && star == 0)
            {
                int iter = cpg.addInterfaceMethodref(DOM_INTF,
                                                     "getTypedAxisIterator",
                                                     "(II)"+NODE_ITERATOR_SIG);
                il.append(methodGen.loadDOM());
                il.append(new PUSH(cpg, Axis.ATTRIBUTE));
                il.append(new PUSH(cpg, _nodeType));
                il.append(new INVOKEINTERFACE(iter, 3));
                return;
            }

            SyntaxTreeNode parent = getParent();
            // Special case for '.'
            if (isAbbreviatedDot()) {
                if (_type == Type.Node) {
                    // Put context node on stack if using Type.Node
                    il.append(methodGen.loadContextNode());
                }
                else {
                    if (parent instanceof ParentLocationPath){
                        // Wrap the context node in a singleton iterator if not.
                        int init = cpg.addMethodref(SINGLETON_ITERATOR,
                                                    "<init>",
                                                    "("+NODE_SIG+")V");
                        il.append(new NEW(cpg.addClass(SINGLETON_ITERATOR)));
                        il.append(DUP);
                        il.append(methodGen.loadContextNode());
                        il.append(new INVOKESPECIAL(init));
                    } else {
                        // DOM.getAxisIterator(int axis);
                        int git = cpg.addInterfaceMethodref(DOM_INTF,
                                                "getAxisIterator",
                                                "(I)"+NODE_ITERATOR_SIG);
                        il.append(methodGen.loadDOM());
                        il.append(new PUSH(cpg, _axis));
                        il.append(new INVOKEINTERFACE(git, 2));
                    }
                }
                return;
            }

            // Special case for /foo/*/bar
            if ((parent instanceof ParentLocationPath) &&
                (parent.getParent() instanceof ParentLocationPath)) {
                if ((_nodeType == NodeTest.ELEMENT) && (!_hadPredicates)) {
                    _nodeType = NodeTest.ANODE;
                }
            }

            // "ELEMENT" or "*" or "@*" or ".." or "@attr" with a parent.
            switch (_nodeType) {
            case NodeTest.ATTRIBUTE:
                _axis = Axis.ATTRIBUTE;
            case NodeTest.ANODE:
                // DOM.getAxisIterator(int axis);
                int git = cpg.addInterfaceMethodref(DOM_INTF,
                                                    "getAxisIterator",
                                                    "(I)"+NODE_ITERATOR_SIG);
                il.append(methodGen.loadDOM());
                il.append(new PUSH(cpg, _axis));
                il.append(new INVOKEINTERFACE(git, 2));
                break;
            default:
                if (star > 1) {
                    final String namespace;
                    if (_axis == Axis.ATTRIBUTE)
                        namespace = name.substring(0,star-2);
                    else
                        namespace = name.substring(0,star-1);

                    final int nsType = xsltc.registerNamespace(namespace);
                    final int ns = cpg.addInterfaceMethodref(DOM_INTF,
                                                    "getNamespaceAxisIterator",
                                                    "(II)"+NODE_ITERATOR_SIG);
                    il.append(methodGen.loadDOM());
                    il.append(new PUSH(cpg, _axis));
                    il.append(new PUSH(cpg, nsType));
                    il.append(new INVOKEINTERFACE(ns, 3));
                    break;
                }
            case NodeTest.ELEMENT:
                // DOM.getTypedAxisIterator(int axis, int type);
                final int ty = cpg.addInterfaceMethodref(DOM_INTF,
                                                "getTypedAxisIterator",
                                                "(II)"+NODE_ITERATOR_SIG);
                // Get the typed iterator we're after
                il.append(methodGen.loadDOM());
                il.append(new PUSH(cpg, _axis));
                il.append(new PUSH(cpg, _nodeType));
                il.append(new INVOKEINTERFACE(ty, 3));

                break;
            }
        }
    }


    /**
     * Translate a sequence of predicates. Each predicate is translated
     * by constructing an instance of <code>CurrentNodeListIterator</code>
     * which is initialized from another iterator (recursive call),
     * a filter and a closure (call to translate on the predicate) and "this".
     * <p>
     *  if((parent instanceof ParentLocationPath)&&(parent.getParent()instanceof ParentLocationPath)){if((_nodeType == NodeTest.ELEMENT)&&(！_hadPredicates)){_nodeType = NodeTest.ANODE; }
     * }。
     * 
     * //"ELEMENT"或"*"或"@ *"或".."或"@attr"。
     *  switch(_nodeType){case NodeTest.ATTRIBUTE：_axis = Axis.ATTRIBUTE; case NodeTest.ANODE：// DOM.getAxisIterator(int axis); int git = cpg.addInterfaceMethodref(DOM_INTF,"getAxisIterator","(I)"+ NODE_ITERATOR_SIG); il.append(methodGen.loadDOM()); il.append(new PUSH(cpg,_axis)); il.append(new INVOKEINTERFACE(git,2));打破;默认值：if(star> 1){final String namespace; if(_axis == Axis.ATTRIBUTE)namespace = name.substring(0,star-2); else namespace = name.substring(0,star-1);。
     * //"ELEMENT"或"*"或"@ *"或".."或"@attr"。
     * 
     *  final int nsType = xsltc.registerNamespace(namespace); final int ns = cpg.addInterfaceMethodref(DOM_
     * INTF,"getNamespaceAxisIterator","(II)"+ NODE_ITERATOR_SIG); il.append(methodGen.loadDOM()); il.append
     * (new PUSH(cpg,_axis)); il.append(new PUSH(cpg,nsType)); il.append(new INVOKEINTERFACE(ns,3));打破; } ca
     * se NodeTest.ELEMENT：// DOM.getTypedAxisIterator(int axes,int type); final int ty = cpg.addInterfaceMe
     * thodref(DOM_INTF,"getTypedAxisIterator","(II)"+ NODE_ITERATOR_SIG); //获取在il.append之后的类型迭代器(methodGen.
     * loadDOM()); il.append(new PUSH(cpg,_axis)); il.append(new PUSH(cpg,_nodeType)); il.append(new INVOKEI
     * NTERFACE(ty,3));。
     * 
     *  打破; }}}
     * 
     *  / **翻译谓词序列。
     * 每个谓词通过构造从另一个迭代器(递归调用),过滤器和闭包(在谓词上调用translate)和"this"初始化的<code> CurrentNodeListIterator </code>实例进行转换。
     *  / **翻译谓词序列。
     * 
     */
    public void translatePredicates(ClassGenerator classGen,
                                    MethodGenerator methodGen,
                                    int predicateIndex) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        int idx = 0;

        if (predicateIndex < 0) {
            translateStep(classGen, methodGen, predicateIndex);
        }
        else {
            final Predicate predicate = (Predicate) _predicates.get(predicateIndex--);

            // Special case for predicates that can use the NodeValueIterator
            // instead of an auxiliary class. Certain path/predicates pairs
            // are translated into a base path, on top of which we place a
            // node value iterator that tests for the desired value:
            //   foo[@attr = 'str']  ->  foo/@attr + test(value='str')
            //   foo[bar = 'str']    ->  foo/bar + test(value='str')
            //   foo/bar[. = 'str']  ->  foo/bar + test(value='str')
            if (predicate.isNodeValueTest()) {
                Step step = predicate.getStep();

                il.append(methodGen.loadDOM());
                // If the predicate's Step is simply '.' we translate this Step
                // and place the node test on top of the resulting iterator
                if (step.isAbbreviatedDot()) {
                    translateStep(classGen, methodGen, predicateIndex);
                    il.append(new ICONST(DOM.RETURN_CURRENT));
                }
                // Otherwise we create a parent location path with this Step and
                // the predicates Step, and place the node test on top of that
                else {
                    ParentLocationPath path = new ParentLocationPath(this, step);
                    _parent = step._parent = path;      // Force re-parenting

                    try {
                        path.typeCheck(getParser().getSymbolTable());
                    }
                    catch (TypeCheckError e) { }
                    translateStep(classGen, methodGen, predicateIndex);
                    path.translateStep(classGen, methodGen);
                    il.append(new ICONST(DOM.RETURN_PARENT));
                }
                predicate.translate(classGen, methodGen);
                idx = cpg.addInterfaceMethodref(DOM_INTF,
                                                GET_NODE_VALUE_ITERATOR,
                                                GET_NODE_VALUE_ITERATOR_SIG);
                il.append(new INVOKEINTERFACE(idx, 5));
            }
            // Handle '//*[n]' expression
            else if (predicate.isNthDescendant()) {
                il.append(methodGen.loadDOM());
                // il.append(new ICONST(NodeTest.ELEMENT));
                il.append(new PUSH(cpg, predicate.getPosType()));
                predicate.translate(classGen, methodGen);
                il.append(new ICONST(0));
                idx = cpg.addInterfaceMethodref(DOM_INTF,
                                                "getNthDescendant",
                                                "(IIZ)"+NODE_ITERATOR_SIG);
                il.append(new INVOKEINTERFACE(idx, 4));
            }
            // Handle 'elem[n]' expression
            else if (predicate.isNthPositionFilter()) {
                idx = cpg.addMethodref(NTH_ITERATOR_CLASS,
                                       "<init>",
                                       "("+NODE_ITERATOR_SIG+"I)V");

                // Backwards branches are prohibited if an uninitialized object
                // is on the stack by section 4.9.4 of the JVM Specification,
                // 2nd Ed.  We don't know whether this code might contain
                // backwards branches, so we mustn't create the new object until
                // after we've created the suspect arguments to its constructor.
                // Instead we calculate the values of the arguments to the
                // constructor first, store them in temporary variables, create
                // the object and reload the arguments from the temporaries to
                // avoid the problem.
                translatePredicates(classGen, methodGen, predicateIndex); // recursive call
                LocalVariableGen iteratorTemp
                        = methodGen.addLocalVariable("step_tmp1",
                                         Util.getJCRefType(NODE_ITERATOR_SIG),
                                         null, null);
                iteratorTemp.setStart(
                        il.append(new ASTORE(iteratorTemp.getIndex())));

                predicate.translate(classGen, methodGen);
                LocalVariableGen predicateValueTemp
                        = methodGen.addLocalVariable("step_tmp2",
                                         Util.getJCRefType("I"),
                                         null, null);
                predicateValueTemp.setStart(
                        il.append(new ISTORE(predicateValueTemp.getIndex())));

                il.append(new NEW(cpg.addClass(NTH_ITERATOR_CLASS)));
                il.append(DUP);
                iteratorTemp.setEnd(
                        il.append(new ALOAD(iteratorTemp.getIndex())));
                predicateValueTemp.setEnd(
                        il.append(new ILOAD(predicateValueTemp.getIndex())));
                il.append(new INVOKESPECIAL(idx));
            }
            else {
                idx = cpg.addMethodref(CURRENT_NODE_LIST_ITERATOR,
                                       "<init>",
                                       "("
                                       + NODE_ITERATOR_SIG
                                       + CURRENT_NODE_LIST_FILTER_SIG
                                       + NODE_SIG
                                       + TRANSLET_SIG
                                       + ")V");

                // Backwards branches are prohibited if an uninitialized object
                // is on the stack by section 4.9.4 of the JVM Specification,
                // 2nd Ed.  We don't know whether this code might contain
                // backwards branches, so we mustn't create the new object until
                // after we've created the suspect arguments to its constructor.
                // Instead we calculate the values of the arguments to the
                // constructor first, store them in temporary variables, create
                // the object and reload the arguments from the temporaries to
                // avoid the problem.
                translatePredicates(classGen, methodGen, predicateIndex); // recursive call
                LocalVariableGen iteratorTemp
                        = methodGen.addLocalVariable("step_tmp1",
                                         Util.getJCRefType(NODE_ITERATOR_SIG),
                                         null, null);
                iteratorTemp.setStart(
                        il.append(new ASTORE(iteratorTemp.getIndex())));

                predicate.translateFilter(classGen, methodGen);
                LocalVariableGen filterTemp
                        = methodGen.addLocalVariable("step_tmp2",
                              Util.getJCRefType(CURRENT_NODE_LIST_FILTER_SIG),
                              null, null);
                filterTemp.setStart(
                        il.append(new ASTORE(filterTemp.getIndex())));
                // create new CurrentNodeListIterator
                il.append(new NEW(cpg.addClass(CURRENT_NODE_LIST_ITERATOR)));
                il.append(DUP);

                iteratorTemp.setEnd(
                        il.append(new ALOAD(iteratorTemp.getIndex())));
                filterTemp.setEnd(il.append(new ALOAD(filterTemp.getIndex())));

                il.append(methodGen.loadCurrentNode());
                il.append(classGen.loadTranslet());
                if (classGen.isExternal()) {
                    final String className = classGen.getClassName();
                    il.append(new CHECKCAST(cpg.addClass(className)));
                }
                il.append(new INVOKESPECIAL(idx));
            }
        }
    }

    /**
     * Returns a string representation of this step.
     * <p>
     * else if(predicate.isNthDescendant()){il.append(methodGen.loadDOM()); // il.append(new ICONST(NodeTest.ELEMENT)); il.append(new PUSH(cpg,predicate.getPosType())); predicate.translate(classGen,methodGen); i.append(new ICONST(0)); idx = cpg.addInterfaceMethodref(DOM_INTF,"getNthDescendant","(IIZ)"+ NODE_ITERATOR_SIG); il.append(new INVOKEINTERFACE(idx,4)); }
     * ; //处理'elem [n]'表达式else if(predicate.isNthPositionFilter()){idx = cpg.addMethodref(NTH_ITERATOR_CLASS,"<init>","("+ NODE_ITERATOR_SIG +。
     * 
     *  //如果一个未初始化的对象//在JVM规范的第4.9.4节中的堆栈上,则禁止反向分支,//第二版。
     * 我们不知道这个代码是否可能包含//向后分支,所以我们不能创建新对象,直到我们在其构造函数中创建了可疑的参数。
     *  //相反,我们首先计算//构造函数的参数值,将它们存储在临时变量中,创建//对象,并从临时值重新加载参数以避免出现问题。
     *  translatePredicates(classGen,methodGen,predicateIndex); //递归调用LocalVariableGen iteratorTemp = method
     * Gen.addLocalVariable("step_tmp1",Util.getJCRefType(NODE_ITERATOR_SIG),null,null); iteratorTemp.setSta
     * rt(il.append(new ASTORE(iteratorTemp.getIndex())));。
     *  //相反,我们首先计算//构造函数的参数值,将它们存储在临时变量中,创建//对象,并从临时值重新加载参数以避免出现问题。
     * 
     * predicate.translate(classGen,methodGen); LocalVariableGen predicateValueTemp = methodGen.addLocalVari
     * able("step_tmp2",Util.getJCRefType("I"),null,null); predicateValueTemp.setStart(il.append(new ISTORE(
     * predicateValueTemp.getIndex())));。
     * 
     *  il.append(new NEW(cpg.addClass(NTH_ITERATOR_CLASS))); i.append(DUP); iteratorTemp.setEnd(il.append(n
     * ew ALOAD(iteratorTemp.getIndex()))); predicateValueTemp.setEnd(il.append(new ILOAD(predicateValueTemp
     * .getIndex()))); il.append(new INVOKESPECIAL(idx)); } else {idx = cpg.addMethodref(CURRENT_NODE_LIST_ITERATOR,"<init>","("+ NODE_ITERATOR_SIG + CURRENT_NODE_LIST_FILTER_SIG + NODE_SIG + TRANSLET_SIG +")V");。
     * 
     *  //如果一个未初始化的对象//在JVM规范的第4.9.4节中的堆栈上,则禁止反向分支,//第二版。
     * 我们不知道这个代码是否可能包含//向后分支,所以我们不能创建新对象,直到我们在其构造函数中创建了可疑的参数。
     */
    public String toString() {
        final StringBuffer buffer = new StringBuffer("step(\"");
        buffer.append(Axis.getNames(_axis)).append("\", ").append(_nodeType);
        if (_predicates != null) {
            final int n = _predicates.size();
            for (int i = 0; i < n; i++) {
                final Predicate pred = (Predicate)_predicates.elementAt(i);
                buffer.append(", ").append(pred.toString());
            }
        }
        return buffer.append(')').toString();
    }
}

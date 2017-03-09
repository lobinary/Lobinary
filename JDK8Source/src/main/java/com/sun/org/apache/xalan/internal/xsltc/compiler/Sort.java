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
 * $Id: Sort.java,v 1.2.4.1 2005/09/12 11:08:12 pvedula Exp $
 * <p>
 *  $ Id：Sort.java,v 1.2.4.1 2005/09/12 11:08:12 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.text.Collator;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.classfile.Method;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import com.sun.org.apache.bcel.internal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import com.sun.org.apache.bcel.internal.generic.ICONST;
import com.sun.org.apache.bcel.internal.generic.ILOAD;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.NOP;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.CompareGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSortRecordFactGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSortRecordGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.org.apache.xml.internal.dtm.Axis;


/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class Sort extends Instruction implements Closure {

    private Expression     _select;
    private AttributeValue _order;
    private AttributeValue _caseOrder;
    private AttributeValue _dataType;
    private String  _lang; // bug! see 26869

    private String         _data = null;


    private String _className = null;
    private ArrayList _closureVars = null;
    private boolean _needsSortRecordFactory = false;

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
        return null;
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
            _needsSortRecordFactory = true;
        }
    }

    // -- End Closure interface ----------------------

    private void setInnerClassName(String className) {
        _className = className;
    }

    /**
     * Parse the attributes of the xsl:sort element
     * <p>
     *  解析xsl：sort元素的属性
     * 
     */
    public void parseContents(Parser parser) {

        final SyntaxTreeNode parent = getParent();
        if (!(parent instanceof ApplyTemplates) &&
            !(parent instanceof ForEach)) {
            reportError(this, parser, ErrorMsg.STRAY_SORT_ERR, null);
            return;
        }

        // Parse the select expression (node string value if no expression)
        _select = parser.parseExpression(this, "select", "string(.)");

        // Get the sort order; default is 'ascending'
        String val = getAttribute("order");
        if (val.length() == 0) val = "ascending";
        _order = AttributeValue.create(this, val, parser);

        // Get the sort data type; default is text
        val = getAttribute("data-type");
        if (val.length() == 0) {
            try {
                final Type type = _select.typeCheck(parser.getSymbolTable());
                if (type instanceof IntType)
                    val = "number";
                else
                    val = "text";
            }
            catch (TypeCheckError e) {
                val = "text";
            }
        }
        _dataType = AttributeValue.create(this, val, parser);

         _lang =  getAttribute("lang"); // bug! see 26869
  // val =  getAttribute("lang");
  // _lang = AttributeValue.create(this, val, parser);
        // Get the case order; default is language dependant
    val = getAttribute("case-order");
    _caseOrder = AttributeValue.create(this, val, parser);

    }

    /**
     * Run type checks on the attributes; expression must return a string
     * which we will use as a sort key
     * <p>
     *  对属性的运行类型检查;表达式必须返回一个字符串,我们将使用它作为排序键
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        final Type tselect = _select.typeCheck(stable);

        // If the sort data-type is not set we use the natural data-type
        // of the data we will sort
        if (!(tselect instanceof StringType)) {
            _select = new CastExpr(_select, Type.String);
        }

        _order.typeCheck(stable);
        _caseOrder.typeCheck(stable);
        _dataType.typeCheck(stable);
        return Type.Void;
    }

    /**
     * These two methods are needed in the static methods that compile the
     * overloaded NodeSortRecord.compareType() and NodeSortRecord.sortOrder()
     * <p>
     *  在编译重载的NodeSortRecord.compareType()和NodeSortRecord.sortOrder()的静态方法中需要这两个方法。
     * 
     */
    public void translateSortType(ClassGenerator classGen,
                                  MethodGenerator methodGen) {
        _dataType.translate(classGen, methodGen);
    }

    public void translateSortOrder(ClassGenerator classGen,
                                   MethodGenerator methodGen) {
        _order.translate(classGen, methodGen);
    }

     public void translateCaseOrder(ClassGenerator classGen,
                   MethodGenerator methodGen) {
    _caseOrder.translate(classGen, methodGen);
    }

    public void translateLang(ClassGenerator classGen,
                   MethodGenerator methodGen) {
    final ConstantPoolGen cpg = classGen.getConstantPool();
    final InstructionList il = methodGen.getInstructionList();
    il.append(new PUSH(cpg, _lang)); // bug! see 26869
    }

    /**
     * This method compiles code for the select expression for this
     * xsl:sort element. The method is called from the static code-generating
     * methods in this class.
     * <p>
     * 此方法为此xsl：sort元素的select表达式编译代码。该方法从该类中的静态代码生成方法中调用。
     * 
     */
    public void translateSelect(ClassGenerator classGen,
                                MethodGenerator methodGen) {
        _select.translate(classGen,methodGen);
    }

    /**
     * This method should not produce any code
     * <p>
     *  这个方法不应该产生任何代码
     * 
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        // empty
    }

    /**
     * Compiles code that instantiates a SortingIterator object.
     * This object's constructor needs referencdes to the current iterator
     * and a node sort record producing objects as its parameters.
     * <p>
     *  编译实例化SortingIterator对象的代码。此对象的构造函数需要引用当前迭代器和一个生成对象作为其参数的节点排序记录。
     * 
     */
    public static void translateSortIterator(ClassGenerator classGen,
                                      MethodGenerator methodGen,
                                      Expression nodeSet,
                                      Vector sortObjects)
    {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // SortingIterator.SortingIterator(NodeIterator,NodeSortRecordFactory);
        final int init = cpg.addMethodref(SORT_ITERATOR, "<init>",
                                          "("
                                          + NODE_ITERATOR_SIG
                                          + NODE_SORT_FACTORY_SIG
                                          + ")V");

        // Backwards branches are prohibited if an uninitialized object is
        // on the stack by section 4.9.4 of the JVM Specification, 2nd Ed.
        // We don't know whether this code might contain backwards branches
        // so we mustn't create the new object until after we've created
        // the suspect arguments to its constructor.  Instead we calculate
        // the values of the arguments to the constructor first, store them
        // in temporary variables, create the object and reload the
        // arguments from the temporaries to avoid the problem.

        LocalVariableGen nodesTemp =
            methodGen.addLocalVariable("sort_tmp1",
                                       Util.getJCRefType(NODE_ITERATOR_SIG),
                                       null, null);

        LocalVariableGen sortRecordFactoryTemp =
            methodGen.addLocalVariable("sort_tmp2",
                                      Util.getJCRefType(NODE_SORT_FACTORY_SIG),
                                      null, null);

        // Get the current node iterator
        if (nodeSet == null) {  // apply-templates default
            final int children = cpg.addInterfaceMethodref(DOM_INTF,
                                                           "getAxisIterator",
                                                           "(I)"+
                                                           NODE_ITERATOR_SIG);
            il.append(methodGen.loadDOM());
            il.append(new PUSH(cpg, Axis.CHILD));
            il.append(new INVOKEINTERFACE(children, 2));
        }
        else {
            nodeSet.translate(classGen, methodGen);
        }

        nodesTemp.setStart(il.append(new ASTORE(nodesTemp.getIndex())));

        // Compile the code for the NodeSortRecord producing class and pass
        // that as the last argument to the SortingIterator constructor.
        compileSortRecordFactory(sortObjects, classGen, methodGen);
        sortRecordFactoryTemp.setStart(
                il.append(new ASTORE(sortRecordFactoryTemp.getIndex())));

        il.append(new NEW(cpg.addClass(SORT_ITERATOR)));
        il.append(DUP);
        nodesTemp.setEnd(il.append(new ALOAD(nodesTemp.getIndex())));
        sortRecordFactoryTemp.setEnd(
                il.append(new ALOAD(sortRecordFactoryTemp.getIndex())));
        il.append(new INVOKESPECIAL(init));
    }


    /**
     * Compiles code that instantiates a NodeSortRecordFactory object which
     * will produce NodeSortRecord objects of a specific type.
     * <p>
     *  编译实例化NodeSortRecordFactory对象的代码,该对象将生成特定类型的NodeSortRecord对象。
     * 
     */
    public static void compileSortRecordFactory(Vector sortObjects,
        ClassGenerator classGen, MethodGenerator methodGen)
    {
        String sortRecordClass =
            compileSortRecord(sortObjects, classGen, methodGen);

        boolean needsSortRecordFactory = false;
        final int nsorts = sortObjects.size();
        for (int i = 0; i < nsorts; i++) {
            final Sort sort = (Sort) sortObjects.elementAt(i);
            needsSortRecordFactory |= sort._needsSortRecordFactory;
        }

        String sortRecordFactoryClass = NODE_SORT_FACTORY;
        if (needsSortRecordFactory) {
            sortRecordFactoryClass =
                compileSortRecordFactory(sortObjects, classGen, methodGen,
                    sortRecordClass);
        }

        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // Backwards branches are prohibited if an uninitialized object is
        // on the stack by section 4.9.4 of the JVM Specification, 2nd Ed.
        // We don't know whether this code might contain backwards branches
        // so we mustn't create the new object until after we've created
        // the suspect arguments to its constructor.  Instead we calculate
        // the values of the arguments to the constructor first, store them
        // in temporary variables, create the object and reload the
        // arguments from the temporaries to avoid the problem.

        // Compile code that initializes the static _sortOrder
        LocalVariableGen sortOrderTemp
                 = methodGen.addLocalVariable("sort_order_tmp",
                                      Util.getJCRefType("[" + STRING_SIG),
                                      null, null);
        il.append(new PUSH(cpg, nsorts));
        il.append(new ANEWARRAY(cpg.addClass(STRING)));
        for (int level = 0; level < nsorts; level++) {
            final Sort sort = (Sort)sortObjects.elementAt(level);
            il.append(DUP);
            il.append(new PUSH(cpg, level));
            sort.translateSortOrder(classGen, methodGen);
            il.append(AASTORE);
        }
        sortOrderTemp.setStart(il.append(new ASTORE(sortOrderTemp.getIndex())));

        LocalVariableGen sortTypeTemp
                 = methodGen.addLocalVariable("sort_type_tmp",
                                      Util.getJCRefType("[" + STRING_SIG),
                                      null, null);
        il.append(new PUSH(cpg, nsorts));
        il.append(new ANEWARRAY(cpg.addClass(STRING)));
        for (int level = 0; level < nsorts; level++) {
            final Sort sort = (Sort)sortObjects.elementAt(level);
            il.append(DUP);
            il.append(new PUSH(cpg, level));
            sort.translateSortType(classGen, methodGen);
            il.append(AASTORE);
        }
        sortTypeTemp.setStart(il.append(new ASTORE(sortTypeTemp.getIndex())));

        LocalVariableGen sortLangTemp
                 = methodGen.addLocalVariable("sort_lang_tmp",
                                      Util.getJCRefType("[" + STRING_SIG),
                                      null, null);
        il.append(new PUSH(cpg, nsorts));
        il.append(new ANEWARRAY(cpg.addClass(STRING)));
        for (int level = 0; level < nsorts; level++) {
              final Sort sort = (Sort)sortObjects.elementAt(level);
              il.append(DUP);
              il.append(new PUSH(cpg, level));
              sort.translateLang(classGen, methodGen);
              il.append(AASTORE);
        }
        sortLangTemp.setStart(il.append(new ASTORE(sortLangTemp.getIndex())));

        LocalVariableGen sortCaseOrderTemp
                 = methodGen.addLocalVariable("sort_case_order_tmp",
                                      Util.getJCRefType("[" + STRING_SIG),
                                      null, null);
        il.append(new PUSH(cpg, nsorts));
        il.append(new ANEWARRAY(cpg.addClass(STRING)));
        for (int level = 0; level < nsorts; level++) {
            final Sort sort = (Sort)sortObjects.elementAt(level);
            il.append(DUP);
            il.append(new PUSH(cpg, level));
            sort.translateCaseOrder(classGen, methodGen);
            il.append(AASTORE);
        }
        sortCaseOrderTemp.setStart(
                il.append(new ASTORE(sortCaseOrderTemp.getIndex())));

        il.append(new NEW(cpg.addClass(sortRecordFactoryClass)));
        il.append(DUP);
        il.append(methodGen.loadDOM());
        il.append(new PUSH(cpg, sortRecordClass));
        il.append(classGen.loadTranslet());

        sortOrderTemp.setEnd(il.append(new ALOAD(sortOrderTemp.getIndex())));
        sortTypeTemp.setEnd(il.append(new ALOAD(sortTypeTemp.getIndex())));
        sortLangTemp.setEnd(il.append(new ALOAD(sortLangTemp.getIndex())));
        sortCaseOrderTemp.setEnd(
                il.append(new ALOAD(sortCaseOrderTemp.getIndex())));

        il.append(new INVOKESPECIAL(
            cpg.addMethodref(sortRecordFactoryClass, "<init>",
                "(" + DOM_INTF_SIG
                    + STRING_SIG
                    + TRANSLET_INTF_SIG
                    + "[" + STRING_SIG
                    + "[" + STRING_SIG
                    + "[" + STRING_SIG
                    + "[" + STRING_SIG + ")V")));

        // Initialize closure variables in sortRecordFactory
        final ArrayList dups = new ArrayList();

        for (int j = 0; j < nsorts; j++) {
            final Sort sort = (Sort) sortObjects.get(j);
            final int length = (sort._closureVars == null) ? 0 :
                sort._closureVars.size();

            for (int i = 0; i < length; i++) {
                VariableRefBase varRef = (VariableRefBase) sort._closureVars.get(i);

                // Discard duplicate variable references
                if (dups.contains(varRef)) continue;

                final VariableBase var = varRef.getVariable();

                // Store variable in new closure
                il.append(DUP);
                il.append(var.loadInstruction());
                il.append(new PUTFIELD(
                        cpg.addFieldref(sortRecordFactoryClass, var.getEscapedName(),
                            var.getType().toSignature())));
                dups.add(varRef);
            }
        }
    }

    public static String compileSortRecordFactory(Vector sortObjects,
        ClassGenerator classGen, MethodGenerator methodGen,
        String sortRecordClass)
    {
        final XSLTC  xsltc = ((Sort)sortObjects.firstElement()).getXSLTC();
        final String className = xsltc.getHelperClassName();

        final NodeSortRecordFactGenerator sortRecordFactory =
            new NodeSortRecordFactGenerator(className,
                                        NODE_SORT_FACTORY,
                                        className + ".java",
                                        ACC_PUBLIC | ACC_SUPER | ACC_FINAL,
                                        new String[] {},
                                        classGen.getStylesheet());

        ConstantPoolGen cpg = sortRecordFactory.getConstantPool();

        // Add a new instance variable for each var in closure
        final int nsorts = sortObjects.size();
        final ArrayList dups = new ArrayList();

        for (int j = 0; j < nsorts; j++) {
            final Sort sort = (Sort) sortObjects.get(j);
            final int length = (sort._closureVars == null) ? 0 :
                sort._closureVars.size();

            for (int i = 0; i < length; i++) {
                final VariableRefBase varRef = (VariableRefBase) sort._closureVars.get(i);

                // Discard duplicate variable references
                if (dups.contains(varRef)) continue;

                final VariableBase var = varRef.getVariable();
                sortRecordFactory.addField(new Field(ACC_PUBLIC,
                                           cpg.addUtf8(var.getEscapedName()),
                                           cpg.addUtf8(var.getType().toSignature()),
                                           null, cpg.getConstantPool()));
                dups.add(varRef);
            }
        }

        // Define a constructor for this class
        final com.sun.org.apache.bcel.internal.generic.Type[] argTypes =
            new com.sun.org.apache.bcel.internal.generic.Type[7];
        argTypes[0] = Util.getJCRefType(DOM_INTF_SIG);
        argTypes[1] = Util.getJCRefType(STRING_SIG);
        argTypes[2] = Util.getJCRefType(TRANSLET_INTF_SIG);
        argTypes[3] = Util.getJCRefType("[" + STRING_SIG);
        argTypes[4] = Util.getJCRefType("[" + STRING_SIG);
  argTypes[5] = Util.getJCRefType("[" + STRING_SIG);
  argTypes[6] = Util.getJCRefType("[" + STRING_SIG);

        final String[] argNames = new String[7];
        argNames[0] = DOCUMENT_PNAME;
        argNames[1] = "className";
        argNames[2] = TRANSLET_PNAME;
        argNames[3] = "order";
        argNames[4] = "type";
  argNames[5] = "lang";
  argNames[6] = "case_order";


        InstructionList il = new InstructionList();
        final MethodGenerator constructor =
            new MethodGenerator(ACC_PUBLIC,
                                com.sun.org.apache.bcel.internal.generic.Type.VOID,
                                argTypes, argNames, "<init>",
                                className, il, cpg);

        // Push all parameters onto the stack and called super.<init>()
        il.append(ALOAD_0);
        il.append(ALOAD_1);
        il.append(ALOAD_2);
        il.append(new ALOAD(3));
        il.append(new ALOAD(4));
        il.append(new ALOAD(5));
  il.append(new ALOAD(6));
  il.append(new ALOAD(7));
        il.append(new INVOKESPECIAL(cpg.addMethodref(NODE_SORT_FACTORY,
            "<init>",
            "(" + DOM_INTF_SIG
                + STRING_SIG
                + TRANSLET_INTF_SIG
                + "[" + STRING_SIG
    + "[" + STRING_SIG
    + "[" + STRING_SIG
                + "[" + STRING_SIG + ")V")));
        il.append(RETURN);

        // Override the definition of makeNodeSortRecord()
        il = new InstructionList();
        final MethodGenerator makeNodeSortRecord =
            new MethodGenerator(ACC_PUBLIC,
                Util.getJCRefType(NODE_SORT_RECORD_SIG),
                new com.sun.org.apache.bcel.internal.generic.Type[] {
                    com.sun.org.apache.bcel.internal.generic.Type.INT,
                    com.sun.org.apache.bcel.internal.generic.Type.INT },
                new String[] { "node", "last" }, "makeNodeSortRecord",
                className, il, cpg);

        il.append(ALOAD_0);
        il.append(ILOAD_1);
        il.append(ILOAD_2);
        il.append(new INVOKESPECIAL(cpg.addMethodref(NODE_SORT_FACTORY,
            "makeNodeSortRecord", "(II)" + NODE_SORT_RECORD_SIG)));
        il.append(DUP);
        il.append(new CHECKCAST(cpg.addClass(sortRecordClass)));

        // Initialize closure in record class
        final int ndups = dups.size();
        for (int i = 0; i < ndups; i++) {
            final VariableRefBase varRef = (VariableRefBase) dups.get(i);
            final VariableBase var = varRef.getVariable();
            final Type varType = var.getType();

            il.append(DUP);

            // Get field from factory class
            il.append(ALOAD_0);
            il.append(new GETFIELD(
                cpg.addFieldref(className,
                    var.getEscapedName(), varType.toSignature())));

            // Put field in record class
            il.append(new PUTFIELD(
                cpg.addFieldref(sortRecordClass,
                    var.getEscapedName(), varType.toSignature())));
        }
        il.append(POP);
        il.append(ARETURN);

        constructor.setMaxLocals();
        constructor.setMaxStack();
        sortRecordFactory.addMethod(constructor);
        makeNodeSortRecord.setMaxLocals();
        makeNodeSortRecord.setMaxStack();
        sortRecordFactory.addMethod(makeNodeSortRecord);
        xsltc.dumpClass(sortRecordFactory.getJavaClass());

        return className;
    }

    /**
     * Create a new auxillary class extending NodeSortRecord.
     * <p>
     *  创建一个扩展NodeSortRecord的辅助类。
     * 
     */
    private static String compileSortRecord(Vector sortObjects,
                                            ClassGenerator classGen,
                                            MethodGenerator methodGen) {
        final XSLTC  xsltc = ((Sort)sortObjects.firstElement()).getXSLTC();
        final String className = xsltc.getHelperClassName();

        // This generates a new class for handling this specific sort
        final NodeSortRecordGenerator sortRecord =
            new NodeSortRecordGenerator(className,
                                        NODE_SORT_RECORD,
                                        "sort$0.java",
                                        ACC_PUBLIC | ACC_SUPER | ACC_FINAL,
                                        new String[] {},
                                        classGen.getStylesheet());

        final ConstantPoolGen cpg = sortRecord.getConstantPool();

        // Add a new instance variable for each var in closure
        final int nsorts = sortObjects.size();
        final ArrayList dups = new ArrayList();

        for (int j = 0; j < nsorts; j++) {
            final Sort sort = (Sort) sortObjects.get(j);

            // Set the name of the inner class in this sort object
            sort.setInnerClassName(className);

            final int length = (sort._closureVars == null) ? 0 :
                sort._closureVars.size();
            for (int i = 0; i < length; i++) {
                final VariableRefBase varRef = (VariableRefBase) sort._closureVars.get(i);

                // Discard duplicate variable references
                if (dups.contains(varRef)) continue;

                final VariableBase var = varRef.getVariable();
                sortRecord.addField(new Field(ACC_PUBLIC,
                                    cpg.addUtf8(var.getEscapedName()),
                                    cpg.addUtf8(var.getType().toSignature()),
                                    null, cpg.getConstantPool()));
                dups.add(varRef);
            }
        }

        MethodGenerator init = compileInit(sortObjects, sortRecord,
                                         cpg, className);
        MethodGenerator extract = compileExtract(sortObjects, sortRecord,
                                        cpg, className);
        sortRecord.addMethod(init);
        sortRecord.addMethod(extract);

        xsltc.dumpClass(sortRecord.getJavaClass());
        return className;
    }

    /**
     * Create a constructor for the new class. Updates the reference to the
     * collator in the super calls only when the stylesheet specifies a new
     * language in xsl:sort.
     * <p>
     *  为新类创建一个构造函数。仅当样式表在xsl：sort中指定新语言时,才更新超级调用中对collat​​or的引用。
     * 
     */
    private static MethodGenerator compileInit(Vector sortObjects,
                                           NodeSortRecordGenerator sortRecord,
                                           ConstantPoolGen cpg,
                                           String className)
    {
        final InstructionList il = new InstructionList();
        final MethodGenerator init =
            new MethodGenerator(ACC_PUBLIC,
                                com.sun.org.apache.bcel.internal.generic.Type.VOID,
                                null, null, "<init>", className,
                                il, cpg);

        // Call the constructor in the NodeSortRecord superclass
        il.append(ALOAD_0);
        il.append(new INVOKESPECIAL(cpg.addMethodref(NODE_SORT_RECORD,
                                                     "<init>", "()V")));



        il.append(RETURN);

        return init;
    }


    /**
     * Compiles a method that overloads NodeSortRecord.extractValueFromDOM()
     * <p>
     *  编译重载NodeSortRecord.extractValueFromDOM()的方法
     */
    private static MethodGenerator compileExtract(Vector sortObjects,
                                         NodeSortRecordGenerator sortRecord,
                                         ConstantPoolGen cpg,
                                         String className) {
        final InstructionList il = new InstructionList();

        // String NodeSortRecord.extractValueFromDOM(dom,node,level);
        final CompareGenerator extractMethod =
            new CompareGenerator(ACC_PUBLIC | ACC_FINAL,
                                 com.sun.org.apache.bcel.internal.generic.Type.STRING,
                                 new com.sun.org.apache.bcel.internal.generic.Type[] {
                                     Util.getJCRefType(DOM_INTF_SIG),
                                     com.sun.org.apache.bcel.internal.generic.Type.INT,
                                     com.sun.org.apache.bcel.internal.generic.Type.INT,
                                     Util.getJCRefType(TRANSLET_SIG),
                                     com.sun.org.apache.bcel.internal.generic.Type.INT
                                 },
                                 new String[] { "dom",
                                                "current",
                                                "level",
                                                "translet",
                                                "last"
                                 },
                                 "extractValueFromDOM", className, il, cpg);

        // Values needed for the switch statement
        final int levels = sortObjects.size();
        final int match[] = new int[levels];
        final InstructionHandle target[] = new InstructionHandle[levels];
        InstructionHandle tblswitch = null;

        // Compile switch statement only if the key has multiple levels
        if (levels > 1) {
            // Put the parameter to the swtich statement on the stack
            il.append(new ILOAD(extractMethod.getLocalIndex("level")));
            // Append the switch statement here later on
            tblswitch = il.append(new NOP());
        }

        // Append all the cases for the switch statment
        for (int level = 0; level < levels; level++) {
            match[level] = level;
            final Sort sort = (Sort)sortObjects.elementAt(level);
            target[level] = il.append(NOP);
            sort.translateSelect(sortRecord, extractMethod);
            il.append(ARETURN);
        }

        // Compile def. target for switch statement if key has multiple levels
        if (levels > 1) {
            // Append the default target - it will _NEVER_ be reached
            InstructionHandle defaultTarget =
                il.append(new PUSH(cpg, EMPTYSTRING));
            il.insert(tblswitch,new TABLESWITCH(match, target, defaultTarget));
            il.append(ARETURN);
        }

        return extractMethod;
    }
}

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
 * $Id: TestSeq.java,v 1.2.4.1 2005/09/12 11:31:38 pvedula Exp $
 * <p>
 *  $ Id：TestSeq.java,v 1.2.4.1 2005/09/12 11:31:38 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Dictionary;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.GOTO_W;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;

/**
 * A test sequence is a sequence of patterns that
 *
 *  (1) occured in templates in the same mode
 *  (2) share the same kernel node type (e.g. A/B and C/C/B)
 *  (3) may also contain patterns matching "*" and "node()"
 *      (element sequence only) or matching "@*" (attribute
 *      sequence only).
 *
 * A test sequence may have a default template, which will be
 * instantiated if none of the other patterns match.
 * <p>
 *  测试序列是一系列模式
 * 
 *  (例如A / B和C / C / B)(3)也可以包含与"*"和"node()"匹配的模式序列)或匹配"@ *"(仅属于属性序列)。
 * 
 *  测试序列可以具有默认模板,如果没有其他模式匹配,则将被实例化。
 * 
 * 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Erwin Bolwidt <ejb@klomp.org>
 * @author Morten Jorgensen <morten.jorgensen@sun.com>
 */
final class TestSeq {

    /**
     * Integer code for the kernel type of this test sequence
     * <p>
     *  此测试序列的内核类型的整数代码
     * 
     */
    private int _kernelType;

    /**
     * Vector of all patterns in the test sequence. May include
     * patterns with "*", "@*" or "node()" kernel.
     * <p>
     *  所有模式在测试序列中的向量。可以包括具有"*","@ *"或"node()"内核的模式。
     * 
     */
    private Vector _patterns = null;

    /**
     * A reference to the Mode object.
     * <p>
     *  对Mode对象的引用。
     * 
     */
    private Mode _mode = null;

    /**
     * Default template for this test sequence
     * <p>
     *  此测试序列的默认模板
     * 
     */
    private Template _default = null;

    /**
     * Instruction list representing this test sequence.
     * <p>
     *  表示此测试序列的指令列表。
     * 
     */
    private InstructionList _instructionList;

    /**
     * Cached handle to avoid compiling more than once.
     * <p>
     *  缓存句柄,以避免编译多次。
     * 
     */
    private InstructionHandle _start = null;

    /**
     * Creates a new test sequence given a set of patterns and a mode.
     * <p>
     * 给定一组模式和模式,创建一个新的测试序列。
     * 
     */
    public TestSeq(Vector patterns, Mode mode) {
        this(patterns, -2, mode);
    }

    public TestSeq(Vector patterns, int kernelType, Mode mode) {
        _patterns = patterns;
        _kernelType = kernelType;
        _mode = mode;
    }

    /**
     * Returns a string representation of this test sequence. Notice
     * that test sequences are mutable, so the value returned by this
     * method is different before and after calling reduce().
     * <p>
     *  返回此测试序列的字符串表示形式。注意,测试序列是可变的,因此该方法返回的值在调用reduce()之前和之后是不同的。
     * 
     */
    public String toString() {
        final int count = _patterns.size();
        final StringBuffer result = new StringBuffer();

        for (int i = 0; i < count; i++) {
            final LocationPathPattern pattern =
                (LocationPathPattern) _patterns.elementAt(i);

            if (i == 0) {
                result.append("Testseq for kernel ").append(_kernelType)
                      .append('\n');
            }
            result.append("   pattern ").append(i).append(": ")
                  .append(pattern.toString())
                  .append('\n');
        }
        return result.toString();
    }

    /**
     * Returns the instruction list for this test sequence
     * <p>
     *  返回此测试序列的指令列表
     * 
     */
    public InstructionList getInstructionList() {
        return _instructionList;
    }

    /**
     * Return the highest priority for a pattern in this test
     * sequence. This is either the priority of the first or
     * of the default pattern.
     * <p>
     *  返回此测试序列中模式的最高优先级。这是第一个或默认模式的优先级。
     * 
     */
    public double getPriority() {
        final Template template = (_patterns.size() == 0) ? _default
            : ((Pattern) _patterns.elementAt(0)).getTemplate();
        return template.getPriority();
    }

    /**
     * Returns the position of the highest priority pattern in
     * this test sequence.
     * <p>
     *  返回此测试序列中最高优先级模式的位置。
     * 
     */
    public int getPosition() {
        final Template template = (_patterns.size() == 0) ? _default
            : ((Pattern) _patterns.elementAt(0)).getTemplate();
        return template.getPosition();
    }

    /**
     * Reduce the patterns in this test sequence. Creates a new
     * vector of patterns and sets the default pattern if it
     * finds a patterns that is fully reduced.
     * <p>
     *  减少此测试序列中的模式。创建一个新的模式向量,并设置默认模式,如果它发现完全缩减的模式。
     * 
     */
    public void reduce() {
        final Vector newPatterns = new Vector();

        final int count = _patterns.size();
        for (int i = 0; i < count; i++) {
            final LocationPathPattern pattern =
                (LocationPathPattern)_patterns.elementAt(i);

            // Reduce this pattern
            pattern.reduceKernelPattern();

            // Is this pattern fully reduced?
            if (pattern.isWildcard()) {
                _default = pattern.getTemplate();
                break;          // Ignore following patterns
            }
            else {
                newPatterns.addElement(pattern);
            }
        }
        _patterns = newPatterns;
    }

    /**
     * Returns, by reference, the templates that are included in
     * this test sequence. Note that a single template can occur
     * in several test sequences if its pattern is a union.
     * <p>
     *  通过引用返回此测试序列中包含的模板。注意,如果单个模板的模式是并集,则可以在若干测试序列中出现单个模板。
     * 
     */
    public void findTemplates(Dictionary templates) {
        if (_default != null) {
            templates.put(_default, this);
        }
        for (int i = 0; i < _patterns.size(); i++) {
            final LocationPathPattern pattern =
                (LocationPathPattern)_patterns.elementAt(i);
            templates.put(pattern.getTemplate(), this);
        }
    }

    /**
     * Get the instruction handle to a template's code. This is
     * used when a single template occurs in several test
     * sequences; that is, if its pattern is a union of patterns
     * (e.g. match="A/B | A/C").
     * <p>
     *  获取模板代码的指令句柄。这在单个模板在多个测试序列中出现时使用;也就是说,如果其模式是模式的并集(例如match ="A / B | A / C")。
     * 
     */
    private InstructionHandle getTemplateHandle(Template template) {
        return (InstructionHandle)_mode.getTemplateInstructionHandle(template);
    }

    /**
     * Returns pattern n in this test sequence
     * <p>
     *  返回此测试序列中的模式n
     * 
     */
    private LocationPathPattern getPattern(int n) {
        return (LocationPathPattern)_patterns.elementAt(n);
    }

    /**
     * Compile the code for this test sequence. Compile patterns
     * from highest to lowest priority. Note that since patterns
     * can be share by multiple test sequences, instruction lists
     * must be copied before backpatching.
     * <p>
     *  编译此测试序列的代码。从最高优先级到最低优先级编译模式。请注意,由于模式可以通过多个测试序列共享,因此必须在重新配置之前复制指令列表。
     */
    public InstructionHandle compile(ClassGenerator classGen,
                                     MethodGenerator methodGen,
                                     InstructionHandle continuation)
    {
        // Returned cached value if already compiled
        if (_start != null) {
            return _start;
        }

        // If not patterns, then return handle for default template
        final int count = _patterns.size();
        if (count == 0) {
            return (_start = getTemplateHandle(_default));
        }

        // Init handle to jump when all patterns failed
        InstructionHandle fail = (_default == null) ? continuation
            : getTemplateHandle(_default);

        // Compile all patterns in reverse order
        for (int n = count - 1; n >= 0; n--) {
            final LocationPathPattern pattern = getPattern(n);
            final Template template = pattern.getTemplate();
            final InstructionList il = new InstructionList();

            // Patterns expect current node on top of stack
            il.append(methodGen.loadCurrentNode());

            // Apply the test-code compiled for the pattern
            InstructionList ilist = methodGen.getInstructionList(pattern);
            if (ilist == null) {
                ilist = pattern.compile(classGen, methodGen);
                methodGen.addInstructionList(pattern, ilist);
            }

            // Make a copy of the instruction list for backpatching
            InstructionList copyOfilist = ilist.copy();

            FlowList trueList = pattern.getTrueList();
            if (trueList != null) {
                trueList = trueList.copyAndRedirect(ilist, copyOfilist);
            }
            FlowList falseList = pattern.getFalseList();
            if (falseList != null) {
                falseList = falseList.copyAndRedirect(ilist, copyOfilist);
            }

            il.append(copyOfilist);

            // On success branch to the template code
            final InstructionHandle gtmpl = getTemplateHandle(template);
            final InstructionHandle success = il.append(new GOTO_W(gtmpl));

            if (trueList != null) {
                trueList.backPatch(success);
            }
            if (falseList != null) {
                falseList.backPatch(fail);
            }

            // Next pattern's 'fail' target is this pattern's first instruction
            fail = il.getStart();

            // Append existing instruction list to the end of this one
            if (_instructionList != null) {
                il.append(_instructionList);
            }

            // Set current instruction list to be this one
            _instructionList = il;
        }
        return (_start = fail);
    }
}

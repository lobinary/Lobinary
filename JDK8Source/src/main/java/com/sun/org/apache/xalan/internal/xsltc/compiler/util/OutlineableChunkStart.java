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
 * $Id: MethodGenerator.java,v 1.10 2010-11-01 04:34:19 joehw Exp $
 * <p>
 *  $ Id：MethodGenerator.java,v 1.10 2010-11-01 04:34:19 joehw Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
import com.sun.org.apache.bcel.internal.generic.Instruction;

/**
 * <p>This pseudo-instruction marks the beginning of a region of byte code that
 * can be copied into a new method, termed an "outlineable" chunk.  The size of
 * the Java stack must be the same at the start of the region as it is at the
 * end of the region, any value on the stack at the start of the region must not
 * be consumed by an instruction in the region of code, the region must not
 * contain a return instruction, no branch instruction in the region is
 * permitted to have a target that is outside the region, and no branch
 * instruction outside the region is permitted to have a target that is inside
 * the region.</p>
 * <p>The end of the region is marked by an {@link OutlineableChunkEnd}
 * pseudo-instruction.</p>
 * <p>Such a region of code may contain other outlineable regions.</p>
 * <p>
 * <p>此伪指令标记可复制到称为"可概括"块的新方法的字节代码区域的开始。
 *  Java堆栈的大小在区域的开始处必须与在区域的结尾相同,在区域开始处的堆栈上的任何值不能被代码区域中的指令占用,该区域必须不包含返回指令,该区域中的分支指令不允许具有该区域外的目标,并且该区域外部的分
 * 支指令不允许具有该区域内的目标。
 * <p>此伪指令标记可复制到称为"可概括"块的新方法的字节代码区域的开始。</p> <p>该区域的结尾由{@link OutlineableChunkEnd}伪指令标记。
 * </p> <p>这样的代码区域可能包含其他可概括的区域。</p>。
 * 
 */
class OutlineableChunkStart extends MarkerInstruction {
    /**
     * A constant instance of {@link OutlineableChunkStart}.  As it has no fields,
     * there should be no need to create an instance of this class.
     * <p>
     *  {@link OutlineableChunkStart}的常数实例。因为它没有字段,所以不需要创建这个类的实例。
     * 
     */
    public static final Instruction OUTLINEABLECHUNKSTART =
                                                new OutlineableChunkStart();

    /**
     * Private default constructor.  As it has no fields,
     * there should be no need to create an instance of this class.  See
     * {@link OutlineableChunkStart#OUTLINEABLECHUNKSTART}.
     * <p>
     *  私有默认构造函数。因为它没有字段,所以不需要创建这个类的实例。请参阅{@link OutlineableChunkStart#OUTLINEABLECHUNKSTART}。
     * 
     */
    private OutlineableChunkStart() {
    }

    /**
     * Get the name of this instruction.  Used for debugging.
     * <p>
     *  获取此指令的名称。用于调试。
     * 
     * 
     * @return the instruction name
     */
    public String getName() {
        return OutlineableChunkStart.class.getName();
    }

    /**
     * Get the name of this instruction.  Used for debugging.
     * <p>
     *  获取此指令的名称。用于调试。
     * 
     * 
     * @return the instruction name
     */
    public String toString() {
        return getName();
    }

    /**
     * Get the name of this instruction.  Used for debugging.
     * <p>
     *  获取此指令的名称。用于调试。
     * 
     * @return the instruction name
     */
    public String toString(boolean verbose) {
        return getName();
    }
}

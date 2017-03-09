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
 * $Id: OutlineableChunkEnd.java,v 1.10 2010-11-01 04:34:19 joehw Exp $
 * <p>
 *  $ Id：OutlineableChunkEnd.java,v 1.10 2010-11-01 04:34:19 joehw Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
import com.sun.org.apache.bcel.internal.generic.Instruction;
/**
 * <p>Marks the end of a region of byte code that can be copied into a new
 * method.  See the {@link OutlineableChunkStart} pseudo-instruction for
 * details.</p>
 * <p>
 *  <p>标记可复制到新方法中的字节代码区域的结尾。有关详细信息,请参阅{@link OutlineableChunkStart}伪指令。</p>
 * 
 */
class OutlineableChunkEnd extends MarkerInstruction {
    /**
     * A constant instance of {@link OutlineableChunkEnd}.  As it has no fields,
     * there should be no need to create an instance of this class.
     * <p>
     *  {@link OutlineableChunkEnd}的常数实例。因为它没有字段,所以不需要创建这个类的实例。
     * 
     */
    public static final Instruction OUTLINEABLECHUNKEND =
                                                new OutlineableChunkEnd();

    /**
     * Private default constructor.  As it has no fields,
     * there should be no need to create an instance of this class.  See
     * {@link OutlineableChunkEnd#OUTLINEABLECHUNKEND}.
     * <p>
     *  私有默认构造函数。因为它没有字段,所以不需要创建这个类的实例。请参阅{@link OutlineableChunkEnd#OUTLINEABLECHUNKEND}。
     * 
     */
    private OutlineableChunkEnd() {
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
        return OutlineableChunkEnd.class.getName();
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

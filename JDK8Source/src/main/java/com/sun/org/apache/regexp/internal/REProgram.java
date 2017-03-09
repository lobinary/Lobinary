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

package com.sun.org.apache.regexp.internal;

import java.io.Serializable;

/**
 * A class that holds compiled regular expressions.  This is exposed mainly
 * for use by the recompile utility (which helps you produce precompiled
 * REProgram objects). You should not otherwise need to work directly with
 * this class.
*
* <p>
*  保存已编译正则表达式的类。这暴露主要是为重新编译实用程序(它帮助您生成预编译的REProgram对象)使用。你不应该另外需要直接与这个类工作。
* 
* 
 * @see RE
 * @see RECompiler
 *
 * @author <a href="mailto:jonl@muppetlabs.com">Jonathan Locke</a>
 */
public class REProgram implements Serializable
{
    static final int OPT_HASBACKREFS = 1;

    char[] instruction;         // The compiled regular expression 'program'
    int lenInstruction;         // The amount of the instruction buffer in use
    char[] prefix;              // Prefix string optimization
    int flags;                  // Optimization flags (REProgram.OPT_*)
    int maxParens = -1;

    /**
     * Constructs a program object from a character array
     * <p>
     *  从字符数组构造一个程序对象
     * 
     * 
     * @param instruction Character array with RE opcode instructions in it
     */
    public REProgram(char[] instruction)
    {
        this(instruction, instruction.length);
    }

    /**
     * Constructs a program object from a character array
     * <p>
     *  从字符数组构造一个程序对象
     * 
     * 
     * @param parens Count of parens in the program
     * @param instruction Character array with RE opcode instructions in it
     */
    public REProgram(int parens, char[] instruction)
    {
        this(instruction, instruction.length);
        this.maxParens = parens;
    }

    /**
     * Constructs a program object from a character array
     * <p>
     *  从字符数组构造一个程序对象
     * 
     * 
     * @param instruction Character array with RE opcode instructions in it
     * @param lenInstruction Amount of instruction array in use
     */
    public REProgram(char[] instruction, int lenInstruction)
    {
        setInstructions(instruction, lenInstruction);
    }

    /**
     * Returns a copy of the current regular expression program in a character
     * array that is exactly the right length to hold the program.  If there is
     * no program compiled yet, getInstructions() will return null.
     * <p>
     *  以字符数组返回当前正则表达式程序的副本,该字符数组的长度正好适合保存程序。如果没有编译程序,getInstructions()将返回null。
     * 
     * 
     * @return A copy of the current compiled RE program
     */
    public char[] getInstructions()
    {
        // Ensure program has been compiled!
        if (lenInstruction != 0)
        {
            // Return copy of program
            char[] ret = new char[lenInstruction];
            System.arraycopy(instruction, 0, ret, 0, lenInstruction);
            return ret;
        }
        return null;
    }

    /**
     * Sets a new regular expression program to run.  It is this method which
     * performs any special compile-time search optimizations.  Currently only
     * two optimizations are in place - one which checks for backreferences
     * (so that they can be lazily allocated) and another which attempts to
     * find an prefix anchor string so that substantial amounts of input can
     * potentially be skipped without running the actual program.
     * <p>
     * 设置要运行的新正则表达式程序。这是执行任何特殊编译时搜索优化的方法。
     * 目前只有两个优化 - 一个检查反向引用(以便它们可以被懒惰地分配),另一个尝试找到前缀锚字符串,使得大量的输入可能被跳过,而不运行实际的程序。
     * 
     * @param instruction Program instruction buffer
     * @param lenInstruction Length of instruction buffer in use
     */
    public void setInstructions(char[] instruction, int lenInstruction)
    {
        // Save reference to instruction array
        this.instruction = instruction;
        this.lenInstruction = lenInstruction;

        // Initialize other program-related variables
        flags = 0;
        prefix = null;

        // Try various compile-time optimizations if there's a program
        if (instruction != null && lenInstruction != 0)
        {
            // If the first node is a branch
            if (lenInstruction >= RE.nodeSize && instruction[0 + RE.offsetOpcode] == RE.OP_BRANCH)
            {
                // to the end node
                int next = instruction[0 + RE.offsetNext];
                if (instruction[next + RE.offsetOpcode] == RE.OP_END)
                {
                    // and the branch starts with an atom
                    if (lenInstruction >= (RE.nodeSize * 2) && instruction[RE.nodeSize + RE.offsetOpcode] == RE.OP_ATOM)
                    {
                        // then get that atom as an prefix because there's no other choice
                        int lenAtom = instruction[RE.nodeSize + RE.offsetOpdata];
                        prefix = new char[lenAtom];
                        System.arraycopy(instruction, RE.nodeSize * 2, prefix, 0, lenAtom);
                    }
                }
            }

            BackrefScanLoop:

            // Check for backreferences
            for (int i = 0; i < lenInstruction; i += RE.nodeSize)
            {
                switch (instruction[i + RE.offsetOpcode])
                {
                    case RE.OP_ANYOF:
                        i += (instruction[i + RE.offsetOpdata] * 2);
                        break;

                    case RE.OP_ATOM:
                        i += instruction[i + RE.offsetOpdata];
                        break;

                    case RE.OP_BACKREF:
                        flags |= OPT_HASBACKREFS;
                        break BackrefScanLoop;
                }
            }
        }
    }
}

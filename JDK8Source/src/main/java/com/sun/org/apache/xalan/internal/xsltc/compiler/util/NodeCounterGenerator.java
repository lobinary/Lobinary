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
 * $Id: NodeCounterGenerator.java,v 1.2.4.1 2005/09/05 11:20:48 pvedula Exp $
 * <p>
 *  $ Id：NodeCounterGenerator.java,v 1.2.4.1 2005/09/05 11:20:48 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler.util;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Stylesheet;

/**
 * This class implements auxiliary classes needed to compile
 * patterns in <tt>xsl:number</tt>. These classes inherit from
 * {Any,Single,Multiple}NodeCounter and override the
 * <tt>matchFrom</tt> and <tt>matchCount</tt> methods.
 * <p>
 *  该类实现了在<tt> xsl：number </tt>中编译模式所需的辅助类。
 * 这些类继承自{Any,Single,Multiple} NodeCounter并覆盖<tt> matchFrom </tt>和<tt> matchCount </tt>方法。
 * 
 * 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public final class NodeCounterGenerator extends ClassGenerator {
    private Instruction _aloadTranslet;

    public NodeCounterGenerator(String className,
                                String superClassName,
                                String fileName,
                                int accessFlags,
                                String[] interfaces,
                                Stylesheet stylesheet) {
        super(className, superClassName, fileName,
              accessFlags, interfaces, stylesheet);
    }

    /**
     * Set the index of the register where "this" (the pointer to
     * the translet) is stored.
     * <p>
     *  设置存储"this"(指向translet的指针)的寄存器的索引。
     * 
     */
    public void setTransletIndex(int index) {
        _aloadTranslet = new ALOAD(index);
    }

    /**
     * The index of the translet pointer within the execution of
     * matchFrom or matchCount.
     * Overridden from ClassGenerator.
     * <p>
     *  在执行matchFrom或matchCount期间的translet指针的索引。从ClassGenerator覆盖。
     * 
     */
    public Instruction loadTranslet() {
        return _aloadTranslet;
    }

    /**
     * Returns <tt>true</tt> since this class is external to the
     * translet.
     * <p>
     *  返回<tt> true </tt>,因为此类在translet外部。
     */
    public boolean isExternal() {
        return true;
    }
}

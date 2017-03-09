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
 * $Id: Closure.java,v 1.2.4.1 2005/09/01 12:01:23 pvedula Exp $
 * <p>
 *  $ Id：Closure.java,v 1.2.4.1 2005/09/01 12:01:23 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

/**
/* <p>
/* 
 * @author Santiago Pericas-Geertsen
 */
public interface Closure {

    /**
     * Returns true if this closure is compiled in an inner class (i.e.
     * if this is a real closure).
     * <p>
     *  如果此闭包在内部类中编译(即如果这是一个真正的闭包),则返回true。
     * 
     */
    public boolean inInnerClass();

    /**
     * Returns a reference to its parent closure or null if outermost.
     * <p>
     *  返回对其父闭包的引用,如果最外层则返回null。
     * 
     */
    public Closure getParentClosure();

    /**
     * Returns the name of the auxiliary class or null if this predicate
     * is compiled inside the Translet.
     * <p>
     *  返回辅助类的名称,如果此谓词在Translet中编译,则返回null。
     * 
     */
    public String getInnerClassName();

    /**
     * Add new variable to the closure.
     * <p>
     *  向闭包添加新变量。
     */
    public void addVariable(VariableRefBase variableRef);
}

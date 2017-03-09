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
/*
 * $Id: PsuedoNames.java,v 1.1.2.1 2005/08/01 01:30:33 jeffsuttor Exp $
 * <p>
 *  $ Id：PsuedoNames.java,v 1.1.2.1 2005/08/01 01:30:33 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.compiler;

/**
 * This is used to represent names of nodes that may not be named, like a
 * comment node.
 * <p>
 *  这用于表示可能未命名的节点的名称,如注释节点。
 * 
 */
public class PsuedoNames
{

  /**
   * Psuedo name for a wild card pattern ('*').
   * <p>
   *  通配符模式('*')的伪元名称。
   * 
   */
  public static final String PSEUDONAME_ANY = "*";

  /**
   * Psuedo name for the root node.
   * <p>
   *  根节点的名称。
   * 
   */
  public static final String PSEUDONAME_ROOT = "/";

  /**
   * Psuedo name for a text node.
   * <p>
   *  文本节点的猜想名称。
   * 
   */
  public static final String PSEUDONAME_TEXT = "#text";

  /**
   * Psuedo name for a comment node.
   * <p>
   *  注释节点的名称。
   * 
   */
  public static final String PSEUDONAME_COMMENT = "#comment";

  /**
   * Psuedo name for a processing instruction node.
   * <p>
   *  处理指令节点的命名名称。
   * 
   */
  public static final String PSEUDONAME_PI = "#pi";

  /**
   * Psuedo name for an unknown type value.
   * <p>
   *  未知类型值的猜测名称。
   */
  public static final String PSEUDONAME_OTHER = "*";
}

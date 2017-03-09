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
 * $Id: TransformStateSetter.java,v 1.2.4.1 2005/09/15 08:15:29 suresh_emailid Exp $
 * <p>
 *  $ Id：TransformStateSetter.java,v 1.2.4.1 2005/09/15 08:15:29 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import javax.xml.transform.Transformer;

import org.w3c.dom.Node;
/**
 * This interface is meant to be used by a base interface to
 * TransformState, but which as only the setters which have non Xalan
 * specific types in their signature, so that there are no dependancies
 * of the serializer on Xalan.
 *
 * This interface is not a public API, it is only public because it is
 * used by Xalan.
 *
 * <p>
 *  这个接口意味着由一个基本接口用于TransformState,但只有在其签名中具有非Xalan特定类型的设置器,因此在Xalan上没有串行器的依赖。
 * 
 *  此接口不是公共API,它只是公共的,因为它被Xalan使用。
 * 
 * 
 * @see com.sun.org.apache.xalan.internal.transformer.TransformState
 * @xsl.usage internal
 */
public interface TransformStateSetter
{


  /**
   * Set the current node.
   *
   * <p>
   *  设置当前节点。
   * 
   * 
   * @param n The current node.
   */
  void setCurrentNode(Node n);

  /**
   * Reset the state on the given transformer object.
   *
   * <p>
   *  重置给定变压器对象的状态。
   * 
   * @param transformer
   */
  void resetState(Transformer transformer);

}

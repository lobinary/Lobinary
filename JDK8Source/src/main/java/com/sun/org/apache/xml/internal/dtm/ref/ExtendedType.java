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
 * $Id: ExtendedType.java,v 1.2.4.1 2005/09/15 08:15:06 suresh_emailid Exp $
 * <p>
 *  $ Id：ExtendedType.java,v 1.2.4.1 2005/09/15 08:15:06 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

/**
 * The class ExtendedType represents an extended type object used by
 * ExpandedNameTable.
 * <p>
 *  类ExtendedType表示ExpandedNameTable使用的扩展类型对象。
 * 
 */
public final class ExtendedType
{
    private int nodetype;
    private String namespace;
    private String localName;
    private int hash;

    /**
     * Create an ExtendedType object from node type, namespace and local name.
     * The hash code is calculated from the node type, namespace and local name.
     *
     * <p>
     *  从节点类型,命名空间和本地名称创建ExtendedType对象。哈希码从节点类型,命名空间和本地名称计算。
     * 
     * 
     * @param nodetype Type of the node
     * @param namespace Namespace of the node
     * @param localName Local name of the node
     */
    public ExtendedType (int nodetype, String namespace, String localName)
    {
      this.nodetype = nodetype;
      this.namespace = namespace;
      this.localName = localName;
      this.hash = nodetype + namespace.hashCode() + localName.hashCode();
    }

    /**
     * Create an ExtendedType object from node type, namespace, local name
     * and a given hash code.
     *
     * <p>
     *  从节点类型,命名空间,本地名称和给定的哈希码创建ExtendedType对象。
     * 
     * 
     * @param nodetype Type of the node
     * @param namespace Namespace of the node
     * @param localName Local name of the node
     * @param hash The given hash code
     */
    public ExtendedType (int nodetype, String namespace, String localName, int hash)
    {
      this.nodetype = nodetype;
      this.namespace = namespace;
      this.localName = localName;
      this.hash = hash;
    }

    /**
     * Redefine this ExtendedType object to represent a different extended type.
     * This is intended to be used ONLY on the hashET object. Using it elsewhere
     * will mess up existing hashtable entries!
     * <p>
     *  重新定义此ExtendedType对象以表示不同的扩展类型。这仅用于在hashET对象上。在其他地方使用它会弄乱现有的哈希表条目！
     * 
     */
    protected void redefine(int nodetype, String namespace, String localName)
    {
      this.nodetype = nodetype;
      this.namespace = namespace;
      this.localName = localName;
      this.hash = nodetype + namespace.hashCode() + localName.hashCode();
    }

    /**
     * Redefine this ExtendedType object to represent a different extended type.
     * This is intended to be used ONLY on the hashET object. Using it elsewhere
     * will mess up existing hashtable entries!
     * <p>
     *  重新定义此ExtendedType对象以表示不同的扩展类型。这仅用于在hashET对象上。在其他地方使用它会弄乱现有的哈希表条目！
     * 
     */
    protected void redefine(int nodetype, String namespace, String localName, int hash)
    {
      this.nodetype = nodetype;
      this.namespace = namespace;
      this.localName = localName;
      this.hash = hash;
    }

    /**
     * Override the hashCode() method in the Object class
     * <p>
     * 覆盖Object类中的hashCode()方法
     * 
     */
    public int hashCode()
    {
      return hash;
    }

    /**
     * Test if this ExtendedType object is equal to the given ExtendedType.
     *
     * <p>
     *  测试此ExtendedType对象是否等于给定的ExtendedType。
     * 
     * 
     * @param other The other ExtendedType object to test for equality
     * @return true if the two ExtendedType objects are equal.
     */
    public boolean equals(ExtendedType other)
    {
      try
      {
        return other.nodetype == this.nodetype &&
                other.localName.equals(this.localName) &&
                other.namespace.equals(this.namespace);
      }
      catch(NullPointerException e)
      {
        return false;
      }
    }

    /**
     * Return the node type
     * <p>
     *  返回节点类型
     * 
     */
    public int getNodeType()
    {
      return nodetype;
    }

    /**
     * Return the local name
     * <p>
     *  返回本地名称
     * 
     */
    public String getLocalName()
    {
      return localName;
    }

    /**
     * Return the namespace
     * <p>
     *  返回命名空间
     */
    public String getNamespace()
    {
      return namespace;
    }

}

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
 *  版权所有1999-2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
/*
 * $Id: DOM2DTMdefaultNamespaceDeclarationNode.java,v 1.2.4.1 2005/09/15 08:15:11 suresh_emailid Exp $
 * <p>
 * $ Id：DOM2DTMdefaultNamespaceDeclarationNodejava,v 1241 2005/09/15 08:15:11 suresh_emailid Exp $
 * 
 */

package com.sun.org.apache.xml.internal.dtm.ref.dom2dtm;

import com.sun.org.apache.xml.internal.dtm.DTMException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.DOMException;

/** This is a kluge to let us shove a declaration for xml: into the
 * DOM2DTM model.  Basically, it creates a proxy node in DOM space to
 * carry the additional information. This is _NOT_ a full DOM
 * implementation, and shouldn't be one since it sits alongside the
 * DOM rather than becoming part of the DOM model.
 *
 * (This used to be an internal class within DOM2DTM. Moved out because
 * I need to perform an instanceof operation on it to support a temporary
 * workaround in DTMManagerDefault.)
 *
 * %REVIEW% What if the DOM2DTM was built around a DocumentFragment and
 * there isn't a single root element? I think this fails that case...
 *
 * %REVIEW% An alternative solution would be to create the node _only_
 * in DTM space, but given how DOM2DTM is currently written I think
 * this is simplest.
 * <p>
 *  DOM2DTM模型基本上,它在DOM空间中创建一个代理节点来承载附加信息这是_NOT_一个完整的DOM实现,不应该是一个,因为它位于DOM旁边,而不是成为DOM模型的一部分
 * 
 *  (这曾经是DOM2DTM内的一个内部类,因为我需要对它执行一个instanceof操作来支持DTMManagerDefault中的临时解决方法)
 * 
 *  ％REVIEW％如果DOM2DTM是围绕DocumentFragment构建的,并且没有单个根元素怎么办?我认为这不符合这种情况
 * 
 * ％REVIEW％一个替代解决方案是在DTM空间中创建节点_only_,但考虑到如何DOM2DTM当前写的我认为这是最简单的
 * 
 * 
 * */
public class DOM2DTMdefaultNamespaceDeclarationNode implements Attr,TypeInfo
{
  final String NOT_SUPPORTED_ERR="Unsupported operation on pseudonode";

  Element pseudoparent;
  String prefix,uri,nodename;
  int handle;
  DOM2DTMdefaultNamespaceDeclarationNode(Element pseudoparent,String prefix,String uri,int handle)
  {
    this.pseudoparent=pseudoparent;
    this.prefix=prefix;
    this.uri=uri;
    this.handle=handle;
    this.nodename="xmlns:"+prefix;
  }
  public String getNodeName() {return nodename;}
  public String getName() {return nodename;}
  public String getNamespaceURI() {return "http://www.w3.org/2000/xmlns/";}
  public String getPrefix() {return prefix;}
  public String getLocalName() {return prefix;}
  public String getNodeValue() {return uri;}
  public String getValue() {return uri;}
  public Element getOwnerElement() {return pseudoparent;}

  public boolean isSupported(String feature, String version) {return false;}
  public boolean hasChildNodes() {return false;}
  public boolean hasAttributes() {return false;}
  public Node getParentNode() {return null;}
  public Node getFirstChild() {return null;}
  public Node getLastChild() {return null;}
  public Node getPreviousSibling() {return null;}
  public Node getNextSibling() {return null;}
  public boolean getSpecified() {return false;}
  public void normalize() {return;}
  public NodeList getChildNodes() {return null;}
  public NamedNodeMap getAttributes() {return null;}
  public short getNodeType() {return Node.ATTRIBUTE_NODE;}
  public void setNodeValue(String value) {throw new DTMException(NOT_SUPPORTED_ERR);}
  public void setValue(String value) {throw new DTMException(NOT_SUPPORTED_ERR);}
  public void setPrefix(String value) {throw new DTMException(NOT_SUPPORTED_ERR);}
  public Node insertBefore(Node a, Node b) {throw new DTMException(NOT_SUPPORTED_ERR);}
  public Node replaceChild(Node a, Node b) {throw new DTMException(NOT_SUPPORTED_ERR);}
  public Node appendChild(Node a) {throw new DTMException(NOT_SUPPORTED_ERR);}
  public Node removeChild(Node a) {throw new DTMException(NOT_SUPPORTED_ERR);}
  public Document getOwnerDocument() {return pseudoparent.getOwnerDocument();}
  public Node cloneNode(boolean deep) {throw new DTMException(NOT_SUPPORTED_ERR);}

    /** Non-DOM method, part of the temporary kluge
     * %REVIEW% This would be a pruning problem, but since it will always be
     * added to the root element and we prune on elements, we shouldn't have
     * to worry.
     * <p>
     *  ％REVIEW％这将是一个修剪问题,但由于它将总是添加到根元素,我们修剪元素,我们不应该担心
     * 
     */
    public int getHandleOfNode()
    {
        return handle;
    }

    //RAMESH: PENDING=> Add proper implementation for the below DOM L3 additions

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.TypeInfo#getTypeName()
     */
    public String getTypeName() {return null; }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.TypeInfo#getTypeNamespace()
     */
    public String getTypeNamespace() { return null;}

    /**
    /* <p>
    /* 
     * @see or.gw3c.dom.TypeInfo#isDerivedFrom(String,String,int)
     */
    public boolean isDerivedFrom( String ns, String localName, int derivationMethod ) {
        return false;
    }

    public TypeInfo getSchemaTypeInfo() { return this; }

    public boolean isId( ) { return false; }

    /**
     * Associate an object to a key on this node. The object can later be
     * retrieved from this node by calling <code>getUserData</code> with the
     * same key.
     * <p>
     *  将对象与此节点上的键相关联稍后可以通过使用相同的键调用<code> getUserData </code>从此节点检索对象
     * 
     * 
     * @param key The key to associate the object to.
     * @param data The object to associate to the given key, or
     *   <code>null</code> to remove any existing association to that key.
     * @param handler The handler to associate to that key, or
     *   <code>null</code>.
     * @return Returns the <code>DOMObject</code> previously associated to
     *   the given key on this node, or <code>null</code> if there was none.
     * @since DOM Level 3
     */
    public Object setUserData(String key,
                              Object data,
                              UserDataHandler handler) {
        return getOwnerDocument().setUserData( key, data, handler);
    }

    /**
     * Retrieves the object associated to a key on a this node. The object
     * must first have been set to this node by calling
     * <code>setUserData</code> with the same key.
     * <p>
     *  检索与此节点上的键相关联的对象必须首先通过使用相同的键调用<code> setUserData </code>将该对象设置为此节点
     * 
     * 
     * @param key The key the object is associated to.
     * @return Returns the <code>DOMObject</code> associated to the given key
     *   on this node, or <code>null</code> if there was none.
     * @since DOM Level 3
     */
    public Object getUserData(String key) {
        return getOwnerDocument().getUserData( key);
    }

    /**
     *  This method returns a specialized object which implements the
     * specialized APIs of the specified feature and version. The
     * specialized object may also be obtained by using binding-specific
     * casting methods but is not necessarily expected to, as discussed in Mixed DOM implementations.
     * <p>
     * 此方法返回实现指定的特征和版本的专用API的专用对象。专用对象也可以通过使用特定于绑定的转换方法来获得,但不一定预期到,如在混合DOM实现中所讨论的
     * 
     * 
     * @param feature The name of the feature requested (case-insensitive).
     * @param version  This is the version number of the feature to test. If
     *   the version is <code>null</code> or the empty string, supporting
     *   any version of the feature will cause the method to return an
     *   object that supports at least one version of the feature.
     * @return  Returns an object which implements the specialized APIs of
     *   the specified feature and version, if any, or <code>null</code> if
     *   there is no object which implements interfaces associated with that
     *   feature. If the <code>DOMObject</code> returned by this method
     *   implements the <code>Node</code> interface, it must delegate to the
     *   primary core <code>Node</code> and not return results inconsistent
     *   with the primary core <code>Node</code> such as attributes,
     *   childNodes, etc.
     * @since DOM Level 3
     */
    public Object getFeature(String feature, String version) {
        // we don't have any alternate node, either this node does the job
        // or we don't have anything that does
        return isSupported(feature, version) ? this : null;
    }

    /**
     * Tests whether two nodes are equal.
     * <br>This method tests for equality of nodes, not sameness (i.e.,
     * whether the two nodes are references to the same object) which can be
     * tested with <code>Node.isSameNode</code>. All nodes that are the same
     * will also be equal, though the reverse may not be true.
     * <br>Two nodes are equal if and only if the following conditions are
     * satisfied: The two nodes are of the same type.The following string
     * attributes are equal: <code>nodeName</code>, <code>localName</code>,
     * <code>namespaceURI</code>, <code>prefix</code>, <code>nodeValue</code>
     * , <code>baseURI</code>. This is: they are both <code>null</code>, or
     * they have the same length and are character for character identical.
     * The <code>attributes</code> <code>NamedNodeMaps</code> are equal.
     * This is: they are both <code>null</code>, or they have the same
     * length and for each node that exists in one map there is a node that
     * exists in the other map and is equal, although not necessarily at the
     * same index.The <code>childNodes</code> <code>NodeLists</code> are
     * equal. This is: they are both <code>null</code>, or they have the
     * same length and contain equal nodes at the same index. This is true
     * for <code>Attr</code> nodes as for any other type of node. Note that
     * normalization can affect equality; to avoid this, nodes should be
     * normalized before being compared.
     * <br>For two <code>DocumentType</code> nodes to be equal, the following
     * conditions must also be satisfied: The following string attributes
     * are equal: <code>publicId</code>, <code>systemId</code>,
     * <code>internalSubset</code>.The <code>entities</code>
     * <code>NamedNodeMaps</code> are equal.The <code>notations</code>
     * <code>NamedNodeMaps</code> are equal.
     * <br>On the other hand, the following do not affect equality: the
     * <code>ownerDocument</code> attribute, the <code>specified</code>
     * attribute for <code>Attr</code> nodes, the
     * <code>isWhitespaceInElementContent</code> attribute for
     * <code>Text</code> nodes, as well as any user data or event listeners
     * registered on the nodes.
     * <p>
     * 测试两个节点是否相等<br>此方法测试节点的相等性,而不是同一性(即,两个节点是否是对同一对象的引用),可以使用<code> NodeisSameNode </code>测试所有节点相同也将是相等的,虽
     * 然相反可能不是真实的<br>当且仅当满足以下条件时,两个节点是相等的：两个节点是相同类型以下字符串属性是相等的：<code> nodeName < / code>,<code> localName </code>
     * ,<code> namespaceURI </code>,<code>前缀</code>,<code> nodeValue </code> ：它们都是<code> null </code>,或者它们具有
     * 相同的长度,并且是字符相同的字符</code> </code> </code> <NamedNodeMaps </code>这是：它们都是<code> null </code>,或者它们具有相同的长度,
     * 并且对于存在于一个映射中的每个节点,存在存在于另一个映射中并且相等的节点,但是不一定是相同的index </code> </Node> </Node> <Node> <Node> </code>是相等的
     * 。
     * 它们都是<code> null </code>,或者它们具有相同的长度,对于<code> Attr </code>节点,与任何其他类型的节点一样。
     * 注意,规范化可以影响等式;为避免这种情况,应在比较之前对节点进行归一化<br>为使两个<code> DocumentType </code>节点相等,还必须满足以下条件：以下字符串属性相等：<code>
     *  publicId < code>,<code> systemId </code>,<code> internalSubset </code><code> entities </code> <code>
     *  NamedNodeMaps </code>是相等的<code>符号</code> <code> NamedNodeMaps </code>是相等的<br>另一方面,平等：<code> ownerDoc
     * ument </code>属性,<code> Attr </code>节点的<code>指定</code>属性,<code> isWhitespaceInElementContent </code> /
     *  code>节点,以及在节点上注册的任何用户数据或事件侦听器。
     * 它们都是<code> null </code>,或者它们具有相同的长度,对于<code> Attr </code>节点,与任何其他类型的节点一样。
     * 
     * 
     * @param arg The node to compare equality with.
     * @param deep If <code>true</code>, recursively compare the subtrees; if
     *   <code>false</code>, compare only the nodes themselves (and its
     *   attributes, if it is an <code>Element</code>).
     * @return If the nodes, and possibly subtrees are equal,
     *   <code>true</code> otherwise <code>false</code>.
     * @since DOM Level 3
     */
    public boolean isEqualNode(Node arg) {
        if (arg == this) {
            return true;
        }
        if (arg.getNodeType() != getNodeType()) {
            return false;
        }
        // in theory nodeName can't be null but better be careful
        // who knows what other implementations may be doing?...
        if (getNodeName() == null) {
            if (arg.getNodeName() != null) {
                return false;
            }
        }
        else if (!getNodeName().equals(arg.getNodeName())) {
            return false;
        }

        if (getLocalName() == null) {
            if (arg.getLocalName() != null) {
                return false;
            }
        }
        else if (!getLocalName().equals(arg.getLocalName())) {
            return false;
        }

        if (getNamespaceURI() == null) {
            if (arg.getNamespaceURI() != null) {
                return false;
            }
        }
        else if (!getNamespaceURI().equals(arg.getNamespaceURI())) {
            return false;
        }

        if (getPrefix() == null) {
            if (arg.getPrefix() != null) {
                return false;
            }
        }
        else if (!getPrefix().equals(arg.getPrefix())) {
            return false;
        }

        if (getNodeValue() == null) {
            if (arg.getNodeValue() != null) {
                return false;
            }
        }
        else if (!getNodeValue().equals(arg.getNodeValue())) {
            return false;
        }
    /*
        if (getBaseURI() == null) {
            if (((NodeImpl) arg).getBaseURI() != null) {
                return false;
            }
        }
        else if (!getBaseURI().equals(((NodeImpl) arg).getBaseURI())) {
            return false;
        }
    /* <p>
    /* if(getBaseURI()== null){if(((NodeImpl)arg)getBaseURI()！ }} else if(！getBaseURI()equals((NodeImpl)arg)
    /* getBaseURI())){return false; }}。
    /* 
*/

             return true;
    }

    /**
     * DOM Level 3 - Experimental:
     * Look up the namespace URI associated to the given prefix, starting from this node.
     * Use lookupNamespaceURI(null) to lookup the default namespace
     *
     * <p>
     *  DOM级别3  - 实验：查找与给定前缀关联的命名空间URI,从此节点开始使用lookupNamespaceURI(null)查找默认命名空间
     * 
     * 
     * @param namespaceURI
     * @return th URI for the namespace
     * @since DOM Level 3
     */
    public String lookupNamespaceURI(String specifiedPrefix) {
        short type = this.getNodeType();
        switch (type) {
        case Node.ELEMENT_NODE : {

                String namespace = this.getNamespaceURI();
                String prefix = this.getPrefix();
                if (namespace !=null) {
                    // REVISIT: is it possible that prefix is empty string?
                    if (specifiedPrefix== null && prefix==specifiedPrefix) {
                        // looking for default namespace
                        return namespace;
                    } else if (prefix != null && prefix.equals(specifiedPrefix)) {
                        // non default namespace
                        return namespace;
                    }
                }
                if (this.hasAttributes()) {
                    NamedNodeMap map = this.getAttributes();
                    int length = map.getLength();
                    for (int i=0;i<length;i++) {
                        Node attr = map.item(i);
                        String attrPrefix = attr.getPrefix();
                        String value = attr.getNodeValue();
                        namespace = attr.getNamespaceURI();
                        if (namespace !=null && namespace.equals("http://www.w3.org/2000/xmlns/")) {
                            // at this point we are dealing with DOM Level 2 nodes only
                            if (specifiedPrefix == null &&
                                attr.getNodeName().equals("xmlns")) {
                                // default namespace
                                return value;
                            } else if (attrPrefix !=null &&
                                       attrPrefix.equals("xmlns") &&
                                       attr.getLocalName().equals(specifiedPrefix)) {
                 // non default namespace
                                return value;
                            }
                        }
                    }
                }
                /*
                NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
                if (ancestor != null) {
                    return ancestor.lookupNamespaceURI(specifiedPrefix);
                }
                /* <p>
                /*  NodeImpl ancestor =(NodeImpl)getElementAncestor(this); if(ancestor！= null){return ancestorlookupNamespaceURI(specifiedPrefix); }
                /* }。
                /* 
                */

                return null;


            }
/*
        case Node.DOCUMENT_NODE : {
                return((NodeImpl)((Document)this).getDocumentElement()).lookupNamespaceURI(specifiedPrefix) ;
            }
/* <p>
/*  case NodeDOCUMENT_NODE：{return((NodeImpl)((Document)this)getDocumentElement())lookupNamespaceURI(specifiedPrefix); }
/* }。
/* 
*/
        case Node.ENTITY_NODE :
        case Node.NOTATION_NODE:
        case Node.DOCUMENT_FRAGMENT_NODE:
        case Node.DOCUMENT_TYPE_NODE:
            // type is unknown
            return null;
        case Node.ATTRIBUTE_NODE:{
                if (this.getOwnerElement().getNodeType() == Node.ELEMENT_NODE) {
                    return getOwnerElement().lookupNamespaceURI(specifiedPrefix);

                }
                return null;
            }
        default:{
           /*
                NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
                if (ancestor != null) {
                    return ancestor.lookupNamespaceURI(specifiedPrefix);
                }
           /* <p>
           /*  NodeImpl ancestor =(NodeImpl)getElementAncestor(this); if(ancestor！= null){return ancestorlookupNamespaceURI(specifiedPrefix); }
           /* }。
           /* 
             */
                return null;
            }

        }
    }

    /**
     *  DOM Level 3: Experimental
     *  This method checks if the specified <code>namespaceURI</code> is the
     *  default namespace or not.
     * <p>
     * DOM Level 3：Experimental此方法检查指定的<code> namespaceURI </code>是否是默认命名空间
     * 
     * 
     *  @param namespaceURI The namespace URI to look for.
     *  @return  <code>true</code> if the specified <code>namespaceURI</code>
     *   is the default namespace, <code>false</code> otherwise.
     * @since DOM Level 3
     */
    public boolean isDefaultNamespace(String namespaceURI){
       /*
        // REVISIT: remove casts when DOM L3 becomes REC.
        short type = this.getNodeType();
        switch (type) {
        case Node.ELEMENT_NODE: {
            String namespace = this.getNamespaceURI();
            String prefix = this.getPrefix();

            // REVISIT: is it possible that prefix is empty string?
            if (prefix == null || prefix.length() == 0) {
                if (namespaceURI == null) {
                    return (namespace == namespaceURI);
                }
                return namespaceURI.equals(namespace);
            }
            if (this.hasAttributes()) {
                ElementImpl elem = (ElementImpl)this;
                NodeImpl attr = (NodeImpl)elem.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
                if (attr != null) {
                    String value = attr.getNodeValue();
                    if (namespaceURI == null) {
                        return (namespace == value);
                    }
                    return namespaceURI.equals(value);
                }
            }

            NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
            if (ancestor != null) {
                return ancestor.isDefaultNamespace(namespaceURI);
            }
            return false;
        }
        case Node.DOCUMENT_NODE:{
                return((NodeImpl)((Document)this).getDocumentElement()).isDefaultNamespace(namespaceURI);
            }

        case Node.ENTITY_NODE :
          case Node.NOTATION_NODE:
        case Node.DOCUMENT_FRAGMENT_NODE:
        case Node.DOCUMENT_TYPE_NODE:
            // type is unknown
            return false;
        case Node.ATTRIBUTE_NODE:{
                if (this.ownerNode.getNodeType() == Node.ELEMENT_NODE) {
                    return ownerNode.isDefaultNamespace(namespaceURI);

                }
                return false;
            }
        default:{
                NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
                if (ancestor != null) {
                    return ancestor.isDefaultNamespace(namespaceURI);
                }
                return false;
            }

        }
       /* <p>
       /*  // REVISIT：当DOM L3成为REC short时删除casts type = thisgetNodeType(); switch(type){case NodeELEMENT_NODE：{String namespace = thisgetNamespaceURI(); String prefix = thisgetPrefix();。
       /* 
       /* // REVISIT：可能的前缀是空字符串? if(prefix == null || prefixlength()== 0){if(namespaceURI == null){return(namespace == namespaceURI); }
       /*  return namespaceURIequals(namespace); } if(thishasAttributes()){ElementImpl elem =(ElementImpl)this; NodeImpl attr =(NodeImpl)elemgetAttributeNodeNS("http：// wwww3org / 2000 / xmlns /","xmlns"); if(attr！= null){String value = attrgetNodeValue(); if(namespaceURI == null){return(namespace == value); }
       /*  return namespaceURIequals(value); }}。
       /* 
       /*  NodeImpl ancestor =(NodeImpl)getElementAncestor(this); if(ancestor！= null){return ancestorisDefaultNamespace(namespaceURI); }
       /*  return false; } case NodeDOCUMENT_NODE：{return((NodeImpl)((Document)this)getDocumentElement())isDefaultNamespace(namespaceURI); }
       /* }。
       /* 
       /* case NodeENTITY_NODE：case NodeNOTATION_NODE：case NodeDOCUMENT_FRAGMENT_NODE：case NodeDOCUMENT_TYPE_NO
       /* DE：// type is unknown return false; case NodeATTRIBUTE_NODE：{if(thisownerNodegetNodeType()== NodeELEMENT_NODE){return ownerNodeisDefaultNamespace(namespaceURI);。
       /* 
       /*  } return false; } default：{NodeImpl ancestor =(NodeImpl)getElementAncestor(this); if(ancestor！= null){return ancestorisDefaultNamespace(namespaceURI); }
       /*  return false; }}。
       /* 
       /*  }}
       /* 
*/
        return false;


    }

    /**
     *
     * DOM Level 3 - Experimental:
     * Look up the prefix associated to the given namespace URI, starting from this node.
     *
     * <p>
     *  DOM Level 3  - 实验性：查找与给定命名空间URI相关联的前缀,从此节点开始
     * 
     * 
     * @param namespaceURI
     * @return the prefix for the namespace
     */
    public String lookupPrefix(String namespaceURI){

        // REVISIT: When Namespaces 1.1 comes out this may not be true
        // Prefix can't be bound to null namespace
        if (namespaceURI == null) {
            return null;
        }

        short type = this.getNodeType();

        switch (type) {
/*
        case Node.ELEMENT_NODE: {

                String namespace = this.getNamespaceURI(); // to flip out children
                return lookupNamespacePrefix(namespaceURI, (ElementImpl)this);
            }

        case Node.DOCUMENT_NODE:{
                return((NodeImpl)((Document)this).getDocumentElement()).lookupPrefix(namespaceURI);
            }
/* <p>
/*  case NodeELEMENT_NODE：{
/* 
/*  String namespace = thisgetNamespaceURI(); // to flip out children return lookupNamespacePrefix(names
/* paceURI,(ElementImpl)this); }}。
/* 
/* case NodeDOCUMENT_NODE：{return((NodeImpl)((Document)this)getDocumentElement())lookupPrefix(namespaceURI); }
/* }。
/* 
*/
        case Node.ENTITY_NODE :
        case Node.NOTATION_NODE:
        case Node.DOCUMENT_FRAGMENT_NODE:
        case Node.DOCUMENT_TYPE_NODE:
            // type is unknown
            return null;
        case Node.ATTRIBUTE_NODE:{
                if (this.getOwnerElement().getNodeType() == Node.ELEMENT_NODE) {
                    return getOwnerElement().lookupPrefix(namespaceURI);

                }
                return null;
            }
        default:{
/*
                NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
                if (ancestor != null) {
                    return ancestor.lookupPrefix(namespaceURI);
                }
/* <p>
/*  NodeImpl ancestor =(NodeImpl)getElementAncestor(this); if(ancestor！= null){return ancestorlookupPrefix(namespaceURI); }
/* }。
/* 
*/
                return null;
            }
         }
    }

    /**
     * Returns whether this node is the same node as the given one.
     * <br>This method provides a way to determine whether two
     * <code>Node</code> references returned by the implementation reference
     * the same object. When two <code>Node</code> references are references
     * to the same object, even if through a proxy, the references may be
     * used completely interchangably, such that all attributes have the
     * same values and calling the same DOM method on either reference
     * always has exactly the same effect.
     * <p>
     *  返回此节点是否与给定的节点相同的节点<br>此方法提供了一种方法来确定实现引用的两个<code> Node </code>引用是否引用相同的对象当两个<code> Node </code >引用是对同
     * 一对象的引用,即使通过代理,引用可以完全互换使用,使得所有属性具有相同的值,并且对任一引用调用相同的DOM方法总是具有完全相同的效果。
     * 
     * 
     * @param other The node to test against.
     * @return Returns <code>true</code> if the nodes are the same,
     *   <code>false</code> otherwise.
     * @since DOM Level 3
     */
    public boolean isSameNode(Node other) {
        // we do not use any wrapper so the answer is obvious
        return this == other;
    }

    /**
     * This attribute returns the text content of this node and its
     * descendants. When it is defined to be null, setting it has no effect.
     * When set, any possible children this node may have are removed and
     * replaced by a single <code>Text</code> node containing the string
     * this attribute is set to. On getting, no serialization is performed,
     * the returned string does not contain any markup. No whitespace
     * normalization is performed, the returned string does not contain the
     * element content whitespaces . Similarly, on setting, no parsing is
     * performed either, the input string is taken as pure textual content.
     * <br>The string returned is made of the text content of this node
     * depending on its type, as defined below:
     * <table border='1'>
     * <tr>
     * <th>Node type</th>
     * <th>Content</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * ELEMENT_NODE, ENTITY_NODE, ENTITY_REFERENCE_NODE,
     * DOCUMENT_FRAGMENT_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>concatenation of the <code>textContent</code>
     * attribute value of every child node, excluding COMMENT_NODE and
     * PROCESSING_INSTRUCTION_NODE nodes</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>ATTRIBUTE_NODE, TEXT_NODE,
     * CDATA_SECTION_NODE, COMMENT_NODE, PROCESSING_INSTRUCTION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>
     * <code>nodeValue</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>DOCUMENT_NODE, DOCUMENT_TYPE_NODE, NOTATION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>
     * null</td>
     * </tr>
     * </table>
     * <p>
     * 此属性返回此节点及其后代的文本内容当定义为null时,设置它无效果设置时,此节点可能具有的任何可能的子项都将被单个<code> Text </code>包含字符串的节点此属性设置为On获取,不执行序列化
     * ,返回的字符串不包含任何标记不执行空白标准化,返回的字符串不包含元素内容whitespaces类似地,在设置时,不执行解析则输入字符串将被视为纯文本内容<br>返回的字符串取决于此节点的文本内容,具体取
     * 决于其类型,如下所述：。
     * <table border='1'>
     * <tr>
     *  <th>节点类型</th> <th>内容</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * 每个子节点的<code> textContent </code>属性值的连接,不包括COMMENT_NODE和("COMMENT_NODE")。
     *  PROCESSING_INSTRUCTION_NODE个节点</td>。
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> ATTRIBUTE_NODE,TEXT_NODE,CDATA_SECTION_NODE,COMMENT_NODE,
     * PROCESSING_INSTRUCTION_NODE </td>。
     * <td valign='top' rowspan='1' colspan='1'>
     *  <code> nodeValue </code> </td>
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> DOCUMENT_NODE,DOCUMENT_TYPE_NODE,NOTATION_NODE </td>
     * <td valign='top' rowspan='1' colspan='1'>
     *  null </td>
     * </tr>
     * </table>
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     * @exception DOMException
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than
     *   fit in a <code>DOMString</code> variable on the implementation
     *   platform.
     * @since DOM Level 3
     */
    public void setTextContent(String textContent)
        throws DOMException {
        setNodeValue(textContent);
    }

    /**
     * This attribute returns the text content of this node and its
     * descendants. When it is defined to be null, setting it has no effect.
     * When set, any possible children this node may have are removed and
     * replaced by a single <code>Text</code> node containing the string
     * this attribute is set to. On getting, no serialization is performed,
     * the returned string does not contain any markup. No whitespace
     * normalization is performed, the returned string does not contain the
     * element content whitespaces . Similarly, on setting, no parsing is
     * performed either, the input string is taken as pure textual content.
     * <br>The string returned is made of the text content of this node
     * depending on its type, as defined below:
     * <table border='1'>
     * <tr>
     * <th>Node type</th>
     * <th>Content</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * ELEMENT_NODE, ENTITY_NODE, ENTITY_REFERENCE_NODE,
     * DOCUMENT_FRAGMENT_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>concatenation of the <code>textContent</code>
     * attribute value of every child node, excluding COMMENT_NODE and
     * PROCESSING_INSTRUCTION_NODE nodes</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>ATTRIBUTE_NODE, TEXT_NODE,
     * CDATA_SECTION_NODE, COMMENT_NODE, PROCESSING_INSTRUCTION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>
     * <code>nodeValue</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>DOCUMENT_NODE, DOCUMENT_TYPE_NODE, NOTATION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>
     * null</td>
     * </tr>
     * </table>
     * <p>
     * 此属性返回此节点及其后代的文本内容当定义为null时,设置它无效果设置时,此节点可能具有的任何可能的子项都将被单个<code> Text </code>包含字符串的节点此属性设置为On获取,不执行序列化
     * ,返回的字符串不包含任何标记不执行空白标准化,返回的字符串不包含元素内容whitespaces类似地,在设置时,不执行解析则输入字符串将被视为纯文本内容<br>返回的字符串取决于此节点的文本内容,具体取
     * 决于其类型,如下所述：。
     * <table border='1'>
     * <tr>
     *  <th>节点类型</th> <th>内容</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * 每个子节点的<code> textContent </code>属性值的连接,不包括COMMENT_NODE和("COMMENT_NODE")。
     *  PROCESSING_INSTRUCTION_NODE个节点</td>。
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> ATTRIBUTE_NODE,TEXT_NODE,CDATA_SECTION_NODE,COMMENT_NODE,
     * PROCESSING_INSTRUCTION_NODE </td>。
     * <td valign='top' rowspan='1' colspan='1'>
     *  <code> nodeValue </code> </td>
     * </tr>
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     * @exception DOMException
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than
     *   fit in a <code>DOMString</code> variable on the implementation
     *   platform.
     * @since DOM Level 3
     */
    public String getTextContent() throws DOMException {
        return getNodeValue();  // overriden in some subclasses
    }

    /**
     * Compares a node with this node with regard to their position in the
     * document.
     * <p>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> DOCUMENT_NODE,DOCUMENT_TYPE_NODE,NOTATION_NODE </td>
     * <td valign='top' rowspan='1' colspan='1'>
     *  null </td>
     * </tr>
     * </table>
     * 
     * @param other The node to compare against this node.
     * @return Returns how the given node is positioned relatively to this
     *   node.
     * @since DOM Level 3
     */
    public short compareDocumentPosition(Node other) throws DOMException {
        return 0;
    }

    /**
     * The absolute base URI of this node or <code>null</code> if undefined.
     * This value is computed according to . However, when the
     * <code>Document</code> supports the feature "HTML" , the base URI is
     * computed using first the value of the href attribute of the HTML BASE
     * element if any, and the value of the <code>documentURI</code>
     * attribute from the <code>Document</code> interface otherwise.
     * <br> When the node is an <code>Element</code>, a <code>Document</code>
     * or a a <code>ProcessingInstruction</code>, this attribute represents
     * the properties [base URI] defined in . When the node is a
     * <code>Notation</code>, an <code>Entity</code>, or an
     * <code>EntityReference</code>, this attribute represents the
     * properties [declaration base URI] in the . How will this be affected
     * by resolution of relative namespace URIs issue?It's not.Should this
     * only be on Document, Element, ProcessingInstruction, Entity, and
     * Notation nodes, according to the infoset? If not, what is it equal to
     * on other nodes? Null? An empty string? I think it should be the
     * parent's.No.Should this be read-only and computed or and actual
     * read-write attribute?Read-only and computed (F2F 19 Jun 2000 and
     * teleconference 30 May 2001).If the base HTML element is not yet
     * attached to a document, does the insert change the Document.baseURI?
     * Yes. (F2F 26 Sep 2001)
     * <p>
     *  将节点与此节点在文档中的位置进行比较
     * 
     * 
     * @since DOM Level 3
     */
    public String getBaseURI() {
        return null;
    }
}

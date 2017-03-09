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
 *      http://www.apache.org/licenses/LICENSE-2.0
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

package com.sun.org.apache.xerces.internal.dom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.w3c.dom.TypeInfo;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Attribute represents an XML-style attribute of an
 * Element. Typically, the allowable values are controlled by its
 * declaration in the Document Type Definition (DTD) governing this
 * kind of document.
 * <P>
 * If the attribute has not been explicitly assigned a value, but has
 * been declared in the DTD, it will exist and have that default. Only
 * if neither the document nor the DTD specifies a value will the
 * Attribute really be considered absent and have no value; in that
 * case, querying the attribute will return null.
 * <P>
 * Attributes may have multiple children that contain their data. (XML
 * allows attributes to contain entity references, and tokenized
 * attribute types such as NMTOKENS may have a child for each token.)
 * For convenience, the Attribute object's getValue() method returns
 * the string version of the attribute's value.
 * <P>
 * Attributes are not children of the Elements they belong to, in the
 * usual sense, and have no valid Parent reference. However, the spec
 * says they _do_ belong to a specific Element, and an INUSE exception
 * is to be thrown if the user attempts to explicitly share them
 * between elements.
 * <P>
 * Note that Elements do not permit attributes to appear to be shared
 * (see the INUSE exception), so this object's mutability is
 * officially not an issue.
 * <p>
 * Note: The ownerNode attribute is used to store the Element the Attr
 * node is associated with. Attr nodes do not have parent nodes.
 * Besides, the getOwnerElement() method can be used to get the element node
 * this attribute is associated with.
 * <P>
 * AttrImpl does not support Namespaces. AttrNSImpl, which inherits from
 * it, does.
 *
 * <p>AttrImpl used to inherit from ParentNode. It now directly inherits from
 * NodeImpl and provide its own implementation of the ParentNode's behavior.
 * The reason is that we now try and avoid to always create a Text node to
 * hold the value of an attribute. The DOM spec requires it, so we still have
 * to do it in case getFirstChild() is called for instance. The reason
 * attribute values are stored as a list of nodes is so that they can carry
 * more than a simple string. They can also contain EntityReference nodes.
 * However, most of the times people only have a single string that they only
 * set and get through Element.set/getAttribute or Attr.set/getValue. In this
 * new version, the Attr node has a value pointer which can either be the
 * String directly or a pointer to the first ChildNode. A flag tells which one
 * it currently is. Note that while we try to stick with the direct String as
 * much as possible once we've switched to a node there is no going back. This
 * is because we have no way to know whether the application keeps referring to
 * the node we once returned.
 * <p> The gain in memory varies on the density of attributes in the document.
 * But in the tests I've run I've seen up to 12% of memory gain. And the good
 * thing is that it also leads to a slight gain in speed because we allocate
 * fewer objects! I mean, that's until we have to actually create the node...
 * <p>
 * To avoid too much duplicated code, I got rid of ParentNode and renamed
 * ChildAndParentNode, which I never really liked, to ParentNode for
 * simplicity, this doesn't make much of a difference in memory usage because
 * there are only very few objects that are only a Parent. This is only true
 * now because AttrImpl now inherits directly from NodeImpl and has its own
 * implementation of the ParentNode's node behavior. So there is still some
 * duplicated code there.
 * <p>
 * This class doesn't directly support mutation events, however, it notifies
 * the document when mutations are performed so that the document class do so.
 *
 * <p><b>WARNING</b>: Some of the code here is partially duplicated in
 * ParentNode, be careful to keep these two classes in sync!
 *
 * @xerces.internal
 *
 * <p>
 *  属性表示元素的XML样式属性。通常,允许值由其在管理此类文档的文档类型定义(DTD)中的声明控​​制。
 * <P>
 *  如果属性没有显式分配一个值,但已在DTD中声明,它将存在并具有该缺省值。只有当文档和DTD都没有指定一个值时,属性才会被认为是空的并且没有值;在这种情况下,查询属性将返回null。
 * <P>
 * 属性可能有多个包含其数据的子级。 (XML允许属性包含实体引用,并且标记化的属性类型,如NMTOKENS可能为每个标记有一个子类。
 * )为方便起见,Attribute对象的getValue()方法返回属性值的字符串版本。
 * <P>
 *  属性不是它们属于的元素的子元素,通常意义上,并没有有效的父引用。但是,规范说他们_do_属于一个特定的元素,如果用户尝试在元素之间显式共享它们,则抛出INUSE异常。
 * <P>
 *  请注意,元素不允许属性显示为共享(参见INUSE异常),因此该对象的可变性在官方上不是问题。
 * <p>
 *  注意：ownerNode属性用于存储与Attr节点相关联的元素。 Attr节点没有父节点。此外,getOwnerElement()方法可用于获取此属性关联的元素节点。
 * <P>
 *  AttrImpl不支持命名空间。 AttrNSImpl,它继承它。
 * 
 * <p> AttrImpl用于从ParentNode继承。它现在直接继承自NodeImpl并提供自己的实现ParentNode的行为。原因是我们现在尝试避免总是创建一个Text节点来保存属性的值。
 *  DOM规范需要它,所以我们仍然必须这样做,如果实例调用getFirstChild()。属性值被存储为节点列表的原因是它们可以携带多于一个简单的字符串。
 * 它们还可以包含EntityReference节点。然而,大多数时候人们只有一个字符串,他们只设置和获取通过Element.set / getAttribute或Attr.set / getValue。
 * 在这个新版本中,Attr节点有一个值指针,它可以是字符串直接或指向第一个子节点的指针。一个标志告诉它当前是哪一个。请注意,虽然我们尽量坚持使用直接String,尽可能一旦我们切换到一个节点,没有回来。
 * 这是因为我们没有办法知道应用程序是否继续引用我们曾经返回的节点。 <p>内存中的增益因文档中属性的密度而异。但在我运行的测试中,我已经看到高达12％的内存增益。
 * 好的事情是,它也导致速度略微增加,因为我们分配更少的对象！我的意思是,这是,直到我们必须实际创建节点...。
 * <p>
 * 为了避免太多重复的代码,为了简单起见,我删除了ParentNode并将ParentAndParentNode重命名为ParentNode,为了简单起见,我从来没有真正喜欢过它,但是这不会对内存使用产生太
 * 大的影响,因为只有很少的对象父母。
 * 这只是真的现在,因为AttrImpl现在直接从NodeImpl继承,并有自己的父节点的节点行为的实现。所以还有一些重复的代码。
 * <p>
 *  这个类不直接支持突变事件,但是,当执行突变时它通知文档,以便文档类这样做。
 * 
 *  <p> <b>警告</b>：此处的部分代码在ParentNode中部分重复,请小心保持这两个类同步！
 * 
 *  @ xerces.internal
 * 
 * 
 * @see AttrNSImpl
 *
 * @author Arnaud  Le Hors, IBM
 * @author Joe Kesselman, IBM
 * @author Andy Clark, IBM
 * @version $Id: AttrImpl.java,v 1.5 2008/06/10 00:59:32 joehw Exp $
 * @since PR-DOM-Level-1-19980818.
 *
 */
public class AttrImpl
    extends NodeImpl
    implements Attr, TypeInfo{

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 7277707688218972102L;

    /** DTD namespace. **/
    static final String DTD_URI = "http://www.w3.org/TR/REC-xml";

    //
    // Data
    //

    /** This can either be a String or the first child node. */
    protected Object value = null;

    /** Attribute name. */
    protected String name;

    /** Type information */
    // REVISIT: we are losing the type information in DOM during serialization
    transient Object type;

    protected TextImpl textNode = null;

    //
    // Constructors
    //

    /**
     * Attribute has no public constructor. Please use the factory
     * method in the Document class.
     * <p>
     *  属性没有公共构造函数。请在Document类中使用工厂方法。
     * 
     */
    protected AttrImpl(CoreDocumentImpl ownerDocument, String name) {
        super(ownerDocument);
        this.name = name;
        /** False for default attributes. */
        isSpecified(true);
        hasStringValue(true);
    }

    // for AttrNSImpl
    protected AttrImpl() {}

    // Support for DOM Level 3 renameNode method.
    // Note: This only deals with part of the pb. It is expected to be
    // called after the Attr has been detached for one thing.
    // CoreDocumentImpl does all the work.
    void rename(String name) {
        if (needsSyncData()) {
            synchronizeData();
        }
        this.name = name;
    }

    // create a real text node as child if we don't have one yet
    protected void makeChildNode() {
        if (hasStringValue()) {
            if (value != null) {
                TextImpl text =
                    (TextImpl) ownerDocument().createTextNode((String) value);
                value = text;
                text.isFirstChild(true);
                text.previousSibling = text;
                text.ownerNode = this;
                text.isOwned(true);
            }
            hasStringValue(false);
        }
    }

    /**
     * NON-DOM
     * set the ownerDocument of this node and its children
     * <p>
     *  NON-DOM设置此节点及其子节点的ownerDocument
     * 
     */
    void setOwnerDocument(CoreDocumentImpl doc) {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        super.setOwnerDocument(doc);
        if (!hasStringValue()) {
            for (ChildNode child = (ChildNode) value;
                 child != null; child = child.nextSibling) {
                child.setOwnerDocument(doc);
            }
        }
    }

    /**
     * NON-DOM: set the type of this attribute to be ID type.
     *
     * <p>
     *  NON-DOM：将此属性的类型设置为ID类型。
     * 
     * 
     * @param id
     */
    public void setIdAttribute(boolean id){
        if (needsSyncData()) {
            synchronizeData();
        }
        isIdAttribute(id);
    }
    /** DOM Level 3: isId*/
    public boolean isId(){
        // REVISIT: should an attribute that is not in the tree return
        // isID true?
        return isIdAttribute();
    }


    //
    // Node methods
    //

    public Node cloneNode(boolean deep) {

        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        AttrImpl clone = (AttrImpl) super.cloneNode(deep);

        // take care of case where there are kids
        if (!clone.hasStringValue()) {

            // Need to break the association w/ original kids
            clone.value = null;

            // Cloning an Attribute always clones its children,
            // since they represent its value, no matter whether this
            // is a deep clone or not
            for (Node child = (Node) value; child != null;
                 child = child.getNextSibling()) {
                 clone.appendChild(child.cloneNode(true));
            }
        }
        clone.isSpecified(true);
        return clone;
    }

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     *  指示这是什么类型的节点的短整数。此值的命名常量在org.w3c.dom.Node接口中定义。
     * 
     */
    public short getNodeType() {
        return Node.ATTRIBUTE_NODE;
    }

    /**
     * Returns the attribute name
     * <p>
     *  返回属性名称
     * 
     */
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return name;
    }

    /**
     * Implicit in the rerouting of getNodeValue to getValue is the
     * need to redefine setNodeValue, for symmetry's sake.  Note that
     * since we're explicitly providing a value, Specified should be set
     * true.... even if that value equals the default.
     * <p>
     *  在getNodeValue到getValue的重新路由中的隐含是需要重新定义setNodeValue,为了对称的缘故。
     * 注意,由于我们显式提供一个值,Specified应该设置为true ....即使该值等于默认值。
     * 
     */
    public void setNodeValue(String value) throws DOMException {
        setValue(value);
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.TypeInfo#getTypeName()
     */
    public String getTypeName() {
        return (String)type;
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.TypeInfo#getTypeNamespace()
     */
    public String getTypeNamespace() {
        if (type != null) {
            return DTD_URI;
        }
        return null;
    }

    /**
     * Method getSchemaTypeInfo.
     * <p>
     *  方法getSchemaTypeInfo。
     * 
     * 
     * @return TypeInfo
     */
    public TypeInfo getSchemaTypeInfo(){
      return this;
    }

    /**
     * In Attribute objects, NodeValue is considered a synonym for
     * Value.
     *
     * <p>
     *  在属性对象中,NodeValue被认为是Value的同义词。
     * 
     * 
     * @see #getValue()
     */
    public String getNodeValue() {
        return getValue();
    }

    //
    // Attr methods
    //

    /**
     * In Attributes, NodeName is considered a synonym for the
     * attribute's Name
     * <p>
     * 在属性中,NodeName被视为属性名称的同义词
     * 
     */
    public String getName() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return name;

    } // getName():String

    /**
     * The DOM doesn't clearly define what setValue(null) means. I've taken it
     * as "remove all children", which from outside should appear
     * similar to setting it to the empty string.
     * <p>
     *  DOM没有清楚地定义什么setValue(null)意味着。我把它当作"删除所有的孩子",从外面应该看起来类似于将其设置为空字符串。
     * 
     */
    public void setValue(String newvalue) {

        CoreDocumentImpl ownerDocument = ownerDocument();

        if (ownerDocument.errorChecking && isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }

        Element ownerElement = getOwnerElement();
        String oldvalue = "";
        if (needsSyncData()) {
            synchronizeData();
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        if (value != null) {
            if (ownerDocument.getMutationEvents()) {
                // Can no longer just discard the kids; they may have
                // event listeners waiting for them to disconnect.
                if (hasStringValue()) {
                    oldvalue = (String) value;
                    // create an actual text node as our child so
                    // that we can use it in the event
                    if (textNode == null) {
                        textNode = (TextImpl)
                            ownerDocument.createTextNode((String) value);
                    }
                    else {
                        textNode.data = (String) value;
                    }
                    value = textNode;
                    textNode.isFirstChild(true);
                    textNode.previousSibling = textNode;
                    textNode.ownerNode = this;
                    textNode.isOwned(true);
                    hasStringValue(false);
                    internalRemoveChild(textNode, true);
                }
                else {
                    oldvalue = getValue();
                    while (value != null) {
                        internalRemoveChild((Node) value, true);
                    }
                }
            }
            else {
                if (hasStringValue()) {
                    oldvalue = (String) value;
                }
                else {
                    // simply discard children if any
                    oldvalue = getValue();
                    // remove ref from first child to last child
                    ChildNode firstChild = (ChildNode) value;
                    firstChild.previousSibling = null;
                    firstChild.isFirstChild(false);
                    firstChild.ownerNode = ownerDocument;
                }
                // then remove ref to current value
                value = null;
                needsSyncChildren(false);
            }
            if (isIdAttribute() && ownerElement != null) {
                ownerDocument.removeIdentifier(oldvalue);
            }
        }

        // Create and add the new one, generating only non-aggregate events
        // (There are no listeners on the new Text, but there may be
        // capture/bubble listeners on the Attr.
        // Note that aggregate events are NOT dispatched here,
        // since we need to combine the remove and insert.
        isSpecified(true);
        if (ownerDocument.getMutationEvents()) {
            // if there are any event handlers create a real node
            internalInsertBefore(ownerDocument.createTextNode(newvalue),
                                 null, true);
            hasStringValue(false);
            // notify document
            ownerDocument.modifiedAttrValue(this, oldvalue);
        } else {
            // directly store the string
            value = newvalue;
            hasStringValue(true);
            changed();
        }
        if (isIdAttribute() && ownerElement != null) {
            ownerDocument.putIdentifier(newvalue, ownerElement);
        }

    } // setValue(String)

    /**
     * The "string value" of an Attribute is its text representation,
     * which in turn is a concatenation of the string values of its children.
     * <p>
     *  属性的"字符串值"是其文本表示,它又是其子节点的字符串值的串联。
     * 
     */
    public String getValue() {

        if (needsSyncData()) {
            synchronizeData();
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        if (value == null) {
            return "";
        }
        if (hasStringValue()) {
            return (String) value;
        }

        ChildNode firstChild = ((ChildNode) value);

        String data = null;
        if (firstChild.getNodeType() == Node.ENTITY_REFERENCE_NODE){
                data = ((EntityReferenceImpl)firstChild).getEntityRefValue();
        }
        else {
                data =  firstChild.getNodeValue();
        }

        ChildNode node = firstChild.nextSibling;

        if (node == null || data == null)  return (data == null)?"":data;

        StringBuffer value = new StringBuffer(data);
        while (node != null) {
            if (node.getNodeType()  == Node.ENTITY_REFERENCE_NODE){
                data = ((EntityReferenceImpl)node).getEntityRefValue();
                if (data == null) return "";
                value.append(data);
            }
            else {
                value.append(node.getNodeValue());
            }
            node = node.nextSibling;
        }
        return value.toString();

    } // getValue():String


    /**
     * The "specified" flag is true if and only if this attribute's
     * value was explicitly specified in the original document. Note that
     * the implementation, not the user, is in charge of this
     * property. If the user asserts an Attribute value (even if it ends
     * up having the same value as the default), it is considered a
     * specified attribute. If you really want to revert to the default,
     * delete the attribute from the Element, and the Implementation will
     * re-assert the default (if any) in its place, with the appropriate
     * specified=false setting.
     * <p>
     *  当且仅当在原始文档中明确指定了该属性的值时,"指定"标志为真。注意,实现,而不是用户,负责此属性。如果用户声明了属性值(即使它最终具有与默认值相同的值),它被认为是指定的属性。
     * 如果你真的想恢复默认,从元素删除属性,实现将重新断言默认(如果有)在它的位置,与适当的指定= false设置。
     * 
     */
    public boolean getSpecified() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return isSpecified();

    } // getSpecified():boolean

    //
    // Attr2 methods
    //

    /**
     * Returns the element node that this attribute is associated with,
     * or null if the attribute has not been added to an element.
     *
     * <p>
     *  返回此属性关联的元素节点,如果属性尚未添加到元素,则返回null。
     * 
     * 
     * @see #getOwnerElement
     *
     * @deprecated Previous working draft of DOM Level 2. New method
     *             is <tt>getOwnerElement()</tt>.
     */
    public Element getElement() {
        // if we have an owner, ownerNode is our ownerElement, otherwise it's
        // our ownerDocument and we don't have an ownerElement
        return (Element) (isOwned() ? ownerNode : null);
    }

    /**
     * Returns the element node that this attribute is associated with,
     * or null if the attribute has not been added to an element.
     *
     * <p>
     *  返回此属性关联的元素节点,如果属性尚未添加到元素,则返回null。
     * 
     * 
     * @since WD-DOM-Level-2-19990719
     */
    public Element getOwnerElement() {
        // if we have an owner, ownerNode is our ownerElement, otherwise it's
        // our ownerDocument and we don't have an ownerElement
        return (Element) (isOwned() ? ownerNode : null);
    }

    public void normalize() {

        // No need to normalize if already normalized or
        // if value is kept as a String.
        if (isNormalized() || hasStringValue())
            return;

        Node kid, next;
        ChildNode firstChild = (ChildNode)value;
        for (kid = firstChild; kid != null; kid = next) {
            next = kid.getNextSibling();

            // If kid is a text node, we need to check for one of two
            // conditions:
            //   1) There is an adjacent text node
            //   2) There is no adjacent text node, but kid is
            //      an empty text node.
            if ( kid.getNodeType() == Node.TEXT_NODE )
            {
                // If an adjacent text node, merge it with kid
                if ( next!=null && next.getNodeType() == Node.TEXT_NODE )
                {
                    ((Text)kid).appendData(next.getNodeValue());
                    removeChild( next );
                    next = kid; // Don't advance; there might be another.
                }
                else
                {
                    // If kid is empty, remove it
                    if ( kid.getNodeValue() == null || kid.getNodeValue().length() == 0 ) {
                        removeChild( kid );
                    }
                }
            }
        }

        isNormalized(true);
    } // normalize()

    //
    // Public methods
    //

    /** NON-DOM, for use by parser */
    public void setSpecified(boolean arg) {

        if (needsSyncData()) {
            synchronizeData();
        }
        isSpecified(arg);

    } // setSpecified(boolean)

        /**
         * NON-DOM: used by the parser
         * <p>
         *  NON-DOM：由解析器使用
         * 
         * 
         * @param type
         */
    public void setType (Object type){
        this.type = type;
    }

    //
    // Object methods
    //

    /** NON-DOM method for debugging convenience */
    public String toString() {
        return getName() + "=" + "\"" + getValue() + "\"";
    }

    /**
     * Test whether this node has any children. Convenience shorthand
     * for (Node.getFirstChild()!=null)
     * <p>
     *  测试此节点是否有任何子节点。方便简写(Node.getFirstChild()！= null)
     * 
     */
    public boolean hasChildNodes() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return value != null;
    }

    /**
     * Obtain a NodeList enumerating all children of this node. If there
     * are none, an (initially) empty NodeList is returned.
     * <p>
     * NodeLists are "live"; as children are added/removed the NodeList
     * will immediately reflect those changes. Also, the NodeList refers
     * to the actual nodes, so changes to those nodes made via the DOM tree
     * will be reflected in the NodeList and vice versa.
     * <p>
     * In this implementation, Nodes implement the NodeList interface and
     * provide their own getChildNodes() support. Other DOMs may solve this
     * differently.
     * <p>
     *  获取枚举此节点的所有子节点的NodeList。如果没有,则返回(最初)空的NodeList。
     * <p>
     * NodeList是"live";当添加/删除子节点时,NodeList将立即反映这些更改。此外,NodeList引用实际节点,因此通过DOM树进行的那些节点的改变将反映在NodeList中,反之亦然。
     * <p>
     *  在这个实现中,节点实现NodeList接口并提供他们自己的getChildNodes()支持。其他DOM可以不同地解决这个问题。
     * 
     */
    public NodeList getChildNodes() {
        // JKESS: KNOWN ISSUE HERE

        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return this;

    } // getChildNodes():NodeList

    /** The first child of this Node, or null if none. */
    public Node getFirstChild() {

        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        makeChildNode();
        return (Node) value;

    }   // getFirstChild():Node

    /** The last child of this Node, or null if none. */
    public Node getLastChild() {

        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return lastChild();

    } // getLastChild():Node

    final ChildNode lastChild() {
        // last child is stored as the previous sibling of first child
        makeChildNode();
        return value != null ? ((ChildNode) value).previousSibling : null;
    }

    final void lastChild(ChildNode node) {
        // store lastChild as previous sibling of first child
        if (value != null) {
            ((ChildNode) value).previousSibling = node;
        }
    }

    /**
     * Move one or more node(s) to our list of children. Note that this
     * implicitly removes them from their previous parent.
     *
     * <p>
     *  将一个或多个节点移动到我们的子节点列表。请注意,这会隐式地从他们以前的父级删除它们。
     * 
     * 
     * @param newChild The Node to be moved to our subtree. As a
     * convenience feature, inserting a DocumentNode will instead insert
     * all its children.
     *
     * @param refChild Current child which newChild should be placed
     * immediately before. If refChild is null, the insertion occurs
     * after all existing Nodes, like appendChild().
     *
     * @return newChild, in its new state (relocated, or emptied in the case of
     * DocumentNode.)
     *
     * @throws DOMException(HIERARCHY_REQUEST_ERR) if newChild is of a
     * type that shouldn't be a child of this node, or if newChild is an
     * ancestor of this node.
     *
     * @throws DOMException(WRONG_DOCUMENT_ERR) if newChild has a
     * different owner document than we do.
     *
     * @throws DOMException(NOT_FOUND_ERR) if refChild is not a child of
     * this node.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if this node is
     * read-only.
     */
    public Node insertBefore(Node newChild, Node refChild)
        throws DOMException {
        // Tail-call; optimizer should be able to do good things with.
        return internalInsertBefore(newChild, refChild, false);
    } // insertBefore(Node,Node):Node

    /** NON-DOM INTERNAL: Within DOM actions,we sometimes need to be able
     * to control which mutation events are spawned. This version of the
     * insertBefore operation allows us to do so. It is not intended
     * for use by application programs.
     * <p>
     *  以控制产生哪些突变事件。这个版本的insertBefore操作允许我们这样做。它不适用于应用程序。
     * 
     */
    Node internalInsertBefore(Node newChild, Node refChild, boolean replace)
        throws DOMException {

        CoreDocumentImpl ownerDocument = ownerDocument();
        boolean errorChecking = ownerDocument.errorChecking;

        if (newChild.getNodeType() == Node.DOCUMENT_FRAGMENT_NODE) {
            // SLOW BUT SAFE: We could insert the whole subtree without
            // juggling so many next/previous pointers. (Wipe out the
            // parent's child-list, patch the parent pointers, set the
            // ends of the list.) But we know some subclasses have special-
            // case behavior they add to insertBefore(), so we don't risk it.
            // This approch also takes fewer bytecodes.

            // NOTE: If one of the children is not a legal child of this
            // node, throw HIERARCHY_REQUEST_ERR before _any_ of the children
            // have been transferred. (Alternative behaviors would be to
            // reparent up to the first failure point or reparent all those
            // which are acceptable to the target node, neither of which is
            // as robust. PR-DOM-0818 isn't entirely clear on which it
            // recommends?????

            // No need to check kids for right-document; if they weren't,
            // they wouldn't be kids of that DocFrag.
            if (errorChecking) {
                for (Node kid = newChild.getFirstChild(); // Prescan
                     kid != null; kid = kid.getNextSibling()) {

                    if (!ownerDocument.isKidOK(this, kid)) {
                        String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null);
                        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, msg);
                    }
                }
            }

            while (newChild.hasChildNodes()) {
                insertBefore(newChild.getFirstChild(), refChild);
            }
            return newChild;
        }

        if (newChild == refChild) {
            // stupid case that must be handled as a no-op triggering events...
            refChild = refChild.getNextSibling();
            removeChild(newChild);
            insertBefore(newChild, refChild);
            return newChild;
        }

        if (needsSyncChildren()) {
            synchronizeChildren();
        }

        if (errorChecking) {
            if (isReadOnly()) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
            }
            if (newChild.getOwnerDocument() != ownerDocument) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null);
                throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, msg);
            }
            if (!ownerDocument.isKidOK(this, newChild)) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null);
                throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, msg);
            }
            // refChild must be a child of this node (or null)
            if (refChild != null && refChild.getParentNode() != this) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null);
                throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
            }

            // Prevent cycles in the tree
            // newChild cannot be ancestor of this Node,
            // and actually cannot be this
            boolean treeSafe = true;
            for (NodeImpl a = this; treeSafe && a != null; a = a.parentNode())
            {
                treeSafe = newChild != a;
            }
            if (!treeSafe) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null);
                throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, msg);
            }
        }

        makeChildNode(); // make sure we have a node and not a string

        // notify document
        ownerDocument.insertingNode(this, replace);

        // Convert to internal type, to avoid repeated casting
        ChildNode newInternal = (ChildNode)newChild;

        Node oldparent = newInternal.parentNode();
        if (oldparent != null) {
            oldparent.removeChild(newInternal);
        }

        // Convert to internal type, to avoid repeated casting
        ChildNode refInternal = (ChildNode) refChild;

        // Attach up
        newInternal.ownerNode = this;
        newInternal.isOwned(true);

        // Attach before and after
        // Note: firstChild.previousSibling == lastChild!!
        ChildNode firstChild = (ChildNode) value;
        if (firstChild == null) {
            // this our first and only child
            value = newInternal; // firstchild = newInternal;
            newInternal.isFirstChild(true);
            newInternal.previousSibling = newInternal;
        }
        else {
            if (refInternal == null) {
                // this is an append
                ChildNode lastChild = firstChild.previousSibling;
                lastChild.nextSibling = newInternal;
                newInternal.previousSibling = lastChild;
                firstChild.previousSibling = newInternal;
            }
            else {
                // this is an insert
                if (refChild == firstChild) {
                    // at the head of the list
                    firstChild.isFirstChild(false);
                    newInternal.nextSibling = firstChild;
                    newInternal.previousSibling = firstChild.previousSibling;
                    firstChild.previousSibling = newInternal;
                    value = newInternal; // firstChild = newInternal;
                    newInternal.isFirstChild(true);
                }
                else {
                    // somewhere in the middle
                    ChildNode prev = refInternal.previousSibling;
                    newInternal.nextSibling = refInternal;
                    prev.nextSibling = newInternal;
                    refInternal.previousSibling = newInternal;
                    newInternal.previousSibling = prev;
                }
            }
        }

        changed();

        // notify document
        ownerDocument.insertedNode(this, newInternal, replace);

        checkNormalizationAfterInsert(newInternal);

        return newChild;

    } // internalInsertBefore(Node,Node,int):Node

    /**
     * Remove a child from this Node. The removed child's subtree
     * remains intact so it may be re-inserted elsewhere.
     *
     * <p>
     *  从此节点删除子项。删除的子树的子树保持原样,因此可以重新插入其他位置。
     * 
     * 
     * @return oldChild, in its new state (removed).
     *
     * @throws DOMException(NOT_FOUND_ERR) if oldChild is not a child of
     * this node.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if this node is
     * read-only.
     */
    public Node removeChild(Node oldChild)
        throws DOMException {
        // Tail-call, should be optimizable
        if (hasStringValue()) {
            // we don't have any child per say so it can't be one of them!
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null);
            throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
        }
        return internalRemoveChild(oldChild, false);
    } // removeChild(Node) :Node

    /** NON-DOM INTERNAL: Within DOM actions,we sometimes need to be able
     * to control which mutation events are spawned. This version of the
     * removeChild operation allows us to do so. It is not intended
     * for use by application programs.
     * <p>
     *  以控制产生哪些突变事件。这个版本的removeChild操作允许我们这样做。它不适用于应用程序。
     * 
     */
    Node internalRemoveChild(Node oldChild, boolean replace)
        throws DOMException {

        CoreDocumentImpl ownerDocument = ownerDocument();
        if (ownerDocument.errorChecking) {
            if (isReadOnly()) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
            }
            if (oldChild != null && oldChild.getParentNode() != this) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null);
                throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
            }
        }

        ChildNode oldInternal = (ChildNode) oldChild;

        // notify document
        ownerDocument.removingNode(this, oldInternal, replace);

        // Patch linked list around oldChild
        // Note: lastChild == firstChild.previousSibling
        if (oldInternal == value) { // oldInternal == firstChild
            // removing first child
            oldInternal.isFirstChild(false);
            // next line is: firstChild = oldInternal.nextSibling
            value = oldInternal.nextSibling;
            ChildNode firstChild = (ChildNode) value;
            if (firstChild != null) {
                firstChild.isFirstChild(true);
                firstChild.previousSibling = oldInternal.previousSibling;
            }
        } else {
            ChildNode prev = oldInternal.previousSibling;
            ChildNode next = oldInternal.nextSibling;
            prev.nextSibling = next;
            if (next == null) {
                // removing last child
                ChildNode firstChild = (ChildNode) value;
                firstChild.previousSibling = prev;
            } else {
                // removing some other child in the middle
                next.previousSibling = prev;
            }
        }

        // Save previous sibling for normalization checking.
        ChildNode oldPreviousSibling = oldInternal.previousSibling();

        // Remove oldInternal's references to tree
        oldInternal.ownerNode       = ownerDocument;
        oldInternal.isOwned(false);
        oldInternal.nextSibling     = null;
        oldInternal.previousSibling = null;

        changed();

        // notify document
        ownerDocument.removedNode(this, replace);

        checkNormalizationAfterRemove(oldPreviousSibling);

        return oldInternal;

    } // internalRemoveChild(Node,int):Node

    /**
     * Make newChild occupy the location that oldChild used to
     * have. Note that newChild will first be removed from its previous
     * parent, if any. Equivalent to inserting newChild before oldChild,
     * then removing oldChild.
     *
     * <p>
     *  使newChild占据oldChild曾经存在的位置。请注意,newChild将首先从其上一个父级(如果有)中删除。相当于在oldChild之前插入newChild,然后删除oldChild。
     * 
     * 
     * @return oldChild, in its new state (removed).
     *
     * @throws DOMException(HIERARCHY_REQUEST_ERR) if newChild is of a
     * type that shouldn't be a child of this node, or if newChild is
     * one of our ancestors.
     *
     * @throws DOMException(WRONG_DOCUMENT_ERR) if newChild has a
     * different owner document than we do.
     *
     * @throws DOMException(NOT_FOUND_ERR) if oldChild is not a child of
     * this node.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if this node is
     * read-only.
     */
    public Node replaceChild(Node newChild, Node oldChild)
        throws DOMException {

        makeChildNode();

        // If Mutation Events are being generated, this operation might
        // throw aggregate events twice when modifying an Attr -- once
        // on insertion and once on removal. DOM Level 2 does not specify
        // this as either desirable or undesirable, but hints that
        // aggregations should be issued only once per user request.

        // notify document
        CoreDocumentImpl ownerDocument = ownerDocument();
        ownerDocument.replacingNode(this);

        internalInsertBefore(newChild, oldChild, true);
        if (newChild != oldChild) {
            internalRemoveChild(oldChild, true);
        }

        // notify document
        ownerDocument.replacedNode(this);

        return oldChild;
    }

    //
    // NodeList methods
    //

    /**
     * NodeList method: Count the immediate children of this node
     * <p>
     *  NodeList方法：计算此节点的直接子节点
     * 
     * 
     * @return int
     */
    public int getLength() {

        if (hasStringValue()) {
            return 1;
        }
        ChildNode node = (ChildNode) value;
        int length = 0;
        for (; node != null; node = node.nextSibling) {
            length++;
        }
        return length;

    } // getLength():int

    /**
     * NodeList method: Return the Nth immediate child of this node, or
     * null if the index is out of bounds.
     * <p>
     *  NodeList方法：返回此节点的第N个直接子节点,如果索引超出边界则返回null。
     * 
     * 
     * @return org.w3c.dom.Node
     * @param Index int
     */
    public Node item(int index) {

        if (hasStringValue()) {
            if (index != 0 || value == null) {
                return null;
            }
            else {
                makeChildNode();
                return (Node) value;
            }
        }
        if (index < 0) {
            return null;
        }
        ChildNode node = (ChildNode) value;
        for (int i = 0; i < index && node != null; i++) {
            node = node.nextSibling;
        }
        return node;

    } // item(int):Node

    //
    // DOM3
    //

    /**
     * DOM Level 3 WD- Experimental.
     * Override inherited behavior from ParentNode to support deep equal.
     * isEqualNode is always deep on Attr nodes.
     * <p>
     *  DOM级别3。覆盖从父节点继承的行为以支持深度相等。 isEqualNode总是在Attr节点上深。
     * 
     */
    public boolean isEqualNode(Node arg) {
        return super.isEqualNode(arg);
    }

    /**
     * Introduced in DOM Level 3. <p>
     * Checks if a type is derived from another by restriction. See:
     * http://www.w3.org/TR/DOM-Level-3-Core/core.html#TypeInfo-isDerivedFrom
     *
     * <p>
     * 在DOM级别3中引入。<p>检查类型是否通过限制从另一个派生。
     * 参见：http://www.w3.org/TR/DOM-Level-3-Core/core.html#TypeInfo-isDerivedFrom。
     * 
     * 
     * @param ancestorNS
     *        The namspace of the ancestor type declaration
     * @param ancestorName
     *        The name of the ancestor type declaration
     * @param type
     *        The reference type definition
     *
     * @return boolean True if the type is derived by restriciton for the
     *         reference type
     */
    public boolean isDerivedFrom(String typeNamespaceArg,
                                 String typeNameArg,
                                 int derivationMethod) {

        return false;
    }


    //
    // Public methods
    //

    /**
     * Override default behavior so that if deep is true, children are also
     * toggled.
     * <p>
     *  覆盖默认行为,以便如果deep为true,孩子也被切换。
     * 
     * 
     * @see Node
     * <P>
     * Note: this will not change the state of an EntityReference or its
     * children, which are always read-only.
     */
    public void setReadOnly(boolean readOnly, boolean deep) {

        super.setReadOnly(readOnly, deep);

        if (deep) {

            if (needsSyncChildren()) {
                synchronizeChildren();
            }

            if (hasStringValue()) {
                return;
            }
            // Recursively set kids
            for (ChildNode mykid = (ChildNode) value;
                 mykid != null;
                 mykid = mykid.nextSibling) {
                if (mykid.getNodeType() != Node.ENTITY_REFERENCE_NODE) {
                    mykid.setReadOnly(readOnly,true);
                }
            }
        }
    } // setReadOnly(boolean,boolean)

    //
    // Protected methods
    //

    /**
     * Override this method in subclass to hook in efficient
     * internal data structure.
     * <p>
     *  在子类中覆盖此方法以挂钩有效的内部数据结构。
     * 
     */
    protected void synchronizeChildren() {
        // By default just change the flag to avoid calling this method again
        needsSyncChildren(false);
    }

    /**
     * Checks the normalized state of this node after inserting a child.
     * If the inserted child causes this node to be unnormalized, then this
     * node is flagged accordingly.
     * The conditions for changing the normalized state are:
     * <ul>
     * <li>The inserted child is a text node and one of its adjacent siblings
     * is also a text node.
     * <li>The inserted child is is itself unnormalized.
     * </ul>
     *
     * <p>
     *  检查插入子节点后此节点的规范化状态。如果插入的子节点导致此节点非规范化,则相应地标记该节点。用于改变归一化状态的条件是：
     * <ul>
     *  <li>插入的子节点是文本节点,其相邻兄弟节点之一也是文本节点。 <li>插入的子项本身是未规范化的。
     * </ul>
     * 
     * 
     * @param insertedChild the child node that was inserted into this node
     *
     * @throws NullPointerException if the inserted child is <code>null</code>
     */
    void checkNormalizationAfterInsert(ChildNode insertedChild) {
        // See if insertion caused this node to be unnormalized.
        if (insertedChild.getNodeType() == Node.TEXT_NODE) {
            ChildNode prev = insertedChild.previousSibling();
            ChildNode next = insertedChild.nextSibling;
            // If an adjacent sibling of the new child is a text node,
            // flag this node as unnormalized.
            if ((prev != null && prev.getNodeType() == Node.TEXT_NODE) ||
                (next != null && next.getNodeType() == Node.TEXT_NODE)) {
                isNormalized(false);
            }
        }
        else {
            // If the new child is not normalized,
            // then this node is inherently not normalized.
            if (!insertedChild.isNormalized()) {
                isNormalized(false);
            }
        }
    } // checkNormalizationAfterInsert(ChildNode)

    /**
     * Checks the normalized of this node after removing a child.
     * If the removed child causes this node to be unnormalized, then this
     * node is flagged accordingly.
     * The conditions for changing the normalized state are:
     * <ul>
     * <li>The removed child had two adjacent siblings that were text nodes.
     * </ul>
     *
     * <p>
     *  检查删除子节点后此节点的规范化。如果删除的子节点导致此节点非规范化,则相应地标记该节点。用于改变归一化状态的条件是：
     * <ul>
     *  <li>已移除的子项有两个相邻的兄弟节点,它们是文本节点。
     * 
     * @param previousSibling the previous sibling of the removed child, or
     * <code>null</code>
     */
    void checkNormalizationAfterRemove(ChildNode previousSibling) {
        // See if removal caused this node to be unnormalized.
        // If the adjacent siblings of the removed child were both text nodes,
        // flag this node as unnormalized.
        if (previousSibling != null &&
            previousSibling.getNodeType() == Node.TEXT_NODE) {

            ChildNode next = previousSibling.nextSibling;
            if (next != null && next.getNodeType() == Node.TEXT_NODE) {
                isNormalized(false);
            }
        }
    } // checkNormalizationAfterRemove(ChildNode)

    //
    // Serialization methods
    //

    /** Serialize object. */
    private void writeObject(ObjectOutputStream out) throws IOException {

        // synchronize chilren
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        // write object
        out.defaultWriteObject();

    } // writeObject(ObjectOutputStream)

    /** Deserialize object. */
    private void readObject(ObjectInputStream ois)
        throws ClassNotFoundException, IOException {

        // perform default deseralization
        ois.defaultReadObject();

        // hardset synchildren - so we don't try to sync -
        // it does not make any sense to try to synchildren when we just
        // deserialize object.
        needsSyncChildren(false);

    } // readObject(ObjectInputStream)


} // class AttrImpl

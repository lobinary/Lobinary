/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import com.sun.org.apache.xerces.internal.util.URI;
import org.w3c.dom.DocumentType;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * EntityReference models the XML &entityname; syntax, when used for
 * entities defined by the DOM. Entities hardcoded into XML, such as
 * character entities, should instead have been translated into text
 * by the code which generated the DOM tree.
 * <P>
 * An XML processor has the alternative of fully expanding Entities
 * into the normal document tree. If it does so, no EntityReference nodes
 * will appear.
 * <P>
 * Similarly, non-validating XML processors are not required to read
 * or process entity declarations made in the external subset or
 * declared in external parameter entities. Hence, some applications
 * may not make the replacement value available for Parsed Entities
 * of these types.
 * <P>
 * EntityReference behaves as a read-only node, and the children of
 * the EntityReference (which reflect those of the Entity, and should
 * also be read-only) give its replacement value, if any. They are
 * supposed to automagically stay in synch if the DocumentType is
 * updated with new values for the Entity.
 * <P>
 * The defined behavior makes efficient storage difficult for the DOM
 * implementor. We can't just look aside to the Entity's definition
 * in the DocumentType since those nodes have the wrong parent (unless
 * we can come up with a clever "imaginary parent" mechanism). We
 * must at least appear to clone those children... which raises the
 * issue of keeping the reference synchronized with its parent.
 * This leads me back to the "cached image of centrally defined data"
 * solution, much as I dislike it.
 * <P>
 * For now I have decided, since REC-DOM-Level-1-19980818 doesn't
 * cover this in much detail, that synchronization doesn't have to be
 * considered while the user is deep in the tree. That is, if you're
 * looking within one of the EntityReferennce's children and the Entity
 * changes, you won't be informed; instead, you will continue to access
 * the same object -- which may or may not still be part of the tree.
 * This is the same behavior that obtains elsewhere in the DOM if the
 * subtree you're looking at is deleted from its parent, so it's
 * acceptable here. (If it really bothers folks, we could set things
 * up so deleted subtrees are walked and marked invalid, but that's
 * not part of the DOM's defined behavior.)
 * <P>
 * As a result, only the EntityReference itself has to be aware of
 * changes in the Entity. And it can take advantage of the same
 * structure-change-monitoring code I implemented to support
 * DeepNodeList.
 *
 * @xerces.internal
 *
 * <p>
 *  EntityReference建模XML&entityname;语法,当用于由DOM定义的实体时。硬编码到XML中的实体(例如字符实体)应该由生成DOM树的代码翻译为文本。
 * <P>
 *  XML处理器具有将实体完全扩展到正常文档树中的替代。如果这样做,则不会出现EntityReference节点。
 * <P>
 *  类似地,不需要非验证XML处理器来读取或处理在外部子集中做出的或在外部参数实体中声明的实体声明。因此,一些应用可能不会使替换值可用于这些类型的解析实体。
 * <P>
 * EntityReference行为作为只读节点,EntityReference的子节点(它们反映实体的子节点,并且也应该是只读的)给出它的替换值(如果有的话)。
 * 如果DocumentType使用实体的新值更新,它们应该自动保持同步。
 * <P>
 *  定义的行为使得DOM实现者难以进行高效存储。我们不能只看看Entity在DocumentType中的定义,因为那些节点有错误的父类(除非我们能够想出一个聪明的"想象的父"机制)。
 * 我们必须至少似乎克隆这些孩子...这引起了保持引用与其父母同步的问题。这导致我回到"集中定义数据的缓存映像"解决方案,就像我不喜欢它。
 * <P>
 *  现在我决定,因为REC-DOM-Level-1-19980818没有详细地涵盖这一点,当用户在树中深处时,不必考虑同步。
 * 也就是说,如果你在一个EntityReferennce的孩子和Entity的改变,你不会被告知;相反,你将继续访问相同的对象 - 这可能或不会仍然是树的一部分。
 * 如果您正在查看的子树从其父级中删除,这是在DOM中其他位置获得的相同行为,因此这里可以接受。
 *  (如果它真的打扰了人,我们可以设置的东西,所以删除的子树被走路和标记为无效,但这不是DOM的定义行为的一部分。)。
 * <P>
 * 因此,只有EntityReference本身必须知道实体中的更改。它可以利用我实现的相同的结构变化监视代码来支持DeepNodeList。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Arnaud  Le Hors, IBM
 * @author Joe Kesselman, IBM
 * @author Andy Clark, IBM
 * @author Ralf Pfeiffer, IBM
 * @since  PR-DOM-Level-1-19980818.
 */
public class EntityReferenceImpl
extends ParentNode
implements EntityReference {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -7381452955687102062L;

    //
    // Data
    //

    /** Name of Entity referenced */
    protected String name;
    /** Base URI*/
    protected String baseURI;


    /** Entity changes. */
    //protected int entityChanges = -1;

    /** Enable synchronize. */
    //protected boolean fEnableSynchronize = false;

    //
    // Constructors
    //

    /** Factory constructor. */
    public EntityReferenceImpl(CoreDocumentImpl ownerDoc, String name) {
        super(ownerDoc);
        this.name = name;
        isReadOnly(true);
        needsSyncChildren(true);
    }

    //
    // Node methods
    //

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     *  指示这是什么类型的节点的短整数。此值的命名常量在org.w3c.dom.Node接口中定义。
     * 
     */
    public short getNodeType() {
        return Node.ENTITY_REFERENCE_NODE;
    }

    /**
     * Returns the name of the entity referenced
     * <p>
     *  返回引用的实体的名称
     * 
     */
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return name;
    }

    /** Clone node. */
    public Node cloneNode(boolean deep) {
        EntityReferenceImpl er = (EntityReferenceImpl)super.cloneNode(deep);
        er.setReadOnly(true, deep);
        return er;
    }

    /**
     * Returns the absolute base URI of this node or null if the implementation
     * wasn't able to obtain an absolute URI. Note: If the URI is malformed, a
     * null is returned.
     *
     * <p>
     *  返回此节点的绝对基本URI,如果实现无法获取绝对URI,则返回null。注意：如果URI格式不正确,则返回null。
     * 
     * 
     * @return The absolute base URI of this node or null.
     * @since DOM Level 3
     */
    public String getBaseURI() {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (baseURI == null) {
            DocumentType doctype;
            NamedNodeMap entities;
            EntityImpl entDef;
            if (null != (doctype = getOwnerDocument().getDoctype()) &&
                null != (entities = doctype.getEntities())) {

                entDef = (EntityImpl)entities.getNamedItem(getNodeName());
                if (entDef !=null) {
                    return entDef.getBaseURI();
                }
            }
        } else if (baseURI != null && baseURI.length() != 0 ) {// attribute value is always empty string
            try {
                return new URI(baseURI).toString();
            }
            catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e){
                // REVISIT: what should happen in this case?
                return null;
            }
        }
        return baseURI;
    }


    /** NON-DOM: set base uri*/
    public void setBaseURI(String uri){
        if (needsSyncData()) {
            synchronizeData();
        }
        baseURI = uri;
    }

        /**
         * NON-DOM: compute string representation of the entity reference.
     * This method is used to retrieve a string value for an attribute node that has child nodes.
     * <p>
     *  NON-DOM：计算实体引用的字符串表示形式。此方法用于检索具有子节点的属性节点的字符串值。
     * 
     * 
         * @return String representing a value of this entity ref. or
     *          null if any node other than EntityReference, Text is encountered
     *          during computation
         */
    protected String getEntityRefValue (){
        if (needsSyncChildren()){
            synchronizeChildren();
        }

        String value = "";
        if (firstChild != null){
          if (firstChild.getNodeType() == Node.ENTITY_REFERENCE_NODE){
              value = ((EntityReferenceImpl)firstChild).getEntityRefValue();
          }
          else if (firstChild.getNodeType() == Node.TEXT_NODE){
            value = firstChild.getNodeValue();
          }
          else {
             // invalid to have other types of nodes in attr value
            return null;
          }

          if (firstChild.nextSibling == null){
            return value;
          }
          else {
            StringBuffer buff = new StringBuffer(value);
            ChildNode next = firstChild.nextSibling;
            while (next != null){

                if (next.getNodeType() == Node.ENTITY_REFERENCE_NODE){
                   value = ((EntityReferenceImpl)next).getEntityRefValue();
                }
                else if (next.getNodeType() == Node.TEXT_NODE){
                  value = next.getNodeValue();
                }
                else {
                    // invalid to have other types of nodes in attr value
                    return null;
                }
                buff.append(value);
                next = next.nextSibling;

            }
            return buff.toString();
          }
        }
        return "";
    }

    /**
     * EntityReference's children are a reflection of those defined in the
     * named Entity. This method creates them if they haven't been created yet.
     * This doesn't support editing the Entity though, since this only called
     * once for all.
     * <p>
     *  EntityReference的孩子是在命名实体中定义的那些的反射。如果尚未创建它们,则此方法将创建它们。这不支持编辑实体,因为这只为所有调用一次。
     * 
     */
    protected void synchronizeChildren() {
        // no need to synchronize again
        needsSyncChildren(false);

        DocumentType doctype;
        NamedNodeMap entities;
        EntityImpl entDef;
        if (null != (doctype = getOwnerDocument().getDoctype()) &&
            null != (entities = doctype.getEntities())) {

            entDef = (EntityImpl)entities.getNamedItem(getNodeName());

            // No Entity by this name, stop here.
            if (entDef == null)
                return;

            // If entity's definition exists, clone its kids
            isReadOnly(false);
            for (Node defkid = entDef.getFirstChild();
                defkid != null;
                defkid = defkid.getNextSibling()) {
                Node newkid = defkid.cloneNode(true);
                insertBefore(newkid, null);
            }
            setReadOnly(true, true);
        }
    }


    /**
     * NON-DOM: sets the node and its children value.
     * <P>
     * Note: make sure that entity reference and its kids could be set readonly.
     * <p>
     *  NON-DOM：设置节点及其子节点的值。
     * <P>
     *  注意：确保实体引用及其孩子可以设置为只读。
     * 
     */
    public void setReadOnly(boolean readOnly, boolean deep) {

        if (needsSyncData()) {
            synchronizeData();
        }
        if (deep) {

            if (needsSyncChildren()) {
                synchronizeChildren();
            }
            // Recursively set kids
            for (ChildNode mykid = firstChild;
                 mykid != null;
                 mykid = mykid.nextSibling) {

                mykid.setReadOnly(readOnly,true);

            }
        }
        isReadOnly(readOnly);
    } // setReadOnly(boolean,boolean)


    /**
     * Enable the synchronize method which may do cloning. This method is enabled
     * when the parser is done with an EntityReference.
    /***
    // revisit: enable editing of Entity
    public void enableSynchronize(boolean enableSynchronize) {
        fEnableSynchronize= enableSynchronize;
    }
    /* <p>
    /*  启用可执行克隆的同步方法。当解析器使用EntityReference完成时,将启用此方法。
    /*  / *** // revisit：启用实体的编辑public void enableSynchronize(boolean enableSynchronize){fEnableSynchronize = enableSynchronize; }
    /* }。
    /*  启用可执行克隆的同步方法。当解析器使用EntityReference完成时,将启用此方法。
    /* 
    /* 
    /***/

    /**
     * EntityReference's children are a reflection of those defined in the
     * named Entity. This method updates them if the Entity is changed.
     * <P>
     * It is unclear what the least-cost resynch mechanism is.
     * If we expect the kids to be shallow, and/or expect changes
     * to the Entity contents to be rare, wiping them all out
     * and recloning is simplest.
     * <P>
     * If we expect them to be deep,
     * it might be better to first decide which kids (if any)
     * persist, and keep the ones (if any) that are unchanged
     * rather than doing all the work of cloning them again.
     * But that latter gets into having to convolve the two child lists,
     * insert new information in the right order (and possibly reorder
     * the existing kids), and a few other complexities that I really
     * don't want to deal with in this implementation.
     * <P>
     * Note that if we decide that we need to update the EntityReference's
     * contents, we have to turn off the readOnly flag temporarily to do so.
     * When we get around to adding multitasking support, this whole method
     * should probably be an atomic operation.
     *
     * <p>
     *  EntityReference的孩子是在命名实体中定义的那些的反射。如果实体更改,此方法将更新它们。
     * <P>
     * 目前尚不清楚什么是最低成本的重新同步机制。如果我们期望孩子们是浅的,和/或期望实体内容的变化是罕见的,将它们全部擦除并重新排序是最简单的。
     * <P>
     *  如果我们期望它们是深的,最好首先决定哪些孩子(如果有的话)坚持,并且保持那些不变的(如果有的话),而不是做所有的克隆它们的工作。
     * 但是后者需要卷积两个子列表,以正确的顺序插入新信息(并且可能重新排序现有的孩子),还有一些其他复杂性,我真的不想在这个实现中处理。
     * <P>
     *  注意,如果我们决定需要更新EntityReference的内容,我们必须临时关闭readOnly标志。当我们开始添加多任务支持时,这个整个方法应该是一个原子操作。
     * 
     * 
     * @see DocumentTypeImpl
     * @see EntityImpl
     */
    // The Xerces parser invokes callbacks for startEnityReference
    // the parsed value of the entity EACH TIME, so it is actually
    // easier to create the nodes through the callbacks rather than
    // clone the Entity.
    /***
    // revisit: enable editing of Entity
    private void synchronize() {
        if (!fEnableSynchronize) {
            return;
        }
        DocumentType doctype;
        NamedNodeMap entities;
        EntityImpl entDef;
        if (null != (doctype = getOwnerDocument().getDoctype()) &&
            null != (entities = doctype.getEntities())) {

            entDef = (EntityImpl)entities.getNamedItem(getNodeName());

            // No Entity by this name. If we had a change count, reset it.
            if(null==entDef)
                entityChanges=-1;

            // If no kids availalble, wipe any pre-existing children.
            // (See discussion above.)
            // Note that we have to use the superclass to avoid recursion
            // through Synchronize.
            readOnly=false;
            if(null==entDef || !entDef.hasChildNodes())
                for(Node kid=super.getFirstChild();
                    kid!=null;
                    kid=super.getFirstChild())
                    removeChild(kid);

            // If entity's definition changed, clone its kids
            // (See discussion above.)
            if(null!=entDef && entDef.changes!=entityChanges) {
                for(Node defkid=entDef.getFirstChild();
                    defkid!=null;
                    defkid=defkid.getNextSibling()) {

                    NodeImpl newkid=(NodeImpl) defkid.cloneNode(true);
                    newkid.setReadOnly(true,true);
                    insertBefore(newkid,null);
                }
                entityChanges=entDef.changes;
            }
            readOnly=true;
        }
    }
    /* <p>
    /*  // revisit：启用编辑实体private void synchronize(){if(！fEnableSynchronize){return; } DocumentType doctype; 
    /* NamedNodeMap实体; EntityImpl entDef; if(null！=(doctype = getOwnerDocument()。
    /* getDoctype())&& null！=(entities = doctype.getEntities。
    /* 
    /*  entDef =(EntityImpl)entities.getNamedItem(getNodeName());
    /* 
    /*  // No Entity by this name。如果我们有一个更改计数,重置它。 if(null == entDef)entityChanges = -1;
    /* 
    /* //如果没有孩子可用,擦拭任何现有的孩子。 //(见上面的讨论。)//注意,我们必须使用超类,以避免递归//通过Synchronize。
    /* 
     /***/


} // class EntityReferenceImpl

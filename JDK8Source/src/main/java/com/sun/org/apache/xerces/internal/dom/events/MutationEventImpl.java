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

package com.sun.org.apache.xerces.internal.dom.events;

import org.w3c.dom.Node;
import org.w3c.dom.events.MutationEvent;

/**
 * @xerces.internal
 *
 * <p>
 *  @ xerces.internal
 * 
 */

public class MutationEventImpl
extends com.sun.org.apache.xerces.internal.dom.events.EventImpl
implements MutationEvent
{
    Node relatedNode=null;
    String prevValue=null,newValue=null,attrName=null;
    // REVISIT: The DOM Level 2 PR has a bug: the init method should let this
    // attribute be specified. Since it doesn't we have to give write access.
    public short attrChange;

    // NON-DOM CONSTANTS: Storage efficiency, avoid risk of typos.
    public static final String DOM_SUBTREE_MODIFIED = "DOMSubtreeModified";
    public static final String DOM_NODE_INSERTED = "DOMNodeInserted";
    public static final String DOM_NODE_REMOVED = "DOMNodeRemoved";
    public static final String DOM_NODE_REMOVED_FROM_DOCUMENT = "DOMNodeRemovedFromDocument";
    public static final String DOM_NODE_INSERTED_INTO_DOCUMENT = "DOMNodeInsertedIntoDocument";
    public static final String DOM_ATTR_MODIFIED = "DOMAttrModified";
    public static final String DOM_CHARACTER_DATA_MODIFIED = "DOMCharacterDataModified";

    /** @return the name of the Attr which
        changed, for DOMAttrModified events.
        Undefined for others.
    /* <p>
    /*  已更改,用于DOMAttrModified事件。未定义为其他人。
    /* 
        */
    public String getAttrName()
    {
        return attrName;
    }

    /**
     *  <code>attrChange</code> indicates the type of change which triggered
     * the DOMAttrModified event. The values can be <code>MODIFICATION</code>
     * , <code>ADDITION</code>, or <code>REMOVAL</code>.
     * <p>
     *  <code> attrChange </code>表示触发DOMAttrModified事件的更改类型。
     * 值可以是<code> MODIFICATION </code>,<code> ADDITION </code>或<code> REMOVAL </code>。
     * 
     */
    public short getAttrChange()
    {
        return attrChange;
    }

    /** @return the new string value of the Attr for DOMAttrModified events, or
        of the CharacterData node for DOMCharDataModifed events.
        Undefined for others.
    /* <p>
    /*  DOMCharDataModifed事件的CharacterData节点。未定义为其他人。
    /* 
        */
    public String getNewValue()
    {
        return newValue;
    }

    /** @return the previous string value of the Attr for DOMAttrModified events, or
        of the CharacterData node for DOMCharDataModifed events.
        Undefined for others.
    /* <p>
    /*  DOMCharDataModifed事件的CharacterData节点。未定义为其他人。
    /* 
        */
    public String getPrevValue()
    {
        return prevValue;
    }

    /** @return a Node related to this event, other than the target that the
        node was dispatched to. For DOMNodeRemoved, it is the node which
        was removed.
        No other uses are currently defined.
    /* <p>
    /*  节点已分派到。对于DOMNodeRemoved,它是被删除的节点。目前没有定义其他用途。
    /* 
        */
    public Node getRelatedNode()
    {
        return relatedNode;
    }

    /** Initialize a mutation event, or overwrite the event's current
        settings with new values of the parameters.
    /* <p>
    /*  设置具有新的参数值。
        */
    public void initMutationEvent(String typeArg, boolean canBubbleArg,
        boolean cancelableArg, Node relatedNodeArg, String prevValueArg,
        String newValueArg, String attrNameArg, short attrChangeArg)
    {
        relatedNode=relatedNodeArg;
        prevValue=prevValueArg;
        newValue=newValueArg;
        attrName=attrNameArg;
        attrChange=attrChangeArg;
        super.initEvent(typeArg,canBubbleArg,cancelableArg);
    }

}

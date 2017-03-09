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

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;

/**
 * EventImpl is an implementation of the basic "generic" DOM Level 2 Event
 * object. It may be subclassed by more specialized event sets.
 * Note that in our implementation, events are re-dispatchable (dispatch
 * clears the stopPropagation and preventDefault flags before it starts);
 * I believe that is the DOM's intent but I don't see an explicit statement
 * to this effect.
 *
 * @xerces.internal
 *
 * <p>
 *  EventImpl是基本的"通用"DOM Level 2事件对象的实现。它可以由更专门的事件集子类化。
 * 注意在我们的实现中,事件是可重新分派的(dispatch在开始之前清除stopPropagation和preventDefault标志);我相信这是DOM的意图,但我没有看到这种效果的明确声明。
 * 
 *  @ xerces.internal
 * 
 */
public class EventImpl implements Event
{
    public String type=null;
    public EventTarget target;
    public EventTarget currentTarget;
    public short eventPhase;
    public boolean initialized=false, bubbles=true, cancelable=false;
    public boolean stopPropagation=false, preventDefault=false;

    protected long timeStamp = System.currentTimeMillis();

    /** The DOM doesn't deal with constructors, so instead we have an
        initializer call to set most of the read-only fields. The
        others are set, and reset, by the event subsystem during dispatch.
        <p>
        Note that init() -- and the subclass-specific initWhatever() calls --
        may be reinvoked. At least one initialization is required; repeated
        initializations overwrite the event with new values of their
        parameters.
    /* <p>
    /*  初始化调用来设置大多数只读字段。其他在调度期间由事件子系统设置和复位。
    /* <p>
    /*  注意,init()和子类特定的initWhatever()调用可以被重新调用。至少需要一次初始化;重复初始化用其参数的新值覆盖事件。
    /* 
    */
    public void initEvent(String eventTypeArg, boolean canBubbleArg,
                        boolean cancelableArg)
    {
            type=eventTypeArg;
            bubbles=canBubbleArg;
            cancelable=cancelableArg;

            initialized=true;
    }

    /** @return true iff this Event is of a class and type which supports
        bubbling. In the generic case, this is True.
    /* <p>
    /*  冒泡。在通用情况下,这是True。
    /* 
        */
    public boolean getBubbles()
    {
        return bubbles;
    }

    /** @return true iff this Event is of a class and type which (a) has a
        Default Behavior in this DOM, and (b)allows cancellation (blocking)
        of that behavior. In the generic case, this is False.
    /* <p>
    /* 此DOM中的默认行为,以及(b)允许取消(阻止)该行为。在通用情况下,这是False。
    /* 
        */
    public boolean getCancelable()
    {
        return cancelable;
    }

    /** @return the Node (EventTarget) whose EventListeners are currently
        being processed. During capture and bubble phases, this may not be
    /* <p>
    /*  正在处理。在捕获和气泡阶段,这可能不是
    /* 
    /* 
        the target node. */
    public EventTarget getCurrentTarget()
    {
        return currentTarget;
    }

    /** @return the current processing phase for this event --
        CAPTURING_PHASE, AT_TARGET, BUBBLING_PHASE. (There may be
    /* <p>
    /*  CAPTURING_PHASE,AT_TARGET,BUBBLING_PHASE。 (可能有
    /* 
    /* 
        an internal DEFAULT_PHASE as well, but the users won't see it.) */
    public short getEventPhase()
    {
        return eventPhase;
    }

    /** @return the EventTarget (Node) to which the event was originally
        dispatched.
    /* <p>
    /*  调度。
    /* 
        */
    public EventTarget getTarget()
    {
        return target;
    }

    /** @return event name as a string
    /* <p>
    */
    public String getType()
    {
        return type;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    /** Causes exit from in-progress event dispatch before the next
        currentTarget is selected. Replaces the preventBubble() and
        preventCapture() methods which were present in early drafts;
    /* <p>
    /*  选择currentTarget。替换早期草案中存在的preventBubble()和preventCapture()方法;
    /* 
    /* 
        they may be reintroduced in future levels of the DOM. */
    public void stopPropagation()
    {
        stopPropagation=true;
    }

    /** Prevents any default processing built into the target node from
        occurring.
    /* <p>
    /*  发生。
      */
    public void preventDefault()
    {
        preventDefault=true;
    }

}

/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.corba.se.spi.orbutil.fsm;

/**
 * A StateEngine defines the state transition function for a
 * finite state machine (FSM). A FSM always has a current state.
 * In response to an Input, the FSM performs an Action and
 * makes a transition to a new state.  Note that any object can
 * be used as an input if it supports the Input interface.
 * For example, a protocol message may be an input.  The FSM
 * uses only the result of calling getLabel on the Input to
 * drive the transition.
 * <p>
 * The function can be non-deterministic
 * in that the same input may cause transitions to different new
 * states from the current state.  In this case, the action that
 * is executed for the transition must set the correct new state.
 *
 * <p>
 *  StateEngine定义了有限状态机(FSM)的状态转换函数。 FSM始终具有当前状态。响应于输入,FSM执行动作并转换到新的状态。注意,任何对象都可以用作输入,如果它支持输入接口。
 * 例如,协议消息可以是输入。 FSM仅使用在输入上调用getLabel的结果来驱动转换。
 * <p>
 *  该函数可以是非确定性的,因为相同的输入可以导致从当前状态到不同的新状态的转变。在这种情况下,为转换执行的操作必须设置正确的新状态。
 * 
 * 
 * @author Ken Cavanaugh
 */
public interface StateEngine
{
        /** Add a new transition (old,in,guard,act,new) to the state engine.
        * Multiple calls to add with the same old and in are permitted,
        * in which case only a transition in which the guard evaluates to
        * true will be taken.  If no such transition is enabled, a default
        * will be taken.  If more than one transition is enabled, one will
        * be chosen arbitrarily.
        * This method can only be called before done().  An attempt to
        * call it after done() results in an IllegalStateException.
        * <p>
        *  允许使用相同的旧和in添加的多个调用,在这种情况下,将仅采用其中防护评估为true的转换。如果未启用此类转换,将采用默认值。如果启用多个转换,将任意选择一个。此方法只能在done()之前调用。
        * 在done()之后尝试调用它会导致IllegalStateException。
        * 
        */
        public StateEngine add( State oldState, Input input, Guard guard,
            Action action, State newState ) throws IllegalStateException ;

        /** Add a transition with a guard that always evaluates to true.
        /* <p>
        */
        public StateEngine add( State oldState, Input input,
            Action action, State newState ) throws IllegalStateException ;

        /** Set the default transition and action for a state.
        * This transition will be used if no more specific transition was
        * defined for the actual input.  Repeated calls to this method
        * simply change the default.
        * This method can only be called before done().  An attempt to
        * call it after done() results in an IllegalStateException.
        * <p>
        *  如果没有为实际输入定义更多的特定转换,则将使用此转换。重复调用此方法只需更改默认值。此方法只能在done()之前调用。尝试在done()之后调用它会导致IllegalStateException。
        * 
        */
        public StateEngine setDefault( State oldState, Action action, State newState )
                throws IllegalStateException ;

        /** Equivalent to setDefault( oldState, act, newState ) where act is an
         * action that does nothing.
         * <p>
         *  行动,什么也不做。
         * 
         */
        public StateEngine setDefault( State oldState, State newState )
                throws IllegalStateException ;

        /** Euaivalent to setDefault( oldState, oldState )
        /* <p>
         */
        public StateEngine setDefault( State oldState )
                throws IllegalStateException ;

        /** Set the default action used in this state engine.  This is the
        * action that is called whenever there is no applicable transition.
        * Normally this would simply flag an error.  This method can only
        * be called before done().  An attempt to
        * call it after done() results in an IllegalStateException.
        * <p>
        * 每当没有适用的转换时调用的操作。通常这只是标记一个错误。此方法只能在done()之前调用。尝试在done()之后调用它会导致IllegalStateException。
        * 
        */
        public void setDefaultAction( Action act ) throws IllegalStateException ;

        /** Called after all transitions have been added to the state engine.
        * This provides an opportunity for the implementation to optimize
        * its representation before the state engine is used.  This method
        * may only be called once.  An attempt to call it more than once
        * results in an IllegalStateException.
        * <p>
        *  这提供了实现在使用状态引擎之前优化其表示的机会。此方法只能调用一次。尝试多次调用它会导致IllegalStateException。
        * 
        */
        public void done() throws IllegalStateException ;

        /** Create an instance of a FSM that uses this state engine.
        * The initial state of the FSM will be the stateState specified
        * here.  This method can only be called after done().  An attempt
        * to call it before done results in an IllegalStateException.
        * <p>
        *  FSM的初始状态将是此处指定的stateState。此方法只能在done()之后调用。尝试在完成之前调用它会导致IllegalStateException。
        */
        public FSM makeFSM( State startState ) throws IllegalStateException ;
}

// end of StateEngine.java

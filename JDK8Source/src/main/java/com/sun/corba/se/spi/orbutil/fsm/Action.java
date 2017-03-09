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
 * Description goes here
 *
 * <p>
 *  说明在这里
 * 
 * 
 * @author Ken Cavanaugh
 */
public interface Action
{
        /** Called by the state engine to perform an action
        * before a state transition takes place.  The FSM is
        * passed so that the Action may set the next state in
        * cases when that is required.  FSM and Input together
        * allow actions to be written that depend on the state and
        * input, but this should generally be avoided, as the
        * reason for a state machine in the first place is to cleanly
        * separate the actions and control flow.   Note that an
        * action should complete in a timely manner.  If the state machine
        * is used for concurrency control with multiple threads, the
        * action must not allow multiple threads to run simultaneously
        * in the state machine, as the state could be corrupted.
        * Any exception thrown by the Action for the transition
        * will be propagated to doIt.
        * <p>
        *  在状态转换发生之前。传递FSM,以便在需要时,Action可以设置下一个状态。 FSM和输入一起允许写入取决于状态和输入的动作,但这通常应当避免,因为状态机首先是清楚地分离动作和控制流的原因。
        * 请注意,操作应及时完成。如果状态机用于具有多个线程的并发控制,则操作不允许多个线程在状态机中同时运行,因为状态可能被破坏。转换的Action抛出的任何异常都将传播到doIt。
        * 
        * @param FSM fsm is the state machine causing this action.
        * @param Input in is the input that caused the transition.
        */
        public void doIt( FSM fsm, Input in ) ;
}

// end of Action.java

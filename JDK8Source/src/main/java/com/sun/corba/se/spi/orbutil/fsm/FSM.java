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

package com.sun.corba.se.spi.orbutil.fsm ;

/**
 * An FSM is used to represent an instance of a finite state machine
 * which has a transition function represented by an instance of
 * StateEngine.  An instance of an FSM may be created either by calling
 * StateEngine.makeFSM( startState ) on a state engine, or by extending FSMImpl and
 * using a constructor.  Using FSMImpl as a base class is convenient if
 * additional state is associated with the FSM beyond that encoded
 * by the current state.  This is especially convenient if an action
 * needs some additional information.  For example, counters are best
 * handled by special actions rather than encoding a bounded counter
 * in a state machine.  It is also possible to create a class that
 * implements the FSM interface by delegating to an FSM instance
 * created by StateEngine.makeFSM.
 *
 * <p>
 *  FSM用于表示有状态机的实例,该有限状态机具有由StateEngine的实例表示的过渡函数。
 * 可以通过在状态引擎上调用StateEngine.makeFSM(startState)或通过扩展FSMImpl并使用构造函数来创建FSM的实例。
 * 如果附加状态与超出由当前状态编码的FSM相关联的FSM关联,则使用FSMImpl作为基类是方便的。如果操作需要一些附加信息,这是特别方便的。
 * 例如,计数器最好由特殊动作处理,而不是在状态机中编码有界计数器。还可以创建一个类,通过委派给由StateEngine.makeFSM创建的FSM实例来实现FSM接口。
 * 
 * 
 * @author Ken Cavanaugh
 */
public interface FSM
{
    /** Get the current state of this FSM.
    /* <p>
    */
    public State getState() ;

    /** Perform the action and transition to the next state based
    * on the current state of the FSM and the input.
    * <p>
    */
    public void doIt( Input in ) ;
}

// end of FSM.java

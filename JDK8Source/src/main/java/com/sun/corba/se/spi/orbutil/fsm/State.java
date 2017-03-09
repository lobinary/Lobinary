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
 * This interface must be implemented by any class that is used as
 * a state in a FSM.  The FSM only needs the identity of this
 * object, so all that is really needs is the default equals implementation.
 * The toString() method should also be overridden to give a concise
 * description or name of the state.  The StateImpl class handles this.
 * <P>
 * Pre- and post- actions are taken only on completed transitions between
 * different states.  Assume that the FSM is in state A, and the FSM will
 * transition to state B under input I with action X.  If A != B and X completes
 * successfully, then after X completes execution, A.postAction is executed,
 * followed by B.preAction.
 *
 * <p>
 *  此接口必须由用作FSM中的状态的任何类实现。 FSM只需要这个对象的身份,所以真正需要的是默认的equals实现。 toString()方法也应该被覆盖以给出简明的描述或状态的名称。
 *  StateImpl类处理这个。
 * <P>
 *  只有在不同状态之间完成的转换时才执行动作之前和之后。
 * 假设FSM处于状态A,并且FSM将在具有动作X的输入I下转变到状态B.如果A！= B和X成功完成,则在X完成执行之后,执行A.postAction,接着执行B. preAction。
 * 
 * 
 * @author Ken Cavanaugh
 */
public interface State
{
    /** Method that defines action that occurs whenever this state is entered.
    * Any exceptions thrown by this method are ignored.
    * <p>
    */
    void preAction( FSM fsm ) ;

    /** Method that defines action that occurs whenever this state is exited.
    * Any exceptions thrown by this method are ignored.
    * <p>
    *  将忽略此方法抛出的任何异常。
    * 
    */
    void postAction( FSM fsm ) ;
}

// end of State.java

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
 * $Id: CoroutineManager.java,v 1.2.4.1 2005/09/15 08:14:58 suresh_emailid Exp $
 * <p>
 *  $ Id：CoroutineManager.java,v 1.2.4.1 2005/09/15 08:14:58 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import java.util.BitSet;

import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;


/**
 * <p>Support the coroutine design pattern.</p>
 *
 * <p>A coroutine set is a very simple cooperative non-preemptive
 * multitasking model, where the switch from one task to another is
 * performed via an explicit request. Coroutines interact according to
 * the following rules:</p>
 *
 * <ul>
 * <li>One coroutine in the set has control, which it retains until it
 * either exits or resumes another coroutine.</li>
 * <li>A coroutine is activated when it is resumed by some other coroutine
 * for the first time.</li>
 * <li>An active coroutine that gives up control by resuming another in
 * the set retains its context -- including call stack and local variables
 * -- so that if/when it is resumed, it will proceed from the point at which
 * it last gave up control.</li>
 * </ul>
 *
 * <p>Coroutines can be thought of as falling somewhere between pipes and
 * subroutines. Like call/return, there is an explicit flow of control
 * from one coroutine to another. Like pipes, neither coroutine is
 * actually "in charge", and neither must exit in order to transfer
 * control to the other. </p>
 *
 * <p>One classic application of coroutines is in compilers, where both
 * the parser and the lexer are maintaining complex state
 * information. The parser resumes the lexer to process incoming
 * characters into lexical tokens, and the lexer resumes the parser
 * when it has reached a point at which it has a reliably interpreted
 * set of tokens available for semantic processing. Structuring this
 * as call-and-return would require saving and restoring a
 * considerable amount of state each time. Structuring it as two tasks
 * connected by a queue may involve higher overhead (in systems which
 * can optimize the coroutine metaphor), isn't necessarily as clear in
 * intent, may have trouble handling cases where data flows in both
 * directions, and may not handle some of the more complex cases where
 * more than two coroutines are involved.</p>
 *
 * <p>Most coroutine systems also provide a way to pass data between the
 * source and target of a resume operation; this is sometimes referred
 * to as "yielding" a value.  Others rely on the fact that, since only
 * one member of a coroutine set is running at a time and does not
 * lose control until it chooses to do so, data structures may be
 * directly shared between them with only minimal precautions.</p>
 *
 * <p>"Note: This should not be taken to mean that producer/consumer
 * problems should be always be done with coroutines." Queueing is
 * often a better solution when only two threads of execution are
 * involved and full two-way handshaking is not required. It's a bit
 * difficult to find short pedagogical examples that require
 * coroutines for a clear solution.</p>
 *
 * <p>The fact that only one of a group of coroutines is running at a
 * time, and the control transfer between them is explicit, simplifies
 * their possible interactions, and in some implementations permits
 * them to be implemented more efficiently than general multitasking.
 * In some situations, coroutines can be compiled out entirely;
 * in others, they may only require a few instructions more than a
 * simple function call.</p>
 *
 * <p>This version is built on top of standard Java threading, since
 * that's all we have available right now. It's been encapsulated for
 * code clarity and possible future optimization.</p>
 *
 * <p>(Two possible approaches: wait-notify based and queue-based. Some
 * folks think that a one-item queue is a cleaner solution because it's
 * more abstract -- but since coroutine _is_ an abstraction I'm not really
 * worried about that; folks should be able to switch this code without
 * concern.)</p>
 *
 * <p>%TBD% THIS SHOULD BE AN INTERFACE, to facilitate building other
 * implementations... perhaps including a true coroutine system
 * someday, rather than controlled threading. Arguably Coroutine
 * itself should be an interface much like Runnable, but I think that
 * can be built on top of this.</p>
 * <p>
 *  <p>支持协同设计模式。</p>
 * 
 *  协同集是一种非常简单的协作非抢占多任务模型,其中从一个任务到另一个任务的切换是通过显式请求来执行的。协同程序根据以下规则进行交互：</p>
 * 
 * <ul>
 * <li>集合中的一个协程具有控制权,它保留,直到它退出或恢复另一个协同程序。</li> <li>协程在第一次由其他协同程序恢复时被激活。
 * </li > <li>通过恢复集合中的另一个来放弃控制的活动协同保留其上下文(包括调用堆栈和局部变量),以便如果/当它被恢复时,它将从它最后给出的点开始</li>。
 * </ul>
 * 
 *  <p>协同程序可以被认为是在管道和子程序之间的某处。像call / return一样,有一个从一个协程到另一个协程的显式控制流。
 * 像管道一样,协同程序实际上是"负责"的,并且两者都不必退出以便将控制转移到另一个。 </p>。
 * 
 * <p>协程的一个典型应用是在编译器中,其中解析器和词法分析器都维护复杂的状态信息。
 * 解析器恢复词法分析器将传入的字符处理为词汇标记,并且词法分析器在达到具有可用于语义处理的可靠解释的标记集合的点时恢复语法分析器。将其构造为呼叫和返回将需要每次保存和恢复相当大量的状态。
 * 将其构造为由队列连接的两个任务可能涉及较高的开销(在可以优化协同程序隐喻的系统中),不必在意图中清楚,可能在处理两个方向上的数据流的情况下存在麻烦,并且可能不处理一些的更复杂的情况,其中涉及两个以上的协
 * 同程序。
 * 解析器恢复词法分析器将传入的字符处理为词汇标记,并且词法分析器在达到具有可用于语义处理的可靠解释的标记集合的点时恢复语法分析器。将其构造为呼叫和返回将需要每次保存和恢复相当大量的状态。</p>。
 * 
 *  <p>大多数协同系统还提供在恢复操作的源和目标之间传递数据的方式;这有时被称为"屈服"一个值。
 * 其他依赖的事实是,由于协同集只有一个成员在同一时间运行,并且不会失去控制,直到它选择这样做,数据结构可以直接在它们之间共享,只需很少的预防措施。</p>。
 * 
 * <p>"注意：这不应该意味着生产者/消费者问题应该总是使用协同程序。当仅涉及两个执行线程并且不需要完全双向握手时,排队通常是更好的解决方案。
 * 这是一个有点困难找到短的教育学的例子,需要协同一个明确的解决方案。</p>。
 * 
 *  <p>每次只有一组协程中的一个正在运行,并且它们之间的控制传递是显式的,简化了它们可能的交互,并且在一些实现中允许它们比一般多任务更有效地实现。
 * 在某些情况下,协同程序可以完全编译出来;在其他情况下,它们可能只需要几个指令,而不仅仅是一个简单的函数调用。</p>。
 * 
 *  <p>此版本是建立在标准Java线程之上的,因为这是我们现在可用的。它已封装,以便代码清晰和可能的未来优化。</p>
 * 
 *  <p>(两种可能的方法：基于等待通知和基于队列。有些人认为单项目队列是一个更清洁的解决方案,因为它更抽象 - 但是因为协程_is_抽象我不真的担心;人们应该能够切换这个代码,不用担心。)</p>
 * 
 * <p>％TBD％这应该是一个接口,以方便构建其他实现...也许包括一个真正的协同系统有一天,而不是控制线程。可以说Coroutine本身应该是一个类似Runnable的接口,但我认为可以建立在这之上。
 * </p>。
 * 
 * 
 * */
public class CoroutineManager
{
  /** "Is this coroutine ID number already in use" lookup table.
   * Currently implemented as a bitset as a compromise between
   * compactness and speed of access, but obviously other solutions
   * could be applied.
   * <p>
   *  当前作为比特集实现作为紧凑性和访问速度之间的折衷,但是显然可以应用其他解决方案。
   * 
   * 
   * */
  BitSet m_activeIDs=new BitSet();

  /** Limit on the coroutine ID numbers accepted. I didn't want the
   * in-use table to grow without bound. If we switch to a more efficient
   * sparse-array mechanism, it may be possible to raise or eliminate
   * this boundary.
   * @xsl.usage internal
   * <p>
   *  使用表无限增长。如果我们切换到更有效的稀疏阵列机制,则可以提高或消除该边界。 @ xsl.usage internal
   * 
   */
  static final int m_unreasonableId=1024;

  /** Internal field used to hold the data being explicitly passed
   * from one coroutine to another during a co_resume() operation.
   * (Of course implicit data sharing may also occur; one of the reasons
   * for using coroutines is that you're guaranteed that none of the
   * other coroutines in your set are using shared structures at the time
   * you access them.)
   *
   * %REVIEW% It's been proposed that we be able to pass types of data
   * other than Object -- more specific object types, or
   * lighter-weight primitives.  This would seem to create a potential
   * explosion of "pass x recieve y back" methods (or require
   * fracturing resume into two calls, resume-other and
   * wait-to-be-resumed), and the weight issue could be managed by
   * reusing a mutable buffer object to contain the primitive
   * (remember that only one coroutine runs at a time, so once the
   * buffer's set it won't be walked on). Typechecking objects is
   * interesting from a code-robustness point of view, but it's
   * unclear whether it makes sense to encapsulate that in the
   * coroutine code or let the callers do it, since it depends on RTTI
   * either way. Restricting the parameters to objects implementing a
   * specific CoroutineParameter interface does _not_ seem to be a net
   * win; applications can do so if they want via front-end code, but
   * there seem to be too many use cases involving passing an existing
   * object type that you may not have the freedom to alter and may
   * not want to spend time wrapping another object around.
   * <p>
   *  在co_resume()操作期间从一个协同程序到另一个协同程序。 (当然也可能发生隐式数据共享;使用协同程序的一个原因是,您保证您集合中的其他协同程序都不会在访问它们时使用共享结构。)
   * 
   * ％REVIEW％建议我们能够传递Object以外的数据类型 - 更具体的对象类型或更轻量的基本类型。
   * 这似乎创造了"通过接收回"方法的潜在爆炸(或者需要将恢复恢复成两个调用,恢复 - 其他和等待恢复),并且重量问题可以通过重用可变缓冲区对象包含原语(记住每次只有一个协程运行,所以一旦缓冲区设置它就不会被
   * 处理)。
   * ％REVIEW％建议我们能够传递Object以外的数据类型 - 更具体的对象类型或更轻量的基本类型。
   * 从代码稳健性的角度来看,类型检查对象很有趣,但是不清楚是否将它封装在协同代码中或让调用者做到这一点是有意义的,因为它依赖于RTTI。
   * 将参数限制为实现特定CoroutineParameter接口的对象,_not_似乎是一个净胜利;应用程序可以这样做,如果他们想通过前端代码,但似乎有太多的使用情况涉及传递一个现有的对象类型,你可能没有自
   * 由地改变,可能不想花时间包围另一个对象。
   * 从代码稳健性的角度来看,类型检查对象很有趣,但是不清楚是否将它封装在协同代码中或让调用者做到这一点是有意义的,因为它依赖于RTTI。
   * 
   * 
   * */
  Object m_yield=null;

  // Expose???
  final static int NOBODY=-1;
  final static int ANYBODY=-1;

  /** Internal field used to confirm that the coroutine now waking up is
   * in fact the one we intended to resume. Some such selection mechanism
   * is needed when more that two coroutines are operating within the same
   * group.
   * <p>
   *  事实上我们打算恢复的。当更多的两个协同器在同一组中操作时,需要一些这样的选择机制。
   * 
   */
  int m_nextCoroutine=NOBODY;

  /** <p>Each coroutine in the set managed by a single
   * CoroutineManager is identified by a small positive integer. This
   * brings up the question of how to manage those integers to avoid
   * reuse... since if two coroutines use the same ID number, resuming
   * that ID could resume either. I can see arguments for either
   * allowing applications to select their own numbers (they may want
   * to declare mnemonics via manefest constants) or generating
   * numbers on demand.  This routine's intended to support both
   * approaches.</p>
   *
   * <p>%REVIEW% We could use an object as the identifier. Not sure
   * it's a net gain, though it would allow the thread to be its own
   * ID. Ponder.</p>
   *
   * <p>
   * CoroutineManager由一个小的正整数标识。这提出了如何管理这些整数以避免重复使用的问题...因为如果两个协程使用相同的ID号,恢复该ID可以恢复。
   * 我可以看到参数,允许应用程序选择自己的数字(他们可能想通过manefest常量声明助记符)或生成数字按需。此例程旨在支持这两种方法。</p>。
   * 
   *  <p>％REVIEW％我们可以使用一个对象作为标识符。不知道这是一个净增益,虽然它会允许线程是自己的ID。 Ponder。</p>
   * 
   * 
   * @param coroutineID  If >=0, requests that we reserve this number.
   * If <0, requests that we find, reserve, and return an available ID
   * number.
   *
   * @return If >=0, the ID number to be used by this coroutine. If <0,
   * an error occurred -- the ID requested was already in use, or we
   * couldn't assign one without going over the "unreasonable value" mark
   * */
  public synchronized int co_joinCoroutineSet(int coroutineID)
  {
    if(coroutineID>=0)
      {
        if(coroutineID>=m_unreasonableId || m_activeIDs.get(coroutineID))
          return -1;
      }
    else
      {
        // What I want is "Find first clear bit". That doesn't exist.
        // JDK1.2 added "find last set bit", but that doesn't help now.
        coroutineID=0;
        while(coroutineID<m_unreasonableId)
          {
            if(m_activeIDs.get(coroutineID))
              ++coroutineID;
            else
              break;
          }
        if(coroutineID>=m_unreasonableId)
          return -1;
      }

    m_activeIDs.set(coroutineID);
    return coroutineID;
  }

  /** In the standard coroutine architecture, coroutines are
   * identified by their method names and are launched and run up to
   * their first yield by simply resuming them; its's presumed that
   * this recognizes the not-already-running case and does the right
   * thing. We seem to need a way to achieve that same threadsafe
   * run-up...  eg, start the coroutine with a wait.
   *
   * %TBD% whether this makes any sense...
   *
   * <p>
   *  通过它们的方法名称来识别,并且通过简单地恢复它们来启动并运行到它们的第一收益;它的推定是,这承认不已经运行的情况,做正确的事情。
   * 我们似乎需要一种方法来实现同样的线程安全运行...例如,启动协同程序与等待。
   * 
   *  ％TBD％这是否有任何意义...
   * 
   * 
   * @param thisCoroutine the identifier of this coroutine, so we can
   * recognize when we are being resumed.
   * @exception java.lang.NoSuchMethodException if thisCoroutine isn't
   * a registered member of this group. %REVIEW% whether this is the
   * best choice.
   * */
  public synchronized Object co_entry_pause(int thisCoroutine) throws java.lang.NoSuchMethodException
  {
    if(!m_activeIDs.get(thisCoroutine))
      throw new java.lang.NoSuchMethodException();

    while(m_nextCoroutine != thisCoroutine)
      {
        try
          {
            wait();
          }
        catch(java.lang.InterruptedException e)
          {
            // %TBD% -- Declare? Encapsulate? Ignore? Or
            // dance widdershins about the instruction cache?
          }
      }

    return m_yield;
  }

  /** Transfer control to another coroutine which has already been started and
   * is waiting on this CoroutineManager. We won't return from this call
   * until that routine has relinquished control.
   *
   * %TBD% What should we do if toCoroutine isn't registered? Exception?
   *
   * <p>
   *  正在等待此CoroutineManager。我们不会从这个调用返回,直到该例程放弃了控制。
   * 
   *  ％TBD％如果toCoroutine未注册,我们应该怎么办?例外?
   * 
   * 
   * @param arg_object A value to be passed to the other coroutine.
   * @param thisCoroutine Integer identifier for this coroutine. This is the
   * ID we watch for to see if we're the ones being resumed.
   * @param toCoroutine  Integer identifier for the coroutine we wish to
   * invoke.
   * @exception java.lang.NoSuchMethodException if toCoroutine isn't a
   * registered member of this group. %REVIEW% whether this is the best choice.
   * */
  public synchronized Object co_resume(Object arg_object,int thisCoroutine,int toCoroutine) throws java.lang.NoSuchMethodException
  {
    if(!m_activeIDs.get(toCoroutine))
      throw new java.lang.NoSuchMethodException(XMLMessages.createXMLMessage(XMLErrorResources.ER_COROUTINE_NOT_AVAIL, new Object[]{Integer.toString(toCoroutine)})); //"Coroutine not available, id="+toCoroutine);

    // We expect these values to be overwritten during the notify()/wait()
    // periods, as other coroutines in this set get their opportunity to run.
    m_yield=arg_object;
    m_nextCoroutine=toCoroutine;

    notify();
    while(m_nextCoroutine != thisCoroutine || m_nextCoroutine==ANYBODY || m_nextCoroutine==NOBODY)
      {
        try
          {
            // System.out.println("waiting...");
            wait();
          }
        catch(java.lang.InterruptedException e)
          {
            // %TBD% -- Declare? Encapsulate? Ignore? Or
            // dance deasil about the program counter?
          }
      }

    if(m_nextCoroutine==NOBODY)
      {
        // Pass it along
        co_exit(thisCoroutine);
        // And inform this coroutine that its partners are Going Away
        // %REVIEW% Should this throw/return something more useful?
        throw new java.lang.NoSuchMethodException(XMLMessages.createXMLMessage(XMLErrorResources.ER_COROUTINE_CO_EXIT, null)); //"CoroutineManager recieved co_exit() request");
      }

    return m_yield;
  }

  /** Terminate this entire set of coroutines. The others will be
   * deregistered and have exceptions thrown at them. Note that this
   * is intended as a panic-shutdown operation; under normal
   * circumstances a coroutine should always end with co_exit_to() in
   * order to politely inform at least one of its partners that it is
   * going away.
   *
   * %TBD% This may need significantly more work.
   *
   * %TBD% Should this just be co_exit_to(,,CoroutineManager.PANIC)?
   *
   * <p>
   *  注销并有异常抛出他们。注意,这旨在作为紧急关闭操作;在正常情况下,协同程序应该总是以co_exit_to()结束,以便礼貌地通知其至少一个合作伙伴它将要离开。
   * 
   *  ％TBD％这可能需要更多的工作。
   * 
   *  ％TBD％这应该是co_exit_to(,, CoroutineManager.PANIC)?
   * 
   * @param thisCoroutine Integer identifier for the coroutine requesting exit.
   * */
  public synchronized void co_exit(int thisCoroutine)
  {
    m_activeIDs.clear(thisCoroutine);
    m_nextCoroutine=NOBODY; // %REVIEW%
    notify();
  }

  /** Make the ID available for reuse and terminate this coroutine,
   * transferring control to the specified coroutine. Note that this
   * returns immediately rather than waiting for any further coroutine
   * traffic, so the thread can proceed with other shutdown activities.
   *
   * <p>
   * 
   * 
   * @param arg_object    A value to be passed to the other coroutine.
   * @param thisCoroutine Integer identifier for the coroutine leaving the set.
   * @param toCoroutine   Integer identifier for the coroutine we wish to
   * invoke.
   * @exception java.lang.NoSuchMethodException if toCoroutine isn't a
   * registered member of this group. %REVIEW% whether this is the best choice.
   * */
  public synchronized void co_exit_to(Object arg_object,int thisCoroutine,int toCoroutine) throws java.lang.NoSuchMethodException
  {
    if(!m_activeIDs.get(toCoroutine))
      throw new java.lang.NoSuchMethodException(XMLMessages.createXMLMessage(XMLErrorResources.ER_COROUTINE_NOT_AVAIL, new Object[]{Integer.toString(toCoroutine)})); //"Coroutine not available, id="+toCoroutine);

    // We expect these values to be overwritten during the notify()/wait()
    // periods, as other coroutines in this set get their opportunity to run.
    m_yield=arg_object;
    m_nextCoroutine=toCoroutine;

    m_activeIDs.clear(thisCoroutine);

    notify();
  }
}

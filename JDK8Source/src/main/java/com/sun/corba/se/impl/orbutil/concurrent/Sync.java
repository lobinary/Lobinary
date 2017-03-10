/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2002, Oracle and/or its affiliates. All rights reserved.
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

/*
  File: Sync.java

  Originally written by Doug Lea and released into the public domain.
  This may be used for any purposes whatsoever without acknowledgment.
  Thanks for the assistance and support of Sun Microsystems Labs,
  and everyone contributing, testing, and using this code.

  History:
  Date       Who                What
  11Jun1998  dl               Create public version
   5Aug1998  dl               Added some convenient time constants
/* <p>
/*  文件：Syncjava
/* 
/*  最初由Doug Lea撰写并发布到公共领域这可能用于任何目的,无需确认感谢您的帮助和支持Sun Microsystems Labs,并且每个人贡献,测试和使用此代码
/* 
/*  历史：日期谁谁11Jun1998 dl创建公共版本5Aug1998 dl添加了一些方便的时间常数
/* 
*/

package com.sun.corba.se.impl.orbutil.concurrent;

/**
 * Main interface for locks, gates, and conditions.
 * <p>
 * Sync objects isolate waiting and notification for particular
 * logical states, resource availability, events, and the like that are
 * shared across multiple threads. Use of Syncs sometimes
 * (but by no means always) adds flexibility and efficiency
 * compared to the use of plain java monitor methods
 * and locking, and are sometimes (but by no means always)
 * simpler to program with.
 * <p>
 *
 * Most Syncs are intended to be used primarily (although
 * not exclusively) in  before/after constructions such as:
 * <pre>
 * class X {
 *   Sync gate;
 *   // ...
 *
 *   public void m() {
 *     try {
 *       gate.acquire();  // block until condition holds
 *       try {
 *         // ... method body
 *       }
 *       finally {
 *         gate.release()
 *       }
 *     }
 *     catch (InterruptedException ex) {
 *       // ... evasive action
 *     }
 *   }
 *
 *   public void m2(Sync cond) { // use supplied condition
 *     try {
 *       if (cond.attempt(10)) {         // try the condition for 10 ms
 *         try {
 *           // ... method body
 *         }
 *         finally {
 *           cond.release()
 *         }
 *       }
 *     }
 *     catch (InterruptedException ex) {
 *       // ... evasive action
 *     }
 *   }
 * }
 * </pre>
 * Syncs may be used in somewhat tedious but more flexible replacements
 * for built-in Java synchronized blocks. For example:
 * <pre>
 * class HandSynched {
 *   private double state_ = 0.0;
 *   private final Sync lock;  // use lock type supplied in constructor
 *   public HandSynched(Sync l) { lock = l; }
 *
 *   public void changeState(double d) {
 *     try {
 *       lock.acquire();
 *       try     { state_ = updateFunction(d); }
 *       finally { lock.release(); }
 *     }
 *     catch(InterruptedException ex) { }
 *   }
 *
 *   public double getState() {
 *     double d = 0.0;
 *     try {
 *       lock.acquire();
 *       try     { d = accessFunction(state_); }
 *       finally { lock.release(); }
 *     }
 *     catch(InterruptedException ex){}
 *     return d;
 *   }
 *   private double updateFunction(double d) { ... }
 *   private double accessFunction(double d) { ... }
 * }
 * </pre>
 * If you have a lot of such methods, and they take a common
 * form, you can standardize this using wrappers. Some of these
 * wrappers are standardized in LockedExecutor, but you can make others.
 * For example:
 * <pre>
 * class HandSynchedV2 {
 *   private double state_ = 0.0;
 *   private final Sync lock;  // use lock type supplied in constructor
 *   public HandSynchedV2(Sync l) { lock = l; }
 *
 *   protected void runSafely(Runnable r) {
 *     try {
 *       lock.acquire();
 *       try { r.run(); }
 *       finally { lock.release(); }
 *     }
 *     catch (InterruptedException ex) { // propagate without throwing
 *       Thread.currentThread().interrupt();
 *     }
 *   }
 *
 *   public void changeState(double d) {
 *     runSafely(new Runnable() {
 *       public void run() { state_ = updateFunction(d); }
 *     });
 *   }
 *   // ...
 * }
 * </pre>
 * <p>
 * One reason to bother with such constructions is to use deadlock-
 * avoiding back-offs when dealing with locks involving multiple objects.
 * For example, here is a Cell class that uses attempt to back-off
 * and retry if two Cells are trying to swap values with each other
 * at the same time.
 * <pre>
 * class Cell {
 *   long value;
 *   Sync lock = ... // some sync implementation class
 *   void swapValue(Cell other) {
 *     for (;;) {
 *       try {
 *         lock.acquire();
 *         try {
 *           if (other.lock.attempt(100)) {
 *             try {
 *               long t = value;
 *               value = other.value;
 *               other.value = t;
 *               return;
 *             }
 *             finally { other.lock.release(); }
 *           }
 *         }
 *         finally { lock.release(); }
 *       }
 *       catch (InterruptedException ex) { return; }
 *     }
 *   }
 * }
 *</pre>
 * <p>
 * Here is an even fancier version, that uses lock re-ordering
 * upon conflict:
 * <pre>
 * class Cell {
 *   long value;
 *   Sync lock = ...;
 *   private static boolean trySwap(Cell a, Cell b) {
 *     a.lock.acquire();
 *     try {
 *       if (!b.lock.attempt(0))
 *         return false;
 *       try {
 *         long t = a.value;
 *         a.value = b.value;
 *         b.value = t;
 *         return true;
 *       }
 *       finally { other.lock.release(); }
 *     }
 *     finally { lock.release(); }
 *     return false;
 *   }
 *
 *  void swapValue(Cell other) {
 *    try {
 *      while (!trySwap(this, other) &&
 *            !tryswap(other, this))
 *        Thread.sleep(1);
 *    }
 *    catch (InterruptedException ex) { return; }
 *  }
 *}
 *</pre>
 * <p>
 * Interruptions are in general handled as early as possible.
 * Normally, InterruptionExceptions are thrown
 * in acquire and attempt(msec) if interruption
 * is detected upon entry to the method, as well as in any
 * later context surrounding waits.
 * However, interruption status is ignored in release();
 * <p>
 * Timed versions of attempt report failure via return value.
 * If so desired, you can transform such constructions to use exception
 * throws via
 * <pre>
 *   if (!c.attempt(timeval)) throw new TimeoutException(timeval);
 * </pre>
 * <p>
 * The TimoutSync wrapper class can be used to automate such usages.
 * <p>
 * All time values are expressed in milliseconds as longs, which have a maximum
 * value of Long.MAX_VALUE, or almost 300,000 centuries. It is not
 * known whether JVMs actually deal correctly with such extreme values.
 * For convenience, some useful time values are defined as static constants.
 * <p>
 * All implementations of the three Sync methods guarantee to
 * somehow employ Java <code>synchronized</code> methods or blocks,
 * and so entail the memory operations described in JLS
 * chapter 17 which ensure that variables are loaded and flushed
 * within before/after constructions.
 * <p>
 * Syncs may also be used in spinlock constructions. Although
 * it is normally best to just use acquire(), various forms
 * of busy waits can be implemented. For a simple example
 * (but one that would probably never be preferable to using acquire()):
 * <pre>
 * class X {
 *   Sync lock = ...
 *   void spinUntilAcquired() throws InterruptedException {
 *     // Two phase.
 *     // First spin without pausing.
 *     int purespins = 10;
 *     for (int i = 0; i < purespins; ++i) {
 *       if (lock.attempt(0))
 *         return true;
 *     }
 *     // Second phase - use timed waits
 *     long waitTime = 1; // 1 millisecond
 *     for (;;) {
 *       if (lock.attempt(waitTime))
 *         return true;
 *       else
 *         waitTime = waitTime * 3 / 2 + 1; // increase 50%
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * In addition pure synchronization control, Syncs
 * may be useful in any context requiring before/after methods.
 * For example, you can use an ObservableSync
 * (perhaps as part of a LayeredSync) in order to obtain callbacks
 * before and after each method invocation for a given class.
 * <p>

 * <p>[<a href="http://gee.cs.oswego.edu/dl/classes/EDU/oswego/cs/dl/util/concurrent/intro.html"> Introduction to this package. </a>]
 * <p>
 *  锁,门和条件的主界面
 * <p>
 * 同步对象隔离在多个线程间共享的特定逻辑状态,资源可用性,事件等的等待和通知使用同步有时(但绝不总是)与使用纯Java监视器方法相比增加了灵活性和效率,锁定,有时(但绝不总是)更简单的编程
 * <p>
 * 
 *  大多数同步件主要用于(但不是排他地)在结构之前/之后,例如：
 * <pre>
 *  class X {同步门; //
 * 
 *  public void m(){try {gateacquire(); // block until condition hold try {// method body} finally {gaterelease()}
 * } catch(InterruptedException ex){// evasive action}}。
 * 
 *  public void m2(Sync cond){//使用提供的条件try {if(condattempt(10)){//尝试条件10 ms try {//方法体} finally {condrelease ){// evasive action}
 * }}。
 * </pre>
 * 同步可以在有些繁琐但更灵活的替换中用于内置的Java同步块例如：
 * <pre>
 *  class HandSynched {private double state_ = 00;私有最终同步锁; //使用构造函数中提供的锁类型public HandSynched(Sync l){lock = l; }
 * }。
 * 
 *  public void changeState(double d){try {lockacquire(); try {state_ = updateFunction(d); } finally {lockrelease(); }
 * } catch(InterruptedException ex){}}。
 * 
 *  public double getState(){double d = 00; try {lockacquire(); try {d = accessFunction(state_); } final
 * ly {lockrelease(); }} catch(InterruptedException ex){} return d; } private double updateFunction(doub
 * le d){} private double accessFunction(double d){}}。
 * </pre>
 *  如果你有很多这样的方法,他们采取一个通用的形式,你可以标准化使用包装器一些这些包装器在LockedExecutor标准化,但你可以使其他例如：
 * <pre>
 * class HandSynchedV2 {private double state_ = 00;私有最终同步锁; //使用构造函数中提供的锁类型public HandSynchedV2(Sync l){lock = l; }
 * }。
 * 
 *  protected void runSafely(Runnable r){try {lockacquire(); try {rrun(); } finally {lockrelease(); }} c
 * atch(InterruptedException ex){//传播不抛出ThreadcurrentThread()interrupt(); }}。
 * 
 *  public void changeState(double d){runSafely(new Runnable(){public void run(){state_ = updateFunction(d);}
 * }); } //}。
 * </pre>
 * <p>
 *  麻烦这种结构的一个原因是使用死锁 - 避免在处理涉及多个对象的锁时的回退例如,这里是一个Cell类,它使用尝试回退和重试,如果两个单元格试图交换值与每个其他同时
 * <pre>
 * class Cell {long value; Sync lock = //一些同步实现类void swapValue(Cell other){for(;;){try {lockacquire(); try {if(otherlockattempt(100)){try {long t = value; value = othervalue; othervalue = t;返回; }
 *  finally {otherlockrelease(); }}} finally {lockrelease(); }} catch(InterruptedException ex){return; }
 * }}}。
 * /pre>
 * <p>
 *  这里是一个甚至fancier版本,使用锁重新排序冲突：
 * <pre>
 *  class Cell {long value;同步锁=; private static boolean trySwap(Cell a,Cell b){alockacquire(); try {！(！blockattempt(0))return false; try {long t = avalue; avalue = bvalue; bvalue = t; return true; }
 * 
**/


public interface Sync {

  /**
   *  Wait (possibly forever) until successful passage.
   *  Fail only upon interuption. Interruptions always result in
   *  `clean' failures. On failure,  you can be sure that it has not
   *  been acquired, and that no
   *  corresponding release should be performed. Conversely,
   *  a normal return guarantees that the acquire was successful.
   * <p>
   *  finally {otherlockrelease(); }} finally {lockrelease(); } return false; }}。
   * 
   *  void swapValue(Cell other){try {while(！trySwap(this,other)&&！tryswap(other,this))Threadsleep } catch
   * (InterruptedException ex){return; }}。
   * 
   * /pre>
   * <p>
   * 中断通常尽可能早地处理通常,如果在进入方法时检测到中断,并且在周围等待的任何后续上下文中检测到中断,则在捕获和尝试(msec)中抛出InterruptedExceptions。
   * 但是,在release()中忽略中断状态, ;。
   * <p>
   *  通过返回值尝试报告故障的定时版本如果需要,您可以将此类构造转换为使用异常throws via
   * <pre>
   *  if(！cattempt(timeval))throw new TimeoutException(timeval);
   * </pre>
   * <p>
   *  TimoutSync包装器类可以用于自动化这样的用法
   * <p>
   *  所有时间值以毫秒为单位表示为longs,其具有LongMAX_VALUE的最大值,或几乎300,000个世纪。
   * 不知道JVM是否实际正确地处理这样的极值值为了方便起见,一些有用的时间值被定义为静态常量。
   * <p>
   * 所有三个同步方法的实现保证以某种方式使用Java <code> synchronized </code>方法或块,并且因此需要在JLS第17章中描述的存储器操作,其确保变量在构造之前/之后被加载和刷新。
   * <p>
   *  同步也可以在自旋锁结构中使用虽然通常最好只使用acquire(),但是可以实现各种形式的忙等待一个简单的例子(但是可能永远不会优于使用acquire())：
   * <pre>
   * 
  **/

  public void acquire() throws InterruptedException;

  /**
   * Wait at most msecs to pass; report whether passed.
   * <p>
   * The method has best-effort semantics:
   * The msecs bound cannot
   * be guaranteed to be a precise upper bound on wait time in Java.
   * Implementations generally can only attempt to return as soon as possible
   * after the specified bound. Also, timers in Java do not stop during garbage
   * collection, so timeouts can occur just because a GC intervened.
   * So, msecs arguments should be used in
   * a coarse-grained manner. Further,
   * implementations cannot always guarantee that this method
   * will return at all without blocking indefinitely when used in
   * unintended ways. For example, deadlocks may be encountered
   * when called in an unintended context.
   * <p>
   * <p>
   *  class X {Sync lock = void spinUntilAcquired()throws InterruptedException {//两个阶段//首先旋转而不暂停int purespins = 10; for(int i = 0; i <purespins; ++ i){if(lockattempt(0))return true; } //第二阶段 - 使用定时等待long waitTime = 1; // 1 millisecond for(;;){if(lockattempt(waitTime))return true; else waitTime = waitTime * 3/2 + 1; // increase 50％}}}。
   * </pre>
   * <p>
   * 此外,纯同步控制,Syncs可能在任何上下文需要前/后方法有用例如,您可以使用ObservableSync(可能作为LayeredSync的一部分),以获得回调之前和之后的每个方法调用给定类
   * <p>
   * 
   *  <p> [<a href="http://geecsoswegoedu/dl/classes/EDU/oswego/cs/dl/util/concurrent/introhtml">此套件简介</a>
   * ]。
   * 
   * 
   * @param msecs the number of milleseconds to wait.
   * An argument less than or equal to zero means not to wait at all.
   * However, this may still require
   * access to a synchronization lock, which can impose unbounded
   * delay if there is a lot of contention among threads.
   * @return true if acquired
  **/

  public boolean attempt(long msecs) throws InterruptedException;

  /**
   * Potentially enable others to pass.
   * <p>
   * Because release does not raise exceptions,
   * it can be used in `finally' clauses without requiring extra
   * embedded try/catch blocks. But keep in mind that
   * as with any java method, implementations may
   * still throw unchecked exceptions such as Error or NullPointerException
   * when faced with uncontinuable errors. However, these should normally
   * only be caught by higher-level error handlers.
   * <p>
   *  等待(可能永远)直到成功通过仅在中断时失败中断总是导致"干净"失败在失败时,可以确定它未​​被获取,并且不应执行相应的释放。相反,正常返回保证获得成功
   * 
   * 
  **/

  public void release();

  /**  One second, in milliseconds; convenient as a time-out value **/
  public static final long ONE_SECOND = 1000;

  /**  One minute, in milliseconds; convenient as a time-out value **/
  public static final long ONE_MINUTE = 60 * ONE_SECOND;

  /**  One hour, in milliseconds; convenient as a time-out value **/
  public static final long ONE_HOUR = 60 * ONE_MINUTE;

  /**  One day, in milliseconds; convenient as a time-out value **/
  public static final long ONE_DAY = 24 * ONE_HOUR;

  /**  One week, in milliseconds; convenient as a time-out value **/
  public static final long ONE_WEEK = 7 * ONE_DAY;

  /**  One year in milliseconds; convenient as a time-out value  **/
  // Not that it matters, but there is some variation across
  // standard sources about value at msec precision.
  // The value used is the same as in java.util.GregorianCalendar
  public static final long ONE_YEAR = (long)(365.2425 * ONE_DAY);

  /**  One century in milliseconds; convenient as a time-out value **/
  public static final long ONE_CENTURY = 100 * ONE_YEAR;


}

/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Class {@code Object} is the root of the class hierarchy.
 * Every class has {@code Object} as a superclass. All objects,
 * including arrays, implement the methods of this class.
 *
 * <p>
 *  类{@code Object}是类层次结构的根。每个类都有{@code Object}作为超类。所有对象,包括数组,实现这个类的方法。
 * 
 * 
 * @author  unascribed
 * @see     java.lang.Class
 * @since   JDK1.0
 */
public class Object {

    private static native void registerNatives();
    static {
        registerNatives();
    }

    /**
     * Returns the runtime class of this {@code Object}. The returned
     * {@code Class} object is the object that is locked by {@code
     * static synchronized} methods of the represented class.
     *
     * <p><b>The actual result type is {@code Class<? extends |X|>}
     * where {@code |X|} is the erasure of the static type of the
     * expression on which {@code getClass} is called.</b> For
     * example, no cast is required in this code fragment:</p>
     *
     * <p>
     * {@code Number n = 0;                             }<br>
     * {@code Class<? extends Number> c = n.getClass(); }
     * </p>
     *
     * <p>
     *  返回此{@code Object}的运行时类。返回的{@code Class}对象是被表示类的{@code static synchronized}方法锁定的对象。
     * 
     *  <p> <b>实际的结果类型是{@code Class <? extends | X |>}其中{@code | X |}是调用{@code getClass}的表达式的静态类型的擦除。
     * </b>例如,在此代码片段中不需要转换： </p>。
     * 
     * <p>
     *  {@code Number n = 0; } <br> {@code Class <? extends Number> c = n.getClass(); }}
     * </p>
     * 
     * 
     * @return The {@code Class} object that represents the runtime
     *         class of this object.
     * @jls 15.8.2 Class Literals
     */
    public final native Class<?> getClass();

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@code equals(Object)}
     *     method, then calling the {@code hashCode} method on each of
     *     the two objects must produce the same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link java.lang.Object#equals(java.lang.Object)}
     *     method, then calling the {@code hashCode} method on each of the
     *     two objects must produce distinct integer results.  However, the
     *     programmer should be aware that producing distinct integer results
     *     for unequal objects may improve the performance of hash tables.
     * </ul>
     * <p>
     * As much as is reasonably practical, the hashCode method defined by
     * class {@code Object} does return distinct integers for distinct
     * objects. (This is typically implemented by converting the internal
     * address of the object into an integer, but this implementation
     * technique is not required by the
     * Java&trade; programming language.)
     *
     * <p>
     *  返回对象的哈希码值。这种方法支持散列表的好处,例如由{@link java.util.HashMap}提供的。
     * <p>
     *  {@code hashCode}的一般合同是：
     * <ul>
     * <li>无论何时在Java应用程序执行期间在同一对象上多次调用同一对象,{@code hashCode}方法必须始终返回相同的整数,前提是在对象的{@code equals}比较中未使用信息被修改。
     * 从应用程序的一个执行到相同应用程序的另一个执行,此整数不需要保持一致。
     *  <li>如果根据{@code equals(Object)}方法,两个对象相等,则对这两个对象中的每一个调用{@code hashCode}方法必须产生相同的整数结果。
     *  <li>这不是</em>要求如果两个对象根据{@link java.lang.Object#equals(java.lang.Object)}方法不相等,则调用{@code hashCode }方法对
     * 两个对象中的每一个必须产生不同的整数结果。
     *  <li>如果根据{@code equals(Object)}方法,两个对象相等,则对这两个对象中的每一个调用{@code hashCode}方法必须产生相同的整数结果。
     * 然而,程序员应该意识到,为不等对象产生不同的整数结果可以提高散列表的性能。
     * </ul>
     * <p>
     *  尽管合理实用,但由类{@code Object}定义的hashCode方法对不同对象返回不同的整数。 (这通常通过将对象的内部地址转换为整数来实现,但是Java和贸易;编程语言不需要这种实现技术。
     * )。
     * 
     * 
     * @return  a hash code value for this object.
     * @see     java.lang.Object#equals(java.lang.Object)
     * @see     java.lang.System#identityHashCode
     */
    public native int hashCode();

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method implements an equivalence relation
     * on non-null object references:
     * <ul>
     * <li>It is <i>reflexive</i>: for any non-null reference value
     *     {@code x}, {@code x.equals(x)} should return
     *     {@code true}.
     * <li>It is <i>symmetric</i>: for any non-null reference values
     *     {@code x} and {@code y}, {@code x.equals(y)}
     *     should return {@code true} if and only if
     *     {@code y.equals(x)} returns {@code true}.
     * <li>It is <i>transitive</i>: for any non-null reference values
     *     {@code x}, {@code y}, and {@code z}, if
     *     {@code x.equals(y)} returns {@code true} and
     *     {@code y.equals(z)} returns {@code true}, then
     *     {@code x.equals(z)} should return {@code true}.
     * <li>It is <i>consistent</i>: for any non-null reference values
     *     {@code x} and {@code y}, multiple invocations of
     *     {@code x.equals(y)} consistently return {@code true}
     *     or consistently return {@code false}, provided no
     *     information used in {@code equals} comparisons on the
     *     objects is modified.
     * <li>For any non-null reference value {@code x},
     *     {@code x.equals(null)} should return {@code false}.
     * </ul>
     * <p>
     * The {@code equals} method for class {@code Object} implements
     * the most discriminating possible equivalence relation on objects;
     * that is, for any non-null reference values {@code x} and
     * {@code y}, this method returns {@code true} if and only
     * if {@code x} and {@code y} refer to the same object
     * ({@code x == y} has the value {@code true}).
     * <p>
     * Note that it is generally necessary to override the {@code hashCode}
     * method whenever this method is overridden, so as to maintain the
     * general contract for the {@code hashCode} method, which states
     * that equal objects must have equal hash codes.
     *
     * <p>
     *  指示一些其他对象是否"等于"这一个。
     * <p>
     *  {@code equals}方法对非空对象引用实现等价关系：
     * <ul>
     * <li> <i> reflexive </i>：对于任何非空引用值{@code x},{@code x.equals(x)}应返回{@code true}。
     *  <li>这是<i>对称</i>：对于任何非空参考值{@code x}和{@code y},{@code x.equals(y)}应返回{@code true}当且仅当{@code y.equals(x)}
     * 返回{@code true}。
     * <li> <i> reflexive </i>：对于任何非空引用值{@code x},{@code x.equals(x)}应返回{@code true}。
     *  <li>这是<trans> </i>：对于任何非空参考值{@code x},{@code y}和{@code z},如果{@code x.equals }返回{@code true}和{@code y.equals(z)}
     * 返回{@code true},那么{@code x.equals(z)}应该返回{@code true}。
     * <li> <i> reflexive </i>：对于任何非空引用值{@code x},{@code x.equals(x)}应返回{@code true}。
     *  <li>这是<i>一致</i>：对于任何非空引用值{@code x}和{@code y},{@code x.equals(y)}的多次调用始终返回{@ code true}或始终返回{@code false}
     * ,前提是没有在对象的{@code equals}比较中使用的信息被修改。
     * <li> <i> reflexive </i>：对于任何非空引用值{@code x},{@code x.equals(x)}应返回{@code true}。
     *  <li>对于任何非空引用值{@code x},{@code x.equals(null)}应返回{@code false}。
     * </ul>
     * <p>
     *  类{@code Object}的{@code equals}方法实现对象上最有区别的可能的等价关系;也就是说,对于任何非空引用值{@code x}和{@code y},当且仅当{@code x}和{@code y}
     * 引用同一对象时,此方法返回{@code true} ({@code x == y}的值为{@code true})。
     * <p>
     * 注意,每当这个方法被覆盖时,通常需要重写{@code hashCode}方法,以便维护{@code hashCode}方法的一般约定,其中规定相等的对象必须具有相等的哈希码。
     * 
     * 
     * @param   obj   the reference object with which to compare.
     * @return  {@code true} if this object is the same as the obj
     *          argument; {@code false} otherwise.
     * @see     #hashCode()
     * @see     java.util.HashMap
     */
    public boolean equals(Object obj) {
        return (this == obj);
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     * <p>
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     * <p>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     * <p>
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p>
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * <p>
     *  创建并返回此对象的副本。 "复制"的精确含义可能取决于对象的类。一般的意图是,对于任何对象{@code x},表达式：
     * <blockquote>
     * <pre>
     *  x.clone()！= x </pre> </blockquote>将为true,表达式为：
     * <blockquote>
     * <pre>
     *  x.clone()。getClass()== x.getClass()</pre> </blockquote>将是{@code true},但这些不是绝对的要求。虽然通常的情况是：
     * <blockquote>
     * <pre>
     *  x.clone()。equals(x)</pre> </blockquote>将是{@code true},这不是绝对的要求。
     * <p>
     *  按照惯例,返回的对象应该通过调用{@code super.clone}获得。如果一个类及其所有的超类(除了{@code Object})遵循这个约定,将会是{@code x.clone()。
     * getClass()== x.getClass()}的情况。
     * <p>
     * 按照惯例,由此方法返回的对象应该独立于此对象(正在被克隆)。为了实现这种独立性,可能需要在返回之前修改由{@code super.clone}返回的对象的一个​​或多个字段。
     * 通常,这意味着复制包含正在克隆的对象的内部"深层结构"的任何可变对象,并使用对副本的引用替换对这些对象的引用。
     * 如果类只包含原始字段或对不可变对象的引用,那么通常情况下,不需要修改{@code super.clone}返回的对象中的字段。
     * <p>
     *  类{@code Object}的方法{@code clone}执行特定的克隆操作。
     * 首先,如果这个对象的类没有实现接口{@code Cloneable},那么会抛出{@code CloneNotSupportedException}。
     * 请注意,所有数组都被认为实现了接口{@code Cloneable},并且数组类型{@code T []}的{@code clone}方法的返回类型是{@code T []},其中T是任何引用或原语类型
     * 。
     * 首先,如果这个对象的类没有实现接口{@code Cloneable},那么会抛出{@code CloneNotSupportedException}。
     * 否则,此方法将创建此对象的类的一个新实例,并使用此对象的相应字段的内容(如通过赋值)完全初始化其所有字段;字段的内容本身不被克隆。因此,该方法执行该对象的"浅拷贝",而不是"深拷贝"操作。
     * <p>
     * 类{@code Object}本身并不实现{@code Cloneable}接口,因此在类为{@code Object}的对象上调用{@code clone}方法将导致在运行时抛出异常。
     * 
     * 
     * @return     a clone of this instance.
     * @throws  CloneNotSupportedException  if the object's class does not
     *               support the {@code Cloneable} interface. Subclasses
     *               that override the {@code clone} method can also
     *               throw this exception to indicate that an instance cannot
     *               be cloned.
     * @see java.lang.Cloneable
     */
    protected native Object clone() throws CloneNotSupportedException;

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * <p>
     *  返回对象的字符串表示形式。一般来说,{@code toString}方法返回一个"文本表示"此对象的字符串。结果应该是一个简单但翔实的表示,是一个人容易阅读。建议所有子类覆盖此方法。
     * <p>
     *  类{@code Object}的{@code toString}方法返回一个字符串,其中包含对象是实例的类的名称,符号字符"{@code @}"和无符号十六进制表示的对象的哈希码。
     * 换句话说,此方法返回一个等于以下值的字符串：。
     * <blockquote>
     * <pre>
     *  getClass()。getName()+'@'+ Integer.toHexString(hashCode())</pre> </blockquote>
     * 
     * 
     * @return  a string representation of the object.
     */
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    /**
     * Wakes up a single thread that is waiting on this object's
     * monitor. If any threads are waiting on this object, one of them
     * is chosen to be awakened. The choice is arbitrary and occurs at
     * the discretion of the implementation. A thread waits on an object's
     * monitor by calling one of the {@code wait} methods.
     * <p>
     * The awakened thread will not be able to proceed until the current
     * thread relinquishes the lock on this object. The awakened thread will
     * compete in the usual manner with any other threads that might be
     * actively competing to synchronize on this object; for example, the
     * awakened thread enjoys no reliable privilege or disadvantage in being
     * the next thread to lock this object.
     * <p>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. A thread becomes the owner of the
     * object's monitor in one of three ways:
     * <ul>
     * <li>By executing a synchronized instance method of that object.
     * <li>By executing the body of a {@code synchronized} statement
     *     that synchronizes on the object.
     * <li>For objects of type {@code Class,} by executing a
     *     synchronized static method of that class.
     * </ul>
     * <p>
     * Only one thread at a time can own an object's monitor.
     *
     * <p>
     *  唤醒正在等待此对象的监视器的单个线程。如果任何线程正在等待这个对象,其中一个被选择被唤醒。选择是任意的,并且发生在实施的判断。线程通过调用{@code wait}方法之一等待对象的监视器。
     * <p>
     * 唤醒的线程将不能继续,直到当前线程放弃对此对象的锁定。唤醒的线程将以通常的方式与可能主动竞争以在该对象上同步的任何其他线程竞争;例如,被唤醒的线程在下一个线程锁定此对象时没有可靠的特权或缺点。
     * <p>
     *  此方法只应由作为此对象的监视器所有者的线程调用。线程通过以下三种方式之一成为对象的监视器的所有者：
     * <ul>
     *  <li>通过执行该对象的同步实例方法。 <li>通过执行在对象上同步的{@code synchronized}语句的正文。
     *  <li>对于类型为{@code Class,}的对象,通过执行该类的同步静态方法。
     * </ul>
     * <p>
     *  一次只有一个线程可以拥有对象的监视器。
     * 
     * 
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of this object's monitor.
     * @see        java.lang.Object#notifyAll()
     * @see        java.lang.Object#wait()
     */
    public final native void notify();

    /**
     * Wakes up all threads that are waiting on this object's monitor. A
     * thread waits on an object's monitor by calling one of the
     * {@code wait} methods.
     * <p>
     * The awakened threads will not be able to proceed until the current
     * thread relinquishes the lock on this object. The awakened threads
     * will compete in the usual manner with any other threads that might
     * be actively competing to synchronize on this object; for example,
     * the awakened threads enjoy no reliable privilege or disadvantage in
     * being the next thread to lock this object.
     * <p>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the {@code notify} method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * <p>
     *  唤醒在此对象的监视器上等待的所有线程。线程通过调用{@code wait}方法之一等待对象的监视器。
     * <p>
     *  唤醒的线程将无法继续,直到当前线程放弃对此对象的锁定。唤醒的线程将以通常的方式与任何其他可能主动竞争以在该对象上同步的线程竞争;例如,被唤醒的线程在下一个线程中没有可靠的特权或缺点来锁定该对象。
     * <p>
     * 此方法只应由作为此对象的监视器所有者的线程调用。有关线程成为监视器所有者的方式的说明,请参阅{@code notify}方法。
     * 
     * 
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of this object's monitor.
     * @see        java.lang.Object#notify()
     * @see        java.lang.Object#wait()
     */
    public final native void notifyAll();

    /**
     * Causes the current thread to wait until either another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object, or a
     * specified amount of time has elapsed.
     * <p>
     * The current thread must own this object's monitor.
     * <p>
     * This method causes the current thread (call it <var>T</var>) to
     * place itself in the wait set for this object and then to relinquish
     * any and all synchronization claims on this object. Thread <var>T</var>
     * becomes disabled for thread scheduling purposes and lies dormant
     * until one of four things happens:
     * <ul>
     * <li>Some other thread invokes the {@code notify} method for this
     * object and thread <var>T</var> happens to be arbitrarily chosen as
     * the thread to be awakened.
     * <li>Some other thread invokes the {@code notifyAll} method for this
     * object.
     * <li>Some other thread {@linkplain Thread#interrupt() interrupts}
     * thread <var>T</var>.
     * <li>The specified amount of real time has elapsed, more or less.  If
     * {@code timeout} is zero, however, then real time is not taken into
     * consideration and the thread simply waits until notified.
     * </ul>
     * The thread <var>T</var> is then removed from the wait set for this
     * object and re-enabled for thread scheduling. It then competes in the
     * usual manner with other threads for the right to synchronize on the
     * object; once it has gained control of the object, all its
     * synchronization claims on the object are restored to the status quo
     * ante - that is, to the situation as of the time that the {@code wait}
     * method was invoked. Thread <var>T</var> then returns from the
     * invocation of the {@code wait} method. Thus, on return from the
     * {@code wait} method, the synchronization state of the object and of
     * thread {@code T} is exactly as it was when the {@code wait} method
     * was invoked.
     * <p>
     * A thread can also wake up without being notified, interrupted, or
     * timing out, a so-called <i>spurious wakeup</i>.  While this will rarely
     * occur in practice, applications must guard against it by testing for
     * the condition that should have caused the thread to be awakened, and
     * continuing to wait if the condition is not satisfied.  In other words,
     * waits should always occur in loops, like this one:
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait(timeout);
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * (For more information on this topic, see Section 3.2.3 in Doug Lea's
     * "Concurrent Programming in Java (Second Edition)" (Addison-Wesley,
     * 2000), or Item 50 in Joshua Bloch's "Effective Java Programming
     * Language Guide" (Addison-Wesley, 2001).
     *
     * <p>If the current thread is {@linkplain java.lang.Thread#interrupt()
     * interrupted} by any thread before or while it is waiting, then an
     * {@code InterruptedException} is thrown.  This exception is not
     * thrown until the lock status of this object has been restored as
     * described above.
     *
     * <p>
     * Note that the {@code wait} method, as it places the current thread
     * into the wait set for this object, unlocks only this object; any
     * other objects on which the current thread may be synchronized remain
     * locked while the thread waits.
     * <p>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the {@code notify} method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * <p>
     *  使当前线程等待,直到另一个线程为此对象调用{@link java.lang.Object#notify()}方法或{@link java.lang.Object#notifyAll()}方法,或指定的
     * 数量的时间。
     * <p>
     *  当前线程必须拥有此对象的监视器。
     * <p>
     *  此方法使当前线程(称为<var> T </var>)将自身置于此对象的等待集中,然后放弃对此对象的任何和所有同步声明。
     * 线程<var> T </var>被禁用用于线程调度目的,并处于休眠状态,直到发生以下四种情况之一：。
     * <ul>
     *  <li>一些其他线程调用此对象的{@code notify}方法,线程<var> T </var>恰好被任意选择为要唤醒的线程。
     *  <li>某些其他线程调用此对象的{@code notifyAll}方法。 <li>一些其他线程{@linkplain线程#interrupt()中断}线程<var> T </var>。
     *  <li>指定的实时时间已过,或多或少。如果{@code timeout}为零,然而,实时没有被考虑,线程只是等待直到通知。
     * </ul>
     * 线程<var> T </var>然后从此对象的等待集中删除,并重新启用线程调度。
     * 然后它以通常的方式与其他线程竞争对象上的同步权;一旦它获得对对象的控制权,对象上的所有同步声明就恢复到状态,即到{@code wait}方法被调用时的情况。
     * 线程<var> T </var>然后从调用{@code wait}方法返回。
     * 因此,从{@code wait}方法返回时,对象和线程{@code T}的同步状态与调用{@code wait}方法时的情况完全相同。
     * <p>
     *  线程也可以在没有被通知,中断或超时的情况下唤醒,即所谓的"虚假唤醒"。虽然这在实践中很少发生,但应用程序必须通过测试应该已经导致线程被唤醒的条件来防止它,并且如果条件不满足,则继续等待。
     * 换句话说,等待应该总是发生在循环中,像这样：。
     * <pre>
     *  synchronized(obj){while(&lt;条件不成立&gt;)obj.wait(timeout); ... //执行适合条件的动作}
     * </pre>
     *  (有关此主题的更多信息,请参阅Doug Lea的"Concurrent Programming in Java(第二版)"(Addison-Wesley,2000)中的第3.2.3节或Joshua B
     * loch的"Effective Java Programming Language Guide"(Addison- Wesley,2001)。
     * 
     * <p>如果当前线程是{@linkplain java.lang.Thread#interrupt()中断}由任何线程之前或当它在等待,然后一个{@code InterruptedException}被抛
     * 出。
     * 在此对象的锁定状态已恢复之前,不会抛出此异常,如上所述。
     * 
     * <p>
     *  请注意,{@code wait}方法将当前线程放入此对象的等待集中,只解锁此对象;当前线程可以同步的任何其他对象在线程等待时保持锁定。
     * <p>
     *  此方法只应由作为此对象的监视器所有者的线程调用。有关线程成为监视器所有者的方式的说明,请参阅{@code notify}方法。
     * 
     * 
     * @param      timeout   the maximum time to wait in milliseconds.
     * @throws  IllegalArgumentException      if the value of timeout is
     *               negative.
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of the object's monitor.
     * @throws  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     * @see        java.lang.Object#notify()
     * @see        java.lang.Object#notifyAll()
     */
    public final native void wait(long timeout) throws InterruptedException;

    /**
     * Causes the current thread to wait until another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object, or
     * some other thread interrupts the current thread, or a certain
     * amount of real time has elapsed.
     * <p>
     * This method is similar to the {@code wait} method of one
     * argument, but it allows finer control over the amount of time to
     * wait for a notification before giving up. The amount of real time,
     * measured in nanoseconds, is given by:
     * <blockquote>
     * <pre>
     * 1000000*timeout+nanos</pre></blockquote>
     * <p>
     * In all other respects, this method does the same thing as the
     * method {@link #wait(long)} of one argument. In particular,
     * {@code wait(0, 0)} means the same thing as {@code wait(0)}.
     * <p>
     * The current thread must own this object's monitor. The thread
     * releases ownership of this monitor and waits until either of the
     * following two conditions has occurred:
     * <ul>
     * <li>Another thread notifies threads waiting on this object's monitor
     *     to wake up either through a call to the {@code notify} method
     *     or the {@code notifyAll} method.
     * <li>The timeout period, specified by {@code timeout}
     *     milliseconds plus {@code nanos} nanoseconds arguments, has
     *     elapsed.
     * </ul>
     * <p>
     * The thread then waits until it can re-obtain ownership of the
     * monitor and resumes execution.
     * <p>
     * As in the one argument version, interrupts and spurious wakeups are
     * possible, and this method should always be used in a loop:
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait(timeout, nanos);
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the {@code notify} method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * <p>
     *  使当前线程等待,直到另一个线程调用此对象的{@link java.lang.Object#notify()}方法或{@link java.lang.Object#notifyAll()}方法,或其他线
     * 程中断当前线程或一定量的实时时间已经过去。
     * <p>
     *  此方法类似于一个参数的{@code wait}方法,但它允许更精细地控制在放弃之前等待通知的时间量。实时时间的量,以纳秒为单位,由下式给出：
     * <blockquote>
     * <pre>
     *  1000000 * timeout + nanos </pre> </blockquote>
     * <p>
     *  在所有其他方面,此方法与一个参数的方法{@link #wait(long)}相同。特别是,{@code wait(0,0)}的含义与{@code wait(0)}相同。
     * <p>
     * 当前线程必须拥有此对象的监视器。线程释放此监视器的所有权,并等待,直到发生以下两个条件之一：
     * <ul>
     *  <li>另一个线程通过调用{@code notify}方法或{@code notifyAll}方法来通知等待此对象的监视器的线程以进行唤醒。
     *  <li>由{@code timeout}毫秒加上{@code nanos}纳秒参数指定的超时期已过。
     * </ul>
     * <p>
     *  线程然后等待,直到它可以重新获得监视器的所有权并恢复执行。
     * <p>
     *  在一个参数版本中,中断和伪唤醒是可能的,并且这种方法应该总是在循环中使用：
     * <pre>
     *  synchronized(obj){while(&lt; condition does not hold&gt;)obj.wait(timeout,nanos); ... //执行适合条件的动作}
     * </pre>
     *  此方法只应由作为此对象的监视器所有者的线程调用。有关线程成为监视器所有者的方式的说明,请参阅{@code notify}方法。
     * 
     * 
     * @param      timeout   the maximum time to wait in milliseconds.
     * @param      nanos      additional time, in nanoseconds range
     *                       0-999999.
     * @throws  IllegalArgumentException      if the value of timeout is
     *                      negative or the value of nanos is
     *                      not in the range 0-999999.
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of this object's monitor.
     * @throws  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     */
    public final void wait(long timeout, int nanos) throws InterruptedException {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos >= 500000 || (nanos != 0 && timeout == 0)) {
            timeout++;
        }

        wait(timeout);
    }

    /**
     * Causes the current thread to wait until another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object.
     * In other words, this method behaves exactly as if it simply
     * performs the call {@code wait(0)}.
     * <p>
     * The current thread must own this object's monitor. The thread
     * releases ownership of this monitor and waits until another thread
     * notifies threads waiting on this object's monitor to wake up
     * either through a call to the {@code notify} method or the
     * {@code notifyAll} method. The thread then waits until it can
     * re-obtain ownership of the monitor and resumes execution.
     * <p>
     * As in the one argument version, interrupts and spurious wakeups are
     * possible, and this method should always be used in a loop:
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait();
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the {@code notify} method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * <p>
     *  使当前线程等待,直到另一个线程调用此对象的{@link java.lang.Object#notify()}方法或{@link java.lang.Object#notifyAll()}方法。
     * 换句话说,这个方法的行为就好像它只是执行调用{@code wait(0)}。
     * <p>
     * 当前线程必须拥有此对象的监视器。线程释放此监视器的所有权,并等待另一个线程通知等待此对象的监视器的线程通过调用{@code notify}方法或{@code notifyAll}方法唤醒。
     * 线程然后等待,直到它可以重新获得监视器的所有权并恢复执行。
     * <p>
     *  在一个参数版本中,中断和伪唤醒是可能的,并且这种方法应该总是在循环中使用：
     * <pre>
     *  synchronized(obj){while(&lt; condition not hold&gt;)obj.wait(); ... //执行适合条件的动作}
     * </pre>
     *  此方法只应由作为此对象的监视器所有者的线程调用。有关线程成为监视器所有者的方式的说明,请参阅{@code notify}方法。
     * 
     * 
     * @throws  IllegalMonitorStateException  if the current thread is not
     *               the owner of the object's monitor.
     * @throws  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     * @see        java.lang.Object#notify()
     * @see        java.lang.Object#notifyAll()
     */
    public final void wait() throws InterruptedException {
        wait(0);
    }

    /**
     * Called by the garbage collector on an object when garbage collection
     * determines that there are no more references to the object.
     * A subclass overrides the {@code finalize} method to dispose of
     * system resources or to perform other cleanup.
     * <p>
     * The general contract of {@code finalize} is that it is invoked
     * if and when the Java&trade; virtual
     * machine has determined that there is no longer any
     * means by which this object can be accessed by any thread that has
     * not yet died, except as a result of an action taken by the
     * finalization of some other object or class which is ready to be
     * finalized. The {@code finalize} method may take any action, including
     * making this object available again to other threads; the usual purpose
     * of {@code finalize}, however, is to perform cleanup actions before
     * the object is irrevocably discarded. For example, the finalize method
     * for an object that represents an input/output connection might perform
     * explicit I/O transactions to break the connection before the object is
     * permanently discarded.
     * <p>
     * The {@code finalize} method of class {@code Object} performs no
     * special action; it simply returns normally. Subclasses of
     * {@code Object} may override this definition.
     * <p>
     * The Java programming language does not guarantee which thread will
     * invoke the {@code finalize} method for any given object. It is
     * guaranteed, however, that the thread that invokes finalize will not
     * be holding any user-visible synchronization locks when finalize is
     * invoked. If an uncaught exception is thrown by the finalize method,
     * the exception is ignored and finalization of that object terminates.
     * <p>
     * After the {@code finalize} method has been invoked for an object, no
     * further action is taken until the Java virtual machine has again
     * determined that there is no longer any means by which this object can
     * be accessed by any thread that has not yet died, including possible
     * actions by other objects or classes which are ready to be finalized,
     * at which point the object may be discarded.
     * <p>
     * The {@code finalize} method is never invoked more than once by a Java
     * virtual machine for any given object.
     * <p>
     * Any exception thrown by the {@code finalize} method causes
     * the finalization of this object to be halted, but is otherwise
     * ignored.
     *
     * <p>
     *  当垃圾收集器确定没有对该对象的更多引用时,由垃圾收集器在对象上调用。子类覆盖{@code finalize}方法以处理系统资源或执行其他清理。
     * <p>
     * {@code finalize}的一般合同是,如果和时间Java和贸易;虚拟机已经确定不再有任何装置,通过该装置,通过尚未死亡的任何线程可以访问该对象,除非由于准备好最终确定的一些其他对象或类的完成所采
     * 取的动作的结果。
     *  {@code finalize}方法可以采取任何操作,包括使此对象再次可用于其他线程;然而,{@code finalize}的通常目的是在对象被不可撤销地丢弃之前执行清除动作。
     * 例如,表示输入/输出连接的对象的finalize方法可能执行显式I / O事务以在永久丢弃对象之前中断连接。
     * <p>
     *  类{@code Object}的{@code finalize}方法不执行任何特殊操作;它只是简单地返回正常。 {@code Object}的子类可以覆盖此定义。
     * <p>
     *  Java编程语言不保证哪个线程将为任何给定的对象调用{@code finalize}方法。但是,确保调用finalize的线程在调用finalize时不会持有任何用户可见的同步锁。
     * 
     * @throws Throwable the {@code Exception} raised by this method
     * @see java.lang.ref.WeakReference
     * @see java.lang.ref.PhantomReference
     * @jls 12.6 Finalization of Class Instances
     */
    protected void finalize() throws Throwable { }
}

/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.print.attribute.standard;

import java.util.Collection;
import java.util.HashSet;

import javax.print.attribute.Attribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class JobStateReasons is a printing attribute class, a set of enumeration
 * values, that provides additional information about the job's current state,
 * i.e., information that augments the value of the job's {@link JobState
 * JobState} attribute.
 * <P>
 * Instances of {@link JobStateReason JobStateReason} do not appear in a Print
 * Job's attribute set directly. Rather, a JobStateReasons attribute appears in
 * the Print Job's attribute set. The JobStateReasons attribute contains zero,
 * one, or more than one {@link JobStateReason JobStateReason} objects which
 * pertain to the Print Job's status. The printer adds a {@link JobStateReason
 * JobStateReason} object to the Print Job's JobStateReasons attribute when the
 * corresponding condition becomes true of the Print Job, and the printer
 * removes the {@link JobStateReason JobStateReason} object again when the
 * corresponding condition becomes false, regardless of whether the Print Job's
 * overall {@link JobState JobState} also changed.
 * <P>
 * Class JobStateReasons inherits its implementation from class {@link
 * java.util.HashSet java.util.HashSet}. Unlike most printing attributes which
 * are immutable once constructed, class JobStateReasons is designed to be
 * mutable; you can add {@link JobStateReason JobStateReason} objects to an
 * existing JobStateReasons object and remove them again. However, like class
 * {@link java.util.HashSet java.util.HashSet}, class JobStateReasons is not
 * multiple thread safe. If a JobStateReasons object will be used by multiple
 * threads, be sure to synchronize its operations (e.g., using a synchronized
 * set view obtained from class {@link java.util.Collections
 * java.util.Collections}).
 * <P>
 * <B>IPP Compatibility:</B> The string value returned by each individual {@link
 * JobStateReason JobStateReason} object's <CODE>toString()</CODE> method gives
 * the IPP keyword value. The category name returned by <CODE>getName()</CODE>
 * gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  JobStateReasons类是一个打印属性类,一组枚举值,提供有关作业当前状态的附加信息,即增加作业{@link JobState JobState}属性值的信息。
 * <P>
 *  {@link JobStateReason JobStateReason}的实例不会直接显示在打印作业的属性集中。相反,JobStateReasons属性显示在打印作业的属性集中。
 *  JobStateReasons属性包含与打印作业的状态相关的零个,一个或多个{@link JobStateReason JobStateReason}对象。
 * 当相应条件变为真实的打印作业时,打印机向打印作业的JobStateReasons属性添加一个{@link JobStateReason JobStateReason}对象,并且当相应条件变为假时,打印机
 * 再次删除{@link JobStateReason JobStateReason}对象,而不管打印作业的整体{@link JobState JobState}是否也更改。
 *  JobStateReasons属性包含与打印作业的状态相关的零个,一个或多个{@link JobStateReason JobStateReason}对象。
 * <P>
 * JobStateReasons类继承自{@link java.util.HashSet java.util.HashSet}类的实现。
 * 与大多数打印属性不同,大多数打印属性一旦构造,类JobStateReasons设计为可变的;您可以向现有的JobStateReasons对象添加{@link JobStateReason JobStateReason}
 * 对象,然后再次删除它们。
 * JobStateReasons类继承自{@link java.util.HashSet java.util.HashSet}类的实现。
 * 但是,像类{@link java.util.HashSet java.util.HashSet},类JobStateReasons不是多线程安全的。
 * 如果多个线程将使用JobStateReasons对象,请确保同步其操作(例如,使用从类{@link java.util.Collections java.util.Collections}获取的同步集视
 * 图)。
 * 但是,像类{@link java.util.HashSet java.util.HashSet},类JobStateReasons不是多线程安全的。
 * <P>
 *  <B> IPP兼容性：</B>每个{@link JobStateReason JobStateReason}对象的<CODE> toString()</CODE>方法返回的字符串值给出了IPP关键字值
 * 。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class JobStateReasons
    extends HashSet<JobStateReason> implements PrintJobAttribute {

    private static final long serialVersionUID = 8849088261264331812L;

    /**
     * Construct a new, empty job state reasons attribute; the underlying hash
     * set has the default initial capacity and load factor.
     * <p>
     *  构造一个新的,空的作业状态原因属性;底层哈希集具有默认的初始容量和负载因子。
     * 
     */
    public JobStateReasons() {
        super();
    }

    /**
     * Construct a new, empty job state reasons attribute; the underlying hash
     * set has the given initial capacity and the default load factor.
     *
     * <p>
     *  构造一个新的,空的作业状态原因属性;底层哈希集具有给定的初始容量和默认负载因子。
     * 
     * 
     * @param  initialCapacity  Initial capacity.
     * @throws IllegalArgumentException if the initial capacity is less
     *     than zero.
     */
    public JobStateReasons(int initialCapacity) {
        super (initialCapacity);
    }

    /**
     * Construct a new, empty job state reasons attribute; the underlying hash
     * set has the given initial capacity and load factor.
     *
     * <p>
     *  构造一个新的,空的作业状态原因属性;底层哈希集具有给定的初始容量和负载因子。
     * 
     * 
     * @param  initialCapacity  Initial capacity.
     * @param  loadFactor       Load factor.
     * @throws IllegalArgumentException if the initial capacity is less
     *     than zero.
     */
    public JobStateReasons(int initialCapacity, float loadFactor) {
        super (initialCapacity, loadFactor);
    }

    /**
     * Construct a new job state reasons attribute that contains the same
     * {@link JobStateReason JobStateReason} objects as the given collection.
     * The underlying hash set's initial capacity and load factor are as
     * specified in the superclass constructor {@link
     * java.util.HashSet#HashSet(java.util.Collection)
     * HashSet(Collection)}.
     *
     * <p>
     * 构造包含与给定集合相同的{@link JobStateReason JobStateReason}对象的新作业状态原因属性。
     * 底层哈希集的初始容量和负载因子是在超类构造函数{@link java.util.HashSet#HashSet(java.util.Collection)HashSet(Collection)}中指定的
     * 。
     * 构造包含与给定集合相同的{@link JobStateReason JobStateReason}对象的新作业状态原因属性。
     * 
     * 
     * @param  collection  Collection to copy.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>collection</CODE> is null or
     *     if any element in <CODE>collection</CODE> is null.
     * @throws  ClassCastException
     *     (unchecked exception) Thrown if any element in
     *     <CODE>collection</CODE> is not an instance of class {@link
     *     JobStateReason JobStateReason}.
     */
   public JobStateReasons(Collection<JobStateReason> collection) {
       super (collection);
   }

    /**
     * Adds the specified element to this job state reasons attribute if it is
     * not already present. The element to be added must be an instance of class
     * {@link JobStateReason JobStateReason}. If this job state reasons
     * attribute already contains the specified element, the call leaves this
     * job state reasons attribute unchanged and returns <tt>false</tt>.
     *
     * <p>
     *  将指定的元素添加到此作业状态reason属性(如果它尚未存在)。要添加的元素必须是类{@link JobStateReason JobStateReason}的实例。
     * 如果此作业状态原因属性已包含指定的元素,则调用将此作业状态reason属性保持不变,并返回<tt> false </tt>。
     * 
     * 
     * @param  o  Element to be added to this job state reasons attribute.
     *
     * @return  <tt>true</tt> if this job state reasons attribute did not
     *          already contain the specified element.
     *
     * @throws  NullPointerException
     *     (unchecked exception) Thrown if the specified element is null.
     * @throws  ClassCastException
     *     (unchecked exception) Thrown if the specified element is not an
     *     instance of class {@link JobStateReason JobStateReason}.
     * @since 1.5
     */
    public boolean add(JobStateReason o) {
        if (o == null) {
            throw new NullPointerException();
        }
        return super.add ((JobStateReason) o);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobStateReasons, the category is class JobStateReasons itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobStateReasons类,类别是JobStateReasons类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobStateReasons.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobStateReasons, the category
     * name is <CODE>"job-state-reasons"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-state-reasons";
    }

}

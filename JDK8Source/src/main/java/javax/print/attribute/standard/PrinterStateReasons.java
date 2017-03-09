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

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Set;

import javax.print.attribute.Attribute;
import javax.print.attribute.PrintServiceAttribute;

/**
 * Class PrinterStateReasons is a printing attribute class, a set of
 * enumeration values, that provides additional information about the
 * printer's current state, i.e., information that augments the value of the
 * printer's {@link PrinterState PrinterState} attribute.
 * <P>
 * Instances of {@link PrinterStateReason PrinterStateReason} do not appear in
 *  a Print Service's attribute set directly. Rather, a PrinterStateReasons
 * attribute appears in the Print Service's attribute set. The
 * PrinterStateReasons attribute contains zero, one, or more than one {@link
 * PrinterStateReason PrinterStateReason} objects which pertain to the Print
 * Service's status, and each {@link PrinterStateReason PrinterStateReason}
 * object is associated with a {@link Severity Severity} level of REPORT
 *  (least severe), WARNING, or ERROR (most severe). The printer adds a {@link
 * PrinterStateReason PrinterStateReason} object to the Print Service's
 * PrinterStateReasons attribute when the corresponding condition becomes true
 * of the printer, and the printer removes the {@link PrinterStateReason
 * PrinterStateReason} object again when the corresponding condition becomes
 * false, regardless of whether the Print Service's overall
 * {@link PrinterState PrinterState} also changed.
 * <P>
 * Class PrinterStateReasons inherits its implementation from class {@link
 * java.util.HashMap java.util.HashMap}. Each entry in the map consists of a
 * {@link PrinterStateReason PrinterStateReason} object (key) mapping to a
 * {@link Severity Severity} object (value):
 * <P>
 * Unlike most printing attributes which are immutable once constructed, class
 * PrinterStateReasons is designed to be mutable; you can add {@link
 * PrinterStateReason PrinterStateReason} objects to an existing
 * PrinterStateReasons object and remove them again. However, like class
 *  {@link java.util.HashMap java.util.HashMap}, class PrinterStateReasons is
 * not multiple thread safe. If a PrinterStateReasons object will be used by
 * multiple threads, be sure to synchronize its operations (e.g., using a
 * synchronized map view obtained from class {@link java.util.Collections
 * java.util.Collections}).
 * <P>
 * <B>IPP Compatibility:</B> The string values returned by each individual
 * {@link PrinterStateReason PrinterStateReason} object's and the associated
 * {@link Severity Severity} object's <CODE>toString()</CODE> methods,
 * concatenated
 * together with a hyphen (<CODE>"-"</CODE>) in between, gives the IPP keyword
 * value. The category name returned by <CODE>getName()</CODE> gives the IPP
 * attribute name.
 * <P>
 *
 * <p>
 *  类PrinterStateReasons是一个打印属性类,一组枚举值,提供有关打印机当前状态的附加信息,即增加打印机{@link PrinterState PrinterState}属性值的信息。
 * <P>
 *  {@link PrinterStateReason PrinterStateReason}的实例不会直接显示在打印服务的属性集中。
 * 相反,PrinterStateReasons属性显示在打印服务的属性集中。
 *  PrinterStateReasons属性包含与打印服务状态相关的零个,一个或多个{@link PrinterStateReason PrinterStateReason}对象,每个{@link PrinterStateReason PrinterStateReason}
 * 对象与REPORT的{@link严重性严重性}级别相关联最不严重),WARNING或ERROR(最严重)。
 * 相反,PrinterStateReasons属性显示在打印服务的属性集中。
 * 当相应条件成为打印机的相应条件时,打印机向打印服务的PrinterStateReasons属性添加{@link PrinterStateReason PrinterStateReason}对象,并且当相
 * 应条件变为false时,打印机再次删除{@link PrinterStateReason PrinterStateReason}对象,而不管是否打印服务的整体{@link PrinterState PrinterState}
 * 也更改了。
 * 相反,PrinterStateReasons属性显示在打印服务的属性集中。
 * <P>
 * 类PrinterStateReasons从类{@link java.util.HashMap java.util.HashMap}继承其实现。
 * 地图中的每个条目都由{@link PrinterStateReason PrinterStateReason}对象(键)映射到{@link严重性严重性}对象(值)构成：。
 * <P>
 *  与大多数打印属性不同,大多数打印属性一旦构造,类PrinterStateReasons设计为可变的;您可以向现有的PrinterStateReasons对象添加{@link PrinterStateReason PrinterStateReason}
 * 对象,然后再次删除它们。
 * 但是,像类{@link java.util.HashMap java.util.HashMap},类PrinterStateReasons不是多线程安全的。
 * 如果一个PrinterStateReasons对象将被多个线程使用,请确保同步其操作(例如,使用从类{@link java.util.Collections java.util.Collections}
 * 获取的同步映射视图)。
 * 但是,像类{@link java.util.HashMap java.util.HashMap},类PrinterStateReasons不是多线程安全的。
 * <P>
 *  <B> IPP兼容性：</B>每个{@link PrinterStateReason PrinterStateReason}对象和关联的{@link严重性严重性}对象的<CODE> toString(
 * )</CODE>方法返回的字符串值与连字符(<CODE>" - "</CODE>)",给出IPP关键字值。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class PrinterStateReasons
    extends HashMap<PrinterStateReason,Severity>
    implements PrintServiceAttribute
{

    private static final long serialVersionUID = -3731791085163619457L;

    /**
     * Construct a new, empty printer state reasons attribute; the underlying
     * hash map has the default initial capacity and load factor.
     * <p>
     *  构造一个新的,空的打印机状态原因属性;底层哈希映射具有默认的初始容量和负载因子。
     * 
     */
    public PrinterStateReasons() {
        super();
    }

    /**
     * super a new, empty printer state reasons attribute; the underlying
     * hash map has the given initial capacity and the default load factor.
     *
     * <p>
     * 超级新的,空打印机状态原因属性;底层哈希图具有给定的初始容量和默认负载因子。
     * 
     * 
     * @param  initialCapacity  Initial capacity.
     *
     * @throws IllegalArgumentException if the initial capacity is less
     *     than zero.
     */
    public PrinterStateReasons(int initialCapacity) {
        super (initialCapacity);
    }

    /**
     * Construct a new, empty printer state reasons attribute; the underlying
     * hash map has the given initial capacity and load factor.
     *
     * <p>
     *  构造一个新的,空的打印机状态原因属性;底层哈希图具有给定的初始容量和负载因子。
     * 
     * 
     * @param  initialCapacity  Initial capacity.
     * @param  loadFactor       Load factor.
     *
     * @throws IllegalArgumentException if the initial capacity is less
     *     than zero.
     */
    public PrinterStateReasons(int initialCapacity, float loadFactor) {
        super (initialCapacity, loadFactor);
    }

    /**
     * Construct a new printer state reasons attribute that contains the same
     * {@link PrinterStateReason PrinterStateReason}-to-{@link Severity
     * Severity} mappings as the given map. The underlying hash map's initial
     * capacity and load factor are as specified in the superclass constructor
     * {@link java.util.HashMap#HashMap(java.util.Map)
     * HashMap(Map)}.
     *
     * <p>
     *  构造包含与给定地图相同的{@link PrinterStateReason PrinterStateReason} --to  -  {@ link Severity Severity}映射的新打印机
     * 状态reason属性。
     * 底层哈希图的初始容量和负载因子是在超类构造函数{@link java.util.HashMap#HashMap(java.util.Map)HashMap(Map)}中指定的。
     * 
     * 
     * @param  map  Map to copy.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>map</CODE> is null or if any
     *     key or value in <CODE>map</CODE> is null.
     * @throws  ClassCastException
     *     (unchecked exception) Thrown if any key in <CODE>map</CODE> is not
     *   an instance of class {@link PrinterStateReason PrinterStateReason} or
     *     if any value in <CODE>map</CODE> is not an instance of class
     *     {@link Severity Severity}.
     */
    public PrinterStateReasons(Map<PrinterStateReason,Severity> map) {
        this();
        for (Map.Entry<PrinterStateReason,Severity> e : map.entrySet())
            put(e.getKey(), e.getValue());
    }

    /**
     * Adds the given printer state reason to this printer state reasons
     * attribute, associating it with the given severity level. If this
     * printer state reasons attribute previously contained a mapping for the
     * given printer state reason, the old value is replaced.
     *
     * <p>
     *  将给定的打印机状态原因添加到此打印机状态reason属性,将其与给定的严重性级别相关联。如果此打印机状态原因属性先前包含给定打印机状态原因的映射,则旧值被替换。
     * 
     * 
     * @param  reason    Printer state reason. This must be an instance of
     *                    class {@link PrinterStateReason PrinterStateReason}.
     * @param  severity  Severity of the printer state reason. This must be
     *                      an instance of class {@link Severity Severity}.
     *
     * @return  Previous severity associated with the given printer state
     *          reason, or <tt>null</tt> if the given printer state reason was
     *          not present.
     *
     * @throws  NullPointerException
     *     (unchecked exception) Thrown if <CODE>reason</CODE> is null or
     *     <CODE>severity</CODE> is null.
     * @throws  ClassCastException
     *     (unchecked exception) Thrown if <CODE>reason</CODE> is not an
     *   instance of class {@link PrinterStateReason PrinterStateReason} or if
     *     <CODE>severity</CODE> is not an instance of class {@link Severity
     *     Severity}.
     * @since 1.5
     */
    public Severity put(PrinterStateReason reason, Severity severity) {
        if (reason == null) {
            throw new NullPointerException("reason is null");
        }
        if (severity == null) {
            throw new NullPointerException("severity is null");
        }
        return super.put(reason, severity);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterStateReasons, the
     * category is class PrinterStateReasons itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterStateReasons类,类别是PrinterStateReasons类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterStateReasons.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterStateReasons, the
     * category name is <CODE>"printer-state-reasons"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     *  对于PrinterStateReasons类,类别名称为<CODE>"printer-state-reasons"</CODE>。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-state-reasons";
    }

    /**
     * Obtain an unmodifiable set view of the individual printer state reason
     * attributes at the given severity level in this PrinterStateReasons
     * attribute. Each element in the set view is a {@link PrinterStateReason
     * PrinterStateReason} object. The only elements in the set view are the
     * {@link PrinterStateReason PrinterStateReason} objects that map to the
     * given severity value. The set view is backed by this
     * PrinterStateReasons attribute, so changes to this PrinterStateReasons
     * attribute are reflected  in the set view.
     * The set view does not support element insertion or
     * removal. The set view's iterator does not support element removal.
     *
     * <p>
     * 在此PrinterStateReasons属性中的给定严重性级别获取单个打印机状态原因属性的不可修改集视图。
     * 集合视图中的每个元素都是{@link PrinterStateReason PrinterStateReason}对象。
     * 集合视图中的唯一元素是映射到给定严重性值的{@link PrinterStateReason PrinterStateReason}对象。
     * 
     * @param  severity  Severity level.
     *
     * @return  Set view of the individual {@link PrinterStateReason
     *          PrinterStateReason} attributes at the given {@link Severity
     *          Severity} level.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>severity</CODE> is null.
     */
    public Set<PrinterStateReason> printerStateReasonSet(Severity severity) {
        if (severity == null) {
            throw new NullPointerException("severity is null");
        }
        return new PrinterStateReasonSet (severity, entrySet());
    }

    private class PrinterStateReasonSet
        extends AbstractSet<PrinterStateReason>
    {
        private Severity mySeverity;
        private Set myEntrySet;

        public PrinterStateReasonSet(Severity severity, Set entrySet) {
            mySeverity = severity;
            myEntrySet = entrySet;
        }

        public int size() {
            int result = 0;
            Iterator iter = iterator();
            while (iter.hasNext()) {
                iter.next();
                ++ result;
            }
            return result;
        }

        public Iterator iterator() {
            return new PrinterStateReasonSetIterator(mySeverity,
                                                     myEntrySet.iterator());
        }
    }

    private class PrinterStateReasonSetIterator implements Iterator {
        private Severity mySeverity;
        private Iterator myIterator;
        private Map.Entry myEntry;

        public PrinterStateReasonSetIterator(Severity severity,
                                             Iterator iterator) {
            mySeverity = severity;
            myIterator = iterator;
            goToNext();
        }

        private void goToNext() {
            myEntry = null;
            while (myEntry == null && myIterator.hasNext()) {
                myEntry = (Map.Entry) myIterator.next();
                if ((Severity) myEntry.getValue() != mySeverity) {
                    myEntry = null;
                }
            }
        }

        public boolean hasNext() {
            return myEntry != null;
        }

        public Object next() {
            if (myEntry == null) {
                throw new NoSuchElementException();
            }
            Object result = myEntry.getKey();
            goToNext();
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

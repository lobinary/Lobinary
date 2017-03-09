/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

import java.util.Vector;
import java.util.Enumeration;

/**
 * Contains a list of <CODE>SnmpVarBind</CODE> objects.
 * This class helps to create an <CODE>SnmpVarBindList</CODE> from a list of MIB variable names.
 * In addition, it contains different forms of methods which can copy or clone the list.
 * This list is required by any SNMP entity which specifies a list of variables to query.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  包含<CODE> SnmpVarBind </CODE>对象的列表。此类有助于从MIB变量名列表创建<CODE> SnmpVarBindList </CODE>。
 * 此外,它包含可以复制或克隆列表的不同形式的方法。此列表是指定要查询的变量列表的任何SNMP实体所必需的。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。
 * </b> </p>。
 * 
 */

public class SnmpVarBindList extends Vector<SnmpVarBind> {
    private static final long serialVersionUID = -7203997794636430321L;

    /**
     * A name given to the <CODE>SnmpVarBindList</CODE>. Useful for debugging.
     * The default name is "VarBindList".
     * <p>
     *  给予<CODE> SnmpVarBindList </CODE>的名称。用于调试。默认名称为"VarBindList"。
     * 
     */
    public String identity = "VarBindList " ;   // name identifying this list.

    /**
     * Timestamp when this <CODE>SnmpVarBindList</CODE> was updated.
     * Valid only for <CODE>SnmpGet</CODE> and <CODE>SnmpGetNext</CODE> operations.
     * <CODE>SnmpTimestamp</CODE> is null by default.
     * Also, when the list is cloned without value the timestamp is not copied.
     * <p>
     *  更新此<CODE> SnmpVarBindList </CODE>时的时间戳记。仅对<CODE> SnmpGet </CODE>和<CODE> SnmpGetNext </CODE>操作有效。
     * 默认情况下,<CODE> SnmpTimestamp </CODE>为null。此外,当克隆列表没有值时,不复制时间戳。
     * 
     */
    Timestamp timestamp ;


    // CONSTRUCTORS
    //-------------

    /**
     * Prepares an empty list.
     * The initial capacity and the capacity increment are initialized to 5.
     * <p>
     *  准备一个空列表。初始容量和容量增量初始化为5。
     * 
     */
    public SnmpVarBindList() {
        super(5, 5) ;
    }

    /**
     * Prepares an empty list.
     * <p>
     *  准备一个空列表。
     * 
     * 
     * @param initialCapacity The initial capacity of the <CODE>SnmpVarBindList</CODE>.
     */
    public SnmpVarBindList(int initialCapacity) {
        super(initialCapacity) ;
    }

    /**
     * Prepares an empty list with a <CODE>String</CODE> to print while debugging.
     * <p>
     *  使用<CODE>字符串</CODE>准备一个空列表,以便在调试时打印。
     * 
     * 
     * @param name The name of the newly created <CODE>SnmpVarBindList</CODE>.
     */
    public SnmpVarBindList(String name) {
        super(5, 5) ;
        identity = name ;
    }

    /**
     * Similar to the copy constructor. Does a shallow copy of the elements.
     * Individual elements are not cloned.
     * <p>
     *  类似于复制构造函数。做一个浅拷贝的元素。不克隆单个元素。
     * 
     * 
     * @param list The <CODE>SnmpVarBindList</CODE> to copy.
     */
    public SnmpVarBindList(SnmpVarBindList list) {
        super(list.size(), 5) ;
        list.copyInto(elementData) ;
        elementCount = list.size() ;
    }

    /**
     * Creates a new <CODE>SnmpVarBindList</CODE> object from a plain vector of <CODE>SnmpVarBind</CODE> objects.
     * Objects in the specified vector can be <CODE>SnmpVarBind</CODE> objects or derivatives.
     * <p>
     *  从<CODE> SnmpVarBind </CODE>对象的平面向量创建一个新的<CODE> SnmpVarBindList </CODE>对象。
     * 指定向量中的对象可以是<CODE> SnmpVarBind </CODE>对象或派生类。
     * 
     * 
     * @param list The vector of <CODE>SnmpVarBind</CODE> objects to copy.
     */
    public SnmpVarBindList(Vector<SnmpVarBind> list) {
        super(list.size(), 5);
        for (Enumeration<SnmpVarBind> e = list.elements(); e.hasMoreElements();) {
            final SnmpVarBind varBind = e.nextElement();
            addElement(varBind.clone());
        }
    }

    /**
     * Creates a new <CODE>SnmpVarBindList</CODE> object from a plain vector of <CODE>SnmpVarBind</CODE> objects.
     * Objects in the specified vector can be <CODE>SnmpVarBind</CODE> objects or derivatives.
     * <p>
     * 从<CODE> SnmpVarBind </CODE>对象的平面向量创建一个新的<CODE> SnmpVarBindList </CODE>对象。
     * 指定向量中的对象可以是<CODE> SnmpVarBind </CODE>对象或派生类。
     * 
     * 
     * @param name The name of the newly created <CODE>SnmpVarBindList</CODE>.
     * @param list The vector of <CODE>SnmpVarBind</CODE> objects to copy.
     */
    public SnmpVarBindList(String name, Vector<SnmpVarBind> list) {
        this(list);
        identity = name;
    }


    // GETTER/SETTER
    //--------------

    /**
     * Gets the <CODE>timestamp</CODE> associated with this <CODE>SnmpVarBindList</CODE>.
     * <p>
     *  获取与此<CODE> SnmpVarBindList </CODE>相关联的<CODE>时间戳记</CODE>。
     * 
     * 
     * @return The <CODE>timestamp</CODE>.
     */
    public Timestamp getTimestamp() {
        return timestamp ;
    }

    /**
     * Records the <CODE>sysUpTime</CODE> and the actual time when this <CODE>SnmpVarBindList</CODE>
     * was changed or created.
     * This needs to be set explicitly.
     * <p>
     *  记录<CODE> sysUpTime </CODE>和更改或创建此<CODE> SnmpVarBindList </CODE>时的实际时间。这需要明确设置。
     * 
     * 
     * @param tstamp The <CODE>SnmpTimestamp</CODE> of the device for which the values hold <CODE>true</CODE>.
     */
    public void setTimestamp(Timestamp tstamp) {
        timestamp = tstamp ;
    }

    /**
     * Gets an <CODE>SnmpVarBind</CODE> object.
     * <p>
     *  获取<CODE> SnmpVarBind </CODE>对象。
     * 
     * 
     * @param pos The position in the list.
     * @return The <CODE>SnmpVarBind</CODE> object at the specified position.
     * @exception java.lang.ArrayIndexOutOfBoundsException If the specified <CODE>pos</CODE> is beyond range.
     */
    public final synchronized SnmpVarBind getVarBindAt(int pos) {
        return elementAt(pos);
    }

    /**
     * Gets the number of elements in this list.
     * <p>
     *  获取此列表中的元素数。
     * 
     * 
     * @return The number of elements in the list.
     */
    public synchronized int getVarBindCount() {
        return size() ;
    }

    /**
     * This is a convenience function that returns an enumeration. This can be used to traverse the list.
     * This is advantageous as it hides the implementation of the class of the list which keeps the variables.
     * <p>
     *  这是一个返回枚举的便利函数。这可以用于遍历列表。这是有利的,因为它隐藏保存变量的列表的类的实现。
     * 
     * 
     * @return An enumeration object of <CODE>SnmpVarBind</CODE> objects.
     */
    public synchronized Enumeration<SnmpVarBind> getVarBindList() {
        return elements() ;
    }

    /**
     * Replaces the current variable binding list of <CODE>SnmpVarBind</CODE> with the new specified variable binding
     * list of <CODE>SnmpVarBind</CODE> objects.
     * This method only clones the vector. It does not clone the <CODE>SnmpVarBind</CODE> objects
     * contained in the list.
     * <p>
     *  将<CODE> SnmpVarBind </CODE>的当前变量绑定列表替换为<CODE> SnmpVarBind </CODE>对象的新指定的变量绑定列表。此方法仅克隆向量。
     * 它不克隆列表中包含的<CODE> SnmpVarBind </CODE>对象。
     * 
     * 
     * @param list A vector of <CODE>SnmpVarBind</CODE> objects.
     */
    public final synchronized void setVarBindList(Vector<SnmpVarBind> list) {
        setVarBindList(list, false) ;
    }

    /**
     * Replaces the current variable binding list of <CODE>SnmpVarBind</CODE> objects with the new variable binding
     * list of <CODE>SnmpVarBind</CODE> objects.
     * If <CODE>copy</CODE> is <CODE>true</CODE>, it will clone each <CODE>SnmpVarBind</CODE> object
     * contained in the list.
     * <p>
     *  将<CODE> SnmpVarBind </CODE>对象的当前变量绑定列表替换为<CODE> SnmpVarBind </CODE>对象的新变量绑定列表。
     * 如果<CODE> copy </CODE>是<CODE> true </CODE>,它将克隆列表中包含的每个<CODE> SnmpVarBind </CODE>对象。
     * 
     * 
     * @param list A vector of <CODE>SnmpVarBind</CODE> objects.
     * @param copy The flag indicating whether each object in the list should be cloned.
     */
    public final synchronized void setVarBindList(Vector<SnmpVarBind> list, boolean copy) {
        synchronized (list) {
            final int max = list.size();
            setSize(max) ;
            list.copyInto(this.elementData) ;
            if (copy) {         // do deepcopy of all vars.
                for (int i = 0; i < max ; i++) {
                    SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
                    elementData[i] = avar.clone() ;
                }
            }
        }
    }


    // PUBLIC METHODS
    //---------------

    /**
     * Appends an <CODE>SnmpVarBindList</CODE> at the end of the current <CODE>SnmpVarBindList</CODE> object.
     * <p>
     *  在当前<CODE> SnmpVarBindList </CODE>对象的结尾处附加<CODE> SnmpVarBindList </CODE>。
     * 
     * 
     * @param list The <CODE>SnmpVarBindList</CODE> to append.
     */
    public synchronized void addVarBindList(SnmpVarBindList list) {
        ensureCapacity(list.size() + size()) ;
        for (int i = 0; i < list.size(); i++) {
            addElement(list.getVarBindAt(i)) ;
        }
    }

    /**
     * Removes all the <CODE>SnmpVarBind</CODE> objects of the given <CODE>SnmpVarBindList</CODE> from the existing
     * <CODE>SnmpVarBindList</CODE>.
     * <p>
     * 从现有的<CODE> SnmpVarBindList </CODE>中删除给定<CODE> SnmpVarBindList </CODE>中的所有<CODE> SnmpVarBind </CODE>
     * 
     * 
     * @param list The <CODE>SnmpVarBindList</CODE> to be removed.
     * @return <CODE>true</CODE> if all the <CODE>SnmpVarBind</CODE> objects were components of this
     * <CODE>SnmpVarBindList</CODE>, <CODE>false</CODE> otherwise.
     */
    public synchronized boolean removeVarBindList(SnmpVarBindList list) {
        boolean result = true;
        for (int i = 0; i < list.size(); i++) {
            result = removeElement(list.getVarBindAt(i)) ;
        }
        return result;
    }

    /**
     * Replaces an element at a specified location with the new element.
     * <p>
     *  使用新元素替换指定位置处的元素。
     * 
     * 
     * @param var The replacement variable.
     * @param pos The location in the <CODE>SnmpVarBindList</CODE>.
     * @exception java.lang.ArrayIndexOutOfBoundsException If the specified <CODE>pos</CODE> is beyond range.
     */
    public final synchronized void replaceVarBind(SnmpVarBind var, int pos) {
        setElementAt(var, pos) ;
    }

    /**
     * Prepares a vector of <CODE>SnmpVarBindList</CODE> from an array of SNMP MIB variables and instances.
     * <p>
     *  从SNMP MIB变量和实例的数组中准备一个<CODE> SnmpVarBindList </CODE>的向量。
     * 
     * 
     * @param list An array of <CODE>String</CODE> containing MIB variable names.
     * @param inst A common instance for each of the MIB variables in <CODE>vlist</CODE>.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public final synchronized void addVarBind(String list[], String inst) throws SnmpStatusException {
        for (int i = 0; i < list.length; i++) {
            SnmpVarBind avar = new SnmpVarBind(list[i]) ;
            avar.addInstance(inst) ;
            addElement(avar) ;
        }
    }

    /**
     * Removes the array of SNMP MIB variables and instances from the existing <CODE>SnmpVarBindList</CODE>.
     * <p>
     *  从现有的<CODE> SnmpVarBindList </CODE>中删除SNMP MIB变量和实例的数组。
     * 
     * 
     * @param list An array of <CODE>String</CODE> containing MIB variable names.
     * @param inst A common instance for each of the MIB variables in <CODE>vlist</CODE>.
     * @return <CODE>true</CODE> if all the SNMP MIB variables were components of this <CODE>SnmpVarBindList</CODE>,
     * <CODE>false</CODE> otherwise.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public synchronized boolean removeVarBind(String list[], String inst) throws SnmpStatusException {
        boolean result = true;
        for (int i = 0; i < list.length; i++) {
            SnmpVarBind avar = new SnmpVarBind(list[i]) ;
            avar.addInstance(inst) ;
            int indexOid = indexOfOid(avar) ;
            try {
                removeElementAt(indexOid) ;
            } catch (ArrayIndexOutOfBoundsException e) {
                result = false ;
            }
        }
        return result ;
    }

    /**
     * Adds an array of MIB variable names to the list. For example:
     * <P>
     * <CODE>
     * String mylist[] = {"sysUpTime.0", "ifInOctets.0"}
     * <BR>
     * vb.addVarBind(mylist) ;
     * </BR>
     * </CODE>
     * <p>
     *  向列表中添加MIB变量名称的数组。例如：
     * <P>
     * <CODE>
     *  String mylist [] = {"sysUpTime.0","ifInOctets.0"}
     * <BR>
     *  vb.addVarBind(mylist);
     * </BR>
     * </CODE>
     * 
     * @param list The array of MIB variable names.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public synchronized void addVarBind(String list[]) throws SnmpStatusException {
        addVarBind(list, null) ;
    }

    /**
     * Removes the array of SNMP MIB variables from the existing <CODE>SnmpVarBindList</CODE>.
     * <p>
     *  从现有的<CODE> SnmpVarBindList </CODE>中删除SNMP MIB变量数组。
     * 
     * 
     * @param list Array of strings containing MIB variable names.
     * @return <CODE>true</CODE> if all the SNMP MIB variables were components of this <CODE>SnmpVarBindList</CODE>,
     * <CODE>false</CODE> otherwise.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public synchronized boolean removeVarBind(String list[]) throws SnmpStatusException {
        return removeVarBind(list, null) ;
    }

    /**
     * Creates an <CODE>SnmpVarBind</CODE> object from the given MIB variable and appends it to the existing
     * <CODE>SnmpVarBindList</CODE>.
     * It creates a new <CODE>SnmpVarBindList</CODE> if one did not exist.
     * <p>
     *  从给定的MIB变量创建<CODE> SnmpVarBind </CODE>对象,并将其附加到现有的<CODE> SnmpVarBindList </CODE>。
     * 它创建一个新的<CODE> SnmpVarBindList </CODE>(如果不存在)。
     * 
     * 
     * @param name A MIB variable name.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public synchronized void addVarBind(String name) throws SnmpStatusException {
        SnmpVarBind avar ;
        avar = new SnmpVarBind(name) ;
        addVarBind(avar) ;
    }

    /**
     * Removes the <CODE>SnmpVarBind</CODE> object corresponding to the given MIB variable from the existing
     * <CODE>SnmpVarBindList</CODE>.
     * <p>
     *  从现有的<CODE> SnmpVarBindList </CODE>中删除与给定MIB变量对应的<CODE> SnmpVarBind </CODE>对象。
     * 
     * 
     * @param name A MIB variable name.
     * @return <CODE>true</CODE> if the SNMP MIB variable was a component of this <CODE>SnmpVarBindList</CODE>,
     * <CODE>false</CODE> otherwise.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public synchronized boolean removeVarBind(String name) throws SnmpStatusException {
        SnmpVarBind avar ;
        int indexOid ;
        avar = new SnmpVarBind(name) ;
        indexOid = indexOfOid(avar) ;
        try {
            removeElementAt(indexOid) ;
            return true ;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false ;
        }
    }

    /**
     * Appends the given <CODE>SnmpVarBind</CODE> object to the existing <CODE>SnmpVarBindList</CODE>.
     * It creates a new <CODE>SnmpVarBindList</CODE> if one did not exist.
     * <p>
     *  将给定的<CODE> SnmpVarBind </CODE>对象附加到现有的<CODE> SnmpVarBindList </CODE>。
     * 它创建一个新的<CODE> SnmpVarBindList </CODE>(如果不存在)。
     * 
     * 
     * @param var The <CODE>SnmpVarBind</CODE> object to be appended.
     */
    public synchronized void addVarBind(SnmpVarBind var) {
        addElement(var) ;
    }

    /**
     * Removes the given <CODE>SnmpVarBind</CODE> object from the existing <CODE>SnmpVarBindList</CODE>.
     * <p>
     *  从现有的<CODE> SnmpVarBindList </CODE>中删除给定的<CODE> SnmpVarBind </CODE>对象。
     * 
     * 
     * @param var The <CODE>SnmpVarBind</CODE> object to be removed.
     * @return <CODE>true</CODE> if the <CODE>SnmpVarBind</CODE> object was a component of this
     * <CODE>SnmpVarBindList</CODE>, <CODE>false</CODE> otherwise.
     */
    public synchronized boolean removeVarBind(SnmpVarBind var) {
        return removeElement(var) ;
    }

    /**
     * Adds the string as an instance part to all OIDs in this list.
     * This method should be used with caution because it affects all OIDs in the list.
     * <p>
     *  将该字符串作为实例部分添加到此列表中的所有OID。应谨慎使用此方法,因为它会影响列表中的所有OID。
     * 
     * 
     * @param inst The <CODE>String</CODE> to add as an instance part.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public synchronized void addInstance(String inst) throws SnmpStatusException {
        int max= size();
        for (int i = 0; i < max;  i++) {
            ((SnmpVarBind)elementData[i]).addInstance(inst) ;
        }
    }

    /**
     * Adds elements in the specified <CODE>SnmpVarBindList</CODE> to this list.
     * The elements are not cloned.
     * <p>
     *  将指定的<CODE> SnmpVarBindList </CODE>中的元素添加到此列表。元素未克隆。
     * 
     * 
     * @param list A vector of <CODE>SnmpVarBind</CODE>.
     */
    final public synchronized void concat(Vector<SnmpVarBind> list) {
        ensureCapacity(size() + list.size()) ;
        for (Enumeration<SnmpVarBind> e = list.elements() ; e.hasMoreElements() ; ) {
            addElement(e.nextElement()) ;
        }
    }

    /**
     * Returns <CODE>false</CODE> if any of the variables does not contain a valid value.
     * Typically used for <CODE>SnmpSet</CODE> operations.
     * <p>
     * 如果任何变量不包含有效值,则返回<CODE> false </CODE>。通常用于<CODE> SnmpSet </CODE>操作。
     * 
     * 
     * @return <CODE>false</CODE> if any of the variables does not contain a valid value, <CODE>true</CODE> otherwise.
     */
    public synchronized boolean checkForValidValues() {
        int max= this.size();
        for (int i = 0; i < max ; i++) {
            SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
            if (avar.isValidValue() == false)
                return false ;
        }
        return true ;
    }

    /**
     * Returns <CODE>true</CODE> if there is a value that is not specified.
     * <p>
     *  如果存在未指定的值,则返回<CODE> true </CODE>。
     * 
     * 
     * @return <CODE>true</CODE> if there is a value that is not specified, <CODE>false</CODE> otherwise.
     */
    public synchronized boolean checkForUnspecifiedValue() {
        int max= this.size();
        for (int i = 0; i < max ; i++) {
            SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
            if (avar.isUnspecifiedValue())
                return true ;
        }
        return false ;
    }

    /**
     * Splits the <CODE>SnmpVarBindList</CODE>.
     * <p>
     *  拆分<CODE> SnmpVarBindList </CODE>。
     * 
     * 
     * @param pos The position at which to split the <CODE>SnmpVarBindList</CODE>
     * @return The <CODE>SnmpVarBindList</CODE> list from the beginning up to the split position.
     */
    public synchronized SnmpVarBindList splitAt(int pos) {
        SnmpVarBindList splitVb = null ;
        if (pos > elementCount)
            return splitVb ;
        splitVb = new SnmpVarBindList() ; // size() - atPosition) ;
        int max= size();
        for (int i = pos; i < max ; i++)
            splitVb.addElement((SnmpVarBind) elementData[i]) ;
        elementCount = pos ;
        trimToSize() ;
        return splitVb ;
    }

    /**
     * Gives the index of an OID in the <CODE>SnmpVarBindList</CODE>.
     * The index returned must be greater than or equal to the <CODE>start</CODE> parameter
     * and smaller than the <CODE>end</CODE> parameter. Otherwise the method returns -1.
     * <p>
     *  在<CODE> SnmpVarBindList </CODE>中提供OID的索引。
     * 返回的索引必须大于或等于<CODE> start </CODE>参数,并小于<CODE> end </CODE>参数。否则该方法返回-1。
     * 
     * 
     * @param var The <CODE>SnmpVarBind</CODE> object with the requested OID.
     * @param min The min index in <CODE>SnmpVarBindList</CODE>.
     * @param max The max index in <CODE>SnmpVarBindList</CODE>.
     * @return The index of the OID in <CODE>SnmpVarBindList</CODE>.
     */
    public synchronized int indexOfOid(SnmpVarBind var, int min, int max) {
        SnmpOid oidarg = var.getOid() ;
        for (int i = min; i < max ; i++) {
            SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
            if (oidarg.equals(avar.getOid()))
                return i ;
        }
        return -1 ;
    }

    /**
     * Gives the index of an OID in the <CODE>SnmpVarBindList</CODE>.
     * <p>
     *  在<CODE> SnmpVarBindList </CODE>中提供OID的索引。
     * 
     * 
     * @param var The <CODE>SnmpVarBind</CODE> object with the requested OID.
     * @return The index of the OID in <CODE>SnmpVarBindList</CODE>.
     */
    public synchronized int indexOfOid(SnmpVarBind var) {
        return indexOfOid(var, 0, size()) ;
    }

    /**
     * Gives the index of an OID in the <CODE>SnmpVarBindList</CODE>.
     * <p>
     *  在<CODE> SnmpVarBindList </CODE>中提供OID的索引。
     * 
     * 
     * @param oid The <CODE>SnmpOid</CODE> object with the requested OID.
     * @return The index of the OID in <CODE>SnmpVarBindList</CODE>.
     */
    public synchronized int indexOfOid(SnmpOid oid) {
        int max = size();
        for (int i = 0; i < max ; i++) {
            SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
            if (oid.equals(avar.getOid()))
                return i ;
        }
        return -1 ;
    }

    /**
     * Clones the <CODE>SnmpVarBindList</CODE>. A new copy of the <CODE>SnmpVarBindList</CODE> is created.
     * It is a real deep copy.
     * <p>
     *  克隆<CODE> SnmpVarBindList </CODE>。创建<CODE> SnmpVarBindList </CODE>的新副本。这是一个真正的深拷贝。
     * 
     * 
     * @return The <CODE>SnmpVarBindList</CODE> clone.
     */
    public synchronized SnmpVarBindList cloneWithValue() {
        SnmpVarBindList newvb = new SnmpVarBindList() ;
        newvb.setTimestamp(this.getTimestamp()) ;
        newvb.ensureCapacity(this.size()) ;
        for (int i = 0; i < this.size() ; i++) {
            SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
            newvb.addElement(avar.clone()) ;
        }
        return newvb ;
    }

    /**
     * Clones the <CODE>SnmpVarBindList</CODE>. It does not clone the value part of the variable.
     * It is a deep copy (except for the value portion).
     * <p>
     *  克隆<CODE> SnmpVarBindList </CODE>。它不克隆变量的值部分。它是一个深层副本(除了值部分)。
     * 
     * 
     * @return The <CODE>SnmpVarBindList</CODE> clone.
     */
    public synchronized SnmpVarBindList cloneWithoutValue() {
        SnmpVarBindList newvb = new SnmpVarBindList() ;
        int max = this.size();
        newvb.ensureCapacity(max) ;
        for (int i = 0; i < max ; i++) {
            SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
            newvb.addElement((SnmpVarBind) avar.cloneWithoutValue()) ;
        }
        return newvb ;
    }

    /**
     * Clones the <CODE>SnmpVarBindList</CODE>. A new copy of the <CODE>SnmpVarBindList</CODE> is created.
     * It is a real deep copy.
     * <p>
     *  克隆<CODE> SnmpVarBindList </CODE>。创建<CODE> SnmpVarBindList </CODE>的新副本。这是一个真正的深拷贝。
     * 
     * 
     * @return The object clone.
     */
    @Override
    public synchronized SnmpVarBindList clone() {
        return cloneWithValue() ;
    }

    /**
     * Copies the <CODE>SnmpVarBindList</CODE> into a plain vector of <CODE>SnmpVarBind</CODE> objects.
     * If the <code>copy</code> flag is false, does a shallow copy of the list. Otherwise,
     * individual elements will be cloned.
     * <p>
     *  将<CODE> SnmpVarBindList </CODE>复制到<CODE> SnmpVarBind </CODE>对象的平面向量中。
     * 如果<code> copy </code>标志为false,则执行列表的浅拷贝。否则,将克隆单个元素。
     * 
     * 
     * @param copy The flag indicating whether each object in the list should be cloned.
     * @return A new vector of <CODE>SnmpVarBind</CODE> objects.
     */
    public synchronized Vector<SnmpVarBind> toVector(boolean copy) {
        final int count = elementCount;
        if (copy == false) return new Vector<>(this);
        Vector<SnmpVarBind> result = new Vector<>(count,5);
        for (int i = 0; i < count ; i++) {
            SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
            result.addElement(avar.clone()) ;
        }
        return result;
    }

    /**
     * Returns a <CODE>String</CODE> containing the ASCII representation of all OIDs in the list.
     * <p>
     *  返回包含列表中所有OID的ASCII表示的<CODE>字符串</CODE>。
     * 
     * 
     * @return An ASCII list of all OIDs in this list.
     */
    public String oidListToString() {
        StringBuilder s = new StringBuilder(300) ;
        for (int i = 0 ; i < elementCount ; i++) {
            SnmpVarBind avar = (SnmpVarBind)elementData[i] ;
            s.append(avar.getOid().toString()).append("\n") ;
        }
        return s.toString() ;
    }

    /**
     * Constructs a <CODE>String</CODE> containing details of each <CODE>SnmpVarBindList</CODE> (oid+value).
     * This is typically used in debugging.
     * <p>
     *  构造包含每个<CODE> SnmpVarBindList </CODE>(oid +值)的详细信息的<CODE>字符串</CODE>。这通常用于调试。
     * 
     * 
     * @return A detailed <CODE>String</CODE> of all in the <CODE>SnmpVarBindList</CODE>.
     */
    public synchronized String varBindListToString() {
        StringBuilder s = new StringBuilder(300) ;
        for (int i = 0; i < elementCount ; i++) {
            s.append(elementData[i].toString()).append("\n")  ;
        }
        return s.toString() ;
    }

    /**
     * Finalizer of the <CODE>SnmpVarBindList</CODE> objects.
     * This method is called by the garbage collector on an object
     * when garbage collection determines that there are no more references to the object.
     * <P>Removes all the elements from this <CODE>SnmpVarBindList</CODE> object.
     * <p>
     * <CODE> SnmpVarBindList </CODE>对象的终结器。当垃圾回收确定没有对对象的更多引用时,垃圾收集器在对象上调用此方法。
     *  <P>从此<CODE> SnmpVarBindList </CODE>对象中删除所有元素。
     */
    @Override
    protected void finalize() {
        removeAllElements() ;
    }
}

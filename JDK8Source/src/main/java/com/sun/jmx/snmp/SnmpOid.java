/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

// java imports
//
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

/**
 * Represents an SNMP Object Identifier (OID).
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  表示SNMP对象标识符(OID)。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public class SnmpOid extends SnmpValue {

    // CONSTRUCTORS
    //-------------
    /**
     * Constructs a new <CODE>SnmpOid</CODE> with no components.
     * <p>
     *  构造一个没有组件的新<CODE> SnmpOid </CODE>。
     * 
     */
    public SnmpOid() {
        components = new long[15] ;
        componentCount = 0 ;
    }

    /**
     * Constructs a new <CODE>SnmpOid</CODE> from the specified component array.
     * <p>
     *  从指定的组件数组构造新的<CODE> SnmpOid </CODE>。
     * 
     * 
     * @param oidComponents The initialization component array.
     */
    public SnmpOid(long[] oidComponents) {
        components = oidComponents.clone() ;
        componentCount = components.length ;
    }

    /**
     * Constructs a new <CODE>SnmpOid</CODE> containing one component with the
     * specified value.
     * <p>
     *  构造一个包含具有指定值的一个组件的新<CODE> SnmpOid </CODE>。
     * 
     * 
     * @param id The initialization component value.
     */
    public SnmpOid(long id) {
        components = new long[1] ;
        components[0] = id ;
        componentCount = components.length ;
    }

    /**
     * Constructs a new <CODE>SnmpOid</CODE> containing four components
     * with the specified values.
     * <p>
     *  构造一个包含具有指定值的四个组件的新<CODE> SnmpOid </CODE>。
     * 
     * 
     * @param id1 The first component value.
     * @param id2 The second component values.
     * @param id3 The third component values.
     * @param id4 The fourth component values.
     */
    public SnmpOid(long id1, long id2, long id3, long id4) {
        components = new long[4] ;
        components[0] = id1 ;
        components[1] = id2 ;
        components[2] = id3 ;
        components[3] = id4 ;
        componentCount = components.length ;
    }

    /**
     * Constructs a new <CODE>SnmpOid</CODE> from a dot-formatted <CODE>String</CODE> or a MIB variable
     * name. It generates an exception if the variable name cannot be resolved, or
     * if the dot-formatted <CODE>String</CODE> has an invalid subidentifier.
     * This constructor helps build an OID object with a <CODE>String</CODE> like .1.2.3.4 or 1.2.3.4
     * or <CODE>ifInOctets</CODE> or <CODE>ifInOctets</CODE>.0.
     * <p>
     *  从点格式的<CODE>字符串</CODE>或MIB变量名称构造新的<CODE> SnmpOid </CODE>。
     * 如果无法解析变量名称,或者如果点格式的<CODE> String </CODE>具有无效的子标识符,则会生成异常。
     * 此构造函数帮助构建具有<CODE> String </CODE>(如.1.2.3.4或1.2.3.4或<CODE> ifInOctets </CODE>或<CODE> ifInOctets </CODE>
     *  .0)的OID对象。
     * 如果无法解析变量名称,或者如果点格式的<CODE> String </CODE>具有无效的子标识符,则会生成异常。
     * 
     * 
     * @param s <CODE>String</CODE> or MIB variable of the form .1.2.3 or 1.2.3 or <CODE>ifInOctets</CODE>.
     * @exception IllegalArgumentException The subidentifier is neither a numeric <CODE>String</CODE>
     * nor a <CODE>String</CODE> of the MIB database.
     */
    public SnmpOid(String s) throws IllegalArgumentException {
        String dotString = s ;

        if (s.startsWith(".") == false) {
            try {
                dotString = resolveVarName(s);
            } catch(SnmpStatusException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        StringTokenizer st = new StringTokenizer(dotString, ".", false) ;
        componentCount= st.countTokens();

        // Now extract the ids
        //
        if (componentCount == 0) {
            components = new long[15] ;
        }  else {
            components = new long[componentCount] ;
            try {
                for (int i = 0 ; i < componentCount ; i++) {
                    try {
                        components[i] = Long.parseLong(st.nextToken()) ;
                    }
                    catch(NoSuchElementException e) {}
                }
            }
            catch(NumberFormatException e) {
                throw new IllegalArgumentException(s) ;
            }
        }
    }

    // PUBLIC METHODS
    //---------------
    /**
     * Gets the number of components in this OID.
     * <p>
     *  获取此OID中的组件数。
     * 
     * 
     * @return The number of components.
     */
    public int getLength() {
        return componentCount ;
    }

    /**
     * Returns a copy of the components array of this <CODE>SnmpOid</CODE>.
     * <p>
     *  返回此<CODE> SnmpOid </CODE>的components数组的副本。
     * 
     * 
     * @return The copy of the components array.
     */
    public long[] longValue() {
        long[] result = new long[componentCount] ;
        System.arraycopy(components,0,result,0,componentCount);
        return result ;
    }

    /**
     * Returns the components array of this <CODE>SnmpOid</CODE>.
     * If <code>duplicate</code> is true, a copy is returned.
     * Otherwise, a reference to the internal array is returned,
     * in which case the caller <b>shall not</b> modify this array.
     * This method is provided to optimize processing in those cases
     * where the caller needs only to read the components array.
     *
     * <p>
     *  返回此<CODE> SnmpOid </CODE>的components数组。如果<code> duplicate </code>为true,则会返回一个副本。
     * 否则,将返回对内部数组的引用,在这种情况下调用者<b>不应</b>修改此数组。在调用者只需要读取components数组的情况下,提供此方法以优化处理。
     * 
     * 
     * @param duplicate Indicates whether a copy or a reference must
     *        be returned:
     *        <li><code>true</code> if a copy must be returned,</li>
     *        <li><code>false</code> if a reference on the internal data
     *            can be returned.</li>
     * @return A copy of (or a reference on) the components array.
     * @Deprecated use {@link #longValue()}
     */
    public final long[] longValue(boolean duplicate) {
        return longValue();
    }

    /**
     * Returns the value of the OID arc found at the requested position
     * in the <CODE>components</CODE> array. The first element is at
     * position <code>0</code>.
     *
     * <p>
     * 返回在<CODE>组件</CODE>数组中的请求位置处找到的OID弧的值。第一个元素位于<code> 0 </code>。
     * 
     * 
     * @param  pos The position at which the OID arc should be peeked.
     *
     * @return The OID arc found at the requested position.
     *
     * @exception SnmpStatusException No OID arc was found at the requested
     *            position.
     */
    public final long getOidArc(int pos) throws SnmpStatusException {
        try {
            return components[pos];
        } catch(Exception e) {
            throw new SnmpStatusException(SnmpStatusException.noAccess);
        }
    }

    /**
     * Converts the OID value to its <CODE>Long</CODE> form.
     * <p>
     *  将OID值转换为其<CODE>长</CODE>表单。
     * 
     * 
     * @return The <CODE>Long</CODE> representation of the value.
     */
    public Long toLong() {
        if (componentCount != 1) {
            throw new IllegalArgumentException() ;
        }
        return new Long(components[0]) ;
    }

    /**
     * Converts the OID value to its <CODE>Integer</CODE> form.
     * <p>
     *  将OID值转换为其<CODE>整数</CODE>表单。
     * 
     * 
     * @return The <CODE>Integer</CODE> representation of the value.
     */
    public Integer toInteger() {
        if ((componentCount != 1) || (components[0] > Integer.MAX_VALUE)) {
            throw new IllegalArgumentException() ;
        }
        return new Integer((int)components[0]) ;
    }

    /**
     * Converts the OID value to its <CODE>String</CODE> form.
     * <p>
     *  将OID值转换为其<CODE>字符串</CODE>表单。
     * 
     * 
     * @return The <CODE>String</CODE> representation of the value.
     */
    public String toString() {
        String result = "" ;
        if (componentCount >= 1) {
            for (int i = 0 ; i < componentCount - 1 ; i++) {
                result = result + components[i] + "." ;
            }
            result = result + components[componentCount - 1] ;
        }
        return result ;
    }

    /**
     * Converts the OID value to its <CODE>Boolean</CODE> form.
     * <p>
     *  将OID值转换为其<CODE> Boolean </CODE>表单。
     * 
     * 
     * @return The <CODE>Boolean</CODE> representation of the value.
     */
    public Boolean toBoolean() {
        if ((componentCount != 1) && (components[0] != 1) && (components[0] != 2)) {
            throw new IllegalArgumentException() ;
        }
        return Boolean.valueOf(components[0] == 1) ;
    }

    /**
     * Converts the OID value to its array of <CODE>Bytes</CODE> form.
     * <p>
     *  将OID值转换为<CODE>字节</CODE>表单的数组。
     * 
     * 
     * @return The array of <CODE>Bytes</CODE> representation of the value.
     */
    public Byte[] toByte() {
        Byte[] result = new Byte[componentCount] ;
        for (int i =0 ; i < componentCount ; i++) {
            if (components[0] > 255) {
                throw new IllegalArgumentException() ;
            }
            result[i] = new Byte((byte)components[i]) ;
        }
        return result ;
    }

    /**
     * Converts the OID value to its <CODE>SnmpOid</CODE> form.
     * <p>
     *  将OID值转换为其<CODE> SnmpOid </CODE>表单。
     * 
     * 
     * @return The OID representation of the value.
     */
    public SnmpOid toOid() {
        long[] ids = new long[componentCount] ;
        for (int i = 0 ; i < componentCount ; i++) {
            ids[i] = components[i] ;
        }
        return new SnmpOid(ids) ;
    }

    /**
     * Extracts the OID from an index OID and returns its
     * value converted as an <CODE>SnmpOid</CODE>.
     * <p>
     *  从索引OID提取OID,并返回其转换为<CODE> SnmpOid </CODE>的值。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The OID representing the OID value.
     * @exception SnmpStatusException There is no OID value
     * available at the start position.
     */
    public static SnmpOid toOid(long[] index, int start) throws SnmpStatusException {
        try {
            if (index[start] > Integer.MAX_VALUE) {
                throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
            }
            int idCount = (int)index[start++] ;
            long[] ids = new long[idCount] ;
            for (int i = 0 ; i < idCount ; i++) {
                ids[i] = index[start + i] ;
            }
            return new SnmpOid(ids) ;
        }
        catch(IndexOutOfBoundsException e) {
            throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
        }
    }

    /**
     * Scans an index OID, skips the OID value and returns the position
     * of the next value.
     * <p>
     *  扫描索引OID,跳过OID值并返回下一个值的位置。
     * 
     * 
     * @param index The index array.
     * @param start The position in the index array.
     * @return The position of the next value.
     * @exception SnmpStatusException There is no OID value
     * available at the start position.
     */
    public static int nextOid(long[] index, int start) throws SnmpStatusException {
        try {
            if (index[start] > Integer.MAX_VALUE) {
                throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
            }
            int idCount = (int)index[start++] ;
            start += idCount ;
            if (start <= index.length) {
                return start ;
            }
            else {
                throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
            }
        }
        catch(IndexOutOfBoundsException e) {
            throw new SnmpStatusException(SnmpStatusException.noSuchName) ;
        }
    }

    /**
     * Appends an <CODE>SnmpOid</CODE> representing an <CODE>SnmpOid</CODE> to another OID.
     * <p>
     *  将代表<CODE> SnmpOid </CODE>的<CODE> SnmpOid </CODE>附加到另一个OID。
     * 
     * 
     * @param source An OID representing an <CODE>SnmpOid</CODE> value.
     * @param dest Where source should be appended.
     */
    public static void appendToOid(SnmpOid source, SnmpOid dest) {
        dest.append(source.getLength()) ;
        dest.append(source) ;
    }

    /**
     * Performs a clone action. This provides a workaround for the
     * <CODE>SnmpValue</CODE> interface.
     * <p>
     *  执行克隆操作。这为<CODE> SnmpValue </CODE>接口提供了一个解决方法。
     * 
     * 
     * @return The SnmpValue clone.
     */
    final synchronized public SnmpValue duplicate() {
        return (SnmpValue)clone() ;
    }

    /**
     * Clones the <CODE>SnmpOid</CODE> object, making a copy of its data.
     * <p>
     *  克隆<CODE> SnmpOid </CODE>对象,创建其数据的副本。
     * 
     * 
     * @return The object clone.
     */
    public Object clone() {
        try {
            SnmpOid obj = (SnmpOid)super.clone() ;
            obj.components = new long[this.componentCount] ;

            System.arraycopy(this.components, 0, obj.components, 0,
                             this.componentCount) ;
            return obj ;
        } catch (CloneNotSupportedException e) {
            throw new InternalError() ;  // should never happen. VM bug.
        }
    }

    /**
     * Inserts a subid at the beginning of this <CODE>SnmpOid</CODE>.
     * <p>
     *  在此<CODE> SnmpOid </CODE>的开头插入一个子标识。
     * 
     * 
     * @param id The long subid to insert.
     */
    public void insert(long id) {
        enlargeIfNeeded(1) ;
        for (int i = componentCount - 1 ; i >= 0 ; i--) {
            components[i + 1] = components[i] ;
        }
        components[0] = id ;
        componentCount++ ;
    }

    /**
     * Inserts a subid at the beginning of this <CODE>SnmpOid</CODE>.
     * <p>
     *  在此<CODE> SnmpOid </CODE>的开头插入一个子标识。
     * 
     * 
     * @param id The integer subid to insert.
     */
    public void insert(int id) {
        insert((long)id) ;
    }

    /**
     * Appends the specified <CODE>SnmpOid</CODE> to the end of this <CODE>SnmpOid</CODE>.
     * <p>
     *  将指定的<CODE> SnmpOid </CODE>附加到此<CODE> SnmpOid </CODE>的末尾。
     * 
     * 
     * @param oid The OID to append.
     */
    public void append(SnmpOid oid) {
        enlargeIfNeeded(oid.componentCount) ;
        for (int i = 0 ; i < oid.componentCount ; i++) {
            components[componentCount + i] = oid.components[i] ;
        }
        componentCount += oid.componentCount ;
    }

    /**
     * Appends the specified long to the end of this <CODE>SnmpOid</CODE>.
     * <p>
     *  将指定的长度附加到此<CODE> SnmpOid </CODE>的末尾。
     * 
     * 
     * @param id The long to append.
     */
    public void append(long id) {
        enlargeIfNeeded(1) ;
        components[componentCount] = id ;
        componentCount++ ;
    }

    /**
     * Adds the specified dot-formatted OID <CODE>String</CODE> to the end of this <CODE>SnmpOid</CODE>.
     * The subidentifiers can be expressed as a dot-formatted <CODE>String</CODE> or a
     * MIB variable name.
     * <p>
     *  将指定的点格式OID <CODE> String </CODE>添加到此<CODE> SnmpOid </CODE>的末尾。
     * 子标识符可以表示为点格式的<CODE> String </CODE>或MIB变量名称。
     * 
     * 
     * @param s Variable name of the form .1.2.3 or  1.2.3 or
     * <CODE>ifInOctets</CODE>.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public void addToOid(String s) throws SnmpStatusException {
        SnmpOid suffix= new SnmpOid(s);
        this.append(suffix);
    }

    /**
     * Adds the specified array of longs to the end of this <CODE>SnmpOid</CODE>.
     * <p>
     *  将指定的longs数组添加到此<CODE> SnmpOid </CODE>末尾。
     * 
     * 
     * @param oid An array of longs.
     * @exception SnmpStatusException An error occurred while accessing a MIB node.
     */
    public void addToOid(long []oid) throws SnmpStatusException {
        SnmpOid suffix= new SnmpOid(oid);
        this.append(suffix);
    }

    /**
     * Checks the validity of the OID.
     * <p>
     * 检查OID的有效性。
     * 
     * 
     * @return <CODE>true</CODE> if the OID is valid, <CODE>false</CODE> otherwise.
     */
    public boolean isValid() {
        return ((componentCount >= 2) &&
                ((0 <= components[0]) && (components[0] < 3)) &&
                ((0 <= components[1]) && (components[1] < 40))) ;
    }

    /**
     * Checks whether the specified <CODE>Object</CODE> is equal to this <CODE>SnmpOid</CODE>.
     * <p>
     *  检查指定的<CODE>对象</CODE>是否等于此<CODE> SnmpOid </CODE>。
     * 
     * 
     * @param o The <CODE>Object</CODE> to be compared.
     * @return <CODE>true</CODE> if <CODE>o</CODE> is an <CODE>SnmpOid</CODE> instance and equal to this, <CODE>false</CODE> otherwise.
     */
    public boolean equals(Object o) {
        boolean result = false ;

        if (o instanceof SnmpOid) {
            SnmpOid oid = (SnmpOid)o ;
            if (oid.componentCount == componentCount) {
                int i = 0 ;
                long[]  objoid = oid.components;
                while ((i < componentCount) && (components[i] == objoid[i]))
                    i++ ;
                result = (i == componentCount) ;
            }
        }
        return result ;
    }

     /**
     * The hashCode is computed from the OID components.
     * <p>
     *  根据OID组件计算hashCode。
     * 
     * 
     * @return a hashCode for this SnmpOid.
     **/
    public int hashCode() {
        long acc=0;
        for (int i=0;i<componentCount;i++) {
            acc = acc*31+components[i];
        }
        return (int)acc;
    }

   /**
     * Compares two OIDs lexicographically.
     * <p>
     *  按字典顺序比较两个OID。
     * 
     * 
     * @param other The OID to be compared.
     * @return
     * The value 0 if the parameter <CODE>other</CODE> is equal to this <CODE>SnmpOid</CODE>.
     * A value smaller than 0 if this <CODE>SnmpOid</CODE> is lexicographically smaller than <CODE>other</CODE>.
     * A value larger than 0 if this <CODE>SnmpOid</CODE> is lexicographically larger than <CODE>other</CODE>.
     */
    public int compareTo(SnmpOid other) {
        int result = 0 ;
        int i = 0 ;
        int cmplen = Math.min(componentCount, other.componentCount) ;
        long[] otheroid = other.components;

        for (i = 0; i < cmplen; i++) {
            if (components[i] != otheroid[i]) {
                break ;
            }
        }
        if ((i == componentCount) && (i == other.componentCount)) {
            result = 0 ;
        }
        else if (i == componentCount) {
            result = -1 ;
        }
        else if (i == other.componentCount) {
            result = 1 ;
        }
        else {
            result = (components[i] < otheroid[i]) ? -1 : 1 ;
        }
        return result ;
    }

    /**
     * Resolves a MIB variable <CODE>String</CODE> with the MIB database.
     * <p>
     *  使用MIB数据库解析MIB变量<CODE> String </CODE>。
     * 
     * 
     * @param s The variable name to resolve.
     * @exception SnmpStatusException If the variable is not found in the MIB database.
     */
    public String resolveVarName(String s) throws SnmpStatusException {
        int index = s.indexOf('.') ;

        // First handle the case where oid is expressed as 1.2.3.4
        //
        try {
            return handleLong(s, index);
        } catch(NumberFormatException e) {}

        SnmpOidTable table = getSnmpOidTable();
        // if we are here, it means we have something to resolve..
        //
        if (table == null)
          throw new SnmpStatusException(SnmpStatusException.noSuchName);

        // Ok assume there is a variable name to resolve ...
        //
        if (index <= 0) {
            SnmpOidRecord rec = table.resolveVarName(s);
            return rec.getOid();

        } else {
            SnmpOidRecord rec = table.resolveVarName(s.substring(0, index));
            return (rec.getOid()+ s.substring(index));

        }
    }

    /**
     * Returns a textual description of the type object.
     * <p>
     *  返回类型对象的文本描述。
     * 
     * 
     * @return ASN.1 textual description.
     */
    public String getTypeName() {
        return name ;
    }

    /**
     * Returns the MIB table used for resolving MIB variable names.
     * <p>
     *  返回用于解析MIB变量名称的MIB表。
     * 
     * 
     * @return The MIB table.
     */
    public static SnmpOidTable getSnmpOidTable() {
        return meta;
    }

    /**
     * Sets the MIB table to use for resolving MIB variable names.
     * If no mib table is available, the class will not be able to resolve
     * names contained in the Object Identifier.
     * <p>
     *  设置用于解析MIB变量名称的MIB表。如果没有mib表可用,该类将无法解析对象标识符中包含的名称。
     * 
     * 
     * @param db The MIB table to use.
     * @throws SecurityException if the security manager is present and
     *         denies the access.
     */
    public static void setSnmpOidTable(SnmpOidTable db) {
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new SnmpPermission("setSnmpOidTable"));
        }

        meta = db;
    }

    /**
     * Converts an OID index converted string back to a DisplayString
     *
     * <p>
     *  将OID索引转换字符串转换回DisplayString
     * 
     * 
     **/
    public String toOctetString() {
        return new String(tobyte()) ;
    }


    // PRIVATE METHODS
    //------------------

    /**
     * convert the components array into a byte array
     * <p>
     *  将组件数组转换为字节数组
     * 
     * 
     **/
    private byte[] tobyte() {
        byte[] result = new byte[componentCount] ;
        for (int i =0 ; i < componentCount ; i++) {
            if (components[0] > 255) {
                throw new IllegalArgumentException() ;
            }
            result[i] = (byte)components[i] ;
        }
        return result ;
    }

    /**
     * Checks if there is enough space in the components
     * array to insert n new subids. If not, it increases the size of
     * the array.
     * In fact it reallocates a new array and copy the old one into the new one.
     * <p>
     *  检查components数组中是否有足够的空间插入n个新子标识。如果没有,它会增加数组的大小。事实上,它重新分配一个新的数组,并将旧的数组复制到新的数组。
     * 
     * 
     * @param n The number of subids to insert.
     */
    private void enlargeIfNeeded(int n) {
        int neededSize = components.length ;
        while (componentCount + n > neededSize) {
            neededSize = neededSize * 2 ;
        }
        if (neededSize > components.length) {
            long[] newComponents = new long[neededSize] ;
            for (int i = 0 ; i < components.length ; i++) {
                newComponents[i] = components[i] ;
            }
            components = newComponents ;
        }
    }

    // PRIVATE METHODS
    //----------------
    private String handleLong(String oid, int index) throws NumberFormatException, SnmpStatusException {
        String str;
        if (index >0) {
            str= oid.substring(0, index);
        } else {
            str= oid ;
        }

        // just parse the element.
        //
        Long.parseLong(str);
        return oid;
    }

    // VARIABLES
    //----------
    /**
     * The components' array.
     * <p>
     *  组件的数组。
     * 
     * 
     * @serial
     */
    protected long components[] = null ;

    /**
     * The length of the components' array.
     * <p>
     *  组件数组的长度。
     * 
     * 
     * @serial
     */
    protected int componentCount = 0 ;

    /**
     * The name of the type.
     * <p>
     *  类型的名称。
     * 
     */
    final static String  name = "Object Identifier";

    /**
     * Reference to a mib table. If no mib table is available,
     * the class will not be able to resolve names contained in the Object Identifier.
     * <p>
     *  引用mib表。如果没有mib表可用,该类将无法解析对象标识符中包含的名称。
     * 
     */
    private static SnmpOidTable meta= null;

    /**
     * Ensure serialization compatibility with version 4.1 FCS
     *
     * <p>
     *  确保与4.1版FCS的序列化兼容性
     */
    static final long serialVersionUID = 8956237235607885096L;
}

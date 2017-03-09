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


package javax.management.openmbean;


// java import
//
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// jmx import
//


/**
 * The <code>TabularType</code> class is the <i> open type</i> class
 * whose instances describe the types of {@link TabularData TabularData} values.
 *
 * <p>
 *  <code> TabularType </code>类是<i> open type </i>类,其实例描述{@link TabularData TabularData}值的类型。
 * 
 * 
 * @since 1.5
 */
public class TabularType extends OpenType<TabularData> {

    /* Serial version */
    static final long serialVersionUID = 6554071860220659261L;


    /**
    /* <p>
    /* 
     * @serial The composite type of rows
     */
    private CompositeType  rowType;

    /**
    /* <p>
    /* 
     * @serial The items used to index each row element, kept in the order the user gave
     *         This is an unmodifiable {@link ArrayList}
     */
    private List<String> indexNames;


    private transient Integer myHashCode = null; // As this instance is immutable, these two values
    private transient String  myToString = null; // need only be calculated once.


    /* *** Constructor *** */

    /**
     * Constructs a <code>TabularType</code> instance, checking for the validity of the given parameters.
     * The validity constraints are described below for each parameter.
     * <p>
     * The Java class name of tabular data values this tabular type represents
     * (ie the class name returned by the {@link OpenType#getClassName() getClassName} method)
     * is set to the string value returned by <code>TabularData.class.getName()</code>.
     * <p>
     * <p>
     *  构造一个<code> TabularType </code>实例,检查给定参数的有效性。下面针对每个参数描述有效性约束。
     * <p>
     *  此表格类型表示的表格数据值的Ja​​va类名称(即{@link OpenType#getClassName()getClassName}方法返回的类名称)设置为<code> TabularData.c
     * lass.getName()返回的字符串值, </code>。
     * <p>
     * 
     * @param  typeName  The name given to the tabular type this instance represents; cannot be a null or empty string.
     * <br>&nbsp;
     * @param  description  The human readable description of the tabular type this instance represents;
     *                      cannot be a null or empty string.
     * <br>&nbsp;
     * @param  rowType  The type of the row elements of tabular data values described by this tabular type instance;
     *                  cannot be null.
     * <br>&nbsp;
     * @param  indexNames  The names of the items the values of which are used to uniquely index each row element in the
     *                     tabular data values described by this tabular type instance;
     *                     cannot be null or empty. Each element should be an item name defined in <var>rowType</var>
     *                     (no null or empty string allowed).
     *                     It is important to note that the <b>order</b> of the item names in <var>indexNames</var>
     *                     is used by the methods {@link TabularData#get(java.lang.Object[]) get} and
     *                     {@link TabularData#remove(java.lang.Object[]) remove} of class
     *                     <code>TabularData</code> to match their array of values parameter to items.
     * <br>&nbsp;
     * @throws IllegalArgumentException  if <var>rowType</var> is null,
     *                                   or <var>indexNames</var> is a null or empty array,
     *                                   or an element in <var>indexNames</var> is a null or empty string,
     *                                   or <var>typeName</var> or <var>description</var> is a null or empty string.
     * <br>&nbsp;
     * @throws OpenDataException  if an element's value of <var>indexNames</var>
     *                            is not an item name defined in <var>rowType</var>.
     */
    public TabularType(String         typeName,
                       String         description,
                       CompositeType  rowType,
                       String[]       indexNames) throws OpenDataException {

        // Check and initialize state defined by parent.
        //
        super(TabularData.class.getName(), typeName, description, false);

        // Check rowType is not null
        //
        if (rowType == null) {
            throw new IllegalArgumentException("Argument rowType cannot be null.");
        }

        // Check indexNames is neither null nor empty and does not contain any null element or empty string
        //
        checkForNullElement(indexNames, "indexNames");
        checkForEmptyString(indexNames, "indexNames");

        // Check all indexNames values are valid item names for rowType
        //
        for (int i=0; i<indexNames.length; i++) {
            if ( ! rowType.containsKey(indexNames[i]) ) {
                throw new OpenDataException("Argument's element value indexNames["+ i +"]=\""+ indexNames[i] +
                                            "\" is not a valid item name for rowType.");
            }
        }

        // initialize rowType
        //
        this.rowType    = rowType;

        // initialize indexNames (copy content so that subsequent
        // modifs to the array referenced by the indexNames parameter
        // have no impact)
        //
        List<String> tmpList = new ArrayList<String>(indexNames.length + 1);
        for (int i=0; i<indexNames.length; i++) {
            tmpList.add(indexNames[i]);
        }
        this.indexNames = Collections.unmodifiableList(tmpList);
    }

    /**
     * Checks that Object[] arg is neither null nor empty (ie length==0)
     * and that it does not contain any null element.
     * <p>
     *  检查Object [] arg既不为null也不为空(即length == 0),并且它不包含任何空元素。
     * 
     */
    private static void checkForNullElement(Object[] arg, String argName) {
        if ( (arg == null) || (arg.length == 0) ) {
            throw new IllegalArgumentException("Argument "+ argName +"[] cannot be null or empty.");
        }
        for (int i=0; i<arg.length; i++) {
            if (arg[i] == null) {
                throw new IllegalArgumentException("Argument's element "+ argName +"["+ i +"] cannot be null.");
            }
        }
    }

    /**
     * Checks that String[] does not contain any empty (or blank characters only) string.
     * <p>
     *  检查String []不包含任何空(仅限空白字符)字符串。
     * 
     */
    private static void checkForEmptyString(String[] arg, String argName) {
        for (int i=0; i<arg.length; i++) {
            if (arg[i].trim().equals("")) {
                throw new IllegalArgumentException("Argument's element "+ argName +"["+ i +"] cannot be an empty string.");
            }
        }
    }


    /* *** Tabular type specific information methods *** */

    /**
     * Returns the type of the row elements of tabular data values
     * described by this <code>TabularType</code> instance.
     *
     * <p>
     *  返回由此<code> TabularType </code>实例描述的表格数据值的行元素的类型。
     * 
     * 
     * @return the type of each row.
     */
    public CompositeType getRowType() {

        return rowType;
    }

    /**
     * <p>Returns, in the same order as was given to this instance's
     * constructor, an unmodifiable List of the names of the items the
     * values of which are used to uniquely index each row element of
     * tabular data values described by this <code>TabularType</code>
     * instance.</p>
     *
     * <p>
     *  <p>以与给予此实例的构造函数相同的顺序返回项目名称的不可修改列表,其值用于唯一地索引由此<code> TabularType <>描述的表格数据值的每个行元素。 / code>实例。</p>
     * 
     * 
     * @return a List of String representing the names of the index
     * items.
     *
     */
    public List<String> getIndexNames() {

        return indexNames;
    }

    /**
     * Tests whether <var>obj</var> is a value which could be
     * described by this <code>TabularType</code> instance.
     *
     * <p>If <var>obj</var> is null or is not an instance of
     * <code>javax.management.openmbean.TabularData</code>,
     * <code>isValue</code> returns <code>false</code>.</p>
     *
     * <p>If <var>obj</var> is an instance of
     * <code>javax.management.openmbean.TabularData</code>, say {@code
     * td}, the result is true if this {@code TabularType} is
     * <em>assignable from</em> {@link TabularData#getTabularType()
     * td.getTabularType()}, as defined in {@link
     * CompositeType#isValue CompositeType.isValue}.</p>
     *
     * <p>
     *  测试<var> obj </var>是否是可以由此<code> TabularType </code>实例描述的值。
     * 
     * <p>如果<var> obj </var>为null或不是<code> javax.management.openmbean.TabularData </code>的实例,<code> isValue 
     * </code>返回<code> false <代码>。
     * </p>。
     * 
     *  <p>如果<var> obj </var>是<code> javax.management.openmbean.TabularData </code>的实例,请说{@code td},如果此{@code TabularType}
     * 可以从{@link CompositeType#isValue CompositeType.isValue}中定义的</em> {@link TabularData#getTabularType()td.getTabularType()}
     * 分配</p>。
     * 
     * 
     * @param obj the value whose open type is to be tested for
     * compatibility with this <code>TabularType</code> instance.
     *
     * @return <code>true</code> if <var>obj</var> is a value for this
     * tabular type, <code>false</code> otherwise.
     */
    public boolean isValue(Object obj) {

        // if obj is null or not a TabularData, return false
        //
        if (!(obj instanceof TabularData))
            return false;

        // if obj is not a TabularData, return false
        //
        TabularData value = (TabularData) obj;
        TabularType valueType = value.getTabularType();
        return isAssignableFrom(valueType);
    }

    @Override
    boolean isAssignableFrom(OpenType<?> ot) {
        if (!(ot instanceof TabularType))
            return false;
        TabularType tt = (TabularType) ot;
        if (!getTypeName().equals(tt.getTypeName()) ||
                !getIndexNames().equals(tt.getIndexNames()))
            return false;
        return getRowType().isAssignableFrom(tt.getRowType());
    }


    /* *** Methods overriden from class Object *** */

    /**
     * Compares the specified <code>obj</code> parameter with this <code>TabularType</code> instance for equality.
     * <p>
     * Two <code>TabularType</code> instances are equal if and only if all of the following statements are true:
     * <ul>
     * <li>their type names are equal</li>
     * <li>their row types are equal</li>
     * <li>they use the same index names, in the same order</li>
     * </ul>
     * <br>&nbsp;
     * <p>
     *  将指定的<code> obj </code>参数与此<code> TabularType </code>实例相比较。
     * <p>
     *  当且仅当所有以下语句都为真时,两个<code> TabularType </code>实例才相等：
     * <ul>
     *  <li>他们的类型名称相等</li> <li>他们的行类型相同</li> <li>他们使用相同的索引名称</li>
     * </ul>
     *  <br>&nbsp;
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>TabularType</code> instance;
     *              if <var>obj</var> is <code>null</code>, <code>equals</code> returns <code>false</code>.
     *
     * @return  <code>true</code> if the specified object is equal to this <code>TabularType</code> instance.
     */
    public boolean equals(Object obj) {

        // if obj is null, return false
        //
        if (obj == null) {
            return false;
        }

        // if obj is not a TabularType, return false
        //
        TabularType other;
        try {
            other = (TabularType) obj;
        } catch (ClassCastException e) {
            return false;
        }

        // Now, really test for equality between this TabularType instance and the other:
        //

        // their names should be equal
        if ( ! this.getTypeName().equals(other.getTypeName()) ) {
            return false;
        }

        // their row types should be equal
        if ( ! this.rowType.equals(other.rowType) ) {
            return false;
        }

        // their index names should be equal and in the same order (ensured by List.equals())
        if ( ! this.indexNames.equals(other.indexNames) ) {
            return false;
        }

        // All tests for equality were successfull
        //
        return true;
    }

    /**
     * Returns the hash code value for this <code>TabularType</code> instance.
     * <p>
     * The hash code of a <code>TabularType</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: name, row type, index names).
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>TabularType</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     * <p>
     * As <code>TabularType</code> instances are immutable, the hash code for this instance is calculated once,
     * on the first call to <code>hashCode</code>, and then the same value is returned for subsequent calls.
     *
     * <p>
     *  返回此<<div> TabularType </code>实例的哈希码值。
     * <p>
     *  <code> TabularType </code>实例的哈希码是在<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：名称,行类型,索引名称)。
     * 这确保<code> t1.equals(t2)</code>意味着任何两个<code> TabularType </code>实例的<code> t1.hashCode()== t2.hashCode(
     * )</代码> t1 </code>和<code> t2 </code>,这是方法{@link Object#hashCode()Object.hashCode()}的一般合同的要求。
     *  <code> TabularType </code>实例的哈希码是在<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：名称,行类型,索引名称)。
     * <p>
     * 由于<code> TabularType </code>实例是不可变的,所以在第一次调用<code> hashCode </code>时计算一次该实例的哈希码,然后为后续调用返回相同的值。
     * 
     * 
     * @return  the hash code value for this <code>TabularType</code> instance
     */
    public int hashCode() {

        // Calculate the hash code value if it has not yet been done (ie 1st call to hashCode())
        //
        if (myHashCode == null) {
            int value = 0;
            value += this.getTypeName().hashCode();
            value += this.rowType.hashCode();
            for (String index : indexNames)
                value += index.hashCode();
            myHashCode = Integer.valueOf(value);
        }

        // return always the same hash code for this instance (immutable)
        //
        return myHashCode.intValue();
    }

    /**
     * Returns a string representation of this <code>TabularType</code> instance.
     * <p>
     * The string representation consists of the name of this class (ie <code>javax.management.openmbean.TabularType</code>),
     * the type name for this instance, the row type string representation of this instance,
     * and the index names of this instance.
     * <p>
     * As <code>TabularType</code> instances are immutable, the string representation for this instance is calculated once,
     * on the first call to <code>toString</code>, and then the same value is returned for subsequent calls.
     *
     * <p>
     * 
     * @return  a string representation of this <code>TabularType</code> instance
     */
    public String toString() {

        // Calculate the string representation if it has not yet been done (ie 1st call to toString())
        //
        if (myToString == null) {
            final StringBuilder result = new StringBuilder()
                .append(this.getClass().getName())
                .append("(name=")
                .append(getTypeName())
                .append(",rowType=")
                .append(rowType.toString())
                .append(",indexNames=(");
            String sep = "";
            for (String index : indexNames) {
                result.append(sep).append(index);
                sep = ",";
            }
            result.append("))");
            myToString = result.toString();
        }

        // return always the same string representation for this instance (immutable)
        //
        return myToString;
    }

}

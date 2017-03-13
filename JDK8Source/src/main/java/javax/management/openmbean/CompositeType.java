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
import java.util.Set;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Iterator;

// jmx import
//


/**
 * The <code>CompositeType</code> class is the <i>open type</i> class
 * whose instances describe the types of {@link CompositeData CompositeData} values.
 *
 *
 * <p>
 *  <code> CompositeType </code>类是<i> open type </i>类,其实例描述{@link CompositeData CompositeData}值的类型
 * 
 * 
 * @since 1.5
 */
public class CompositeType extends OpenType<CompositeData> {

    /* Serial version */
    static final long serialVersionUID = -5366242454346948798L;

    /**
    /* <p>
    /* 
     * @serial Sorted mapping of the item names to their descriptions
     */
    private TreeMap<String,String> nameToDescription;

    /**
    /* <p>
    /* 
     * @serial Sorted mapping of the item names to their open types
     */
    private TreeMap<String,OpenType<?>> nameToType;

    /* As this instance is immutable, following three values need only
    /* <p>
    /* 
     * be calculated once.  */
    private transient Integer myHashCode = null;
    private transient String  myToString = null;
    private transient Set<String> myNamesSet = null;


    /* *** Constructor *** */

    /**
     * Constructs a <code>CompositeType</code> instance, checking for the validity of the given parameters.
     * The validity constraints are described below for each parameter.
     * <p>
     * Note that the contents of the three array parameters
     * <var>itemNames</var>, <var>itemDescriptions</var> and <var>itemTypes</var>
     * are internally copied so that any subsequent modification of these arrays by the caller of this constructor
     * has no impact on the constructed <code>CompositeType</code> instance.
     * <p>
     * The Java class name of composite data values this composite type represents
     * (ie the class name returned by the {@link OpenType#getClassName() getClassName} method)
     * is set to the string value returned by <code>CompositeData.class.getName()</code>.
     * <p>
     * <p>
     *  构造一个<code> CompositeType </code>实例,检查给定参数的有效性下面描述了每个参数的有效性约束
     * <p>
     *  请注意,内部复制了三个数组参数<var> itemNames </var>,<var> itemDescriptions </var>和<var> itemTypes </var>的内容,以便调用者对
     * 这些数组进行任何后续修改这个构造函数对构造的<code> CompositeType </code>实例没有影响。
     * <p>
     * 该复合类型表示的复合数据值的Ja​​va类名称(即{@link OpenType#getClassName()getClassName}方法返回的类名称)设置为<code> CompositeDatac
     * lassgetName()</code>。
     * <p>
     * 
     * @param  typeName  The name given to the composite type this instance represents; cannot be a null or empty string.
     * <br>&nbsp;
     * @param  description  The human readable description of the composite type this instance represents;
     *                      cannot be a null or empty string.
     * <br>&nbsp;
     * @param  itemNames  The names of the items contained in the
     *                    composite data values described by this <code>CompositeType</code> instance;
     *                    cannot be null and should contain at least one element; no element can be a null or empty string.
     *                    Note that the order in which the item names are given is not important to differentiate a
     *                    <code>CompositeType</code> instance from another;
     *                    the item names are internally stored sorted in ascending alphanumeric order.
     * <br>&nbsp;
     * @param  itemDescriptions  The descriptions, in the same order as <var>itemNames</var>, of the items contained in the
     *                           composite data values described by this <code>CompositeType</code> instance;
     *                           should be of the same size as <var>itemNames</var>;
     *                           no element can be null or an empty string.
     * <br>&nbsp;
     * @param  itemTypes  The open type instances, in the same order as <var>itemNames</var>, describing the items contained
     *                    in the composite data values described by this <code>CompositeType</code> instance;
     *                    should be of the same size as <var>itemNames</var>;
     *                    no element can be null.
     * <br>&nbsp;
     * @throws IllegalArgumentException  If <var>typeName</var> or <var>description</var> is a null or empty string,
     *                                   or <var>itemNames</var> or <var>itemDescriptions</var> or <var>itemTypes</var> is null,
     *                                   or any element of <var>itemNames</var> or <var>itemDescriptions</var>
     *                                   is a null or empty string,
     *                                   or any element of <var>itemTypes</var> is null,
     *                                   or <var>itemNames</var> or <var>itemDescriptions</var> or <var>itemTypes</var>
     *                                   are not of the same size.
     * <br>&nbsp;
     * @throws OpenDataException  If <var>itemNames</var> contains duplicate item names
     *                            (case sensitive, but leading and trailing whitespaces removed).
     */
    public CompositeType(String        typeName,
                         String        description,
                         String[]      itemNames,
                         String[]      itemDescriptions,
                         OpenType<?>[] itemTypes) throws OpenDataException {

        // Check and construct state defined by parent
        //
        super(CompositeData.class.getName(), typeName, description, false);

        // Check the 3 arrays are not null or empty (ie length==0) and that there is no null element or empty string in them
        //
        checkForNullElement(itemNames, "itemNames");
        checkForNullElement(itemDescriptions, "itemDescriptions");
        checkForNullElement(itemTypes, "itemTypes");
        checkForEmptyString(itemNames, "itemNames");
        checkForEmptyString(itemDescriptions, "itemDescriptions");

        // Check the sizes of the 3 arrays are the same
        //
        if ( (itemNames.length != itemDescriptions.length) || (itemNames.length != itemTypes.length) ) {
            throw new IllegalArgumentException("Array arguments itemNames[], itemDescriptions[] and itemTypes[] "+
                                               "should be of same length (got "+ itemNames.length +", "+
                                               itemDescriptions.length +" and "+ itemTypes.length +").");
        }

        // Initialize internal "names to descriptions" and "names to types" sorted maps,
        // and, by doing so, check there are no duplicate item names
        //
        nameToDescription = new TreeMap<String,String>();
        nameToType        = new TreeMap<String,OpenType<?>>();
        String key;
        for (int i=0; i<itemNames.length; i++) {
            key = itemNames[i].trim();
            if (nameToDescription.containsKey(key)) {
                throw new OpenDataException("Argument's element itemNames["+ i +"]=\""+ itemNames[i] +
                                            "\" duplicates a previous item names.");
            }
            nameToDescription.put(key, itemDescriptions[i].trim());
            nameToType.put(key, itemTypes[i]);
        }
    }

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

    private static void checkForEmptyString(String[] arg, String argName) {
        for (int i=0; i<arg.length; i++) {
            if (arg[i].trim().equals("")) {
                throw new IllegalArgumentException("Argument's element "+ argName +"["+ i +"] cannot be an empty string.");
            }
        }
    }

    /* *** Composite type specific information methods *** */

    /**
     * Returns <code>true</code> if this <code>CompositeType</code> instance defines an item
     * whose name is <var>itemName</var>.
     *
     * <p>
     *  如果<code> CompositeType </code>实例定义名称为<var> itemName </var>的项目,则返回<code> true </code>
     * 
     * 
     * @param itemName the name of the item.
     *
     * @return true if an item of this name is present.
     */
    public boolean containsKey(String itemName) {

        if (itemName == null) {
            return false;
        }
        return nameToDescription.containsKey(itemName);
    }

    /**
     * Returns the description of the item whose name is <var>itemName</var>,
     * or <code>null</code> if this <code>CompositeType</code> instance does not define any item
     * whose name is <var>itemName</var>.
     *
     * <p>
     *  如果此<> CompositeType </code>实例未定义任何名称为<var> itemName的项目,则返回名称为<var> itemName </var>或<code> null </code>
     *  </var>。
     * 
     * 
     * @param itemName the name of the item.
     *
     * @return the description.
     */
    public String getDescription(String itemName) {

        if (itemName == null) {
            return null;
        }
        return nameToDescription.get(itemName);
    }

    /**
     * Returns the <i>open type</i> of the item whose name is <var>itemName</var>,
     * or <code>null</code> if this <code>CompositeType</code> instance does not define any item
     * whose name is <var>itemName</var>.
     *
     * <p>
     *  返回名称为<var> itemName </var>或<code> null </code>的项目的<i>打开类型</i>,如果此<> CompositeType </code>实例未定义任何名称为<var>
     *  itemName </var>的项目。
     * 
     * 
     * @param itemName the name of the time.
     *
     * @return the type.
     */
    public OpenType<?> getType(String itemName) {

        if (itemName == null) {
            return null;
        }
        return (OpenType<?>) nameToType.get(itemName);
    }

    /**
     * Returns an unmodifiable Set view of all the item names defined by this <code>CompositeType</code> instance.
     * The set's iterator will return the item names in ascending order.
     *
     * <p>
     * 返回由此<code> CompositeType </code>实例定义的所有项目名称的不可修改的Set视图集合的迭代器将按升序返回项目名称
     * 
     * 
     * @return a {@link Set} of {@link String}.
     */
    public Set<String> keySet() {

        // Initializes myNamesSet on first call
        if (myNamesSet == null) {
            myNamesSet = Collections.unmodifiableSet(nameToDescription.keySet());
        }

        return myNamesSet; // always return the same value
    }


    /**
     * Tests whether <var>obj</var> is a value which could be
     * described by this <code>CompositeType</code> instance.
     *
     * <p>If <var>obj</var> is null or is not an instance of
     * <code>javax.management.openmbean.CompositeData</code>,
     * <code>isValue</code> returns <code>false</code>.</p>
     *
     * <p>If <var>obj</var> is an instance of
     * <code>javax.management.openmbean.CompositeData</code>, then let
     * {@code ct} be its {@code CompositeType} as returned by {@link
     * CompositeData#getCompositeType()}.  The result is true if
     * {@code this} is <em>assignable from</em> {@code ct}.  This
     * means that:</p>
     *
     * <ul>
     * <li>{@link #getTypeName() this.getTypeName()} equals
     * {@code ct.getTypeName()}, and
     * <li>there are no item names present in {@code this} that are
     * not also present in {@code ct}, and
     * <li>for every item in {@code this}, its type is assignable from
     * the type of the corresponding item in {@code ct}.
     * </ul>
     *
     * <p>A {@code TabularType} is assignable from another {@code
     * TabularType} if they have the same {@linkplain
     * TabularType#getTypeName() typeName} and {@linkplain
     * TabularType#getIndexNames() index name list}, and the
     * {@linkplain TabularType#getRowType() row type} of the first is
     * assignable from the row type of the second.
     *
     * <p>An {@code ArrayType} is assignable from another {@code
     * ArrayType} if they have the same {@linkplain
     * ArrayType#getDimension() dimension}; and both are {@linkplain
     * ArrayType#isPrimitiveArray() primitive arrays} or neither is;
     * and the {@linkplain ArrayType#getElementOpenType() element
     * type} of the first is assignable from the element type of the
     * second.
     *
     * <p>In every other case, an {@code OpenType} is assignable from
     * another {@code OpenType} only if they are equal.</p>
     *
     * <p>These rules mean that extra items can be added to a {@code
     * CompositeData} without making it invalid for a {@code CompositeType}
     * that does not have those items.</p>
     *
     * <p>
     *  测试<var> obj </var>是否是可由此<code> CompositeType </code>实例描述的值
     * 
     *  <p>如果<var> obj </var>为null或不是<code> javaxmanagementopenmbeanCompositeData </code>的实例,<code> isValue 
     * </code>返回<code> false </code>。
     * 
     *  <p>如果<var> obj </var>是<code> javaxmanagementopenmbeanCompositeData </code>的实例,则让{@code ct}成为{@link CompositeData#getCompositeType }
     * 如果{@code this}可以从</em> {@code ct}分配,结果为true。
     * </p>。
     * 
     * <ul>
     * <li> {@ link #getTypeName()thisgetTypeName()} equals {@code ctgetTypeName()},并且<li>在{@code this}中没有出现
     * 在{@code ct}中的项目名称和<li>对{@code this}中的每个项目,其类型可以从{@code ct}中相应项目的类型分配。
     * </ul>
     * 
     *  <p>如果{@code TabularType}具有相同的{@linkplain TabularType#getTypeName()typeName}和{@linkplain TabularType#getIndexNames()索引名称列表}
     * ,{@code TabularType}可以从另一个{@code TabularType} @linkplain TabularType#getRowType()row type}的第一个是可从第二个行
     * 的类型中赋值的。
     * 
     * <p>如果{@code ArrayType}具有相同的{@linkplain ArrayType#getDimension()dimension},则可以从另一个{@code ArrayType}赋值;
     * 并且都是{@linkplain ArrayType#isPrimitiveArray()原始数组}或两者都不是;并且第一个的{@linkplain ArrayType#getElementOpenType()元素类型}
     * 可以从第二个的元素类型。
     * 
     *  <p>在其他任何情况下,{@code OpenType}只能在其他{@code OpenType}相等时分配。</p>
     * 
     *  <p>这些规则意味着,对于没有这些项目的{@code CompositeType},可以将额外的项目添加到{@code CompositeData},而不使其无效。</p>
     * 
     * 
     * @param  obj  the value whose open type is to be tested for compatibility
     * with this <code>CompositeType</code> instance.
     *
     * @return <code>true</code> if <var>obj</var> is a value for this
     * composite type, <code>false</code> otherwise.
     */
    public boolean isValue(Object obj) {

        // if obj is null or not CompositeData, return false
        //
        if (!(obj instanceof CompositeData)) {
            return false;
        }

        // if obj is not a CompositeData, return false
        //
        CompositeData value = (CompositeData) obj;

        // test value's CompositeType is assignable to this CompositeType instance
        //
        CompositeType valueType = value.getCompositeType();
        return this.isAssignableFrom(valueType);
    }

    /**
     * Tests whether values of the given type can be assigned to this
     * open type.  The result is true if the given type is also a
     * CompositeType with the same name ({@link #getTypeName()}), and
     * every item in this type is also present in the given type with
     * the same name and assignable type.  There can be additional
     * items in the given type, which are ignored.
     *
     * <p>
     * 测试给定类型的值是否可以分配给此开放类型如果给定类型也是具有相同名称({@link #getTypeName()})的CompositeType,则结果为true,并且此类型中的每个项目也存在在给定类型
     * 中具有相同的名称和可分配类型在给定类型中可以有其他项目,它们被忽略。
     * 
     * 
     * @param ot the type to be tested.
     *
     * @return true if {@code ot} is assignable to this open type.
     */
    @Override
    boolean isAssignableFrom(OpenType<?> ot) {
        if (!(ot instanceof CompositeType))
            return false;
        CompositeType ct = (CompositeType) ot;
        if (!ct.getTypeName().equals(getTypeName()))
            return false;
        for (String key : keySet()) {
            OpenType<?> otItemType = ct.getType(key);
            OpenType<?> thisItemType = getType(key);
            if (otItemType == null ||
                    !thisItemType.isAssignableFrom(otItemType))
                return false;
        }
        return true;
    }


    /* *** Methods overriden from class Object *** */

    /**
     * Compares the specified <code>obj</code> parameter with this <code>CompositeType</code> instance for equality.
     * <p>
     * Two <code>CompositeType</code> instances are equal if and only if all of the following statements are true:
     * <ul>
     * <li>their type names are equal</li>
     * <li>their items' names and types are equal</li>
     * </ul>
     * <br>&nbsp;
     * <p>
     *  将指定的<code> obj </code>参数与此<code> CompositeType </code>实例进行比较以确保相等
     * <p>
     *  当且仅当所有以下语句都为真时,两个<code> CompositeType </code>实例才相等：
     * <ul>
     *  <li>其类型名称相等</li> <li>其项目的名称和类型相等</li>
     * </ul>
     *  <br>&nbsp;
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>CompositeType</code> instance;
     *              if <var>obj</var> is <code>null</code>, <code>equals</code> returns <code>false</code>.
     *
     * @return  <code>true</code> if the specified object is equal to this <code>CompositeType</code> instance.
     */
    public boolean equals(Object obj) {

        // if obj is null, return false
        //
        if (obj == null) {
            return false;
        }

        // if obj is not a CompositeType, return false
        //
        CompositeType other;
        try {
            other = (CompositeType) obj;
        } catch (ClassCastException e) {
            return false;
        }

        // Now, really test for equality between this CompositeType instance and the other
        //

        // their names should be equal
        if ( ! this.getTypeName().equals(other.getTypeName()) ) {
            return false;
        }

        // their items names and types should be equal
        if ( ! this.nameToType.equals(other.nameToType) ) {
            return false;
        }

        // All tests for equality were successfull
        //
        return true;
    }

    /**
     * Returns the hash code value for this <code>CompositeType</code> instance.
     * <p>
     * The hash code of a <code>CompositeType</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons
     * (ie: name, items names, items types).
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code>
     * for any two <code>CompositeType</code> instances <code>t1</code> and <code>t2</code>,
     * as required by the general contract of the method
     * {@link Object#hashCode() Object.hashCode()}.
     * <p>
     * As <code>CompositeType</code> instances are immutable, the hash code for this instance is calculated once,
     * on the first call to <code>hashCode</code>, and then the same value is returned for subsequent calls.
     *
     * <p>
     *  返回此<> CompositeType </code>实例的哈希码值
     * <p>
     * <code> CompositeType </code>实例的哈希码是在<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：名称,项目名称,项目类型)。
     * 这确保对于任何两个<code> CompositeType </code>实例<code> t1 </code>,<code> t1equals(t2)</code>意味着<code> t1hashCo
     * de()== t2hashCode()</code> <code> t2 </code>,根据方法的一般合同{@link Object#hashCode()ObjecthashCode()}。
     * <code> CompositeType </code>实例的哈希码是在<code> equals </code>比较中使用的所有信息元素的哈希码的总和(即：名称,项目名称,项目类型)。
     * <p>
     *  由于<code> CompositeType </code>实例是不可变的,所以在第一次调用<code> hashCode </code>时计算一次该实例的哈希码,然后为后续调用返回相同的值
     * 
     * @return  the hash code value for this <code>CompositeType</code> instance
     */
    public int hashCode() {

        // Calculate the hash code value if it has not yet been done (ie 1st call to hashCode())
        //
        if (myHashCode == null) {
            int value = 0;
            value += this.getTypeName().hashCode();
            for (String key : nameToDescription.keySet()) {
                value += key.hashCode();
                value += this.nameToType.get(key).hashCode();
            }
            myHashCode = Integer.valueOf(value);
        }

        // return always the same hash code for this instance (immutable)
        //
        return myHashCode.intValue();
    }

    /**
     * Returns a string representation of this <code>CompositeType</code> instance.
     * <p>
     * The string representation consists of
     * the name of this class (ie <code>javax.management.openmbean.CompositeType</code>), the type name for this instance,
     * and the list of the items names and types string representation of this instance.
     * <p>
     * As <code>CompositeType</code> instances are immutable, the string representation for this instance is calculated once,
     * on the first call to <code>toString</code>, and then the same value is returned for subsequent calls.
     *
     * <p>
     * 
     * 
     * @return  a string representation of this <code>CompositeType</code> instance
     */
    public String toString() {

        // Calculate the string representation if it has not yet been done (ie 1st call to toString())
        //
        if (myToString == null) {
            final StringBuilder result = new StringBuilder();
            result.append(this.getClass().getName());
            result.append("(name=");
            result.append(getTypeName());
            result.append(",items=(");
            int i=0;
            Iterator<String> k=nameToType.keySet().iterator();
            String key;
            while (k.hasNext()) {
                key = k.next();
                if (i > 0) result.append(",");
                result.append("(itemName=");
                result.append(key);
                result.append(",itemType=");
                result.append(nameToType.get(key).toString() +")");
                i++;
            }
            result.append("))");
            myToString = result.toString();
        }

        // return always the same string representation for this instance (immutable)
        //
        return myToString;
    }

}

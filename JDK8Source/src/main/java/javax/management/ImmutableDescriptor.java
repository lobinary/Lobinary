/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;

import com.sun.jmx.mbeanserver.Util;
import java.io.InvalidObjectException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * An immutable descriptor.
 * <p>
 *  一个不变的描述符。
 * 
 * 
 * @since 1.6
 */
public class ImmutableDescriptor implements Descriptor {
    private static final long serialVersionUID = 8853308591080540165L;

    /**
     * The names of the fields in this ImmutableDescriptor with their
     * original case.  The names must be in alphabetical order as determined
     * by {@link String#CASE_INSENSITIVE_ORDER}.
     * <p>
     *  此ImmutableDescriptor中的字段名称及其原始大小写。名称必须按字母顺序排列,由{@link String#CASE_INSENSITIVE_ORDER}确定。
     * 
     */
    private final String[] names;
    /**
     * The values of the fields in this ImmutableDescriptor.  The
     * elements in this array match the corresponding elements in the
     * {@code names} array.
     * <p>
     *  此ImmutableDescriptor中的字段的值。此数组中的元素与{@code names}数组中的相应元素相匹配。
     * 
     */
    private final Object[] values;

    private transient int hashCode = -1;

    /**
     * An empty descriptor.
     * <p>
     *  空描述符。
     * 
     */
    public static final ImmutableDescriptor EMPTY_DESCRIPTOR =
            new ImmutableDescriptor();

    /**
     * Construct a descriptor containing the given fields and values.
     *
     * <p>
     *  构造一个包含给定字段和值的描述符。
     * 
     * 
     * @throws IllegalArgumentException if either array is null, or
     * if the arrays have different sizes, or
     * if a field name is null or empty, or if the same field name
     * appears more than once.
     */
    public ImmutableDescriptor(String[] fieldNames, Object[] fieldValues) {
        this(makeMap(fieldNames, fieldValues));
    }

    /**
     * Construct a descriptor containing the given fields.  Each String
     * must be of the form {@code fieldName=fieldValue}.  The field name
     * ends at the first {@code =} character; for example if the String
     * is {@code a=b=c} then the field name is {@code a} and its value
     * is {@code b=c}.
     *
     * <p>
     *  构造包含给定字段的描述符。每个String的格式必须为{@code fieldName = fieldValue}。
     * 字段名以第一个{@code =}字符结束;例如如果String是{@code a = b = c},则字段名称为{@code a},其值为{@code b = c}。
     * 
     * 
     * @throws IllegalArgumentException if the parameter is null, or
     * if a field name is empty, or if the same field name appears
     * more than once, or if one of the strings does not contain
     * an {@code =} character.
     */
    public ImmutableDescriptor(String... fields) {
        this(makeMap(fields));
    }

    /**
     * <p>Construct a descriptor where the names and values of the fields
     * are the keys and values of the given Map.</p>
     *
     * <p>
     *  <p>构造一个描述符,其中字段的名称和值是给定Map的键和值。</p>
     * 
     * 
     * @throws IllegalArgumentException if the parameter is null, or
     * if a field name is null or empty, or if the same field name appears
     * more than once (which can happen because field names are not case
     * sensitive).
     */
    public ImmutableDescriptor(Map<String, ?> fields) {
        if (fields == null)
            throw new IllegalArgumentException("Null Map");
        SortedMap<String, Object> map =
                new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
        for (Map.Entry<String, ?> entry : fields.entrySet()) {
            String name = entry.getKey();
            if (name == null || name.equals(""))
                throw new IllegalArgumentException("Empty or null field name");
            if (map.containsKey(name))
                throw new IllegalArgumentException("Duplicate name: " + name);
            map.put(name, entry.getValue());
        }
        int size = map.size();
        this.names = map.keySet().toArray(new String[size]);
        this.values = map.values().toArray(new Object[size]);
    }

    /**
     * This method can replace a deserialized instance of this
     * class with another instance.  For example, it might replace
     * a deserialized empty ImmutableDescriptor with
     * {@link #EMPTY_DESCRIPTOR}.
     *
     * <p>
     *  此方法可以用另一个实例替换此类的反序列化实例。例如,它可以用{@link #EMPTY_DESCRIPTOR}替换反序列化的空ImmutableDescriptor。
     * 
     * 
     * @return the replacement object, which may be {@code this}.
     *
     * @throws InvalidObjectException if the read object has invalid fields.
     */
    private Object readResolve() throws InvalidObjectException {

        boolean bad = false;
        if (names == null || values == null || names.length != values.length)
            bad = true;
        if (!bad) {
            if (names.length == 0 && getClass() == ImmutableDescriptor.class)
                return EMPTY_DESCRIPTOR;
            final Comparator<String> compare = String.CASE_INSENSITIVE_ORDER;
            String lastName = ""; // also catches illegal null name
            for (int i = 0; i < names.length; i++) {
                if (names[i] == null ||
                        compare.compare(lastName, names[i]) >= 0) {
                    bad = true;
                    break;
                }
                lastName = names[i];
            }
        }
        if (bad)
            throw new InvalidObjectException("Bad names or values");

        return this;
    }

    private static SortedMap<String, ?> makeMap(String[] fieldNames,
                                                Object[] fieldValues) {
        if (fieldNames == null || fieldValues == null)
            throw new IllegalArgumentException("Null array parameter");
        if (fieldNames.length != fieldValues.length)
            throw new IllegalArgumentException("Different size arrays");
        SortedMap<String, Object> map =
                new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < fieldNames.length; i++) {
            String name = fieldNames[i];
            if (name == null || name.equals(""))
                throw new IllegalArgumentException("Empty or null field name");
            Object old = map.put(name, fieldValues[i]);
            if (old != null) {
                throw new IllegalArgumentException("Duplicate field name: " +
                                                   name);
            }
        }
        return map;
    }

    private static SortedMap<String, ?> makeMap(String[] fields) {
        if (fields == null)
            throw new IllegalArgumentException("Null fields parameter");
        String[] fieldNames = new String[fields.length];
        String[] fieldValues = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            int eq = field.indexOf('=');
            if (eq < 0) {
                throw new IllegalArgumentException("Missing = character: " +
                                                   field);
            }
            fieldNames[i] = field.substring(0, eq);
            // makeMap will catch the case where the name is empty
            fieldValues[i] = field.substring(eq + 1);
        }
        return makeMap(fieldNames, fieldValues);
    }

    /**
     * <p>Return an {@code ImmutableDescriptor} whose contents are the union of
     * the given descriptors.  Every field name that appears in any of
     * the descriptors will appear in the result with the
     * value that it has when the method is called.  Subsequent changes
     * to any of the descriptors do not affect the ImmutableDescriptor
     * returned here.</p>
     *
     * <p>In the simplest case, there is only one descriptor and the
     * returned {@code ImmutableDescriptor} is a copy of its fields at the
     * time this method is called:</p>
     *
     * <pre>
     * Descriptor d = something();
     * ImmutableDescriptor copy = ImmutableDescriptor.union(d);
     * </pre>
     *
     * <p>
     *  <p>返回一个{@code ImmutableDescriptor},其内容是给定描述符的并集。在任何描述符中出现的每个字段名称将显示在调用该方法时具有的值的结果中。
     * 对任何描述符的后续更改不会影响此处返回的ImmutableDescriptor。</p>。
     * 
     * <p>在最简单的情况下,只有一个描述符,返回的{@code ImmutableDescriptor}是在调用此方法时其字段的副本：</p>
     * 
     * <pre>
     *  描述符d = something(); ImmutableDescriptor copy = ImmutableDescriptor.union(d);
     * </pre>
     * 
     * 
     * @param descriptors the descriptors to be combined.  Any of the
     * descriptors can be null, in which case it is skipped.
     *
     * @return an {@code ImmutableDescriptor} that is the union of the given
     * descriptors.  The returned object may be identical to one of the
     * input descriptors if it is an ImmutableDescriptor that contains all of
     * the required fields.
     *
     * @throws IllegalArgumentException if two Descriptors contain the
     * same field name with different associated values.  Primitive array
     * values are considered the same if they are of the same type with
     * the same elements.  Object array values are considered the same if
     * {@link Arrays#deepEquals(Object[],Object[])} returns true.
     */
    public static ImmutableDescriptor union(Descriptor... descriptors) {
        // Optimize the case where exactly one Descriptor is non-Empty
        // and it is immutable - we can just return it.
        int index = findNonEmpty(descriptors, 0);
        if (index < 0)
            return EMPTY_DESCRIPTOR;
        if (descriptors[index] instanceof ImmutableDescriptor
                && findNonEmpty(descriptors, index + 1) < 0)
            return (ImmutableDescriptor) descriptors[index];

        Map<String, Object> map =
            new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
        ImmutableDescriptor biggestImmutable = EMPTY_DESCRIPTOR;
        for (Descriptor d : descriptors) {
            if (d != null) {
                String[] names;
                if (d instanceof ImmutableDescriptor) {
                    ImmutableDescriptor id = (ImmutableDescriptor) d;
                    names = id.names;
                    if (id.getClass() == ImmutableDescriptor.class
                            && names.length > biggestImmutable.names.length)
                        biggestImmutable = id;
                } else
                    names = d.getFieldNames();
                for (String n : names) {
                    Object v = d.getFieldValue(n);
                    Object old = map.put(n, v);
                    if (old != null) {
                        boolean equal;
                        if (old.getClass().isArray()) {
                            equal = Arrays.deepEquals(new Object[] {old},
                                                      new Object[] {v});
                        } else
                            equal = old.equals(v);
                        if (!equal) {
                            final String msg =
                                "Inconsistent values for descriptor field " +
                                n + ": " + old + " :: " + v;
                            throw new IllegalArgumentException(msg);
                        }
                    }
                }
            }
        }
        if (biggestImmutable.names.length == map.size())
            return biggestImmutable;
        return new ImmutableDescriptor(map);
    }

    private static boolean isEmpty(Descriptor d) {
        if (d == null)
            return true;
        else if (d instanceof ImmutableDescriptor)
            return ((ImmutableDescriptor) d).names.length == 0;
        else
            return (d.getFieldNames().length == 0);
    }

    private static int findNonEmpty(Descriptor[] ds, int start) {
        for (int i = start; i < ds.length; i++) {
            if (!isEmpty(ds[i]))
                return i;
        }
        return -1;
    }

    private int fieldIndex(String name) {
        return Arrays.binarySearch(names, name, String.CASE_INSENSITIVE_ORDER);
    }

    public final Object getFieldValue(String fieldName) {
        checkIllegalFieldName(fieldName);
        int i = fieldIndex(fieldName);
        if (i < 0)
            return null;
        Object v = values[i];
        if (v == null || !v.getClass().isArray())
            return v;
        if (v instanceof Object[])
            return ((Object[]) v).clone();
        // clone the primitive array, could use an 8-way if/else here
        int len = Array.getLength(v);
        Object a = Array.newInstance(v.getClass().getComponentType(), len);
        System.arraycopy(v, 0, a, 0, len);
        return a;
    }

    public final String[] getFields() {
        String[] result = new String[names.length];
        for (int i = 0; i < result.length; i++) {
            Object value = values[i];
            if (value == null)
                value = "";
            else if (!(value instanceof String))
                value = "(" + value + ")";
            result[i] = names[i] + "=" + value;
        }
        return result;
    }

    public final Object[] getFieldValues(String... fieldNames) {
        if (fieldNames == null)
            return values.clone();
        Object[] result = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            String name = fieldNames[i];
            if (name != null && !name.equals(""))
                result[i] = getFieldValue(name);
        }
        return result;
    }

    public final String[] getFieldNames() {
        return names.clone();
    }

    /**
     * Compares this descriptor to the given object.  The objects are equal if
     * the given object is also a Descriptor, and if the two Descriptors have
     * the same field names (possibly differing in case) and the same
     * associated values.  The respective values for a field in the two
     * Descriptors are equal if the following conditions hold:
     *
     * <ul>
     * <li>If one value is null then the other must be too.</li>
     * <li>If one value is a primitive array then the other must be a primitive
     * array of the same type with the same elements.</li>
     * <li>If one value is an object array then the other must be too and
     * {@link Arrays#deepEquals(Object[],Object[])} must return true.</li>
     * <li>Otherwise {@link Object#equals(Object)} must return true.</li>
     * </ul>
     *
     * <p>
     *  将此描述符与给定对象进行比较。如果给定对象也是描述符,并且如果两个描述符具有相同的字段名(在情况下可能不同)和相同的关联值,则对象是相等的。如果以下条件成立,则两个描述符中的字段的相应值相等：
     * 
     * <ul>
     *  <li>如果一个值为null,那么另一个值也必须为空。</li> <li>如果一个值是基本数组,则另一个值必须是具有相同元素的相同类型的基本数组。
     * </li> <li>如果一个值是一个对象数组,那么另一个值也必须是对象数组,并且{@link Arrays#deepEquals(Object [],Object [])}必须​​返回true。
     * </li> <li>否则{@link Object# equals(Object)}必须返回true。</li>。
     * </ul>
     * 
     * 
     * @param o the object to compare with.
     *
     * @return {@code true} if the objects are the same; {@code false}
     * otherwise.
     *
     */
    // Note: this Javadoc is copied from javax.management.Descriptor
    //       due to 6369229.
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Descriptor))
            return false;
        String[] onames;
        if (o instanceof ImmutableDescriptor) {
            onames = ((ImmutableDescriptor) o).names;
        } else {
            onames = ((Descriptor) o).getFieldNames();
            Arrays.sort(onames, String.CASE_INSENSITIVE_ORDER);
        }
        if (names.length != onames.length)
            return false;
        for (int i = 0; i < names.length; i++) {
            if (!names[i].equalsIgnoreCase(onames[i]))
                return false;
        }
        Object[] ovalues;
        if (o instanceof ImmutableDescriptor)
            ovalues = ((ImmutableDescriptor) o).values;
        else
            ovalues = ((Descriptor) o).getFieldValues(onames);
        return Arrays.deepEquals(values, ovalues);
    }

    /**
     * <p>Returns the hash code value for this descriptor.  The hash
     * code is computed as the sum of the hash codes for each field in
     * the descriptor.  The hash code of a field with name {@code n}
     * and value {@code v} is {@code n.toLowerCase().hashCode() ^ h}.
     * Here {@code h} is the hash code of {@code v}, computed as
     * follows:</p>
     *
     * <ul>
     * <li>If {@code v} is null then {@code h} is 0.</li>
     * <li>If {@code v} is a primitive array then {@code h} is computed using
     * the appropriate overloading of {@code java.util.Arrays.hashCode}.</li>
     * <li>If {@code v} is an object array then {@code h} is computed using
     * {@link Arrays#deepHashCode(Object[])}.</li>
     * <li>Otherwise {@code h} is {@code v.hashCode()}.</li>
     * </ul>
     *
     * <p>
     *  <p>返回此描述符的哈希码值。哈希码被计算为描述符中的每个字段的哈希码的和。名为{@code n}和值{@code v}的字段的哈希码是{@code n.toLowerCase()。
     * hashCode()^ h}。这里{@code h}是{@code v}的哈希码,计算如下：</p>。
     * 
     * <ul>
     * <li>如果{@code v}为null,则{@code h}为0。
     * </li> <li>如果{@code v}是原始数组,则{@code h} {@code java.util.Arrays.hashCode}。
     * </li> <li>如果{@code v}是对象数组,则{@code h}使用{@link Arrays#deepHashCode(Object [])} </li> <li>否则{@code h}是{@code v.hashCode()}
     * 。
     * </li> <li>如果{@code v}是原始数组,则{@code h} {@code java.util.Arrays.hashCode}。</li>。
     * </ul>
     * 
     * 
     * @return A hash code value for this object.
     *
     */
    // Note: this Javadoc is copied from javax.management.Descriptor
    //       due to 6369229.
    @Override
    public int hashCode() {
        if (hashCode == -1) {
            hashCode = Util.hashCode(names, values);
        }
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < names.length; i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(names[i]).append("=");
            Object v = values[i];
            if (v != null && v.getClass().isArray()) {
                String s = Arrays.deepToString(new Object[] {v});
                s = s.substring(1, s.length() - 1); // remove [...]
                v = s;
            }
            sb.append(String.valueOf(v));
        }
        return sb.append("}").toString();
    }

    /**
     * Returns true if all of the fields have legal values given their
     * names.  This method always returns true, but a subclass can
     * override it to return false when appropriate.
     *
     * <p>
     *  如果所有字段都具有指定名称的合法值,则返回true。此方法总是返回true,但是子类可以覆盖它,以在适当时返回false。
     * 
     * 
     * @return true if the values are legal.
     *
     * @exception RuntimeOperationsException if the validity checking fails.
     * The method returns false if the descriptor is not valid, but throws
     * this exception if the attempt to determine validity fails.
     */
    public boolean isValid() {
        return true;
    }

    /**
     * <p>Returns a descriptor which is equal to this descriptor.
     * Changes to the returned descriptor will have no effect on this
     * descriptor, and vice versa.</p>
     *
     * <p>This method returns the object on which it is called.
     * A subclass can override it
     * to return another object provided the contract is respected.
     *
     * <p>
     *  <p>返回等于此描述符的描述符。对返回的描述符的更改对此描述符没有影响,反之亦然。</p>
     * 
     *  <p>此方法返回调用它的对象。子类可以覆盖它以返回另一个对象,只要合同受到尊重。
     * 
     * 
     * @exception RuntimeOperationsException for illegal value for field Names
     * or field Values.
     * If the descriptor construction fails for any reason, this exception will
     * be thrown.
     */
    @Override
    public Descriptor clone() {
        return this;
    }

    /**
     * This operation is unsupported since this class is immutable.  If
     * this call would change a mutable descriptor with the same contents,
     * then a {@link RuntimeOperationsException} wrapping an
     * {@link UnsupportedOperationException} is thrown.  Otherwise,
     * the behavior is the same as it would be for a mutable descriptor:
     * either an exception is thrown because of illegal parameters, or
     * there is no effect.
     * <p>
     *  此操作不受支持,因为此类是不可变的。
     * 如果此调用将更改具有相同内容的可变描述符,则会抛出{@link RuntimeOperationsException}包装{@link UnsupportedOperationException}。
     * 否则,行为与可变描述符的行为相同：或者由于非法参数抛出异常,或者没有效果。
     * 
     */
    public final void setFields(String[] fieldNames, Object[] fieldValues)
        throws RuntimeOperationsException {
        if (fieldNames == null || fieldValues == null)
            illegal("Null argument");
        if (fieldNames.length != fieldValues.length)
            illegal("Different array sizes");
        for (int i = 0; i < fieldNames.length; i++)
            checkIllegalFieldName(fieldNames[i]);
        for (int i = 0; i < fieldNames.length; i++)
            setField(fieldNames[i], fieldValues[i]);
    }

    /**
     * This operation is unsupported since this class is immutable.  If
     * this call would change a mutable descriptor with the same contents,
     * then a {@link RuntimeOperationsException} wrapping an
     * {@link UnsupportedOperationException} is thrown.  Otherwise,
     * the behavior is the same as it would be for a mutable descriptor:
     * either an exception is thrown because of illegal parameters, or
     * there is no effect.
     * <p>
     * 此操作不受支持,因为此类是不可变的。
     * 如果此调用将更改具有相同内容的可变描述符,则会抛出{@link RuntimeOperationsException}包装{@link UnsupportedOperationException}。
     * 否则,行为与可变描述符的行为相同：或者由于非法参数抛出异常,或者没有效果。
     * 
     */
    public final void setField(String fieldName, Object fieldValue)
        throws RuntimeOperationsException {
        checkIllegalFieldName(fieldName);
        int i = fieldIndex(fieldName);
        if (i < 0)
            unsupported();
        Object value = values[i];
        if ((value == null) ?
                (fieldValue != null) :
                !value.equals(fieldValue))
            unsupported();
    }

    /**
     * Removes a field from the descriptor.
     *
     * <p>
     * 
     * @param fieldName String name of the field to be removed.
     * If the field name is illegal or the field is not found,
     * no exception is thrown.
     *
     * @exception RuntimeOperationsException if a field of the given name
     * exists and the descriptor is immutable.  The wrapped exception will
     * be an {@link UnsupportedOperationException}.
     */
    public final void removeField(String fieldName) {
        if (fieldName != null && fieldIndex(fieldName) >= 0)
            unsupported();
    }

    static Descriptor nonNullDescriptor(Descriptor d) {
        if (d == null)
            return EMPTY_DESCRIPTOR;
        else
            return d;
    }

    private static void checkIllegalFieldName(String name) {
        if (name == null || name.equals(""))
            illegal("Null or empty field name");
    }

    private static void unsupported() {
        UnsupportedOperationException uoe =
            new UnsupportedOperationException("Descriptor is read-only");
        throw new RuntimeOperationsException(uoe);
    }

    private static void illegal(String message) {
        IllegalArgumentException iae = new IllegalArgumentException(message);
        throw new RuntimeOperationsException(iae);
    }
}

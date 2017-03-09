/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;

import com.sun.beans.TypeResolver;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;

import java.lang.reflect.Method;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map.Entry;

/**
 * The FeatureDescriptor class is the common baseclass for PropertyDescriptor,
 * EventSetDescriptor, and MethodDescriptor, etc.
 * <p>
 * It supports some common information that can be set and retrieved for
 * any of the introspection descriptors.
 * <p>
 * In addition it provides an extension mechanism so that arbitrary
 * attribute/value pairs can be associated with a design feature.
 * <p>
 *  FeatureDescriptor类是PropertyDescriptor,EventSetDescriptor和MethodDescriptor等的公共基类。
 * <p>
 *  它支持一些常见的信息,可以为任何内省描述符设置和检索。
 * <p>
 *  此外,它提供了扩展机制,使得任意属性/值对可以与设计特征相关联。
 * 
 */

public class FeatureDescriptor {
    private static final String TRANSIENT = "transient";

    private Reference<? extends Class<?>> classRef;

    /**
     * Constructs a <code>FeatureDescriptor</code>.
     * <p>
     *  构造<code> FeatureDescriptor </code>。
     * 
     */
    public FeatureDescriptor() {
    }

    /**
     * Gets the programmatic name of this feature.
     *
     * <p>
     *  获取此功能的程序化名称。
     * 
     * 
     * @return The programmatic name of the property/method/event
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the programmatic name of this feature.
     *
     * <p>
     *  设置此功能的程序化名称。
     * 
     * 
     * @param name  The programmatic name of the property/method/event
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the localized display name of this feature.
     *
     * <p>
     *  获取此功能的本地化显示名称。
     * 
     * 
     * @return The localized display name for the property/method/event.
     *  This defaults to the same as its programmatic name from getName.
     */
    public String getDisplayName() {
        if (displayName == null) {
            return getName();
        }
        return displayName;
    }

    /**
     * Sets the localized display name of this feature.
     *
     * <p>
     *  设置此功能的本地化显示名称。
     * 
     * 
     * @param displayName  The localized display name for the
     *          property/method/event.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * The "expert" flag is used to distinguish between those features that are
     * intended for expert users from those that are intended for normal users.
     *
     * <p>
     *  "专家"标志用于区分专门用于专用用户的那些特征和用于普通用户的那些特征。
     * 
     * 
     * @return True if this feature is intended for use by experts only.
     */
    public boolean isExpert() {
        return expert;
    }

    /**
     * The "expert" flag is used to distinguish between features that are
     * intended for expert users from those that are intended for normal users.
     *
     * <p>
     *  "专家"标志用于区分专用用户的特征和正常用户的特征。
     * 
     * 
     * @param expert True if this feature is intended for use by experts only.
     */
    public void setExpert(boolean expert) {
        this.expert = expert;
    }

    /**
     * The "hidden" flag is used to identify features that are intended only
     * for tool use, and which should not be exposed to humans.
     *
     * <p>
     *  "隐藏"标志用于识别仅用于工具使用且不应暴露于人的特征。
     * 
     * 
     * @return True if this feature should be hidden from human users.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * The "hidden" flag is used to identify features that are intended only
     * for tool use, and which should not be exposed to humans.
     *
     * <p>
     *  "隐藏"标志用于识别仅用于工具使用且不应暴露于人的特征。
     * 
     * 
     * @param hidden  True if this feature should be hidden from human users.
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * The "preferred" flag is used to identify features that are particularly
     * important for presenting to humans.
     *
     * <p>
     *  "优选"标志用于识别对于向人类呈现特别重要的特征。
     * 
     * 
     * @return True if this feature should be preferentially shown to human users.
     */
    public boolean isPreferred() {
        return preferred;
    }

    /**
     * The "preferred" flag is used to identify features that are particularly
     * important for presenting to humans.
     *
     * <p>
     * "优选"标志用于识别对于向人类呈现特别重要的特征。
     * 
     * 
     * @param preferred  True if this feature should be preferentially shown
     *                   to human users.
     */
    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    /**
     * Gets the short description of this feature.
     *
     * <p>
     *  获取此功能的简短说明。
     * 
     * 
     * @return  A localized short description associated with this
     *   property/method/event.  This defaults to be the display name.
     */
    public String getShortDescription() {
        if (shortDescription == null) {
            return getDisplayName();
        }
        return shortDescription;
    }

    /**
     * You can associate a short descriptive string with a feature.  Normally
     * these descriptive strings should be less than about 40 characters.
     * <p>
     *  您可以将简短的描述性字符串与要素关联。通常这些描述性字符串应小于约40个字符。
     * 
     * 
     * @param text  A (localized) short description to be associated with
     * this property/method/event.
     */
    public void setShortDescription(String text) {
        shortDescription = text;
    }

    /**
     * Associate a named attribute with this feature.
     *
     * <p>
     *  将命名属性与此功能关联。
     * 
     * 
     * @param attributeName  The locale-independent name of the attribute
     * @param value  The value.
     */
    public void setValue(String attributeName, Object value) {
        getTable().put(attributeName, value);
    }

    /**
     * Retrieve a named attribute with this feature.
     *
     * <p>
     *  使用此功能检索命名属性。
     * 
     * 
     * @param attributeName  The locale-independent name of the attribute
     * @return  The value of the attribute.  May be null if
     *     the attribute is unknown.
     */
    public Object getValue(String attributeName) {
        return (this.table != null)
                ? this.table.get(attributeName)
                : null;
    }

    /**
     * Gets an enumeration of the locale-independent names of this
     * feature.
     *
     * <p>
     *  获取此功能的与语言环境无关的名称的枚举。
     * 
     * 
     * @return  An enumeration of the locale-independent names of any
     *    attributes that have been registered with setValue.
     */
    public Enumeration<String> attributeNames() {
        return getTable().keys();
    }

    /**
     * Package-private constructor,
     * Merge information from two FeatureDescriptors.
     * The merged hidden and expert flags are formed by or-ing the values.
     * In the event of other conflicts, the second argument (y) is
     * given priority over the first argument (x).
     *
     * <p>
     *  Package-private构造函数,合并来自两个FeatureDescriptors的信息。合并的隐藏和专家标志由值形成。在其他冲突的情况下,第二自变量(y)被赋予优先于第一自变量(x)。
     * 
     * 
     * @param x  The first (lower priority) MethodDescriptor
     * @param y  The second (higher priority) MethodDescriptor
     */
    FeatureDescriptor(FeatureDescriptor x, FeatureDescriptor y) {
        expert = x.expert | y.expert;
        hidden = x.hidden | y.hidden;
        preferred = x.preferred | y.preferred;
        name = y.name;
        shortDescription = x.shortDescription;
        if (y.shortDescription != null) {
            shortDescription = y.shortDescription;
        }
        displayName = x.displayName;
        if (y.displayName != null) {
            displayName = y.displayName;
        }
        classRef = x.classRef;
        if (y.classRef != null) {
            classRef = y.classRef;
        }
        addTable(x.table);
        addTable(y.table);
    }

    /*
     * Package-private dup constructor
     * This must isolate the new object from any changes to the old object.
     * <p>
     *  Package-private dup constructor这必须将新对象与对旧对象的任何更改隔离开来。
     * 
     */
    FeatureDescriptor(FeatureDescriptor old) {
        expert = old.expert;
        hidden = old.hidden;
        preferred = old.preferred;
        name = old.name;
        shortDescription = old.shortDescription;
        displayName = old.displayName;
        classRef = old.classRef;

        addTable(old.table);
    }

    /**
     * Copies all values from the specified attribute table.
     * If some attribute is exist its value should be overridden.
     *
     * <p>
     *  从指定的属性表复制所有值。如果某个属性存在,则其值应被覆盖。
     * 
     * 
     * @param table  the attribute table with new values
     */
    private void addTable(Hashtable<String, Object> table) {
        if ((table != null) && !table.isEmpty()) {
            getTable().putAll(table);
        }
    }

    /**
     * Returns the initialized attribute table.
     *
     * <p>
     *  返回初始化的属性表。
     * 
     * 
     * @return the initialized attribute table
     */
    private Hashtable<String, Object> getTable() {
        if (this.table == null) {
            this.table = new Hashtable<>();
        }
        return this.table;
    }

    /**
     * Sets the "transient" attribute according to the annotation.
     * If the "transient" attribute is already set
     * it should not be changed.
     *
     * <p>
     *  根据注释设置"transient"属性。如果"transient"属性已设置,则不应更改。
     * 
     * 
     * @param annotation  the annotation of the element of the feature
     */
    void setTransient(Transient annotation) {
        if ((annotation != null) && (null == getValue(TRANSIENT))) {
            setValue(TRANSIENT, annotation.value());
        }
    }

    /**
     * Indicates whether the feature is transient.
     *
     * <p>
     *  指示功能是否为暂时的。
     * 
     * 
     * @return {@code true} if the feature is transient,
     *         {@code false} otherwise
     */
    boolean isTransient() {
        Object value = getValue(TRANSIENT);
        return (value instanceof Boolean)
                ? (Boolean) value
                : false;
    }

    // Package private methods for recreating the weak/soft referent

    void setClass0(Class<?> cls) {
        this.classRef = getWeakReference(cls);
    }

    Class<?> getClass0() {
        return (this.classRef != null)
                ? this.classRef.get()
                : null;
    }

    /**
     * Creates a new soft reference that refers to the given object.
     *
     * <p>
     *  创建引用给定对象的新软引用。
     * 
     * 
     * @return a new soft reference or <code>null</code> if object is <code>null</code>
     *
     * @see SoftReference
     */
    static <T> Reference<T> getSoftReference(T object) {
        return (object != null)
                ? new SoftReference<>(object)
                : null;
    }

    /**
     * Creates a new weak reference that refers to the given object.
     *
     * <p>
     *  创建一个引用给定对象的新弱引用。
     * 
     * 
     * @return a new weak reference or <code>null</code> if object is <code>null</code>
     *
     * @see WeakReference
     */
    static <T> Reference<T> getWeakReference(T object) {
        return (object != null)
                ? new WeakReference<>(object)
                : null;
    }

    /**
     * Resolves the return type of the method.
     *
     * <p>
     *  解析方法的返回类型。
     * 
     * 
     * @param base    the class that contains the method in the hierarchy
     * @param method  the object that represents the method
     * @return a class identifying the return type of the method
     *
     * @see Method#getGenericReturnType
     * @see Method#getReturnType
     */
    static Class<?> getReturnType(Class<?> base, Method method) {
        if (base == null) {
            base = method.getDeclaringClass();
        }
        return TypeResolver.erase(TypeResolver.resolveInClass(base, method.getGenericReturnType()));
    }

    /**
     * Resolves the parameter types of the method.
     *
     * <p>
     *  解析方法的参数类型。
     * 
     * 
     * @param base    the class that contains the method in the hierarchy
     * @param method  the object that represents the method
     * @return an array of classes identifying the parameter types of the method
     *
     * @see Method#getGenericParameterTypes
     * @see Method#getParameterTypes
     */
    static Class<?>[] getParameterTypes(Class<?> base, Method method) {
        if (base == null) {
            base = method.getDeclaringClass();
        }
        return TypeResolver.erase(TypeResolver.resolveInClass(base, method.getGenericParameterTypes()));
    }

    private boolean expert;
    private boolean hidden;
    private boolean preferred;
    private String shortDescription;
    private String name;
    private String displayName;
    private Hashtable<String, Object> table;

    /**
     * Returns a string representation of the object.
     *
     * <p>
     *  返回对象的字符串表示形式。
     * 
     * @return a string representation of the object
     *
     * @since 1.7
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append("[name=").append(this.name);
        appendTo(sb, "displayName", this.displayName);
        appendTo(sb, "shortDescription", this.shortDescription);
        appendTo(sb, "preferred", this.preferred);
        appendTo(sb, "hidden", this.hidden);
        appendTo(sb, "expert", this.expert);
        if ((this.table != null) && !this.table.isEmpty()) {
            sb.append("; values={");
            for (Entry<String, Object> entry : this.table.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("; ");
            }
            sb.setLength(sb.length() - 2);
            sb.append("}");
        }
        appendTo(sb);
        return sb.append("]").toString();
    }

    void appendTo(StringBuilder sb) {
    }

    static void appendTo(StringBuilder sb, String name, Reference<?> reference) {
        if (reference != null) {
            appendTo(sb, name, reference.get());
        }
    }

    static void appendTo(StringBuilder sb, String name, Object value) {
        if (value != null) {
            sb.append("; ").append(name).append("=").append(value);
        }
    }

    static void appendTo(StringBuilder sb, String name, boolean value) {
        if (value) {
            sb.append("; ").append(name);
        }
    }
}

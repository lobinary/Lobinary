/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing;


import javax.swing.plaf.ComponentUI;
import javax.swing.border.*;
import javax.swing.event.SwingPropertyChangeSupport;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.Locale;
import java.util.Vector;
import java.util.MissingResourceException;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.lang.reflect.Method;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.security.AccessController;
import java.security.AccessControlContext;
import java.security.PrivilegedAction;

import sun.reflect.misc.MethodUtil;
import sun.reflect.misc.ReflectUtil;
import sun.swing.SwingUtilities2;
import sun.util.CoreResourceBundleControl;

/**
 * A table of defaults for Swing components.  Applications can set/get
 * default values via the <code>UIManager</code>.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  Swing组件的默认表。应用程序可以通过<code> UIManager </code>设置/获取默认值。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see UIManager
 * @author Hans Muller
 */
public class UIDefaults extends Hashtable<Object,Object>
{
    private static final Object PENDING = "Pending";

    private SwingPropertyChangeSupport changeSupport;

    private Vector<String> resourceBundles;

    private Locale defaultLocale = Locale.getDefault();

    /**
     * Maps from a Locale to a cached Map of the ResourceBundle. This is done
     * so as to avoid an exception being thrown when a value is asked for.
     * Access to this should be done while holding a lock on the
     * UIDefaults, eg synchronized(this).
     * <p>
     *  从区域设置映射到ResourceBundle的缓存映射。这样做是为了避免在请求值时抛出异常。应该在对UIDefaults持有锁时进行访问,例如synchronized(this)。
     * 
     */
    private Map<Locale, Map<String, Object>> resourceCache;

    /**
     * Creates an empty defaults table.
     * <p>
     *  创建一个空的默认值表。
     * 
     */
    public UIDefaults() {
        this(700, .75f);
    }

    /**
     * Creates an empty defaults table with the specified initial capacity and
     * load factor.
     *
     * <p>
     *  创建具有指定的初始容量和负载因子的空默认值表。
     * 
     * 
     * @param initialCapacity   the initial capacity of the defaults table
     * @param loadFactor        the load factor of the defaults table
     * @see java.util.Hashtable
     * @since 1.6
     */
    public UIDefaults(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        resourceCache = new HashMap<Locale, Map<String, Object>>();
    }


    /**
     * Creates a defaults table initialized with the specified
     * key/value pairs.  For example:
     * <pre>
        Object[] uiDefaults = {
             "Font", new Font("Dialog", Font.BOLD, 12),
            "Color", Color.red,
             "five", new Integer(5)
        }
        UIDefaults myDefaults = new UIDefaults(uiDefaults);
     * </pre>
     * <p>
     *  创建使用指定键/值对初始化的默认表。例如：
     * <pre>
     *  Object [] uiDefaults = {"Font",new Font("Dialog",Font.BOLD,12),"Color",Color.red,"five",new Integer(5)}
     *  UIDefaults myDefaults = new UIDefaults(uiDefaults) ;。
     * </pre>
     * 
     * @param keyValueList  an array of objects containing the key/value
     *          pairs
     */
    public UIDefaults(Object[] keyValueList) {
        super(keyValueList.length / 2);
        for(int i = 0; i < keyValueList.length; i += 2) {
            super.put(keyValueList[i], keyValueList[i + 1]);
        }
    }

    /**
     * Returns the value for key.  If the value is a
     * <code>UIDefaults.LazyValue</code> then the real
     * value is computed with <code>LazyValue.createValue()</code>,
     * the table entry is replaced, and the real value is returned.
     * If the value is an <code>UIDefaults.ActiveValue</code>
     * the table entry is not replaced - the value is computed
     * with <code>ActiveValue.createValue()</code> for each
     * <code>get()</code> call.
     *
     * If the key is not found in the table then it is searched for in the list
     * of resource bundles maintained by this object.  The resource bundles are
     * searched most recently added first using the locale returned by
     * <code>getDefaultLocale</code>.  <code>LazyValues</code> and
     * <code>ActiveValues</code> are not supported in the resource bundles.

     *
     * <p>
     * 返回键的值。
     * 如果值是<code> UIDefaults.LazyValue </code>,则实际值用<code> LazyValue.createValue()</code>计算,表项被替换,并返回实际值。
     * 如果值为<code> UIDefaults.ActiveValue </code>,则表项不会被替换 - 该值使用<code> ActiveValue.createValue()</code>对每个<code>
     *  get >调用。
     * 如果值是<code> UIDefaults.LazyValue </code>,则实际值用<code> LazyValue.createValue()</code>计算,表项被替换,并返回实际值。
     * 
     *  如果在表中找不到密钥,那么在此对象维护的资源束列表中搜索密钥。最近使用<code> getDefaultLocale </code>返回的语言环境添加资源包。
     *  <code> LazyValues </code>和<code> ActiveValues </code>在资源束中不受支持。
     * 
     * 
     * @param key the desired key
     * @return the value for <code>key</code>
     * @see LazyValue
     * @see ActiveValue
     * @see java.util.Hashtable#get
     * @see #getDefaultLocale
     * @see #addResourceBundle
     * @since 1.4
     */
    public Object get(Object key) {
        Object value = getFromHashtable( key );
        return (value != null) ? value : getFromResourceBundle(key, null);
    }

    /**
     * Looks up up the given key in our Hashtable and resolves LazyValues
     * or ActiveValues.
     * <p>
     *  在我们的Hashtable中查找给定的键,并解析LazyValues或ActiveValues。
     * 
     */
    private Object getFromHashtable(Object key) {
        /* Quickly handle the common case, without grabbing
         * a lock.
         * <p>
         *  锁。
         * 
         */
        Object value = super.get(key);
        if ((value != PENDING) &&
            !(value instanceof ActiveValue) &&
            !(value instanceof LazyValue)) {
            return value;
        }

        /* If the LazyValue for key is being constructed by another
         * thread then wait and then return the new value, otherwise drop
         * the lock and construct the ActiveValue or the LazyValue.
         * We use the special value PENDING to mark LazyValues that
         * are being constructed.
         * <p>
         *  线程然后等待然后返回新值,否则丢弃锁并构造ActiveValue或LazyValue。我们使用特殊值PENDING来标记正在构建的LazyValues。
         * 
         */
        synchronized(this) {
            value = super.get(key);
            if (value == PENDING) {
                do {
                    try {
                        this.wait();
                    }
                    catch (InterruptedException e) {
                    }
                    value = super.get(key);
                }
                while(value == PENDING);
                return value;
            }
            else if (value instanceof LazyValue) {
                super.put(key, PENDING);
            }
            else if (!(value instanceof ActiveValue)) {
                return value;
            }
        }

        /* At this point we know that the value of key was
         * a LazyValue or an ActiveValue.
         * <p>
         *  LazyValue或ActiveValue。
         * 
         */
        if (value instanceof LazyValue) {
            try {
                /* If an exception is thrown we'll just put the LazyValue
                 * back in the table.
                 * <p>
                 *  回到表中。
                 * 
                 */
                value = ((LazyValue)value).createValue(this);
            }
            finally {
                synchronized(this) {
                    if (value == null) {
                        super.remove(key);
                    }
                    else {
                        super.put(key, value);
                    }
                    this.notifyAll();
                }
            }
        }
        else {
            value = ((ActiveValue)value).createValue(this);
        }

        return value;
    }


    /**
     * Returns the value for key associated with the given locale.
     * If the value is a <code>UIDefaults.LazyValue</code> then the real
     * value is computed with <code>LazyValue.createValue()</code>,
     * the table entry is replaced, and the real value is returned.
     * If the value is an <code>UIDefaults.ActiveValue</code>
     * the table entry is not replaced - the value is computed
     * with <code>ActiveValue.createValue()</code> for each
     * <code>get()</code> call.
     *
     * If the key is not found in the table then it is searched for in the list
     * of resource bundles maintained by this object.  The resource bundles are
     * searched most recently added first using the given locale.
     * <code>LazyValues</code> and <code>ActiveValues</code> are not supported
     * in the resource bundles.
     *
     * <p>
     * 返回与给定语言环境关联的键的值。
     * 如果值是<code> UIDefaults.LazyValue </code>,则实际值用<code> LazyValue.createValue()</code>计算,表项被替换,并返回实际值。
     * 如果值为<code> UIDefaults.ActiveValue </code>,则表项不会被替换 - 该值使用<code> ActiveValue.createValue()</code>对每个<code>
     *  get >调用。
     * 如果值是<code> UIDefaults.LazyValue </code>,则实际值用<code> LazyValue.createValue()</code>计算,表项被替换,并返回实际值。
     * 
     *  如果在表中找不到密钥,那么在此对象维护的资源束列表中搜索密钥。最近使用给定的区域设置首先添加资源束。
     *  <code> LazyValues </code>和<code> ActiveValues </code>在资源束中不受支持。
     * 
     * 
     * @param key the desired key
     * @param l the desired <code>locale</code>
     * @return the value for <code>key</code>
     * @see LazyValue
     * @see ActiveValue
     * @see java.util.Hashtable#get
     * @see #addResourceBundle
     * @since 1.4
     */
    public Object get(Object key, Locale l) {
        Object value = getFromHashtable( key );
        return (value != null) ? value : getFromResourceBundle(key, l);
    }

    /**
     * Looks up given key in our resource bundles.
     * <p>
     *  在我们的资源包中查找给定的键。
     * 
     */
    private Object getFromResourceBundle(Object key, Locale l) {

        if( resourceBundles == null ||
            resourceBundles.isEmpty() ||
            !(key instanceof String) ) {
            return null;
        }

        // A null locale means use the default locale.
        if( l == null ) {
            if( defaultLocale == null )
                return null;
            else
                l = defaultLocale;
        }

        synchronized(this) {
            return getResourceCache(l).get(key);
        }
    }

    /**
     * Returns a Map of the known resources for the given locale.
     * <p>
     *  返回给定语言环境的已知资源的映射。
     * 
     */
    private Map<String, Object> getResourceCache(Locale l) {
        Map<String, Object> values = resourceCache.get(l);

        if (values == null) {
            values = new TextAndMnemonicHashMap();
            for (int i=resourceBundles.size()-1; i >= 0; i--) {
                String bundleName = resourceBundles.get(i);
                try {
                    Control c = CoreResourceBundleControl.getRBControlInstance(bundleName);
                    ResourceBundle b;
                    if (c != null) {
                        b = ResourceBundle.getBundle(bundleName, l, c);
                    } else {
                        b = ResourceBundle.getBundle(bundleName, l);
                    }
                    Enumeration keys = b.getKeys();

                    while (keys.hasMoreElements()) {
                        String key = (String)keys.nextElement();

                        if (values.get(key) == null) {
                            Object value = b.getObject(key);

                            values.put(key, value);
                        }
                    }
                } catch( MissingResourceException mre ) {
                    // Keep looking
                }
            }
            resourceCache.put(l, values);
        }
        return values;
    }

    /**
     * Sets the value of <code>key</code> to <code>value</code> for all locales.
     * If <code>key</code> is a string and the new value isn't
     * equal to the old one, fire a <code>PropertyChangeEvent</code>.
     * If value is <code>null</code>, the key is removed from the table.
     *
     * <p>
     *  为所有区域设置<code> key </code>的值为<code> value </code>。
     * 如果<code> key </code>是一个字符串,并且新值不等于旧的值,则触发一个<code> PropertyChangeEvent </code>。
     * 如果值为<code> null </code>,则从表中删除键。
     * 
     * 
     * @param key    the unique <code>Object</code> who's value will be used
     *          to retrieve the data value associated with it
     * @param value  the new <code>Object</code> to store as data under
     *          that key
     * @return the previous <code>Object</code> value, or <code>null</code>
     * @see #putDefaults
     * @see java.util.Hashtable#put
     */
    public Object put(Object key, Object value) {
        Object oldValue = (value == null) ? super.remove(key) : super.put(key, value);
        if (key instanceof String) {
            firePropertyChange((String)key, oldValue, value);
        }
        return oldValue;
    }


    /**
     * Puts all of the key/value pairs in the database and
     * unconditionally generates one <code>PropertyChangeEvent</code>.
     * The events oldValue and newValue will be <code>null</code> and its
     * <code>propertyName</code> will be "UIDefaults".  The key/value pairs are
     * added for all locales.
     *
     * <p>
     *  将所有键/值对放在数据库中,并无条件地生成一个<code> PropertyChangeEvent </code>。
     * 事件oldValue和newValue将是<code> null </code>,它的<code> propertyName </code>将是"UIDefaults"。将为所有区域设置添加键/值对。
     * 
     * 
     * @param keyValueList  an array of key/value pairs
     * @see #put
     * @see java.util.Hashtable#put
     */
    public void putDefaults(Object[] keyValueList) {
        for(int i = 0, max = keyValueList.length; i < max; i += 2) {
            Object value = keyValueList[i + 1];
            if (value == null) {
                super.remove(keyValueList[i]);
            }
            else {
                super.put(keyValueList[i], value);
            }
        }
        firePropertyChange("UIDefaults", null, null);
    }


    /**
     * If the value of <code>key</code> is a <code>Font</code> return it,
     * otherwise return <code>null</code>.
     * <p>
     *  如果<code> key </code>的值是<code> Font </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @return if the value for <code>key</code> is a <code>Font</code>,
     *          return the <code>Font</code> object; otherwise return
     *          <code>null</code>
     */
    public Font getFont(Object key) {
        Object value = get(key);
        return (value instanceof Font) ? (Font)value : null;
    }


    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is a <code>Font</code> return it, otherwise return <code>null</code>.
     * <p>
     * 如果给定<code> Locale </code>的<code> key </code>的值为<code> Font </code>,则返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @param l the desired locale
     * @return if the value for <code>key</code> and <code>Locale</code>
     *          is a <code>Font</code>,
     *          return the <code>Font</code> object; otherwise return
     *          <code>null</code>
     * @since 1.4
     */
    public Font getFont(Object key, Locale l) {
        Object value = get(key,l);
        return (value instanceof Font) ? (Font)value : null;
    }

    /**
     * If the value of <code>key</code> is a <code>Color</code> return it,
     * otherwise return <code>null</code>.
     * <p>
     *  如果<code> key </code>的值是<code> Color </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @return if the value for <code>key</code> is a <code>Color</code>,
     *          return the <code>Color</code> object; otherwise return
     *          <code>null</code>
     */
    public Color getColor(Object key) {
        Object value = get(key);
        return (value instanceof Color) ? (Color)value : null;
    }


    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is a <code>Color</code> return it, otherwise return <code>null</code>.
     * <p>
     *  如果给定<code> Locale </code>的<code> key </code>的值为<code> Color </code>,则返回它,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @param l the desired locale
     * @return if the value for <code>key</code> and <code>Locale</code>
     *          is a <code>Color</code>,
     *          return the <code>Color</code> object; otherwise return
     *          <code>null</code>
     * @since 1.4
     */
    public Color getColor(Object key, Locale l) {
        Object value = get(key,l);
        return (value instanceof Color) ? (Color)value : null;
    }


    /**
     * If the value of <code>key</code> is an <code>Icon</code> return it,
     * otherwise return <code>null</code>.
     * <p>
     *  如果<code> key </code>的值是<code> Icon </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @return if the value for <code>key</code> is an <code>Icon</code>,
     *          return the <code>Icon</code> object; otherwise return
     *          <code>null</code>
     */
    public Icon getIcon(Object key) {
        Object value = get(key);
        return (value instanceof Icon) ? (Icon)value : null;
    }


    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is an <code>Icon</code> return it, otherwise return <code>null</code>.
     * <p>
     *  如果给定<code> Locale </code>的<code> key </code>的值是<code> Icon </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @param l the desired locale
     * @return if the value for <code>key</code> and <code>Locale</code>
     *          is an <code>Icon</code>,
     *          return the <code>Icon</code> object; otherwise return
     *          <code>null</code>
     * @since 1.4
     */
    public Icon getIcon(Object key, Locale l) {
        Object value = get(key,l);
        return (value instanceof Icon) ? (Icon)value : null;
    }


    /**
     * If the value of <code>key</code> is a <code>Border</code> return it,
     * otherwise return <code>null</code>.
     * <p>
     *  如果<code> key </code>的值是<code> Border </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @return if the value for <code>key</code> is a <code>Border</code>,
     *          return the <code>Border</code> object; otherwise return
     *          <code>null</code>
     */
    public Border getBorder(Object key) {
        Object value = get(key);
        return (value instanceof Border) ? (Border)value : null;
    }


    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is a <code>Border</code> return it, otherwise return <code>null</code>.
     * <p>
     *  如果给定<code> Locale </code>的<code> key </code>的值为<code> Border </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @param l the desired locale
     * @return if the value for <code>key</code> and <code>Locale</code>
     *          is a <code>Border</code>,
     *          return the <code>Border</code> object; otherwise return
     *          <code>null</code>
     * @since 1.4
     */
    public Border getBorder(Object key, Locale l)  {
        Object value = get(key,l);
        return (value instanceof Border) ? (Border)value : null;
    }


    /**
     * If the value of <code>key</code> is a <code>String</code> return it,
     * otherwise return <code>null</code>.
     * <p>
     *  如果<code> key </code>的值是<code> String </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @return if the value for <code>key</code> is a <code>String</code>,
     *          return the <code>String</code> object; otherwise return
     *          <code>null</code>
     */
    public String getString(Object key) {
        Object value = get(key);
        return (value instanceof String) ? (String)value : null;
    }

    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is a <code>String</code> return it, otherwise return <code>null</code>.
     * <p>
     *  如果给定<code> Locale </code>的<code> key </code>的值为<code> String </code>,则返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @param l the desired <code>Locale</code>
     * @return if the value for <code>key</code> for the given
     *          <code>Locale</code> is a <code>String</code>,
     *          return the <code>String</code> object; otherwise return
     *          <code>null</code>
     * @since 1.4
     */
    public String getString(Object key, Locale l) {
        Object value = get(key,l);
        return (value instanceof String) ? (String)value : null;
    }

    /**
     * If the value of <code>key</code> is an <code>Integer</code> return its
     * integer value, otherwise return 0.
     * <p>
     *  如果<code> key </code>的值是<code> Integer </code>,则返回其整数值,否则返回0。
     * 
     * 
     * @param key the desired key
     * @return if the value for <code>key</code> is an <code>Integer</code>,
     *          return its value, otherwise return 0
     */
    public int getInt(Object key) {
        Object value = get(key);
        return (value instanceof Integer) ? ((Integer)value).intValue() : 0;
    }


    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is an <code>Integer</code> return its integer value, otherwise return 0.
     * <p>
     *  如果给定<code> Locale </code>的<code>键</code>的值是<code> Integer </code>,则返回其整数值,否则返回0。
     * 
     * 
     * @param key the desired key
     * @param l the desired locale
     * @return if the value for <code>key</code> and <code>Locale</code>
     *          is an <code>Integer</code>,
     *          return its value, otherwise return 0
     * @since 1.4
     */
    public int getInt(Object key, Locale l) {
        Object value = get(key,l);
        return (value instanceof Integer) ? ((Integer)value).intValue() : 0;
    }


    /**
     * If the value of <code>key</code> is boolean, return the
     * boolean value, otherwise return false.
     *
     * <p>
     *  如果<code> key </code>的值为boolean,则返回布尔值,否则返回false。
     * 
     * 
     * @param key an <code>Object</code> specifying the key for the desired boolean value
     * @return if the value of <code>key</code> is boolean, return the
     *         boolean value, otherwise return false.
     * @since 1.4
     */
    public boolean getBoolean(Object key) {
        Object value = get(key);
        return (value instanceof Boolean) ? ((Boolean)value).booleanValue() : false;
    }


    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is boolean, return the boolean value, otherwise return false.
     *
     * <p>
     * 如果给定<code> Locale </code>的<code>键</code>的值为boolean,则返回布尔值,否则返回false。
     * 
     * 
     * @param key an <code>Object</code> specifying the key for the desired boolean value
     * @param l the desired locale
     * @return if the value for <code>key</code> and <code>Locale</code>
     *         is boolean, return the
     *         boolean value, otherwise return false.
     * @since 1.4
     */
    public boolean getBoolean(Object key, Locale l) {
        Object value = get(key,l);
        return (value instanceof Boolean) ? ((Boolean)value).booleanValue() : false;
    }


    /**
     * If the value of <code>key</code> is an <code>Insets</code> return it,
     * otherwise return <code>null</code>.
     * <p>
     *  如果<code> key </code>的值是<code> Insets </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @return if the value for <code>key</code> is an <code>Insets</code>,
     *          return the <code>Insets</code> object; otherwise return
     *          <code>null</code>
     */
    public Insets getInsets(Object key) {
        Object value = get(key);
        return (value instanceof Insets) ? (Insets)value : null;
    }


    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is an <code>Insets</code> return it, otherwise return <code>null</code>.
     * <p>
     *  如果给定<code> Locale </code>的<code> key </code>的值是<code> Insets </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @param l the desired locale
     * @return if the value for <code>key</code> and <code>Locale</code>
     *          is an <code>Insets</code>,
     *          return the <code>Insets</code> object; otherwise return
     *          <code>null</code>
     * @since 1.4
     */
    public Insets getInsets(Object key, Locale l) {
        Object value = get(key,l);
        return (value instanceof Insets) ? (Insets)value : null;
    }


    /**
     * If the value of <code>key</code> is a <code>Dimension</code> return it,
     * otherwise return <code>null</code>.
     * <p>
     *  如果<code> key </code>的值是<code> Dimension </code>返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @return if the value for <code>key</code> is a <code>Dimension</code>,
     *          return the <code>Dimension</code> object; otherwise return
     *          <code>null</code>
     */
    public Dimension getDimension(Object key) {
        Object value = get(key);
        return (value instanceof Dimension) ? (Dimension)value : null;
    }


    /**
     * If the value of <code>key</code> for the given <code>Locale</code>
     * is a <code>Dimension</code> return it, otherwise return <code>null</code>.
     * <p>
     *  如果给定<code> Locale </code>的<code> key </code>的值为<code> Dimension </code>,则返回,否则返回<code> null </code>。
     * 
     * 
     * @param key the desired key
     * @param l the desired locale
     * @return if the value for <code>key</code> and <code>Locale</code>
     *          is a <code>Dimension</code>,
     *          return the <code>Dimension</code> object; otherwise return
     *          <code>null</code>
     * @since 1.4
     */
    public Dimension getDimension(Object key, Locale l) {
        Object value = get(key,l);
        return (value instanceof Dimension) ? (Dimension)value : null;
    }


    /**
     * The value of <code>get(uidClassID)</code> must be the
     * <code>String</code> name of a
     * class that implements the corresponding <code>ComponentUI</code>
     * class.  If the class hasn't been loaded before, this method looks
     * up the class with <code>uiClassLoader.loadClass()</code> if a non
     * <code>null</code>
     * class loader is provided, <code>classForName()</code> otherwise.
     * <p>
     * If a mapping for <code>uiClassID</code> exists or if the specified
     * class can't be found, return <code>null</code>.
     * <p>
     * This method is used by <code>getUI</code>, it's usually
     * not necessary to call it directly.
     *
     * <p>
     *  <code> get(uidClassID)</code>的值必须是实现相应的<code> ComponentUI </code>类的类的<code> String </code>名称。
     * 如果类之前没有被加载,这个方法查找类与<code> uiClassLoader.loadClass()</code>如果提供非<code> null </code>类加载器,<code> classFo
     * rName )</code>。
     *  <code> get(uidClassID)</code>的值必须是实现相应的<code> ComponentUI </code>类的类的<code> String </code>名称。
     * <p>
     *  如果<code> uiClassID </code>的映射存在,或者找不到指定的类,则返回<code> null </code>。
     * <p>
     *  此方法由<code> getUI </code>使用,通常不需要直接调用它。
     * 
     * 
     * @param uiClassID  a string containing the class ID
     * @param uiClassLoader the object which will load the class
     * @return the value of <code>Class.forName(get(uidClassID))</code>
     * @see #getUI
     */
    public Class<? extends ComponentUI>
        getUIClass(String uiClassID, ClassLoader uiClassLoader)
    {
        try {
            String className = (String)get(uiClassID);
            if (className != null) {
                ReflectUtil.checkPackageAccess(className);

                Class cls = (Class)get(className);
                if (cls == null) {
                    if (uiClassLoader == null) {
                        cls = SwingUtilities.loadSystemClass(className);
                    }
                    else {
                        cls = uiClassLoader.loadClass(className);
                    }
                    if (cls != null) {
                        // Save lookup for future use, as forName is slow.
                        put(className, cls);
                    }
                }
                return cls;
            }
        }
        catch (ClassNotFoundException e) {
            return null;
        }
        catch (ClassCastException e) {
            return null;
        }
        return null;
    }


    /**
     * Returns the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类。
     * 
     * 
     * @param uiClassID a string containing the class ID
     * @return the Class object returned by
     *          <code>getUIClass(uiClassID, null)</code>
     */
    public Class<? extends ComponentUI> getUIClass(String uiClassID) {
        return getUIClass(uiClassID, null);
    }


    /**
     * If <code>getUI()</code> fails for any reason,
     * it calls this method before returning <code>null</code>.
     * Subclasses may choose to do more or less here.
     *
     * <p>
     *  如果<code> getUI()</code>由于任何原因失败,它会在返回<code> null </code>之前调用此方法。子类可以选择做更多或更少的在这里。
     * 
     * 
     * @param msg message string to print
     * @see #getUI
     */
    protected void getUIError(String msg) {
        System.err.println("UIDefaults.getUI() failed: " + msg);
        try {
            throw new Error();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an <code>ComponentUI</code> implementation for the
     * specified component.  In other words create the look
     * and feel specific delegate object for <code>target</code>.
     * This is done in two steps:
     * <ul>
     * <li> Look up the name of the <code>ComponentUI</code> implementation
     * class under the value returned by <code>target.getUIClassID()</code>.
     * <li> Use the implementation classes static <code>createUI()</code>
     * method to construct a look and feel delegate.
     * </ul>
     * <p>
     * 为指定的组件创建一个<code> ComponentUI </code>实现。换句话说,为<code> target </code>创建外观和感觉特定的委托对象。这是通过两个步骤完成的：
     * <ul>
     *  <li>在<code> target.getUIClassID()</code>返回的值下查找<code> ComponentUI </code>实现类的名称。
     *  <li>使用实现类static <code> createUI()</code>方法构造一个外观感觉委托。
     * </ul>
     * 
     * @param target  the <code>JComponent</code> which needs a UI
     * @return the <code>ComponentUI</code> object
     */
    public ComponentUI getUI(JComponent target) {

        Object cl = get("ClassLoader");
        ClassLoader uiClassLoader =
            (cl != null) ? (ClassLoader)cl : target.getClass().getClassLoader();
        Class<? extends ComponentUI> uiClass = getUIClass(target.getUIClassID(), uiClassLoader);
        Object uiObject = null;

        if (uiClass == null) {
            getUIError("no ComponentUI class for: " + target);
        }
        else {
            try {
                Method m = (Method)get(uiClass);
                if (m == null) {
                    m = uiClass.getMethod("createUI", new Class[]{JComponent.class});
                    put(uiClass, m);
                }
                uiObject = MethodUtil.invoke(m, null, new Object[]{target});
            }
            catch (NoSuchMethodException e) {
                getUIError("static createUI() method not found in " + uiClass);
            }
            catch (Exception e) {
                getUIError("createUI() failed for " + target + " " + e);
            }
        }

        return (ComponentUI)uiObject;
    }

    /**
     * Adds a <code>PropertyChangeListener</code> to the listener list.
     * The listener is registered for all properties.
     * <p>
     * A <code>PropertyChangeEvent</code> will get fired whenever a default
     * is changed.
     *
     * <p>
     *  向侦听器列表中添加<code> PropertyChangeListener </code>。侦听器为所有属性注册。
     * <p>
     *  当更改默认值时,会触发<code> PropertyChangeEvent </code>。
     * 
     * 
     * @param listener  the <code>PropertyChangeListener</code> to be added
     * @see java.beans.PropertyChangeSupport
     */
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport == null) {
            changeSupport = new SwingPropertyChangeSupport(this);
        }
        changeSupport.addPropertyChangeListener(listener);
    }


    /**
     * Removes a <code>PropertyChangeListener</code> from the listener list.
     * This removes a <code>PropertyChangeListener</code> that was registered
     * for all properties.
     *
     * <p>
     *  从侦听器列表中删除<code> PropertyChangeListener </code>。这将删除为所有属性注册的<code> PropertyChangeListener </code>。
     * 
     * 
     * @param listener  the <code>PropertyChangeListener</code> to be removed
     * @see java.beans.PropertyChangeSupport
     */
    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport != null) {
            changeSupport.removePropertyChangeListener(listener);
        }
    }


    /**
     * Returns an array of all the <code>PropertyChangeListener</code>s added
     * to this UIDefaults with addPropertyChangeListener().
     *
     * <p>
     *  返回通过addPropertyChangeListener()添加到此UIDefaults的所有<code> PropertyChangeListener </code>数组。
     * 
     * 
     * @return all of the <code>PropertyChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        if (changeSupport == null) {
            return new PropertyChangeListener[0];
        }
        return changeSupport.getPropertyChangeListeners();
    }


    /**
     * Support for reporting bound property changes.  If oldValue and
     * newValue are not equal and the <code>PropertyChangeEvent</code>x
     * listener list isn't empty, then fire a
     * <code>PropertyChange</code> event to each listener.
     *
     * <p>
     *  支持报告绑定的属性更改。
     * 如果oldValue和newValue不相等,并且<code> PropertyChangeEvent </code> x侦听器列表不为空,那么为每个侦听器触发一个<code> PropertyChan
     * ge </code>事件。
     *  支持报告绑定的属性更改。
     * 
     * 
     * @param propertyName  the programmatic name of the property
     *          that was changed
     * @param oldValue  the old value of the property
     * @param newValue  the new value of the property
     * @see java.beans.PropertyChangeSupport
     */
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (changeSupport != null) {
            changeSupport.firePropertyChange(propertyName, oldValue, newValue);
        }
    }


    /**
     * Adds a resource bundle to the list of resource bundles that are
     * searched for localized values.  Resource bundles are searched in the
     * reverse order they were added.  In other words, the most recently added
     * bundle is searched first.
     *
     * <p>
     *  将资源束添加到搜索本地化值的资源束列表中。按照它们添加的相反顺序搜索资源束。换句话说,首先搜索最近添加的束。
     * 
     * 
     * @param bundleName  the base name of the resource bundle to be added
     * @see java.util.ResourceBundle
     * @see #removeResourceBundle
     * @since 1.4
     */
    public synchronized void addResourceBundle( String bundleName ) {
        if( bundleName == null ) {
            return;
        }
        if( resourceBundles == null ) {
            resourceBundles = new Vector<String>(5);
        }
        if (!resourceBundles.contains(bundleName)) {
            resourceBundles.add( bundleName );
            resourceCache.clear();
        }
    }


    /**
     * Removes a resource bundle from the list of resource bundles that are
     * searched for localized defaults.
     *
     * <p>
     *  从搜索本地化默认值的资源束列表中删除资源束。
     * 
     * 
     * @param bundleName  the base name of the resource bundle to be removed
     * @see java.util.ResourceBundle
     * @see #addResourceBundle
     * @since 1.4
     */
    public synchronized void removeResourceBundle( String bundleName ) {
        if( resourceBundles != null ) {
            resourceBundles.remove( bundleName );
        }
        resourceCache.clear();
    }

    /**
     * Sets the default locale.  The default locale is used in retrieving
     * localized values via <code>get</code> methods that do not take a
     * locale argument.  As of release 1.4, Swing UI objects should retrieve
     * localized values using the locale of their component rather than the
     * default locale.  The default locale exists to provide compatibility with
     * pre 1.4 behaviour.
     *
     * <p>
     * 设置默认区域设置。默认语言环境用于通过不带区域设置参数的<code> get </code>方法检索本地化值。
     * 从1.4版本开始,Swing UI对象应该使用其组件的语言环境而不是默认语言环境来检索本地化值。提供默认语言环境以提供与1.4之前的行为的兼容性。
     * 
     * 
     * @param l the new default locale
     * @see #getDefaultLocale
     * @see #get(Object)
     * @see #get(Object,Locale)
     * @since 1.4
     */
    public void setDefaultLocale( Locale l ) {
        defaultLocale = l;
    }

    /**
     * Returns the default locale.  The default locale is used in retrieving
     * localized values via <code>get</code> methods that do not take a
     * locale argument.  As of release 1.4, Swing UI objects should retrieve
     * localized values using the locale of their component rather than the
     * default locale.  The default locale exists to provide compatibility with
     * pre 1.4 behaviour.
     *
     * <p>
     *  返回默认语言环境。默认语言环境用于通过不带区域设置参数的<code> get </code>方法检索本地化值。
     * 从1.4版本开始,Swing UI对象应该使用其组件的语言环境而不是默认语言环境来检索本地化值。提供默认语言环境以提供与1.4之前的行为的兼容性。
     * 
     * 
     * @return the default locale
     * @see #setDefaultLocale
     * @see #get(Object)
     * @see #get(Object,Locale)
     * @since 1.4
     */
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * This class enables one to store an entry in the defaults
     * table that isn't constructed until the first time it's
     * looked up with one of the <code>getXXX(key)</code> methods.
     * Lazy values are useful for defaults that are expensive
     * to construct or are seldom retrieved.  The first time
     * a <code>LazyValue</code> is retrieved its "real value" is computed
     * by calling <code>LazyValue.createValue()</code> and the real
     * value is used to replace the <code>LazyValue</code> in the
     * <code>UIDefaults</code>
     * table.  Subsequent lookups for the same key return
     * the real value.  Here's an example of a <code>LazyValue</code>
     * that constructs a <code>Border</code>:
     * <pre>
     *  Object borderLazyValue = new UIDefaults.LazyValue() {
     *      public Object createValue(UIDefaults table) {
     *          return new BorderFactory.createLoweredBevelBorder();
     *      }
     *  };
     *
     *  uiDefaultsTable.put("MyBorder", borderLazyValue);
     * </pre>
     *
     * <p>
     *  这个类允许在默认表中存储一个没有被构造的条目,直到第一次使用<code> getXXX(key)</code>方法之一查找。惰性值对于构造昂贵或很少被检索的默认值是有用的。
     * 第一次检索<code> LazyValue </code>时,通过调用<code> LazyValue.createValue()</code>来计算它的"实际值",实际值用于替换<code> Lazy
     * Value <代码>在<code> UIDefaults </code>表中。
     *  这个类允许在默认表中存储一个没有被构造的条目,直到第一次使用<code> getXXX(key)</code>方法之一查找。惰性值对于构造昂贵或很少被检索的默认值是有用的。
     * 对同一键的后续查找返回实际值。下面是构建<code> Border </code>的<code> LazyValue </code>示例：。
     * <pre>
     * 对象borderLazyValue = new UIDefaults.LazyValue(){public Object createValue(UIDefaults table){return new BorderFactory.createLoweredBevelBorder(); }
     * };。
     * 
     *  uiDefaultsTable.put("MyBorder",borderLazyValue);
     * </pre>
     * 
     * 
     * @see UIDefaults#get
     */
    public interface LazyValue {
        /**
         * Creates the actual value retrieved from the <code>UIDefaults</code>
         * table. When an object that implements this interface is
         * retrieved from the table, this method is used to create
         * the real value, which is then stored in the table and
         * returned to the calling method.
         *
         * <p>
         *  创建从<code> UIDefaults </code>表中检索的实际值。当从表中检索实现此接口的对象时,此方法用于创建实际值,然后存储在表中并返回到调用方法。
         * 
         * 
         * @param table  a <code>UIDefaults</code> table
         * @return the created <code>Object</code>
         */
        Object createValue(UIDefaults table);
    }


    /**
     * This class enables one to store an entry in the defaults
     * table that's constructed each time it's looked up with one of
     * the <code>getXXX(key)</code> methods. Here's an example of
     * an <code>ActiveValue</code> that constructs a
     * <code>DefaultListCellRenderer</code>:
     * <pre>
     *  Object cellRendererActiveValue = new UIDefaults.ActiveValue() {
     *      public Object createValue(UIDefaults table) {
     *          return new DefaultListCellRenderer();
     *      }
     *  };
     *
     *  uiDefaultsTable.put("MyRenderer", cellRendererActiveValue);
     * </pre>
     *
     * <p>
     *  这个类允许在每次使用<code> getXXX(key)</code>方法查找时构造的defaults表中存储一个条目。
     * 下面是构建一个<code> DefaultListCellRenderer </code>的<code> ActiveValue </code>示例：。
     * <pre>
     *  Object cellRendererActiveValue = new UIDefaults.ActiveValue(){public Object createValue(UIDefaults table){return new DefaultListCellRenderer(); }
     * };。
     * 
     *  uiDefaultsTable.put("MyRenderer",cellRendererActiveValue);
     * </pre>
     * 
     * 
     * @see UIDefaults#get
     */
    public interface ActiveValue {
        /**
         * Creates the value retrieved from the <code>UIDefaults</code> table.
         * The object is created each time it is accessed.
         *
         * <p>
         *  创建从<code> UIDefaults </code>表中检索的值。每次访问对象时都会创建对象。
         * 
         * 
         * @param table  a <code>UIDefaults</code> table
         * @return the created <code>Object</code>
         */
        Object createValue(UIDefaults table);
    }

    /**
     * This class provides an implementation of <code>LazyValue</code>
     * which can be
     * used to delay loading of the Class for the instance to be created.
     * It also avoids creation of an anonymous inner class for the
     * <code>LazyValue</code>
     * subclass.  Both of these improve performance at the time that a
     * a Look and Feel is loaded, at the cost of a slight performance
     * reduction the first time <code>createValue</code> is called
     * (since Reflection APIs are used).
     * <p>
     * 这个类提供了一个<code> LazyValue </code>的实现,它可以用来延迟要创建的实例的Class的加载。它还避免为<code> LazyValue </code>子类创建一个匿名内部类。
     * 这两个都提高了在加载Look和Feel时的性能,代价是第一次调用<code> createValue </code>(由于使用了Reflection API),性能有所降低。
     * 
     * 
     * @since 1.3
     */
    public static class ProxyLazyValue implements LazyValue {
        private AccessControlContext acc;
        private String className;
        private String methodName;
        private Object[] args;

        /**
         * Creates a <code>LazyValue</code> which will construct an instance
         * when asked.
         *
         * <p>
         *  创建一个<code> LazyValue </code>,它会在请求时构造一个实例。
         * 
         * 
         * @param c    a <code>String</code> specifying the classname
         *             of the instance to be created on demand
         */
        public ProxyLazyValue(String c) {
            this(c, (String)null);
        }
        /**
         * Creates a <code>LazyValue</code> which will construct an instance
         * when asked.
         *
         * <p>
         *  创建一个<code> LazyValue </code>,它会在请求时构造一个实例。
         * 
         * 
         * @param c    a <code>String</code> specifying the classname of
         *              the class
         *              containing a static method to be called for
         *              instance creation
         * @param m    a <code>String</code> specifying the static
         *              method to be called on class c
         */
        public ProxyLazyValue(String c, String m) {
            this(c, m, null);
        }
        /**
         * Creates a <code>LazyValue</code> which will construct an instance
         * when asked.
         *
         * <p>
         *  创建一个<code> LazyValue </code>,它会在请求时构造一个实例。
         * 
         * 
         * @param c    a <code>String</code> specifying the classname
         *              of the instance to be created on demand
         * @param o    an array of <code>Objects</code> to be passed as
         *              paramaters to the constructor in class c
         */
        public ProxyLazyValue(String c, Object[] o) {
            this(c, null, o);
        }
        /**
         * Creates a <code>LazyValue</code> which will construct an instance
         * when asked.
         *
         * <p>
         *  创建一个<code> LazyValue </code>,它会在请求时构造一个实例。
         * 
         * 
         * @param c    a <code>String</code> specifying the classname
         *              of the class
         *              containing a static method to be called for
         *              instance creation.
         * @param m    a <code>String</code> specifying the static method
         *              to be called on class c
         * @param o    an array of <code>Objects</code> to be passed as
         *              paramaters to the static method in class c
         */
        public ProxyLazyValue(String c, String m, Object[] o) {
            acc = AccessController.getContext();
            className = c;
            methodName = m;
            if (o != null) {
                args = o.clone();
            }
        }

        /**
         * Creates the value retrieved from the <code>UIDefaults</code> table.
         * The object is created each time it is accessed.
         *
         * <p>
         *  创建从<code> UIDefaults </code>表中检索的值。每次访问对象时都会创建对象。
         * 
         * 
         * @param table  a <code>UIDefaults</code> table
         * @return the created <code>Object</code>
         */
        public Object createValue(final UIDefaults table) {
            // In order to pick up the security policy in effect at the
            // time of creation we use a doPrivileged with the
            // AccessControlContext that was in place when this was created.
            if (acc == null && System.getSecurityManager() != null) {
                throw new SecurityException("null AccessControlContext");
            }
            return AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    try {
                        Class<?> c;
                        Object cl;
                        // See if we should use a separate ClassLoader
                        if (table == null || !((cl = table.get("ClassLoader"))
                                               instanceof ClassLoader)) {
                            cl = Thread.currentThread().
                                        getContextClassLoader();
                            if (cl == null) {
                                // Fallback to the system class loader.
                                cl = ClassLoader.getSystemClassLoader();
                            }
                        }
                        ReflectUtil.checkPackageAccess(className);
                        c = Class.forName(className, true, (ClassLoader)cl);
                        SwingUtilities2.checkAccess(c.getModifiers());
                        if (methodName != null) {
                            Class[] types = getClassArray(args);
                            Method m = c.getMethod(methodName, types);
                            return MethodUtil.invoke(m, c, args);
                        } else {
                            Class[] types = getClassArray(args);
                            Constructor constructor = c.getConstructor(types);
                            SwingUtilities2.checkAccess(constructor.getModifiers());
                            return constructor.newInstance(args);
                        }
                    } catch(Exception e) {
                        // Ideally we would throw an exception, unfortunately
                        // often times there are errors as an initial look and
                        // feel is loaded before one can be switched. Perhaps a
                        // flag should be added for debugging, so that if true
                        // the exception would be thrown.
                    }
                    return null;
                }
            }, acc);
        }

        /*
         * Coerce the array of class types provided into one which
         * looks the way the Reflection APIs expect.  This is done
         * by substituting primitive types for their Object counterparts,
         * and superclasses for subclasses used to add the
         * <code>UIResource</code> tag.
         * <p>
         *  将提供的类类型数组强制转换为一个看起来像Reflection API期望的类型的数组。
         * 这是通过用原始类型替换它们的Object对象,以及用于添加<code> UIResource </code>标签的子类的超类。
         * 
         */
        private Class[] getClassArray(Object[] args) {
            Class[] types = null;
            if (args!=null) {
                types = new Class[args.length];
                for (int i = 0; i< args.length; i++) {
                    /* PENDING(ges): At present only the primitive types
                       used are handled correctly; this should eventually
                    /* <p>
                    /*  使用正确处理;这应该最终
                    /* 
                    /* 
                       handle all primitive types */
                    if (args[i] instanceof java.lang.Integer) {
                        types[i]=Integer.TYPE;
                    } else if (args[i] instanceof java.lang.Boolean) {
                        types[i]=Boolean.TYPE;
                    } else if (args[i] instanceof javax.swing.plaf.ColorUIResource) {
                        /* PENDING(ges) Currently the Reflection APIs do not
                           search superclasses of parameters supplied for
                           constructor/method lookup.  Since we only have
                           one case where this is needed, we substitute
                           directly instead of adding a massive amount
                           of mechanism for this.  Eventually this will
                           probably need to handle the general case as well.
                        /* <p>
                        /*  搜索为构造函数/方法查找提供的参数的超类。由于我们只有一个需要这种情况,我们直接替换,而不是为此添加大量的机制。最终,这可能需要处理一般情况。
                        /* 
                           */
                        types[i]=java.awt.Color.class;
                    } else {
                        types[i]=args[i].getClass();
                    }
                }
            }
            return types;
        }

        private String printArgs(Object[] array) {
            String s = "{";
            if (array !=null) {
                for (int i = 0 ; i < array.length-1; i++) {
                    s = s.concat(array[i] + ",");
                }
                s = s.concat(array[array.length-1] + "}");
            } else {
                s = s.concat("}");
            }
            return s;
        }
    }


    /**
     * <code>LazyInputMap</code> will create a <code>InputMap</code>
     * in its <code>createValue</code>
     * method. The bindings are passed in in the constructor.
     * The bindings are an array with
     * the even number entries being string <code>KeyStrokes</code>
     * (eg "alt SPACE") and
     * the odd number entries being the value to use in the
     * <code>InputMap</code> (and the key in the <code>ActionMap</code>).
     * <p>
     * <code> LazyInputMap </code>将在其<code> createValue </code>方法中创建一个<code> InputMap </code>。绑定在构造函数中传递。
     * 绑定是一个数组,偶数条目是字符串<code> KeyStrokes </code>(例如"alt SPACE"),奇数条目是要在<code> InputMap </code>键入<code> Actio
     * nMap </code>)。
     * <code> LazyInputMap </code>将在其<code> createValue </code>方法中创建一个<code> InputMap </code>。绑定在构造函数中传递。
     * 
     * 
     * @since 1.3
     */
    public static class LazyInputMap implements LazyValue {
        /** Key bindings are registered under. */
        private Object[] bindings;

        public LazyInputMap(Object[] bindings) {
            this.bindings = bindings;
        }

        /**
         * Creates an <code>InputMap</code> with the bindings that are
         * passed in.
         *
         * <p>
         *  使用传入的绑定创建<code> InputMap </code>。
         * 
         * 
         * @param table a <code>UIDefaults</code> table
         * @return the <code>InputMap</code>
         */
        public Object createValue(UIDefaults table) {
            if (bindings != null) {
                InputMap km = LookAndFeel.makeInputMap(bindings);
                return km;
            }
            return null;
        }
    }

    /**
     * <code>TextAndMnemonicHashMap</code> stores swing resource strings. Many of strings
     * can have a mnemonic. For example:
     *   FileChooser.saveButton.textAndMnemonic=&Save
     * For this case method get returns "Save" for the key "FileChooser.saveButtonText" and
     * mnemonic "S" for the key "FileChooser.saveButtonMnemonic"
     *
     * There are several patterns for the text and mnemonic suffixes which are checked by the
     * <code>TextAndMnemonicHashMap</code> class.
     * Patterns which are converted to the xxx.textAndMnemonic key:
     * (xxxNameText, xxxNameMnemonic)
     * (xxxNameText, xxxMnemonic)
     * (xxx.nameText, xxx.mnemonic)
     * (xxxText, xxxMnemonic)
     *
     * These patterns can have a mnemonic index in format
     * (xxxDisplayedMnemonicIndex)
     *
     * Pattern which is converted to the xxx.titleAndMnemonic key:
     * (xxxTitle, xxxMnemonic)
     *
     * <p>
     *  <code> TextAndMnemonicHashMap </code>存储swing资源字符串。许多字符串可以有一个助记符。
     * 例如：FileChooser.saveButton.textAndMnemonic =&Save对于这种情况,方法get为键"FileChooser.saveButtonText"返回"Save",为键
     * "FileChooser.saveButtonMememonic"返回助记符"S"。
     *  <code> TextAndMnemonicHashMap </code>存储swing资源字符串。许多字符串可以有一个助记符。
     * 
     *  对于由<code> TextAndMnemonicHashMap </code>类检查的文本和助记符后缀,有几种模式。
     * 转换为xxx.textAndMnemonic键的模式：(xxxNameText,xxxNameMnemonic)(xxxNameText,xxxMnemonic)(xxx.nameText,xxx.mn
     */
    private static class TextAndMnemonicHashMap extends HashMap<String, Object> {

        static final String AND_MNEMONIC = "AndMnemonic";
        static final String TITLE_SUFFIX = ".titleAndMnemonic";
        static final String TEXT_SUFFIX = ".textAndMnemonic";

        @Override
        public Object get(Object key) {

            Object value = super.get(key);

            if (value == null) {

                boolean checkTitle = false;

                String stringKey = key.toString();
                String compositeKey = null;

                if (stringKey.endsWith(AND_MNEMONIC)) {
                    return null;
                }

                if (stringKey.endsWith(".mnemonic")) {
                    compositeKey = composeKey(stringKey, 9, TEXT_SUFFIX);
                } else if (stringKey.endsWith("NameMnemonic")) {
                    compositeKey = composeKey(stringKey, 12, TEXT_SUFFIX);
                } else if (stringKey.endsWith("Mnemonic")) {
                    compositeKey = composeKey(stringKey, 8, TEXT_SUFFIX);
                    checkTitle = true;
                }

                if (compositeKey != null) {
                    value = super.get(compositeKey);
                    if (value == null && checkTitle) {
                        compositeKey = composeKey(stringKey, 8, TITLE_SUFFIX);
                        value = super.get(compositeKey);
                    }

                    return value == null ? null : getMnemonicFromProperty(value.toString());
                }

                if (stringKey.endsWith("NameText")) {
                    compositeKey = composeKey(stringKey, 8, TEXT_SUFFIX);
                } else if (stringKey.endsWith(".nameText")) {
                    compositeKey = composeKey(stringKey, 9, TEXT_SUFFIX);
                } else if (stringKey.endsWith("Text")) {
                    compositeKey = composeKey(stringKey, 4, TEXT_SUFFIX);
                } else if (stringKey.endsWith("Title")) {
                    compositeKey = composeKey(stringKey, 5, TITLE_SUFFIX);
                }

                if (compositeKey != null) {
                    value = super.get(compositeKey);
                    return value == null ? null : getTextFromProperty(value.toString());
                }

                if (stringKey.endsWith("DisplayedMnemonicIndex")) {
                    compositeKey = composeKey(stringKey, 22, TEXT_SUFFIX);
                    value = super.get(compositeKey);
                    if (value == null) {
                        compositeKey = composeKey(stringKey, 22, TITLE_SUFFIX);
                        value = super.get(compositeKey);
                    }
                    return value == null ? null : getIndexFromProperty(value.toString());
                }
            }

            return value;
        }

        String composeKey(String key, int reduce, String sufix) {
            return key.substring(0, key.length() - reduce) + sufix;
        }

        String getTextFromProperty(String text) {
            return text.replace("&", "");
        }

        String getMnemonicFromProperty(String text) {
            int index = text.indexOf('&');
            if (0 <= index && index < text.length() - 1) {
                char c = text.charAt(index + 1);
                return Integer.toString((int) Character.toUpperCase(c));
            }
            return null;
        }

        String getIndexFromProperty(String text) {
            int index = text.indexOf('&');
            return (index == -1) ? null : Integer.toString(index);
        }
    }

}

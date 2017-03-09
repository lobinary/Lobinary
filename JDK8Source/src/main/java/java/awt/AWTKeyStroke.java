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
package java.awt;

import java.awt.event.KeyEvent;
import sun.awt.AppContext;
import java.awt.event.InputEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

/**
 * An <code>AWTKeyStroke</code> represents a key action on the
 * keyboard, or equivalent input device. <code>AWTKeyStroke</code>s
 * can correspond to only a press or release of a
 * particular key, just as <code>KEY_PRESSED</code> and
 * <code>KEY_RELEASED</code> <code>KeyEvent</code>s do;
 * alternately, they can correspond to typing a specific Java character, just
 * as <code>KEY_TYPED</code> <code>KeyEvent</code>s do.
 * In all cases, <code>AWTKeyStroke</code>s can specify modifiers
 * (alt, shift, control, meta, altGraph, or a combination thereof) which must be present
 * during the action for an exact match.
 * <p>
 * <code>AWTKeyStrokes</code> are immutable, and are intended
 * to be unique. Client code should never create an
 * <code>AWTKeyStroke</code> on its own, but should instead use
 * a variant of <code>getAWTKeyStroke</code>. Client use of these factory
 * methods allows the <code>AWTKeyStroke</code> implementation
 * to cache and share instances efficiently.
 *
 * <p>
 *  <code> AWTKeyStroke </code>表示键盘或等效输入设备上的键操作。
 *  <code> AWTKeyStroke </code>可以仅对应于特定键的按下或释放,正如<code> KEY_PRESSED </code>和<code> KEY_RELEASED </code> 
 * <code> KeyEvent </code>做;或者,它们可以对应于键入特定的Java字符,正如<code> KEY_TYPED </code> <code> KeyEvent </code>。
 *  <code> AWTKeyStroke </code>表示键盘或等效输入设备上的键操作。
 * 在所有情况下,<code> AWTKeyStroke </code>可以指定在动作期间必须出现以用于精确匹配的修饰符(alt,shift,control,meta,altGraph或其组合)。
 * <p>
 *  <code> AWTKeyStrokes </code>是不可变的,并且旨在是唯一的。
 * 客户端代码不应该自己创建<code> AWTKeyStroke </code>,而应该使用<code> getAWTKeyStroke </code>的变体。
 * 客户端使用这些工厂方法允许<code> AWTKeyStroke </code>实现高效地缓存和共享实例。
 * 
 * 
 * @see #getAWTKeyStroke
 *
 * @author Arnaud Weber
 * @author David Mendenhall
 * @since 1.4
 */
public class AWTKeyStroke implements Serializable {
    static final long serialVersionUID = -6430539691155161871L;

    private static Map<String, Integer> modifierKeywords;
    /**
     * Associates VK_XXX (as a String) with code (as Integer). This is
     * done to avoid the overhead of the reflective call to find the
     * constant.
     * <p>
     *  将VK_XXX(作为字符串)与代码(作为Integer)关联。这是为了避免反射调用的开销来找到常量。
     * 
     */
    private static VKCollection vks;

    //A key for the collection of AWTKeyStrokes within AppContext.
    private static Object APP_CONTEXT_CACHE_KEY = new Object();
    //A key withing the cache
    private static AWTKeyStroke APP_CONTEXT_KEYSTROKE_KEY = new AWTKeyStroke();

    /*
     * Reads keystroke class from AppContext and if null, puts there the
     * AWTKeyStroke class.
     * Must be called under locked AWTKeyStro
     * <p>
     *  从AppContext中读取击键类,如果为null,则在那里放入AWTKeyStroke类。必须在锁定的AWTKeyStro下调用
     * 
     */
    private static Class<AWTKeyStroke> getAWTKeyStrokeClass() {
        Class<AWTKeyStroke> clazz = (Class)AppContext.getAppContext().get(AWTKeyStroke.class);
        if (clazz == null) {
            clazz = AWTKeyStroke.class;
            AppContext.getAppContext().put(AWTKeyStroke.class, AWTKeyStroke.class);
        }
        return clazz;
    }

    private char keyChar = KeyEvent.CHAR_UNDEFINED;
    private int keyCode = KeyEvent.VK_UNDEFINED;
    private int modifiers;
    private boolean onKeyRelease;

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
    }

    /**
     * Constructs an <code>AWTKeyStroke</code> with default values.
     * The default values used are:
     * <table border="1" summary="AWTKeyStroke default values">
     * <tr><th>Property</th><th>Default Value</th></tr>
     * <tr>
     *    <td>Key Char</td>
     *    <td><code>KeyEvent.CHAR_UNDEFINED</code></td>
     * </tr>
     * <tr>
     *    <td>Key Code</td>
     *    <td><code>KeyEvent.VK_UNDEFINED</code></td>
     * </tr>
     * <tr>
     *    <td>Modifiers</td>
     *    <td>none</td>
     * </tr>
     * <tr>
     *    <td>On key release?</td>
     *    <td><code>false</code></td>
     * </tr>
     * </table>
     *
     * <code>AWTKeyStroke</code>s should not be constructed
     * by client code. Use a variant of <code>getAWTKeyStroke</code>
     * instead.
     *
     * <p>
     *  使用默认值构造一个<code> AWTKeyStroke </code>。使用的默认值为：
     * <table border="1" summary="AWTKeyStroke default values">
     *  <tr> <th>属性</th> <th>默认值</th> </tr>
     * <tr>
     * <td>键字符</td> <td> <code> KeyEvent.CHAR_UNDEFINED </code> </td>
     * </tr>
     * <tr>
     *  <td>键代码</td> <td> <code> KeyEvent.VK_UNDEFINED </code> </td>
     * </tr>
     * <tr>
     *  <td>修饰符</td> <td>无</td>
     * </tr>
     * <tr>
     *  <td>关键释放?</td> <td> <code> false </code> </td>
     * </tr>
     * </table>
     * 
     *  <code> AWTKeyStroke </code> s不应由客户端代码构造。请改用<code> getAWTKeyStroke </code>的变体。
     * 
     * 
     * @see #getAWTKeyStroke
     */
    protected AWTKeyStroke() {
    }

    /**
     * Constructs an <code>AWTKeyStroke</code> with the specified
     * values. <code>AWTKeyStroke</code>s should not be constructed
     * by client code. Use a variant of <code>getAWTKeyStroke</code>
     * instead.
     *
     * <p>
     *  用指定的值构造一个<code> AWTKeyStroke </code>。 <code> AWTKeyStroke </code> s不应由客户端代码构造。
     * 请改用<code> getAWTKeyStroke </code>的变体。
     * 
     * 
     * @param keyChar the character value for a keyboard key
     * @param keyCode the key code for this <code>AWTKeyStroke</code>
     * @param modifiers a bitwise-ored combination of any modifiers
     * @param onKeyRelease <code>true</code> if this
     *        <code>AWTKeyStroke</code> corresponds
     *        to a key release; <code>false</code> otherwise
     * @see #getAWTKeyStroke
     */
    protected AWTKeyStroke(char keyChar, int keyCode, int modifiers,
                           boolean onKeyRelease) {
        this.keyChar = keyChar;
        this.keyCode = keyCode;
        this.modifiers = modifiers;
        this.onKeyRelease = onKeyRelease;
    }

    /**
     * Registers a new class which the factory methods in
     * <code>AWTKeyStroke</code> will use when generating new
     * instances of <code>AWTKeyStroke</code>s. After invoking this
     * method, the factory methods will return instances of the specified
     * Class. The specified Class must be either <code>AWTKeyStroke</code>
     * or derived from <code>AWTKeyStroke</code>, and it must have a
     * no-arg constructor. The constructor can be of any accessibility,
     * including <code>private</code>. This operation
     * flushes the current <code>AWTKeyStroke</code> cache.
     *
     * <p>
     *  注册在<code> AWTKeyStroke </code>中的工厂方法在生成<code> AWTKeyStroke </code>的新实例时将使用的新类。
     * 在调用此方法后,工厂方法将返回指定类的实例。
     * 指定的类必须是<code> AWTKeyStroke </code>或派生自<code> AWTKeyStroke </code>,并且它必须有一个无参构造函数。
     * 构造函数可以是任何可访问性,包括<code> private </code>。此操作刷新当前的<code> AWTKeyStroke </code>缓存。
     * 
     * 
     * @param subclass the new Class of which the factory methods should create
     *        instances
     * @throws IllegalArgumentException if subclass is <code>null</code>,
     *         or if subclass does not have a no-arg constructor
     * @throws ClassCastException if subclass is not
     *         <code>AWTKeyStroke</code>, or a class derived from
     *         <code>AWTKeyStroke</code>
     */
    protected static void registerSubclass(Class<?> subclass) {
        if (subclass == null) {
            throw new IllegalArgumentException("subclass cannot be null");
        }
        synchronized (AWTKeyStroke.class) {
            Class<AWTKeyStroke> keyStrokeClass = (Class)AppContext.getAppContext().get(AWTKeyStroke.class);
            if (keyStrokeClass != null && keyStrokeClass.equals(subclass)){
                // Already registered
                return;
            }
        }
        if (!AWTKeyStroke.class.isAssignableFrom(subclass)) {
            throw new ClassCastException("subclass is not derived from AWTKeyStroke");
        }

        Constructor ctor = getCtor(subclass);

        String couldNotInstantiate = "subclass could not be instantiated";

        if (ctor == null) {
            throw new IllegalArgumentException(couldNotInstantiate);
        }
        try {
            AWTKeyStroke stroke = (AWTKeyStroke)ctor.newInstance((Object[]) null);
            if (stroke == null) {
                throw new IllegalArgumentException(couldNotInstantiate);
            }
        } catch (NoSuchMethodError e) {
            throw new IllegalArgumentException(couldNotInstantiate);
        } catch (ExceptionInInitializerError e) {
            throw new IllegalArgumentException(couldNotInstantiate);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(couldNotInstantiate);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(couldNotInstantiate);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(couldNotInstantiate);
        }

        synchronized (AWTKeyStroke.class) {
            AppContext.getAppContext().put(AWTKeyStroke.class, subclass);
            AppContext.getAppContext().remove(APP_CONTEXT_CACHE_KEY);
            AppContext.getAppContext().remove(APP_CONTEXT_KEYSTROKE_KEY);
        }
    }

    /* returns noarg Constructor for class with accessible flag. No security
       threat as accessible flag is set only for this Constructor object,
       not for Class constructor.
    /* <p>
    /*  威胁作为可访问标志仅设置为此构造函数对象,而不是类构造函数。
    /* 
     */
    private static Constructor getCtor(final Class clazz)
    {
        Constructor ctor = AccessController.doPrivileged(new PrivilegedAction<Constructor>() {
            public Constructor run() {
                try {
                    Constructor ctor = clazz.getDeclaredConstructor((Class[]) null);
                    if (ctor != null) {
                        ctor.setAccessible(true);
                    }
                    return ctor;
                } catch (SecurityException e) {
                } catch (NoSuchMethodException e) {
                }
                return null;
            }
        });
        return (Constructor)ctor;
    }

    private static synchronized AWTKeyStroke getCachedStroke
        (char keyChar, int keyCode, int modifiers, boolean onKeyRelease)
    {
        Map<AWTKeyStroke, AWTKeyStroke> cache = (Map)AppContext.getAppContext().get(APP_CONTEXT_CACHE_KEY);
        AWTKeyStroke cacheKey = (AWTKeyStroke)AppContext.getAppContext().get(APP_CONTEXT_KEYSTROKE_KEY);

        if (cache == null) {
            cache = new HashMap<>();
            AppContext.getAppContext().put(APP_CONTEXT_CACHE_KEY, cache);
        }

        if (cacheKey == null) {
            try {
                Class<AWTKeyStroke> clazz = getAWTKeyStrokeClass();
                cacheKey = (AWTKeyStroke)getCtor(clazz).newInstance((Object[]) null);
                AppContext.getAppContext().put(APP_CONTEXT_KEYSTROKE_KEY, cacheKey);
            } catch (InstantiationException e) {
                assert(false);
            } catch (IllegalAccessException e) {
                assert(false);
            } catch (InvocationTargetException e) {
                assert(false);
            }
        }
        cacheKey.keyChar = keyChar;
        cacheKey.keyCode = keyCode;
        cacheKey.modifiers = mapNewModifiers(mapOldModifiers(modifiers));
        cacheKey.onKeyRelease = onKeyRelease;

        AWTKeyStroke stroke = (AWTKeyStroke)cache.get(cacheKey);
        if (stroke == null) {
            stroke = cacheKey;
            cache.put(stroke, stroke);
            AppContext.getAppContext().remove(APP_CONTEXT_KEYSTROKE_KEY);
        }
        return stroke;
    }

    /**
     * Returns a shared instance of an <code>AWTKeyStroke</code>
     * that represents a <code>KEY_TYPED</code> event for the
     * specified character.
     *
     * <p>
     *  返回代表指定字符的<code> KEY_TYPED </code>事件的<code> AWTKeyStroke </code>的共享实例。
     * 
     * 
     * @param keyChar the character value for a keyboard key
     * @return an <code>AWTKeyStroke</code> object for that key
     */
    public static AWTKeyStroke getAWTKeyStroke(char keyChar) {
        return getCachedStroke(keyChar, KeyEvent.VK_UNDEFINED, 0, false);
    }

    /**
     * Returns a shared instance of an {@code AWTKeyStroke}
     * that represents a {@code KEY_TYPED} event for the
     * specified Character object and a set of modifiers. Note
     * that the first parameter is of type Character rather than
     * char. This is to avoid inadvertent clashes with
     * calls to <code>getAWTKeyStroke(int keyCode, int modifiers)</code>.
     *
     * The modifiers consist of any combination of following:<ul>
     * <li>java.awt.event.InputEvent.SHIFT_DOWN_MASK
     * <li>java.awt.event.InputEvent.CTRL_DOWN_MASK
     * <li>java.awt.event.InputEvent.META_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK
     * </ul>
     * The old modifiers listed below also can be used, but they are
     * mapped to _DOWN_ modifiers. <ul>
     * <li>java.awt.event.InputEvent.SHIFT_MASK
     * <li>java.awt.event.InputEvent.CTRL_MASK
     * <li>java.awt.event.InputEvent.META_MASK
     * <li>java.awt.event.InputEvent.ALT_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_MASK
     * </ul>
     * also can be used, but they are mapped to _DOWN_ modifiers.
     *
     * Since these numbers are all different powers of two, any combination of
     * them is an integer in which each bit represents a different modifier
     * key. Use 0 to specify no modifiers.
     *
     * <p>
     * 返回{@code AWTKeyStroke}的共享实例,该实例表示指定Character对象和一组修饰符的{@code KEY_TYPED}事件。
     * 注意,第一个参数是Character类型,而不是char。
     * 这是为了避免无意中与调用<code> getAWTKeyStroke(int keyCode,int modifiers)</code>发生冲突。
     * 
     *  修饰符由以下内容的任意组合组成：<ul> <li> java.awt.event.InputEvent.SHIFT_DOWN_MASK <li> java.awt.event.InputEvent.C
     * TRL_DOWN_MASK <li> java.awt.event.InputEvent.META_DOWN_MASK <li > java.awt.event.InputEvent.ALT_DOWN_
     * MASK <li> java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK。
     * </ul>
     *  下面列出的旧修饰符也可以使用,但它们映射到_DOWN_修饰符。
     *  <ul> <li> java.awt.event.InputEvent.SHIFT_MASK <li> java.awt.event.InputEvent.CTRL_MASK <li> java.aw
     * t.event.InputEvent.META_MASK <li> java.awt.event.InputEvent。
     *  下面列出的旧修饰符也可以使用,但它们映射到_DOWN_修饰符。 ALT_MASK <li> java.awt.event.InputEvent.ALT_GRAPH_MASK。
     * </ul>
     *  也可以使用,但是它们被映射到_DOWN_修饰符。
     * 
     *  由于这些数字都是2的不同次幂,它们的任何组合是一个整数,其中每个位表示不同的修饰键。使用0指定无修饰符。
     * 
     * 
     * @param keyChar the Character object for a keyboard character
     * @param modifiers a bitwise-ored combination of any modifiers
     * @return an <code>AWTKeyStroke</code> object for that key
     * @throws IllegalArgumentException if <code>keyChar</code> is
     *       <code>null</code>
     *
     * @see java.awt.event.InputEvent
     */
    public static AWTKeyStroke getAWTKeyStroke(Character keyChar, int modifiers)
    {
        if (keyChar == null) {
            throw new IllegalArgumentException("keyChar cannot be null");
        }
        return getCachedStroke(keyChar.charValue(), KeyEvent.VK_UNDEFINED,
                               modifiers, false);
    }

    /**
     * Returns a shared instance of an <code>AWTKeyStroke</code>,
     * given a numeric key code and a set of modifiers, specifying
     * whether the key is activated when it is pressed or released.
     * <p>
     * The "virtual key" constants defined in
     * <code>java.awt.event.KeyEvent</code> can be
     * used to specify the key code. For example:<ul>
     * <li><code>java.awt.event.KeyEvent.VK_ENTER</code>
     * <li><code>java.awt.event.KeyEvent.VK_TAB</code>
     * <li><code>java.awt.event.KeyEvent.VK_SPACE</code>
     * </ul>
     * Alternatively, the key code may be obtained by calling
     * <code>java.awt.event.KeyEvent.getExtendedKeyCodeForChar</code>.
     *
     * The modifiers consist of any combination of:<ul>
     * <li>java.awt.event.InputEvent.SHIFT_DOWN_MASK
     * <li>java.awt.event.InputEvent.CTRL_DOWN_MASK
     * <li>java.awt.event.InputEvent.META_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK
     * </ul>
     * The old modifiers <ul>
     * <li>java.awt.event.InputEvent.SHIFT_MASK
     * <li>java.awt.event.InputEvent.CTRL_MASK
     * <li>java.awt.event.InputEvent.META_MASK
     * <li>java.awt.event.InputEvent.ALT_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_MASK
     * </ul>
     * also can be used, but they are mapped to _DOWN_ modifiers.
     *
     * Since these numbers are all different powers of two, any combination of
     * them is an integer in which each bit represents a different modifier
     * key. Use 0 to specify no modifiers.
     *
     * <p>
     *  返回一个<code> AWTKeyStroke </code>的共享实例,给定一个数字键代码和一组修饰符,指定在按下或释放时是否激活该键。
     * <p>
     * 在<code> java.awt.event.KeyEvent </code>中定义的"虚拟键"常量可用于指定键代码。
     * 例如：<ul> <li> <code> java.awt.event.KeyEvent.VK_ENTER </code> <li> <code> java.awt.event.KeyEvent.VK_T
     * AB </code> <li> <code> java.awt.event.KeyEvent.VK_SPACE </code>。
     * 在<code> java.awt.event.KeyEvent </code>中定义的"虚拟键"常量可用于指定键代码。
     * </ul>
     *  或者,可以通过调用<code> java.awt.event.KeyEvent.getExtendedKeyCodeForChar </code>获得键代码。
     * 
     *  修饰符由以下各项的任意组合组成：<ul> <li> java.awt.event.InputEvent.SHIFT_DOWN_MASK <li> java.awt.event.InputEvent.C
     * TRL_DOWN_MASK <li> java.awt.event.InputEvent.META_DOWN_MASK <li> java.awt.event.InputEvent.ALT_DOWN_M
     * ASK <li> java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK。
     * </ul>
     *  旧的修饰符<ul> <li> java.awt.event.InputEvent.SHIFT_MASK <li> java.awt.event.InputEvent.CTRL_MASK <li> ja
     * va.awt.event.InputEvent.META_MASK <li> java.awt.event .InputEvent.ALT_MASK <li> java.awt.event.InputE
     * vent.ALT_GRAPH_MASK。
     * </ul>
     *  也可以使用,但是它们被映射到_DOWN_修饰符。
     * 
     *  由于这些数字都是2的不同次幂,它们的任何组合是一个整数,其中每个位表示不同的修饰键。使用0指定无修饰符。
     * 
     * 
     * @param keyCode an int specifying the numeric code for a keyboard key
     * @param modifiers a bitwise-ored combination of any modifiers
     * @param onKeyRelease <code>true</code> if the <code>AWTKeyStroke</code>
     *        should represent a key release; <code>false</code> otherwise
     * @return an AWTKeyStroke object for that key
     *
     * @see java.awt.event.KeyEvent
     * @see java.awt.event.InputEvent
     */
    public static AWTKeyStroke getAWTKeyStroke(int keyCode, int modifiers,
                                               boolean onKeyRelease) {
        return getCachedStroke(KeyEvent.CHAR_UNDEFINED, keyCode, modifiers,
                               onKeyRelease);
    }

    /**
     * Returns a shared instance of an <code>AWTKeyStroke</code>,
     * given a numeric key code and a set of modifiers. The returned
     * <code>AWTKeyStroke</code> will correspond to a key press.
     * <p>
     * The "virtual key" constants defined in
     * <code>java.awt.event.KeyEvent</code> can be
     * used to specify the key code. For example:<ul>
     * <li><code>java.awt.event.KeyEvent.VK_ENTER</code>
     * <li><code>java.awt.event.KeyEvent.VK_TAB</code>
     * <li><code>java.awt.event.KeyEvent.VK_SPACE</code>
     * </ul>
     * The modifiers consist of any combination of:<ul>
     * <li>java.awt.event.InputEvent.SHIFT_DOWN_MASK
     * <li>java.awt.event.InputEvent.CTRL_DOWN_MASK
     * <li>java.awt.event.InputEvent.META_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK
     * </ul>
     * The old modifiers <ul>
     * <li>java.awt.event.InputEvent.SHIFT_MASK
     * <li>java.awt.event.InputEvent.CTRL_MASK
     * <li>java.awt.event.InputEvent.META_MASK
     * <li>java.awt.event.InputEvent.ALT_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_MASK
     * </ul>
     * also can be used, but they are mapped to _DOWN_ modifiers.
     *
     * Since these numbers are all different powers of two, any combination of
     * them is an integer in which each bit represents a different modifier
     * key. Use 0 to specify no modifiers.
     *
     * <p>
     *  返回一个<code> AWTKeyStroke </code>的共享实例,给定一个数字键代码和一组修饰符。返回的<code> AWTKeyStroke </code>会对应一个按键。
     * <p>
     * 在<code> java.awt.event.KeyEvent </code>中定义的"虚拟键"常量可用于指定键代码。
     * 例如：<ul> <li> <code> java.awt.event.KeyEvent.VK_ENTER </code> <li> <code> java.awt.event.KeyEvent.VK_T
     * AB </code> <li> <code> java.awt.event.KeyEvent.VK_SPACE </code>。
     * 在<code> java.awt.event.KeyEvent </code>中定义的"虚拟键"常量可用于指定键代码。
     * </ul>
     *  修饰符由以下各项的任意组合组成：<ul> <li> java.awt.event.InputEvent.SHIFT_DOWN_MASK <li> java.awt.event.InputEvent.C
     * TRL_DOWN_MASK <li> java.awt.event.InputEvent.META_DOWN_MASK <li> java.awt.event.InputEvent.ALT_DOWN_M
     * ASK <li> java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK。
     * </ul>
     *  旧的修饰符<ul> <li> java.awt.event.InputEvent.SHIFT_MASK <li> java.awt.event.InputEvent.CTRL_MASK <li> ja
     * va.awt.event.InputEvent.META_MASK <li> java.awt.event .InputEvent.ALT_MASK <li> java.awt.event.InputE
     * vent.ALT_GRAPH_MASK。
     * </ul>
     *  也可以使用,但是它们被映射到_DOWN_修饰符。
     * 
     *  由于这些数字都是2的不同次幂,它们的任何组合是一个整数,其中每个位表示不同的修饰键。使用0指定无修饰符。
     * 
     * 
     * @param keyCode an int specifying the numeric code for a keyboard key
     * @param modifiers a bitwise-ored combination of any modifiers
     * @return an <code>AWTKeyStroke</code> object for that key
     *
     * @see java.awt.event.KeyEvent
     * @see java.awt.event.InputEvent
     */
    public static AWTKeyStroke getAWTKeyStroke(int keyCode, int modifiers) {
        return getCachedStroke(KeyEvent.CHAR_UNDEFINED, keyCode, modifiers,
                               false);
    }

    /**
     * Returns an <code>AWTKeyStroke</code> which represents the
     * stroke which generated a given <code>KeyEvent</code>.
     * <p>
     * This method obtains the keyChar from a <code>KeyTyped</code>
     * event, and the keyCode from a <code>KeyPressed</code> or
     * <code>KeyReleased</code> event. The <code>KeyEvent</code> modifiers are
     * obtained for all three types of <code>KeyEvent</code>.
     *
     * <p>
     *  返回<code> AWTKeyStroke </code>,它代表生成给定<code> KeyEvent </code>的笔触。
     * <p>
     *  此方法从<code> KeyTyped </code>事件获取keyChar,并从<code> KeyPressed </code>或<code> KeyReleased </code>事件获取key
     * Code。
     * 为所有三种类型的<code> KeyEvent </code>获得<code> KeyEvent </code>修饰符。
     * 
     * 
     * @param anEvent the <code>KeyEvent</code> from which to
     *      obtain the <code>AWTKeyStroke</code>
     * @throws NullPointerException if <code>anEvent</code> is null
     * @return the <code>AWTKeyStroke</code> that precipitated the event
     */
    public static AWTKeyStroke getAWTKeyStrokeForEvent(KeyEvent anEvent) {
        int id = anEvent.getID();
        switch(id) {
          case KeyEvent.KEY_PRESSED:
          case KeyEvent.KEY_RELEASED:
            return getCachedStroke(KeyEvent.CHAR_UNDEFINED,
                                   anEvent.getKeyCode(),
                                   anEvent.getModifiers(),
                                   (id == KeyEvent.KEY_RELEASED));
          case KeyEvent.KEY_TYPED:
            return getCachedStroke(anEvent.getKeyChar(),
                                   KeyEvent.VK_UNDEFINED,
                                   anEvent.getModifiers(),
                                   false);
          default:
            // Invalid ID for this KeyEvent
            return null;
        }
    }

    /**
     * Parses a string and returns an <code>AWTKeyStroke</code>.
     * The string must have the following syntax:
     * <pre>
     *    &lt;modifiers&gt;* (&lt;typedID&gt; | &lt;pressedReleasedID&gt;)
     *
     *    modifiers := shift | control | ctrl | meta | alt | altGraph
     *    typedID := typed &lt;typedKey&gt;
     *    typedKey := string of length 1 giving Unicode character.
     *    pressedReleasedID := (pressed | released) key
     *    key := KeyEvent key code name, i.e. the name following "VK_".
     * </pre>
     * If typed, pressed or released is not specified, pressed is assumed. Here
     * are some examples:
     * <pre>
     *     "INSERT" =&gt; getAWTKeyStroke(KeyEvent.VK_INSERT, 0);
     *     "control DELETE" =&gt; getAWTKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_MASK);
     *     "alt shift X" =&gt; getAWTKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK);
     *     "alt shift released X" =&gt; getAWTKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK, true);
     *     "typed a" =&gt; getAWTKeyStroke('a');
     * </pre>
     *
     * <p>
     *  解析一个字符串并返回一个<code> AWTKeyStroke </code>。字符串必须具有以下语法：
     * <pre>
     * &lt; modifiers&gt; *(&lt; typedID&gt; |&lt; pressedReleasedID&gt;)
     * 
     *  修饰符：= shift |控制| ctrl |元| alt | altGraph typedID：= typed&lt; typedKey&gt; typedKey：=长度为1的字符串,给出Unico
     * de字符。
     *  pressReleasedID：=(按下|释放)键键：= KeyEvent键代码名称,即"VK_"后面的名称。
     * </pre>
     *  如果没有指定打字,按下或释放,则按下。这里有些例子：
     * <pre>
     *  "INSERT"=&gt; getAWTKeyStroke(KeyEvent.VK_INSERT,0); "control DELETE"=&gt; getAWTKeyStroke(KeyEvent.
     * VK_DELETE,InputEvent.CTRL_MASK); "alt shift X"=&gt; getAWTKeyStroke(KeyEvent.VK_X,InputEvent.ALT_MASK
     *  | InputEvent.SHIFT_MASK); "alt shift released X"=&gt; getAWTKeyStroke(KeyEvent.VK_X,InputEvent.ALT_M
     * ASK | InputEvent.SHIFT_MASK,true); "typed a"=&gt; getAWTKeyStroke('a');。
     * </pre>
     * 
     * 
     * @param s a String formatted as described above
     * @return an <code>AWTKeyStroke</code> object for that String
     * @throws IllegalArgumentException if <code>s</code> is <code>null</code>,
     *        or is formatted incorrectly
     */
    public static AWTKeyStroke getAWTKeyStroke(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String cannot be null");
        }

        final String errmsg = "String formatted incorrectly";

        StringTokenizer st = new StringTokenizer(s, " ");

        int mask = 0;
        boolean released = false;
        boolean typed = false;
        boolean pressed = false;

        synchronized (AWTKeyStroke.class) {
            if (modifierKeywords == null) {
                Map<String, Integer> uninitializedMap = new HashMap<>(8, 1.0f);
                uninitializedMap.put("shift",
                                     Integer.valueOf(InputEvent.SHIFT_DOWN_MASK
                                                     |InputEvent.SHIFT_MASK));
                uninitializedMap.put("control",
                                     Integer.valueOf(InputEvent.CTRL_DOWN_MASK
                                                     |InputEvent.CTRL_MASK));
                uninitializedMap.put("ctrl",
                                     Integer.valueOf(InputEvent.CTRL_DOWN_MASK
                                                     |InputEvent.CTRL_MASK));
                uninitializedMap.put("meta",
                                     Integer.valueOf(InputEvent.META_DOWN_MASK
                                                     |InputEvent.META_MASK));
                uninitializedMap.put("alt",
                                     Integer.valueOf(InputEvent.ALT_DOWN_MASK
                                                     |InputEvent.ALT_MASK));
                uninitializedMap.put("altGraph",
                                     Integer.valueOf(InputEvent.ALT_GRAPH_DOWN_MASK
                                                     |InputEvent.ALT_GRAPH_MASK));
                uninitializedMap.put("button1",
                                     Integer.valueOf(InputEvent.BUTTON1_DOWN_MASK));
                uninitializedMap.put("button2",
                                     Integer.valueOf(InputEvent.BUTTON2_DOWN_MASK));
                uninitializedMap.put("button3",
                                     Integer.valueOf(InputEvent.BUTTON3_DOWN_MASK));
                modifierKeywords =
                    Collections.synchronizedMap(uninitializedMap);
            }
        }

        int count = st.countTokens();

        for (int i = 1; i <= count; i++) {
            String token = st.nextToken();

            if (typed) {
                if (token.length() != 1 || i != count) {
                    throw new IllegalArgumentException(errmsg);
                }
                return getCachedStroke(token.charAt(0), KeyEvent.VK_UNDEFINED,
                                       mask, false);
            }

            if (pressed || released || i == count) {
                if (i != count) {
                    throw new IllegalArgumentException(errmsg);
                }

                String keyCodeName = "VK_" + token;
                int keyCode = getVKValue(keyCodeName);

                return getCachedStroke(KeyEvent.CHAR_UNDEFINED, keyCode,
                                       mask, released);
            }

            if (token.equals("released")) {
                released = true;
                continue;
            }
            if (token.equals("pressed")) {
                pressed = true;
                continue;
            }
            if (token.equals("typed")) {
                typed = true;
                continue;
            }

            Integer tokenMask = (Integer)modifierKeywords.get(token);
            if (tokenMask != null) {
                mask |= tokenMask.intValue();
            } else {
                throw new IllegalArgumentException(errmsg);
            }
        }

        throw new IllegalArgumentException(errmsg);
    }

    private static VKCollection getVKCollection() {
        if (vks == null) {
            vks = new VKCollection();
        }
        return vks;
    }
    /**
     * Returns the integer constant for the KeyEvent.VK field named
     * <code>key</code>. This will throw an
     * <code>IllegalArgumentException</code> if <code>key</code> is
     * not a valid constant.
     * <p>
     *  返回名为<code> key </code>的KeyEvent.VK字段的整数常量。
     * 如果<code>键</code>不是有效的常量,则会抛出<code> IllegalArgumentException </code>。
     * 
     */
    private static int getVKValue(String key) {
        VKCollection vkCollect = getVKCollection();

        Integer value = vkCollect.findCode(key);

        if (value == null) {
            int keyCode = 0;
            final String errmsg = "String formatted incorrectly";

            try {
                keyCode = KeyEvent.class.getField(key).getInt(KeyEvent.class);
            } catch (NoSuchFieldException nsfe) {
                throw new IllegalArgumentException(errmsg);
            } catch (IllegalAccessException iae) {
                throw new IllegalArgumentException(errmsg);
            }
            value = Integer.valueOf(keyCode);
            vkCollect.put(key, value);
        }
        return value.intValue();
    }

    /**
     * Returns the character for this <code>AWTKeyStroke</code>.
     *
     * <p>
     *  返回此<code> AWTKeyStroke </code>的字符。
     * 
     * 
     * @return a char value
     * @see #getAWTKeyStroke(char)
     * @see KeyEvent#getKeyChar
     */
    public final char getKeyChar() {
        return keyChar;
    }

    /**
     * Returns the numeric key code for this <code>AWTKeyStroke</code>.
     *
     * <p>
     *  返回此<code> AWTKeyStroke </code>的数字键代码。
     * 
     * 
     * @return an int containing the key code value
     * @see #getAWTKeyStroke(int,int)
     * @see KeyEvent#getKeyCode
     */
    public final int getKeyCode() {
        return keyCode;
    }

    /**
     * Returns the modifier keys for this <code>AWTKeyStroke</code>.
     *
     * <p>
     *  返回此<code> AWTKeyStroke </code>的修饰符键。
     * 
     * 
     * @return an int containing the modifiers
     * @see #getAWTKeyStroke(int,int)
     */
    public final int getModifiers() {
        return modifiers;
    }

    /**
     * Returns whether this <code>AWTKeyStroke</code> represents a key release.
     *
     * <p>
     *  返回此<代码> AWTKeyStroke </code>是否代表密钥版本。
     * 
     * 
     * @return <code>true</code> if this <code>AWTKeyStroke</code>
     *          represents a key release; <code>false</code> otherwise
     * @see #getAWTKeyStroke(int,int,boolean)
     */
    public final boolean isOnKeyRelease() {
        return onKeyRelease;
    }

    /**
     * Returns the type of <code>KeyEvent</code> which corresponds to
     * this <code>AWTKeyStroke</code>.
     *
     * <p>
     *  返回与此<code> AWTKeyStroke </code>对应的<code> KeyEvent </code>的类型。
     * 
     * 
     * @return <code>KeyEvent.KEY_PRESSED</code>,
     *         <code>KeyEvent.KEY_TYPED</code>,
     *         or <code>KeyEvent.KEY_RELEASED</code>
     * @see java.awt.event.KeyEvent
     */
    public final int getKeyEventType() {
        if (keyCode == KeyEvent.VK_UNDEFINED) {
            return KeyEvent.KEY_TYPED;
        } else {
            return (onKeyRelease)
                ? KeyEvent.KEY_RELEASED
                : KeyEvent.KEY_PRESSED;
        }
    }

    /**
     * Returns a numeric value for this object that is likely to be unique,
     * making it a good choice as the index value in a hash table.
     *
     * <p>
     *  返回可能是唯一的此对象的数值,使其成为散列表中的索引值的不错选择。
     * 
     * 
     * @return an int that represents this object
     */
    public int hashCode() {
        return (((int)keyChar) + 1) * (2 * (keyCode + 1)) * (modifiers + 1) +
            (onKeyRelease ? 1 : 2);
    }

    /**
     * Returns true if this object is identical to the specified object.
     *
     * <p>
     * 如果此对象与指定对象相同,则返回true。
     * 
     * 
     * @param anObject the Object to compare this object to
     * @return true if the objects are identical
     */
    public final boolean equals(Object anObject) {
        if (anObject instanceof AWTKeyStroke) {
            AWTKeyStroke ks = (AWTKeyStroke)anObject;
            return (ks.keyChar == keyChar && ks.keyCode == keyCode &&
                    ks.onKeyRelease == onKeyRelease &&
                    ks.modifiers == modifiers);
        }
        return false;
    }

    /**
     * Returns a string that displays and identifies this object's properties.
     * The <code>String</code> returned by this method can be passed
     * as a parameter to <code>getAWTKeyStroke(String)</code> to produce
     * a key stroke equal to this key stroke.
     *
     * <p>
     *  返回显示和标识此对象属性的字符串。
     * 此方法返回的<code> String </code>可以作为参数传递给<code> getAWTKeyStroke(String)</code>,以产生一个等于此关键笔划的关键笔划。
     * 
     * 
     * @return a String representation of this object
     * @see #getAWTKeyStroke(String)
     */
    public String toString() {
        if (keyCode == KeyEvent.VK_UNDEFINED) {
            return getModifiersText(modifiers) + "typed " + keyChar;
        } else {
            return getModifiersText(modifiers) +
                (onKeyRelease ? "released" : "pressed") + " " +
                getVKText(keyCode);
        }
    }

    static String getModifiersText(int modifiers) {
        StringBuilder buf = new StringBuilder();

        if ((modifiers & InputEvent.SHIFT_DOWN_MASK) != 0 ) {
            buf.append("shift ");
        }
        if ((modifiers & InputEvent.CTRL_DOWN_MASK) != 0 ) {
            buf.append("ctrl ");
        }
        if ((modifiers & InputEvent.META_DOWN_MASK) != 0 ) {
            buf.append("meta ");
        }
        if ((modifiers & InputEvent.ALT_DOWN_MASK) != 0 ) {
            buf.append("alt ");
        }
        if ((modifiers & InputEvent.ALT_GRAPH_DOWN_MASK) != 0 ) {
            buf.append("altGraph ");
        }
        if ((modifiers & InputEvent.BUTTON1_DOWN_MASK) != 0 ) {
            buf.append("button1 ");
        }
        if ((modifiers & InputEvent.BUTTON2_DOWN_MASK) != 0 ) {
            buf.append("button2 ");
        }
        if ((modifiers & InputEvent.BUTTON3_DOWN_MASK) != 0 ) {
            buf.append("button3 ");
        }

        return buf.toString();
    }

    static String getVKText(int keyCode) {
        VKCollection vkCollect = getVKCollection();
        Integer key = Integer.valueOf(keyCode);
        String name = vkCollect.findName(key);
        if (name != null) {
            return name.substring(3);
        }
        int expected_modifiers =
            (Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);

        Field[] fields = KeyEvent.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                if (fields[i].getModifiers() == expected_modifiers
                    && fields[i].getType() == Integer.TYPE
                    && fields[i].getName().startsWith("VK_")
                    && fields[i].getInt(KeyEvent.class) == keyCode)
                {
                    name = fields[i].getName();
                    vkCollect.put(name, key);
                    return name.substring(3);
                }
            } catch (IllegalAccessException e) {
                assert(false);
            }
        }
        return "UNKNOWN";
    }

    /**
     * Returns a cached instance of <code>AWTKeyStroke</code> (or a subclass of
     * <code>AWTKeyStroke</code>) which is equal to this instance.
     *
     * <p>
     * 
     * @return a cached instance which is equal to this instance
     */
    protected Object readResolve() throws java.io.ObjectStreamException {
        synchronized (AWTKeyStroke.class) {
            if (getClass().equals(getAWTKeyStrokeClass())) {
                return  getCachedStroke(keyChar, keyCode, modifiers, onKeyRelease);
            }
        }
        return this;
    }

    private static int mapOldModifiers(int modifiers) {
        if ((modifiers & InputEvent.SHIFT_MASK) != 0) {
            modifiers |= InputEvent.SHIFT_DOWN_MASK;
        }
        if ((modifiers & InputEvent.ALT_MASK) != 0) {
            modifiers |= InputEvent.ALT_DOWN_MASK;
        }
        if ((modifiers & InputEvent.ALT_GRAPH_MASK) != 0) {
            modifiers |= InputEvent.ALT_GRAPH_DOWN_MASK;
        }
        if ((modifiers & InputEvent.CTRL_MASK) != 0) {
            modifiers |= InputEvent.CTRL_DOWN_MASK;
        }
        if ((modifiers & InputEvent.META_MASK) != 0) {
            modifiers |= InputEvent.META_DOWN_MASK;
        }

        modifiers &= InputEvent.SHIFT_DOWN_MASK
            | InputEvent.ALT_DOWN_MASK
            | InputEvent.ALT_GRAPH_DOWN_MASK
            | InputEvent.CTRL_DOWN_MASK
            | InputEvent.META_DOWN_MASK
            | InputEvent.BUTTON1_DOWN_MASK
            | InputEvent.BUTTON2_DOWN_MASK
            | InputEvent.BUTTON3_DOWN_MASK;

        return modifiers;
    }

    private static int mapNewModifiers(int modifiers) {
        if ((modifiers & InputEvent.SHIFT_DOWN_MASK) != 0) {
            modifiers |= InputEvent.SHIFT_MASK;
        }
        if ((modifiers & InputEvent.ALT_DOWN_MASK) != 0) {
            modifiers |= InputEvent.ALT_MASK;
        }
        if ((modifiers & InputEvent.ALT_GRAPH_DOWN_MASK) != 0) {
            modifiers |= InputEvent.ALT_GRAPH_MASK;
        }
        if ((modifiers & InputEvent.CTRL_DOWN_MASK) != 0) {
            modifiers |= InputEvent.CTRL_MASK;
        }
        if ((modifiers & InputEvent.META_DOWN_MASK) != 0) {
            modifiers |= InputEvent.META_MASK;
        }

        return modifiers;
    }

}

class VKCollection {
    Map<Integer, String> code2name;
    Map<String, Integer> name2code;

    public VKCollection() {
        code2name = new HashMap<>();
        name2code = new HashMap<>();
    }

    public synchronized void put(String name, Integer code) {
        assert((name != null) && (code != null));
        assert(findName(code) == null);
        assert(findCode(name) == null);
        code2name.put(code, name);
        name2code.put(name, code);
    }

    public synchronized Integer findCode(String name) {
        assert(name != null);
        return (Integer)name2code.get(name);
    }

    public synchronized String findName(Integer code) {
        assert(code != null);
        return (String)code2name.get(code);
    }
}

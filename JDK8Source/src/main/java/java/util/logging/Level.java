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

package java.util.logging;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The Level class defines a set of standard logging levels that
 * can be used to control logging output.  The logging Level objects
 * are ordered and are specified by ordered integers.  Enabling logging
 * at a given level also enables logging at all higher levels.
 * <p>
 * Clients should normally use the predefined Level constants such
 * as Level.SEVERE.
 * <p>
 * The levels in descending order are:
 * <ul>
 * <li>SEVERE (highest value)
 * <li>WARNING
 * <li>INFO
 * <li>CONFIG
 * <li>FINE
 * <li>FINER
 * <li>FINEST  (lowest value)
 * </ul>
 * In addition there is a level OFF that can be used to turn
 * off logging, and a level ALL that can be used to enable
 * logging of all messages.
 * <p>
 * It is possible for third parties to define additional logging
 * levels by subclassing Level.  In such cases subclasses should
 * take care to chose unique integer level values and to ensure that
 * they maintain the Object uniqueness property across serialization
 * by defining a suitable readResolve method.
 *
 * <p>
 *  Level类定义了一组可用于控制日志输出的标准日志记录级别。日志记录级对象是有序的,由有序整数指定。在给定级别启用日志记录还可以在所有更高级别上进行日志记录。
 * <p>
 *  客户端通常应使用预定义的级别常量,如Level.SEVERE。
 * <p>
 *  级别按降序排列：
 * <ul>
 *  <li>严重(最高价值)<li>警告<li>信息<li>配置<li> FINE <li> FINER <li> FINEST
 * </ul>
 *  此外,还有一个级别OFF,可用于关闭日志记录,以及一个级别ALL,可用于启用所有消息的日志记录。
 * <p>
 *  第三方可以通过子类化Level来定义附加的日志记录级别。在这种情况下,子类应注意选择唯一的整数级值,并确保它们通过定义一个合适的readResolve方法在序列化中维护Object唯一性属性。
 * 
 * 
 * @since 1.4
 */

public class Level implements java.io.Serializable {
    private static final String defaultBundle = "sun.util.logging.resources.logging";

    /**
    /* <p>
    /* 
     * @serial  The non-localized name of the level.
     */
    private final String name;

    /**
    /* <p>
    /* 
     * @serial  The integer value of the level.
     */
    private final int value;

    /**
    /* <p>
    /* 
     * @serial The resource bundle name to be used in localizing the level name.
     */
    private final String resourceBundleName;

    // localized level name
    private transient String localizedLevelName;
    private transient Locale cachedLocale;

    /**
     * OFF is a special level that can be used to turn off logging.
     * This level is initialized to <CODE>Integer.MAX_VALUE</CODE>.
     * <p>
     *  OFF是可用于关闭日志记录的特殊级别。此级别初始化为<CODE> Integer.MAX_VALUE </CODE>。
     * 
     */
    public static final Level OFF = new Level("OFF",Integer.MAX_VALUE, defaultBundle);

    /**
     * SEVERE is a message level indicating a serious failure.
     * <p>
     * In general SEVERE messages should describe events that are
     * of considerable importance and which will prevent normal
     * program execution.   They should be reasonably intelligible
     * to end users and to system administrators.
     * This level is initialized to <CODE>1000</CODE>.
     * <p>
     *  SEVERE是指示严重故障的消息级别。
     * <p>
     * 一般来说,SEVERE消息应描述相当重要的事件,这将阻止正常的程序执行。它们应该对于最终用户和系统管理员是合理的。此级别初始化为<CODE> 1000 </CODE>。
     * 
     */
    public static final Level SEVERE = new Level("SEVERE",1000, defaultBundle);

    /**
     * WARNING is a message level indicating a potential problem.
     * <p>
     * In general WARNING messages should describe events that will
     * be of interest to end users or system managers, or which
     * indicate potential problems.
     * This level is initialized to <CODE>900</CODE>.
     * <p>
     *  WARNING是指示潜在问题的消息级别。
     * <p>
     *  一般来说,警告消息应描述最终用户或系统管理员感兴趣的事件或指示潜在问题的事件。此级别初始化为<CODE> 900 </CODE>。
     * 
     */
    public static final Level WARNING = new Level("WARNING", 900, defaultBundle);

    /**
     * INFO is a message level for informational messages.
     * <p>
     * Typically INFO messages will be written to the console
     * or its equivalent.  So the INFO level should only be
     * used for reasonably significant messages that will
     * make sense to end users and system administrators.
     * This level is initialized to <CODE>800</CODE>.
     * <p>
     *  INFO是信息消息的消息级别。
     * <p>
     *  通常,INFO消息将被写入控制台或其等价物。因此,INFO级别应该仅用于对最终用户和系统管理员有意义的合理重要的消息。此级别初始化为<CODE> 800 </CODE>。
     * 
     */
    public static final Level INFO = new Level("INFO", 800, defaultBundle);

    /**
     * CONFIG is a message level for static configuration messages.
     * <p>
     * CONFIG messages are intended to provide a variety of static
     * configuration information, to assist in debugging problems
     * that may be associated with particular configurations.
     * For example, CONFIG message might include the CPU type,
     * the graphics depth, the GUI look-and-feel, etc.
     * This level is initialized to <CODE>700</CODE>.
     * <p>
     *  CONFIG是静态配置消息的消息级别。
     * <p>
     *  CONFIG消息旨在提供各种静态配置信息,以帮助调试可能与特定配置相关联的问题。例如,CONFIG消息可能包括CPU类型,图形深度,GUI外观等。此级别初始化为<CODE> 700 </CODE>。
     * 
     */
    public static final Level CONFIG = new Level("CONFIG", 700, defaultBundle);

    /**
     * FINE is a message level providing tracing information.
     * <p>
     * All of FINE, FINER, and FINEST are intended for relatively
     * detailed tracing.  The exact meaning of the three levels will
     * vary between subsystems, but in general, FINEST should be used
     * for the most voluminous detailed output, FINER for somewhat
     * less detailed output, and FINE for the  lowest volume (and
     * most important) messages.
     * <p>
     * In general the FINE level should be used for information
     * that will be broadly interesting to developers who do not have
     * a specialized interest in the specific subsystem.
     * <p>
     * FINE messages might include things like minor (recoverable)
     * failures.  Issues indicating potential performance problems
     * are also worth logging as FINE.
     * This level is initialized to <CODE>500</CODE>.
     * <p>
     *  FINE是提供跟踪信息的消息级别。
     * <p>
     * 所有FINE,FINER和FINEST都用于相对详细的跟踪。
     * 三个级别的确切含义将在子系统之间变化,但一般来说,FINEST应用于最大量的详细输出,FINER用于较不详细的输出,FINE用于最小量(和最重要的)消息。
     * <p>
     *  通常,FINE级别应该用于对特定子系统没有特殊兴趣的开发人员广泛感兴趣的信息。
     * <p>
     *  FINE消息可能包括轻微(可恢复)故障。表示潜在性能问题的问题也值得记录为FINE。此级别初始化为<CODE> 500 </CODE>。
     * 
     */
    public static final Level FINE = new Level("FINE", 500, defaultBundle);

    /**
     * FINER indicates a fairly detailed tracing message.
     * By default logging calls for entering, returning, or throwing
     * an exception are traced at this level.
     * This level is initialized to <CODE>400</CODE>.
     * <p>
     *  FINER表示相当详细的跟踪消息。默认情况下,在此级别跟踪进入,返回或抛出异常的日志调用。此级别初始化为<CODE> 400 </CODE>。
     * 
     */
    public static final Level FINER = new Level("FINER", 400, defaultBundle);

    /**
     * FINEST indicates a highly detailed tracing message.
     * This level is initialized to <CODE>300</CODE>.
     * <p>
     *  FINEST表示高度详细的跟踪消息。此级别初始化为<CODE> 300 </CODE>。
     * 
     */
    public static final Level FINEST = new Level("FINEST", 300, defaultBundle);

    /**
     * ALL indicates that all messages should be logged.
     * This level is initialized to <CODE>Integer.MIN_VALUE</CODE>.
     * <p>
     *  ALL表示应记录所有消息。此级别初始化为<CODE> Integer.MIN_VALUE </CODE>。
     * 
     */
    public static final Level ALL = new Level("ALL", Integer.MIN_VALUE, defaultBundle);

    /**
     * Create a named Level with a given integer value.
     * <p>
     * Note that this constructor is "protected" to allow subclassing.
     * In general clients of logging should use one of the constant Level
     * objects such as SEVERE or FINEST.  However, if clients need to
     * add new logging levels, they may subclass Level and define new
     * constants.
     * <p>
     *  使用给定的整数值创建命名级别。
     * <p>
     *  请注意,此构造函数是"protected"以允许子类化。一般来说,日志的客户端应该使用一个常量级对象,如SEVERE或FINEST。
     * 但是,如果客户端需要添加新的日志记录级别,则它们可以子类化Level并定义新的常量。
     * 
     * 
     * @param name  the name of the Level, for example "SEVERE".
     * @param value an integer value for the level.
     * @throws NullPointerException if the name is null
     */
    protected Level(String name, int value) {
        this(name, value, null);
    }

    /**
     * Create a named Level with a given integer value and a
     * given localization resource name.
     * <p>
     * <p>
     * 使用给定的整数值和给定的本地化资源名称创建命名的级别。
     * <p>
     * 
     * @param name  the name of the Level, for example "SEVERE".
     * @param value an integer value for the level.
     * @param resourceBundleName name of a resource bundle to use in
     *    localizing the given name. If the resourceBundleName is null
     *    or an empty string, it is ignored.
     * @throws NullPointerException if the name is null
     */
    protected Level(String name, int value, String resourceBundleName) {
        this(name, value, resourceBundleName, true);
    }

    // private constructor to specify whether this instance should be added
    // to the KnownLevel list from which Level.parse method does its look up
    private Level(String name, int value, String resourceBundleName, boolean visible) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.value = value;
        this.resourceBundleName = resourceBundleName;
        this.localizedLevelName = resourceBundleName == null ? name : null;
        this.cachedLocale = null;
        if (visible) {
            KnownLevel.add(this);
        }
    }

    /**
     * Return the level's localization resource bundle name, or
     * null if no localization bundle is defined.
     *
     * <p>
     *  返回级别的本地化资源束名称,如果未定义本地化束,则返回null。
     * 
     * 
     * @return localization resource bundle name
     */
    public String getResourceBundleName() {
        return resourceBundleName;
    }

    /**
     * Return the non-localized string name of the Level.
     *
     * <p>
     *  返回级别的非本地化字符串名称。
     * 
     * 
     * @return non-localized name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the localized string name of the Level, for
     * the current default locale.
     * <p>
     * If no localization information is available, the
     * non-localized name is returned.
     *
     * <p>
     *  返回当前默认语言环境的级别的本地化字符串名称。
     * <p>
     *  如果没有可用的本地化信息,则返回非本地化名称。
     * 
     * 
     * @return localized name
     */
    public String getLocalizedName() {
        return getLocalizedLevelName();
    }

    // package-private getLevelName() is used by the implementation
    // instead of getName() to avoid calling the subclass's version
    final String getLevelName() {
        return this.name;
    }

    private String computeLocalizedLevelName(Locale newLocale) {
        ResourceBundle rb = ResourceBundle.getBundle(resourceBundleName, newLocale);
        final String localizedName = rb.getString(name);

        final boolean isDefaultBundle = defaultBundle.equals(resourceBundleName);
        if (!isDefaultBundle) return localizedName;

        // This is a trick to determine whether the name has been translated
        // or not. If it has not been translated, we need to use Locale.ROOT
        // when calling toUpperCase().
        final Locale rbLocale = rb.getLocale();
        final Locale locale =
                Locale.ROOT.equals(rbLocale)
                || name.equals(localizedName.toUpperCase(Locale.ROOT))
                ? Locale.ROOT : rbLocale;

        // ALL CAPS in a resource bundle's message indicates no translation
        // needed per Oracle translation guideline.  To workaround this
        // in Oracle JDK implementation, convert the localized level name
        // to uppercase for compatibility reason.
        return Locale.ROOT.equals(locale) ? name : localizedName.toUpperCase(locale);
    }

    // Avoid looking up the localizedLevelName twice if we already
    // have it.
    final String getCachedLocalizedLevelName() {

        if (localizedLevelName != null) {
            if (cachedLocale != null) {
                if (cachedLocale.equals(Locale.getDefault())) {
                    // OK: our cached value was looked up with the same
                    //     locale. We can use it.
                    return localizedLevelName;
                }
            }
        }

        if (resourceBundleName == null) {
            // No resource bundle: just use the name.
            return name;
        }

        // We need to compute the localized name.
        // Either because it's the first time, or because our cached
        // value is for a different locale. Just return null.
        return null;
    }

    final synchronized String getLocalizedLevelName() {

        // See if we have a cached localized name
        final String cachedLocalizedName = getCachedLocalizedLevelName();
        if (cachedLocalizedName != null) {
            return cachedLocalizedName;
        }

        // No cached localized name or cache invalid.
        // Need to compute the localized name.
        final Locale newLocale = Locale.getDefault();
        try {
            localizedLevelName = computeLocalizedLevelName(newLocale);
        } catch (Exception ex) {
            localizedLevelName = name;
        }
        cachedLocale = newLocale;
        return localizedLevelName;
    }

    // Returns a mirrored Level object that matches the given name as
    // specified in the Level.parse method.  Returns null if not found.
    //
    // It returns the same Level object as the one returned by Level.parse
    // method if the given name is a non-localized name or integer.
    //
    // If the name is a localized name, findLevel and parse method may
    // return a different level value if there is a custom Level subclass
    // that overrides Level.getLocalizedName() to return a different string
    // than what's returned by the default implementation.
    //
    static Level findLevel(String name) {
        if (name == null) {
            throw new NullPointerException();
        }

        KnownLevel level;

        // Look for a known Level with the given non-localized name.
        level = KnownLevel.findByName(name);
        if (level != null) {
            return level.mirroredLevel;
        }

        // Now, check if the given name is an integer.  If so,
        // first look for a Level with the given value and then
        // if necessary create one.
        try {
            int x = Integer.parseInt(name);
            level = KnownLevel.findByValue(x);
            if (level == null) {
                // add new Level
                Level levelObject = new Level(name, x);
                level = KnownLevel.findByValue(x);
            }
            return level.mirroredLevel;
        } catch (NumberFormatException ex) {
            // Not an integer.
            // Drop through.
        }

        level = KnownLevel.findByLocalizedLevelName(name);
        if (level != null) {
            return level.mirroredLevel;
        }

        return null;
    }

    /**
     * Returns a string representation of this Level.
     *
     * <p>
     *  返回此级别的字符串表示形式。
     * 
     * 
     * @return the non-localized name of the Level, for example "INFO".
     */
    @Override
    public final String toString() {
        return name;
    }

    /**
     * Get the integer value for this level.  This integer value
     * can be used for efficient ordering comparisons between
     * Level objects.
     * <p>
     *  获取此级别的整数值。此整数值可用于级对象之间的有效排序比较。
     * 
     * 
     * @return the integer value for this level.
     */
    public final int intValue() {
        return value;
    }

    private static final long serialVersionUID = -8176160795706313070L;

    // Serialization magic to prevent "doppelgangers".
    // This is a performance optimization.
    private Object readResolve() {
        KnownLevel o = KnownLevel.matches(this);
        if (o != null) {
            return o.levelObject;
        }

        // Woops.  Whoever sent us this object knows
        // about a new log level.  Add it to our list.
        Level level = new Level(this.name, this.value, this.resourceBundleName);
        return level;
    }

    /**
     * Parse a level name string into a Level.
     * <p>
     * The argument string may consist of either a level name
     * or an integer value.
     * <p>
     * For example:
     * <ul>
     * <li>     "SEVERE"
     * <li>     "1000"
     * </ul>
     *
     * <p>
     *  将级别名称字符串解析为级别。
     * <p>
     *  参数字符串可以由级别名称或整数值组成。
     * <p>
     *  例如：
     * <ul>
     *  <li>"SEVERE"<li>"1000"
     * </ul>
     * 
     * @param  name   string to be parsed
     * @throws NullPointerException if the name is null
     * @throws IllegalArgumentException if the value is not valid.
     * Valid values are integers between <CODE>Integer.MIN_VALUE</CODE>
     * and <CODE>Integer.MAX_VALUE</CODE>, and all known level names.
     * Known names are the levels defined by this class (e.g., <CODE>FINE</CODE>,
     * <CODE>FINER</CODE>, <CODE>FINEST</CODE>), or created by this class with
     * appropriate package access, or new levels defined or created
     * by subclasses.
     *
     * @return The parsed value. Passing an integer that corresponds to a known name
     * (e.g., 700) will return the associated name (e.g., <CODE>CONFIG</CODE>).
     * Passing an integer that does not (e.g., 1) will return a new level name
     * initialized to that value.
     */
    public static synchronized Level parse(String name) throws IllegalArgumentException {
        // Check that name is not null.
        name.length();

        KnownLevel level;

        // Look for a known Level with the given non-localized name.
        level = KnownLevel.findByName(name);
        if (level != null) {
            return level.levelObject;
        }

        // Now, check if the given name is an integer.  If so,
        // first look for a Level with the given value and then
        // if necessary create one.
        try {
            int x = Integer.parseInt(name);
            level = KnownLevel.findByValue(x);
            if (level == null) {
                // add new Level
                Level levelObject = new Level(name, x);
                level = KnownLevel.findByValue(x);
            }
            return level.levelObject;
        } catch (NumberFormatException ex) {
            // Not an integer.
            // Drop through.
        }

        // Finally, look for a known level with the given localized name,
        // in the current default locale.
        // This is relatively expensive, but not excessively so.
        level = KnownLevel.findByLocalizedLevelName(name);
        if (level != null) {
            return level.levelObject;
        }

        // OK, we've tried everything and failed
        throw new IllegalArgumentException("Bad level \"" + name + "\"");
    }

    /**
     * Compare two objects for value equality.
     * <p>
     * 
     * 
     * @return true if and only if the two objects have the same level value.
     */
    @Override
    public boolean equals(Object ox) {
        try {
            Level lx = (Level)ox;
            return (lx.value == this.value);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Generate a hashcode.
     * <p>
     *  比较两个对象的值相等。
     * 
     * 
     * @return a hashcode based on the level value
     */
    @Override
    public int hashCode() {
        return this.value;
    }

    // KnownLevel class maintains the global list of all known levels.
    // The API allows multiple custom Level instances of the same name/value
    // be created. This class provides convenient methods to find a level
    // by a given name, by a given value, or by a given localized name.
    //
    // KnownLevel wraps the following Level objects:
    // 1. levelObject:   standard Level object or custom Level object
    // 2. mirroredLevel: Level object representing the level specified in the
    //                   logging configuration.
    //
    // Level.getName, Level.getLocalizedName, Level.getResourceBundleName methods
    // are non-final but the name and resource bundle name are parameters to
    // the Level constructor.  Use the mirroredLevel object instead of the
    // levelObject to prevent the logging framework to execute foreign code
    // implemented by untrusted Level subclass.
    //
    // Implementation Notes:
    // If Level.getName, Level.getLocalizedName, Level.getResourceBundleName methods
    // were final, the following KnownLevel implementation can be removed.
    // Future API change should take this into consideration.
    static final class KnownLevel {
        private static Map<String, List<KnownLevel>> nameToLevels = new HashMap<>();
        private static Map<Integer, List<KnownLevel>> intToLevels = new HashMap<>();
        final Level levelObject;     // instance of Level class or Level subclass
        final Level mirroredLevel;   // mirror of the custom Level
        KnownLevel(Level l) {
            this.levelObject = l;
            if (l.getClass() == Level.class) {
                this.mirroredLevel = l;
            } else {
                // this mirrored level object is hidden
                this.mirroredLevel = new Level(l.name, l.value, l.resourceBundleName, false);
            }
        }

        static synchronized void add(Level l) {
            // the mirroredLevel object is always added to the list
            // before the custom Level instance
            KnownLevel o = new KnownLevel(l);
            List<KnownLevel> list = nameToLevels.get(l.name);
            if (list == null) {
                list = new ArrayList<>();
                nameToLevels.put(l.name, list);
            }
            list.add(o);

            list = intToLevels.get(l.value);
            if (list == null) {
                list = new ArrayList<>();
                intToLevels.put(l.value, list);
            }
            list.add(o);
        }

        // Returns a KnownLevel with the given non-localized name.
        static synchronized KnownLevel findByName(String name) {
            List<KnownLevel> list = nameToLevels.get(name);
            if (list != null) {
                return list.get(0);
            }
            return null;
        }

        // Returns a KnownLevel with the given value.
        static synchronized KnownLevel findByValue(int value) {
            List<KnownLevel> list = intToLevels.get(value);
            if (list != null) {
                return list.get(0);
            }
            return null;
        }

        // Returns a KnownLevel with the given localized name matching
        // by calling the Level.getLocalizedLevelName() method (i.e. found
        // from the resourceBundle associated with the Level object).
        // This method does not call Level.getLocalizedName() that may
        // be overridden in a subclass implementation
        static synchronized KnownLevel findByLocalizedLevelName(String name) {
            for (List<KnownLevel> levels : nameToLevels.values()) {
                for (KnownLevel l : levels) {
                    String lname = l.levelObject.getLocalizedLevelName();
                    if (name.equals(lname)) {
                        return l;
                    }
                }
            }
            return null;
        }

        static synchronized KnownLevel matches(Level l) {
            List<KnownLevel> list = nameToLevels.get(l.name);
            if (list != null) {
                for (KnownLevel level : list) {
                    Level other = level.mirroredLevel;
                    if (l.value == other.value &&
                           (l.resourceBundleName == other.resourceBundleName ||
                               (l.resourceBundleName != null &&
                                l.resourceBundleName.equals(other.resourceBundleName)))) {
                        return level;
                    }
                }
            }
            return null;
        }
    }

}

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

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventPostProcessor;
import java.awt.Toolkit;

import java.awt.event.KeyEvent;

import java.security.AccessController;

import javax.swing.plaf.ComponentUI;
import javax.swing.border.Border;

import javax.swing.event.SwingPropertyChangeSupport;
import java.beans.PropertyChangeListener;

import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Locale;

import sun.awt.SunToolkit;
import sun.awt.OSInfo;
import sun.security.action.GetPropertyAction;
import sun.swing.SwingUtilities2;
import java.lang.reflect.Method;
import java.util.HashMap;
import sun.awt.AppContext;
import sun.awt.AWTAccessor;


/**
 * {@code UIManager} manages the current look and feel, the set of
 * available look and feels, {@code PropertyChangeListeners} that
 * are notified when the look and feel changes, look and feel defaults, and
 * convenience methods for obtaining various default values.
 *
 * <h3>Specifying the look and feel</h3>
 *
 * The look and feel can be specified in two distinct ways: by
 * specifying the fully qualified name of the class for the look and
 * feel, or by creating an instance of {@code LookAndFeel} and passing
 * it to {@code setLookAndFeel}. The following example illustrates
 * setting the look and feel to the system look and feel:
 * <pre>
 *   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
 * </pre>
 * The following example illustrates setting the look and feel based on
 * class name:
 * <pre>
 *   UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
 * </pre>
 * Once the look and feel has been changed it is imperative to invoke
 * {@code updateUI} on all {@code JComponents}. The method {@link
 * SwingUtilities#updateComponentTreeUI} makes it easy to apply {@code
 * updateUI} to a containment hierarchy. Refer to it for
 * details. The exact behavior of not invoking {@code
 * updateUI} after changing the look and feel is
 * unspecified. It is very possible to receive unexpected exceptions,
 * painting problems, or worse.
 *
 * <h3>Default look and feel</h3>
 *
 * The class used for the default look and feel is chosen in the following
 * manner:
 * <ol>
 *   <li>If the system property <code>swing.defaultlaf</code> is
 *       {@code non-null}, use its value as the default look and feel class
 *       name.
 *   <li>If the {@link java.util.Properties} file <code>swing.properties</code>
 *       exists and contains the key <code>swing.defaultlaf</code>,
 *       use its value as the default look and feel class name. The location
 *       that is checked for <code>swing.properties</code> may vary depending
 *       upon the implementation of the Java platform. Typically the
 *       <code>swing.properties</code> file is located in the <code>lib</code>
 *       subdirectory of the Java installation directory.
 *       Refer to the release notes of the implementation being used for
 *       further details.
 *   <li>Otherwise use the cross platform look and feel.
 * </ol>
 *
 * <h3>Defaults</h3>
 *
 * {@code UIManager} manages three sets of {@code UIDefaults}. In order, they
 * are:
 * <ol>
 *   <li>Developer defaults. With few exceptions Swing does not
 *       alter the developer defaults; these are intended to be modified
 *       and used by the developer.
 *   <li>Look and feel defaults. The look and feel defaults are
 *       supplied by the look and feel at the time it is installed as the
 *       current look and feel ({@code setLookAndFeel()} is invoked). The
 *       look and feel defaults can be obtained using the {@code
 *       getLookAndFeelDefaults()} method.
 *   <li>System defaults. The system defaults are provided by Swing.
 * </ol>
 * Invoking any of the various {@code get} methods
 * results in checking each of the defaults, in order, returning
 * the first {@code non-null} value. For example, invoking
 * {@code UIManager.getString("Table.foreground")} results in first
 * checking developer defaults. If the developer defaults contain
 * a value for {@code "Table.foreground"} it is returned, otherwise
 * the look and feel defaults are checked, followed by the system defaults.
 * <p>
 * It's important to note that {@code getDefaults} returns a custom
 * instance of {@code UIDefaults} with this resolution logic built into it.
 * For example, {@code UIManager.getDefaults().getString("Table.foreground")}
 * is equivalent to {@code UIManager.getString("Table.foreground")}. Both
 * resolve using the algorithm just described. In many places the
 * documentation uses the word defaults to refer to the custom instance
 * of {@code UIDefaults} with the resolution logic as previously described.
 * <p>
 * When the look and feel is changed, {@code UIManager} alters only the
 * look and feel defaults; the developer and system defaults are not
 * altered by the {@code UIManager} in any way.
 * <p>
 * The set of defaults a particular look and feel supports is defined
 * and documented by that look and feel. In addition, each look and
 * feel, or {@code ComponentUI} provided by a look and feel, may
 * access the defaults at different times in their life cycle. Some
 * look and feels may aggressively look up defaults, so that changing a
 * default may not have an effect after installing the look and feel.
 * Other look and feels may lazily access defaults so that a change to
 * the defaults may effect an existing look and feel. Finally, other look
 * and feels might not configure themselves from the defaults table in
 * any way. None-the-less it is usually the case that a look and feel
 * expects certain defaults, so that in general
 * a {@code ComponentUI} provided by one look and feel will not
 * work with another look and feel.
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
 *  {@code UIManager}管理当前外观和感觉,当外观和感觉变化时通知的可用外观和感觉集合{@code PropertyChangeListeners},外观和感觉默认值,以及用于获取各种默认值
 * 的便利方法。
 * 
 *  <h3>指定外观和感觉</h3>
 * 
 *  可以通过两种不同的方式指定外观和感觉：通过为外观和感觉指定类的完全限定名称,或者创建{@code LookAndFeel}的实例并将其传递给{@code setLookAndFeel}。
 * 下面的示例说明了设置外观和感觉到系统的外观和感觉：。
 * <pre>
 * UIManagersetLookAndFeel(UIManagergetSystemLookAndFeelClassName());
 * </pre>
 *  以下示例说明了基于类名设置外观和感觉：
 * <pre>
 *  UIManagersetLookAndFeel("javaxswingplafmetalMetalLookAndFeel");
 * </pre>
 *  一旦外观和感觉已经改变,就必须在所有{@code JComponents}上调用{@code updateUI}。
 * {@link SwingUtilities#updateComponentTreeUI}方法可以很容易地将{@code updateUI}应用到包含层次结构。
 * 参考它的详细信息更改外观和感觉后不调用{@code updateUI}的确切行为是未指定的非常有可能接收意外的异常,绘画问题,或更糟。
 * 
 *  <h3>默认外观</h3>
 * 
 *  用于默认外观的类以下列方式选择：
 * <ol>
 * <li>如果系统属性<code> swingdefaultlaf </code>为{@code non-null},请使用其值作为默认外观类名称<li>如果{@link javautilProperties}
 * 文件<code> swingproperties </code>存在并包含键<code> swingdefaultlaf </code>,将其值用作默认外观和感觉类名称检查<code> swingpro
 * perties </code>的位置可能会根据实施Java平台通常,<code> swingproperties </code>文件位于Java安装目录的<code> lib </code>子目录中。
 * 参考要用于进一步详细信息的实现的发行说明<li>否则使用跨平台的外观和感觉。
 * </ol>
 * 
 *  <h3>默认</h3>
 * 
 * {@code UIManager}管理三组{@code UIDefaults},它们是：
 * <ol>
 *  <li>开发人员默认值除了少数例外,Swing不会更改开发人员默认值;这些是开发者修改和使用<li>外观和默认默认值外观和感觉默认值由安装时的外观和感觉作为当前的外观({@code setLookAndFeel()}
 * 被调用)外观和感觉默认值可以使用{@code getLookAndFeelDefaults()}方法获得<li>系统默认值系统默认值由Swing提供。
 * </ol>
 * 调用各种{@code get}方法会导致按顺序检查每个默认值,返回第一个{@code non-null}值。
 * 例如,调用{@code UIManagergetString("Tableforeground")}检查开发人员默认值如果开发人员默认值包含{@code"Tableforeground"}的值,则返回它
 * ,否则将检查外观和默认值,然后检查系统默认值。
 * 调用各种{@code get}方法会导致按顺序检查每个默认值,返回第一个{@code non-null}值。
 * <p>
 * 重要的是要注意,{@code getDefaults}返回一个内置了这个分辨率逻辑的{@code UIDefaults}的自定义实例例如,{@code UIManagergetDefaults()getString("Tableforeground")}
 * 等价于{@code UIManagergetString ("Tableforeground")}两者都使用刚刚描述的算法解析在许多地方,文档使用单词defaults来引用{@code UIDefaults}
 * 的自定义实例,具有如前所述的解析逻辑。
 * <p>
 *  当外观和感觉改变时,{@code UIManager}只改变外观和感觉的默认值;开发人员和系统默认值不会被{@code UIManager}以任何方式改变
 * <p>
 * 此外,每种外观和感觉(或者由外观和感觉提供的{@code ComponentUI})可以在其生命中的不同时间访问默认值循环一些外观和感觉可能积极地查找默认值,以便更改默认值可能在安装外观和感觉后没有影响
 * 其他外观和感觉可能会延迟访问默认值,以便更改默认值可能影响现有的外观和感觉最后,其他外观和感觉可能不会以任何方式从默认表中配置自己无论如何,通常情况下,外观和感觉期望某些默认值,所以一般来说,{@code ComponentUI}
 * 提供的一种外观和感觉不会工作与另一种外观和感觉。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将不与未来的Swing版本兼容当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 支持长期存储所有JavaBeans&trade;已添加到<code> javabeans </code>包中请参见{@link javabeansXMLEncoder}。
 * 
 * 
 * @author Thomas Ball
 * @author Hans Muller
 */
public class UIManager implements Serializable
{
    /**
     * This class defines the state managed by the <code>UIManager</code>.  For
     * Swing applications the fields in this class could just as well
     * be static members of <code>UIManager</code> however we give them
     * "AppContext"
     * scope instead so that applets (and potentially multiple lightweight
     * applications running in a single VM) have their own state. For example,
     * an applet can alter its look and feel, see <code>setLookAndFeel</code>.
     * Doing so has no affect on other applets (or the browser).
     * <p>
     * 这个类定义了由<code> UIManager </code>管理的状态。
     * 对于Swing应用程序,这个类中的字段也可以是<code> UIManager </code>的静态成员,但是我们给它们"AppContext" applet(以及可能在单个VM中运行的多个轻量级应用程
     * 序)有自己的状态例如,一个applet可以改变它的外观和感觉,参见<code> setLookAndFeel </code>这样做对其他applet浏览器)。
     * 这个类定义了由<code> UIManager </code>管理的状态。
     * 
     */
    private static class LAFState
    {
        Properties swingProps;
        private UIDefaults[] tables = new UIDefaults[2];

        boolean initialized = false;
        boolean focusPolicyInitialized = false;
        MultiUIDefaults multiUIDefaults = new MultiUIDefaults(tables);
        LookAndFeel lookAndFeel;
        LookAndFeel multiLookAndFeel = null;
        Vector<LookAndFeel> auxLookAndFeels = null;
        SwingPropertyChangeSupport changeSupport;

        LookAndFeelInfo[] installedLAFs;

        UIDefaults getLookAndFeelDefaults() { return tables[0]; }
        void setLookAndFeelDefaults(UIDefaults x) { tables[0] = x; }

        UIDefaults getSystemDefaults() { return tables[1]; }
        void setSystemDefaults(UIDefaults x) { tables[1] = x; }

        /**
         * Returns the SwingPropertyChangeSupport for the current
         * AppContext.  If <code>create</code> is a true, a non-null
         * <code>SwingPropertyChangeSupport</code> will be returned, if
         * <code>create</code> is false and this has not been invoked
         * with true, null will be returned.
         * <p>
         *  返回当前AppContext的SwingPropertyChangeSupport如果<code> create </code>为true,则会返回非null <code> SwingProperty
         * ChangeSupport </code>,如果<code> create </code>未使用true调用,将返回null。
         * 
         */
        public synchronized SwingPropertyChangeSupport
                                 getPropertyChangeSupport(boolean create) {
            if (create && changeSupport == null) {
                changeSupport = new SwingPropertyChangeSupport(
                                         UIManager.class);
            }
            return changeSupport;
        }
    }




    /* Lock object used in place of class object for synchronization. (4187686)
    /* <p>
     */
    private static final Object classLock = new Object();

    /**
     * Return the <code>LAFState</code> object, lazily create one if necessary.
     * All access to the <code>LAFState</code> fields is done via this method,
     * for example:
     * <pre>
     *     getLAFState().initialized = true;
     * </pre>
     * <p>
     * 返回<code> LAFState </code>对象,如果需要,可以懒惰地创建所有访问<code> LAFState </code>字段是通过此方法完成的,例如：
     * <pre>
     *  getLAFState()initialized = true;
     * </pre>
     */
    private static LAFState getLAFState() {
        LAFState rv = (LAFState)SwingUtilities.appContextGet(
                SwingUtilities2.LAF_STATE_KEY);
        if (rv == null) {
            synchronized (classLock) {
                rv = (LAFState)SwingUtilities.appContextGet(
                        SwingUtilities2.LAF_STATE_KEY);
                if (rv == null) {
                    SwingUtilities.appContextPut(
                            SwingUtilities2.LAF_STATE_KEY,
                            (rv = new LAFState()));
                }
            }
        }
        return rv;
    }


    /* Keys used in the <code>swing.properties</code> properties file.
     * See loadUserProperties(), initialize().
     * <p>
     *  请参阅loadUserProperties(),initialize()
     * 
     */

    private static final String defaultLAFKey = "swing.defaultlaf";
    private static final String auxiliaryLAFsKey = "swing.auxiliarylaf";
    private static final String multiplexingLAFKey = "swing.plaf.multiplexinglaf";
    private static final String installedLAFsKey = "swing.installedlafs";
    private static final String disableMnemonicKey = "swing.disablenavaids";

    /**
     * Return a <code>swing.properties</code> file key for the attribute of specified
     * look and feel.  The attr is either "name" or "class", a typical
     * key would be: "swing.installedlaf.windows.name"
     * <p>
     *  为指定外观的属性返回<code> swingproperties </code>文件键attr是"name"或"class",典型的键是："swinginstalledlafwindowsname"。
     * 
     */
    private static String makeInstalledLAFKey(String laf, String attr) {
        return "swing.installedlaf." + laf + "." + attr;
    }

    /**
     * The location of the <code>swing.properties</code> property file is
     * implementation-specific.
     * It is typically located in the <code>lib</code> subdirectory of the Java
     * installation directory. This method returns a bogus filename
     * if <code>java.home</code> isn't defined.
     * <p>
     *  <code> swingproperties </code>属性文件的位置是特定于实现的它通常位于Java安装目录的<code> lib </code>子目录中此方法返回一个伪造的文件名如果<code>
     *  javahome < / code>未定义。
     * 
     */
    private static String makeSwingPropertiesFilename() {
        String sep = File.separator;
        // No need to wrap this in a doPrivileged as it's called from
        // a doPrivileged.
        String javaHome = System.getProperty("java.home");
        if (javaHome == null) {
            javaHome = "<java.home undefined>";
        }
        return javaHome + sep + "lib" + sep + "swing.properties";
    }


    /**
     * Provides a little information about an installed
     * <code>LookAndFeel</code> for the sake of configuring a menu or
     * for initial application set up.
     *
     * <p>
     * 提供有关安装的<code> LookAndFeel </code>的一些信息,以便配置菜单或初始应用程序设置
     * 
     * 
     * @see UIManager#getInstalledLookAndFeels
     * @see LookAndFeel
     */
    public static class LookAndFeelInfo {
        private String name;
        private String className;

        /**
         * Constructs a <code>UIManager</code>s
         * <code>LookAndFeelInfo</code> object.
         *
         * <p>
         *  构造一个<code> UIManager </code> s <code> LookAndFeelInfo </code>对象
         * 
         * 
         * @param name      a <code>String</code> specifying the name of
         *                      the look and feel
         * @param className a <code>String</code> specifying the name of
         *                      the class that implements the look and feel
         */
        public LookAndFeelInfo(String name, String className) {
            this.name = name;
            this.className = className;
        }

        /**
         * Returns the name of the look and feel in a form suitable
         * for a menu or other presentation
         * <p>
         *  返回适合菜单或其他演示文稿的形式的外观和感觉的名称
         * 
         * 
         * @return a <code>String</code> containing the name
         * @see LookAndFeel#getName
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the name of the class that implements this look and feel.
         * <p>
         *  返回实现此外观的类的名称
         * 
         * 
         * @return the name of the class that implements this
         *              <code>LookAndFeel</code>
         * @see LookAndFeel
         */
        public String getClassName() {
            return className;
        }

        /**
         * Returns a string that displays and identifies this
         * object's properties.
         *
         * <p>
         *  返回显示和标识此对象属性的字符串
         * 
         * 
         * @return a <code>String</code> representation of this object
         */
        public String toString() {
            return getClass().getName() + "[" + getName() + " " + getClassName() + "]";
        }
    }


    /**
     * The default value of <code>installedLAFS</code> is used when no
     * <code>swing.properties</code>
     * file is available or if the file doesn't contain a "swing.installedlafs"
     * property.
     *
     * <p>
     *  当没有<code> swingproperties </code>文件可用或者文件不包含"swinginstalledlafs"属性时,使用<code> installedLAFS </code>的默
     * 认值。
     * 
     * 
     * @see #initializeInstalledLAFs
     */
    private static LookAndFeelInfo[] installedLAFs;

    static {
        ArrayList<LookAndFeelInfo> iLAFs = new ArrayList<LookAndFeelInfo>(4);
        iLAFs.add(new LookAndFeelInfo(
                      "Metal", "javax.swing.plaf.metal.MetalLookAndFeel"));
        iLAFs.add(new LookAndFeelInfo(
                      "Nimbus", "javax.swing.plaf.nimbus.NimbusLookAndFeel"));
        iLAFs.add(new LookAndFeelInfo("CDE/Motif",
                  "com.sun.java.swing.plaf.motif.MotifLookAndFeel"));

        // Only include windows on Windows boxs.
        OSInfo.OSType osType = AccessController.doPrivileged(OSInfo.getOSTypeAction());
        if (osType == OSInfo.OSType.WINDOWS) {
            iLAFs.add(new LookAndFeelInfo("Windows",
                        "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));
            if (Toolkit.getDefaultToolkit().getDesktopProperty(
                    "win.xpstyle.themeActive") != null) {
                iLAFs.add(new LookAndFeelInfo("Windows Classic",
                 "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"));
            }
        }
        else if (osType == OSInfo.OSType.MACOSX) {
            iLAFs.add(new LookAndFeelInfo("Mac OS X", "com.apple.laf.AquaLookAndFeel"));
        }
        else {
            // GTK is not shipped on Windows.
            iLAFs.add(new LookAndFeelInfo("GTK+",
                  "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"));
        }
        installedLAFs = iLAFs.toArray(new LookAndFeelInfo[iLAFs.size()]);
    }


    /**
     * Returns an array of {@code LookAndFeelInfo}s representing the
     * {@code LookAndFeel} implementations currently available. The
     * <code>LookAndFeelInfo</code> objects can be used by an
     * application to construct a menu of look and feel options for
     * the user, or to determine which look and feel to set at startup
     * time. To avoid the penalty of creating numerous {@code
     * LookAndFeel} objects, {@code LookAndFeelInfo} maintains the
     * class name of the {@code LookAndFeel} class, not the actual
     * {@code LookAndFeel} instance.
     * <p>
     * The following example illustrates setting the current look and feel
     * from an instance of {@code LookAndFeelInfo}:
     * <pre>
     *   UIManager.setLookAndFeel(info.getClassName());
     * </pre>
     *
     * <p>
     * 返回表示当前可用的{@code LookAndFeel}实现的{@code LookAndFeelInfo}的数组可以由应用程序使用<code> LookAndFeelInfo </code>对象来构造
     * 用户的外观和感觉选项菜单,以确定在启动时设置的外观和感觉为了避免创建大量{@code LookAndFeel}对象的代价,{@code LookAndFeelInfo}维护{@code LookAndFeel}
     * 类的类名,而不是实际的{@code LookAndFeel} }实例。
     * <p>
     *  以下示例说明如何从{@code LookAndFeelInfo}的实例设置当前外观：
     * <pre>
     *  UIManagersetLookAndFeel(infogetClassName());
     * </pre>
     * 
     * 
     * @return an array of <code>LookAndFeelInfo</code> objects
     * @see #setLookAndFeel
     */
    public static LookAndFeelInfo[] getInstalledLookAndFeels() {
        maybeInitialize();
        LookAndFeelInfo[] ilafs = getLAFState().installedLAFs;
        if (ilafs == null) {
            ilafs = installedLAFs;
        }
        LookAndFeelInfo[] rv = new LookAndFeelInfo[ilafs.length];
        System.arraycopy(ilafs, 0, rv, 0, ilafs.length);
        return rv;
    }


    /**
     * Sets the set of available look and feels. While this method does
     * not check to ensure all of the {@code LookAndFeelInfos} are
     * {@code non-null}, it is strongly recommended that only {@code non-null}
     * values are supplied in the {@code infos} array.
     *
     * <p>
     * 设置可用的外观和感觉的集合虽然此方法不检查以确保所有{@code LookAndFeelInfos}是{@code非空},强烈建议只提供{@code非null}值{@code infos}数组
     * 
     * 
     * @param infos set of <code>LookAndFeelInfo</code> objects specifying
     *        the available look and feels
     *
     * @see #getInstalledLookAndFeels
     * @throws NullPointerException if {@code infos} is {@code null}
     */
    public static void setInstalledLookAndFeels(LookAndFeelInfo[] infos)
        throws SecurityException
    {
        maybeInitialize();
        LookAndFeelInfo[] newInfos = new LookAndFeelInfo[infos.length];
        System.arraycopy(infos, 0, newInfos, 0, infos.length);
        getLAFState().installedLAFs = newInfos;
    }


    /**
     * Adds the specified look and feel to the set of available look
     * and feels. While this method allows a {@code null} {@code info},
     * it is strongly recommended that a {@code non-null} value be used.
     *
     * <p>
     *  将指定的外观和感觉添加到可用的外观和感觉集合虽然此方法允许{@code null} {@code info},但强烈建议使用{@code非null}值
     * 
     * 
     * @param info a <code>LookAndFeelInfo</code> object that names the
     *          look and feel and identifies the class that implements it
     * @see #setInstalledLookAndFeels
     */
    public static void installLookAndFeel(LookAndFeelInfo info) {
        LookAndFeelInfo[] infos = getInstalledLookAndFeels();
        LookAndFeelInfo[] newInfos = new LookAndFeelInfo[infos.length + 1];
        System.arraycopy(infos, 0, newInfos, 0, infos.length);
        newInfos[infos.length] = info;
        setInstalledLookAndFeels(newInfos);
    }


    /**
     * Adds the specified look and feel to the set of available look
     * and feels. While this method does not check the
     * arguments in any way, it is strongly recommended that {@code
     * non-null} values be supplied.
     *
     * <p>
     *  将指定的外观和感觉添加到可用的外观和感觉集合虽然此方法不以任何方式检查参数,但强烈建议提供{@code non-null}值
     * 
     * 
     * @param name descriptive name of the look and feel
     * @param className name of the class that implements the look and feel
     * @see #setInstalledLookAndFeels
     */
    public static void installLookAndFeel(String name, String className) {
        installLookAndFeel(new LookAndFeelInfo(name, className));
    }


    /**
     * Returns the current look and feel or <code>null</code>.
     *
     * <p>
     *  返回当前的外观和感觉或<code> null </code>
     * 
     * 
     * @return current look and feel, or <code>null</code>
     * @see #setLookAndFeel
     */
    public static LookAndFeel getLookAndFeel() {
        maybeInitialize();
        return getLAFState().lookAndFeel;
    }


    /**
     * Sets the current look and feel to {@code newLookAndFeel}.
     * If the current look and feel is {@code non-null} {@code
     * uninitialize} is invoked on it. If {@code newLookAndFeel} is
     * {@code non-null}, {@code initialize} is invoked on it followed
     * by {@code getDefaults}. The defaults returned from {@code
     * newLookAndFeel.getDefaults()} replace those of the defaults
     * from the previous look and feel. If the {@code newLookAndFeel} is
     * {@code null}, the look and feel defaults are set to {@code null}.
     * <p>
     * A value of {@code null} can be used to set the look and feel
     * to {@code null}. As the {@code LookAndFeel} is required for
     * most of Swing to function, setting the {@code LookAndFeel} to
     * {@code null} is strongly discouraged.
     * <p>
     * This is a JavaBeans bound property.
     *
     * <p>
     * 将当前外观设置为{@code newLookAndFeel}如果当前外观是{@code非空} {@code uninitialize}被调用,如果{@code newLookAndFeel}是{@code non-null}
     * 调用{@code initialize},然后调用{@code getDefaults}从{@code newLookAndFeelgetDefaults()}返回的默认值会替换那些来自之前外观的默认值
     * 如果{@code newLookAndFeel}是{@code null },外观和默认设置为{@code null}。
     * <p>
     *  {@code null}可以用来设置外观和感觉{@code null}因为Swing的大部分功能需要{@code LookAndFeel},所以将{@code LookAndFeel}设置为{@code null}
     * 。
     * <p>
     *  这是一个JavaBeans绑定属性
     * 
     * 
     * @param newLookAndFeel {@code LookAndFeel} to install
     * @throws UnsupportedLookAndFeelException if
     *          {@code newLookAndFeel} is {@code non-null} and
     *          {@code newLookAndFeel.isSupportedLookAndFeel()} returns
     *          {@code false}
     * @see #getLookAndFeel
     */
    public static void setLookAndFeel(LookAndFeel newLookAndFeel)
        throws UnsupportedLookAndFeelException
    {
        if ((newLookAndFeel != null) && !newLookAndFeel.isSupportedLookAndFeel()) {
            String s = newLookAndFeel.toString() + " not supported on this platform";
            throw new UnsupportedLookAndFeelException(s);
        }

        LAFState lafState = getLAFState();
        LookAndFeel oldLookAndFeel = lafState.lookAndFeel;
        if (oldLookAndFeel != null) {
            oldLookAndFeel.uninitialize();
        }

        lafState.lookAndFeel = newLookAndFeel;
        if (newLookAndFeel != null) {
            sun.swing.DefaultLookup.setDefaultLookup(null);
            newLookAndFeel.initialize();
            lafState.setLookAndFeelDefaults(newLookAndFeel.getDefaults());
        }
        else {
            lafState.setLookAndFeelDefaults(null);
        }

        SwingPropertyChangeSupport changeSupport = lafState.
                                         getPropertyChangeSupport(false);
        if (changeSupport != null) {
            changeSupport.firePropertyChange("lookAndFeel", oldLookAndFeel,
                                             newLookAndFeel);
        }
    }


    /**
     * Loads the {@code LookAndFeel} specified by the given class
     * name, using the current thread's context class loader, and
     * passes it to {@code setLookAndFeel(LookAndFeel)}.
     *
     * <p>
     * 加载由给定类名指定的{@code LookAndFeel},使用当前线程的上下文类加载器,并将其传递给{@code setLookAndFeel(LookAndFeel)}
     * 
     * 
     * @param className  a string specifying the name of the class that implements
     *        the look and feel
     * @exception ClassNotFoundException if the <code>LookAndFeel</code>
     *           class could not be found
     * @exception InstantiationException if a new instance of the class
     *          couldn't be created
     * @exception IllegalAccessException if the class or initializer isn't accessible
     * @exception UnsupportedLookAndFeelException if
     *          <code>lnf.isSupportedLookAndFeel()</code> is false
     * @throws ClassCastException if {@code className} does not identify
     *         a class that extends {@code LookAndFeel}
     */
    public static void setLookAndFeel(String className)
        throws ClassNotFoundException,
               InstantiationException,
               IllegalAccessException,
               UnsupportedLookAndFeelException
    {
        if ("javax.swing.plaf.metal.MetalLookAndFeel".equals(className)) {
            // Avoid reflection for the common case of metal.
            setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
        }
        else {
            Class lnfClass = SwingUtilities.loadSystemClass(className);
            setLookAndFeel((LookAndFeel)(lnfClass.newInstance()));
        }
    }

    /**
     * Returns the name of the <code>LookAndFeel</code> class that implements
     * the native system look and feel if there is one, otherwise
     * the name of the default cross platform <code>LookAndFeel</code>
     * class. This value can be overriden by setting the
     * <code>swing.systemlaf</code> system property.
     *
     * <p>
     *  返回实现原生系统外观的<code> LookAndFeel </code>类的名称,否则为默认跨平台的名称<code> LookAndFeel </code> class此值可以被覆盖设置<code>
     *  swingsystemlaf </code>系统属性。
     * 
     * 
     * @return the <code>String</code> of the <code>LookAndFeel</code>
     *          class
     *
     * @see #setLookAndFeel
     * @see #getCrossPlatformLookAndFeelClassName
     */
    public static String getSystemLookAndFeelClassName() {
        String systemLAF = AccessController.doPrivileged(
                             new GetPropertyAction("swing.systemlaf"));
        if (systemLAF != null) {
            return systemLAF;
        }
        OSInfo.OSType osType = AccessController.doPrivileged(OSInfo.getOSTypeAction());
        if (osType == OSInfo.OSType.WINDOWS) {
            return "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        } else {
            String desktop = AccessController.doPrivileged(new GetPropertyAction("sun.desktop"));
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            if ("gnome".equals(desktop) &&
                    toolkit instanceof SunToolkit &&
                    ((SunToolkit) toolkit).isNativeGTKAvailable()) {
                // May be set on Linux and Solaris boxs.
                return "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            }
            if (osType == OSInfo.OSType.MACOSX) {
                if (toolkit.getClass() .getName()
                                       .equals("sun.lwawt.macosx.LWCToolkit")) {
                    return "com.apple.laf.AquaLookAndFeel";
                }
            }
            if (osType == OSInfo.OSType.SOLARIS) {
                return "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            }
        }
        return getCrossPlatformLookAndFeelClassName();
    }


    /**
     * Returns the name of the <code>LookAndFeel</code> class that implements
     * the default cross platform look and feel -- the Java
     * Look and Feel (JLF).  This value can be overriden by setting the
     * <code>swing.crossplatformlaf</code> system property.
     *
     * <p>
     *  返回实现默认跨平台外观的<code> LookAndFeel </code>类的名称 -  Java Look and Feel(JLF)该值可以通过设置<code> swingcrossplatfo
     * rmlaf </code>系统属性。
     * 
     * 
     * @return  a string with the JLF implementation-class
     * @see #setLookAndFeel
     * @see #getSystemLookAndFeelClassName
     */
    public static String getCrossPlatformLookAndFeelClassName() {
        String laf = AccessController.doPrivileged(
                             new GetPropertyAction("swing.crossplatformlaf"));
        if (laf != null) {
            return laf;
        }
        return "javax.swing.plaf.metal.MetalLookAndFeel";
    }


    /**
     * Returns the defaults. The returned defaults resolve using the
     * logic specified in the class documentation.
     *
     * <p>
     * 返回默认值返回的默认值使用类文档中指定的逻辑进行解析
     * 
     * 
     * @return a <code>UIDefaults</code> object containing the default values
     */
    public static UIDefaults getDefaults() {
        maybeInitialize();
        return getLAFState().multiUIDefaults;
    }

    /**
     * Returns a font from the defaults. If the value for {@code key} is
     * not a {@code Font}, {@code null} is returned.
     *
     * <p>
     *  从默认值返回字体如果{@code key}的值不是{@code Font},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the font
     * @return the <code>Font</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static Font getFont(Object key) {
        return getDefaults().getFont(key);
    }

    /**
     * Returns a font from the defaults that is appropriate
     * for the given locale. If the value for {@code key} is
     * not a {@code Font}, {@code null} is returned.
     *
     * <p>
     *  从适合于给定语言环境的默认值返回字体如果{@code key}的值不是{@code Font},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the font
     * @param l the <code>Locale</code> for which the font is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the <code>Font</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static Font getFont(Object key, Locale l) {
        return getDefaults().getFont(key,l);
    }

    /**
     * Returns a color from the defaults. If the value for {@code key} is
     * not a {@code Color}, {@code null} is returned.
     *
     * <p>
     *  从默认值返回颜色如果{@code key}的值不是{@code Color},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the color
     * @return the <code>Color</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static Color getColor(Object key) {
        return getDefaults().getColor(key);
    }

    /**
     * Returns a color from the defaults that is appropriate
     * for the given locale. If the value for {@code key} is
     * not a {@code Color}, {@code null} is returned.
     *
     * <p>
     *  返回适合于给定语言环境的默认值的颜色如果{@code key}的值不是{@code Color},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the color
     * @param l the <code>Locale</code> for which the color is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the <code>Color</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static Color getColor(Object key, Locale l) {
        return getDefaults().getColor(key,l);
    }

    /**
     * Returns an <code>Icon</code> from the defaults. If the value for
     * {@code key} is not an {@code Icon}, {@code null} is returned.
     *
     * <p>
     *  从默认值中返回<code> Icon </code>如果{@code key}的值不是{@code Icon},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the icon
     * @return the <code>Icon</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static Icon getIcon(Object key) {
        return getDefaults().getIcon(key);
    }

    /**
     * Returns an <code>Icon</code> from the defaults that is appropriate
     * for the given locale. If the value for
     * {@code key} is not an {@code Icon}, {@code null} is returned.
     *
     * <p>
     * 从适用于给定语言环境的默认值返回<code> Icon </code>如果{@code key}的值不是{@code Icon},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the icon
     * @param l the <code>Locale</code> for which the icon is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the <code>Icon</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static Icon getIcon(Object key, Locale l) {
        return getDefaults().getIcon(key,l);
    }

    /**
     * Returns a border from the defaults. If the value for
     * {@code key} is not a {@code Border}, {@code null} is returned.
     *
     * <p>
     *  从默认值返回边框如果{@code key}的值不是{@code Border},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the border
     * @return the <code>Border</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static Border getBorder(Object key) {
        return getDefaults().getBorder(key);
    }

    /**
     * Returns a border from the defaults that is appropriate
     * for the given locale.  If the value for
     * {@code key} is not a {@code Border}, {@code null} is returned.
     *
     * <p>
     *  从适合给定语言环境的默认值返回边框如果{@code key}的值不是{@code Border},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the border
     * @param l the <code>Locale</code> for which the border is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the <code>Border</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static Border getBorder(Object key, Locale l) {
        return getDefaults().getBorder(key,l);
    }

    /**
     * Returns a string from the defaults. If the value for
     * {@code key} is not a {@code String}, {@code null} is returned.
     *
     * <p>
     *  从默认值返回一个字符串如果{@code key}的值不是{@code String},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the string
     * @return the <code>String</code>
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static String getString(Object key) {
        return getDefaults().getString(key);
    }

    /**
     * Returns a string from the defaults that is appropriate for the
     * given locale.  If the value for
     * {@code key} is not a {@code String}, {@code null} is returned.
     *
     * <p>
     *  返回适用于给定语言环境的默认值的字符串如果{@code key}的值不是{@code String},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the string
     * @param l the <code>Locale</code> for which the string is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the <code>String</code>
     * @since 1.4
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static String getString(Object key, Locale l) {
        return getDefaults().getString(key,l);
    }

    /**
     * Returns a string from the defaults that is appropriate for the
     * given locale.  If the value for
     * {@code key} is not a {@code String}, {@code null} is returned.
     *
     * <p>
     * 返回适用于给定语言环境的默认值的字符串如果{@code key}的值不是{@code String},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the string
     * @param c {@code Component} used to determine the locale;
     *          {@code null} implies the default locale as
     *          returned by {@code Locale.getDefault()}
     * @return the <code>String</code>
     * @throws NullPointerException if {@code key} is {@code null}
     */
    static String getString(Object key, Component c) {
        Locale l = (c == null) ? Locale.getDefault() : c.getLocale();
        return getString(key, l);
    }

    /**
     * Returns an integer from the defaults. If the value for
     * {@code key} is not an {@code Integer}, or does not exist,
     * {@code 0} is returned.
     *
     * <p>
     *  从默认值返回一个整数如果{@code key}的值不是{@code Integer}或不存在,则返回{@code 0}
     * 
     * 
     * @param key  an <code>Object</code> specifying the int
     * @return the int
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static int getInt(Object key) {
        return getDefaults().getInt(key);
    }

    /**
     * Returns an integer from the defaults that is appropriate
     * for the given locale. If the value for
     * {@code key} is not an {@code Integer}, or does not exist,
     * {@code 0} is returned.
     *
     * <p>
     *  返回适合于给定语言环境的默认值的整数如果{@code key}的值不是{@code Integer}或不存在,则返回{@code 0}
     * 
     * 
     * @param key  an <code>Object</code> specifying the int
     * @param l the <code>Locale</code> for which the int is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the int
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static int getInt(Object key, Locale l) {
        return getDefaults().getInt(key,l);
    }

    /**
     * Returns a boolean from the defaults which is associated with
     * the key value. If the key is not found or the key doesn't represent
     * a boolean value then {@code false} is returned.
     *
     * <p>
     *  从与键值相关联的默认值返回布尔值如果未找到键或键不表示布尔值,则返回{@code false}
     * 
     * 
     * @param key  an <code>Object</code> specifying the key for the desired boolean value
     * @return the boolean value corresponding to the key
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static boolean getBoolean(Object key) {
        return getDefaults().getBoolean(key);
    }

    /**
     * Returns a boolean from the defaults which is associated with
     * the key value and the given <code>Locale</code>. If the key is not
     * found or the key doesn't represent
     * a boolean value then {@code false} will be returned.
     *
     * <p>
     * 从与键值和给定的<code> Locale </code>相关联的默认值返回布尔值。如果未找到键或键未表示布尔值,则将返回{@code false}
     * 
     * 
     * @param key  an <code>Object</code> specifying the key for the desired
     *             boolean value
     * @param l the <code>Locale</code> for which the boolean is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the boolean value corresponding to the key
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static boolean getBoolean(Object key, Locale l) {
        return getDefaults().getBoolean(key,l);
    }

    /**
     * Returns an <code>Insets</code> object from the defaults. If the value
     * for {@code key} is not an {@code Insets}, {@code null} is returned.
     *
     * <p>
     *  从默认值返回<code> Insets </code>对象如果{@code key}的值不是{@code Insets},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the <code>Insets</code> object
     * @return the <code>Insets</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static Insets getInsets(Object key) {
        return getDefaults().getInsets(key);
    }

    /**
     * Returns an <code>Insets</code> object from the defaults that is
     * appropriate for the given locale. If the value
     * for {@code key} is not an {@code Insets}, {@code null} is returned.
     *
     * <p>
     *  从适用于给定语言环境的默认值返回<code> Insets </code>对象如果{@code key}的值不是{@code Insets},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the <code>Insets</code> object
     * @param l the <code>Locale</code> for which the object is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the <code>Insets</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static Insets getInsets(Object key, Locale l) {
        return getDefaults().getInsets(key,l);
    }

    /**
     * Returns a dimension from the defaults. If the value
     * for {@code key} is not a {@code Dimension}, {@code null} is returned.
     *
     * <p>
     *  从默认值返回一个维度如果{@code key}的值不是{@code Dimension},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the dimension object
     * @return the <code>Dimension</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static Dimension getDimension(Object key) {
        return getDefaults().getDimension(key);
    }

    /**
     * Returns a dimension from the defaults that is appropriate
     * for the given locale. If the value
     * for {@code key} is not a {@code Dimension}, {@code null} is returned.
     *
     * <p>
     * 从适合给定语言环境的默认值返回一个维度如果{@code key}的值不是{@code维度},则返回{@code null}
     * 
     * 
     * @param key  an <code>Object</code> specifying the dimension object
     * @param l the <code>Locale</code> for which the object is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the <code>Dimension</code> object
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static Dimension getDimension(Object key, Locale l) {
        return getDefaults().getDimension(key,l);
    }

    /**
     * Returns an object from the defaults.
     *
     * <p>
     *  从默认值返回对象
     * 
     * 
     * @param key  an <code>Object</code> specifying the desired object
     * @return the <code>Object</code>
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public static Object get(Object key) {
        return getDefaults().get(key);
    }

    /**
     * Returns an object from the defaults that is appropriate for
     * the given locale.
     *
     * <p>
     *  从适合于给定语言环境的默认值返回对象
     * 
     * 
     * @param key  an <code>Object</code> specifying the desired object
     * @param l the <code>Locale</code> for which the object is desired; refer
     *        to {@code UIDefaults} for details on how a {@code null}
     *        {@code Locale} is handled
     * @return the <code>Object</code>
     * @throws NullPointerException if {@code key} is {@code null}
     * @since 1.4
     */
    public static Object get(Object key, Locale l) {
        return getDefaults().get(key,l);
    }

    /**
     * Stores an object in the developer defaults. This is a cover method
     * for {@code getDefaults().put(key, value)}. This only effects the
     * developer defaults, not the system or look and feel defaults.
     *
     * <p>
     *  在开发人员默认值中存储对象这是{@code getDefaults()put(key,value)}的覆盖方法这只影响开发人员的默认值,而不影响系统或外观和默认值
     * 
     * 
     * @param key    an <code>Object</code> specifying the retrieval key
     * @param value  the <code>Object</code> to store; refer to
     *               {@code UIDefaults} for details on how {@code null} is
     *               handled
     * @return the <code>Object</code> returned by {@link UIDefaults#put}
     * @throws NullPointerException if {@code key} is {@code null}
     * @see UIDefaults#put
     */
    public static Object put(Object key, Object value) {
        return getDefaults().put(key, value);
    }

    /**
     * Returns the appropriate {@code ComponentUI} implementation for
     * {@code target}. Typically, this is a cover for
     * {@code getDefaults().getUI(target)}. However, if an auxiliary
     * look and feel has been installed, this first invokes
     * {@code getUI(target)} on the multiplexing look and feel's
     * defaults, and returns that value if it is {@code non-null}.
     *
     * <p>
     * 为{@code target}返回适当的{@code ComponentUI}实现通常,这是{@code getDefaults()getUI(target)}的封面。
     * 但是,如果已经安装了辅助外观,代码getUI(target)}对复用的外观和感觉的默认值,并返回该值,如果它是{@code非null}。
     * 
     * 
     * @param target the <code>JComponent</code> to return the
     *        {@code ComponentUI} for
     * @return the <code>ComponentUI</code> object for {@code target}
     * @throws NullPointerException if {@code target} is {@code null}
     * @see UIDefaults#getUI
     */
    public static ComponentUI getUI(JComponent target) {
        maybeInitialize();
        maybeInitializeFocusPolicy(target);
        ComponentUI ui = null;
        LookAndFeel multiLAF = getLAFState().multiLookAndFeel;
        if (multiLAF != null) {
            // This can return null if the multiplexing look and feel
            // doesn't support a particular UI.
            ui = multiLAF.getDefaults().getUI(target);
        }
        if (ui == null) {
            ui = getDefaults().getUI(target);
        }
        return ui;
    }


    /**
     * Returns the {@code UIDefaults} from the current look and feel,
     * that were obtained at the time the look and feel was installed.
     * <p>
     * In general, developers should use the {@code UIDefaults} returned from
     * {@code getDefaults()}. As the current look and feel may expect
     * certain values to exist, altering the {@code UIDefaults} returned
     * from this method could have unexpected results.
     *
     * <p>
     *  从当前的外观和感觉返回{@code UIDefaults},这是在外观和感觉安装时获得的
     * <p>
     *  一般来说,开发人员应该使用从{@code getDefaults()}返回的{@code UIDefaults}由于当前的外观和感觉可能存在某些值,改变从此方法返回的{@code UIDefaults}
     * 可能会有意想不到的结果。
     * 
     * 
     * @return <code>UIDefaults</code> from the current look and feel
     * @see #getDefaults
     * @see #setLookAndFeel(LookAndFeel)
     * @see LookAndFeel#getDefaults
     */
    public static UIDefaults getLookAndFeelDefaults() {
        maybeInitialize();
        return getLAFState().getLookAndFeelDefaults();
    }

    /**
     * Finds the Multiplexing <code>LookAndFeel</code>.
     * <p>
     *  找到Multiplexing <code> LookAndFeel </code>
     * 
     */
    private static LookAndFeel getMultiLookAndFeel() {
        LookAndFeel multiLookAndFeel = getLAFState().multiLookAndFeel;
        if (multiLookAndFeel == null) {
            String defaultName = "javax.swing.plaf.multi.MultiLookAndFeel";
            String className = getLAFState().swingProps.getProperty(multiplexingLAFKey, defaultName);
            try {
                Class lnfClass = SwingUtilities.loadSystemClass(className);
                multiLookAndFeel = (LookAndFeel)lnfClass.newInstance();
            } catch (Exception exc) {
                System.err.println("UIManager: failed loading " + className);
            }
        }
        return multiLookAndFeel;
    }

    /**
     * Adds a <code>LookAndFeel</code> to the list of auxiliary look and feels.
     * The auxiliary look and feels tell the multiplexing look and feel what
     * other <code>LookAndFeel</code> classes for a component instance are to be used
     * in addition to the default <code>LookAndFeel</code> class when creating a
     * multiplexing UI.  The change will only take effect when a new
     * UI class is created or when the default look and feel is changed
     * on a component instance.
     * <p>Note these are not the same as the installed look and feels.
     *
     * <p>
     * 在辅助外观和感觉列表中添加一个<code> LookAndFeel </code>辅助外观和感觉告诉复用外观和感觉组件实例的其他<code>类LookAndFeel </code>创建多重UI时,默认的
     * <code> LookAndFeel </code>类更改将仅在创建新UI类或在组件实例上更改默认外观时生效<p>注意这些不是与安装的外观和感觉相同。
     * 
     * 
     * @param laf the <code>LookAndFeel</code> object
     * @see #removeAuxiliaryLookAndFeel
     * @see #setLookAndFeel
     * @see #getAuxiliaryLookAndFeels
     * @see #getInstalledLookAndFeels
     */
    static public void addAuxiliaryLookAndFeel(LookAndFeel laf) {
        maybeInitialize();

        if (!laf.isSupportedLookAndFeel()) {
            // Ideally we would throw an exception here, but it's too late
            // for that.
            return;
        }
        Vector<LookAndFeel> v = getLAFState().auxLookAndFeels;
        if (v == null) {
            v = new Vector<LookAndFeel>();
        }

        if (!v.contains(laf)) {
            v.addElement(laf);
            laf.initialize();
            getLAFState().auxLookAndFeels = v;

            if (getLAFState().multiLookAndFeel == null) {
                getLAFState().multiLookAndFeel = getMultiLookAndFeel();
            }
        }
    }

    /**
     * Removes a <code>LookAndFeel</code> from the list of auxiliary look and feels.
     * The auxiliary look and feels tell the multiplexing look and feel what
     * other <code>LookAndFeel</code> classes for a component instance are to be used
     * in addition to the default <code>LookAndFeel</code> class when creating a
     * multiplexing UI.  The change will only take effect when a new
     * UI class is created or when the default look and feel is changed
     * on a component instance.
     * <p>Note these are not the same as the installed look and feels.
     * <p>
     * 从辅助外观和感觉的列表中删除<code> LookAndFeel </code>辅助外观和感觉告诉多路复用外观和感觉组件实例的其他<code> LookAndFeel </code>类要另外使用创建多重
     * UI时,默认的<code> LookAndFeel </code>类更改将仅在创建新UI类或在组件实例上更改默认外观时生效<p>注意这些不是与安装的外观和感觉相同。
     * 
     * 
     * @return true if the <code>LookAndFeel</code> was removed from the list
     * @see #removeAuxiliaryLookAndFeel
     * @see #getAuxiliaryLookAndFeels
     * @see #setLookAndFeel
     * @see #getInstalledLookAndFeels
     */
    static public boolean removeAuxiliaryLookAndFeel(LookAndFeel laf) {
        maybeInitialize();

        boolean result;

        Vector<LookAndFeel> v = getLAFState().auxLookAndFeels;
        if ((v == null) || (v.size() == 0)) {
            return false;
        }

        result = v.removeElement(laf);
        if (result) {
            if (v.size() == 0) {
                getLAFState().auxLookAndFeels = null;
                getLAFState().multiLookAndFeel = null;
            } else {
                getLAFState().auxLookAndFeels = v;
            }
        }
        laf.uninitialize();

        return result;
    }

    /**
     * Returns the list of auxiliary look and feels (can be <code>null</code>).
     * The auxiliary look and feels tell the multiplexing look and feel what
     * other <code>LookAndFeel</code> classes for a component instance are
     * to be used in addition to the default LookAndFeel class when creating a
     * multiplexing UI.
     * <p>Note these are not the same as the installed look and feels.
     *
     * <p>
     * 返回辅助外观和感觉的列表(可以<code> null </code>)辅助外观和感觉告诉复用外观和感觉什么其他<code> LookAndFeel </code>类用于组件实例要使用除了默认的LookA
     * ndFeel类,当创建一个多路复用UI <p>注意这些不是与安装的外观和感觉相同。
     * 
     * 
     * @return list of auxiliary <code>LookAndFeel</code>s or <code>null</code>
     * @see #addAuxiliaryLookAndFeel
     * @see #removeAuxiliaryLookAndFeel
     * @see #setLookAndFeel
     * @see #getInstalledLookAndFeels
     */
    static public LookAndFeel[] getAuxiliaryLookAndFeels() {
        maybeInitialize();

        Vector<LookAndFeel> v = getLAFState().auxLookAndFeels;
        if ((v == null) || (v.size() == 0)) {
            return null;
        }
        else {
            LookAndFeel[] rv = new LookAndFeel[v.size()];
            for (int i = 0; i < rv.length; i++) {
                rv[i] = v.elementAt(i);
            }
            return rv;
        }
    }


    /**
     * Adds a <code>PropertyChangeListener</code> to the listener list.
     * The listener is registered for all properties.
     *
     * <p>
     *  向侦听器列表中添加<code> PropertyChangeListener </code>为所有属性注册侦听器
     * 
     * 
     * @param listener  the <code>PropertyChangeListener</code> to be added
     * @see java.beans.PropertyChangeSupport
     */
    public static void addPropertyChangeListener(PropertyChangeListener listener)
    {
        synchronized (classLock) {
            getLAFState().getPropertyChangeSupport(true).
                             addPropertyChangeListener(listener);
        }
    }


    /**
     * Removes a <code>PropertyChangeListener</code> from the listener list.
     * This removes a <code>PropertyChangeListener</code> that was registered
     * for all properties.
     *
     * <p>
     *  从侦听器列表中删除<code> PropertyChangeListener </code>这会删除为所有属性注册的<code> PropertyChangeListener </code>
     * 
     * 
     * @param listener  the <code>PropertyChangeListener</code> to be removed
     * @see java.beans.PropertyChangeSupport
     */
    public static void removePropertyChangeListener(PropertyChangeListener listener)
    {
        synchronized (classLock) {
            getLAFState().getPropertyChangeSupport(true).
                          removePropertyChangeListener(listener);
        }
    }


    /**
     * Returns an array of all the <code>PropertyChangeListener</code>s added
     * to this UIManager with addPropertyChangeListener().
     *
     * <p>
     *  返回添加到此UIManager的所有<code> PropertyChangeListener </code>数组,其中包含addPropertyChangeListener()
     * 
     * 
     * @return all of the <code>PropertyChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public static PropertyChangeListener[] getPropertyChangeListeners() {
        synchronized(classLock) {
            return getLAFState().getPropertyChangeSupport(true).
                      getPropertyChangeListeners();
        }
    }

    private static Properties loadSwingProperties()
    {
        /* Don't bother checking for Swing properties if untrusted, as
         * there's no way to look them up without triggering SecurityExceptions.
         * <p>
         * 没有办法查找它们而不触发SecurityExceptions
         * 
         */
        if (UIManager.class.getClassLoader() != null) {
            return new Properties();
        }
        else {
            final Properties props = new Properties();

            java.security.AccessController.doPrivileged(
                new java.security.PrivilegedAction<Object>() {
                public Object run() {
                    OSInfo.OSType osType = AccessController.doPrivileged(OSInfo.getOSTypeAction());
                    if (osType == OSInfo.OSType.MACOSX) {
                        props.put(defaultLAFKey, getSystemLookAndFeelClassName());
                    }

                    try {
                        File file = new File(makeSwingPropertiesFilename());

                        if (file.exists()) {
                            // InputStream has been buffered in Properties
                            // class
                            FileInputStream ins = new FileInputStream(file);
                            props.load(ins);
                            ins.close();
                        }
                    }
                    catch (Exception e) {
                        // No such file, or file is otherwise non-readable.
                    }

                    // Check whether any properties were overridden at the
                    // command line.
                    checkProperty(props, defaultLAFKey);
                    checkProperty(props, auxiliaryLAFsKey);
                    checkProperty(props, multiplexingLAFKey);
                    checkProperty(props, installedLAFsKey);
                    checkProperty(props, disableMnemonicKey);
                    // Don't care about return value.
                    return null;
                }
            });
            return props;
        }
    }

    private static void checkProperty(Properties props, String key) {
        // No need to do catch the SecurityException here, this runs
        // in a doPrivileged.
        String value = System.getProperty(key);
        if (value != null) {
            props.put(key, value);
        }
    }


    /**
     * If a <code>swing.properties</code> file exist and it has a
     * <code>swing.installedlafs</code> property
     * then initialize the <code>installedLAFs</code> field.
     *
     * <p>
     *  如果存在<code> swingproperties </code>文件并且它有一个<code> swinginstalledlafs </code>属性,然后初始化<code> installedL
     * AFs </code>字段。
     * 
     * 
     * @see #getInstalledLookAndFeels
     */
    private static void initializeInstalledLAFs(Properties swingProps)
    {
        String ilafsString = swingProps.getProperty(installedLAFsKey);
        if (ilafsString == null) {
            return;
        }

        /* Create a vector that contains the value of the swing.installedlafs
         * property.  For example given "swing.installedlafs=motif,windows"
         * lafs = {"motif", "windows"}.
         * <p>
         *  属性例如给定"swinginstalledlafs = motif,windows"lafs = {"motif","windows"}
         * 
         */
        Vector<String> lafs = new Vector<String>();
        StringTokenizer st = new StringTokenizer(ilafsString, ",", false);
        while (st.hasMoreTokens()) {
            lafs.addElement(st.nextToken());
        }

        /* Look up the name and class for each name in the "swing.installedlafs"
         * list.  If they both exist then add a LookAndFeelInfo to
         * the installedLafs array.
         * <p>
         *  list如果它们都存在,那么添加一个LookAndFeelInfo到installedLafs数组
         * 
         */
        Vector<LookAndFeelInfo> ilafs = new Vector<LookAndFeelInfo>(lafs.size());
        for (String laf : lafs) {
            String name = swingProps.getProperty(makeInstalledLAFKey(laf, "name"), laf);
            String cls = swingProps.getProperty(makeInstalledLAFKey(laf, "class"));
            if (cls != null) {
                ilafs.addElement(new LookAndFeelInfo(name, cls));
            }
        }

        LookAndFeelInfo[] installedLAFs = new LookAndFeelInfo[ilafs.size()];
        for(int i = 0; i < ilafs.size(); i++) {
            installedLAFs[i] = ilafs.elementAt(i);
        }
        getLAFState().installedLAFs = installedLAFs;
    }


    /**
     * If the user has specified a default look and feel, use that.
     * Otherwise use the look and feel that's native to this platform.
     * If this code is called after the application has explicitly
     * set it's look and feel, do nothing.
     *
     * <p>
     *  如果用户指定了默认外观,请使用否则使用本平台本地的外观。如果在应用程序明确设置它的外观之后调用此代码,则不执行任何操作
     * 
     * 
     * @see #maybeInitialize
     */
    private static void initializeDefaultLAF(Properties swingProps)
    {
        if (getLAFState().lookAndFeel != null) {
            return;
        }

        // Try to get default LAF from system property, then from AppContext
        // (6653395), then use cross-platform one by default.
        String lafName = null;
        HashMap lafData =
                (HashMap) AppContext.getAppContext().remove("swing.lafdata");
        if (lafData != null) {
            lafName = (String) lafData.remove("defaultlaf");
        }
        if (lafName == null) {
            lafName = getCrossPlatformLookAndFeelClassName();
        }
        lafName = swingProps.getProperty(defaultLAFKey, lafName);

        try {
            setLookAndFeel(lafName);
        } catch (Exception e) {
            throw new Error("Cannot load " + lafName);
        }

        // Set any properties passed through AppContext (6653395).
        if (lafData != null) {
            for (Object key: lafData.keySet()) {
                UIManager.put(key, lafData.get(key));
            }
        }
    }


    private static void initializeAuxiliaryLAFs(Properties swingProps)
    {
        String auxLookAndFeelNames = swingProps.getProperty(auxiliaryLAFsKey);
        if (auxLookAndFeelNames == null) {
            return;
        }

        Vector<LookAndFeel> auxLookAndFeels = new Vector<LookAndFeel>();

        StringTokenizer p = new StringTokenizer(auxLookAndFeelNames,",");
        String factoryName;

        /* Try to load each LookAndFeel subclass in the list.
        /* <p>
         */

        while (p.hasMoreTokens()) {
            String className = p.nextToken();
            try {
                Class lnfClass = SwingUtilities.loadSystemClass(className);
                LookAndFeel newLAF = (LookAndFeel)lnfClass.newInstance();
                newLAF.initialize();
                auxLookAndFeels.addElement(newLAF);
            }
            catch (Exception e) {
                System.err.println("UIManager: failed loading auxiliary look and feel " + className);
            }
        }

        /* If there were problems and no auxiliary look and feels were
         * loaded, make sure we reset auxLookAndFeels to null.
         * Otherwise, we are going to use the MultiLookAndFeel to get
         * all component UI's, so we need to load it now.
         * <p>
         *  加载,确保我们将auxLookAndFeels重置为null否则,我们将使用MultiLookAndFeel来获取所有组件UI,所以我们需要立即加载它
         * 
         */
        if (auxLookAndFeels.size() == 0) {
            auxLookAndFeels = null;
        }
        else {
            getLAFState().multiLookAndFeel = getMultiLookAndFeel();
            if (getLAFState().multiLookAndFeel == null) {
                auxLookAndFeels = null;
            }
        }

        getLAFState().auxLookAndFeels = auxLookAndFeels;
    }


    private static void initializeSystemDefaults(Properties swingProps) {
        getLAFState().swingProps = swingProps;
    }


    /*
     * This method is called before any code that depends on the
     * <code>AppContext</code> specific LAFState object runs.  When the AppContext
     * corresponds to a set of applets it's possible for this method
     * to be re-entered, which is why we grab a lock before calling
     * initialize().
     * <p>
     * 这个方法在依赖于<code> AppContext </code>特定LAFState对象的任何代码之前被调用当AppContext对应于一组小程序时,可以重新输入这个方法,这就是为什么我们抓住一个锁之
     * 前调用initialize()。
     * 
     */
    private static void maybeInitialize() {
        synchronized (classLock) {
            if (!getLAFState().initialized) {
                getLAFState().initialized = true;
                initialize();
            }
        }
    }

    /*
     * Sets default swing focus traversal policy.
     * <p>
     *  设置默认摇摆焦点遍历策略
     * 
     */
    private static void maybeInitializeFocusPolicy(JComponent comp) {
        // Check for JRootPane which indicates that a swing toplevel
        // is coming, in which case a swing default focus policy
        // should be instatiated. See 7125044.
        if (comp instanceof JRootPane) {
            synchronized (classLock) {
                if (!getLAFState().focusPolicyInitialized) {
                    getLAFState().focusPolicyInitialized = true;

                    if (FocusManager.isFocusManagerEnabled()) {
                        KeyboardFocusManager.getCurrentKeyboardFocusManager().
                            setDefaultFocusTraversalPolicy(
                                new LayoutFocusTraversalPolicy());
                    }
                }
            }
        }
    }

    /*
     * Only called by maybeInitialize().
     * <p>
     *  只有通过maybeInitialize()
     */
    private static void initialize() {
        Properties swingProps = loadSwingProperties();
        initializeSystemDefaults(swingProps);
        initializeDefaultLAF(swingProps);
        initializeAuxiliaryLAFs(swingProps);
        initializeInstalledLAFs(swingProps);

        // Install Swing's PaintEventDispatcher
        if (RepaintManager.HANDLE_TOP_LEVEL_PAINT) {
            sun.awt.PaintEventDispatcher.setPaintEventDispatcher(
                                        new SwingPaintEventDispatcher());
        }
        // Install a hook that will be invoked if no one consumes the
        // KeyEvent.  If the source isn't a JComponent this will process
        // key bindings, if the source is a JComponent it implies that
        // processKeyEvent was already invoked and thus no need to process
        // the bindings again, unless the Component is disabled, in which
        // case KeyEvents will no longer be dispatched to it so that we
        // handle it here.
        KeyboardFocusManager.getCurrentKeyboardFocusManager().
                addKeyEventPostProcessor(new KeyEventPostProcessor() {
                    public boolean postProcessKeyEvent(KeyEvent e) {
                        Component c = e.getComponent();

                        if ((!(c instanceof JComponent) ||
                             (c != null && !c.isEnabled())) &&
                                JComponent.KeyboardState.shouldProcess(e) &&
                                SwingUtilities.processKeyBindings(e)) {
                            e.consume();
                            return true;
                        }
                        return false;
                    }
                });
        AWTAccessor.getComponentAccessor().
            setRequestFocusController(JComponent.focusController);
    }
}

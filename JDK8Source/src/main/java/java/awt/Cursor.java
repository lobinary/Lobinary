/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.io.FileInputStream;

import java.beans.ConstructorProperties;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

import java.security.AccessController;

import sun.util.logging.PlatformLogger;
import sun.awt.AWTAccessor;

/**
 * A class to encapsulate the bitmap representation of the mouse cursor.
 *
 * <p>
 *  一个类来封装鼠标光标的位图表示。
 * 
 * 
 * @see Component#setCursor
 * @author      Amy Fowler
 */
public class Cursor implements java.io.Serializable {

    /**
     * The default cursor type (gets set if no cursor is defined).
     * <p>
     *  默认游标类型(如果未定义游标,则设置)。
     * 
     */
    public static final int     DEFAULT_CURSOR                  = 0;

    /**
     * The crosshair cursor type.
     * <p>
     *  十字光标类型。
     * 
     */
    public static final int     CROSSHAIR_CURSOR                = 1;

    /**
     * The text cursor type.
     * <p>
     *  文本光标类型。
     * 
     */
    public static final int     TEXT_CURSOR                     = 2;

    /**
     * The wait cursor type.
     * <p>
     *  等待游标类型。
     * 
     */
    public static final int     WAIT_CURSOR                     = 3;

    /**
     * The south-west-resize cursor type.
     * <p>
     *  西南调整光标类型。
     * 
     */
    public static final int     SW_RESIZE_CURSOR                = 4;

    /**
     * The south-east-resize cursor type.
     * <p>
     *  东南调整光标类型。
     * 
     */
    public static final int     SE_RESIZE_CURSOR                = 5;

    /**
     * The north-west-resize cursor type.
     * <p>
     *  西北调整光标类型。
     * 
     */
    public static final int     NW_RESIZE_CURSOR                = 6;

    /**
     * The north-east-resize cursor type.
     * <p>
     *  东北调整光标类型。
     * 
     */
    public static final int     NE_RESIZE_CURSOR                = 7;

    /**
     * The north-resize cursor type.
     * <p>
     *  北调整游标类型。
     * 
     */
    public static final int     N_RESIZE_CURSOR                 = 8;

    /**
     * The south-resize cursor type.
     * <p>
     *  南调整光标类型。
     * 
     */
    public static final int     S_RESIZE_CURSOR                 = 9;

    /**
     * The west-resize cursor type.
     * <p>
     *  西调整大小的游标类型。
     * 
     */
    public static final int     W_RESIZE_CURSOR                 = 10;

    /**
     * The east-resize cursor type.
     * <p>
     *  东调整大小的游标类型。
     * 
     */
    public static final int     E_RESIZE_CURSOR                 = 11;

    /**
     * The hand cursor type.
     * <p>
     *  手形光标类型。
     * 
     */
    public static final int     HAND_CURSOR                     = 12;

    /**
     * The move cursor type.
     * <p>
     *  移动光标类型。
     * 
     */
    public static final int     MOVE_CURSOR                     = 13;

    /**
    /* <p>
    /* 
      * @deprecated As of JDK version 1.7, the {@link #getPredefinedCursor(int)}
      * method should be used instead.
      */
    @Deprecated
    protected static Cursor predefined[] = new Cursor[14];

    /**
     * This field is a private replacement for 'predefined' array.
     * <p>
     *  此字段是"预定义"数组的私有替换。
     * 
     */
    private final static Cursor[] predefinedPrivate = new Cursor[14];

    /* Localization names and default values */
    static final String[][] cursorProperties = {
        { "AWT.DefaultCursor", "Default Cursor" },
        { "AWT.CrosshairCursor", "Crosshair Cursor" },
        { "AWT.TextCursor", "Text Cursor" },
        { "AWT.WaitCursor", "Wait Cursor" },
        { "AWT.SWResizeCursor", "Southwest Resize Cursor" },
        { "AWT.SEResizeCursor", "Southeast Resize Cursor" },
        { "AWT.NWResizeCursor", "Northwest Resize Cursor" },
        { "AWT.NEResizeCursor", "Northeast Resize Cursor" },
        { "AWT.NResizeCursor", "North Resize Cursor" },
        { "AWT.SResizeCursor", "South Resize Cursor" },
        { "AWT.WResizeCursor", "West Resize Cursor" },
        { "AWT.EResizeCursor", "East Resize Cursor" },
        { "AWT.HandCursor", "Hand Cursor" },
        { "AWT.MoveCursor", "Move Cursor" },
    };

    /**
     * The chosen cursor type initially set to
     * the <code>DEFAULT_CURSOR</code>.
     *
     * <p>
     *  所选的光标类型最初设置为<code> DEFAULT_CURSOR </code>。
     * 
     * 
     * @serial
     * @see #getType()
     */
    int type = DEFAULT_CURSOR;

    /**
     * The type associated with all custom cursors.
     * <p>
     *  与所有自定义光标相关联的类型。
     * 
     */
    public static final int     CUSTOM_CURSOR                   = -1;

    /*
     * hashtable, filesystem dir prefix, filename, and properties for custom cursors support
     * <p>
     *  hashtable,filesystem dir前缀,文件名和自定义光标支持的属性
     * 
     */

    private static final Hashtable<String,Cursor> systemCustomCursors = new Hashtable<>(1);
    private static final String systemCustomCursorDirPrefix = initCursorDir();

    private static String initCursorDir() {
        String jhome = java.security.AccessController.doPrivileged(
               new sun.security.action.GetPropertyAction("java.home"));
        return jhome +
            File.separator + "lib" + File.separator + "images" +
            File.separator + "cursors" + File.separator;
    }

    private static final String     systemCustomCursorPropertiesFile = systemCustomCursorDirPrefix + "cursors.properties";

    private static       Properties systemCustomCursorProperties = null;

    private static final String CursorDotPrefix  = "Cursor.";
    private static final String DotFileSuffix    = ".File";
    private static final String DotHotspotSuffix = ".HotSpot";
    private static final String DotNameSuffix    = ".Name";

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 8028237497568985504L;

    private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.Cursor");

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }

        AWTAccessor.setCursorAccessor(
            new AWTAccessor.CursorAccessor() {
                public long getPData(Cursor cursor) {
                    return cursor.pData;
                }

                public void setPData(Cursor cursor, long pData) {
                    cursor.pData = pData;
                }

                public int getType(Cursor cursor) {
                    return cursor.type;
                }
            });
    }

    /**
     * Initialize JNI field and method IDs for fields that may be
     * accessed from C.
     * <p>
     *  初始化可从C访问的字段的JNI字段和方法ID。
     * 
     */
    private static native void initIDs();

    /**
     * Hook into native data.
     * <p>
     *  挂钩到本机数据。
     * 
     */
    private transient long pData;

    private transient Object anchor = new Object();

    static class CursorDisposer implements sun.java2d.DisposerRecord {
        volatile long pData;
        public CursorDisposer(long pData) {
            this.pData = pData;
        }
        public void dispose() {
            if (pData != 0) {
                finalizeImpl(pData);
            }
        }
    }
    transient CursorDisposer disposer;
    private void setPData(long pData) {
        this.pData = pData;
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }
        if (disposer == null) {
            disposer = new CursorDisposer(pData);
            // anchor is null after deserialization
            if (anchor == null) {
                anchor = new Object();
            }
            sun.java2d.Disposer.addRecord(anchor, disposer);
        } else {
            disposer.pData = pData;
        }
    }

    /**
     * The user-visible name of the cursor.
     *
     * <p>
     *  用户可见的光标名称。
     * 
     * 
     * @serial
     * @see #getName()
     */
    protected String name;

    /**
     * Returns a cursor object with the specified predefined type.
     *
     * <p>
     *  返回具有指定的预定义类型的游标对象。
     * 
     * 
     * @param type the type of predefined cursor
     * @return the specified predefined cursor
     * @throws IllegalArgumentException if the specified cursor type is
     *         invalid
     */
    static public Cursor getPredefinedCursor(int type) {
        if (type < Cursor.DEFAULT_CURSOR || type > Cursor.MOVE_CURSOR) {
            throw new IllegalArgumentException("illegal cursor type");
        }
        Cursor c = predefinedPrivate[type];
        if (c == null) {
            predefinedPrivate[type] = c = new Cursor(type);
        }
        // fill 'predefined' array for backwards compatibility.
        if (predefined[type] == null) {
            predefined[type] = c;
        }
        return c;
    }

    /**
     * Returns a system-specific custom cursor object matching the
     * specified name.  Cursor names are, for example: "Invalid.16x16"
     *
     * <p>
     *  返回与指定名称匹配的系统特定的自定义游标对象。光标名称是,例如："Invalid.16x16"
     * 
     * 
     * @param name a string describing the desired system-specific custom cursor
     * @return the system specific custom cursor named
     * @exception HeadlessException if
     * <code>GraphicsEnvironment.isHeadless</code> returns true
     */
    static public Cursor getSystemCustomCursor(final String name)
        throws AWTException, HeadlessException {
        GraphicsEnvironment.checkHeadless();
        Cursor cursor = systemCustomCursors.get(name);

        if (cursor == null) {
            synchronized(systemCustomCursors) {
                if (systemCustomCursorProperties == null)
                    loadSystemCustomCursorProperties();
            }

            String prefix = CursorDotPrefix + name;
            String key    = prefix + DotFileSuffix;

            if (!systemCustomCursorProperties.containsKey(key)) {
                if (log.isLoggable(PlatformLogger.Level.FINER)) {
                    log.finer("Cursor.getSystemCustomCursor(" + name + ") returned null");
                }
                return null;
            }

            final String fileName =
                systemCustomCursorProperties.getProperty(key);

            String localized = systemCustomCursorProperties.getProperty(prefix + DotNameSuffix);

            if (localized == null) localized = name;

            String hotspot = systemCustomCursorProperties.getProperty(prefix + DotHotspotSuffix);

            if (hotspot == null)
                throw new AWTException("no hotspot property defined for cursor: " + name);

            StringTokenizer st = new StringTokenizer(hotspot, ",");

            if (st.countTokens() != 2)
                throw new AWTException("failed to parse hotspot property for cursor: " + name);

            int x = 0;
            int y = 0;

            try {
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
            } catch (NumberFormatException nfe) {
                throw new AWTException("failed to parse hotspot property for cursor: " + name);
            }

            try {
                final int fx = x;
                final int fy = y;
                final String flocalized = localized;

                cursor = java.security.AccessController.<Cursor>doPrivileged(
                    new java.security.PrivilegedExceptionAction<Cursor>() {
                    public Cursor run() throws Exception {
                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                        Image image = toolkit.getImage(
                           systemCustomCursorDirPrefix + fileName);
                        return toolkit.createCustomCursor(
                                    image, new Point(fx,fy), flocalized);
                    }
                });
            } catch (Exception e) {
                throw new AWTException(
                    "Exception: " + e.getClass() + " " + e.getMessage() +
                    " occurred while creating cursor " + name);
            }

            if (cursor == null) {
                if (log.isLoggable(PlatformLogger.Level.FINER)) {
                    log.finer("Cursor.getSystemCustomCursor(" + name + ") returned null");
                }
            } else {
                systemCustomCursors.put(name, cursor);
            }
        }

        return cursor;
    }

    /**
     * Return the system default cursor.
     * <p>
     *  返回系统默认光标。
     * 
     */
    static public Cursor getDefaultCursor() {
        return getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    /**
     * Creates a new cursor object with the specified type.
     * <p>
     *  创建具有指定类型的新光标对象。
     * 
     * 
     * @param type the type of cursor
     * @throws IllegalArgumentException if the specified cursor type
     * is invalid
     */
    @ConstructorProperties({"type"})
    public Cursor(int type) {
        if (type < Cursor.DEFAULT_CURSOR || type > Cursor.MOVE_CURSOR) {
            throw new IllegalArgumentException("illegal cursor type");
        }
        this.type = type;

        // Lookup localized name.
        name = Toolkit.getProperty(cursorProperties[type][0],
                                   cursorProperties[type][1]);
    }

    /**
     * Creates a new custom cursor object with the specified name.<p>
     * Note:  this constructor should only be used by AWT implementations
     * as part of their support for custom cursors.  Applications should
     * use Toolkit.createCustomCursor().
     * <p>
     * 创建具有指定名称的新自定义游标对象。<p>注意：此构造函数只应由AWT实现使用,作为对自定义游标的支持的一部分。应用程序应使用Toolkit.createCustomCursor()。
     * 
     * 
     * @param name the user-visible name of the cursor.
     * @see java.awt.Toolkit#createCustomCursor
     */
    protected Cursor(String name) {
        this.type = Cursor.CUSTOM_CURSOR;
        this.name = name;
    }

    /**
     * Returns the type for this cursor.
     * <p>
     *  返回此游标的类型。
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the name of this cursor.
     * <p>
     *  返回此游标的名称。
     * 
     * 
     * @return    a localized description of this cursor.
     * @since     1.2
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of this cursor.
     * <p>
     *  返回此游标的字符串表示形式。
     * 
     * 
     * @return    a string representation of this cursor.
     * @since     1.2
     */
    public String toString() {
        return getClass().getName() + "[" + getName() + "]";
    }

    /*
     * load the cursor.properties file
     * <p>
     *  加载cursor.properties文件
     */
    private static void loadSystemCustomCursorProperties() throws AWTException {
        synchronized(systemCustomCursors) {
            systemCustomCursorProperties = new Properties();

            try {
                AccessController.<Object>doPrivileged(
                      new java.security.PrivilegedExceptionAction<Object>() {
                    public Object run() throws Exception {
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(
                                           systemCustomCursorPropertiesFile);
                            systemCustomCursorProperties.load(fis);
                        } finally {
                            if (fis != null)
                                fis.close();
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                systemCustomCursorProperties = null;
                 throw new AWTException("Exception: " + e.getClass() + " " +
                   e.getMessage() + " occurred while loading: " +
                                        systemCustomCursorPropertiesFile);
            }
        }
    }

    private native static void finalizeImpl(long pData);
}

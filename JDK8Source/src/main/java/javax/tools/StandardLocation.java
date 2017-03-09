/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.tools;

import javax.tools.JavaFileManager.Location;

import java.util.concurrent.*;

/**
 * Standard locations of file objects.
 *
 * <p>
 *  文件对象的标准位置。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public enum StandardLocation implements Location {

    /**
     * Location of new class files.
     * <p>
     *  新类文件的位置。
     * 
     */
    CLASS_OUTPUT,

    /**
     * Location of new source files.
     * <p>
     *  新源文件的位置。
     * 
     */
    SOURCE_OUTPUT,

    /**
     * Location to search for user class files.
     * <p>
     *  搜索用户类文件的位置。
     * 
     */
    CLASS_PATH,

    /**
     * Location to search for existing source files.
     * <p>
     *  搜索现有源文件的位置。
     * 
     */
    SOURCE_PATH,

    /**
     * Location to search for annotation processors.
     * <p>
     *  搜索注释处理器的位置。
     * 
     */
    ANNOTATION_PROCESSOR_PATH,

    /**
     * Location to search for platform classes.  Sometimes called
     * the boot class path.
     * <p>
     *  搜索平台类的位置。有时称为引导类路径。
     * 
     */
    PLATFORM_CLASS_PATH,

    /**
     * Location of new native header files.
     * <p>
     *  新本地头文件的位置。
     * 
     * 
     * @since 1.8
     */
    NATIVE_HEADER_OUTPUT;

    /**
     * Gets a location object with the given name.  The following
     * property must hold: {@code locationFor(x) ==
     * locationFor(y)} if and only if {@code x.equals(y)}.
     * The returned location will be an output location if and only if
     * name ends with {@code "_OUTPUT"}.
     *
     * <p>
     *  获取具有给定名称的位置对象。以下属性必须包含：{@code locationFor(x)== locationFor(y)}当且仅当{@code x.equals(y)}。
     * 当且仅当名称以{@code"_OUTPUT"}结尾时,返回的位置将是输出位置。
     * 
     * @param name a name
     * @return a location
     */
    public static Location locationFor(final String name) {
        if (locations.isEmpty()) {
            // can't use valueOf which throws IllegalArgumentException
            for (Location location : values())
                locations.putIfAbsent(location.getName(), location);
        }
        locations.putIfAbsent(name.toString(/* null-check */), new Location() {
                public String getName() { return name; }
                public boolean isOutputLocation() { return name.endsWith("_OUTPUT"); }
            });
        return locations.get(name);
    }
    //where
        private static final ConcurrentMap<String,Location> locations
            = new ConcurrentHashMap<String,Location>();

    public String getName() { return name(); }

    public boolean isOutputLocation() {
        switch (this) {
            case CLASS_OUTPUT:
            case SOURCE_OUTPUT:
            case NATIVE_HEADER_OUTPUT:
                return true;
            default:
                return false;
        }
    }
}

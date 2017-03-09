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

/**
 * The PropertyEditorManager can be used to locate a property editor for
 * any given type name.  This property editor must support the
 * java.beans.PropertyEditor interface for editing a given object.
 * <P>
 * The PropertyEditorManager uses three techniques for locating an editor
 * for a given type.  First, it provides a registerEditor method to allow
 * an editor to be specifically registered for a given type.  Second it
 * tries to locate a suitable class by adding "Editor" to the full
 * qualified classname of the given type (e.g. "foo.bah.FozEditor").
 * Finally it takes the simple classname (without the package name) adds
 * "Editor" to it and looks in a search-path of packages for a matching
 * class.
 * <P>
 * So for an input class foo.bah.Fred, the PropertyEditorManager would
 * first look in its tables to see if an editor had been registered for
 * foo.bah.Fred and if so use that.  Then it will look for a
 * foo.bah.FredEditor class.  Then it will look for (say)
 * standardEditorsPackage.FredEditor class.
 * <p>
 * Default PropertyEditors will be provided for the Java primitive types
 * "boolean", "byte", "short", "int", "long", "float", and "double"; and
 * for the classes java.lang.String. java.awt.Color, and java.awt.Font.
 * <p>
 *  PropertyEditorManager可用于定位任何给定类型名称的属性编辑器。此属性编辑器必须支持用于编辑给定对象的java.beans.PropertyEditor界面。
 * <P>
 *  PropertyEditorManager使用三种技术来定位给定类型的编辑器。首先,它提供了一个registerEditor方法,允许编辑器为给定类型专门注册。
 * 其次,它通过将"Editor"添加到给定类型的全限定类名(例如"foo.bah.FozEditor")来找到合适的类。最后,它需要简单的类名(没有包名称)添加"编辑器",并查找包的搜索路径匹配类。
 * <P>
 *  所以对于输入类foo.bah.Fred,PropertyEditorManager首先查看它的表以查看是否已经为foo.bah.Fred注册了编辑器,如果是这样,使用它。
 * 然后它会寻找一个foo.bah.FredEditor类。然后它会寻找(说)standardEditorsPackage.FredEditor类。
 * <p>
 *  将为Java原语类型"boolean","byte","short","int","long","float"和"double"提供默认PropertyEditor;和类java.lang.Strin
 * g。
 *  java.awt.Color和java.awt.Font。
 * 
 */

public class PropertyEditorManager {

    /**
     * Registers an editor class to edit values of the given target class.
     * If the editor class is {@code null},
     * then any existing definition will be removed.
     * Thus this method can be used to cancel the registration.
     * The registration is canceled automatically
     * if either the target or editor class is unloaded.
     * <p>
     * If there is a security manager, its {@code checkPropertiesAccess}
     * method is called. This could result in a {@linkplain SecurityException}.
     *
     * <p>
     * 注册编辑器类以编辑给定目标类的值。如果编辑器类是{@code null},那么任何现有的定义都将被删除。因此,该方法可以用于取消注册。如果卸载目标或编辑器类,则会自动取消注册。
     * <p>
     *  如果有安全管理器,则调用其{@code checkPropertiesAccess}方法。这可能会导致{@linkplain SecurityException}。
     * 
     * 
     * @param targetType   the class object of the type to be edited
     * @param editorClass  the class object of the editor class
     * @throws SecurityException  if a security manager exists and
     *                            its {@code checkPropertiesAccess} method
     *                            doesn't allow setting of system properties
     *
     * @see SecurityManager#checkPropertiesAccess
     */
    public static void registerEditor(Class<?> targetType, Class<?> editorClass) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPropertiesAccess();
        }
        ThreadGroupContext.getContext().getPropertyEditorFinder().register(targetType, editorClass);
    }

    /**
     * Locate a value editor for a given target type.
     *
     * <p>
     *  找到给定目标类型的值编辑器。
     * 
     * 
     * @param targetType  The Class object for the type to be edited
     * @return An editor object for the given target class.
     * The result is null if no suitable editor can be found.
     */
    public static PropertyEditor findEditor(Class<?> targetType) {
        return ThreadGroupContext.getContext().getPropertyEditorFinder().find(targetType);
    }

    /**
     * Gets the package names that will be searched for property editors.
     *
     * <p>
     *  获取将搜索属性编辑器的包名称。
     * 
     * 
     * @return  The array of package names that will be searched in
     *          order to find property editors.
     * <p>     The default value for this array is implementation-dependent,
     *         e.g. Sun implementation initially sets to  {"sun.beans.editors"}.
     */
    public static String[] getEditorSearchPath() {
        return ThreadGroupContext.getContext().getPropertyEditorFinder().getPackages();
    }

    /**
     * Change the list of package names that will be used for
     *          finding property editors.
     *
     * <p>First, if there is a security manager, its <code>checkPropertiesAccess</code>
     * method is called. This could result in a SecurityException.
     *
     * <p>
     *  更改将用于查找属性编辑器的包名称列表。
     * 
     * 
     * @param path  Array of package names.
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertiesAccess</code> method doesn't allow setting
     *              of system properties.
     * @see SecurityManager#checkPropertiesAccess
     */
    public static void setEditorSearchPath(String[] path) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPropertiesAccess();
        }
        ThreadGroupContext.getContext().getPropertyEditorFinder().setPackages(path);
    }
}

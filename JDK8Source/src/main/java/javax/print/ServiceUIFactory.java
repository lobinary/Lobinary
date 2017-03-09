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

package javax.print;

/**
 * Services may optionally provide UIs which allow different styles
 * of interaction in different roles.
 * One role may be end-user browsing and setting of print options.
 * Another role may be administering the print service.
 * <p>
 * Although the Print Service API does not presently provide standardised
 * support for administering a print service, monitoring of the print
 * service is possible and a UI may provide for private update mechanisms.
 * <p>
 * The basic design intent is to allow applications to lazily locate and
 * initialize services only when needed without any API dependencies
 * except in an environment in which they are used.
 * <p>
 * Swing UIs are preferred as they provide a more consistent {@literal L&F}
 * and can support accessibility APIs.
 * <p>
 * Example usage:
 * <pre>
 *  ServiceUIFactory factory = printService.getServiceUIFactory();
 *  if (factory != null) {
 *      JComponent swingui = (JComponent)factory.getUI(
 *                                         ServiceUIFactory.MAIN_UIROLE,
 *                                         ServiceUIFactory.JCOMPONENT_UI);
 *      if (swingui != null) {
 *          tabbedpane.add("Custom UI", swingui);
 *      }
 *  }
 * </pre>
 * <p>
 *  服务可以可选地提供允许在不同角色中的不同风格的交互的UI。一个角色可以是终端用户浏览和打印选项的设置。另一个角色可以是管理打印服务。
 * <p>
 *  尽管打印服务API当前不提供用于管理打印服务的标准化支持,但是打印服务的监视是可能的,并且UI可以提供私有更新机制。
 * <p>
 *  基本设计意图是允许应用程序仅在需要时才懒惰地定位和初始化服务,而不使用任何API依赖性,除非在使用它们的环境中。
 * <p>
 *  优先选择Swing UI,因为它们提供了更一致的{@literal L&F},并且可以支持可访问性API。
 * <p>
 *  用法示例：
 * <pre>
 *  ServiceUIFactory factory = printService.getServiceUIFactory(); if(factory！= null){JComponent swingui =(JComponent)factory.getUI(ServiceUIFactory.MAIN_UIROLE,ServiceUIFactory.JCOMPONENT_UI); if(swingui！= null){tabbedpane.add("Custom UI",swingui); }
 * }。
 * </pre>
 */

public abstract class ServiceUIFactory {

    /**
     * Denotes a UI implemented as a Swing component.
     * The value of the String is the fully qualified classname :
     * "javax.swing.JComponent".
     * <p>
     *  表示实现为Swing组件的UI。 String的值是完全限定的类名："javax.swing.JComponent"。
     * 
     */
    public static final String JCOMPONENT_UI = "javax.swing.JComponent";

    /**
     * Denotes a UI implemented as an AWT panel.
     * The value of the String is the fully qualified classname :
     * "java.awt.Panel"
     * <p>
     *  表示实现为AWT面板的UI。 String的值是完全限定的类名："java.awt.Panel"
     * 
     */
    public static final String PANEL_UI = "java.awt.Panel";

    /**
     * Denotes a UI implemented as an AWT dialog.
     * The value of the String is the fully qualified classname :
     * "java.awt.Dialog"
     * <p>
     * 表示实现为AWT对话框的UI。 String的值是完全限定的类名："java.awt.Dialog"
     * 
     */
    public static final String DIALOG_UI = "java.awt.Dialog";

    /**
     * Denotes a UI implemented as a Swing dialog.
     * The value of the String is the fully qualified classname :
     * "javax.swing.JDialog"
     * <p>
     *  表示实现为Swing对话框的UI。 String的值是完全限定的类名："javax.swing.JDialog"
     * 
     */
    public static final String JDIALOG_UI = "javax.swing.JDialog";

    /**
     * Denotes a UI which performs an informative "About" role.
     * <p>
     *  表示执行信息性"关于"角色的UI。
     * 
     */
    public static final int ABOUT_UIROLE = 1;

    /**
     * Denotes a UI which performs an administrative role.
     * <p>
     *  表示执行管理角色的UI。
     * 
     */
    public static final int ADMIN_UIROLE = 2;

    /**
     * Denotes a UI which performs the normal end user role.
     * <p>
     *  表示执行正常最终用户角色的UI。
     * 
     */
    public static final int MAIN_UIROLE = 3;

    /**
     * Not a valid role but role id's greater than this may be used
     * for private roles supported by a service. Knowledge of the
     * function performed by this role is required to make proper use
     * of it.
     * <p>
     *  不是有效的角色,但角色ID大于此值可用于服务支持的私有角色。需要知道这个角色执行的功能,才能正确使用它。
     * 
     */
    public static final int RESERVED_UIROLE = 99;
    /**
     * Get a UI object which may be cast to the requested UI type
     * by the application and used in its user interface.
     * <P>
     * <p>
     *  获取可由应用程序转换为所请求的UI类型并在其用户界面中使用的UI对象。
     * <P>
     * 
     * @param role requested. Must be one of the standard roles or
     * a private role supported by this factory.
     * @param ui type in which the role is requested.
     * @return the UI role or null if the requested UI role is not available
     * from this factory
     * @throws IllegalArgumentException if the role or ui is neither
     * one of the standard ones, nor a private one
     * supported by the factory.
     */
    public abstract Object getUI(int role, String ui) ;

    /**
     * Given a UI role obtained from this factory obtain the UI
     * types available from this factory which implement this role.
     * The returned Strings should refer to the static variables defined
     * in this class so that applications can use equality of reference
     * ("==").
     * <p>
     *  给定从此工厂获得的UI角色获取实现此角色的此工厂可用的UI类型。返回的字符串应该引用在这个类中定义的静态变量,以便应用程序可以使用等式引用("==")。
     * 
     * @param role to be looked up.
     * @return the UI types supported by this class for the specified role,
     * null if no UIs are available for the role.
     * @throws IllegalArgumentException is the role is a non-standard
     * role not supported by this factory.
     */
    public abstract String[] getUIClassNamesForRole(int role) ;



}

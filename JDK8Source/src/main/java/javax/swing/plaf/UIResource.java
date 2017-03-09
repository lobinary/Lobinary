/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf;


/**
 * This interface is used to mark objects created by ComponentUI delegates.
 * The <code>ComponentUI.installUI()</code> and
 * <code>ComponentUI.uninstallUI()</code> methods can use this interface
 * to decide if a properties value has been overridden.  For example, the
 * JList cellRenderer property is initialized by BasicListUI.installUI(),
 * only if it's initial value is null:
 * <pre>
 * if (list.getCellRenderer() == null) {
 *     list.setCellRenderer((ListCellRenderer)(UIManager.get("List.cellRenderer")));
 * }
 * </pre>
 * At uninstallUI() time we reset the property to null if its value
 * is an instance of UIResource:
 * <pre>
 * if (list.getCellRenderer() instanceof UIResource) {
 *     list.setCellRenderer(null);
 * }
 *</pre>
 * This pattern applies to all properties except the java.awt.Component
 * properties font, foreground, and background.  If one of these
 * properties isn't initialized, or is explicitly set to null,
 * its container provides the value.  For this reason the
 * <code>"== null"</code> is unreliable when installUI() is called
 * to dynamically change a components look and feel.  So at installUI()
 * time we check to see if the current value is a UIResource:
 *<pre>
 * if (!(list.getFont() instanceof UIResource)) {
 *     list.setFont(UIManager.getFont("List.font"));
 * }
 * </pre>
 *
 * <p>
 *  此接口用于标记由ComponentUI代理创建的对象。
 *  <code> ComponentUI.installUI()</code>和<code> ComponentUI.uninstallUI()</code>方法可以使用此接口来决定属性值是否被覆盖。
 * 例如,JList cellRenderer属性由BasicListUI.installUI()初始化,只有当它的初始值为null时：。
 * <pre>
 *  if(list.getCellRenderer()== null){list.setCellRenderer((ListCellRenderer)(UIManager.get("List.cellRenderer"))); }
 * }。
 * </pre>
 *  在uninstallUI()时间,如果它的值是UIResource的一个实例,我们将该属性重置为null：
 * <pre>
 * 
 * @see ComponentUI
 * @author Hans Muller
 *
 */

public interface UIResource {
}

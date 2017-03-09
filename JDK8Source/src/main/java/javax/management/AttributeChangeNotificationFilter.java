/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;


import java.util.Vector;


/**
 * This class implements of the {@link javax.management.NotificationFilter NotificationFilter}
 * interface for the {@link javax.management.AttributeChangeNotification attribute change notification}.
 * The filtering is performed on the name of the observed attribute.
 * <P>
 * It manages a list of enabled attribute names.
 * A method allows users to enable/disable as many attribute names as required.
 *
 * <p>
 *  此类实现{@link javax.management.AttributeChangeNotification属性更改通知}的{@link javax.management.NotificationFilter NotificationFilter}
 * 接口。
 * 对观察属性的名称执行过滤。
 * <P>
 *  它管理已启用的属性名称的列表。方法允许用户根据需要启用/禁用任意数量的属性名称。
 * 
 * 
 * @since 1.5
 */
public class AttributeChangeNotificationFilter implements NotificationFilter {

    /* Serial version */
    private static final long serialVersionUID = -6347317584796410029L;

    /**
    /* <p>
    /* 
     * @serial {@link Vector} that contains the enabled attribute names.
     *         The default value is an empty vector.
     */
    private Vector<String> enabledAttributes = new Vector<String>();


    /**
     * Invoked before sending the specified notification to the listener.
     * <BR>This filter compares the attribute name of the specified attribute change notification
     * with each enabled attribute name.
     * If the attribute name equals one of the enabled attribute names,
     * the notification must be sent to the listener and this method returns <CODE>true</CODE>.
     *
     * <p>
     *  在将指定的通知发送到侦听器之前调用。 <BR>此过滤器将指定的属性更改通知的属性名称与每个启用的属性名称进行比较。
     * 如果属性名称等于启用的属性名称之一,则通知必须发送到侦听器,并且此方法返回<CODE> true </CODE>。
     * 
     * 
     * @param notification The attribute change notification to be sent.
     * @return <CODE>true</CODE> if the notification has to be sent to the listener, <CODE>false</CODE> otherwise.
     */
    public synchronized boolean isNotificationEnabled(Notification notification) {

        String type = notification.getType();

        if ((type == null) ||
            (type.equals(AttributeChangeNotification.ATTRIBUTE_CHANGE) == false) ||
            (!(notification instanceof AttributeChangeNotification))) {
            return false;
        }

        String attributeName =
          ((AttributeChangeNotification)notification).getAttributeName();
        return enabledAttributes.contains(attributeName);
    }

    /**
     * Enables all the attribute change notifications the attribute name of which equals
     * the specified name to be sent to the listener.
     * <BR>If the specified name is already in the list of enabled attribute names,
     * this method has no effect.
     *
     * <p>
     *  启用其属性名称等于指定名称的所有属性更改通知发送到侦听器。 <BR>如果指定的名称已在已启用的属性名称列表中,则此方法无效。
     * 
     * 
     * @param name The attribute name.
     * @exception java.lang.IllegalArgumentException The attribute name parameter is null.
     */
    public synchronized void enableAttribute(String name) throws java.lang.IllegalArgumentException {

        if (name == null) {
            throw new java.lang.IllegalArgumentException("The name cannot be null.");
        }
        if (!enabledAttributes.contains(name)) {
            enabledAttributes.addElement(name);
        }
    }

    /**
     * Disables all the attribute change notifications the attribute name of which equals
     * the specified attribute name to be sent to the listener.
     * <BR>If the specified name is not in the list of enabled attribute names,
     * this method has no effect.
     *
     * <p>
     *  禁用其属性名称等于要发送给侦听器的指定属性名称的所有属性更改通知。 <BR>如果指定的名称不在已启用的属性名称列表中,则此方法无效。
     * 
     * 
     * @param name The attribute name.
     */
    public synchronized void disableAttribute(String name) {
        enabledAttributes.removeElement(name);
    }

    /**
     * Disables all the attribute names.
     * <p>
     *  禁用所有属性名称。
     * 
     */
    public synchronized void disableAllAttributes() {
        enabledAttributes.removeAllElements();
    }

    /**
     * Gets all the enabled attribute names for this filter.
     *
     * <p>
     *  获取此过滤器的所有已启用的属性名称。
     * 
     * @return The list containing all the enabled attribute names.
     */
    public synchronized Vector<String> getEnabledAttributes() {
        return enabledAttributes;
    }

}

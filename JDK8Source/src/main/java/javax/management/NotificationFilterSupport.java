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


import java.util.List;
import java.util.Vector;

/**
 * Provides an implementation of the {@link javax.management.NotificationFilter} interface.
 * The filtering is performed on the notification type attribute.
 * <P>
 * Manages a list of enabled notification types.
 * A method allows users to enable/disable as many notification types as required.
 * <P>
 * Then, before sending a notification to a listener registered with a filter,
 * the notification broadcaster compares this notification type with all notification types
 * enabled by the filter. The notification will be sent to the listener
 * only if its filter enables this notification type.
 * <P>
 * Example:
 * <BLOCKQUOTE>
 * <PRE>
 * NotificationFilterSupport myFilter = new NotificationFilterSupport();
 * myFilter.enableType("my_example.my_type");
 * myBroadcaster.addListener(myListener, myFilter, null);
 * </PRE>
 * </BLOCKQUOTE>
 * The listener <CODE>myListener</CODE> will only receive notifications the type of which equals/starts with "my_example.my_type".
 *
 * <p>
 *  提供{@link javax.management.NotificationFilter}接口的实现。对通知类型属性执行过滤。
 * <P>
 *  管理已启用的通知类型的列表。方法允许用户启用/禁用所需的通知类型。
 * <P>
 *  然后,在向通过过滤器注册的侦听器发送通知之前,通知广播公司将此通知类型与过滤器启用的所有通知类型进行比较。仅当其过滤器启用此通知类型时,通知才会发送到侦听器。
 * <P>
 *  例：
 * <BLOCKQUOTE>
 * <PRE>
 *  NotificationFilterSupport myFilter = new NotificationFilterSupport(); myFilter.enableType("my_exampl
 * e.my_type"); myBroadcaster.addListener(myListener,myFilter,null);。
 * </PRE>
 * </BLOCKQUOTE>
 *  侦听器<CODE> myListener </CODE>只会接收其类型等于/以"my_example.my_type"开头的通知。
 * 
 * 
 * @see javax.management.NotificationBroadcaster#addNotificationListener
 *
 * @since 1.5
 */
public class NotificationFilterSupport implements NotificationFilter {

    /* Serial version */
    private static final long serialVersionUID = 6579080007561786969L;

    /**
    /* <p>
    /* 
     * @serial {@link Vector} that contains the enabled notification types.
     *         The default value is an empty vector.
     */
    private List<String> enabledTypes = new Vector<String>();


    /**
     * Invoked before sending the specified notification to the listener.
     * <BR>This filter compares the type of the specified notification with each enabled type.
     * If the notification type matches one of the enabled types,
     * the notification should be sent to the listener and this method returns <CODE>true</CODE>.
     *
     * <p>
     *  在将指定的通知发送到侦听器之前调用。 <BR>此过滤器会将指定通知的类型与每个启用的类型进行比较。
     * 如果通知类型与启用的类型之一匹配,则通知应发送到侦听器,此方法返回<CODE> true </CODE>。
     * 
     * 
     * @param notification The notification to be sent.
     * @return <CODE>true</CODE> if the notification should be sent to the listener, <CODE>false</CODE> otherwise.
     */
    public synchronized boolean isNotificationEnabled(Notification notification) {

        String type = notification.getType();

        if (type == null) {
            return false;
        }
        try {
            for (String prefix : enabledTypes) {
                if (type.startsWith(prefix)) {
                    return true;
                }
            }
        } catch (java.lang.NullPointerException e) {
            // Should never occurs...
            return false;
        }
        return false;
    }

    /**
     * Enables all the notifications the type of which starts with the specified prefix
     * to be sent to the listener.
     * <BR>If the specified prefix is already in the list of enabled notification types,
     * this method has no effect.
     * <P>
     * Example:
     * <BLOCKQUOTE>
     * <PRE>
     * // Enables all notifications the type of which starts with "my_example" to be sent.
     * myFilter.enableType("my_example");
     * // Enables all notifications the type of which is "my_example.my_type" to be sent.
     * myFilter.enableType("my_example.my_type");
     * </PRE>
     * </BLOCKQUOTE>
     *
     * Note that:
     * <BLOCKQUOTE><CODE>
     * myFilter.enableType("my_example.*");
     * </CODE></BLOCKQUOTE>
     * will no match any notification type.
     *
     * <p>
     * 将所有以指定前缀开头的通知发送到侦听器。 <BR>如果指定的前缀已在已启用的通知类型列表中,则此方法无效。
     * <P>
     *  例：
     * <BLOCKQUOTE>
     * <PRE>
     *  //允许发送以"my_example"开头的所有通知。 myFilter.enableType("my_example"); //允许发送类型为"my_example.my_type"的所有通知。
     *  myFilter.enableType("my_example.my_type");。
     * </PRE>
     * </BLOCKQUOTE>
     * 
     *  请注意：<BLOCKQUOTE> <CODE> myFilter.enableType("my_example。*"); </CODE> </BLOCKQUOTE>将不匹配任何通知类型。
     * 
     * @param prefix The prefix.
     * @exception java.lang.IllegalArgumentException The prefix parameter is null.
     */
    public synchronized void enableType(String prefix)
            throws IllegalArgumentException {

        if (prefix == null) {
            throw new IllegalArgumentException("The prefix cannot be null.");
        }
        if (!enabledTypes.contains(prefix)) {
            enabledTypes.add(prefix);
        }
    }

    /**
     * Removes the given prefix from the prefix list.
     * <BR>If the specified prefix is not in the list of enabled notification types,
     * this method has no effect.
     *
     * <p>
     * 
     * 
     * @param prefix The prefix.
     */
    public synchronized void disableType(String prefix) {
        enabledTypes.remove(prefix);
    }

    /**
     * Disables all notification types.
     * <p>
     *  从前缀列表中删除给定的前缀。 <BR>如果指定的前缀不在已启用的通知类型列表中,则此方法无效。
     * 
     */
    public synchronized void disableAllTypes() {
        enabledTypes.clear();
    }


    /**
     * Gets all the enabled notification types for this filter.
     *
     * <p>
     *  禁用所有通知类型。
     * 
     * 
     * @return The list containing all the enabled notification types.
     */
    public synchronized Vector<String> getEnabledTypes() {
        return (Vector<String>)enabledTypes;
    }

}

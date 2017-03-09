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

package java.util.prefs;
import java.util.*;

/**
 * A factory object that generates Preferences objects.  Providers of
 * new {@link Preferences} implementations should provide corresponding
 * <tt>PreferencesFactory</tt> implementations so that the new
 * <tt>Preferences</tt> implementation can be installed in place of the
 * platform-specific default implementation.
 *
 * <p><strong>This class is for <tt>Preferences</tt> implementers only.
 * Normal users of the <tt>Preferences</tt> facility should have no need to
 * consult this documentation.</strong>
 *
 * <p>
 *  生成首选项对象的工厂对象。
 * 新的{@link Preferences}实施的提供者应提供相应的<tt> PreferencesFactory </tt>实现,以便可以安装新的<tt> Preferences </tt>实现来替代平
 * 台特定的默认实现。
 *  生成首选项对象的工厂对象。
 * 
 *  <p> <strong>此课程仅适用于<tt>偏好设置</tt>。 <tt>偏好设置</tt>工具的正常用户不需要查阅此文档。</strong>
 * 
 * 
 * @author  Josh Bloch
 * @see     Preferences
 * @since   1.4
 */
public interface PreferencesFactory {
    /**
     * Returns the system root preference node.  (Multiple calls on this
     * method will return the same object reference.)
     * <p>
     * 
     * @return the system root preference node
     */
    Preferences systemRoot();

    /**
     * Returns the user root preference node corresponding to the calling
     * user.  In a server, the returned value will typically depend on
     * some implicit client-context.
     * <p>
     *  返回系统根首选节点。 (对此方法的多次调用将返回相同的对象引用。)
     * 
     * 
     * @return the user root preference node corresponding to the calling
     * user
     */
    Preferences userRoot();
}

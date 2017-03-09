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

/**
 * A listener for receiving preference change events.
 *
 * <p>
 *  用于接收首选项更改事件的侦听器。
 * 
 * 
 * @author  Josh Bloch
 * @see Preferences
 * @see PreferenceChangeEvent
 * @see NodeChangeListener
 * @since   1.4
 */
@FunctionalInterface
public interface PreferenceChangeListener extends java.util.EventListener {
    /**
     * This method gets called when a preference is added, removed or when
     * its value is changed.
     * <p>
     * <p>
     *  当添加,删除首选项或更改其值时,将调用此方法。
     * 
     * @param evt A PreferenceChangeEvent object describing the event source
     *          and the preference that has changed.
     */
    void preferenceChange(PreferenceChangeEvent evt);
}

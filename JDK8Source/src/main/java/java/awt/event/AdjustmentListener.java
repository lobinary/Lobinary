/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.event;

import java.util.EventListener;

/**
 * The listener interface for receiving adjustment events.
 *
 * <p>
 *  用于接收调整事件的侦听器接口。
 * 
 * 
 * @author Amy Fowler
 * @since 1.1
 */
public interface AdjustmentListener extends EventListener {

    /**
     * Invoked when the value of the adjustable has changed.
     * <p>
     *  在可调整值已更改时调用。
     */
    public void adjustmentValueChanged(AdjustmentEvent e);

}

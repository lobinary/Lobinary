/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.event;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A listener implementing all the methods in both the {@code MouseListener} and
 * {@code MouseMotionListener} interfaces.
 *
 * <p>
 *  一个实现{@code MouseListener}和{@code MouseMotionListener}接口中所有方法的侦听器。
 * 
 * @see MouseInputAdapter
 * @author Philip Milne
 */

public interface MouseInputListener extends MouseListener, MouseMotionListener {
}

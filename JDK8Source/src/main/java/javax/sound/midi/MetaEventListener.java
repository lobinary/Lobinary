/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2002, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.midi;

import java.util.EventListener;


/**
 * The <code>MetaEventListener</code> interface should be implemented
 * by classes whose instances need to be notified when a <code>{@link Sequencer}</code>
 * has processed a <code>{@link MetaMessage}</code>.
 * To register a <code>MetaEventListener</code> object to receive such
 * notifications, pass it as the argument to the
 * <code>{@link Sequencer#addMetaEventListener(MetaEventListener) addMetaEventListener}</code>
 * method of <code>Sequencer</code>.
 *
 * <p>
 *  <code> MetaEventListener </code>接口应该由实现需要在<code> {@ link Sequencer} </code>处理<code> {@ link MetaMessage}
 *  </code> 。
 * 要注册<code> MetaEventListener </code>对象以接收此类通知,请将其作为<code> Sequencer </code>方法的<code> {@ link Sequencer#addMetaEventListener(MetaEventListener)addMetaEventListener}
 *  </代码>。
 * 
 * 
 * @author Kara Kytle
 */
public interface MetaEventListener extends EventListener {

    /**
     * Invoked when a <code>{@link Sequencer}</code> has encountered and processed
     * a <code>MetaMessage</code> in the <code>{@link Sequence}</code> it is processing.
     * <p>
     * 
     * @param meta the meta-message that the sequencer encountered
     */
    public void meta(MetaMessage meta);
}

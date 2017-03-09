/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 1997, Oracle and/or its affiliates. All rights reserved.
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

package java.applet;

/**
 * The <code>AudioClip</code> interface is a simple abstraction for
 * playing a sound clip. Multiple <code>AudioClip</code> items can be
 * playing at the same time, and the resulting sound is mixed
 * together to produce a composite.
 *
 * <p>
 *  <code> AudioClip </code>接口是播放声音片段的简单抽象。多个<code> AudioClip </code>项目可以同时播放,并且所得到的声音被混合在一起以产生复合。
 * 
 * 
 * @author      Arthur van Hoff
 * @since       JDK1.0
 */
public interface AudioClip {
    /**
     * Starts playing this audio clip. Each time this method is called,
     * the clip is restarted from the beginning.
     * <p>
     *  开始播放此音频剪辑。每次调用此方法时,剪辑都从头重新启动。
     * 
     */
    void play();

    /**
     * Starts playing this audio clip in a loop.
     * <p>
     *  开始循环播放此音频剪辑。
     * 
     */
    void loop();

    /**
     * Stops playing this audio clip.
     * <p>
     *  停止播放此音频剪辑。
     */
    void stop();
}

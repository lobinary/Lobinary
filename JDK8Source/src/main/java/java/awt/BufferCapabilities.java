/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

/**
 * Capabilities and properties of buffers.
 *
 * <p>
 *  缓冲区的功能和属性。
 * 
 * 
 * @see java.awt.image.BufferStrategy#getCapabilities()
 * @see GraphicsConfiguration#getBufferCapabilities
 * @author Michael Martak
 * @since 1.4
 */
public class BufferCapabilities implements Cloneable {

    private ImageCapabilities frontCaps;
    private ImageCapabilities backCaps;
    private FlipContents flipContents;

    /**
     * Creates a new object for specifying buffering capabilities
     * <p>
     *  创建用于指定缓冲能力的新对象
     * 
     * 
     * @param frontCaps the capabilities of the front buffer; cannot be
     * <code>null</code>
     * @param backCaps the capabilities of the back and intermediate buffers;
     * cannot be <code>null</code>
     * @param flipContents the contents of the back buffer after page-flipping,
     * <code>null</code> if page flipping is not used (implies blitting)
     * @exception IllegalArgumentException if frontCaps or backCaps are
     * <code>null</code>
     */
    public BufferCapabilities(ImageCapabilities frontCaps,
        ImageCapabilities backCaps, FlipContents flipContents) {
        if (frontCaps == null || backCaps == null) {
            throw new IllegalArgumentException(
                "Image capabilities specified cannot be null");
        }
        this.frontCaps = frontCaps;
        this.backCaps = backCaps;
        this.flipContents = flipContents;
    }

    /**
    /* <p>
    /* 
     * @return the image capabilities of the front (displayed) buffer
     */
    public ImageCapabilities getFrontBufferCapabilities() {
        return frontCaps;
    }

    /**
    /* <p>
    /* 
     * @return the image capabilities of all back buffers (intermediate buffers
     * are considered back buffers)
     */
    public ImageCapabilities getBackBufferCapabilities() {
        return backCaps;
    }

    /**
    /* <p>
    /* 
     * @return whether or not the buffer strategy uses page flipping; a set of
     * buffers that uses page flipping
     * can swap the contents internally between the front buffer and one or
     * more back buffers by switching the video pointer (or by copying memory
     * internally).  A non-flipping set of
     * buffers uses blitting to copy the contents from one buffer to
     * another; when this is the case, <code>getFlipContents</code> returns
     * <code>null</code>
     */
    public boolean isPageFlipping() {
        return (getFlipContents() != null);
    }

    /**
    /* <p>
    /* 
     * @return the resulting contents of the back buffer after page-flipping.
     * This value is <code>null</code> when the <code>isPageFlipping</code>
     * returns <code>false</code>, implying blitting.  It can be one of
     * <code>FlipContents.UNDEFINED</code>
     * (the assumed default), <code>FlipContents.BACKGROUND</code>,
     * <code>FlipContents.PRIOR</code>, or
     * <code>FlipContents.COPIED</code>.
     * @see #isPageFlipping
     * @see FlipContents#UNDEFINED
     * @see FlipContents#BACKGROUND
     * @see FlipContents#PRIOR
     * @see FlipContents#COPIED
     */
    public FlipContents getFlipContents() {
        return flipContents;
    }

    /**
    /* <p>
    /* 
     * @return whether page flipping is only available in full-screen mode.  If this
     * is <code>true</code>, full-screen exclusive mode is required for
     * page-flipping.
     * @see #isPageFlipping
     * @see GraphicsDevice#setFullScreenWindow
     */
    public boolean isFullScreenRequired() {
        return false;
    }

    /**
    /* <p>
    /* 
     * @return whether or not
     * page flipping can be performed using more than two buffers (one or more
     * intermediate buffers as well as the front and back buffer).
     * @see #isPageFlipping
     */
    public boolean isMultiBufferAvailable() {
        return false;
    }

    /**
    /* <p>
    /* 
     * @return a copy of this BufferCapabilities object.
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // Since we implement Cloneable, this should never happen
            throw new InternalError(e);
        }
    }

    // Inner class FlipContents
    /**
     * A type-safe enumeration of the possible back buffer contents after
     * page-flipping
     * <p>
     *  页面翻转后可能的后缓冲器内容的类型安全枚举
     * 
     * 
     * @since 1.4
     */
    public static final class FlipContents extends AttributeValue {

        private static int I_UNDEFINED = 0;
        private static int I_BACKGROUND = 1;
        private static int I_PRIOR = 2;
        private static int I_COPIED = 3;

        private static final String NAMES[] =
            { "undefined", "background", "prior", "copied" };

        /**
         * When flip contents are <code>UNDEFINED</code>, the
         * contents of the back buffer are undefined after flipping.
         * <p>
         *  当翻转内容为<code> UNDEFINED </code>时,后退缓冲区的内容在翻转后未定义。
         * 
         * 
         * @see #isPageFlipping
         * @see #getFlipContents
         * @see #BACKGROUND
         * @see #PRIOR
         * @see #COPIED
         */
        public static final FlipContents UNDEFINED =
            new FlipContents(I_UNDEFINED);

        /**
         * When flip contents are <code>BACKGROUND</code>, the
         * contents of the back buffer are cleared with the background color after
         * flipping.
         * <p>
         *  当翻转内容是<code> BACKGROUND </code>时,后台缓冲器的内容在翻转后以背景颜色清除。
         * 
         * 
         * @see #isPageFlipping
         * @see #getFlipContents
         * @see #UNDEFINED
         * @see #PRIOR
         * @see #COPIED
         */
        public static final FlipContents BACKGROUND =
            new FlipContents(I_BACKGROUND);

        /**
         * When flip contents are <code>PRIOR</code>, the
         * contents of the back buffer are the prior contents of the front buffer
         * (a true page flip).
         * <p>
         *  当翻转内容为<code> PRIOR </code>时,后缓冲器的内容是前缓冲器的先前内容(真正的页翻转)。
         * 
         * 
         * @see #isPageFlipping
         * @see #getFlipContents
         * @see #UNDEFINED
         * @see #BACKGROUND
         * @see #COPIED
         */
        public static final FlipContents PRIOR =
            new FlipContents(I_PRIOR);

        /**
         * When flip contents are <code>COPIED</code>, the
         * contents of the back buffer are copied to the front buffer when
         * flipping.
         * <p>
         *  当翻转内容是<code> COPIED </code>时,后台缓冲区的内容在翻转时被复制到前台缓冲区。
         * 
         * @see #isPageFlipping
         * @see #getFlipContents
         * @see #UNDEFINED
         * @see #BACKGROUND
         * @see #PRIOR
         */
        public static final FlipContents COPIED =
            new FlipContents(I_COPIED);

        private FlipContents(int type) {
            super(type, NAMES);
        }

    } // Inner class FlipContents

}

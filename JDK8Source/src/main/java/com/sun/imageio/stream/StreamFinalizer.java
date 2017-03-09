/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.imageio.stream;

import java.io.IOException;
import javax.imageio.stream.ImageInputStream;

/**
 * Small class to assist in properly closing an ImageInputStream instance
 * prior to garbage collection.  The ImageInputStreamImpl class defines a
 * finalize() method, but in a number of its public subclasses
 * (e.g. FileImageInputStream) we override the finalize() method to be
 * empty for performance reasons, and instead rely on the Disposer mechanism
 * for closing/disposing resources.  This is fine when one of these classes
 * is instantiated directly (e.g. new FileImageInputStream()) but in the
 * unlikely case where a user defines their own subclass of one of those
 * streams, we need some way to get back to the behavior of
 * ImageInputStreamImpl, which will call close() as part of finalization.
 *
 * Typically an Image{Input,Output}Stream will construct an instance of
 * StreamFinalizer in its constructor if it detects that it has been
 * subclassed by the user.  The ImageInputStream instance will hold a
 * reference to the StreamFinalizer, and the StreamFinalizer will hold a
 * reference back to the ImageInputStream from which it was created.  When
 * both are no longer reachable, the StreamFinalizer.finalize() method will
 * be called, which will take care of closing down the ImageInputStream.
 *
 * Clearly this is a bit of a hack, but it will likely only be used in the
 * rarest of circumstances: when a user has subclassed one of the public
 * stream classes.  (It should be no worse than the old days when the public
 * stream classes had non-empty finalize() methods.)
 * <p>
 *  小类,以帮助在垃圾回收之前正确关闭ImageInputStream实例。
 *  ImageInputStreamImpl类定义了一个finalize()方法,但是在一些公共子类(例如FileImageInputStream)中,为了性能原因,我们覆盖了finalize()方法为空
 * ,而是依赖于Disposer机制来关闭/处理资源。
 *  小类,以帮助在垃圾回收之前正确关闭ImageInputStream实例。
 * 这是很好的,当这些类之一直接实例化(例如新的FileImageInputStream()),但在不太可能的情况下,用户定义其中一个流的自己的子类,我们需要一些方法来回到ImageInputStreamI
 * mpl的行为,将调用close()作为完成的一部分。
 *  小类,以帮助在垃圾回收之前正确关闭ImageInputStream实例。
 */
public class StreamFinalizer {
    private ImageInputStream stream;

    public StreamFinalizer(ImageInputStream stream) {
        this.stream = stream;
    }

    protected void finalize() throws Throwable {
        try {
            stream.close();
        } catch (IOException e) {
        } finally {
            stream = null;
            super.finalize();
        }
    }
}

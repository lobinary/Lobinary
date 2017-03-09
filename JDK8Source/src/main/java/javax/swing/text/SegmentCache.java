/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2008, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.util.ArrayList;
import java.util.List;

/**
 * SegmentCache caches <code>Segment</code>s to avoid continually creating
 * and destroying of <code>Segment</code>s. A common use of this class would
 * be:
 * <pre>
 *   Segment segment = segmentCache.getSegment();
 *   // do something with segment
 *   ...
 *   segmentCache.releaseSegment(segment);
 * </pre>
 *
 * <p>
 *  SegmentCache缓存<code> Segment </code>以避免不断创建和销毁<code> Segment </code>。这个类的常见用法是：
 * <pre>
 *  细分细分= segmentCache.getSegment(); // do something with segment ... segmentCache.releaseSegment(segmen
 * t);。
 * </pre>
 * 
 */
class SegmentCache {
    /**
     * A global cache.
     * <p>
     *  全局缓存。
     * 
     */
    private static SegmentCache sharedCache = new SegmentCache();

    /**
     * A list of the currently unused Segments.
     * <p>
     *  当前未使用的细分的列表。
     * 
     */
    private List<Segment> segments;


    /**
     * Returns the shared SegmentCache.
     * <p>
     *  返回共享的SegmentCache。
     * 
     */
    public static SegmentCache getSharedInstance() {
        return sharedCache;
    }

    /**
     * A convenience method to get a Segment from the shared
     * <code>SegmentCache</code>.
     * <p>
     *  从共享<code> SegmentCache </code>获取细分的一种便利方法。
     * 
     */
    public static Segment getSharedSegment() {
        return getSharedInstance().getSegment();
    }

    /**
     * A convenience method to release a Segment to the shared
     * <code>SegmentCache</code>.
     * <p>
     *  一种方便的方法,用于将段发布到共享<code> SegmentCache </code>。
     * 
     */
    public static void releaseSharedSegment(Segment segment) {
        getSharedInstance().releaseSegment(segment);
    }



    /**
     * Creates and returns a SegmentCache.
     * <p>
     *  创建并返回SegmentCache。
     * 
     */
    public SegmentCache() {
        segments = new ArrayList<Segment>(11);
    }

    /**
     * Returns a <code>Segment</code>. When done, the <code>Segment</code>
     * should be recycled by invoking <code>releaseSegment</code>.
     * <p>
     *  返回<code>段</code>。完成后,应通过调用<code> releaseSegment </code>来回收<code> Segment </code>。
     * 
     */
    public Segment getSegment() {
        synchronized(this) {
            int size = segments.size();

            if (size > 0) {
                return segments.remove(size - 1);
            }
        }
        return new CachedSegment();
    }

    /**
     * Releases a Segment. You should not use a Segment after you release it,
     * and you should NEVER release the same Segment more than once, eg:
     * <pre>
     *   segmentCache.releaseSegment(segment);
     *   segmentCache.releaseSegment(segment);
     * </pre>
     * Will likely result in very bad things happening!
     * <p>
     *  释放段。在您释放后,您不应该使用细分,并且您不应该多次释放同一个细分,例如：
     * <pre>
     *  segmentCache.releaseSegment(segment); segmentCache.releaseSegment(segment);
     * </pre>
     *  可能会导致非常糟糕的事情发生！
     */
    public void releaseSegment(Segment segment) {
        if (segment instanceof CachedSegment) {
            synchronized(this) {
                segment.array = null;
                segment.count = 0;
                segments.add(segment);
            }
        }
    }


    /**
     * CachedSegment is used as a tagging interface to determine if
     * a Segment can successfully be shared.
     * <p>
     * 
     */
    private static class CachedSegment extends Segment {
    }
}

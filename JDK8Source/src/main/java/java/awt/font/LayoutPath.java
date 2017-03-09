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
/*
 * (C) Copyright IBM Corp. 2005, All Rights Reserved.
 * <p>
 *  (C)Copyright IBM Corp. 2005,保留所有权利。
 * 
 */
package java.awt.font;

import java.awt.geom.Point2D;

/**
 * LayoutPath provides a mapping between locations relative to the
 * baseline and points in user space.  Locations consist of an advance
 * along the baseline, and an offset perpendicular to the baseline at
 * the advance.  Positive values along the perpendicular are in the
 * direction that is 90 degrees clockwise from the baseline vector.
 * Locations are represented as a <code>Point2D</code>, where x is the advance and
 * y is the offset.
 *
 * <p>
 *  LayoutPath提供了相对于基线的位置和用户空间中的点之间的映射。位置包括沿基线的前进,以及在前进处垂直于基线的偏移。沿着垂线的正值在从基线向量顺时针旋转90度的方向上。
 * 位置表示为<code> Point2D </code>,其中x是提前量,y是偏移量。
 * 
 * 
 * @since 1.6
 */
public abstract class LayoutPath {
    /**
     * Convert a point in user space to a location relative to the
     * path. The location is chosen so as to minimize the distance
     * from the point to the path (e.g., the magnitude of the offset
     * will be smallest).  If there is more than one such location,
     * the location with the smallest advance is chosen.
     * <p>
     *  将用户空间中的点转换为相对于路径的位置。选择位置以便使从点到路径的距离最小化(例如,偏移的大小将是最小的)。如果存在多于一个这样的位置,则选择具有最小前进的位置。
     * 
     * 
     * @param point the point to convert.  If it is not the same
     * object as location, point will remain unmodified by this call.
     * @param location a <code>Point2D</code> to hold the returned location.
     * It can be the same object as point.
     * @return true if the point is associated with the portion of the
     * path preceding the location, false if it is associated with
     * the portion following.  The default, if the location is not at
     * a break or sharp bend in the path, is to return true.
     * @throws NullPointerException if point or location is null
     * @since 1.6
     */
    public abstract boolean pointToPath(Point2D point, Point2D location);

    /**
     * Convert a location relative to the path to a point in user
     * coordinates.  The path might bend abruptly or be disjoint at
     * the location's advance.  If this is the case, the value of
     * 'preceding' is used to disambiguate the portion of the path
     * whose location and slope is to be used to interpret the offset.
     * <p>
     *  将相对于路径的位置转换为用户坐标中的点。路径可能突然弯曲或在该位置的前进处不相交。如果是这种情况,则使用"在前"的值来消除路径的位置和斜率将被用于解释偏移的部分。
     * 
     * @param location a <code>Point2D</code> representing the advance (in x) and
     * offset (in y) of a location relative to the path.  If location
     * is not the same object as point, location will remain
     * unmodified by this call.
     * @param preceding if true, the portion preceding the advance
     * should be used, if false the portion after should be used.
     * This has no effect if the path does not break or bend sharply
     * at the advance.
     * @param point a <code>Point2D</code> to hold the returned point.  It can be
     * the same object as location.
     * @throws NullPointerException if location or point is null
     * @since 1.6
     */
    public abstract void pathToPoint(Point2D location, boolean preceding,
                                     Point2D point);
}

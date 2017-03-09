/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
 **********************************************************************
 **********************************************************************
 **********************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997                      ***
 *** As  an unpublished  work pursuant to Title 17 of the United    ***
 *** States Code.  All rights reserved.                             ***
 **********************************************************************
 **********************************************************************
 * <p>
 *  **************************************************** ****************** ****************************
 * **** ************************************ ************** ********************************************
 * ******** **** * COPYRIGHT(c)Eastman Kodak Company,1997 *** *根据United *** *国家法典第17章的未发表的作品。
 * 版权所有。
 *  *** *********************************************** ********************* **************************
 * *** ***************************************。
 * 版权所有。
 * 
 * 
 **********************************************************************/

package java.awt.color;

import sun.java2d.cmm.Profile;
import sun.java2d.cmm.ProfileDeferralInfo;

/**
 *
 * A subclass of the ICC_Profile class which represents profiles
 * which meet the following criteria: the color space type of the
 * profile is TYPE_GRAY and the profile includes the grayTRCTag and
 * mediaWhitePointTag tags.  Examples of this kind of profile are
 * monochrome input profiles, monochrome display profiles, and
 * monochrome output profiles.  The getInstance methods in the
 * ICC_Profile class will
 * return an ICC_ProfileGray object when the above conditions are
 * met.  The advantage of this class is that it provides a lookup
 * table that Java or native methods may be able to use directly to
 * optimize color conversion in some cases.
 * <p>
 * To transform from a GRAY device profile color space to the CIEXYZ Profile
 * Connection Space, the device gray component is transformed by
 * a lookup through the tone reproduction curve (TRC).  The result is
 * treated as the achromatic component of the PCS.
<pre>

&nbsp;               PCSY = grayTRC[deviceGray]

</pre>
 * The inverse transform is done by converting the PCS Y components to
 * device Gray via the inverse of the grayTRC.
 * <p>
 *  ICC_Profile类的子类,它表示满足以下条件的概要文件：概要文件的颜色空间类型为TYPE_GRAY,概要文件包括grayTRCTag和mediaWhitePointTag标记。
 * 这种简档的示例是单色输入简档,单色显示简档和单色输出简档。当满足上述条件时,ICC_Profile类中的getInstance方法将返回一个ICC_ProfileGray对象。
 * 这个类的优点是它提供了一个查找表,Java或本地方法可能能够在某些情况下直接使用来优化颜色转换。
 * <p>
 * 为了从GRAY设备配置文件颜色空间变换到CIEXYZ配置文件连接空间,通过色调再现曲线(TRC)的查找来转换设备灰色分量。结果被视为PCS的消色差分量。
 * <pre>
 * 
 *  &nbsp; PCSY = grayTRC [deviceGray]
 * 
 * </pre>
 *  逆变换通过经由grayTRC的逆来将PCS Y分量转换到设备Gray来完成。
 * 
 */



public class ICC_ProfileGray
extends ICC_Profile {

    static final long serialVersionUID = -1124721290732002649L;

    /**
     * Constructs a new ICC_ProfileGray from a CMM ID.
     * <p>
     *  从CMM ID构造新的ICC_ProfileGray。
     * 
     */
    ICC_ProfileGray(Profile p) {
        super(p);
    }

    /**
     * Constructs a new ICC_ProfileGray from a ProfileDeferralInfo object.
     * <p>
     *  从ProfileDeferralInfo对象构造新的ICC_ProfileGray。
     * 
     */
    ICC_ProfileGray(ProfileDeferralInfo pdi) {
        super(pdi);
    }


    /**
     * Returns a float array of length 3 containing the X, Y, and Z
     * components of the mediaWhitePointTag in the ICC profile.
     * <p>
     *  返回长度为3的float数组,其中包含ICC配置文件中mediaWhitePointTag的X,Y和Z分量。
     * 
     * 
     * @return an array containing the components of the
     * mediaWhitePointTag in the ICC profile.
     */
    public float[] getMediaWhitePoint() {
        return super.getMediaWhitePoint();
    }


    /**
     * Returns a gamma value representing the tone reproduction
     * curve (TRC).  If the profile represents the TRC as a table rather
     * than a single gamma value, then an exception is thrown.  In this
     * case the actual table can be obtained via getTRC().  When
     * using a gamma value, the PCS Y component is computed as follows:
<pre>

&nbsp;                         gamma
&nbsp;        PCSY = deviceGray

</pre>
     * <p>
     *  返回表示色调再现曲线(TRC)的伽马值。如果配置文件将TRC表示为表而不是单个gamma值,则抛出异常。在这种情况下,实际的表可以通过getTRC()获得。当使用伽马值时,PCS Y分量计算如下：
     * <pre>
     * 
     *  &nbsp; gamma&nbsp; PCSY = deviceGray
     * 
     * </pre>
     * 
     * @return the gamma value as a float.
     * @exception ProfileDataException if the profile does not specify
     *            the TRC as a single gamma value.
     */
    public float getGamma() {
    float theGamma;

        theGamma = super.getGamma(ICC_Profile.icSigGrayTRCTag);
        return theGamma;
    }

    /**
     * Returns the TRC as an array of shorts.  If the profile has
     * specified the TRC as linear (gamma = 1.0) or as a simple gamma
     * value, this method throws an exception, and the getGamma() method
     * should be used to get the gamma value.  Otherwise the short array
     * returned here represents a lookup table where the input Gray value
     * is conceptually in the range [0.0, 1.0].  Value 0.0 maps
     * to array index 0 and value 1.0 maps to array index length-1.
     * Interpolation may be used to generate output values for
     * input values which do not map exactly to an index in the
     * array.  Output values also map linearly to the range [0.0, 1.0].
     * Value 0.0 is represented by an array value of 0x0000 and
     * value 1.0 by 0xFFFF, i.e. the values are really unsigned
     * short values, although they are returned in a short array.
     * <p>
     * 
     * @return a short array representing the TRC.
     * @exception ProfileDataException if the profile does not specify
     *            the TRC as a table.
     */
    public short[] getTRC() {
    short[]    theTRC;

        theTRC = super.getTRC(ICC_Profile.icSigGrayTRCTag);
        return theTRC;
    }

}

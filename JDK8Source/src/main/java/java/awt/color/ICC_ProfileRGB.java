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
 * The ICC_ProfileRGB class is a subclass of the ICC_Profile class
 * that represents profiles which meet the following criteria:
 * <ul>
 * <li>The profile's color space type is RGB.</li>
 * <li>The profile includes the <code>redColorantTag</code>,
 * <code>greenColorantTag</code>, <code>blueColorantTag</code>,
 * <code>redTRCTag</code>, <code>greenTRCTag</code>,
 * <code>blueTRCTag</code>, and <code>mediaWhitePointTag</code> tags.</li>
 * </ul>
 * The <code>ICC_Profile</code> <code>getInstance</code> method will
 * return an <code>ICC_ProfileRGB</code> object when these conditions are met.
 * Three-component, matrix-based input profiles and RGB display profiles are
 * examples of this type of profile.
 * <p>
 * This profile class provides color transform matrices and lookup tables
 * that Java or native methods can use directly to
 * optimize color conversion in some cases.
 * <p>
 * To transform from a device profile color space to the CIEXYZ Profile
 * Connection Space, each device color component is first linearized by
 * a lookup through the corresponding tone reproduction curve (TRC).
 * The resulting linear RGB components are converted to the CIEXYZ PCS
 * using a a 3x3 matrix constructed from the RGB colorants.
 * <pre>
 *
 * &nbsp;               linearR = redTRC[deviceR]
 *
 * &nbsp;               linearG = greenTRC[deviceG]
 *
 * &nbsp;               linearB = blueTRC[deviceB]
 *
 * &nbsp; _      _       _                                             _   _         _
 * &nbsp;[  PCSX  ]     [  redColorantX  greenColorantX  blueColorantX  ] [  linearR  ]
 * &nbsp;[        ]     [                                               ] [           ]
 * &nbsp;[  PCSY  ]  =  [  redColorantY  greenColorantY  blueColorantY  ] [  linearG  ]
 * &nbsp;[        ]     [                                               ] [           ]
 * &nbsp;[_ PCSZ _]     [_ redColorantZ  greenColorantZ  blueColorantZ _] [_ linearB _]
 *
 * </pre>
 * The inverse transform is performed by converting PCS XYZ components to linear
 * RGB components through the inverse of the above 3x3 matrix, and then converting
 * linear RGB to device RGB through inverses of the TRCs.
 * <p>
 *  ICC_ProfileRGB类是ICC_Profile类的子类,表示满足以下条件的概要文件：
 * <ul>
 *  <li>个人资料的颜色空间类型为RGB。
 * </li> <li>个人资料包含<code> redColorantTag </code>,<code> greenColorantTag </code> code> redTRCTag </code>
 * ,<code> greenTRCTag </code>,<code> blueTRCTag </code>和<code> mediaWhitePointTag </code>。
 *  <li>个人资料的颜色空间类型为RGB。
 * </ul>
 *  当满足这些条件时,<code> ICC_Profile </code> <code> getInstance </code>方法将返回一个<code> ICC_ProfileRGB </code>对象
 * 。
 * 三分量,基于矩阵的输入分布和RGB显示分布是这种类型的分布的示例。
 * <p>
 * 此配置文件类提供颜色变换矩阵和查找表,Java或本机方法在某些情况下可以直接使用以优化颜色转换。
 * <p>
 *  为了从设备配置文件颜色空间变换到CIEXYZ配置文件连接空间,首先通过相应的色调再现曲线(TRC)的查找来将每个设备颜色分量线性化。
 * 使用从RGB着色剂构造的3×3矩阵将所得的线性RGB分量转换为CIEXYZ PCS。
 * <pre>
 * 
 *  &nbsp; linearR = redTRC [deviceR]
 * 
 *  &nbsp; linearG = greenTRC [deviceG]
 * 
 *  &nbsp; linearB = blueTRC [deviceB]
 * 
 *  &nbsp; _ _ _ _ _ _#&nbsp; [PCSX] [redColorantX greenColorantX blueColorantX] [linearR]&nbsp; [] [] [
 * ]&nbsp; [PCSY] = [redColorantY greenColorantY blueColorantY] [linearG]&nbsp; [] ; [_ PCSZ _] [_ redCo
 * lorantZ greenColorantZ blueColorantZ _] [_ linearB _]。
 * 
 * </pre>
 *  通过上述3×3矩阵的逆,将PCS XYZ分量转换为线性RGB分量,然后通过TRC的逆转换线性RGB到设备RGB,执行逆变换。
 * 
 */



public class ICC_ProfileRGB
extends ICC_Profile {

    static final long serialVersionUID = 8505067385152579334L;

    /**
     * Used to get a gamma value or TRC for the red component.
     * <p>
     *  用于获取红色分量的伽马值或TRC。
     * 
     */
    public static final int REDCOMPONENT = 0;

    /**
     * Used to get a gamma value or TRC for the green component.
     * <p>
     *  用于获取绿色组件的gamma值或TRC。
     * 
     */
    public static final int GREENCOMPONENT = 1;

    /**
     * Used to get a gamma value or TRC for the blue component.
     * <p>
     * 用于获取蓝色分量的伽马值或TRC。
     * 
     */
    public static final int BLUECOMPONENT = 2;


    /**
     * Constructs an new <code>ICC_ProfileRGB</code> from a CMM ID.
     *
     * <p>
     *  从CMM ID构造新的<code> ICC_ProfileRGB </code>。
     * 
     * 
     * @param p The CMM ID for the profile.
     *
     */
    ICC_ProfileRGB(Profile p) {
        super(p);
    }

    /**
     * Constructs a new <code>ICC_ProfileRGB</code> from a
     * ProfileDeferralInfo object.
     *
     * <p>
     *  从ProfileDeferralInfo对象构造新的<code> ICC_ProfileRGB </code>。
     * 
     * 
     * @param pdi
     */
    ICC_ProfileRGB(ProfileDeferralInfo pdi) {
        super(pdi);
    }


    /**
     * Returns an array that contains the components of the profile's
     * <CODE>mediaWhitePointTag</CODE>.
     *
     * <p>
     *  返回包含配置文件<CODE> mediaWhitePointTag </CODE>的组件的数组。
     * 
     * 
     * @return A 3-element <CODE>float</CODE> array containing the x, y,
     * and z components of the profile's <CODE>mediaWhitePointTag</CODE>.
     */
    public float[] getMediaWhitePoint() {
        return super.getMediaWhitePoint();
    }


    /**
     * Returns a 3x3 <CODE>float</CODE> matrix constructed from the
     * X, Y, and Z components of the profile's <CODE>redColorantTag</CODE>,
     * <CODE>greenColorantTag</CODE>, and <CODE>blueColorantTag</CODE>.
     * <p>
     * This matrix can be used for color transforms in the forward
     * direction of the profile--from the profile color space
     * to the CIEXYZ PCS.
     *
     * <p>
     *  返回从配置文件的<CODE> redColorantTag </CODE>,<CODE> greenColorantTag </CODE>和<CODE> blueColorantTag </CODE>
     * 的X,Y和Z分量构造的3x3 <CODE> float </CODE> CODE>。
     * <p>
     *  此矩阵可用于从轮廓颜色空间到CIEXYZ PCS的轮廓的正向方向上的颜色变换。
     * 
     * 
     * @return A 3x3 <CODE>float</CODE> array that contains the x, y, and z
     * components of the profile's <CODE>redColorantTag</CODE>,
     * <CODE>greenColorantTag</CODE>, and <CODE>blueColorantTag</CODE>.
     */
    public float[][] getMatrix() {
        float[][] theMatrix = new float[3][3];
        float[] tmpMatrix;

        tmpMatrix = getXYZTag(ICC_Profile.icSigRedColorantTag);
        theMatrix[0][0] = tmpMatrix[0];
        theMatrix[1][0] = tmpMatrix[1];
        theMatrix[2][0] = tmpMatrix[2];
        tmpMatrix = getXYZTag(ICC_Profile.icSigGreenColorantTag);
        theMatrix[0][1] = tmpMatrix[0];
        theMatrix[1][1] = tmpMatrix[1];
        theMatrix[2][1] = tmpMatrix[2];
        tmpMatrix = getXYZTag(ICC_Profile.icSigBlueColorantTag);
        theMatrix[0][2] = tmpMatrix[0];
        theMatrix[1][2] = tmpMatrix[1];
        theMatrix[2][2] = tmpMatrix[2];
        return theMatrix;
    }

    /**
     * Returns a gamma value representing the tone reproduction curve
     * (TRC) for a particular component.  The component parameter
     * must be one of REDCOMPONENT, GREENCOMPONENT, or BLUECOMPONENT.
     * <p>
     * If the profile
     * represents the TRC for the corresponding component
     * as a table rather than a single gamma value, an
     * exception is thrown.  In this case the actual table
     * can be obtained through the {@link #getTRC(int)} method.
     * When using a gamma value,
     * the linear component (R, G, or B) is computed as follows:
     * <pre>
     *
     * &nbsp;                                         gamma
     * &nbsp;        linearComponent = deviceComponent
     *
     *</pre>
     * <p>
     *  返回表示特定组件的色调再现曲线(TRC)的伽马值。组件参数必须是REDCOMPONENT,GREENCOMPONENT或BLUECOMPONENT中的一个。
     * <p>
     *  如果配置文件将相应组件的TRC表示为表而不是单个gamma值,则会抛出异常。在这种情况下,实际的表可以通过{@link #getTRC(int)}方法获得。
     * 当使用伽马值时,线性分量(R,G或B)计算如下：。
     * <pre>
     * 
     *  &nbsp; gamma&nbsp; linearComponent = deviceComponent
     * 
     * /pre>
     * 
     * @param component The <CODE>ICC_ProfileRGB</CODE> constant that
     * represents the component whose TRC you want to retrieve
     * @return the gamma value as a float.
     * @exception ProfileDataException if the profile does not specify
     *            the corresponding TRC as a single gamma value.
     */
    public float getGamma(int component) {
    float theGamma;
    int theSignature;

        switch (component) {
        case REDCOMPONENT:
            theSignature = ICC_Profile.icSigRedTRCTag;
            break;

        case GREENCOMPONENT:
            theSignature = ICC_Profile.icSigGreenTRCTag;
            break;

        case BLUECOMPONENT:
            theSignature = ICC_Profile.icSigBlueTRCTag;
            break;

        default:
            throw new IllegalArgumentException("Must be Red, Green, or Blue");
        }

        theGamma = super.getGamma(theSignature);

        return theGamma;
    }

    /**
     * Returns the TRC for a particular component as an array.
     * Component must be <code>REDCOMPONENT</code>,
     * <code>GREENCOMPONENT</code>, or <code>BLUECOMPONENT</code>.
     * Otherwise the returned array
     * represents a lookup table where the input component value
     * is conceptually in the range [0.0, 1.0].  Value 0.0 maps
     * to array index 0 and value 1.0 maps to array index length-1.
     * Interpolation might be used to generate output values for
     * input values that do not map exactly to an index in the
     * array.  Output values also map linearly to the range [0.0, 1.0].
     * Value 0.0 is represented by an array value of 0x0000 and
     * value 1.0 by 0xFFFF.  In other words, the values are really unsigned
     * <code>short</code> values even though they are returned in a
     * <code>short</code> array.
     *
     * If the profile has specified the corresponding TRC
     * as linear (gamma = 1.0) or as a simple gamma value, this method
     * throws an exception.  In this case, the {@link #getGamma(int)}
     * method should be used to get the gamma value.
     *
     * <p>
     * 
     * @param component The <CODE>ICC_ProfileRGB</CODE> constant that
     * represents the component whose TRC you want to retrieve:
     * <CODE>REDCOMPONENT</CODE>, <CODE>GREENCOMPONENT</CODE>, or
     * <CODE>BLUECOMPONENT</CODE>.
     *
     * @return a short array representing the TRC.
     * @exception ProfileDataException if the profile does not specify
     *            the corresponding TRC as a table.
     */
    public short[] getTRC(int component) {
    short[] theTRC;
    int theSignature;

        switch (component) {
        case REDCOMPONENT:
            theSignature = ICC_Profile.icSigRedTRCTag;
            break;

        case GREENCOMPONENT:
            theSignature = ICC_Profile.icSigGreenTRCTag;
            break;

        case BLUECOMPONENT:
            theSignature = ICC_Profile.icSigBlueTRCTag;
            break;

        default:
            throw new IllegalArgumentException("Must be Red, Green, or Blue");
        }

        theTRC = super.getTRC(theSignature);

        return theTRC;
    }

}

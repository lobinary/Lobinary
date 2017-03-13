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
 * ******** **** * COPYRIGHT(c)Eastman Kodak Company,1997 *** *作为根据联合王国***的第17条的未发表的作品*国家代码保留所有权利*** ***
 * ****** **************************************************** ********* *******************************
 * ******** ***************************。
 * 
 * 
 **********************************************************************/

package java.awt.color;

import sun.java2d.cmm.PCMM;
import sun.java2d.cmm.CMSManager;
import sun.java2d.cmm.Profile;
import sun.java2d.cmm.ProfileDataVerifier;
import sun.java2d.cmm.ProfileDeferralMgr;
import sun.java2d.cmm.ProfileDeferralInfo;
import sun.java2d.cmm.ProfileActivator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.Serializable;

import java.util.StringTokenizer;

import java.security.AccessController;
import java.security.PrivilegedAction;


/**
 * A representation of color profile data for device independent and
 * device dependent color spaces based on the International Color
 * Consortium Specification ICC.1:2001-12, File Format for Color Profiles,
 * (see <A href="http://www.color.org"> http://www.color.org</A>).
 * <p>
 * An ICC_ColorSpace object can be constructed from an appropriate
 * ICC_Profile.
 * Typically, an ICC_ColorSpace would be associated with an ICC
 * Profile which is either an input, display, or output profile (see
 * the ICC specification).  There are also device link, abstract,
 * color space conversion, and named color profiles.  These are less
 * useful for tagging a color or image, but are useful for other
 * purposes (in particular device link profiles can provide improved
 * performance for converting from one device's color space to
 * another's).
 * <p>
 * ICC Profiles represent transformations from the color space of
 * the profile (e.g. a monitor) to a Profile Connection Space (PCS).
 * Profiles of interest for tagging images or colors have a PCS
 * which is one of the two specific device independent
 * spaces (one CIEXYZ space and one CIELab space) defined in the
 * ICC Profile Format Specification.  Most profiles of interest
 * either have invertible transformations or explicitly specify
 * transformations going both directions.
 * <p>
 * 基于国际色彩联盟规范ICC1：2001-12,用于颜色配置文件的文件格式(参见<A href=\"http://wwwcolororg\"> http：///)的颜色配置文件数据的表示, / wwwco
 * lororg </A>)。
 * <p>
 *  ICC_ColorSpace对象可以从适当的ICC_Profile构造。通常,ICC_ColorSpace将与ICC配置文件相关联,该ICC配置文件是输入,显示或输出配置文件(参见ICC规范)。
 * 还有设备链接,抽象,颜色空间转换,和命名的颜色配置文件这些对标记颜色或图像不太有用,但对其他用途很有用(特别是设备链接配置文件可以提供从一个设备的颜色空间转换到另一个设备的颜色空间的改进性能)。
 * <p>
 * ICC配置文件表示从配置文件(例如,监视器)的颜色空间到配置文件连接空间(PCS)的转换。
 * 标记图像或颜色的配置文件具有PCS,这是两个特定的设备独立空间之一(一个CIEXYZ空间和一个CIELab空间)在ICC配置文件格式规范中定义大多数感兴趣的配置文件要么具有可逆的变换,要么明确指定双向的
 * 变换。
 * ICC配置文件表示从配置文件(例如,监视器)的颜色空间到配置文件连接空间(PCS)的转换。
 * 
 * 
 * @see ICC_ColorSpace
 */


public class ICC_Profile implements Serializable {

    private static final long serialVersionUID = -3938515861990936766L;

    private transient Profile cmmProfile;

    private transient ProfileDeferralInfo deferralInfo;
    private transient ProfileActivator profileActivator;

    // Registry of singleton profile objects for specific color spaces
    // defined in the ColorSpace class (e.g. CS_sRGB), see
    // getInstance(int cspace) factory method.
    private static ICC_Profile sRGBprofile;
    private static ICC_Profile XYZprofile;
    private static ICC_Profile PYCCprofile;
    private static ICC_Profile GRAYprofile;
    private static ICC_Profile LINEAR_RGBprofile;


    /**
     * Profile class is input.
     * <p>
     *  输入配置文件类
     * 
     */
    public static final int CLASS_INPUT = 0;

    /**
     * Profile class is display.
     * <p>
     *  配置文件类是显示
     * 
     */
    public static final int CLASS_DISPLAY = 1;

    /**
     * Profile class is output.
     * <p>
     *  输出配置文件类
     * 
     */
    public static final int CLASS_OUTPUT = 2;

    /**
     * Profile class is device link.
     * <p>
     *  配置文件类是设备链接
     * 
     */
    public static final int CLASS_DEVICELINK = 3;

    /**
     * Profile class is color space conversion.
     * <p>
     *  配置文件类是颜色空间转换
     * 
     */
    public static final int CLASS_COLORSPACECONVERSION = 4;

    /**
     * Profile class is abstract.
     * <p>
     *  概要文件类是抽象的
     * 
     */
    public static final int CLASS_ABSTRACT = 5;

    /**
     * Profile class is named color.
     * <p>
     *  配置文件类名为color
     * 
     */
    public static final int CLASS_NAMEDCOLOR = 6;


    /**
     * ICC Profile Color Space Type Signature: 'XYZ '.
     * <p>
     *  ICC配置文件颜色空间类型签名：'XYZ'
     * 
     */
    public static final int icSigXYZData        = 0x58595A20;    /* 'XYZ ' */

    /**
     * ICC Profile Color Space Type Signature: 'Lab '.
     * <p>
     *  ICC配置文件颜色空间类型签名：'Lab'
     * 
     */
    public static final int icSigLabData        = 0x4C616220;    /* 'Lab ' */

    /**
     * ICC Profile Color Space Type Signature: 'Luv '.
     * <p>
     * ICC配置文件颜色空间类型签名：'Luv'
     * 
     */
    public static final int icSigLuvData        = 0x4C757620;    /* 'Luv ' */

    /**
     * ICC Profile Color Space Type Signature: 'YCbr'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'YCbr'
     * 
     */
    public static final int icSigYCbCrData        = 0x59436272;    /* 'YCbr' */

    /**
     * ICC Profile Color Space Type Signature: 'Yxy '.
     * <p>
     *  ICC配置文件颜色空间类型签名：'Yxy'
     * 
     */
    public static final int icSigYxyData        = 0x59787920;    /* 'Yxy ' */

    /**
     * ICC Profile Color Space Type Signature: 'RGB '.
     * <p>
     *  ICC配置文件颜色空间类型签名：'RGB'
     * 
     */
    public static final int icSigRgbData        = 0x52474220;    /* 'RGB ' */

    /**
     * ICC Profile Color Space Type Signature: 'GRAY'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'GRAY'
     * 
     */
    public static final int icSigGrayData        = 0x47524159;    /* 'GRAY' */

    /**
     * ICC Profile Color Space Type Signature: 'HSV'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'HSV'
     * 
     */
    public static final int icSigHsvData        = 0x48535620;    /* 'HSV ' */

    /**
     * ICC Profile Color Space Type Signature: 'HLS'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'HLS'
     * 
     */
    public static final int icSigHlsData        = 0x484C5320;    /* 'HLS ' */

    /**
     * ICC Profile Color Space Type Signature: 'CMYK'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'CMYK'
     * 
     */
    public static final int icSigCmykData        = 0x434D594B;    /* 'CMYK' */

    /**
     * ICC Profile Color Space Type Signature: 'CMY '.
     * <p>
     *  ICC配置文件颜色空间类型签名：'CMY'
     * 
     */
    public static final int icSigCmyData        = 0x434D5920;    /* 'CMY ' */

    /**
     * ICC Profile Color Space Type Signature: '2CLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'2CLR'
     * 
     */
    public static final int icSigSpace2CLR        = 0x32434C52;    /* '2CLR' */

    /**
     * ICC Profile Color Space Type Signature: '3CLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'3CLR'
     * 
     */
    public static final int icSigSpace3CLR        = 0x33434C52;    /* '3CLR' */

    /**
     * ICC Profile Color Space Type Signature: '4CLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'4CLR'
     * 
     */
    public static final int icSigSpace4CLR        = 0x34434C52;    /* '4CLR' */

    /**
     * ICC Profile Color Space Type Signature: '5CLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'5CLR'
     * 
     */
    public static final int icSigSpace5CLR        = 0x35434C52;    /* '5CLR' */

    /**
     * ICC Profile Color Space Type Signature: '6CLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'6CLR'
     * 
     */
    public static final int icSigSpace6CLR        = 0x36434C52;    /* '6CLR' */

    /**
     * ICC Profile Color Space Type Signature: '7CLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'7CLR'
     * 
     */
    public static final int icSigSpace7CLR        = 0x37434C52;    /* '7CLR' */

    /**
     * ICC Profile Color Space Type Signature: '8CLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'8CLR'
     * 
     */
    public static final int icSigSpace8CLR        = 0x38434C52;    /* '8CLR' */

    /**
     * ICC Profile Color Space Type Signature: '9CLR'.
     * <p>
     * ICC配置文件颜色空间类型签名：'9CLR'
     * 
     */
    public static final int icSigSpace9CLR        = 0x39434C52;    /* '9CLR' */

    /**
     * ICC Profile Color Space Type Signature: 'ACLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'ACLR'
     * 
     */
    public static final int icSigSpaceACLR        = 0x41434C52;    /* 'ACLR' */

    /**
     * ICC Profile Color Space Type Signature: 'BCLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'BCLR'
     * 
     */
    public static final int icSigSpaceBCLR        = 0x42434C52;    /* 'BCLR' */

    /**
     * ICC Profile Color Space Type Signature: 'CCLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名："CCLR"
     * 
     */
    public static final int icSigSpaceCCLR        = 0x43434C52;    /* 'CCLR' */

    /**
     * ICC Profile Color Space Type Signature: 'DCLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'DCLR'
     * 
     */
    public static final int icSigSpaceDCLR        = 0x44434C52;    /* 'DCLR' */

    /**
     * ICC Profile Color Space Type Signature: 'ECLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'ECLR'
     * 
     */
    public static final int icSigSpaceECLR        = 0x45434C52;    /* 'ECLR' */

    /**
     * ICC Profile Color Space Type Signature: 'FCLR'.
     * <p>
     *  ICC配置文件颜色空间类型签名：'FCLR'
     * 
     */
    public static final int icSigSpaceFCLR        = 0x46434C52;    /* 'FCLR' */


    /**
     * ICC Profile Class Signature: 'scnr'.
     * <p>
     *  ICC配置文件类签名：'scnr'
     * 
     */
    public static final int icSigInputClass       = 0x73636E72;    /* 'scnr' */

    /**
     * ICC Profile Class Signature: 'mntr'.
     * <p>
     *  ICC配置文件类签名：'mntr'
     * 
     */
    public static final int icSigDisplayClass     = 0x6D6E7472;    /* 'mntr' */

    /**
     * ICC Profile Class Signature: 'prtr'.
     * <p>
     *  ICC配置文件类签名：'prtr'
     * 
     */
    public static final int icSigOutputClass      = 0x70727472;    /* 'prtr' */

    /**
     * ICC Profile Class Signature: 'link'.
     * <p>
     *  ICC配置文件类签名：'link'
     * 
     */
    public static final int icSigLinkClass        = 0x6C696E6B;    /* 'link' */

    /**
     * ICC Profile Class Signature: 'abst'.
     * <p>
     *  ICC配置文件类签名：'abst'
     * 
     */
    public static final int icSigAbstractClass    = 0x61627374;    /* 'abst' */

    /**
     * ICC Profile Class Signature: 'spac'.
     * <p>
     *  ICC配置文件类签名：'spac'
     * 
     */
    public static final int icSigColorSpaceClass  = 0x73706163;    /* 'spac' */

    /**
     * ICC Profile Class Signature: 'nmcl'.
     * <p>
     *  ICC配置文件类签名：'nmcl'
     * 
     */
    public static final int icSigNamedColorClass  = 0x6e6d636c;    /* 'nmcl' */


    /**
     * ICC Profile Rendering Intent: Perceptual.
     * <p>
     *  ICC配置文件呈现意图：感知
     * 
     */
    public static final int icPerceptual            = 0;

    /**
     * ICC Profile Rendering Intent: RelativeColorimetric.
     * <p>
     *  ICC配置文件渲染意图：RelativeColorimetric
     * 
     */
    public static final int icRelativeColorimetric    = 1;

    /**
     * ICC Profile Rendering Intent: Media-RelativeColorimetric.
     * <p>
     *  ICC配置文件渲染意图：Media-RelativeColorimetric
     * 
     * 
     * @since 1.5
     */
    public static final int icMediaRelativeColorimetric = 1;

    /**
     * ICC Profile Rendering Intent: Saturation.
     * <p>
     * ICC配置文件渲染意图：饱和度
     * 
     */
    public static final int icSaturation            = 2;

    /**
     * ICC Profile Rendering Intent: AbsoluteColorimetric.
     * <p>
     *  ICC配置文件渲染意图：AbsoluteColorimetric
     * 
     */
    public static final int icAbsoluteColorimetric    = 3;

    /**
     * ICC Profile Rendering Intent: ICC-AbsoluteColorimetric.
     * <p>
     *  ICC配置文件渲染意图：ICC-AbsoluteColorimetric
     * 
     * 
     * @since 1.5
     */
    public static final int icICCAbsoluteColorimetric = 3;


    /**
     * ICC Profile Tag Signature: 'head' - special.
     * <p>
     *  ICC简介标签签名："头" - 特殊
     * 
     */
    public static final int icSigHead      = 0x68656164; /* 'head' - special */

    /**
     * ICC Profile Tag Signature: 'A2B0'.
     * <p>
     *  ICC配置文件标签签名：'A2B0'
     * 
     */
    public static final int icSigAToB0Tag         = 0x41324230;    /* 'A2B0' */

    /**
     * ICC Profile Tag Signature: 'A2B1'.
     * <p>
     *  ICC配置文件标签签名：'A2B1'
     * 
     */
    public static final int icSigAToB1Tag         = 0x41324231;    /* 'A2B1' */

    /**
     * ICC Profile Tag Signature: 'A2B2'.
     * <p>
     *  ICC配置文件标签签名：'A2B2'
     * 
     */
    public static final int icSigAToB2Tag         = 0x41324232;    /* 'A2B2' */

    /**
     * ICC Profile Tag Signature: 'bXYZ'.
     * <p>
     *  ICC简介标签签名：'bXYZ'
     * 
     */
    public static final int icSigBlueColorantTag  = 0x6258595A;    /* 'bXYZ' */

    /**
     * ICC Profile Tag Signature: 'bXYZ'.
     * <p>
     *  ICC简介标签签名：'bXYZ'
     * 
     * 
     * @since 1.5
     */
    public static final int icSigBlueMatrixColumnTag = 0x6258595A; /* 'bXYZ' */

    /**
     * ICC Profile Tag Signature: 'bTRC'.
     * <p>
     *  ICC简介标签签名：'bTRC'
     * 
     */
    public static final int icSigBlueTRCTag       = 0x62545243;    /* 'bTRC' */

    /**
     * ICC Profile Tag Signature: 'B2A0'.
     * <p>
     *  ICC简介标签签名：'B2A0'
     * 
     */
    public static final int icSigBToA0Tag         = 0x42324130;    /* 'B2A0' */

    /**
     * ICC Profile Tag Signature: 'B2A1'.
     * <p>
     *  ICC简介标签签名：'B2A1'
     * 
     */
    public static final int icSigBToA1Tag         = 0x42324131;    /* 'B2A1' */

    /**
     * ICC Profile Tag Signature: 'B2A2'.
     * <p>
     *  ICC简介标签签名：'B2A2'
     * 
     */
    public static final int icSigBToA2Tag         = 0x42324132;    /* 'B2A2' */

    /**
     * ICC Profile Tag Signature: 'calt'.
     * <p>
     *  ICC配置文件标签签名：'calt'
     * 
     */
    public static final int icSigCalibrationDateTimeTag = 0x63616C74;
                                                                   /* 'calt' */

    /**
     * ICC Profile Tag Signature: 'targ'.
     * <p>
     *  ICC配置文件标签签名：'targ'
     * 
     */
    public static final int icSigCharTargetTag    = 0x74617267;    /* 'targ' */

    /**
     * ICC Profile Tag Signature: 'cprt'.
     * <p>
     *  ICC配置文件标签签名：'cprt'
     * 
     */
    public static final int icSigCopyrightTag     = 0x63707274;    /* 'cprt' */

    /**
     * ICC Profile Tag Signature: 'crdi'.
     * <p>
     *  ICC配置文件标签签名：'crdi'
     * 
     */
    public static final int icSigCrdInfoTag       = 0x63726469;    /* 'crdi' */

    /**
     * ICC Profile Tag Signature: 'dmnd'.
     * <p>
     *  ICC简介标签签名：'dmnd'
     * 
     */
    public static final int icSigDeviceMfgDescTag = 0x646D6E64;    /* 'dmnd' */

    /**
     * ICC Profile Tag Signature: 'dmdd'.
     * <p>
     *  ICC简介标签签名：'dmdd'
     * 
     */
    public static final int icSigDeviceModelDescTag = 0x646D6464;  /* 'dmdd' */

    /**
     * ICC Profile Tag Signature: 'devs'.
     * <p>
     *  ICC简介标签签名：'devs'
     * 
     */
    public static final int icSigDeviceSettingsTag =  0x64657673;  /* 'devs' */

    /**
     * ICC Profile Tag Signature: 'gamt'.
     * <p>
     * ICC简介标签签名：'gamt'
     * 
     */
    public static final int icSigGamutTag         = 0x67616D74;    /* 'gamt' */

    /**
     * ICC Profile Tag Signature: 'kTRC'.
     * <p>
     *  ICC简介标签签名：'kTRC'
     * 
     */
    public static final int icSigGrayTRCTag       = 0x6b545243;    /* 'kTRC' */

    /**
     * ICC Profile Tag Signature: 'gXYZ'.
     * <p>
     *  ICC简介标签签名：'gXYZ'
     * 
     */
    public static final int icSigGreenColorantTag = 0x6758595A;    /* 'gXYZ' */

    /**
     * ICC Profile Tag Signature: 'gXYZ'.
     * <p>
     *  ICC简介标签签名：'gXYZ'
     * 
     * 
     * @since 1.5
     */
    public static final int icSigGreenMatrixColumnTag = 0x6758595A;/* 'gXYZ' */

    /**
     * ICC Profile Tag Signature: 'gTRC'.
     * <p>
     *  ICC简介标签签名：'gTRC'
     * 
     */
    public static final int icSigGreenTRCTag      = 0x67545243;    /* 'gTRC' */

    /**
     * ICC Profile Tag Signature: 'lumi'.
     * <p>
     *  ICC配置文件标签签名：'lumi'
     * 
     */
    public static final int icSigLuminanceTag     = 0x6C756d69;    /* 'lumi' */

    /**
     * ICC Profile Tag Signature: 'meas'.
     * <p>
     *  ICC简介标签签名：'meas'
     * 
     */
    public static final int icSigMeasurementTag   = 0x6D656173;    /* 'meas' */

    /**
     * ICC Profile Tag Signature: 'bkpt'.
     * <p>
     *  ICC配置文件标签签名：'bkpt'
     * 
     */
    public static final int icSigMediaBlackPointTag = 0x626B7074;  /* 'bkpt' */

    /**
     * ICC Profile Tag Signature: 'wtpt'.
     * <p>
     *  ICC简介标签签名：'wtpt'
     * 
     */
    public static final int icSigMediaWhitePointTag = 0x77747074;  /* 'wtpt' */

    /**
     * ICC Profile Tag Signature: 'ncl2'.
     * <p>
     *  ICC配置文件标签签名：'ncl2'
     * 
     */
    public static final int icSigNamedColor2Tag   = 0x6E636C32;    /* 'ncl2' */

    /**
     * ICC Profile Tag Signature: 'resp'.
     * <p>
     *  ICC简介标签签名：'resp'
     * 
     */
    public static final int icSigOutputResponseTag = 0x72657370;   /* 'resp' */

    /**
     * ICC Profile Tag Signature: 'pre0'.
     * <p>
     *  ICC简介标签签名：'pre0'
     * 
     */
    public static final int icSigPreview0Tag      = 0x70726530;    /* 'pre0' */

    /**
     * ICC Profile Tag Signature: 'pre1'.
     * <p>
     *  ICC简介标签签名：'pre1'
     * 
     */
    public static final int icSigPreview1Tag      = 0x70726531;    /* 'pre1' */

    /**
     * ICC Profile Tag Signature: 'pre2'.
     * <p>
     *  ICC简介标签签名：'pre2'
     * 
     */
    public static final int icSigPreview2Tag      = 0x70726532;    /* 'pre2' */

    /**
     * ICC Profile Tag Signature: 'desc'.
     * <p>
     *  ICC简介标签签名：'desc'
     * 
     */
    public static final int icSigProfileDescriptionTag = 0x64657363;
                                                                   /* 'desc' */

    /**
     * ICC Profile Tag Signature: 'pseq'.
     * <p>
     *  ICC简介标签签名：'pseq'
     * 
     */
    public static final int icSigProfileSequenceDescTag = 0x70736571;
                                                                   /* 'pseq' */

    /**
     * ICC Profile Tag Signature: 'psd0'.
     * <p>
     *  ICC配置文件标签签名：'psd0'
     * 
     */
    public static final int icSigPs2CRD0Tag       = 0x70736430;    /* 'psd0' */

    /**
     * ICC Profile Tag Signature: 'psd1'.
     * <p>
     *  ICC配置文件标签签名：'psd1'
     * 
     */
    public static final int icSigPs2CRD1Tag       = 0x70736431;    /* 'psd1' */

    /**
     * ICC Profile Tag Signature: 'psd2'.
     * <p>
     *  ICC配置文件标签签名：'psd2'
     * 
     */
    public static final int icSigPs2CRD2Tag       = 0x70736432;    /* 'psd2' */

    /**
     * ICC Profile Tag Signature: 'psd3'.
     * <p>
     *  ICC配置文件标签签名：'psd3'
     * 
     */
    public static final int icSigPs2CRD3Tag       = 0x70736433;    /* 'psd3' */

    /**
     * ICC Profile Tag Signature: 'ps2s'.
     * <p>
     *  ICC配置文件标签签名：'ps2s'
     * 
     */
    public static final int icSigPs2CSATag        = 0x70733273;    /* 'ps2s' */

    /**
     * ICC Profile Tag Signature: 'ps2i'.
     * <p>
     *  ICC配置文件标签签名：'ps2i'
     * 
     */
    public static final int icSigPs2RenderingIntentTag = 0x70733269;
                                                                   /* 'ps2i' */

    /**
     * ICC Profile Tag Signature: 'rXYZ'.
     * <p>
     * ICC配置文件标签签名：'rXYZ'
     * 
     */
    public static final int icSigRedColorantTag   = 0x7258595A;    /* 'rXYZ' */

    /**
     * ICC Profile Tag Signature: 'rXYZ'.
     * <p>
     *  ICC配置文件标签签名：'rXYZ'
     * 
     * 
     * @since 1.5
     */
    public static final int icSigRedMatrixColumnTag = 0x7258595A;  /* 'rXYZ' */

    /**
     * ICC Profile Tag Signature: 'rTRC'.
     * <p>
     *  ICC简介标签签名：'rTRC'
     * 
     */
    public static final int icSigRedTRCTag        = 0x72545243;    /* 'rTRC' */

    /**
     * ICC Profile Tag Signature: 'scrd'.
     * <p>
     *  ICC个人资料标签签名：'scrd'
     * 
     */
    public static final int icSigScreeningDescTag = 0x73637264;    /* 'scrd' */

    /**
     * ICC Profile Tag Signature: 'scrn'.
     * <p>
     *  ICC简介标签签名：'scrn'
     * 
     */
    public static final int icSigScreeningTag     = 0x7363726E;    /* 'scrn' */

    /**
     * ICC Profile Tag Signature: 'tech'.
     * <p>
     *  ICC简介标签签名：'tech'
     * 
     */
    public static final int icSigTechnologyTag    = 0x74656368;    /* 'tech' */

    /**
     * ICC Profile Tag Signature: 'bfd '.
     * <p>
     *  ICC个人资料标签签名：'bfd'
     * 
     */
    public static final int icSigUcrBgTag         = 0x62666420;    /* 'bfd ' */

    /**
     * ICC Profile Tag Signature: 'vued'.
     * <p>
     *  ICC个人资料标签签名：'vued'
     * 
     */
    public static final int icSigViewingCondDescTag = 0x76756564;  /* 'vued' */

    /**
     * ICC Profile Tag Signature: 'view'.
     * <p>
     *  ICC配置文件标签签名：'view'
     * 
     */
    public static final int icSigViewingConditionsTag = 0x76696577;/* 'view' */

    /**
     * ICC Profile Tag Signature: 'chrm'.
     * <p>
     *  ICC配置文件标签签名：'chrm'
     * 
     */
    public static final int icSigChromaticityTag  = 0x6368726d;    /* 'chrm' */

    /**
     * ICC Profile Tag Signature: 'chad'.
     * <p>
     *  ICC简介标签签名：'chad'
     * 
     * 
     * @since 1.5
     */
    public static final int icSigChromaticAdaptationTag = 0x63686164;/* 'chad' */

    /**
     * ICC Profile Tag Signature: 'clro'.
     * <p>
     *  ICC简介标签签名：'clro'
     * 
     * 
     * @since 1.5
     */
    public static final int icSigColorantOrderTag = 0x636C726F;    /* 'clro' */

    /**
     * ICC Profile Tag Signature: 'clrt'.
     * <p>
     *  ICC配置文件标签签名：'clrt'
     * 
     * 
     * @since 1.5
     */
    public static final int icSigColorantTableTag = 0x636C7274;    /* 'clrt' */


    /**
     * ICC Profile Header Location: profile size in bytes.
     * <p>
     *  ICC配置文件头位置：配置文件大小(字节)
     * 
     */
    public static final int icHdrSize         = 0;  /* Profile size in bytes */

    /**
     * ICC Profile Header Location: CMM for this profile.
     * <p>
     *  ICC配置文件头位置：此配置文件的CMM
     * 
     */
    public static final int icHdrCmmId        = 4;  /* CMM for this profile */

    /**
     * ICC Profile Header Location: format version number.
     * <p>
     *  ICC配置文件头位置：格式版本号
     * 
     */
    public static final int icHdrVersion      = 8;  /* Format version number */

    /**
     * ICC Profile Header Location: type of profile.
     * <p>
     *  ICC配置文件头位置：配置文件类型
     * 
     */
    public static final int icHdrDeviceClass  = 12; /* Type of profile */

    /**
     * ICC Profile Header Location: color space of data.
     * <p>
     *  ICC配置文件头位置：数据的颜色空间
     * 
     */
    public static final int icHdrColorSpace   = 16; /* Color space of data */

    /**
     * ICC Profile Header Location: PCS - XYZ or Lab only.
     * <p>
     *  ICC配置文件头位置：PCS  - 仅限XYZ或实验室
     * 
     */
    public static final int icHdrPcs          = 20; /* PCS - XYZ or Lab only */

    /**
     * ICC Profile Header Location: date profile was created.
     * <p>
     * ICC配置文件头位置：日期配置文件已创建
     * 
     */
    public static final int icHdrDate       = 24; /* Date profile was created */

    /**
     * ICC Profile Header Location: icMagicNumber.
     * <p>
     *  ICC配置文件头位置：icMagicNumber
     * 
     */
    public static final int icHdrMagic        = 36; /* icMagicNumber */

    /**
     * ICC Profile Header Location: primary platform.
     * <p>
     *  ICC配置文件头位置：主平台
     * 
     */
    public static final int icHdrPlatform     = 40; /* Primary Platform */

    /**
     * ICC Profile Header Location: various bit settings.
     * <p>
     *  ICC配置文件头位置：各种位设置
     * 
     */
    public static final int icHdrFlags        = 44; /* Various bit settings */

    /**
     * ICC Profile Header Location: device manufacturer.
     * <p>
     *  ICC配置文件头位置：设备制造商
     * 
     */
    public static final int icHdrManufacturer = 48; /* Device manufacturer */

    /**
     * ICC Profile Header Location: device model number.
     * <p>
     *  ICC配置文件头位置：设备型号
     * 
     */
    public static final int icHdrModel        = 52; /* Device model number */

    /**
     * ICC Profile Header Location: device attributes.
     * <p>
     *  ICC配置文件头位置：设备属性
     * 
     */
    public static final int icHdrAttributes   = 56; /* Device attributes */

    /**
     * ICC Profile Header Location: rendering intent.
     * <p>
     *  ICC配置文件头位置：渲染意图
     * 
     */
    public static final int icHdrRenderingIntent = 64; /* Rendering intent */

    /**
     * ICC Profile Header Location: profile illuminant.
     * <p>
     *  ICC配置文件头位置：配置文件光源
     * 
     */
    public static final int icHdrIlluminant   = 68; /* Profile illuminant */

    /**
     * ICC Profile Header Location: profile creator.
     * <p>
     *  ICC配置文件头位置：配置文件创建者
     * 
     */
    public static final int icHdrCreator      = 80; /* Profile creator */

    /**
     * ICC Profile Header Location: profile's ID.
     * <p>
     *  ICC配置文件头位置：配置文件的ID
     * 
     * 
     * @since 1.5
     */
    public static final int icHdrProfileID = 84; /* Profile's ID */


    /**
     * ICC Profile Constant: tag type signaturE.
     * <p>
     *  ICC配置文件常量：标签类型符号
     * 
     */
    public static final int icTagType          = 0;    /* tag type signature */

    /**
     * ICC Profile Constant: reserved.
     * <p>
     *  ICC配置文件常量：保留
     * 
     */
    public static final int icTagReserved      = 4;    /* reserved */

    /**
     * ICC Profile Constant: curveType count.
     * <p>
     *  ICC配置文件常量：curveType计数
     * 
     */
    public static final int icCurveCount       = 8;    /* curveType count */

    /**
     * ICC Profile Constant: curveType data.
     * <p>
     *  ICC配置文件常量：curveType数据
     * 
     */
    public static final int icCurveData        = 12;   /* curveType data */

    /**
     * ICC Profile Constant: XYZNumber X.
     * <p>
     *  ICC配置文件常量：XYZNumber X
     * 
     */
    public static final int icXYZNumberX       = 8;    /* XYZNumber X */


    /**
     * Constructs an ICC_Profile object with a given ID.
     * <p>
     *  构造具有给定ID的ICC_Profile对象
     * 
     */
    ICC_Profile(Profile p) {
        this.cmmProfile = p;
    }


    /**
     * Constructs an ICC_Profile object whose loading will be deferred.
     * The ID will be 0 until the profile is loaded.
     * <p>
     * 构造一个ICC_Profile对象,其加载将被延迟该ID将为0,直到加载配置文件
     * 
     */
    ICC_Profile(ProfileDeferralInfo pdi) {
        this.deferralInfo = pdi;
        this.profileActivator = new ProfileActivator() {
            public void activate() throws ProfileDataException {
                activateDeferredProfile();
            }
        };
        ProfileDeferralMgr.registerDeferral(this.profileActivator);
    }


    /**
     * Frees the resources associated with an ICC_Profile object.
     * <p>
     *  释放与ICC_Profile对象关联的资源
     * 
     */
    protected void finalize () {
        if (cmmProfile != null) {
            CMSManager.getModule().freeProfile(cmmProfile);
        } else if (profileActivator != null) {
            ProfileDeferralMgr.unregisterDeferral(profileActivator);
        }
    }


    /**
     * Constructs an ICC_Profile object corresponding to the data in
     * a byte array.  Throws an IllegalArgumentException if the data
     * does not correspond to a valid ICC Profile.
     * <p>
     *  构造与字节数组中的数据对应的ICC_Profile对象如果数据不对应于有效的ICC配置文件,则抛出IllegalArgumentException异常
     * 
     * 
     * @param data the specified ICC Profile data
     * @return an <code>ICC_Profile</code> object corresponding to
     *          the data in the specified <code>data</code> array.
     */
    public static ICC_Profile getInstance(byte[] data) {
    ICC_Profile thisProfile;

        Profile p = null;

        if (ProfileDeferralMgr.deferring) {
            ProfileDeferralMgr.activateProfiles();
        }

        ProfileDataVerifier.verify(data);

        try {
            p = CMSManager.getModule().loadProfile(data);
        } catch (CMMException c) {
            throw new IllegalArgumentException("Invalid ICC Profile Data");
        }

        try {
            if ((getColorSpaceType (p) == ColorSpace.TYPE_GRAY) &&
                (getData (p, icSigMediaWhitePointTag) != null) &&
                (getData (p, icSigGrayTRCTag) != null)) {
                thisProfile = new ICC_ProfileGray (p);
            }
            else if ((getColorSpaceType (p) == ColorSpace.TYPE_RGB) &&
                (getData (p, icSigMediaWhitePointTag) != null) &&
                (getData (p, icSigRedColorantTag) != null) &&
                (getData (p, icSigGreenColorantTag) != null) &&
                (getData (p, icSigBlueColorantTag) != null) &&
                (getData (p, icSigRedTRCTag) != null) &&
                (getData (p, icSigGreenTRCTag) != null) &&
                (getData (p, icSigBlueTRCTag) != null)) {
                thisProfile = new ICC_ProfileRGB (p);
            }
            else {
                thisProfile = new ICC_Profile (p);
            }
        } catch (CMMException c) {
            thisProfile = new ICC_Profile (p);
        }
        return thisProfile;
    }



    /**
     * Constructs an ICC_Profile corresponding to one of the specific color
     * spaces defined by the ColorSpace class (for example CS_sRGB).
     * Throws an IllegalArgumentException if cspace is not one of the
     * defined color spaces.
     *
     * <p>
     *  构造与ColorSpace类定义的特定颜色空间之一对应的ICC_Profile(例如CS_sRGB)如果cspace不是定义的颜色空间之一,则抛出IllegalArgumentException异常。
     * 
     * 
     * @param cspace the type of color space to create a profile for.
     * The specified type is one of the color
     * space constants defined in the  <CODE>ColorSpace</CODE> class.
     *
     * @return an <code>ICC_Profile</code> object corresponding to
     *          the specified <code>ColorSpace</code> type.
     * @exception IllegalArgumentException If <CODE>cspace</CODE> is not
     * one of the predefined color space types.
     */
    public static ICC_Profile getInstance (int cspace) {
        ICC_Profile thisProfile = null;
        String fileName;

        switch (cspace) {
        case ColorSpace.CS_sRGB:
            synchronized(ICC_Profile.class) {
                if (sRGBprofile == null) {
                    /*
                     * Deferral is only used for standard profiles.
                     * Enabling the appropriate access privileges is handled
                     * at a lower level.
                     * <p>
                     *  延迟仅用于标准配置文件启用适当的访问权限在较低级别处理
                     * 
                     */
                    ProfileDeferralInfo pInfo =
                        new ProfileDeferralInfo("sRGB.pf",
                                                ColorSpace.TYPE_RGB, 3,
                                                CLASS_DISPLAY);
                    sRGBprofile = getDeferredInstance(pInfo);
                }
                thisProfile = sRGBprofile;
            }

            break;

        case ColorSpace.CS_CIEXYZ:
            synchronized(ICC_Profile.class) {
                if (XYZprofile == null) {
                    ProfileDeferralInfo pInfo =
                        new ProfileDeferralInfo("CIEXYZ.pf",
                                                ColorSpace.TYPE_XYZ, 3,
                                                CLASS_DISPLAY);
                    XYZprofile = getDeferredInstance(pInfo);
                }
                thisProfile = XYZprofile;
            }

            break;

        case ColorSpace.CS_PYCC:
            synchronized(ICC_Profile.class) {
                if (PYCCprofile == null) {
                    if (standardProfileExists("PYCC.pf"))
                    {
                        ProfileDeferralInfo pInfo =
                            new ProfileDeferralInfo("PYCC.pf",
                                                    ColorSpace.TYPE_3CLR, 3,
                                                    CLASS_DISPLAY);
                        PYCCprofile = getDeferredInstance(pInfo);
                    } else {
                        throw new IllegalArgumentException(
                                "Can't load standard profile: PYCC.pf");
                    }
                }
                thisProfile = PYCCprofile;
            }

            break;

        case ColorSpace.CS_GRAY:
            synchronized(ICC_Profile.class) {
                if (GRAYprofile == null) {
                    ProfileDeferralInfo pInfo =
                        new ProfileDeferralInfo("GRAY.pf",
                                                ColorSpace.TYPE_GRAY, 1,
                                                CLASS_DISPLAY);
                    GRAYprofile = getDeferredInstance(pInfo);
                }
                thisProfile = GRAYprofile;
            }

            break;

        case ColorSpace.CS_LINEAR_RGB:
            synchronized(ICC_Profile.class) {
                if (LINEAR_RGBprofile == null) {
                    ProfileDeferralInfo pInfo =
                        new ProfileDeferralInfo("LINEAR_RGB.pf",
                                                ColorSpace.TYPE_RGB, 3,
                                                CLASS_DISPLAY);
                    LINEAR_RGBprofile = getDeferredInstance(pInfo);
                }
                thisProfile = LINEAR_RGBprofile;
            }

            break;

        default:
            throw new IllegalArgumentException("Unknown color space");
        }

        return thisProfile;
    }

    /* This asserts system privileges, so is used only for the
     * standard profiles.
     * <p>
     *  标准配置文件
     * 
     */
    private static ICC_Profile getStandardProfile(final String name) {

        return AccessController.doPrivileged(
            new PrivilegedAction<ICC_Profile>() {
                 public ICC_Profile run() {
                     ICC_Profile p = null;
                     try {
                         p = getInstance (name);
                     } catch (IOException ex) {
                         throw new IllegalArgumentException(
                               "Can't load standard profile: " + name);
                     }
                     return p;
                 }
             });
    }

    /**
     * Constructs an ICC_Profile corresponding to the data in a file.
     * fileName may be an absolute or a relative file specification.
     * Relative file names are looked for in several places: first, relative
     * to any directories specified by the java.iccprofile.path property;
     * second, relative to any directories specified by the java.class.path
     * property; finally, in a directory used to store profiles always
     * available, such as the profile for sRGB.  Built-in profiles use .pf as
     * the file name extension for profiles, e.g. sRGB.pf.
     * This method throws an IOException if the specified file cannot be
     * opened or if an I/O error occurs while reading the file.  It throws
     * an IllegalArgumentException if the file does not contain valid ICC
     * Profile data.
     * <p>
     * 构造与文件中的数据相对应的ICC_Profile fileName可以是绝对或相对文件规范在多个位置查找相对文件名：首先,相对于由javaiccprofilepath属性指定的任何目录;第二,相对于由j
     * avaclasspath属性指定的任何目录;最后,在用于存储配置文件的目录中始终可用,例如sRGB的配置文件内置配置文件使用pf作为配置文件的文件扩展名,例如sRGBpf如果指定的文件无法打开,或者如果
     * I / O错误读取文件时如果文件不包含有效的ICC配置文件数据,则会抛出IllegalArgumentException异常。
     * 
     * 
     * @param fileName The file that contains the data for the profile.
     *
     * @return an <code>ICC_Profile</code> object corresponding to
     *          the data in the specified file.
     * @exception IOException If the specified file cannot be opened or
     * an I/O error occurs while reading the file.
     *
     * @exception IllegalArgumentException If the file does not
     * contain valid ICC Profile data.
     *
     * @exception SecurityException If a security manager is installed
     * and it does not permit read access to the given file.
     */
    public static ICC_Profile getInstance(String fileName) throws IOException {
        ICC_Profile thisProfile;
        FileInputStream fis = null;


        File f = getProfileFile(fileName);
        if (f != null) {
            fis = new FileInputStream(f);
        }
        if (fis == null) {
            throw new IOException("Cannot open file " + fileName);
        }

        thisProfile = getInstance(fis);

        fis.close();    /* close the file */

        return thisProfile;
    }


    /**
     * Constructs an ICC_Profile corresponding to the data in an InputStream.
     * This method throws an IllegalArgumentException if the stream does not
     * contain valid ICC Profile data.  It throws an IOException if an I/O
     * error occurs while reading the stream.
     * <p>
     * 构造与InputStream中的数据相对应的ICC_Profile如果流不包含有效的ICC配置文件数据,此方法将抛出IllegalArgumentException如果在读取流时发生I / O错误,则抛
     * 出IOException。
     * 
     * 
     * @param s The input stream from which to read the profile data.
     *
     * @return an <CODE>ICC_Profile</CODE> object corresponding to the
     *     data in the specified <code>InputStream</code>.
     *
     * @exception IOException If an I/O error occurs while reading the stream.
     *
     * @exception IllegalArgumentException If the stream does not
     * contain valid ICC Profile data.
     */
    public static ICC_Profile getInstance(InputStream s) throws IOException {
    byte profileData[];

        if (s instanceof ProfileDeferralInfo) {
            /* hack to detect profiles whose loading can be deferred */
            return getDeferredInstance((ProfileDeferralInfo) s);
        }

        if ((profileData = getProfileDataFromStream(s)) == null) {
            throw new IllegalArgumentException("Invalid ICC Profile Data");
        }

        return getInstance(profileData);
    }


    static byte[] getProfileDataFromStream(InputStream s) throws IOException {
    byte profileData[];
    int profileSize;

        byte header[] = new byte[128];
        int bytestoread = 128;
        int bytesread = 0;
        int n;

        while (bytestoread != 0) {
            if ((n = s.read(header, bytesread, bytestoread)) < 0) {
                return null;
            }
            bytesread += n;
            bytestoread -= n;
        }
        if (header[36] != 0x61 || header[37] != 0x63 ||
            header[38] != 0x73 || header[39] != 0x70) {
            return null;   /* not a valid profile */
        }
        profileSize = ((header[0] & 0xff) << 24) |
                      ((header[1] & 0xff) << 16) |
                      ((header[2] & 0xff) <<  8) |
                       (header[3] & 0xff);
        profileData = new byte[profileSize];
        System.arraycopy(header, 0, profileData, 0, 128);
        bytestoread = profileSize - 128;
        bytesread = 128;
        while (bytestoread != 0) {
            if ((n = s.read(profileData, bytesread, bytestoread)) < 0) {
                return null;
            }
            bytesread += n;
            bytestoread -= n;
        }

        return profileData;
    }


    /**
     * Constructs an ICC_Profile for which the actual loading of the
     * profile data from a file and the initialization of the CMM should
     * be deferred as long as possible.
     * Deferral is only used for standard profiles.
     * If deferring is disabled, then getStandardProfile() ensures
     * that all of the appropriate access privileges are granted
     * when loading this profile.
     * If deferring is enabled, then the deferred activation
     * code will take care of access privileges.
     * <p>
     *  构造一个ICC_Profile,从文件中实际加载配置文件数据和CMM的初始化应尽可能延迟。
     * Deferred仅用于标准配置文件如果禁用了延迟,则getStandardProfile()确保所有适当的加载此配置文件时授予访问权限如果启用了延迟,则延迟激活代码将负责访问权限。
     * 
     * 
     * @see activateDeferredProfile()
     */
    static ICC_Profile getDeferredInstance(ProfileDeferralInfo pdi) {
        if (!ProfileDeferralMgr.deferring) {
            return getStandardProfile(pdi.filename);
        }
        if (pdi.colorSpaceType == ColorSpace.TYPE_RGB) {
            return new ICC_ProfileRGB(pdi);
        } else if (pdi.colorSpaceType == ColorSpace.TYPE_GRAY) {
            return new ICC_ProfileGray(pdi);
        } else {
            return new ICC_Profile(pdi);
        }
    }


    void activateDeferredProfile() throws ProfileDataException {
        byte profileData[];
        FileInputStream fis;
        final String fileName = deferralInfo.filename;

        profileActivator = null;
        deferralInfo = null;
        PrivilegedAction<FileInputStream> pa = new PrivilegedAction<FileInputStream>() {
            public FileInputStream run() {
                File f = getStandardProfileFile(fileName);
                if (f != null) {
                    try {
                        return new FileInputStream(f);
                    } catch (FileNotFoundException e) {}
                }
                return null;
            }
        };
        if ((fis = AccessController.doPrivileged(pa)) == null) {
            throw new ProfileDataException("Cannot open file " + fileName);
        }
        try {
            profileData = getProfileDataFromStream(fis);
            fis.close();    /* close the file */
        }
        catch (IOException e) {
            ProfileDataException pde = new
                ProfileDataException("Invalid ICC Profile Data" + fileName);
            pde.initCause(e);
            throw pde;
        }
        if (profileData == null) {
            throw new ProfileDataException("Invalid ICC Profile Data" +
                fileName);
        }
        try {
            cmmProfile = CMSManager.getModule().loadProfile(profileData);
        } catch (CMMException c) {
            ProfileDataException pde = new
                ProfileDataException("Invalid ICC Profile Data" + fileName);
            pde.initCause(c);
            throw pde;
        }
    }


    /**
     * Returns profile major version.
     * <p>
     *  返回配置文件主版本
     * 
     * 
     * @return  The major version of the profile.
     */
    public int getMajorVersion() {
    byte[] theHeader;

        theHeader = getData(icSigHead); /* getData will activate deferred
        theHeader = getData(icSigHead); /* <p>
        theHeader = getData(icSigHead); /* 
                                           profiles if necessary */

        return (int) theHeader[8];
    }

    /**
     * Returns profile minor version.
     * <p>
     *  返回配置文件次要版本
     * 
     * 
     * @return The minor version of the profile.
     */
    public int getMinorVersion() {
    byte[] theHeader;

        theHeader = getData(icSigHead); /* getData will activate deferred
        theHeader = getData(icSigHead); /* <p>
        theHeader = getData(icSigHead); /* 
                                           profiles if necessary */

        return (int) theHeader[9];
    }

    /**
     * Returns the profile class.
     * <p>
     *  返回概要文件类
     * 
     * 
     * @return One of the predefined profile class constants.
     */
    public int getProfileClass() {
    byte[] theHeader;
    int theClassSig, theClass;

        if (deferralInfo != null) {
            return deferralInfo.profileClass; /* Need to have this info for
                                                 ICC_ColorSpace without
                                                 causing a deferred profile
            return deferralInfo.profileClass; /* <p>
            return deferralInfo.profileClass; /* ICC_ColorSpace而不导致延迟配置文件
            return deferralInfo.profileClass; /* 
            return deferralInfo.profileClass; /* 
                                                 to be loaded */
        }

        theHeader = getData(icSigHead);

        theClassSig = intFromBigEndian (theHeader, icHdrDeviceClass);

        switch (theClassSig) {
        case icSigInputClass:
            theClass = CLASS_INPUT;
            break;

        case icSigDisplayClass:
            theClass = CLASS_DISPLAY;
            break;

        case icSigOutputClass:
            theClass = CLASS_OUTPUT;
            break;

        case icSigLinkClass:
            theClass = CLASS_DEVICELINK;
            break;

        case icSigColorSpaceClass:
            theClass = CLASS_COLORSPACECONVERSION;
            break;

        case icSigAbstractClass:
            theClass = CLASS_ABSTRACT;
            break;

        case icSigNamedColorClass:
            theClass = CLASS_NAMEDCOLOR;
            break;

        default:
            throw new IllegalArgumentException("Unknown profile class");
        }

        return theClass;
    }

    /**
     * Returns the color space type.  Returns one of the color space type
     * constants defined by the ColorSpace class.  This is the
     * "input" color space of the profile.  The type defines the
     * number of components of the color space and the interpretation,
     * e.g. TYPE_RGB identifies a color space with three components - red,
     * green, and blue.  It does not define the particular color
     * characteristics of the space, e.g. the chromaticities of the
     * primaries.
     * <p>
     *  返回颜色空间类型返回由ColorSpace类定义的一个颜色空间类型常量这是配置文件的"输入"颜色空间类型定义颜色空间的组件数量和解释,例如TYPE_RGB标识颜色空间具有三个分量 - 红色,绿色和蓝色
     * 它不定义空间的特定颜色特性,例如初色的色度。
     * 
     * 
     * @return One of the color space type constants defined in the
     * <CODE>ColorSpace</CODE> class.
     */
    public int getColorSpaceType() {
        if (deferralInfo != null) {
            return deferralInfo.colorSpaceType; /* Need to have this info for
                                                   ICC_ColorSpace without
                                                   causing a deferred profile
            return deferralInfo.colorSpaceType; /* <p>
            return deferralInfo.colorSpaceType; /*  ICC_ColorSpace而不导致延迟配置文件
            return deferralInfo.colorSpaceType; /* 
            return deferralInfo.colorSpaceType; /* 
                                                   to be loaded */
        }
        return    getColorSpaceType(cmmProfile);
    }

    static int getColorSpaceType(Profile p) {
    byte[] theHeader;
    int theColorSpaceSig, theColorSpace;

        theHeader = getData(p, icSigHead);
        theColorSpaceSig = intFromBigEndian(theHeader, icHdrColorSpace);
        theColorSpace = iccCStoJCS (theColorSpaceSig);
        return theColorSpace;
    }

    /**
     * Returns the color space type of the Profile Connection Space (PCS).
     * Returns one of the color space type constants defined by the
     * ColorSpace class.  This is the "output" color space of the
     * profile.  For an input, display, or output profile useful
     * for tagging colors or images, this will be either TYPE_XYZ or
     * TYPE_Lab and should be interpreted as the corresponding specific
     * color space defined in the ICC specification.  For a device
     * link profile, this could be any of the color space type constants.
     * <p>
     * 返回配置文件连接空间(PCS)的颜色空间类型返回由ColorSpace类定义的一个颜色空间类型常量这是配置文件的"输出"颜色空间对于用于标记颜色的输入,显示或输出配置文件或图像,这将是TYPE_XYZ或
     * TYPE_Lab,并且应该被解释为在ICC规范中定义的相应的特定颜色空间。
     * 对于设备链接简档,这可以是任何颜色空间类型常量。
     * 
     * 
     * @return One of the color space type constants defined in the
     * <CODE>ColorSpace</CODE> class.
     */
    public int getPCSType() {
        if (ProfileDeferralMgr.deferring) {
            ProfileDeferralMgr.activateProfiles();
        }
        return getPCSType(cmmProfile);
    }


    static int getPCSType(Profile p) {
    byte[] theHeader;
    int thePCSSig, thePCS;

        theHeader = getData(p, icSigHead);
        thePCSSig = intFromBigEndian(theHeader, icHdrPcs);
        thePCS = iccCStoJCS(thePCSSig);
        return thePCS;
    }


    /**
     * Write this ICC_Profile to a file.
     *
     * <p>
     *  将此ICC_Profile写入文件
     * 
     * 
     * @param fileName The file to write the profile data to.
     *
     * @exception IOException If the file cannot be opened for writing
     * or an I/O error occurs while writing to the file.
     */
    public void write(String fileName) throws IOException {
    FileOutputStream outputFile;
    byte profileData[];

        profileData = getData(); /* this will activate deferred
        profileData = getData(); /* <p>
        profileData = getData(); /* 
                                    profiles if necessary */
        outputFile = new FileOutputStream(fileName);
        outputFile.write(profileData);
        outputFile.close ();
    }


    /**
     * Write this ICC_Profile to an OutputStream.
     *
     * <p>
     *  将此ICC_Profile写入OutputStream
     * 
     * 
     * @param s The stream to write the profile data to.
     *
     * @exception IOException If an I/O error occurs while writing to the
     * stream.
     */
    public void write(OutputStream s) throws IOException {
    byte profileData[];

        profileData = getData(); /* this will activate deferred
        profileData = getData(); /* <p>
        profileData = getData(); /* 
                                    profiles if necessary */
        s.write(profileData);
    }


    /**
     * Returns a byte array corresponding to the data of this ICC_Profile.
     * <p>
     *  返回与此ICC_Profile的数据对应的字节数组
     * 
     * 
     * @return A byte array that contains the profile data.
     * @see #setData(int, byte[])
     */
    public byte[] getData() {
    int profileSize;
    byte[] profileData;

        if (ProfileDeferralMgr.deferring) {
            ProfileDeferralMgr.activateProfiles();
        }

        PCMM mdl = CMSManager.getModule();

        /* get the number of bytes needed for this profile */
        profileSize = mdl.getProfileSize(cmmProfile);

        profileData = new byte [profileSize];

        /* get the data for the profile */
        mdl.getProfileData(cmmProfile, profileData);

        return profileData;
    }


    /**
     * Returns a particular tagged data element from the profile as
     * a byte array.  Elements are identified by signatures
     * as defined in the ICC specification.  The signature
     * icSigHead can be used to get the header.  This method is useful
     * for advanced applets or applications which need to access
     * profile data directly.
     *
     * <p>
     * 从配置文件中返回特定的标记数据元素作为字节数组元素通过ICC规范中定义的签名来标识。签名icSigHead可用于获取报头此方法对于需要直接访问配置文件数据的高级applet或应用程序非常有用
     * 
     * 
     * @param tagSignature The ICC tag signature for the data element you
     * want to get.
     *
     * @return A byte array that contains the tagged data element. Returns
     * <code>null</code> if the specified tag doesn't exist.
     * @see #setData(int, byte[])
     */
    public byte[] getData(int tagSignature) {

        if (ProfileDeferralMgr.deferring) {
            ProfileDeferralMgr.activateProfiles();
        }

        return getData(cmmProfile, tagSignature);
    }


    static byte[] getData(Profile p, int tagSignature) {
    int tagSize;
    byte[] tagData;

        try {
            PCMM mdl = CMSManager.getModule();

            /* get the number of bytes needed for this tag */
            tagSize = mdl.getTagSize(p, tagSignature);

            tagData = new byte[tagSize]; /* get an array for the tag */

            /* get the tag's data */
            mdl.getTagData(p, tagSignature, tagData);
        } catch(CMMException c) {
            tagData = null;
        }

        return tagData;
    }

    /**
     * Sets a particular tagged data element in the profile from
     * a byte array. The array should contain data in a format, corresponded
     * to the {@code tagSignature} as defined in the ICC specification, section 10.
     * This method is useful for advanced applets or applications which need to
     * access profile data directly.
     *
     * <p>
     *  从字节数组中设置配置文件中特定的标记数据元素该数组应包含格式化的数据,对应于ICC规范第10节中定义的{@code tagSignature}。该方法对于高级小应用程序或需要直接访问配置文件数据
     * 
     * 
     * @param tagSignature The ICC tag signature for the data element
     * you want to set.
     * @param tagData the data to set for the specified tag signature
     * @throws IllegalArgumentException if {@code tagSignature} is not a signature
     *         as defined in the ICC specification.
     * @throws IllegalArgumentException if a content of the {@code tagData}
     *         array can not be interpreted as valid tag data, corresponding
     *         to the {@code tagSignature}.
     * @see #getData
     */
    public void setData(int tagSignature, byte[] tagData) {

        if (ProfileDeferralMgr.deferring) {
            ProfileDeferralMgr.activateProfiles();
        }

        CMSManager.getModule().setTagData(cmmProfile, tagSignature, tagData);
    }

    /**
     * Sets the rendering intent of the profile.
     * This is used to select the proper transform from a profile that
     * has multiple transforms.
     * <p>
     *  设置配置文件的渲染意图这用于从具有多个变换的配置文件中选择适当的变换
     * 
     */
    void setRenderingIntent(int renderingIntent) {
        byte[] theHeader = getData(icSigHead);/* getData will activate deferred
        byte[] theHeader = getData(icSigHead);/* <p>
        byte[] theHeader = getData(icSigHead);/* 
                                                 profiles if necessary */
        intToBigEndian (renderingIntent, theHeader, icHdrRenderingIntent);
                                                 /* set the rendering intent */
        setData (icSigHead, theHeader);
    }


    /**
     * Returns the rendering intent of the profile.
     * This is used to select the proper transform from a profile that
     * has multiple transforms.  It is typically set in a source profile
     * to select a transform from an output profile.
     * <p>
     * 返回配置文件的渲染意图这用于从具有多个变换的配置文件中选择适当的变换它通常在源配置文件中设置以从输出配置文件中选择变换
     * 
     */
    int getRenderingIntent() {
        byte[] theHeader = getData(icSigHead);/* getData will activate deferred
        byte[] theHeader = getData(icSigHead);/* <p>
        byte[] theHeader = getData(icSigHead);/* 
                                                 profiles if necessary */

        int renderingIntent = intFromBigEndian(theHeader, icHdrRenderingIntent);
                                                 /* set the rendering intent */

        /* According to ICC spec, only the least-significant 16 bits shall be
         * used to encode the rendering intent. The most significant 16 bits
         * shall be set to zero. Thus, we are ignoring two most significant
         * bytes here.
         *
         *  See http://www.color.org/ICC1v42_2006-05.pdf, section 7.2.15.
         * <p>
         *  用于对渲染意图进行编码。最高有效16位应设置为零因此,我们在此忽略两个最高有效字节
         * 
         *  参见http：// wwwcolororg / ICC1v42_2006-05pdf,section 7215
         * 
         */
        return (0xffff & renderingIntent);
    }


    /**
     * Returns the number of color components in the "input" color
     * space of this profile.  For example if the color space type
     * of this profile is TYPE_RGB, then this method will return 3.
     *
     * <p>
     *  返回此概要文件的"输入"颜色空间中的颜色分量数例如,如果此概要文件的颜色空间类型为TYPE_RGB,则此方法将返回3
     * 
     * 
     * @return The number of color components in the profile's input
     * color space.
     *
     * @throws ProfileDataException if color space is in the profile
     *         is invalid
     */
    public int getNumComponents() {
    byte[]    theHeader;
    int    theColorSpaceSig, theNumComponents;

        if (deferralInfo != null) {
            return deferralInfo.numComponents; /* Need to have this info for
                                                  ICC_ColorSpace without
                                                  causing a deferred profile
            return deferralInfo.numComponents; /* <p>
            return deferralInfo.numComponents; /*  ICC_ColorSpace而不导致延迟配置文件
            return deferralInfo.numComponents; /* 
            return deferralInfo.numComponents; /* 
                                                  to be loaded */
        }
        theHeader = getData(icSigHead);

        theColorSpaceSig = intFromBigEndian (theHeader, icHdrColorSpace);

        switch (theColorSpaceSig) {
        case icSigGrayData:
            theNumComponents = 1;
            break;

        case icSigSpace2CLR:
            theNumComponents = 2;
            break;

        case icSigXYZData:
        case icSigLabData:
        case icSigLuvData:
        case icSigYCbCrData:
        case icSigYxyData:
        case icSigRgbData:
        case icSigHsvData:
        case icSigHlsData:
        case icSigCmyData:
        case icSigSpace3CLR:
            theNumComponents = 3;
            break;

        case icSigCmykData:
        case icSigSpace4CLR:
            theNumComponents = 4;
            break;

        case icSigSpace5CLR:
            theNumComponents = 5;
            break;

        case icSigSpace6CLR:
            theNumComponents = 6;
            break;

        case icSigSpace7CLR:
            theNumComponents = 7;
            break;

        case icSigSpace8CLR:
            theNumComponents = 8;
            break;

        case icSigSpace9CLR:
            theNumComponents = 9;
            break;

        case icSigSpaceACLR:
            theNumComponents = 10;
            break;

        case icSigSpaceBCLR:
            theNumComponents = 11;
            break;

        case icSigSpaceCCLR:
            theNumComponents = 12;
            break;

        case icSigSpaceDCLR:
            theNumComponents = 13;
            break;

        case icSigSpaceECLR:
            theNumComponents = 14;
            break;

        case icSigSpaceFCLR:
            theNumComponents = 15;
            break;

        default:
            throw new ProfileDataException ("invalid ICC color space");
        }

        return theNumComponents;
    }


    /**
     * Returns a float array of length 3 containing the X, Y, and Z
     * components of the mediaWhitePointTag in the ICC profile.
     * <p>
     *  返回长度为3的float数组,其中包含ICC配置文件中mediaWhitePointTag的X,Y和Z分量
     * 
     */
    float[] getMediaWhitePoint() {
        return getXYZTag(icSigMediaWhitePointTag);
                                           /* get the media white point tag */
    }


    /**
     * Returns a float array of length 3 containing the X, Y, and Z
     * components encoded in an XYZType tag.
     * <p>
     * 返回长度为3的float数组,其中包含在XYZType标记中编码的X,Y和Z分量
     * 
     */
    float[] getXYZTag(int theTagSignature) {
    byte[] theData;
    float[] theXYZNumber;
    int i1, i2, theS15Fixed16;

        theData = getData(theTagSignature); /* get the tag data */
                                            /* getData will activate deferred
                                            /* <p>
                                            /* 
                                               profiles if necessary */

        theXYZNumber = new float [3];        /* array to return */

        /* convert s15Fixed16Number to float */
        for (i1 = 0, i2 = icXYZNumberX; i1 < 3; i1++, i2 += 4) {
            theS15Fixed16 = intFromBigEndian(theData, i2);
            theXYZNumber [i1] = ((float) theS15Fixed16) / 65536.0f;
        }
        return theXYZNumber;
    }


    /**
     * Returns a gamma value representing a tone reproduction
     * curve (TRC).  If the profile represents the TRC as a table rather
     * than a single gamma value, then an exception is thrown.  In this
     * case the actual table can be obtained via getTRC().
     * theTagSignature should be one of icSigGrayTRCTag, icSigRedTRCTag,
     * icSigGreenTRCTag, or icSigBlueTRCTag.
     * <p>
     *  返回表示色调再现曲线(TRC)的伽马值如果轮廓将TRC表示为表而不是单个伽马值,则抛出异常在这种情况下,实际的表可以通过getTRC()获得,TagSignature应该是一个of icSigGray
     * TRCTag,icSigRedTRCTag,icSigGreenTRCTag或icSigBlueTRCTag。
     * 
     * 
     * @return the gamma value as a float.
     * @exception ProfileDataException if the profile does not specify
     *            the TRC as a single gamma value.
     */
    float getGamma(int theTagSignature) {
    byte[] theTRCData;
    float theGamma;
    int theU8Fixed8;

        theTRCData = getData(theTagSignature); /* get the TRC */
                                               /* getData will activate deferred
                                               /* <p>
                                               /* 
                                                  profiles if necessary */

        if (intFromBigEndian (theTRCData, icCurveCount) != 1) {
            throw new ProfileDataException ("TRC is not a gamma");
        }

        /* convert u8Fixed8 to float */
        theU8Fixed8 = (shortFromBigEndian(theTRCData, icCurveData)) & 0xffff;

        theGamma = ((float) theU8Fixed8) / 256.0f;

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
     * theTagSignature should be one of icSigGrayTRCTag, icSigRedTRCTag,
     * icSigGreenTRCTag, or icSigBlueTRCTag.
     * <p>
     * 将TRC返回为短整型数组如果概要文件将TRC指定为线性(gamma = 10)或作为简单的gamma值,则此方法会抛出异常,并应使用getGamma()方法获取伽马值这里返回的短数组表示查找表,其中输入
     * Gray值概念上在范围[00,10]中。
     * 值00映射到数组索引0,值10映射到数组索引长度-1。
     * 内插可以用于生成输出值不精确映射到数组中索引的输入值输出值也线性映射到范围[00,10]值0x00由数组值0x0000表示,值10由0xFFFF表示,即这些值实际上是无符号短值,尽管它们以短阵列返回th
     * eTagSignature应该是icSigGrayTRCTag,icSigRedTRCTag,icSigGreenTRCTag或icSigBlueTRCTag之一。
     * 值00映射到数组索引0,值10映射到数组索引长度-1。
     * 
     * 
     * @return a short array representing the TRC.
     * @exception ProfileDataException if the profile does not specify
     *            the TRC as a table.
     */
    short[] getTRC(int theTagSignature) {
    byte[] theTRCData;
    short[] theTRC;
    int i1, i2, nElements, theU8Fixed8;

        theTRCData = getData(theTagSignature); /* get the TRC */
                                               /* getData will activate deferred
                                               /* <p>
                                               /* 
                                                  profiles if necessary */

        nElements = intFromBigEndian(theTRCData, icCurveCount);

        if (nElements == 1) {
            throw new ProfileDataException("TRC is not a table");
        }

        /* make the short array */
        theTRC = new short [nElements];

        for (i1 = 0, i2 = icCurveData; i1 < nElements; i1++, i2 += 2) {
            theTRC[i1] = shortFromBigEndian(theTRCData, i2);
        }

        return theTRC;
    }


    /* convert an ICC color space signature into a Java color space type */
    static int iccCStoJCS(int theColorSpaceSig) {
    int theColorSpace;

        switch (theColorSpaceSig) {
        case icSigXYZData:
            theColorSpace = ColorSpace.TYPE_XYZ;
            break;

        case icSigLabData:
            theColorSpace = ColorSpace.TYPE_Lab;
            break;

        case icSigLuvData:
            theColorSpace = ColorSpace.TYPE_Luv;
            break;

        case icSigYCbCrData:
            theColorSpace = ColorSpace.TYPE_YCbCr;
            break;

        case icSigYxyData:
            theColorSpace = ColorSpace.TYPE_Yxy;
            break;

        case icSigRgbData:
            theColorSpace = ColorSpace.TYPE_RGB;
            break;

        case icSigGrayData:
            theColorSpace = ColorSpace.TYPE_GRAY;
            break;

        case icSigHsvData:
            theColorSpace = ColorSpace.TYPE_HSV;
            break;

        case icSigHlsData:
            theColorSpace = ColorSpace.TYPE_HLS;
            break;

        case icSigCmykData:
            theColorSpace = ColorSpace.TYPE_CMYK;
            break;

        case icSigCmyData:
            theColorSpace = ColorSpace.TYPE_CMY;
            break;

        case icSigSpace2CLR:
            theColorSpace = ColorSpace.TYPE_2CLR;
            break;

        case icSigSpace3CLR:
            theColorSpace = ColorSpace.TYPE_3CLR;
            break;

        case icSigSpace4CLR:
            theColorSpace = ColorSpace.TYPE_4CLR;
            break;

        case icSigSpace5CLR:
            theColorSpace = ColorSpace.TYPE_5CLR;
            break;

        case icSigSpace6CLR:
            theColorSpace = ColorSpace.TYPE_6CLR;
            break;

        case icSigSpace7CLR:
            theColorSpace = ColorSpace.TYPE_7CLR;
            break;

        case icSigSpace8CLR:
            theColorSpace = ColorSpace.TYPE_8CLR;
            break;

        case icSigSpace9CLR:
            theColorSpace = ColorSpace.TYPE_9CLR;
            break;

        case icSigSpaceACLR:
            theColorSpace = ColorSpace.TYPE_ACLR;
            break;

        case icSigSpaceBCLR:
            theColorSpace = ColorSpace.TYPE_BCLR;
            break;

        case icSigSpaceCCLR:
            theColorSpace = ColorSpace.TYPE_CCLR;
            break;

        case icSigSpaceDCLR:
            theColorSpace = ColorSpace.TYPE_DCLR;
            break;

        case icSigSpaceECLR:
            theColorSpace = ColorSpace.TYPE_ECLR;
            break;

        case icSigSpaceFCLR:
            theColorSpace = ColorSpace.TYPE_FCLR;
            break;

        default:
            throw new IllegalArgumentException ("Unknown color space");
        }

        return theColorSpace;
    }


    static int intFromBigEndian(byte[] array, int index) {
        return (((array[index]   & 0xff) << 24) |
                ((array[index+1] & 0xff) << 16) |
                ((array[index+2] & 0xff) <<  8) |
                 (array[index+3] & 0xff));
    }


    static void intToBigEndian(int value, byte[] array, int index) {
            array[index]   = (byte) (value >> 24);
            array[index+1] = (byte) (value >> 16);
            array[index+2] = (byte) (value >>  8);
            array[index+3] = (byte) (value);
    }


    static short shortFromBigEndian(byte[] array, int index) {
        return (short) (((array[index]   & 0xff) << 8) |
                         (array[index+1] & 0xff));
    }


    static void shortToBigEndian(short value, byte[] array, int index) {
            array[index]   = (byte) (value >> 8);
            array[index+1] = (byte) (value);
    }


    /*
     * fileName may be an absolute or a relative file specification.
     * Relative file names are looked for in several places: first, relative
     * to any directories specified by the java.iccprofile.path property;
     * second, relative to any directories specified by the java.class.path
     * property; finally, in a directory used to store profiles always
     * available, such as a profile for sRGB.  Built-in profiles use .pf as
     * the file name extension for profiles, e.g. sRGB.pf.
     * <p>
     * fileName可以是绝对或相对文件规范在多个位置查找相对文件名：首先,相对于javaiccprofilepath属性指定的任何目录;第二,相对于由javaclasspath属性指定的任何目录;最后,在
     * 用于存储概要文件的目录中始终可用,例如sRGB的概要文件内置概要文件使用pf作为概要文件的文件扩展名,例如sRGBpf。
     * 
     */
    private static File getProfileFile(String fileName) {
        String path, dir, fullPath;

        File f = new File(fileName); /* try absolute file name */
        if (f.isAbsolute()) {
            /* Rest of code has little sense for an absolute pathname,
            /* <p>
            /* 
               so return here. */
            return f.isFile() ? f : null;
        }
        if ((!f.isFile()) &&
                ((path = System.getProperty("java.iccprofile.path")) != null)){
                                    /* try relative to java.iccprofile.path */
                StringTokenizer st =
                    new StringTokenizer(path, File.pathSeparator);
                while (st.hasMoreTokens() && ((f == null) || (!f.isFile()))) {
                    dir = st.nextToken();
                        fullPath = dir + File.separatorChar + fileName;
                    f = new File(fullPath);
                    if (!isChildOf(f, dir)) {
                        f = null;
                    }
                }
            }

        if (((f == null) || (!f.isFile())) &&
                ((path = System.getProperty("java.class.path")) != null)) {
                                    /* try relative to java.class.path */
                StringTokenizer st =
                    new StringTokenizer(path, File.pathSeparator);
                while (st.hasMoreTokens() && ((f == null) || (!f.isFile()))) {
                    dir = st.nextToken();
                        fullPath = dir + File.separatorChar + fileName;
                    f = new File(fullPath);
                }
            }

        if ((f == null) || (!f.isFile())) {
            /* try the directory of built-in profiles */
            f = getStandardProfileFile(fileName);
        }
        if (f != null && f.isFile()) {
            return f;
        }
        return null;
    }

    /**
     * Returns a file object corresponding to a built-in profile
     * specified by fileName.
     * If there is no built-in profile with such name, then the method
     * returns null.
     * <p>
     *  返回与fileName指定的内置配置文件对应的文件对象如果没有具有此类名称的内置配置文件,则方法将返回null
     * 
     */
    private static File getStandardProfileFile(String fileName) {
        String dir = System.getProperty("java.home") +
            File.separatorChar + "lib" + File.separatorChar + "cmm";
        String fullPath = dir + File.separatorChar + fileName;
        File f = new File(fullPath);
        return (f.isFile() && isChildOf(f, dir)) ? f : null;
    }

    /**
     * Checks whether given file resides inside give directory.
     * <p>
     *  检查给定文件是否驻留在给定目录中
     * 
     */
    private static boolean isChildOf(File f, String dirName) {
        try {
            File dir = new File(dirName);
            String canonicalDirName = dir.getCanonicalPath();
            if (!canonicalDirName.endsWith(File.separator)) {
                canonicalDirName += File.separator;
            }
            String canonicalFileName = f.getCanonicalPath();
            return canonicalFileName.startsWith(canonicalDirName);
        } catch (IOException e) {
            /* we do not expect the IOException here, because invocation
             * of this function is always preceeded by isFile() call.
             * <p>
             *  的这个函数总是由前面的isFile()调用
             * 
             */
            return false;
        }
    }

    /**
     * Checks whether built-in profile specified by fileName exists.
     * <p>
     *  检查fileName指定的内置配置文件是否存在
     * 
     */
    private static boolean standardProfileExists(final String fileName) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
                public Boolean run() {
                    return getStandardProfileFile(fileName) != null;
                }
            });
    }


    /*
     * Serialization support.
     *
     * Directly deserialized profiles are useless since they are not
     * registered with CMM.  We don't allow constructor to be called
     * directly and instead have clients to call one of getInstance
     * factory methods that will register the profile with CMM.  For
     * deserialization we implement readResolve method that will
     * resolve the bogus deserialized profile object with one obtained
     * with getInstance as well.
     *
     * There're two primary factory methods for construction of ICC
     * profiles: getInstance(int cspace) and getInstance(byte[] data).
     * This implementation of ICC_Profile uses the former to return a
     * cached singleton profile object, other implementations will
     * likely use this technique too.  To preserve the singleton
     * pattern across serialization we serialize cached singleton
     * profiles in such a way that deserializing VM could call
     * getInstance(int cspace) method that will resolve deserialized
     * object into the corresponding singleton as well.
     *
     * Since the singletons are private to ICC_Profile the readResolve
     * method have to be `protected' instead of `private' so that
     * singletons that are instances of subclasses of ICC_Profile
     * could be correctly deserialized.
     * <p>
     * 序列化支持
     * 
     *  直接反序列化的配置文件是没有用的,因为它们没有注册到CMM我们不允许直接调用构造函数,而是让客户端调用一个getInstance工厂方法,将注册配置文件与CMM对于反序列化,我们实现readResol
     * ve方法,将解决伪造反序列化对象与一个获得getInstance以及。
     * 
     * 有两个主要的工厂方法来构建ICC配置文件：getInstance(int cspace)和getInstance(byte [] data)ICC_Profile的这个实现使用前者返回一个缓存的单例配置
     * 文件对象,其他实现也可能使用这种技术。
     * 保留单例模式跨序列化我们序列化缓存的单例配置文件,这样,反序列化VM可以调用getInstance(int cspace)方法,将解析反序列化的对象到相应的单例。
     * 
     *  由于单例对ICC_Profile是私有的,因此readResolve方法必须是"protected"而不是"private",这样ICC_Profile子类的实例的单例可以正确地反序列化
     * 
     */


    /**
     * Version of the format of additional serialized data in the
     * stream.  Version&nbsp;<code>1</code> corresponds to Java&nbsp;2
     * Platform,&nbsp;v1.3.
     * <p>
     * 流中的其他序列化数据的版本版本&nbsp; <code> 1 </code>对应于Java&nbsp; 2 Platform,&nbsp; v13
     * 
     * 
     * @since 1.3
     * @serial
     */
    private int iccProfileSerializedDataVersion = 1;


    /**
     * Writes default serializable fields to the stream.  Writes a
     * string and an array of bytes to the stream as additional data.
     *
     * <p>
     *  将缺省可序列化字段写入流将字符串和字节数组写入流作为附加数据
     * 
     * 
     * @param s stream used for serialization.
     * @throws IOException
     *     thrown by <code>ObjectInputStream</code>.
     * @serialData
     *     The <code>String</code> is the name of one of
     *     <code>CS_<var>*</var></code> constants defined in the
     *     {@link ColorSpace} class if the profile object is a profile
     *     for a predefined color space (for example
     *     <code>"CS_sRGB"</code>).  The string is <code>null</code>
     *     otherwise.
     *     <p>
     *     The <code>byte[]</code> array is the profile data for the
     *     profile.  For predefined color spaces <code>null</code> is
     *     written instead of the profile data.  If in the future
     *     versions of Java API new predefined color spaces will be
     *     added, future versions of this class may choose to write
     *     for new predefined color spaces not only the color space
     *     name, but the profile data as well so that older versions
     *     could still deserialize the object.
     */
    private void writeObject(ObjectOutputStream s)
      throws IOException
    {
        s.defaultWriteObject();

        String csName = null;
        if (this == sRGBprofile) {
            csName = "CS_sRGB";
        } else if (this == XYZprofile) {
            csName = "CS_CIEXYZ";
        } else if (this == PYCCprofile) {
            csName = "CS_PYCC";
        } else if (this == GRAYprofile) {
            csName = "CS_GRAY";
        } else if (this == LINEAR_RGBprofile) {
            csName = "CS_LINEAR_RGB";
        }

        // Future versions may choose to write profile data for new
        // predefined color spaces as well, if any will be introduced,
        // so that old versions that don't recognize the new CS name
        // may fall back to constructing profile from the data.
        byte[] data = null;
        if (csName == null) {
            // getData will activate deferred profile if necessary
            data = getData();
        }

        s.writeObject(csName);
        s.writeObject(data);
    }

    // Temporary storage used by readObject to store resolved profile
    // (obtained with getInstance) for readResolve to return.
    private transient ICC_Profile resolvedDeserializedProfile;

    /**
     * Reads default serializable fields from the stream.  Reads from
     * the stream a string and an array of bytes as additional data.
     *
     * <p>
     *  从流中读取缺省的可序列化字段从流中读取字符串和字节数组作为附加数据
     * 
     * 
     * @param s stream used for deserialization.
     * @throws IOException
     *     thrown by <code>ObjectInputStream</code>.
     * @throws ClassNotFoundException
     *     thrown by <code>ObjectInputStream</code>.
     * @serialData
     *     The <code>String</code> is the name of one of
     *     <code>CS_<var>*</var></code> constants defined in the
     *     {@link ColorSpace} class if the profile object is a profile
     *     for a predefined color space (for example
     *     <code>"CS_sRGB"</code>).  The string is <code>null</code>
     *     otherwise.
     *     <p>
     *     The <code>byte[]</code> array is the profile data for the
     *     profile.  It will usually be <code>null</code> for the
     *     predefined profiles.
     *     <p>
     *     If the string is recognized as a constant name for
     *     predefined color space the object will be resolved into
     *     profile obtained with
     *     <code>getInstance(int&nbsp;cspace)</code> and the profile
     *     data are ignored.  Otherwise the object will be resolved
     *     into profile obtained with
     *     <code>getInstance(byte[]&nbsp;data)</code>.
     * @see #readResolve()
     * @see #getInstance(int)
     * @see #getInstance(byte[])
     */
    private void readObject(ObjectInputStream s)
      throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();

        String csName = (String)s.readObject();
        byte[] data = (byte[])s.readObject();

        int cspace = 0;         // ColorSpace.CS_* constant if known
        boolean isKnownPredefinedCS = false;
        if (csName != null) {
            isKnownPredefinedCS = true;
            if (csName.equals("CS_sRGB")) {
                cspace = ColorSpace.CS_sRGB;
            } else if (csName.equals("CS_CIEXYZ")) {
                cspace = ColorSpace.CS_CIEXYZ;
            } else if (csName.equals("CS_PYCC")) {
                cspace = ColorSpace.CS_PYCC;
            } else if (csName.equals("CS_GRAY")) {
                cspace = ColorSpace.CS_GRAY;
            } else if (csName.equals("CS_LINEAR_RGB")) {
                cspace = ColorSpace.CS_LINEAR_RGB;
            } else {
                isKnownPredefinedCS = false;
            }
        }

        if (isKnownPredefinedCS) {
            resolvedDeserializedProfile = getInstance(cspace);
        } else {
            resolvedDeserializedProfile = getInstance(data);
        }
    }

    /**
     * Resolves instances being deserialized into instances registered
     * with CMM.
     * <p>
     *  将正在反序列化的实例解析为使用CMM注册的实例
     * 
     * @return ICC_Profile object for profile registered with CMM.
     * @throws ObjectStreamException
     *     never thrown, but mandated by the serialization spec.
     * @since 1.3
     */
    protected Object readResolve() throws ObjectStreamException {
        return resolvedDeserializedProfile;
    }
}

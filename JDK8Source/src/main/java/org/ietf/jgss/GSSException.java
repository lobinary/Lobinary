/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package org.ietf.jgss;

/**
 * This exception is thrown whenever a GSS-API error occurs, including
 * any mechanism specific error.  It may contain both the major and the
 * minor GSS-API status codes.  Major error codes are those defined at the
 * GSS-API level in this class. Minor error codes are mechanism specific
 * error codes that can provide additional information. The underlying
 * mechanism implementation is responsible for setting appropriate minor
 * status codes when throwing this exception.  Aside from delivering the
 * numeric error codes to the caller, this class performs the mapping from
 * their numeric values to textual representations. <p>
 *
 * <p>
 *  每当发生GSS-API错误时抛出此异常,包括任何机制特定的错误。它可能包含主要和次要GSS-API状态代码。主要错误代码是在此类中的GSS-API级别定义的代码。
 * 次要错误代码是可提供其他信息的机制特定错误代码。底层机制实现负责在抛出此异常时设置适当的次要状态代码。除了向调用者传递数字错误代码外,此类还执行从其数值到文本表示的映射。 <p>。
 * 
 * 
 * @author Mayank Upadhyay
 * @since 1.4
 */
public class GSSException extends Exception {

    private static final long serialVersionUID = -2706218945227726672L;

    /**
     * Channel bindings mismatch.
     * <p>
     *  通道绑定不匹配。
     * 
     */
    public static final int BAD_BINDINGS = 1; //start with 1

    /**
     * Unsupported mechanism requested.
     * <p>
     *  请求不支持的机制。
     * 
     */
    public static final int BAD_MECH = 2;

    /**
     * Invalid name provided.
     * <p>
     *  提供的名称无效。
     * 
     */
    public static final int BAD_NAME = 3;

    /**
     * Name of unsupported type provided.
     * <p>
     *  提供的不支持类型的名称。
     * 
     */
    public static final int BAD_NAMETYPE = 4;

    /**
     * Invalid status code.
     * <p>
     *  状态代码无效。
     * 
     */
    /*
     * This is meant to be thrown by display_status which displays
     * major/minor status when an incorrect status type is passed in to it!
     * <p>
     *  这意味着通过display_status抛出,当不正确的状态类型传递给它显示主/次要状态！
     * 
     */
    public static final int BAD_STATUS = 5;

    /**
     * Token had invalid integrity check.
     * <p>
     *  令牌具有无效的完整性检查。
     * 
     */
    public static final int BAD_MIC = 6;

    /**
     * Security context expired.
     * <p>
     *  安全上下文已过期。
     * 
     */
    public static final int CONTEXT_EXPIRED = 7;

    /**
     * Expired credentials.
     * <p>
     *  过期凭据。
     * 
     */
    public static final int CREDENTIALS_EXPIRED  = 8;

    /**
     * Defective credentials.
     *
     * <p>
     *  凭证有缺陷。
     * 
     */
    public static final int DEFECTIVE_CREDENTIAL = 9;

    /**
     * Defective token.
     *
     * <p>
     *  缺陷令牌。
     * 
     */
    public static final int DEFECTIVE_TOKEN = 10;

    /**
     * General failure, unspecified at GSS-API level.
     * <p>
     *  一般失败,未指定GSS-API级别。
     * 
     */
    public static final int FAILURE = 11;

    /**
     * Invalid security context.
     * <p>
     *  安全上下文无效。
     * 
     */
    public static final int NO_CONTEXT = 12;

    /**
     * Invalid credentials.
     * <p>
     *  无效证件。
     * 
     */
    public static final int NO_CRED = 13;

    /**
     * Unsupported QOP value.
     * <p>
     *  不支持的QOP值。
     * 
     */
    public static final int BAD_QOP = 14;

    /**
     * Operation unauthorized.
     * <p>
     *  操作未经授权。
     * 
     */
    public static final int UNAUTHORIZED = 15;

    /**
     * Operation unavailable.
     * <p>
     *  操作不可用。
     * 
     */
    public static final int UNAVAILABLE = 16;

    /**
     * Duplicate credential element requested.
     * <p>
     *  请求的重复凭证元素。
     * 
     */
    public static final int DUPLICATE_ELEMENT = 17;

    /**
     * Name contains multi-mechanism elements.
     * <p>
     *  名称包含多机制元素。
     * 
     */
    public static final int NAME_NOT_MN = 18;

    /**
     * The token was a duplicate of an earlier token.
     * This is a fatal error code that may occur during
     * context establishment.  It is not used to indicate
     * supplementary status values. The MessageProp object is
     * used for that purpose.
     * <p>
     * 令牌是早期令牌的副本。这是在上下文建立期间可能发生的致命错误代码。它不用于指示补充状态值。 MessageProp对象用于此目的。
     * 
     */
    public static final int DUPLICATE_TOKEN = 19;

    /**
     * The token's validity period has expired.  This is a
     * fatal error code that may occur during context establishment.
     * It is not used to indicate supplementary status values.
     * The MessageProp object is used for that purpose.
     * <p>
     *  令牌的有效期已过期。这是在上下文建立期间可能发生的致命错误代码。它不用于指示补充状态值。 MessageProp对象用于此目的。
     * 
     */
    public static final int OLD_TOKEN = 20;


    /**
     * A later token has already been processed.  This is a
     * fatal error code that may occur during context establishment.
     * It is not used to indicate supplementary status values.
     * The MessageProp object is used for that purpose.
     * <p>
     *  稍后的令牌已处理。这是在上下文建立期间可能发生的致命错误代码。它不用于指示补充状态值。 MessageProp对象用于此目的。
     * 
     */
    public static final int UNSEQ_TOKEN = 21;


    /**
     * An expected per-message token was not received.  This is a
     * fatal error code that may occur during context establishment.
     * It is not used to indicate supplementary status values.
     * The MessageProp object is used for that purpose.
     * <p>
     *  未收到预期的每个消息令牌。这是在上下文建立期间可能发生的致命错误代码。它不用于指示补充状态值。 MessageProp对象用于此目的。
     * 
     */
    public static final int GAP_TOKEN = 22;


    private static String[] messages = {
        "Channel binding mismatch", // BAD_BINDINGS
        "Unsupported mechanism requested", // BAD_MECH
        "Invalid name provided", // BAD_NAME
        "Name of unsupported type provided", //BAD_NAMETYPE
        "Invalid input status selector", // BAD_STATUS
        "Token had invalid integrity check", // BAD_SIG
        "Specified security context expired", // CONTEXT_EXPIRED
        "Expired credentials detected", // CREDENTIALS_EXPIRED
        "Defective credential detected", // DEFECTIVE_CREDENTIAL
        "Defective token detected", // DEFECTIVE_TOKEN
        "Failure unspecified at GSS-API level", // FAILURE
        "Security context init/accept not yet called or context deleted",
                                                // NO_CONTEXT
        "No valid credentials provided", // NO_CRED
        "Unsupported QOP value", // BAD_QOP
        "Operation unauthorized", // UNAUTHORIZED
        "Operation unavailable", // UNAVAILABLE
        "Duplicate credential element requested", //DUPLICATE_ELEMENT
        "Name contains multi-mechanism elements", // NAME_NOT_MN
        "The token was a duplicate of an earlier token", //DUPLICATE_TOKEN
        "The token's validity period has expired", //OLD_TOKEN
        "A later token has already been processed", //UNSEQ_TOKEN
        "An expected per-message token was not received", //GAP_TOKEN
    };

   /**
    * The major code for this exception
    *
    * <p>
    *  此异常的主要代码
    * 
    * 
    * @serial
    */
    private int major;

   /**
    * The minor code for this exception
    *
    * <p>
    *  此异常的次要代码
    * 
    * 
    * @serial
    */
    private int minor = 0;

   /**
    * The text string for minor code
    *
    * <p>
    *  次要代码的文本字符串
    * 
    * 
    * @serial
    */
    private String minorMessage = null;

   /**
    * Alternate text string for major code
    *
    * <p>
    *  主代码的替代文本字符串
    * 
    * 
    * @serial
    */

    private String majorString = null;

    /**
     *  Creates a GSSException object with a specified major code.
     *
     * <p>
     *  创建具有指定主代码的GSSException对象。
     * 
     * 
     * @param majorCode the The GSS error code for the problem causing this
     * exception to be thrown.
     */
    public GSSException (int majorCode) {

        if (validateMajor(majorCode))
            major = majorCode;
        else
            major = FAILURE;
    }

    /**
     * Construct a GSSException object with a specified major code and a
     * specific major string for it.
     *
     * <p>
     *  构造具有指定主代码和特定主要字符串的GSSException对象。
     * 
     * 
     * @param majorCode the fatal error code causing this exception.
     * @param majorString an expicit message to be included in this exception
     */
    GSSException (int majorCode, String majorString) {

        if (validateMajor(majorCode))
            major = majorCode;
        else
            major = FAILURE;
        this.majorString = majorString;
    }


    /**
     * Creates a GSSException object with the specified major code, minor
     * code, and minor code textual explanation.  This constructor is to be
     * used when the exception is originating from the underlying mechanism
     * level. It allows the setting of both the GSS code and the mechanism
     * code.
     *
     * <p>
     *  创建具有指定主代码,次代码和次代码文本说明的GSSException对象。当异常源自底层机制级别时,将使用此构造函数。它允许设置GSS代码和机制代码。
     * 
     * 
     * @param majorCode the GSS error code for the problem causing this
     * exception to be thrown.
     * @param minorCode the mechanism level error code for the problem
     * causing this exception to be thrown.
     * @param minorString the textual explanation of the mechanism error
     * code.
     */
    public GSSException (int majorCode, int minorCode, String minorString) {

        if (validateMajor(majorCode))
            major = majorCode;
        else
            major = FAILURE;

        minor = minorCode;
        minorMessage = minorString;
    }

    /**
     * Returns the GSS-API level major error code for the problem causing
     * this exception to be thrown. Major error codes are
     * defined at the mechanism independent GSS-API level in this
     * class. Mechanism specific error codes that might provide more
     * information are set as the minor error code.
     *
     * <p>
     * 返回导致抛出此异常的问题的GSS-API级别主要错误代码。主要错误代码在此类中与机制无关的GSS-API级别上定义。可能提供更多信息的机制特定错误代码被设置为次要错误代码。
     * 
     * 
     * @return int the GSS-API level major error code causing this exception
     * @see #getMajorString
     * @see #getMinor
     * @see #getMinorString
     */
    public int getMajor() {
        return major;
    }

    /**
     * Returns the mechanism level error code for the problem causing this
     * exception to be thrown. The minor code is set by the underlying
     * mechanism.
     *
     * <p>
     *  返回导致抛出此异常的问题的机制级别错误代码。次要代码由底层机制设置。
     * 
     * 
     * @return int the mechanism error code; 0 indicates that it has not
     * been set.
     * @see #getMinorString
     * @see #setMinor
     */
    public int  getMinor(){
        return minor;
    }

    /**
     * Returns a string explaining the GSS-API level major error code in
     * this exception.
     *
     * <p>
     *  返回一个字符串,说明此异常中的GSS-API级别主要错误代码。
     * 
     * 
     * @return String explanation string for the major error code
     * @see #getMajor
     * @see #toString
     */
    public String getMajorString() {

        if (majorString != null)
            return majorString;
        else
            return messages[major - 1];
    }


    /**
     * Returns a string explaining the mechanism specific error code.
     * If the minor status code is 0, then no mechanism level error details
     * will be available.
     *
     * <p>
     *  返回一个字符串,说明机制特定的错误代码。如果次要状态代码为0,则没有机制级别错误详细信息可用。
     * 
     * 
     * @return String a textual explanation of mechanism error code
     * @see #getMinor
     * @see #getMajorString
     * @see #toString
     */
    public String getMinorString() {

        return minorMessage;
    }


    /**
     * Used by the exception thrower to set the mechanism
     * level minor error code and its string explanation.  This is used by
     * mechanism providers to indicate error details.
     *
     * <p>
     *  用于由异常thrower设置机制级别的小错误代码及其字符串解释。这由机制提供程序用于指示错误详细信息。
     * 
     * 
     * @param minorCode the mechanism specific error code
     * @param message textual explanation of the mechanism error code
     * @see #getMinor
     */
    public void setMinor(int minorCode, String message) {

        minor = minorCode;
        minorMessage = message;
    }


    /**
     * Returns a textual representation of both the major and the minor
     * status codes.
     *
     * <p>
     *  返回主要和次要状态代码的文本表示。
     * 
     * 
     * @return a String with the error descriptions
     */
    public String toString() {
        return ("GSSException: " + getMessage());
    }

    /**
     * Returns a textual representation of both the major and the minor
     * status codes.
     *
     * <p>
     *  返回主要和次要状态代码的文本表示。
     * 
     * 
     * @return a String with the error descriptions
     */
    public String getMessage() {
        if (minor == 0)
            return (getMajorString());

        return (getMajorString()
                + " (Mechanism level: " + getMinorString() + ")");
    }


    /*
     * Validates the major code in the proper range.
     * <p>
     *  验证主代码是否在正确的范围内。
     */
    private boolean validateMajor(int major) {

        if (major > 0 && major <= messages.length)
            return (true);

        return (false);
    }
}

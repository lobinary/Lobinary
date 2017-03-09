/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * This is a utility class used within the per-message GSSContext
 * methods to convey per-message properties.<p>
 *
 * When used with the GSSContext interface's wrap and getMIC methods, an
 * instance of this class is used to indicate the desired
 * Quality-of-Protection (QOP) and to request if confidentiality services
 * are to be applied to caller supplied data (wrap only).  To request
 * default QOP, the value of 0 should be used for QOP.<p>
 *
 * When used with the unwrap and verifyMIC methods of the GSSContext
 * interface, an instance of this class will be used to indicate the
 * applied QOP and confidentiality services over the supplied message.
 * In the case of verifyMIC, the confidentiality state will always be
 * <code>false</code>.  Upon return from these methods, this object will also
 * contain any supplementary status values applicable to the processed
 * token.  The supplementary status values can indicate old tokens, out
 * of sequence tokens, gap tokens or duplicate tokens.<p>
 *
 * <p>
 *  这是一个在每个消息GSSContext方法中使用的实用程序类,用于传递每个消息的属性。<p>
 * 
 *  当与GSSContext接口的wrap和getMIC方法一起使用时,此类的实例用于指示所需的保护质量(QOP),并请求是否将机密性服务应用于调用者提供的数据(仅包裹)。
 * 要请求默认QOP,值0应该用于QOP。<p>。
 * 
 *  当与GSSContext接口的unwrap和verifyMIC方法一起使用时,此类的实例将用于通过提供的消息指示应用的QOP和机密性服务。
 * 在verifyMIC的情况下,保密状态将始终为<code> false </code>。从这些方法返回时,此对象还将包含适用于处理的令牌的任何补充状态值。
 * 补充状态值可以指示旧令牌,序列令牌,间隙令牌或复制令牌。<p>。
 * 
 * 
 * @see GSSContext#wrap
 * @see GSSContext#unwrap
 * @see GSSContext#getMIC
 * @see GSSContext#verifyMIC
 *
 * @author Mayank Upadhyay
 * @since 1.4
 */
public class MessageProp {

    private boolean privacyState;
    private int qop;
    private boolean dupToken;
    private boolean oldToken;
    private boolean unseqToken;
    private boolean gapToken;
    private int minorStatus;
    private String minorString;

   /**
    * Constructor which sets the desired privacy state. The QOP value used
    * is 0.
    *
    * <p>
    *  构造器设置所需的隐私状态。使用的QOP值为0。
    * 
    * 
    * @param privState the privacy (i.e. confidentiality) state
    */
    public MessageProp(boolean privState) {
        this(0, privState);
    }

    /**
     * Constructor which sets the values for the qop and privacy state.
     *
     * <p>
     *  构造函数,用于设置qop和隐私状态的值。
     * 
     * 
     * @param qop the QOP value
     * @param privState the privacy (i.e. confidentiality) state
     */
    public MessageProp(int qop, boolean privState) {
        this.qop = qop;
        this.privacyState = privState;
        resetStatusValues();
    }

    /**
     * Retrieves the QOP value.
     *
     * <p>
     *  检索QOP值。
     * 
     * 
     * @return an int representing the QOP value
     * @see #setQOP
     */
    public int getQOP() {
        return qop;
    }

    /**
     * Retrieves the privacy state.
     *
     * <p>
     *  检索隐私状态。
     * 
     * 
     * @return true if the privacy (i.e., confidentiality) state is true,
     * false otherwise.
     * @see #setPrivacy
     */
    public boolean getPrivacy() {

        return (privacyState);
    }

    /**
     * Sets the QOP value.
     *
     * <p>
     *  设置QOP值。
     * 
     * 
     * @param qop the int value to set the QOP to
     * @see #getQOP
     */
    public void setQOP(int qop) {
        this.qop = qop;
    }


    /**
     * Sets the privacy state.
     *
     * <p>
     *  设置隐私状态。
     * 
     * 
     * @param privState true is the privacy (i.e., confidentiality) state
     * is true, false otherwise.
     * @see #getPrivacy
     */
    public void setPrivacy(boolean privState) {

        this.privacyState = privState;
    }


    /**
     * Tests if this is a duplicate of an earlier token.
     *
     * <p>
     *  测试这是否是早期令牌的副本。
     * 
     * 
     * @return true if this is a duplicate, false otherwise.
     */
    public boolean isDuplicateToken() {
        return dupToken;
    }

    /**
     * Tests if this token's validity period has expired, i.e., the token
     * is too old to be checked for duplication.
     *
     * <p>
     * 测试此令牌的有效期是否已过期,即令牌太旧,无法检查其是否重复。
     * 
     * 
     * @return true if the token's validity period has expired, false
     * otherwise.
     */
    public boolean isOldToken() {
        return oldToken;
    }

    /**
     * Tests if a later token had already been processed.
     *
     * <p>
     *  测试稍后的令牌是否已被处理。
     * 
     * 
     * @return true if a later token had already been processed, false otherwise.
     */
    public boolean isUnseqToken() {
        return unseqToken;
    }

    /**
     * Tests if an expected token was not received, i.e., one or more
     * predecessor tokens have not yet been successfully processed.
     *
     * <p>
     *  测试是否未接收到预期令牌,即,一个或多个前趋令牌尚未成功处理。
     * 
     * 
     * @return true if an expected per-message token was not received,
     * false otherwise.
     */
    public boolean isGapToken() {
        return gapToken;
    }

    /**
     * Retrieves the minor status code that the underlying mechanism might
     * have set for this per-message operation.
     *
     * <p>
     *  检索底层机制可能为此每个消息操作设置的次要状态代码。
     * 
     * 
     * @return the int minor status
     */
    public int getMinorStatus(){
        return minorStatus;
    }

    /**
     * Retrieves a string explaining the minor status code.
     *
     * <p>
     *  检索解释次要状态代码的字符串。
     * 
     * 
     * @return a String corresponding to the minor status
     * code. <code>null</code> will be returned when no minor status code
     * has been set.
     */
    public String getMinorString(){
        return minorString;
    }

    /**
     * This method sets the state for the supplementary information flags
     * and the minor status in MessageProp.  It is not used by the
     * application but by the GSS implementation to return this information
     * to the caller of a per-message context method.
     *
     * <p>
     *  此方法在MessageProp中设置补充信息标志和次要状态的状态。它不由应用程序使用,而是由GSS实现将此信息返回给每个消息上下文方法的调用者。
     * 
     * 
     * @param duplicate true if the token was a duplicate of an earlier
     * token, false otherwise
     * @param old true if the token's validity period has expired, false
     * otherwise
     * @param unseq true if a later token has already been processed, false
     * otherwise
     * @param gap true if one or more predecessor tokens have not yet been
     * successfully processed, false otherwise
     * @param minorStatus the int minor status code for the per-message
     * operation
     * @param  minorString the textual representation of the minorStatus value
     */
   public void setSupplementaryStates(boolean duplicate,
                  boolean old, boolean unseq, boolean gap,
                  int minorStatus, String minorString) {
       this.dupToken = duplicate;
       this.oldToken = old;
       this.unseqToken = unseq;
       this.gapToken = gap;
       this.minorStatus = minorStatus;
       this.minorString = minorString;
    }

    /**
     * Resets the supplementary status values to false.
     * <p>
     *  将补充状态值重置为false。
     */
    private void resetStatusValues() {
        dupToken = false;
        oldToken = false;
        unseqToken = false;
        gapToken = false;
        minorStatus = 0;
        minorString = null;
    }
}

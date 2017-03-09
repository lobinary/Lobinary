/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth.callback;

/**
 * <p> Underlying security services instantiate and pass a
 * {@code PasswordCallback} to the {@code handle}
 * method of a {@code CallbackHandler} to retrieve password information.
 *
 * <p>
 *  <p>基础安全服务会实例化一个{@code PasswordCallback}并传递给{@code CallbackHandler}的{@code handle}方法,以检索密码信息。
 * 
 * 
 * @see javax.security.auth.callback.CallbackHandler
 */
public class PasswordCallback implements Callback, java.io.Serializable {

    private static final long serialVersionUID = 2267422647454909926L;

    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private String prompt;
    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private boolean echoOn;
    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private char[] inputPassword;

    /**
     * Construct a {@code PasswordCallback} with a prompt
     * and a boolean specifying whether the password should be displayed
     * as it is being typed.
     *
     * <p>
     *
     * <p>
     *  使用提示和一个布尔值构造{@code PasswordCallback},指定是否应该在输入时显示密码。
     * 
     * <p>
     * 
     * 
     * @param prompt the prompt used to request the password. <p>
     *
     * @param echoOn true if the password should be displayed
     *                  as it is being typed.
     *
     * @exception IllegalArgumentException if {@code prompt} is null or
     *                  if {@code prompt} has a length of 0.
     */
    public PasswordCallback(String prompt, boolean echoOn) {
        if (prompt == null || prompt.length() == 0)
            throw new IllegalArgumentException();

        this.prompt = prompt;
        this.echoOn = echoOn;
    }

    /**
     * Get the prompt.
     *
     * <p>
     *
     * <p>
     *  获取提示。
     * 
     * <p>
     * 
     * 
     * @return the prompt.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Return whether the password
     * should be displayed as it is being typed.
     *
     * <p>
     *
     * <p>
     *  返回是否应该在输入时显示密码。
     * 
     * <p>
     * 
     * 
     * @return the whether the password
     *          should be displayed as it is being typed.
     */
    public boolean isEchoOn() {
        return echoOn;
    }

    /**
     * Set the retrieved password.
     *
     * <p> This method makes a copy of the input <i>password</i>
     * before storing it.
     *
     * <p>
     *
     * <p>
     *  设置检索的密码。
     * 
     *  <p>此方法会在存储输入<i>密码</i>之前复制该输入。
     * 
     * <p>
     * 
     * 
     * @param password the retrieved password, which may be null.
     *
     * @see #getPassword
     */
    public void setPassword(char[] password) {
        this.inputPassword = (password == null ? null : password.clone());
    }

    /**
     * Get the retrieved password.
     *
     * <p> This method returns a copy of the retrieved password.
     *
     * <p>
     *
     * <p>
     *  获取检索的密码。
     * 
     *  <p>此方法返回检索的密码的副本。
     * 
     * <p>
     * 
     * @return the retrieved password, which may be null.
     *
     * @see #setPassword
     */
    public char[] getPassword() {
        return (inputPassword == null ? null : inputPassword.clone());
    }

    /**
     * Clear the retrieved password.
     * <p>
     * 
     */
    public void clearPassword() {
        if (inputPassword != null) {
            for (int i = 0; i < inputPassword.length; i++)
                inputPassword[i] = ' ';
        }
    }
}

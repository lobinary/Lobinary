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
 * {@code TextInputCallback} to the {@code handle}
 * method of a {@code CallbackHandler} to retrieve generic text
 * information.
 *
 * <p>
 *  <p>基础安全服务会实例化一个{@code TextInputCallback}并传递给{@code CallbackHandler}的{@code handle}方法,以检索通用文字信息。
 * 
 * 
 * @see javax.security.auth.callback.CallbackHandler
 */
public class TextInputCallback implements Callback, java.io.Serializable {

    private static final long serialVersionUID = -8064222478852811804L;

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
    private String defaultText;
    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private String inputText;

    /**
     * Construct a {@code TextInputCallback} with a prompt.
     *
     * <p>
     *
     * <p>
     *  使用提示构造一个{@code TextInputCallback}。
     * 
     * <p>
     * 
     * 
     * @param prompt the prompt used to request the information.
     *
     * @exception IllegalArgumentException if {@code prompt} is null
     *                  or if {@code prompt} has a length of 0.
     */
    public TextInputCallback(String prompt) {
        if (prompt == null || prompt.length() == 0)
            throw new IllegalArgumentException();
        this.prompt = prompt;
    }

    /**
     * Construct a {@code TextInputCallback} with a prompt
     * and default input value.
     *
     * <p>
     *
     * <p>
     *  使用提示和默认输入值构造{@code TextInputCallback}。
     * 
     * <p>
     * 
     * 
     * @param prompt the prompt used to request the information. <p>
     *
     * @param defaultText the text to be used as the default text displayed
     *                  with the prompt.
     *
     * @exception IllegalArgumentException if {@code prompt} is null,
     *                  if {@code prompt} has a length of 0,
     *                  if {@code defaultText} is null
     *                  or if {@code defaultText} has a length of 0.
     */
    public TextInputCallback(String prompt, String defaultText) {
        if (prompt == null || prompt.length() == 0 ||
            defaultText == null || defaultText.length() == 0)
            throw new IllegalArgumentException();

        this.prompt = prompt;
        this.defaultText = defaultText;
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
     * Get the default text.
     *
     * <p>
     *
     * <p>
     *  获取默认文本。
     * 
     * <p>
     * 
     * 
     * @return the default text, or null if this {@code TextInputCallback}
     *          was not instantiated with {@code defaultText}.
     */
    public String getDefaultText() {
        return defaultText;
    }

    /**
     * Set the retrieved text.
     *
     * <p>
     *
     * <p>
     *  设置检索的文本。
     * 
     * <p>
     * 
     * 
     * @param text the retrieved text, which may be null.
     *
     * @see #getText
     */
    public void setText(String text) {
        this.inputText = text;
    }

    /**
     * Get the retrieved text.
     *
     * <p>
     *
     * <p>
     *  获取检索到的文本。
     * 
     * 
     * @return the retrieved text, which may be null.
     *
     * @see #setText
     */
    public String getText() {
        return inputText;
    }
}

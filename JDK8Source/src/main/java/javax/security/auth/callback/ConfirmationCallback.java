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
 * {@code ConfirmationCallback} to the {@code handle}
 * method of a {@code CallbackHandler} to ask for YES/NO,
 * OK/CANCEL, YES/NO/CANCEL or other similar confirmations.
 *
 * <p>
 *  <p>基础安全服务会实例化{@code ConfirmationCallback}并传送给{@code CallbackHandler}的{@code handle}方法,要求提供YES / NO,O
 * K / CANCEL,YES / NO / CANCEL或其他类似确认。
 * 
 * 
 * @see javax.security.auth.callback.CallbackHandler
 */
public class ConfirmationCallback implements Callback, java.io.Serializable {

    private static final long serialVersionUID = -9095656433782481624L;

    /**
     * Unspecified option type.
     *
     * <p> The {@code getOptionType} method returns this
     * value if this {@code ConfirmationCallback} was instantiated
     * with {@code options} instead of an {@code optionType}.
     * <p>
     *  未指定的选项类型。
     * 
     *  <p>如果{@code ConfirmationCallback}使用{@code options}而不是{@code optionType}实例化,{@code getOptionType}方法会返
     * 回此值。
     * 
     */
    public static final int UNSPECIFIED_OPTION          = -1;

    /**
     * YES/NO confirmation option.
     *
     * <p> An underlying security service specifies this as the
     * {@code optionType} to a {@code ConfirmationCallback}
     * constructor if it requires a confirmation which can be answered
     * with either {@code YES} or {@code NO}.
     * <p>
     *  是/否确认选项。
     * 
     *  <p>如果底层安全服务需要可以使用{@code YES}或{@code NO}来回答的确认,那么底层安全服务会将其指定为{@code ConfirmationCallback}构造函数的{@code optionType}
     * 。
     * 
     */
    public static final int YES_NO_OPTION               = 0;

    /**
     * YES/NO/CANCEL confirmation confirmation option.
     *
     * <p> An underlying security service specifies this as the
     * {@code optionType} to a {@code ConfirmationCallback}
     * constructor if it requires a confirmation which can be answered
     * with either {@code YES}, {@code NO} or {@code CANCEL}.
     * <p>
     *  YES / NO / CANCEL确认确认选项。
     * 
     *  <p>如果底层安全服务需要可以使用{@code YES},{@code NO}或{@code}来回答的确认,那么底层安全服务会将其指定为{@code ConfirmationCallback}构造函数
     * 的{@code optionType}取消}。
     * 
     */
    public static final int YES_NO_CANCEL_OPTION        = 1;

    /**
     * OK/CANCEL confirmation confirmation option.
     *
     * <p> An underlying security service specifies this as the
     * {@code optionType} to a {@code ConfirmationCallback}
     * constructor if it requires a confirmation which can be answered
     * with either {@code OK} or {@code CANCEL}.
     * <p>
     *  确定/取消确认确认选项。
     * 
     *  <p>如果底层安全服务要求可以使用{@code OK}或{@code CANCEL}应答的确认,则此底层安全服务会将其指定为{@code ConfirmationCallback}构造函数的{@code optionType}
     * 。
     * 
     */
    public static final int OK_CANCEL_OPTION            = 2;

    /**
     * YES option.
     *
     * <p> If an {@code optionType} was specified to this
     * {@code ConfirmationCallback}, this option may be specified as a
     * {@code defaultOption} or returned as the selected index.
     * <p>
     *  YES选项。
     * 
     * <p>如果为此{@code ConfirmationCallback}指定了{@code optionType},则此选项可以指定为{@code defaultOption}或返回为所选索引。
     * 
     */
    public static final int YES                         = 0;

    /**
     * NO option.
     *
     * <p> If an {@code optionType} was specified to this
     * {@code ConfirmationCallback}, this option may be specified as a
     * {@code defaultOption} or returned as the selected index.
     * <p>
     *  NO选项。
     * 
     *  <p>如果为此{@code ConfirmationCallback}指定了{@code optionType},则此选项可以指定为{@code defaultOption}或返回为所选索引。
     * 
     */
    public static final int NO                          = 1;

    /**
     * CANCEL option.
     *
     * <p> If an {@code optionType} was specified to this
     * {@code ConfirmationCallback}, this option may be specified as a
     * {@code defaultOption} or returned as the selected index.
     * <p>
     *  CANCEL选项。
     * 
     *  <p>如果为此{@code ConfirmationCallback}指定了{@code optionType},则此选项可以指定为{@code defaultOption}或返回为所选索引。
     * 
     */
    public static final int CANCEL                      = 2;

    /**
     * OK option.
     *
     * <p> If an {@code optionType} was specified to this
     * {@code ConfirmationCallback}, this option may be specified as a
     * {@code defaultOption} or returned as the selected index.
     * <p>
     *  确定选项。
     * 
     *  <p>如果为此{@code ConfirmationCallback}指定了{@code optionType},则此选项可以指定为{@code defaultOption}或返回为所选索引。
     * 
     */
    public static final int OK                          = 3;

    /** INFORMATION message type.  */
    public static final int INFORMATION                 = 0;

    /** WARNING message type. */
    public static final int WARNING                     = 1;

    /** ERROR message type. */
    public static final int ERROR                       = 2;
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
    private int messageType;
    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private int optionType = UNSPECIFIED_OPTION;
    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private int defaultOption;
    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private String[] options;
    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private int selection;

    /**
     * Construct a {@code ConfirmationCallback} with a
     * message type, an option type and a default option.
     *
     * <p> Underlying security services use this constructor if
     * they require either a YES/NO, YES/NO/CANCEL or OK/CANCEL
     * confirmation.
     *
     * <p>
     *
     * <p>
     *  使用消息类型,选项类型和默认选项构造{@code ConfirmationCallback}。
     * 
     *  <p>如果基础安全服务需要YES / NO,YES / NO / CANCEL或OK / CANCEL确认,则使用此构造函数。
     * 
     * <p>
     * 
     * 
     * @param messageType the message type ({@code INFORMATION},
     *                  {@code WARNING} or {@code ERROR}). <p>
     *
     * @param optionType the option type ({@code YES_NO_OPTION},
     *                  {@code YES_NO_CANCEL_OPTION} or
     *                  {@code OK_CANCEL_OPTION}). <p>
     *
     * @param defaultOption the default option
     *                  from the provided optionType ({@code YES},
     *                  {@code NO}, {@code CANCEL} or
     *                  {@code OK}).
     *
     * @exception IllegalArgumentException if messageType is not either
     *                  {@code INFORMATION}, {@code WARNING},
     *                  or {@code ERROR}, if optionType is not either
     *                  {@code YES_NO_OPTION},
     *                  {@code YES_NO_CANCEL_OPTION}, or
     *                  {@code OK_CANCEL_OPTION},
     *                  or if {@code defaultOption}
     *                  does not correspond to one of the options in
     *                  {@code optionType}.
     */
    public ConfirmationCallback(int messageType,
                int optionType, int defaultOption) {

        if (messageType < INFORMATION || messageType > ERROR ||
            optionType < YES_NO_OPTION || optionType > OK_CANCEL_OPTION)
            throw new IllegalArgumentException();

        switch (optionType) {
        case YES_NO_OPTION:
            if (defaultOption != YES && defaultOption != NO)
                throw new IllegalArgumentException();
            break;
        case YES_NO_CANCEL_OPTION:
            if (defaultOption != YES && defaultOption != NO &&
                defaultOption != CANCEL)
                throw new IllegalArgumentException();
            break;
        case OK_CANCEL_OPTION:
            if (defaultOption != OK && defaultOption != CANCEL)
                throw new IllegalArgumentException();
            break;
        }

        this.messageType = messageType;
        this.optionType = optionType;
        this.defaultOption = defaultOption;
    }

    /**
     * Construct a {@code ConfirmationCallback} with a
     * message type, a list of options and a default option.
     *
     * <p> Underlying security services use this constructor if
     * they require a confirmation different from the available preset
     * confirmations provided (for example, CONTINUE/ABORT or STOP/GO).
     * The confirmation options are listed in the {@code options} array,
     * and are displayed by the {@code CallbackHandler} implementation
     * in a manner consistent with the way preset options are displayed.
     *
     * <p>
     *
     * <p>
     *  使用消息类型,选项列表和默认选项构造{@code ConfirmationCallback}。
     * 
     *  <p>如果基础安全服务需要与提供的可用预设确认(例如,CONTINUE / ABORT或STOP / GO)不同的确认,则使用此构造函数。
     * 确认选项列在{@code options}数组中,并由{@code CallbackHandler}实现以与显示预设选项的方式一致的方式显示。
     * 
     * <p>
     * 
     * 
     * @param messageType the message type ({@code INFORMATION},
     *                  {@code WARNING} or {@code ERROR}). <p>
     *
     * @param options the list of confirmation options. <p>
     *
     * @param defaultOption the default option, represented as an index
     *                  into the {@code options} array.
     *
     * @exception IllegalArgumentException if messageType is not either
     *                  {@code INFORMATION}, {@code WARNING},
     *                  or {@code ERROR}, if {@code options} is null,
     *                  if {@code options} has a length of 0,
     *                  if any element from {@code options} is null,
     *                  if any element from {@code options}
     *                  has a length of 0, or if {@code defaultOption}
     *                  does not lie within the array boundaries of
     *                  {@code options}.
     */
    public ConfirmationCallback(int messageType,
                String[] options, int defaultOption) {

        if (messageType < INFORMATION || messageType > ERROR ||
            options == null || options.length == 0 ||
            defaultOption < 0 || defaultOption >= options.length)
            throw new IllegalArgumentException();

        for (int i = 0; i < options.length; i++) {
            if (options[i] == null || options[i].length() == 0)
                throw new IllegalArgumentException();
        }

        this.messageType = messageType;
        this.options = options;
        this.defaultOption = defaultOption;
    }

    /**
     * Construct a {@code ConfirmationCallback} with a prompt,
     * message type, an option type and a default option.
     *
     * <p> Underlying security services use this constructor if
     * they require either a YES/NO, YES/NO/CANCEL or OK/CANCEL
     * confirmation.
     *
     * <p>
     *
     * <p>
     * 使用提示,消息类型,选项类型和默认选项构造{@code ConfirmationCallback}。
     * 
     *  <p>如果基础安全服务需要YES / NO,YES / NO / CANCEL或OK / CANCEL确认,则使用此构造函数。
     * 
     * <p>
     * 
     * 
     * @param prompt the prompt used to describe the list of options. <p>
     *
     * @param messageType the message type ({@code INFORMATION},
     *                  {@code WARNING} or {@code ERROR}). <p>
     *
     * @param optionType the option type ({@code YES_NO_OPTION},
     *                  {@code YES_NO_CANCEL_OPTION} or
     *                  {@code OK_CANCEL_OPTION}). <p>
     *
     * @param defaultOption the default option
     *                  from the provided optionType ({@code YES},
     *                  {@code NO}, {@code CANCEL} or
     *                  {@code OK}).
     *
     * @exception IllegalArgumentException if {@code prompt} is null,
     *                  if {@code prompt} has a length of 0,
     *                  if messageType is not either
     *                  {@code INFORMATION}, {@code WARNING},
     *                  or {@code ERROR}, if optionType is not either
     *                  {@code YES_NO_OPTION},
     *                  {@code YES_NO_CANCEL_OPTION}, or
     *                  {@code OK_CANCEL_OPTION},
     *                  or if {@code defaultOption}
     *                  does not correspond to one of the options in
     *                  {@code optionType}.
     */
    public ConfirmationCallback(String prompt, int messageType,
                int optionType, int defaultOption) {

        if (prompt == null || prompt.length() == 0 ||
            messageType < INFORMATION || messageType > ERROR ||
            optionType < YES_NO_OPTION || optionType > OK_CANCEL_OPTION)
            throw new IllegalArgumentException();

        switch (optionType) {
        case YES_NO_OPTION:
            if (defaultOption != YES && defaultOption != NO)
                throw new IllegalArgumentException();
            break;
        case YES_NO_CANCEL_OPTION:
            if (defaultOption != YES && defaultOption != NO &&
                defaultOption != CANCEL)
                throw new IllegalArgumentException();
            break;
        case OK_CANCEL_OPTION:
            if (defaultOption != OK && defaultOption != CANCEL)
                throw new IllegalArgumentException();
            break;
        }

        this.prompt = prompt;
        this.messageType = messageType;
        this.optionType = optionType;
        this.defaultOption = defaultOption;
    }

    /**
     * Construct a {@code ConfirmationCallback} with a prompt,
     * message type, a list of options and a default option.
     *
     * <p> Underlying security services use this constructor if
     * they require a confirmation different from the available preset
     * confirmations provided (for example, CONTINUE/ABORT or STOP/GO).
     * The confirmation options are listed in the {@code options} array,
     * and are displayed by the {@code CallbackHandler} implementation
     * in a manner consistent with the way preset options are displayed.
     *
     * <p>
     *
     * <p>
     *  使用提示,消息类型,选项列表和默认选项构造{@code ConfirmationCallback}。
     * 
     *  <p>如果基础安全服务需要与提供的可用预设确认(例如,CONTINUE / ABORT或STOP / GO)不同的确认,则使用此构造函数。
     * 确认选项列在{@code options}数组中,并由{@code CallbackHandler}实现以与显示预设选项的方式一致的方式显示。
     * 
     * <p>
     * 
     * 
     * @param prompt the prompt used to describe the list of options. <p>
     *
     * @param messageType the message type ({@code INFORMATION},
     *                  {@code WARNING} or {@code ERROR}). <p>
     *
     * @param options the list of confirmation options. <p>
     *
     * @param defaultOption the default option, represented as an index
     *                  into the {@code options} array.
     *
     * @exception IllegalArgumentException if {@code prompt} is null,
     *                  if {@code prompt} has a length of 0,
     *                  if messageType is not either
     *                  {@code INFORMATION}, {@code WARNING},
     *                  or {@code ERROR}, if {@code options} is null,
     *                  if {@code options} has a length of 0,
     *                  if any element from {@code options} is null,
     *                  if any element from {@code options}
     *                  has a length of 0, or if {@code defaultOption}
     *                  does not lie within the array boundaries of
     *                  {@code options}.
     */
    public ConfirmationCallback(String prompt, int messageType,
                String[] options, int defaultOption) {

        if (prompt == null || prompt.length() == 0 ||
            messageType < INFORMATION || messageType > ERROR ||
            options == null || options.length == 0 ||
            defaultOption < 0 || defaultOption >= options.length)
            throw new IllegalArgumentException();

        for (int i = 0; i < options.length; i++) {
            if (options[i] == null || options[i].length() == 0)
                throw new IllegalArgumentException();
        }

        this.prompt = prompt;
        this.messageType = messageType;
        this.options = options;
        this.defaultOption = defaultOption;
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
     * @return the prompt, or null if this {@code ConfirmationCallback}
     *          was instantiated without a {@code prompt}.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Get the message type.
     *
     * <p>
     *
     * <p>
     *  获取消息类型。
     * 
     * <p>
     * 
     * 
     * @return the message type ({@code INFORMATION},
     *          {@code WARNING} or {@code ERROR}).
     */
    public int getMessageType() {
        return messageType;
    }

    /**
     * Get the option type.
     *
     * <p> If this method returns {@code UNSPECIFIED_OPTION}, then this
     * {@code ConfirmationCallback} was instantiated with
     * {@code options} instead of an {@code optionType}.
     * In this case, invoke the {@code getOptions} method
     * to determine which confirmation options to display.
     *
     * <p>
     *
     * <p>
     *  获取选项类型。
     * 
     *  <p>如果此方法返回{@code UNSPECIFIED_OPTION},则此{@code ConfirmationCallback}将使用{@code options}而不是{@code optionType}
     * 进行实例化。
     * 在这种情况下,调用{@code getOptions}方法来确定要显示哪些确认选项。
     * 
     * <p>
     * 
     * 
     * @return the option type ({@code YES_NO_OPTION},
     *          {@code YES_NO_CANCEL_OPTION} or
     *          {@code OK_CANCEL_OPTION}), or
     *          {@code UNSPECIFIED_OPTION} if this
     *          {@code ConfirmationCallback} was instantiated with
     *          {@code options} instead of an {@code optionType}.
     */
    public int getOptionType() {
        return optionType;
    }

    /**
     * Get the confirmation options.
     *
     * <p>
     *
     * <p>
     *  获取确认选项。
     * 
     * <p>
     * 
     * 
     * @return the list of confirmation options, or null if this
     *          {@code ConfirmationCallback} was instantiated with
     *          an {@code optionType} instead of {@code options}.
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Get the default option.
     *
     * <p>
     *
     * <p>
     *  获取默认选项。
     * 
     * <p>
     * 
     * 
     * @return the default option, represented as
     *          {@code YES}, {@code NO}, {@code OK} or
     *          {@code CANCEL} if an {@code optionType}
     *          was specified to the constructor of this
     *          {@code ConfirmationCallback}.
     *          Otherwise, this method returns the default option as
     *          an index into the
     *          {@code options} array specified to the constructor
     *          of this {@code ConfirmationCallback}.
     */
    public int getDefaultOption() {
        return defaultOption;
    }

    /**
     * Set the selected confirmation option.
     *
     * <p>
     *
     * <p>
     *  设置所选的确认选项。
     * 
     * <p>
     * 
     * 
     * @param selection the selection represented as {@code YES},
     *          {@code NO}, {@code OK} or {@code CANCEL}
     *          if an {@code optionType} was specified to the constructor
     *          of this {@code ConfirmationCallback}.
     *          Otherwise, the selection represents the index into the
     *          {@code options} array specified to the constructor
     *          of this {@code ConfirmationCallback}.
     *
     * @see #getSelectedIndex
     */
    public void setSelectedIndex(int selection) {
        this.selection = selection;
    }

    /**
     * Get the selected confirmation option.
     *
     * <p>
     *
     * <p>
     *  获取所选的确认选项。
     * 
     * 
     * @return the selected confirmation option represented as
     *          {@code YES}, {@code NO}, {@code OK} or
     *          {@code CANCEL} if an {@code optionType}
     *          was specified to the constructor of this
     *          {@code ConfirmationCallback}.
     *          Otherwise, this method returns the selected confirmation
     *          option as an index into the
     *          {@code options} array specified to the constructor
     *          of this {@code ConfirmationCallback}.
     *
     * @see #setSelectedIndex
     */
    public int getSelectedIndex() {
        return selection;
    }
}

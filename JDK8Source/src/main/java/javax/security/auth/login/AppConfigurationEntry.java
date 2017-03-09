/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth.login;

import java.util.Map;
import java.util.Collections;

/**
 * This class represents a single {@code LoginModule} entry
 * configured for the application specified in the
 * {@code getAppConfigurationEntry(String appName)}
 * method in the {@code Configuration} class.  Each respective
 * {@code AppConfigurationEntry} contains a {@code LoginModule} name,
 * a control flag (specifying whether this {@code LoginModule} is
 * REQUIRED, REQUISITE, SUFFICIENT, or OPTIONAL), and LoginModule-specific
 * options.  Please refer to the {@code Configuration} class for
 * more information on the different control flags and their semantics.
 *
 * <p>
 *  此类表示为{@code Configuration}类中的{@code getAppConfigurationEntry(String appName)}方法中指定的应用程序配置的一个{@code LoginModule}
 * 条目。
 * 每个相应的{@code AppConfigurationEntry}包含{@code LoginModule}名称,控制标志(指定此{@code LoginModule}是必需的,要求的,足够的还是可选
 * 的)以及特定于LoginModule的选项。
 * 有关不同控制标志及其语义的更多信息,请参阅{@code Configuration}类。
 * 
 * 
 * @see javax.security.auth.login.Configuration
 */
public class AppConfigurationEntry {

    private String loginModuleName;
    private LoginModuleControlFlag controlFlag;
    private Map<String,?> options;

    /**
     * Default constructor for this class.
     *
     * <p> This entry represents a single {@code LoginModule}
     * entry configured for the application specified in the
     * {@code getAppConfigurationEntry(String appName)}
     * method from the {@code Configuration} class.
     *
     * <p>
     *  此类的默认构造函数。
     * 
     *  <p>此条目表示为{@code Configuration}类中的{@code getAppConfigurationEntry(String appName)}方法中指定的应用程序配置的一个{@code LoginModule}
     * 条目。
     * 
     * 
     * @param loginModuleName String representing the class name of the
     *                  {@code LoginModule} configured for the
     *                  specified application. <p>
     *
     * @param controlFlag either REQUIRED, REQUISITE, SUFFICIENT,
     *                  or OPTIONAL. <p>
     *
     * @param options the options configured for this {@code LoginModule}.
     *
     * @exception IllegalArgumentException if {@code loginModuleName}
     *                  is null, if {@code LoginModuleName}
     *                  has a length of 0, if {@code controlFlag}
     *                  is not either REQUIRED, REQUISITE, SUFFICIENT
     *                  or OPTIONAL, or if {@code options} is null.
     */
    public AppConfigurationEntry(String loginModuleName,
                                LoginModuleControlFlag controlFlag,
                                Map<String,?> options)
    {
        if (loginModuleName == null || loginModuleName.length() == 0 ||
            (controlFlag != LoginModuleControlFlag.REQUIRED &&
                controlFlag != LoginModuleControlFlag.REQUISITE &&
                controlFlag != LoginModuleControlFlag.SUFFICIENT &&
                controlFlag != LoginModuleControlFlag.OPTIONAL) ||
            options == null)
            throw new IllegalArgumentException();

        this.loginModuleName = loginModuleName;
        this.controlFlag = controlFlag;
        this.options = Collections.unmodifiableMap(options);
    }

    /**
     * Get the class name of the configured {@code LoginModule}.
     *
     * <p>
     *  获取配置的{@code LoginModule}的类名。
     * 
     * 
     * @return the class name of the configured {@code LoginModule} as
     *          a String.
     */
    public String getLoginModuleName() {
        return loginModuleName;
    }

    /**
     * Return the controlFlag
     * (either REQUIRED, REQUISITE, SUFFICIENT, or OPTIONAL)
     * for this {@code LoginModule}.
     *
     * <p>
     *  返回此{@code LoginModule}的controlFlag(要么是REQUIRED,REQUISITE,SUFFICIENT或OPTIONAL)。
     * 
     * 
     * @return the controlFlag
     *          (either REQUIRED, REQUISITE, SUFFICIENT, or OPTIONAL)
     *          for this {@code LoginModule}.
     */
    public LoginModuleControlFlag getControlFlag() {
        return controlFlag;
    }

    /**
     * Get the options configured for this {@code LoginModule}.
     *
     * <p>
     *  获取为此{@code LoginModule}配置的选项。
     * 
     * 
     * @return the options configured for this {@code LoginModule}
     *          as an unmodifiable {@code Map}.
     */
    public Map<String,?> getOptions() {
        return options;
    }

    /**
     * This class represents whether or not a {@code LoginModule}
     * is REQUIRED, REQUISITE, SUFFICIENT or OPTIONAL.
     * <p>
     *  此类表示{@code LoginModule}是否必需,必需,足够或可选。
     * 
     */
    public static class LoginModuleControlFlag {

        private String controlFlag;

        /**
         * Required {@code LoginModule}.
         * <p>
         *  必需{@code LoginModule}。
         * 
         */
        public static final LoginModuleControlFlag REQUIRED =
                                new LoginModuleControlFlag("required");

        /**
         * Requisite {@code LoginModule}.
         * <p>
         *  必需{@code LoginModule}。
         * 
         */
        public static final LoginModuleControlFlag REQUISITE =
                                new LoginModuleControlFlag("requisite");

        /**
         * Sufficient {@code LoginModule}.
         * <p>
         *  足够{@code LoginModule}。
         * 
         */
        public static final LoginModuleControlFlag SUFFICIENT =
                                new LoginModuleControlFlag("sufficient");

        /**
         * Optional {@code LoginModule}.
         * <p>
         *  可选{@code LoginModule}。
         * 
         */
        public static final LoginModuleControlFlag OPTIONAL =
                                new LoginModuleControlFlag("optional");

        private LoginModuleControlFlag(String controlFlag) {
            this.controlFlag = controlFlag;
        }

        /**
         * Return a String representation of this controlFlag.
         *
         * <p> The String has the format, "LoginModuleControlFlag: <i>flag</i>",
         * where <i>flag</i> is either <i>required</i>, <i>requisite</i>,
         * <i>sufficient</i>, or <i>optional</i>.
         *
         * <p>
         *  返回此controlFlag的String表示形式。
         * 
         * 
         * @return a String representation of this controlFlag.
         */
        public String toString() {
            return (sun.security.util.ResourcesMgr.getString
                ("LoginModuleControlFlag.") + controlFlag);
        }
    }
}

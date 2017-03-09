/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.tools;

/**
 * Interface for recognizing options.
 *
 * <p>
 *  识别选项的界面。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface OptionChecker {

    /**
     * Determines if the given option is supported and if so, the
     * number of arguments the option takes.
     *
     * <p>
     *  确定是否支持给定选项,如果支持,则选项所需的参数数。
     * 
     * @param option an option
     * @return the number of arguments the given option takes or -1 if
     * the option is not supported
     */
    int isSupportedOption(String option);

}

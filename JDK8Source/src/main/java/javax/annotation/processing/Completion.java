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

package javax.annotation.processing;

/**
 * A suggested {@linkplain Processor#getCompletions <em>completion</em>} for an
 * annotation.  A completion is text meant to be inserted into a
 * program as part of an annotation.
 *
 * <p>
 *  建议的{@linkplain Processor#getCompletions <em>完成</em>}注释。完成是意在作为注释的一部分插入到程序中的文本。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface Completion {

    /**
     * Returns the text of the suggested completion.
     * <p>
     *  返回建议完成的文本。
     * 
     * 
     * @return the text of the suggested completion.
     */
    String getValue();

    /**
     * Returns an informative message about the completion.
     * <p>
     *  返回有关完成的信息性消息。
     * 
     * @return an informative message about the completion.
     */
    String getMessage();
}

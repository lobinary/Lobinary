/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
 * $Id: KeySelectorResult.java,v 1.3 2005/05/10 15:47:42 mullan Exp $
 * <p>
 *  $ Id：KeySelectorResult.java,v 1.3 2005/05/10 15:47:42 mullan Exp $
 * 
 */
package javax.xml.crypto;

import java.security.Key;

/**
 * The result returned by the {@link KeySelector#select KeySelector.select}
 * method.
 * <p>
 * At a minimum, a <code>KeySelectorResult</code> contains the <code>Key</code>
 * selected by the <code>KeySelector</code>. Implementations of this interface
 * may add methods to return implementation or algorithm specific information,
 * such as a chain of certificates or debugging information.
 *
 * <p>
 *  由{@link KeySelector#select KeySelector.select}方法返回的结果。
 * <p>
 *  至少,<code> KeySelectorResult </code>包含由<code> KeySelector </code>选择的<code> Key </code>。
 * 该接口的实现可以添加返回实现或算法特定信息的方法,诸如证书链或调试信息。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see KeySelector
 */
public interface KeySelectorResult {

    /**
     * Returns the selected key.
     *
     * <p>
     * 
     * 
     * @return the selected key, or <code>null</code> if none can be found
     */
    Key getKey();
}

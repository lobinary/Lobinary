/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

/**
 * Defines access modes used to test the accessibility of a file.
 *
 * <p>
 *  定义用于测试文件可访问性的访问模式。
 * 
 * 
 * @since 1.7
 */

public enum AccessMode {
    /**
     * Test read access.
     * <p>
     *  测试读取访问。
     * 
     */
    READ,
    /**
     * Test write access.
     * <p>
     *  测试写访问。
     * 
     */
    WRITE,
    /**
     * Test execute access.
     * <p>
     *  测试执行访问。
     */
    EXECUTE;
}

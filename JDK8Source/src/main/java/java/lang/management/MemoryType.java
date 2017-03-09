/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.management;

/**
 * Types of {@link MemoryPoolMXBean memory pools}.
 *
 * <p>
 *  {@link MemoryPoolMXBean内存池}的类型。
 * 
 * 
 * @author  Mandy Chung
 * @since   1.5
 */
public enum MemoryType {

    /**
     * Heap memory type.
     * <p>
     * The Java virtual machine has a <i>heap</i>
     * that is the runtime data area from which
     * memory for all class instances and arrays are allocated.
     * <p>
     *  堆内存类型。
     * <p>
     *  Java虚拟机有一个<i>堆</i>,它是运行时数据区,从中分配所有类实例和数组的内存。
     * 
     */
    HEAP("Heap memory"),

    /**
     * Non-heap memory type.
     * <p>
     * The Java virtual machine manages memory other than the heap
     * (referred as <i>non-heap memory</i>).  The non-heap memory includes
     * the <i>method area</i> and memory required for the internal
     * processing or optimization for the Java virtual machine.
     * It stores per-class structures such as a runtime
     * constant pool, field and method data, and the code for
     * methods and constructors.
     * <p>
     *  非堆内存类型。
     * <p>
     *  Java虚拟机管理除了堆之外的存储器(称为非堆存储器</i>)。非堆存储器包括用于Java虚拟机的内部处理或优化所需的<i>方法区域和存储器。
     * 它存储每类结构,例如运行时常量池,字段和方法数据,以及方法和构造函数的代码。
     */
    NON_HEAP("Non-heap memory");

    private final String description;

    private MemoryType(String s) {
        this.description = s;
    }

    /**
     * Returns the string representation of this <tt>MemoryType</tt>.
     * <p>
     * 
     * 
     * @return the string representation of this <tt>MemoryType</tt>.
     */
    public String toString() {
        return description;
    }

    private static final long serialVersionUID = 6992337162326171013L;
}

/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing;

import sun.awt.AWTAccessor;

/**
 * An enumeration for keys used as client properties within the Swing
 * implementation.
 * <p>
 * This enum holds only a small subset of the keys currently used within Swing,
 * but we may move more of them here in the future.
 * <p>
 * Adding an item to, and using, this class instead of {@code String} for
 * client properties protects against conflicts with developer-set client
 * properties. Using this class also avoids a problem with {@code StringBuilder}
 * and {@code StringBuffer} keys, whereby the keys are not recognized upon
 * deserialization.
 * <p>
 * When a client property value associated with one of these keys does not
 * implement {@code Serializable}, the result during serialization depends
 * on how the key is defined here. Historically, client properties with values
 * not implementing {@code Serializable} have simply been dropped and left out
 * of the serialized representation. To define keys with such behavior in this
 * enum, provide a value of {@code false} for the {@code reportValueNotSerializable}
 * property. When migrating existing properties to this enum, one may wish to
 * consider using this by default, to preserve backward compatibility.
 * <p>
 * To instead have a {@code NotSerializableException} thrown when a
 * {@code non-Serializable} property is encountered, provide the value of
 * {@code true} for the {@code reportValueNotSerializable} property. This
 * is useful when the property represents something that the developer
 * needs to know about when it cannot be serialized.
 *
 * <p>
 *  在Swing实现中用作客户端属性的键的枚举。
 * <p>
 *  这个枚举只保留了Swing中目前使用的一小部分键,但我们可能会在将来移动更多的键。
 * <p>
 *  向客户端属性添加项目并将其用于此类而不是{@code String}以防止与开发人员设置的客户端属性冲突。
 * 使用此类也避免了{@code StringBuilder}和{@code StringBuffer}键的问题,由此反序列化后无法识别键。
 * <p>
 *  当与这些键之一相关联的客户机属性值不实现{@code Serializable}时,序列化期间的结果取决于如何在此定义键。
 * 历史上,具有不实现{@code Serializable}的值的客户端属性被简单地删除并且被排除在序列化表示之外。
 * 要在此枚举中定义具有此行为的键,请为{@code reportValueNotSerializable}属性提供值{@code false}。
 * 将现有属性迁移到此枚举时,可能希望默认使用此属性,以保留向后兼容性。
 * <p>
 * 要在遇到{@code non-Serializable}属性时改为引发{@code NotSerializableException},请为{@code reportValueNotSerializable}
 * 属性提供{@code true}的值。
 * 当属性表示开发人员需要知道的什么时候不能被序列化时,这是有用的。
 * 
 * 
 * @author  Shannon Hickey
 */
enum ClientPropertyKey {

    /**
     * Key used by JComponent for storing InputVerifier.
     * <p>
     *  JComponent用于存储InputVerifier的键。
     * 
     */
    JComponent_INPUT_VERIFIER(true),

    /**
     * Key used by JComponent for storing TransferHandler.
     * <p>
     *  JComponent用于存储TransferHandler的密钥。
     * 
     */
    JComponent_TRANSFER_HANDLER(true),

    /**
     * Key used by JComponent for storing AncestorNotifier.
     * <p>
     *  JComponent用于存储AncestorNotifier的键。
     * 
     */
    JComponent_ANCESTOR_NOTIFIER(true),

    /**
     * Key used by PopupFactory to force heavy weight popups for a
     * component.
     * <p>
     *  PopupFactory使用的键强制组件的重量弹出。
     * 
     */
    PopupFactory_FORCE_HEAVYWEIGHT_POPUP(true);


    /**
     * Whether or not a {@code NotSerializableException} should be thrown
     * during serialization, when the value associated with this key does
     * not implement {@code Serializable}.
     * <p>
     *  在与此键关联的值未实现{@code Serializable}时,是否应在序列化期间抛出{@code NotSerializableException}。
     * 
     */
    private final boolean reportValueNotSerializable;

    static {
        AWTAccessor.setClientPropertyKeyAccessor(
            new AWTAccessor.ClientPropertyKeyAccessor() {
                public Object getJComponent_TRANSFER_HANDLER() {
                    return JComponent_TRANSFER_HANDLER;
                }
            });
    }

    /**
     * Constructs a key with the {@code reportValueNotSerializable} property
     * set to {@code false}.
     * <p>
     *  构造一个键,将{@code reportValueNotSerializable}属性设置为{@code false}。
     * 
     */
    private ClientPropertyKey() {
        this(false);
    }

    /**
     * Constructs a key with the {@code reportValueNotSerializable} property
     * set to the given value.
     * <p>
     *  构造一个键,将{@code reportValueNotSerializable}属性设置为给定值。
     * 
     */
    private ClientPropertyKey(boolean reportValueNotSerializable) {
        this.reportValueNotSerializable = reportValueNotSerializable;
    }

    /**
     * Returns whether or not a {@code NotSerializableException} should be thrown
     * during serialization, when the value associated with this key does
     * not implement {@code Serializable}.
     * <p>
     *  返回在与此键关联的值未实现{@code Serializable}时是否应在序列化期间抛出{@code NotSerializableException}。
     */
    public boolean getReportValueNotSerializable() {
        return reportValueNotSerializable;
    }
}

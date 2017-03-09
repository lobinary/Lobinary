/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2014, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.midi.spi;

import javax.sound.midi.MidiDevice;

/**
 * A {@code MidiDeviceProvider} is a factory or provider for a particular type
 * of MIDI device. This mechanism allows the implementation to determine how
 * resources are managed in the creation and management of a device.
 *
 * <p>
 *  {@code MidiDeviceProvider}是特定类型MIDI设备的工厂或提供商。这种机制允许实现来确定在设备的创建和管理中如何管理资源。
 * 
 * 
 * @author Kara Kytle
 */
public abstract class MidiDeviceProvider {

    /**
     * Indicates whether the device provider supports the device represented by
     * the specified device info object.
     *
     * <p>
     *  指示设备提供程序是否支持由指定设备信息对象表示的设备。
     * 
     * 
     * @param  info an info object that describes the device for which support
     *         is queried
     * @return {@code true} if the specified device is supported, otherwise
     *         {@code false}
     */
    public boolean isDeviceSupported(MidiDevice.Info info) {

        MidiDevice.Info infos[] = getDeviceInfo();

        for(int i=0; i<infos.length; i++) {
            if( info.equals( infos[i] ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtains the set of info objects representing the device or devices
     * provided by this {@code MidiDeviceProvider}.
     *
     * <p>
     *  获取表示由此{@code MidiDeviceProvider}提供的一个或多个设备的信息对象集合。
     * 
     * 
     * @return set of device info objects
     */
    public abstract MidiDevice.Info[] getDeviceInfo();

    /**
     * Obtains an instance of the device represented by the info object.
     *
     * <p>
     *  获取由info对象表示的设备的实例。
     * 
     * @param  info an info object that describes the desired device
     * @return device instance
     * @throws IllegalArgumentException if the info object specified does not
     *         match the info object for a device supported by this
     *         {@code MidiDeviceProvider}
     */
    public abstract MidiDevice getDevice(MidiDevice.Info info);
}

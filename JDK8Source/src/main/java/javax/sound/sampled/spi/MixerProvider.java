/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.sampled.spi;

import javax.sound.sampled.Mixer;

/**
 * A provider or factory for a particular mixer type.
 * This mechanism allows the implementation to determine
 * how resources are managed in creation / management of
 * a mixer.
 *
 * <p>
 *  特定搅拌器类型的提供者或工厂。该机制允许实现以确定在混合器的创建/管理中如何管理资源。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class MixerProvider {


    /**
     * Indicates whether the mixer provider supports the mixer represented by
     * the specified mixer info object.
     * <p>
     * The full set of mixer info objects that represent the mixers supported
     * by this {@code MixerProvider} may be obtained
     * through the {@code getMixerInfo} method.
     *
     * <p>
     *  指示混音器提供程序是否支持由指定的混音器信息对象表示的混音器。
     * <p>
     *  表示由此{@code MixerProvider}支持的混合器的全部混合器信息对象可以通过{@code getMixerInfo}方法获得。
     * 
     * 
     * @param info an info object that describes the mixer for which support is queried
     * @return {@code true} if the specified mixer is supported,
     *     otherwise {@code false}
     * @see #getMixerInfo()
     */
    public boolean isMixerSupported(Mixer.Info info) {

        Mixer.Info infos[] = getMixerInfo();

        for(int i=0; i<infos.length; i++){
            if( info.equals( infos[i] ) ) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtains the set of info objects representing the mixer
     * or mixers provided by this MixerProvider.
     * <p>
     * The {@code isMixerSupported} method returns {@code true}
     * for all the info objects returned by this method.
     * The corresponding mixer instances for the info objects
     * are returned by the {@code getMixer} method.
     *
     * <p>
     *  获取表示由此MixerProvider提供的混音器的一组信息对象。
     * <p>
     *  {@code isMixerSupported}方法对此方法返回的所有信息对象返回{@code true}。 info对象的相应混合器实例由{@code getMixer}方法返回。
     * 
     * 
     * @return a set of mixer info objects
     * @see #getMixer(javax.sound.sampled.Mixer.Info) getMixer(Mixer.Info)
     * @see #isMixerSupported(javax.sound.sampled.Mixer.Info) isMixerSupported(Mixer.Info)
     */
    public abstract Mixer.Info[] getMixerInfo();


    /**
     * Obtains an instance of the mixer represented by the info object.
     * <p>
     * The full set of the mixer info objects that represent the mixers
     * supported by this {@code MixerProvider} may be obtained
     * through the {@code getMixerInfo} method.
     * Use the {@code isMixerSupported} method to test whether
     * this {@code MixerProvider} supports a particular mixer.
     *
     * <p>
     *  获取由info对象表示的混合器的实例。
     * <p>
     *  表示此{@code MixerProvider}支持的混合器的混合器信息对象的全集可以通过{@code getMixerInfo}方法获得。
     * 
     * @param info an info object that describes the desired mixer
     * @return mixer instance
     * @throws IllegalArgumentException if the info object specified does not
     *     match the info object for a mixer supported by this MixerProvider.
     * @see #getMixerInfo()
     * @see #isMixerSupported(javax.sound.sampled.Mixer.Info) isMixerSupported(Mixer.Info)
     */
    public abstract Mixer getMixer(Mixer.Info info);
}

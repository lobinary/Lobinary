/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2002, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.sampled;

import java.security.BasicPermission;


/**
 * The <code>AudioPermission</code> class represents access rights to the audio
 * system resources.  An <code>AudioPermission</code> contains a target name
 * but no actions list; you either have the named permission or you don't.
 * <p>
 * The target name is the name of the audio permission (see the table below).
 * The names follow the hierarchical property-naming convention. Also, an asterisk
 * can be used to represent all the audio permissions.
 * <p>
 * The following table lists the possible <code>AudioPermission</code> target names.
 * For each name, the table provides a description of exactly what that permission
 * allows, as well as a discussion of the risks of granting code the permission.
 * <p>
 *
 * <table border=1 cellpadding=5 summary="permission target name, what the permission allows, and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 * <td>play</td>
 * <td>Audio playback through the audio device or devices on the system.
 * Allows the application to obtain and manipulate lines and mixers for
 * audio playback (rendering).</td>
 * <td>In some cases use of this permission may affect other
 * applications because the audio from one line may be mixed with other audio
 * being played on the system, or because manipulation of a mixer affects the
 * audio for all lines using that mixer.</td>
 *</tr>
 *
 * <tr>
 * <td>record</td>
 * <td>Audio recording through the audio device or devices on the system.
 * Allows the application to obtain and manipulate lines and mixers for
 * audio recording (capture).</td>
 * <td>In some cases use of this permission may affect other
 * applications because manipulation of a mixer affects the audio for all lines
 * using that mixer.
 * This permission can enable an applet or application to eavesdrop on a user.</td>
 *</tr>
 *</table>
 *<p>
 *
 * <p>
 *  <code> AudioPermission </code>类表示对音频系统资源的访问权限。
 *  <code> AudioPermission </code>包含目标名称,但没有操作列表;你有命名的权限或你不。
 * <p>
 *  目标名称是音频权限的名称(请参阅下表)。名称遵循分层属性命名约定。此外,星号可用于表示所有音频权限。
 * <p>
 *  下表列出了可能的<code> AudioPermission </code>目标名称。对于每个名称,表格提供了该权限允许的具体描述,以及对授予代码权限的风险的讨论。
 * <p>
 * 
 * <table border=1 cellpadding=5 summary="permission target name, what the permission allows, and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td>播放</td> <td>通过音频设备或系统上的设备播放音频。允许应用程序获取和操作音频播放(渲染)的线和混音器。
 * </td> <td>在某些情况下,使用此权限可能会影响其他应用程序,因为来自一行的音频可能与正在播放的其他音频混合系统,或者因为混音器的操作会影响使用该混音器的所有线路的音频。</td>。
 * /tr>
 * 
 * <tr>
 * <td>录制</td> <td>通过音频设备或系统上的设备录音。允许应用程序获取和操作音频录音(捕获)的线路和混音器。
 * </td> <td>在某些情况下,使用此权限可能会影响其他应用程序,因为混音器的操作会影响使用该混音器的所有线路的音频。此权限可以启用小程序或应用程序来窃听用户。</td>。
 * /tr>
 * /table>
 * p>
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
/*
 * (OLD PERMISSIONS TAKEN OUT FOR 1.2 BETA)
 *
 * <tr>
 * <td>playback device access</td>
 * <td>Direct access to the audio playback device(s), including configuration of the
 * playback format, volume, and balance, explicit opening and closing of the device,
 * etc.</td>
 * <td>Changes the properties of a shared system device and therefore
 * can affect other applications.</td>
 * </tr>
 *
 * <tr>
 * <td>playback device override</td>
 * <td>Manipulation of the audio playback device(s) in a way that directly conflicts
 * with use by other applications.  This includes closing the device while it is in
 * use by another application, changing the device format while another application
 * is using it, etc. </td>
 * <td>Changes the properties of a shared system device and therefore
 * can affect other applications.</td>
 * </tr>
 *
 * <tr>
 * <td>record device access</td>
 * <td>Direct access to the audio recording device(s), including configuration of the
 * the record format, volume, and balance, explicit opening and closing of the device,
 * etc.</td>
 * <td>Changes the properties of a shared system device and therefore
 * can affect other applications.</td>
 * </tr>
 *
 * <tr>
 * <td>record device override</td>
 * <td>Manipulation of the audio recording device(s) in a way that directly conflicts
 * with use by other applications.  This includes closing the device while it is in
 * use by another application, changing the device format while another application
 * is using it, etc. </td>
 * <td>Changes the properties of a shared system device and therefore
 * can affect other applications.</td>
 * </tr>
 *
 * </table>
 *<p>
 *
 * <p>
 *  (旧许可证适用于1.2 BETA)
 * 
 * <tr>
 *  <td>播放设备访问</td> <td>直接访问音频播放设备,包括播放格式,音量和平衡的配置,设备的显式打开和关闭等。</td> <td>更改共享系统设备的属性,因此可能会影响其他应用程序。
 * </td>。
 * </tr>
 * 
 * <tr>
 *  <td>播放设备覆盖</td> <td>以与其他应用程序的使用直接冲突的方式操纵音频播放设备。这包括在其他应用程序正在使用设备时关闭设备,在其他应用程序正在使用设备格式时更改设备格式等。
 * </td> <td>更改共享系统设备的属性,因此可能会影响其他应用程序。 / td>。
 * </tr>
 * 
 * <tr>
 *  <td>记录设备访问</td> <td>直接访问音频记录设备,包括配置记录格式,音量和平衡,设备的显式打开和关闭等。</td > <td>更改共享系统设备的属性,因此可能会影响其他应用程序。
 * </td>。
 * </tr>
 * 
 * <tr>
 * <td>记录设备覆盖</td> <td>以与其他应用程序的使用直接冲突的方式操纵音频记录设备。这包括在其他应用程序正在使用设备时关闭设备,在其他应用程序正在使用设备格式时更改设备格式等。
 * </td> <td>更改共享系统设备的属性,因此可能会影响其他应用程序。 / td>。
 * </tr>
 * 
 * </table>
 * p>
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */

/*
 * The <code>AudioPermission</code> class represents access rights to the audio
 * system resources.  An <code>AudioPermission</code> contains a target name
 * but no actions list; you either have the named permission or you don't.
 * <p>
 * The target name is the name of the audio permission (see the table below).
 * The names follow the hierarchical property-naming convention. Also, an asterisk
 * can be used to represent all the audio permissions.
 * <p>
 * The following table lists all the possible AudioPermission target names.
 * For each name, the table provides a description of exactly what that permission
 * allows, as well as a discussion of the risks of granting code the permission.
 * <p>
 *
 * <table border=1 cellpadding=5>
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 * <td>play</td>
 * <td>Audio playback through the audio device or devices on the system.</td>
 * <td>Allows the application to use a system device.  Can affect other applications,
 * because the result will be mixed with other audio being played on the system.</td>
 *</tr>
 *
 * <tr>
 * <td>record</td>
 * <td>Recording audio from the audio device or devices on the system,
 * commonly through a microphone.</td>
 * <td>Can enable an applet or application to eavesdrop on a user.</td>
 * </tr>
 *
 * <tr>
 * <td>playback device access</td>
 * <td>Direct access to the audio playback device(s), including configuration of the
 * playback format, volume, and balance, explicit opening and closing of the device,
 * etc.</td>
 * <td>Changes the properties of a shared system device and therefore
 * can affect other applications.</td>
 * </tr>
 *
 * <tr>
 * <td>playback device override</td>
 * <td>Manipulation of the audio playback device(s) in a way that directly conflicts
 * with use by other applications.  This includes closing the device while it is in
 * use by another application, changing the device format while another application
 * is using it, etc. </td>
 * <td>Changes the properties of a shared system device and therefore
 * can affect other applications.</td>
 * </tr>
 *
 * <tr>
 * <td>record device access</td>
 * <td>Direct access to the audio recording device(s), including configuration of the
 * the record format, volume, and balance, explicit opening and closing of the device,
 * etc.</td>
 * <td>Changes the properties of a shared system device and therefore
 * can affect other applications.</td>
 * </tr>
 *
 * <tr>
 * <td>record device override</td>
 * <td>Manipulation of the audio recording device(s) in a way that directly conflicts
 * with use by other applications.  This includes closing the device while it is in
 * use by another application, changing the device format while another application
 * is using it, etc. </td>
 * <td>Changes the properties of a shared system device and therefore
 * can affect other applications.</td>
 * </tr>
 *
 * </table>
 *<p>
 *
 * <p>
 *  <code> AudioPermission </code>类表示对音频系统资源的访问权限。
 *  <code> AudioPermission </code>包含目标名称,但没有操作列表;你有命名的权限或你不。
 * <p>
 *  目标名称是音频权限的名称(请参阅下表)。名称遵循分层属性命名约定。此外,星号可用于表示所有音频权限。
 * <p>
 *  下表列出了所有可能的AudioPermission目标名称。对于每个名称,表格提供了该权限允许的具体描述,以及对授予代码权限的风险的讨论。
 * <p>
 * 
 * <table border=1 cellpadding=5>
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td>播放</td> <td>通过音频设备或系统上的设备播放音频。</td> <td>允许应用程序使用系统设备。可能会影响其他应用程序,因为结果将与在系统上播放的其他音频混合。</td>
 * /tr>
 * 
 * <tr>
 * <td> record </td> <td>通常通过麦克风从音频设备或设备上录制音频。</td> <td>可以启用小程序或应用程序来窃听用户。</td >
 * </tr>
 * 
 * <tr>
 *  <td>播放设备访问</td> <td>直接访问音频播放设备,包括播放格式,音量和平衡的配置,设备的显式打开和关闭等。</td> <td>更改共享系统设备的属性,因此可能会影响其他应用程序。
 * </td>。
 * </tr>
 * 
 * 
 * @author Kara Kytle
 */

public class AudioPermission extends BasicPermission {

    /**
     * Creates a new <code>AudioPermission</code> object that has the specified
     * symbolic name, such as "play" or "record". An asterisk can be used to indicate
     * all audio permissions.
     * <p>
     * <tr>
     *  <td>播放设备覆盖</td> <td>以与其他应用程序的使用直接冲突的方式操纵音频播放设备。这包括在其他应用程序正在使用设备时关闭设备,在其他应用程序正在使用设备格式时更改设备格式等。
     * </td> <td>更改共享系统设备的属性,因此可能会影响其他应用程序。 / td>。
     * </tr>
     * 
     * <tr>
     *  <td>记录设备访问</td> <td>直接访问音频记录设备,包括配置记录格式,音量和平衡,设备的显式打开和关闭等。</td > <td>更改共享系统设备的属性,因此可能会影响其他应用程序。
     * </td>。
     * </tr>
     * 
     * <tr>
     * <td>记录设备覆盖</td> <td>以与其他应用程序的使用直接冲突的方式操纵音频记录设备。这包括在其他应用程序正在使用设备时关闭设备,在其他应用程序正在使用设备格式时更改设备格式等。
     * </td> <td>更改共享系统设备的属性,因此可能会影响其他应用程序。 / td>。
     * 
     * @param name the name of the new <code>AudioPermission</code>
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */
    public AudioPermission(String name) {

        super(name);
    }

    /**
     * Creates a new <code>AudioPermission</code> object that has the specified
     * symbolic name, such as "play" or "record".  The <code>actions</code>
     * parameter is currently unused and should be <code>null</code>.
     * <p>
     * </tr>
     * 
     * </table>
     * p>
     * 
     * 
     * @param name the name of the new <code>AudioPermission</code>
     * @param actions (unused; should be <code>null</code>)
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */
    public AudioPermission(String name, String actions) {

        super(name, actions);
    }
}

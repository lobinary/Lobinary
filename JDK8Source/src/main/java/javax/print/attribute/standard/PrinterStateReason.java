/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.print.attribute.standard;

import javax.print.attribute.EnumSyntax;
import javax.print.attribute.Attribute;

/**
 * Class PrinterStateReason is a printing attribute class, an enumeration,
 * that provides additional information about the printer's current state,
 * i.e., information that augments the value of the printer's
 * {@link PrinterState PrinterState} attribute.
 * Class PrinterStateReason defines standard printer
 * state reason values. A Print Service implementation only needs to report
 * those printer state reasons which are appropriate for the particular
 * implementation; it does not have to report every defined printer state
 * reason.
 * <P>
 * Instances of PrinterStateReason do not appear in a Print Service's
 * attribute set directly.
 * Rather, a {@link PrinterStateReasons PrinterStateReasons}
 * attribute appears in the Print Service's attribute set. The {@link
 * PrinterStateReasons PrinterStateReasons} attribute contains zero, one, or
 * more than one PrinterStateReason objects which pertain to the
 * Print Service's status, and each PrinterStateReason object is
 * associated with a {@link Severity Severity} level of REPORT (least severe),
 * WARNING, or ERROR (most severe). The printer adds a PrinterStateReason
 * object to the Print Service's
 * {@link PrinterStateReasons PrinterStateReasons} attribute when the
 * corresponding condition becomes true of the printer, and the printer
 * removes the PrinterStateReason object again when the corresponding
 * condition becomes false, regardless of whether the Print Service's overall
 * {@link PrinterState PrinterState} also changed.
 * <P>
 * <B>IPP Compatibility:</B>
 * The string values returned by each individual {@link PrinterStateReason} and
 * associated {@link Severity} object's <CODE>toString()</CODE>
 * methods, concatenated together with a hyphen (<CODE>"-"</CODE>) in
 * between, gives the IPP keyword value for a {@link PrinterStateReasons}.
 * The category name returned by <CODE>getName()</CODE> gives the IPP
 * attribute name.
 * <P>
 *
 * <p>
 *  类PrinterStateReason是一个打印属性类,枚举,提供有关打印机当前状态的附加信息,即增加打印机{@link PrinterState PrinterState}属性值的信息。
 *  PrinterStateReason类定义标准打印机状态原因值。打印服务实现仅需要报告适合于特定实现的那些打印机状态原因;它不必报告每个定义的打印机状态原因。
 * <P>
 * PrinterStateReason的实例不会直接显示在打印服务的属性集中。
 * 相反,{@link PrinterStateReasons PrinterStateReasons}属性显示在打印服务的属性集中。
 *  {@link PrinterStateReasons PrinterStateReasons}属性包含零个,一个或多个与打印服务状态相关的PrinterStateReason对象,每个PrinterS
 * tateReason对象与{@link严重性}级别的REPORT(最不严重),WARNING ,或ERROR(最严重)。
 * 相反,{@link PrinterStateReasons PrinterStateReasons}属性显示在打印服务的属性集中。
 * 当相应条件成为打印机的相应条件时,打印机向打印服务的{@link PrinterStateReasons PrinterStateReasons}属性添加PrinterStateReason对象,并且当
 * 相应条件为假时,打印机再次删除PrinterStateReason对象,而不管打印服务的整体{ @link PrinterState PrinterState}也改变了。
 * 相反,{@link PrinterStateReasons PrinterStateReasons}属性显示在打印服务的属性集中。
 * <P>
 *  <B> IPP兼容性：</B>每个{@link PrinterStateReason}和相关联的{@link Severity}对象的<CODE> toString()</CODE>方法返回的字符串值
 * 与连字符>" - "</CODE>),给出{@link PrinterStateReasons}的IPP关键字值。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class PrinterStateReason extends EnumSyntax implements Attribute {

    private static final long serialVersionUID = -1623720656201472593L;

    /**
     * The printer has detected an error other than ones listed below.
     * <p>
     *  打印机检测到以下错误之外的错误。
     * 
     */
    public static final PrinterStateReason OTHER = new PrinterStateReason(0);

    /**
     * A tray has run out of media.
     * <p>
     *  托盘用完介质。
     * 
     */
    public static final PrinterStateReason
        MEDIA_NEEDED = new PrinterStateReason(1);

    /**
     * The device has a media jam.
     * <p>
     *  设备有介质卡纸。
     * 
     */
    public static final PrinterStateReason
        MEDIA_JAM = new PrinterStateReason(2);

    /**
     * Someone has paused the printer, but the device(s) are taking an
     * appreciable time to stop. Later, when all output has stopped,
     * the {@link  PrinterState PrinterState} becomes STOPPED,
     * and the PAUSED value replaces
     * the MOVING_TO_PAUSED value in the {@link PrinterStateReasons
     * PrinterStateReasons} attribute. This value must be supported if the
     * printer can be paused and the implementation takes significant time to
     * pause a device in certain circumstances.
     * <p>
     * 有人暂停了打印机,但设备正在等待相当长的时间停止打印。
     * 稍后,当所有输出停止时,{@link PrinterState PrinterState}变为STOPPED,并且PAUSED值替换{@link PrinterStateReasons PrinterStateReasons}
     * 属性中的MOVING_TO_PAUSED值。
     * 有人暂停了打印机,但设备正在等待相当长的时间停止打印。如果打印机可以暂停,并且实施在某些情况下暂停设备需要很长时间,则必须支持此值。
     * 
     */
    public static final PrinterStateReason
        MOVING_TO_PAUSED = new PrinterStateReason(3);

    /**
     * Someone has paused the printer and the printer's {@link PrinterState
     * PrinterState} is STOPPED. In this state, a printer must not produce
     * printed output, but it must perform other operations requested by a
     * client. If a printer had been printing a job when the printer was
     * paused,
     * the Printer must resume printing that job when the printer is no longer
     * paused and leave no evidence in the printed output of such a pause.
     * This value must be supported if the printer can be paused.
     * <p>
     *  有人暂停了打印机,打印机的{@link PrinterState PrinterState}已停止。在此状态下,打印机不得产生打印输出,但必须执行客户端请求的其他操作。
     * 如果打印机暂停时打印机已经打印了作业,则当打印机不再暂停时,打印机必须恢复打印该作业,并在打印输出中不留下任何证据。如果可以暂停打印机,则必须支持此值。
     * 
     */
    public static final PrinterStateReason
        PAUSED = new PrinterStateReason(4);

    /**
     * Someone has removed a printer from service, and the device may be
     * powered down or physically removed.
     * In this state, a printer must not produce
     * printed output, and unless the printer is realized by a print server
     * that is still active, the printer must perform no other operations
     * requested by a client.
     * If a printer had been printing a job when it was shut down,
     * the printer need not resume printing that job when the printer is no
     * longer shut down. If the printer resumes printing such a job, it may
     * leave evidence in the printed output of such a shutdown, e.g. the part
     * printed before the shutdown may be printed a second time after the
     * shutdown.
     * <p>
     * 有人从服务中移除了打印机,设备可能已关闭或已被物理删除。在此状态下,打印机不得产生打印输出,除非打印机由仍处于活动状态的打印服务器实现,打印机必须不执行客户端请求的其他操作。
     * 如果打印机在关闭时已打印作业,则当打印机不再关闭时,打印机不需要恢复打印作业。如果打印机恢复打印这样的作业,它可能在打印输出中留下这样的关机的证据,例如。在关闭之前打印的部分可以在关闭之后第二次打印。
     * 
         */
    public static final PrinterStateReason
        SHUTDOWN = new PrinterStateReason(5);

    /**
     * The printer has scheduled a job on the output device and is in the
     * process of connecting to a shared network output device (and might not
     * be able to actually start printing the job for an arbitrarily long time
     * depending on the usage of the output device by other servers on the
     * network).
     * <p>
     *  打印机已经在输出设备上安排了作业,并且正在连接到共享网络输出设备的过程中(并且可能无法根据其他设备的输出设备的使用情况,实际开始打印作业任意长时间网络上的服务器)。
     * 
     */
    public static final PrinterStateReason
        CONNECTING_TO_DEVICE = new PrinterStateReason(6);

    /**
     * The server was able to connect to the output device (or is always
     * connected), but was unable to get a response from the output device.
     * <p>
     *  服务器能够连接到输出设备(或始终连接),但无法从输出设备获得响应。
     * 
     */
    public static final PrinterStateReason
        TIMED_OUT = new PrinterStateReason(7);

    /**
     * The printer is in the process of stopping the device and will be
     * stopped in a while.
     * When the device is stopped, the printer will change the
     * {@link PrinterState PrinterState} to STOPPED. The STOPPING reason is
     * never an error, even for a printer with a single output device. When an
     * output device ceases accepting jobs, the printer's {@link
     * PrinterStateReasons PrinterStateReasons} will have this reason while
     * the output device completes printing.
     * <p>
     * 打印机正在停止设备,并将在一段时间后停止。当设备停止时,打印机将{@link PrinterState PrinterState}更改为STOPPED。
     *  STOPPING原因从不是错误,即使对于具有单个输出设备的打印机也是如此。
     * 当输出设备停止接受作业时,打印机的{@link PrinterStateReasons PrinterStateReasons}将在输出设备完成打印时出现此原因。
     * 
     */
    public static final PrinterStateReason
        STOPPING = new PrinterStateReason(8);

    /**
     * When a printer controls more than one output device, this reason
     * indicates that one or more output devices are stopped. If the reason's
     * severity is a report, fewer than half of the output devices are
     * stopped.
     * If the reason's severity is a warning, half or more but fewer than
     * all of the output devices are stopped.
     * <p>
     *  当打印机控制多个输出设备时,此原因表示一个或多个输出设备已停止。如果原因的严重性是报告,则少于一半的输出设备被停止。如果原因的严重性是警告,则停止一半或更多但少于所有输出设备。
     * 
     */
    public static final PrinterStateReason
        STOPPED_PARTLY = new PrinterStateReason(9);

    /**
     * The device is low on toner.
     * <p>
     *  设备碳粉不足。
     * 
     */
    public static final PrinterStateReason
        TONER_LOW = new PrinterStateReason(10);

    /**
     * The device is out of toner.
     * <p>
     *  设备缺少碳粉。
     * 
     */
    public static final PrinterStateReason
        TONER_EMPTY = new PrinterStateReason(11);

    /**
     * The limit of persistent storage allocated for spooling has been
     * reached.
     * The printer is temporarily unable to accept more jobs. The printer will
     * remove this reason when it is able to accept more jobs.
     * This value should  be used by a non-spooling printer that only
     * accepts one or a small number
     * jobs at a time or a spooling printer that has filled the spool space.
     * <p>
     *  已达到分配用于假脱机的永久存储的限制。打印机暂时无法接受更多作业。当打印机能够接受更多作业时,将删除此原因。此值应由一个只能接受一个或少量作业的非假脱机打印机或已填充假脱机空间的假脱机打印机使用。
     * 
     */
    public static final PrinterStateReason
        SPOOL_AREA_FULL = new PrinterStateReason(12);

    /**
     * One or more covers on the device are open.
     * <p>
     *  设备上的一个或多个盖板已打开。
     * 
     */
    public static final PrinterStateReason
        COVER_OPEN = new PrinterStateReason(13);

    /**
     * One or more interlock devices on the printer are unlocked.
     * <p>
     *  打印机上的一个或多个互锁设备已解锁。
     * 
     */
    public static final PrinterStateReason
        INTERLOCK_OPEN = new PrinterStateReason(14);

    /**
     * One or more doors on the device are open.
     * <p>
     *  设备上的一个或多个门打开。
     * 
     */
    public static final PrinterStateReason
        DOOR_OPEN = new PrinterStateReason(15);

    /**
     * One or more input trays are not in the device.
     * <p>
     *  一个或多个输入托盘不在设备中。
     * 
     */
    public static final PrinterStateReason
        INPUT_TRAY_MISSING = new PrinterStateReason(16);

    /**
     * At least one input tray is low on media.
     * <p>
     *  至少有一个进纸盒的介质不足。
     * 
     */
    public static final PrinterStateReason
        MEDIA_LOW = new PrinterStateReason(17);

    /**
     * At least one input tray is empty.
     * <p>
     *  至少有一个进纸盘为空。
     * 
     */
    public static final PrinterStateReason
        MEDIA_EMPTY = new PrinterStateReason(18);

    /**
     * One or more output trays are not in the device.
     * <p>
     * 一个或多个输出托盘不在设备中。
     * 
     */
    public static final PrinterStateReason
        OUTPUT_TRAY_MISSING = new PrinterStateReason(19);

    /**
     * One or more output areas are almost full
     * (e.g. tray, stacker, collator).
     * <p>
     *  一个或多个输出区域几乎是满的(例如托盘,堆叠器,整理器)。
     * 
     */
    public static final PrinterStateReason
        OUTPUT_AREA_ALMOST_FULL = new PrinterStateReason(20);

    /**
     * One or more output areas are full (e.g. tray, stacker, collator).
     * <p>
     *  一个或多个输出区域是满的(例如托盘,堆叠器,整理器)。
     * 
     */
    public static final PrinterStateReason
        OUTPUT_AREA_FULL = new PrinterStateReason(21);

    /**
     * The device is low on at least one marker supply (e.g. toner, ink,
     * ribbon).
     * <p>
     *  该装置至少有一个标记物供应(例如调色剂,油墨,色带)。
     * 
     */
    public static final PrinterStateReason
        MARKER_SUPPLY_LOW = new PrinterStateReason(22);

    /**
     * The device is out of at least one marker supply (e.g. toner, ink,
     * ribbon).
     * <p>
     *  该装置出于至少一个标记物供应(例如调色剂,墨水,色带)。
     * 
     */
    public static final PrinterStateReason
        MARKER_SUPPLY_EMPTY = new PrinterStateReason(23);

    /**
     * The device marker supply waste receptacle is almost full.
     * <p>
     *  设备标记供应废液容器几乎已满。
     * 
     */
    public static final PrinterStateReason
        MARKER_WASTE_ALMOST_FULL = new PrinterStateReason(24);

    /**
     * The device marker supply waste receptacle is full.
     * <p>
     *  设备标记供应废液容器已满。
     * 
     */
    public static final PrinterStateReason
        MARKER_WASTE_FULL = new PrinterStateReason(25);

    /**
     * The fuser temperature is above normal.
     * <p>
     *  定影温度高于正常。
     * 
     */
    public static final PrinterStateReason
        FUSER_OVER_TEMP = new PrinterStateReason(26);

    /**
     * The fuser temperature is below normal.
     * <p>
     *  定影温度低于正常。
     * 
     */
    public static final PrinterStateReason
        FUSER_UNDER_TEMP = new PrinterStateReason(27);

    /**
     * The optical photo conductor is near end of life.
     * <p>
     *  光导光导接近寿命。
     * 
     */
    public static final PrinterStateReason
        OPC_NEAR_EOL = new PrinterStateReason(28);

    /**
     * The optical photo conductor is no longer functioning.
     * <p>
     *  光学光导体不再起作用。
     * 
     */
    public static final PrinterStateReason
        OPC_LIFE_OVER = new PrinterStateReason(29);

    /**
     * The device is low on developer.
     * <p>
     *  设备开发人员不足。
     * 
     */
    public static final PrinterStateReason
        DEVELOPER_LOW = new PrinterStateReason(30);

    /**
     * The device is out of developer.
     * <p>
     *  设备缺乏开发人员。
     * 
     */
    public static final PrinterStateReason
        DEVELOPER_EMPTY = new PrinterStateReason(31);

    /**
     * An interpreter resource is unavailable (e.g., font, form).
     * <p>
     *  解释器资源不可用(例如,字体,形式)。
     * 
     */
    public static final PrinterStateReason
        INTERPRETER_RESOURCE_UNAVAILABLE = new PrinterStateReason(32);

    /**
     * Construct a new printer state reason enumeration value with
     * the given integer value.
     *
     * <p>
     *  使用给定的整数值构造新的打印机状态原因枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected PrinterStateReason(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "other",
        "media-needed",
        "media-jam",
        "moving-to-paused",
        "paused",
        "shutdown",
        "connecting-to-device",
        "timed-out",
        "stopping",
        "stopped-partly",
        "toner-low",
        "toner-empty",
        "spool-area-full",
        "cover-open",
        "interlock-open",
        "door-open",
        "input-tray-missing",
        "media-low",
        "media-empty",
        "output-tray-missing",
        "output-area-almost-full",
        "output-area-full",
        "marker-supply-low",
        "marker-supply-empty",
        "marker-waste-almost-full",
        "marker-waste-full",
        "fuser-over-temp",
        "fuser-under-temp",
        "opc-near-eol",
        "opc-life-over",
        "developer-low",
        "developer-empty",
        "interpreter-resource-unavailable"
    };

    private static final PrinterStateReason[] myEnumValueTable = {
        OTHER,
        MEDIA_NEEDED,
        MEDIA_JAM,
        MOVING_TO_PAUSED,
        PAUSED,
        SHUTDOWN,
        CONNECTING_TO_DEVICE,
        TIMED_OUT,
        STOPPING,
        STOPPED_PARTLY,
        TONER_LOW,
        TONER_EMPTY,
        SPOOL_AREA_FULL,
        COVER_OPEN,
        INTERLOCK_OPEN,
        DOOR_OPEN,
        INPUT_TRAY_MISSING,
        MEDIA_LOW,
        MEDIA_EMPTY,
        OUTPUT_TRAY_MISSING,
        OUTPUT_AREA_ALMOST_FULL,
        OUTPUT_AREA_FULL,
        MARKER_SUPPLY_LOW,
        MARKER_SUPPLY_EMPTY,
        MARKER_WASTE_ALMOST_FULL,
        MARKER_WASTE_FULL,
        FUSER_OVER_TEMP,
        FUSER_UNDER_TEMP,
        OPC_NEAR_EOL,
        OPC_LIFE_OVER,
        DEVELOPER_LOW,
        DEVELOPER_EMPTY,
        INTERPRETER_RESOURCE_UNAVAILABLE
    };

    /**
     * Returns the string table for class PrinterStateReason.
     * <p>
     *  返回PrinterStateReason类的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class PrinterStateReason.
     * <p>
     *  返回PrinterStateReason类的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }


    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class PrinterStateReason and any vendor-defined subclasses, the
     * category is class PrinterStateReason itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于PrinterStateReason类和任何供应商定义的子类,类别是PrinterStateReason类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return PrinterStateReason.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class PrinterStateReason and any vendor-defined subclasses, the
     * category name is <CODE>"printer-state-reason"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "printer-state-reason";
    }

}

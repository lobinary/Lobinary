/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

/**
 * A set of attributes which control a print job.
 * <p>
 * Instances of this class control the number of copies, default selection,
 * destination, print dialog, file and printer names, page ranges, multiple
 * document handling (including collation), and multi-page imposition (such
 * as duplex) of every print job which uses the instance. Attribute names are
 * compliant with the Internet Printing Protocol (IPP) 1.1 where possible.
 * Attribute values are partially compliant where possible.
 * <p>
 * To use a method which takes an inner class type, pass a reference to
 * one of the constant fields of the inner class. Client code cannot create
 * new instances of the inner class types because none of those classes
 * has a public constructor. For example, to set the print dialog type to
 * the cross-platform, pure Java print dialog, use the following code:
 * <pre>
 * import java.awt.JobAttributes;
 *
 * public class PureJavaPrintDialogExample {
 *     public void setPureJavaPrintDialog(JobAttributes jobAttributes) {
 *         jobAttributes.setDialog(JobAttributes.DialogType.COMMON);
 *     }
 * }
 * </pre>
 * <p>
 * Every IPP attribute which supports an <i>attributeName</i>-default value
 * has a corresponding <code>set<i>attributeName</i>ToDefault</code> method.
 * Default value fields are not provided.
 *
 * <p>
 *  控制打印作业的一组属性。
 * <p>
 *  该类的实例控制每个打印作业的份数,默认选择,目的地,打印对话框,文件和打印机名称,页面范围,多文档处理(包括整理)和多页整版(例如双面),其​​使用实例。
 * 属性名称尽可能符合Internet打印协议(IPP)1.1。属性值在可能的情况下部分符合。
 * <p>
 *  要使用一个接受一个内部类类型的方法,传递一个引用到内部类的一个常量字段。客户端代码无法创建内部类类型的新实例,因为这些类都没有公共构造函数。
 * 例如,要将打印对话框类型设置为跨平台,纯Java打印对话框,请使用以下代码：。
 * <pre>
 *  import java.awt.JobAttributes;
 * 
 *  public class PureJavaPrintDialogExample {public void setPureJavaPrintDialog(JobAttributes jobAttributes){jobAttributes.setDialog(JobAttributes.DialogType.COMMON); }
 * }。
 * </pre>
 * <p>
 *  支持<i> attributeName </i> -default value的每个IPP属性都有相应的<code> set <i> attributeName </i> ToDefault </code>
 * 方法。
 * 不提供默认值字段。
 * 
 * 
 * @author      David Mendenhall
 * @since 1.3
 */
public final class JobAttributes implements Cloneable {
    /**
     * A type-safe enumeration of possible default selection states.
     * <p>
     *  可能的默认选择状态的类型安全枚举。
     * 
     * 
     * @since 1.3
     */
    public static final class DefaultSelectionType extends AttributeValue {
        private static final int I_ALL = 0;
        private static final int I_RANGE = 1;
        private static final int I_SELECTION = 2;

        private static final String NAMES[] = {
            "all", "range", "selection"
        };

        /**
         * The <code>DefaultSelectionType</code> instance to use for
         * specifying that all pages of the job should be printed.
         * <p>
         * 用于指定应打印作业的所有页面的<code> DefaultSelectionType </code>实例。
         * 
         */
        public static final DefaultSelectionType ALL =
           new DefaultSelectionType(I_ALL);
        /**
         * The <code>DefaultSelectionType</code> instance to use for
         * specifying that a range of pages of the job should be printed.
         * <p>
         *  用于指定应打印作业页面范围的<code> DefaultSelectionType </code>实例。
         * 
         */
        public static final DefaultSelectionType RANGE =
           new DefaultSelectionType(I_RANGE);
        /**
         * The <code>DefaultSelectionType</code> instance to use for
         * specifying that the current selection should be printed.
         * <p>
         *  用于指定应打印当前选择的<code> DefaultSelectionType </code>实例。
         * 
         */
        public static final DefaultSelectionType SELECTION =
           new DefaultSelectionType(I_SELECTION);

        private DefaultSelectionType(int type) {
            super(type, NAMES);
        }
    }

    /**
     * A type-safe enumeration of possible job destinations.
     * <p>
     *  可能的作业目的地的类型安全枚举。
     * 
     * 
     * @since 1.3
     */
    public static final class DestinationType extends AttributeValue {
        private static final int I_FILE = 0;
        private static final int I_PRINTER = 1;

        private static final String NAMES[] = {
            "file", "printer"
        };

        /**
         * The <code>DestinationType</code> instance to use for
         * specifying print to file.
         * <p>
         *  用于指定打印到文件的<code> DestinationType </code>实例。
         * 
         */
        public static final DestinationType FILE =
            new DestinationType(I_FILE);
        /**
         * The <code>DestinationType</code> instance to use for
         * specifying print to printer.
         * <p>
         *  用于指定打印到打印机的<code> DestinationType </code>实例。
         * 
         */
        public static final DestinationType PRINTER =
            new DestinationType(I_PRINTER);

        private DestinationType(int type) {
            super(type, NAMES);
        }
    }

    /**
     * A type-safe enumeration of possible dialogs to display to the user.
     * <p>
     *  可显示给用户的可能对话框的类型安全枚举。
     * 
     * 
     * @since 1.3
     */
    public static final class DialogType extends AttributeValue {
        private static final int I_COMMON = 0;
        private static final int I_NATIVE = 1;
        private static final int I_NONE = 2;

        private static final String NAMES[] = {
            "common", "native", "none"
        };

        /**
         * The <code>DialogType</code> instance to use for
         * specifying the cross-platform, pure Java print dialog.
         * <p>
         *  用于指定跨平台,纯Java打印对话框的<code> DialogType </code>实例。
         * 
         */
        public static final DialogType COMMON = new DialogType(I_COMMON);
        /**
         * The <code>DialogType</code> instance to use for
         * specifying the platform's native print dialog.
         * <p>
         *  用于指定平台的本机打印对话框的<code> DialogType </code>实例。
         * 
         */
        public static final DialogType NATIVE = new DialogType(I_NATIVE);
        /**
         * The <code>DialogType</code> instance to use for
         * specifying no print dialog.
         * <p>
         *  用于指定无打印对话框的<code> DialogType </code>实例。
         * 
         */
        public static final DialogType NONE = new DialogType(I_NONE);

        private DialogType(int type) {
            super(type, NAMES);
        }
    }

    /**
     * A type-safe enumeration of possible multiple copy handling states.
     * It is used to control how the sheets of multiple copies of a single
     * document are collated.
     * <p>
     *  可能的多个拷贝处理状态的类型安全枚举。它用于控制如何整理单个文档的多个副本的工作表。
     * 
     * 
     * @since 1.3
     */
    public static final class MultipleDocumentHandlingType extends
                                                               AttributeValue {
        private static final int I_SEPARATE_DOCUMENTS_COLLATED_COPIES = 0;
        private static final int I_SEPARATE_DOCUMENTS_UNCOLLATED_COPIES = 1;

        private static final String NAMES[] = {
            "separate-documents-collated-copies",
            "separate-documents-uncollated-copies"
        };

        /**
         * The <code>MultipleDocumentHandlingType</code> instance to use for specifying
         * that the job should be divided into separate, collated copies.
         * <p>
         *  用于指定作业应划分为单独的分页副本的<code> MultipleDocumentHandlingType </code>实例。
         * 
         */
        public static final MultipleDocumentHandlingType
            SEPARATE_DOCUMENTS_COLLATED_COPIES =
                new MultipleDocumentHandlingType(
                    I_SEPARATE_DOCUMENTS_COLLATED_COPIES);
        /**
         * The <code>MultipleDocumentHandlingType</code> instance to use for specifying
         * that the job should be divided into separate, uncollated copies.
         * <p>
         *  用于指定作业应该分为单独的未分层副本的<code> MultipleDocumentHandlingType </code>实例。
         * 
         */
        public static final MultipleDocumentHandlingType
            SEPARATE_DOCUMENTS_UNCOLLATED_COPIES =
                new MultipleDocumentHandlingType(
                    I_SEPARATE_DOCUMENTS_UNCOLLATED_COPIES);

        private MultipleDocumentHandlingType(int type) {
            super(type, NAMES);
        }
    }

    /**
     * A type-safe enumeration of possible multi-page impositions. These
     * impositions are in compliance with IPP 1.1.
     * <p>
     *  可能的多页imposition的类型安全枚举。这些义务符合IPP 1.1。
     * 
     * 
     * @since 1.3
     */
    public static final class SidesType extends AttributeValue {
        private static final int I_ONE_SIDED = 0;
        private static final int I_TWO_SIDED_LONG_EDGE = 1;
        private static final int I_TWO_SIDED_SHORT_EDGE = 2;

        private static final String NAMES[] = {
            "one-sided", "two-sided-long-edge", "two-sided-short-edge"
        };

        /**
         * The <code>SidesType</code> instance to use for specifying that
         * consecutive job pages should be printed upon the same side of
         * consecutive media sheets.
         * <p>
         * 用于指定连续作业页的<code> SidesType </code>实例应该打印在连续介质页的同一面上。
         * 
         */
        public static final SidesType ONE_SIDED = new SidesType(I_ONE_SIDED);
        /**
         * The <code>SidesType</code> instance to use for specifying that
         * consecutive job pages should be printed upon front and back sides
         * of consecutive media sheets, such that the orientation of each pair
         * of pages on the medium would be correct for the reader as if for
         * binding on the long edge.
         * <p>
         *  用于指定连续作业页面的<code> SidesType </code>实例应当被打印在连续介质页的正面和背面上,使得介质上的每对页面的方向对于读者是正确的如果用于绑定在长边上。
         * 
         */
        public static final SidesType TWO_SIDED_LONG_EDGE =
            new SidesType(I_TWO_SIDED_LONG_EDGE);
        /**
         * The <code>SidesType</code> instance to use for specifying that
         * consecutive job pages should be printed upon front and back sides
         * of consecutive media sheets, such that the orientation of each pair
         * of pages on the medium would be correct for the reader as if for
         * binding on the short edge.
         * <p>
         *  用于指定连续作业页面的<code> SidesType </code>实例应当被打印在连续介质页的正面和背面上,使得介质上的每对页面的方向对于读者是正确的如果用于绑定在短边上。
         * 
         */
        public static final SidesType TWO_SIDED_SHORT_EDGE =
            new SidesType(I_TWO_SIDED_SHORT_EDGE);

        private SidesType(int type) {
            super(type, NAMES);
        }
    }

    private int copies;
    private DefaultSelectionType defaultSelection;
    private DestinationType destination;
    private DialogType dialog;
    private String fileName;
    private int fromPage;
    private int maxPage;
    private int minPage;
    private MultipleDocumentHandlingType multipleDocumentHandling;
    private int[][] pageRanges;
    private int prFirst;
    private int prLast;
    private String printer;
    private SidesType sides;
    private int toPage;

    /**
     * Constructs a <code>JobAttributes</code> instance with default
     * values for every attribute.  The dialog defaults to
     * <code>DialogType.NATIVE</code>.  Min page defaults to
     * <code>1</code>.  Max page defaults to <code>Integer.MAX_VALUE</code>.
     * Destination defaults to <code>DestinationType.PRINTER</code>.
     * Selection defaults to <code>DefaultSelectionType.ALL</code>.
     * Number of copies defaults to <code>1</code>. Multiple document handling defaults
     * to <code>MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES</code>.
     * Sides defaults to <code>SidesType.ONE_SIDED</code>. File name defaults
     * to <code>null</code>.
     * <p>
     *  构造具有每个属性的默认值的<code> JobAttributes </code>实例。对话框默认为<code> DialogType.NATIVE </code>。
     * 最小页面默认为<code> 1 </code>。最大页面默认为<code> Integer.MAX_VALUE </code>。
     * 目标默认为<code> DestinationType.PRINTER </code>。选择默认为<code> DefaultSelectionType.ALL </code>。
     * 份数默认为<code> 1 </code>。
     * 多个文档处理默认为<code> MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES </code>。
     * 侧面默认为<code> SidesType.ONE_SIDED </code>。文件名默认为<code> null </code>。
     * 
     */
    public JobAttributes() {
        setCopiesToDefault();
        setDefaultSelection(DefaultSelectionType.ALL);
        setDestination(DestinationType.PRINTER);
        setDialog(DialogType.NATIVE);
        setMaxPage(Integer.MAX_VALUE);
        setMinPage(1);
        setMultipleDocumentHandlingToDefault();
        setSidesToDefault();
    }

    /**
     * Constructs a <code>JobAttributes</code> instance which is a copy
     * of the supplied <code>JobAttributes</code>.
     *
     * <p>
     *  构造一个<code> JobAttributes </code>实例,它是提供的<code> JobAttributes </code>的副本。
     * 
     * 
     * @param   obj the <code>JobAttributes</code> to copy
     */
    public JobAttributes(JobAttributes obj) {
        set(obj);
    }

    /**
     * Constructs a <code>JobAttributes</code> instance with the
     * specified values for every attribute.
     *
     * <p>
     * 构造具有每个属性的指定值的<code> JobAttributes </code>实例。
     * 
     * 
     * @param   copies an integer greater than 0
     * @param   defaultSelection <code>DefaultSelectionType.ALL</code>,
     *          <code>DefaultSelectionType.RANGE</code>, or
     *          <code>DefaultSelectionType.SELECTION</code>
     * @param   destination <code>DesintationType.FILE</code> or
     *          <code>DesintationType.PRINTER</code>
     * @param   dialog <code>DialogType.COMMON</code>,
     *          <code>DialogType.NATIVE</code>, or
     *          <code>DialogType.NONE</code>
     * @param   fileName the possibly <code>null</code> file name
     * @param   maxPage an integer greater than zero and greater than or equal
     *          to <i>minPage</i>
     * @param   minPage an integer greater than zero and less than or equal
     *          to <i>maxPage</i>
     * @param   multipleDocumentHandling
     *     <code>MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_COLLATED_COPIES</code> or
     *     <code>MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES</code>
     * @param   pageRanges an array of integer arrays of two elements; an array
     *          is interpreted as a range spanning all pages including and
     *          between the specified pages; ranges must be in ascending
     *          order and must not overlap; specified page numbers cannot be
     *          less than <i>minPage</i> nor greater than <i>maxPage</i>;
     *          for example:
     *          <pre>
     *          (new int[][] { new int[] { 1, 3 }, new int[] { 5, 5 },
     *                         new int[] { 15, 19 } }),
     *          </pre>
     *          specifies pages 1, 2, 3, 5, 15, 16, 17, 18, and 19. Note that
     *          (<code>new int[][] { new int[] { 1, 1 }, new int[] { 1, 2 } }</code>),
     *          is an invalid set of page ranges because the two ranges
     *          overlap
     * @param   printer the possibly <code>null</code> printer name
     * @param   sides <code>SidesType.ONE_SIDED</code>,
     *          <code>SidesType.TWO_SIDED_LONG_EDGE</code>, or
     *          <code>SidesType.TWO_SIDED_SHORT_EDGE</code>
     * @throws  IllegalArgumentException if one or more of the above
     *          conditions is violated
     */
    public JobAttributes(int copies, DefaultSelectionType defaultSelection,
                         DestinationType destination, DialogType dialog,
                         String fileName, int maxPage, int minPage,
                         MultipleDocumentHandlingType multipleDocumentHandling,
                         int[][] pageRanges, String printer, SidesType sides) {
        setCopies(copies);
        setDefaultSelection(defaultSelection);
        setDestination(destination);
        setDialog(dialog);
        setFileName(fileName);
        setMaxPage(maxPage);
        setMinPage(minPage);
        setMultipleDocumentHandling(multipleDocumentHandling);
        setPageRanges(pageRanges);
        setPrinter(printer);
        setSides(sides);
    }

    /**
     * Creates and returns a copy of this <code>JobAttributes</code>.
     *
     * <p>
     *  创建并返回此<code> JobAttributes </code>的副本。
     * 
     * 
     * @return  the newly created copy; it is safe to cast this Object into
     *          a <code>JobAttributes</code>
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // Since we implement Cloneable, this should never happen
            throw new InternalError(e);
        }
    }

    /**
     * Sets all of the attributes of this <code>JobAttributes</code> to
     * the same values as the attributes of obj.
     *
     * <p>
     *  将此<code> JobAttributes </code>的所有属性设置为与obj的属性相同的值。
     * 
     * 
     * @param   obj the <code>JobAttributes</code> to copy
     */
    public void set(JobAttributes obj) {
        copies = obj.copies;
        defaultSelection = obj.defaultSelection;
        destination = obj.destination;
        dialog = obj.dialog;
        fileName = obj.fileName;
        fromPage = obj.fromPage;
        maxPage = obj.maxPage;
        minPage = obj.minPage;
        multipleDocumentHandling = obj.multipleDocumentHandling;
        // okay because we never modify the contents of pageRanges
        pageRanges = obj.pageRanges;
        prFirst = obj.prFirst;
        prLast = obj.prLast;
        printer = obj.printer;
        sides = obj.sides;
        toPage = obj.toPage;
    }

    /**
     * Returns the number of copies the application should render for jobs
     * using these attributes. This attribute is updated to the value chosen
     * by the user.
     *
     * <p>
     *  返回应用程序应为使用这些属性的作业呈现的副本数。此属性更新为用户选择的值。
     * 
     * 
     * @return  an integer greater than 0.
     */
    public int getCopies() {
        return copies;
    }

    /**
     * Specifies the number of copies the application should render for jobs
     * using these attributes. Not specifying this attribute is equivalent to
     * specifying <code>1</code>.
     *
     * <p>
     *  指定应用程序应为使用这些属性的作业呈现的副本数。不指定此属性等效于指定<code> 1 </code>。
     * 
     * 
     * @param   copies an integer greater than 0
     * @throws  IllegalArgumentException if <code>copies</code> is less than
     *      or equal to 0
     */
    public void setCopies(int copies) {
        if (copies <= 0) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "copies");
        }
        this.copies = copies;
    }

    /**
     * Sets the number of copies the application should render for jobs using
     * these attributes to the default. The default number of copies is 1.
     * <p>
     *  设置应用程序应使用这些属性为默认值呈现的副本数。默认份数为1。
     * 
     */
    public void setCopiesToDefault() {
        setCopies(1);
    }

    /**
     * Specifies whether, for jobs using these attributes, the application
     * should print all pages, the range specified by the return value of
     * <code>getPageRanges</code>, or the current selection. This attribute
     * is updated to the value chosen by the user.
     *
     * <p>
     *  指定对于使用这些属性的作业,应用程序是否应打印所有页面,由<code> getPageRanges </code>的返回值指定的范围或当前选择。此属性更新为用户选择的值。
     * 
     * 
     * @return  DefaultSelectionType.ALL, DefaultSelectionType.RANGE, or
     *          DefaultSelectionType.SELECTION
     */
    public DefaultSelectionType getDefaultSelection() {
        return defaultSelection;
    }

    /**
     * Specifies whether, for jobs using these attributes, the application
     * should print all pages, the range specified by the return value of
     * <code>getPageRanges</code>, or the current selection. Not specifying
     * this attribute is equivalent to specifying DefaultSelectionType.ALL.
     *
     * <p>
     *  指定对于使用这些属性的作业,应用程序是否应打印所有页面,由<code> getPageRanges </code>返回值指定的范围或当前选择。
     * 不指定此属性等效于指定DefaultSelectionType.ALL。
     * 
     * 
     * @param   defaultSelection DefaultSelectionType.ALL,
     *          DefaultSelectionType.RANGE, or DefaultSelectionType.SELECTION.
     * @throws  IllegalArgumentException if defaultSelection is <code>null</code>
     */
    public void setDefaultSelection(DefaultSelectionType defaultSelection) {
        if (defaultSelection == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "defaultSelection");
        }
        this.defaultSelection = defaultSelection;
    }

    /**
     * Specifies whether output will be to a printer or a file for jobs using
     * these attributes. This attribute is updated to the value chosen by the
     * user.
     *
     * <p>
     *  指定对使用这些属性的作业的输出是打印机还是文件。此属性更新为用户选择的值。
     * 
     * 
     * @return  DesintationType.FILE or DesintationType.PRINTER
     */
    public DestinationType getDestination() {
        return destination;
    }

    /**
     * Specifies whether output will be to a printer or a file for jobs using
     * these attributes. Not specifying this attribute is equivalent to
     * specifying DesintationType.PRINTER.
     *
     * <p>
     * 指定对使用这些属性的作业的输出是打印机还是文件。不指定此属性等效于指定DesintationType.PRINTER。
     * 
     * 
     * @param   destination DesintationType.FILE or DesintationType.PRINTER.
     * @throws  IllegalArgumentException if destination is null.
     */
    public void setDestination(DestinationType destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "destination");
        }
        this.destination = destination;
    }

    /**
     * Returns whether, for jobs using these attributes, the user should see
     * a print dialog in which to modify the print settings, and which type of
     * print dialog should be displayed. DialogType.COMMON denotes a cross-
     * platform, pure Java print dialog. DialogType.NATIVE denotes the
     * platform's native print dialog. If a platform does not support a native
     * print dialog, the pure Java print dialog is displayed instead.
     * DialogType.NONE specifies no print dialog (i.e., background printing).
     * This attribute cannot be modified by, and is not subject to any
     * limitations of, the implementation or the target printer.
     *
     * <p>
     *  返回对于使用这些属性的作业,用户是否应该看到打印对话框,以修改打印设置,以及应显示哪种类型的打印对话框。 DialogType.COMMON表示跨平台,纯Java打印对话框。
     *  DialogType.NATIVE表示平台的本地打印对话框。如果平台不支持本地打印对话框,则会显示纯Java打印对话框。 DialogType.NONE不指定打印对话框(即背景打印)。
     * 此属性不能由实施或目标打印机修改,并且不受其实施或目标打印机的任何限制。
     * 
     * 
     * @return  <code>DialogType.COMMON</code>, <code>DialogType.NATIVE</code>, or
     *          <code>DialogType.NONE</code>
     */
    public DialogType getDialog() {
        return dialog;
    }

    /**
     * Specifies whether, for jobs using these attributes, the user should see
     * a print dialog in which to modify the print settings, and which type of
     * print dialog should be displayed. DialogType.COMMON denotes a cross-
     * platform, pure Java print dialog. DialogType.NATIVE denotes the
     * platform's native print dialog. If a platform does not support a native
     * print dialog, the pure Java print dialog is displayed instead.
     * DialogType.NONE specifies no print dialog (i.e., background printing).
     * Not specifying this attribute is equivalent to specifying
     * DialogType.NATIVE.
     *
     * <p>
     *  指定对于使用这些属性的作业,用户是否应该看到打印对话框,以修改打印设置,以及应显示哪种类型的打印对话框。 DialogType.COMMON表示跨平台,纯Java打印对话框。
     *  DialogType.NATIVE表示平台的本地打印对话框。如果平台不支持本地打印对话框,则会显示纯Java打印对话框。 DialogType.NONE不指定打印对话框(即背景打印)。
     * 不指定此属性等效于指定DialogType.NATIVE。
     * 
     * 
     * @param   dialog DialogType.COMMON, DialogType.NATIVE, or
     *          DialogType.NONE.
     * @throws  IllegalArgumentException if dialog is null.
     */
    public void setDialog(DialogType dialog) {
        if (dialog == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "dialog");
        }
        this.dialog = dialog;
    }

    /**
     * Specifies the file name for the output file for jobs using these
     * attributes. This attribute is updated to the value chosen by the user.
     *
     * <p>
     *  指定使用这些属性的作业的输出文件的文件名。此属性更新为用户选择的值。
     * 
     * 
     * @return  the possibly <code>null</code> file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Specifies the file name for the output file for jobs using these
     * attributes. Default is platform-dependent and implementation-defined.
     *
     * <p>
     * 指定使用这些属性的作业的输出文件的文件名。默认值是平台相关的和实现定义的。
     * 
     * 
     * @param   fileName the possibly null file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns, for jobs using these attributes, the first page to be
     * printed, if a range of pages is to be printed. This attribute is
     * updated to the value chosen by the user. An application should ignore
     * this attribute on output, unless the return value of the <code>
     * getDefaultSelection</code> method is DefaultSelectionType.RANGE. An
     * application should honor the return value of <code>getPageRanges</code>
     * over the return value of this method, if possible.
     *
     * <p>
     *  对于使用这些属性的作业,返回要打印的第一页(如果要打印一系列页面)。此属性更新为用户选择的值。
     * 应用程序应忽略输出上的此属性,除非<code> getDefaultSelection </code>方法的返回值为DefaultSelectionType.RANGE。
     * 如果可能,应用程序应该遵守<code> getPageRanges </code>的返回值超过此方法的返回值。
     * 
     * 
     * @return  an integer greater than zero and less than or equal to
     *          <i>toPage</i> and greater than or equal to <i>minPage</i> and
     *          less than or equal to <i>maxPage</i>.
     */
    public int getFromPage() {
        if (fromPage != 0) {
            return fromPage;
        } else if (toPage != 0) {
            return getMinPage();
        } else if (pageRanges != null) {
            return prFirst;
        } else {
            return getMinPage();
        }
    }

    /**
     * Specifies, for jobs using these attributes, the first page to be
     * printed, if a range of pages is to be printed. If this attribute is not
     * specified, then the values from the pageRanges attribute are used. If
     * pageRanges and either or both of fromPage and toPage are specified,
     * pageRanges takes precedence. Specifying none of pageRanges, fromPage,
     * or toPage is equivalent to calling
     * setPageRanges(new int[][] { new int[] { <i>minPage</i> } });
     *
     * <p>
     *  对于使用这些属性的作业,指定要打印的第一页,如果要打印一系列页面。如果未指定此属性,那么将使用来自pageRanges属性的值。
     * 如果指定了pageRanges和fromPage和toPage中的一个或两个,则页面宽度优先。
     * 没有指定pageRanges,fromPage或toPage等效于调用setPageRanges(new int [] [] {new int [] {<i> minPage </i>}});。
     * 
     * 
     * @param   fromPage an integer greater than zero and less than or equal to
     *          <i>toPage</i> and greater than or equal to <i>minPage</i> and
     *          less than or equal to <i>maxPage</i>.
     * @throws  IllegalArgumentException if one or more of the above
     *          conditions is violated.
     */
    public void setFromPage(int fromPage) {
        if (fromPage <= 0 ||
            (toPage != 0 && fromPage > toPage) ||
            fromPage < minPage ||
            fromPage > maxPage) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "fromPage");
        }
        this.fromPage = fromPage;
    }

    /**
     * Specifies the maximum value the user can specify as the last page to
     * be printed for jobs using these attributes. This attribute cannot be
     * modified by, and is not subject to any limitations of, the
     * implementation or the target printer.
     *
     * <p>
     *  指定用户可以指定为使用这些属性的作业要打印的最后一页的最大值。此属性不能由实施或目标打印机修改,并且不受其实施或目标打印机的任何限制。
     * 
     * 
     * @return  an integer greater than zero and greater than or equal
     *          to <i>minPage</i>.
     */
    public int getMaxPage() {
        return maxPage;
    }

    /**
     * Specifies the maximum value the user can specify as the last page to
     * be printed for jobs using these attributes. Not specifying this
     * attribute is equivalent to specifying <code>Integer.MAX_VALUE</code>.
     *
     * <p>
     *  指定用户可以指定为使用这些属性的作业要打印的最后一页的最大值。不指定此属性等效于指定<code> Integer.MAX_VALUE </code>。
     * 
     * 
     * @param   maxPage an integer greater than zero and greater than or equal
     *          to <i>minPage</i>
     * @throws  IllegalArgumentException if one or more of the above
     *          conditions is violated
     */
    public void setMaxPage(int maxPage) {
        if (maxPage <= 0 || maxPage < minPage) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "maxPage");
        }
        this.maxPage = maxPage;
    }

    /**
     * Specifies the minimum value the user can specify as the first page to
     * be printed for jobs using these attributes. This attribute cannot be
     * modified by, and is not subject to any limitations of, the
     * implementation or the target printer.
     *
     * <p>
     * 指定用户可以指定为使用这些属性的作业要打印的第一页的最小值。此属性不能由实施或目标打印机修改,并且不受其实施或目标打印机的任何限制。
     * 
     * 
     * @return  an integer greater than zero and less than or equal
     *          to <i>maxPage</i>.
     */
    public int getMinPage() {
        return minPage;
    }

    /**
     * Specifies the minimum value the user can specify as the first page to
     * be printed for jobs using these attributes. Not specifying this
     * attribute is equivalent to specifying <code>1</code>.
     *
     * <p>
     *  指定用户可以指定为使用这些属性的作业要打印的第一页的最小值。不指定此属性等效于指定<code> 1 </code>。
     * 
     * 
     * @param   minPage an integer greater than zero and less than or equal
     *          to <i>maxPage</i>.
     * @throws  IllegalArgumentException if one or more of the above
     *          conditions is violated.
     */
    public void setMinPage(int minPage) {
        if (minPage <= 0 || minPage > maxPage) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "minPage");
        }
        this.minPage = minPage;
    }

    /**
     * Specifies the handling of multiple copies, including collation, for
     * jobs using these attributes. This attribute is updated to the value
     * chosen by the user.
     *
     * <p>
     *  指定对使用这些属性的作业处理多个副本(包括归类)。此属性更新为用户选择的值。
     * 
     * 
     * @return
     *     MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_COLLATED_COPIES or
     *     MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES.
     */
    public MultipleDocumentHandlingType getMultipleDocumentHandling() {
        return multipleDocumentHandling;
    }

    /**
     * Specifies the handling of multiple copies, including collation, for
     * jobs using these attributes. Not specifying this attribute is equivalent
     * to specifying
     * MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES.
     *
     * <p>
     *  指定对使用这些属性的作业处理多个副本(包括归类)。
     * 不指定此属性等同于指定MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES。
     * 
     * 
     * @param   multipleDocumentHandling
     *     MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_COLLATED_COPIES or
     *     MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES.
     * @throws  IllegalArgumentException if multipleDocumentHandling is null.
     */
    public void setMultipleDocumentHandling(MultipleDocumentHandlingType
                                            multipleDocumentHandling) {
        if (multipleDocumentHandling == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "multipleDocumentHandling");
        }
        this.multipleDocumentHandling = multipleDocumentHandling;
    }

    /**
     * Sets the handling of multiple copies, including collation, for jobs
     * using these attributes to the default. The default handling is
     * MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES.
     * <p>
     *  将使用这些属性的作业的多个副本(包括归类)的处理设置为默认值。
     * 默认处理为MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES。
     * 
     */
    public void setMultipleDocumentHandlingToDefault() {
        setMultipleDocumentHandling(
            MultipleDocumentHandlingType.SEPARATE_DOCUMENTS_UNCOLLATED_COPIES);
    }

    /**
     * Specifies, for jobs using these attributes, the ranges of pages to be
     * printed, if a range of pages is to be printed. All range numbers are
     * inclusive. This attribute is updated to the value chosen by the user.
     * An application should ignore this attribute on output, unless the
     * return value of the <code>getDefaultSelection</code> method is
     * DefaultSelectionType.RANGE.
     *
     * <p>
     *  对于使用这些属性的作业,指定要打印的页面范围(如果要打印一页范围)。所有范围数字都包括在内。此属性更新为用户选择的值。
     * 应用程序应忽略输出上的此属性,除非<code> getDefaultSelection </code>方法的返回值为DefaultSelectionType.RANGE。
     * 
     * 
     * @return  an array of integer arrays of 2 elements. An array
     *          is interpreted as a range spanning all pages including and
     *          between the specified pages. Ranges must be in ascending
     *          order and must not overlap. Specified page numbers cannot be
     *          less than <i>minPage</i> nor greater than <i>maxPage</i>.
     *          For example:
     *          (new int[][] { new int[] { 1, 3 }, new int[] { 5, 5 },
     *                         new int[] { 15, 19 } }),
     *          specifies pages 1, 2, 3, 5, 15, 16, 17, 18, and 19.
     */
    public int[][] getPageRanges() {
        if (pageRanges != null) {
            // Return a copy because otherwise client code could circumvent the
            // the checks made in setPageRanges by modifying the returned
            // array.
            int[][] copy = new int[pageRanges.length][2];
            for (int i = 0; i < pageRanges.length; i++) {
                copy[i][0] = pageRanges[i][0];
                copy[i][1] = pageRanges[i][1];
            }
            return copy;
        } else if (fromPage != 0 || toPage != 0) {
            int fromPage = getFromPage();
            int toPage = getToPage();
            return new int[][] { new int[] { fromPage, toPage } };
        } else {
            int minPage = getMinPage();
            return new int[][] { new int[] { minPage, minPage } };
        }
    }

    /**
     * Specifies, for jobs using these attributes, the ranges of pages to be
     * printed, if a range of pages is to be printed. All range numbers are
     * inclusive. If this attribute is not specified, then the values from the
     * fromPage and toPages attributes are used. If pageRanges and either or
     * both of fromPage and toPage are specified, pageRanges takes precedence.
     * Specifying none of pageRanges, fromPage, or toPage is equivalent to
     * calling setPageRanges(new int[][] { new int[] { <i>minPage</i>,
     *                                                 <i>minPage</i> } });
     *
     * <p>
     * 对于使用这些属性的作业,指定要打印的页面范围(如果要打印一页范围)。所有范围数字都包括在内。如果未指定此属性,那么将使用fromPage和toPages属性中的值。
     * 如果指定了pageRanges和fromPage和toPage中的一个或两个,则页面宽度优先。
     * 没有指定pageRanges,fromPage或toPage等效于调用setPageRanges(new int [] [] {new int [] {<i> minPage </i>,<i> minPage </i>}
     * });。
     * 如果指定了pageRanges和fromPage和toPage中的一个或两个,则页面宽度优先。
     * 
     * 
     * @param   pageRanges an array of integer arrays of 2 elements. An array
     *          is interpreted as a range spanning all pages including and
     *          between the specified pages. Ranges must be in ascending
     *          order and must not overlap. Specified page numbers cannot be
     *          less than <i>minPage</i> nor greater than <i>maxPage</i>.
     *          For example:
     *          (new int[][] { new int[] { 1, 3 }, new int[] { 5, 5 },
     *                         new int[] { 15, 19 } }),
     *          specifies pages 1, 2, 3, 5, 15, 16, 17, 18, and 19. Note that
     *          (new int[][] { new int[] { 1, 1 }, new int[] { 1, 2 } }),
     *          is an invalid set of page ranges because the two ranges
     *          overlap.
     * @throws  IllegalArgumentException if one or more of the above
     *          conditions is violated.
     */
    public void setPageRanges(int[][] pageRanges) {
        String xcp = "Invalid value for attribute pageRanges";
        int first = 0;
        int last = 0;

        if (pageRanges == null) {
            throw new IllegalArgumentException(xcp);
        }

        for (int i = 0; i < pageRanges.length; i++) {
            if (pageRanges[i] == null ||
                pageRanges[i].length != 2 ||
                pageRanges[i][0] <= last ||
                pageRanges[i][1] < pageRanges[i][0]) {
                    throw new IllegalArgumentException(xcp);
            }
            last = pageRanges[i][1];
            if (first == 0) {
                first = pageRanges[i][0];
            }
        }

        if (first < minPage || last > maxPage) {
            throw new IllegalArgumentException(xcp);
        }

        // Store a copy because otherwise client code could circumvent the
        // the checks made above by holding a reference to the array and
        // modifying it after calling setPageRanges.
        int[][] copy = new int[pageRanges.length][2];
        for (int i = 0; i < pageRanges.length; i++) {
            copy[i][0] = pageRanges[i][0];
            copy[i][1] = pageRanges[i][1];
        }
        this.pageRanges = copy;
        this.prFirst = first;
        this.prLast = last;
    }

    /**
     * Returns the destination printer for jobs using these attributes. This
     * attribute is updated to the value chosen by the user.
     *
     * <p>
     *  返回使用这些属性的作业的目标打印机。此属性更新为用户选择的值。
     * 
     * 
     * @return  the possibly null printer name.
     */
    public String getPrinter() {
        return printer;
    }

    /**
     * Specifies the destination printer for jobs using these attributes.
     * Default is platform-dependent and implementation-defined.
     *
     * <p>
     *  为使用这些属性的作业指定目标打印机。默认值是平台相关的和实现定义的。
     * 
     * 
     * @param   printer the possibly null printer name.
     */
    public void setPrinter(String printer) {
        this.printer = printer;
    }

    /**
     * Returns how consecutive pages should be imposed upon the sides of the
     * print medium for jobs using these attributes. SidesType.ONE_SIDED
     * imposes each consecutive page upon the same side of consecutive media
     * sheets. This imposition is sometimes called <i>simplex</i>.
     * SidesType.TWO_SIDED_LONG_EDGE imposes each consecutive pair of pages
     * upon front and back sides of consecutive media sheets, such that the
     * orientation of each pair of pages on the medium would be correct for
     * the reader as if for binding on the long edge. This imposition is
     * sometimes called <i>duplex</i>. SidesType.TWO_SIDED_SHORT_EDGE imposes
     * each consecutive pair of pages upon front and back sides of consecutive
     * media sheets, such that the orientation of each pair of pages on the
     * medium would be correct for the reader as if for binding on the short
     * edge. This imposition is sometimes called <i>tumble</i>. This attribute
     * is updated to the value chosen by the user.
     *
     * <p>
     * 返回对于使用这些属性的作业,应如何将连续页面强加在打印介质的两侧。 SidesType.ONE_SIDED将每个连续页面强加在连续媒体工作表的同一面上。此拼版有时称为<i> simplex </i>。
     *  SidesType.TWO_SIDED_LONG_EDGE将每个连续的页对施加在连续介质页的前侧和后侧上,使得介质上的每对页的取向对于读取者来说对于在长边上的绑定是正确的。
     * 此拼版有时称为<i>双面</i>。
     *  SidesType.TWO_SIDED_SHORT_EDGE将连续的一对页面施加在连续介质页的前侧和后侧,使得介质上的每对页面的取向对于读取器来说对于在短边缘上的绑定是正确的。
     * 此拼版有时称为<i>滚动</i>。此属性更新为用户选择的值。
     * 
     * 
     * @return  SidesType.ONE_SIDED, SidesType.TWO_SIDED_LONG_EDGE, or
     *          SidesType.TWO_SIDED_SHORT_EDGE.
     */
    public SidesType getSides() {
        return sides;
    }

    /**
     * Specifies how consecutive pages should be imposed upon the sides of the
     * print medium for jobs using these attributes. SidesType.ONE_SIDED
     * imposes each consecutive page upon the same side of consecutive media
     * sheets. This imposition is sometimes called <i>simplex</i>.
     * SidesType.TWO_SIDED_LONG_EDGE imposes each consecutive pair of pages
     * upon front and back sides of consecutive media sheets, such that the
     * orientation of each pair of pages on the medium would be correct for
     * the reader as if for binding on the long edge. This imposition is
     * sometimes called <i>duplex</i>. SidesType.TWO_SIDED_SHORT_EDGE imposes
     * each consecutive pair of pages upon front and back sides of consecutive
     * media sheets, such that the orientation of each pair of pages on the
     * medium would be correct for the reader as if for binding on the short
     * edge. This imposition is sometimes called <i>tumble</i>. Not specifying
     * this attribute is equivalent to specifying SidesType.ONE_SIDED.
     *
     * <p>
     * 指定对于使用这些属性的作业,应如何将连续页面强加在打印介质的两侧。 SidesType.ONE_SIDED将每个连续页面强加在连续媒体工作表的同一面上。此拼版有时称为<i> simplex </i>。
     *  SidesType.TWO_SIDED_LONG_EDGE将每个连续的页对施加在连续介质页的前侧和后侧,使得介质上的每对页面的取向对于读取者来说对于在长边上的绑定是正确的。
     * 此拼版有时称为<i>双面</i>。
     *  SidesType.TWO_SIDED_SHORT_EDGE将每个连续的页对施加在连续介质页的前侧和后侧上,使得介质上的每对页的取向对于读取者来说对于在短边上的绑定是正确的。
     * 此拼版有时称为<i>滚动</i>。不指定此属性等效于指定SidesType.ONE_SIDED。
     * 
     * 
     * @param   sides SidesType.ONE_SIDED, SidesType.TWO_SIDED_LONG_EDGE, or
     *          SidesType.TWO_SIDED_SHORT_EDGE.
     * @throws  IllegalArgumentException if sides is null.
     */
    public void setSides(SidesType sides) {
        if (sides == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "sides");
        }
        this.sides = sides;
    }

    /**
     * Sets how consecutive pages should be imposed upon the sides of the
     * print medium for jobs using these attributes to the default. The
     * default imposition is SidesType.ONE_SIDED.
     * <p>
     *  设置如何将使用这些属性的作业的连续页面应用于打印介质两侧的默认值。默认拼版是SidesType.ONE_SIDED。
     * 
     */
    public void setSidesToDefault() {
        setSides(SidesType.ONE_SIDED);
    }

    /**
     * Returns, for jobs using these attributes, the last page (inclusive)
     * to be printed, if a range of pages is to be printed. This attribute is
     * updated to the value chosen by the user. An application should ignore
     * this attribute on output, unless the return value of the <code>
     * getDefaultSelection</code> method is DefaultSelectionType.RANGE. An
     * application should honor the return value of <code>getPageRanges</code>
     * over the return value of this method, if possible.
     *
     * <p>
     * 对于使用这些属性的作业,返回要打印的最后一页(包括),如果要打印一页范围。此属性更新为用户选择的值。
     * 应用程序应忽略输出上的此属性,除非<code> getDefaultSelection </code>方法的返回值为DefaultSelectionType.RANGE。
     * 如果可能,应用程序应该遵守<code> getPageRanges </code>的返回值超过此方法的返回值。
     * 
     * 
     * @return  an integer greater than zero and greater than or equal
     *          to <i>toPage</i> and greater than or equal to <i>minPage</i>
     *          and less than or equal to <i>maxPage</i>.
     */
    public int getToPage() {
        if (toPage != 0) {
            return toPage;
        } else if (fromPage != 0) {
            return fromPage;
        } else if (pageRanges != null) {
            return prLast;
        } else {
            return getMinPage();
        }
    }

    /**
     * Specifies, for jobs using these attributes, the last page (inclusive)
     * to be printed, if a range of pages is to be printed.
     * If this attribute is not specified, then the values from the pageRanges
     * attribute are used. If pageRanges and either or both of fromPage and
     * toPage are specified, pageRanges takes precedence. Specifying none of
     * pageRanges, fromPage, or toPage is equivalent to calling
     * setPageRanges(new int[][] { new int[] { <i>minPage</i> } });
     *
     * <p>
     *  对于使用这些属性的作业,指定要打印的最后一页(包括),如果要打印一页范围。如果未指定此属性,那么将使用来自pageRanges属性的值。
     * 如果指定了pageRanges和fromPage和toPage中的一个或两个,则页面宽度优先。
     * 没有指定pageRanges,fromPage或toPage等效于调用setPageRanges(new int [] [] {new int [] {<i> minPage </i>}});。
     * 
     * 
     * @param   toPage an integer greater than zero and greater than or equal
     *          to <i>fromPage</i> and greater than or equal to <i>minPage</i>
     *          and less than or equal to <i>maxPage</i>.
     * @throws  IllegalArgumentException if one or more of the above
     *          conditions is violated.
     */
    public void setToPage(int toPage) {
        if (toPage <= 0 ||
            (fromPage != 0 && toPage < fromPage) ||
            toPage < minPage ||
            toPage > maxPage) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "toPage");
        }
        this.toPage = toPage;
    }

    /**
     * Determines whether two JobAttributes are equal to each other.
     * <p>
     * Two JobAttributes are equal if and only if each of their attributes are
     * equal. Attributes of enumeration type are equal if and only if the
     * fields refer to the same unique enumeration object. A set of page
     * ranges is equal if and only if the sets are of equal length, each range
     * enumerates the same pages, and the ranges are in the same order.
     *
     * <p>
     *  确定两个JobAttributes是否相等。
     * <p>
     *  当且仅当它们的每个属性相等时,两个JobAttributes是相等的。当且仅当字段引用同一唯一枚举对象时,枚举类型的属性相等。
     * 当且仅当集合具有相等的长度,每个范围枚举相同的页面,并且范围以相同的顺序时,一组页面范围是相等的。
     * 
     * 
     * @param   obj the object whose equality will be checked.
     * @return  whether obj is equal to this JobAttribute according to the
     *          above criteria.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof JobAttributes)) {
            return false;
        }
        JobAttributes rhs = (JobAttributes)obj;

        if (fileName == null) {
            if (rhs.fileName != null) {
                return false;
            }
        } else {
            if (!fileName.equals(rhs.fileName)) {
                return false;
            }
        }

        if (pageRanges == null) {
            if (rhs.pageRanges != null) {
                return false;
            }
        } else {
            if (rhs.pageRanges == null ||
                    pageRanges.length != rhs.pageRanges.length) {
                return false;
            }
            for (int i = 0; i < pageRanges.length; i++) {
                if (pageRanges[i][0] != rhs.pageRanges[i][0] ||
                    pageRanges[i][1] != rhs.pageRanges[i][1]) {
                    return false;
                }
            }
        }

        if (printer == null) {
            if (rhs.printer != null) {
                return false;
            }
        } else {
            if (!printer.equals(rhs.printer)) {
                return false;
            }
        }

        return (copies == rhs.copies &&
                defaultSelection == rhs.defaultSelection &&
                destination == rhs.destination &&
                dialog == rhs.dialog &&
                fromPage == rhs.fromPage &&
                maxPage == rhs.maxPage &&
                minPage == rhs.minPage &&
                multipleDocumentHandling == rhs.multipleDocumentHandling &&
                prFirst == rhs.prFirst &&
                prLast == rhs.prLast &&
                sides == rhs.sides &&
                toPage == rhs.toPage);
    }

    /**
     * Returns a hash code value for this JobAttributes.
     *
     * <p>
     *  返回此JobAttributes的哈希码值。
     * 
     * 
     * @return  the hash code.
     */
    public int hashCode() {
        int rest = ((copies + fromPage + maxPage + minPage + prFirst + prLast +
                     toPage) * 31) << 21;
        if (pageRanges != null) {
            int sum = 0;
            for (int i = 0; i < pageRanges.length; i++) {
                sum += pageRanges[i][0] + pageRanges[i][1];
            }
            rest ^= (sum * 31) << 11;
        }
        if (fileName != null) {
            rest ^= fileName.hashCode();
        }
        if (printer != null) {
            rest ^= printer.hashCode();
        }
        return (defaultSelection.hashCode() << 6 ^
                destination.hashCode() << 5 ^
                dialog.hashCode() << 3 ^
                multipleDocumentHandling.hashCode() << 2 ^
                sides.hashCode() ^
                rest);
    }

    /**
     * Returns a string representation of this JobAttributes.
     *
     * <p>
     *  返回此JobAttributes的字符串表示形式。
     * 
     * @return  the string representation.
     */
    public String toString() {
        int[][] pageRanges = getPageRanges();
        String prStr = "[";
        boolean first = true;
        for (int i = 0; i < pageRanges.length; i++) {
            if (first) {
                first = false;
            } else {
                prStr += ",";
            }
            prStr += pageRanges[i][0] + ":" + pageRanges[i][1];
        }
        prStr += "]";

        return "copies=" + getCopies() + ",defaultSelection=" +
            getDefaultSelection() + ",destination=" + getDestination() +
            ",dialog=" + getDialog() + ",fileName=" + getFileName() +
            ",fromPage=" + getFromPage() + ",maxPage=" + getMaxPage() +
            ",minPage=" + getMinPage() + ",multiple-document-handling=" +
            getMultipleDocumentHandling() + ",page-ranges=" + prStr +
            ",printer=" + getPrinter() + ",sides=" + getSides() + ",toPage=" +
            getToPage();
    }
}

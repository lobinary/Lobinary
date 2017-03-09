/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.print;

import java.awt.AWTError;
import java.awt.HeadlessException;
import java.util.Enumeration;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import sun.security.action.GetPropertyAction;

/**
 * The <code>PrinterJob</code> class is the principal class that controls
 * printing. An application calls methods in this class to set up a job,
 * optionally to invoke a print dialog with the user, and then to print
 * the pages of the job.
 * <p>
 *  <code> PrinterJob </code>类是控制打印的主类。应用程序调用此类中的方法来设置作业,可选择调用与用户的打印对话框,然后打印作业的页面。
 * 
 */
public abstract class PrinterJob {

 /* Public Class Methods */

    /**
     * Creates and returns a <code>PrinterJob</code> which is initially
     * associated with the default printer.
     * If no printers are available on the system, a PrinterJob will still
     * be returned from this method, but <code>getPrintService()</code>
     * will return <code>null</code>, and calling
     * {@link #print() print} with this <code>PrinterJob</code> might
     * generate an exception.  Applications that need to determine if
     * there are suitable printers before creating a <code>PrinterJob</code>
     * should ensure that the array returned from
     * {@link #lookupPrintServices() lookupPrintServices} is not empty.
     * <p>
     *  创建并返回最初与默认打印机相关联的<code> PrinterJob </code>。
     * 如果系统上没有可用的打印机,则仍会从此方法返回PrinterJob,但<code> getPrintService()</code>将返回<code> null </code>,并调用{@link #print print}
     * 与此<code> PrinterJob </code>可能会生成异常。
     *  创建并返回最初与默认打印机相关联的<code> PrinterJob </code>。
     * 在创建<code> PrinterJob </code>之前需要确定是否有合适的打印机的应用程序应确保从{@link #lookupPrintServices()lookupPrintServices}
     * 返回的数组不为空。
     *  创建并返回最初与默认打印机相关联的<code> PrinterJob </code>。
     * 
     * 
     * @return a new <code>PrinterJob</code>.
     *
     * @throws  SecurityException if a security manager exists and its
     *          {@link java.lang.SecurityManager#checkPrintJobAccess}
     *          method disallows this thread from creating a print job request
     */
    public static PrinterJob getPrinterJob() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPrintJobAccess();
        }
        return (PrinterJob) java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction() {
            public Object run() {
                String nm = System.getProperty("java.awt.printerjob", null);
                try {
                    return (PrinterJob)Class.forName(nm).newInstance();
                } catch (ClassNotFoundException e) {
                    throw new AWTError("PrinterJob not found: " + nm);
                } catch (InstantiationException e) {
                 throw new AWTError("Could not instantiate PrinterJob: " + nm);
                } catch (IllegalAccessException e) {
                    throw new AWTError("Could not access PrinterJob: " + nm);
                }
            }
        });
    }

    /**
     * A convenience method which looks up 2D print services.
     * Services returned from this method may be installed on
     * <code>PrinterJob</code>s which support print services.
     * Calling this method is equivalent to calling
     * {@link javax.print.PrintServiceLookup#lookupPrintServices(
     * DocFlavor, AttributeSet)
     * PrintServiceLookup.lookupPrintServices()}
     * and specifying a Pageable DocFlavor.
     * <p>
     *  一种查找2D打印服务的方便方法。从此方法返回的服务可能安装在支持打印服务的<code> PrinterJob </code>上。
     * 调用此方法等效于调用{@link javax.print.PrintServiceLookup#lookupPrintServices(DocFlavor,AttributeSet)PrintServiceLookup.lookupPrintServices()}
     * 并指定一个Pageable DocFlavor。
     *  一种查找2D打印服务的方便方法。从此方法返回的服务可能安装在支持打印服务的<code> PrinterJob </code>上。
     * 
     * 
     * @return a possibly empty array of 2D print services.
     * @since     1.4
     */
    public static PrintService[] lookupPrintServices() {
        return PrintServiceLookup.
            lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
    }


    /**
     * A convenience method which locates factories for stream print
     * services which can image 2D graphics.
     * Sample usage :
     * <pre>{@code
     * FileOutputStream outstream;
     * StreamPrintService psPrinter;
     * String psMimeType = "application/postscript";
     * PrinterJob pj = PrinterJob.getPrinterJob();
     *
     * StreamPrintServiceFactory[] factories =
     *     PrinterJob.lookupStreamPrintServices(psMimeType);
     * if (factories.length > 0) {
     *     try {
     *         outstream = new File("out.ps");
     *         psPrinter =  factories[0].getPrintService(outstream);
     *         // psPrinter can now be set as the service on a PrinterJob
     *         pj.setPrintService(psPrinter)
     *     } catch (Exception e) {
     *         e.printStackTrace();
     *     }
     * }
     * }</pre>
     * Services returned from this method may be installed on
     * <code>PrinterJob</code> instances which support print services.
     * Calling this method is equivalent to calling
     * {@link javax.print.StreamPrintServiceFactory#lookupStreamPrintServiceFactories(DocFlavor, String)
     * StreamPrintServiceFactory.lookupStreamPrintServiceFactories()
     * } and specifying a Pageable DocFlavor.
     *
     * <p>
     * 一种方便的方法,定位可以成像2D图形的流打印服务的工厂。
     * 样例用法：<pre> {@ code FileOutputStream outstream; StreamPrintService psPrinter; String psMimeType ="application / postscript"; PrinterJob pj = PrinterJob.getPrinterJob();。
     * 一种方便的方法,定位可以成像2D图形的流打印服务的工厂。
     * 
     *  StreamPrintServiceFactory [] factories = PrinterJob.lookupStreamPrintServices(psMimeType); if(factor
     * ies.length> 0){try {outstream = new File("out.ps"); psPrinter = factories [0] .getPrintService(outstream); // psPrinter现在可以设置为PrinterJob上的服务pj.setPrintService(psPrinter)}
     *  catch(Exception e){e.printStackTrace(); }}} </pre>从此方法返回的服务可能安装在支持打印服务的<code> PrinterJob </code>实例上。
     * 调用此方法等效于调用{@link javax.print.StreamPrintServiceFactory#lookupStreamPrintServiceFactories(DocFlavor,String)StreamPrintServiceFactory.lookupStreamPrintServiceFactories()}
     * 并指定一个Pageable DocFlavor。
     * 
     * 
     * @param mimeType the required output format, or null to mean any format.
     * @return a possibly empty array of 2D stream print service factories.
     * @since     1.4
     */
    public static StreamPrintServiceFactory[]
        lookupStreamPrintServices(String mimeType) {
        return StreamPrintServiceFactory.lookupStreamPrintServiceFactories(
                                       DocFlavor.SERVICE_FORMATTED.PAGEABLE,
                                       mimeType);
    }


 /* Public Methods */

    /**
     * A <code>PrinterJob</code> object should be created using the
     * static {@link #getPrinterJob() getPrinterJob} method.
     * <p>
     *  应使用静态{@link #getPrinterJob()getPrinterJob}方法创建<code> PrinterJob </code>对象。
     * 
     */
    public PrinterJob() {
    }

    /**
     * Returns the service (printer) for this printer job.
     * Implementations of this class which do not support print services
     * may return null.  null will also be returned if no printers are
     * available.
     * <p>
     *  返回此打印机作业的服务(打印机)。此类的不支持打印服务的实现可能返回null。如果没有可用的打印机,也将返回null。
     * 
     * 
     * @return the service for this printer job.
     * @see #setPrintService(PrintService)
     * @see #getPrinterJob()
     * @since     1.4
     */
    public PrintService getPrintService() {
        return null;
    }

    /**
     * Associate this PrinterJob with a new PrintService.
     * This method is overridden by subclasses which support
     * specifying a Print Service.
     *
     * Throws <code>PrinterException</code> if the specified service
     * cannot support the <code>Pageable</code> and
     * <code>Printable</code> interfaces necessary to support 2D printing.
     * <p>
     *  将此PrinterJob与一个新的PrintService相关联。此方法被支持指定打印服务的子类覆盖。
     * 
     * 如果指定的服务不支持支持2D打印所需的<code> Pageable </code>和<code>可打印</code>界面,则抛出<code> PrinterException </code>。
     * 
     * 
     * @param service a print service that supports 2D printing
     * @exception PrinterException if the specified service does not support
     * 2D printing, or this PrinterJob class does not support
     * setting a 2D print service, or the specified service is
     * otherwise not a valid print service.
     * @see #getPrintService
     * @since     1.4
     */
    public void setPrintService(PrintService service)
        throws PrinterException {
            throw new PrinterException(
                         "Setting a service is not supported on this class");
    }

    /**
     * Calls <code>painter</code> to render the pages.  The pages in the
     * document to be printed by this
     * <code>PrinterJob</code> are rendered by the {@link Printable}
     * object, <code>painter</code>.  The {@link PageFormat} for each page
     * is the default page format.
     * <p>
     *  调用<code> painter </code>来呈现页面。
     * 要由<code> PrinterJob </code>打印的文档中的页面由{@link Printable}对象<code>画家</code>呈现。
     * 每个页面的{@link PageFormat}是默认的页面格式。
     * 
     * 
     * @param painter the <code>Printable</code> that renders each page of
     * the document.
     */
    public abstract void setPrintable(Printable painter);

    /**
     * Calls <code>painter</code> to render the pages in the specified
     * <code>format</code>.  The pages in the document to be printed by
     * this <code>PrinterJob</code> are rendered by the
     * <code>Printable</code> object, <code>painter</code>. The
     * <code>PageFormat</code> of each page is <code>format</code>.
     * <p>
     *  调用<code> painter </code>以指定的<code>格式</code>呈现页面。
     * 要由<code> PrinterJob </code>打印的文档中的页面由<code> Printable </code>对象<code>画家</code>呈现。
     * 每个页面的<code> PageFormat </code>是<code> format </code>。
     * 
     * 
     * @param painter the <code>Printable</code> called to render
     *          each page of the document
     * @param format the size and orientation of each page to
     *                   be printed
     */
    public abstract void setPrintable(Printable painter, PageFormat format);

    /**
     * Queries <code>document</code> for the number of pages and
     * the <code>PageFormat</code> and <code>Printable</code> for each
     * page held in the <code>Pageable</code> instance,
     * <code>document</code>.
     * <p>
     *  在<code> Pageable </code>实例中保存的每个页面的<page> </code>查询<code> document </code>,<code> PageFormat </code>
     * 代码>文档</code>。
     * 
     * 
     * @param document the pages to be printed. It can not be
     * <code>null</code>.
     * @exception NullPointerException the <code>Pageable</code> passed in
     * was <code>null</code>.
     * @see PageFormat
     * @see Printable
     */
    public abstract void setPageable(Pageable document)
        throws NullPointerException;

    /**
     * Presents a dialog to the user for changing the properties of
     * the print job.
     * This method will display a native dialog if a native print
     * service is selected, and user choice of printers will be restricted
     * to these native print services.
     * To present the cross platform print dialog for all services,
     * including native ones instead use
     * <code>printDialog(PrintRequestAttributeSet)</code>.
     * <p>
     * PrinterJob implementations which can use PrintService's will update
     * the PrintService for this PrinterJob to reflect the new service
     * selected by the user.
     * <p>
     *  向用户提供用于更改打印作业属性的对话框。如果选择了本地打印服务,则此方法将显示本地对话框,并且打印机的用户选择将仅限于这些本地打印服务。
     * 要显示所有服务的跨平台打印对话框,包括原生代码,请使用<code> printDialog(PrintRequestAttributeSet)</code>。
     * <p>
     *  可以使用PrintService的PrinterJob实现将更新此PrinterJob的PrintService以反映用户选择的新服务。
     * 
     * 
     * @return <code>true</code> if the user does not cancel the dialog;
     * <code>false</code> otherwise.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public abstract boolean printDialog() throws HeadlessException;

    /**
     * A convenience method which displays a cross-platform print dialog
     * for all services which are capable of printing 2D graphics using the
     * <code>Pageable</code> interface. The selected printer when the
     * dialog is initially displayed will reflect the print service currently
     * attached to this print job.
     * If the user changes the print service, the PrinterJob will be
     * updated to reflect this, unless the user cancels the dialog.
     * As well as allowing the user to select the destination printer,
     * the user can also select values of various print request attributes.
     * <p>
     * The attributes parameter on input will reflect the applications
     * required initial selections in the user dialog. Attributes not
     * specified display using the default for the service. On return it
     * will reflect the user's choices. Selections may be updated by
     * the implementation to be consistent with the supported values
     * for the currently selected print service.
     * <p>
     * As the user scrolls to a new print service selection, the values
     * copied are based on the settings for the previous service, together
     * with any user changes. The values are not based on the original
     * settings supplied by the client.
     * <p>
     * With the exception of selected printer, the PrinterJob state is
     * not updated to reflect the user's changes.
     * For the selections to affect a printer job, the attributes must
     * be specified in the call to the
     * <code>print(PrintRequestAttributeSet)</code> method. If using
     * the Pageable interface, clients which intend to use media selected
     * by the user must create a PageFormat derived from the user's
     * selections.
     * If the user cancels the dialog, the attributes will not reflect
     * any changes made by the user.
     * <p>
     * 一种方便的方法,为所有能够使用<code> Pageable </code>界面打印2D图形的服务显示跨平台打印对话框。最初显示对话框时所选的打印机将反映当前附加到此打印作业的打印服务。
     * 如果用户更改打印服务,则PrinterJob将被更新以反映此情况,除非用户取消对话框。除了允许用户选择目的地打印机之外,用户还可以选择各种打印请求属性的值。
     * <p>
     *  输入上的attributes参数将反映用户对话框中所需的初始选择的应用程序。未指定的属性使用服务的默认值显示。返回时,它将反映用户的选择。选择可以通过实现来更新以与当前选择的打印服务的支持值一致。
     * <p>
     *  当用户滚动到新的打印服务选择时,复制的值基于先前服务的设置以及任何用户改变。这些值不基于客户端提供的原始设置。
     * <p>
     * 除了所选的打印机,PrinterJob状态不会更新以反映用户的更改。
     * 对于影响打印机作业的选择,必须在调用<code> print(PrintRequestAttributeSet)</code>方法中指定属性。
     * 如果使用Pageable界面,打算使用用户选择的媒体的客户端必须创建一个从用户选择派生的PageFormat。如果用户取消对话框,那么属性将不反映用户做出的任何更改。
     * 
     * 
     * @param attributes on input is application supplied attributes,
     * on output the contents are updated to reflect user choices.
     * This parameter may not be null.
     * @return <code>true</code> if the user does not cancel the dialog;
     * <code>false</code> otherwise.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @exception NullPointerException if <code>attributes</code> parameter
     * is null.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since     1.4
     *
     */
    public boolean printDialog(PrintRequestAttributeSet attributes)
        throws HeadlessException {

        if (attributes == null) {
            throw new NullPointerException("attributes");
        }
        return printDialog();
    }

    /**
     * Displays a dialog that allows modification of a
     * <code>PageFormat</code> instance.
     * The <code>page</code> argument is used to initialize controls
     * in the page setup dialog.
     * If the user cancels the dialog then this method returns the
     * original <code>page</code> object unmodified.
     * If the user okays the dialog then this method returns a new
     * <code>PageFormat</code> object with the indicated changes.
     * In either case, the original <code>page</code> object is
     * not modified.
     * <p>
     *  显示允许修改<code> PageFormat </code>实例的对话框。 <code> page </code>参数用于在页面设置对话框中初始化控件。
     * 如果用户取消对话框,则此方法将返回原始的<code> page </code>对象。如果用户确认对话框,那么此方法将返回一个新的具有指定更改的<code> PageFormat </code>对象。
     * 在任一情况下,原始<code>页</code>对象不被修改。
     * 
     * 
     * @param page the default <code>PageFormat</code> presented to the
     *                  user for modification
     * @return    the original <code>page</code> object if the dialog
     *            is cancelled; a new <code>PageFormat</code> object
     *            containing the format indicated by the user if the
     *            dialog is acknowledged.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since     1.2
     */
    public abstract PageFormat pageDialog(PageFormat page)
        throws HeadlessException;

    /**
     * A convenience method which displays a cross-platform page setup dialog.
     * The choices available will reflect the print service currently
     * set on this PrinterJob.
     * <p>
     * The attributes parameter on input will reflect the client's
     * required initial selections in the user dialog. Attributes which are
     * not specified display using the default for the service. On return it
     * will reflect the user's choices. Selections may be updated by
     * the implementation to be consistent with the supported values
     * for the currently selected print service.
     * <p>
     * The return value will be a PageFormat equivalent to the
     * selections in the PrintRequestAttributeSet.
     * If the user cancels the dialog, the attributes will not reflect
     * any changes made by the user, and the return value will be null.
     * <p>
     *  显示跨平台页面设置对话框的方便方法。可用的选项将反映此PrinterJob上当前设置的打印服务。
     * <p>
     *  输入上的attributes参数将反映客户端在用户对话框中所需的初始选择。未指定的属性使用服务的默认值显示。返回时,它将反映用户的选择。选择可以通过实现来更新以与当前选择的打印服务的支持值一致。
     * <p>
     * 返回值将是一个等价于PrintRequestAttributeSet中的选择的PageFormat。如果用户取消对话框,那么属性将不反映用户做出的任何更改,并且返回值将为null。
     * 
     * 
     * @param attributes on input is application supplied attributes,
     * on output the contents are updated to reflect user choices.
     * This parameter may not be null.
     * @return a page format if the user does not cancel the dialog;
     * <code>null</code> otherwise.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @exception NullPointerException if <code>attributes</code> parameter
     * is null.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since     1.4
     *
     */
    public PageFormat pageDialog(PrintRequestAttributeSet attributes)
        throws HeadlessException {

        if (attributes == null) {
            throw new NullPointerException("attributes");
        }
        return pageDialog(defaultPage());
    }

    /**
     * Clones the <code>PageFormat</code> argument and alters the
     * clone to describe a default page size and orientation.
     * <p>
     *  克隆<code> PageFormat </code>参数,并更改克隆以描述默认页面大小和方向。
     * 
     * 
     * @param page the <code>PageFormat</code> to be cloned and altered
     * @return clone of <code>page</code>, altered to describe a default
     *                      <code>PageFormat</code>.
     */
    public abstract PageFormat defaultPage(PageFormat page);

    /**
     * Creates a new <code>PageFormat</code> instance and
     * sets it to a default size and orientation.
     * <p>
     *  创建一个新的<code> PageFormat </code>实例,并将其设置为默认大小和方向。
     * 
     * 
     * @return a <code>PageFormat</code> set to a default size and
     *          orientation.
     */
    public PageFormat defaultPage() {
        return defaultPage(new PageFormat());
    }

    /**
     * Calculates a <code>PageFormat</code> with values consistent with those
     * supported by the current <code>PrintService</code> for this job
     * (ie the value returned by <code>getPrintService()</code>) and media,
     * printable area and orientation contained in <code>attributes</code>.
     * <p>
     * Calling this method does not update the job.
     * It is useful for clients that have a set of attributes obtained from
     * <code>printDialog(PrintRequestAttributeSet attributes)</code>
     * and need a PageFormat to print a Pageable object.
     * <p>
     *  使用与此作业的当前<code> PrintService </code>(即<code> getPrintService()</code>返回的值)和媒体支持的值一致的值计算<code> PageFo
     * rmat </code>可打印区域和方向包含在<code>属性</code>中。
     * <p>
     *  调用此方法不会更新作业。
     * 它对于具有从<code> printDialog(PrintRequestAttributeSet属性)</code>获得的一组属性的客户端很有用,并且需要一个PageFormat来打印一个Pageab
     * le对象。
     *  调用此方法不会更新作业。
     * 
     * 
     * @param attributes a set of printing attributes, for example obtained
     * from calling printDialog. If <code>attributes</code> is null a default
     * PageFormat is returned.
     * @return a <code>PageFormat</code> whose settings conform with
     * those of the current service and the specified attributes.
     * @since 1.6
     */
    public PageFormat getPageFormat(PrintRequestAttributeSet attributes) {

        PrintService service = getPrintService();
        PageFormat pf = defaultPage();

        if (service == null || attributes == null) {
            return pf;
        }

        Media media = (Media)attributes.get(Media.class);
        MediaPrintableArea mpa =
            (MediaPrintableArea)attributes.get(MediaPrintableArea.class);
        OrientationRequested orientReq =
           (OrientationRequested)attributes.get(OrientationRequested.class);

        if (media == null && mpa == null && orientReq == null) {
           return pf;
        }
        Paper paper = pf.getPaper();

        /* If there's a media but no media printable area, we can try
         * to retrieve the default value for mpa and use that.
         * <p>
         *  以检索mpa的默认值并使用它。
         * 
         */
        if (mpa == null && media != null &&
            service.isAttributeCategorySupported(MediaPrintableArea.class)) {
            Object mpaVals =
                service.getSupportedAttributeValues(MediaPrintableArea.class,
                                                    null, attributes);
            if (mpaVals instanceof MediaPrintableArea[] &&
                ((MediaPrintableArea[])mpaVals).length > 0) {
                mpa = ((MediaPrintableArea[])mpaVals)[0];
            }
        }

        if (media != null &&
            service.isAttributeValueSupported(media, null, attributes)) {
            if (media instanceof MediaSizeName) {
                MediaSizeName msn = (MediaSizeName)media;
                MediaSize msz = MediaSize.getMediaSizeForName(msn);
                if (msz != null) {
                    double inch = 72.0;
                    double paperWid = msz.getX(MediaSize.INCH) * inch;
                    double paperHgt = msz.getY(MediaSize.INCH) * inch;
                    paper.setSize(paperWid, paperHgt);
                    if (mpa == null) {
                        paper.setImageableArea(inch, inch,
                                               paperWid-2*inch,
                                               paperHgt-2*inch);
                    }
                }
            }
        }

        if (mpa != null &&
            service.isAttributeValueSupported(mpa, null, attributes)) {
            float [] printableArea =
                mpa.getPrintableArea(MediaPrintableArea.INCH);
            for (int i=0; i < printableArea.length; i++) {
                printableArea[i] = printableArea[i]*72.0f;
            }
            paper.setImageableArea(printableArea[0], printableArea[1],
                                   printableArea[2], printableArea[3]);
        }

        if (orientReq != null &&
            service.isAttributeValueSupported(orientReq, null, attributes)) {
            int orient;
            if (orientReq.equals(OrientationRequested.REVERSE_LANDSCAPE)) {
                orient = PageFormat.REVERSE_LANDSCAPE;
            } else if (orientReq.equals(OrientationRequested.LANDSCAPE)) {
                orient = PageFormat.LANDSCAPE;
            } else {
                orient = PageFormat.PORTRAIT;
            }
            pf.setOrientation(orient);
        }

        pf.setPaper(paper);
        pf = validatePage(pf);
        return pf;
    }

    /**
     * Returns the clone of <code>page</code> with its settings
     * adjusted to be compatible with the current printer of this
     * <code>PrinterJob</code>.  For example, the returned
     * <code>PageFormat</code> could have its imageable area
     * adjusted to fit within the physical area of the paper that
     * is used by the current printer.
     * <p>
     *  返回<code> page </code>的克隆,其设置已调整为与此<code> PrinterJob </code>的当前打印机兼容。
     * 例如,返回的<code> PageFormat </code>可以将其可成像区域调整为适合当前打印机使用的纸张的物理区域。
     * 
     * 
     * @param page the <code>PageFormat</code> that is cloned and
     *          whose settings are changed to be compatible with
     *          the current printer
     * @return a <code>PageFormat</code> that is cloned from
     *          <code>page</code> and whose settings are changed
     *          to conform with this <code>PrinterJob</code>.
     */
    public abstract PageFormat validatePage(PageFormat page);

    /**
     * Prints a set of pages.
     * <p>
     *  打印一组页面。
     * 
     * 
     * @exception PrinterException an error in the print system
     *            caused the job to be aborted.
     * @see Book
     * @see Pageable
     * @see Printable
     */
    public abstract void print() throws PrinterException;

   /**
     * Prints a set of pages using the settings in the attribute
     * set. The default implementation ignores the attribute set.
     * <p>
     * Note that some attributes may be set directly on the PrinterJob
     * by equivalent method calls, (for example), copies:
     * <code>setcopies(int)</code>, job name: <code>setJobName(String)</code>
     * and specifying media size and orientation though the
     * <code>PageFormat</code> object.
     * <p>
     * If a supported attribute-value is specified in this attribute set,
     * it will take precedence over the API settings for this print()
     * operation only.
     * The following behaviour is specified for PageFormat:
     * If a client uses the Printable interface, then the
     * <code>attributes</code> parameter to this method is examined
     * for attributes which specify media (by size), orientation, and
     * imageable area, and those are used to construct a new PageFormat
     * which is passed to the Printable object's print() method.
     * See {@link Printable} for an explanation of the required
     * behaviour of a Printable to ensure optimal printing via PrinterJob.
     * For clients of the Pageable interface, the PageFormat will always
     * be as supplied by that interface, on a per page basis.
     * <p>
     * These behaviours allow an application to directly pass the
     * user settings returned from
     * <code>printDialog(PrintRequestAttributeSet attributes</code> to
     * this print() method.
     * <p>
     *
     * <p>
     *  使用属性集中的设置打印一组页面。默认实现忽略属性集。
     * <p>
     * 注意,一些属性可以通过等效的方法调用(例如)直接在PrinterJob上设置副本：<code> setcopies(int)</code>,作业名称：<code> setJobName(String)</code>
     * 通过<code> PageFormat </code>对象指定介质大小和方向。
     * <p>
     *  如果在此属性集中指定了支持的属性值,则它将优先于仅用于此print()操作的API设置。
     * 为PageFormat指定了以下行为：如果客户端使用Printable接口,则会针对指定媒体(按大小),方向和可成像区域的属性检查此方法的<code> attributes </code>参数,用于构造
     * 一个新的PageFormat,它被传递给Printable对象的print()方法。
     *  如果在此属性集中指定了支持的属性值,则它将优先于仅用于此print()操作的API设置。
     * 有关Printable所需行为的说明,请参阅{@link Printable},以确保通过PrinterJob进行最佳打印。
     * 对于Pageable接口的客户端,PageFormat将始终是由该接口提供的,以每页为基础。
     * <p>
     *  这些行为允许应用程序直接将从<code> printDialog(PrintRequestAttributeSet attributes </code>)返回的用户设置传递给此print()方法。
     * <p>
     * 
     * 
     * @param attributes a set of attributes for the job
     * @exception PrinterException an error in the print system
     *            caused the job to be aborted.
     * @see Book
     * @see Pageable
     * @see Printable
     * @since 1.4
     */
    public void print(PrintRequestAttributeSet attributes)
        throws PrinterException {
        print();
    }

    /**
     * Sets the number of copies to be printed.
     * <p>
     *  设置要打印的份数。
     * 
     * 
     * @param copies the number of copies to be printed
     * @see #getCopies
     */
    public abstract void setCopies(int copies);

    /**
     * Gets the number of copies to be printed.
     * <p>
     *  获取要打印的份数。
     * 
     * 
     * @return the number of copies to be printed.
     * @see #setCopies
     */
    public abstract int getCopies();

    /**
     * Gets the name of the printing user.
     * <p>
     *  获取打印用户的名称。
     * 
     * 
     * @return the name of the printing user
     */
    public abstract String getUserName();

    /**
     * Sets the name of the document to be printed.
     * The document name can not be <code>null</code>.
     * <p>
     *  设置要打印的文档的名称。文档名称不能为<code> null </code>。
     * 
     * 
     * @param jobName the name of the document to be printed
     * @see #getJobName
     */
    public abstract void setJobName(String jobName);

    /**
     * Gets the name of the document to be printed.
     * <p>
     *  获取要打印的文档的名称。
     * 
     * 
     * @return the name of the document to be printed.
     * @see #setJobName
     */
    public abstract String getJobName();

    /**
     * Cancels a print job that is in progress.  If
     * {@link #print() print} has been called but has not
     * returned then this method signals
     * that the job should be cancelled at the next
     * chance. If there is no print job in progress then
     * this call does nothing.
     * <p>
     * 取消正在进行的打印作业。如果{@link #print()print}被调用,但没有返回,那么这个方法表示应该在下一次机会取消作业。如果没有正在进行的打印作业,则此调用不执行任何操作。
     * 
     */
    public abstract void cancel();

    /**
     * Returns <code>true</code> if a print job is
     * in progress, but is going to be cancelled
     * at the next opportunity; otherwise returns
     * <code>false</code>.
     * <p>
     *  如果打印作业正在进行,则返回<code> true </code>,但会在下一个机会时取消;否则返回<code> false </code>。
     * 
     * @return <code>true</code> if the job in progress
     * is going to be cancelled; <code>false</code> otherwise.
     */
    public abstract boolean isCancelled();

}

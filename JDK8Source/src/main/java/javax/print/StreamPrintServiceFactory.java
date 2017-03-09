/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Iterator;

import javax.print.DocFlavor;

import sun.awt.AppContext;
import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;

/**
 * A <code>StreamPrintServiceFactory</code> is the factory for
 * {@link StreamPrintService} instances,
 * which can print to an output stream in a particular
 * document format described as a mime type.
 * A typical output document format may be Postscript(TM).
 * <p>
 * This class is implemented by a service and located by the
 * implementation using the
 * <a href="../../../technotes/guides/jar/jar.html#Service Provider">
 * SPI JAR File specification</a>.
 * <p>
 * Applications locate instances of this class by calling the
 * {@link #lookupStreamPrintServiceFactories(DocFlavor, String)} method.
 * <p>
 * Applications can use a <code>StreamPrintService</code> obtained from a
 * factory in place of a <code>PrintService</code> which represents a
 * physical printer device.
 * <p>
 *  <code> StreamPrintServiceFactory </code>是{@link StreamPrintService}实例的工厂,可以打印到以mime类型描述的特定文档格式的输出流。
 * 典型的输出文档格式可以是Postscript TM。
 * <p>
 *  这个类是由一个服务实现的,并通过实现使用
 * <a href="../../../technotes/guides/jar/jar.html#Service Provider">
 *  SPI JAR文件规范</a>。
 * <p>
 *  应用程序通过调用{@link #lookupStreamPrintServiceFactories(DocFlavor,String)}方法来查找此类的实例。
 * <p>
 *  应用程序可以使用从工厂获取的<code> StreamPrintService </code>代替代表物理打印机设备的<code> PrintService </code>。
 * 
 */

public abstract class StreamPrintServiceFactory {

    static class Services {
        private ArrayList listOfFactories = null;
    }

    private static Services getServices() {
        Services services =
            (Services)AppContext.getAppContext().get(Services.class);
        if (services == null) {
            services = new Services();
            AppContext.getAppContext().put(Services.class, services);
        }
        return services;
    }

    private static ArrayList getListOfFactories() {
        return getServices().listOfFactories;
    }

    private static ArrayList initListOfFactories() {
        ArrayList listOfFactories = new ArrayList();
        getServices().listOfFactories = listOfFactories;
        return listOfFactories;
    }

    /**
     * Locates factories for print services that can be used with
     * a print job to output a stream of data in the
     * format specified by {@code outputMimeType}.
     * <p>
     * The {@code outputMimeType} parameter describes the document type that
     * you want to create, whereas the {@code flavor} parameter describes the
     * format in which the input data will be provided by the application
     * to the {@code StreamPrintService}.
     * <p>
     * Although null is an acceptable value to use in the lookup of stream
     * printing services, it's typical to search for a particular
     * desired format, such as Postscript(TM).
     * <p>
     * <p>
     *  找到可与打印作业一起使用的打印服务的工厂以{@code outputMimeType}指定的格式输出数据流。
     * <p>
     *  {@code outputMimeType}参数描述您要创建的文档类型,而{@code flavor}参数描述了应用程序将输入数据提供给{@code StreamPrintService}的格式。
     * <p>
     *  尽管null是用于查找流打印服务的可接受的值,但是通常搜索特定期望的格式,例如Postscript TM。
     * <p>
     * 
     * @param flavor of the input document type - null means match all
     * types.
     * @param outputMimeType representing the required output format, used to
     * identify suitable stream printer factories. A value of null means
     * match all formats.
     * @return - matching factories for stream print service instance,
     *           empty if no suitable factories could be located.
     */
     public static StreamPrintServiceFactory[]
         lookupStreamPrintServiceFactories(DocFlavor flavor,
                                           String outputMimeType) {

         ArrayList list = getFactories(flavor, outputMimeType);
         return (StreamPrintServiceFactory[])
               (list.toArray(new StreamPrintServiceFactory[list.size()]));
     }

    /** Queries the factory for the document format that is emitted
     * by printers obtained from this factory.
     *
     * <p>
     *  通过从这个工厂获得的打印机。
     * 
     * 
     * @return the output format described as a mime type.
     */
    public abstract String getOutputFormat();

    /**
     * Queries the factory for the document flavors that can be accepted
     * by printers obtained from this factory.
     * <p>
     * 查询工厂以查找从此工厂获得的打印机可以接受的文档风格。
     * 
     * 
     * @return array of supported doc flavors.
     */
    public abstract DocFlavor[] getSupportedDocFlavors();

    /**
     * Returns a <code>StreamPrintService</code> that can print to
     * the specified output stream.
     * The output stream is created and managed by the application.
     * It is the application's responsibility to close the stream and
     * to ensure that this Printer is not reused.
     * The application should not close this stream until any print job
     * created from the printer is complete. Doing so earlier may generate
     * a <code>PrinterException</code> and an event indicating that the
     * job failed.
     * <p>
     * Whereas a <code>PrintService</code> connected to a physical printer
     * can be reused,
     * a <code>StreamPrintService</code> connected to a stream cannot.
     * The underlying <code>StreamPrintService</code> may be disposed by
     * the print system with
     * the {@link StreamPrintService#dispose() dispose} method
     * before returning from the
     * {@link DocPrintJob#print(Doc, javax.print.attribute.PrintRequestAttributeSet) print}
     * method of <code>DocPrintJob</code> so that the print system knows
     * this printer is no longer usable.
     * This is equivalent to a physical printer going offline - permanently.
     * Applications may supply a null print stream to create a queryable
     * service. It is not valid to create a PrintJob for such a stream.
     * Implementations which allocate resources on construction should examine
     * the stream and may wish to only allocate resources if the stream is
     * non-null.
     * <p>
     * <p>
     *  返回可打印到指定输出流的<code> StreamPrintService </code>。输出流由应用程序创建和管理。应用程序有责任关闭流,并确保此打印机不被重复使用。
     * 应用程序不应关闭此流,直到从打印机创建的任何打印作业完成。这样做可能会生成<code> PrinterException </code>和指示作业失败的事件。
     * <p>
     *  连接到物理打印机的<code> PrintService </code>可以重复使用,但连接到流的<code> StreamPrintService </code>不能。
     * 在从{@link DocPrintJob#print(Doc,javax.print.attribute.PrintRequestAttributeSet)返回之前,底层的<code> StreamPrintService </code>可以由打印系统使用{@link StreamPrintService#dispose )print}
     * 
     * @param out destination stream for generated output.
     * @return a PrintService which will generate the format specified by the
     * DocFlavor supported by this Factory.
     */
    public abstract StreamPrintService getPrintService(OutputStream out);


    private static ArrayList getAllFactories() {
        synchronized (StreamPrintServiceFactory.class) {

          ArrayList listOfFactories = getListOfFactories();
            if (listOfFactories != null) {
                return listOfFactories;
            } else {
                listOfFactories = initListOfFactories();
            }

            try {
                java.security.AccessController.doPrivileged(
                     new java.security.PrivilegedExceptionAction() {
                        public Object run() {
                            Iterator<StreamPrintServiceFactory> iterator =
                                ServiceLoader.load
                                (StreamPrintServiceFactory.class).iterator();
                            ArrayList lof = getListOfFactories();
                            while (iterator.hasNext()) {
                                try {
                                    lof.add(iterator.next());
                                }  catch (ServiceConfigurationError err) {
                                     /* In the applet case, we continue */
                                    if (System.getSecurityManager() != null) {
                                        err.printStackTrace();
                                    } else {
                                        throw err;
                                    }
                                }
                            }
                            return null;
                        }
                });
            } catch (java.security.PrivilegedActionException e) {
            }
            return listOfFactories;
        }
    }

    private static boolean isMember(DocFlavor flavor, DocFlavor[] flavors) {
        for (int f=0; f<flavors.length; f++ ) {
            if (flavor.equals(flavors[f])) {
                return true;
            }
        }
        return false;
    }

    private static ArrayList getFactories(DocFlavor flavor, String outType) {

        if (flavor == null && outType == null) {
            return getAllFactories();
        }

        ArrayList list = new ArrayList();
        Iterator iterator = getAllFactories().iterator();
        while (iterator.hasNext()) {
            StreamPrintServiceFactory factory =
                (StreamPrintServiceFactory)iterator.next();
            if ((outType == null ||
                 outType.equalsIgnoreCase(factory.getOutputFormat())) &&
                (flavor == null ||
                 isMember(flavor, factory.getSupportedDocFlavors()))) {
                list.add(factory);
            }
        }

        return list;
    }

}

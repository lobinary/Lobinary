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

package javax.print;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.KeyboardFocusManager;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.Fidelity;

import sun.print.ServiceDialog;
import sun.print.SunAlternateMedia;

/** This class is a collection of UI convenience methods which provide a
 * graphical user dialog for browsing print services looked up through the Java
 * Print Service API.
 * <p>
 * The dialogs follow a standard pattern of acting as a continue/cancel option
 *for a user as well as allowing the user to select the print service to use
 *and specify choices such as paper size and number of copies.
 * <p>
 * <p>
 * The dialogs are designed to work with pluggable print services though the
 * public APIs of those print services.
 * <p>
 * If a print service provides any vendor extensions these may be made
 * accessible to the user through a vendor supplied tab panel Component.
 * Such a vendor extension is encouraged to use Swing! and to support its
 * accessibility APIs.
 * The vendor extensions should return the settings as part of the
 * AttributeSet.
 * Applications which want to preserve the user settings should use those
 * settings to specify the print job.
 * Note that this class is not referenced by any other part of the Java
 * Print Service and may not be included in profiles which cannot depend
 * on the presence of the AWT packages.
 * <p>
 *  图形用户对话框,用于浏览通过Java打印服务API查找的打印服务。
 * <p>
 *  对话框遵循充当继续/取消选项或用户的标准模式,以及允许用户选择要使用的打印服务,并指定诸如纸张尺寸和份数之类的选择。
 * <p>
 * <p>
 *  这些对话框旨在通过这些打印服务的公共API来处理可插入打印服务。
 * <p>
 *  如果打印服务提供任何供应商扩展,则可以通过供应商提供的选项卡面板组件使用户可以访问这些扩展。鼓励这样的供应商扩展使用Swing！并支持其可访问性API。
 * 供应商扩展应将设置作为AttributeSet的一部分返回。要保留用户设置的应用程序应使用这些设置指定打印作业。
 * 注意,此类不被Java打印服务的任何其他部分引用,并且可能不包括在不能依赖于AWT包的存在的简档中。
 * 
 */

public class ServiceUI {


    /**
     * Presents a dialog to the user for selecting a print service (printer).
     * It is displayed at the location specified by the application and
     * is modal.
     * If the specification is invalid or would make the dialog not visible it
     * will be displayed at a location determined by the implementation.
     * The dialog blocks its calling thread and is application modal.
     * <p>
     * The dialog may include a tab panel with custom UI lazily obtained from
     * the PrintService's ServiceUIFactory when the PrintService is browsed.
     * The dialog will attempt to locate a MAIN_UIROLE first as a JComponent,
     * then as a Panel. If there is no ServiceUIFactory or no matching role
     * the custom tab will be empty or not visible.
     * <p>
     * The dialog returns the print service selected by the user if the user
     * OK's the dialog and null if the user cancels the dialog.
     * <p>
     * An application must pass in an array of print services to browse.
     * The array must be non-null and non-empty.
     * Typically an application will pass in only PrintServices capable
     * of printing a particular document flavor.
     * <p>
     * An application may pass in a PrintService to be initially displayed.
     * A non-null parameter must be included in the array of browsable
     * services.
     * If this parameter is null a service is chosen by the implementation.
     * <p>
     * An application may optionally pass in the flavor to be printed.
     * If this is non-null choices presented to the user can be better
     * validated against those supported by the services.
     * An application must pass in a PrintRequestAttributeSet for returning
     * user choices.
     * On calling the PrintRequestAttributeSet may be empty, or may contain
     * application-specified values.
     * <p>
     * These are used to set the initial settings for the initially
     * displayed print service. Values which are not supported by the print
     * service are ignored. As the user browses print services, attributes
     * and values are copied to the new display. If a user browses a
     * print service which does not support a particular attribute-value, the
     * default for that service is used as the new value to be copied.
     * <p>
     * If the user cancels the dialog, the returned attributes will not reflect
     * any changes made by the user.
     *
     * A typical basic usage of this method may be :
     * <pre>{@code
     * PrintService[] services = PrintServiceLookup.lookupPrintServices(
     *                            DocFlavor.INPUT_STREAM.JPEG, null);
     * PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
     * if (services.length > 0) {
     *    PrintService service =  ServiceUI.printDialog(null, 50, 50,
     *                                               services, services[0],
     *                                               null,
     *                                               attributes);
     *    if (service != null) {
     *     ... print ...
     *    }
     * }
     * }</pre>
     * <p>

     * <p>
     * 向用户呈现用于选择打印服务(打印机)的对话框。它显示在应用程序指定的位置,并且是模态的。如果规范无效或使对话框不可见,它将显示在由实现确定的位置。对话框阻塞其调用线程,并且是应用程序模态。
     * <p>
     *  该对话框可以包括具有当浏览PrintService时从PrintService的ServiceUIFactory延迟获得的自定义UI的选项卡面板。
     * 对话框将首先尝试将MAIN_UIROLE定位为JComponent,然后再定位为Panel。如果没有ServiceUIFactory或没有匹配的角色,自定义选项卡将为空或不可见。
     * <p>
     *  如果用户确定是对话框,则对话框返回用户选择的打印服务,如果用户取消对话框,则返回null。
     * <p>
     *  应用程序必须传入打印服务数组才能浏览。数组必须为非空且非空。通常,应用程序将仅传递能够打印特定文档味道的PrintServices。
     * <p>
     *  应用程序可以传入要初始显示的PrintService。可浏览服务数组中必须包含非空参数。如果此参数为null,则实施选择服务。
     * <p>
     * 应用可以可选地传递要打印的香料。如果这是非空,则呈现给用户的选择可以更好地针对由服务支持的选项来验证。应用程序必须传入PrintRequestAttributeSet以返回用户选择。
     * 调用PrintRequestAttributeSet可能为空,或者可能包含应用程序指定的值。
     * <p>
     *  这些用于设置最初显示的打印服务的初始设置。忽略打印​​服务不支持的值。当用户浏览打印服务时,属性和值被复制到新的显示器。如果用户浏览不支持特定属性值的打印服务,则将该服务的默认值用作要复制的新值。
     * <p>
     *  如果用户取消对话框,返回的属性将不反映用户做出的任何更改。
     * 
     *  此方法的典型基本用法可能是：<pre> {@ code PrintService [] services = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.JPEG,null); PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet(); if(services.length> 0){PrintService service = ServiceUI.printDialog(null,50,50,services,services [0],null,attributes); if(service！= null){... print ...}
     * }} </pre>。
     * <p>
     * 
     * 
     * @param gc used to select screen. null means primary or default screen.
     * @param x location of dialog including border in screen coordinates
     * @param y location of dialog including border in screen coordinates
     * @param services to be browsable, must be non-null.
     * @param defaultService - initial PrintService to display.
     * @param flavor - the flavor to be printed, or null.
     * @param attributes on input is the initial application supplied
     * preferences. This cannot be null but may be empty.
     * On output the attributes reflect changes made by the user.
     * @return print service selected by the user, or null if the user
     * cancelled the dialog.
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @throws IllegalArgumentException if services is null or empty,
     * or attributes is null, or the initial PrintService is not in the
     * list of browsable services.
     */
    public static PrintService printDialog(GraphicsConfiguration gc,
                                           int x, int y,
                                           PrintService[] services,
                                           PrintService defaultService,
                                           DocFlavor flavor,
                                           PrintRequestAttributeSet attributes)
        throws HeadlessException
    {
        int defaultIndex = -1;

        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        } else if ((services == null) || (services.length == 0)) {
            throw new IllegalArgumentException("services must be non-null " +
                                               "and non-empty");
        } else if (attributes == null) {
            throw new IllegalArgumentException("attributes must be non-null");
        }

        if (defaultService != null) {
            for (int i = 0; i < services.length; i++) {
                if (services[i].equals(defaultService)) {
                    defaultIndex = i;
                    break;
                }
            }

            if (defaultIndex < 0) {
                throw new IllegalArgumentException("services must contain " +
                                                   "defaultService");
            }
        } else {
            defaultIndex = 0;
        }

        // For now we set owner to null. In the future, it may be passed
        // as an argument.
        Window owner = null;

        Rectangle gcBounds = (gc == null) ?  GraphicsEnvironment.
            getLocalGraphicsEnvironment().getDefaultScreenDevice().
            getDefaultConfiguration().getBounds() : gc.getBounds();

        ServiceDialog dialog;
        if (owner instanceof Frame) {
            dialog = new ServiceDialog(gc,
                                       x + gcBounds.x,
                                       y + gcBounds.y,
                                       services, defaultIndex,
                                       flavor, attributes,
                                       (Frame)owner);
        } else {
            dialog = new ServiceDialog(gc,
                                       x + gcBounds.x,
                                       y + gcBounds.y,
                                       services, defaultIndex,
                                       flavor, attributes,
                                       (Dialog)owner);
        }
        Rectangle dlgBounds = dialog.getBounds();

        // get union of all GC bounds
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (int j=0; j<gs.length; j++) {
            gcBounds =
                gcBounds.union(gs[j].getDefaultConfiguration().getBounds());
        }

        // if portion of dialog is not within the gc boundary
        if (!gcBounds.contains(dlgBounds)) {
            // put in the center relative to parent frame/dialog
            dialog.setLocationRelativeTo(owner);
        }
        dialog.show();

        if (dialog.getStatus() == ServiceDialog.APPROVE) {
            PrintRequestAttributeSet newas = dialog.getAttributes();
            Class dstCategory = Destination.class;
            Class amCategory = SunAlternateMedia.class;
            Class fdCategory = Fidelity.class;

            if (attributes.containsKey(dstCategory) &&
                !newas.containsKey(dstCategory))
            {
                attributes.remove(dstCategory);
            }

            if (attributes.containsKey(amCategory) &&
                !newas.containsKey(amCategory))
            {
                attributes.remove(amCategory);
            }

            attributes.addAll(newas);

            Fidelity fd = (Fidelity)attributes.get(fdCategory);
            if (fd != null) {
                if (fd == Fidelity.FIDELITY_TRUE) {
                    removeUnsupportedAttributes(dialog.getPrintService(),
                                                flavor, attributes);
                }
            }
        }

        return dialog.getPrintService();
    }

    /**
     * POSSIBLE FUTURE API: This method may be used down the road if we
     * decide to allow developers to explicitly display a "page setup" dialog.
     * Currently we use that functionality internally for the AWT print model.
     * <p>
     *  可能的未来API：如果我们决定允许开发人员明确显示一个"页面设置"对话框,这种方法可能会被使用。目前,我们在AWT打印模型内部使用该功能。
     * 
     */
    /*
    public static void pageDialog(GraphicsConfiguration gc,
                                  int x, int y,
                                  PrintService service,
                                  DocFlavor flavor,
                                  PrintRequestAttributeSet attributes)
        throws HeadlessException
    {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        } else if (service == null) {
            throw new IllegalArgumentException("service must be non-null");
        } else if (attributes == null) {
            throw new IllegalArgumentException("attributes must be non-null");
        }

        ServiceDialog dialog = new ServiceDialog(gc, x, y, service,
                                                 flavor, attributes);
        dialog.show();

        if (dialog.getStatus() == ServiceDialog.APPROVE) {
            PrintRequestAttributeSet newas = dialog.getAttributes();
            Class amCategory = SunAlternateMedia.class;

            if (attributes.containsKey(amCategory) &&
                !newas.containsKey(amCategory))
            {
                attributes.remove(amCategory);
            }

            attributes.addAll(newas.values());
        }

        dialog.getOwner().dispose();
    }
    /* <p>
    /* public void voidDialog(GraphicsConfiguration gc,int x,int y,PrintService service,DocFlavor flavor,Pri
    /* ntRequestAttributeSet attributes)throws HeadlessException {if(GraphicsEnvironment.isHeadless()){throw new HeadlessException }
    /*  else if(service == null){throw new IllegalArgumentException("service must be non-null"); } else if(a
    /* ttributes == null){throw new IllegalArgumentException("attributes must be non-null"); }}。
    /* 
    /*  ServiceDialog dialog = new ServiceDialog(gc,x,y,service,flavor,attributes); dialog.show();
    /* 
    /*  if(dialog.getStatus()== ServiceDialog.APPROVE){PrintRequestAttributeSet newas = dialog.getAttributes();类amCategory = SunAlternateMedia.class;。
    /* 
    /*  if(attributes.containsKey(amCategory)&&！newas.containsKey(amCategory)){attributes.remove(amCategory); }
    */

    /**
     * Removes any attributes from the given AttributeSet that are
     * unsupported by the given PrintService/DocFlavor combination.
     * <p>
     * }。
     * 
     *  attributes.addAll(newas.values()); }}
     * 
     *  dialog.getOwner()。dispose(); }}
     */
    private static void removeUnsupportedAttributes(PrintService ps,
                                                    DocFlavor flavor,
                                                    AttributeSet aset)
    {
        AttributeSet asUnsupported = ps.getUnsupportedAttributes(flavor,
                                                                 aset);

        if (asUnsupported != null) {
            Attribute[] usAttrs = asUnsupported.toArray();

            for (int i=0; i<usAttrs.length; i++) {
                Class category = usAttrs[i].getCategory();

                if (ps.isAttributeCategorySupported(category)) {
                    Attribute attr =
                        (Attribute)ps.getDefaultAttributeValue(category);

                    if (attr != null) {
                        aset.add(attr);
                    } else {
                        aset.remove(category);
                    }
                } else {
                    aset.remove(category);
                }
            }
        }
    }
}

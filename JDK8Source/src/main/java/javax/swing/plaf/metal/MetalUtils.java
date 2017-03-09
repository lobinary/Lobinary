/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf.metal;

import javax.swing.plaf.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.lang.ref.*;
import java.util.*;
import sun.swing.CachedPainter;
import sun.swing.ImageIconUIResource;

/**
 * This is a dumping ground for random stuff we want to use in several places.
 *
 * <p>
 *  这是一个倾销的地方,我们想要在几个地方使用随机的东西。
 * 
 * 
 * @author Steve Wilson
 */

class MetalUtils {

    static void drawFlush3DBorder(Graphics g, Rectangle r) {
        drawFlush3DBorder(g, r.x, r.y, r.width, r.height);
    }

    /**
      * This draws the "Flush 3D Border" which is used throughout the Metal L&F
      * <p>
      *  这绘制了"Flush 3D Border",这是在Metal L&F中使用的
      * 
      */
    static void drawFlush3DBorder(Graphics g, int x, int y, int w, int h) {
        g.translate( x, y);
        g.setColor( MetalLookAndFeel.getControlDarkShadow() );
        g.drawRect( 0, 0, w-2, h-2 );
        g.setColor( MetalLookAndFeel.getControlHighlight() );
        g.drawRect( 1, 1, w-2, h-2 );
        g.setColor( MetalLookAndFeel.getControl() );
        g.drawLine( 0, h-1, 1, h-2 );
        g.drawLine( w-1, 0, w-2, 1 );
        g.translate( -x, -y);
    }

    /**
      * This draws a variant "Flush 3D Border"
      * It is used for things like pressed buttons.
      * <p>
      *  这将绘制一个变体"冲洗3D边框"它用于像按下按钮的东西。
      * 
      */
    static void drawPressed3DBorder(Graphics g, Rectangle r) {
        drawPressed3DBorder( g, r.x, r.y, r.width, r.height );
    }

    static void drawDisabledBorder(Graphics g, int x, int y, int w, int h) {
        g.translate( x, y);
        g.setColor( MetalLookAndFeel.getControlShadow() );
        g.drawRect( 0, 0, w-1, h-1 );
        g.translate(-x, -y);
    }

    /**
      * This draws a variant "Flush 3D Border"
      * It is used for things like pressed buttons.
      * <p>
      *  这将绘制一个变体"冲洗3D边框"它用于像按下按钮的东西。
      * 
      */
    static void drawPressed3DBorder(Graphics g, int x, int y, int w, int h) {
        g.translate( x, y);

        drawFlush3DBorder(g, 0, 0, w, h);

        g.setColor( MetalLookAndFeel.getControlShadow() );
        g.drawLine( 1, 1, 1, h-2 );
        g.drawLine( 1, 1, w-2, 1 );
        g.translate( -x, -y);
    }

    /**
      * This draws a variant "Flush 3D Border"
      * It is used for things like active toggle buttons.
      * This is used rarely.
      * <p>
      *  这将绘制一个变体"冲洗3D边框"它用于像活动切换按钮的东西。这很少使用。
      * 
      */
    static void drawDark3DBorder(Graphics g, Rectangle r) {
        drawDark3DBorder(g, r.x, r.y, r.width, r.height);
    }

    /**
      * This draws a variant "Flush 3D Border"
      * It is used for things like active toggle buttons.
      * This is used rarely.
      * <p>
      *  这将绘制一个变体"冲洗3D边框"它用于像活动切换按钮的东西。这很少使用。
      * 
      */
    static void drawDark3DBorder(Graphics g, int x, int y, int w, int h) {
        g.translate( x, y);

        drawFlush3DBorder(g, 0, 0, w, h);

        g.setColor( MetalLookAndFeel.getControl() );
        g.drawLine( 1, 1, 1, h-2 );
        g.drawLine( 1, 1, w-2, 1 );
        g.setColor( MetalLookAndFeel.getControlShadow() );
        g.drawLine( 1, h-2, 1, h-2 );
        g.drawLine( w-2, 1, w-2, 1 );
        g.translate( -x, -y);
    }

    static void drawButtonBorder(Graphics g, int x, int y, int w, int h, boolean active) {
        if (active) {
            drawActiveButtonBorder(g, x, y, w, h);
        } else {
            drawFlush3DBorder(g, x, y, w, h);
        }
    }

    static void drawActiveButtonBorder(Graphics g, int x, int y, int w, int h) {
        drawFlush3DBorder(g, x, y, w, h);
        g.setColor( MetalLookAndFeel.getPrimaryControl() );
        g.drawLine( x+1, y+1, x+1, h-3 );
        g.drawLine( x+1, y+1, w-3, x+1 );
        g.setColor( MetalLookAndFeel.getPrimaryControlDarkShadow() );
        g.drawLine( x+2, h-2, w-2, h-2 );
        g.drawLine( w-2, y+2, w-2, h-2 );
    }

    static void drawDefaultButtonBorder(Graphics g, int x, int y, int w, int h, boolean active) {
        drawButtonBorder(g, x+1, y+1, w-1, h-1, active);
        g.translate(x, y);
        g.setColor( MetalLookAndFeel.getControlDarkShadow() );
        g.drawRect( 0, 0, w-3, h-3 );
        g.drawLine( w-2, 0, w-2, 0);
        g.drawLine( 0, h-2, 0, h-2);
        g.translate(-x, -y);
    }

    static void drawDefaultButtonPressedBorder(Graphics g, int x, int y, int w, int h) {
        drawPressed3DBorder(g, x + 1, y + 1, w - 1, h - 1);
        g.translate(x, y);
        g.setColor(MetalLookAndFeel.getControlDarkShadow());
        g.drawRect(0, 0, w - 3, h - 3);
        g.drawLine(w - 2, 0, w - 2, 0);
        g.drawLine(0, h - 2, 0, h - 2);
        g.setColor(MetalLookAndFeel.getControl());
        g.drawLine(w - 1, 0, w - 1, 0);
        g.drawLine(0, h - 1, 0, h - 1);
        g.translate(-x, -y);
    }

    /*
     * Convenience function for determining ComponentOrientation.  Helps us
     * avoid having Munge directives throughout the code.
     * <p>
     *  用于确定ComponentOrientation的便捷功能。帮助我们避免在整个代码中有Munge指令。
     * 
     */
    static boolean isLeftToRight( Component c ) {
        return c.getComponentOrientation().isLeftToRight();
    }

    static int getInt(Object key, int defaultValue) {
        Object value = UIManager.get(key);

        if (value instanceof Integer) {
            return ((Integer)value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String)value);
            } catch (NumberFormatException nfe) {}
        }
        return defaultValue;
    }

    //
    // Ocean specific stuff.
    //
    /**
     * Draws a radial type gradient. The gradient will be drawn vertically if
     * <code>vertical</code> is true, otherwise horizontally.
     * The UIManager key consists of five values:
     * r1 r2 c1 c2 c3. The gradient is broken down into four chunks drawn
     * in order from the origin.
     * <ol>
     * <li>Gradient r1 % of the size from c1 to c2
     * <li>Rectangle r2 % of the size in c2.
     * <li>Gradient r1 % of the size from c2 to c1
     * <li>The remaining size will be filled with a gradient from c1 to c3.
     * </ol>
     *
     * <p>
     *  绘制径向类型渐变。如果<code> vertical </code>为true,或者水平,则会垂直绘制渐变。 UIManager键由五个值组成：r1 r2 c1 c2 c3。
     * 梯度被分解成从原点开始依次绘制的四个块。
     * <ol>
     *  <li>渐变r1从c1到c2的尺寸<li>矩形r2为c2中尺寸的％。 <li>渐变从c2到c1的大小的r1％<li>剩余的大小将填充从c1到c3的渐变。
     * </ol>
     * 
     * 
     * @param c Component rendering to
     * @param g Graphics to draw to.
     * @param key UIManager key used to look up gradient values.
     * @param x X coordinate to draw from
     * @param y Y coordinate to draw from
     * @param w Width to draw to
     * @param h Height to draw to
     * @param vertical Direction of the gradient
     * @return true if <code>key</code> exists, otherwise false.
     */
    static boolean drawGradient(Component c, Graphics g, String key,
                                int x, int y, int w, int h, boolean vertical) {
        java.util.List gradient = (java.util.List)UIManager.get(key);
        if (gradient == null || !(g instanceof Graphics2D)) {
            return false;
        }

        if (w <= 0 || h <= 0) {
            return true;
        }

        GradientPainter.INSTANCE.paint(
                c, (Graphics2D)g, gradient, x, y, w, h, vertical);
        return true;
    }


    private static class GradientPainter extends CachedPainter {
        /**
         * Instance used for painting.  This is the only instance that is
         * ever created.
         * <p>
         *  用于绘画的实例。这是唯一的实例。
         * 
         */
        public static final GradientPainter INSTANCE = new GradientPainter(8);

        // Size of images to create. For vertical gradients this is the width,
        // otherwise it's the height.
        private static final int IMAGE_SIZE = 64;

        /**
         * This is the actual width we're painting in, or last painted to.
         * <p>
         *  这是我们画在或最后画的实际宽度。
         * 
         */
        private int w;
        /**
         * This is the actual height we're painting in, or last painted to
         * <p>
         *  这是我们画的实际高度,或最后画的高度
         * 
         */
        private int h;


        GradientPainter(int count) {
            super(count);
        }

        public void paint(Component c, Graphics2D g,
                          java.util.List gradient, int x, int y, int w,
                          int h, boolean isVertical) {
            int imageWidth;
            int imageHeight;
            if (isVertical) {
                imageWidth = IMAGE_SIZE;
                imageHeight = h;
            }
            else {
                imageWidth = w;
                imageHeight = IMAGE_SIZE;
            }
            synchronized(c.getTreeLock()) {
                this.w = w;
                this.h = h;
                paint(c, g, x, y, imageWidth, imageHeight,
                      gradient, isVertical);
            }
        }

        protected void paintToImage(Component c, Image image, Graphics g,
                                    int w, int h, Object[] args) {
            Graphics2D g2 = (Graphics2D)g;
            java.util.List gradient = (java.util.List)args[0];
            boolean isVertical = ((Boolean)args[1]).booleanValue();
            // Render to the VolatileImage
            if (isVertical) {
                drawVerticalGradient(g2,
                                     ((Number)gradient.get(0)).floatValue(),
                                     ((Number)gradient.get(1)).floatValue(),
                                     (Color)gradient.get(2),
                                     (Color)gradient.get(3),
                                     (Color)gradient.get(4), w, h);
            }
            else {
                drawHorizontalGradient(g2,
                                      ((Number)gradient.get(0)).floatValue(),
                                      ((Number)gradient.get(1)).floatValue(),
                                      (Color)gradient.get(2),
                                      (Color)gradient.get(3),
                                      (Color)gradient.get(4), w, h);
            }
        }

        protected void paintImage(Component c, Graphics g,
                                  int x, int y, int imageW, int imageH,
                                  Image image, Object[] args) {
            boolean isVertical = ((Boolean)args[1]).booleanValue();
            // Render to the screen
            g.translate(x, y);
            if (isVertical) {
                for (int counter = 0; counter < w; counter += IMAGE_SIZE) {
                    int tileSize = Math.min(IMAGE_SIZE, w - counter);
                    g.drawImage(image, counter, 0, counter + tileSize, h,
                                0, 0, tileSize, h, null);
                }
            }
            else {
                for (int counter = 0; counter < h; counter += IMAGE_SIZE) {
                    int tileSize = Math.min(IMAGE_SIZE, h - counter);
                    g.drawImage(image, 0, counter, w, counter + tileSize,
                                0, 0, w, tileSize, null);
                }
            }
            g.translate(-x, -y);
        }

        private void drawVerticalGradient(Graphics2D g, float ratio1,
                                          float ratio2, Color c1,Color c2,
                                          Color c3, int w, int h) {
            int mid = (int)(ratio1 * h);
            int mid2 = (int)(ratio2 * h);
            if (mid > 0) {
                g.setPaint(getGradient((float)0, (float)0, c1, (float)0,
                                       (float)mid, c2));
                g.fillRect(0, 0, w, mid);
            }
            if (mid2 > 0) {
                g.setColor(c2);
                g.fillRect(0, mid, w, mid2);
            }
            if (mid > 0) {
                g.setPaint(getGradient((float)0, (float)mid + mid2, c2,
                                       (float)0, (float)mid * 2 + mid2, c1));
                g.fillRect(0, mid + mid2, w, mid);
            }
            if (h - mid * 2 - mid2 > 0) {
                g.setPaint(getGradient((float)0, (float)mid * 2 + mid2, c1,
                                       (float)0, (float)h, c3));
                g.fillRect(0, mid * 2 + mid2, w, h - mid * 2 - mid2);
            }
        }

        private void drawHorizontalGradient(Graphics2D g, float ratio1,
                                            float ratio2, Color c1,Color c2,
                                            Color c3, int w, int h) {
            int mid = (int)(ratio1 * w);
            int mid2 = (int)(ratio2 * w);
            if (mid > 0) {
                g.setPaint(getGradient((float)0, (float)0, c1,
                                       (float)mid, (float)0, c2));
                g.fillRect(0, 0, mid, h);
            }
            if (mid2 > 0) {
                g.setColor(c2);
                g.fillRect(mid, 0, mid2, h);
            }
            if (mid > 0) {
                g.setPaint(getGradient((float)mid + mid2, (float)0, c2,
                                       (float)mid * 2 + mid2, (float)0, c1));
                g.fillRect(mid + mid2, 0, mid, h);
            }
            if (w - mid * 2 - mid2 > 0) {
                g.setPaint(getGradient((float)mid * 2 + mid2, (float)0, c1,
                                       w, (float)0, c3));
                g.fillRect(mid * 2 + mid2, 0, w - mid * 2 - mid2, h);
            }
        }

        private GradientPaint getGradient(float x1, float y1,
                                                 Color c1, float x2, float y2,
                                                 Color c2) {
            return new GradientPaint(x1, y1, c1, x2, y2, c2, true);
        }
    }


    /**
     * Returns true if the specified widget is in a toolbar.
     * <p>
     * 如果指定的窗口小部件在工具栏中,则返回true。
     * 
     */
    static boolean isToolBarButton(JComponent c) {
        return (c.getParent() instanceof JToolBar);
    }

    static Icon getOceanToolBarIcon(Image i) {
        ImageProducer prod = new FilteredImageSource(i.getSource(),
                             new OceanToolBarImageFilter());
        return new ImageIconUIResource(Toolkit.getDefaultToolkit().createImage(prod));
    }

    static Icon getOceanDisabledButtonIcon(Image image) {
        Object[] range = (Object[])UIManager.get("Button.disabledGrayRange");
        int min = 180;
        int max = 215;
        if (range != null) {
            min = ((Integer)range[0]).intValue();
            max = ((Integer)range[1]).intValue();
        }
        ImageProducer prod = new FilteredImageSource(image.getSource(),
                      new OceanDisabledButtonImageFilter(min , max));
        return new ImageIconUIResource(Toolkit.getDefaultToolkit().createImage(prod));
    }




    /**
     * Used to create a disabled Icon with the ocean look.
     * <p>
     *  用于创建一个禁用图标与海洋的样子。
     * 
     */
    private static class OceanDisabledButtonImageFilter extends RGBImageFilter{
        private float min;
        private float factor;

        OceanDisabledButtonImageFilter(int min, int max) {
            canFilterIndexColorModel = true;
            this.min = (float)min;
            this.factor = (max - min) / 255f;
        }

        public int filterRGB(int x, int y, int rgb) {
            // Coefficients are from the sRGB color space:
            int gray = Math.min(255, (int)(((0.2125f * ((rgb >> 16) & 0xFF)) +
                    (0.7154f * ((rgb >> 8) & 0xFF)) +
                    (0.0721f * (rgb & 0xFF)) + .5f) * factor + min));

            return (rgb & 0xff000000) | (gray << 16) | (gray << 8) |
                (gray << 0);
        }
    }


    /**
     * Used to create the rollover icons with the ocean look.
     * <p>
     *  用于创建带有海洋外观的翻转图标。
     */
    private static class OceanToolBarImageFilter extends RGBImageFilter {
        OceanToolBarImageFilter() {
            canFilterIndexColorModel = true;
        }

        public int filterRGB(int x, int y, int rgb) {
            int r = ((rgb >> 16) & 0xff);
            int g = ((rgb >> 8) & 0xff);
            int b = (rgb & 0xff);
            int gray = Math.max(Math.max(r, g), b);
            return (rgb & 0xff000000) | (gray << 16) | (gray << 8) |
                (gray << 0);
        }
    }
}

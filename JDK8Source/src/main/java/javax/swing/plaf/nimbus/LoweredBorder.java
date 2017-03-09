/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.nimbus;

import javax.swing.border.Border;
import javax.swing.JComponent;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * LoweredBorder - A recessed rounded inner shadowed border. Used as the
 * standard Nimbus TitledBorder. This class is both a painter and a swing
 * border.
 *
 * <p>
 *  LoweredBorder  - 一个凹进的圆形内部阴影边框。用作标准Nimbus TitledBorder。这个类既是画家又是摇摆边界。
 * 
 * 
 * @author Jasper Potts
 */
class LoweredBorder extends AbstractRegionPainter implements Border {
    private static final int IMG_SIZE = 30;
    private static final int RADIUS = 13;
    private static final Insets INSETS = new Insets(10,10,10,10);
    private static final PaintContext PAINT_CONTEXT = new PaintContext(INSETS,
            new Dimension(IMG_SIZE,IMG_SIZE),false,
            PaintContext.CacheMode.NINE_SQUARE_SCALE,
            Integer.MAX_VALUE, Integer.MAX_VALUE);

    // =========================================================================
    // Painter Methods

    @Override
    protected Object[] getExtendedCacheKeys(JComponent c) {
        return (c != null)
                ? new Object[] { c.getBackground() }
                : null;
    }

    /**
     * Actually performs the painting operation. Subclasses must implement this
     * method. The graphics object passed may represent the actual surface being
     * rendered to, or it may be an intermediate buffer. It has also been
     * pre-translated. Simply render the component as if it were located at 0, 0
     * and had a width of <code>width</code> and a height of
     * <code>height</code>. For performance reasons, you may want to read the
     * clip from the Graphics2D object and only render within that space.
     *
     * <p>
     *  实际上执行喷涂操作。子类必须实现此方法。传递的图形对象可以表示正被渲染的实际表面,或者它可以是中间缓冲器。它也已经预翻译。
     * 简单地渲染组件,就像它位于0,0,宽度为<code> width </code>和高度<code> height </code>。
     * 出于性能原因,您可能需要从Graphics2D对象中读取剪辑,并且只在该空间内呈现。
     * 
     * 
     * @param g      The Graphics2D surface to paint to
     * @param c      The JComponent related to the drawing event. For example,
     *               if the region being rendered is Button, then <code>c</code>
     *               will be a JButton. If the region being drawn is
     *               ScrollBarSlider, then the component will be JScrollBar.
     *               This value may be null.
     * @param width  The width of the region to paint. Note that in the case of
     *               painting the foreground, this value may differ from
     *               c.getWidth().
     * @param height The height of the region to paint. Note that in the case of
     *               painting the foreground, this value may differ from
     *               c.getHeight().
     */
    protected void doPaint(Graphics2D g, JComponent c, int width, int height,
            Object[] extendedCacheKeys) {
        Color color = (c == null) ? Color.BLACK : c.getBackground();
        BufferedImage img1 = new BufferedImage(IMG_SIZE,IMG_SIZE,
                    BufferedImage.TYPE_INT_ARGB);
        BufferedImage img2 = new BufferedImage(IMG_SIZE,IMG_SIZE,
                    BufferedImage.TYPE_INT_ARGB);
        // draw shadow shape
        Graphics2D g2 = (Graphics2D)img1.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillRoundRect(2,0,26,26,RADIUS,RADIUS);
        g2.dispose();
        // draw shadow
        InnerShadowEffect effect = new InnerShadowEffect();
        effect.setDistance(1);
        effect.setSize(3);
        effect.setColor(getLighter(color, 2.1f));
        effect.setAngle(90);
        effect.applyEffect(img1,img2,IMG_SIZE,IMG_SIZE);
        // draw outline to img2
        g2 = (Graphics2D)img2.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(0,28,IMG_SIZE,1);
        g2.setColor(getLighter(color, 0.90f));
        g2.drawRoundRect(2,1,25,25,RADIUS,RADIUS);
        g2.dispose();
        // draw final image
        if (width != IMG_SIZE || height != IMG_SIZE){
            ImageScalingHelper.paint(g,0,0,width,height,img2, INSETS, INSETS,
                    ImageScalingHelper.PaintType.PAINT9_STRETCH,
                    ImageScalingHelper.PAINT_ALL);
        } else {
            g.drawImage(img2,0,0,c);
        }
        img1 = null;
        img2 = null;
    }

    /**
     * <p>Gets the PaintContext for this painting operation. This method is
     * called on every paint, and so should be fast and produce no garbage. The
     * PaintContext contains information such as cache hints. It also contains
     * data necessary for decoding points at runtime, such as the stretching
     * insets, the canvas size at which the encoded points were defined, and
     * whether the stretching insets are inverted.</p>
     * <p/>
     * <p> This method allows for subclasses to package the painting of
     * different states with possibly different canvas sizes, etc, into one
     * AbstractRegionPainter implementation.</p>
     *
     * <p>
     *  <p>获取此绘画操作的PaintContext。这种方法在每种颜料上调用,因此应该快速,不产生垃圾。 PaintContext包含诸如缓存提示之类的信息。
     * 它还包含在运行时解码点所需的数据,例如拉伸插入,定义编码点的画布大小,以及拉伸插入是否反转。</p>。
     * <p/>
     *  <p>此方法允许子类将具有可能不同的画布大小等的不同状态的绘画打包到一个AbstractRegionPainter实现中。</p>
     * 
     * 
     * @return a PaintContext associated with this paint operation.
     */
    protected PaintContext getPaintContext() {
        return PAINT_CONTEXT;
    }

    // =========================================================================
    // Border Methods

    /**
     * Returns the insets of the border.
     *
     * <p>
     *  返回边框的插入。
     * 
     * 
     * @param c the component for which this border insets value applies
     */
    public Insets getBorderInsets(Component c) {
        return (Insets) INSETS.clone();
    }

    /**
     * Returns whether or not the border is opaque.  If the border is opaque, it
     * is responsible for filling in it's own background when painting.
     * <p>
     * 返回边框是否不透明。如果边框是不透明的,它负责填充它自己的背景时,绘画。
     * 
     */
    public boolean isBorderOpaque() {
        return false;
    }

    /**
     * Paints the border for the specified component with the specified position
     * and size.
     *
     * <p>
     *  以指定的位置和大小绘制指定组件的边框。
     * 
     * @param c      the component for which this border is being painted
     * @param g      the paint graphics
     * @param x      the x position of the painted border
     * @param y      the y position of the painted border
     * @param width  the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
                            int height) {
        JComponent comp = (c instanceof JComponent)?(JComponent)c:null;
        if (g instanceof Graphics2D){
            Graphics2D g2 = (Graphics2D)g;
            g2.translate(x,y);
            paint(g2,comp, width, height);
            g2.translate(-x,-y);
        } else {
            BufferedImage img =  new BufferedImage(IMG_SIZE,IMG_SIZE,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)img.getGraphics();
            paint(g2,comp, width, height);
            g2.dispose();
            ImageScalingHelper.paint(g,x,y,width,height,img,INSETS, INSETS,
                    ImageScalingHelper.PaintType.PAINT9_STRETCH,
                    ImageScalingHelper.PAINT_ALL);
        }
    }

    private Color getLighter(Color c, float factor){
        return new Color(Math.min((int)(c.getRed()/factor), 255),
                         Math.min((int)(c.getGreen()/factor), 255),
                         Math.min((int)(c.getBlue()/factor), 255));
    }
}


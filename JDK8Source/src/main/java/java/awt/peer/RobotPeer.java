/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.peer;

import java.awt.*;

/**
 * RobotPeer defines an interface whereby toolkits support automated testing
 * by allowing native input events to be generated from Java code.
 *
 * This interface should not be directly imported by code outside the
 * java.awt.* hierarchy; it is not to be considered public and is subject
 * to change.
 *
 * <p>
 *  RobotPeer定义了一个接口,工具包通过允许从Java代码生成本地输入事件来支持自动测试。
 * 
 *  这个接口不应该由java.awt。*层次之外的代码直接导入;它不被认为是公开的,并且可能会更改。
 * 
 * 
 * @author      Robi Khan
 */
public interface RobotPeer
{
    /**
     * Moves the mouse pointer to the specified screen location.
     *
     * <p>
     *  将鼠标指针移动到指定的屏幕位置。
     * 
     * 
     * @param x the X location on screen
     * @param y the Y location on screen
     *
     * @see Robot#mouseMove(int, int)
     */
    void mouseMove(int x, int y);

    /**
     * Simulates a mouse press with the specified button(s).
     *
     * <p>
     *  模拟鼠标按下指定的按钮。
     * 
     * 
     * @param buttons the button mask
     *
     * @see Robot#mousePress(int)
     */
    void mousePress(int buttons);

    /**
     * Simulates a mouse release with the specified button(s).
     *
     * <p>
     *  使用指定的按钮模拟鼠标释放。
     * 
     * 
     * @param buttons the button mask
     *
     * @see Robot#mouseRelease(int)
     */
    void mouseRelease(int buttons);

    /**
     * Simulates mouse wheel action.
     *
     * <p>
     *  模拟鼠标滚轮操作。
     * 
     * 
     * @param wheelAmt number of notches to move the mouse wheel
     *
     * @see Robot#mouseWheel(int)
     */
    void mouseWheel(int wheelAmt);

    /**
     * Simulates a key press of the specified key.
     *
     * <p>
     *  模拟指定键的按键。
     * 
     * 
     * @param keycode the key code to press
     *
     * @see Robot#keyPress(int)
     */
    void keyPress(int keycode);

    /**
     * Simulates a key release of the specified key.
     *
     * <p>
     *  模拟指定键的键释放。
     * 
     * 
     * @param keycode the key code to release
     *
     * @see Robot#keyRelease(int)
     */
    void keyRelease(int keycode);

    /**
     * Gets the RGB value of the specified pixel on screen.
     *
     * <p>
     *  获取屏幕上指定像素的RGB值。
     * 
     * 
     * @param x the X screen coordinate
     * @param y the Y screen coordinate
     *
     * @return the RGB value of the specified pixel on screen
     *
     * @see Robot#getPixelColor(int, int)
     */
    int getRGBPixel(int x, int y);

    /**
     * Gets the RGB values of the specified screen area as an array.
     *
     * <p>
     *  将指定屏幕区域的RGB值作为数组获取。
     * 
     * 
     * @param bounds the screen area to capture the RGB values from
     *
     * @return the RGB values of the specified screen area
     *
     * @see Robot#createScreenCapture(Rectangle)
     */
    int[] getRGBPixels(Rectangle bounds);

    /**
     * Disposes the robot peer when it is not needed anymore.
     * <p>
     *  当不再需要机器人对等体时,对其进行处理。
     */
    void dispose();
}

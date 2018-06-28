package com.lobinary.java.jdk.jdk16.systemtray;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrayFactory {
	private TrayIcon trayIcon = null;
	private Component comp = null;
	private String trayImageName = null;
	private String tooltip = null;

	private TrayFactory(Component comp, String trayImageName, String tooltip) {
		this.comp = comp;
		this.trayImageName = trayImageName;
		this.tooltip = tooltip;

		init();
	}

	private void init() {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource(trayImageName));
			PopupMenu popupMenu = new PopupMenu();
			MenuItem restoreItem = new MenuItem("还原");
			MenuItem exitItem = new MenuItem("退出");

			restoreItem.addActionListener(new RestoreItemActionListener());
			exitItem.addActionListener(new ExitItemActionListener());

			popupMenu.add(restoreItem);
			popupMenu.addSeparator();
			popupMenu.add(exitItem);

			trayIcon = new TrayIcon(image, tooltip, popupMenu);
			trayIcon.addMouseListener(new TrayIconMouseListener());
			// trayIcon.addActionListener(new TrayIconActionListener());

			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}
		} else {
			// ..........
		}
	}

	private class RestoreItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			comp.setVisible(true);
		}
	}

	private class ExitItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
	}

	private class TrayIconMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent me) {
			if (me.getButton() == MouseEvent.BUTTON1) {
				comp.setVisible(true);
			}
		}
	}

	public static void createTray(Component comp, String trayImageName, String tooltip) {
		new TrayFactory(comp, trayImageName, tooltip);
	}
}

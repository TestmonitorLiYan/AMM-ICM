package org.amm.icm.fileUpload.controller;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MouseController implements Runnable {

	private Dimension dim;
	 private Random rand;
	 private Robot robot;
	 private volatile boolean stop = false;
	 public MouseController() {
	 dim = Toolkit.getDefaultToolkit().getScreenSize();
	 rand = new Random();
	 try {
	  robot = new Robot();
	 } catch (AWTException ex) {
	  ex.printStackTrace();
	 }
	 }
	 public void run() {
		 for(int i=0;i<=100;i++) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				robot.mouseMove(1380, 0);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				robot.keyPress(KeyEvent.VK_ENTER);
			}
	 }
	 public synchronized void stop() {
	 stop = true;
	 }
	 public static void main(String[] args) {
	 MouseController mc = new MouseController();
	 Thread mcThread = new Thread(mc);
	 System.out.println("Mouse Controller start");
	 mcThread.start();
	 try {
	  Thread.sleep(60000);
	 } catch (InterruptedException ex) {
	  ex.printStackTrace();
	 }
	 mc.stop();
	 System.out.println("Mouse Controller stoped");
	}
}

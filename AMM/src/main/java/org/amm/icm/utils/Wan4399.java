package org.amm.icm.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Wan4399 {
	public static void main(String[] args) {
		/*Runtime runtime = Runtime.getRuntime();
		String file = "C:\\Program Files (x86)\\kuwo\\kuwomusic\\9.0.3.0_P2T1\\KwMusic.exe";
		String command = "cmd /c start "+file.replaceAll(" ", "\" \"");
	    try {
			runtime.exec(command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			Robot robot = new Robot();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
				//robot.keyPress(KeyEvent.VK_ENTER);
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

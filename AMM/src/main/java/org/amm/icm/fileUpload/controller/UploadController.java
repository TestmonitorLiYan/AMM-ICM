package org.amm.icm.fileUpload.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/file")
@CrossOrigin
public class UploadController {
	 @Value("${prop.upload-folder}")
	  private String UPLOAD_FOLDER;
	@PostMapping("/upload")
    public Object singleFileUpload(MultipartFile[] files) {
		if (files==null||files.length==0) {
            return "文件集合为空";}
		for(MultipartFile file:files) {
		if (file==null||file.isEmpty()) {
            return "文件为空";}
        //获取文件名
        String fileName = file.getOriginalFilename();// 文件上传后的路径
        //存储路径
        String tagFilePath = UPLOAD_FOLDER + fileName;
        File dest = new File(tagFilePath);
        //检测是否存在目录
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try{
            file.transferTo(dest);
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    	
		}
		return  "上传成功";
    }
	@GetMapping("/test")
    public void wan(){
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
				robot.keyPress(KeyEvent.VK_ENTER);
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

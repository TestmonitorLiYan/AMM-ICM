package org.amm.icm.listener;


import javax.servlet.http.HttpSession;

import org.amm.icm.utils.Progress;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;
@Component
public class MyProgressListener implements ProgressListener {
	 private HttpSession session;
	 public void setSession(HttpSession session){
	     this.session=session;
	     Progress status = new Progress();//保存上传状态
	     session.setAttribute("status", status);
	 }
	 @Override
	    public void update(long bytesRead, long contentLength, int items) {
	        Progress status = (Progress) session.getAttribute("status");
	        status.setBytesRead(bytesRead);
	        status.setContentLength(contentLength);
	        status.setItems(items);
	    }
}

package org.amm.icm.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;


public class HtmlCodeToPdf {
	 public static void htmlCodeToPdf(String htmlCode, String pdfPath) {
	        OutputStream os =null;
	        try {
	            os=new FileOutputStream(pdfPath);
	            ITextRenderer renderer = new ITextRenderer();
	            ITextFontResolver fontResolver = renderer.getFontResolver();
	            fontResolver.addFont("C:\\Windows\\Fonts\\simsunb.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
	            renderer.setDocumentFromString(htmlCode);
	            renderer.layout();
	            renderer.createPDF(os);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }catch (IOException e) {
	            e.printStackTrace();
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }finally {
	            if(null!=os){
	                try {
	                    os.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
}

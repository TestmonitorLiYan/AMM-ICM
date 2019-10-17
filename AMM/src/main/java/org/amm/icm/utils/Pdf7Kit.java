package org.amm.icm.utils;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.WritableDirectElement;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.WritableElement;
public class Pdf7Kit {


		public static void main(String[] args) throws Exception {
			String blogURL = "https://blog.csdn.net/guoxiaolongonly/article/details/51497495";
			 
			// 直接把网页内容转为PDF文件
			String pdfFile = "D:\\itextFile\\first\\demo-URL2.pdf";
			Pdf7Kit.parseURL2PDFFile(pdfFile, blogURL);
	 
			// 把网页内容转为PDF中的Elements
			String pdfFile2 = "D:\\itextFile\\first\\demo-URL1.pdf";
			Pdf7Kit.parseURL2PDFElement(pdfFile2, blogURL);
		}
		/**
		 * 根据URL提前blog的基本信息，返回结果&gt;&gt;:[主题 ,分类,日期,内容]等.
		 *
		 * @param blogURL
		 * @return
		 * @throws Exception
		 */
		public static String[] extractBlogInfo(String blogURL) throws Exception {
			String[] info = new String[4];
			org.jsoup.nodes.Document doc = Jsoup.connect(blogURL).get();
			//org.jsoup.nodes.Element e_title = doc.getElementsByAttributeValue("id", "mainBox").first();
			org.jsoup.nodes.Element e_title = doc.select("dev.mainBox").first();
			//org.jsoup.nodes.Element e_title = doc.select("h2.title").first();
			if(e_title!=null) {
			
				info[0] = e_title.text();
				System.out.println("info[0]:"+info[0]);
			}
			org.jsoup.nodes.Element e_category = doc.select("a[rel=category tag]")
					.first();
			if(e_category!=null) {
				info[1] = e_category.attr("href").replace("https://blog.csdn.net/", "");
				System.out.println("info[1]:"+info[1]);
			}
			org.jsoup.nodes.Element e_date = doc.select("code.language-java")
					.first();
			if(e_date!=null){
				String dateStr = e_date.text().split("日期")[1].trim();
				info[2] = dateStr;
				System.out.println("info[2]:"+info[2]);
			}
			org.jsoup.nodes.Element entry = doc.select("div.mainBox").first();
			info[3] = formatContentTag(entry).replace("<br>","<br/>");
			System.out.println("info[3]:"+info[3]);
			return info;
		}
	 
		/**
		 * 格式化 img标签
		 *
		 * @param entry
		 * @return
		 */
		private static String formatContentTag(org.jsoup.nodes.Element entry) {
			try {
				entry.select("div").remove();
				// 把 &lt;a href="*.jpg" &gt;&lt;img src="*.jpg"/&gt;&lt;/a&gt; 替换为 &lt;img
				// src="*.jpg"/&gt;
				for (org.jsoup.nodes.Element imgEle : entry
						.select("a[href~=(?i)\\.(png|jpe?g)]")) {
					imgEle.replaceWith(imgEle.select("img").first());
				}
				return entry.html();
			} catch (Exception e) {
				return "";
			}
		}
	 
		/**
		 * 把String 转为 InputStream
		 *
		 * @param content
		 * @return
		 */
		public static InputStream parse2Stream(String content) {
			try {
				ByteArrayInputStream stream = new ByteArrayInputStream(
						content.getBytes("utf-8"));
				return stream;
			} catch (Exception e) {
	 
				return null;
			}
		}
	 
		/**
		 * 直接把网页内容转为PDF文件
		 *
		 * @param fileName
		 * @throws Exception
		 */
		public static void parseURL2PDFFile(String pdfFile, String blogURL)
				throws Exception {
	 
			BaseFont bfCN = BaseFont.createFont("C:\\Windows\\Fonts\\simsunb.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			// 中文字体定义
			Font chFont = new Font(bfCN, 14, Font.NORMAL, BaseColor.BLUE);
			Font secFont = new Font(bfCN, 12, Font.NORMAL, new BaseColor(0, 204,
					255));
			Font textFont = new Font(bfCN, 12, Font.NORMAL, BaseColor.BLACK);
	 
			Document document = new Document();
			PdfWriter pdfwriter = PdfWriter.getInstance(document,
					new FileOutputStream(pdfFile));
			pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
			document.open();
	 
			String[] blogInfo = extractBlogInfo(blogURL);
			System.out.println("htmlCode:");
			for(String info:blogInfo) {
				System.out.println(info);
			}
			int chNum = 1;
			Chapter chapter = new Chapter(new Paragraph("URL转PDF测试", chFont),
					chNum++);
	 
			Section section = chapter
					.addSection(new Paragraph(blogInfo[0], secFont));
			section.setIndentation(10);
			section.setIndentationLeft(10);
			section.setBookmarkOpen(false);
			section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
			section.add(new Chunk("分类：" + blogInfo[1] + " 日期：" + blogInfo[2],
					textFont));
	 
			LineSeparator line = new LineSeparator(1, 100, new BaseColor(204, 204,
					204), Element.ALIGN_CENTER, -2);
			Paragraph p_line = new Paragraph(" ");
			p_line.add(line);
			section.add(p_line);
			section.add(Chunk.NEWLINE);
			document.add(chapter);
			// html文件
			XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document,
					parse2Stream(blogInfo[3]));
			document.close();
		}
	 
		/**
		 * 把网页内容转为PDF中的Elements
		 *
		 * @param pdfFile
		 * @param htmlFileStream
		 */
		public static void parseURL2PDFElement(String pdfFile, String blogURL) {
			try {
				Document document = new Document(PageSize.A4);
	 
				FileOutputStream outputStream = new FileOutputStream(pdfFile);
				PdfWriter pdfwriter = PdfWriter.getInstance(document, outputStream);
				 pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
				document.open();
				/*BaseFont bfCN = BaseFont.createFont("STSong-Light",
						"UniGB-UTF8-V", false);*/
				BaseFont bfCN = BaseFont.createFont("C:\\Windows\\Fonts\\simsunb.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
				// 中文字体定义
				Font chFont = new Font(bfCN, 14, Font.NORMAL, BaseColor.BLUE);
				Font secFont = new Font(bfCN, 12, Font.NORMAL, new BaseColor(0,
						204, 255));
				Font textFont = new Font(bfCN, 12, Font.NORMAL, BaseColor.BLACK);
	 
				int chNum = 1;
				Chapter chapter = new Chapter(new Paragraph("URL转PDF元素，便于追加其他内容",
						chFont), chNum++);
	 
				String[] blogInfo = extractBlogInfo(blogURL);
	 
				Section section = chapter.addSection(new Paragraph(blogInfo[0],
						secFont));
	 
				section.setIndentation(10);
				section.setIndentationLeft(10);
				section.setBookmarkOpen(false);
				section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
				section.add(new Chunk("分类：" + blogInfo[1] + " 发表日期：" + blogInfo[2],
						textFont));
				LineSeparator line = new LineSeparator(1, 100, new BaseColor(204,
						204, 204), Element.ALIGN_CENTER, -2);
				Paragraph p_line = new Paragraph();
				p_line.add(line);
				section.add(p_line);
				section.add(Chunk.NEWLINE);
	 
				final List<Element> pdfeleList = new ArrayList<Element>();
				ElementHandler elemH = new ElementHandler() {
	 
					public void add(final Writable w) {
						if (w instanceof WritableElement) {
							pdfeleList.addAll(((WritableElement) w).elements());
						}
	 
					}
				};
				XMLWorkerHelper.getInstance().parseXHtml(elemH,
						new InputStreamReader(parse2Stream(blogInfo[3]), "utf-8"));
	 
				List<Element> list = new ArrayList<Element>();
				for (Element ele : pdfeleList) {
					if (ele instanceof LineSeparator
							|| ele instanceof WritableDirectElement) {
						continue;
					}
					list.add(ele);
				}
				section.addAll(list);
	 
				section = chapter.addSection(new Paragraph("继续添加章节", secFont));
	 
				section.setIndentation(10);
				section.setIndentationLeft(10);
				section.setBookmarkOpen(false);
				section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
				section.add(new Chunk("测试URL转为PDF元素，方便追加其他内容", textFont));
	 
				document.add(chapter);
				document.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	 
		}

}

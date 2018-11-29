package com.utils.QRUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码生产工具类
 */
@Controller
public class QRDownloadController {
   
   @RequestMapping("/testDownload")
   public static void testDownload(HttpServletResponse response) throws Exception { 
       response.reset();
       //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型   
       
       response.setContentType("multipart/form-data;charset=utf-8");   
       
       String filename="测试下载二维码.png";
       //注意fileName不能直接是普通string，必须new String(filename.getBytes(),"iso-8859-1")——>防止中文乱码 
       response.setHeader("Content-Disposition", "attachment;fileName="+new String(filename.getBytes(),"iso-8859-1"));  
       OutputStream outputStream = response.getOutputStream();
       
       String text = "http://www.baidu.com"; 
       int width = 300; 
       int height = 300; 
       //二维码的图片格式 
       String format = "png"; 
       Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>(); 
       //内容所使用编码 
       hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
       //设置二维码白色边距
       hints.put(EncodeHintType.MARGIN, 1);
       BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints); 
       //生成二维码 
       File outputFile = new File("d:"+File.separator+"new.png"); 
       QRUtil.writeToFile(bitMatrix, format, outputFile); 
       
       BufferedImage bufferedImage = QRUtil.toBufferedImage(bitMatrix);
       ImageIO.write(bufferedImage, format, outputStream);
   } 
}

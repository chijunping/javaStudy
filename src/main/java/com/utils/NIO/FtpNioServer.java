package com.utils.NIO;
  
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
  
  
public class FtpNioServer {  
    public static final int PORT = 5000;  
    public static int connum = 0;  
    public static final int MAX = 5000;  
    public static final String ROOT = "D:/FtpDir/";  
      
    public static void main(String argv[]) {  
          
        try {  
            Selector selector = Selector.open();  
            ServerSocketChannel ssc = ServerSocketChannel.open();  
            ssc.configureBlocking(false);  
              
            ServerSocket ss = ssc.socket();  
            ss.bind(new InetSocketAddress(PORT));  
              
            SelectionKey skey = ssc.register(selector, SelectionKey.OP_ACCEPT);  
            skey.attach(new ServerHandler(ssc, selector));  
              
            System.out.println("Start ftp server on " + PORT);  
              
            while(!Thread.interrupted()) {  
                int n = selector.select();  
                if (n == 0) {  
                    continue;  
                }  
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();  
                while(it.hasNext()) {  
                    SelectionKey key = it.next();  
                    it.remove();  
                      
                    NioHandler handler = (NioHandler)key.attachment();  
                    handler.execute(key);  
                }  
            }  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static String ByteBufferToString(ByteBuffer dst) {  
        String ret = null;  
        if (dst != null) {  
            dst.flip();  
            byte[] tempb = new byte[dst.limit()];  
            dst.get(tempb);  
            ret = new String(tempb);  
        }  
        return ret;  
    }  
      
    public static ByteBuffer StringToByteBuffer(String s) {  
        ByteBuffer other = null;  
        if (s != null) {  
            other = ByteBuffer.wrap(s.getBytes());  
        }  
        return other;  
    }  
      
    public static int AnalyCmd(String cmd) {  
        int ret = -1;  
          
        if (cmd.toLowerCase().startsWith("upload")) {  
            ret = 0;  
        } else if (cmd.toLowerCase().startsWith("download")) {  
            ret = 1;  
        } else if (cmd.toLowerCase().equals("ls")) {  
            ret = 2;  
        }  
          
        return ret;  
    }  
}  
package com.utils.NIO;
  
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
  
  
public class ServerHandler implements NioHandler {  
  
    private ServerSocketChannel ssc;  
    private Selector selector;  
      
    public ServerHandler(ServerSocketChannel ssc, Selector selector) {  
        this.ssc = ssc;  
        this.selector = selector;  
    }  
      
    @Override  
    public void execute(SelectionKey key) {  
        // TODO Auto-generated method stub  
        try {  
            String cmd = null;  
              
            SocketChannel sc = ssc.accept();  
              
            //读取客户端的命令，并且给客户端回应消息  
            ByteBuffer dst = ByteBuffer.allocate(1024);  
            int ret = sc.read(dst);  
            if (ret > 0) {  
                cmd = FtpNioServer.ByteBufferToString(dst);  
                int n = FtpNioServer.AnalyCmd(cmd);  
                if (n != -1) {  
                    String s = "ack";  
                    ByteBuffer b = ByteBuffer.wrap(s.getBytes());  
                    sc.write(b);  
                } else {  
                    String s = "cmd is invalid, please check and try again!!!";  
                    ByteBuffer b = ByteBuffer.wrap(s.getBytes());  
                    sc.write(b);  
                    sc.close();  
                    return;  
                }  
            } else {  
                System.out.println("client no send cmd!!!");  
                sc.close();  
                return;  
            }  
              
            //配置客户端Socket为非阻塞  
            sc.configureBlocking(false);  
              
            //new 一个新的客户端对象  
            ClientHandler h = new ClientHandler(sc, selector);  
            h.InitClientHandler(cmd);  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
}  
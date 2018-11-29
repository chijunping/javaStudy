package com.utils.NIO;
  
import java.nio.channels.SelectionKey;  
  
public interface NioHandler {  
    void execute(SelectionKey key);  
}  
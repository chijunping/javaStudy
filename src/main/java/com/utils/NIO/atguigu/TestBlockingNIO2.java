package com.utils.NIO.atguigu;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 阻塞式网络通信二：接受服务端反馈信息
 */
public class TestBlockingNIO2 {

	// 客户端：1.将文件读入到FileChannel，2.将FileChannel中数据写到SocketChannel
	@Test
	public void client() throws IOException {
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
		// 1.将文件读入到FileChannel
		FileChannel inChannel = FileChannel.open(Paths.get("src/main/resources/TestFile/dubbo-consumer.zip"), StandardOpenOption.READ);
		// 2.将FileChannel中数据写到SocketChannel
		ByteBuffer buf = ByteBuffer.allocate(1024);
		while (inChannel.read(buf) != -1) {
			buf.flip();
			sChannel.write(buf);
			buf.clear();
		}
        //3.往SocketChannel写完数据后，需要关闭（否则服务端不知道是否传输数据完毕，而持续等待，造成网络阻塞）
		sChannel.shutdownOutput();

		//4.接收服务端的反馈
		int len = 0;
		while ((len = sChannel.read(buf)) != -1) {
			buf.flip();
			System.out.println(new String(buf.array(), 0, len));
			buf.clear();
		}

		inChannel.close();
		sChannel.close();
	}

	// 服务端：1.将SocketChannel中的数据读入到ByteBuffer缓冲区，2.将缓冲区的数据写到FileChannel（也就会写到指定文件）
	@Test
	public void server() throws IOException {
		//1.获取ServerSocketChannel
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		//2.为服务器ServerSocketChannel绑定端口
		ssChannel.bind(new InetSocketAddress(9898));
		//3.接受客户端的SocketChannel对象（该对象含有客户端放进去的数据）
		SocketChannel sChannel = ssChannel.accept();
		//4.创建文件的输出FileChannel
		FileChannel outChannel = FileChannel.open(Paths.get("src/main/resources/TestFile/dubbo-consumer4.zip"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
		//5.创建指定大小缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
        //6.将SocketChannel中数据读入ByteBuffer，再将ByteBuffer中数据写入输出FileChannel
		while (sChannel.read(buf) != -1) {
			buf.flip();
			outChannel.write(buf);
			buf.clear();
		}

		//7.将反馈信息写入SocketChannel，由SocketChannel带着反馈信息给客户端
		buf.put("服务端接收数据成功".getBytes());
		buf.flip();
		sChannel.write(buf);

		sChannel.close();
		outChannel.close();
		ssChannel.close();
	}

}

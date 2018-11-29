package com.utils.NIO.atguigu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.junit.Test;

/*
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * 
 * 二、通道的主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel        ——>tcp（网络传输）
 * 		|--ServerSocketChannel  ——>tcp（网络传输）
 * 		|--DatagramChannel      ——>UDP（网络传输）
 * 
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 		本地 IO：
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile
 * 
 * 		网络IO：
 * 		Socket
 * 		ServerSocket
 * 		DatagramSocket
 * 		
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * 
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * 
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * 
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 * 
 * 七、文件复制速度对比：
 *    传统io-"fileInputStream":7600ms(可调节byte[1024]缓冲区大小调优)
 *    传统io-"BufferdInputStream":5500ms(可调节byte[1024]缓冲区大小调优)
 *    NIO-非直接缓冲区—FileChannel通道+ByteBuffer缓冲区：8000ms
 *    NIO-直接缓冲区-MappedByteBuffer物理内存映射:1300ms
 *    NIO-直接缓冲区-FileChannel.transferTo()transferFrom():630ms
 * 效率总结：transferTo()/transferFrom()>MappedByteBuffer>BufferdInputStream>fileInputStream>FileChannel+ByteBuffer
 */
public class TestChannel {

	/**
	 * 6.字符集（编码-解码）:CharBuffer——>ByteBuffer——>CharBuffer
	 * 
	 * @throws IOException
	 */
	@Test
	public void test6() throws IOException {
		Charset cs1 = Charset.forName("GBK");

		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("尚硅谷威武！");

		// 编码
		cBuf.flip();
		ByteBuffer bBuf = cs1.encode(cBuf);
		for (int i = 0; i < bBuf.limit(); i++) {
			System.out.println(bBuf.get());
		}

		// 解码
		bBuf.flip();
		CharBuffer cBuf2 = cs1.decode(bBuf);
		System.out.println(cBuf2.toString());

		System.out.println("------------------------------------------------------");

		Charset cs2 = Charset.forName("GBK");
		bBuf.flip();
		CharBuffer cBuf3 = cs2.decode(bBuf);
		System.out.println(cBuf3.toString());
	}

	/**
	 * 5.获取MappedByteBuffer下的所有字符集
	 */
	@Test
	public void test5() {
		Map<String, Charset> map = Charset.availableCharsets();

		Set<Entry<String, Charset>> set = map.entrySet();

		for (Entry<String, Charset> entry : set) {
			System.out.println(entry.getKey() + "=" + entry.getValue());
		}
	}

	/**
	 * 4.分散和聚集
	 * 
	 * @throws IOException
	 */
	@Test
	public void test4() throws IOException {
		RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");

		// 1. 获取通道
		FileChannel channel1 = raf1.getChannel();

		// 2. 分配指定大小的缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(100);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);

		// 3. 分散读取：将一个文件拆分读取到不同的byteBuffer中
		ByteBuffer[] bufs = { buf1, buf2 };
		channel1.read(bufs);

		for (ByteBuffer byteBuffer : bufs) {
			byteBuffer.flip();
		}

		System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
		System.out.println("-----------------");
		System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

		// 4. 聚集写入：将一个byteBuffer[] 中不同的byteBuffer的数据写入到一个fileChannel中
		RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();

		channel2.write(bufs);
	}

	/**
	 * 3.通道之间的数据传输(直接缓冲区)——简单
	 * 
	 * @throws IOException
	 */
	@Test
	public void test3() throws IOException {// 630ms
		long start = System.currentTimeMillis();
		FileChannel inChannel = FileChannel.open(Paths.get("C:\\Users\\Administrator\\Desktop\\临时test\\test001.zip"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\Administrator\\Desktop\\test001.zip"), StandardOpenOption.WRITE, StandardOpenOption.READ,
				StandardOpenOption.CREATE);

		// 两个方法都可以实现文件复制传输
		// inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());

		inChannel.close();
		outChannel.close();
		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}

	/**
	 * 2.使用直接缓冲区完成文件的复制(内存映射文件MappedByteBuffer)——复杂
	 * 
	 * @throws IOException
	 */
	@Test
	public void test2() throws IOException {// 1300ms
		long start = System.currentTimeMillis();

		// 1.将文件放入inChannel
		FileChannel inChannel = FileChannel.open(Paths.get("C:\\Users\\Administrator\\Desktop\\临时test\\test001.zip"), StandardOpenOption.READ);
		// StandardOpenOption.WRITE——写权限, StandardOpenOption.READ——读权限,
		// StandardOpenOption.CREATE——有则覆盖，无则创建
		// 2.定义一个空的outChannel
		FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\Administrator\\Desktop\\test001.zip"), StandardOpenOption.WRITE, StandardOpenOption.READ,
				StandardOpenOption.CREATE);

		// 内存映射文件
		// 3.将inChannel中的文件完整的，以只读模式的，映射到物理内存inMappedBuf（此时文件在inMappedBuf）
		MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		// 4.创建inChannel中的文件完整长度的，读写模式的outMappedBuf（此时outMappedBuf中无文件）
		MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());

		// 直接对缓冲区进行数据的读写操作
		byte[] dst = new byte[inMappedBuf.limit()];
		// 5.将inMappedBuf内存中文件读出到byte[] dst
		inMappedBuf.get(dst);
		// 6.将byte[]
		// dst中数据放入outMappedBuf内存中，——>也就会放入outChannel中,——>也就会放入Paths.get("d:/2.mkv")中
		outMappedBuf.put(dst);

		inChannel.close();
		outChannel.close();

		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}

	/**
	 * 1.利用FileChannel通道完成文件的复制（非直接缓冲区）
	 */
	@Test
	public void test1() {// 8000ms
		long start = System.currentTimeMillis();

		FileInputStream fis = null;
		FileOutputStream fos = null;
		// ①获取通道
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream("C:\\Users\\Administrator\\Desktop\\临时test\\test001.zip");
			fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\test001.zip");

			inChannel = fis.getChannel();
			outChannel = fos.getChannel();

			// ②分配指定大小的缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);

			// ③将通道中的数据存入缓冲区中
			while (inChannel.read(buf) != -1) {
				buf.flip(); // 切换读取数据的模式
				// ④将缓冲区中的数据写入通道中
				outChannel.write(buf);
				buf.clear(); // 清空缓冲区
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outChannel != null) {
				try {
					outChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (inChannel != null) {
				try {
					inChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));
	}

	/**
	 * 0.传统IO-“缓冲流”复制
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0() throws Exception {// 5500ms
		long start = System.currentTimeMillis();

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("C:\\Users\\Administrator\\Desktop\\临时test\\test001.zip"));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\test001.zip"));
		byte[] buf = new byte[1024];
		int len;
		while ((len = bis.read(buf)) != -1) {
			bos.write(buf, 0, len);
			bos.flush();
		}
		IOUtils.closeQuietly(bos);
		IOUtils.closeQuietly(bis);

		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));

	}

	/**
	 * 00.传统IO-“普通流”复制
	 * 
	 * @throws Exception
	 */
	@Test
	public void test00() throws Exception {// 7600ms
		long start = System.currentTimeMillis();

		InputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\临时test\\test001.zip");
		OutputStream os = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\test001.zip");
		byte[] buf = new byte[1024];
		int len;
		while ((len = is.read(buf)) != -1) {
			os.write(buf, 0, len);
		}
		IOUtils.closeQuietly(os);
		IOUtils.closeQuietly(is);

		long end = System.currentTimeMillis();
		System.out.println("耗费时间为：" + (end - start));

	}

}

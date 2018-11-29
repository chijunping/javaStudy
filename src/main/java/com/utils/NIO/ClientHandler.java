package com.utils.NIO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientHandler implements NioHandler {

	private String cmd;
	private Selector selector;
	private SocketChannel sc = null;
	private FileChannel fileChannel = null;
	private ByteBuffer buf = ByteBuffer.allocate(1024);

	private long sum = 0;

	public ClientHandler(SocketChannel sc, Selector selector) {
		this.sc = sc;
		this.selector = selector;

		FtpNioServer.connum++;
		System.out.println(FtpNioServer.connum + " Client:" + sc.socket().getRemoteSocketAddress().toString() + " open");
	}

	public int InitClientHandler(String cmd) {
		// 参数判空
		if (cmd == null) {
			return 1;
		}
		this.cmd = cmd;

		try {
			SelectionKey key = null;
			int n = FtpNioServer.AnalyCmd(this.cmd);
			if (n == 0) {
				key = this.sc.register(this.selector, SelectionKey.OP_READ);
				key.attach(this);
			} else {
				key = this.sc.register(this.selector, SelectionKey.OP_WRITE);
				key.attach(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	@Override
	public void execute(SelectionKey key) {
		if (key.isReadable()) {
			try {
				int n = FtpNioServer.AnalyCmd(this.cmd);
				switch (n) {
				case 0:
					proccessUpLoadCmd(key);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			return;
		}

		if (key.isWritable()) {
			try {
				int n = FtpNioServer.AnalyCmd(this.cmd);
				switch (n) {
				case 1:
					proccessDownLoadCmd(key);
					break;
				case 2:
					proccessLsCmd(key);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			return;
		}
	}

	private void proccessUpLoadCmd(SelectionKey key) {

		String s[] = this.cmd.split(":");
		String filepath = FtpNioServer.ROOT + s[1];

		try {
			int n = sc.read(buf);
			if (n >= 0) {
				sum += n;
				WriteToFile(filepath, buf);
			} else {
				ReleaseResource(key);
				System.out.println(FtpNioServer.connum + " read sum:" + sum + " Client:" + sc.socket().getRemoteSocketAddress().toString() + " close");
				FtpNioServer.connum--;
				return;
			}
		} catch (IOException e) {
			try {
				ReleaseResource(key);
				FtpNioServer.connum--;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("IOException " + FtpNioServer.connum + " Client:" + sc.socket().getRemoteSocketAddress().toString() + " close");
			return;
		}
	}

	private void proccessDownLoadCmd(SelectionKey key) {

		String s[] = this.cmd.split(":");
		String filepath = FtpNioServer.ROOT + s[1];

		try {
			int n = ReadFromFile(filepath, buf);
			if (n >= 0) {
				sum += n;
				buf.flip();
				sc.write(buf);
			} else {
				ReleaseResource(key);
				System.out.println(FtpNioServer.connum + " send sum:" + sum + " Client:" + sc.socket().getRemoteSocketAddress().toString() + " close");
				FtpNioServer.connum--;
				return;
			}
		} catch (Exception e) {
			try {
				ReleaseResource(key);
				FtpNioServer.connum--;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("IOException " + FtpNioServer.connum + " Client:" + sc.socket().getRemoteSocketAddress().toString() + " close");
			return;
		}
	}

	private void proccessLsCmd(SelectionKey key) throws IOException {
		File dir = new File(FtpNioServer.ROOT);
		File files[] = dir.listFiles();

		String ret = null;
		for (File f : files) {
			if (ret == null) {
				ret = f.getName();
			} else {
				ret += ";";
				ret += f.getName();
			}
		}

		ByteBuffer src = null;
		if (ret != null) {
			src = ByteBuffer.wrap(ret.getBytes());
		} else {
			String error = "cmd execute fail!!!";
			src = ByteBuffer.wrap(error.getBytes());
		}

		// write data to client socket channel
		this.sc.write(src);

		ReleaseResource(key);

		System.out.println(FtpNioServer.connum + " Client:" + sc.socket().getRemoteSocketAddress().toString() + " close");

		FtpNioServer.connum--;
	}

	private void WriteToFile(String path, ByteBuffer buf) throws IOException {

		if (fileChannel == null) {
			fileChannel = new RandomAccessFile(path, "rw").getChannel();
		}

		buf.flip();
		fileChannel.write(buf);
		buf.clear();
	}

	@SuppressWarnings("resource")
	private int ReadFromFile(String path, ByteBuffer buf) throws IOException {

		if (fileChannel == null) {
			fileChannel = new RandomAccessFile(path, "r").getChannel();
		}

		buf.clear();
		return fileChannel.read(buf);
	}

	private void ReleaseResource(SelectionKey key) throws IOException {

		sc.close();
		key.cancel();

		if (fileChannel != null) {
			fileChannel.close();
		}
	}
}

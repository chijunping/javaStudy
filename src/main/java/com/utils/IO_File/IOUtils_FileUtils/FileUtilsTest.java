package com.utils.IO_File.IOUtils_FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.Test;

/**
 * 使用org.apache.commons.io.FileUtils工具类简化File操作
 * 
 * @ClassName: FileUtilsTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年5月26日 下午2:37:27
 */
public class FileUtilsTest {

	/**
	 * 文件夹大小：sizeOfDirectory()
	 */
	@Test
	public void sizeOfDirectory() {
		File dir = new File("C:\\Users\\Administrator\\Desktop\\临时test");
		long size = FileUtils.sizeOfDirectory(dir);
		System.out.println("该文件夹占用磁盘大小为：" + size / (1024 * 1024) + "M");
	}

	/**
	 * 逐层创建目录/文件（推荐）： 传统IO中的以下方法： f.getParentFile(); parentFile.mkdirs();
	 * f.createNewFile();
	 */
	@Test
	public void touch() {

		File file = null;
		try {
			file = new File("C:\\Users\\Administrator\\Desktop/wwwww/aaaaa/javaTest.txt");
			// 如果指定file不存在则逐层新建，如果存在则更新文件的修改时间(内容不变)
			FileUtils.touch(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1.删除文件（或文件夹）及其子目录 2.清空文件夹下内容
	 */
	@Test
	public void deleteDirectory() throws Exception {

		File dir = new File("C:\\Users\\Administrator\\Desktop/wwwww/aaaaa");
		// 清空目录下的文件（清空最后一层文件夹中的文件）
		FileUtils.cleanDirectory(dir);
		// 删除目录和目录下的文件（删除最后一层文件夹和其中的文件）
		FileUtils.deleteDirectory(dir);
	}

	/**
	 * 网络流保存为文件
	 */
	@Test
	public void copyURLToFile() throws Exception {

		URL url = new URL("http://www.163.com");
		File file = new File("src/main/resources/TestFile/163.html");
		// 目标目录会逐层创建或重写覆盖
		FileUtils.copyURLToFile(url, file);
	}

	/**
	 * “文件”复制：指定的目录中生成一个同名同内容文件
	 */
	@Test
	public void copyFileToDirectory() throws IOException {

		File srcfile = new File("C:\\Users\\Administrator\\Desktop/wwwww/aaaaa/javaTest.txt");
		File destDir = new File("C:\\Users\\Administrator\\Desktop/qq");
		// 目标目录会逐层创建或重写覆盖
		FileUtils.copyFileToDirectory(srcfile, destDir);
	}

	/**
	 * 文件“内容”复制：复制到指定文件内，覆盖文件原来的内容
	 */
	@Test
	public void copyFile() throws IOException {

		File srcfile = new File("C:\\Users\\Administrator\\Desktop\\临时test\\javaTest.txt");
		File destfile = new File("C:\\Users\\Administrator\\Desktop/qq/a.java");
		// 目标目录会逐层创建或重写覆盖
		FileUtils.copyFile(srcfile, destfile);
	}

	/**
	 * 文本写入指定文件：writeStringToFile
	 */
	@Test
	public void writeStringToFile() throws Exception {
		String name = "my name is panxiuyan";

		File file = new File("c:\\name.txt");
		// 目标目录会逐层创建或重写覆盖
		FileUtils.writeStringToFile(file, name);
	}

	@Test
	public void testSome() throws Exception {
		File file = new File("c:\\pc面料需手动上传列表.txt");

		// 将Long字节数——>人性化单位大小:EB, PB, TB, GB, MB, KB or bytes
		String byteCountToDisplaySize = FileUtils.byteCountToDisplaySize(1024 * 100024);
		System.out.println(byteCountToDisplaySize);

		//
		long csum = FileUtils.checksum(file, new CRC32()).getValue();
		System.out.println(csum);
	}
	

	private static File parent = new File("E:/testFile");

	/**
	 * 获取临时目录、主目录
	 */
	@Test
	public void getDirTest() {
		/*
		 * 获取临时目录
		 */
		System.out.println(FileUtils.getTempDirectory());
		/*
		 * 获取用户主目录
		 */
		System.out.println(FileUtils.getUserDirectory());

	}

	/**
	 * 文件大小单位人性化
	 */
	@Test
	public void byteCountToDisplaySize() {

		/*
		 * 人性化文件大小单位：以可读的方式，返回文件的大小EB, PB, TB, GB, MB, KB or bytes
		 */
		String sise1 = FileUtils.byteCountToDisplaySize(1);
		String sise2 = FileUtils.byteCountToDisplaySize(10000000);
		String sise3 = FileUtils.byteCountToDisplaySize(10000000000L);
		String sise4 = FileUtils.byteCountToDisplaySize(22222222222222L);
		String sise5 = FileUtils.byteCountToDisplaySize(22222222222222222L);
		String sise6 = FileUtils.byteCountToDisplaySize(2222222222222222222L);
		System.out.println(sise1);
		System.out.println(sise2);
		System.out.println(sise3);
		System.out.println(sise4);
		System.out.println(sise5);
		System.out.println(sise6);
	}

	/**
	 * 打开文件流
	 */
	@Test
	public void openStream() throws IOException {
		/*
		 * 获取文件输入和输出的文件流, 文件是目录或者不存在，都会抛出异常
		 */
		InputStream in = FileUtils.openInputStream(new File("E:/testFile/test1/临时文本.txt"));
		OutputStream out = FileUtils.openOutputStream(new File("E:/testFile/test2/sgfdsg.log"));
		// 是否追加的形式添加内容(不知道怎么用)
		OutputStream out2 = FileUtils.openOutputStream(new File("E:/testFile/test2/sgfdsg.log"), true);
	}

	/**
	 * 查找文件
	 */
	@Test
	public void findFiles() {
		/*
		 * 获取指定文件夹下的文件（推荐）： 参数：1.指定文件夹；2.过滤后缀；3.是否递归查找
		 */
		List<File> files = (List<File>) FileUtils.listFiles(parent, new String[] { "txt", "log" }, true);// TODO
		System.out.println(files); // 重点看一下源码

		/*
		 * 获取指定文件夹下的文件（不如上面的方法）： 参数：1.指定文件夹；2.过滤后缀；3.是否递归查找(null——>不递归)
		 */
		Collection<File> listFiles1 = FileUtils.listFiles(parent, FileFilterUtils.suffixFileFilter("txt"), null);
		Collection<File> listFiles2 = FileUtils.listFiles(parent, FileFilterUtils.suffixFileFilter("txt"), DirectoryFileFilter.INSTANCE);
		System.out.println(listFiles1);
		System.out.println(listFiles2);

		/*
		 * 返回文件迭代器
		 */
		Iterator<File> files_iter = FileUtils.iterateFiles(parent, new String[] { "txt", "log" }, true);
		while (files_iter.hasNext()) {
			File file = files_iter.next();
			System.out.println("文件迭代器：" + file);
		}
		/*
		 * 把collection<File>转换成File[]
		 */
		File[] fileArray = FileUtils.convertFileCollectionToFileArray(files);
		for (File file : fileArray) {
			System.out.println("文件结合转数组：" + file);
		}
	}

	/**
	 * 操作文件
	 */
	@Test
	public void FileOperation() throws IOException {
		// 创建文件/文件夹，如果文件存在则更新时间；如果不存在，创建一个空文件
		// 创建空文件的方式为：
		
		// 创建的不是文件夹，是无后缀文件
		FileUtils.touch(new File("E:/testFile/test3"));
		// 可逐层创建文件夹和文件
		FileUtils.touch(new File("E:/testFile/test4/哈哈.txt"));

		// 文件内容的对比：前后空格、换行符会影响结果
		boolean isEquals1 = FileUtils.contentEquals(new File("E:/testFile/test1/临时文本-带换行符.txt"), new File("E:/testFile/test1/临时文本-尾部有空格.txt"));
		boolean isEquals2 = FileUtils.contentEquals(new File("E:/testFile/test1/临时文-开头有空格.txt"), new File("E:/testFile/test1/临时文本-尾部有空格.txt"));
		System.out.println(isEquals1);
		System.out.println(isEquals2);
		// 忽略换行符，第三个参数是字符集（测试结果：有换行符还是判定为不等）
		boolean isEquals3 = FileUtils.contentEqualsIgnoreEOL(new File("E:/testFile/test1/临时文本-带换行符.txt"), new File("E:/testFile/test1/临时文本-没空格和换行符.txt"), "utf-8");
		System.out.println(isEquals3);
		
		// 根据URL获取文件（暂未验证）
		FileUtils.toFile(new URL("file://E:/testFile/test1"));
		FileUtils.toFiles(null);
		FileUtils.toURLs(new File[] { new File("E:/testFile/test1") });

		// 文件拷贝
		FileUtils.copyFileToDirectory(new File("/test1"), new File("/dir"), true);// 第三个参数是否更新时间
		FileUtils.copyFile(new File("/source"), new File("/target"), true);

		// 目录拷贝
		File srcDir = new File("/source");
		File destDir = new File("/target");

		FileUtils.copyDirectoryToDirectory(new File("/source"), new File("/target"));
		FileUtils.copyDirectory(new File("/source"), new File("/target"));

		FileUtils.copyDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY);// 仅仅拷贝目录

		IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".txt");// 创建.txt过滤器
		IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);
		// 创建包含目录或者txt文件的过滤器
		FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, txtFiles);
		// Copy using the filter
		FileUtils.copyDirectory(srcDir, destDir, filter);

		// 文件拷贝
		FileUtils.copyInputStreamToFile(new FileInputStream("/test"), new File("/test"));
		FileUtils.copyURLToFile(new URL("file:/test"), new File("/test"));

		// 删除文件
		FileUtils.deleteDirectory(new File("/test"));// 删除目录下所有的内容
		FileUtils.deleteQuietly(new File("/test"));// 如果是目录，会级联删除；不会抛出异常

		// 判断文件是否存在
		FileUtils.directoryContains(new File("/dir"), new File("/file"));

		// 清除目录中的内容,不会删除该目录；
		// 先verifiedListFiles检查目录，检查目录是否为目录、是否存在，然后调用listFiles，如果返回null，则抛出异常
		// 遍历目录中的文件，如果是目录则递归删除；如果是文件则强制删除，删除失败（文件不存在或无法删除）都会抛出异常
		FileUtils.cleanDirectory(new File("/dir"));//

		// 等待一个文件xx秒，直到文件创建后才返回。每max(100,remainning)循环检查一次
		while (FileUtils.waitFor(new File("/dir"), 60)) {
		}

		// 读取目标文件，内部调用IOUtils.toString(inputstream,"utf-8")
		String str = FileUtils.readFileToString(new File("/dir"), "utf-8");

		// 内部调用IOUtils.toByteArray(in)
		byte[] bytes = FileUtils.readFileToByteArray(new File("/dir"));

		// 内部调用IOUtils.readLines(in, Charsets.toCharset(encoding));
		List<String> strs = FileUtils.readLines(new File("/dir"), "utf-8");

		// 内部调用IOUtils.lineIterator(in, encoding)
		FileUtils.lineIterator(new File("/dir"), "utf-8");

		// 四个参数分别为：目标文件，写入的字符串，字符集，是否追加
		FileUtils.writeStringToFile(new File("/target"), "string", "utf-8", true);

		// write可以接受charsequence类型的数据，string,stringbuilder和stringbuffer都是实现了charsequence接口
		FileUtils.write(new File("/target"), "target char sequence", "utf-8", true);

		FileUtils.writeByteArrayToFile(new File("/target"), "bytes".getBytes());// (file,字符数组)
		FileUtils.writeByteArrayToFile(new File("/target"), "bytes".getBytes(), true);// (file,字符数组，是否追加)
//		FileUtils.writeByteArrayToFile(new File("/target"), "bytes".getBytes(), 0, 10);// (file,字符数组，起始位置，结束位置)
//		FileUtils.writeByteArrayToFile(new File("/target"), "bytes".getBytes(), 0, 10, true);// (file,字符数组，起始位置，结束位置，是否追加)

		// writeLines多了一个lineEnding参数
		FileUtils.writeLines(new File("/target"), "utf-8", FileUtils.readLines(new File("/target"), "utf-8"));

		// 强制删除
		FileUtils.forceDelete(new File("/target"));

		// 在JVM
		FileUtils.forceDeleteOnExit(new File("/target"));

		// 强制创建文件目录，如果文件存在，会抛出异常
		FileUtils.forceMkdir(new File("/target"));

		// 强制创建父级目录
//		FileUtils.forceMkdirParent(new File("/xxxx/target"));

		// 如果是文件，直接读取文件大小；如果是目录，级联计算文件下的所有文件大小
		FileUtils.sizeOf(new File("/target"));// 返回Long
		FileUtils.sizeOfAsBigInteger(new File("/target"));// 返回BigInteger
		FileUtils.sizeOfDirectory(new File("/target"));
		FileUtils.sizeOfDirectoryAsBigInteger(new File("/target"));

		// 对比文件新旧
		FileUtils.isFileNewer(new File("/target"), new File("/file"));

		FileUtils.isFileOlder(new File("/target"), new Date());

		FileUtils.checksum(new File("/target"), new CRC32());
		FileUtils.checksumCRC32(new File("/target"));

		FileUtils.moveDirectory(new File("/target"), new File("/file"));
		FileUtils.moveDirectoryToDirectory(new File("/target"), new File("/file"), true);
		FileUtils.moveFile(new File("/target"), new File("/file"));
		FileUtils.moveFileToDirectory(new File("/target"), new File("/dir"), true);
		FileUtils.moveToDirectory(new File("/target"), new File("/dir"), true);

		FileUtils.isSymlink(new File("/target"));
	}

}

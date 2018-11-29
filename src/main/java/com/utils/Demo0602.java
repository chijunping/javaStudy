package com.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import org.junit.Test;

public class Demo0602 extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static JButton open = null;
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public Demo0602() {

		open = new JButton("open");
		this.add(open);
		this.setBounds(500, 500, 500, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		open.addActionListener(this);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Demo0602();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		// 目录和文件都可以选择
		// jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
		jfc.showDialog(new JLabel(), "选择");
		// 只能选择文件
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		// 自定义过滤器
		// FileNameExtensionFilter filter = new
		// FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		jfc.setFileFilter(new FileFilter() {
			// 设置文件格式
			private String description = "txt";

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public boolean accept(File f) {
				if (f != null) {
					if (f.isDirectory()) {
						return false;
					}
					String extension = getExtension(f);
					if (extension != null && extension.equalsIgnoreCase(this.description))
						return true;
				}
				return false;
			}

			private String getExtension(File f) {
				if (f != null) {
					String filename = f.getName();
					int i = filename.lastIndexOf('.');
					if (i > 0 && i < filename.length() - 1) {
						return filename.substring(i + 1).toLowerCase();
					}
				}
				return null;
			}
		});
		File file = jfc.getSelectedFile();
		if (file != null) {
			showFileContent(file);
		} else {
			System.out.println("不支持的文件类型");
		}
	}

	/**
	 * 用于显示文件内容
	 * 
	 * @param file
	 */
	public void showFileContent(File file) {
		JFrame jFrame = new JFrame(file.getAbsolutePath());
		BufferedReader br = null;
		JTextArea jta = new JTextArea();
		int line = 1;
		try {
			br = new BufferedReader(new FileReader(file));
			String tmp = null;

			while ((tmp = br.readLine()) != null) {
				jta.setText(jta.getText() + "\r\n" + tmp);
				line++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane(jta);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		jFrame.add(scrollPane, BorderLayout.CENTER);
		jFrame.setBounds(0, 0, screenSize.width, screenSize.height);
		jFrame.setVisible(true);
	}

	@Test
	public void test() {
		List<String> list1 = Arrays.asList("aa", "bb", "cc");
		List<String> list2 = Arrays.asList("bb", "aa", "cc");
		boolean flag = list1.containsAll(list2) && list2.containsAll(list1);
		System.out.println(flag ? "想等！" : "不想等！");
	}

	@Test
	public void testFile() throws Exception {
		int number = 10;
		// 原始数二进制
		printInfo(number);
		number = number << 1;
		// 左移一位
		printInfo(number);
		number = number >> 1;
		// 右移一位
		printInfo(number);
	}

	/**
	 * 输出一个int的二进制数
	 * 
	 * @param num
	 */
	private static void printInfo(int num) {
		System.out.println(Integer.toBinaryString(num));
	}

	@Test
	public void pattern() throws Exception {
		/**
		 * 
		 * 一、引子 话说十年前，有一个爆发户，他家有三辆汽车（Benz（奔驰）、Bmw（宝马）、Audi（奥迪）看来这人比较爱国，没有日本车），
		 * 还雇了司机为他开车
		 * 。不过，爆发户坐车时总是这样：上Benz车后跟司机说"开奔驰车！"，坐上Bmw后他说"开宝马车！"，坐上Audi后他说"开奥迪车！"
		 * 。你一定说：这人有病！直接说开车不就行了？！
		 * 而当把这个爆发户的行为放到我们程序语言中来，我们发现C语言一直是通过这种方式来坐车的！幸运的是
		 * ，这种有病的现象在OO语言中可以避免了。下面以Java语言为基础来引入我们本文的主题：工厂模式！！
		 * 
		 * 二、简介 工厂模式主要是为创建对象提供了接口。工厂模式按照《Java与模式》中的提法分为三类： 1. 简单工厂模式(Simple
		 * Factory) 2. 工厂方法模式(Factory Method) 3. 抽象工厂模式(Abstract Factory)
		 * 这三种模式从上到下逐步抽象
		 * ，并且更具一般性。还有一种分类法，就是将简单工厂模式看为工厂方法模式的一种特例，两个归为一类。下面是使用工厂模式的两种情况：
		 * 1.在编码时不能预见需要创建哪种类的实例。 2.系统不应依赖于产品类实例如何被创建、组合和表达的细节
		 * 
		 * 三、简单工厂模式 顾名思义，这个模式本身很简单，而且使用在业务较简单的情况下。 它由三种角色组成（关系见下面的类图）：
		 * 1、工厂类角色：这是本模式的核心，含有一定的商业逻辑和判断逻辑。在java中它往往由一个具体类实现。
		 * 
		 * 2、抽象产品角色：它一般是具体产品继承的父类或者实现的接口。在java中由接口或者抽象类来实现。
		 * 
		 * 3、具体产品角色：工厂类所创建的对象就是此角色的实例。在java中由一个具体类实现。 clip_image002
		 * 那么简单工厂模式怎么用呢？我来举个例子吧，我想这个比讲一大段理论上的文字描述要容易理解的多！下面就来给那个暴发户治病: P
		 * 在使用了简单工厂模式后，现在暴发户只需要坐在车里对司机说句："开车"就可以了。来看看怎么实现的：
		 * 
		 * //抽象产品角色 public interface Car{ public void drive(); }
		 * 
		 * //具体产品角色 public class Benz implements Car{ public void drive() {
		 * System.out.println("Driving Benz "); } }
		 * 
		 * public class Bmw implements Car{ public void drive() {
		 * System.out.println("Driving Bmw "); } } 。。。（奥迪我就不写了:P）
		 * 
		 * //工厂类角色 public class Driver{
		 * 
		 * //工厂方法 //注意 返回类型为抽象产品角色 public static Car driverCar(String s)throws
		 * Exception {
		 * 
		 * //判断逻辑，返回具体的产品角色给Client if(s.equalsIgnoreCase("Benz")) return new
		 * Benz(); else if(s.equalsIgnoreCase("Bmw")) return new Bmw();
		 * 
		 * ...... else throw new Exception(); 。。。
		 * 
		 * //欢迎暴发户出场...... public class Magnate{ public static void
		 * main(String[] args){ try{ //告诉司机我今天坐奔驰 Car car =
		 * Driver.driverCar("benz"); //下命令：开车 car.drive(); 。。。
		 * 
		 * 
		 * 
		 * 如果将所有的类放在一个文件中，请不要忘记只能有一个类被声明为public。 程序中类之间的关系如下：
		 * 
		 * clip_image004
		 * 
		 * 这便是简单工厂模式了。下面是其好处：
		 * 
		 * 首先，使用了简单工厂模式后，我们的程序不在"有病"，更加符合现实中的情况；而且客户端免除了直接创建产品对象的责任，而仅仅负责"消费"产品（
		 * 正如暴发户所为）。 下面我们从开闭原则上来分析下简单工厂模式。当暴发户增加了一辆车的时候，只要符合抽象产品制定的合同，
		 * 那么只要通知工厂类知道就可以被客户使用了
		 * 。那么对于产品部分来说，它是符合开闭原则的--对扩展开放、对修改关闭；但是工厂部分好像不太理想，因为每增加一辆车
		 * ，都要在工厂类中增加相应的商业逻辑和判断逻辑，这显自然是违背开闭原则的。
		 * 对于这样的工厂类（在我们的例子中是为司机师傅），我们称它为全能类或者上帝类。
		 * 我们举的例子是最简单的情况，而在实际应用中，很可能产品是一个多层次的树状结构
		 * 。由于简单工厂模式中只有一个工厂类来对应这些产品，所以这可能会把我们的上帝类坏了，进而累坏了我们可爱的程序员:(
		 * 正如我前面提到的简单工厂模式适用于业务将简单的情况下。而对于复杂的业务环境可能不太适应阿。这就应该由工厂方法模式来出场了！！
		 * 
		 * 四、工厂方法模式 先来看下它的组成吧：
		 * 1、抽象工厂角色：这是工厂方法模式的核心，它与应用程序无关。是具体工厂角色必须实现的接口或者必须继承的父类
		 * 。在java中它由抽象类或者接口来实现。
		 * 2、具体工厂角色：它含有和具体业务逻辑有关的代码。由应用程序调用以创建对应的具体产品的对象。在java中它由具体的类来实现。
		 * 3、抽象产品角色：它是具体产品继承的父类或者是实现的接口。在java中一般有抽象类或者接口来实现。
		 * 4、具体产品角色：具体工厂角色所创建的对象就是此角色的实例。在java中由具体的类来实现。 来用类图来清晰的表示下的它们之间的关系：
		 * 
		 * clip_image006
		 * 
		 * 我们还是老规矩使用一个完整的例子来看看工厂模式各个角色之间是如何来协调的。话说暴发户生意越做越大，自己的爱车也越来越多。
		 * 这可苦了那位司机师傅了
		 * ，什么车它都要记得，维护，都要经过他来使用！于是暴发户同情他说：看你跟我这么多年的份上，以后你不用这么辛苦了，我给你分配几个人手
		 * ，你只管管好他们就行了！于是，工厂方法模式的管理出现了。代码如下：
		 * //抽象产品角色，具体产品角色与简单工厂模式类似，只是变得复杂了些，这里略。
		 * 
		 * //抽象工厂角色 public interface Driver{ public Car driverCar(); } public
		 * class BenzDriver implements Driver{ public Car driverCar(){ return
		 * new Benz(); } } public class BmwDriver implements Driver{ public Car
		 * driverCar() { return new Bmw(); } } ......//应该和具体产品形成对应关系，这里略...
		 * //有请暴发户先生 public class Magnate { public static void main(String[]
		 * args) { try{ Driver driver = new BenzDriver();
		 * 
		 * Car car = driver.driverCar(); car.drive(); }catch(Exception e) { } }
		 * }
		 * 
		 * 工厂方法使用一个抽象工厂角色作为核心来代替在简单工厂模式中使用具体类作为核心。
		 * 让我们来看看工厂方法模式给我们带来了什么？使用开闭原则来分析下工厂方法模式
		 * 。当有新的产品（即暴发户的汽车）产生时，只要按照抽象产品角色、抽象工厂角色提供的合同来生成
		 * ，那么就可以被客户使用，而不必去修改任何已有的代码。看来，工厂方法模式是完全符合开闭原则的！
		 * 使用工厂方法模式足以应付我们可能遇到的大部分业务需求。但是当产品种类非常多时，就会出现大量的与之对应的工厂类，这不应该是我们所希望的。
		 * 所以我建议在这种情况下使用简单工厂模式与工厂方法模式相结合的方式来减少工厂类
		 * ：即对于产品树上类似的种类（一般是树的叶子中互为兄弟的）使用简单工厂模式来实现。
		 * 当然特殊的情况，就要特殊对待了：对于系统中存在不同的产品树，而且产品树上存在产品族，那么这种情况下就可能可以使用抽象工厂模式了。
		 * 
		 * 五、小结 让我们来看看简单工厂模式、工厂方法模式给我们的启迪：
		 * 如果不使用工厂模式来实现我们的例子，也许代码会减少很多--只需要实现已有的车
		 * ，不使用多态。但是在可维护性上，可扩展性上是非常差的（你可以想象一下
		 * ，添加一辆车后要牵动的类）。因此为了提高扩展性和维护性，多写些代码是值得的。
		 * 
		 * 六、抽象工厂模式
		 * 先来认识下什么是产品族：位于不同产品等级结构中，功能相关联的产品组成的家族。如果光看这句话就能清楚的理解这个概念，我不得不佩服你啊
		 * 。还是让我们用一个例子来形象地说明一下吧。
		 * 
		 * 图中的BmwCar和BenzCar就是两个产品树（产品层次结构）；
		 * 而如图所示的BenzSportsCar和BmwSportsCar就是一个产品族
		 * 。他们都可以放到跑车家族中，因此功能有所关联。同理BmwBussinessCar和BenzSportsCar也是一个产品族。
		 * 回到抽象产品模式的话题上
		 * ，可以这么说，它和工厂方法模式的区别就在于需要创建对象的复杂程度上。而且抽象工厂模式是三个里面最为抽象、最具一般性的
		 * 。抽象工厂模式的用意为：给客户端提供一个接口，可以创建多个产品族中的产品对象。而且使用抽象工厂模式还要满足一下条件：
		 * 1.系统中有多个产品族，而系统一次只可能消费其中一族产品 2.同属于同一个产品族的产品一起使用时。
		 * 来看看抽象工厂模式的各个角色（和工厂方法的如出一辙）：
		 * 抽象工厂角色：这是工厂方法模式的核心，它与应用程序无关。是具体工厂角色必须实现的接口或者必须继承的父类
		 * 。在java中它由抽象类或者接口来实现。
		 * 具体工厂角色：它含有和具体业务逻辑有关的代码。由应用程序调用以创建对应的具体产品的对象。在java中它由具体的类来实现。
		 * 抽象产品角色：它是具体产品继承的父类或者是实现的接口。在java中一般有抽象类或者接口来实现。
		 * 具体产品角色：具体工厂角色所创建的对象就是此角色的实例。在java中由具体的类来实现。
		 * 
		 * clip_image008
		 * 
		 * 看过了前两个模式，对这个模式各个角色之间的协调情况应该心里有个数了，我就不举具体的例子了。只是一定要注意满足使用抽象工厂模式的条件哦，
		 * 不然即使存在了多个产品树，也存在产品族，但是不能使用的。
		 * */

		/**
		 * 
		 * 建造者模式
		 * 
		 * 11.1 变化是永恒的
		 * 
		 * 又是一个周三，快要下班了，老大突然拉住我，喜滋滋地告诉我:“牛叉公司很满意我们做的模型，又签订了一个合同，把奔驰、
		 * 宝马的车辆模型都交给我们公司制作了
		 * ，不过这次又额外增加了一个新需求：汽车的启动、停止、喇叭声音、引擎声音都由客户自己控制，他想什么顺序就什么顺序，这个没问题吧?”
		 * 
		 * 看着老大殷切的目光，我还能说啥？非常肯定地点头，“没问题！”，加班加点做呗，“再苦再累就当自己二百五！再难再险就当自己二皮脸！与君共勉！”
		 * 这句话说出了俺的心声。
		 * 
		 * 那任务是接下来，又是一个时间紧，工程量大的项目，为什么是“又”呢？因为基本上每个项目都是如此，我该怎么来完成这个任务呢？
		 * 
		 * 首先我们分析一下需求，奔驰、宝马都是一个产品，他们有共有的属性，牛叉公司关心的是单个模型的运行过程：奔驰模型A是先有引擎声音，然后再响喇叭
		 * ；奔驰模型B是先启动起来，然后再有引擎声音，这才是牛叉公司要关心的，那到我们老大这边呢，就是满足人家的要求，
		 * 要什么顺序就立马能产生什么顺序的模型出来
		 * ，我就负责把老大的要求实现出来，而且还要是批量的，也就是说牛叉公司下单订购宝马A车模，我们老大马上就找我
		 * “生产一个这样的车模，启动完毕后，喇叭响一下
		 * ”，然后我们就准备开始批量生产这些模型。由我生产出N多个奔驰和宝马车辆模型，这些车辆模型的都有run
		 * ()方法，但是具体到每一个模型的run(
		 * )方法中间的执行任务的顺序是不同的，老大说要啥顺序，我就给啥顺序，最终客户买走后只能是既定的模型。好
		 * ，需求还是比较复杂，我们先一个一个的解决，先从找一个最简单的切入点——产品类，每个车都是一个产品，如图11-1所示。
		 * 
		 * clip_image002
		 * 
		 * 图11-1 汽车模型类图
		 * 
		 * 类图比较简单，在CarModel中我们定义了一个setSequence方法，车辆模型的这几个动作要如何排布，
		 * 是在这个ArrayList中定义的
		 * ，然后run()方法根据sequence定义的顺序完成指定的顺序动作，与我们上一章节介绍的模板方法模式是不是非常类似？好
		 * ，我们先看CarModel源代码，如代码清单11-1所示。
		 * 
		 * 代码清单11-1 车辆模型的抽象类
		 * 
		 * 
		 * public abstract class CarModel {
		 * 
		 * //这个参数是各个基本方法执行的顺序
		 * 
		 * private ArrayList<String> sequence = new ArrayList<String>();
		 * 
		 * //模型是启动开始跑了
		 * 
		 * protected abstract void start();
		 * 
		 * //能发动，那还要能停下来，那才是真本事
		 * 
		 * protected abstract void stop();
		 * 
		 * //喇叭会出声音，是滴滴叫，还是哔哔叫
		 * 
		 * protected abstract void alarm();
		 * 
		 * //引擎会轰隆隆地响，不响那是假的
		 * 
		 * protected abstract void engineBoom();
		 * 
		 * //那模型应该会跑吧，别管是人推的，还是电力驱动，总之要会跑
		 * 
		 * final public void run() {
		 * 
		 * //循环一边，谁在前，就先执行谁
		 * 
		 * for(int i=0;i<this.sequence.size();i++){
		 * 
		 * String actionName = this.sequence.get(i);
		 * 
		 * if(actionName.equalsIgnoreCase("start")){
		 * 
		 * this.start(); //开启汽车
		 * 
		 * }else if(actionName.equalsIgnoreCase("stop")){
		 * 
		 * this.stop(); //停止汽车
		 * 
		 * }else if(actionName.equalsIgnoreCase("alarm")){
		 * 
		 * this.alarm(); //喇叭开始叫了
		 * 
		 * }else if(actionName.equalsIgnoreCase("engine boom")){ //如果是engine
		 * boom关键字
		 * 
		 * this.engineBoom(); //引擎开始轰鸣
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * //把传递过来的值传递到类内
		 * 
		 * final public void setSequence(ArrayList<String> sequence){
		 * 
		 * this.sequence = sequence;
		 * 
		 * }
		 * 
		 * } CarModel的设计原理是这样的，setSequence方法是允许客户自己设置一个顺序，是要先启动响一下喇叭再跑起来，
		 * 还是要先响一下喇叭再启动
		 * ，对于一个具体的模型永远都固定的，但是对N多个模型就是动态的了。在子类中实现父类的基本方法，run()方法读取sequence
		 * ，然后遍历sequence中的字符串，哪个字符串在先，就先执行哪个方法。
		 * 
		 * 两个实现类分别实现父类的基本方法，奔驰模型如代码清单11-2所示。
		 * 
		 * 代码清单11-2 奔驰模型代码
		 * 
		 * public class BenzModel extends CarModel {
		 * 
		 * protected void alarm() {
		 * 
		 * System.out.println("奔驰车的喇叭声音是这个样子的...");
		 * 
		 * }
		 * 
		 * protected void engineBoom() {
		 * 
		 * System.out.println("奔驰车的引擎室这个声音的...");
		 * 
		 * }
		 * 
		 * protected void start() {
		 * 
		 * System.out.println("奔驰车跑起来是这个样子的...");
		 * 
		 * }
		 * 
		 * protected void stop() {
		 * 
		 * System.out.println("奔驰车应该这样停车...");
		 * 
		 * }
		 * 
		 * } 宝马车模型如代码清单11-3所示。
		 * 
		 * 代码清单11-3 宝马模型代码
		 * 
		 * public class BMWModel extends CarModel {
		 * 
		 * protected void alarm() {
		 * 
		 * System.out.println("宝马车的喇叭声音是这个样子的...");
		 * 
		 * }
		 * 
		 * protected void engineBoom() {
		 * 
		 * System.out.println("宝马车的引擎室这个声音的...");
		 * 
		 * }
		 * 
		 * protected void start() {
		 * 
		 * System.out.println("宝马车跑起来是这个样子的...");
		 * 
		 * }
		 * 
		 * protected void stop() {
		 * 
		 * System.out.println("宝马车应该这样停车...");
		 * 
		 * }
		 * 
		 * } 两个产品的实现类都完成，我们来模拟一下牛叉公司的要求：生产1件奔驰模型，要求跑的时候，先发动引擎，然后再挂档启动，然后停下来，
		 * 不需要喇叭。这个需求很容易满足，我们增加一个场景类实现该需求，如代码清单11-4所示。
		 * 
		 * 代码清单11-4 宝马模型代码
		 * 
		 * public class Client {
		 * 
		 * public static void main(String[] args) {
		 * 
		 * 
		 * 
		 * 客户告诉牛叉公司，我要这样一个模型，然后牛叉公司就告诉我老大
		 * 
		 * 说要这样一个模型，这样一个顺序，然后我就来制造
		 * 
		 * 
		 * BenzModel benz = new BenzModel();
		 * 
		 * //存放run的顺序
		 * 
		 * ArrayList<String> sequence = new ArrayList<String>();
		 * 
		 * sequence.add("engine boom"); //客户要求，run的时候时候先发动引擎
		 * 
		 * sequence.add("start"); //启动起来
		 * 
		 * sequence.add("stop"); //开了一段就停下来
		 * 
		 * //我们把这个顺序赋予奔驰车
		 * 
		 * benz.setSequence(sequence);
		 * 
		 * benz.run();
		 * 
		 * }
		 * 
		 * } 运行结果如下所示。
		 * 
		 * 奔驰车的引擎是这个声音的...
		 * 
		 * 奔驰车跑起来是这个样子的...
		 * 
		 * 奔驰车应该这样停车...
		 * 
		 * 看，我们组装了这样的一辆汽车，满足了牛叉公司的需求了。但是想想我们的需求，汽车的动作执行顺序是要能够随意调整的，我们只满足了一个需求，
		 * 还要下一个需求呀
		 * ，然后是第2件宝马模型，只要启动、停止，其他的什么都不要，第3件模型，先喇叭，然后启动，然后停止，第4件...直到把你逼疯为止
		 * ，那怎么办？我们就一个一个的来写场景类满足吗？不可能了
		 * ，那我们要想办法来解决这个问题，有了！我们为每种模型产品模型定义一个建造者，你要啥顺序直接告诉建造者
		 * ，由建造者来建造，于是乎我们就有了如图11-2所示的类图。
		 * 
		 * clip_image004
		 * 
		 * 图11-2 增加了建造者的汽车模型类图
		 * 
		 * 增加了一个CarBuilder抽象类，由它来组装各个车模，要什么类型什么顺序的车辆模型，都由相关的子类完成，
		 * 首先编写CarBuilder代码，如代码清单11-5所示。
		 * 
		 * 代码清单11-5 抽象汽车组装者
		 * 
		 * 
		 * public abstract class CarBuilder {
		 * 
		 * //建造一个模型，你要给我一个顺序要，就是组装顺序
		 * 
		 * public abstract void setSequence(ArrayList<String> sequence);
		 * 
		 * //设置完毕顺序后，就可以直接拿到这个车辆模型
		 * 
		 * public abstract CarModel getCarModel();
		 * 
		 * } 很简单，每个车辆模型都要有确定的运行顺序，然后才能返回一个车辆模型。奔驰车的组装者如代码清单11-6所示。
		 * 
		 * 代码清单11-6 奔驰车组装者
		 * 
		 * public class BenzBuilder extends CarBuilder {
		 * 
		 * private BenzModel benz = new BenzModel();
		 * 
		 * public CarModel getCarModel() {
		 * 
		 * return this.benz;
		 * 
		 * }
		 * 
		 * public void setSequence(ArrayList<String> sequence) {
		 * 
		 * this.benz.setSequence(sequence);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 非常简单实用的程序，给定一个汽车的运行顺序，然后就返回一个奔驰车，简单了很多，宝马车的组装与此相同，如代码清单11-7所示。
		 * 
		 * 代码清单11-7 宝马车组装者
		 * 
		 * public class BMWBuilder extends CarBuilder {
		 * 
		 * private BMWModel bmw = new BMWModel();
		 * 
		 * public CarModel getCarModel() {
		 * 
		 * return this.bmw;
		 * 
		 * }
		 * 
		 * public void setSequence(ArrayList<String> sequence) {
		 * 
		 * this.bmw.setSequence(sequence);
		 * 
		 * }
		 * 
		 * } 两个组装者都完成了，我们再来看看牛叉公司的需求如何满足，修改一下场景类，如代码清单11-8所示。
		 * 
		 * 代码清单11-8 修改后的场景类
		 * 
		 * public class Client {
		 * 
		 * public static void main(String[] args) {
		 * 
		 * 
		 * 客户告诉牛叉公司，我要这样一个模型，然后牛叉公司就告诉我老大
		 * 
		 * 说要这样一个模型，这样一个顺序，然后我就来制造
		 * 
		 * 
		 * 
		 * ArrayList<String> sequence = new ArrayList<String>(); //存放run的顺序
		 * 
		 * sequence.add("engine boom"); //客户要求，run的时候时候先发动引擎
		 * 
		 * sequence.add("start"); //启动起来
		 * 
		 * sequence.add("stop"); //开了一段就停下来
		 * 
		 * //要一个奔驰车：
		 * 
		 * BenzBuilder benzBuilder = new BenzBuilder();
		 * 
		 * //把顺序给这个builder类，制造出这样一个车出来
		 * 
		 * benzBuilder.setSequence(sequence);
		 * 
		 * //制造出一个奔驰车
		 * 
		 * BenzModel benz = (BenzModel)benzBuilder.getCarModel();
		 * 
		 * //奔驰车跑一下看看
		 * 
		 * benz.run();
		 * 
		 * }
		 * 
		 * } 运行结果如下所示。
		 * 
		 * 奔驰车的引擎是这个声音的...
		 * 
		 * 奔驰车跑起来是这个样子的...
		 * 
		 * 奔驰车应该这样停车...
		 * 
		 * 那如果我再想要个同样顺序的宝马车呢？很简单，再次修改一下场景类，如代码清单11-9所示。
		 * 
		 * 代码清单11-9 相同顺序的宝马车的场景类
		 * 
		 * 
		 * public class Client {
		 * 
		 * public static void main(String[] args) {
		 * 
		 * ArrayList<String> sequence = new ArrayList<String>(); //存放run的顺序
		 * 
		 * sequence.add("engine boom"); //客户要求，run的时候时候先发动引擎
		 * 
		 * sequence.add("start"); //启动起来
		 * 
		 * sequence.add("stop"); //开了一段就挺下来
		 * 
		 * //要一个奔驰车：
		 * 
		 * BenzBuilder benzBuilder = new BenzBuilder();
		 * 
		 * //把顺序给这个builder类，制造出这样一个车出来
		 * 
		 * benzBuilder.setSequence(sequence);
		 * 
		 * //制造出一个奔驰车
		 * 
		 * BenzModel benz = (BenzModel)benzBuilder.getCarModel();
		 * 
		 * //奔驰车跑一下看看
		 * 
		 * benz.run();
		 * 
		 * //按照同样的顺序，我再要一个宝马
		 * 
		 * BMWBuilder bmwBuilder = new BMWBuilder();
		 * 
		 * bmwBuilder.setSequence(sequence);
		 * 
		 * BMWModel bmw = (BMWModel)bmwBuilder.getCarModel();
		 * 
		 * bmw.run();
		 * 
		 * }
		 * 
		 * } 运行结果如下所示。
		 * 
		 * 奔驰车的引擎是这个声音的...
		 * 
		 * 奔驰车跑起来是这个样子的...
		 * 
		 * 奔驰车应该这样停车...
		 * 
		 * 宝马车的引擎是这个声音的...
		 * 
		 * 宝马车跑起来是这个样子的...
		 * 
		 * 宝马车应该这样停车...
		 * 
		 * 看，同样运行顺序的宝马车也生产出来了，而且代码是不是比刚开始直接访问产品类(Procuct)简单了很多。我们在做项目时，经常会有一个共识：
		 * 需求是无底洞
		 * ，是无理性的，不可能你告诉它不增加需求就不增加，这四个过程（start、stop、alarm、engineBoom）按照排列组合有很多种
		 * ，牛叉公司可以随意组合
		 * ，它要什么顺序的车模我就必须生成什么顺序的车模，客户可是上帝！那我们不可能预知他们要什么顺序的模型呀，怎么办？封装一下
		 * ，找一个导演，指挥各个事件的先后顺序
		 * ，然后为每种顺序指定一个代码，你说一种我们立刻就给你生产处理，好方法，厉害！我们先修正一下类图，如图11-3所示。
		 * 
		 * clip_image006
		 * 
		 * 图11-3 完整汽车模型类图
		 * 
		 * 类图看着复杂了，但是还是比较简单，我们增加了一个Director类，负责按照指定的顺序生产模型，其中方法说明如下：
		 * 
		 * getABenzModel方法
		 * 组建出A型号的奔驰车辆模型，其过程为只有启动（start）、停止(stop)方法，其他的引擎声音、喇叭都没有。
		 * 
		 * getBBenzModel方法 组建出B型号的奔驰车，其过程为先发动引擎（engine
		 * boom）,然后启动(star),再然后停车(stop),没有喇叭。
		 * 
		 * getCBMWModel方法
		 * 组建出C型号的宝马车，其过程为先喇叭叫一下（alarm），然后（start）,再然后是停车(stop)，引擎不轰鸣。
		 * 
		 * getDBMWModel方法
		 * 组建出D型号的宝马车，其过程就一个启动(start)，然后一路跑到黑，永动机，没有停止方法，没有喇叭，没有引擎轰鸣。
		 * 
		 * 其他的E型号、F型号……等等，可以有很多，启动(start)、停止(stop)、喇叭(alarm)、引擎轰鸣(engine
		 * boom)这四个方法在这个类中可以随意的自由组合
		 * ，有几种呢？好像是排列组合，这个不会算，高中数学没学好，反正有很多种了，都可以实现。Director类如代码清单11-10所示。
		 * 
		 * 代码清单11-10 导演类
		 * 
		 * 
		 * public class Director {
		 * 
		 * private ArrayList<String> sequence = new ArrayList();
		 * 
		 * private BenzBuilder benzBuilder = new BenzBuilder();
		 * 
		 * private BMWBuilder bmwBuilder = new BMWBuilder();
		 * 
		 * 
		 * A类型的奔驰车模型，先start,然后stop,其他什么引擎了，喇叭一概没有
		 * 
		 * 
		 * public BenzModel getABenzModel(){
		 * 
		 * //清理场景，这里是一些初级程序员不注意的地方
		 * 
		 * this.sequence.clear();
		 * 
		 * //这只ABenzModel的执行顺序
		 * 
		 * this.sequence.add("start");
		 * 
		 * this.sequence.add("stop");
		 * 
		 * //按照顺序返回一个奔驰车
		 * 
		 * this.benzBuilder.setSequence(this.sequence);
		 * 
		 * return (BenzModel)this.benzBuilder.getCarModel();
		 * 
		 * }
		 * 
		 * 
		 * B型号的奔驰车模型，是先发动引擎，然后启动，然后停止，没有喇叭
		 * 
		 * 
		 * public BenzModel getBBenzModel(){
		 * 
		 * this.sequence.clear();
		 * 
		 * this.sequence.add("engine boom");
		 * 
		 * this.sequence.add("start");
		 * 
		 * this.sequence.add("stop");
		 * 
		 * this.benzBuilder.setSequence(this.sequence);
		 * 
		 * return (BenzModel)this.benzBuilder.getCarModel();
		 * 
		 * }
		 * 
		 * 
		 * C型号的宝马车是先按下喇叭（炫耀嘛），然后启动，然后停止
		 * 
		 * public BMWModel getCBMWModel(){
		 * 
		 * this.sequence.clear();
		 * 
		 * this.sequence.add("alarm");
		 * 
		 * this.sequence.add("start");
		 * 
		 * this.sequence.add("stop");
		 * 
		 * this.bmwBuilder.setSequence(this.sequence);
		 * 
		 * return (BMWModel)this.bmwBuilder.getCarModel();
		 * 
		 * }
		 * 
		 * 
		 * D类型的宝马车只有一个功能，就是跑，启动起来就跑，永远不停止，牛叉
		 * 
		 * 
		 * public BMWModel getDBMWModel(){
		 * 
		 * this.sequence.clear();
		 * 
		 * this.sequence.add("start");
		 * 
		 * this.bmwBuilder.setSequence(this.sequence);
		 * 
		 * return (BMWModel)this.benzBuilder.getCarModel();
		 * 
		 * }
		 * 
		 * 
		 * 这里还可以有很多方法，你可以先停止，然后再启动，或者一直停着不动，静态的嘛
		 * 
		 * 导演类嘛，按照什么顺序是导演说了算
		 * 
		 * 
		 * } 顺便说一下，大家看一下程序中有很多this调用，这个我一般是这样要求项目组成员的，如果你要调用类中的成员变量或方法，
		 * 需要在前面加上this关键字
		 * ，不加也能正常的跑起来，但是不清晰，加上this关键字，我就是要调用本类中成员变量或方法，而不是本方法的中的一个变量
		 * ，还有super方法也是一样
		 * ，是调用父类的的成员变量或者方法，那就加上这个关键字，不要省略，这要靠约束，还有就是程序员的自觉性，他要是死不悔改，那咱也没招。
		 * 
		 * 注意 上面每个方法都一个this.sequence.clear()，这个估计你一看就明白，
		 * 但是作为一个系统分析师或是技术经理一定要告诉告诉项目成员
		 * ，ArrayList和HashMap如果定义成类的成员变量，那你在方法中调用一定要做一个clear的动作
		 * ，防止数据混乱。如果你发生过一次类似问题的话
		 * ，比如ArrayList中出现一个“出乎意料”的数据，而你又花费了几个通宵才解决这个问题，那你会有很深刻的印象。
		 * 
		 * 有了这样一个导演类后，我们的场景类就更容易处理了，牛叉公司要A类型的奔驰车1W辆，B类型的奔驰车100W辆，C类型的宝马车1000W辆，
		 * D类型的不需要，非常容易处理，如代码清单11-11所示。
		 * 
		 * 代码清单11-11 导演类
		 * 
		 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26
		 * 27 28 29 30 31 32 33 public class Client {
		 * 
		 * public static void main(String[] args) {
		 * 
		 * Director director = new Director();
		 * 
		 * //1W辆A类型的奔驰车
		 * 
		 * for(int i=0;i<10000;i++){
		 * 
		 * director.getABenzModel().run();
		 * 
		 * }
		 * 
		 * //100W辆B类型的奔驰车
		 * 
		 * for(int i=0;i<1000000;i++){
		 * 
		 * director.getBBenzModel().run();
		 * 
		 * }
		 * 
		 * //1000W辆C类型的宝马车
		 * 
		 * for(int i=0;i<10000000;i++){
		 * 
		 * director.getCBMWModel().run();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } 清晰，简单吧，我们写程序重构的最终目的就是:简单、清晰，代码是让人看的，不是写完就完事了，我一直在教育我带的团队，
		 * Java程序不是像我们前辈写二进制代码
		 * 、汇编一样，写完基本上就自己能看懂，别人看就跟看天书一样，现在的高级语言，要像写中文汉字一样，你写的，别人能看懂。——这就是建造者模式。
		 * 
		 * 11.2 建造者模式的定义
		 * 
		 * 建造者模式(Builder Pattern)也叫做生成器模式，其定义如下：
		 * 
		 * Separate the construction of a complex object from its representation
		 * so that the same construction process can create different
		 * representations. 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
		 * 
		 * 建造者模式的通用类图如图11-4所示。
		 * 
		 * clip_image008
		 * 
		 * 图11-4 建造者模式通用类图
		 * 
		 * 在建造者模式中，有如下四个角色：
		 * 
		 * Product 产品类 通常是实现了模板方法模式，也就是有模板方法和基本方法，这个参考上一章节的模板方法模式。在例子中，
		 * BenzModel和BMWModel就属于产品类。
		 * 
		 * Builder 抽象建造者 规范产品的组建，一般是由子类实现。在例子中，CarBuilder属于抽象建造者。
		 * 
		 * ConcreteBuilder 具体建造者
		 * 实现抽象类定义的所有方法，并且返回一个组件好的对象。在例子中，BenzBuilder和BMWBuilder就属于具体建造者。
		 * 
		 * Director 导演
		 * 负责安排已有模块的顺序，然后告诉Builder开始建造，在上面的例子中就是我们的老大，牛叉公司找到老大，说我要这个，
		 * 这个，那个类型的车辆模型，然后老大就把命令传递给我，我和我的团队就开始拼命的建造，于是一个项目建设完毕了。
		 * 
		 * 建造者模式的通用源代码也比较简单，先看Product类，通常它是一个组合或继承（如模板方法模式）产生的类，如代码清单11-12所示。
		 * 
		 * 代码清单11-12 产品类
		 * 
		 * 1 2 3 4 5 6 7 8 9 public class Product {
		 * 
		 * public void doSomething(){
		 * 
		 * //独立业务处理
		 * 
		 * }
		 * 
		 * } 抽象建造者如代码清单11-13所示。
		 * 
		 * 代码清单11-13 抽象建造者
		 * 
		 * 1 2 3 4 5 6 7 8 9 10 11 public abstract class Builder {
		 * 
		 * //设置产品的不同部分，以获得不同的产品
		 * 
		 * public abstract void setPart();
		 * 
		 * //建造产品
		 * 
		 * public abstract Product buildProduct();
		 * 
		 * } 其中，setPart方法是零件的配置，什么是零件？其他的对象，获得一个不同零件，或者不同的装配顺序就可能产生不同的产品。
		 * 具体的建造者如代码清单11-14所示。
		 * 
		 * 代码清单11-14 具体建造者
		 * 
		 * public class ConcreteProduct extends Builder {
		 * 
		 * private Product product = new Product();
		 * 
		 * //设置产品零件
		 * 
		 * public void setPart(){
		 * 
		 * 产品类内的逻辑处理 }
		 * 
		 * //组建一个产品
		 * 
		 * public Product buildProduct() {
		 * 
		 * return product;
		 * 
		 * }
		 * 
		 * } 需要注意的是，如果有多个产品类就有几个具体的建造者，而且这多个产品类具有相同接口或抽象类，参考我们上面的例子。
		 * 
		 * 导演类如代码清单11-15所示。
		 * 
		 * 代码清单11-15 导演类 public class Director {
		 * 
		 * private Builder builder = new ConcreteProduct();
		 * 
		 * //构建不同的产品
		 * 
		 * public Product getAProduct(){
		 * 
		 * builder.setPart();
		 * 
		 * 设置不同的零件，产生不同的产品
		 * 
		 * return builder.buildProduct();
		 * 
		 * }
		 * 
		 * } 导演类就是起到封装的作用，避免高层模块深入到建造者内部的实现类。当然，在建造者模式比较庞大时，导演类可以有多个。
		 * 
		 * 11.3 建造者模式的应用
		 * 
		 * 1. 建造者模式的优点
		 * 
		 * 封装性 使用建造者模式可以使客户端不必知道产品内部组成的细节，如例子中我们就不需要关心每一个具体的模型内部是如何实现的，
		 * 产生的对象类型就是CarModel。
		 * 
		 * 建造者独立，容易扩展 BenzBuilder和BMWBuilder是相互独立的，对系统的扩展非常有利。
		 * 
		 * 便于控制细节风险 由于具体的建造者是独立的，因此可以对建造过程逐步细化，而不对其他的模块产生任何影响。
		 * 
		 * 2. 建造者模式的使用场景
		 * 
		 * 相同的方法，不同的执行顺序，产生不同的事件结果时，可以采用建造者模式。
		 * 多个部件或零件,都可以装配到一个对象中，但是产生的运行结果又不相同时，则可以使用该模式。
		 * 产品类非常复杂，或者产品类中的调用顺序不同产生了不同的效能，这个时候使用建造者模式是非常合适。
		 * 在对象创建过程中会使用到系统中的一些其它对象
		 * ，这些对象在产品对象的创建过程中不易得到时，也可以采用建造者模式封装该对象的创建过程。该种场景，
		 * 只能是一个补偿方法，因为一个对象不容易获得，而在设计阶段竟然没有发觉，而要通过创建者模式柔化创建过程，本身已经违反设计最初目标。 3.
		 * 建造者模式的注意事项
		 * 
		 * 建造者模式关注的是的零件类型和装配工艺（顺序），这是它与工厂方法模式最大不同的地方，虽然同为创建类模式，但是注重点不同。
		 * 
		 * 11.4 建造者模式的扩展
		 * 
		 * 已经不用扩展了，因为我们在汽车模型制造的例子中已经对建造者模式进行了扩展，引入了模板方法模式，可能大家会比较疑惑，
		 * 为什么在其他介绍设计模式的书籍上创建者模式并不是这样说的
		 * ，读者请注意，建造者模式中还有一个角色没有说明，就是零件，建造者怎么去建造一个对象？是零件的组装
		 * ，组装顺序不同对象效能也不同，这才是建造者模式要表达的核心意义
		 * ，而怎么才能更好的达到这种效果呢？引入模板方法模式是一个非常简单而有效的办法。
		 * 
		 * 大家看到这里估计就开始犯嘀咕了，这个建造者模式和工厂模式非常相似呀，Yes，是的，是非常相似，但是记住一点你就可以游刃有余的使用了：
		 * 建造者模式最主要功能是基本方法的调用顺序安排
		 * ，也就是这些基本方法已经实现了，通俗的说就是零件的装配，顺序不同产生的对象也不同；而工厂方法则重点是创建
		 * ，创建零件时它的主要职责，你要什么对象我创造一个对象出来，组装顺序则不是他关心的。
		 * 
		 * 11.5 最佳实践
		 * 
		 * 再次说明，在使用建造者模式的时候考虑一下模板方法模式，别孤立的思考一个模式，僵化的套用一个模式会让受害无穷！
		 * 
		 * 如果你已经看懂本章节举的例子，并认可这种建造者模式，那你就放心使用，比单独使用某些书上的纯建造者是高效、简洁得多。
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
	}

	@Test
	public void testtest() {
		/*
		 * 
		 * 
		 * 
		 * 
		 * 避免在 where 子句中使用!=或<>操作符，否则将引擎放弃使用索引而进行全表扫描。
　　3.应尽量避免在 where 子句中对字段进行 null 值判断，否则将导致引擎放弃使用索引而进行全表扫描，如：
　　select id from t where num is null
　　可以在num上设置默认值0，确保表中num列没有null值，然后这样查询：
　　select id from t where num=0
　　4.应尽量避免在 where 子句中使用 or 来连接条件，否则将导致引擎放弃使用索引而进行全表扫描，如：
　　select id from t where num=10 or num=20
　　可以这样查询：
　　select id from t where num=10
　　union all
　　select id from t where num=20
　　5.下面的查询也将导致全表扫描：
　　select id from t where name like '%abc%'
　　若要提高效率，可以考虑全文检索。
　　6.in 和 not in 也要慎用，否则会导致全表扫描，如：
　　select id from t where num in(1,2,3)
　　对于连续的数值，能用 between 就不要用 in 了：
　　select id from t where num between 1 and 3
　　7.如果在 where 子句中使用参数，也会导致全表扫描。因为SQL只有在运行时才会解析局部变量，但优化程序不能将访问计划的选择推迟到运行时;它必须在编译时进行选择。然而，如果在编译时建立访问计划，变量的值还是未知的，因而无法作为索引选择的输入项。如下面语句将进行全表扫描：
　　select id from t where num=@num
　　可以改为强制查询使用索引：
　　select id from t with(index(索引名)) where num=@num
　　8.应尽量避免在 where 子句中对字段进行表达式操作，这将导致引擎放弃使用索引而进行全表扫描。如：
　　select id from t where num/2=100
　　应改为:
　　select id from t where num=100*2
　　9.应尽量避免在where子句中对字段进行函数操作，这将导致引擎放弃使用索引而进行全表扫描。如：
　　select id from t where substring(name,1,3)='abc'--name以abc开头的id
　　select id from t where datediff(day,createdate,'2005-11-30')=0--'2005-11-30'生成的id
　　应改为:
　　select id from t where name like 'abc%'
　　select id from t where createdate>='2005-11-30' and createdate<'2005-12-1'
　　10.不要在 where 子句中的“=”左边进行函数、算术运算或其他表达式运算，否则系统将可能无法正确使用索引。
　　11.在使用索引字段作为条件时，如果该索引是复合索引，那么必须使用到该索引中的第一个字段作为条件时才能保证系统使用该索引，否则该索引将不会被使用，并且应尽可能的让字段顺序与索引顺序相一致。
　　12.不要写一些没有意义的查询，如需要生成一个空表结构：
　　select col1,col2 into #t from t where 1=0
　　这类代码不会返回任何结果集，但是会消耗系统资源的，应改成这样：
　　create table #t(...)
 
　　13.很多时候用 exists 代替 in 是一个好的选择：
　　select num from a where num in(select num from b)
　　用下面的语句替换：
　　select num from a where exists(select 1 from b where num=a.num)
　　14.并不是所有索引对查询都有效，SQL是根据表中数据来进行查询优化的，当索引列有大量数据重复时，SQL查询可能不会去利用索引，如一表中有字段sex，male、female几乎各一半，那么即使在sex上建了索引也对查询效率起不了作用。
　　15.索引并不是越多越好，索引固然可以提高相应的 select 的效率，但同时也降低了 insert 及 update 的效率，因为 insert 或 update 时有可能会重建索引，所以怎样建索引需要慎重考虑，视具体情况而定。一个表的索引数最好不要超过6个，若太多则应考虑一些不常使用到的列上建的索引是否有必要。
　　16.应尽可能的避免更新 clustered 索引数据列，因为 clustered 索引数据列的顺序就是表记录的物理存储顺序，一旦该列值改变将导致整个表记录的顺序的调整，会耗费相当大的资源。若应用系统需要频繁更新 clustered 索引数据列，那么需要考虑是否应将该索引建为 clustered 索引。
　　17.尽量使用数字型字段，若只含数值信息的字段尽量不要设计为字符型，这会降低查询和连接的性能，并会增加存储开销。这是因为引擎在处理查询和连接时会逐个比较字符串中每一个字符，而对于数字型而言只需要比较一次就够了。
　　18.尽可能的使用 varchar/nvarchar 代替 char/nchar ，因为首先变长字段存储空间小，可以节省存储空间，其次对于查询来说，在一个相对较小的字段内搜索效率显然要高些。
　　19.任何地方都不要使用 select * from t ，用具体的字段列表代替“*”，不要返回用不到的任何字段。
　　20.尽量使用表变量来代替临时表。如果表变量包含大量数据，请注意索引非常有限(只有主键索引)。
　　21.避免频繁创建和删除临时表，以减少系统表资源的消耗。
　　22.临时表并不是不可使用，适当地使用它们可以使某些例程更有效，例如，当需要重复引用大型表或常用表中的某个数据集时。但是，对于一次性事件，最好使用导出表。
　　23.在新建临时表时，如果一次性插入数据量很大，那么可以使用 select into 代替 create table，避免造成大量 log ，以提高速度;如果数据量不大，为了缓和系统表的资源，应先create table，然后insert。
　　24.如果使用到了临时表，在存储过程的最后务必将所有的临时表显式删除，先 truncate table ，然后 drop table ，这样可以避免系统表的较长时间锁定。
　　25.尽量避免使用游标，因为游标的效率较差，如果游标操作的数据超过1万行，那么就应该考虑改写。
　　26.使用基于游标的方法或临时表方法之前，应先寻找基于集的解决方案来解决问题，基于集的方法通常更有效。
　　27.与临时表一样，游标并不是不可使用。对小型数据集使用 FAST_FORWARD 游标通常要优于其他逐行处理方法，尤其是在必须引用几个表才能获得所需的数据时。在结果集中包括“合计”的例程通常要比使用游标执行的速度快。如果开发时间允许，基于游标的方法和基于集的方法都可以尝试一下，看哪一种方法的效果更好。
　　28.在所有的存储过程和触发器的开始处设置 SET NOCOUNT ON ，在结束时设置 SET NOCOUNT OFF 。无需在执行存储过程和触发器的每个语句后向客户端发送 DONE_IN_PROC 消息。
　　29.尽量避免向客户端返回大数据量，若数据量过大，应该考虑相应需求是否合理。
　　30.尽量避免大事务操作，提高系统并发能力。

上面有几句写的有问题。

第二方面：
select Count (*)和Select Count(1)以及Select Count(column)区别
一般情况下，Select Count (*)和Select Count(1)两着返回结果是一样的
    假如表沒有主键(Primary key), 那么count(1)比count(*)快，
    如果有主键的話，那主键作为count的条件时候count(主键)最快
    如果你的表只有一个字段的话那count(*)就是最快的
   count(*) 跟 count(1) 的结果一样，都包括对NULL的统计，而count(column) 是不包括NULL的统计

第三方面：
索引列上计算引起的索引失效及优化措施以及注意事项

创建索引、优化查询以便达到更好的查询优化效果。但实际上，MySQL有时并不按我们设计的那样执行查询。MySQL是根据统计信息来生成执行计划的，这就涉及索引及索引的刷选率，表数据量，还有一些额外的因素。
Each table index is queried, and the best index is used unless the optimizer believes that it is more efficient to use a table scan. At one time, a scan was used based on whether the best index spanned more than 30% of the table, but a fixed percentage no longer determines the choice between using an index or a scan. The optimizer now is more complex and bases its estimate on additional factors such as table size, number of rows, and I/O block size.
简而言之，当MYSQL认为符合条件的记录在30%以上，它就不会再使用索引，因为mysql认为走索引的代价比不用索引代价大，所以优化器选择了自己认为代价最小的方式。事实也的确如此

是MYSQL认为记录是30%以上，而不是实际MYSQL去查完
*
*/
		
		
		
		
		
		/**
		 * Spring框架IOC容器和AOP解析

主要分析点：

一、Spring开源框架的简介 

二、Spring下IOC容器和DI(依赖注入Dependency injection)

三、Spring下面向切面编程(AOP)和事务管理配置 

 

一、Spring开源框架的简介 

　　Spring是一个开源框架，Spring是于2003 年兴起的一个轻量级的Java 开发框架，由Rod Johnson 在其著作Expert One-On-One J2EE Development and Design中阐述的部分理念和原型衍生而来。它是为了解决企业应用开发的复杂性而创建的。Spring使用基本的JavaBean来完成以前只可能由EJB完成的事情。然而，Spring的用途不仅限于服务器端的开发。从简单性、可测试性和松耦合的角度而言，任何Java应用都可以从Spring中受益。 简单来说，Spring是一个轻量级的控制反转（IoC）和面向切面（AOP）的容器框架。

　　spring的基本框架主要包含六大模块：DAO、ORM、AOP、JEE、WEB、CORE



Spring DAO：Spring提供了对JDBC的操作支持：JdbcTemplate模板工具类 。

Spring ORM：Spring可以与ORM框架整合。例如Spring整合Hibernate框架，其中Spring还提供HibernateDaoSupport工具类，简化了Hibernate的操作 。

Spring WEB：Spring提供了对Struts、Springmvc的支持，支持WEB开发。与此同时Spring自身也提供了基于MVC的解决方案 。

Spring  AOP：Spring提供面向切面的编程，可以给某一层提供事务管理，例如在Service层添加事物控制 。

Spring   JEE：J2EE开发规范的支持，例如EJB 。

Spring Core：提供IOC容器对象的创建和处理依赖对象关系 。

 

二、Spring下IOC容器和DI(依赖注入Dependency injection)

　　IOC容器：就是具有依赖注入功能的容器，是可以创建对象的容器，IOC容器负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。通常new一个实例，控制权由程序员控制，而"控制反转"是指new实例工作不由程序员来做而是交给Spring容器来做。。在Spring中BeanFactory是IOC容器的实际代表者。

　　DI(依赖注入Dependency injection) ：在容器创建对象后，处理对象的依赖关系。

　　依赖注入spring的注入方式：

set注入方式
静态工厂注入方式
构造方法注入方式
基于注解的方式
1、set注入方式：

控制层代码：

private OrderServiceImp orderService;
    
public void setOrderService(OrderServiceImp orderService) {
       this.orderService = orderService;
}
Spring配置XML文件：其中配置声明OrderAction类存在属性orderService。程式运行时候，会将已经实例化的orderService对象调用setOrderService方式注入。

<bean name="orderAction" class="com.pec.do.OrderAction">
        <property name="orderService" ref="orderService"></property>
</bean>
<bean name="orderService" class="com.pec.service.imp.OrderServiceImp"></bean>
 

2、构造器注入方式：

控制层代码：

private OrderServiceImp orderService;
    
public OrderAction(OrderServiceImp orderService) {
        this.orderService = orderService;
    }
Spring配置XML文件：

<bean name="orderAction" class="com.pec.do.OrderAction">
      <constructor-arg ref="orderService"></constructor-arg>
</bean>
<bean name="orderService" class="com.pec.service.imp.OrderServiceImp"></bean>
 

3、基于注解的方式（推荐使用，比较便捷少配置）

控制层代码：

@Autowired   //@Resource
private OrderServiceImp orderService;
 服务层代码：

@Service("orderService")
public class OrderServiceImp implements IOrderService {

    @Autowired
    private JavaOrderMDaoImp javaOrderMDao;

    @Autowired
    private JavaOrderDDaoImp javaOrderDDao;

    @Override
    public List<JavaOrderMList> findOrderM(OrderSearch search) {
        return javaOrderMDao.findJavaOrderM(search);
    }

    @Override
    public List<JavaOrderDList> findOrderD(OrderSearch search) {
        return javaOrderDDao.findJavaOrderD(search);
    }

}

 DAO层代码：

@Repository("javaOrderMDao")
public class JavaOrderMDaoImp extends BaseHibernateDAO<JavaOrderM, Serializable> implements IJavaOrderMDao {...}
@Repository("javaOrderDDao")
public class JavaOrderDDaoImp extendsBaseHibernateDAO<JavaOrderD, Serializable> implements IJavaOrderDDao {...}
 注意点：

　　⑴ 持久层DAO层注解Repository中规定了名称，在Service层中声明名称必须一致。

　　⑵ 服务层Service层注解Service中规定了名称，在控制层中声明的名称必须一致。

　　⑶ 注解方式注入依赖注解：

复制代码
@Component         把对象加入ioc容器，对象引用名称是类名，第一个字母小写
@Component(“name”) 把指定名称的对象，加入ioc容器
@Repository        主要用于标识加入容器的对象是一个持久层的组件(类)
@Service           主要用于标识加入容器的对象是一个业务逻辑层的组件
@Controller        主要用于标识加入容器的对象是一个控制层的组件
@Resource          注入属性(DI), 会从容器中找对象注入到@Resource修饰的对象上
@Autowired         注入属性(DI), 会从容器中找对象注入到@Autowired修饰的对象上
复制代码
　　 ⑷ 注解可以简化配置，提升开发效率，但是也不利于后期维护。

 注：@Autowired与@Resource的区别

 

三、Spring下面向切面编程(AOP)和事务管理配置 

 　　AOP就是纵向的编程，如业务1和业务2都需要一个共同的操作，与其往每个业务中都添加同样的代码，不如写一遍代码，让两个业务共同使用这段代码。在日常有订单管理、商品管理、资金管理、库存管理等业务，都会需要到类似日志记录、事务控制、权限控制、性能统计、异常处理及事务处理等。AOP把所有共有代码全部抽取出来，放置到某个地方集中管理，然后在具体运行时，再由容器动态织入这些共有代码。



 

 AOP涉及名称：

切面（Aspect）：其实就是共有功能的实现。如日志切面、权限切面、事务切面等。在实际应用中通常是一个存放共有功能实现的普通Java类，之所以能被AOP容器识别成切面，是在配置中指定的。

通知（Advice）：是切面的具体实现。以目标方法为参照点，根据放置的地方不同，可分为前置通知（Before）、后置通知（AfterReturning）、异常通知（AfterThrowing）、最终通知（After）与环绕通知（Around）5种。在实际应用中通常是切面类中的一个方法，具体属于哪类通知，同样是在配置中指定的。

连接点（Joinpoint）：就是程序在运行过程中能够插入切面的地点。例如，方法调用、异常抛出或字段修改等，但Spring只支持方法级的连接点。

切入点（Pointcut）：用于定义通知应该切入到哪些连接点上。不同的通知通常需要切入到不同的连接点上，这种精准的匹配是由切入点的正则表达式来定义的。

目标对象（Target）：就是那些即将切入切面的对象，也就是那些被通知的对象。这些对象中已经只剩下干干净净的核心业务逻辑代码了，所有的共有功能代码等待AOP容器的切入。

代理对象（Proxy）：将通知应用到目标对象之后被动态创建的对象。可以简单地理解为，代理对象的功能等于目标对象的核心业务逻辑功能加上共有功能。代理对象对于使用者而言是透明的，是程序运行过程中的产物。

织入（Weaving）：将切面应用到目标对象从而创建一个新的代理对象的过程。这个过程可以发生在编译期、类装载期及运行期，当然不同的发生点有着不同的前提条件。譬如发生在编译期的话，就要求有一个支持这种AOP实现的特殊编译器；发生在类装载期，就要求有一个支持AOP实现的特殊类装载器；只有发生在运行期，则可直接通过Java语言的反射机制与动态代理机制来动态实现。

　

　　Spring使用AOP配置事务管理由三个部分组成，分别是DataSource、TransactionManager和代理机制这三部分，无论哪种配置方式，一般变化的只是代理机制这部分。DataSource、TransactionManager这两部分只是会根据数据访问方式有所变化，比如使用hibernate进行数据访问时，DataSource实际为SessionFactory，TransactionManager的实现为HibernateTransactionManager。

 
spring事务配置的五种方式：每个Bean都有一个代理、所有Bean共享一个代理基类、使用拦截器、使用tx标签配置的拦截器、全注解

1、使用tx标签配置的拦截器

<!--4、配置hibernate属性 -->
    <!--引入db.properties属性文件 -->
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <property name="location" value="classpath:db.properties"></property>
    </bean>
    <!-- 配置数据源，连接池使用c3p0,详细信息参见hibernate官方文档"基础配置章节" -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
        destroy-method="close" dependency-check="none">
        <property name="driverClass">
            <value>${datasource.driverClassName}</value>
        </property>
        <property name="jdbcUrl">
            <value>${datasource.url}</value>
        </property>
        <property name="user">
            <value>${datasource.username}</value>
        </property>
        <property name="password">
            <value>${datasource.password}</value>
        </property>
        <property name="acquireIncrement">
            <!--当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3 -->
            <value>${c3p0.acquireIncrement}</value>
        </property>
        <property name="initialPoolSize">
            <!--初始化时获取的连接数，取值应在minPoolSize与maxPoolSize之间。Default: 3 -->
            <value>${c3p0.initialPoolSize}</value>
        </property>
        <property name="minPoolSize">
            <!--连接池中保留的最小连接数。 -->
            <value>${c3p0.minPoolSize}</value>
        </property>
        <property name="maxPoolSize">
            <!--连接池中保留的最大连接数。Default: 15 -->
            <value>${c3p0.maxPoolSize}</value>
        </property>
        <property name="maxIdleTime">
            <!--最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0 -->
            <value>${c3p0.maxIdleTime}</value>
        </property>
        <property name="idleConnectionTestPeriod">
            <!--每60秒检查所有连接池中的空闲连接。Default: 0 -->
            <value>${c3p0.idleConnectionTestPeriod}</value>
        </property>
        <property name="maxStatements">
            <!-- JDBC的标准参数，用以控制数据源内加载的PreparedStatements数量。但由于预缓存的statements 属于单个connection而不是整个连接池。所以设置这个参数需要考虑到多方面的因素。 
                如果maxStatements与maxStatementsPerConnection均为0，则缓存被关闭。Default: 0 -->
            <value>${c3p0.maxStatements}</value>
        </property>
        <property name="numHelperThreads">
            <!-- C3P0是异步操作的，缓慢的JDBC操作通过帮助进程完成。扩展这些操作可以有效的提升性能， 通过多线程实现多个操作同时被执行。Default: 
                3 -->
            <value>${c3p0.numHelperThreads}</value>
        </property>
    </bean>

    <!--配置 sessionFactory -->
    <bean id="sessionFactory"
        class="org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean">
        <property name="dataSource" ref="dataSource">
        </property>
        <!-- hibernate的设置 -->
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.dialect">${hibernate.dialect}</prop>
                <prop key="hibernate.show_sql"> ${hibernate.show_sql} </prop>
                <prop key="hibernate.jdbc.fetch_size">${hibernate.jdbc.fetch_size}</prop>
                <prop key="hibernate.jdbc.batch_size">${hibernate.jdbc.batch_size}</prop>
                <prop key="hibernate.connection.release_mode">${hibernate.connection.release_mode}</prop>
                <prop key="hibernate.format_sql">${hibernate.format_sql}</prop>
                <prop key="hibernate.connection.SetBigStringTryClob">true</prop>
            </props>
        </property>
        <!-- anotation注解扫描实体类 -->
        <property name="packagesToScan">
            <list>
                <value>com.pec.model</value>
            </list>
        </property>
    </bean>

    <!--5、Spring 配置声明式事物 -->
    
    <!-- 配置事务 -->
    <bean id="transactionManager"
        class="org.springframework.orm.hibernate3.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory"></property>
    </bean>
    
    <!-- 配置事务范围 -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="get*" read-only="false" propagation="NOT_SUPPORTED" />
            <tx:method name="find*" read-only="false" propagation="NOT_SUPPORTED" />
            <tx:method name="save*" propagation="REQUIRED" />
            <tx:method name="update*" propagation="REQUIRED" />
            <tx:method name="delete*" propagation="REQUIRED" />
            <tx:method name="create*" propagation="REQUIRED" />
            <tx:method name="anscy*" propagation="REQUIRED" />
        </tx:attributes>
    </tx:advice>
    
    <!-- 定义切面 -->
    <aop:config proxy-target-class="true">
        <aop:pointcut id="pointcut" expression="execution(* com.pec.service..*.*(..))"  />
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut" />
    </aop:config>
复制代码
 

 有几点需要说明：

⑴ pointcut中的三个"*"中，第一个*代表返回值，第二*代表service下子包，第三个*代表方法名，“（..）”代表方法参数。

⑵ 此时配置的切点在Service层，方法命名需要按照以上advice通知点开头命名。

⑶ 按照规定命名方法，会受到Spring事务的管控，保持操作的一致性。例如向数据库插入100条数据，前面99条记录都正常执行直至第100条出现错误，则事务管控会回滚到执行前的初始状态。

 

2、使用Bean代理

复制代码
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
      http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
      http://www.springframework.org/schema/context
      http://www.springframework.org/schema/context/spring-context.xsd
      http://www.springframework.org/schema/tx 
      http://www.springframework.org/schema/tx/spring-tx-2.5.xsd
      http://www.springframework.org/schema/aop 
      http://www.springframework.org/schema/aop/spring-aop-2.5.xsd"
      default-autowire="default">
    
    <!-- <bean name="userManager" class="com.tgb.manager.UserManagerImpl"></bean>
    <bean name="userController" class="com.tgb.web.UserController">
        <property name="userManager" ref="userManager"></property>
    </bean> -->
    <context:component-scan base-package="com.tgb.dao" />
    <context:component-scan base-package="com.tgb.entity" />
    <context:component-scan base-package="com.tgb.manager" />
    <context:component-scan base-package="com.tgb.web" />
    
    <!-- 引入init.properties中属性 -->
    <bean id="placeholderConfig" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
        <list>
            <value>classpath:config/spring/jdbc.properties</value>
        </list>
        </property>
    </bean>
    
    <!-- 配置数据源，连接池使用c3p0,详细信息参见hibernate官方文档"基础配置章节" -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource" >
        <property name="driverClass" value="${datasource.driverClassName}"></property>
        <property name="jdbcUrl" value="${datasource.url}"></property>
        <property name="user" value="${datasource.username}"></property>
        <property name="password" value="${datasource.password}"></property>
    </bean>
    
    <!-- 配置SessionFactory -->
    <bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <property name="hibernateProperties">
            <props>
                <prop key="hibernate.dialect">
                    ${hibernate.dialect}
                </prop>
                <prop key="hibernate.show_sql"> 
                    ${hibernate.show_sql} 
                </prop>
                <prop key="hibernate.jdbc.fetch_size">
                    ${hibernate.jdbc.fetch_size}
                </prop>
                <prop key="hibernate.jdbc.batch_size">
                    ${hibernate.jdbc.batch_size}
                </prop>
                <prop key="hibernate.connection.release_mode">
                    ${hibernate.connection.release_mode}
                </prop>
                <prop key="hibernate.format_sql">
                    ${hibernate.format_sql}
                </prop>
                <prop key="hibernate.connection.SetBigStringTryClob">true</prop>
            </props>
        </property>
        <!-- anotation注解扫描实体类  -->
        <property name="annotatedClasses">
            <list>
                <value>com.tgb.entity.User</value>
            </list>
        </property>
    </bean>

    <!-- 配置一个事务管理器 将事务与Hibernate关联-->
    <bean id="transactionManager" class="org.springframework.orm.hibernate4.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory"/>
    </bean>
    
    <!-- 配置事务范围，使用代理的方式 -->
    <bean id="transactionProxy" class="org.springframework.transaction.interceptor.TransactionProxyFactoryBean" abstract="true">  
        <!--   为事务代理bean注入事务管理器--> 
        <property name="transactionManager" >
            <ref bean="transactionManager"/>
        </property>  
         <!--设置事务属性范围-->  
        <property name="transactionAttributes">  
            <props>  
                <prop key="add*">PROPAGATION_REQUIRED,-Exception</prop>  
                <prop key="get">PROPAGATION_REQUIRED,-Exception</prop> 
                <prop key="update*">PROPAGATION_REQUIRED,-myException</prop>  
                <prop key="del*">PROPAGATION_REQUIRED</prop>  
                <prop key="*">PROPAGATION_REQUIRED</prop>      
            </props>  
        </property>  
    </bean> 
    
    <bean id="userDao" class="com.tgb.dao.UserDaoImpl">
        <property name="sessionFactory" ref="sessionFactory"></property>
    </bean>

    <bean id="userManagerBase" class="com.tgb.manager.UserManagerImpl">
        <property name="userDao" ref="userDao"></property>
    </bean>
    
    <!-- 此处为代理 -->
    <bean name="userManager" parent="transactionProxy">
        <property name="target" ref="userManagerBase"></property>
    </bean>    
     
</beans>
		 */
	}

}
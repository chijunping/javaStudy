package com.utils.Arrays;
/*
 * 
 *   sort(对数组排序)
 binarySearch（二分法查找数组中的元素）
 equals（比较两个数组是否相等）
 fill（对数组中的指定位置填充相同的内容）
 copyOf（数组拷贝）
 asList（将数组转换为一个固定的List对象）
 hashCode（计算数组的哈希值）
 toString（以特定格式输出数组）
 * 
 */
public class ArraysUtil {

	/*
	 * Java.util.Arrays类能方便地操作数组，它提供的方法都是静态的。整个Arrays工具类的实现有3000+行，但是归纳总结一下可知它有以下功能
	 * （9个）：
	 * 
	 * 1. asList
	 * 
	 * 定义：
	 * 
	 * @SafeVarargs public static <T> List<T> asList(T... a) { return new
	 * ArrayList<>(a); } 1 2 3 4 1 2 3 4
	 * 功能：将一个数组(变长参数的语法糖实现就是数组)转变成一个List(确切的来说是ArrayList
	 * )，注意这个List是定长的，企图添加或者删除数据都会报错（java.lang.UnsupportedOperationException）.
	 * 譬如案例1-1：
	 * 
	 * List<Integer> list = Arrays.asList(3,4,2,1,5,7,6);
	 * System.out.println(list); 1 2 1 2 输出结果：[3, 4, 2, 1, 5, 7, 6]
	 * 但是，对于基础类型（比如byte,int,float等）千万不要想着这么实现（案例1-2，勿效仿）：
	 * 
	 * int a[] = new int[]{1,2,5,4,6,8,7,9}; List list = Arrays.asList(a); 1 2 1
	 * 2 因为List list = Arrays.asList(a);会变成List
	 * 
	 * for(int[] arr:list) { for(int i:arr) { System.out.println(i); } } 1 2 3 4
	 * 5 6 7 1 2 3 4 5 6 7
	 * 这样操作就显得非常的烦琐。因为预想List是List<Integer>形式的，没想到是List<int[]>形式的。使用的时候要特别的注意一下。
	 * 
	 * 2. sort
	 * 
	 * 对数组进行排序。适合byte,char,double,float,int,long,short等基本类型，还有Object类型（
	 * 实现了Comparable接口），如果提供了比较器Comparator也可以适用于泛型。 案例2-1（基础类型，输出：[1, 1, 4, 4,
	 * 5, 6, 7, 9]）：
	 * 
	 * int a[] = new int[]{1,9,5,4,6,4,7,1}; Arrays.sort(a);
	 * System.out.println(Arrays.toString(a)); 1 2 3 1 2 3
	 * 案例2-2（String类型(Object)，实现了Comparable接口，输出：[s1, s2, s3, s4]）：
	 * 
	 * String str[] = {"s2","s4","s1","s3"}; Arrays.sort(str);
	 * System.out.println(Arrays.toString(str)); 1 2 3 1 2 3 案例2-3
	 * （自定义类型，实现了Comparable接口，输出：[jj:17, zzh:18, qq:19]）：
	 * 
	 * Person1 persons[] = new Person1[]{ new Person1("zzh",18),new
	 * Person1("jj",17),new Person1("qq",19) }; Arrays.sort(persons);
	 * System.out.println(Arrays.toString(persons)); 1 2 3 4 5 1 2 3 4 5
	 * 案例2-4（泛型，如果类型没有实现Comparable接口，可以通过Comparator实现排序）：
	 * 
	 * Person2 persons2[] = new Person2[]{ new Person2("zzh",18),new
	 * Person2("jj",17),new Person2("qq",19) }; Arrays.sort(persons2,new
	 * Comparator<Person2>(){
	 * 
	 * @Override public int compare(Person2 o1, Person2 o2) { if(o1 == null ||
	 * o2 == null) return 0; return o1.getAge()-o2.getAge(); }
	 * 
	 * }); System.out.println(Arrays.toString(persons2)); 1 2 3 4 5 6 7 8 9 10
	 * 11 12 13 14 15 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 输出：[jj:17, zzh:18,
	 * qq:19] 关于类Person1和类Person2的详细细节可以参考《Comparable与Comparator浅析》
	 * 
	 * 3. binarySearch
	 * 
	 * 通过二分查找法对已排序（譬如经过Arrays.sort排序，且按照升序进行排序。如果数组没有经过排序，那么检索结果未知）的数组进行查找。适合byte
	 * ,char,double,float,int,long,short等基本类型，还有Object类型和泛型（参考sort那段） 案例3-1：
	 * 
	 * String str[] = {"s2","s4","s1","s3"}; Arrays.sort(str);
	 * System.out.println(Arrays.toString(str)); int ans =
	 * Arrays.binarySearch(str, "s1"); System.out.println(ans); 1 2 3 4 5 6 1 2
	 * 3 4 5 6 输出：
	 * 
	 * [s1, s2, s3, s4] 0 1 2 1 2 4. copyOf
	 * 
	 * 数组拷贝，底层采用System.arrayCopy（native方法）实现。 案例4-1：
	 * 
	 * String str[] = {"s2","s4","s1","s3"}; String str2[] = Arrays.copyOf(str,
	 * str.length); System.out.println(Arrays.toString(str2)); 1 2 3 1 2 3
	 * 输出：[s2, s4, s1, s3]
	 * 
	 * 5. copyOfRange
	 * 
	 * 数组拷贝，指定一定的范围，譬如（public static T[] copyOfRange(T[] original, int from, int
	 * to)）。底层采用System.arrayCopy（native方法）实现。 案例5-1：
	 * 
	 * String str[] = {"s2","s4","s1","s3"}; String str2[] =
	 * Arrays.copyOfRange(str,1,3); System.out.println(Arrays.toString(str2)); 1
	 * 2 3 1 2 3 输出：[s4, s1]
	 * 
	 * 6. equals和deepEquals
	 * 
	 * equals：判断两个数组的每一个对应的元素是否相等（equals, 对于两个数组的元素o1和o2有o1==null ? o2==null :
	 * o1.equals(o2)）。 案例6-1：
	 * 
	 * String str1[] = {"s2","s4","s1","s3",null}; String str2[] =
	 * Arrays.copyOf(str1, str1.length); System.out.println(Arrays.equals(str1,
	 * str2)); 1 2 3 4 1 2 3 4 输出：true
	 * deepEquals：主要针对一个数组中的元素还是数组的情况，类似deepToString, deepHashCode如下： 案例6-1：
	 * 
	 * int a1[] = new int[]{1,2,3}; int a2[] = new int[]{1,3,3}; int a3[] = new
	 * int[]{4,3,2,1}; int a4[] = new int[]{1,2,3}; int a5[] = new int[]{1,3,3};
	 * int a6[] = new int[]{4,3,2,1}; int[] a [] = new int[][]{a1,a2,a3}; int[]
	 * b [] = new int[][]{a4,a5,a6};
	 * 
	 * System.out.println(Arrays.equals(a, b));
	 * System.out.println(Arrays.deepEquals(a, b)); 1 2 3 4 5 6 7 8 9 10 11 1 2
	 * 3 4 5 6 7 8 9 10 11 输出结果：
	 * 
	 * false true 1 2 1 2 7. fill
	 * 
	 * 给数组赋值。填充数组之用。 案例7-1：
	 * 
	 * String str[] = {"s2","s4","s1","s3",null};
	 * System.out.println(Arrays.toString(str)); Arrays.fill(str, "s5");
	 * System.out.println(Arrays.toString(str)); 1 2 3 4 1 2 3 4 输出：
	 * 
	 * [s2, s4, s1, s3, null] [s5, s5, s5, s5, s5] 1 2 1 2 8.
	 * toString和deepToString
	 * 
	 * toString：对于一个数组int a[] = new
	 * int[]{1,9,5,4,6,4,7,1};如果按照System.out.println
	 * (a);打印企图可以打印出[1,9,5,4,6,4,7,1
	 * ]，实际上只会打印出[I@3e2de41d这种。在打印数组的时候需要写成Arrays.toString(a)的形式。可参考sort的详解。
	 * deepToString：当数组中又包含数组，那么就不能单存的利用Arrays.toString()了，请看例子。 案例8-1：
	 * 
	 * int a1[] = new int[]{1,2,3}; int a2[] = new int[]{1,3,3}; int a3[] = new
	 * int[]{4,3,2,1}; int[] a [] = new int[][]{a1,a2,a3};
	 * System.out.println(Arrays.toString(a));
	 * System.out.println(Arrays.deepToString(a)); 1 2 3 4 5 6 1 2 3 4 5 6 输出结果：
	 * 
	 * [[I@1b6b7f83, [I@2e807f85, [I@76340c9c] [[1, 2, 3], [1, 3, 3], [4, 3, 2,
	 * 1]] 1 2 1 2 相信各位应该看到差别了吧。
	 * 
	 * 9. hashCode和deepHashCode
	 * 
	 * hashCode：计算一个数组的hashCode.对于一个数组Object[],
	 * hashCode方法返回的值取决于：数组中每个元素的元素oi.hashCode()的值初级计算result = 31 * result +
	 * (oi== null ? 0 : oi.hashCode()); deepHashCode: 对于一个数组Object[],
	 * deepHashCode取决于：数组中每个元素oi，如果oi还是一个数组，那么就继续深入的去获取hashCode，这段比较绕，来个例子比较形象。
	 * 案例9-1：
	 * 
	 * int a1[] = new int[]{1,2,3}; int a2[] = new int[]{1,3,3}; int a3[] = new
	 * int[]{4,3,2,1}; int[] a [] = new int[][]{a1,a2,a3};
	 * System.out.println(Arrays.hashCode(a));
	 * System.out.println(Arrays.deepHashCode(a)); 1 2 3 4 5 6 1 2 3 4 5 6 运行结果：
	 * 
	 * -1683374023 31646847 1 2 1 2
	 * 这样可以看到hashCode与deepHashCode的区别。对于数组而言hashCode只调用到它第一层元素
	 * ，deepHashCode会一直调用直至不能再拆分成数组的元素。
	 */
}
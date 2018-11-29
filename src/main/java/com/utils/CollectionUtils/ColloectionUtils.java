package com.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

/*
 * collection的工具类：
 * 1.org.apache.commons.collections
 * 1.org.apache.commons.collections.CollectionUtils
 */
public class ColloectionUtils {
	
	/**
	 * List反转顺序
	 */
	@Test
	public void reverse(){
		List<String> listA = Arrays.asList("A", "E", "F","B", "C", "D");
		//反转
		Collections.reverse(listA);
		System.out.println(listA);
	}
	/**
	 * List自然排序(升序、降序)
	 */
	@Test
	public void sort(){
		List<String> listA = Arrays.asList("A", "E", "F","B", "C", "D");
		//1.自然排序——>升序
		Collections.sort(listA);
		System.out.println(listA);
		//2.自然排序——>降序
		Collections.reverse(listA);
		System.out.println(listA);
		
	}

	/**
	 * 并集(组合，去重)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void union() {
		List<String> listA = Arrays.asList("A", "B", "C", "D", "E", "F");
		List<String> listB = Arrays.asList("B", "D", "F", "G", "H", "K");
		// 合并两个collection
		Collection<?> union = CollectionUtils.union(listA, listB);
		// List排序
		Collections.sort((List<String>) union);
		// 将数组作为String输出
		String string = ArrayUtils.toString(union);

		System.out.println(string);
		// [A, B, C, D, E, F, G, H, K]
	}

	/**
	 * 交集(相同部分)
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void intersection() {
		List<String> listA = Arrays.asList("A", "B", "C", "D", "E", "F");
		List<String> listB = Arrays.asList("B", "D", "F", "G", "H", "K");
		// 2个数组取交集
		Collection intersection = CollectionUtils.intersection(listA, listB);

		System.out.println(ArrayUtils.toString(intersection));
		// [B, D, F]

	}

	/**
	 * 交集的补集(除去相同部分)
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void disjunction() {
		List<String> listA = Arrays.asList("A", "B", "C", "D", "E", "F");
		List<String> listB = Arrays.asList("B", "D", "F", "G", "H", "K");
		// 2个数组取交集 的补集
		Collection disjunction = CollectionUtils.disjunction(listA, listB);

		System.out.println(ArrayUtils.toString(disjunction));
		// [A, C, E, G, H, K]
	}

	/**
	 * 差集(集合相减)
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void subtract() {
		List<String> listA = Arrays.asList("A", "B", "C", "D", "E", "F");
		List<String> listB = Arrays.asList("B", "D", "F", "G", "H", "K");
		// listA扣除listB
		Collection subtract = CollectionUtils.subtract(listA, listB);

		System.out.println(ArrayUtils.toString(subtract));
		// [A, C, E]

	}

	/**
	 * 集合是否为空
	 */
	@Test
	public void isEmpty() {
		// 内部类
		class Person {
		}
		class Girl extends Person {
		}

		List<Integer> first = new ArrayList<>();
		List<Integer> second = null;
		List<Person> boy = new ArrayList<>();

		boy.add(new Girl());
		// 判断集合是否为空
		System.out.println(CollectionUtils.isEmpty(first)); // true
		System.out.println(CollectionUtils.isEmpty(second)); // true
		System.out.println(CollectionUtils.isEmpty(boy)); // false

		// 判断集合是否不为空
		System.out.println(CollectionUtils.isNotEmpty(first)); // false
		System.out.println(CollectionUtils.isNotEmpty(second)); // false
		System.out.println(CollectionUtils.isNotEmpty(boy)); // true
	}

	/**
	 * 集合是否相等
	 */
	@Test
	public void isEqualCollection() {
		// 内部类
		class Person {
		}
		class Girl extends Person {
		}

		List<Object> first = new ArrayList<>();
		List<Object> second = new ArrayList<>();

		first.add(1);
		first.add("aaa");
		second.add("aaa");
		second.add(1);
		// 简单的数字和字符串比较
		System.out.println("数字、字符串类型：" + CollectionUtils.isEqualCollection(first, second)); // true

		List<Person> boy1 = new ArrayList<>();
		boy1.add(new Girl());
		List<Person> boy2 = new ArrayList<>();
		boy2.add(new Girl());
		// 比较两集合值
		System.out.println("自定义类型的不同实例对象，但是属性值想等：" + CollectionUtils.isEqualCollection(boy1, boy2)); // false

		Girl goodGirl = new Girl();
		List<Person> boy3 = new ArrayList<>();
		boy3.add(goodGirl);
		List<Person> boy4 = new ArrayList<>();
		boy4.add(goodGirl);
		System.out.println("自定义类型的同一个实例对象：" + CollectionUtils.isEqualCollection(boy3, boy4)); // true
		
		/*
		 * In other words, two lists are defined to be equal if they contain the same elements in the same order.
		 * 翻译：必须按顺序完全一致，则返回true
		 */
		List<String> asList1 = Arrays.asList("aa","bb","cc");
		List<String> asList2 = Arrays.asList("bb","aa","cc");
		System.out.println("比较集合："+asList1.equals(asList2));
	}

	/**
	 * 不可修改的集合
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void unmodifiableCollection() {
		/*
		 * 我们对c进行操作，s也同样获得了和c相同的内容，这样就可以避免其他人员修改这个s对象。有时候需要对它进行保护，避免返回结果被人修改。
		 * Collections.unmodifiableCollection可以得到一个集合的镜像，它的返回结果是不可直接被改变，否则会提示错误:
		 * java.lang.UnsupportedOperationException at
		 * org.apache.commons.collections
		 * .collection.UnmodifiableCollection.add(UnmodifiableCollection
		 * .java:75)
		 */
		Collection<String> c = new ArrayList<>();
		// S只是一个返回镜像，不支持修改，否则报错
		Collection<String> s = CollectionUtils.unmodifiableCollection(c);
		c.add("boy");
		c.add("love");
		c.add("girl");
		//s.add("have a error");
		System.out.println(s);
	}

}

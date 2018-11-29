package com.utils.junit;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.util.StopWatch;

/**
 * 代码来自GitHub上JUnit推荐的一个工具类，我在其上做了少许修改。可以用来方便地测量某个方法或某段代码在高并发场景下的表现。
 * ConcurrencyTestUtil.assertConcurrent("1024tasks", tasks, 10, 1000);
 * 参数分别是测试任务名、要运行的任务、最大允许执行总时间和线程池最大大小。
 * 
 * @ClassName: ConcurrencyTestUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ChiJunping
 * @date 2017年5月10日 上午10:18:54
 */
public class ConcurrencyTestUtil {

	public static void assertConcurrent(final String message, final List<? extends Runnable> runnables, final int maxTimeoutSeconds, final int maxThreadPoolSize)
			throws InterruptedException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(message);

		final int numThreads = runnables.size();
		final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
		final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads > maxThreadPoolSize ? maxThreadPoolSize : numThreads);
		try {
			final CountDownLatch afterInitBlocker = new CountDownLatch(1);
			final CountDownLatch allDone = new CountDownLatch(numThreads);
			for (final Runnable submittedTestRunnable : runnables) {
				threadPool.submit(new Runnable() {
					public void run() {
						try {
							afterInitBlocker.await(); // 相当于加了个阀门等待所有任务都准备就绪
							submittedTestRunnable.run();
						} catch (final Throwable e) {
							exceptions.add(e);
						} finally {
							allDone.countDown();
						}
					}
				});
			}

			// start all test runners
			afterInitBlocker.countDown();
			assertTrue(message + " timeout! More than" + maxTimeoutSeconds + "seconds", allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));

			stopWatch.stop();
			System.out.println(stopWatch.prettyPrint());
		} finally {
			threadPool.shutdownNow();
		}
		assertTrue(message + "failed with exception(s)" + exceptions, exceptions.isEmpty());
	}
}
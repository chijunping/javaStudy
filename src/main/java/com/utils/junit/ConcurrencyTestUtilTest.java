package com.utils.junit;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConcurrencyTestUtilTest {

    @Test
    public void testAssertConcurrent() throws InterruptedException {
        List<Runnable> tasks = new ArrayList<Runnable>(100000);
        for (int i = 0; i < 100000; i++) {
            tasks.add(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            });
        }

        ConcurrencyTestUtil.assertConcurrent("1024tasks", tasks, 10, 1000);
    }
}
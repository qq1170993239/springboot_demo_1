package com.lix.study.sdk.common;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 自定义的ThreadPoolExecutor
 * 简单包装以供定义threadName
 * 
 * 声明为Component以提供缺省的小型线程池，直接注入使用
 * @author lix
 */
@Component("myThreadPoolExecutor")
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor() {
        this(1, 10, 0, "ThreadPoolThread");
    }

    /**
     * 线程池构造方法
     * @param corePoolSize
     * @param maxPoolSize
     * @param keepAliveTime 单位为秒
     * @param threadName
     */
    public MyThreadPoolExecutor(int corePoolSize, int maxPoolSize, int keepAliveTime, String threadName) {
        super(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new InnerThreadFactory(threadName));
    }

    /**
     * 私有ThreadFactory实现类
     */
    private static class InnerThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public InnerThreadFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            if (StringUtils.isEmpty(s)) {
                name = "slnb";
            }
            namePrefix = name + "-" + POOL_NUMBER.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}

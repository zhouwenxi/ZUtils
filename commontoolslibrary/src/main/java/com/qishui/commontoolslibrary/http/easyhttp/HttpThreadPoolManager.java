package com.qishui.commontoolslibrary.http.easyhttp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpThreadPoolManager {

    //一个由链表结构组成的双向阻塞队列，即可以从队列的两端插入和移除元素
    private LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();

    //添加任务就是添加线程
    public void excute(Runnable runnable) {
        try {
            if (runnable != null) {
                queue.put(runnable);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                Runnable runnable = null;
                try {
                    //从队列里取线程；
                    runnable = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (runnable != null) {
                    threadPoolExecutor.execute(runnable);
                }
            }
        }
    };

    //拒绝策略,将超时的线程重新放回线程池；
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable/*runnable 超时的线程*/, ThreadPoolExecutor threadPoolExecutor) {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    //把任务放到线程池；
    private ThreadPoolExecutor threadPoolExecutor;

    private HttpThreadPoolManager() {

        //在没有任务到来之前就创建corePoolSize个线程或者一个线程。默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
        // 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中

        //maximumPoolSize：线程池最大线程数，这个参数也是一个非常重要的参数，它表示在线程池中最多能创建多少个线程；

        //keepAliveTime：表示线程没有任务执行时最多保持多久时间会终止。
        // 默认情况下，只有当线程池中的线程数大于corePoolSize时，keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize，
        // 即当线程池中的线程数大于corePoolSize时，如果一个线程空闲的时间达到keepAliveTime，则会终止，直到线程池中的线程数不超过corePoolSize。
        // 但是如果调用了allowCoreThreadTimeOut(boolean)方法，在线程池中的线程数不大于corePoolSize时，keepAliveTime参数也会起作用，直到线程池中的线程数为0；

        // corePoolSize在很多地方被翻译成核心池大小，其实我的理解这个就是线程池的大小。举个简单的例子：
        //　　假如有一个工厂，工厂里面有10个工人，每个工人同时只能做一件任务。
        //　　因此只要当10个工人中有工人是空闲的，来了任务就分配给空闲的工人做；
        //　　当10个工人都有任务在做时，如果还来了任务，就把任务进行排队等待；
        //　　如果说新任务数目增长的速度远远大于工人做任务的速度，那么此时工厂主管可能会想补救措施，比如重新招4个临时工人进来；
        //　　然后就将任务也分配给这4个临时工人做；
        //　　如果说着14个工人做任务的速度还是不够，此时工厂主管可能就要考虑不再接收新的任务或者抛弃前面的一些任务了。
        //　　当这14个工人当中有人空闲时，而新任务增长的速度又比较缓慢，工厂主管可能就考虑辞掉4个临时工了，只保持原来的10个工人，毕竟请额外的工人是要花钱的。
        //　　这个例子中的corePoolSize就是10，而maximumPoolSize就是14（10+4）。
        //　　也就是说corePoolSize就是线程池大小，maximumPoolSize在我看来是线程池的一种补救措施，即任务量突然过大时的一种补救措施。
        //　　不过为了方便理解，在本文后面还是将corePoolSize翻译成核心池大小。

        threadPoolExecutor = new ThreadPoolExecutor(5, 20, 35, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        //开启传动带
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 取消任务
     * @param runnable
     */
    public void cancel(Runnable runnable) {
        if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown() && !threadPoolExecutor.isTerminated()) {
            threadPoolExecutor.remove(runnable);
        }
    }

    //写成单列模式
    private volatile static HttpThreadPoolManager threadPoolManager;

    public static HttpThreadPoolManager with() {

        if (threadPoolManager == null) {
            synchronized (HttpThreadPoolManager.class) {
                if (threadPoolManager == null) {
                    threadPoolManager = new HttpThreadPoolManager();
                }
            }
        }
        return threadPoolManager;
    }

}

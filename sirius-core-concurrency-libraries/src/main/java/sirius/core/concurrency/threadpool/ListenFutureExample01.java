package sirius.core.concurrency.threadpool;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Google Guava工具集
 * @author shadow
 * @create 2023-06-13 00:53
 **/
public class ListenFutureExample01 {

    public void example01() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 通过 MoreExecutors定义ListeningExecutorService
        ListeningExecutorService decoratorService = MoreExecutors.listeningDecorator(executorService);
        // 提交异步任务并且返回ListenableFuture
        ListenableFuture<String> listenableFuture = decoratorService.submit(() -> {
            TimeUnit.SECONDS.sleep(10);
            return "I am the result";
        });

        // 注册回调函数，待任务执行完成后，该回调函数将被调用执行
        listenableFuture.addListener(() -> {
            System.out.println("The task completed.");
            try {
                System.out.println("The task result:" + listenableFuture.get());
                decoratorService.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println("The task failed");
            }
        }, decoratorService);
    }

    public void example02() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        ListeningExecutorService decoratorService =
                MoreExecutors.listeningDecorator(executorService);
        // 提交任务返回listenableFuture
        ListenableFuture<String> listenableFuture =
                decoratorService.submit(() ->
                {
                    TimeUnit.SECONDS.sleep(10);
                    return "I am the result";
                });

        // 使用Futures增加callback
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            // 任务执行成功会被回调
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("The Task completed and result:" + result);
                decoratorService.shutdown();
            }

            // 任务执行失败会被回调
            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, decoratorService);
    }

}

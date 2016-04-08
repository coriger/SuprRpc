package supr;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import supr.rpc.HelloWorldService;
import supr.rpc.Reponse;
import supr.rpc.Request;

/**
 * Created by lujiangtao on 2016/4/8.
 */
public class AsyncClients {

    private static Object WAITOBJECT = new Object();

    public static final void main(String[] args) throws Exception {
        TNonblockingSocket transport = new TNonblockingSocket("127.0.0.1", 9111);
        TAsyncClientManager clientManager = new TAsyncClientManager();

        // 准备调用参数
        Request request = new Request("lujiangtao","18");
        HelloWorldService.AsyncClient asyncClient = new HelloWorldService.AsyncClient.Factory(clientManager, new TBinaryProtocol.Factory()).getAsyncClient(transport);

        // 既然是非阻塞异步模式，所以客户端一定是通过“事件回调”方式，接收到服务器的响应通知的
        asyncClient.send(request,new  AsyncMethodCallback<HelloWorldService.AsyncClient.send_call>() {
            /**
             * 当服务器正确响应了客户端的请求后，这个事件被触发
             */
            @Override
            public void onComplete(HelloWorldService.AsyncClient.send_call call) {
                Reponse response = null;
                try {
                    response = call.getResult();
                } catch (TException e) {
                    return;
                }
            }

            /**
             * 当服务器没有正确响应了客户端的请求，或者其中过程中出现了不可控制的情况。
             * 那么这个事件会被触发
             */
            @Override
            public void onError(Exception exception) {
            }
        });

        //这段代码保证客户端在得到服务器回复前，应用程序本身不会终止
        synchronized (AsyncClients.WAITOBJECT) {
            AsyncClients.WAITOBJECT.wait();
        }

    }

}

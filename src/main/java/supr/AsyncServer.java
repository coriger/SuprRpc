package supr;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import supr.rpc.HelloWorldService;
import supr.rpc.HelloWorldServiceImpl;

import java.nio.channels.Selector;
import java.util.concurrent.Executors;

/**
 * Created by lujiangtao on 2016/4/8.
 */
public class AsyncServer {


    public static final int SERVER_PORT = 9111;

    public void startServer() {
        try {
            // 服务执行控制器（只要是调度服务的具体实现该如何运行）
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

            // 非阻塞异步通讯模型（服务器端）
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(AsyncServer.SERVER_PORT);
            // Selector这个类，是不是很熟悉。
            serverTransport.registerSelector(Selector.open());

            THsHaServer.Args tArgs = new THsHaServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            // 指定消息的封装格式（采用二进制流封装）
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // 指定处理器的所使用的线程池。
            tArgs.executorService(Executors.newFixedThreadPool(100));

            // 启动这个thrift服务
            THsHaServer server = new THsHaServer(tArgs);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AsyncServer server = new AsyncServer();
        server.startServer();
    }

}

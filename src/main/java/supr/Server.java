package supr;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import supr.rpc.HelloWorldService;
import supr.rpc.HelloWorldServiceImpl;

import java.util.concurrent.Executors;

/**
 * Created by lujiangtao on 2016/4/8.
 */
public class Server {


    public static final int SERVER_PORT = 9111;

    public void startServer() {
        try {
            // 服务执行控制器（只要是调度服务的具体实现该如何运行）
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

            // 基于阻塞式同步IO模型的Thrift服务，正式生产环境不建议用这个
            TServerSocket serverTransport = new TServerSocket(Server.SERVER_PORT);

            // 为这个服务器设置对应的IO网络模型、设置使用的消息格式封装、设置线程池参数
            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            // 指定消息的封装格式（采用二进制流封装）
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // 指定处理器的所使用的线程池。
            tArgs.executorService(Executors.newFixedThreadPool(100));

            // 启动这个thrift服务
            TThreadPoolServer server = new TThreadPoolServer(tArgs);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

}

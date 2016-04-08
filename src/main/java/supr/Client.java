package supr;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import supr.rpc.HelloWorldService;
import supr.rpc.Reponse;
import supr.rpc.Request;

/**
 * Created by lujiangtao on 2016/4/8.
 */
public class Client {

    public static final void main(String[] args) throws Exception {
        // 服务器所在的IP和端口
        TSocket transport = new TSocket("127.0.0.1", 9111);
        TProtocol protocol = new TBinaryProtocol(transport);

        // 准备调用参数
        Request request = new Request("lujiangtao","18");
        HelloWorldService.Client client = new HelloWorldService.Client(protocol);

        // 准备传输
        transport.open();
        // 正式调用接口
        Reponse reponse = client.send(request);
        // 一定要记住关闭
        transport.close();

    }

}

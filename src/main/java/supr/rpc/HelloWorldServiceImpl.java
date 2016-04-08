package supr.rpc;

import org.apache.thrift.TException;

/**
 * Created by lujiangtao on 2016/4/8.
 */
public class HelloWorldServiceImpl implements HelloWorldService.Iface {

    @Override
    public Reponse send(Request request) throws ServiceException, TException {
        String name = request.getName();
        String age = request.getAge();

        System.out.println("name is " + name + "; age is "+age);

        Reponse response = new Reponse();
        response.setCode("0");
        response.setMsg("ok");
        return response;
    }
}

# 命名空间的定义
namespace java com.supr.rpc 

# 结构体定义
struct Request {
    1:required string name;
    2:required string age;
}

# 结构体定义
struct Reponse {
    1:required  string code;
    2:required  string msg;
}

# 异常描述定义
exception ServiceException {
    1:required string exceptionCode;
    2:required string exceptionMsg;
}

# 服务定义
service HelloWorldService {
    Reponse send(1:Request request) throws (1:ServiceException e);
}
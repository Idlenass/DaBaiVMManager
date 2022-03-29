package Agent.PVE;

public enum PveResultType {
    Normal("OK"),
    SessionkeyInvaild("身份验证失败"),
    ClientError("内部错误：中间件客户端异常"),
    NodeNameNotFound("内部错误：节点名称未找到"),
    NodeNotFound("内部错误：节点未找到"),
    VmidNotFound("内部错误：VPSid未找到"),
    VmNotFound("内部错误：VPS未找到"),
    VmIllegalAccess("无权访问该VPS"),
    UserAccessDenied("内部错误：中间件客户端鉴权失败"),
    JSONException("内部错误：JSONException"),
    InvocationTargetException("内部错误：Reflect-InvocationTargetException"),
    NoSuchMethodException("内部错误：Reflect-NoSuchMethodException"),
    IllegalAccessException("内部错误：Reflect-IllegalAccessException"),
    CDROMError("内部错误：ISO镜像异常"),
    VPSTimeOut("该VPS已到期");

    private String str;

    PveResultType(String str){
        this.str = str;
    }

    @Override
    public String toString(){
        return this.str;
    }
}

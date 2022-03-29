package Agent.MCSM;

public class McsmResult {
    //结果类型
    private McsmResultType resultType;
    //返回的数据
    private String data;

    public McsmResult() {
    }

    public McsmResult(McsmResultType resultType, String data) {
        this.resultType = resultType;
        this.data = data;
    }

    public McsmResult(McsmResultType resultType) {
        this.resultType = resultType;
    }

    public McsmResultType getResultType() {
        return resultType;
    }

    public void setResultType(McsmResultType resultType) {
        this.resultType = resultType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

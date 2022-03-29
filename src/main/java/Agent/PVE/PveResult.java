package Agent.PVE;

public class PveResult {

    private PveResultType resultType = PveResultType.Normal;
    private String data;
    private Object objectData;

    public PveResult() {
    }

    public PveResult(PveResultType resultType, String data) {
        this.resultType = resultType;
        this.data = data;
    }

    public PveResultType getResultType() {
        return resultType;
    }

    public void setResultType(PveResultType resultType) {
        this.resultType = resultType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Object getObjectData() {
        return objectData;
    }

    public void setObjectData(Object objectData) {
        this.objectData = objectData;
    }
}

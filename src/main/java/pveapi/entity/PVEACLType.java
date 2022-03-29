package pveapi.entity;

public enum PVEACLType {
    //PVE主机ACL权限中的三种授权类型
    user,
    group,
    token,
    OTHER;

    //将字符串转换为枚举类类型，如果找不到匹配的类型，返回OTHER
    public static PVEACLType getPVEACLTypeFromString(String str){
        for(PVEACLType t : PVEACLType.values()){
            if(str.equals(t.toString())){
                return t;
            }
        }
        return OTHER;
    }
}

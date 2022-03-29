package dao;

import entity.Sessionkey;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Sessionkey)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-02 00:55:15
 */
public interface SessionkeyDao {

    /**
     * 通过ID查询单条数据
     *
     * @param sessionkey 主键
     * @return 实例对象
     */
    Sessionkey querySessionkeyBySessionkey(String sessionkey);

    /**
     * 通过Userid查询单条数据
     *
     * @param userid Userid
     * @return 实例对象
     */
    Sessionkey querySessionkeyByUserid(String userid);

    /**
     * 查询指定行数据
     *
     * @param sessionkey 查询条件
     * @return 对象列表
     */
    List<Sessionkey> querySessionkeyAll(Sessionkey sessionkey);

    /**
     * 统计总行数
     *
     * @param sessionkey 查询条件
     * @return 总行数
     */
    long countSessionkey(Sessionkey sessionkey);

    /**
     * 新增数据
     *
     * @param sessionkey 实例对象
     * @return 影响行数
     */
    int insertSessionkey(Sessionkey sessionkey);

    /**
     * 修改数据
     *
     * @param sessionkey 实例对象
     * @return 影响行数
     */
    int updateSessionkey(Sessionkey sessionkey);

    /**
     * 通过主键删除数据
     *
     * @param sessionkey 主键
     * @return 影响行数
     */
    int deleteSessionkeyBySessionkey(String sessionkey);

    /**
     * 通过Userid删除数据
     *
     * @param userid Userid
     * @return 影响行数
     */
    int deleteSessionkeyByUserid(String userid);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Sessionkey> 实例对象列表
     * @return 影响行数
     */
    int insertSessionkeyBatch(@Param("entities") List<Sessionkey> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Sessionkey> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateSessionkeyBatch(@Param("entities") List<Sessionkey> entities);

}


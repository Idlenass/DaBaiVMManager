package dao;

import entity.Pveuser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Pveuser)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-24 17:26:42
 */
public interface PveuserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userid 主键
     * @return 实例对象
     */
    Pveuser queryPveuserByUserid(String userid);

    /**
     * 通过ID查询单条数据
     *
     * @param pveuserid 主键
     * @return 实例对象
     */
    Pveuser queryPveuserByPveuserid(String pveuserid);

    /**
     * 查询指定行数据
     *
     * @param pveuser 查询条件
     * @return 对象列表
     */
    List<Pveuser> queryPveuserByPveuser(Pveuser pveuser);

    /**
     * 统计总行数
     *
     * @param pveuser 查询条件
     * @return 总行数
     */
    long countPveuser(Pveuser pveuser);

    /**
     * 新增数据
     *
     * @param pveuser 实例对象
     * @return 影响行数
     */
    int insertPveuser(Pveuser pveuser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Pveuser> 实例对象列表
     * @return 影响行数
     */
    int insertPveuserBatch(@Param("entities") List<Pveuser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Pveuser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdatePveuserBatch(@Param("entities") List<Pveuser> entities);

    /**
     * 修改数据
     *
     * @param pveuser 实例对象
     * @return 影响行数
     */
    int updatePveuser(Pveuser pveuser);

    /**
     * 通过主键删除数据
     *
     * @param userid 主键
     * @return 影响行数
     */
    int deletePveuserByuserid(String userid);

    /**
     * 通过Pveusername删除数据
     *
     * @param pveuserid 主键
     * @return 影响行数
     */
    int deletePveuserByPveuserid(String pveuserid);

}


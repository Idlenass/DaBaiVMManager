package dao;

import entity.Mcsmuser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Mcsmuser)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-09 20:49:00
 */
public interface McsmuserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userid 主键
     * @return 实例对象
     */
    Mcsmuser queryMcsmuserByUserid(String userid);

    /**
     * 通过ID查询单条数据
     *
     * @param mcsmusername 主键
     * @return 实例对象
     */
    Mcsmuser queryMcsmuserByMcsmusername(String mcsmusername);

    /**
     * 查询指定行数据
     *
     * @param mcsmuser 查询条件
     * @return 对象列表
     */
    List<Mcsmuser> queryMcsmuserByMcsmuser(Mcsmuser mcsmuser);

    /**
     * 统计总行数
     *
     * @param mcsmuser 查询条件
     * @return 总行数
     */
    long countMcsmuser(Mcsmuser mcsmuser);

    /**
     * 新增数据
     *
     * @param mcsmuser 实例对象
     * @return 影响行数
     */
    int insertMcsmuser(Mcsmuser mcsmuser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Mcsmuser> 实例对象列表
     * @return 影响行数
     */
    int insertMcsmuserBatch(@Param("entities") List<Mcsmuser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Mcsmuser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateMcsmuserBatch(@Param("entities") List<Mcsmuser> entities);

    /**
     * 修改数据
     *
     * @param mcsmuser 实例对象
     * @return 影响行数
     */
    int updateMcsmuser(Mcsmuser mcsmuser);

    /**
     * 通过主键删除数据
     *
     * @param mcsmusername 主键
     * @return 影响行数
     */
    int deleteMcsmuserByMcsmusername(String mcsmusername);

    /**
     * 通过Userid删除数据
     *
     * @param userid 主键
     * @return 影响行数
     */
    int deleteMcsmuserByUserid(String userid);
}


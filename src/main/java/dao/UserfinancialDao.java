package dao;

import entity.Userfinancial;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Userfinancial)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-03 18:11:18
 */
public interface UserfinancialDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userid 主键
     * @return 实例对象
     */
    Userfinancial queryUserfinancialByUserid(String userid);

    /**
     * 查询指定行数据
     *
     * @param userfinancial 查询条件
     * @return 对象列表
     */
    List<Userfinancial> queryUserfinancialAll(Userfinancial userfinancial);

    /**
     * 统计总行数
     *
     * @param userfinancial 查询条件
     * @return 总行数
     */
    long countUserfinancial(Userfinancial userfinancial);

    /**
     * 新增数据
     *
     * @param userfinancial 实例对象
     * @return 影响行数
     */
    int insertUserfinancial(Userfinancial userfinancial);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Userfinancial> 实例对象列表
     * @return 影响行数
     */
    int insertUserfinancialBatch(@Param("entities") List<Userfinancial> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Userfinancial> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateUserfinancialBatch(@Param("entities") List<Userfinancial> entities);

    /**
     * 修改数据
     *
     * @param userfinancial 实例对象
     * @return 影响行数
     */
    int updateUserfinancial(Userfinancial userfinancial);

    /**
     * 通过主键删除数据
     *
     * @param userid 主键
     * @return 影响行数
     */
    int deleteUserfinancialByUserid(String userid);

}


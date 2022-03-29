package dao;

import entity.Isofile;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Isofile)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-20 21:11:33
 */
public interface IsofileDao {

    /**
     * 通过ID查询单条数据
     *
     * @param isoid 主键
     * @return 实例对象
     */
    Isofile queryIsofileById(Integer isoid);

    /**
     * 查询指定行数据
     *
     * @param isofile 查询条件
     * @return 对象列表
     */
    List<Isofile> queryIsofileByIsofile(Isofile isofile);

    /**
     * 统计总行数
     *
     * @param isofile 查询条件
     * @return 总行数
     */
    long countIsofile(Isofile isofile);

    /**
     * 新增数据
     *
     * @param isofile 实例对象
     * @return 影响行数
     */
    int insertIsofile(Isofile isofile);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Isofile> 实例对象列表
     * @return 影响行数
     */
    int insertIsofileBatch(@Param("entities") List<Isofile> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Isofile> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateIsofileBatch(@Param("entities") List<Isofile> entities);

    /**
     * 修改数据
     *
     * @param isofile 实例对象
     * @return 影响行数
     */
    int updateIsofile(Isofile isofile);

    /**
     * 通过主键删除数据
     *
     * @param isoid 主键
     * @return 影响行数
     */
    int deleteIsofileById(Integer isoid);

}


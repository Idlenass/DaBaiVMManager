package dao;

import entity.Pveserverappendinfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Pveserverappendinfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-24 21:51:56
 */
public interface PveserverappendinfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param vmid 主键
     * @return 实例对象
     */
    Pveserverappendinfo queryPvesaiByvmid(int vmid);

    /**
     * 查询指定行数据
     *
     * @param pveserverappendinfo 查询条件
     * @return 对象列表
     */
    List<Pveserverappendinfo> queryPvesaiByPvesai(Pveserverappendinfo pveserverappendinfo);

    /**
     * 统计总行数
     *
     * @param pveserverappendinfo 查询条件
     * @return 总行数
     */
    long countPvesai(Pveserverappendinfo pveserverappendinfo);

    /**
     * 新增数据
     *
     * @param pveserverappendinfo 实例对象
     * @return 影响行数
     */
    int insertPvesai(Pveserverappendinfo pveserverappendinfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Pveserverappendinfo> 实例对象列表
     * @return 影响行数
     */
    int insertPvesaiBatch(@Param("entities") List<Pveserverappendinfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Pveserverappendinfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdatePvesaiBatch(@Param("entities") List<Pveserverappendinfo> entities);

    /**
     * 修改数据
     *
     * @param pveserverappendinfo 实例对象
     * @return 影响行数
     */
    int updatePvesai(Pveserverappendinfo pveserverappendinfo);

    /**
     * 通过主键删除数据
     *
     * @param vmid 主键
     * @return 影响行数
     */
    int deletePvesaiByvmid(int vmid);

}


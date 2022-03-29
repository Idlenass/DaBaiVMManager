package dao;

import entity.Mcsmserverappendinfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Mcsmserverappendinfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-12 20:33:20
 */
public interface McsmserverappendinfoDao {
    //Mcsai MCsmServerAppendInfo的缩写
    /**
     * 通过ID查询单条数据
     *
     * @param servername 主键
     * @return 实例对象
     */
    Mcsmserverappendinfo queryMcsaiByServername(String servername);

    /**
     * 查询指定行数据
     *
     * @param mcsmserverappendinfo 查询条件
     * @return 对象列表
     */
    List<Mcsmserverappendinfo> queryMcsaiByMcsai(Mcsmserverappendinfo mcsmserverappendinfo);

    /**
     * 统计总行数
     *
     * @param mcsmserverappendinfo 查询条件
     * @return 总行数
     */
    long countMcsai(Mcsmserverappendinfo mcsmserverappendinfo);

    /**
     * 新增数据
     *
     * @param mcsmserverappendinfo 实例对象
     * @return 影响行数
     */
    int insertMcsai(Mcsmserverappendinfo mcsmserverappendinfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Mcsmserverappendinfo> 实例对象列表
     * @return 影响行数
     */
    int insertMcsaiBatch(@Param("entities") List<Mcsmserverappendinfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Mcsmserverappendinfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateMcsaiBatch(@Param("entities") List<Mcsmserverappendinfo> entities);

    /**
     * 修改数据
     *
     * @param mcsmserverappendinfo 实例对象
     * @return 影响行数
     */
    int updateMcsai(Mcsmserverappendinfo mcsmserverappendinfo);

    /**
     * 通过主键删除数据
     *
     * @param servername 主键
     * @return 影响行数
     */
    int deleteMcsaiByServername(String servername);

}


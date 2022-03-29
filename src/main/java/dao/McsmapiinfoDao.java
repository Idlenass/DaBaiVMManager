package dao;

import entity.Mcsmapiinfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Mcsmapiinfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-07 14:56:41
 */
public interface McsmapiinfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Mcsmapiinfo queryMcsmapiinfoById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Mcsmapiinfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param mcsmapiinfo 实例对象
     * @return 对象列表
     */
    List<Mcsmapiinfo> queryMcsmapiinfoByMcsmapiinfo(Mcsmapiinfo mcsmapiinfo);

    /**
     * 新增数据
     *
     * @param mcsmapiinfo 实例对象
     * @return 影响行数
     */
    int insertMcsmapiinfo(Mcsmapiinfo mcsmapiinfo);

    /**
     * 修改数据
     *
     * @param mcsmapiinfo 实例对象
     * @return 影响行数
     */
    int updateMcsmapiinfo(Mcsmapiinfo mcsmapiinfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteMcsmapiinfoById(Integer id);

}
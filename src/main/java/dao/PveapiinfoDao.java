package dao;

import entity.Pveapiinfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Pveapiinfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-08 13:36:34
 */
public interface PveapiinfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Pveapiinfo queryPveapiinfoById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Pveapiinfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param pveapiinfo 实例对象
     * @return 对象列表
     */
    List<Pveapiinfo> queryPveapiinfoByPveapiinfo(Pveapiinfo pveapiinfo);

    /**
     * 新增数据
     *
     * @param pveapiinfo 实例对象
     * @return 影响行数
     */
    int insertPveapiinfo(Pveapiinfo pveapiinfo);

    /**
     * 修改数据
     *
     * @param pveapiinfo 实例对象
     * @return 影响行数
     */
    int updatePveapiinfo(Pveapiinfo pveapiinfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deletePveapiinfoById(Integer id);

}
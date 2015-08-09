/*
 * 文  件   名: MybatisAbstractEntity.java
 * 版         本: opengadget-core(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月23日
 */
package org.anttribe.opengadget.core.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.dayatang.domain.InstanceFactory;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * Mybatis实现机制的领域实体：Mybatis实现中采用Xml映射文件的方式实现
 * 
 * @author zhaoyong
 * @version 2015年1月23日
 */
public class MybatisAbstractEntity implements Entity
{
    /**
     * sqlSessionTemplate
     */
    private static SqlSessionTemplate sqlSessionTemplate;
    
    /**
     * 实体类名(simple className)
     */
    private String entityClassName = this.getClass().getCanonicalName();
    
    /**
     * 获取SqlSessionTemplate对象
     * 
     * @return SqlSessionTemplate
     */
    public static SqlSessionTemplate getSqlSessionTemplate()
    {
        if (null == sqlSessionTemplate)
        {
            sqlSessionTemplate = InstanceFactory.getInstance(SqlSessionTemplate.class);
        }
        return sqlSessionTemplate;
    }
    
    /**
     * 保存数据
     * 
     * @param entity 实体对象
     * @return int
     */
    public int save()
    {
        String statement = (new StringBuffer(entityClassName)).append(".insert").toString();
        return getSqlSessionTemplate().insert(statement, this);
    }
    
    /**
     * 更新当前对象
     * 
     * @return int
     */
    public int update()
    {
        String statement = (new StringBuffer(entityClassName)).append(".update").toString();
        return getSqlSessionTemplate().update(statement, this);
    }
    
    /**
     * 删除当前对象
     * 
     * @return int
     */
    public int remove()
    {
        String statement = (new StringBuffer(entityClassName)).append(".delete").toString();
        return getSqlSessionTemplate().update(statement, this);
    }
    
    /**
     * 批量保存数据
     * 
     * @param entityClass Class<? extends Entity>
     * @param entities List<Entity>
     * @return int
     */
    public static int batchSave(Class<? extends Entity> entityClass, List<? extends Entity> entities)
    {
        String statement = (new StringBuffer(entityClass.getCanonicalName())).append(".batchInsert").toString();
        return getSqlSessionTemplate().insert(statement, entities);
    }
    
    /**
     * 批量保存数据
     * 
     * @param entityClass 实体类
     * @param entities List<Entity>
     * @return int
     */
    public static int batchUpdate(Class<? extends Entity> entityClass, List<? extends Entity> entities)
    {
        String statement = (new StringBuffer(entityClass.getCanonicalName())).append(".batchUpdate").toString();
        return getSqlSessionTemplate().insert(statement, entities);
    }
    
    /**
     * 批量删除数据
     * 
     * @param entityClass Class<Entity>
     * @param entities List<Entity>
     * @return int
     */
    public static int batchRemove(Class<? extends Entity> entityClass, List<? extends Entity> entities)
    {
        String statement = (new StringBuffer(entityClass.getCanonicalName())).append(".batchDelete").toString();
        return getSqlSessionTemplate().insert(statement, entities);
    }
    
    /**
     * 根据实体id获取对应实体对象
     * 
     * @param entityClass
     * @param entityId
     * @return
     */
    public static <T extends Entity> T load(Class<? extends Entity> entityClass, Object entityId)
    {
        String statement = (new StringBuilder()).append(entityClass.getCanonicalName()).append(".queryById").toString();
        return getSqlSessionTemplate().selectOne(statement, entityId);
    }
    
    /**
     * 获取当前类型的所有对象
     * 
     * @param entityClass Class<Entity>
     * @return List<T>
     */
    public static <T extends Entity> List<T> findAll(Class<? extends Entity> entityClass)
    {
        String statement = (new StringBuilder()).append(entityClass.getCanonicalName()).append(".queryAll").toString();
        return getSqlSessionTemplate().selectList(statement);
    }
    
    /**
     * 根据条件查询数据
     * 
     * @param entityClass Class<? extends Entity>
     * @param criteria
     * @return
     */
    public static <T extends Entity> List<T> find(Class<? extends Entity> entityClass, Map<String, Object> criteria)
    {
        String statement =
            (new StringBuilder()).append(entityClass.getCanonicalName()).append(".queryByCriteria").toString();
        return getSqlSessionTemplate().selectList(statement, criteria);
    }
    
    /**
     * 查询分页数据
     * 
     * @param currentPage 当前页
     * @param pagesize 每页显示数据条数
     * @param criteria 查询条件
     * @return Pagination<T>
     */
    public static <T extends Entity> Pagination<T> find(Class<? extends Entity> entityClass, int currentPage,
        int pagesize, Map<String, Object> criteria)
    {
        String statement =
            (new StringBuilder()).append(entityClass.getCanonicalName()).append(".queryByPagination").toString();
        
        if (MapUtils.isEmpty(criteria))
        {
            criteria = new HashMap<String, Object>();
        }
        int totalCount = count(entityClass, criteria);
        Pagination<T> pagination = new Pagination<T>(currentPage, pagesize, totalCount);
        criteria.put("offset", pagination.getOffset());
        criteria.put("limit", pagination.getPagesize());
        List<T> datas = getSqlSessionTemplate().selectList(statement, criteria);
        pagination.setDatas(datas);
        return pagination;
    }
    
    /**
     * 根据条件查询数据条数
     * 
     * @param entityClass
     * @param criteria
     * @return
     */
    public static int count(Class<? extends Entity> entityClass, Map<String, Object> criteria)
    {
        String statement =
            (new StringBuilder()).append(entityClass.getCanonicalName()).append(".queryDataCount").toString();
        return Integer.parseInt(getSqlSessionTemplate().selectOne(statement, criteria).toString());
    }
}
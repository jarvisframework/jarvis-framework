package com.jarvis.framework.web.controller;

import com.jarvis.framework.bizlog.annotation.BizLogger;
import com.jarvis.framework.bizlog.constant.BizLevel;
import com.jarvis.framework.constant.BaseColumnConstant;
import com.jarvis.framework.constant.SymbolConstant;
import com.jarvis.framework.core.entity.BaseDynamicEntity;
import com.jarvis.framework.core.exception.FrameworkException;
import com.jarvis.framework.mybatis.mapper.BaseDynamicEntityMapper;
import com.jarvis.framework.search.CriteriaQueryBuilder;
import com.jarvis.framework.search.DynamicEntityQuery;
import com.jarvis.framework.search.Page;
import com.jarvis.framework.web.rest.RestResponse;
import com.jarvis.framework.web.service.BaseDynamicEntityService;
import com.jarvis.framework.web.util.SwaggerAipHttpMethod;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年1月27日
 */
public abstract class BaseDynamicEntityController<Id extends Serializable, Entity extends BaseDynamicEntity<Id>,
                                                  Mapper extends BaseDynamicEntityMapper<Id, Entity>,
                                                  Service extends BaseDynamicEntityService<Id, Entity, Mapper>>
    extends BaseController {

    @Autowired
    protected Service service;

    protected Service getService() {
        return service;
    }

    /**
     * 获取动态表
     *
     * @param tableId
     * @return
     */
    @SuppressWarnings("unchecked")
    protected String getTableName(Id tableId) {
        return service.getTableName((Id) processTableId(tableId));
    }

    @ApiOperation(value = "新增", httpMethod = SwaggerAipHttpMethod.POST)
    @PostMapping
    @BizLogger(content = "新增内容：#{args[0].toString()}", level = BizLevel.WRITE)
    public RestResponse<?> create(@Validated @RequestBody Entity entity, Id tableId) {
        getService().insert(processTableName(entity, tableId));
        return RestResponse.success(entity);
    }

    @ApiOperation(value = "修改", httpMethod = SwaggerAipHttpMethod.PUT)
    @PutMapping
    @BizLogger(content = "修改内容：#{args[0]}", level = BizLevel.WRITE)
    public RestResponse<?> update(@Validated @RequestBody Entity entity, Id tableId) {
        getService().update(processTableName(entity, tableId));
        return RestResponse.success(entity);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "删除", httpMethod = SwaggerAipHttpMethod.DELETE)
    @DeleteMapping("/{id}")
    @BizLogger(content = "删除ID：#{args[0]}", level = BizLevel.WRITE)
    public RestResponse<Boolean> deleteById(@PathVariable("id") Id id, Id tableId) {
        return RestResponse.success(getService().deleteById((Id) processTableId(tableId), id));
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "批量删除", httpMethod = SwaggerAipHttpMethod.DELETE)
    @DeleteMapping
    @BizLogger(content = "删除ID集合：#{args[0]}", level = BizLevel.WRITE)
    public RestResponse<Integer> deleteByIds(@RequestBody Id[] ids, Id tableId) {
        return RestResponse
            .success(getService().deleteByIds((Id) processTableId(tableId), (Collection<Id>) processIds(ids)));
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "根据ID获取对象", httpMethod = SwaggerAipHttpMethod.GET)
    @GetMapping("/{id}")
    public RestResponse<Entity> getById(@PathVariable("id") Id id, Id tableId) {
        return RestResponse.success(getService().getById((Id) processTableId(tableId), id));
    }

    @ApiOperation(value = "总数", httpMethod = SwaggerAipHttpMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "tableId", value = "表id"),
        @ApiImplicitParam(name = "Q_[EQ|LIKE|GT]_[属性名称]", value = "动态条件，可以有多个，如：Q_EQ_status=80"),
        @ApiImplicitParam(name = "keyword", value = "关键字"),
    })
    @GetMapping("/count")
    public RestResponse<?> count(@ApiParam(hidden = true) DynamicEntityQuery criterion, String keyword, Id tableId) {
        return RestResponse.success(getService().count(criteriaQuery(tableId, criterion, keyword)));
    }

    @ApiOperation(value = "分页", httpMethod = SwaggerAipHttpMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "tableId", value = "表id"),
        @ApiImplicitParam(name = "pageNumber", value = "页号", defaultValue = "1"),
        @ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10"),
        @ApiImplicitParam(name = "counted", value = "是否查询总数", defaultValue = "true"),
        @ApiImplicitParam(name = "Q_[EQ|LIKE|GT]_[属性名称]", value = "动态条件，可以有多个，如：Q_EQ_status=80"),
        @ApiImplicitParam(name = "keyword", value = "关键字"),
    })
    @GetMapping("/page")
    @BizLogger(level = BizLevel.QUERY)
    public RestResponse<?> page(Page page, @ApiParam(hidden = true) DynamicEntityQuery criterion, String keyword,
        Id tableId) {
        final List<?> result = getService().page(page, criteriaQuery(tableId, criterion, keyword));
        if (page.isCounted()) {
            return RestResponse.success(page);
        }
        return RestResponse.success(result);
    }

    private Entity processTableName(Entity entity, Id tableId) {
        entity.tableName(getTableName(tableId));
        return entity;
    }

    private Serializable processTableId(Id tableId) {
        if (null == tableId) {
            return null;
        }
        final Class<?> idClass = getIdClass(this.getClass());
        if (idClass.isAssignableFrom(Long.class)) {
            return Long.parseLong(tableId.toString());
        }
        return tableId;
    }

    private List<?> processIds(Id[] ids) {
        final Class<?> idClass = getIdClass(this.getClass());
        if (idClass.isAssignableFrom(Long.class)) {
            return Stream.of(ids).map(e -> {
                return Long.parseLong(e.toString());
            }).collect(Collectors.toList());
        }
        return Stream.of(ids).collect(Collectors.toList());
    }

    protected DynamicEntityQuery criteriaQuery(Id tableId, DynamicEntityQuery criterion, String keyword) {
        if (null == criterion) {
            criterion = CriteriaQueryBuilder.createDynamicEntityCriterion();
        }
        criterion.tableName(getTableName(tableId));
        // 把entity中不为空的属性添加为过滤条件
        //EntityQueryUtil.processQuery(criterion, entity);
        // 前台传过来查询指定字段
        final String selectAttributes = getParameter(selectAttributesParameter);
        if (StringUtils.hasText(selectAttributes)) {
            criterion.getColumns().add(BaseColumnConstant.ID);
            final String[] arraySelect = selectAttributes.split(SymbolConstant.COMMA);
            criterion.getColumns().addAll(Arrays.asList(arraySelect));
        }
        // 处理keyword检索
        if (StringUtils.hasText(keyword)) {
            final String keywordAttributes = getParameter(keywordAttributesParameter);
            // 前台传过来查询指定一体化查询字段
            if (StringUtils.hasText(keywordAttributes)) {
                criterion.filter(condition -> {
                    condition.like(keywordAttributes.split(SymbolConstant.COMMA), keyword);
                });
            } else {
                final List<String> keywordFields = keywordFields();
                if (null == keywordFields || keywordFields.isEmpty()) {
                    processKeywordCriterion(criterion, keyword);
                } else {
                    criterion.filter(condition -> {
                        condition.like(keywordFields, keyword);
                    });
                }
            }
        }
        // 处理排序字段
        processPageCriterion(criterion);

        return criterion;
    }

    /**
     * 设置keyword条件对应的字段
     *
     * @return
     */
    protected List<String> keywordFields() {
        throw new FrameworkException("未设置keyword检索字段，请复写在controller中keywordFields方法");
    }

    /**
     * 设置分页额外条件、排序
     *
     * @param criterion
     */
    protected void processPageCriterion(DynamicEntityQuery criterion) {

    }

    /**
     * 自定义keyword条件（前提是keywordFields方法返回null或空）
     *
     * <pre>
     * Integer k1 = toK1(keyword);
     * String k2 = toK2(keyword);
     * criterion.filter(f -> {
     *     f.orSubCondition()
     *         .like("c1", k1).like("c2", k2).like("c3", keyword)
     *         .endSubCondition();
     * });
     * </pre>
     *
     * @param criterion 条件
     * @param key 关键字
     */
    protected void processKeywordCriterion(DynamicEntityQuery criterion, String keyword) {

    }
}

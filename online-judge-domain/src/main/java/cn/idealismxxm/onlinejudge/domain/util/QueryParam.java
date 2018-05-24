package cn.idealismxxm.onlinejudge.domain.util;

import java.io.Serializable;
import java.util.Map;

/**
 * 查询参数封装
 *
 * @author idealism
 * @date 2018/5/24
 */
public class QueryParam implements Serializable {

    /**
     * 列表页大小的最小值
     */
    private static final Integer LIST_MIN_PAGE_SIZE = 20;

    /**
     * 列表页大小的最大值
     */
    private static final Integer LIST_MAX_PAGE_SIZE = 100;

    /**
     * 页号
     */
    private Integer pageNum = 1;

    /**
     * 页大小
     */
    private Integer pageSize = QueryParam.LIST_MIN_PAGE_SIZE;

    /**
     * 参数
     */
    private Map<String, Object> param;

    public QueryParam() {}

    public QueryParam(Integer pageNum, Integer pageSize, Map<String, Object> param) {
        this.setPageNum(pageNum);
        this.setPageSize(pageSize);
        this.setParam(param);
    }

    public Integer getOffset() {
        return (this.pageNum - 1) * this.pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }
    }

    public Integer getLimit() {
        return this.pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= QueryParam.LIST_MIN_PAGE_SIZE) {
            this.pageSize = QueryParam.LIST_MIN_PAGE_SIZE;
        } else if (pageSize >= QueryParam.LIST_MAX_PAGE_SIZE) {
            this.pageNum = QueryParam.LIST_MAX_PAGE_SIZE;
        } else {
            this.pageNum = QueryParam.LIST_MAX_PAGE_SIZE;
        }
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
}

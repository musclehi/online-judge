package cn.idealismxxm.onlinejudge.domain.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 *
 * @author idealism
 * @date 2018/5/24
 */
public class Pagination<T> implements Serializable {
    /**
     * 页号
     */
    private Integer pageNum = 0;

    /**
     * 页大小
     */
    private Integer pageSize = 0;

    /**
     * 数据总数
     */
    private Integer totalCount = 0;

    /**
     * 页总数
     */
    private Integer totalPage = 0;

    /**
     * 数据
     */
    private List<T> data;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

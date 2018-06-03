package cn.idealismxxm.onlinejudge.domain.entity;
public class ProblemTag implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private java.util.Date updatedTime;//更新时间
    private Integer deletedStatus;//逻辑删除状态（0：未删除，1：已删除）

    private Integer problemId;//题目主键
    private Integer tagId;//标签主键
    private String updator;//修改人的用户名
    public ProblemTag() {
        super();
    }
    public ProblemTag(Integer id,java.util.Date updatedTime,Integer deletedStatus,Integer problemId,Integer tagId,String updator) {
        super();
        this.id = id;
        this.updatedTime = updatedTime;
        this.deletedStatus = deletedStatus;
        this.problemId = problemId;
        this.tagId = tagId;
        this.updator = updator;
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.util.Date getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(java.util.Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getDeletedStatus() {
        return this.deletedStatus;
    }

    public void setDeletedStatus(Integer deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public Integer getProblemId() {
        return this.problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getTagId() {
        return this.tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getUpdator() {
        return this.updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

}

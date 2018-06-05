package cn.idealismxxm.onlinejudge.domain.entity;
public class Problem implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private java.util.Date updatedTime;//更新时间
    private Integer timeLimit;//题目时间限制（单位：ms）
    private Integer descriptionId;//题目描述id
    private Integer publicStatus;//公开状态（0：私密，1：公开）
    private Integer memoryLimit;//题目空间限制（单位：KB）
    private String title;//题目标题
    private Integer originalOj;//原始OJ
    private String originalId;//原始题号
    private String url;//题目链接
    public Problem() {
        super();
    }
    public Problem(Integer id,java.util.Date updatedTime,Integer timeLimit,Integer descriptionId,Integer publicStatus,Integer memoryLimit,String title,Integer originalOj,String originalId,String url) {
        super();
        this.id = id;
        this.updatedTime = updatedTime;
        this.timeLimit = timeLimit;
        this.descriptionId = descriptionId;
        this.publicStatus = publicStatus;
        this.memoryLimit = memoryLimit;
        this.title = title;
        this.originalOj = originalOj;
        this.originalId = originalId;
        this.url = url;
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

    public Integer getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getDescriptionId() {
        return this.descriptionId;
    }

    public void setDescriptionId(Integer descriptionId) {
        this.descriptionId = descriptionId;
    }

    public Integer getPublicStatus() {
        return this.publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    public Integer getMemoryLimit() {
        return this.memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOriginalOj() {
        return this.originalOj;
    }

    public void setOriginalOj(Integer originalOj) {
        this.originalOj = originalOj;
    }

    public String getOriginalId() {
        return this.originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

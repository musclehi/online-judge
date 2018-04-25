package cn.idealismxxm.onlinejudge.domain.entity;
public class Contest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private java.util.Date startTime;//比赛开始时间
    private String problemIds;//题目id数组json串
    private String creator;//创建者的用户名
    private String name;//比赛名称
    private java.util.Date endTime;//比赛结束时间
    public Contest() {
        super();
    }
    public Contest(Integer id,java.util.Date startTime,String problemIds,String creator,String name,java.util.Date endTime) {
        super();
        this.id = id;
        this.startTime = startTime;
        this.problemIds = problemIds;
        this.creator = creator;
        this.name = name;
        this.endTime = endTime;
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.util.Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    public String getProblemIds() {
        return this.problemIds;
    }

    public void setProblemIds(String problemIds) {
        this.problemIds = problemIds;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

}

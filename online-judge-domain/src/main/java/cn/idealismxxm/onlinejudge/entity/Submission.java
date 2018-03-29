package cn.idealismxxm.onlinejudge.entity;
public class Submission implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private java.util.Date updatedTime;//更新时间
    private String extension;//扩充字段（Json串）
    private Integer memory;//运行内存（单位：KB）
    private Integer publicStatus;//源代码公开状态（0：私密，1：公开）
    private Integer language;//提交的语言
    private java.util.Date submittedTime;//提交时间
    private String source;//源代码
    private Integer result;//评测结果
    private Integer remoteSubmissionId;//原OJ提交id
    private Integer problemId;//题目id
    private Integer time;//运行时间（单位：ms）
    private String username;//提交的用户名
    private Integer remoteAccountId;//原OJ提交帐号id
    public Submission() {
        super();
    }
    public Submission(Integer id,java.util.Date updatedTime,String extension,Integer memory,Integer publicStatus,Integer language,java.util.Date submittedTime,String source,Integer result,Integer remoteSubmissionId,Integer problemId,Integer time,String username,Integer remoteAccountId) {
        super();
        this.id = id;
        this.updatedTime = updatedTime;
        this.extension = extension;
        this.memory = memory;
        this.publicStatus = publicStatus;
        this.language = language;
        this.submittedTime = submittedTime;
        this.source = source;
        this.result = result;
        this.remoteSubmissionId = remoteSubmissionId;
        this.problemId = problemId;
        this.time = time;
        this.username = username;
        this.remoteAccountId = remoteAccountId;
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

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getMemory() {
        return this.memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getPublicStatus() {
        return this.publicStatus;
    }

    public void setPublicStatus(Integer publicStatus) {
        this.publicStatus = publicStatus;
    }

    public Integer getLanguage() {
        return this.language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public java.util.Date getSubmittedTime() {
        return this.submittedTime;
    }

    public void setSubmittedTime(java.util.Date submittedTime) {
        this.submittedTime = submittedTime;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getRemoteSubmissionId() {
        return this.remoteSubmissionId;
    }

    public void setRemoteSubmissionId(Integer remoteSubmissionId) {
        this.remoteSubmissionId = remoteSubmissionId;
    }

    public Integer getProblemId() {
        return this.problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRemoteAccountId() {
        return this.remoteAccountId;
    }

    public void setRemoteAccountId(Integer remoteAccountId) {
        this.remoteAccountId = remoteAccountId;
    }

}

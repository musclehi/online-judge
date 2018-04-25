package cn.idealismxxm.onlinejudge.domain.entity;
public class ContestSubmission implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private Integer submissionId;//提交记录id
    private Integer problemId;//题目id
    private Integer contestId;//比赛id
    public ContestSubmission() {
        super();
    }
    public ContestSubmission(Integer id,Integer submissionId,Integer problemId,Integer contestId) {
        super();
        this.id = id;
        this.submissionId = submissionId;
        this.problemId = problemId;
        this.contestId = contestId;
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubmissionId() {
        return this.submissionId;
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getProblemId() {
        return this.problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getContestId() {
        return this.contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

}

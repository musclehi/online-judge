package cn.idealismxxm.onlinejudge.domain.entity;
public class ContestContestant implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private Integer contestId;//比赛id
    private String username;//参赛者的用户名
    public ContestContestant() {
        super();
    }
    public ContestContestant(Integer id,Integer contestId,String username) {
        super();
        this.id = id;
        this.contestId = contestId;
        this.username = username;
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContestId() {
        return this.contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

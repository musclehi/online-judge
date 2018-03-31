package cn.idealismxxm.onlinejudge.domain.entity;
public class TestCase implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private String output;//标准输出
    private Integer score;//测试点分数
    private String input;//输入
    private Integer problemId;//题目id
    public TestCase() {
        super();
    }
    public TestCase(Integer id,String output,Integer score,String input,Integer problemId) {
        super();
        this.id = id;
        this.output = output;
        this.score = score;
        this.input = input;
        this.problemId = problemId;
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutput() {
        return this.output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getInput() {
        return this.input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Integer getProblemId() {
        return this.problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

}

package cn.idealismxxm.onlinejudge.entity;

public class OriginalProblem implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//题号
    private String output;//题目输出描述
    private String input;//题目输入描述
    private String extension;//扩充字段（Json串）
    private String sampleInput;//题目输入样例
    private Integer timeLimit;//题目时间限制（单位：ms）
    private String sampleOutput;//题目输出样例
    private String description;//题目描述
    private Integer memoryLimit;//题目空间限制（单位：KB）
    private String title;//题目标题

    public OriginalProblem() {
        super();
    }

    public OriginalProblem(Integer id, String output, String input, String extension, String sampleInput, Integer timeLimit, String sampleOutput, String description, Integer memoryLimit, String title) {
        super();
        this.id = id;
        this.output = output;
        this.input = input;
        this.extension = extension;
        this.sampleInput = sampleInput;
        this.timeLimit = timeLimit;
        this.sampleOutput = sampleOutput;
        this.description = description;
        this.memoryLimit = memoryLimit;
        this.title = title;
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

    public String getInput() {
        return this.input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSampleInput() {
        return this.sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public Integer getTimeLimit() {
        return this.timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getSampleOutput() {
        return this.sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}

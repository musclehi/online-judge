package cn.idealismxxm.onlinejudge.domain.entity;
public class Description implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private String output;//题目输出描述
    private String input;//题目输入描述
    private String extension;//扩充字段（Json串）
    private String sampleInput;//题目输入样例
    private String sampleOutput;//题目输出样例
    private String description;//题目描述
    public Description() {
        super();
    }
    public Description(Integer id,String output,String input,String extension,String sampleInput,String sampleOutput,String description) {
        super();
        this.id = id;
        this.output = output;
        this.input = input;
        this.extension = extension;
        this.sampleInput = sampleInput;
        this.sampleOutput = sampleOutput;
        this.description = description;
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

}

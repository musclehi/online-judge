package cn.idealismxxm.onlinejudge.domain.entity;
public class Tag implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private static final Tag ROOT_TAG = new Tag(0, null, null, "/", null);

    private Integer id;//主键
    private java.util.Date updatedTime;//更新时间
    private Integer parentId;//上级标签的主键
    private String name;//标签名称
    private String updator;//修改人的用户名

    public Tag() {
        super();
    }
    public Tag(Integer id,java.util.Date updatedTime,Integer parentId,String name,String updator) {
        super();
        this.id = id;
        this.updatedTime = updatedTime;
        this.parentId = parentId;
        this.name = name;
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

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdator() {
        return this.updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public static Tag getRootTag() {
        return ROOT_TAG;
    }
}

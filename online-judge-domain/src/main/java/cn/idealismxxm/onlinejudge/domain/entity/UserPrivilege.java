package cn.idealismxxm.onlinejudge.domain.entity;
public class UserPrivilege implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private java.util.Date updatedTime;//更新时间
    private Integer deletedStatus;//逻辑删除状态（0：未删除，1：已删除）

    private String updator;//修改人的用户名
    private Integer privilege;//权限标识
    private String username;//用户名
    public UserPrivilege() {
        super();
    }
    public UserPrivilege(Integer id,java.util.Date updatedTime,Integer deletedStatus,String updator,Integer privilege,String username) {
        super();
        this.id = id;
        this.updatedTime = updatedTime;
        this.deletedStatus = deletedStatus;
        this.updator = updator;
        this.privilege = privilege;
        this.username = username;
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

    public String getUpdator() {
        return this.updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Integer getPrivilege() {
        return this.privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

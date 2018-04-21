package cn.idealismxxm.onlinejudge.domain.entity;
public class User implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;//主键
    private java.util.Date updatedTime;//更新时间
    private String password;//密码
    private String nickname;//昵称
    private String email;//邮箱
    private String username;//用户名
    public User() {
        super();
    }
    public User(Integer id,java.util.Date updatedTime,String password,String nickname,String email,String username) {
        super();
        this.id = id;
        this.updatedTime = updatedTime;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

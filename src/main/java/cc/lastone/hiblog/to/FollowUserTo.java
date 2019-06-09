package cc.lastone.hiblog.to;


public class FollowUserTo {
    // id  nickname  avatar
    private Integer id;
    private String nickname;
    private String avatar;
    // 是否关注   0未关注，1已关注
    private Integer followType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

    @Override
    public String toString() {
        return "FollowUserTo{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", followType=" + followType +
                '}';
    }
}

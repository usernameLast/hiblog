package cc.lastone.hiblog.to;


public class CollectArticleTo {
    // id  uid title nickname
    private Integer id;
    private Integer uid;
    private String title;
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "CollectArticleTo{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

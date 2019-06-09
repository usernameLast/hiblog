package cc.lastone.hiblog.domain;

import java.io.Serializable;

public class FollowPrimaryKey implements Serializable {
    private Integer uid;
    private Integer followId;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }
}

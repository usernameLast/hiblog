package cc.lastone.hiblog.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "hb_follow")
@IdClass(FollowPrimaryKey.class)
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "uid")
    private Integer uid;

    @Id
    @Column(name = "follow_id")
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
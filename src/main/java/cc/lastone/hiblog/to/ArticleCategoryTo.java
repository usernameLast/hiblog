package cc.lastone.hiblog.to;


import java.math.BigInteger;

public class ArticleCategoryTo {

    // id  name

    private Integer id;
    private String name;
    private BigInteger num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getNum() {
        return num;
    }

    public void setNum(BigInteger num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ArticleCategoryTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}

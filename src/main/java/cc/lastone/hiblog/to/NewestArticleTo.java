package cc.lastone.hiblog.to;


public class NewestArticleTo {

    // id  title

    private Integer id;
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NewestArticleTo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}

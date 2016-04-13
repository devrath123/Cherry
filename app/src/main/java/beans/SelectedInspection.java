package beans;

/**
 * Created by devrath.rathee on 3/14/2016.
 */
public class SelectedInspection {

    String title,desc,id,comment,quantity;

    public SelectedInspection(String title, String desc, String id, String comment, String quantity) {
        this.title = title;
        this.desc = desc;
        this.id = id;
        this.comment = comment;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

package application.knhash.ddnews;

/**
 * Created by Shashank on 14-Jul-16.
 */

public class ListItem {
    private String title, image, pubDate, fullDesc, shortDesc, id;

    public ListItem() {
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getPublishDate() {
        return pubDate;
    }

    public String getFullDesc() {
        return fullDesc;
    }

    public String getShortDesc () {
        return shortDesc;
    }

    public String getId() { return id; }

    public void setTitle(String name) {
        this.title = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPublishDate(String release) {
        this.pubDate = release;
    }

    public void setFullDesc(String rating) {
        this.fullDesc = rating;
    }

    public void setShortDesc(String overview) {
        this.shortDesc = overview;
    }

    public void setId(String id) { this.id = id; }
}

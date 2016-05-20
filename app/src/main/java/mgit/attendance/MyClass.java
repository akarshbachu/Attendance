package mgit.attendance;

/**
 * Created by bachu_000 on 05-03-2016.
 */
public class MyClass {
    String title,desc,present,absent;

    int img;

    public MyClass(String title, String desc,String present,String absent,int img) {
        this.title = title;
        this.desc = desc;
        this.present=present;
        this.absent=absent;
        this.img = img;
    }

    public String getPresent() {
        return present;
    }

    public String getAbsent() {
        return absent;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getImg() {
        return img;
    }
}

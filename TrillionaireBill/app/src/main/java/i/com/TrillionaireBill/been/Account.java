package i.com.TrillionaireBill.been;

import java.util.List;

/**
 * Created by Administrator on 2020/10/16.
 */

public class Account {

    private int picture;

    private String name;

    private String remake;

    private List<String> zname;

    public Account(int picture , String name , String remake , List<String> zname){
        this.picture = picture;
        this.name = name;
        this.remake = remake;
        this.zname = zname;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public List<String> getZname() {
        return zname;
    }

    public void setZname(List<String> zname) {
        this.zname = zname;
    }
}

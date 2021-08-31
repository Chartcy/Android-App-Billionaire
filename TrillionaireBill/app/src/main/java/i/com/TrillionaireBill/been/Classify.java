package i.com.TrillionaireBill.been;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by Administrator on 2020/10/19.
 */

//分类
@Entity
public class Classify {

    @PrimaryKey
    @NonNull
    private String id;
    private String stairTx;
    private String secondTx;
    private String merchantTx;
    private String memberTx;

    //一级分类
    @Ignore
    private String[] stair;
    //二级分类
    @Ignore
    private Map<String ,String[]> second;
    //商家
    @Ignore
    private String[] merchant;
    //成员
    @Ignore
    private String[] member;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStairTx() {
        return stairTx;
    }

    public void setStairTx(String stairTx) {
        this.stairTx = stairTx;
    }

    public String getSecondTx() {
        return secondTx;
    }

    public void setSecondTx(String secondTx) {
        this.secondTx = secondTx;
    }

    public String getMerchantTx() {
        return merchantTx;
    }

    public void setMerchantTx(String merchantTx) {
        this.merchantTx = merchantTx;
    }

    public String getMemberTx() {
        return memberTx;
    }

    public void setMemberTx(String memberTx) {
        this.memberTx = memberTx;
    }

    public String[] getMerchant() {
        return merchant;
    }

    public void setMerchant(String[] merchant) {
        this.merchant = merchant;
    }

    public String[] getMember() {
        return member;
    }

    public void setMember(String[] member) {
        this.member = member;
    }

    public Map<String, String[]> getSecond() {
        return second;
    }

    public void setSecond(Map<String, String[]> second) {
        this.second = second;
    }

    public String[] getStair() {
        return stair;
    }

    public void setStair(String[] stair) {
        this.stair = stair;
    }
}

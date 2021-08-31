package i.com.TrillionaireBill.been;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import i.com.TrillionaireBill.login.GestureView;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    private String account;

    private String member;

    private String password;

    private String gesture;

    @Ignore
    private  List<GestureView.GestureBean> listDatas;
    @Ignore
    private List<Account> accountList;

    public List<GestureView.GestureBean> getListDatas() {
        return listDatas;
    }

    public void setListDatas(List<GestureView.GestureBean> listDatas) {
        this.listDatas = listDatas;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture = gesture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public static class Account implements Parcelable {
        String name;

        double price;

        int icon;

        LinkedList<Account> accounts;

        String time;//以下字段流水，记账时使用

        String stair;

        String second;

        String merchant;

        String member;

        String selectAccount;
        //0为支出，1为收入
        int state;

        public Account(double price, String time, String stair, String second, String merchant, String member, String selectAccount, int state) {
            this.price = price;
            this.time = time;
            this.stair = stair;
            this.second = second;
            this.merchant = merchant;
            this.member = member;
            this.selectAccount = selectAccount;
            this.state = state;
        }

        protected Account(Parcel in) {
            name = in.readString();
            price = in.readDouble();
            icon = in.readInt();
            time = in.readString();
            stair = in.readString();
            second = in.readString();
            merchant = in.readString();
            member = in.readString();
            selectAccount = in.readString();
            state = in.readInt();
        }

        public static final Creator<Account> CREATOR = new Creator<Account>() {
            @Override
            public Account createFromParcel(Parcel in) {
                return new Account(in);
            }

            @Override
            public Account[] newArray(int size) {
                return new Account[size];
            }
        };

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStair() {
            return stair;
        }

        public void setStair(String stair) {
            this.stair = stair;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }

        public String getMerchant() {
            return merchant;
        }

        public void setMerchant(String merchant) {
            this.merchant = merchant;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getSelectAccount() {
            return selectAccount;
        }

        public void setSelectAccount(String selectAccount) {
            this.selectAccount = selectAccount;
        }

        public Account(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public Account(String name, double price, LinkedList<Account> accounts) {
            this.name = name;
            this.price = price;
            this.accounts = accounts;
        }

        public Account(String name, double price, LinkedList<Account> accounts , int icon) {
            this.name = name;
            this.price = price;
            this.accounts = accounts;
            this.icon = icon;
        }

        public Account(String name, double price, String time) {
            this.name = name;
            this.price = price;
            this.time = time;
        }


        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public LinkedList<Account> getAccounts() {
            return accounts;
        }

        public void setAccounts(LinkedList<Account> accounts) {
            this.accounts = accounts;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeDouble(price);
            dest.writeInt(icon);
            dest.writeString(time);
            dest.writeString(stair);
            dest.writeString(second);
            dest.writeString(merchant);
            dest.writeString(member);
            dest.writeString(selectAccount);
            dest.writeInt(state);
        }
    }

}

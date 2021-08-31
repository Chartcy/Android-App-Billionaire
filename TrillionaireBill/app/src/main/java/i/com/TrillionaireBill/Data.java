package i.com.TrillionaireBill;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import i.com.TrillionaireBill.been.Classify;
import i.com.TrillionaireBill.been.MyDatabase;
import i.com.TrillionaireBill.been.User;

public class Data {

    public static void initData(Context context) {
        mUserData = MyDatabase.getInstance(context).user().getUserById("1");
        mClassify = MyDatabase.getInstance(context).classifyDao().getClassifyById("1");

        if(mUserData != null){
            initUser();
        }

        if(mClassify != null){
            initClassify();
        }
    }

    public static User mUserData;

    public static Classify mClassify;

    public static void setmUserData(User mUserData) {
        Data.mUserData = mUserData;
    }
    //更新用户信息
    public static void upUser(Context context) {
        String s = new Gson().toJson(mUserData.getAccountList());
        mUserData.setAccount(s);
        MyDatabase.getInstance(context).user().updateUser(mUserData);
    }
    //更新分类信息
    public static void upClassify(Context context) {
        mClassify.setMemberTx(new Gson().toJson(mClassify.getMember()));
        mClassify.setMerchantTx(new Gson().toJson(mClassify.getMerchant()));
        mClassify.setSecondTx(new Gson().toJson(mClassify.getSecond()));
        mClassify.setStairTx(new Gson().toJson(mClassify.getStair()));
        MyDatabase.getInstance(context).classifyDao().updateClassify(mClassify);
    }

    public static void initClassify(Context context) {
        mClassify.setMemberTx(new Gson().toJson(mClassify.getMember()));
        mClassify.setMerchantTx(new Gson().toJson(mClassify.getMerchant()));
        mClassify.setSecondTx(new Gson().toJson(mClassify.getSecond()));
        mClassify.setStairTx(new Gson().toJson(mClassify.getStair()));
        MyDatabase.getInstance(context).classifyDao().insertClassify(mClassify);
    }

    private static void initUser(){
        if(!TextUtils.isEmpty(Data.mUserData.getAccount())){
            Type type = new TypeToken<List<User.Account>>(){}.getType();
            List<User.Account> list = new Gson().fromJson(Data.mUserData.getAccount() , type);
            Data.mUserData.setAccountList(list);
        }
    }

    private static void initClassify() {
        Type type = new TypeToken<String[]>() {
        }.getType();
        if (!TextUtils.isEmpty(mClassify.getMemberTx())) {
            String[] member = new Gson().fromJson(mClassify.getMemberTx(), type);
            mClassify.setMember(member);
        }
        if (!TextUtils.isEmpty(mClassify.getMerchantTx())) {
            String[] member = new Gson().fromJson(mClassify.getMerchantTx(), type);
            mClassify.setMerchant(member);
        }
        if (!TextUtils.isEmpty(mClassify.getSecondTx())) {
            Map<String, String[]> member = new Gson().fromJson(mClassify.getSecondTx(), new TypeToken<Map<String, String[]>>() {
            }.getType());
            mClassify.setSecond(member);
        }
        if (!TextUtils.isEmpty(mClassify.getStairTx())) {
            String[] member = new Gson().fromJson(mClassify.getStairTx(), type);
            mClassify.setStair(member);
        }
    }
}

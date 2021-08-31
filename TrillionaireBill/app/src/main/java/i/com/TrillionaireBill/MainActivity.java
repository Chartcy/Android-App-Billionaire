package i.com.TrillionaireBill;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.account.AccountFragment;
import i.com.TrillionaireBill.been.Classify;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.bookkeeping.BookKeepingActivity;
import i.com.TrillionaireBill.chart.ChartFragment;
import i.com.TrillionaireBill.flowingwater.FlowingWaterFragment;
import i.com.TrillionaireBill.settting.SettingFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.running_water)
    TextView runningWater;
    @BindView(R.id.chart)
    TextView chart;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.add)
    TextView add;

    private AccountFragment mAccountFragment;
    private FlowingWaterFragment flowingWaterFragment;
    private ChartFragment chartFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Data.initData(this);
        //初始化
        if (TextUtils.isEmpty(Data.mUserData.getAccount())) {
            List<User.Account> accountList = new LinkedList<>();
            LinkedList<User.Account> cash = new LinkedList<>();
            LinkedList<User.Account> creditCard = new LinkedList<>();
            LinkedList<User.Account> finance = new LinkedList<>();
            LinkedList<User.Account> virtuality = new LinkedList<>();

            cash.add(new User.Account("现金", 0.00));
            creditCard.add(new User.Account("信用卡", 0.00));
            finance.add(new User.Account("银行卡", 0.00));
            finance.add(new User.Account("校园卡", 0.00));
            virtuality.add(new User.Account("微信钱包", 0.00));
            virtuality.add(new User.Account("支付宝", 0.00));
            virtuality.add(new User.Account("公交卡", 0.00));
            virtuality.add(new User.Account("饭卡", 0.00));

            accountList.add(new User.Account("现金账户", 0.00, cash, R.drawable.cash2_icon));
            accountList.add(new User.Account("信用卡账户", 0.00, creditCard, R.drawable.account9_icon));
            accountList.add(new User.Account("金融账户", 0.00, finance, R.drawable.account5_icon));
            accountList.add(new User.Account("虚拟账户", 0.00, virtuality, R.drawable.account2_icon));
            Data.mUserData.setAccountList(accountList);
            Data.upUser(this);
            Data.mUserData.setAccount(new Gson().toJson(accountList));
        }
        if (Data.mClassify == null) {
            Data.mClassify = new Classify();
            Data.mClassify.setId("1");
            if (Data.mClassify.getStair() == null || Data.mClassify.getStair().length == 0) {
                Data.mClassify.setStair(new String[]{"食品酒水", "居家物业", "衣服饰品", "行车交通", "交流通讯", "休闲娱乐", "学习进修"});
            }
            if (Data.mClassify.getMerchant() == null || Data.mClassify.getMerchant().length == 0) {
                Data.mClassify.setMerchant(new String[]{"无商家/地点", "超市", "商场", "淘宝", "京东", "饭堂", "银行", "公交", "其它"});
            }
            if (Data.mClassify.getMember() == null || Data.mClassify.getMember().length == 0) {
                Data.mClassify.setMember(new String[]{"无成员", "本人", "妻子", "丈夫", "子女", "父母", "家庭公用"});
            }
            if (Data.mClassify.getSecond() == null || Data.mClassify.getSecond().size() == 0) {
                Map<String, String[]> map = new LinkedHashMap<>();
                map.put("食品酒水", new String[]{"早午晚餐", "烟酒茶", "水果零食", "午餐"});
                map.put("居家物业", new String[]{"日常用品", "水电煤气", "房租", "物业管理", "维修保养"});
                map.put("衣服饰品", new String[]{"衣服裤子", "鞋帽包包", "化妆饰品"});
                map.put("行车交通", new String[]{"公共交通", "打车租车", "私家车费用"});
                map.put("交流通讯", new String[]{"座机费", "手机费", "上网费", "邮寄费"});
                map.put("休闲娱乐", new String[]{"运动健身", "休闲玩乐", "宠物宝贝", "旅游度假"});
                map.put("学习进修", new String[]{"数据装备", "书报杂志", "培训进修"});
                Data.mClassify.setSecond(map);
            }
            Data.initClassify(MainActivity.this);
        }
        mAccountFragment = new AccountFragment();
        flowingWaterFragment = new FlowingWaterFragment();
        chartFragment = new ChartFragment();
        settingFragment = new SettingFragment();
        replaceFragment(mAccountFragment);
        //五种页面跳转
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(mAccountFragment);
            }
        });

        runningWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(flowingWaterFragment);
            }
        });

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(chartFragment);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(settingFragment);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookKeepingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentlayout, fragment, "")
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAccountFragment != null) {
            mAccountFragment.notifyDataSetChanged();
        }
        if (flowingWaterFragment != null) {
            flowingWaterFragment.notifyDataSetChanged();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.CHINESE);
            Date curDate = new Date(System.currentTimeMillis());
            int year = Integer.parseInt(sdf.format(curDate));
            flowingWaterFragment.setTime(year);
        }
        if (chartFragment != null) {
            chartFragment.notifyDataSetChanged();
        }
    }
}

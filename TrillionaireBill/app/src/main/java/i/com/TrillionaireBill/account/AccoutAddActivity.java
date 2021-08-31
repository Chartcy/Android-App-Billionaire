package i.com.TrillionaireBill.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.been.Account;
import i.com.TrillionaireBill.R;


public class AccoutAddActivity extends Activity {

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.expandable)
    ExpandableListView expandable;

    private List<Account> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);
        ButterKnife.bind(this);
        initData();
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AccoutAddAdapter addAdapter = new AccoutAddAdapter(data , this);
        expandable.setAdapter(addAdapter);
        addAdapter.setListener(new AccoutAddAdapter.AccoutAddAListener() {
            @Override
            public void onClick(String name , int groupPosition) {
                Intent intent = new Intent(AccoutAddActivity.this , AccountAddDetailsActivity.class);
                intent.putExtra("name" , name);
                intent.putExtra("position" , groupPosition);
                startActivity(intent);
            }
        });
    }

    private void initData(){
        data = new ArrayList<>();
        data.add(new Account(R.drawable.cash1_icon, "现金" , "" , null));
        data.add(new Account(R.drawable.account8_icon, "信用卡" , "蚂蚁花呗、京东白条也可以记在这里哦" , null));
        List<String> list = new ArrayList<>();
        list.add("储蓄卡/借记卡");
        list.add("存折");
        data.add(new Account(R.drawable.account1_icon, "储蓄卡/借记卡、存折" , "" , list));
        List<String> list1 = new ArrayList<>();
        list1.add("在线支付");
        list1.add("现金券");
        list1.add("储值卡");
        data.add(new Account(R.drawable.account6_icon, "虚拟账户" , "支付宝、微信钱包、饭卡、公交卡" , list1));
    }

}

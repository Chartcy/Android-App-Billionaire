package i.com.TrillionaireBill.flowingwater.account;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.flowingwater.FlowingWaterAdapter;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.R;


public class AccountItemActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_item);
        ButterKnife.bind(this);

        String name = getIntent().getStringExtra("name");
        String account = getIntent().getStringExtra("accountList");

        back.setText("＜ ".concat(name).concat("流水"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Type type = new TypeToken<List<User.Account>>() {
        }.getType();

        List<User.Account> accountList = new Gson().fromJson(account , type);
        FlowingWaterAdapter adapter1 = new FlowingWaterAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter1);
        adapter1.setData(accountList);

    }
}

package i.com.TrillionaireBill.account;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.TimeUtils;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.flowingwater.FlowingWaterAdapter;

public class AccountRunningWaterActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.last)
    ImageView last;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.button4)
    TextView button4;
    @BindView(R.id.button5)
    TextView button5;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private long timeDifference = 0;
    private Date startDate;
    private Date endDate;

    private ArrayList<User.Account> totalData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_running_water);
        ButterKnife.bind(this);


        back.setText("＜ ".concat(getIntent().getStringExtra("name")).concat("流水"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button5.setTextColor(0xff808080);
        button4.setTextColor(0xffF7502F);
        startDate = TimeUtils.getTimesMonthmorning();
        endDate = TimeUtils.getTimesMonthnight();
        timeDifference = startDate.getTime() - endDate.getTime();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        totalData = getIntent().getParcelableArrayListExtra("data");
        time.setText(sdf.format(startDate).concat(" ~ ").concat(sdf.format(endDate)));
        final FlowingWaterAdapter adapter = new FlowingWaterAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        if(totalData != null){
            adapter.setData(setTimeData());
        }

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = new Date(startDate.getTime() + timeDifference);
                endDate = new Date(endDate.getTime() + timeDifference);
                time.setText(sdf.format(startDate).concat(" ~ ").concat(sdf.format(endDate)));
                adapter.setData(setTimeData());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = new Date(startDate.getTime() - timeDifference);
                endDate = new Date(endDate.getTime() - timeDifference);
                time.setText(sdf.format(startDate).concat(" ~ ").concat(sdf.format(endDate)));
                adapter.setData(setTimeData());
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button5.setTextColor(0xff808080);
                button4.setTextColor(0xffF7502F);
                startDate = TimeUtils.getTimesMonthmorning();
                endDate = TimeUtils.getTimesMonthnight();
                timeDifference = startDate.getTime() - endDate.getTime();
                time.setText(sdf.format(startDate).concat(" ~ ").concat(sdf.format(endDate)));
                adapter.setData(setTimeData());
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button4.setTextColor(0xff808080);
                button5.setTextColor(0xffF7502F);
                startDate = TimeUtils.getCurrentYearStartTime();
                endDate = TimeUtils.getCurrentYearEndTime();
                timeDifference = startDate.getTime() - endDate.getTime();
                time.setText(sdf.format(startDate).concat(" ~ ").concat(sdf.format(endDate)));
                adapter.setData(setTimeData());
            }
        });

    }

    private List<User.Account> setTimeData(){
        List<User.Account> data = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟
        for(User.Account account :totalData){
            Date date = null;
            try {
                date = sdf.parse(account.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (startDate.getTime() < date.getTime() && endDate.getTime() > date.getTime()) {
                data.add(account);
            }
        }
        return data;
    }

}

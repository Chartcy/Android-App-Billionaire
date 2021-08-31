package i.com.TrillionaireBill.addclassify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.TaskOutAdapter;


public class AddclassificationActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classification);
        ButterKnife.bind(this);

        back.setText("＜ ".concat("新建支出分类"));
        icon.setBackgroundResource(R.drawable.add_icon);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddclassificationActivity.this, AddClassifcationOneActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String, String[]> second = Data.mClassify.getSecond();
        TaskOutAdapter mTaskOutAdapter = new TaskOutAdapter(null, null);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mTaskOutAdapter);
        List<AbstractFlexibleItem> list = new ArrayList();
        //展示所有一级分类及其二级分类
        for (int i = 0; i < second.size(); i++) {
            Object[] objects = second.keySet().toArray();
            String[] strings = second.get(objects[i]);
            ClassifcationGroup fuItem = new ClassifcationGroup(second);
            ClassifcationItem subclassItem = new ClassifcationItem(fuItem, strings, this, objects[i].toString());
            fuItem.addSubItem(subclassItem);
            list.add(fuItem);
        }
        mTaskOutAdapter.updateDataSet(list);
    }
}

package i.com.TrillionaireBill.addclassify;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;

//新建二级分类
public class AddClassifcationTwoActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.button3)
    Button button3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classifcation);
        ButterKnife.bind(this);

        back.setText("＜ ".concat("新建二级分类"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button3.setText("完成");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText3.getText().toString())) {
                    Toast.makeText(AddClassifcationTwoActivity.this, "请输入分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = getIntent().getStringExtra("name");
                Map<String, String[]> second = Data.mClassify.getSecond();
                //得到一级分类名
                String[] strings = second.get(name);
                if (strings == null) {
                    //新建一级分类
                    strings = new String[]{editText3.getText().toString()};
                    String[] stair = Data.mClassify.getStair();
                    List<String> stairList = new LinkedList<>(Arrays.asList(stair));
                    if (!stairList.contains(name)) {
                        stairList.add(name);
                        Data.mClassify.setStair(stairList.toArray(new String[stairList.size()]));
                    }
                } else {
                    List<String> stringList = new LinkedList<>(Arrays.asList(strings));
                    stringList.add(editText3.getText().toString());
                    strings = stringList.toArray(new String[stringList.size()]);
                }
                second.put(name, strings);
                Data.mClassify.setSecond(second);
                Data.upClassify(AddClassifcationTwoActivity.this);
                Toast.makeText(AddClassifcationTwoActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}

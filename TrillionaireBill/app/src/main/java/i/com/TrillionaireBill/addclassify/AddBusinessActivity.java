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

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;


//新建商家
public class AddBusinessActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.button3)
    Button button3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        ButterKnife.bind(this);

        back.setText("＜ ".concat("新建商家/地点"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText3.getText().toString())) {
                    Toast.makeText(AddBusinessActivity.this, "请输入商家/地点", Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] second = Data.mClassify.getMerchant();

                List<String> stringList = new LinkedList<>(Arrays.asList(second));
                stringList.set(stringList.size() - 1, editText3.getText().toString());
                stringList.add("");
                second = stringList.toArray(new String[stringList.size()]);

                Data.mClassify.setMerchant(second);
                Data.upClassify(AddBusinessActivity.this);
                Toast.makeText(AddBusinessActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

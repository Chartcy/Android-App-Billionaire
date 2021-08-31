package i.com.TrillionaireBill.addclassify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.R;

//新建一级分类
public class AddClassifcationOneActivity extends Activity {

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

        back.setText("＜ ".concat("新建一级分类"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button3.setText("下一步");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText3.getText().toString())) {
                    Toast.makeText(AddClassifcationOneActivity.this, "请输入分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(AddClassifcationOneActivity.this, AddClassifcationTwoActivity.class);
                intent.putExtra("name", editText3.getText().toString());
                startActivity(intent);
                finish();
            }
        });

    }
}

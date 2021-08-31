package i.com.TrillionaireBill.settting;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;

public class EditPasswordActivity extends Activity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.button5)
    TextView button5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        ButterKnife.bind(this);

        title.setText("密码修改");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText2.getText().toString())) {
                    Toast.makeText(EditPasswordActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editText3.getText().toString())) {
                    Toast.makeText(EditPasswordActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!editText3.getText().toString().equals(editText2.getText().toString())) {
                    Toast.makeText(EditPasswordActivity.this, "两次输入的密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                Data.mUserData.setPassword(editText2.getText().toString());
                Data.upUser(EditPasswordActivity.this);

                Toast.makeText(EditPasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

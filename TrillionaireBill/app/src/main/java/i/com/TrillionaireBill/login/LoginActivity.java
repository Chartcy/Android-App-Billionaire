package i.com.TrillionaireBill.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.been.MyDatabase;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.been.UserDao;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.MainActivity;
import i.com.TrillionaireBill.R;


public class LoginActivity extends Activity {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.textView18)
    TextView textView18;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        UserDao user = MyDatabase.getInstance(this).user();
        final String imei = "1";
        Data.mUserData = user.getUserById(imei);
        if (Data.mUserData == null || TextUtils.isEmpty(Data.mUserData.getPassword())) {
            editText.setHint("请设置密码");
            textView18.setVisibility(View.GONE);
            button2.setText("下一步");
        } else {
            editText.setHint("请输入密码");
            textView18.setVisibility(View.VISIBLE);
            button2.setText("登录");
        }

        textView18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SetGestureActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText())) {
                    Toast.makeText(LoginActivity.this, "密码不可为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Data.mUserData == null || TextUtils.isEmpty(Data.mUserData.getPassword())) {
                    Data.mUserData = new User();
                    Data.mUserData.setId(imei);
                    Data.mUserData.setPassword(editText.getText().toString());
                    Intent intent = new Intent(LoginActivity.this, SetGestureActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else {
                    if (Data.mUserData.getPassword().equals(editText.getText().toString())) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}

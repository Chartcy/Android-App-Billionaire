package i.com.TrillionaireBill.account;

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

import com.lsh.library.BankNumEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.R;

/**
 * Created by Administrator on 2020/10/17.
 */

public class AccountAddDetailsActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.edit1)
    BankNumEditText edit1;
    @BindView(R.id.textView6)
    Button textView6;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.group)
    android.support.constraint.Group group;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accout_add_details);
        ButterKnife.bind(this);

        String breakName = getIntent().getStringExtra("name");
        final int position = getIntent().getIntExtra("position", -1);
        back.setText("＜ ".concat("新建").concat(breakName).concat("账户"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (position == 1 || position == 2) {
            group.setVisibility(View.VISIBLE);
        } else {
            group.setVisibility(View.GONE);
        }

        edit1.setBankNameListener(new BankNumEditText.BankNameListener() {
            @Override
            public void success(String s) {
                textView8.setText(s);
            }

            @Override
            public void failure() {
                textView8.setText("");
            }
        });

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.Account account = Data.mUserData.getAccountList().get(position);
                LinkedList<User.Account> accounts = account.getAccounts();
                double price = 0.00;
                if (position == 1 || position == 2) {
                    if (TextUtils.isEmpty(edit1.getText())) {
                        Toast.makeText(AccountAddDetailsActivity.this, "请输入卡号", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(textView8.getText())) {
                        Toast.makeText(AccountAddDetailsActivity.this, "请输入正确的卡号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (TextUtils.isEmpty(name.getText())) {
                    Toast.makeText(AccountAddDetailsActivity.this, "请输入账户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(editText2.getText())) {
                    price = Double.parseDouble(editText2.getText().toString());
                }
                User.Account account1 = new User.Account(name.getText().toString(), price);
                if(price != 0){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date curDate = new Date(System.currentTimeMillis());
                    LinkedList<User.Account> list = new LinkedList<>();
                    list.add(new User.Account(price, formatter.format(curDate), "余额变更"
                            , "", "", "",name.getText().toString(), 1));
                    account1.setAccounts(list);
                }
                accounts.add(account1);
                account.setPrice(account.getPrice() + price);
                Toast.makeText(AccountAddDetailsActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                Data.upUser(AccountAddDetailsActivity.this);
                finish();
            }
        });

    }
}

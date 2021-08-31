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

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.R;

public class EditAccountNameActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.textView6)
    Button textView6;
    @BindView(R.id.old_name)
    TextView oldName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_name);
        ButterKnife.bind(this);

        back.setText("＜ ".concat("修改账户名"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final String nameTx = getIntent().getStringExtra("name");
        oldName.setText("(".concat(nameTx).concat(")"));
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText())){
                    Toast.makeText(EditAccountNameActivity.this , "请输入账户名称" , Toast.LENGTH_SHORT).show();
                    return;
                }

                List<User.Account> accountList = Data.mUserData.getAccountList();
                String group = getIntent().getStringExtra("group");
                boolean isExistence = false;
                for(User.Account account : accountList){
                    if(account.getName().equals(group)){
                        LinkedList<User.Account> accounts = account.getAccounts();
                        for(int i = 0 ; i < accounts.size() ; i++){
                            User.Account account1 = accounts.get(i);
                            if(account1.getName().equals(name.getText().toString())){
                                isExistence = true;
                                break;
                            }
                        }
                    }
                }

                if(!isExistence){
                    for(User.Account account : accountList){
                        if(account.getName().equals(group)){
                            LinkedList<User.Account> accounts = account.getAccounts();
                            for(int i = 0 ; i < accounts.size() ; i++){
                                User.Account account1 = accounts.get(i);
                                if(account1.getName().equals(nameTx)){
                                    account1.setName(name.getText().toString());
                                    if(account1.getAccounts() != null){
                                        for(User.Account list : account1.getAccounts()){
                                            list.setSecond(list.getSecond().replace(nameTx , name.getText().toString()));
                                            list.setSelectAccount(list.getSelectAccount().replace(nameTx , name.getText().toString()));
                                            Toast.makeText(EditAccountNameActivity.this , "修改成功" , Toast.LENGTH_SHORT).show();
                                            Data.upUser(EditAccountNameActivity.this);
                                            finish();
                                        }
                                    }
                                }
                            }
                        }
                    }

                }else{
                    Toast.makeText(EditAccountNameActivity.this , "已有该账户名称，请重新输入" , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

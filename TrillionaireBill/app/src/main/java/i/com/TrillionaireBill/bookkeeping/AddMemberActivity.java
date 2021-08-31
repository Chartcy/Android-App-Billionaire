package i.com.TrillionaireBill.bookkeeping;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;

public class AddMemberActivity extends Activity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.button3)
    Button button3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        ButterKnife.bind(this);
        back.setText("＜ ".concat("新建成员"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList(Arrays.asList(Data.mClassify.getMember()));
                if (list.contains(editText3.getText().toString())) {
                    Toast.makeText(AddMemberActivity.this, "已有该成员", Toast.LENGTH_SHORT).show();
                    return;
                }
                list.add(editText3.getText().toString());
                String[] toBeStored = list.toArray(new String[list.size()]);
                Data.mClassify.setMember(toBeStored);
                Data.upClassify(AddMemberActivity.this);
                finish();
            }
        });

    }
}

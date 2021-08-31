package i.com.TrillionaireBill.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.List;

import i.com.TrillionaireBill.been.MyDatabase;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.MainActivity;
import i.com.TrillionaireBill.R;

public class SetGestureActivity extends Activity implements GestureView.GestureCallBack {
    private GestureView gestureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gesture);
        gestureView = (GestureView) findViewById(R.id.gesture);
        gestureView.setGestureCallBack(this);
        if (getIntent().getIntExtra("type", 0) == 1 || getIntent().getIntExtra("type", 0) == 3) {
            //不调用这个方法会造成第二次启动程序直接进入手势识别而不是手势设置
            gestureView.clearCache();
        }
        gestureView.setMinPointNums(5);
    }

    @Override
    public void gestureVerifySuccessListener(int stateFlag, List<GestureView.GestureBean> data, String message, boolean success) {
        if (stateFlag == GestureView.STATE_LOGIN) {
            int type = getIntent().getIntExtra("type", 0);
            if (type == 2) {
                startActivity(new Intent(SetGestureActivity.this, MainActivity.class));
            } else {

                if (type != 3) {
                    Intent intent = new Intent(SetGestureActivity.this, Main2Activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SetGestureActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                }
                //设置手势密码
                Data.mUserData.setListDatas(data);
                Data.mUserData.setGesture(new Gson().toJson(data));
                //把用户放入数据库中
                MyDatabase.getInstance(SetGestureActivity.this).user().insertUser(Data.mUserData);
            }

            this.finish();
        }
    }
}
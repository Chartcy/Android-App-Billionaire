package i.com.TrillionaireBill.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import i.com.TrillionaireBill.MainActivity;
import i.com.TrillionaireBill.R;

public class Main2Activity extends Activity implements GestureView.GestureCallBack {

    private GestureView gestureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gestureView = (GestureView) findViewById(R.id.gesture1);
        gestureView.setGestureCallBack(this);
    }

    @Override
    public void gestureVerifySuccessListener(int stateFlag, List<GestureView.GestureBean> data, String message, boolean success) {
        if (success) {
            startActivity(new Intent(Main2Activity.this, MainActivity.class));
            this.finish();
        }
    }
}

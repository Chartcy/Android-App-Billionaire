package i.com.TrillionaireBill.settting;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.been.MyDatabase;
import i.com.TrillionaireBill.login.LoginActivity;
import i.com.TrillionaireBill.login.SetGestureActivity;

public class SettingFragment extends Fragment {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.textView17)
    TextView textView17;
    @BindView(R.id.textView20)
    TextView textView20;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);

        Picasso.with(getActivity())
                .load(R.drawable.profile_photo_icon)
                .transform(new CircleTransform())
                .into(imageView);

        textView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntent(EditPasswordActivity.class);
            }
        });

        textView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , SetGestureActivity.class);
                intent.putExtra("type" , 3);
                startActivity(intent);
            }
        });

        textView17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空数据
                Data.mUserData = null;
                Data.mClassify = null;
                MyDatabase.getInstance(getActivity()).user().deleteUser("1");
                MyDatabase.getInstance(getActivity()).classifyDao().deleteClassify("1");
                Intent intent = new Intent(getActivity() , LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity() , "清空数据成功" , Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

        textView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退回到登入界面
                Intent intent = new Intent(getActivity() , LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void getIntent(Class c){
        Intent intent = new Intent(getActivity() , c);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

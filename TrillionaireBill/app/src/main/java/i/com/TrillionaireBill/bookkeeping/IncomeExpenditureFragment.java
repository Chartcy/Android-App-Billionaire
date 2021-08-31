package i.com.TrillionaireBill.bookkeeping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.DateDialog;
import i.com.TrillionaireBill.R;

public class IncomeExpenditureFragment extends Fragment {

    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.classification_name)
    TextView classificationName;
    @BindView(R.id.classification)
    ConstraintLayout classification;
    @BindView(R.id.account_name)
    TextView accountName;
    @BindView(R.id.account)
    ConstraintLayout account;
    @BindView(R.id.time_name)
    TextView timeName;
    @BindView(R.id.time)
    ConstraintLayout time;
    @BindView(R.id.member_name)
    TextView memberName;
    @BindView(R.id.member)
    ConstraintLayout member;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.business)
    ConstraintLayout business;
    Unbinder unbinder;

    public static IncomeExpenditureFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        IncomeExpenditureFragment fragment = new IncomeExpenditureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments().getInt("type") == 0) {
            money.setTextColor(0xff15BA88);
            view1.setBackgroundColor(0xff15BA88);
        } else {
            money.setTextColor(0xffF1533A);
            view1.setBackgroundColor(0xffF1533A);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        timeName.setText(formatter.format(curDate));
        return view;
    }

    public User.Account getData() {
        double price = TextUtils.isEmpty(money.getText().toString()) ? 0.00 : Double.parseDouble(money.getText().toString());

        if (TextUtils.isEmpty(classificationName.getText())) {
            Toast.makeText(getActivity(), "请选择分类", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (TextUtils.isEmpty(accountName.getText())) {
            Toast.makeText(getActivity(), "请选择账户", Toast.LENGTH_SHORT).show();
            return null;
        }

        String[] split = classificationName.getText().toString().split(" > ");
        String stair = split[0];
        String second = split[1];

        money.setText("");

        return new User.Account(price, timeName.getText().toString(), stair , second
                , businessName.getText().toString(), memberName.getText().toString(), accountName.getText().toString(), getArguments().getInt("type"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.classification, R.id.account, R.id.time, R.id.member, R.id.business})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.classification:
                showDialog(LinkageDialog.CLASSIFY_TYPE, classificationName);
                break;
            case R.id.account:
                showDialog(LinkageDialog.ACCOUNT_TYPE, accountName);
                break;
            case R.id.member:
                showDialog(LinkageDialog.MEMBER_TYPE, memberName);
                break;
            case R.id.business:
                showDialog(LinkageDialog.MERCHANT_TYPE, businessName);
                break;
            case R.id.time:
                DateDialog dateDialog = DateDialog.newInstance(DateDialog.SHOW_TIME, "");
                dateDialog.show(getActivity().getFragmentManager(), "");
                dateDialog.setDatePickerListener(new DateDialog.DateListener() {
                    @Override
                    public void dateListener(String date) {
                        timeName.setText(date);
                    }
                });
                break;
        }
    }

    private void showDialog(final int type, final TextView name) {
        //实例化Dialog1
        final LinkageDialog dialog1 = LinkageDialog.newInstance(type);
        dialog1.setCancelable(true);
        dialog1.show(getActivity().getFragmentManager(), "");
        dialog1.setClicklistener(new LinkageDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                if (type == LinkageDialog.CLASSIFY_TYPE) {
                    name.setText(dialog1.getProvince().concat(" > ").concat(dialog1.getCity()));
                } else {
                    name.setText(dialog1.getCity());
                }

                dialog1.dismiss();

            }

            @Override
            public void doCancel() {
            }
        });
    }

}

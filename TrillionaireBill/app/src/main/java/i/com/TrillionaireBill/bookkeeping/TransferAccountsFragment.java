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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.DateDialog;
import i.com.TrillionaireBill.R;

import static i.com.TrillionaireBill.Data.mUserData;

public class TransferAccountsFragment extends Fragment {

    @BindView(R.id.money)
    EditText money;
    @BindView(R.id.account_name)
    TextView accountName;
    @BindView(R.id.account)
    ConstraintLayout account;
    @BindView(R.id.member_name)
    TextView memberName;
    @BindView(R.id.member)
    ConstraintLayout member;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.business)
    ConstraintLayout business;
    Unbinder unbinder;
    @BindView(R.id.time)
    ConstraintLayout time;
    @BindView(R.id.time_name)
    TextView timeName;
    @BindView(R.id.account_r_name)
    TextView accountRName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer_accounts, container, false);
        unbinder = ButterKnife.bind(this, view);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());
        timeName.setText(formatter.format(curDate));
        return view;
    }

    @OnClick({R.id.account_r_name,R.id.account_name, R.id.time, R.id.member, R.id.business})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_r_name:
                showDialog(LinkageDialog.ACCOUNT_TYPE, accountRName , 1);
                break;
            case R.id.account_name:
                showDialog(LinkageDialog.ACCOUNT_TYPE, accountName , 1);
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
        showDialog(type,name , 0);
    }

    private void showDialog(final int type, final TextView name , int showPrice) {
        //实例化Dialog1
        final LinkageDialog dialog1 = LinkageDialog.newInstance(type , showPrice);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getData() {
        double price = TextUtils.isEmpty(money.getText().toString()) ? 0.00 : Double.parseDouble(money.getText().toString());
        if (TextUtils.isEmpty(accountName.getText())) {
            Toast.makeText(getActivity(), "请选择转出账户", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(accountRName.getText())) {
            Toast.makeText(getActivity(), "请选择转入账户", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = mUserData;
        List<User.Account> accountList = user.getAccountList();

        Map<String , String[]> map = new LinkedHashMap<>();
        String[] s = new String[accountList.size() + 2];
        for(int i = 0 ; i < s.length ; i++){
            s[i] = "";
        }
        for (int i = 0 ; i < accountList.size() ; i++) {
            s[i + 1] = accountList.get(i).getName();
            LinkedList<User.Account> accounts1 = accountList.get(i).getAccounts();
            String[] accounts = new String[accounts1.size()];

            for(int j = 0 ; j < accounts1.size() ; j++){
                User.Account account = accounts1.get(j);
                accounts[j] = account.getName();
            }
            map.put(s[i] , accounts );
        }

        for (int i = 0; i < accountList.size(); i++) {
            User.Account stair = accountList.get(i);
            for (int j = 0; j < stair.getAccounts().size(); j++) {
                User.Account second = stair.getAccounts().get(j);
                if (accountName.getText().toString().contains(second.getName())) {
                    if (second.getAccounts() == null) {
                        second.setAccounts(new LinkedList<User.Account>());
                    }
                    User.Account data = new User.Account(price, timeName.getText().toString(), ""
                            , accountName.getText().toString().concat("转账到").concat(accountRName.getText().toString())
                            , businessName.getText().toString(), memberName.getText().toString(), accountName.getText().toString(), 0);
                    second.getAccounts().add(data);
                    second.setPrice(second.getPrice() - data.getPrice());
                    stair.getAccounts().set(j, second);
                    accountList.set(i, stair);
                    mUserData.setAccountList(accountList);
                }else if(accountRName.getText().toString().contains(second.getName())){
                    if (second.getAccounts() == null) {
                        second.setAccounts(new LinkedList<User.Account>());
                    }
                    User.Account data = new User.Account(price, timeName.getText().toString(), ""
                            , accountName.getText().toString().concat("转账到").concat(accountRName.getText().toString())
                            , businessName.getText().toString(), memberName.getText().toString(), accountName.getText().toString(), 1);
                    second.getAccounts().add(data);
                    second.setPrice(second.getPrice() + data.getPrice());
                    stair.getAccounts().set(j, second);
                    accountList.set(i, stair);
                    mUserData.setAccountList(accountList);
                }
            }
        }
        money.setText("");
    }
}

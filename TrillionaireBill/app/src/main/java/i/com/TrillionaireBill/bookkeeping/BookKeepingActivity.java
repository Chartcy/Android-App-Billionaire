package i.com.TrillionaireBill.bookkeeping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;

public class BookKeepingActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.preservation)
    Button preservation;
    @BindView(R.id.button)
    Button button;

    private Fragment selectFragment;

    private IncomeExpenditureFragment expenditureFragment;
    private IncomeExpenditureFragment incomeFragment;
    private TransferAccountsFragment transferAccountsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_keeping);
        ButterKnife.bind(this);

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        expenditureFragment = IncomeExpenditureFragment.newInstance(0);
        incomeFragment = IncomeExpenditureFragment.newInstance(1);
        transferAccountsFragment = new TransferAccountsFragment();

        fragmentList.add(expenditureFragment);
        fragmentList.add(incomeFragment);
        fragmentList.add(transferAccountsFragment);

        titleList.add("支出");
        titleList.add("收入");
        titleList.add("转账");

        ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(0);
        tablayout.setupWithViewPager(viewpager);
        selectFragment = expenditureFragment;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        preservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookKeeping();
                finish();
            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookKeeping();
            }
        });

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            //判断活动行为
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0: {
                        selectFragment = expenditureFragment;
                        break;
                    }

                    case 1: {
                        selectFragment = incomeFragment;
                        break;
                    }

                    case 2: {
                        selectFragment = transferAccountsFragment;
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void bookKeeping() {
        User mUserData = Data.mUserData;
        List<User.Account> accountList = mUserData.getAccountList();
        if (selectFragment == expenditureFragment) {
            User.Account data = expenditureFragment.getData();
            if (data == null) return;
            for (int i = 0; i < accountList.size(); i++) {
                User.Account stair = accountList.get(i);
                for (int j = 0; j < stair.getAccounts().size(); j++) {
                    User.Account second = stair.getAccounts().get(j);
                    if (data.getSelectAccount().contains(second.getName())) {
                        if (second.getAccounts() == null) {
                            second.setAccounts(new LinkedList<User.Account>());
                        }
                        second.getAccounts().add(data);
                        stair.getAccounts().set(j, second);
                        accountList.set(i, stair);
                        mUserData.setAccountList(accountList);
                        break;
                    }
                }
            }
        } else if (selectFragment == incomeFragment) {
            User.Account data = incomeFragment.getData();
            if (data == null) return;
            for (int i = 0; i < accountList.size(); i++) {
                User.Account stair = accountList.get(i);
                for (int j = 0; j < stair.getAccounts().size(); j++) {
                    User.Account second = stair.getAccounts().get(j);
                    if (data.getSelectAccount().contains(second.getName())) {
                        if (second.getAccounts() == null) {
                            second.setAccounts(new LinkedList<User.Account>());
                        }
                        second.getAccounts().add(data);
                        second.setPrice(second.getPrice() + data.getPrice());
                        stair.getAccounts().set(j, second);
                        accountList.set(i, stair);
                        mUserData.setAccountList(accountList);
                        break;
                    }
                }
            }
        } else {
            transferAccountsFragment.getData();
        }
        Data.upUser(BookKeepingActivity.this);
    }
}

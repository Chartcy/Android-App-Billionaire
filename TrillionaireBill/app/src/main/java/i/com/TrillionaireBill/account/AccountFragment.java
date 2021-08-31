package i.com.TrillionaireBill.account;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.been.User;


public class AccountFragment extends Fragment {

    @BindView(R.id.property)
    TextView property;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);

        notifyDataSetChanged();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccoutAddActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void notifyDataSetChanged() {
        double price = 0;
        List<User.Account> accountList = Data.mUserData.getAccountList();
        for (User.Account account : accountList) {
            double accountPrice = 0.00;
            for (User.Account second : account.getAccounts()) {
                double secondPrice = 0.00;

                if (second.getAccounts() != null) {
                    for (User.Account account1 : second.getAccounts()) {
                        secondPrice += account1.getState() == 0 ? -account1.getPrice() : account1.getPrice();
                    }
                }
                second.setPrice(secondPrice);
                accountPrice += secondPrice;
            }
            account.setPrice(accountPrice);
            price += account.getPrice();
        }

        if (property != null) {
            property.setText(String.format(Locale.CHINA, "%.2f", price));
        }


        if (recycler != null) {
            AccountAdapter accountAdapter = new AccountAdapter(getActivity());
            recycler.setAdapter(accountAdapter);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycler.addItemDecoration(new RecyclerViewLineDivider());
            accountAdapter.setData(accountList);
        }

    }
}

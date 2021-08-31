package i.com.TrillionaireBill.flowingwater.account;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.ExpandableViewHolder;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.R;

public class AccountItem extends AbstractSectionableItem<AccountItem.Viewholder, AccountGroup> {

    private Context mContext;
    private List<User.Account> accounts;

    private AccountItemListener listener;
    private String name;

    public void setListener(AccountItemListener listener) {
        this.listener = listener;
    }

    public interface AccountItemListener {
        void itemClick(String name, List<User.Account> accountList);
    }

    public AccountItem(AccountGroup header, List<User.Account> accounts, String name, Context context) {
        super(header);
        mContext = context;
        this.name = name;
        this.accounts = accounts;
    }

    @Override
    public Viewholder createViewHolder(View view, FlexibleAdapter adapter) {
        return new Viewholder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, Viewholder holder, final int position, List payloads) {
        double income = 0;
        double expenditure = 0;
        for (User.Account account : accounts) {
            if (account.getState() == 0) {
                expenditure += account.getPrice();
            } else if (account.getState() == 1) {
                income += account.getPrice();
            }
        }
        holder.money.setText(String.format(Locale.CHINA, "%.2f", income - expenditure));
        holder.month.setText(name);

        holder.year.setVisibility(View.GONE);
        holder.textView24.setText(String.format(Locale.CHINA, "%.2f", income));
        holder.textView26.setText(String.format(Locale.CHINA, "%.2f", expenditure));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(name, accounts);
            }
        });

    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AccountItem && this == o;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_water_group;
    }


    class Viewholder extends ExpandableViewHolder {
        TextView month;
        TextView textView24;
        TextView textView26;
        TextView year;
        TextView money;

        public Viewholder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            month = view.findViewById(R.id.month);
            textView24 = view.findViewById(R.id.textView24);
            textView26 = view.findViewById(R.id.textView26);
            year = view.findViewById(R.id.year);
            money = view.findViewById(R.id.money);
            view.setOnClickListener(this);
        }
    }
}

package i.com.TrillionaireBill.flowingwater.member;


import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.ExpandableViewHolder;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.R;

public class MemberGroup extends AbstractExpandableHeaderItem<MemberGroup.Viewholder, AbstractSectionableItem> {

    private double expenditure;
    private double income;
    private String name;

    public MemberGroup(String name, List<User.Account> data) {
        setHidden(false);
        setExpanded(false);
        setSelectable(false);
        this.name = name;
        for (User.Account accounts : data) {
            if (accounts.getState() == 0) {
                expenditure += accounts.getPrice();
            } else {
                income += accounts.getPrice();
            }
        }
    }

    @Override
    public AbstractExpandableItem setSubItems(List<AbstractSectionableItem> subItems) {
        return super.setSubItems(subItems);
    }

    @Override
    public Viewholder createViewHolder(View view, FlexibleAdapter adapter) {
        return new Viewholder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, Viewholder holder, int position, List payloads) {
        holder.money.setText(String.format(Locale.CHINA, "%.2f", income - expenditure));
        holder.month.setText(name);
        holder.textView24.setText(String.format(Locale.CHINA, "%.2f", income));
        holder.textView26.setText(String.format(Locale.CHINA, "%.2f", expenditure));
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.adapter_water_group;
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
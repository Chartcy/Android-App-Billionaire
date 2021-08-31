package i.com.TrillionaireBill;


import android.view.View;
import android.widget.TextView;


import java.util.List;
import java.util.Locale;
import java.util.Map;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.ExpandableViewHolder;
import i.com.TrillionaireBill.been.User;

public class FuItem extends AbstractExpandableHeaderItem<FuItem.Viewholder, AbstractSectionableItem> {


    private Map<Integer, List<User.Account>> data;
    private int type;
    private int yearTime;
    private double income;//收
    private double expenditure;

    public FuItem(Map<Integer, List<User.Account>> data, int type, int year) {
        setHidden(false);
        setExpanded(false);
        setSelectable(false);
        this.data = data;
        this.type = type;
        this.yearTime = year;
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
        Object[] objects = data.keySet().toArray();
        Object object = objects[position];
        List<User.Account> accountList = data.get(object);
        for(User.Account account : accountList){
            if (account.getState() == 0) {
                expenditure += account.getPrice();
            } else {
                income += account.getPrice();
            }
        }
        if (type == 1) {
            holder.money.setText(String.format(Locale.CHINA, "%.2f", income - expenditure));
            String s = object.toString();
            if(s.length() == 1){
                holder.month.setText("0".concat(s));
            }else{
                holder.month.setText(s);
            }

            holder.year.setText(String.valueOf(yearTime).concat("年"));
            holder.textView24.setText(String.format(Locale.CHINA, "%.2f", income));
            holder.textView26.setText(String.format(Locale.CHINA, "%.2f", expenditure));
        } else {
            holder.btMoney.setText("结余 ".concat(String.format(Locale.CHINA, "%.2f", income - expenditure)));
            holder.btYear.setText(object.toString().concat("月"));
        }
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return type == 1 ? R.layout.adapter_water_group : R.layout.adapter_flowing_water_grou;
    }

    private void setStatusProperty(TextView view, String str, int cocler, int isShow) {
        view.setText(str);
        view.setBackgroundResource(cocler);
        view.setVisibility(isShow);
    }

    class Viewholder extends ExpandableViewHolder {
        TextView month;
        TextView textView24;
        TextView textView26;
        TextView year;
        TextView money;
        TextView btMoney;
        TextView btYear;

        public Viewholder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            month = view.findViewById(R.id.month);
            textView24 = view.findViewById(R.id.textView24);
            textView26 = view.findViewById(R.id.textView26);
            year = view.findViewById(R.id.year);
            money = view.findViewById(R.id.money);
            btYear = view.findViewById(R.id.bt_year);
            btMoney = view.findViewById(R.id.bt_money);
            view.setOnClickListener(this);
        }
    }
}
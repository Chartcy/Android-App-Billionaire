package i.com.TrillionaireBill.flowingwater.account;


import android.view.View;
import android.widget.TextView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.ExpandableViewHolder;
import i.com.TrillionaireBill.R;

public class AccountGroup extends AbstractExpandableHeaderItem<AccountGroup.Viewholder, AbstractSectionableItem> {


    private String data;
    public AccountGroup(String data) {
        setHidden(false);
        setExpanded(false);
        setSelectable(false);
        this.data = data;
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
        holder.name.setText(data);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.account_group;
    }

    class Viewholder extends ExpandableViewHolder {
        TextView name;

        public Viewholder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            name = view.findViewById(R.id.name);
            view.setOnClickListener(this);
        }
    }
}
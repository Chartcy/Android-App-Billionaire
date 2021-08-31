package i.com.TrillionaireBill.flowingwater;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.*;

public class SubclassItem extends AbstractSectionableItem<SubclassItem.Viewholder, FuItem> {

    private Context mContext;
    private List<User.Account> accounts;

    public SubclassItem(FuItem header, List<User.Account> accounts, Context context) {
        super(header);
        mContext = context;
        this.accounts = accounts;
    }

    @Override
    public Viewholder createViewHolder(View view, FlexibleAdapter adapter) {
        return new Viewholder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, Viewholder holder, int position, List payloads) {
        FlowingWaterAdapter adapter1 = new FlowingWaterAdapter();
        holder.recyle.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyle.setAdapter(adapter1);
        adapter1.setData(accounts);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SubclassItem && this == o;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.adapter_flowing_water_item;
    }


    class Viewholder extends FlexibleViewHolder {

        RecyclerView recyle;

        public Viewholder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            recyle = view.findViewById(R.id.recycler);
        }
    }
}

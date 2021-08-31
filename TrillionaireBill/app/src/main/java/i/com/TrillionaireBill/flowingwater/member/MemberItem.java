package i.com.TrillionaireBill.flowingwater.member;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.ExpandableViewHolder;
import i.com.TrillionaireBill.flowingwater.FlowingWaterAdapter;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.R;

public class MemberItem extends AbstractSectionableItem<MemberItem.Viewholder, MemberGroup> {

    private Context mContext;
    private List<User.Account> accounts;

    public MemberItem(MemberGroup header, List<User.Account> accounts, Context context) {
        super(header);
        mContext = context;
        this.accounts = accounts;
    }

    @Override
    public Viewholder createViewHolder(View view, FlexibleAdapter adapter) {
        return new Viewholder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, Viewholder holder, final int position, List payloads) {
        FlowingWaterAdapter adapter1 = new FlowingWaterAdapter();
        holder.recycler.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recycler.setAdapter(adapter1);
        adapter1.setData(accounts);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MemberItem && this == o;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.member_item;
    }


    class Viewholder extends ExpandableViewHolder {
        RecyclerView recycler;


        public Viewholder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            recycler = view.findViewById(R.id.recycler);
            view.setOnClickListener(this);
        }
    }
}

package i.com.TrillionaireBill.addclassify;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import i.com.TrillionaireBill.R;

public class ClassifcationItem extends AbstractSectionableItem<ClassifcationItem.Viewholder, ClassifcationGroup> {

    private Context mContext;
    private String[] accounts;
    private String groupName;

    public ClassifcationItem(ClassifcationGroup header, String[] accounts, Context context, String groupName) {
        super(header);
        mContext = context;
        this.accounts = accounts;
        this.groupName = groupName;
    }

    @Override
    public Viewholder createViewHolder(View view, FlexibleAdapter adapter) {
        return new Viewholder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, Viewholder holder, int position, List payloads) {
        ClassifcationAdapter adapter1 = new ClassifcationAdapter();
        holder.recyle.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyle.setAdapter(adapter1);
        adapter1.setData(accounts);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接创建二级分类
                Intent intent = new Intent(mContext, AddClassifcationTwoActivity.class);
                intent.putExtra("name", groupName);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ClassifcationItem && this == o;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.classifcation_item;
    }


    class Viewholder extends FlexibleViewHolder {

        RecyclerView recyle;
        TextView add;

        public Viewholder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            recyle = view.findViewById(R.id.recyclerView);
            add = view.findViewById(R.id.add);
        }
    }
}

package i.com.TrillionaireBill.addclassify;

import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractExpandableHeaderItem;
import eu.davidea.flexibleadapter.items.AbstractExpandableItem;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.ExpandableViewHolder;
import i.com.TrillionaireBill.R;


public class ClassifcationGroup extends AbstractExpandableHeaderItem<ClassifcationGroup.Viewholder, AbstractSectionableItem> {

    private Map<String, String[]> data;

    public ClassifcationGroup(Map<String, String[]> data) {
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
    public ClassifcationGroup.Viewholder createViewHolder(View view, FlexibleAdapter adapter) {
        return new ClassifcationGroup.Viewholder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ClassifcationGroup.Viewholder holder, int position, List payloads) {
        Object[] objects = data.keySet().toArray();
        holder.name.setText(objects[position].toString());
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.classifcation_group ;
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

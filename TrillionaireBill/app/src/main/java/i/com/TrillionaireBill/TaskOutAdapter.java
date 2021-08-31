package i.com.TrillionaireBill;


import android.support.annotation.Nullable;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

public class TaskOutAdapter extends FlexibleAdapter<AbstractFlexibleItem> {

    public TaskOutAdapter(@Nullable List<AbstractFlexibleItem> items, @Nullable Object listeners) {
        super(items, listeners);
    }
}

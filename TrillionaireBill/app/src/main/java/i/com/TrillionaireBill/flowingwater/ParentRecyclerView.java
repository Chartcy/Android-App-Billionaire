package i.com.TrillionaireBill.flowingwater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ParentRecyclerView extends RecyclerView {

    public ParentRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //不拦截，继续分发下去
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }
}




















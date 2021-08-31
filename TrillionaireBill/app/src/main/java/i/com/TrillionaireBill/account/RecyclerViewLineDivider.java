package i.com.TrillionaireBill.account;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewLineDivider extends RecyclerView.ItemDecoration {

    private Paint paint;

    public RecyclerViewLineDivider() {
        this(0xFFF4F4F4, 3);
    }

    public RecyclerViewLineDivider(int color, int width) {
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(width);
    }

    @Override public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            int bottom = parent.getChildAt(i).getBottom();
            c.drawLine(0, bottom, parent.getWidth(), bottom, paint);
        }
    }
}

package i.com.TrillionaireBill.addclassify;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import i.com.TrillionaireBill.R;

public class ClassifcationAdapter extends RecyclerView.Adapter<ClassifcationAdapter.ViewHolder> {

    private String[] data;

    public void setData(String[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.classifcation_items, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

}

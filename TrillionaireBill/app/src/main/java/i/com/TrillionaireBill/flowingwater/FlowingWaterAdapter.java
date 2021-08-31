package i.com.TrillionaireBill.flowingwater;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.R;

public class FlowingWaterAdapter extends RecyclerView.Adapter<FlowingWaterAdapter.ViewHolder> {


    private List<User.Account> data;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_flowing_water, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User.Account account = data.get(position);
        holder.name.setText(account.getSecond());
        holder.time.setText(account.getTime());
        holder.price.setText(String.format(Locale.CHINA, "%.2f", account.getState() == 0 ? -account.getPrice() : account.getPrice()));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<User.Account> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.price)
        TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

package i.com.TrillionaireBill.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.been.User;

/**
 * Created by Administrator on 2020/10/17.
 */

public class AccountItemAdapter extends RecyclerView.Adapter<AccountItemAdapter.ViewHolder> {

    private List<User.Account> data;
    private int icon;
    private AccountItemListener listener;

    public void setListener(AccountItemListener listener) {
        this.listener = listener;
    }

    public AccountItemAdapter(int icon) {
        this.icon = icon;
    }

    public interface AccountItemListener {
        void itemClick(List<User.Account> account, String name);

        void itemLongClick(String name);
    }

    public void setData(List<User.Account> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_account_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User.Account account = data.get(position);
        holder.name.setText(account.getName());
        holder.icon.setBackgroundResource(icon);
        holder.price.setText(String.format(Locale.CHINA, "%.2f", account.getPrice()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.itemLongClick(data.get(position).getName());
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(data.get(position).getAccounts(), data.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.icon)
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

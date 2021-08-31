package i.com.TrillionaireBill.account;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.R;


public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private List<User.Account> data;
    private Context context;

    public void setData(List<User.Account> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public AccountAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_account, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        User.Account account = data.get(position);
        holder.name.setText(account.getName());
        holder.price.setText(String.format(Locale.CHINA, "%.2f", account.getPrice()));

        holder.recycler.setLayoutManager(new LinearLayoutManager(context));
        holder.recycler.addItemDecoration(new DividerItemDecoration(context, 1));
        AccountItemAdapter itemAdapter = new AccountItemAdapter(account.getIcon());
        itemAdapter.setListener(new AccountItemAdapter.AccountItemListener() {
            @Override
            public void itemClick(List<User.Account> accountList, String name) {
                Intent intent = new Intent(context, AccountRunningWaterActivity.class);
                intent.putParcelableArrayListExtra("data", accountList != null ? (new ArrayList<Parcelable>(accountList)) : new ArrayList<Parcelable>());
                intent.putExtra("name", name);
                context.startActivity(intent);
            }

            @Override
            public void itemLongClick(String name) {
                Intent intent = new Intent(context, EditAccountNameActivity.class);
                intent.putExtra("group", data.get(position).getName());
                intent.putExtra("name", name);
                context.startActivity(intent);
            }
        });
        holder.recycler.setAdapter(itemAdapter);
        itemAdapter.setData(account.getAccounts());
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
        @BindView(R.id.recycler)
        RecyclerView recycler;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

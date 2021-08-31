package i.com.TrillionaireBill.account;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import i.com.TrillionaireBill.been.Account;
import i.com.TrillionaireBill.R;


public class AccoutAddAdapter extends BaseExpandableListAdapter {

    private List<Account> data;
    private Context context;
    private AccoutAddAListener listener;

    public AccoutAddAdapter(List<Account> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public interface AccoutAddAListener{
        void onClick(String name , int position);
    }

    public void setListener(AccoutAddAListener listener) {
        this.listener = listener;
    }

    @Override
    public int getGroupCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data == null ? 0 : data.get(groupPosition).getZname() == null ? 0 : data.get(groupPosition).getZname().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getZname().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_accout_add_group, null);

        ImageView picture = view.findViewById(R.id.picture);
        ImageView state = view.findViewById(R.id.state);
        TextView name = view.findViewById(R.id.name);
        TextView remark = view.findViewById(R.id.remark);
        final Account account = data.get(groupPosition);

        if (account.getZname() == null) {
            state.setVisibility(View.GONE);
        } else {
            state.setVisibility(View.VISIBLE);
        }

        if (!isExpanded) {
            state.setBackgroundResource(R.drawable.up_icon);
        } else {
            state.setBackgroundResource(R.drawable.down_icon);
        }

        picture.setBackgroundResource(account.getPicture());
        name.setText(account.getName());
        if (!TextUtils.isEmpty(account.getRemake())) {
            remark.setText(account.getRemake());
            remark.setVisibility(View.VISIBLE);
        } else {
            remark.setVisibility(View.GONE);
        }

        if (account.getZname() == null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(account.getName() , groupPosition);
                }
            });
        }

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_accout_add_item, null);
        }
        TextView name = convertView.findViewById(R.id.name);
        final String s = data.get(groupPosition).getZname().get(childPosition);
        name.setText(s);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(s , groupPosition);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}

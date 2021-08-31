package i.com.TrillionaireBill.flowingwater;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.FuItem;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.TimeUtils;
import i.com.TrillionaireBill.TaskOutAdapter;
import i.com.TrillionaireBill.been.User;
import i.com.TrillionaireBill.flowingwater.account.AccountGroup;
import i.com.TrillionaireBill.flowingwater.account.AccountItem;
import i.com.TrillionaireBill.flowingwater.account.AccountItemActivity;
import i.com.TrillionaireBill.flowingwater.member.MemberGroup;
import i.com.TrillionaireBill.flowingwater.member.MemberItem;

public class FlowingWaterFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    Unbinder unbinder;
    @BindView(R.id.button4)
    TextView button4;
    @BindView(R.id.button5)
    TextView button5;
    @BindView(R.id.button6)
    TextView button6;
    @BindView(R.id.button7)
    TextView button7;
    @BindView(R.id.button8)
    TextView button8;
    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.textview)
    TextView textview;

    int type = 1;
    @BindView(R.id.month)
    TextView month;
    @BindView(R.id.year)
    TextView mYear;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.textView24)
    TextView textView24;
    @BindView(R.id.textView26)
    TextView textView26;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.bt_year)
    TextView btYear;
    @BindView(R.id.bt_money)
    TextView btMoney;
    @BindView(R.id.group1)
    ConstraintLayout group1;
    @BindView(R.id.group2)
    ConstraintLayout group2;

    @BindView(R.id.last)
    ImageView last;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.container1)
    ConstraintLayout container1;

    int selectTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flowing_water, container, false);
        unbinder = ButterKnife.bind(this, view);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.CHINESE);
        Date curDate = new Date(System.currentTimeMillis());
        selectTime = Integer.parseInt(sdf.format(curDate));
        setTime(selectTime);
        time.setText(String.valueOf(selectTime));
        notifyDataSetChanged();
        //前进时间
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime += 1;
                time.setText(String.valueOf(selectTime));
                setTime(selectTime);
            }
        });
        //后退时间
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime -= 1;
                time.setText(String.valueOf(selectTime));
                setTime(selectTime);
            }
        });
        //月度
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                setTime(selectTime);
            }
        });
        //年度
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                setTime(selectTime);
            }
        });
        //账户
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                group1.setVisibility(View.VISIBLE);
                group2.setVisibility(View.GONE);
                mYear.setVisibility(View.GONE);
                setTime(selectTime);
            }
        });
        //成员
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 4;
                group1.setVisibility(View.VISIBLE);
                group2.setVisibility(View.GONE);
                mYear.setVisibility(View.GONE);
                setTime(selectTime);
            }
        });
        //商家
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 5;
                group1.setVisibility(View.VISIBLE);
                group2.setVisibility(View.GONE);
                mYear.setVisibility(View.GONE);
                setTime(selectTime);
            }
        });

        return view;
    }

    private void setSelectButton(TextView textview) {
        //更换按钮颜色
        if (button4 != null && button5 != null && button6 != null
                && button7 != null && button8 != null && textview != null) {
            button5.setTextColor(0xff808080);
            button4.setTextColor(0xff808080);
            button6.setTextColor(0xff808080);
            button7.setTextColor(0xff808080);
            button8.setTextColor(0xff808080);
            textview.setTextColor(0xffF7502F);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public Long parseServerTime(String serverTime) {
        String format = "yyyy-MM-dd HH:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {
        }
        return date.getTime();
    }

    public void notifyDataSetChanged() {
        List<User.Account> accountList = Data.mUserData.getAccountList();
        User.Account temp = null;
        List<User.Account> data = new ArrayList<>();
        for (User.Account account : accountList) {
            for (User.Account second : account.getAccounts()) {
                if (second.getAccounts() != null) {
                    data.addAll(second.getAccounts());
                }
            }
        }
        if (data.size() != 0) {
            for (int i = 0; i < data.size() - 1; i++) {
                for (int j = 0; j < data.size() - 1 - i; j++) {
                    if (parseServerTime(data.get(j).getTime()) > parseServerTime(data.get(j + 1).getTime())) {
                        temp = data.get(j);
                        data.set(j, data.get(j + 1));
                        data.set(j + 1, temp);
                    }
                }
            }
        }

        if (textview != null) {
            if (data.size() == 0) {
                textview.setVisibility(View.VISIBLE);
            } else {
                textview.setVisibility(View.GONE);
            }
        }

    }

    public void setTime(int year) {
        if (type != 0) {
            switch (type) {
                case 1: {
                    setSelectButton(button4);
                    break;
                }
                case 2: {
                    setSelectButton(button5);
                    break;
                }
                case 3: {
                    setSelectButton(button6);
                    break;
                }
                case 4: {
                    setSelectButton(button7);
                    break;
                }
                case 5: {
                    setSelectButton(button8);
                    break;
                }
            }
            if (group1 != null && group2 != null && mYear != null) {
                if (type == 1) {
                    group2.setVisibility(View.VISIBLE);
                    group1.setVisibility(View.GONE);
                } else {
                    group1.setVisibility(View.VISIBLE);
                    group2.setVisibility(View.GONE);
                    mYear.setVisibility(View.GONE);
                }
            }

        } else {
            setSelectButton(button4);
        }
        Date startDate = TimeUtils.getSupportBeginDayofMonth(year, 1);
        Date endDate = TimeUtils.getSupportEndDayofMonth(year, 12);
        //List<PieChartView.PieceDataHolder> pieceDataHolders = new ArrayList<>();
        List<User.Account> accountList = Data.mUserData.getAccountList();
        List<User.Account> totalList = new LinkedList<>();
        double expenditure = 0;
        double income = 0;
        //用来给账户做数据
        Map<String, Map<String, List<User.Account>>> accountMap = new LinkedHashMap<>();

        for (User.Account account : accountList) {

            Map<String, List<User.Account>> map = accountMap.get(account.getName());
            if (map == null) {
                map = new LinkedHashMap<>();
            }
            for (User.Account second : account.getAccounts()) {
                if (second.getAccounts() != null) {
                    List<User.Account> accountList1 = map.get(second.getName());
                    if (accountList1 == null) {
                        accountList1 = new LinkedList<>();
                    }
                    for (User.Account account1 : second.getAccounts()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date date = null;
                        try {
                            date = sdf.parse(account1.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (startDate.getTime() < date.getTime() && endDate.getTime() > date.getTime()) {
                            totalList.add(account1);
                            accountList1.add(account1);
                            if (account1.getState() == 0) {
                                expenditure += account1.getPrice();
                            } else if (account1.getState() == 1) {
                                income += account1.getPrice();
                            }
                        }
                    }
                    map.put(second.getName(), accountList1);
                }
            }
            if (map.size() != 0) {
                accountMap.put(account.getName(), map);
            }
        }

        if (btYear != null) {
            btYear.setText(String.valueOf(year));
        }
        if (btMoney != null) {
            btMoney.setText("结余 ".concat(String.format(Locale.CHINA, "%.2f", income - expenditure)));
        }
        if (month != null) {
            month.setText(String.valueOf(year));
        }
        if (mMoney != null) {
            mMoney.setText(String.format(Locale.CHINA, "%.2f", income - expenditure));
        }
        if (textView24 != null) {
            textView24.setText(String.format(Locale.CHINA, "%.2f", income));
        }
        if (textView26 != null) {
            textView26.setText(String.format(Locale.CHINA, "%.2f", expenditure));
        }
        TaskOutAdapter mTaskOutAdapter = new TaskOutAdapter(null, null);
        if (recycler != null) {
            recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            recycler.setAdapter(mTaskOutAdapter);
        }

        if (type == 1 || type == 2) {
            Map<Integer, List<User.Account>> data = new LinkedHashMap<>();
            for (int i = 1; i <= 12; i++) {
                data.put(i, null);
            }
            for (User.Account account : totalList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = null;
                try {
                    date = sdf.parse(account.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat moth = new SimpleDateFormat("MM");
                int time = Integer.parseInt(moth.format(date));
                List<User.Account> accounts = data.get(time);
                if (accounts == null) {
                    accounts = new ArrayList<>();
                }
                accounts.add(account);
                data.put(time, accounts);
            }
            for (int x = 1; x <= 12; x++) {
                if (data.get(x) == null) {
                    data.remove(x);
                } else {
                    List<User.Account> accounts = data.get(x);
                    User.Account temp = null;
                    for (int i = 0; i < accounts.size() - 1; i++) {
                        for (int j = 0; j < accounts.size() - 1 - i; j++) {
                            if (parseServerTime(accounts.get(j).getTime()) < parseServerTime(accounts.get(j + 1).getTime())) {
                                temp = accounts.get(j);
                                accounts.set(j, accounts.get(j + 1));
                                accounts.set(j + 1, temp);
                            }
                        }
                    }
                    data.put(x, accounts);
                }
            }

            List<AbstractFlexibleItem> list = new ArrayList();
            for (List<User.Account> accountList2 : data.values()) {
                FuItem fuItem = new FuItem(data, type, year);
                SubclassItem subclassItem = new SubclassItem(fuItem, accountList2, getActivity());
                fuItem.addSubItem(subclassItem);
                list.add(fuItem);
            }
            mTaskOutAdapter.updateDataSet(list);

        } else if (type == 3) {
            List<AbstractFlexibleItem> list = new ArrayList();

            Object[] objects = accountMap.keySet().toArray();
            for (int j = 0; j < objects.length; j++) {
                AccountGroup accountGroup = new AccountGroup(objects[j].toString());
                Map<String, List<User.Account>> map = accountMap.get(objects[j]);
                for (int x = 0; x < map.size(); x++) {
                    Object[] objects1 = map.keySet().toArray();
                    List<User.Account> accountList1 = map.get(objects1[x]);
                    AccountItem accountItem = new AccountItem(accountGroup, accountList1, objects1[x].toString(), getActivity());
                    accountItem.setListener(new AccountItem.AccountItemListener() {
                        @Override
                        public void itemClick(String name, List<User.Account> accountList) {
                            Intent intent = new Intent(getActivity(), AccountItemActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("accountList", new Gson().toJson(accountList));
                            startActivity(intent);
                        }
                    });
                    accountGroup.addSubItem(accountItem);
                }
                list.add(accountGroup);
            }
            mTaskOutAdapter.updateDataSet(list);
        } else if (type == 4) {
            Map<String, List<User.Account>> data = new LinkedHashMap<>();
            List<AbstractFlexibleItem> list = new ArrayList();
            for (User.Account account : totalList) {
                String s = TextUtils.isEmpty(account.getMember()) ? "无成员" : account.getMember();
                List<User.Account> accountList1 = data.get(s);
                if (accountList1 == null) {
                    accountList1 = new LinkedList<>();
                }
                accountList1.add(account);
                data.put(s, accountList1);
            }

            for (int i = 0; i < data.size(); i++) {
                Object[] objects1 = data.keySet().toArray();
                List<User.Account> accountList1 = data.get(objects1[i]);
                MemberGroup memberGroup = new MemberGroup(objects1[i].toString(), accountList1);
                MemberItem item = new MemberItem(memberGroup, accountList1, getActivity());
                memberGroup.addSubItem(item);
                list.add(memberGroup);
            }
            mTaskOutAdapter.updateDataSet(list);

        } else if (type == 5) {
            Map<String, List<User.Account>> data = new LinkedHashMap<>();
            List<AbstractFlexibleItem> list = new ArrayList();
            for (User.Account account : totalList) {
                String s = TextUtils.isEmpty(account.getMerchant()) ? "无商家/地点" : account.getMerchant();
                List<User.Account> accountList1 = data.get(s);
                if (accountList1 == null) {
                    accountList1 = new LinkedList<>();
                }
                accountList1.add(account);
                data.put(s, accountList1);
            }

            for (int i = 0; i < data.size(); i++) {
                Object[] objects1 = data.keySet().toArray();
                List<User.Account> accountList1 = data.get(objects1[i]);
                MemberGroup memberGroup = new MemberGroup(objects1[i].toString(), accountList1);
                MemberItem item = new MemberItem(memberGroup, accountList1, getActivity());
                memberGroup.addSubItem(item);
                list.add(memberGroup);
            }
            mTaskOutAdapter.updateDataSet(list);

        }

    }
}

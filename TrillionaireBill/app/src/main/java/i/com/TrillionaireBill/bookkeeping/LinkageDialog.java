package i.com.TrillionaireBill.bookkeeping;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import i.com.TrillionaireBill.account.AccoutAddActivity;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.addclassify.AddBusinessActivity;
import i.com.TrillionaireBill.addclassify.AddclassificationActivity;
import i.com.TrillionaireBill.been.Classify;
import i.com.TrillionaireBill.been.User;

public class LinkageDialog extends DialogFragment {

    public static final int CLASSIFY_TYPE = 1;
    public static final int MERCHANT_TYPE = 2;
    public static final int MEMBER_TYPE = 3;
    public static final int ACCOUNT_TYPE = 4;

    private String province;
    private LinkageDialog.ClickListenerInterface clickListenerInterface;
    private String[] provinces, cities;
    private String selectedProvince;
    private int currentProvince, currentCity;

    private ListView lv_city, lv_province;
    private ArrayAdapter adapter;
    private Resources res;

    private Map<String, String[]> mapData;

    public static LinkageDialog newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        LinkageDialog fragment = new LinkageDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static LinkageDialog newInstance(int type, int showPrice) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putInt("showPrice", showPrice);
        LinkageDialog fragment = new LinkageDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_linkage, null);
        final int type = getArguments().getInt("type");
        final Classify mClassify = Data.mClassify;
        final User user = Data.mUserData;
        if (type == CLASSIFY_TYPE) {
            provinces = mClassify.getStair();
            cities = mClassify.getSecond().get(provinces[0]);
        } else if (type == MERCHANT_TYPE) {
            provinces = new String[]{"所有"};
            cities = mClassify.getMerchant();
        } else if (type == MEMBER_TYPE) {
            provinces = new String[]{"所有"};
            cities = mClassify.getMember();
        } else if (type == ACCOUNT_TYPE) {
            List<User.Account> accountList = user.getAccountList();
            Map<String, String[]> map = new LinkedHashMap<>();
            String[] s = new String[accountList.size()];
            for (int i = 0; i < s.length; i++) {
                s[i] = "";
            }
            for (int i = 0; i < accountList.size(); i++) {
                s[i] = accountList.get(i).getName();
                LinkedList<User.Account> accounts1 = accountList.get(i).getAccounts();
                String[] accounts = new String[accounts1.size()];
                for (int j = 0; j < accounts1.size(); j++) {
                    User.Account account = accounts1.get(j);
                    accounts[j] = account.getName().concat(getArguments().getInt("showPrice", 0)
                            == 1 ? "" : ("(").concat(String.format(Locale.CHINA, "%.2f", account.getPrice())).concat(")"));
                }
                map.put(s[i], accounts);
            }
            provinces = s;
            mapData = map;
            cities = map.get(provinces[0]);
        }

        province = provinces[0];
        res = getActivity().getResources();
        //获取省份String数组
//        provinces = res.getStringArray(R.array.province);

        //传进来的字符串为空，则设置默认的省份为食品酒水
//        cities = res.getStringArray(R.array.食品酒水);
//        this.province = "早午晚餐";

        lv_province = view.findViewById(R.id.lv_dialog1_1);
        lv_city = view.findViewById(R.id.lv_dialog1_2);
        Button bt = view.findViewById(R.id.bt_dialog1);
        final TextView add = view.findViewById(R.id.add);
        bt.setOnClickListener(new clickListener());

        //给省份listview设置适配器，并设置滑动到默认位置
        // （注意：我们要让目标省份显示在中间，所以滑动到 currentProvince - 1 的位置）
        String[] strings = getData(provinces);
        String[] citiesStrings = getData(cities);
        lv_province.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_dialog_lv, strings));
        lv_province.setSelection(currentProvince - 1);

        //给省城市listview设置适配器，并设置滑动到默认位置
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_dialog_lv, citiesStrings);
        lv_city.setAdapter(adapter);
        lv_city.setSelection(currentCity - 1);

        /*
        对省份listview设置滑动监听，滑动停止的时候给城市listview重新获取字符串数组，
        并重新设置设置适配器（这一步应该是可以让适配器执行notifyDataSetChanged()的）,
        但是我对ArrayAdapter的notifyDataSetChanged()方法不太确定怎么用
         */
        lv_province.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                OnScrollListener.SCROLL_STATE_IDLE：滚动停止时的状态
//                OnScrollListener.SCROLL_STATE_STOUCH_SCROLL：触摸正在滚动，手指还没离开界面时的状态
//                OnScrollListener.SCROLL_STATE_FLING：用户在用力滑动后，ListView由于惯性将继续滑动时的状态
                if (scrollState == SCROLL_STATE_IDLE) {
                    int position = lv_province.getFirstVisiblePosition();
//                    LinkageDialog.this.province = provinces[position + 1];
                    if (type == CLASSIFY_TYPE) {
                        LinkageDialog.this.province = provinces[position];
                        cities = mClassify.getSecond().get(provinces[position]);
                    } else if (type == ACCOUNT_TYPE) {
                        LinkageDialog.this.province = provinces[position];
                        cities = mapData.get(provinces[position]);
                    }
//                    cities = getCities(position);
                    String[] citiesStrings = getData(cities);
                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_dialog_lv, citiesStrings);
                    lv_city.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        lv_city.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int position = lv_city.getFirstVisiblePosition();
                if (cities.length - (position + 2) <= 2) {
                    add.setVisibility(View.VISIBLE);
                } else {
                    add.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (type == CLASSIFY_TYPE) {
                    getIntent(AddclassificationActivity.class);
                } else if (type == MERCHANT_TYPE) {
                    getIntent(AddBusinessActivity.class);
                } else if (type == MEMBER_TYPE) {
                    getIntent(AddMemberActivity.class);
                } else if (type == ACCOUNT_TYPE) {
                    getIntent(AccoutAddActivity.class);
                }
            }
        });

        return view;
    }

    private String[] getData(String[] cities) {
        List<String> stringList = new LinkedList<>(Arrays.asList(cities));
        stringList.add(0, "");
        stringList.add("");
        return stringList.toArray(new String[stringList.size()]);
    }

    private void getIntent(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }

    public interface ClickListenerInterface {
        //这里只设置了一个按钮，有需要可以自己添加
        void doConfirm();

        void doCancel();//可以设置多个方法和多个按钮对应
    }

    //监听点击按钮的回调
    public void setClicklistener(LinkageDialog.ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.bt_dialog1:
                    clickListenerInterface.doConfirm();
                    break;
                //多个按钮在这添加case
                default:
                    break;
            }
        }
    }

    //两个get方法
    public String getProvince() {
        return province;
    }

    public String getCity() {
        return cities[lv_city.getFirstVisiblePosition()];
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }
}


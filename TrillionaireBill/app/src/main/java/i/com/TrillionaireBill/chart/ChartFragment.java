package i.com.TrillionaireBill.chart;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xclcharts.chart.PieData;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import i.com.TrillionaireBill.account.AccountRunningWaterActivity;
import i.com.TrillionaireBill.Data;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.TimeUtils;
import i.com.TrillionaireBill.been.User;

public class ChartFragment extends Fragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.last)
    ImageView last;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.textView15)
    TextView textView15;
    Unbinder unbinder;
    @BindView(R.id.linear)
    LinearLayout layout;

    private Object[] objects;
    private Map<String, Double> mapData;
    private int type = 1;
    private int grade = 0;

    private PieChart02View pieChart;
    private double totalTimePrice = 0;

    private long timeDifference = 0;
    private Date startDate;
    private Date endDate;

    Map<String, List<User.Account>> stairIncomeData;
    Map<String, List<User.Account>> secondIncomeData;

    Map<String, List<User.Account>> stairData;
    Map<String, List<User.Account>> secondData;

    Map<String, List<User.Account>> memberIncomeData;
    Map<String, List<User.Account>> memberData;

    Map<String, List<User.Account>> merchantIncomeData;
    Map<String, List<User.Account>> merchantData;

    Map<String, List<User.Account>> accountIncomeData;
    Map<String, List<User.Account>> accountMapData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setNavigationIcon(null);
        title.setText("一级支出");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

        pieChart = new PieChart02View(getActivity());
        layout.addView(pieChart);
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(defaultDisplay.getWidth(), defaultDisplay.getHeight() / 3 * 2);
        pieChart.setLayoutParams(layoutParams);
        pieChart.setListener(new PieChart02View.PieChartListener() {
            @Override
            public void itemClick(String name) {
                Intent intent = new Intent(getActivity(), AccountRunningWaterActivity.class);
                List<User.Account> accountList;
                if (type == 0) {
                    if (grade == 0) {
                        accountList = stairIncomeData.get(name);
                    } else if (grade == 1) {
                        accountList = secondIncomeData.get(name);
                    } else if (grade == 2) {
                        accountList = memberIncomeData.get(name);
                    } else if (grade == 3) {
                        accountList = merchantIncomeData.get(name);
                    } else {
                        accountList = accountIncomeData.get(name);
                    }
                } else {
                    if (grade == 0) {
                        accountList = stairData.get(name);
                    } else if (grade == 1) {
                        accountList = secondData.get(name);
                    } else if (grade == 2) {
                        accountList = memberData.get(name);
                    } else if (grade == 3) {
                        accountList = merchantData.get(name);
                    } else {
                        accountList = accountMapData.get(name);
                    }
                }
                if (accountList != null) {
                    intent.putParcelableArrayListExtra("data", new ArrayList<Parcelable>(accountList));
                }
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDateDialog selectDateDialog = SelectDateDialog.newInstance();
                selectDateDialog.show(getFragmentManager(), "");
                selectDateDialog.setListener(new SelectDateDialog.SelectDateListener() {
                    @Override
                    public void timeClick(String name, Date start, Date end) {
                        startDate = start;
                        endDate = end;

                        timeDifference = start.getTime() - end.getTime();
                        if (name.equals("全部")) {
                            time.setText("全部时间");
                            last.setVisibility(View.GONE);
                            next.setVisibility(View.GONE);
                        } else {
                            time.setText(name.concat(" ").concat(sdf.format(start).concat(" ~ ").concat(sdf.format(end))));
                            last.setVisibility(View.VISIBLE);
                            next.setVisibility(View.VISIBLE);
                        }
                        notifyDataSetChanged(type, grade);
                    }
                });
            }
        });

        toolbar.inflateMenu(R.menu.chart_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.item_expenditure: {
                        type = 1;
                        grade = 0;
                        break;
                    }
                    case R.id.item_expenditure_second: {
                        type = 1;
                        grade = 1;
                        break;
                    }
                    case R.id.item_income: {
                        type = 0;
                        grade = 0;
                        break;
                    }
                    case R.id.item_income_second: {
                        type = 0;
                        grade = 1;
                        break;
                    }
                    case R.id.item_expenditure_membe: {
                        type = 1;
                        grade = 2;
                        break;
                    }
                    case R.id.item_income_member: {
                        type = 0;
                        grade = 2;
                        break;
                    }
                    case R.id.item_expenditure_merchant: {
                        type = 1;
                        grade = 3;
                        break;
                    }
                    case R.id.item_income_merchant: {
                        type = 0;
                        grade = 3;
                        break;
                    }
                    case R.id.item_expenditure_account: {
                        type = 1;
                        grade = 4;
                        break;
                    }
                    case R.id.item_income_account: {
                        type = 0;
                        grade = 4;
                        break;
                    }
                }
                title.setText(item.getTitle());
                notifyDataSetChanged(type, grade);
                return false;
            }
        });

        startDate = TimeUtils.getTimesmorning();
        endDate = TimeUtils.getTimesnight();

        timeDifference = startDate.getTime() - endDate.getTime();

        time.setText("今天 ".concat(sdf.format(startDate).concat(" ~ ").concat(sdf.format(endDate))));


        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = new Date(startDate.getTime() + timeDifference);
                endDate = new Date(endDate.getTime() + timeDifference);
                time.setText(sdf.format(startDate).concat(" ~ ").concat(sdf.format(endDate)));
                notifyDataSetChanged(type, grade);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate = new Date(startDate.getTime() - timeDifference);
                endDate = new Date(endDate.getTime() - timeDifference);
                time.setText(sdf.format(startDate).concat(" ~ ").concat(sdf.format(endDate)));
                notifyDataSetChanged(type, grade);
            }
        });

        notifyDataSetChanged(type, grade);
//        layout.run();
//        PieChart02View layout = new PieChart02View(getActivity() , objects , mapData);
//        layout.setLayoutParams(layoutParams);
//        frameLayout.addView(layout);
        return view;
    }

    /**
     * startDate 开始时间
     * endDate  结束时间
     * type 收入还是支出
     * grade 一级还是二级
     */
    public LinkedList<PieData> setTime(Date startDate, Date endDate, int type, int grade) {
        totalTimePrice = 0;
        LinkedList<PieData> pieceDataHolders = new LinkedList<>();
        List<User.Account> accountList = Data.mUserData.getAccountList();
        List<User.Account> totalList = new LinkedList<>();
        for (User.Account account : accountList) {
            double accountPrice = 0.00;
            for (User.Account second : account.getAccounts()) {
                double secondPrice = 0.00;

                if (second.getAccounts() != null) {
                    for (User.Account account1 : second.getAccounts()) {
                        secondPrice += account1.getState() == 0 ? -account1.getPrice() : account1.getPrice();
                        totalList.add(account1);
                    }
                }
                second.setPrice(secondPrice);
                accountPrice += secondPrice;
            }
            account.setPrice(accountPrice);
        }

        List<User.Account> data = new LinkedList<>();
        for (int i = totalList.size() - 1; i >= 0; i--) {
            User.Account account = totalList.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//小写的mm表示的是分钟
            Date date = null;
            try {
                date = sdf.parse(account.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (startDate.getTime() < date.getTime() && endDate.getTime() > date.getTime()) {
                data.add(account);
            }
        }
        stairIncomeData = new LinkedHashMap<>();
        secondIncomeData = new LinkedHashMap<>();

        Map<String, Double> stairIncome = new LinkedHashMap<>();
        Map<String, Double> secondIncome = new LinkedHashMap<>();


        Map<String, Double> stair = new LinkedHashMap<>();
        Map<String, Double> second = new LinkedHashMap<>();
        stairData = new LinkedHashMap<>();
        secondData = new LinkedHashMap<>();

        Map<String, Double> memberIncome = new LinkedHashMap<>();
        Map<String, Double> member = new LinkedHashMap<>();
        memberIncomeData = new LinkedHashMap<>();
        memberData = new LinkedHashMap<>();

        Map<String, Double> merchantIncome = new LinkedHashMap<>();
        Map<String, Double> merchant = new LinkedHashMap<>();
        merchantIncomeData = new LinkedHashMap<>();
        merchantData = new LinkedHashMap<>();

        Map<String, Double> accountIncome = new LinkedHashMap<>();
        Map<String, Double> accountMap = new LinkedHashMap<>();
        accountIncomeData = new LinkedHashMap<>();
        accountMapData = new LinkedHashMap<>();

        for (User.Account account : data) {
            String[] split = account.getSelectAccount().split("\\(");
            if (account.getState() == 0) {
                stair.put(account.getStair(), (stair.get(account.getStair()) == null ? 0 : stair.get(account.getStair())) + account.getPrice());
                stairData.put(account.getStair(), setMapData(stairData, account.getStair(), account));

                second.put(account.getSecond(), (second.get(account.getSecond()) == null ? 0 : second.get(account.getSecond())) + account.getPrice());
                secondData.put(account.getSecond(), setMapData(secondData, account.getSecond(), account));

                member.put(account.getMember(), (member.get(account.getMember()) == null ? 0 : member.get(account.getMember())) + account.getPrice());
                memberData.put(account.getMember(), setMapData(memberData, account.getMember(), account));

                merchant.put(account.getMerchant(), (merchant.get(account.getMerchant()) == null ? 0 : merchant.get(account.getMerchant())) + account.getPrice());
                merchantData.put(account.getMerchant(), setMapData(merchantData, account.getMerchant(), account));
                if (split.length != 0) {
                    accountMap.put(split[0], (accountMap.get(split[0]) == null ? 0 : accountMap.get(split[0])) + account.getPrice());
                    accountMapData.put(split[0], setMapData(accountMapData, split[0], account));
                }

            } else {
                stairIncome.put(account.getStair(), (stairIncome.get(account.getStair()) == null ? 0 : stairIncome.get(account.getStair())) + account.getPrice());
                secondIncome.put(account.getSecond(), (secondIncome.get(account.getSecond()) == null ? 0 : secondIncome.get(account.getSecond())) + account.getPrice());

                stairIncomeData.put(account.getSecond(), setMapData(stairIncomeData, account.getStair(), account));
                secondIncomeData.put(account.getSecond(), setMapData(secondIncomeData, account.getSecond(), account));

                memberIncome.put(account.getMember(), (memberIncome.get(account.getMember()) == null ? 0 : memberIncome.get(account.getMember())) + account.getPrice());
                memberIncomeData.put(account.getMember(), setMapData(memberIncomeData, account.getMember(), account));

                merchantIncome.put(account.getMerchant(), (merchantIncome.get(account.getMerchant()) == null ? 0 : merchantIncome.get(account.getMerchant())) + account.getPrice());
                merchantIncomeData.put(account.getMerchant(), setMapData(merchantIncomeData, account.getMerchant(), account));
                if (split.length != 0) {
                    accountIncome.put(split[0], (accountIncome.get(split[0]) == null ? 0 : accountIncome.get(split[0])) + account.getPrice());
                    accountIncomeData.put(split[0], setMapData(accountIncomeData, split[0], account));
                }
            }
            totalTimePrice += account.getPrice();
        }

        if (type == 0) {
            if (grade == 0) {
                objects = stairIncome.keySet().toArray();
                mapData = stairIncome;
            } else if (grade == 1) {
                objects = secondIncome.keySet().toArray();
                mapData = secondIncome;
            } else if (grade == 2) {
                objects = memberIncome.keySet().toArray();
                mapData = memberIncome;
            } else if (grade == 3) {
                objects = merchantIncome.keySet().toArray();
                mapData = merchantIncome;
            } else {
                objects = accountIncome.keySet().toArray();
                mapData = accountIncome;
            }
        } else {
            if (grade == 0) {
                objects = stair.keySet().toArray();
                mapData = stair;
            } else if (grade == 1) {
                objects = second.keySet().toArray();
                mapData = second;
            } else if (grade == 2) {
                objects = member.keySet().toArray();
                mapData = member;
            } else if (grade == 3) {
                objects = merchant.keySet().toArray();
                mapData = merchant;
            } else {
                objects = accountMap.keySet().toArray();
                mapData = accountMap;
            }
        }
        double total = 0;
        for (double price : mapData.values()) {
            total += price;
        }
        int[] color = new int[7];
        color[0] = 0xFFFF2600;
        color[1] = 0xFFFF7300;
        color[2] = 0xFF00FF05;
        color[3] = 0xFF00FFFB;
        color[4] = 0xFF0061FF;
        color[5] = 0xFFB300FF;
        color[6] = 0xFFFF00EE;
        int cnt = 0;
        for (Object s : objects) {

            Random random = new Random();
            int Color = color[(cnt++) % 7];//0xff000000 | random.nextInt(0x00ffffff);
            if (cnt == objects.length && cnt % 7 == 1) {
                Color = 0xFF6700FF;
            }

            Double aDouble = mapData.get(s.toString());
            if (aDouble != 0) {
                float rateCharge = ((float) (new BigDecimal(aDouble).divide(new BigDecimal(total),
                        3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));

                PieData pd = new PieData(s.toString(), s.toString().concat(String.format(Locale.CHINA, "%.1f", rateCharge)).concat("%")
                        , rateCharge, Color);

                pieceDataHolders.add(pd);
            }
        }
        return pieceDataHolders;
    }

    private List<User.Account> setMapData(Map<String, List<User.Account>> stairIncomeData, String name, User.Account account) {
        List<User.Account> accountList1 = stairIncomeData.get(name);
        if (accountList1 == null) {
            accountList1 = new ArrayList<>();
        }
        accountList1.add(account);
        return accountList1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void notifyDataSetChanged() {
        notifyDataSetChanged(type, grade);
    }

    public void notifyDataSetChanged(int type, int grade) {
        final SimpleDateFormat timeSDF = new SimpleDateFormat("yyyy.MM.dd");
        if (startDate == null) {
            return;
        }

        if (layout != null) {
            LinkedList<PieData> pieceDataHolders = setTime(startDate, endDate, type, grade);

            if (pieceDataHolders.size() == 0) {
                textView15.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
            } else {
                textView15.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                pieChart.setData(pieceDataHolders, totalTimePrice);
            }
        }

    }
}

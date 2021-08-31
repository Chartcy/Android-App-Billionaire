package i.com.TrillionaireBill.chart;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import i.com.TrillionaireBill.DateDialog;
import i.com.TrillionaireBill.R;
import i.com.TrillionaireBill.TimeUtils;


public class SelectDateDialog extends DialogFragment {

    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.day)
    TextView day;
    @BindView(R.id.week)
    TextView week;
    @BindView(R.id.month)
    TextView month;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.confirm)
    TextView confirm;
    Unbinder unbinder;

    private SelectDateListener listener;

    public void setListener(SelectDateListener listener) {
        this.listener = listener;
    }

    public interface SelectDateListener {
        void timeClick(String type, Date start, Date end);
    }

    public static SelectDateDialog newInstance() {

        Bundle args = new Bundle();

        SelectDateDialog fragment = new SelectDateDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_date, container, false);
        unbinder = ButterKnife.bind(this, view);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date startDate = sdf.parse("1970-1-1");
                    Date endDate = sdf.parse("2100-12-31");
                    listener.timeClick("全部时间", startDate, endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dismiss();
            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.timeClick("今天", TimeUtils.getTimesmorning(), TimeUtils.getTimesnight());
                dismiss();
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.timeClick("本周", TimeUtils.getTimesWeekmorning(), TimeUtils.getTimesWeeknight());
                dismiss();
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.timeClick("本月", TimeUtils.getTimesMonthmorning(), TimeUtils.getTimesMonthnight());
                dismiss();
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.timeClick("本年", TimeUtils.getCurrentYearStartTime(), TimeUtils.getCurrentYearEndTime());
                dismiss();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = DateDialog.newInstance(DateDialog.GONE_TIME, "");
                dateDialog.show(getActivity().getFragmentManager(), "");
                dateDialog.setDatePickerListener(new DateDialog.DateListener() {
                    @Override
                    public void dateListener(String date) {
                        start.setText(date);
                    }
                });
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = DateDialog.newInstance(DateDialog.GONE_TIME, "");
                dateDialog.show(getActivity().getFragmentManager(), "");
                dateDialog.setDatePickerListener(new DateDialog.DateListener() {
                    @Override
                    public void dateListener(String date) {
                        end.setText(date);
                    }
                });
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date startDate = sdf.parse(start.getText().toString().concat( " 00:00:00"));
                    Date endDate = sdf.parse(end.getText().toString().concat(" 23:59:59"));
                    listener.timeClick("", startDate, endDate);
                    dismiss();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

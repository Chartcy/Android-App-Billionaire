package i.com.TrillionaireBill;


import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import i.com.TrillionaireBill.R;

public class DateDialog extends DialogFragment {

    @BindView(R.id.datepicker)
    DatePicker datepicker;
    @BindView(R.id.time_picker)
    TimePicker timePicker;
    @BindView(R.id.cancel)
    TextView cancels;
    @BindView(R.id.affirm)
    TextView affirmRecharge;
    DateListener dateListener;
    public static int SHOW_TIME = 1;
    public static int GONE_TIME = 2;
    protected Unbinder mUnBinder;

    public static DateDialog newInstance(int type, String time) {
        DateDialog dateDialog = new DateDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("DateType", type);
        bundle.putString("time", time);
        dateDialog.setArguments(bundle);
        return dateDialog;
    }

    public interface DateListener {
        void dateListener(String date);
    }

    public void setDatePickerListener(DateListener dateListener) {
        this.dateListener = dateListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_take_out_datepicker, container, false);
        int type = getArguments().getInt("DateType");
        mUnBinder = ButterKnife.bind(this, view);
        final boolean isTimeType = SHOW_TIME == type;
        if (isTimeType) {
            timePicker.setIs24HourView(true);
        } else {
            timePicker.setVisibility(View.GONE);
        }
        String mPickerTime = getArguments().getString("time", "");
        if (!TextUtils.isEmpty(mPickerTime)) {
            String[] split = mPickerTime.split(":");
            timePicker.setCurrentHour(Integer.parseInt(split[0].trim()));
            timePicker.setCurrentMinute(Integer.parseInt(split[1].trim()));
        }

        cancels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        affirmRecharge.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  String time = "";
                                                  String month = (datepicker.getMonth() + 1) >= 10 ? String.valueOf(datepicker.getMonth() + 1) : "0" + (datepicker.getMonth() + 1);
                                                  if (isTimeType) {
                                                      time = datepicker.getYear() + "-" + month + "-" + datepicker.getDayOfMonth() + " "
                                                              + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":" + "00";
                                                  } else {
                                                      time = datepicker.getYear() + "-" + month + "-" + datepicker.getDayOfMonth();
                                                  }
                                                  dateListener.dateListener(time);
                                                  dismiss();
                                              }
                                          }
        );
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        int type = getArguments().getInt("DateType");
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Window window = dialog.getWindow();
            int height = 0;
            if (type == SHOW_TIME) {
                height = getContext().getResources().getDimensionPixelSize(R.dimen.vip_date_dialog_height);
            } else {
                height = getContext().getResources().getDimensionPixelSize(R.dimen.vip_date_dialog_gone_time_height);
            }
            if (window != null) {
                window.setLayout(getContext().getResources().getDimensionPixelSize(R.dimen.vip_date_dialog_width), height);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) mUnBinder.unbind();
    }

}

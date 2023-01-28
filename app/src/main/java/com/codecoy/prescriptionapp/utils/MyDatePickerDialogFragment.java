package com.codecoy.prescriptionapp.utils;

import android.annotation.SuppressLint;

import com.codecoy.prescriptionapp.R;
import com.ycuwq.datepicker.date.DatePickerDialogFragment;

public class MyDatePickerDialogFragment extends DatePickerDialogFragment {


    @Override
    protected void initChild() {
        super.initChild();
        mCancelButton.setTextSize(20);
        mCancelButton.setTextColor(getActivity().getResources().getColor(R.color.white));
        mDecideButton.setTextColor(getActivity().getResources().getColor(R.color.white));
        mCancelButton.setBackgroundColor(getActivity().getResources().getColor(R.color.colorRed));
        mDecideButton.setBackgroundColor(getActivity().getResources().getColor(R.color.bg_color));
        mDecideButton.setTextSize(20);
        mDatePicker.setShowCurtain(false);
    }
}

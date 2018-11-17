package com.hao.customkeyboard.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hao.customkeyboard.R;
import com.hao.customkeyboard.utils.CarKeyBoardUtil;
import com.hao.customkeyboard.view.CarNumKeyboardView;

/**
 * Created by jhn on 2018/9/20.
 * Description :车牌号键盘
 */
public class CarNumDialogFragment extends DialogFragment {

    private static final String TAG = "PayDialogFragment";

    private CarKeyBoardUtil keyBoardUtil;
    private CarNumKeyboardView keyboardView;
    private EditText editText;
    private LinearLayout keyboard_parent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        //去掉dialog的标题，需要在setContentView()之前
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fgm_dialog_car_num, null);
        keyboard_parent = view.findViewById(R.id.keyboard_parent);
        editText = view.findViewById(R.id.fgm_car_num_et_carNum);
        keyboardView = view.findViewById(R.id.keyboard_ky);
        view.findViewById(R.id.fgm_car_num_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        keyBoardUtil = new CarKeyBoardUtil(keyboard_parent, keyboardView, editText);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (keyBoardUtil == null) {
                    keyBoardUtil = new CarKeyBoardUtil(keyboard_parent, keyboardView, editText);
                }
                keyBoardUtil.showKeyboard();
                return false;
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.windowAnimations = R.style.DialogFragmentAnimation;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        //去掉dialog默认的padding
//        window.getDecorView().setPadding(0, 0, 0, 0);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
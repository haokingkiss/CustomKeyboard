package com.hao.customkeyboard.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.hao.customkeyboard.R;
import com.hao.customkeyboard.view.PwdEditText;
import com.hao.customkeyboard.view.PwdKeyboardView;


/**
 * Created by jhn on 2018/9/20.
 * Description : 数字支付键盘
 */
public class PayDialogFragment extends DialogFragment implements PwdEditText.OnTextInputListener {

    private static final String TAG = "PayDialogFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        //去掉dialog的标题，需要在setContentView()之前
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.fgm_dialog_pay, null);
        ImageView exitImgView = view.findViewById(R.id.iv_exit);
        exitImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayDialogFragment.this.dismiss();
            }
        });
        final PwdEditText editText = view.findViewById(R.id.et_input);
        editText.setOnTextInputListener(this);
        PwdKeyboardView keyboardView = view.findViewById(R.id.key_board);
        keyboardView.setOnKeyListener(new PwdKeyboardView.OnKeyListener() {
            @Override
            public void onInput(String text) {
                Log.d(TAG, "onInput: text = " + text);
                editText.append(text);
                String content = editText.getText().toString();
                Log.d(TAG, "onInput: content = " + content);
            }

            @Override
            public void onDelete() {
                Log.d(TAG, "onDelete: ");
                String content = editText.getText().toString();
                if (content.length() > 0) {
                    editText.setText(content.substring(0, content.length() - 1));
                }
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

    @Override
    public void onComplete(String result) {
        Log.d(TAG, "onComplete: result = " + result);
        Toast.makeText(getContext(), "input complete : " + result, Toast.LENGTH_SHORT).show();
    }
}
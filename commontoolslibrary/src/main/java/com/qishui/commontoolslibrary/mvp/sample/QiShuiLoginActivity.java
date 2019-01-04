package com.qishui.commontoolslibrary.mvp.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.activity.QiShuiMainStyle01Activity;
import com.qishui.commontoolslibrary.click.QiShuiClick;
import com.qishui.commontoolslibrary.mvp.MvpBaseActivity;
import com.qishui.commontoolslibrary.notice.dialog.LoadingDialog;

/**
 * MVP 使用实例 login
 */
public class QiShuiLoginActivity extends MvpBaseActivity<QiShuiMVPContract.ILoginView, QiShuiLoginModel, QiShuiLoginPresenter> implements QiShuiMVPContract.ILoginView {

    private EditText etName;
    private EditText etPassword;

    @Override
    protected QiShuiLoginPresenter createPresenter() {
        return new QiShuiLoginPresenter(new QiShuiLoginModel());
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_login;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {

        bindView();
        login();
    }

    private void bindView() {
        etName = findViewById(R.id.qishui_login_et_name);
        etPassword = findViewById(R.id.qishui_login_et_password);
    }

    /**
     * 登录
     */
    public void login() {
        findViewById(R.id.qishui_login_btn).setOnClickListener(new QiShuiClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPresenter() != null) {
                    getPresenter().login(getText(etName), getText(etPassword));
                }
            }
        }));
    }

    @Override
    public void show(String message) {
        toast(message);
    }

    @Override
    public void loadingDialog(String text) {
        LoadingDialog.with(this).setText(text).showDialog();
    }

    @Override
    public void dissmissDialog() {
        LoadingDialog.with(this).dismissDialog();
    }

    @Override
    public void nextPage() {
         startActivity(QiShuiMainStyle01Activity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadingDialog.with(this).dismissDialog();
    }
}

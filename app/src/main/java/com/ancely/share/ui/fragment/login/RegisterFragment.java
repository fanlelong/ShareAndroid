package com.ancely.share.ui.fragment.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ancely.netan.base.BaseModelFragment;
import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.MainActivity;
import com.ancely.share.R;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.LoginBean;
import com.ancely.share.model.RegisterModelP;
import com.ancely.share.ui.activity.LoginActivity;
import com.ancely.share.utils.PreferenceUtils;
import com.ancely.share.viewmodel.RegisterVM;
import com.ancely.share.views.StrakeOutEditText;

import androidx.navigation.Navigation;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.fragment.login
 *  @文件名:   RegisterFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 4:53 PM
 *  @描述：    注册
 */
public class RegisterFragment extends BaseModelFragment<RegisterVM, HttpResult<LoginBean>> implements StrakeOutEditText.EditextChangedListener {
    private StrakeOutEditText mFragRegisterAccount;
    private StrakeOutEditText mFragRegisterPassword;
    private StrakeOutEditText mFragRegisterPasswordAgain;
    private Button mFragRegisterRegister;
    private TextView mJumpToLogin;
    private RegisterModelP mModelP;

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

        mFragRegisterAccount = findViewById(R.id.frag_register_account);
        mFragRegisterPassword = findViewById(R.id.frag_register_password);
        mFragRegisterPasswordAgain = findViewById(R.id.frag_register_password_again);
        mFragRegisterRegister = findViewById(R.id.frag_register_register);
        mJumpToLogin = findViewById(R.id.jump_to_login);
        mModelP = new RegisterModelP(this, RegisterVM.class);
    }

    @Override
    protected void initEvent() {
        mJumpToLogin.setOnClickListener(this);
        mFragRegisterRegister.setOnClickListener(this);
        mFragRegisterAccount.setEditextChangedListener(this);
        mFragRegisterPassword.setEditextChangedListener(this);
        mFragRegisterPasswordAgain.setEditextChangedListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_register;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof LoginActivity) {
            ((LoginActivity) getActivity()).setBarTitle(R.string.login_register_title);
            ((LoginActivity) getActivity()).setLeftIconIsShow(true);
        }
    }

    @Override
    public void onEditextChangedListener() {
        mFragRegisterRegister.setEnabled(mFragRegisterAccount.getEditext().length() >= 6 && mFragRegisterPassword.getEditext().length() >= 6 && mFragRegisterPasswordAgain.getEditext().length() >= 6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_register_register:
                mParams.put("username", mFragRegisterAccount.getEditext());
                mParams.put("password", mFragRegisterPassword.getEditext());
                mParams.put("repassword", mFragRegisterPasswordAgain.getEditext());
                mModelP.startRequestService(mParams);
                break;
            case R.id.jump_to_login:
                Navigation.findNavController(v).popBackStack();
                break;
            default:

                break;
        }
    }

    @Override
    public void accessSuccess(ResponseBean<HttpResult<LoginBean>> responseBean) {
        super.accessSuccess(responseBean);
        LoginBean loginBean = responseBean.body.getData();
        PreferenceUtils.saveString(getContext(), "userId", String.valueOf(loginBean.getId()));
        PreferenceUtils.saveString(getContext(), "userName", loginBean.getUsername());
        PreferenceUtils.saveBoolean(getContext(), "userName", true);
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }
}

package com.ancely.share.ui.fragment.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ancely.netan.request.mvvm.bean.ResponseBean;
import com.ancely.share.MainActivity;
import com.ancely.share.R;
import com.ancely.share.base.BaseFragment;
import com.ancely.share.base.HttpResult;
import com.ancely.share.bean.LoginBean;
import com.ancely.share.model.LoginModelP;
import com.ancely.share.ui.activity.LoginActivity;
import com.ancely.share.utils.PreferenceUtils;
import com.ancely.share.viewmodel.LoginVM;
import com.ancely.share.views.StrakeOutEditText;

import androidx.navigation.Navigation;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.fragment.login
 *  @文件名:   LoginFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 3:23 PM
 *  @描述：    TODO
 */
public class LoginFragment extends BaseFragment<LoginVM, LoginBean> implements StrakeOutEditText.EditextChangedListener {
    private LoginModelP mModelP;
    private StrakeOutEditText mFragLoginUsername;
    private StrakeOutEditText mFragLoginPassword;
    private Button mFragLoginLogin;
    private TextView mFragLoginCodeLogin;
    private TextView mJumpToRegister;

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        mFragLoginUsername = findViewById(R.id.frag_login_username);
        mFragLoginPassword = findViewById(R.id.frag_login_password);
        mFragLoginLogin = findViewById(R.id.frag_login_login);
        mFragLoginCodeLogin = findViewById(R.id.frag_login_code_login);
        mJumpToRegister = findViewById(R.id.jump_to_register);
        mModelP = new LoginModelP(this, LoginVM.class);

    }

    @Override
    protected void initEvent() {
        mFragLoginUsername.setEditextChangedListener(this);
        mFragLoginPassword.setEditextChangedListener(this);

        mFragLoginLogin.setOnClickListener(this);
        mFragLoginCodeLogin.setOnClickListener(this);
        mJumpToRegister.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_login;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }

    private void login() {
        if (TextUtils.isEmpty(mFragLoginUsername.getEditext())) {
            Toast.makeText(mContext, R.string.login_accound_error_tips, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mFragLoginPassword.getEditext())) {
            Toast.makeText(mContext, R.string.login_psw_error_tips, Toast.LENGTH_SHORT).show();
            return;
        }
        mParams.put("username", mFragLoginUsername.getEditext());
        mParams.put("password", mFragLoginPassword.getEditext());
        mModelP.startRequestService(mParams);
    }

    @Override
    public void onEditextChangedListener() {
        mFragLoginLogin.setEnabled(mFragLoginUsername.getEditext().length() >= 6 && mFragLoginPassword.getEditext().length() >= 6);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_login_login:
                login();
                break;
            case R.id.frag_login_code_login:
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_codeLoginFragment);
                break;
            case R.id.jump_to_register:
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
                break;
        }
    }


    @Override
    public void accessSuccess(ResponseBean<HttpResult<LoginBean>> responseBean) {
        super.accessSuccess(responseBean);
        LoginBean loginBean = responseBean.body.getData();
        PreferenceUtils.saveString("userId", String.valueOf(loginBean.getId()));
        PreferenceUtils.saveString( "userName", loginBean.getUsername());
        PreferenceUtils.saveBoolean( "userName", true);
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof LoginActivity) {
            ((LoginActivity) getActivity()).setBarTitle(R.string.login);
            ((LoginActivity) getActivity()).setLeftIconIsShow(false);
        }
    }
}

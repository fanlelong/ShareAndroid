package com.ancely.share.ui.fragment.login;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ancely.netan.base.BaseModelFragment;
import com.ancely.share.R;
import com.ancely.share.ui.activity.LoginActivity;
import com.ancely.share.views.StrakeOutEditText;

import androidx.navigation.Navigation;

/*
 *  @项目名：  ShareAndroid
 *  @包名：    com.ancely.share.ui.fragment.login
 *  @文件名:   CodeLoginFragment
 *  @创建者:   fanlelong
 *  @创建时间:  2019/5/31 4:53 PM
 *  @描述：    验证码登陆
 */
public class CodeLoginFragment extends BaseModelFragment {
    private StrakeOutEditText mFragLoginUsername;
    private StrakeOutEditText mFragLoginPassword;
    private TextView mFragCodeCode;
    private Button mFragLoginLogin;
    private TextView mFragCodeAccountLogin;
    private TextView mJumpToRegister;


    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        mFragLoginUsername = (StrakeOutEditText) findViewById(R.id.frag_login_username);
        mFragLoginPassword = (StrakeOutEditText) findViewById(R.id.frag_login_password);
        mFragCodeCode = (TextView) findViewById(R.id.frag_code_code);
        mFragLoginLogin = (Button) findViewById(R.id.frag_login_login);
        mFragCodeAccountLogin = (TextView) findViewById(R.id.frag_code_account_login);
        mJumpToRegister = (TextView) findViewById(R.id.jump_to_register);
    }

    @Override
    protected void initEvent() {
        mFragCodeAccountLogin.setOnClickListener(this);
        mJumpToRegister.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_code;
    }

    @Override
    public boolean isNeedCheckNetWork() {
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_code_account_login:
                Navigation.findNavController(v).popBackStack();
                break;
            case R.id.jump_to_register:
                Navigation.findNavController(v).navigate(R.id.action_codeLoginFragment_to_registerFragment);
                break;
            default:

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof LoginActivity) {
            ((LoginActivity) getActivity()).setBarTitle(R.string.login_code_title);
            ((LoginActivity) getActivity()).setLeftIconIsShow(true);
        }
    }
}

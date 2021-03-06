package com.xiaokun.advance_practive.im.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaokun.advance_practive.R;
import com.xiaokun.advance_practive.im.PdIMClient;
import com.xiaokun.advance_practive.im.PdMessageListener;
import com.xiaokun.advance_practive.im.PdOptions;
import com.xiaokun.advance_practive.im.database.bean.PdMessage;
import com.xiaokun.advance_practive.im.database.bean.msgBody.PdImgMsgBody;
import com.xiaokun.advance_practive.im.database.bean.msgBody.PdTextMsgBody;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2019/02/21
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class ImLoginActivity extends AppCompatActivity implements PdMessageListener {

    private static final String TAG = "ImLoginActivity";

    private TextInputEditText mEtUsername;
    private TextInputEditText mEtPassword;
    private EditText mEtReceiver;
    private Button mBtnSendMsg;
    private Button mBtnToList;
    private PdMessage mPdMessage;
    private Button mBtnSendImgMsg;
    private EditText mEtReceiver1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_im_login);
        PdOptions pdOptions = new PdOptions();
        pdOptions.setAppKey("12345678");
        PdIMClient.getInstance().init(this, pdOptions);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PdIMClient.getInstance().getChatManager().addMessageListener(this);
    }

    private void initView() {
        mEtUsername = findViewById(R.id.et_username);
        mEtPassword = findViewById(R.id.et_password);
        mEtReceiver = findViewById(R.id.et_receiver);
        mEtReceiver1 = findViewById(R.id.et_receiver1);
        mEtUsername.setText("test7");
        mEtPassword.setText("test7");
        mEtReceiver.setText("test8@peidou/pd");
        mEtReceiver1.setText("test8@peidou/pd");
        mBtnSendMsg = findViewById(R.id.btn_send_msg);
        mBtnToList = findViewById(R.id.btn_to_list);
        mBtnSendImgMsg = findViewById(R.id.btn_send_img_msg);
    }

    public void loginIm(View view) {
        String name = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
            PdIMClient.getInstance().login(name, password, new PdIMClient.LoginCallback() {
                @Override
                public void onSuccess() {
                    Log.e(TAG, "loginIm(" + TAG + ".java:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")" + "登录成功");
                    Toast.makeText(ImLoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    mBtnSendMsg.setEnabled(true);
                    mBtnToList.setEnabled(true);
                    mBtnSendImgMsg.setEnabled(true);
                }

                @Override
                public void onError(int code, String errorMsg) {
                    Toast.makeText(ImLoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "loginIm(" + TAG + ".java:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")" +
                            "code:" + code + ";errorMsg:" + errorMsg);
                }
            });

        }
    }

    public void sendMsg(View view) {
        String name = mEtReceiver.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "填写接收者", Toast.LENGTH_SHORT).show();
            return;
        }

        mPdMessage = PdMessage.createPdMessage(name, PdMessage.PDChatType.SINGLE);
        PdTextMsgBody pdTextMsgBody = new PdTextMsgBody();
        pdTextMsgBody.content = "你好我是小菜-更新";
        mPdMessage.pdMsgBody = pdTextMsgBody;
        mPdMessage.addBody(pdTextMsgBody);

        mPdMessage = PdIMClient.getInstance().getChatManager().sendMessage(mPdMessage);
    }

    public void sendImageMsg(View view) {
        String name = mEtReceiver.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "填写接收者", Toast.LENGTH_SHORT).show();
            return;
        }

        mPdMessage = PdMessage.createPdMessage(name, PdMessage.PDChatType.SINGLE);
        PdImgMsgBody pdImgMsgBody = new PdImgMsgBody();
        pdImgMsgBody.remoteUrl = "https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg";
        pdImgMsgBody.thumbnailRemoteUrl = "";
        mPdMessage.pdMsgBody = pdImgMsgBody;
        mPdMessage.addBody(pdImgMsgBody);

        mPdMessage = PdIMClient.getInstance().getChatManager().sendMessage(mPdMessage);
    }

    public void toConversationListPage(View view) {
        ImConversationListActivity.start(ImLoginActivity.this);
    }

    @Override
    public void onMessageReceived(PdMessage pdMessage) {

    }

    @Override
    public void onReceiptsMessageReceived(String msgId) {
        Log.e(TAG, "onReceiptsMessageReceived(" + TAG + ".java:" + Thread.currentThread().getStackTrace()[2].getLineNumber() + ")" +
                "回执成功" + msgId);
    }

    @Override
    public void onFailedMessageReceived(PdMessage pdMessage) {

    }

    public void logout(View view) {
        //退出
        PdIMClient.getInstance().logout();

    }
}

package com.ui.advise;

import com.C;
import com.app.annotation.apt.InstanceFactory;
import com.apt.ApiFactory;
import com.base.adapter.AdapterPresenter;
import com.base.util.ApiUtil;
import com.base.util.SpUtil;
import com.model.Message;
import com.model._User;

/**
 * Created by baixiaokang on 17/1/7.
 */
@InstanceFactory
public class AdvisePresenter extends AdviseContract.Presenter {

    @Override
    public void createMessage(String msg) {
        _User user = SpUtil.getUser();
        ApiFactory.createMessage(
                new Message(ApiUtil.getPointer(
                        new _User(C.ADMIN_ID)), msg,
                        ApiUtil.getPointer(user),
                        user.objectId))
                .subscribe(
                        res -> mView.sendSuc(),
                        e -> mView.showMsg("消息发送失败!"));
    }

    @Override
    public void initAdapterPresenter(AdapterPresenter mAdapterPresenter) {
        mAdapterPresenter
                .setRepository(ApiFactory::getMessageList)
                .setParam(C.INCLUDE, C.CREATER)
                .setParam(C.UID, SpUtil.getUser().objectId)
                .fetch();
    }
}

package hello.leavesC.presenter.viewModel;

import android.arch.lifecycle.MediatorLiveData;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;

import hello.leavesC.presenter.TransformUtil;
import hello.leavesC.presenter.event.SelfProfileEvent;
import hello.leavesC.presenter.model.ProfileModel;
import hello.leavesC.presenter.viewModel.base.BaseViewModel;

/**
 * 作者：叶应是叶
 * 时间：2018/10/1 9:46
 * 描述：
 */
public class SelfProfileViewModel extends BaseViewModel {

    private MediatorLiveData<ProfileModel> profileModelLiveData = new MediatorLiveData<>();

    private MediatorLiveData<SelfProfileEvent> eventLiveData = new MediatorLiveData<>();

    private TIMValueCallBack<TIMUserProfile> callBack = new TIMValueCallBack<TIMUserProfile>() {
        @Override
        public void onError(int i, String s) {
            showToast(s);
        }

        @Override
        public void onSuccess(TIMUserProfile timUserProfile) {
            ProfileModel profileModel = new ProfileModel();
            profileModel.setIdentifier(timUserProfile.getIdentifier());
            profileModel.setSelfSignature(timUserProfile.getSelfSignature());
            profileModel.setFaceUrl(timUserProfile.getFaceUrl());
            profileModel.setNickName(timUserProfile.getNickName());
            profileModel.setGenderType(timUserProfile.getGender());
            profileModel.setGender(TransformUtil.parseGender(timUserProfile.getGender()));
            profileModel.setAllowType(timUserProfile.getAllowType());
            profileModel.setAllow(TransformUtil.parseAllowType(timUserProfile.getAllowType()));
            profileModelLiveData.setValue(profileModel);
        }
    };

    public SelfProfileViewModel() {

    }

    public void getSelfProfile() {
        TIMFriendshipManager.getInstance().getSelfProfile(callBack);
    }

    public void logout() {
        showLoadingDialog("正在注销登录");
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                dismissLoadingDialog();
                showToast(s);
            }

            @Override
            public void onSuccess() {
                dismissLoadingDialog();
                eventLiveData.setValue(new SelfProfileEvent(SelfProfileEvent.LOGOUT_SUCCESS));
            }
        });
    }

    public void applyJoinGroup(String groupId, String reason) {
        showLoadingDialog("正在申请加入开发者交流群");
        TIMGroupManager.getInstance().applyJoinGroup(groupId, reason, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                dismissLoadingDialog();
                showToast(s);
            }

            @Override
            public void onSuccess() {
                dismissLoadingDialog();
                showToast("已加入开发者交流群");
            }
        });
    }

    public MediatorLiveData<ProfileModel> getProfileModelLiveData() {
        return profileModelLiveData;
    }

    public MediatorLiveData<SelfProfileEvent> getEventLiveData() {
        return eventLiveData;
    }

}
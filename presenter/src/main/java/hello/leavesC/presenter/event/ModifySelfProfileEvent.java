package hello.leavesC.presenter.event;

import hello.leavesC.presenter.event.base.BaseEvent;

/**
 * 作者：叶应是叶
 * 时间：2018/10/1 14:16
 * 描述：
 */
public class ModifySelfProfileEvent extends BaseEvent {

    public static final int MODIFY_SUCCESS = 10;

    public ModifySelfProfileEvent(int action) {
        super(action);
    }

}
package com.xiaokun.advance_practive.im.database.bean;

import com.xiaokun.advance_practive.im.PdIMClient;
import com.xiaokun.advance_practive.im.database.bean.msgBody.PdMsgBody;
import com.xiaokun.advance_practive.im.database.dao.ConversationDao;
import com.xiaokun.advance_practive.im.database.dao.MessageDao;

import java.util.List;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2019/02/18
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class PdConversation {

    public long conversationId;
    //是否转接
    public TransferType transfer;
    //是否历史会话
    public HistoryType history;
    //最后一条消息消息id
    public String lastMsgId;
    //单聊群聊
    public ConversationType conversationType;
    //会话用户id
    public long conversationUserId;
    //对方用户昵称
    public String nickName;
    //对方用户头像url
    public String avatar;
    //对方用户im账号
    public String imUserId;
    //未读消息数量
    public int unRead;

    /**
     * 获取未读消息数量
     *
     * @return
     */
    public int getUnReadCount() {
        List<PdMessage> pdMessages = MessageDao.getInstance().queryUnreadMsgsByConversationId(imUserId);
        return pdMessages.size();
    }

    /**
     * 将所有未读消息置成已读
     */
    public void markAllMessagesAsRead() {
        List<PdMessage> pdMessages = MessageDao.getInstance().queryUnreadMsgsByConversationId(imUserId);
        for (PdMessage pdMessage : pdMessages) {
            pdMessage.read = PdMessage.PDRead.READ;
            MessageDao.getInstance().updateMsg(pdMessage);
        }
    }

    /**
     * 当在聊天窗口监听接收消息时,调用此方法。条件是必须是当前的会话下
     *
     * @param imMsgId 消息id
     */
    public void markMessageAsRead(String imMsgId) {
        MessageDao.getInstance().updateMsgAsReadByMsgId(imMsgId);
    }

    /**
     * 获得最后一条消息
     *
     * @return
     */
    public PdMessage getLastMsg() {
        return MessageDao.getInstance().queryMsgById(lastMsgId);
    }

    /**
     * 从数据库中分页获取消息条数
     *
     * @param pageNum  页码 (从1开始)
     * @param pageSize 每页消息条数
     * @return
     */
    public List<PdMessage> loadMsgs(int pageNum, int pageSize) {
        if (pageNum < 1) {
            return null;
        }
        List<PdMessage> pdMessages = MessageDao.getInstance().loadMsgsPagination(imUserId, pageSize,
                (pageNum - 1) * pageSize);

        for (PdMessage pdMessage : pdMessages) {
            PdMsgBody pdMsgBody = PdIMClient.parseJson(pdMessage.msgContent, pdMessage.msgType);
            pdMessage.addBody(pdMsgBody);
        }

        return pdMessages;
    }

    public List<PdMessage> loadAllMsgsByConversationId(String toChatUserImId) {
        return MessageDao.getInstance().queryMsgsByConversationId(toChatUserImId);
    }

    public enum HistoryType {
        //
        Normal(1, "普通会话"),
        History(2, "历史会话");

        public int mType;
        public String mDesc;

        HistoryType(int type, String desc) {
            mType = type;
            mDesc = desc;
        }
    }

    public enum TransferType {
        //
        Normal(1, "普通会话"),
        Transfer(2, "转接会话"),;

        public int mType;
        public String mDesc;

        TransferType(int type, String desc) {
            mType = type;
            mDesc = desc;
        }
    }

    public static enum ConversationType {
        //
        Single(1, "单聊"),
        Group(2, "群聊"),
        ChatRoom(3, "聊天室");

        public int mType;
        public String mDesc;

        ConversationType(int type, String desc) {
            mType = type;
            mDesc = desc;
        }

    }

}

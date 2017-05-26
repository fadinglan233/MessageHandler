package protocol;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by fadinglan on 2017/5/4.
 */
public interface SocketMsg {

    String getIoTag();

    void setIoTag(String ioTag);
    String getFrom();

    void setFrom(String from);

    String getTo();

    void setTo(String to);

    int getMsgType();

    void setMsgType(int msgType);

    int getState();

    void setState(int state);

    int getMsgID();

    void setMsgID(int msgID);

    void setConnectType(String connectType);

    String getConnectType();
    JSONArray getParams();

    void setParams(JSONArray params);
}

package socket;

import com.alibaba.fastjson.JSONObject;
import systemcontroller.SystemController;
import view.Controller;

import java.net.URI;

public class SocketHandler {

    private static final SocketHandler instance = new SocketHandler();
    private final String LOGIN_ACTION = "LOGIN";
    private final String GET_PROCESS_ACTION = "GETPROCES";
    private final String KILL_PROCESS_ACTION = "KILL";
    private final String Action_Tag = "ACTION";
    private final String Target_Tag = "TARGET";
    private final String Data_Tag = "DATA";
    private final String User_ID_Tag = "UID";
    private final String Password_Tag = "PWD";
    private Socket socket = null;

    public static SocketHandler getInstance() {
        return instance;
    }

    public void init(URI uri) {
        socket = new Socket(uri);
        sendMessage("{\"ACTION\":\"LOGIN\"}");

    }

    public void sendMessage(String message) {
        socket.sendMessage(message);
    }

    public void handleMessage(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        String action = jsonObject.getString(Action_Tag);
        switch (action) {
            case LOGIN_ACTION:
                JSONObject logininfo = jsonObject.getJSONObject(Data_Tag);
                String userid = logininfo.getString(User_ID_Tag);
                String password = logininfo.getString(Password_Tag);
                System.out.println(logininfo.toJSONString());
                Controller.getInstance().setLoginInfo(userid, password);
                break;
            case GET_PROCESS_ACTION:
                String result = SystemController.getallprocesses();
                jsonObject.put("DATA", result);
                sendMessage(jsonObject.toJSONString());
                break;
            case KILL_PROCESS_ACTION:
                int pid = jsonObject.getInteger("DATA");
                int result_int = SystemController.killProcess(pid);
                jsonObject.put("DATA", result_int);
                sendMessage(jsonObject.toJSONString());
                break;
            default:
                break;
        }

    }


}

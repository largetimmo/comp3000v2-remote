package socket;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

public class SocketHandler {

    private Socket socket = null;
    private static final SocketHandler instance = new SocketHandler();
    public static SocketHandler getInstance(){
        return instance;
    }

    private final String LOGIN_ACTION = "LOGIN";
    private final String GET_PROCESS_ACTION = "GETPROCES";
    private final String KILL_PROCESS_ACTION = "KILL";
    private final String Action_Tag = "ACTION";
    private final String Target_Tag = "TARGET";
    private final String Data_Tag = "DATA";
    private final String User_ID_Tag = "UID";
    private final String Password_Tag = "PWD";



    public void init(URI uri){
        socket = new Socket(uri);
    }

    public void sendMessage(String message){
        socket.session.getAsyncRemote().sendText(message);
    }

    protected void handleMessage(String message){
        JSONObject jsonObject = JSONObject.parseObject(message);
        String action = jsonObject.getString(Action_Tag);
        switch (action) {
            case LOGIN_ACTION:
                JSONObject logininfo = jsonObject.getJSONObject(Data_Tag);
                String userid = logininfo.getString(User_ID_Tag);
                String password = logininfo.getString(Password_Tag);
                //todo set userid and pwd to screen
                break;
            case GET_PROCESS_ACTION:
                
                break;
            case KILL_PROCESS_ACTION:
                break;
            default:
                break;
        }

    }

    @ClientEndpoint
    class Socket{

        private Session session;

        private Socket(URI serveraddr){
            WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
            try {
                webSocketContainer.connectToServer(this,serveraddr);
            } catch (DeploymentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @OnOpen
        private void onOpen(Session session) {
            this.session = session;
        }

        @OnMessage
        private void onMessage(String message) {
            handleMessage(message);
        }

        @OnClose
        private void onClose() {

        }

        @OnError
        private void onError(Throwable throwable) {

        }
    }






}

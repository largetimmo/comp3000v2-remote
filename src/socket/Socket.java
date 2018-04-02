package socket;


import systemcontroller.SystemController;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class Socket{

    private Session session;

    public Socket(URI serveraddr){
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
    public void onOpen(Session session) {
        this.session = session;
        session.setMaxTextMessageBufferSize(32768);
        session.setMaxIdleTimeout(0);
    }

    @OnMessage
    public void onMessage(String message,Session session) throws Exception {
        System.out.println(message);
        SocketHandler.getInstance().handleMessage(message);
    }

    @OnClose
    private void onClose(CloseReason reason) {
        System.out.println("Close:"+reason.getReasonPhrase());
        System.out.println(reason.getCloseCode());
    }

    @OnError
    public void onError(Session error_session, Throwable throwable) {

    }
    public void sendMessage(String message){
        session.getAsyncRemote().sendText(message);
    }
}

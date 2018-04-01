package socket;


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
    }

    @OnMessage
    public void onMessage(String message,Session session) throws Exception {
        System.out.println(message);
        SocketHandler.getInstance().handleMessage(message);
    }

    @OnClose
    private void onClose() {

    }

    @OnError
    public void onError(Session error_session,Throwable throwable) {
        throwable.printStackTrace();
    }
    public void sendMessage(String message){
        session.getAsyncRemote().sendText(message);
    }
}

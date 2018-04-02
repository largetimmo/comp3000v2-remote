package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import socket.SocketHandler;
import systemcontroller.SystemController;

import java.net.URI;
import java.net.URISyntaxException;


public class Controller {
    private static Controller instance = null;
    @FXML
    private Button connectServer;
    @FXML
    private TextField address;
    @FXML
    private TextField port;
    @FXML
    private Text uid;
    @FXML
    private Text password;
    @FXML
    private TextField libpath;

    public static Controller getInstance() {
        return instance;
    }

    @FXML
    private void initialize() {
        instance = this;
    }

    @FXML
    private void setServer() throws URISyntaxException {
        String uri = "ws://" + address.getText() + ":" + port.getText() + "/ws/remote";
        //String uri = "ws://127.0.0.1:8080/ws/remote";
        if (libpath.getText().length() != 0) {
            SocketHandler.getInstance().init(new URI(uri));
            connectServer.setDisable(true);
            SystemController.loadLib(libpath.getText());
        }
    }

    public void setLoginInfo(String uid, String password) {
        this.uid.setText(uid);
        this.password.setText(password);
    }


}

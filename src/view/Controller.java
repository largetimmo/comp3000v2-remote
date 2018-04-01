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
    public static Controller getInstance(){
        return instance;
    }


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
    private void initialize(){
        instance = this;
    }

    @FXML
    private void setServer() throws URISyntaxException {
        //String uri = "ws://"+address.getText().toString() + ":" + port.getText().toString()+"/ws/remote";
        String uri = "ws://127.0.0.1:8080/ws/remote";
        SocketHandler.getInstance().init(new URI(uri));

    }

    public void setLoginInfo(String uid, String password){
        this.uid.setText(uid);
        this.password.setText(password);
    }



    public void printMessage(String message){
        System.out.println(message);
    }



}

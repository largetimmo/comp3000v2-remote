package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import socket.SocketHandler;
import systemcontroller.SystemController;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;


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

    public static Controller getInstance() {
        return instance;
    }

    @FXML
    private void initialize() {
        instance = this;
    }

    @FXML
    private void setServer() throws URISyntaxException, IOException {
        String uri = "ws://" + address.getText() + ":" + port.getText() + "/ws/remote";
        //String uri = "ws://127.0.0.1:8080/ws/remote";
        if (address.getText().length() != 0) {
            SocketHandler.getInstance().init(new URI(uri));
            connectServer.setDisable(true);
            InputStream is  = getClass().getResourceAsStream("/systemcontroller/libsystemcontroller.so");
            File file = File.createTempFile("syscontrollerlib","so");
            OutputStream os = new FileOutputStream(file);
            int result = 0;
            byte[] buffer = new byte[1024];
            while ((result = is.read(buffer)) != -1){
                os.write(buffer,0,result);
            }
            is.close();
            os.close();
            SystemController.loadLib(file.getAbsolutePath());
        }
    }

    public void setLoginInfo(String uid, String password) {
        this.uid.setText(uid);
        this.password.setText(password);
    }


}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User2  extends  Frame implements Runnable, ActionListener {
    TextField textField ;

    TextArea textArea;

    Button send;

    Socket socket;

    DataInputStream dataInputStream;

    DataOutputStream dataOutputStream;

    Thread chat;

    User2(){
        textField = new TextField("Message Here");
        textField.setBounds(100,300,300,80);
        textArea = new TextArea();
        textArea.setBounds(50,50,400,150);
        send = new Button("Send");
        send.setBounds(250,250,50,30);

        send.addActionListener(this);


        try {
            socket  = new Socket( "localhost",12000);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception E) {

        }
        add(textField);
        add(textArea);
        add(send);


        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();

        setSize(500,500);
        setTitle("John");
        setLayout(new FlowLayout());
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String msg = textField.getText();
        textArea.append("John : " + msg + "\n");
        textField.setText("");
        try {
             dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException ex){

        }

    }

    public static void main(String[] args) {
        new User2();
    }


    public void run() {

        while(true){
            try{
                String msg =dataInputStream.readUTF();
                textArea.append("Surya :  " + msg + "\n");
            }
            catch(Exception E){

            }
        }
    }
}

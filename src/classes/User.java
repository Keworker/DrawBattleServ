package classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
    private long id;
    private short gameStage;
    private InputStream in;
    private OutputStream out;
    private PrintWriter printWriter;

    public User(long id) {
        this.id = id;
        this.gameStage = 0;
    }

    public void setSocket(Socket socket) {
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            printWriter = new PrintWriter(out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public long getId() {
        return id;
    }

    public short getGameStage() {
        return gameStage;
    }

    @Override
    public String toString() {
        return "User {" + "\n\t\tid = " + id + ", \n\t\tgameStage = " + gameStage +
                ", \n\t\tin = " + in.toString() + ", \n\t\tout = " + out.toString() +
                ", \n\t\tprintWriter = " + printWriter.toString() + "\n\t}";
    }
}

package debug;

import classes.User;
import classes.rooms.Room;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientDebug1 {
    public static void main(String[] args) {
        System.out.println("!start");
        try {
            String users[] = new String[6];
            Socket socket = new Socket("127.0.0.1", 5000);
            PrintWriter pW = new PrintWriter(socket.getOutputStream());
            pW.println("init/0/Nick");
            pW.flush();
            new Thread() {
                Scanner scanner = new Scanner(socket.getInputStream());
                @Override
                public void run() {
                    next: while (true) {
                        if (scanner.hasNext()) {
                            String s[] = scanner.nextLine().split("/");
                            for (String ss : s) {
                                System.out.print(ss + "/");
                            }
                            System.out.print("\n");
                            try {
                                switch (s[0]) {
                                    case "request": {
                                        switch (s[1]) {
                                            case "text": {
                                                pW.println("text/some text");
                                                pW.flush();
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                    case "init": {
                                        continue next;
                                    }
                                    case "run": {
                                        if (Integer.parseInt(s[1]) == 200) {
                                            String teams[] = s[2].split(",");
                                            for (int i = 0; i < 6; i++) {
                                                users[i] = teams[i];
                                            }
                                            //Переход активити в андроид приложении
                                        }
                                        break;
                                    }
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
            new Thread() {
                Scanner in = new Scanner(System.in);
                @Override
                public void run() {
                    while (true) {
                        if (in.hasNext()) {
                            switch (in.nextLine()) {
                                case "run-game": {
                                    pW.println("run/");
                                    pW.flush();
                                    break;
                                }
                            }
                        }
                    }
                }
            }.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package debug;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientDebug1 {
    public static void main(String[] args) {
        System.out.println("10. We start work with client!");
        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            PrintWriter pW = new PrintWriter(socket.getOutputStream());
            pW.println(0);
            pW.flush();
            pW.println("Pre-build-test");
            pW.flush();
            new Thread() {
                Scanner scanner = new Scanner(socket.getInputStream());
                @Override
                public void run() {
                    while (true) {
                        if (scanner.hasNext()) {
                            System.out.println(scanner.nextLine());
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

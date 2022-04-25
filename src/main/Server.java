package main;

import classes.rooms.DTR;
import classes.rooms.MGR;
import classes.rooms.Room;
import classes.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    final String logStart = "\tLog-Debug:\n";
    public static final short MAIN_GAME = 0, DRAW_TOUR = 1;
    private static ServerSocket serverSocket;
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private Scanner in;
    int id = 0;
    int rId = 0;

    public Server() {
        in = new Scanner(System.in);
        rooms = new ArrayList<Room>();
        users = new ArrayList<User>();
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println(logStart + "22. main.Server is ready.");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(logStart + "25. Somebody is watching me!");
                User user = new User(id);
                id++;
                user.setSocket(socket);
                users.add(user);
                toRoom(user, in.nextInt());
                ReaderThread readerThread = new ReaderThread(user);
                readerThread.start();
                for (Room room : rooms) {
                    System.out.println(logStart + room.toString());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toRoom(User user, int mode) {
        for (Room room : rooms) {
            if (mode == 0 && room.getClass() == MGR.class) {
                if (room.addUser(user)) {
                    return;
                }
            }
            else if (mode == 1 && room.getClass() == DTR.class) {
                if (room.addUser(user)) {
                    return;
                }
            }
        }
        if (mode == 0) {
            MGR room = new MGR(rId);
            rId++;
            room.addUser(user);
            rooms.add(room);
        }
        else if (mode == 1) {
            DTR room = new DTR(rId);
            rId++;
            room.addUser(user);
            rooms.add(room);
        }
    }

    private class ReaderThread extends Thread {
        InputStream in;
        User user;

        public ReaderThread(User user) {
            this.in = user.getIn();
            this.user = user;
        }

        @Override
        public void run() {
            Scanner in = new Scanner(this.in);
            while (in.hasNext()) {
                String message = in.nextLine();
                System.out.println(logStart + "68. main.Server got It: " + message + " from "
                        + user.getId() + " room ");
                user.getPrintWriter().println("\tresponse 200:\n" + message);
                user.getPrintWriter().flush();
                for (Room room : rooms) {
                    System.out.println(logStart + room.toString());
                }
            }
        }
    }
}

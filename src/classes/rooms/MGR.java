package classes.rooms;

import classes.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class MGR extends Room {
    final String LC = "*lost connected*";
    String txts[][] = {{LC, LC, LC, LC, LC, LC}, {LC, LC, LC, LC, LC, LC}, {LC, LC, LC, LC, LC, LC}};
    int offset;

    public MGR(long id) {
        super(id);
        offset = 1;
        maxSize = 2;
    }

    @Override
    public boolean updateGameStage() {
        if (gameStage < maxSize) {
            if (gameStage % 2 == 0) {
                for (int i = 0; i < members.size(); i++) {
                    members.get(i).getPrintWriter().println("request/text");
                    members.get(i).getPrintWriter().flush();
                    Scanner in = new Scanner(members.get(i).getIn());
                    System.out.println(gameStage);
                    txts[gameStage / 2][(i + offset) % maxSize] = LC + "Random text";
                    for (int j = 0; j < 15; j++) {
                        try {
                            if (members.get(i).getIn().available() > 0) {
                                String s[] = in.nextLine().split("/");
                                if (s[0].matches("text")) {
                                    System.out.println(s[1]);
                                    txts[gameStage / 2][(i + offset) % maxSize] = s[1];
                                    break;
                                }
                            }
                            sleep(1000);
                        }
                        catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //К строке прибавляется рандомная история из 20-30 заранее забитых
                }

//                for (String[] txt : txts) {
//                    for (String t : txt) {
//                        System.out.print(t + " ");
//                    }
//                }
            }
            else {
                for (int i = 0; i < members.size(); i++) {
                    members.get(i).getPrintWriter().println("request/image");
                    members.get(i).getPrintWriter().flush();
                    Scanner in = new Scanner(members.get(i).getIn());
                    for (int j = 0; j < 15; j++) {
                        try {
                            if (members.get(i).getIn().available() > 0) {
                                String s[] = in.nextLine().split("/");
                                if (s[0].matches("image")) {
                                    //get image
                                    break;
                                }
                            }
                            sleep(1000);
                        }
                        catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (int i = 0; i < members.size(); i++) {
                    members.get(i).getPrintWriter().println(members.get(i).getId() + ": some image got");
                    members.get(i).getPrintWriter().flush();
                }
            }
        }
        else {
            while (members.size() > 0) {
                for (User u : members) {
                    try {
                        if (u.getIn().available() > 0) {
                            Scanner in = new Scanner(u.getIn());
                            if (in.nextLine() == "game/exit") {
                                for (int i = 0; i < members.size(); i++) {
                                    if (members.get(i).getId() == u.getId()) {
                                        members.remove(i);
                                        u.getPrintWriter().println("exit/200/goodbye");
                                        u.getPrintWriter().flush();
                                    }
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //удалить комнату с сервера
            return false;
        }
        offset++;
        gameStage++;
        new GameTimer().start();
        return true;
    }
}

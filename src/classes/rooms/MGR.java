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
        maxSize = 6;
    }

    @Override
    public boolean updateGameStage() {
        if (gameStage < 5) {
            if (gameStage % 2 == 0) {
                for (int i = 0; i < members.size(); i++) {
                    members.get(i).getPrintWriter().println("request/text");
                    members.get(i).getPrintWriter().flush();
                    Scanner in = new Scanner(members.get(i).getIn());
                    txts[gameStage / 2 - 1][(i + offset) % maxSize] = LC + "Random text";
                    for (int j = 0; j < 15; j++) {
                        try {
                            if (members.get(i).getIn().available() > 0) {
                                String s[] = in.nextLine().split("/");
                                if (s[0].matches("text")) {
                                    System.out.println(2);
                                    txts[gameStage / 2 - 1][(i + offset) % maxSize] = s[1];
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
                for (int i = 0; i < members.size(); i++) {
                    members.get(i).getPrintWriter().println(members.get(i).getId() + ": text/" + txts[gameStage / 2][
                            (i < offset) ? (maxSize - (offset - i)) : (i - offset)]);
                    members.get(i).getPrintWriter().flush();
                }

                for (String[] txt : txts) {
                    for (String t : txt) {
                        System.out.print(t + " ");
                    }
                }
            }
            else {

            }
        }
        else {
            //Разослать результаты
            return false;
        }
        offset++;
        gameStage++;
        new GameTimer().start();
        return true;
    }
}

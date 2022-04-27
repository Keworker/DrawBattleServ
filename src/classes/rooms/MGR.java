package classes.rooms;

import classes.User;

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
                    String cur = "AFFF"; //Рандомный текст из списка
                    for (int j = 0; j < 15; j++) {
                        if (in.hasNext()) {
                            String s[] = in.nextLine().split("/");
                            if (s[0] == "text") {
                                txts[gameStage / 2][(i + offset) % maxSize] = s[1];
                            }

                        }
                        try {
                            sleep(1000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        txts[gameStage / 2][(i + offset) % maxSize] = LC + cur;
                    }
                    //К строке прибавляется рандомная история из 20-30 заранее забитых
                }
                for (int i = 0; i < members.size(); i++) {
                    members.get(i).getPrintWriter().println("text/" + txts[gameStage / 2][
                            (i < offset) ? (maxSize - (offset - i)) : (i - offset)]);
                    members.get(i).getPrintWriter().flush();
                }
            }
            else {
                //Получить рисунки и разослать их другим участникам
            }
        }
        else {
            //Разослать результаты
            return false;
        }
        offset++;
        gameStage++;
        return true;
    }
}

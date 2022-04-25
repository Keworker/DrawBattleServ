package classes.rooms;

public class MGR extends Room {
    public MGR(long id) {
        super(id);
        maxSize = 6;
    }

    @Override
    public boolean updateGameStage() {
        if (gameStage < 5) {
            if (gameStage % 2 == 0) {
                //Получить текста и разослать их участникам
            }
            else {
                //Получить рисунки и разослать их другим участникам
            }
        }
        else {
            //Разослать результаты
            return false;
        }
    gameStage++;
        return true;
    }
}

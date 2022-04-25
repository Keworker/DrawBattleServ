package classes.rooms;

import classes.User;

import java.util.ArrayList;

public abstract class Room {
    protected long id;
    protected ArrayList<User> members;
    protected short maxSize;
    protected short gameStage;

    public Room(long id) {
        this.id = id;
        this.members = new ArrayList<>();
        this.gameStage = 0;
    }

    public abstract boolean updateGameStage();

    public boolean addUser(long id) {
        if (members.size() < maxSize) {
            members.add(new User(id));
            return true;
        }
        return false;
    }

    public boolean addUser(User user) {
        if (members.size() < maxSize) {
            members.add(user);
            user.getPrintWriter().println("\tresponse 200:\n" + this.toString());
            return true;
        }
        return false;
    }

    public long getId() {
        return id;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public short getGameStage() {
        return gameStage;
    }

    public class Timer extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                sleep(90000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateGameStage();
        }
    }

    @Override
    public String toString() {
        String s = "Room {" + "\n\tid = " + id + ", \n\tmembers:";
        for (int i = 0; i < members.size(); i++) {
            s += "\n\t" + members.get(i).toString();
        }
        return  s + ",\n\tmaxSize = " + maxSize + ",\n\tgameStage = " + gameStage + "\n}";
    }
}
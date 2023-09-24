/**
 * This file is part of a program that makes an initiative tracker for a table-top role-playing game
 *
 * This class extends turn, is for turns specifically for effects that last a specific duration
 *
 * @author Marcus Herbert
 * @version 1
 */

public class Effect extends Turn{

    private int duration;
    private boolean active;
    private int roundsLeft;

    public Effect (String n, int i, int d) {
        super(n, i);
        roundsLeft = duration = d;
        active = true;
    }

    public boolean isActive () {
        return active;
    }
    public int getRoundsLeft () {
        return roundsLeft;
    }
    public int getDuration () {
        return duration;
    }

    @Override
    public String toString () {
        if (!isActive()) {
            return super.toString() + ", is no longer active";
        }else{
            return super.toString();
        }
    }

    public void end () {
        active = false;
    }
    public void roundPass () {
        roundsLeft--;
        if (roundsLeft == 0) {
            end();
        }
    }

}
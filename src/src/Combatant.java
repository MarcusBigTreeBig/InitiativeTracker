/**
 * This file is part of a program that makes an initiative tracker for a table-top role-playing game
 *
 * This class extends turn, is for turns specifically for characters/combatants
 *
 * @author Marcus Herbert
 * @version 1
 */

public class Combatant extends Turn {

    private int lifeStatus;

    public Combatant(String n, int i) {
        super(n, i);
        lifeStatus = 1;
    }

    public boolean isAlive () {
        return lifeStatus == 1;
    }
    public boolean isDown () {
        return lifeStatus == 0;
    }
    public boolean isDead () {
        return lifeStatus == -1;
    }

    public void up () {
        lifeStatus = 1;
    }
    public void down () {
        lifeStatus = 0;
    }
    public void die () {
        lifeStatus = -1;
    }

    @Override
    public String toString () {
        switch (lifeStatus) {
            case 1:
                return super.toString();
            case 0:
                return super.toString() + ", roll death saves";
            case -1:
                return super.toString() + ", dead";
            default:
                return super.toString();
        }
    }

}
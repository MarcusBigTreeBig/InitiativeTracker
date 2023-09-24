/**
 * This file is part of a program that makes an initiative tracker for a table-top role-playing game
 *
 * This class defines the class Turn
 * each Turn object is used to represent one turn on the initiative ladder
 *
 * @author Marcus Herbert
 * @version 1
 */

public class Turn implements Comparable<Turn> {

    private String name;
    private int initiative;

    public Turn (String n, int i) {
        name = n;
        initiative = i;
    }

    public int getInitiative() {
        return initiative;
    }

    public String getName () {
        return name;
    }

    @Override
    public String toString () {
        return getInitiative() + ": " + getName() + "'s turn";
    }

    @Override
    public int compareTo(Turn t) {
        return t.getInitiative() - this.getInitiative();
    }

}
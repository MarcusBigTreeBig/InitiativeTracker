import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This file is part of a program that makes an initiative tracker for a table-top role-playing game
 *
 * This class contains the methods needed to run the main program in a loop
 *
 * @author Marcus Herbert
 * @version 1
 */

public class Main {

    /**
     * Runs a loop that takes user inputs to perform various actions
     *
     * @param args
     */

    public static void main(String[] args) {

        //defining variables to be used inside the main loop
        ArrayList<Turn> ladder = new ArrayList<Turn>();
        boolean running = true;
        Scanner input = new Scanner(System.in);
        char command;
        Turn current = null;
        boolean firstTurn = true;
        int round = 1;

        while (running) {//main loop that has several commands that do various things

            System.out.println("Enter Command:");
            command = input.nextLine().charAt(0);

            switch (command) {

                case 'n'://add new turn
                    ladder.add(newTurnFromUser());
                    Collections.sort(ladder);
                    break;

                case 'p'://print all turns
                    for (Turn i: ladder) {
                        System.out.println(i.getInitiative() + ": " + i.getName());
                    }
                    break;

                case ' '://next turn

                    //initialize the ladder to the top (bottom, but immediately put to top) of the initiative ladder if this is the first time hitting going to the next turn
                    if (firstTurn) {
                        firstTurn = false;
                        current = ladder.get(ladder.size()-1);
                    }

                    //proceeds to next turn
                    if (ladder.indexOf(current) == ladder.size() - 1) {
                        current = ladder.get(0);
                    }else{
                        current = ladder.get(ladder.indexOf(current) + 1);
                    }

                    //if this is the top of the initiative ladder, prints the round number
                    if (ladder.indexOf(current) == 0) {
                        System.out.println("Round: " + round++);
                    }

                    System.out.println(current);

                    //ends the effect if it's duration is up
                    if (current.getClass().getName() == "Effect") {
                        Effect e = (Effect) current;
                        e.roundPass();
                        if (!e.isActive()) {
                            System.out.println(current.getName() + " has ended");
                            current = ladder.get(ladder.indexOf(current) - 1);//to avoid skipping
                            ladder.remove(e);
                        }
                    }

                    break;

                case 'd'://downs a combatant
                    Combatant toDown = getCombatantFromUser(ladder);
                    toDown.down();
                    break;
                case 'u'://ups a combatant
                    Combatant toUp = getCombatantFromUser(ladder);
                    toUp.up();
                    break;
                case 'k'://kills a combatant
                    Combatant toKill = getCombatantFromUser(ladder);
                    toKill.die();
                    ladder.remove(toKill);
                    break;
                case 'e'://ends finite effect
                    Effect toEnd = getEffectFromUser(ladder);
                    toEnd.end();
                    ladder.remove(toEnd);
                    break;

                case 'c'://prints out all commands
                    printCommand('c', "Shows all actions");
                    printCommand('n', "Add a new turn");
                    printCommand('p', "Print a list of every turn");
                    printCommand(' ', "Move to the next turn");
                    printCommand('d', "Down a character");
                    printCommand('u', "Up a character");
                    printCommand('k', "Kill a character");
                    printCommand('e', "Ends a finite effect");
                    printCommand('x', "Exits the program");
                    break;

                case 'x'://exits the program
                    running = false;
                    break;

            }
            System.out.println();//for text organization

        }

    }

    /**
     * Takes user input to add a turn to the initiative tracker
     * Base class of Turn is used for infinite effects, as they don't need any extra functionality
     *
     * @return a Turn object that represents a turn in the initiative
     */
    public static Turn newTurnFromUser () {

        Scanner input = new Scanner (System.in);
        System.out.println("Name: ");
        String name = input.nextLine();
        System.out.println("Initiative: ");
        int initiative = input.nextInt();
        System.out.println("Type (Enter Number):\n1: Character\n2: Finite Effect\n3: Infinite Effect");

        switch (input.nextInt()) {
            case 1:
                return new Combatant(name, initiative);
            case 2:
                System.out.println("Duration: (in rounds)");
                int duration = input.nextInt();
                return new Effect(name, initiative, duration);
            case 3:
                return new Turn(name, initiative);
            default:
                return new Turn(name, initiative);
        }

    }

    /**
     * Displays all turns so that the user can pick from one of them
     *
     * @param turns are the turns the user can choose from
     * @return the one that the user picks
     */
    public static Turn getTurnFromUser (ArrayList<Turn> turns) {

        for (int i = 0; i < turns.size(); i++) {
            System.out.println((i+1) + ": " + turns.get(i).getName());
        }

        Scanner input = new Scanner(System.in);
        System.out.println("Enter number of which you choose:");
        return turns.get(input.nextInt()-1);

    }

    /**
     * Filters out only the Combatants in the given turns and gets the user to choose one
     *
     * @param turns are the turns that need to be filtered
     * @return the turn the user picked
     */
    public static Combatant getCombatantFromUser (ArrayList<Turn> turns) {
        ArrayList<Turn> combatants = new ArrayList<Turn>();
        for (Turn t: turns) {
            if (t.getClass().getName() == "Combatant") {
                combatants.add((Combatant)t);
            }
        }
        return (Combatant)getTurnFromUser(combatants);
    }
    /**
     * Filters out only the Effects in the given turns and gets the user to choose one
     *
     * @param turns are the turns that need to be filtered
     * @return the turn the user picked
     */
    public static Effect getEffectFromUser (ArrayList<Turn> turns) {
        ArrayList<Turn> effects = new ArrayList<Turn>();
        for (Turn t: turns) {
            if (t.getClass().getName() == "Effect") {
                effects.add((Effect)t);
            }
        }
        return (Effect)getTurnFromUser(effects);
    }

    public static void printCommand (char command, String purpose) {
        System.out.println(command + ": " + purpose);
    }

}
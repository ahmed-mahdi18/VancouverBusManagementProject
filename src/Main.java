import com.ahmed.busmanagement.ArrivalTimeSearch;
import com.ahmed.busmanagement.Between2Stops;
import com.ahmed.busmanagement.TernarySearchTree;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        File f = new File("./resources/stop_times.txt");
        JOptionPane.showMessageDialog(null, "Hello welcome to the Vancouver Bus Management App developed " +
                "to make finding your bus and what times the buses come easier! ");
        System.out.println("System loading......");
        boolean finished = false;
        int destination = 0;
        int source;
        boolean ID = false;
        Between2Stops sh;
        sh = new Between2Stops();
        TernarySearchTree tst = new TernarySearchTree("./resources/stops.txt");

        Scanner input = new Scanner(System.in);
        System.out.println("1 - Search for a bus stop");
        System.out.println("2 - Search for an arrival time");
        System.out.println("3 - Find shortest paths between 2 stops ");
        System.out.println("0 - To exit");
        System.out.print("select one of the above options by number 1,2,3 or 0 to exit : ");


        while (input.hasNext()) {
            int choice = input.next().charAt(0);
            input.nextLine();

            switch (choice) {
                case '0' -> {
                    System.out.println("Thank you for using our system, Have a safe journey!");
                    System.exit(0);
                }
                case '1' -> {
                    System.out.println("You chose option 1");
                    System.out.print("Enter the bus stop's full name or by the first few letters in the name: ");
                    String inputByUser = input.nextLine();
                    tst.busStopDetails(inputByUser.toUpperCase()).forEach(System.out::println);
                    System.out.print("select one of the above options by number 1,2,3 or 0 to exit : ");
                }
                case '2' -> {
                    System.out.println("You chose option 2");
                    do {
                        System.out.print("Enter Arrival time in the form (HH:MM:SS): ");
                        ArrayList<String> times = new ArrayList<>();
                        finished = false;
                        ArrayList<String> stopT = ArrivalTimeSearch.getFileLineByLine(f);
                        try {

                            String userInput = input.next();
                            if (!userInput.matches("(([0-1]?[0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]")) {
                                if (((int) userInput.charAt(1) >= 4) && (userInput.charAt(5) == ':') && (userInput.charAt(2) == ':')) {
                                    System.out.println("Input is more than 23:59:59");
                                } else
                                    System.out.println("Wrong Format, Has to be in the form HH:MM:SS, Try again");
                            } else {
                                if (userInput.length() == 7) {
                                    userInput = userInput + " ";
                                }
                                int i = 0, stopTSize = stopT.size();
                                while (i < stopTSize) {
                                    String s = stopT.get(i);
                                    if (s.contains(userInput)) times.add(s);
                                    finished = true;
                                    i++;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Wrong Input!, Has to be in the form HH:MM:SS, Try again!");
                        }
                        if (times.size() > 0) {
                            System.out.println("Loading......");
                            ArrivalTimeSearch.PrintTimes(times);
                        } else if (finished) {
                            System.out.println("No arrival time found.");
                        }
                    } while (!finished);
                    System.out.print("select one of the above options by number 1,2,3 or 0 to exit : ");
                }
                case '3' -> {
                    boolean end = false;
                    System.out.println("You chose Option 3");
                            System.out.print("Enter the stop you are departing from: ");
                            try {
                                source = input.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Incorrect input, try again");
                                source = 0;
                                ID = false;
                            }
                            input.nextLine();
                            {
                                int i = 0;
                                while (i < Between2Stops.IDs.size()) {
                                    if (source == Between2Stops.IDs.get(i)) {
                                        ID = true;
                                        break;
                                    }
                                    i++;
                                }

                            }
                        if (ID) {
                            ID = false;
                            System.out.print("Enter the stop you want to arrive at: ");
                            try {
                                destination = input.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Incorrect input, try again");
                                destination = 0;
                                ID = false;
                            }
                            input.nextLine();
                            int i = Between2Stops.IDs.size() - 1;
                            while (i >= 0) {
                                if (destination == Between2Stops.IDs.get(i)) {
                                    ID = true;
                                    break;
                                }
                                i--;
                            }
                        }
                        if (ID) {
                            if (Between2Stops.weight == -1) {
                                System.err.println("Path does not exist\n");
                            } else {
                                String route = sh.shortestPath(destination, source);
                                System.out.println("the Weight is : " + Between2Stops.weight);
                                System.out.println("the route you will be taking will be : " + route);
                                System.out.print("select one of the above options by number 1,2,3 or 0 to exit : ");
                            }
                        }
                        if (!ID) {
                            System.out.println("Stop number does not exist");
                            System.out.print("select one of the above options by number 1,2,3 or 0 to exit : ");
                        }

                }
                    default -> {
                        System.out.println("Wrong input, try again");
                        System.out.println("1 - Search for a bus stop");
                        System.out.println("2 - Search for an arrival time");
                        System.out.println("3 - Find shortest paths between 2 stops ");
                        System.out.println("0 - To exit");
                        System.out.print("select one of the above options by number 1,2,3 or 0 to exit : ");
                    }
                }
            }
        }
}





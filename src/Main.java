import com.ahmed.busmanagement.ArrivalTimeSearch;
import com.ahmed.busmanagement.TernarySearchTree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        File f = new File("./resources/stop_times.txt");
        System.out.println("Hello welcome to the Vancouver Bus Management App");
        boolean finished = false;
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
                    System.out.println("Thank you, have a safe journey");
                    System.exit(0);
                }
                case '1' -> {
                    System.out.println("you chose option 1");
                    System.out.print("enter the bus stop's full name or by the first few letters in its name: ");
                    String inputByUser = input.nextLine();
                    tst.busStopDetails(inputByUser.toUpperCase()).forEach(System.out::println);
                    System.out.print("select one of the above options by number 1,2,3 or 0 to exit : ");
                }
                case '2' -> {
                    System.out.println("you chose option 2");
                    do {

                        System.out.println("Enter Arrival time in the form (HH:MM:SS) ");
                        ArrayList times = new ArrayList();
                        finished = false;
                        ArrayList<String> stopT = ArrivalTimeSearch.getFileLineByLine(f);
                        try {

                            String userInput = input.next();
                            if (!userInput.matches("(([0-1]?[0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]")) {
                                if (((int) userInput.charAt(1) >= 4) && (userInput.charAt(5) == ':') && (userInput.charAt(2) == ':')) {
                                    System.out.println("Input is more than 23:59:59");
                                } else
                                    System.out.println("wrong Format, has to be HH:MM:SS, try again");
                            } else {
                                if (userInput.length() == 7) {
                                    userInput = userInput + " ";
                                }
                                for (String s : stopT) {
                                    if (s.contains(userInput)) times.add(s);
                                    finished = true;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Wrong Input!, has to be in the form HH:MM:SS");
                        }
                        if (times.size() > 0) {
                            ArrivalTimeSearch.PrintTimes(times);
                        } else if (finished) {
                            System.out.println("No arrival time found.");
                        }
                    }while(!finished);
                    System.out.print("select one of the above options by number 1,2,3 or 0 to exit : ");
                }
                case '3' -> {
                    System.out.println("You chose Option 3");
                    System.out.println("in progress");
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



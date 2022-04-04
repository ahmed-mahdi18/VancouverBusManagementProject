import com.ahmed.busmanagement.ArrivalTimeSearch;
import com.ahmed.busmanagement.TernarySearchTree;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        TernarySearchTree tst = new TernarySearchTree("./resources/stops.txt");
        Scanner input = new Scanner(System.in);
        System.out.println("select one of the following options by number 1,2,3 or 0 to exit");
        System.out.println("1. Search for a bus stop");
        System.out.println("2. Find shortest paths between 2 stops ");
        System.out.println("3. Search for an arrival time");
        System.out.println("0. To exit");
        if (input.hasNextInt()) {
            int selected = input.nextInt();
            input.nextLine();
            if (selected == 0) {
                System.out.println("thank you!");
                input.close();
            }
            if (selected < 0 && selected > 3) {
                System.out.println("please enter a valid number");
            }else if (selected == 1) {
                    System.out.print("enter the bus stop's full name or by the first few letters in its name: ");
                    String inputByUser = input.nextLine();
                    tst.busStopDetails(inputByUser.toUpperCase()).forEach(System.out::println);
                }
        }
        }





































}


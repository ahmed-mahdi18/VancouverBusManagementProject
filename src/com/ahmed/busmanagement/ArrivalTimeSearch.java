package com.ahmed.busmanagement;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.lang.Integer.*;


public class ArrivalTimeSearch {



    public static ArrayList<String> getFileLineByLine(File f) throws IOException {
        return (ArrayList<String>) Files.readAllLines(Paths.get("./resources/stop_times.txt"));
    }


    public static void getResults(File filename) throws IOException {
            ArrayList times = new ArrayList();
            ArrayList<String> stopT = getFileLineByLine(filename);
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Arrival time (HH:MM:SS) ");
            boolean finished = false;
            try {
                String userInput = input.next();
                if (!userInput.matches("(([0-1]?[0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]")) {
                    if (((int) userInput.charAt(1) >= 4) && (userInput.charAt(5) == ':') && (userInput.charAt(2) == ':')) {
                        System.out.println("Input is more than 23:59:59");
                    } else
                        System.out.println("Wrong Format");
                } else {
                    if (userInput.length() == 7) {
                        userInput = userInput + " ";
                    } for (String s : stopT) {
                        if (s.contains(userInput)) times.add(s);
                        finished = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("Wrong Input!");
            } if (times.size() > 0) {
               PrintTimes(times);
            } else if (finished) {
                System.out.println("No arrival time found.");
            }
    }

    public static void PrintTimes(ArrayList<String> l) {

        l.sort(Comparator.comparingInt(ArrivalTimeSearch::parseId));
        System.out.println("trip_id, arrival_time, departure_time, stop_id, stop_sequence, stop_headsign, pickup_type, drop_off_type, shape_dist_traveled  ");
        for (int i = 0, listSize = l.size(); i < listSize; i++) {
            String string = l.get(i);
            System.out.println(string);
        }
    }
    static int parseId(String line) {
        var i = parseInt(line.split(",")[0]);
        return i;
    }


}


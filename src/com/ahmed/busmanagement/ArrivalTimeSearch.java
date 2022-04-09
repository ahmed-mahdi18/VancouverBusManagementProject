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


    public static void PrintTimes(ArrayList<String> l) {

        l.sort(Comparator.comparingInt(ArrivalTimeSearch::siftID));
        System.out.println("These are the times Corresponding to the trip_id, arrival_time," +
                " departure_time, stop_id, stop_sequence, stop_headsign, pickup_type, drop_off_type and shape_dist_traveled");
        int i = 0, listSize = l.size();
        while (i < listSize) {
            String string = l.get(i);
            System.out.println(string);
            i++;
        }
    }

    static int siftID(String line) {
       var i = parseInt(line.split(",")[0]);
        return i;
    }


}


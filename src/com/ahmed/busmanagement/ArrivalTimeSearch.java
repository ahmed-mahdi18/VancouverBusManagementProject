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
        System.out.println("these are the times");
        for (int i = 0, listSize = l.size(); i < listSize; i++) {
            String string = l.get(i);
            System.out.println(string);
        }
    }

    static int siftID(String line) {
       var i = parseInt(line.split(",")[0]);
        return i;
    }


}


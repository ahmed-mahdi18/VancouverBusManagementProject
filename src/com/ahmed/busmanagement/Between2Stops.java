package com.ahmed.busmanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Between2Stops {
    public static ArrayList<Integer> IDs;
    public static double weight;
    private static DirectedGraph graph;


    public Between2Stops() throws FileNotFoundException {
            File file = new File("./resources/stops.txt");
            IDs = new ArrayList<>();
            Scanner scanner = new Scanner(file);
            String preventFormatException = scanner.nextLine();
            if (scanner.hasNextLine()) {
                String[] stopSplit = scanner.nextLine().split(",");
                IDs.add(Integer.parseInt(stopSplit[0]));
                if (scanner.hasNextLine()) {
                    do {
                        stopSplit = scanner.nextLine().split(",");
                        IDs.add(Integer.parseInt(stopSplit[0]));
                    } while (scanner.hasNextLine());
                }
            }
            graph = new DirectedGraph(15000);
            File stopTimes = new File("./resources/stop_times.txt");
            Scanner scanner1 = new Scanner(stopTimes);
            preventFormatException = scanner1.nextLine();

            if (scanner1.hasNext()) {
                do {
                    String row = scanner1.nextLine();
                    if (!scanner1.hasNext()) {
                        break;
                    }
                    String row2 = scanner1.nextLine();
                    String[] rowSplit = row.split(",");
                    String[] row2Split = row2.split(",");

                    if (rowSplit[0].equals(row2Split[0]))
                    {
                        int x = Integer.parseInt(rowSplit[3]);
                        int z = Integer.parseInt(row2Split[3]);
                        DirectedEdge edge = new DirectedEdge(x, z, 1);
                        graph.addEdge(edge);
                    }
                } while (scanner1.hasNext());
            }

            File transferTimes = new File("./resources/transfers.txt");
            Scanner scanner2 = new Scanner(transferTimes);
            preventFormatException = scanner2.nextLine();
            if (scanner2.hasNextLine()) {
                do {
                    String row = scanner2.nextLine();
                    String[] rowSplit = row.split(",");
                    int x = Integer.parseInt(rowSplit[0]);
                    int z = Integer.parseInt(rowSplit[1]);
                    if (Integer.parseInt(rowSplit[2]) == 0) {
                        DirectedEdge e = new DirectedEdge(x, z, 2);
                        graph.addEdge(e);
                    } else if (Integer.parseInt(rowSplit[2]) == 2) {
                        double transferTime = Double.parseDouble(rowSplit[3]);
                        DirectedEdge edge = new DirectedEdge(x, z, transferTime / 100);
                        graph.addEdge(edge);
                    }
                } while (scanner2.hasNextLine());
            }
            scanner2.close();
            scanner1.close();
            scanner.close();
        }

    public String shortestPath(int startStop, int destStop) {

        DijkstraShortestPaths route = new DijkstraShortestPaths(graph, startStop);
        if (route.hasPathTo(destStop)) {
            route.pathTo(destStop);
            route.distTo(destStop);
            weight = route.distTo(destStop);
           Iterable<DirectedEdge> ShortestPath = route.pathTo(destStop);

            return ShortestPath.toString();
        }
        return "-1";
    }
}




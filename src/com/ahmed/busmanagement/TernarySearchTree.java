package com.ahmed.busmanagement;

// source https://algs4.cs.princeton.edu/52trie/TST.java.html

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TernarySearchTree<Value> {

    private HashMap<String, String> map = new HashMap<>();
    private int n;
    private Node<Value> root;

    private static class Node<Value> {
        private char c;
        private Node<Value> left, mid, right;
        private String val;
    }

    public TernarySearchTree(String filename) {
        File file = new File(filename);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (sc == null) throw new AssertionError();
        sc.nextLine();
        map = new HashMap<>();

        while (sc.hasNextLine()) {
            String all_line = sc.nextLine();
            String[] arr = all_line.split(",");
            String stopID = arr[0];
            StringBuilder s = new StringBuilder();
            s.append(arr[2]);
            if (s.substring(0, 2).equals("NB") || s.substring(0, 2).equals("SB") || s.substring(0, 2).equals("WB")
                    || s.substring(0, 2).equals("EB")) {
                String st  = s.substring(0, 2);
                s.delete(0, 3);
                s.append(" ").append(st);
            } else if ((s.substring(0, 8).equals("fs"))) {
                String direct = s.substring(0, 11);
                s.delete(0, 12);
                s.append(" ").append(direct);
            }
            String stopName = s.toString();
            this.put(stopName, stopID);

            StringBuilder sDetails = new StringBuilder();
            List<StringBuilder> asList = Arrays.asList(sDetails.append(" STOP_ID: ").append(stopID), sDetails.append(" STOP_CODE: ").append(arr[1]),
                    sDetails.append(" STOP_NAME: ").append(stopName), sDetails.append(" STOP_DESC: ").append(arr[3]),
                    sDetails.append(" STOP_LAT: ").append(arr[4]), sDetails.append(" STOP_LON: ").append(arr[5]),
                    sDetails.append(" ZONE_ID: ").append(arr[6]), sDetails.append(" STOP_URL").append(arr[7]), sDetails.append(" LOCATION_TYPE: ").append(arr[8]));
            for (StringBuilder stringBuilder : asList) {
                stringBuilder.append(" ");
            }
            String stopInformation = sDetails.toString();
            map.put(stopID, stopInformation);

        }
    }

    public List<String> busStopDetails(String input) {
        List<String> stopsList = new LinkedList<>();
        Iterator<String> iterator = this.keysWithPrefix(input).iterator();
        while (iterator.hasNext()) {
            String info = iterator.next();
            stopsList.add(map.get(this.get(info)));
        }
        if (!stopsList.isEmpty()) {
            return stopsList;
        }
        stopsList.add("stop is not valid\n");
        return stopsList;
    }

    public int size() {
        return n;
    }

    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }


    public String get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node<Value> x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }


    private Node<Value> get(Node<Value> x, String key, int d) {
        if (x == null) return null;
        if (key.length() == 0)
            throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(d);
        if (c < x.c)
            return get(x.left,  key, d);
        else if (c > x.c)
            return get(x.right, key, d);
        else if (d < key.length() - 1)
            return get(x.mid,   key, d+1);
        else
            return x;
    }


    public void put(String key, String val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!contains(key)) n++;
        else if(val == null) n--;       // delete existing key
        root = put(root, key, val, 0);
    }

    private Node<Value> put(Node<Value> x, String key, String val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node<Value>();
            x.c = c;
        }
        if (c < x.c)
            x.left  = put(x.left,  key, val, d);
        else if (c > x.c)
            x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)
            x.mid = put(x.mid,   key, val, d+1);
        else
            x.val = val;
        return x;
    }

    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
        }
        if (query.length() == 0) return null;
        int length = 0;
        Node<Value> x = root;
        int i = 0;
        while (x != null && i < query.length()) {
            char c = query.charAt(i);
            if
            (c < x.c) x = x.left;
            else if
            (c > x.c) x = x.right;
            else {
                    i++;
                    if (x.val != null) length = i;
                    x = x.mid;
            }
        }
        return query.substring(0, length);
    }

    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> queue = new Queue<String>();
        Node<Value> x = get(root, prefix, 0);
        if (x == null)
            return queue;
        if (x.val != null)
            queue.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    private void collect(Node<Value> x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix, queue);
        if (x.val != null) {
            queue.enqueue(prefix.toString() + x.c);
        }
        collect(x.mid,   prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }


    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), 0, pattern, queue);
        return queue;
    }

    private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
        if (x == null) return;
        char c = pattern.charAt(i);
        if (c == '.' || c < x.c)
            collect(x.left, prefix, i, pattern, queue);
        if (c == '.' || c == x.c) {
            if (i == pattern.length() - 1 && x.val != null)
                queue.enqueue(prefix.toString() + x.c);
            if (i < pattern.length() - 1) {
                collect(x.mid, prefix.append(x.c), i+1, pattern, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (c == '.' || c > x.c) collect(x.right, prefix, i, pattern, queue);
    }
}



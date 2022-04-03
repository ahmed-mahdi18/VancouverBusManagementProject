package com.ahmed.busmanagement;

// source https://algs4.cs.princeton.edu/52trie/TST.java.html

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TernarySearchTree<Value> {

    private HashMap<String, String> map;
    private int n;              // size
    private Node<Value> root;   // root of TST

    private static class Node<Value> {
        private char c;                        // character
        private Node<Value> left, mid, right;  // left, middle, and right subtries
        private String val;                     // value associated with string
    }

    /**
     * Initializes an empty string symbol table.
     */
    public TernarySearchTree() {
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public String get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node<Value> x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    // return subtrie corresponding to given key
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

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is {@code null}, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
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
            x.mid   = put(x.mid,   key, val, d+1);
        else
            x.val   = val;
        return x;
    }

    /**
     * Returns the string in the symbol table that is the longest prefix of {@code query},
     * or {@code null}, if no such string.
     * @param query the query string
     * @return the string in the symbol table that is the longest prefix of {@code query},
     *     or {@code null} if no such string
     * @throws IllegalArgumentException if {@code query} is {@code null}
     */
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

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     * @return all keys in the symbol table as an {@code Iterable}
     */
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     * @param prefix the prefix
     * @return all of the keys in the set that start with {@code prefix},
     *     as an iterable
     * @throws IllegalArgumentException if {@code prefix} is {@code null}
     */
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

    // all keys in subtrie rooted at x with given prefix
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


    /**
     * Returns all of the keys in the symbol table that match {@code pattern},
     * where the character '.' is interpreted as a wildcard character.
     * @param pattern the pattern
     * @return all of the keys in the symbol table that match {@code pattern},
     *     as an iterable, where . is treated as a wildcard character.
     */
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
        stopsList.add("stop does not exist\n");
        return stopsList;
    }



    public TernarySearchTree(String filename) {
        File file = new File(filename);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert sc != null;
        sc.nextLine();
        map = new HashMap<String, String>();

        while (sc.hasNextLine()) {
            String all_line = sc.nextLine();
            String[] arr = all_line.split(",");
            String stopID = arr[0];
            StringBuilder s = new StringBuilder();
            s.append(arr[2]);
            if ("fs".equals(s.substring(0, 8))) {
                String dir = s.substring(0, 11);
                s.delete(0, 12);
                s.append(" ").append(dir);
            }
            if (s.substring(0, 2).equals("nb") || s.substring(0, 2).equals("sb")
                    || s.substring(0, 2).equals("wb") || s.substring(0, 2).equals("eb")) {
                    String dir = s.substring(0, 2);
                    s.delete(0, 3);
                    s.append(" ").append(dir);
            }
            String stopName = s.toString();
            this.put(stopName, stopID);

            StringBuilder sDetails = new StringBuilder();
            List<StringBuilder> asList = Arrays.asList(sDetails.append(" stop_id: ").append(stopID), sDetails.append(" stop_code: ").append(arr[1]),
                    sDetails.append(" stop_name: ").append(stopName), sDetails.append(" stop_desc: ").append(arr[3]),
                    sDetails.append(" stop_lat: ").append(arr[4]), sDetails.append(" stop_lon: ").append(arr[5]),
                    sDetails.append(" zone_id: ").append(arr[6]), sDetails.append(" location_type: ").append(arr[8]));
            for (StringBuilder stringBuilder : asList) {
                stringBuilder.append(" ");
            }
            String stopInformation = sDetails.toString();
            map.put(stopID, stopInformation);

        }
    }

















}



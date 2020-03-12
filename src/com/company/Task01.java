package com.company;

import textprocessing.BoyerMoore;
import textprocessing.BruteForceMatch;
import textprocessing.In;
import textprocessing.KMP;

import java.nio.file.*;
import java.util.ArrayList;

/**
 * @author Maxi Agrippa
 */
public class Task01
{
    private In in;
    private Path DataPath = Paths.get("./src/Data/Hard disk.txt");
    public ArrayList<String> Data = new ArrayList<String>();
    public static String[] PATTERNS = {"hard", "disk", "hard disk", "hard drive", "hard dist", "xltpru"};
    public String stringdata = "";

    // Algorithms
    BoyerMoore boyerMoore = null;
    KMP kmp = null;

    // Constructor, Get Data and store it.
    public Task01 ()
    {
        try
        {
            in = new In(DataPath.toAbsolutePath().toString());
            while (!in.isEmpty())
            {
                Data.add(in.readLine());
            }
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
        // put all data in a string.
        for (String s : Data)
        {
            stringdata += s;
        }
    }

    public ArrayList<Integer> boyerMooreSearch (String pattern)
    {
        String data = stringdata;
        // initialize BoyerMoore with pattern
        boyerMoore = new BoyerMoore(pattern);
        // store results
        ArrayList<Integer> offsets = new ArrayList<Integer>();
        // if there is the pattern
        boolean has = true;
        // temporary store Offset
        int tempOffset = 0;
        // keep search if there is the pattern
        while (has)
        {
            // find the pattern and return the relative offset.
            tempOffset = boyerMoore.search(data);
            // check if the search is ended.
            if (tempOffset == data.length())
            {
                has = false;
            }
            else
            {
                // put offset into offset ArrayList.
                offsets.add(tempOffset);
                // set the tempOffset to the end of the pattern
                tempOffset += "hard".length();
                // remove that pattern in next search.
                data = data.substring(tempOffset, data.length());
            }
        }
        // calculate the absolute offset
        for (int i = 1; i < offsets.size(); i++)
        {
            offsets.set(i, (offsets.get(i) + offsets.get(i - 1)));
        }
        return offsets;
    }

    public ArrayList<Integer> bruteForceMatchSearch (String pattern)
    {
        String data = stringdata;
        // store results
        ArrayList<Integer> offsets = new ArrayList<Integer>();
        // if there is the pattern
        boolean has = true;
        // temporary store Offset
        int tempOffset = 0;
        // keep search if there is the pattern
        while (has)
        {
            // find the pattern and return the relative offset.
            tempOffset = BruteForceMatch.search1(pattern, data);
            // check if the search is ended.
            if (tempOffset == data.length())
            {
                has = false;
            }
            else
            {
                // put offset into offset ArrayList.
                offsets.add(tempOffset);
                // set the tempOffset to the end of the pattern
                tempOffset += "hard".length();
                // remove that pattern in next search.
                data = data.substring(tempOffset, data.length());
            }
        }
        // calculate the absolute offset
        for (int i = 1; i < offsets.size(); i++)
        {
            offsets.set(i, (offsets.get(i) + offsets.get(i - 1)));
        }
        return offsets;
    }

    public ArrayList<Integer> KMPSearch (String pattern)
    {
        String data = stringdata;
        // initialize BoyerMoore with pattern
        kmp = new KMP(pattern);
        // store results
        ArrayList<Integer> offsets = new ArrayList<Integer>();
        // if there is the pattern
        boolean has = true;
        // temporary store Offset
        int tempOffset = 0;
        // keep search if there is the pattern
        while (has)
        {
            // find the pattern and return the relative offset.
            tempOffset = kmp.search(data);
            // check if the search is ended.
            if (tempOffset == data.length())
            {
                has = false;
            }
            else
            {
                // put offset into offset ArrayList.
                offsets.add(tempOffset);
                // set the tempOffset to the end of the pattern
                tempOffset += "hard".length();
                // remove that pattern in next search.
                data = data.substring(tempOffset, data.length());
            }
        }
        // calculate the absolute offset
        for (int i = 1; i < offsets.size(); i++)
        {
            offsets.set(i, (offsets.get(i) + offsets.get(i - 1)));
        }
        return offsets;
    }

    public static void main (String[] args)
    {
        for (String pattern : PATTERNS)
        {
            eachCasePrint(pattern);
        }
    }

    public static void eachCasePrint (String pat)
    {
        Task01 task01 = new Task01();
        ArrayList<Integer> offsets = new ArrayList<Integer>();
        long startTime = 0;
        long endTime = 0;
        long time = 0;
        // bruteForceMatchSearch
        startTime = System.nanoTime();
        for (int i = 0; i < 100; i++)
        {
            offsets = task01.bruteForceMatchSearch(pat);
        }
        endTime = System.nanoTime();
        time = (endTime - startTime) / 100;
        System.out.println("Brute Force Match Search Pattern" + pat + " Time: " + time);
        System.out.println(offsets);

        // boyerMooreSearch
        startTime = System.nanoTime();
        for (int i = 0; i < 100; i++)
        {
            offsets = task01.boyerMooreSearch(pat);
        }
        endTime = System.nanoTime();
        time = (endTime - startTime) / 100;
        System.out.println("Boyer Moore Search Pattern" + pat + " Time: " + time);
        System.out.println(offsets);

        // KMPSearch
        startTime = System.nanoTime();
        for (int i = 0; i < 100; i++)
        {
            offsets = task01.KMPSearch(pat);
        }
        endTime = System.nanoTime();
        time = (endTime - startTime) / 100;
        System.out.println("KMP Search Pattern" + pat + " Time: " + time);
        System.out.println(offsets);
    }
}

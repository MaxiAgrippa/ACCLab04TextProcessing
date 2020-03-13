package com.company;

import textprocessing.In;
import textprocessing.TST;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Maxi Agrippa
 */
public class Task02
{
    private In in;
    private Path DataPath = Paths.get("./src/Data/Protein.txt");
    public ArrayList<String> Data = new ArrayList<String>();
    public String stringdata = "";
    public TST<Integer> tst = new TST<Integer>();
    public ArrayList<String> keys = new ArrayList<String>();
    public StringTokenizer stringTokenizer = null;

    public Task02 ()
    {
        try
        {
            File file = new File(DataPath.toAbsolutePath().toString());
            Scanner s = new Scanner(file);
            stringdata = Files.readString(DataPath);
            stringdata = stringdata.replace(",", "");
            stringdata = stringdata.replace(".", "");
            while (s.hasNext())
            {
                keys.add(s.next());
            }
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
        // get ride of illegal keys
        for (int i = 0; i < keys.size(); i++)
        {
            String[] tempStrings = keys.get(i).split("\\W");
            keys.remove(i);
            for (int j = 0; j < tempStrings.length; j++)
            {
                keys.add(i + j, tempStrings[j]);
            }
        }
        // Remove empty keys
        for (int i = 0; i < keys.size(); i++)
        {
            if (keys.get(i).equals(""))
            {
                keys.remove(i);
            }
        }
        // form TST
        for (int i = 0; i < keys.size(); i++)
        {
            tst.put(keys.get(i), i);
        }
    }

    public void searchesOfKey (String word)
    {
        int occurrences = 0;
        stringTokenizer = new StringTokenizer(stringdata);
        while (stringTokenizer.hasMoreTokens())
        {
            String temp = stringTokenizer.nextToken();
            if (temp.equals(word))
            {
                occurrences += 1;
            }
        }
        System.out.println("the occurrences of word " + word + " in file is: " + occurrences);
    }

    public static void main (String[] args)
    {
        Task02 task02 = new Task02();
        task02.searchesOfKey("protein");
        task02.searchesOfKey("complex");
        task02.searchesOfKey("PPI");
        task02.searchesOfKey("prediction");
    }
}

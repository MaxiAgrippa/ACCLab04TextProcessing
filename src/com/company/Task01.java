package com.company;

import textprocessing.In;

import java.nio.file.*;

/**
 * @author Maxi Agrippa
 */
public class Task01
{
    private In in;
    private Path FilePath = Paths.get("./src/Data/Hard disk.txt");

    public Task01 ()
    {
        try
        {
            in = new In(FilePath.toAbsolutePath().toString());
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}

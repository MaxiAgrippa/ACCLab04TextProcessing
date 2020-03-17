package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Maxi Agrippa
 */
public class Task03
{
    private Path DataPath = Paths.get("./src/Data/W3C Web Pages");
    private Path OutputPath = Paths.get("./src/Data/text");
    private File file = new File(DataPath.toAbsolutePath().toString());
    private FilenameFilter filenameFilter = (file, name) -> name.endsWith(".htm");
    private File[] files = file.listFiles(filenameFilter);
    // Data
    public ArrayList<String> Data = new ArrayList<String>();
    public ArrayList<Document> documents = new ArrayList<Document>();
    public ArrayList<String> PhoneNumbers = new ArrayList<String>();
    public ArrayList<String> EmailAddresse = new ArrayList<String>();
    public ArrayList<String> URLs = new ArrayList<String>();
    //Pattern
    private Pattern phoneNumberPattern01 = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
    private Pattern phoneNumberPattern02 = Pattern.compile("\\d{10}");
    private Pattern phoneNumberPattern03 = Pattern.compile("\\d{11}");
    private Pattern emailAddressePattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}");
    private Pattern URLPattern = Pattern.compile("(?:^|[\\W])" + "(((http|https|ftp|file):(\\/\\/)?))" + "(([\\w\\-\\_%]+(@))?)" + "(([\\w\\-\\_%]+\\.)+)" + "([\\w\\-\\_%]+)" + "((:[\\d]{2,5})?)" + "((\\/[\\w\\-\\_\\%]+)*)" + "((\\/(#|\\?)[\\w\\-\\.\\_\\~\\:\\/\\?\\#\\[\\]\\@\\!\\$\\&\\'\\(\\)\\*\\+\\,\\;\\=\\.]+)?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public Task03 ()
    {
        // get files
        for (File f : files)
        {
            try
            {
                // build output path
                String fileName = f.getName();
                fileName = fileName.replace(".htm", ".txt");
                Path outputFilePath = Paths.get(OutputPath.toAbsolutePath().toString() + "/" + fileName);
                // get file content
                String content = "";
                try
                {
                    content = Files.readString(f.toPath(), Charset.forName("UTF-8"));
                } catch (IOException e)
                {
                    try
                    {
                        content = Files.readString(f.toPath(), Charset.forName("US-ASCII"));
                    } catch (IOException e1)
                    {
                        try
                        {
                            content = Files.readString(f.toPath(), Charset.forName("ISO-8859-1"));
                        } catch (IOException e2)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                // Store the content
                Data.add(content);
                // Write 
                File file = new File(outputFilePath.toAbsolutePath().toString());
                if (file.exists())
                {
                    file.delete();
                }
                Files.writeString(file.toPath(), content, Charset.forName("UTF-8"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        for (String s : Data)
        {
            documents.add(Jsoup.parse(s));
        }
    }

    /**
     * Task 04
     */
    public void PhoneNumberAndEmailAddress ()
    {
        Path textDataPath = Paths.get("./src/Data/text");
        File textFile = new File(textDataPath.toAbsolutePath().toString());
        FilenameFilter filenameFilter = (textfile, name) -> name.endsWith(".txt");
        File[] files = textFile.listFiles(filenameFilter);
        String content = "";
        ArrayList<String> Data = new ArrayList<String>();
        for (File f : files)
        {
            try
            {
                content = Files.readString(f.toPath(), Charset.forName("UTF-8"));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            Data.add(content);
        }
        Matcher phoneMatcher;
        Matcher emailMatcher;
        for (String s : Data)
        {
            phoneMatcher = phoneNumberPattern01.matcher(s);
            emailMatcher = emailAddressePattern.matcher(s);
            while (phoneMatcher.find())
            {
                PhoneNumbers.add(phoneMatcher.group());
            }
            phoneMatcher = phoneNumberPattern02.matcher(s);
            while (phoneMatcher.find())
            {
                PhoneNumbers.add(phoneMatcher.group());
            }
            phoneMatcher = phoneNumberPattern03.matcher(s);
            while (phoneMatcher.find())
            {
                PhoneNumbers.add(phoneMatcher.group());
            }
            while (emailMatcher.find())
            {
                EmailAddresse.add(emailMatcher.group());
            }
        }
    }

    /**
     * Task 05
     */
    public void GetURLs ()
    {
        Path textDataPath = Paths.get("./src/Data/W3C Web Pages");
        File textFile = new File(textDataPath.toAbsolutePath().toString());
        FilenameFilter filenameFilter = (textfile, name) -> name.endsWith(".htm");
        File[] files = textFile.listFiles(filenameFilter);
        String content = "";
        ArrayList<String> Data = new ArrayList<String>();
        for (File f : files)
        {
            content = "";
            try
            {
                content = Files.readString(f.toPath(), Charset.forName("UTF-8"));
            } catch (IOException e)
            {
                try
                {
                    content = Files.readString(f.toPath(), Charset.forName("US-ASCII"));
                } catch (IOException e1)
                {
                    try
                    {
                        content = Files.readString(f.toPath(), Charset.forName("ISO-8859-1"));
                    } catch (IOException e2)
                    {
                        e.printStackTrace();
                    }
                }
            }
            Data.add(content);
        }
        Matcher URLmatcher;
        for (String s : Data)
        {
            URLmatcher = URLPattern.matcher(s);
            while (URLmatcher.find())
            {
                URLs.add(URLmatcher.group());
            }
        }
    }

    public ArrayList<String> SpecialLinkPattern (Pattern pattern)
    {
        if (URLs.isEmpty())
        {
            GetURLs();
        }
        ArrayList<String> OutPut = new ArrayList<String>();
        Matcher matcher;
        for (String s : URLs)
        {
            matcher = pattern.matcher(s);
            while (matcher.find())
            {
                OutPut.add(s);
            }
        }
        return OutPut;
    }


    public static void main (String[] args)
    {
        Task03 task03 = new Task03();
        task03.PhoneNumberAndEmailAddress();
        task03.GetURLs();
        System.out.println("EmailAddresse:");
        for (String s : task03.EmailAddresse)
        {
            System.out.println(s);
        }
        System.out.println("PhoneNumbers:");
        for (String s : task03.PhoneNumbers)
        {
            System.out.println(s);
        }
        System.out.println("URLs:");
        for (String s : task03.URLs)
        {
            System.out.println(s);
        }
        // w3.org
        System.out.println("w3.org");
        Pattern pattern01 = Pattern.compile("(w3\\.org)");
        ArrayList<String> results01 = task03.SpecialLinkPattern(pattern01);
        for (String s : results01)
        {
            System.out.println(s);
        }
        // contain folders
        System.out.println("contain folders");
        Pattern pattern02 = Pattern.compile("(?:^|[\\W])" + "(((http|https|ftp|file):(\\/\\/)?))" + "(([\\w\\-\\_%]+(@))?)" + "(([\\w\\-\\_%]+\\.)+)" + "([\\w\\-\\_%]+)" + "((:[\\d]{2,5})?)" + "((\\/[\\w\\-\\_\\%]+)+)" + "((\\/(#|\\?)[\\w\\-\\.\\_\\~\\:\\/\\?\\#\\[\\]\\@\\!\\$\\&\\'\\(\\)\\*\\+\\,\\;\\=\\.]+)?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        ArrayList<String> results02 = task03.SpecialLinkPattern(pattern02);
        for (String s : results02)
        {
            System.out.println(s);
        }
        // contain references
        System.out.println("contain references");
        Pattern pattern03 = Pattern.compile("(?:^|[\\W])" + "(((http|https|ftp|file):(\\/\\/)?))" + "(([\\w\\-\\_%]+(@))?)" + "(([\\w\\-\\_%]+\\.)+)" + "([\\w\\-\\_%]+)" + "((:[\\d]{2,5})?)" + "((\\/[\\w\\-\\_\\%]+)*)" + "((\\/(#)[\\w\\-\\.\\_\\~\\:\\/\\?\\#\\[\\]\\@\\!\\$\\&\\'\\(\\)\\*\\+\\,\\;\\=\\.]+))", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        ArrayList<String> results03 = task03.SpecialLinkPattern(pattern03);
        for (String s : results03)
        {
            System.out.println(s);
        }
        // links with .net
        System.out.println("links with .net");
        Pattern pattern04 = Pattern.compile("(?:^|[\\W])" + "(((http|https|ftp|file):(\\/\\/)?))" + "(([\\w\\-\\_%]+(@))?)" + "(([\\w\\-\\_%]+\\.)+)" + "(net)" + "((:[\\d]{2,5})?)" + "((\\/[\\w\\-\\_\\%]+)*)" + "((\\/(#|\\?)[\\w\\-\\.\\_\\~\\:\\/\\?\\#\\[\\]\\@\\!\\$\\&\\'\\(\\)\\*\\+\\,\\;\\=\\.]+)?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        ArrayList<String> results04 = task03.SpecialLinkPattern(pattern04);
        for (String s : results04)
        {
            System.out.println(s);
        }
        // links with .com
        System.out.println("links with .com");
        Pattern pattern05 = Pattern.compile("(?:^|[\\W])" + "(((http|https|ftp|file):(\\/\\/)?))" + "(([\\w\\-\\_%]+(@))?)" + "(([\\w\\-\\_%]+\\.)+)" + "(com)" + "((:[\\d]{2,5})?)" + "((\\/[\\w\\-\\_\\%]+)*)" + "((\\/(#|\\?)[\\w\\-\\.\\_\\~\\:\\/\\?\\#\\[\\]\\@\\!\\$\\&\\'\\(\\)\\*\\+\\,\\;\\=\\.]+)?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        ArrayList<String> results05 = task03.SpecialLinkPattern(pattern05);
        for (String s : results05)
        {
            System.out.println(s);
        }
        // links with .org
        System.out.println("links with .org");
        Pattern pattern06 = Pattern.compile("(?:^|[\\W])" + "(((http|https|ftp|file):(\\/\\/)?))" + "(([\\w\\-\\_%]+(@))?)" + "(([\\w\\-\\_%]+\\.)+)" + "(org)" + "((:[\\d]{2,5})?)" + "((\\/[\\w\\-\\_\\%]+)*)" + "((\\/(#|\\?)[\\w\\-\\.\\_\\~\\:\\/\\?\\#\\[\\]\\@\\!\\$\\&\\'\\(\\)\\*\\+\\,\\;\\=\\.]+)?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        ArrayList<String> results06 = task03.SpecialLinkPattern(pattern06);
        for (String s : results06)
        {
            System.out.println(s);
        }

    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Table
 */
public class Table {
    public static ArrayList<ArrayList<String>> tableMeta = new ArrayList<>();
    public static ArrayList<ArrayList<String>> records = new ArrayList<>();
    private static final String tableData = "tableData.txt";

    public static void main(String[] args) throws IOException{

        tableMeta = readFromFile();
        records = readFromFile();
        System.out.println("CREATE TABLE table_name (col1 datatype, col2 datatype, ...) - to create a table");
        System.out.println("INSERT INTO table_name VALUES (val1,val2,...) - to insert values in a table");
        System.out.println("exit to terminate the program");


        Scanner s = new Scanner(System.in);

        while(s.hasNextLine())
        {
            String line = s.nextLine();
            if(line.equalsIgnoreCase("exit")) 
            {
                System.out.println("The program has been terminated.");
                break;
            }
                
            executeCommand(line);
        }
        s.close();

    }

    public static void executeCommand(String command) 
    {
        String[] tokens = command.split("[(\\s)]");
        String keyword = tokens[0];

        switch (keyword.toUpperCase()) {
            case "CREATE" -> createTable(tokens);
            case "INSERT" -> insertIntoTable(tokens);
            default -> System.out.println("Command not found.");
        }
    }

    public static void createTable(String[] tokens) 
    {
        String tableName = tokens[2];
        StringBuilder metadata = new StringBuilder();

        for (int i = 3; i < tokens.length; i++) {
            String column = tokens[i].replace(",", "");
            metadata.append(column).append(" ");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(tableData    , true))) {
            writer.println(tableName + " " + metadata);
            System.out.println("Table created: " + tableName);
        } catch (IOException e) {
            System.out.println("An error occurred while creating the table.");
            e.printStackTrace();
        }
    }

    public static void insertIntoTable(String[] tokens) 
    {
        String tableName = tokens[2];
        StringBuilder values = new StringBuilder();
        boolean insert = false;
        for(int j=0; j< tableMeta.size();j++)
        {
            if(tableMeta.get(j).contains(tableName)){
                for (int i = 4; i < tokens.length; i++) {
                    String value = tokens[i].replace(",", "");
                    values.append(value).append(" ");
                }

                try (PrintWriter writer = new PrintWriter(new FileWriter(tableName + ".txt"))) {
                    writer.println(values);
                    System.out.println("Inserted into table: " + tableName);
                    insert = true;

                } catch (IOException e) {
                    System.out.println("An error occurred while inserting into the table.");
                    e.printStackTrace();
                }
                break;
            }
        }
        if(!insert) 
            System.out.println(tableName + " is not created");
    }

    public static ArrayList<ArrayList<String>> readFromFile() throws IOException 
    {
        ArrayList<ArrayList<String>> all = new ArrayList<>();
        ArrayList<String> sublist = new ArrayList<>();
        String line;
        BufferedReader reader =  new BufferedReader(new FileReader(tableData));

        while((line=reader.readLine())!=null)
        {
            String[] tokens = line.split(" ");
            Collections.addAll(sublist,tokens);
            all.add(sublist);
        }
        reader.close();
        return all;
    }
    
}
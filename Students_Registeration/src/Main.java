import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
//    static String pathToBatch = "D:\\.1Level 4\\Cloud Computing\\Assignments\\Assignment2\\batch\\";
//    static String pathToDatabase = "D:\\.1Level 4\\Cloud Computing\\Assignments\\Assignment2\\database\\";
    static String pathToBatch = "/app/data/batch/";
    static String pathToDatabase = "/app/data/database/";
    static Scanner consoleIn = new Scanner(System.in);

    static void writeStudent(String name, String id, String[] courses) {
        String coursesFormatted = "";
        for (int i=0; i< courses.length; i++) {
            coursesFormatted += courses[i] + (i == courses.length-1 ? "" : ",");
        }
        try {
            File databaseFolder = new File(pathToDatabase);
            databaseFolder.mkdirs();
            File file = new File(pathToDatabase + "/db.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter dbWriter = new FileWriter(file, true);
            dbWriter.write(name + ";");
            dbWriter.write(id + ";");
            dbWriter.write(coursesFormatted);
            dbWriter.write("\n");
            dbWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    static void runOption1() {
        String name = consoleIn.nextLine();
        String id = consoleIn.nextLine();
        String[] courses = consoleIn.nextLine().split(",( )?");
        writeStudent(name, id, courses);
    }
    static void runOption2() {
        File directory = new File(pathToBatch);
        directory.mkdirs();
        // Verify that the path points to a directory
        File[] files = directory.listFiles();
        if(files == null) {
            System.out.println("Invalid");
            return;
        }
        Map<String, File> filesMap = Arrays.stream(files)
                .filter(file -> file.isFile() && file.getName().contains("verified"))
                .collect(Collectors.toMap(File::getName, file -> file));
        if(filesMap.size() == 0) {
            System.out.println("No batch files");
            return;
        }
        for (var entry : filesMap.entrySet()) {
            System.out.println(entry.getKey());
        }
//        System.out.print("File Name: ");
        String userInput = consoleIn.nextLine();
        File selectedFile = filesMap.get(userInput);
        if(selectedFile == null) {
            System.out.println("Doesn't Exists");
            return;
        }

        try {
            Scanner selectedFileReader = new Scanner(selectedFile);
            while(selectedFileReader.hasNextLine()) {
                String[] studentLine = selectedFileReader.nextLine().split(";");
                writeStudent(studentLine[0], studentLine[1], studentLine[2].split(","));
            }
            selectedFileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println("1- Add student data");
        System.out.println("2- Add batch students data");

        int chosenOption = consoleIn.nextInt();
        consoleIn.nextLine();

        if(chosenOption == 1) {
            runOption1();
            return;
        }
        runOption2();
    }
}

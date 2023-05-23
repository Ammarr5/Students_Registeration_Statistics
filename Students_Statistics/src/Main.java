import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
//        static String pathToBatch = "D:\\.1Level 4\\Cloud Computing\\Assignments\\Assignment2\\batch\\";
//        static String pathToDatabase = "D:\\.1Level 4\\Cloud Computing\\Assignments\\Assignment2\\database\\";
    static String pathToBatch = "/app/data/batch/";
    static String pathToDatabase = "/app/data/database/";

    public static void main(String[] args) throws IOException {
        File databaseFolder = new File(pathToDatabase);
        databaseFolder.mkdirs();
        File dbFile = new File(databaseFolder, "db.txt");
        if(!dbFile.exists()) {
            dbFile.createNewFile();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dbFile))) {
            Map<String, Integer> courseCount = new HashMap<>();
            int userCount = 0;

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String[] courses = parts[2].split(",");
                    for (String course : courses) {
                        courseCount.put(course, courseCount.getOrDefault(course, 0) + 1);
                    }
                    userCount++;
                }
            }

            System.out.println("Number of users: " + userCount);
            for (Map.Entry<String, Integer> entry : courseCount.entrySet()) {
                String course = entry.getKey();
                int count = entry.getValue();
                System.out.println("Number of students registered in " + course + " course: " + count);
            }
            File directory = new File(pathToBatch);
            directory.mkdirs();

            // Verify that the path points to a directory
            File[] files = directory.listFiles();
            if(files == null) {
                System.out.println("Invalid");
                return;
            }

            int totalBatchFiles = 0;
            int verifiedBatchFiles = 0;

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    totalBatchFiles++;

                    if (file.getName().contains("verified")) {
                        verifiedBatchFiles++;
                    }
                }
            }
            System.out.println("Number of batch files: " + totalBatchFiles);
            System.out.println("Number of verified batch files: " + verifiedBatchFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

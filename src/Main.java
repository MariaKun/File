import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String path = "C:\\Users\\redfi\\Downloads\\Java\\Games\\";
        StringBuilder log = new StringBuilder();

        String pathSaves = path + "savegames\\";
        CreateFolder(pathSaves, log);

        String pathTemp = path + "temp\\";
        CreateFolder(pathTemp, log);

        String pathSrc = path + "src\\";
        CreateFolder(pathSrc, log);
        List<String> childSrc = Arrays.asList("main", "test");
        CreateFolders(pathSrc, childSrc, log);

        String pathRes = path + "res\\";
        CreateFolder(pathRes, log);
        List<String> childRes = Arrays.asList("drawables", "vectors", "icons");
        CreateFolders(pathRes, childRes, log);

        List<String> mainFiles = Arrays.asList("Main.java", "Utils.java");
        CreateFiles(pathSrc + "main\\", mainFiles, log);

        List<String> tempFiles = Arrays.asList("temp.txt");
        CreateFiles(pathTemp, tempFiles, log);

        try (FileWriter fileWriter = new FileWriter(pathTemp + "temp.txt")) {
            fileWriter.write(log.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        GameProgress gameProgress1 = new GameProgress(2, 5, 3, 7);
        GameProgress gameProgress2 = new GameProgress(3, 4, 4, 8);
        GameProgress gameProgress3 = new GameProgress(4, 3, 5, 9);
        gameProgress1.saveGame(pathSaves + "save1.dat");
        gameProgress2.saveGame(pathSaves + "save2.dat");
        gameProgress3.saveGame(pathSaves + "save3.dat");

        File saved = new File(pathSaves);
        File[] filesInSaved = saved.listFiles();
        List<String> list = Arrays.stream(filesInSaved)
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());
        gameProgress1.zipFiles(pathSaves + "zip.zip", list);
        for (File file : filesInSaved
        ) {
            file.delete();
        }
    }

    public static void CreateFolders(String path, List<String> folders, StringBuilder log) {
        for (String folder : folders) {
            CreateFolder(path + folder, log);
        }
    }

    public static void CreateFolder(String pathFolder, StringBuilder log) {
        File file = new File(pathFolder);
        String res = "";
        if (!file.mkdir()) {
            res = "not ";
        }
        log.append(pathFolder + " is " + res + "created \n");
    }

    public static void CreateFiles(String path, List<String> files, StringBuilder log) {
        for (String file1 : files) {
            String pathFolder = path + file1;
            File file = new File(pathFolder);
            String res = "";
            try {
                if (!file.createNewFile()) {
                    res = "not ";
                }
                log.append(pathFolder + " is " + res + "created \n");
            } catch (IOException e) {
                log.append("File " + pathFolder + " is not created with error: " + e.getMessage());
            }
        }
    }
}

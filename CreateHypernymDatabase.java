// 207133935 Daniel Lev

/**
 * @author Daniel Lev.
 * @version 1.0
 * @since 2023-06-20
 * Class CreateHypernymDatabase.
 */

import java.io.File;
import java.io.IOException;

public class CreateHypernymDatabase {
    /**
     * main: Creats a database and writes to file.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);
        File[] files = inputFile.listFiles();
        RelationsDatabase database = new RelationsDatabase();
        Run processor = new Run(database, CheckPattern.PATTERNS, lemma);
        try {
            processor.process(files);
            database.writeIntoFile(outputFile);
        } catch (IOException e) {
            System.out.println("Invalid arguments - failed to create database");
        }
    }
}

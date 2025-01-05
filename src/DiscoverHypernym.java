// 207133935 Daniel Lev

/**
 * @author Daniel Lev.
 * @version 1.0
 * @since 2023-06-29
 * Class DiscoverHypernym.
 */

import java.io.File;
import java.io.IOException;

public class DiscoverHypernym {
    /**
     * main: Search lemma.
     *
     * @param args cmd arguments.
     */
    public static void main(String[] args) {
        File dir = new File(args[0]);
        String lemma = args[1];
        DatabaseRelations database = new DatabaseRelations();
        File[] files = dir.listFiles();
        CorpusRun processing = new CorpusRun(database, PatternCheck.PATTERNS, lemma);
        try {
            processing.process(files);
        } catch (IOException e) {
            System.out.println("database creation failed");
        }
        database.searchInDatabase(lemma);
    }
}

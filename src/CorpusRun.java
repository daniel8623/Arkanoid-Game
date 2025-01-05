// 207133935 Daniel Lev

/**
 * @author Daniel Lev.
 * @version 1.0
 * @since 2023-06-29
 * Class Run.
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class CorpusRun {
    private final DatabaseRelations database;
    private final List<PatternCheck> patterns;
    private final String lemma;

    /**
     * Run Constructor.
     *
     * @param database a database.
     * @param patterns to search in the text.
     * @param lemma the lemma.
     */
    public CorpusRun(DatabaseRelations database, List<PatternCheck> patterns, String lemma) {
        this.database = database;
        this.patterns = patterns;
        this.lemma = lemma;
    }

    public static Map<String, Map<String, Integer>> removeSingularFromNps(Map<String, Map<String, Integer>> nps){
     for (Map.Entry<String, Map<String, Integer>> entry : nps.entrySet()) {
            String key = entry.getKey();
            Map<String, Integer> innerMap = entry.getValue();
            String pluralKey = key + "s";
            if (innerMap.containsKey(pluralKey)) {
                innerMap.remove(key);
            }
        }
        return nps;
    }

    /**
     * @param files array of files.
     * @throws IOException if error occurs.
     */
    public void process(File[] files) throws IOException {
        for (File f : files) {
            progress(f);
        }
    }

    /**
     * @param f a file.
     * @throws IOException if error occurs.
     */
    public void progress(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        for (String l = reader.readLine(); l != null; l = reader.readLine()) {
            process(l);
        }
    }

    /**
     * @param line a text.
     */
    public void process(String line) {
        if (line.contains("<np>" + lemma + "</np>")) {
            for (PatternCheck pat : patterns) {
                List<String> matches = pat.matchesNps(line);
                if (matches.isEmpty()) {
                    continue;
                }
                for (String match : matches) {
                    List<String> npPhrases = PatternCheck.findNpsInSentence(match);
                    String hypernym = npPhrases.get(pat.getHypernymIndex());
                    npPhrases.remove(pat.getHypernymIndex());
                    for (String hyponym : npPhrases) {
                        database.enterToDatabase(hypernym, hyponym);
                    }
                }
            }
        }
    }
}

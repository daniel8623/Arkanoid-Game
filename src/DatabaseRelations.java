// 207133935 Daniel Lev

/**
 * @author Daniel Lev.
 * @version 1.0
 * @since 2023-06-29
 * Class RelationsDatabase.
 */

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DatabaseRelations {
    private final Map<String, Map<String, Integer>> dataB = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * search: Printing all relations that included the lemma.
     *
     * @param lemma a lemma.
     */
    public void searchInDatabase(String lemma) {
        String out = dataB.entrySet().stream().filter(e -> e.getValue().containsKey(lemma))
                .sorted((e1, e2) -> e2.getValue().get(lemma) - e1.getValue().get(lemma))
                .map(e -> String.format("%s: (%d)", e.getKey(), e.getValue().get(lemma)))
                .collect(Collectors.joining("\n"));
        if (!out.isEmpty()) {
            System.out.println(out);
        } else {
            System.out.println("The lemma doesn't appear in the corpus.");
        }
    }

    /**
     * insert: Inserting a new relation.
     *
     * @param hypernym a hypernym.
     * @param hyponym  a hyponym.
     */
    public void enterToDatabase(String hypernym, String hyponym) {
        if (dataB.containsKey(hypernym) == false) {
            dataB.put(hypernym, new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
        }
        Map<String, Integer> counter = dataB.get(hypernym);
        counter.put(hyponym, counter.getOrDefault(hyponym, 0) + 1);
    }

    /**
     * writeIntoFile: Writing to file.
     *
     * @param file file to write into.
     * @throws IOException if error occurs.
     */
    public void writeIntoFile(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        file.createNewFile();
        writer.write(dataB.entrySet().stream().filter(e -> e.getValue().size() >= 3)
                .map(e -> e.getKey() + ": " + e.getValue().entrySet().stream()
                        .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                        .map(e0 -> String.format("%s (%d)", e0.getKey(), e0.getValue()))
                        .collect(Collectors.joining(", ")))
                .collect(Collectors.joining("\n")));
        writer.close();
    }
}

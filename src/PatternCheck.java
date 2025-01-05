// 207133935 Daniel Lev

/**
 * @author Daniel Lev.
 * @version 1.0
 * @since 2023-06-29
 * Class CheckPattern.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternCheck {
    private final Pattern pattern;
    private final int index;
    private static final String npPattern = "<np>([^<]*)</np>";
    public static final int index_0 = 0, index_1 = 1;
    public static final List<PatternCheck> PATTERNS = Arrays.asList(
            new PatternCheck("NP (,)?such as NP( (, )?NP)*( (, )?(and|or) NP)?", index_0 ),
            new PatternCheck("such NP as NP( , NP)*( (, )?(and|or) NP)?", index_0),
            new PatternCheck("NP (, )?including NP( (, )?NP)*( (, )?(and|or) NP)?", index_0),
            new PatternCheck("NP (, )?especially NP( (, )?NP)*( (, )?(and|or) NP)?", index_0),
            new PatternCheck("NP (, )?which is ((an example|a kind|a class) of )?NP", index_1)
    );

    /**
     * CheckPattern Constructor.
     *
     * @param pattern the pattern.
     * @param index   the hypernym index.
     */
    public PatternCheck(String pattern, int index) {
        this.pattern = Pattern.compile(pattern.replace("NP", npPattern));
        this.index = index;
    }

    /**
     * getHypernymIndex: getter
     *
     * @return hypernymIndex.
     */
    public int getHypernymIndex() {
        return index;
    }

    /**
     * @param line a line.
     * @return list of sentences match the pattern.
     */
    public List<String> matchesNps(String line) {
        List<String> results = new ArrayList<>();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            results.add(matcher.group());
        }
        return results;
    }

    /**
     * findNpsInSentence: extract the noun phrases.
     *
     * @param sentence a sentence.
     * @return list of all NPs in the sentence.
     */
    public static List<String> findNpsInSentence(String sentence) {
        List<String> nps = new ArrayList<>();
        Matcher matcher = Pattern.compile(npPattern).matcher(sentence);
        while (matcher.find()) {
            nps.add(matcher.group(index_1));
        }
        return nps;
    }
}

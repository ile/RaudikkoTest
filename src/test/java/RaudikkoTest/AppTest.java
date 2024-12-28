package RaudikkoTest;

import fi.evident.raudikko.Analyzer;
import fi.evident.raudikko.Analysis;
import fi.evident.raudikko.AnalyzerConfiguration;
import fi.evident.raudikko.Morphology;
import fi.evident.raudikko.analysis.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testAnalyzerCreation() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        assertNotNull(analyzer);
    }

    @Test
    public void testAnalyzeKnownWord() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("kissa");
        assertNotNull(analysis);
        assertFalse(analysis.isEmpty());
        // verify the word class
        Optional<Analysis> first = analysis.stream().findFirst();
        first.ifPresent(a -> assertEquals(WordClass.NOUN, a.getWordClass()));
    }

    @Test
    public void testAnalyzeWordKissoittansa() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("kissoittansa");
        assertNotNull(analysis);
        assertFalse(analysis.isEmpty());
        assertEquals(1, analysis.size());
    }

    @Test
    public void testAnalyzeWordKahdellakymmenella() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("kahdellakymmenelläseitsemällä");
        assertNotNull(analysis);
        assertFalse(analysis.isEmpty());
        assertEquals(1, analysis.size());
    }

    @Test
    public void testAnalyzeUnknownWord() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("someRandomWord");
        assertNotNull(analysis);
        assertTrue(analysis.isEmpty());
    }

    @Test
    public void testAnalyzerWithConfiguration() {
        Morphology morphology = Morphology.loadBundled();
        AnalyzerConfiguration config = new AnalyzerConfiguration();
        config.setIncludeBaseForm(false);
        config.setIncludeStructure(false);
        config.setIncludeFstOutput(false);
        Analyzer analyzer = morphology.newAnalyzer(config);
        List<Analysis> analysis = analyzer.analyze("kissoittansa");

        Optional<Analysis> first = analysis.stream().findFirst();
        first.ifPresent(a -> assertNull(a.getBaseForm()));
        first.ifPresent(a -> assertNull(a.getStructure()));
        first.ifPresent(a -> assertNull(a.getFstOutput()));
    }

    @Test
    public void testLocative() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("talossa");
        assertNotNull(analysis);
        assertFalse(analysis.isEmpty());
        Optional<Analysis> first = analysis.stream().findFirst();
        first.ifPresent(a -> assertEquals(Locative.INESIVE, a.getLocative()));
    }

    @Test
    public void testMood() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("lukekaa");
        assertNotNull(analysis);
        assertFalse(analysis.isEmpty());
        Optional<Analysis> first = analysis.stream().findFirst();
        first.ifPresent(a -> assertEquals(Mood.IMPERATIVE, a.getMood()));
    }

    @Test
    public void testTense() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("luki");
        assertNotNull(analysis);
        assertFalse(analysis.isEmpty());
        Optional<Analysis> first = analysis.stream().findFirst();
        first.ifPresent(a -> assertEquals(Tense.PAST_IMPERFECTIVE, a.getTense()));
    }

    @Test
    public void testParticiple() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("lukeva");
        System.out.println(".." + analysis);
        assertNotNull(analysis);
        assertFalse(analysis.isEmpty());
        Optional<Analysis> first = analysis.stream().findFirst();
        first.ifPresent(a -> assertEquals(Participle.PRESENT_ACTIVE, a.getParticiple()));
    }

    @Test
    public void testComparison() {
        Morphology morphology = Morphology.loadBundled();
        Analyzer analyzer = morphology.newAnalyzer();
        List<Analysis> analysis = analyzer.analyze("kauniimpi");
        assertNotNull(analysis);
        assertFalse(analysis.isEmpty());
        Optional<Analysis> first = analysis.stream().findFirst();
        first.ifPresent(a -> assertEquals(Comparison.COMPARATIVE, a.getComparison()));
    }
}
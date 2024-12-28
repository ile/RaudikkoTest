package RaudikkoTest;

import fi.evident.raudikko.Analyzer;
import fi.evident.raudikko.Analysis;
import fi.evident.raudikko.AnalyzerConfiguration;
import fi.evident.raudikko.Morphology;
import fi.evident.raudikko.analysis.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Morphology morphology = Morphology.loadBundled();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nType a Finnish word to analyze (or 'exit' to quit):");
            String word = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(word)) {
                break;
            }

            AnalyzerConfiguration config = new AnalyzerConfiguration();

            System.out.println("Enable structure analysis (y/n)?");
            String enableStructure = scanner.nextLine();
             config.setIncludeStructure(enableStructure.toLowerCase().startsWith("y"));

            System.out.println("Enable base form analysis (y/n)?");
            String enableBaseForm = scanner.nextLine();
            config.setIncludeBaseForm(enableBaseForm.toLowerCase().startsWith("y"));

              System.out.println("Enable FST output analysis (y/n)?");
            String enableFstOutput = scanner.nextLine();
            config.setIncludeFstOutput(enableFstOutput.toLowerCase().startsWith("y"));

            Analyzer analyzer = morphology.newAnalyzer(config);


            List<Analysis> analysisResults = analyzer.analyze(word);

              System.out.println("\nAnalysis results for '" + word + "':");
              if(analysisResults.isEmpty()){
                 System.out.println("   No analysis found for this word.");
                 continue;
             }
              for(Analysis analysis : analysisResults) {
                  System.out.println("   -------------------------------------------");
                 if (config.isIncludeBaseForm()) {
                     System.out.println("   Base form: " + analysis.getBaseForm());
                  }
                  if (config.isIncludeStructure()) {
                      System.out.println("   Structure: " + analysis.getStructure());
                  }
                  if(config.isIncludeFstOutput()) {
                      System.out.println("   FST output: " + analysis.getFstOutput());
                  }

                System.out.println("   Word class: " + Optional.ofNullable(analysis.getWordClass()).map(wc -> wc.toString()).orElse("null"));
                   System.out.println("   Locative: " + Optional.ofNullable(analysis.getLocative()).map(loc -> loc.toString()).orElse("null"));
                   System.out.println("   Mood: " + Optional.ofNullable(analysis.getMood()).map(mood -> mood.toString()).orElse("null"));
                    System.out.println("   Tense: " + Optional.ofNullable(analysis.getTense()).map(tense -> tense.toString()).orElse("null"));
                    System.out.println("   Participle: " + Optional.ofNullable(analysis.getParticiple()).map(p -> p.toString()).orElse("null"));
                    System.out.println("   Comparison: " + Optional.ofNullable(analysis.getComparison()).map(c -> c.toString()).orElse("null"));
                }
        }
        System.out.println("Exiting Raudikko Analyzer.");
        scanner.close();
    }
}
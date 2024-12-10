package org.example.Migrador;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class LectorCsv {

    public List<String[]> leerArchivoCsv(String csvPath) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setDelimiter(',');
        settings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(settings);

        List<String[]> allRows;
        try {
            File file = new File(csvPath);
            allRows = parser.parseAll(new FileReader(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return allRows;
    }
}

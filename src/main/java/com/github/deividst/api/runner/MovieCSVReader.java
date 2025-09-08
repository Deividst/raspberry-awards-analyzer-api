package com.github.deividst.api.runner;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.deividst.api.dto.MovieDTO;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;


public class MovieCSVReader {

    public static List<MovieDTO> read(File file) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("year")
                .addColumn("title")
                .addColumn("studios")
                .addColumn("producers")
                .addColumn("winner")
                .setUseHeader(true)
                .setColumnSeparator(';')
                .build();

        try (Reader reader = new FileReader(file)) {
            return csvMapper.readerFor(MovieDTO.class)
                    .with(schema)
                    .<MovieDTO>readValues(reader)
                    .readAll();
        }
    }

}

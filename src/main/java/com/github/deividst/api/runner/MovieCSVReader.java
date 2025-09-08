package com.github.deividst.api.runner;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.deividst.api.dto.MovieDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MovieCSVReader {

    public static List<MovieDTO> read(InputStream inputStream) throws IOException {
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


        return csvMapper.readerFor(MovieDTO.class)
                .with(schema)
                .<MovieDTO>readValues(inputStream)
                .readAll();

    }

}

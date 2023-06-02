package com.device.shop.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.device.shop.entity.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Product> csvToProducts(InputStream is) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';').withIgnoreHeaderCase().withTrim());) {

            List<Product> products = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String name = csvRecord.get("name");
                String description = csvRecord.get("description");
                String sku = csvRecord.get("sku");
                double price = Double.parseDouble(csvRecord.get("price"));

                Product product = Product.builder()
                        .name(name)
                        .description(description)
                        .sku(sku)
                        .price(price)
                        .build();

                products.add(product);
            }

            return products;


        }
    }
}

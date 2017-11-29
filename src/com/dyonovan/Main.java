package com.dyonovan;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static BufferedWriter outputWriter;

    public static void main(String[] args) {

        List<MissingNums> data = new ArrayList<>();

        try {
            Reader in = new FileReader(args[0]);
            Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);
            for (CSVRecord record : records) {
                MissingNums nums = new MissingNums(record.get(0), record.get(1), record.get(2), record.get(3), record.get(4));
                data.add(nums);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void write(List<MissingNums> data) throws IOException {

        outputWriter = new BufferedWriter(new FileWriter("notused.csv"));

        for (int i = 1; i <= 99999; i++) {
            boolean found = isIn(i, data);
            if (!found) {
                outputWriter.write("\"" + StringUtils.leftPad(Integer.toString(i), 5, "0") + "\"");
                outputWriter.newLine();
            }
        }

        outputWriter.flush();
        outputWriter.close();
    }

    private static boolean isIn(int i, List<MissingNums> nums) throws IOException {
        for (MissingNums num : nums) {
            if (Integer.parseInt(num.id) == i) {
                outputWriter.write(
                        "\"" + StringUtils.leftPad(num.id, 5, "0") + "\"," +
                                "\"" + num.sku + "\"," +
                                "\"" + num.name + "\"," +
                                "\"" + num.price + "\"," +
                                "\"" + num.size + "\""
                );
                outputWriter.newLine();
                return true;
            }
        }
        return false;
    }
}

package core.basesyntax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WorkWithFile {
    private static final String BUY = "buy";
    private static final String SUPPLY = "supply";
    private static final String RESULT = "result";
    private static final String COMMA = ",";

    private static final int NUMBER_OF_COLUMNS = 2;

    private static final int ACTION_TYPE_INDEX = 0;
    private static final int SUM_INDEX = 1;

    private static void writeUsingFiles(String data, String fileName) {
        try {
            Files.write(Paths.get(fileName), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from the file " + fileName, e);
        }
    }

    public void getStatistic(String fromFileName, String toFileName) {
        String[] linesArr = readFileToArr(fromFileName);
        String[][] separatedLinesArr = new String[linesArr.length][NUMBER_OF_COLUMNS];

        for (int i = 0; i < linesArr.length; i++) {
            separatedLinesArr[i] = linesArr[i].split(COMMA);
        }

        String result = getString(separatedLinesArr);

        writeUsingFiles(result, toFileName);
    }

    private static String[] readFileToArr(String fileName) {
        File file = new File(fileName);
        String[] linesArr;

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            linesArr = lines.toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to the file " + fileName, e);
        }

        return linesArr;
    }

    private static String getString(String[][] separatedArr) {
        int supplySum = 0;
        int buySum = 0;

        for (String[] item: separatedArr) {
            if (item[ACTION_TYPE_INDEX].equals(SUPPLY)) {
                supplySum += Integer.parseInt(item[SUM_INDEX]);
                continue;
            }

            if (item[ACTION_TYPE_INDEX].equals(BUY)) {
                buySum += Integer.parseInt(item[SUM_INDEX]);
            }
        }

        return SUPPLY + COMMA + supplySum + System.lineSeparator()
                + BUY + COMMA + buySum + System.lineSeparator()
                + RESULT + COMMA + (supplySum - buySum);
    }
}

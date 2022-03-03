package extensions;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class readFromExcel {


    public static ArrayList<Map<String, String>> readFromExcelFunction(String path, int cellNum) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path));
        ArrayList<Map<String, String>> readFromExel = new ArrayList<Map<String, String>>();
        Map<String, String> StringSpesificLineFromExel;
        ArrayList<String> fieldName = new ArrayList<String>();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        int flag = 0;

        while (iterator.hasNext()) {
            int flagCell = 0;
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            StringSpesificLineFromExel = new HashMap<>();
            while (cellIterator.hasNext() && flagCell <= cellNum) {


                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {

                    case Cell.CELL_TYPE_STRING:
                        if (flag == 0) {
                            fieldName.add(cell.getStringCellValue());
                        } else {
                            StringSpesificLineFromExel.put(fieldName.get(cell.getColumnIndex()), cell.getStringCellValue());
                        }
                        break;

                    case Cell.CELL_TYPE_NUMERIC:
                        if (flag == 0) {
                            fieldName.add(Double.toString(cell.getNumericCellValue()));
                        } else {
                            StringSpesificLineFromExel.put(fieldName.get(cell.getColumnIndex()),
                                    Integer.toString((int) cell.getNumericCellValue()));
                        }
                        break;

                }
                flagCell++;

            }
            if (flag != 0) {

                readFromExel.add(StringSpesificLineFromExel);

            }
            flag++;


        }
        System.out.println(readFromExel.toString());
        workbook.close();
        inputStream.close();
        return readFromExel;
    }

    public static String readFromExcelSpesificFields(String path, String fields, int flag) throws IOException {

        //
        ArrayList<String> arrFieldPlassValue = readFromExcelSpesificFieldsName(path, fields);
        String value = "";

        //
        if (arrFieldPlassValue.size() >= flag) {
            value = arrFieldPlassValue.get(flag);
        }

        return value;
    }

    public static ArrayList<String> readFromExcelSpesificFieldsName(String path, String fields) throws IOException {

        //
        ArrayList<Map<String, String>> arrFieldPlassValue = readFromExcelFunction(path, getFieldsName(path).size() - 1);
        ArrayList<String> mapFieldPlassValue = new ArrayList<String>();

        //
        for (int a = 0; a < arrFieldPlassValue.size(); a++) {
            mapFieldPlassValue.add(arrFieldPlassValue.get(a).get(fields));

        }
        // System.out.println(mapFieldPlassValue);
        return mapFieldPlassValue;
    }

    public static ArrayList<String> getFieldsName(String path) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(path));

        ArrayList<String> fieldName = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        int flag = 0;

        while (iterator.hasNext()) {
            flag++;


            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                if (flag <= 1) {
                    switch (cell.getCellType()) {

                        case Cell.CELL_TYPE_STRING:
                            fieldName.add(cell.getStringCellValue());

                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            fieldName.add(cell.getStringCellValue());

                            break;
                        case Cell.CELL_TYPE_NUMERIC:

                            fieldName.add(cell.getStringCellValue());

                            break;
                    }

                } else {
                    break;
                }

            }

        }

        return fieldName;
    }

}

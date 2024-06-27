package q3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Q3 {
    Cell[][] table;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Cell[][] table = new Cell[n][n];
        for (int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < n ; j++) {
                table[i][j] = new Cell(scanner.nextInt());
            }
            scanner.nextLine();
        }

        List<Row> rows = new ArrayList<>();
        List<Column> columns = new ArrayList<>();

//        fill rows and columns
        for(int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                if (table[i][j].row == null) {
                    table[i][j].row = new Row();
                    table[i][j].row.cells.add(table[i][j]);
                    rows.add(table[i][j].row);
                    for(int k = j+1 ; k < n ; k++) {
                        if(table[i][k].value == table[i][j].value){
                            table[i][k].row = table[i][j].row;
                            table[i][j].row.cells.add(table[i][k]);
                        }
                    }

                }
                if (table[i][j].column == null) {
                    table[i][j].column = new Column();
                    table[i][j].column.cells.add(table[i][j]);
                    columns.add(table[i][j].column);
                    for(int k = i+1 ; k < n ; k++) {
                        if(table[k][j].value == table[i][j].value){
                            table[k][j].column = table[i][j].column;
                            table[i][j].column.cells.add(table[k][j]);
                        }
                    }

                }
            }
        }
        int output = 0;


        for(Column column : columns) {
            if (column.cells.size() <= 1){
                continue;
            }
            column.cells.sort(new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o1.row.cells.size() > o2.row.cells.size() ? 1:0;
                }
            });
            Cell stayedCell = column.cells.get(column.cells.size() -1);
            for (int i = 0 ; i < column.cells.size() -1;i++) {
                Cell cell = column.cells.get(i);
                cell.row.cells.remove(cell);
                output++;
            }
            column.cells.clear();
            column.cells.add(stayedCell);
        }

        for(Row row : rows) {
            if (row.cells.size() <= 1){
                continue;
            }
            row.cells.sort(new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o1.column.cells.size() > o2.column.cells.size() ? 1:0;
                }
            });
            Cell stayedCell = row.cells.get(row.cells.size() -1);
            for (int i = 0 ; i < row.cells.size() -1;i++) {
                Cell cell = row.cells.get(i);
                cell.column.cells.remove(cell);
                output++;
            }
            row.cells.clear();
            row.cells.add(stayedCell);
        }


        System.out.println(output);


    }



}
class Row{
    ArrayList<Cell> cells = new ArrayList<>();
}
class Column{
    ArrayList<Cell> cells = new ArrayList<>();
}
class Cell{
    public Row row;
    Column column;
    int value;

    public Cell(int value) {
        this.value = value;
    }
}

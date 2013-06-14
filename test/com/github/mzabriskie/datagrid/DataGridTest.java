package com.github.mzabriskie.datagrid;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class DataGridTest extends Assert {

    private DataGrid grid;

    @Before
    public void setup() {
        grid = new DataGrid("ID", "FirstName", "LastName", "EmailAddr");
    }

    @Test
    public void testColumns() {
        assertEquals(4, grid.getColCount());
    }

    @Test
    public void testAddingRows() {
        //1. Add row using varargs
        grid.addDataRow(1, "Fred", "Flintstone", "fred.flintstone@example.com");
        assertEquals(1, grid.getRowCount());

        //2. Add row using List
        grid.addDataRow(Arrays.asList(2, "Wilma", "Flintstone", "wilma.flintstone@example.com"));
        assertEquals(2, grid.getRowCount());
    }

    @Test
    public void testAddingInvalidRows() {
        //1. Make sure adding too few columns throws an error
        boolean invalid = false;
        try {
            grid.addDataRow(1, "Fred");
        } catch (AssertionError e) {
            invalid = true;
        }
        assertTrue(invalid);

        //2. Make sure adding too many columns throws an error
        invalid = false;
        try {
            grid.addDataRow(1, "Fred", "Flintstone", "fred.flintstone@example.com", "foo bar");
        } catch (AssertionError e) {
            invalid = true;
        }
        assertTrue(invalid);
    }

    @Test
    public void testRendering() {
        StringBuilder sb = new StringBuilder();
        StringBuilder expected = new StringBuilder();

        expected.append("+----+-----------+------------+------------------------------+\n")
                .append("| ID | FirstName | LastName   | EmailAddr                    |\n")
                .append("+----+-----------+------------+------------------------------+\n")
                .append("|  1 | Fred      | Flintstone | fred.flintstone@example.com  |\n")
                .append("|  2 | Wilma     | Flintstone | wilma.flintstone@example.com |\n")
                .append("|  3 | Barney    | Rubble     | barney.rubble@example.com    |\n")
                .append("|  4 | Betty     | Rubble     | betty.rubble@example.com     |\n")
                .append("+----+-----------+------------+------------------------------+\n");

        grid.addDataRow(1, "Fred", "Flintstone", "fred.flintstone@example.com");
        grid.addDataRow(2, "Wilma", "Flintstone", "wilma.flintstone@example.com");
        grid.addDataRow(3, "Barney", "Rubble", "barney.rubble@example.com");
        grid.addDataRow(4, "Betty", "Rubble", "betty.rubble@example.com");
        grid.render(sb);

        assertEquals(expected.toString(), sb.toString());
    }
}

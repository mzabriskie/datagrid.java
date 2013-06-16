package com.github.mzabriskie.datagrid;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DataGridTest extends Assert {

    private DataGrid grid;

    @Before
    public void setup() {
        grid = new DataGrid("ID", "FirstName", "LastName", "EmailAddr");
    }

    @Test
    public void testColumns() {
        assertEquals(4, grid.columns.size());
    }

    @Test
    public void testData() {
        grid.add(1, "Fred", "Flintstone", "fred.flintstone@example.com");

        List<List> data = grid.data;
        assertEquals(1, data.get(0).get(0));
        assertEquals("Fred", data.get(0).get(1));
        assertEquals("Flintstone", data.get(0).get(2));
        assertEquals("fred.flintstone@example.com", data.get(0).get(3));
    }

    @Test
    public void testAddingData() {
        //1. Add data using varargs
        grid.add(1, "Fred", "Flintstone", "fred.flintstone@example.com");
        assertEquals(1, grid.size());

        //2. Add data using List
        grid.add(Arrays.asList(2, "Wilma", "Flintstone", "wilma.flintstone@example.com"));
        assertEquals(2, grid.size());
    }

    @Test
    public void testAddingInvalidRows() {
        //1. Make sure adding too few columns throws an error
        boolean invalid = false;
        try {
            grid.add(1, "Fred");
        } catch (AssertionError e) {
            invalid = true;
        }
        assertTrue(invalid);

        //2. Make sure adding too many columns throws an error
        invalid = false;
        try {
            grid.add(1, "Fred", "Flintstone", "fred.flintstone@example.com", "foo bar");
        } catch (AssertionError e) {
            invalid = true;
        }
        assertTrue(invalid);
    }

    @Test
    public void testSortingByIndex() {
        grid.add(4, "Betty", "Rubble", "betty.rubble@example.com");
        grid.add(2, "Wilma", "Flintstone", "wilma.flintstone@example.com");

        //1. Sort by index 0 and make sure items are correctly ordered
        grid.sort(0);

        List<List> data = grid.data;
        assertEquals(2, data.get(0).get(0));
        assertEquals(4, data.get(1).get(0));

        //2. Sort by index 1 and make sure items are correctly ordered
        grid.sort(1);

        data = grid.data;
        assertEquals("Betty", data.get(0).get(1));
        assertEquals("Wilma", data.get(1).get(1));

        //3. Sort by index 2 DESC and make sure items are correctly ordered
        grid.sort(2, DataGrid.Sort.DESC);

        data = grid.data;
        assertEquals("Rubble", data.get(0).get(2));
        assertEquals("Flintstone", data.get(1).get(2));

        //4. Sort by index 3 DESC and make sure items are correctly ordered
        grid.sort(3, DataGrid.Sort.DESC);

        data = grid.data;
        assertEquals("wilma.flintstone@example.com", data.get(0).get(3));
        assertEquals("betty.rubble@example.com", data.get(1).get(3));
    }

    @Test
    public void testSortingByKey() {
        grid.add(4, "Betty", "Rubble", "betty.rubble@example.com");
        grid.add(2, "Wilma", "Flintstone", "wilma.flintstone@example.com");

        //1. Sort by ID and make sure items are correctly ordered
        grid.sort("ID");

        List<List> data = grid.data;
        assertEquals(2, data.get(0).get(0));
        assertEquals(4, data.get(1).get(0));

        //2. Sort by FirstName and make sure items are correctly ordered
        grid.sort("FirstName");

        data = grid.data;
        assertEquals("Betty", data.get(0).get(1));
        assertEquals("Wilma", data.get(1).get(1));

        //3. Sort by LastName DESC and make sure items are correctly ordered
        grid.sort("LastName", DataGrid.Sort.DESC);

        data = grid.data;
        assertEquals("Rubble", data.get(0).get(2));
        assertEquals("Flintstone", data.get(1).get(2));

        //4. Sort by EmailAddr DESC and make sure items are correctly ordered
        grid.sort("EmailAddr", DataGrid.Sort.DESC);

        data = grid.data;
        assertEquals("wilma.flintstone@example.com", data.get(0).get(3));
        assertEquals("betty.rubble@example.com", data.get(1).get(3));
    }

    @Test
    public void testSortingNumbers() {
        grid.add(10, "a", "b", "c");
        grid.add(140, "a", "b", "c");
        grid.add(4, "a", "b", "c");
        grid.add(1, "a", "b", "c");
        grid.add(14, "a", "b", "c");
        grid.add(100, "a", "b", "c");
        grid.sort(0);

        List<List> data = grid.data;
        assertEquals(1, data.get(0).get(0));
        assertEquals(4, data.get(1).get(0));
        assertEquals(10, data.get(2).get(0));
        assertEquals(14, data.get(3).get(0));
        assertEquals(100, data.get(4).get(0));
        assertEquals(140, data.get(5).get(0));
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

        grid.add(1, "Fred", "Flintstone", "fred.flintstone@example.com");
        grid.add(2, "Wilma", "Flintstone", "wilma.flintstone@example.com");
        grid.add(3, "Barney", "Rubble", "barney.rubble@example.com");
        grid.add(4, "Betty", "Rubble", "betty.rubble@example.com");
        grid.render(sb);

        assertEquals(expected.toString(), sb.toString());
    }

    @Test
    public void testClearing() {
        //1. Add data and make sure it's been added correctly
        grid.add(1, "Fred", "Flintstone", "fred.flintstone@example.com");
        grid.add(2, "Wilma", "Flintstone", "wilma.flintstone@example.com");
        grid.add(3, "Barney", "Rubble", "barney.rubble@example.com");
        grid.add(4, "Betty", "Rubble", "betty.rubble@example.com");

        assertEquals(4, grid.size());

        //2. Remove data from the grid and make sure it's been removed
        grid.clear();
        assertEquals(0, grid.size());
        assertTrue(grid.isEmpty());

        //3. Add new data and make sure widths have been reset correctly
        StringBuilder sb = new StringBuilder();
        StringBuilder expected = new StringBuilder();

        expected.append("+----+-----------+----------+-----------+\n")
                .append("| ID | FirstName | LastName | EmailAddr |\n")
                .append("+----+-----------+----------+-----------+\n")
                .append("|  1 | a         | b        | c         |\n")
                .append("+----+-----------+----------+-----------+\n");

        grid.add(1, "a", "b", "c");
        grid.render(sb);

        assertEquals(expected.toString(), sb.toString());
    }
}

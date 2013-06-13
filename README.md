datagrid.java
=============

DataGrid for Java

```java
DataGrid grid = new DataGrid("ID", "FirstName", "LastName", "EmailAddr");
		
grid.addDataRow(1, "Fred", "Flintstone", "fred.flintstone@example.com");
grid.addDataRow(2, "Wilma", "Flintstone", "wilma.flintstone@example.com");
grid.addDataRow(3, "Barney", "Rubble", "barney.rubble@example.com");
grid.addDataRow(4, "Betty", "Rubble", "betty.rubble@example.com");
		
if (grid.getRowCount() > 0) {
	grid.render();
	System.out.println(grid.getRowCount() + " rows in set\n");
} else {
	System.out.println("Empty set\n");
}
```

Code above will output the following data grid:

+----+-----------+------------+------------------------------+
| ID | FirstName | LastName   | EmailAddr                    |
+----+-----------+------------+------------------------------+
|  1 | Fred      | Flintstone | fred.flintstone@example.com  |
|  2 | Wilma     | Flintstone | wilma.flintstone@example.com |
|  3 | Barney    | Rubble     | barney.rubble@example.com    |
|  4 | Betty     | Rubble     | betty.rubble@example.com     |
+----+-----------+------------+------------------------------+
4 rows in set
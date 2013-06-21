datagrid.java [![Build Status](https://travis-ci.org/mzabriskie/datagrid.java.png?branch=master)](https://travis-ci.org/mzabriskie/datagrid.java)
=============

Command line DataGrid for Java

### Creating a DataGrid

```java
DataGrid grid = new DataGrid("ID", "FirstName", "LastName", "EmailAddr");
		
grid.add(1, "Fred", "Flintstone", "fred.flintstone@example.com");
grid.add(2, "Wilma", "Flintstone", "wilma.flintstone@example.com");
grid.add(3, "Barney", "Rubble", "barney.rubble@example.com");
grid.add(4, "Betty", "Rubble", "betty.rubble@example.com");
		
if (grid.size() > 0) {
	grid.render();
	System.out.println(grid.size() + " rows in set\n");
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
	
### Sorting a DataGrid

```java
// DataGrid can be sorted using the name of the column
grid.sort("FirstName");

// Alternatively you can use the index of the column
grid.sort(1);

// By default sorting is done ascending
// You can control the sorting direction using DataGrid.Sort
grid.sort("FirstName", DataGrid.Sort.DESC);
```
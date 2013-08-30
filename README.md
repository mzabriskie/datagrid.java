datagrid.java [![Build Status](https://travis-ci.org/mzabriskie/datagrid.java.png?branch=master)](https://travis-ci.org/mzabriskie/datagrid.java)
=============

Command line DataGrid for Java

## Creating a DataGrid

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
	
## Sorting a DataGrid

```java
// DataGrid can be sorted using the name of the column
grid.sort("FirstName");

// Alternatively you can use the index of the column
grid.sort(1);

// By default sorting is done ascending
// You can control the sorting direction using DataGrid.Sort
grid.sort("FirstName", DataGrid.Sort.DESC);
```

## DataGrid API

#### DataGrid(List&lt;String&gt; cols)
Constructs a <code>DataGrid</code> object using the <code>java.util.List</code> specified as the column names

#### DataGrid(String… cols)
Constructs a <code>DataGrid</code> object using the <code>java.lang.String[]</code> specified as the column names

#### add(Object… row)
Appends the <code>Object[]</code> specified to the end of the <code>DataGrid</code> rows

#### add(List row)
Appends the <code>java.util.List</code> specified to the end of the <code>DataGrid</code> rows

#### size()
Returns the number of rows in this <code>DataGrid</code>

#### clear()
Removes all the rows from this <code>DataGrid</code>

#### isEmpty()
Test if this <code>DataGrid</code> has no rows

#### sort(String key)
Sort this <code>DataGrid</code> using the column specified using an ascending sort order

#### sort(String key, Sort sort)
Sort this <code>DataGrid</code> using the column and sort order specified

#### sort(int key)
Sort this <code>DataGrid</code> using the column specified using an ascending sort order

#### sort(int key, Sort sort)
Sort this <code>DataGrid</code> using the column and sort order specified

#### render()
Render this <code>DataGrid</code> using the <code>java.io.PrintStream</code> specified by <code>System.out</code>

#### render(StringBuilder sb)
Render this <code>DataGrid</code> using the <code>java.lang.StringBuilder</code> specified

## Building and Testing

You can build the source using ant:

```bash
$ ant build
```
	
This will compile the source and place the output in a folder called <em>out</em> under the root directory of the project.

You can likewise run the test suite using ant:

```bash
$ ant test
```
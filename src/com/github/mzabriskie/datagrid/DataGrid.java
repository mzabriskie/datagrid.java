/*

Copyright (c) 2013 by Matt Zabriskie

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

*/

package com.github.mzabriskie.datagrid;

import java.util.*;

public class DataGrid {
	List<List> data;
	List<String> columns;
	Map<String, Integer> widths;

    public static enum Sort {
        ASC {
            @Override
            int compare(Calendar a, Calendar b) {
                return a.compareTo(b);
            }

            @Override
            int compare(Date a, Date b) {
                return a.compareTo(b);
            }

            @Override
            int compare(String a, String b) {
                return a.compareTo(b);
            }

            @Override
            int compare(Number a, Number b) {
                int result = 0;
                if (a instanceof Byte && b instanceof Byte) {
                    result = ((Byte) a).compareTo((Byte) b);
                } else if (a instanceof Short && b instanceof Short) {
                    result = ((Short) a).compareTo((Short) b);
                } else if (a instanceof Integer && b instanceof Integer) {
                    result = ((Integer) a).compareTo((Integer) b);
                } else if (a instanceof Long && b instanceof Long) {
                    result = ((Long) a).compareTo((Long) b);
                } else if (a instanceof Float && b instanceof Float) {
                    result = ((Float) a).compareTo((Float) b);
                } else if (a instanceof Double && b instanceof Double) {
                    result = ((Double) a).compareTo((Double) b);
                }
                return result;
            }
        },
        DESC {
            @Override
            int compare(Calendar a, Calendar b) {
                return b.compareTo(a);
            }

            @Override
            int compare(Date a, Date b) {
                return b.compareTo(a);
            }

            @Override
            int compare(String a, String b) {
                return b.compareTo(a);
            }

            @Override
            int compare(Number a, Number b) {
                int result = 0;
                if (a instanceof Byte && b instanceof Byte) {
                    result = ((Byte) b).compareTo((Byte) a);
                } else if (a instanceof Short && b instanceof Short) {
                    result = ((Short) b).compareTo((Short) a);
                } else if (a instanceof Integer && b instanceof Integer) {
                    result = ((Integer) b).compareTo((Integer) a);
                } else if (a instanceof Long && b instanceof Long) {
                    result = ((Long) b).compareTo((Long) a);
                } else if (a instanceof Float && b instanceof Float) {
                    result = ((Float) b).compareTo((Float) a);
                } else if (a instanceof Double && b instanceof Double) {
                    result = ((Double) b).compareTo((Double) a);
                }
                return result;
            }
        };

        abstract int compare(Calendar a, Calendar b);
        abstract int compare(Date a, Date b);
        abstract int compare(String a, String b);
        abstract int compare(Number a, Number b);
    }

	private DataGrid() {
		data = new ArrayList<List>();
		columns = new ArrayList<String>();
		widths = new HashMap<String, Integer>();
	}

    /**
     * Constructs a DataGrid object using the {@link java.util.List} specified as the column names.
     *
     * @param cols the names of the columns to be used for this DataGrid
     */
    public DataGrid(List<String> cols) {
		this();
		columns = cols;
	}

    /**
     * Constructs a DataGrid object using the {@link java.lang.String[]} specified as the column names.
     *
     * @param cols the names of the columns to be used for this DataGrid
     */
    public DataGrid(String... cols) {
		this(Arrays.asList(cols));
	}

    /**
     * Appends the {@link java.lang.Object[]} specified to the end of the DataGrid.
     *
     * @param row the data to be appended to this DataGrid
     */
    public void add(Object... row) {
		add(Arrays.asList(row));
	}

    /**
     * Appends the {@link java.util.List} specified to the end of the DataGrid.
     *
     * @param row the data to be appended to this DataGrid
     */
    public void add(List row) {
		assert row.size() == columns.size() : "Invalid data: incorrect number of columns";
		data.add(row);
		syncWidths(row);
	}

    /**
     * Returns the number of rows in this DataGrid.
     *
     * @return the number of rows in this DataGrid
     */
    public int size() {
		return data.size();
	}

    /**
     * Removes all of the rows from this DataGrid.
     */
    public void clear() {
        data.clear();
        widths.clear();
    }

    /**
     * Test if this DataGrid has no rows.
     *
     * @return <code>true</code> if this list has no elements; <code>false</code> otherwise
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Sort this DataGrid using the column specified using an ascending sort order.
     *
     * @param key the name of the column to sort this DataGrid by
     */
    public void sort(String key) {
        sort(key, Sort.ASC);
    }

    /**
     * Sort this DataGrid using the column specified using the sort order specified.
     *
     * @param key the name of the column to sort this DataGrid by
     * @param sort the sort order to use when sorting this DataGrid
     */
    public void sort(String key, Sort sort) {
        sort(getIdxForColumn(key), sort);
    }

    /**
     * Sort this DataGrid using the column specified using an ascending sort order.
     *
     * @param index the index of the column to sort this DataGrid by
     */
    public void sort(int index) {
        sort(index, Sort.ASC);
    }

    /**
     * Sort this DataGrid using the column specified using the sort order specified.
     *
     * @param index the index of the column to sort this DataGrid by
     * @param sort the sort order to use when sorting this DataGrid
     */
    public void sort(final int index, final Sort sort) {
        Collections.sort(data, new Comparator<List>() {
            @Override
            public int compare(List a, List b) {
                int result;
                Object val1 = a.get(index);
                Object val2 = b.get(index);

                if (val1 instanceof Number && val2 instanceof Number) {
                    result = sort.compare((Number) val1, (Number) val2);
                } else if (val1 instanceof Date && val2 instanceof Date) {
                    result = sort.compare((Date) val1, (Date) val2);
                } else if (val1 instanceof Calendar && val2 instanceof Calendar) {
                    result = sort.compare((Calendar) val1, (Calendar) val2);
                } else {
                    result = sort.compare(String.valueOf(val1), String.valueOf(val2));
                }
                return result;
            }
        });
    }

    /**
     * Render this DataGrid using the {@link java.io.PrintStream} specified by System.out.
     */
    public void render() {
        StringBuilder sb = new StringBuilder();
        render(sb);
        System.out.println(sb);
    }

    /**
     * Render this DataGrid using the {@link java.lang.StringBuilder} specified.
     *
     * @param sb the {@link java.lang.StringBuilder} to render this DataGrid to
     */
    public void render(StringBuilder sb) {
        String rowSeparator = getRowSeparator();
        sb.append(rowSeparator).append("\n");

        sb.append("|");
        for (String column : columns) {
            sb.append(pad(column, getWidthForColumn(column)));
            sb.append("|");
        }
        sb.append("\n");

        sb.append(rowSeparator).append("\n");

        for (List row : data) {
            sb.append("|");
            for (int i=0; i<row.size(); i++) {
                String key = getKeyForColumn(i);
                sb.append(pad(row.get(i), getWidthForColumn(key)));
                sb.append("|");
            }
            sb.append("\n");
        }

        sb.append(rowSeparator).append("\n");
    }

	private String getRowSeparator() {
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		for (String column : columns) {
			sb.append(repeat("-", getWidthForColumn(column) + 2));
			sb.append("+");
		}
		return sb.toString();
	}

	private int getIdxForColumn(String key) {
		for (int i=0; i<columns.size(); i++) {
			if (columns.get(i).equals(key)) return i;
		}
		return -1;
	}

	private String getKeyForColumn(int index) {
		return columns.get(index);
	}

	private String getDisplayForColumn(Object val) {
        String display;

        if (val instanceof Calendar) {
            // Calendar.toString produces a less than human friendly result.
            // Display Date.toString instead.
            display = ((Calendar) val).getTime().toString();
        } else {
            display = String.valueOf(val);
        }

		return display;
	}

	private int getWidthForColumn(String key) {
		Integer width = widths.get(key);
		if (width == null) {
			width = 0;
		}
		return Math.max(columns.get(getIdxForColumn(key)).length(), width);
	}

	private void syncWidths(List row) {
		for (int i=0; i<row.size(); i++) {
			String key = getKeyForColumn(i);
			String val = getDisplayForColumn(row.get(i));

			if (widths.get(key) == null || val.length() > widths.get(key)) {
				widths.put(key, val.length());
			}
		}
	}

	private String repeat(String str, int count) {
		String result = "";
		for (int i=0; i<count; i++) {
			result += str;
		}
		return result;
	}

	private String pad(Object obj, int length) {
		String result = "";
		String display = getDisplayForColumn(obj);

		result += repeat(" ", length - display.length());

		if (obj instanceof Number) {
			result = result + display;
		} else {
			result = display + result;
		}

		return " " + result + " ";
	}
}


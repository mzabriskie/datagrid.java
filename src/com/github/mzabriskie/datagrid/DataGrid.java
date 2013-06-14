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
	private List<List> data;
	private List<String> columns;
	private Map<String, Integer> widths;

	private DataGrid() {
		data = new ArrayList<List>();
		columns = new ArrayList<String>();
		widths = new HashMap<String, Integer>();
	}

	public DataGrid(List<String> cols) {
		this();
		columns = cols;
	}

	public DataGrid(String... cols) {
		this(Arrays.asList(cols));
	}

	public void addDataRow(Object... cols) {
		addDataRow(Arrays.asList(cols));
	}

	public void addDataRow(List row) {
		assert row.size() == columns.size() : "Invalid row: incorrect number of columns";
		data.add(row);
		syncWidths(row);
	}

    public int getColCount() {
        return columns.size();
    }

	public int getRowCount() {
		return data.size();
	}

	public void render() {
        StringBuilder sb = new StringBuilder();
        render(sb);
        System.out.println(sb);
    }

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

	private String getValForColumn(Object val) {
		return String.valueOf(val);
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
			String val = getValForColumn(row.get(i));

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
		String val = getValForColumn(obj);

		result += repeat(" ", length - val.length());

		if (obj instanceof Number) {
			result = result + val;
		} else {
			result = val + result;
		}

		return " " + result + " ";
	}
}


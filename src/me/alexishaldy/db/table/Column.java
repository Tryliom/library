package me.alexishaldy.db.table;

import java.util.Vector;

/**
 * This class defines a column in a table and the link to the next column.
 * This way, a full table is defined through a set of columns and links.
 * This structure is dynamic and, therefore, it can be used for any table with
 * any number of columns.
 * @author Ronald T. Fernandez
 * @version 1.0
 */
public class Column {
	/**
	 * Name of the column
	 */
	private String _columnName;
	/**
	 * Values saved in the column
	 */
	private Vector<String> _values;
	/**
	 * Link to the next column in the table
	 */
	private Column _neighbor;

	/**
	 * Class constructor
	 */
	public Column() {
		_columnName = "";
		_values = new Vector<String>();
		_neighbor = null;
	}

	/**
	 * Class constructor.
	 * @param columnName	Name of the column in the table from the database
	 */
	public Column(String columnName) {
		_columnName = columnName;
		_values = new Vector<String>();
		_neighbor = null;
	}

	/**
	 * Class constructor
	 * @param columnName	Name of the column in the table from the database
	 * @param value			A single value in the column
	 */
	public Column(String columnName, String value) {
		_columnName = columnName;
		_values = new Vector<String>();
		_values.addElement(value);
		_neighbor = null;
	}

	/**
	 * Add a value to the column
	 * @param value			Value to be added
	 */
	public void addValue(String value) {
		_values.addElement(value);
	}

	/**
	 * Get column values
	 * @return		Values stored in the table
	 */
	public Vector<String> getValues() {
		return _values;
	}

	/**
	 * Get the name of the column
	 * @return 					Name of the column
	 */
	public final String getColumnName() {
		return _columnName;
	}

	/**
	 * Set a row neighbor to the current rows
	 * @param column			Neighbor rows
	 */
	public void setNeighbor(Column column) {
		_neighbor = column;
	}

	/**
	 * Get the link to the next column in the table
	 * @return					Neighbor column to the given one
	 */
	public Column getNeighbor() {
		return _neighbor;
	}
}
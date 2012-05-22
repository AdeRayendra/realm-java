package com.tightdb;

import java.nio.ByteBuffer;
import java.util.Date;

import com.tightdb.lib.IRowsetBase;

/**
 * This class represents a view of a particular table. We can think of
 * a tableview is a subset of a table. It contains less than or 
 * equal to the number of entries of a table. A table view is often a
 * result of a query. 
 * 
 * The view don't copy data from the table, but merely has a list of 
 * row-references into the original table with the real data.
 * 
 * The class serves as a base class of all table view. It is a raw level table view, users 
 * are not encouraged to use this class. Instead users are advised to use 
 * the generated subclass version of the table.
 * 
 * Let us assume we are going to keep track of a table to store the employees 
 * of a company or an organization.
 * 
 * Following is a small example how to use the autogenerated class of the 
 * tableview. For that purpose we will first define the spec of an employee 
 * entity
 * 
 * 	@Table
 *	public class employee {
 *		String name;
 *		long age;
 *		boolean hired;
 *		byte[] imageData;
 *	}
 * 
 * Once this class is compiled along with TightDB annotation processor
 * this will produce following classes.
 * 
 * 1. Employee
 * 2. EmployeeTable
 * 3. EmployeeView.
 * 
 * In this class context our interest is EmployeeView which will be inherited from
 * this class.
 * 
 * The generated class will have more specialized method to do operations on TableView.
 * 
 */
public class TableViewBase implements IRowsetBase {
	/**
	 * Creates a TableViewBase for the TableBase table. The constructor
	 * implicitly create the native object tableview which is the backbone
	 * of this class.
	 * 
	 * @param table The table.
	 */
	public TableViewBase(TableBase table){
		this.table = table;
		this.tableView = null;
		this.nativePtr = createNativeTableView(this.table);
	}
	
	/**
	 * Creates a TableViewBase with a Java Object Table and a already created
	 * native reference to a TableView. This method is not supposed to be 
	 * called by a user of this db. It is for internal use only.
	 * 
	 * @param table The table.
	 * @param nativePtr pointer to table.
	 */
	protected TableViewBase(TableBase table, long nativePtr){
		this.table = table;
		this.tableView = null;
		this.nativePtr = nativePtr;
	}
	
	/**
	 * Creates a TableView with already created Java TableView Object and a 
	 * native native TableView object reference. The method is not supposed to
	 * be called by the user of the db. The method is for internal use only.
	 * 
	 * @param tableView A table view.
	 * @param nativePtr pointer to table.
	 */
	public TableViewBase(TableViewBase tableView, long nativePtr){
		this.table = null;
		this.tableView = tableView;
		this.nativePtr = nativePtr;
	}
	
	/**
	 * Checks whether this table is empty or not.
	 * 
	 * @return true if empty, otherwise false.
	 */
	public boolean isEmpty(){
		return size() != 0;
	}

	/**
	 * Get the number of entries/rows of this table. 
	 * 
	 * @return The number of rows.
	 */
	public long size(){
		return nativeSize(nativePtr);
	}

	protected native long nativeSize(long nativeViewPtr);
	
	/**
	 * Get the value of the particular (integer) cell.
	 * 
	 * @param columnIndex 0 based index value of the column.
	 * @param rowIndex 0 based row value of the column.
	 * @return value of the particular cell.
	 */
	public long getLong(long columnIndex, long rowIndex){
		return nativeGetLong(nativePtr, columnIndex, rowIndex);
	}
	
	protected native long nativeGetLong(long nativeViewPtr, long columnIndex, long rowIndex);
	
	/**
	 * Get the value of the particular (boolean) cell.
	 * 
	 * @param columnIndex 0 based index value of the cell column.
	 * @param rowIndex 0 based index of the row.
	 * @return value of the particular cell.
	 */
	public boolean getBoolean(long columnIndex, long rowIndex){
		return nativeGetBoolean(nativePtr, columnIndex, rowIndex);
	}
	
	protected native boolean nativeGetBoolean(long nativeViewPtr, long columnIndex, long rowIndex);
	
	/**
	 * Get the value of the particular (date) cell.
	 * 
	 * @param columnIndex 0 based index value of the cell column.
	 * @param rowIndex 0 based index of the row.
	 * @return value of the particular cell.
	 */
	public Date getDate(long columnIndex, long rowIndex){
		return new Date(nativeGetDateTimeValue(nativePtr, columnIndex, rowIndex));
	}
	
	protected native long nativeGetDateTimeValue(long nativeViewPtr, long columnIndex, long rowIndex);

	/**
	 * Get the value of a (string )cell.
	 * 
	 * @param columnIndex 0 based index value of the column
	 * @param rowIndex 0 based index of the row.
	 * @return value of the particular cell
	 */
	public String getString(long columnIndex, long rowIndex){
		return nativeGetString(nativePtr, columnIndex, rowIndex);
	}
	
	protected native String nativeGetString(long nativeViewPtr, long columnInde, long rowIndex);
	
	
	/**
	 * Get the  value of a (binary) cell.
	 * 
	 * @param columnIndex 0 based index value of the cell column
	 * @param rowIndex 0 based index value of the cell row
	 * @return value of the particular cell.
	 */
	public ByteBuffer getBinary(long columnIndex, long rowIndex){
		return nativeGetBinary(nativePtr, columnIndex, rowIndex);
	}
	
	protected native ByteBuffer nativeGetBinary(long nativeViewPtr, long columnIndex, long rowIndex);
	
	public Mixed getMixed(long columnIndex, long rowIndex){
		return nativeGetMixed(nativePtr, columnIndex, rowIndex);
	}
	
	protected native Mixed nativeGetMixed(long nativeViewPtr, long columnIndex, long rowIndex);
	
	public TableBase getSubTable(long columnIndex, long rowIndex){
		return new TableBase(nativeGetSubTable(nativePtr, columnIndex, rowIndex));
	}
	
	protected native long nativeGetSubTable(long nativeViewPtr, long columnIndex, long rowIndex);
	
	// Methods for setting values.

	/**
	 * Sets the value for a particular (integer) cell.
	 * 
	 * @param columnIndex column index of the cell
	 * @param rowIndex row index of the cell
	 * @param value
	 */
	public void setLong(long columnIndex, long rowIndex, long value){
		nativeSetLong(nativePtr, columnIndex, rowIndex, value);
	}
	
	protected native void nativeSetLong(long nativeViewPtr, long columnIndex, long rowIndex, long value);
	
	/**
	 * Sets the value for a particular (boolean) cell.
	 * 
	 * @param columnIndex column index of the cell
	 * @param rowIndex row index of the cell
	 * @param value
	 */
	public void setBoolean(long columnIndex, long rowIndex, boolean value){
		nativeSetBoolean(nativePtr, columnIndex, rowIndex, value);
	}
	
	protected native void nativeSetBoolean(long nativeViewPtr, long columnIndex, long rowIndex, boolean value);

	/**
	 * Sets the value for a particular (date) cell.
	 * 
	 * @param columnIndex column index of the cell
	 * @param rowIndex row index of the cell
	 * @param value
	 */	
	public void setDate(long columnIndex, long rowIndex, Date value){
		nativeSetDateTimeValue(nativePtr, columnIndex, rowIndex, value.getTime());
	}
	
	protected native void nativeSetDateTimeValue(long nativePtr, long columnIndex, long rowIndex, long dateTimeValue);
	
	/**
	 * Sets the value for a particular (sting) cell.
	 * 
	 * @param columnIndex column index of the cell
	 * @param rowIndex row index of the cell
	 * @param value
	 */
	public void setString(long columnIndex, long rowIndex, String value){
		nativeSetString(nativePtr, columnIndex, rowIndex, value);
	}
	
	protected native void nativeSetString(long nativeViewPtr, long columnIndex, long rowIndex, String value);

	/**
	 * Sets the value for a particular (binary) cell.
	 * 
	 * @param columnIndex column index of the cell
	 * @param rowIndex row index of the cell
	 * @param data
	 */
	public void setBinary(long columnIndex, long rowIndex, ByteBuffer data){
		nativeSetBinary(nativePtr, columnIndex, rowIndex, data);
	}
	
	protected native void nativeSetBinary(long nativeViewPtr, long columnIndex, long rowIndex, ByteBuffer data);

	/**
	 * Sets the value for a particular (mixed typed) cell.
	 * 
	 * @param columnIndex column index of the cell
	 * @param rowIndex row index of the cell
	 * @param data
	 */
	public void setMixed(long columnIndex, long rowIndex, Mixed data){
		nativeSetMixed(nativePtr, columnIndex, rowIndex, data);
	}
	
	protected native void nativeSetMixed(long nativeViewPtr, long columnIndex, long rowIndex, Mixed value);
	
	// Methods for deleting.
	public void clear(){
		nativeClear(nativePtr);
	}
	
	protected native void nativeClear(long nativeViewPtr);

	/**
	 * Removes a particular row identified by the index from the tableview.
	 * [citation needed] The corresponding row of the table also get deleted 
	 * for which the tableview is part of.
	 * 
	 * @param rowIndex the row index 
	 */
	public void removeRow(long rowIndex){
		nativeRemoveRow(nativePtr, rowIndex);
	}
	
	protected native void nativeRemoveRow(long nativeViewPtr, long rowIndex);
	
	public void removeLast(){
		if(!isEmpty()){
			removeRow(size() - 1);
		}
	}
	
	// Searching functions.
	public long findFirst(long columnIndex, long value){
		return nativeFindFirst(nativePtr, columnIndex, value);
	}
	
	protected native long nativeFindFirst(long nativeTableViewPtr, long columnIndex, long value);
	
	protected long findFirst(long columnIndex, String value){
		return nativeFindFirst(nativePtr, columnIndex, value);
	}
	
	protected native long nativeFindFirst(long nativePtr, long columnIndex, String value);
	
	public TableViewBase findAll(long columnIndex, long value){
		return new TableViewBase(this,  nativeFindAll(nativePtr, columnIndex, value));
	}
	
	protected native long nativeFindAll(long nativePtr, long columnIndex, long value);
	
	public TableViewBase findAll(long columnIndex, String value){
		return new TableViewBase(this, nativeFindAll(nativePtr, columnIndex, value));
	}
	
	protected native long nativeFindAll(long nativePtr, long columnIndex, String value);

	/** 
	 * Calculate the sum of the values in a particular column of this 
	 * tableview. 
	 *
	 * Note: the type of the column marked by the columnIndex has to be of
	 * type ColumnType.ColumnTypeInt for obvious reason.
	 * 
	 * @param columnIndex column index
	 * @return the sum of the values in the column
	 */
	public long sum(long columnIndex){
		return nativeSum(nativePtr, columnIndex);
	}
	
	protected native long nativeSum(long nativeViewPtr, long columnIndex);
	
	/** 
	 * Returns the maximum value of the cells in a column.
	 * 
	 * Note: for this method to work the Type of the column 
	 * identified by the columnIndex has to be ColumnType.ColumnTypeInt.
	 * 
	 * @param columnIndex column index
	 * @return the maximum value
	 */
	public long maximum(long columnIndex){
		return nativeMaximum(nativePtr, columnIndex);
	}
	
	protected native long nativeMaximum(long nativeViewPtr, long columnIndex);
	
	/** 
	 * Returns the minimum value of the cells in a column.
	 * 
	 * Note: for this method to work the Type of the column 
	 * identified by the columnIndex has to be ColumnType.ColumnTypeInt.
	 * 
	 * @param columnIndex column index
	 * @return the minimum value
	 */
	public long minimum(long columnIndex){
		return nativeMinimum(nativePtr, columnIndex);
	}
	
	protected native long nativeMinimum(long nativeViewPtr, long columnIndex);
	
	
	public void sort(long columnIndex, boolean ascending){
		nativeSort(nativePtr, columnIndex, ascending);
	}
	
	protected native void nativeSort(long nativeTableViewPtr, long columnIndex, boolean ascending);
	
	public void sort(long columnIndex){
		sort(columnIndex, true);
	}
	
	protected native long createNativeTableView(TableBase table);
	
	public void finalize(){
		close();
	}
	
	public void close(){
		if(nativePtr == 0)
			return;
		nativeClose(nativePtr);
		nativePtr = 0;
	}
	
	protected native void nativeClose(long nativeViewPtr);
	
	protected TableBase getRootTable(){
		if(table != null)
			return table;
		return tableView.getRootTable();
	}
	
	protected long nativePtr;
	protected TableBase table;
	protected TableViewBase tableView;
	
	@Override
	public void remove(long index) {
		removeRow(index);
	}
	
}

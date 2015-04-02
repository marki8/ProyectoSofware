package com.ps.gui.jgrid;

import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import com.ps.common.Book;

public class SelectionModel implements ListModel {

	private List<Book> bookList;

	public SelectionModel(List<Book> list) {
		this.bookList = list;
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
	}

	@Override
	public int getSize() {
		return bookList.size();
	}

	@Override
	public Object getElementAt(int index) {
		return bookList.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
	}
}

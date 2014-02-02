package com.orekyuu.javatter.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.EventObject;

import javax.swing.CellRendererPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TimelineTable extends JTable{
	private static final long serialVersionUID = 1L;

	public DefaultTableModel model = new DefaultTableModel(new Object[] {""}, 0);

	public TimelineTable(){
		super();
		setTableHeader(null);
		setIntercellSpacing(new Dimension(0, 0));
		setDefaultEditor(Object.class, new TableCellEditor(){
			@Override
			public void addCellEditorListener(CellEditorListener arg0){
			}

			@Override
			public void cancelCellEditing(){
			}

			@Override
			public Object getCellEditorValue(){
				return null;
			}

			@Override
			public boolean isCellEditable(EventObject arg0){
				return true;
			}

			@Override
			public void removeCellEditorListener(CellEditorListener arg0){
			}

			@Override
			public boolean shouldSelectCell(EventObject arg0){
				return true;
			}

			@Override
			public boolean stopCellEditing(){
				return true;
			}

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean select, int row, int column){
				return (Component) value;
			}
		});
		setModel(model);
		setDefaultRenderer(Object.class, new TableCellRenderer(){
			public Component getTableCellRendererComponent(JTable table, Object value, boolean select, boolean focus, int row, int column){
				JPanel p = (JPanel) value;
				p.setMinimumSize(null);
				p.setPreferredSize(null);
				p.setMaximumSize(null);
				int height = p.getPreferredSize().height;
				if (table.getRowHeight(row) != height){
					table.setRowHeight(row, p.getPreferredSize().height);
				}
				return p;
			}
		});
	}

	public void addTop(Object o){
		if (isEditing()){
			removeEditor();
		}
		model.insertRow(0, new Object[] {o});
	}

	public void addLast(Object o){
		if (isEditing()){
			removeEditor();
		}
		model.addRow(new Object[] {o});
	}

	@Override
	public void removeEditor(){
		for (Component c : getComponents()){
			if (!(c instanceof CellRendererPane)){
				remove(c);
			}
		}
		super.removeEditor();
	}
}

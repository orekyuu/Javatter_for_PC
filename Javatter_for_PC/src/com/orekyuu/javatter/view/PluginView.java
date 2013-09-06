package com.orekyuu.javatter.view;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.orekyuu.javatter.util.BackGroundColor;
import com.orekyuu.javatter.viewobserver.PluginViewObserver;

/**
 * プラグイン管理のタブを表すクラス
 * @author orekyuu
 *
 */
public class PluginView implements PluginViewObserver, IJavatterTab{

	private final DefaultListModel<String> model = new DefaultListModel<String>();
	private final List<String> pluginName=new ArrayList<String>();
	private final Map<String,IJavatterTab> configMap=new HashMap<String,IJavatterTab>();

	@Override
	public Component getComponent()
	{
		final JList<String> list=new JList<String>();
		list.setBackground(BackGroundColor.color);
		list.setDragEnabled(false);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(model);
		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				String s=pluginName.get(list.getSelectedIndex());
				IJavatterTab tab=configMap.get(s);
				JDialog dialog=new JDialog();
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.add(tab.getComponent());
				dialog.setTitle(s);
				dialog.setSize(400, 400);
				dialog.setVisible(true);
				list.clearSelection();
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		JScrollPane tp = new JScrollPane(20, 31);
		tp.setViewportView(list);
		tp.getVerticalScrollBar().setUnitIncrement(20);
		return tp;
	}

	@Override
	public void update(String[] pluginName,String[] version)
	{
		model.clear();
		this.pluginName.clear();
		for (int i=0;i<pluginName.length;i++){
			model.addElement(pluginName[i]+" "+version[i]);
			this.pluginName.add(pluginName[i]);
		}

	}

	@Override
	public void addPluginConfigTab(String title, IJavatterTab tab) {
		configMap.put(title, tab);
	}
}
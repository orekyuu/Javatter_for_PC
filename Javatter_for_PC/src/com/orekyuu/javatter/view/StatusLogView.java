package com.orekyuu.javatter.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 * ステータスバーのログを表示するダイアログ.
 * @author Getaji
 *
 */
public class StatusLogView extends JDialog {
	
	/** リストのモデル. */
	private DefaultListModel<String> listModel;
	
	/** リスト. */
	private JList<String> list;
	
	/** スクロールバー. */
	private JScrollPane scrollPane;
	
	/** クリアボタン. */
	private JButton buttonClear;
	
	/** ログ. */
	private static List<String> log = new ArrayList<String>();;
	
	/**
	 * 
	 * @param parent 親フレーム（だいたいはMainWindowViewのwindowを指定する）
	 */
	public StatusLogView(JFrame parent) {
		super(parent, "ステータスログ");
		setLayout(new BorderLayout());
		setModal(true);
		setSize(400, 500);
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		for (String str : log) {
			listModel.add(0, str);
		}
		list.setCellRenderer(new SpriteCellRenderer());
		list.setFont(new Font("Meiryo UI", Font.PLAIN, 12));
		scrollPane = new JScrollPane();
		scrollPane.add(list);
		
		buttonClear = new JButton("履歴をクリアする");
		buttonClear.setPreferredSize(new Dimension(300, 30));
		buttonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(
						null, "履歴をクリアしますか？", "確認",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (i == JOptionPane.OK_OPTION) {
					listModel.removeAllElements();
					log.removeAll(log);
				}
			}
		});
		
		add(list, BorderLayout.CENTER);
		add(buttonClear, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	/**
	 * ログテキストを追加する.
	 * @param text ログテキスト
	 */
	public static void addLog(String text) {
		log.add(text);
	}
}

/**
 * リストの項目の色を交互に変えるためのレンダラ.
 * @author Getaji
 *
 */
class SpriteCellRenderer extends DefaultListCellRenderer {
	
	@Override
	public Component getListCellRendererComponent(JList list,
			Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
                index, isSelected, cellHasFocus);

        // 奇数行を薄い青にする
        if (index % 2 != 0) {
            if (list.isSelectedIndex(index)) {
                // 選択行はデフォルトの色
                label.setBackground(list.getSelectionBackground());
            } else {
                // 選択してない行は薄い青
                label.setBackground(new Color(240, 240, 255));
            }
        }
        return label;
	}
}

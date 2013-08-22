package javatter.plugin.omikuzi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.orekyuu.javatter.util.SaveData;
import com.orekyuu.javatter.view.IJavatterTab;

public class OmikuziConfigView implements IJavatterTab, ActionListener{

	private SaveData data;
	private JCheckBox check;
	private JTextArea area;
	private JButton button;
	public OmikuziConfigView(SaveData data){
		this.data=data;
	}

	@Override
	public Component getComponent() {
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel enable=new JPanel();
		enable.setLayout(new BoxLayout(enable, BoxLayout.LINE_AXIS));
		JLabel label=new JLabel("おみくじ機能を使う");
		check=new JCheckBox();
		check.addActionListener(this);
		check.setSelected(data.getBoolean("omikuzi"));
		enable.add(label);
		enable.add(check);
		panel.add(enable,BorderLayout.PAGE_START);

		area=new JTextArea();
		area.setText(data.getString("OmikuziText"));
		JScrollPane pane=new JScrollPane();
		pane.setViewportView(area);
		pane.getVerticalScrollBar().setUnitIncrement(20);
		panel.add(pane,BorderLayout.CENTER);

		button=new JButton("更新");
		button.addActionListener(this);
		panel.add(button,BorderLayout.PAGE_END);

		System.out.println("omikuzi");

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(check)){
			data.setBoolean("omikuzi", check.isSelected());
		}
		if(arg0.getSource().equals(button)){
			data.setString("OmikuziText", area.getText());
		}
	}

}

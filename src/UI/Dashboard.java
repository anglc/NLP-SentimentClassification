package UI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import main.Main;

public class Dashboard extends JFrame {
	SpinnerModel ngramModel;
	JSpinner ngramSpinner;
	SpinnerModel topModel;
	JSpinner topSpinner;
	JSlider trainingAndtestingRatio;
	JTextField trainingField, consoleField;
	JButton trainingButton;

	public Dashboard() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}

		this.setTitle("Dashboard");
		this.setSize(400, 400);
		this.setLayout(new BorderLayout());
		this.add(createSettingPanel(), BorderLayout.NORTH);
		this.add(createChartPanel(), BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public JPanel createSettingPanel() {
		JPanel p = new JPanel();
		JPanel spinnerBlock = new JPanel(), ratioBlock = new JPanel(), fileBlock = new JPanel(), workBlock = new JPanel();
		ngramModel = new SpinnerNumberModel(2, 1, 6, 1);
		ngramSpinner = new JSpinner(ngramModel);
		topModel = new SpinnerNumberModel(10000, 1, 100000, 1);
		topSpinner = new JSpinner(topModel);
		trainingAndtestingRatio = new JSlider(JSlider.HORIZONTAL, 1, 10, 8);
		trainingField = new JTextField("training_set");
		consoleField = new JTextField("output");
		trainingButton = new JButton("Training");

		trainingField.setEditable(false);
		trainingField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				topSpinner.requestFocus();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(trainingField.getText()));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath = chooser.getSelectedFile()
							.getAbsolutePath();
					trainingField.setText(filepath);
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		consoleField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				topSpinner.requestFocus();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(consoleField.getText()));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath = chooser.getSelectedFile()
							.getAbsolutePath();
					trainingField.setText(filepath);
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		trainingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int Ngram = (Integer) ngramSpinner.getValue();
				int topNgram = (Integer) topSpinner.getValue();
				String trainingPath = trainingField.getText();
				Main.work(Ngram, topNgram, trainingPath, 1, 1);
			}
			
		});

		trainingAndtestingRatio.setMajorTickSpacing(2);
		trainingAndtestingRatio.setMinorTickSpacing(1);
		trainingAndtestingRatio.setPaintLabels(true);
		trainingAndtestingRatio.setPaintTicks(true);
		trainingAndtestingRatio.setSnapToTicks(true);

		spinnerBlock.setLayout(new GridLayout(1, 4));
		spinnerBlock.add(new JLabel(" N-gram "));
		spinnerBlock.add(ngramSpinner);
		spinnerBlock.add(new JLabel(" Top-features "));
		spinnerBlock.add(topSpinner);

		ratioBlock.setLayout(new GridLayout(1, 2));
		ratioBlock.add(new JLabel(" Training : Testing "));
		ratioBlock.add(trainingAndtestingRatio);

		fileBlock.setLayout(new GridLayout(2, 2));
		fileBlock.add(new JLabel(" Training source file"));
		fileBlock.add(trainingField);
		fileBlock.add(new JLabel(" Console log file"));
		fileBlock.add(consoleField);
		
		workBlock.setLayout(new BorderLayout());
		workBlock.add(trainingButton, BorderLayout.EAST);

		p.setLayout(new GridLayout(4, 1));
		p.setBorder(BorderFactory.createTitledBorder("setting"));
		p.add(spinnerBlock);
		p.add(ratioBlock);
		p.add(fileBlock);
		p.add(workBlock);
		return p;
	}

	public JPanel createChartPanel() {
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder("chart"));
		return p;
	}
}

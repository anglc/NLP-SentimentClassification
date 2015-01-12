package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import main.Article;
import main.Main;
import main.ReturnCell;
import model.Classifier;
import model.ModelUtilities;
import model.PassiveAggressive;
import model.nGram;

public class Dashboard extends JFrame {
	SpinnerModel ngramModel;
	JSpinner ngramSpinner;
	SpinnerModel topModel;
	JSpinner topSpinner;
	JSlider trainingAndtestingRatio;
	JTextField trainingField, testField, consoleField;
	JButton trainingButton, updateButton;
	JTextArea reportArea;
	JTextArea classifyArea;
	JButton classifyButton;

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

		new Thread("stdin-piped") {
			public void run() {
				while (true) {
					try {
						PipedOutputStream poutput = new PipedOutputStream();
						PipedInputStream pinput = new PipedInputStream(poutput);
						System.setOut(new PrintStream(poutput, true));
						Thread.sleep(1000);
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(pinput));
						for (String line; (line = reader.readLine()) != null;) {
							reportArea.append(line);
							reportArea.append(System.lineSeparator());
							reportArea.setCaretPosition(reportArea
									.getDocument().getLength());
						}
						reader.close();
					} catch (Exception e) {
					}
				}
			}
		}.start();

		this.setTitle("Dashboard");
		this.setSize(800, 500);
		this.setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(createSettingPanel(), BorderLayout.NORTH);
		centerPanel.add(createClassifyPanel(), BorderLayout.CENTER);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(createChartPanel(), BorderLayout.EAST);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public JPanel createSettingPanel() {
		JPanel p = new JPanel();
		JPanel spinnerBlock = new JPanel(), ratioBlock = new JPanel();
		JPanel fileBlock = new JPanel(), colsoleBlock = new JPanel(), workBlock = new JPanel();
		ngramModel = new SpinnerNumberModel(2, 1, 6, 1);
		ngramSpinner = new JSpinner(ngramModel);
		topModel = new SpinnerNumberModel(10000, 1, 100000, 1);
		topSpinner = new JSpinner(topModel);
		trainingAndtestingRatio = new JSlider(JSlider.HORIZONTAL, 1, 10, 8);
		trainingField = new JTextField("training_set");
		consoleField = new JTextField("output");
		trainingButton = new JButton("Training");
		updateButton = new JButton("Update");
		classifyButton = new JButton("Classify");

		trainingField.setEditable(false);
		trainingField.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {
				topSpinner.requestFocus();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(trainingField
						.getText()));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath = chooser.getSelectedFile()
							.getAbsolutePath();
					trainingField.setText(filepath);
				}
			}

			public void focusLost(FocusEvent arg0) {
			}

		});

		consoleField.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {
				topSpinner.requestFocus();
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File(consoleField
						.getText()));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath = chooser.getSelectedFile()
							.getAbsolutePath();
					trainingField.setText(filepath);
				}
			}

			public void focusLost(FocusEvent arg0) {
			}

		});

		trainingButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final int Ngram = (Integer) ngramSpinner.getValue();
				final int topNgram = (Integer) topSpinner.getValue();
				final String trainingPath = trainingField.getText();
				final String testPath = testField == null ? "user_test"
						: testField.getText();
				new Thread("NLP-SC-Trainer") {
					public void run() {
						try {
							reportArea.setText("");
							trainingButton.setText("Working...");
							trainingButton.setEnabled(false);
							Main.work(Ngram, topNgram, trainingPath, testPath);
							classifyButton.setEnabled(true);
							updateButton.setEnabled(true);
							System.out.println("## Ready for testing ##");
							System.out.println();
						} catch (Exception ex) {
						} finally {
							trainingButton.setText("Training");
							trainingButton.setEnabled(true);
						}
					}
				}.start();
			}

		});

		classifyButton.setEnabled(false);
		classifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String test[] = classifyArea.getText().toLowerCase()
						.split("\r?\n");
				int Ngram = (Integer) ngramSpinner.getValue();
				Classifier classifier = Main.PAmachines[0];
				classifyArea.setText("");
				for (String t : test) {
					if (t.matches(".+ \\[online-(pos|neg)\\]$"))
						t = t.substring(0, t.length() - 13);
					if (t.matches(".+ \\[(pos|neg)\\]$"))
						t = t.substring(0, t.length() - 6);
					System.out.println("'" + t + "'");
					classifyArea.append(t);
					if (!t.isEmpty()) {
						TreeMap<Integer, Double> x = ModelUtilities
								.getCharacteristicWeightVector(t, Ngram,
										Main.mixPickPosMap, null, null);
						boolean pos = classifier.classify(x);
						System.out.print("classify :");
						System.out.println(pos ? " [pos]" : " [neg]");
						classifyArea.append(pos ? " [pos]" : " [neg]");

						ArrayList<Article> testArticles = new ArrayList<Article>();
						ReturnCell<PassiveAggressive> PA = new ReturnCell<PassiveAggressive>(
								null);
						testArticles.add(new Article(t, 0));
						Main.preprocessInput(Ngram, testArticles,
								new ArrayList<Article>(), Main.mixPick,
								Main.posPickSet, Main.negPickSet,
								Main.mixPickSet, Main.mixPickPosMap);
						Main.onlineClassify(Main.posTrainArticles,
								Main.negTrainArticles, testArticles,
								new ArrayList<Article>(), PA);
						boolean online_pos = PA.get().classify(x);
						System.out.print("online-classify :");
						System.out.println(online_pos ? " [online-pos]" : " [online-neg]");
						classifyArea.append(online_pos ? " [online-pos]" : " [online-neg]");
					}
					classifyArea.append("\n");
				}
			}
		});

		updateButton.setEnabled(false);
		updateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String update[] = classifyArea.getText().toLowerCase()
						.split("\r?\n");
				List<String> posTrain = new LinkedList<>();
				List<String> negTrain = new LinkedList<>();
				for (String line : update) {
					if (line.matches(".+ \\[(pos|neg)\\]$")) {
						String tag = line.substring(line.length() - 4,
								line.length() - 1);
						line = line.substring(0, line.length() - 6);
						if (tag.equals("pos"))
							posTrain.add(line);
						else
							negTrain.add(line);
					}
				}
			}

		});

		trainingAndtestingRatio.setMajorTickSpacing(2);
		trainingAndtestingRatio.setMinorTickSpacing(1);
		trainingAndtestingRatio.setPaintLabels(true);
		trainingAndtestingRatio.setPaintTicks(true);
		trainingAndtestingRatio.setSnapToTicks(true);

		spinnerBlock.setLayout(new GridLayout(1, 4, 5, 0));
		spinnerBlock.add(new JLabel(" N-gram "));
		spinnerBlock.add(ngramSpinner);
		spinnerBlock.add(new JLabel(" Top-features "));
		spinnerBlock.add(topSpinner);

		ratioBlock.setLayout(new GridLayout(1, 2, 5, 0));
		ratioBlock.add(new JLabel(" Training : Testing "));
		ratioBlock.add(trainingAndtestingRatio);

		fileBlock.setLayout(new GridLayout(1, 2, 5, 0));
		fileBlock.add(new JLabel(" Training source file"));
		fileBlock.add(trainingField);

		colsoleBlock.setLayout(new GridLayout(1, 2, 5, 0));
		colsoleBlock.add(new JLabel(" Console log file"));
		colsoleBlock.add(consoleField);

		workBlock.setLayout(new GridLayout(1, 4, 5, 0));

		workBlock.add(new JLabel());
		workBlock.add(classifyButton);
		workBlock.add(updateButton);
		workBlock.add(trainingButton);

		p.setLayout(new GridLayout(5, 1, 5, 0));
		p.setBorder(BorderFactory.createTitledBorder("setting"));
		p.setPreferredSize(new Dimension(400, 250));
		p.add(spinnerBlock);
		p.add(ratioBlock);
		p.add(fileBlock);
		p.add(colsoleBlock);
		p.add(workBlock);
		return p;
	}

	public JPanel createChartPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder("report"));
		reportArea = new JTextArea();
		reportArea.setBackground(new Color(0, 0, 0, 0));
		reportArea.setBorder(null);
		reportArea.setFont(new Font("Consolas", Font.PLAIN, 12));
		reportArea.setEditable(false);
		JScrollPane scroll = new JScrollPane(reportArea);
		scroll.setBackground(new Color(0, 0, 0, 0));
		scroll.setBorder(null);
		scroll.setPreferredSize(new Dimension(400, 600));
		p.add(scroll, BorderLayout.CENTER);
		return p;
	}

	public JPanel createClassifyPanel() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder("classify"));
		classifyArea = new JTextArea();
		classifyArea.setFont(new Font("Consolas", Font.PLAIN, 12));
		classifyArea.setBackground(new Color(0, 0, 0, 0));
		classifyArea.setBorder(null);
		classifyArea.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
				classifyArea.setSelectionStart(0);
				classifyArea.setSelectionEnd(classifyArea.getDocument()
						.getLength());
			}

			public void focusLost(FocusEvent arg0) {
			}
		});
		JScrollPane scroll = new JScrollPane(classifyArea);
		scroll.setBackground(new Color(0, 0, 0, 0));
		scroll.setBorder(null);
		p.add(scroll, BorderLayout.CENTER);

		return p;
	}
}

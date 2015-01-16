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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import comp.ReturnCell;
import main.Main;
import main.TrainingConfig;
import model.Article;
import model.ModelUtilities;
import model.classifier.Classifier;
import model.classifier.PassiveAggressive;

public class Dashboard extends JFrame {
	SpinnerModel ngramModel, topModel, ITmodel, crossModel, cpartModel,
			oITmodel;
	JSpinner ngramSpinner, topSpinner, ITSpinner, crossSpinner, cpartSpinner,
			oITSpinner;
	JTextField trainingField, consoleField;
	JButton trainingButton, updateButton, classifyButton, enterButton;
	JTextArea reportArea, classifyArea, inputArea, classifyResultArea;
	ClockTextLabel clockText;

	public Dashboard() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			UIManager.getLookAndFeelDefaults().put("defaultFont",
					new Font("Comic Sans MS", Font.PLAIN, 18));
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
		this.setSize(1000, 700);
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
		JPanel spinnerBlock = new JPanel();
		JPanel fileBlock = new JPanel(), colsoleBlock = new JPanel(), workBlock = new JPanel();
		ngramModel = new SpinnerNumberModel(3, 1, 6, 1);
		ngramSpinner = new JSpinner(ngramModel);
		ITmodel = new SpinnerNumberModel(20, 1, 100, 1);
		ITSpinner = new JSpinner(ITmodel);
		oITmodel = new SpinnerNumberModel(20, 1, 100, 1);
		oITSpinner = new JSpinner(oITmodel);
		crossModel = new SpinnerNumberModel(10, 1, 100, 1);
		crossSpinner = new JSpinner(crossModel);
		cpartModel = new SpinnerNumberModel(5, 1, 100, 1);
		cpartSpinner = new JSpinner(cpartModel);
		topModel = new SpinnerNumberModel(40000, 1, 100000, 1);
		topSpinner = new JSpinner(topModel);
		trainingField = new JTextField("training_set");
		consoleField = new JTextField("user_test");
		trainingButton = new JButton("Training");
		updateButton = new JButton("Online-Classify");
		classifyButton = new JButton("Offline-Classify");
		clockText = new ClockTextLabel();

		clockText.setFont(new Font("Comic Sans MS", Font.PLAIN, 32));
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
				final String testPath = consoleField.getText();
				final int ITLIMIT = (Integer) ITSpinner.getValue();
				final int OITLIMIT = (Integer) oITSpinner.getValue();
				final int cross = (Integer) crossSpinner.getValue();
				final int crosspart = (Integer) cpartSpinner.getValue();

				String[] args = { "-n", String.valueOf(Ngram), "-top",
						String.valueOf(topNgram), "-path", trainingPath,
						"-tpath", testPath, "-ittime", String.valueOf(ITLIMIT),
						"-oittime", String.valueOf(OITLIMIT), "-crosspart",
						String.valueOf(crosspart), "-cross",
						String.valueOf(cross) };

				TrainingConfig config = new TrainingConfig(args);
				Main.workConfig = config;

				new Thread("NLP-SC-Trainer") {
					public void run() {
						try {
							reportArea.setText("");
							trainingButton.setText("Working...");
							trainingButton.setEnabled(false);
							classifyButton.setEnabled(false);
							updateButton.setEnabled(false);
							clockText.start();
							Main.work(Main.workConfig);
							clockText.stop();
							classifyButton.setEnabled(true);
							updateButton.setEnabled(true);
							trainingButton.setEnabled(true);
							System.out.println("\n## Ready for testing ##");
							System.out.println();
						} catch (Exception ex) {
							ex.printStackTrace();
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
				Classifier classifier = Main.workConfig.PAmachines[0];
				classifyArea.setText("");
				classifyResultArea.setText("");
				for (String t : test) {
					System.out.println("'" + t + "'");
					classifyArea.append(t + "\n");
					if (!t.isEmpty()) {
						TreeMap<Integer, Double> x = ModelUtilities
								.getCharacteristicWeightVector(t, Ngram,
										Main.workConfig.mixPickPosMap, null,
										null);
						boolean pos = classifier.classify(x);
						System.out.print("classify :");
						System.out.println(pos ? " [pos]" : " [neg]");
						classifyResultArea
								.append(pos ? " [pos]\n" : " [neg]\n");
					}
				}
			}
		});

		updateButton.setEnabled(false);
		updateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new Thread("update-online") {
					public void run() {
						classifyButton.setEnabled(false);
						updateButton.setEnabled(false);
						updateButton.setText("Working...");
						trainingButton.setEnabled(false);

						String test[] = classifyArea.getText().toLowerCase()
								.split("\r?\n");
						int Ngram = (Integer) ngramSpinner.getValue();
						classifyArea.setText("");
						classifyResultArea.setText("");
						ArrayList<Article> testArticles = new ArrayList<Article>();
						for (String t : test) {
							if (!t.isEmpty()) {
								testArticles.add(new Article(t, 0));
							}
						}
						ReturnCell<PassiveAggressive> PA = new ReturnCell<PassiveAggressive>(
								null);
						Main.preprocessInput(Ngram, testArticles,
								new ArrayList<Article>(),
								Main.workConfig.mixPick,
								Main.workConfig.posPickSet,
								Main.workConfig.negPickSet,
								Main.workConfig.mixPickSet,
								Main.workConfig.mixPickPosMap);
						Main.onlineClassify(Main.workConfig,
								Main.workConfig.posTrainArticles,
								Main.workConfig.negTrainArticles, testArticles,
								new ArrayList<Article>(), PA);
						for (Article t : testArticles) {
							System.out.println("'" + t.content + "'");
							boolean online_pos = PA.get().classify(t.vec);
							System.out.print("online-classify :");
							System.out.println(online_pos ? " [online-pos]"
									: " [online-neg]");
							classifyResultArea.append(online_pos ? " [pos]\n"
									: " [neg]\n");
							classifyArea.append(t.content + "\n");
						}
						classifyButton.setEnabled(true);
						updateButton.setEnabled(true);
						updateButton.setText("Online-Classfiy");
						trainingButton.setEnabled(true);
					}
				}.start();
			}

		});

		spinnerBlock.setLayout(new GridLayout(3, 4, 5, 0));
		spinnerBlock.add(new JLabel(" N-grams "));
		spinnerBlock.add(ngramSpinner);
		spinnerBlock.add(new JLabel(" K-Top-features "));
		spinnerBlock.add(topSpinner);
		spinnerBlock.add(new JLabel(" #CROSS "));
		spinnerBlock.add(crossSpinner);
		spinnerBlock.add(new JLabel(" #CROSS-PART "));
		spinnerBlock.add(cpartSpinner);
		spinnerBlock.add(new JLabel(" #ITERATOR "));
		spinnerBlock.add(ITSpinner);
		spinnerBlock.add(new JLabel(" #ONLINE-ITERATOR "));
		spinnerBlock.add(oITSpinner);

		fileBlock.setLayout(new GridLayout(1, 2, 5, 0));
		fileBlock.add(new JLabel(" Training source file"));
		fileBlock.add(trainingField);

		colsoleBlock.setLayout(new GridLayout(1, 2, 5, 0));
		colsoleBlock.add(new JLabel(" Test source file"));
		colsoleBlock.add(consoleField);

		workBlock.setLayout(new GridLayout(1, 4, 5, 0));

		workBlock.add(clockText);
		workBlock.add(classifyButton);
		workBlock.add(updateButton);
		workBlock.add(trainingButton);

		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(BorderFactory.createTitledBorder("setting"));
		p.setPreferredSize(new Dimension(400, 300));
		p.add(spinnerBlock);
		// p.add(ratioBlock);
		p.add(new JSeparator(SwingConstants.HORIZONTAL));
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
		reportArea.setFont(new Font("Consolas", Font.PLAIN, 18));
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
		JPanel blockPanel = new JPanel(new BorderLayout());
		JPanel showPanel = new JPanel(new BorderLayout());

		p.setBorder(BorderFactory
				.createTitledBorder("classify (each of data to be classified is contained on a single line of input)"));
		classifyArea = new JTextArea();
		classifyResultArea = new JTextArea();
		inputArea = new JTextArea();
		enterButton = new JButton("Enter");
		inputArea.setFont(new Font("Consolas", Font.PLAIN, 18));
		classifyResultArea.setFont(new Font("Consolas", Font.PLAIN, 18));
		classifyResultArea.setBackground(new Color(0, 0, 0, 0));
		classifyResultArea.setBorder(null);
		classifyArea.setFont(new Font("Consolas", Font.PLAIN, 18));
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
		enterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = inputArea.getText();
				s = s.replace("\n", " ");
				classifyArea.append(" " + s + "\n");
				classifyResultArea.append(" [unknown]\n");
				inputArea.setText("");
			}

		});
		inputArea.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0
						&& (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					String s = inputArea.getText();
					s = s.replace("\n", " ");
					classifyArea.append(" " + s + "\n");
					classifyResultArea.append(" [unknown]\n");
					inputArea.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});
		JScrollPane upscroll = new JScrollPane(classifyArea);
		JScrollPane leftscroll = new JScrollPane(classifyResultArea);
		JScrollPane downscroll = new JScrollPane(inputArea);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftscroll, upscroll);
		splitPane.setDividerLocation(120);
		Dimension minSize = new Dimension(50, 50);
		upscroll.setMinimumSize(minSize);
		leftscroll.setMinimumSize(minSize);

		upscroll.setBackground(new Color(0, 0, 0, 0));

		showPanel.add(splitPane, BorderLayout.CENTER);
		blockPanel.add(downscroll, BorderLayout.CENTER);
		blockPanel.add(enterButton, BorderLayout.EAST);
		blockPanel.setBorder(BorderFactory
				.createTitledBorder("type block data to line (CTRL + ENTER)"));
		p.add(showPanel, BorderLayout.CENTER);
		p.add(blockPanel, BorderLayout.SOUTH);
		return p;
	}
}

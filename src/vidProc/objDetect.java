package vidProc;


import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import org.opencv.core.Core;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import java.awt.Cursor;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class objDetect extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public static JPanel contentPane;
	
	
	private JTextField templ_text;
	private JTextField file_text;
	 private JFileChooser fileChooser;
	 JRadioButton writeAText;
	 JRadioButton fromFolder1;
	 JRadioButton fromFolder2;
	 JRadioButton fromWebcam;
	 static JButton submit;
	 JButton browse2;
	 JButton browse1;
	 vidProc proc;
	    public static final int MODE_OPEN = 1;
	    public static final int MODE_SAVE = 2;
	   
	   public static JFrame out_frm;
	    private JCheckBox cus_set;
	    private JLabel n_angles;
	    JSpinner n_angles_spinner;
	    private JLabel StartScaleSize_lbl;
	    private JSpinner Startscalesize_spinner;
	    private JLabel label_1;
	    private JLabel Scalestepsize_lbl;
	    private JSpinner Scalestepsize_spinner;
	    private JLabel label_2;
	    private JLabel CorrelationThreshold_lbl;
	    private JSpinner CorrelationThreshold_spinner;
	    private JLabel label_3;
	    private JLabel sampleAt_lbl;
	    private JSpinner SampleAt_spinner;
	    private JLabel lblFramesPerSecond;
	    public static JButton out_btn;
	    private JTable out_table;
	    JTextPane Warning_lbl;
	    JButton Reset_btn;
	    JCheckBox find_n;
	    JRadioButton GetCoordinates_btn;
	    JButton btnStop;
	    JSpinner sensitivity_spinner;
	    JLabel bck_label;
	 
		private JLabel lblAdditionalOptions;
		static DefaultTableModel model;
		public static JProgressBar progressBar;
		private JSeparator separator_2;
		private JSeparator separator_3;
		private JSeparator separator_1;
		private JSeparator separator_4;
		private JSeparator separator_5;
		private JSeparator separator_6;
		private JSeparator separator_7;
		private JSeparator separator_8;
		private JSeparator separator_9;
		private JSeparator separator_10;
		
		private JLabel about_lbl;
		private JLabel sensitivity_lbl;
		private JLabel lblNewLabel;
		private JLabel lblChangeWallpaper;
		private JButton Stop_btn;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		} catch (InstantiationException e1) {
		
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		   
		System.gc();
		
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					objDetect frame = new objDetect();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public objDetect() {
		
		setIgnoreRepaint(true);
		
		
		setTitle("Ivy");
		setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setIconImage(new ImageIcon(getClass().getResource("Ivy.png"),"").getImage());
		
		
		setBounds(0, 0, 1480, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.setForeground(Color.BLACK);
		//contentPane.setBorder(new EmptyBorder(100, 100, 100, 100));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contentPane.setBackground(Color.GRAY);
		
		fileChooser = new JFileChooser();
         
		browse1 = new JButton("Browse");
		browse1.setOpaque(false);
		browse1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		browse1.setAlignmentX(8.0f);
		browse1.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		browse1.setToolTipText("Browse Template Image");
		browse1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openfilefinder(e,1); 
			}
		});
		
		Stop_btn = new JButton("STOP");
		Stop_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Stop_btn.setOpaque(false);
		Stop_btn.setVisible(false);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setBounds(32, 565, 487, 2);
		contentPane.add(separator_11);
		Stop_btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		Stop_btn.setBounds(290, 592, 89, 23);
		contentPane.add(Stop_btn);
		
		
		browse1.setBounds(384, 63, 89, 23);
		contentPane.add(browse1);
		
		browse2 = new JButton("Browse");
		browse2.setToolTipText("Browse Image/Video");
		browse2.setOpaque(false);
		browse2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		browse2.setAlignmentX(8.0f);
		browse2.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		browse2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openfilefinder(e,2);
			}
		});
		browse2.setBounds(384, 187, 89, 23);
		contentPane.add(browse2);
		
		JLabel templ_label = new JLabel("Upload Template");
		templ_label.setAlignmentX(8.0f);
		templ_label.setBackground(Color.WHITE);
		templ_label.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		templ_label.setForeground(Color.WHITE);
		templ_label.setBounds(57, 22, 166, 33);
		contentPane.add(templ_label);
		
		JLabel file_label = new JLabel("Upload File");
		file_label.setAlignmentX(8.0f);
		file_label.setForeground(Color.WHITE);
		file_label.setBackground(Color.WHITE);
		file_label.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		file_label.setBounds(57, 151, 140, 18);
		contentPane.add(file_label);

		templ_text = new JTextField();
		templ_text.setDisabledTextColor(Color.GRAY);
		templ_text.setAlignmentX(8.0f);
		templ_text.setOpaque(false);
		templ_text.setCaretColor(Color.WHITE);
		templ_text.setSelectedTextColor(Color.WHITE);
		templ_text.setSelectionColor(Color.WHITE);
		templ_text.setBorder(null);
		templ_text.setBackground(Color.WHITE);
		templ_text.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		templ_text.setForeground(Color.WHITE);
		templ_text.setBounds(57, 107, 440, 20);
		contentPane.add(templ_text);
		templ_text.setColumns(10);
		
		
		file_text = new JTextField();
		file_text.setAlignmentX(8.0f);
		file_text.setBackground(Color.WHITE);
		file_text.setBorder(null);
		file_text.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		file_text.setForeground(Color.WHITE);
		file_text.setCaretColor(Color.WHITE);
		file_text.setOpaque(false);
		file_text.setBounds(57, 234, 440, 20);
		contentPane.add(file_text);
		file_text.setColumns(10);
		
		
		submit = new JButton("SUBMIT");
		submit.setBorderPainted(false);
		submit.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED, null));
		submit.setBackground(Color.RED);
		submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		submit.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		submit.setBounds(400, 591, 99, 61);
		contentPane.add(submit);
		
		
		fromFolder1 = new JRadioButton("From Folder");
		fromFolder1.setToolTipText("Upload a Template  Image from computer ");
		fromFolder1.setAlignmentX(8.0f);
		fromFolder1.setForeground(Color.WHITE);
		fromFolder1.setBackground(Color.WHITE);
		fromFolder1.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		fromFolder1.setSelected(true);
		fromFolder1.setOpaque(false);
		
		fromFolder1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				 radio_btn_sel(1);
			}
		});
		fromFolder1.setBounds(57, 62, 85, 25);
		contentPane.add(fromFolder1);
		
		dispose();
		fromFolder2 = new JRadioButton("From Folder");
		fromFolder2.setToolTipText("Upload an Image from computer ");
		fromFolder2.setAlignmentX(8.0f);
		fromFolder2.setForeground(Color.WHITE);
		fromFolder2.setBackground(Color.WHITE);
		fromFolder2.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		fromFolder2.setSelected(true);
		fromFolder2.setOpaque(false);
		fromFolder2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				radio_btn_sel(3);
			}
		});
		
		
		fromFolder2.setBounds(57, 187, 109, 23);
		contentPane.add(fromFolder2);
		
		fromWebcam = new JRadioButton("From Webcam");
		fromWebcam.setToolTipText("Use the webcam to capture real time video");
		fromWebcam.setAlignmentX(8.0f);
		fromWebcam.setForeground(Color.WHITE);
		fromWebcam.setBackground(Color.WHITE);
		fromWebcam.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		fromWebcam.setOpaque(false);
		fromWebcam.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				radio_btn_sel(4);
			}
		});
		
		fromWebcam.setBounds(168, 187, 109, 23);
		contentPane.add(fromWebcam);
		
		
		
		n_angles = new JLabel("Number of angles: ");
		n_angles.setForeground(Color.WHITE);
		n_angles.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		n_angles.setEnabled(false);
		n_angles.setBounds(80, 356, 99, 23);
		contentPane.add(n_angles);
		
		 n_angles_spinner = new JSpinner();
		 n_angles_spinner.setAlignmentX(8.0f);
		 n_angles_spinner.setBackground(Color.WHITE);
		 n_angles_spinner.setBorder(null);
		 n_angles_spinner.setForeground(Color.WHITE);
		 n_angles_spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		 n_angles_spinner.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		n_angles_spinner.setEnabled(false);
		n_angles_spinner.setBounds(206, 358, 52, 20);
		contentPane.add(n_angles_spinner);
		
		
		
		
		Warning_lbl = new JTextPane();
		Warning_lbl.setAlignmentX(8.0f);
		Warning_lbl.setBackground(Color.WHITE);
		Warning_lbl.setOpaque(false);
		Warning_lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		Warning_lbl.setEditable(false);
		Warning_lbl.setVisible(false);
		Warning_lbl.setForeground(Color.WHITE);
		Warning_lbl.setText("NOTE: The options below can affect the performance.\r\nChange these only if you know what you are doing!");
		Warning_lbl.setBounds(346, 432, 166, 79);
		contentPane.add(Warning_lbl);
		
		StartScaleSize_lbl = new JLabel("Start scale size:");
		StartScaleSize_lbl.setEnabled(false);
		StartScaleSize_lbl.setAlignmentX(8.0f);
		StartScaleSize_lbl.setBackground(Color.WHITE);
		StartScaleSize_lbl.setForeground(Color.WHITE);
		StartScaleSize_lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		StartScaleSize_lbl.setBounds(77, 441, 85, 14);
		contentPane.add(StartScaleSize_lbl);
		
		Startscalesize_spinner = new JSpinner();
		Startscalesize_spinner.setBorder(null);
		Startscalesize_spinner.setAlignmentX(8.0f);
		Startscalesize_spinner.setBackground(Color.WHITE);
		Startscalesize_spinner.setOpaque(false);
		Startscalesize_spinner.setForeground(Color.WHITE);
		Startscalesize_spinner.setEnabled(false);
		Startscalesize_spinner.setModel(new SpinnerNumberModel(50.0, 20.0, 100.0, 5.0));
		Startscalesize_spinner.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		Startscalesize_spinner.setBounds(225, 438, 52, 20);
		contentPane.add(Startscalesize_spinner);
		
		label_1 = new JLabel("%");
		label_1.setAlignmentX(8.0f);
		label_1.setBackground(Color.WHITE);
		label_1.setForeground(Color.WHITE);
		label_1.setEnabled(false);
		label_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		label_1.setBounds(290, 441, 46, 14);
		contentPane.add(label_1);
		
		Scalestepsize_lbl = new JLabel("Scale step size:");
		Scalestepsize_lbl.setAlignmentX(8.0f);
		Scalestepsize_lbl.setBackground(Color.WHITE);
		Scalestepsize_lbl.setForeground(Color.WHITE);
		Scalestepsize_lbl.setEnabled(false);
		Scalestepsize_lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		Scalestepsize_lbl.setBounds(77, 469, 91, 14);
		contentPane.add(Scalestepsize_lbl);
		
		Scalestepsize_spinner = new JSpinner();
		Scalestepsize_spinner.setAlignmentX(8.0f);
		Scalestepsize_spinner.setBackground(Color.WHITE);
		Scalestepsize_spinner.setOpaque(false);
		Scalestepsize_spinner.setForeground(Color.WHITE);
		Scalestepsize_spinner.setEnabled(false);
		Scalestepsize_spinner.setModel(new SpinnerNumberModel(new Double(5), new Double(5), null, new Double(5)));
		Scalestepsize_spinner.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		Scalestepsize_spinner.setBounds(225, 466, 52, 20);
		contentPane.add(Scalestepsize_spinner);
		
		label_2 = new JLabel("%");
		label_2.setAlignmentX(8.0f);
		label_2.setBackground(Color.WHITE);
		label_2.setForeground(Color.WHITE);
		label_2.setEnabled(false);
		label_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		label_2.setBounds(290, 469, 46, 14);
		contentPane.add(label_2);
		
		CorrelationThreshold_lbl = new JLabel("Correlation Threshold:");
		CorrelationThreshold_lbl.setAlignmentX(8.0f);
		CorrelationThreshold_lbl.setBackground(Color.WHITE);
		CorrelationThreshold_lbl.setForeground(Color.WHITE);
		CorrelationThreshold_lbl.setEnabled(false);
		CorrelationThreshold_lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		CorrelationThreshold_lbl.setBounds(77, 497, 130, 14);
		contentPane.add(CorrelationThreshold_lbl);
		
		CorrelationThreshold_spinner = new JSpinner();
		CorrelationThreshold_spinner.setAlignmentX(8.0f);
		CorrelationThreshold_spinner.setBackground(Color.WHITE);
		CorrelationThreshold_spinner.setOpaque(false);
		CorrelationThreshold_spinner.setForeground(Color.WHITE);
		CorrelationThreshold_spinner.setEnabled(false);
		CorrelationThreshold_spinner.setModel(new SpinnerNumberModel(30.0, 20.0, 100.0, 2.0));
		CorrelationThreshold_spinner.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		CorrelationThreshold_spinner.setBounds(225, 494, 52, 20);
		contentPane.add(CorrelationThreshold_spinner);
		
		label_3 = new JLabel("%");
		label_3.setAlignmentX(8.0f);
		label_3.setBackground(Color.WHITE);
		label_3.setForeground(Color.WHITE);
		label_3.setEnabled(false);
		label_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		label_3.setBounds(290, 497, 46, 14);
		contentPane.add(label_3);
		
		sampleAt_lbl = new JLabel("Sample at every:");
		sampleAt_lbl.setAlignmentX(8.0f);
		sampleAt_lbl.setBackground(Color.WHITE);
		sampleAt_lbl.setForeground(Color.WHITE);
		sampleAt_lbl.setEnabled(false);
		sampleAt_lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		sampleAt_lbl.setBounds(77, 525, 121, 14);
		contentPane.add(sampleAt_lbl);
		
		SampleAt_spinner = new JSpinner();
		SampleAt_spinner.setAlignmentX(8.0f);
		SampleAt_spinner.setBackground(Color.WHITE);
		SampleAt_spinner.setOpaque(false);
		SampleAt_spinner.setForeground(Color.WHITE);
		SampleAt_spinner.setEnabled(false);
		SampleAt_spinner.setModel(new SpinnerNumberModel(new Integer(30), null, null, new Integer(1)));
		SampleAt_spinner.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		SampleAt_spinner.setBounds(224, 522, 53, 20);
		contentPane.add(SampleAt_spinner);
		
		lblFramesPerSecond = new JLabel("Frame");
		lblFramesPerSecond.setAlignmentX(8.0f);
		lblFramesPerSecond.setBackground(Color.WHITE);
		lblFramesPerSecond.setForeground(Color.WHITE);
		lblFramesPerSecond.setEnabled(false);
		lblFramesPerSecond.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		lblFramesPerSecond.setBounds(290, 525, 89, 14);
		contentPane.add(lblFramesPerSecond);
		
		GetCoordinates_btn = new JRadioButton("Get Coordinates");
		GetCoordinates_btn.setAlignmentX(8.0f);
		GetCoordinates_btn.setBackground(Color.WHITE);
		GetCoordinates_btn.setSelected(true);
		GetCoordinates_btn.setForeground(Color.WHITE);
		GetCoordinates_btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		GetCoordinates_btn.setOpaque(false);
		GetCoordinates_btn.setRequestFocusEnabled(false);
		GetCoordinates_btn.setBounds(316, 357, 109, 23);
		contentPane.add(GetCoordinates_btn);
		
		find_n = new JCheckBox("Find the number of matches?");
		find_n.setAlignmentX(8.0f);
		find_n.setForeground(Color.WHITE);
		find_n.setBackground(Color.WHITE);
		find_n.setOpaque(false);
		find_n.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(find_n.isSelected())
				{
				n_angles.setEnabled(true);
				n_angles_spinner.setEnabled(true);
				 GetCoordinates_btn.setEnabled(true);
				 
				}
				else
				{
					n_angles.setEnabled(false);
					n_angles_spinner.setEnabled(false);
					 GetCoordinates_btn.setEnabled(false);
				}
			}
		});
		find_n.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		find_n.setBounds(57, 315, 201, 23);
		contentPane.add(find_n);
		
		writeAText = new JRadioButton("Write a text to find");
		writeAText.setToolTipText("Write a text to find in the Image");
		writeAText.setAlignmentX(8.0f);
		writeAText.setForeground(Color.WHITE);
		writeAText.setBackground(Color.WHITE);
		writeAText.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		writeAText.setOpaque(false);
		writeAText.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				radio_btn_sel(2);
				if(writeAText.isSelected())
				{	
					
					templ_text.setBorder(BorderFactory.createLineBorder(Color.WHITE));
					find_n.setEnabled(false);
				}
				else
				{
					templ_text.setBorder(null);
					find_n.setEnabled(true);
				}
			}
		});
		writeAText.setBounds(171, 62, 140, 23);
		contentPane.add(writeAText);
		
		sensitivity_lbl = new JLabel("Sensitivity:");
		sensitivity_lbl.setAlignmentX(8.0f);
		sensitivity_lbl.setBackground(Color.WHITE);
		sensitivity_lbl.setEnabled(false);
		sensitivity_lbl.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		sensitivity_lbl.setForeground(Color.WHITE);
		sensitivity_lbl.setBounds(356, 525, 67, 14);
		contentPane.add(sensitivity_lbl);
		
		sensitivity_spinner = new JSpinner();
		sensitivity_spinner.setAlignmentX(8.0f);
		sensitivity_spinner.setBackground(Color.WHITE);
		sensitivity_spinner.setForeground(Color.WHITE);
		sensitivity_spinner.setEnabled(false);
		sensitivity_spinner.setModel(new SpinnerNumberModel(new Double(17), null, null, new Double(1)));
		sensitivity_spinner.setBounds(423, 523, 46, 20);
		contentPane.add(sensitivity_spinner);
		
		cus_set = new JCheckBox("Custom settings");
		cus_set.setAlignmentX(8.0f);
		cus_set.setForeground(Color.WHITE);
		cus_set.setBackground(Color.WHITE);
		cus_set.setOpaque(false);
		cus_set.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		cus_set.setBounds(57, 404, 140, 23);
		contentPane.add(cus_set);
		cus_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cus_set.isSelected())
				{
				Warning_lbl.setVisible(true);
				StartScaleSize_lbl.setEnabled(true);
				Startscalesize_spinner.setEnabled(true);
				label_1.setEnabled(true);
				Scalestepsize_lbl.setEnabled(true);
				Scalestepsize_spinner.setEnabled(true);
				label_2.setEnabled(true);
				CorrelationThreshold_lbl.setEnabled(true);
				CorrelationThreshold_spinner.setEnabled(true);
				label_3.setEnabled(true);
				sampleAt_lbl.setEnabled(true);
				SampleAt_spinner.setEnabled(true);
				lblFramesPerSecond.setEnabled(true);
				Reset_btn.setEnabled(true);
				sensitivity_lbl.setEnabled(true);
				sensitivity_spinner.setEnabled(true);
				}
				
				else
				{
					sensitivity_lbl.setEnabled(false);
					Warning_lbl.setVisible(false);
					StartScaleSize_lbl.setEnabled(false);
					Startscalesize_spinner.setEnabled(false);
					label_1.setEnabled(false);
					Scalestepsize_lbl.setEnabled(false);
					Scalestepsize_spinner.setEnabled(false);
					label_2.setEnabled(false);
					CorrelationThreshold_lbl.setEnabled(false);
					CorrelationThreshold_spinner.setEnabled(false);
					label_3.setEnabled(false);
					sampleAt_lbl.setEnabled(false);
					SampleAt_spinner.setEnabled(false);
					lblFramesPerSecond.setEnabled(false);
					Reset_btn.setEnabled(false);
					sensitivity_spinner.setEnabled(false);

					
				}
					
			}
		});
		
		Reset_btn = new JButton("Reset");
		Reset_btn.setOpaque(false);
		Reset_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Reset_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Startscalesize_spinner.setValue((double)50);
				Scalestepsize_spinner.setValue((double)5);
				CorrelationThreshold_spinner.setValue((double)30);
				SampleAt_spinner.setValue((int)30);
				sensitivity_spinner.setValue((double)17);
			}
		});
		
		Reset_btn.setEnabled(false);
		Reset_btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		Reset_btn.setBounds(185, 592, 89, 23);
		contentPane.add(Reset_btn);
		
		out_btn = new JButton("Output image");
		out_btn.setOpaque(false);
		out_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		out_btn.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		out_btn.setEnabled(false);
		
		out_btn.setBounds(57, 592, 109, 23);
		contentPane.add(out_btn);
		
		out_table = new JTable();
		out_table.setVisible(false);
		out_table.setOpaque(false);
		out_table.setGridColor(Color.WHITE);
		out_table.setBackground(new Color(0, 0, 51));
		out_table.setBorder(null);
		out_table.setRequestFocusEnabled(false);
		out_table.setRowSelectionAllowed(false);
		out_table.setAlignmentY(Component.TOP_ALIGNMENT);
		out_table.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		out_table.setSelectionBackground(Color.WHITE);
		out_table.setForeground(Color.WHITE);
		out_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		out_table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Sl. No", "Status", "Number of matches", "Time", "       (X,Y)"},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		out_table.getColumnModel().getColumn(0).setPreferredWidth(32);
		out_table.getColumnModel().getColumn(2).setPreferredWidth(96);
		out_table.setBounds(576, 32, 763, 599);
		contentPane.add(out_table);
		model = (DefaultTableModel) out_table.getModel();
		
		
		JSeparator separator = new JSeparator();
		separator.setAlignmentX(8.0f);
		separator.setBackground(Color.WHITE);
		separator.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		separator.setForeground(Color.WHITE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(517, 36, 2, 635);
		contentPane.add(separator);
		
		lblAdditionalOptions = new JLabel("Additional options");
		lblAdditionalOptions.setAlignmentX(8.0f);
		lblAdditionalOptions.setForeground(Color.WHITE);
		lblAdditionalOptions.setBackground(Color.WHITE);
		lblAdditionalOptions.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		lblAdditionalOptions.setBounds(66, 275, 131, 33);
		contentPane.add(lblAdditionalOptions);
		
		progressBar = new JProgressBar();
		progressBar.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		progressBar.setForeground(Color.BLACK);
		progressBar.setOpaque(true);
		progressBar.setBackground(Color.WHITE);
		progressBar.setAlignmentX(8.0f);
		progressBar.setStringPainted(true);
		progressBar.setBounds(57, 638, 322, 14);
		contentPane.add(progressBar);
		
		separator_2 = new JSeparator();
		separator_2.setAlignmentX(8.0f);
		separator_2.setBackground(Color.WHITE);
		separator_2.setOpaque(true);
		separator_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		separator_2.setForeground(Color.WHITE);
		separator_2.setBounds(188, 293, 331, 2);
		contentPane.add(separator_2);
		
		separator_3 = new JSeparator();
		separator_3.setAlignmentX(8.0f);
		separator_3.setBackground(Color.WHITE);
		separator_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		separator_3.setForeground(Color.WHITE);
		separator_3.setBounds(178, 419, 341, 2);
		contentPane.add(separator_3);
		
		separator_1 = new JSeparator();
		separator_1.setAlignmentX(8.0f);
		separator_1.setBackground(Color.WHITE);
		separator_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(179, 36, 341, 2);
		contentPane.add(separator_1);
		
		separator_4 = new JSeparator();
		separator_4.setAlignmentX(8.0f);
		separator_4.setBackground(Color.WHITE);
		separator_4.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		separator_4.setForeground(Color.WHITE);
		separator_4.setOrientation(SwingConstants.VERTICAL);
		separator_4.setBounds(31, 35, 2, 636);
		contentPane.add(separator_4);
		
		separator_5 = new JSeparator();
		separator_5.setAlignmentX(8.0f);
		separator_5.setBackground(Color.WHITE);
		separator_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		separator_5.setForeground(Color.WHITE);
		separator_5.setBounds(31, 669, 487, 2);
		contentPane.add(separator_5);
		
		separator_6 = new JSeparator();
		separator_6.setAlignmentX(8.0f);
		separator_6.setForeground(Color.WHITE);
		separator_6.setBounds(32, 293, 31, 2);
		contentPane.add(separator_6);
		
		separator_7 = new JSeparator();
		separator_7.setAlignmentX(8.0f);
		separator_7.setBackground(Color.WHITE);
		separator_7.setForeground(Color.WHITE);
		separator_7.setBounds(137, 161, 382, 3);
		contentPane.add(separator_7);
		
		separator_8 = new JSeparator();
		separator_8.setAlignmentX(8.0f);
		separator_8.setBackground(Color.WHITE);
		separator_8.setForeground(Color.WHITE);
		separator_8.setBounds(31, 160, 21, 3);
		contentPane.add(separator_8);
		
		separator_9 = new JSeparator();
		separator_9.setAlignmentX(8.0f);
		separator_9.setForeground(Color.WHITE);
		separator_9.setBackground(Color.WHITE);
		separator_9.setBounds(31, 35, 21, 6);
		contentPane.add(separator_9);
		
		separator_10 = new JSeparator();
		separator_10.setAlignmentX(8.0f);
		separator_10.setForeground(Color.WHITE);
		separator_10.setBounds(31, 419, 21, 3);
		contentPane.add(separator_10);
		
		about_lbl = new JLabel("About us");
		about_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFrame abt_us_frm=new JFrame();
				String abt_us_str="This Software was developed by: \n1)Vishwaas N: vishwaas.universe@gmail.com\n"
						+"2)Shwethark Raghuvanshi: shwetarkraghuvanshi@gmail.com\n"
						+ "3)Tausif Ahmed: excaliber2099@gmail.com\n"
						+ "4)Krishna Koushik: krishna17_koushik@yahoo.com"
						+ "\n\nContact us at:\n"
						+ "+919632761048"
						+ "\n\n\t\t     FOR NON COMMERCIAL USE ONLY"
						+ "\n\n\t\t\t THANK YOU !!";
			JTextArea abt_us_txt=new JTextArea(abt_us_str); 
			abt_us_txt.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
			abt_us_txt.setBackground(Color.DARK_GRAY);
			abt_us_txt.setForeground(Color.white);
			abt_us_txt.setEditable(false);
				abt_us_frm.getContentPane().add(abt_us_txt);
				abt_us_frm.setVisible(true);
				abt_us_frm.pack();
				abt_us_frm.setSize(1000, 500);
				
			}
		});
		
		about_lbl.setBackground(Color.WHITE);
		about_lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		about_lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		about_lbl.setForeground(Color.WHITE);
		about_lbl.setBounds(1270, 648, 67, 23);
		contentPane.add(about_lbl);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 0, 51));
		lblNewLabel.setBounds(20, 22, 510, 661);
		contentPane.add(lblNewLabel);
		
		lblChangeWallpaper = new JLabel("Change wallpaper");
		lblChangeWallpaper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblChangeWallpaper.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				if(fileChooser.showOpenDialog(lblChangeWallpaper) == JFileChooser.APPROVE_OPTION)
				{
					ImageIcon I=	new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
					bck_label.setIcon(I);
					bck_label.setBounds(700-I.getIconWidth()/2,360-I.getIconHeight()/2,I.getIconWidth(),I.getIconHeight());
					bck_label.setVisible(true);
					
					
					
				}
				
			}
		});
		lblChangeWallpaper.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		lblChangeWallpaper.setForeground(Color.WHITE);
		lblChangeWallpaper.setBounds(1139, 645, 121, 29);
		contentPane.add(lblChangeWallpaper);
		
	bck_label = new JLabel("");
	
		
		bck_label.setBounds(0, 0, 1480, 720);
		//bck_label.setIcon(new ImageIcon("D:\\Vishwaas\\Workspace\\galaxy1.jpeg"));
		contentPane.add(bck_label);
		bck_label.setVisible(false);
		
		
		
	
		System.gc();
	
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setVisible(true);
				build_run();
			
				System.gc();
			}
			 
		}
		);
		
	}
	
	private void openfilefinder(ActionEvent evt, int a) {
		if(a==1)
		{
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                templ_text.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
		}
		else
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                file_text.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
			
    }
	void radio_btn_sel(int a)
	{
		switch(a)
		{
		case 1:	if(fromFolder1.isSelected())
				{
				writeAText.setSelected(false);
				browse1.setEnabled(true);
				}
		break;
		case 2: if(writeAText.isSelected())
				{
					fromFolder1.setSelected(false);
					browse1.setEnabled(false);
				}
		break;
		case 3:	if(fromFolder2.isSelected())
				{
					fromWebcam.setSelected(false);
					file_text.setEnabled(true);
					browse2.setEnabled(true);
				}
		break;
		case 4: if(fromWebcam.isSelected())
				{
				fromFolder2.setSelected(false);
				file_text.setEnabled(false);
				browse2.setEnabled(false);
				}
		break;	
		
	
		}
	}
 
	public void build_run()
	{
		Stop_btn.setVisible(false);

		 boolean is_ok=true;
		objDetect.model.setRowCount(1);
		model.setRowCount(1);
		
		
		String templ=templ_text.getText();
		out_btn.setEnabled(false);
		
		templ=templ.replace('\\', '/');
		
		String img=file_text.getText();
		
		img=img.replace('\\', '/');
		
		
		String format= img.substring(img.indexOf('.')+1, img.length());
		
		proc=new vidProc(img,templ);
		
		proc.stop=false;
		
		if(!fromWebcam.isSelected())
		{
		if(format.equals("jpg")||format.equals("jpeg")||format.equals("png")||format.equals("tif"))
			proc.is_vid=false;
		else if(format.equals("mpg")||format.equals("mpeg")||format.equals("mp4"))
			proc.is_vid=true;
		else
		{
			JOptionPane.showMessageDialog(null,"Not a valid file format \n\r only jpg, jpeg, png, tif, mpg, mpeg, mp4 are supported !!");

			 is_ok = false;
		}
		}
		
		if(find_n.isSelected())
		proc.find_n=true;
		else
		proc.find_n=false;
		
		proc.n_angles=(int)n_angles_spinner.getValue();
		
		proc.strt_scl=(double)((double)Startscalesize_spinner.getValue()/100);
		
		
		proc.scl_step_sze=(double)((double)Scalestepsize_spinner.getValue()/100);
		
		proc.corr_thr=(double)((double)CorrelationThreshold_spinner.getValue()/100);
		
		proc.smpl_at= (int)SampleAt_spinner.getValue();
		
		proc.param=(double)((double)(sensitivity_spinner.getValue())/10);
		
	
		if(fromFolder1.isSelected())
		{
			proc.text_templ=false;
		}
		if(writeAText.isSelected())
		{
			
			proc.find_n=false;
			
			proc.text_templ=true;
			proc.text=templ_text.getText();
		}
		
		if(fromFolder2.isSelected())
		{
			proc.real_time=false;
		}
		if(fromWebcam.isSelected())
		{
			Stop_btn.setVisible(true);
			proc.is_vid=true;
			proc.real_time=true;
			
		}
		
			if(proc.is_vid)
			{
				Stop_btn.setVisible(true);
			}
				
		
		if(GetCoordinates_btn.isSelected())
		{
			proc.get_coordinates=true;
		}
		else
			proc.get_coordinates=false;
		
		

		
		if(is_ok)
		proc.start();
		
		
		
		out_table.setVisible(true);
		
		out_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(out_frm!=null)
				out_frm.setVisible(true);
			
			}
			
		});
		
		Stop_btn.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				proc.stop=true;
			
			}});
		
	
	
	}
}
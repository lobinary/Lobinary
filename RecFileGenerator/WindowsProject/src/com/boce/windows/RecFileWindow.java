package com.boce.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.boce.model.RecFileModel;
import com.boce.service.IRecFileService;
import com.boce.util.RecFileUtil;

public class RecFileWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3729132413918126960L;

	/**
	 * 主窗口
	 */
	private JFrame mainFrame = new JFrame();
	/**
	 * 上方窗口(选项窗口)
	 */
	private JPanel topPanel = new JPanel();
	/**
	 * 中间窗口(数据窗口)
	 */
	private JPanel middlePanel = new JPanel();
	/**
	 * 下方窗口(日志窗口)
	 */
	private JPanel bottomPanel = new JPanel();

	/**
	 * 生成对账文件按钮
	 */
	private JButton generateFileButton = new JButton("生成对账文件");
	/**
	 * 清除所有数据按钮(恢复默认设置)
	 */
	private JButton resetButton = new JButton("重置");
	/**
	 * 输出路径展示框
	 */
	private JTextField outFolderTextFiled = new JTextField(RecFileUtil.getOutRecFileDir() + "                                 ");
	/**
	 * 输出路径选择按钮
	 */
	private JButton outFolderButton = new JButton("选择");
	/**
	 * 打开输出文件夹按钮
	 */
	private JButton openOutFolderButton = new JButton("打开目录");
	/**
	 * 输出文件夹选择器
	 */
	private JFileChooser outFileFolderChooser = new JFileChooser(RecFileUtil.getOutRecFileDir());
	/**
	 * 日志滚动条
	 */
	private JScrollPane logScrollPanel = new JScrollPane();
	/**
	 * 日志数据池
	 */
	private JTextArea logTextArea = new JTextArea();
	/**
	 * 日志右键菜单
	 */
	private JPopupMenu logMenu = new JPopupMenu();
	/**
	 * 日志右键-"清空日志"-按钮
	 */
	private JMenuItem clearLogMenu = new JMenuItem("清空日志");
	/**
	 * 日志右键-"复制"-按钮
	 */
	private JMenuItem copyLogMenu = new JMenuItem("复制选中内容");
	/**
	 * 应用图标
	 */
	Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/boce/windows/lobinary-logo.png"));
	
	// 选择生成对账文件的种类（中信B2C还是银联快捷之类的选项）
	private JLabel generateTypeLabel = new JLabel("生成格式");;
	private JComboBox generateTypeComboBox = new JComboBox();

	// 判断金额是元还是分
	private JLabel isYuanLabel = new JLabel("金额单位：");
	private JComboBox isYuanComboBox = new JComboBox();

	// // 几条数据 0~7
	// private JLabel dataNumLabel = new JLabel("数据条数：");
	// private JComboBox<String> dataNumLabelComboBox = new JComboBox<String>();

	// 输入的数据
	private JLabel payOrderNoLabelInfo1 = new JLabel("数据号(生成数据打勾√)全选/反选");
	private JLabel payOrderNoLabelInfo2 = new JLabel("流水号");
	private JLabel payOrderNoLabelInfo3 = new JLabel("日期YYYYMMDD[可不填]");
	private JLabel payOrderNoLabelInfo4 = new JLabel("金额");
	
//	payOrderNoLabelInfo2.setText("流水号");
//	payOrderNoLabelInfo3.setText("日期YYYYMMDD");
	// 标签
	private JLabel payOrderNoLabel1 = new JLabel("数据1");
	private JLabel payOrderNoLabel2 = new JLabel("数据2");
	private JLabel payOrderNoLabel3 = new JLabel("数据3");
	private JLabel payOrderNoLabel4 = new JLabel("数据4");
	private JLabel payOrderNoLabel5 = new JLabel("数据5");
	private JLabel payOrderNoLabel6 = new JLabel("数据6");
	private JLabel payOrderNoLabel7 = new JLabel("数据7");
	private JLabel refundOrderNoLabel1 = new JLabel("数据8");
	private JLabel refundOrderNoLabel2 = new JLabel("数据9");
	private JLabel refundOrderNoLabel3 = new JLabel("数据10");
	private JLabel refundOrderNoLabel4 = new JLabel("数据11");

	// 是否有效
	private JCheckBox allIsValid = new JCheckBox();
	private JCheckBox payIsValid1 = new JCheckBox();
	private JCheckBox payIsValid2 = new JCheckBox();
	private JCheckBox payIsValid3 = new JCheckBox();
	private JCheckBox payIsValid4 = new JCheckBox();
	private JCheckBox payIsValid5 = new JCheckBox();
	private JCheckBox payIsValid6 = new JCheckBox();
	private JCheckBox payIsValid7 = new JCheckBox();
	private JCheckBox refundIsValid1 = new JCheckBox();
	private JCheckBox refundIsValid2 = new JCheckBox();
	private JCheckBox refundIsValid3 = new JCheckBox();
	private JCheckBox refundIsValid4 = new JCheckBox();

	// 银行订单号
	private JTextField payOrderNoTextField1 = new JTextField();
	private JTextField payOrderNoTextField2 = new JTextField();
	private JTextField payOrderNoTextField3 = new JTextField();
	private JTextField payOrderNoTextField4 = new JTextField();
	private JTextField payOrderNoTextField5 = new JTextField();
	private JTextField payOrderNoTextField6 = new JTextField();
	private JTextField payOrderNoTextField7 = new JTextField();
	private JTextField refundOrderNoTextField1 = new JTextField();
	private JTextField refundOrderNoTextField2 = new JTextField();
	private JTextField refundOrderNoTextField3 = new JTextField();
	private JTextField refundOrderNoTextField4 = new JTextField();
	// 支付流水号
	private JTextField paySerialNoTextField1 = new JTextField();
	private JTextField paySerialNoTextField2 = new JTextField();
	private JTextField paySerialNoTextField3 = new JTextField();
	private JTextField paySerialNoTextField4 = new JTextField();
	private JTextField paySerialNoTextField5 = new JTextField();
	private JTextField paySerialNoTextField6 = new JTextField();
	private JTextField paySerialNoTextField7 = new JTextField();
	private JTextField refundSerialNoTextField1 = new JTextField();
	private JTextField refundSerialNoTextField2 = new JTextField();
	private JTextField refundSerialNoTextField3 = new JTextField();
	private JTextField refundSerialNoTextField4 = new JTextField();
	// 金额
	private JTextField payAmountTextField1 = new JTextField();
	private JTextField payAmountTextField2 = new JTextField();
	private JTextField payAmountTextField3 = new JTextField();
	private JTextField payAmountTextField4 = new JTextField();
	private JTextField payAmountTextField5 = new JTextField();
	private JTextField payAmountTextField6 = new JTextField();
	private JTextField payAmountTextField7 = new JTextField();
	private JTextField refundAmountTextField1 = new JTextField();
	private JTextField refundAmountTextField2 = new JTextField();
	private JTextField refundAmountTextField3 = new JTextField();
	private JTextField refundAmountTextField4 = new JTextField();

	public void start() {
		initialWindows();
		configWindows();
		addComponets();
		addListeners();
		mainFrame.setVisible(true);
	}

	/**
	 * 生成对账文件
	 */
	public void assembleRecFile() {
		RecFileUtil.outLog("正在装配数据......");
		String isYuan = isYuanComboBox.getSelectedItem().toString();
		List<RecFileModel> rfmList = new ArrayList<RecFileModel>();
		rfmList.add(new RecFileModel("pay", payIsValid1.isSelected(), payOrderNoTextField1.getText().toString(), paySerialNoTextField1.getText().toString(),
				RecFileUtil.generateAmount(payAmountTextField1.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("pay", payIsValid2.isSelected(), payOrderNoTextField2.getText().toString(), paySerialNoTextField2.getText().toString(),
				RecFileUtil.generateAmount(payAmountTextField2.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("pay", payIsValid3.isSelected(), payOrderNoTextField3.getText().toString(), paySerialNoTextField3.getText().toString(),
				RecFileUtil.generateAmount(payAmountTextField3.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("pay", payIsValid4.isSelected(), payOrderNoTextField4.getText().toString(), paySerialNoTextField4.getText().toString(),
				RecFileUtil.generateAmount(payAmountTextField4.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("pay", payIsValid5.isSelected(), payOrderNoTextField5.getText().toString(), paySerialNoTextField5.getText().toString(),
				RecFileUtil.generateAmount(payAmountTextField5.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("pay", payIsValid6.isSelected(), payOrderNoTextField6.getText().toString(), paySerialNoTextField6.getText().toString(),
				RecFileUtil.generateAmount(payAmountTextField6.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("pay", payIsValid7.isSelected(), payOrderNoTextField7.getText().toString(), paySerialNoTextField7.getText().toString(),
				RecFileUtil.generateAmount(payAmountTextField7.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("refund", refundIsValid1.isSelected(), refundOrderNoTextField1.getText().toString(), refundSerialNoTextField1.getText()
				.toString(), RecFileUtil.generateAmount(refundAmountTextField1.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("refund", refundIsValid2.isSelected(), refundOrderNoTextField2.getText().toString(), refundSerialNoTextField2.getText()
				.toString(), RecFileUtil.generateAmount(refundAmountTextField2.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("refund", refundIsValid3.isSelected(), refundOrderNoTextField3.getText().toString(), refundSerialNoTextField3.getText()
				.toString(), RecFileUtil.generateAmount(refundAmountTextField3.getText().toString(), isYuan)));
		rfmList.add(new RecFileModel("refund", refundIsValid4.isSelected(), refundOrderNoTextField4.getText().toString(), refundSerialNoTextField4.getText()
				.toString(), RecFileUtil.generateAmount(refundAmountTextField4.getText().toString(), isYuan)));
		IRecFileService irfs = RecFileUtil.getRecServiceByStr(generateTypeComboBox.getSelectedItem().toString());
		RecFileUtil.outLog("数据装配完毕，准备执行生成对账文件操作！");
		irfs.generateRecFile(rfmList);
	}

	// 初始化窗口参数
	public void initialWindows() {
		RecFileUtil.setTextArea(logTextArea);// 提供日志输出功能所需的TextArea
		String strs = RecFileUtil.getGenerateTypeStr();
		String[] strArray = strs.split("\\|");
		updateDataBySystemName(strArray[0]);
	}

	public void configWindows() {
		mainFrame.setIconImage(icon);
		mainFrame.setTitle("对账文件自动生成器 V2.1  ——————————技术部：吕斌——电话：18612966769——QQ：919515134————如有问题，请您联系我！");
		mainFrame.setBounds(200, 100, 1000, 600);
		mainFrame.setLayout(new BorderLayout()); // 设置布局管理器为3行3列的GridLayout,组件间水平与垂直间距为2
		middlePanel.setLayout(new GridLayout(12, 5));
		outFolderTextFiled.setEditable(false);// 日志框不可更改
		// 输出对账文件路径选择器为只选文件
		outFileFolderChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		generateTypeComboBox.setModel(new DefaultComboBoxModel(RecFileUtil.getGenerateTypeArray()));
		// dataNumLabelComboBox.setModel(new
		// DefaultComboBoxModel<String>(RecFileConfig.getDataNumArray()));
		isYuanComboBox.setModel(new DefaultComboBoxModel(RecFileUtil.getAmountUnitArray()));
		bottomPanel.setLayout(new BorderLayout());
		logScrollPanel.setViewportView(logTextArea);
		logMenu.add(clearLogMenu);
		logMenu.add(copyLogMenu);
		logTextArea.setAutoscrolls(true);
		logTextArea.setRows(18);
		logTextArea.setEditable(false);
		logTextArea.setBackground(new Color(243, 243, 243));
		logTextArea.setLineWrap(true);

	}

	/**
	 * 对组建进行装配
	 */
	public void addComponets() {
		mainFrame.add(topPanel, BorderLayout.NORTH);
		mainFrame.add(middlePanel, BorderLayout.CENTER);
		mainFrame.add(bottomPanel, BorderLayout.SOUTH);
		topPanel.add(generateTypeLabel);
		topPanel.add(generateTypeComboBox);
		// topPanel.add(dataNumLabel);
		// topPanel.add(dataNumLabelComboBox);
		topPanel.add(isYuanLabel);
		topPanel.add(isYuanComboBox);
		topPanel.add(outFolderTextFiled);
		topPanel.add(outFolderButton);
		topPanel.add(openOutFolderButton);
		topPanel.add(resetButton);
		topPanel.add(generateFileButton);
		bottomPanel.add(logScrollPanel, BorderLayout.CENTER);
		middlePanel.add(payOrderNoLabelInfo1);
		middlePanel.add(allIsValid);
		middlePanel.add(payOrderNoLabelInfo2);
		middlePanel.add(payOrderNoLabelInfo3);
		middlePanel.add(payOrderNoLabelInfo4);
		middlePanel.add(payOrderNoLabel1);
		middlePanel.add(payIsValid1);
		middlePanel.add(payOrderNoTextField1);
		middlePanel.add(paySerialNoTextField1);
		middlePanel.add(payAmountTextField1);
		middlePanel.add(payOrderNoLabel2);
		middlePanel.add(payIsValid2);
		middlePanel.add(payOrderNoTextField2);
		middlePanel.add(paySerialNoTextField2);
		middlePanel.add(payAmountTextField2);
		middlePanel.add(payOrderNoLabel3);
		middlePanel.add(payIsValid3);
		middlePanel.add(payOrderNoTextField3);
		middlePanel.add(paySerialNoTextField3);
		middlePanel.add(payAmountTextField3);
		middlePanel.add(payOrderNoLabel4);
		middlePanel.add(payIsValid4);
		middlePanel.add(payOrderNoTextField4);
		middlePanel.add(paySerialNoTextField4);
		middlePanel.add(payAmountTextField4);
		middlePanel.add(payOrderNoLabel5);
		middlePanel.add(payIsValid5);
		middlePanel.add(payOrderNoTextField5);
		middlePanel.add(paySerialNoTextField5);
		middlePanel.add(payAmountTextField5);
		middlePanel.add(payOrderNoLabel6);
		middlePanel.add(payIsValid6);
		middlePanel.add(payOrderNoTextField6);
		middlePanel.add(paySerialNoTextField6);
		middlePanel.add(payAmountTextField6);
		middlePanel.add(payOrderNoLabel7);
		middlePanel.add(payIsValid7);
		middlePanel.add(payOrderNoTextField7);
		middlePanel.add(paySerialNoTextField7);
		middlePanel.add(payAmountTextField7);
		middlePanel.add(refundOrderNoLabel1);
		middlePanel.add(refundIsValid1);
		middlePanel.add(refundOrderNoTextField1);
		middlePanel.add(refundSerialNoTextField1);
		middlePanel.add(refundAmountTextField1);
		middlePanel.add(refundOrderNoLabel2);
		middlePanel.add(refundIsValid2);
		middlePanel.add(refundOrderNoTextField2);
		middlePanel.add(refundSerialNoTextField2);
		middlePanel.add(refundAmountTextField2);
		middlePanel.add(refundOrderNoLabel3);
		middlePanel.add(refundIsValid3);
		middlePanel.add(refundOrderNoTextField3);
		middlePanel.add(refundSerialNoTextField3);
		middlePanel.add(refundAmountTextField3);
		middlePanel.add(refundOrderNoLabel4);
		middlePanel.add(refundIsValid4);
		middlePanel.add(refundOrderNoTextField4);
		middlePanel.add(refundSerialNoTextField4);
		middlePanel.add(refundAmountTextField4);
	}

	/**
	 * 对组建添加监听器
	 */
	public void addListeners() {
		
		generateTypeComboBox.addActionListener(new ActionListener() {
			
//			private JLabel payOrderNoLabelInfo2 = new JLabel("订单号");
//			private JLabel payOrderNoLabelInfo3 = new JLabel("流水号");
//			private JLabel payOrderNoLabelInfo4 = new JLabel("金额");
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectItem = generateTypeComboBox.getSelectedItem().toString();
				updateDataBySystemName(selectItem);
			}
		});

		// 全选功能
		allIsValid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean selectStatus = allIsValid.isSelected();
				payIsValid1.setSelected(selectStatus);
				payIsValid2.setSelected(selectStatus);
				payIsValid3.setSelected(selectStatus);
				payIsValid4.setSelected(selectStatus);
				payIsValid5.setSelected(selectStatus);
				payIsValid6.setSelected(selectStatus);
				payIsValid7.setSelected(selectStatus);
				refundIsValid1.setSelected(selectStatus);
				refundIsValid2.setSelected(selectStatus);
				refundIsValid3.setSelected(selectStatus);
				refundIsValid4.setSelected(selectStatus);
			}
		});

		// 关闭功能
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				outLog("对账文件生成系统安全退出！");
				System.exit(0);
			}
		});

		// 打开输出文件夹
		openOutFolderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String fileDir = RecFileUtil.getOutRecFileDir();
					String folder = fileDir.substring(0, fileDir.lastIndexOf('\\'));
					java.awt.Desktop.getDesktop().open(new java.io.File(folder));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 输出对账文件路径选择功能
		outFolderButton.addActionListener(new ActionListener() {//
					@Override
					public void actionPerformed(ActionEvent e) {
						int intRetVal = outFileFolderChooser.showDialog(mainFrame, "选择");
						if (intRetVal == JFileChooser.APPROVE_OPTION) {
							String dirStr = outFileFolderChooser.getSelectedFile().getPath();
							RecFileUtil.setOutRecFileDir(dirStr);
							outFolderTextFiled.setText(dirStr);
						}
						outFileFolderChooser.setVisible(true);
					}
				});

		// 清除日志功能
		clearLogMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logTextArea.setText("");
			}
		});
		
		copyLogMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				String logSelectStr = logTextArea.getSelectedText();
//				if(logSelectStr==null||logSelectStr.length()==0){
//					//如无选择内容,则表示全选
//					logSelectStr = logTextArea.getText();
//				}
				logTextArea.copy();
			}
		});

		// 日志右键菜单
		logTextArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					logMenu.show(logTextArea, e.getX(), e.getY());
				}
			}
		});
		
		//生成对账文件按钮功能
		generateFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmStatus = JOptionPane
						.showConfirmDialog(mainFrame, "为防止错误操作导致被写文件内容丢失，请确认输出路径\n如确认，将会创建或重写该文件！", "温馨提示", JOptionPane.OK_CANCEL_OPTION);
				if (confirmStatus == 0) {
					RecFileUtil.outLog("————————————————————————————开始生成对账文件————————————————————————————");
					assembleRecFile();
					RecFileUtil.outLog("————————————————————————————结束生成对账文件————————————————————————————");
				} else {
					RecFileUtil.outLog("生成对账任务被取消！");
				}
			}
		});
		
		//重置按钮功能
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RecFileUtil.outLog("准备清空数据！");
				logTextArea.setText("");
				allIsValid.setSelected(false);
				payIsValid1.setSelected(false);
				payIsValid2.setSelected(false);
				payIsValid3.setSelected(false);
				payIsValid4.setSelected(false);
				payIsValid5.setSelected(false);
				payIsValid6.setSelected(false);
				payIsValid7.setSelected(false);
				refundIsValid1.setSelected(false);
				refundIsValid2.setSelected(false);
				refundIsValid3.setSelected(false);
				refundIsValid4.setSelected(false);
				payOrderNoTextField1.setText("");
				payOrderNoTextField2.setText("");
				payOrderNoTextField3.setText("");
				payOrderNoTextField4.setText("");
				payOrderNoTextField5.setText("");
				payOrderNoTextField6.setText("");
				payOrderNoTextField7.setText("");
				refundOrderNoTextField1.setText("");
				refundOrderNoTextField2.setText("");
				refundOrderNoTextField3.setText("");
				refundOrderNoTextField4.setText("");
				paySerialNoTextField1.setText("");
				paySerialNoTextField2.setText("");
				paySerialNoTextField3.setText("");
				paySerialNoTextField4.setText("");
				paySerialNoTextField5.setText("");
				paySerialNoTextField6.setText("");
				paySerialNoTextField7.setText("");
				refundSerialNoTextField1.setText("");
				refundSerialNoTextField2.setText("");
				refundSerialNoTextField3.setText("");
				refundSerialNoTextField4.setText("");
				payAmountTextField1.setText("");
				payAmountTextField2.setText("");
				payAmountTextField3.setText("");
				payAmountTextField4.setText("");
				payAmountTextField5.setText("");
				payAmountTextField6.setText("");
				payAmountTextField7.setText("");
				refundAmountTextField1.setText("");
				refundAmountTextField2.setText("");
				refundAmountTextField3.setText("");
				refundAmountTextField4.setText("");
				RecFileUtil.outLog("数据清空完成！");
			}
		});

	}

	/**
	 * 在日志栏输出输出日志
	 * @param logStr
	 */
	public void outLog(String logStr) {
		System.out.println(logStr);
		StringBuffer sb = new StringBuffer();
		sb.append(logTextArea.getText());
		sb.append(logStr + "\n");
		logTextArea.setText(sb.toString());
	}
	
	public void updateDataBySystemName(String selectItem){
		outLog("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★已切换到生成" + selectItem + "对账文件模式★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		if(selectItem.equals("账务系统")){
			payOrderNoLabelInfo2.setText("流水号");
			payOrderNoLabelInfo3.setText("日期YYYYMMDD");
			payOrderNoLabel1.setText("数据1");
			payOrderNoLabel2.setText("数据2");
			payOrderNoLabel3.setText("数据3");
			payOrderNoLabel4.setText("数据4");
			payOrderNoLabel5.setText("数据5");
			payOrderNoLabel6.setText("数据6");
			payOrderNoLabel7.setText("数据7");
			refundOrderNoLabel1.setText("数据8");
			refundOrderNoLabel2.setText("数据9");
			refundOrderNoLabel3.setText("数据10");
			refundOrderNoLabel4.setText("数据11");
		}else{
			payOrderNoLabelInfo2.setText("订单号");
			payOrderNoLabelInfo3.setText("流水号");
			payOrderNoLabel1.setText("支付1");
			payOrderNoLabel2.setText("支付2");
			payOrderNoLabel3.setText("支付3");
			payOrderNoLabel4.setText("支付4");
			payOrderNoLabel5.setText("支付5");
			payOrderNoLabel6.setText("支付6");
			payOrderNoLabel7.setText("支付7");
			refundOrderNoLabel1.setText("退款8");
			refundOrderNoLabel2.setText("退款9");
			refundOrderNoLabel3.setText("退款10");
			refundOrderNoLabel4.setText("退款11");
		}
	}
}

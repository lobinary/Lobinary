package com.lobinary.实用工具.数据备份;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.apache.commons.io.FileUtils;
import org.springframework.util.DigestUtils;

import com.lobinary.java.多线程.TU;
import com.lobinary.实用工具.实用工具;
import com.lobinary.实用工具.主窗口.实用工具标签标准类;
import com.lobinary.工具类.常用工具;
import com.lobinary.工具类.date.DateUtil;

public class 数据备份工具 extends 实用工具标签标准类 {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8854133778693907627L;
	private static boolean isLoading = false;
	private JLabel 提示信息 = new JLabel("提示信息：");
	private final static JLabel 提示信息内容 = new JLabel("欢迎使用备份工具,如需备份数据,请先点击\"扫描数据\"进行 未备份数据 扫描");
	private final JButton 备份数据到外部存储按钮 = new JButton("备份数据到外部存储");
	private final JButton 从未备份迁移到备份文件夹按钮 = new JButton("从未备份迁移到备份文件夹");
	private final static 备份数据表格模板 表格模板 = new 备份数据表格模板();
	JTable 备份文件夹列表 = new JTable(表格模板);
	private JFileChooser fileFolderChooser = new JFileChooser("");

	public static long totalSize;
	public static long finishSize;
	public static boolean backuping = false;

	private static Map<String, 备份记录> 备份记录保存路径配置 = new HashMap<String, 备份记录>();

	DecimalFormat df = new DecimalFormat("######0.00");
	
	@SuppressWarnings("unchecked")
	@Override
	public void loadConfig() {
		try {
			实用工具.log("数据备份工具：准备加载数据备份工具配置信息");
			备份记录保存路径配置 = (Map<String, 备份记录>) 实用工具.getConfig("备份记录保存路径配置");
			if (备份记录保存路径配置 == null) {
				备份记录保存路径配置 = new HashMap<String, 备份记录>();
				实用工具.saveConfig("备份记录保存路径配置", 备份记录保存路径配置);
			}

			表格模板.loadConfigData();
			实用工具.log("数据备份工具：数据备份工具配置信息加载完毕");
		} catch (Exception e) {
			error("初始化数据备份工具配置信息异常");
			e.printStackTrace();
		}
	}

	@Override
	public void loadDefaultConfig() {
		实用工具.log("数据备份工具：准备加载数据备份工具默认配置信息");
		备份记录保存路径配置 = new HashMap<String, 备份记录>();
		备份数据表格模板.备份记录列表 = new ArrayList<备份记录>();
		表格模板.fireTableDataChanged();
		实用工具.log("数据备份工具：数据备份工具默认配置信息加载完毕");
	}

	@Override
	public void saveConfig() {
		实用工具.log("数据备份工具：准备保存配置信息");
		List<备份记录> 备份记录列表 = 表格模板.getData();
		for (备份记录 备份记录 : 备份记录列表) {
			备份记录保存路径配置.put(备份记录.getUnBackupFolder(), 备份记录);
		}
		实用工具.saveConfig("备份记录保存路径配置", 备份记录保存路径配置);
		
		实用工具.saveConfig("备份记录列表", 备份数据表格模板.备份记录列表);
		实用工具.log("数据备份工具：配置信息保存完毕");
	}

	/**
	 * Create the panel.
	 */
	public 数据备份工具() {
		final Container 父窗口 = this.getParent();
		setLayout(null);
		this.setSize(1280, 800);
		JButton 扫描数据按钮 = new JButton("扫描数据");
		扫描数据按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					log("========================================================================开始扫描数据====" + DateUtil.getCurrentTime()
							+ "=====================================================================");

					File[] roots = File.listRoots();
					File 未备份文件夹 = null;
					File 本地备份文件夹 = null;
					File 外部存储备份文件夹 = null;
					for (int i = 0; i < roots.length; i++) {
						File 磁盘根目录 = roots[i];
						// l("准备扫描磁盘:" + 磁盘根目录);
						String[] list = 磁盘根目录.list();
						if (list == null)
							continue;
						for (String fs : list) {
							// l("扫描到:"+list[j]);
							File ft = new File(磁盘根目录 + fs);
							if (ft.getName().equals("未备份文件夹")) {
								未备份文件夹 = ft;
								l("扫描到未备份文件夹:" + 未备份文件夹);
							}
							if (ft.getName().equals("本地备份文件夹")) {
								本地备份文件夹 = ft;
								l("扫描到本地备份文件夹:" + 本地备份文件夹);
							}
							if (ft.getName().equals("外部存储备份文件夹")) {
								外部存储备份文件夹 = ft;
								l("扫描到外部存储备份文件夹:" + 外部存储备份文件夹);
							}
						}
					}

					loging("检测扫描数据有效性中");
					if (未备份文件夹 == null) {
						error("扫描异常，未发现未备份文件夹,未发现未备份文件夹需要在磁盘根目录放置一个名称为“未备份文件夹”的文件夹");
						return;
					}
					// else if(本地备份文件夹==null){
					// error("扫描异常，未发现本地备份文件夹,本地备份文件夹需要在磁盘根目录放置一个名称为“备份文件夹”的文件夹");
					// return;
					// }else if(外部存储备份文件夹==null){
					// error("扫描异常，未发现外部存储备份文件夹,外部存储文件夹下需要放置一个名称为“外部存储备份文件夹标识.Lobxxx”的文件");
					// return;
					// }
					log("扫描数据正常");

					表格模板.清除数据(备份记录.扫描添加);
					表格模板.清除数据(备份记录.手动添加);
					
					loging("开始扫描未备份文件夹数据");
					String[] 未备份文件夹目录数组 = 未备份文件夹.list();
					
					for (String 未备份文件夹子目录路径 : 未备份文件夹目录数组) {//遍历未备份目录
						File 未备份文件夹子目录 = new File(未备份文件夹 + "/" + 未备份文件夹子目录路径);
						int fileNums = 常用工具.getFileNums(未备份文件夹子目录);//未备份子目录文件数量
						long fileSize = 常用工具.getFileSize(未备份文件夹子目录);//未备份子目录文件大小
						String formetFileSize = 常用工具.formetFileSize(fileSize);
						if (fileNums == 0 || formetFileSize.equals("0.00B")) {
							l("发现空备份目录    [" + 未备份文件夹子目录.getAbsolutePath() + "]   文件数量：" + fileNums + ",文件大小：" + formetFileSize);
							continue;
						}else{
							l("发现非空备份目录    [" + 未备份文件夹子目录.getAbsolutePath() + "]   文件数量：" + fileNums + ",文件大小：" + formetFileSize);
						}
						备份记录 b = new 备份记录();
						b.setUnBackupFolder(未备份文件夹子目录.getAbsolutePath());
						备份记录 配置备份记录 = 备份记录保存路径配置.get(b.getUnBackupFolder());
						if (配置备份记录 == null) {
							if (本地备份文件夹 == null) {
								b.setLocalFolder("未扫描到本地“备份文件夹”");
							} else {
								b.setLocalFolder(本地备份文件夹.getAbsolutePath() + "\\" + 未备份文件夹子目录路径);
							}
							if (外部存储备份文件夹 == null) {
								b.setOutStoreFolder("未扫描到“外部存储备份文件夹”");
							} else {
								b.setOutStoreFolder(外部存储备份文件夹.getAbsolutePath() + "\\" + 未备份文件夹子目录路径);
							}
						} else {
							b.setLocalFolder(配置备份记录.getLocalFolder());
							b.setOutStoreFolder(配置备份记录.getOutStoreFolder());
						}
						表格模板.addData(备份文件夹列表, b);
					}
					表格模板.refreshTotalSize();
					log("扫描数据完毕.");
					l("============================================扫描数据完毕====" + DateUtil.getCurrentTime()
							+ "========文件总大小："+常用工具.formetFileSize(totalSize)+"=============================================================");
				} catch (Exception e1) {
					e1.printStackTrace();
					error("扫描数据异常：" + e1);
				}
			}
		});
		扫描数据按钮.setBounds(0, 0, 101, 23);
		add(扫描数据按钮);
		备份数据到外部存储按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (backuping) {
					error("正在备份文件中，请勿重复点击");
					return;
				}
				backuping = true;
				new Thread() {
					@Override
					public void run() {
						super.run();
						try {
							表格模板.refreshTotalSize();
							List<备份记录> 备份数据 = 表格模板.getData();
							for (备份记录 备份记录 : 备份数据) {// 目录检查
								String 外部存储数据路径 = 备份记录.getOutStoreFolder();
								if (!外部存储数据路径.contains("\\")) {
									error("请补全所有“外部存储文件夹”后再执行备份操作");
									backuping = false;
									return;
								}
							}
							loging("备份数据到外部存储中");
							l("=============================================================备份数据到外部存储开始====" + DateUtil.getCurrentTime()
									+ "=====================================================================");
							for (备份记录 备份记录 : 备份数据) {
								String 未备份文件夹 = 备份记录.getUnBackupFolder();
								String 外部存储数据路径 = 备份记录.getOutStoreFolder();
								File fromFile = new File(未备份文件夹);
								File toFile = new File(外部存储数据路径);
								l("准备备份数据从" + 未备份文件夹 + "到" + 外部存储数据路径);
								copyFile(fromFile, toFile);
							}
							l("=============================================================备份数据到外部存储结束====" + DateUtil.getCurrentTime()
									+ "=====================================================================");
							finishSize = 0l;
							log("备份数据到外部存储成功");
						} catch (Exception e1) {
							e1.printStackTrace();
							error("备份到外部存储异常:" + e1);
						}
						backuping = false;
					}
				}.start();
			}
		});
		备份数据到外部存储按钮.setBounds(109, 0, 166, 23);

		add(备份数据到外部存储按钮);
		从未备份迁移到备份文件夹按钮.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (backuping) {
					error("正在迁移文件中，请勿重复点击");
					return;
				}
				backuping = true;
				new Thread() {
					@Override
					public void run() {
						super.run();
						try {
							表格模板.refreshTotalSize();
							loging("从未备份迁移到备份文件夹中");
							List<备份记录> 备份数据 = 表格模板.getData();
							for (备份记录 备份记录 : 备份数据) {// 目录检查
								String 外部存储数据路径 = 备份记录.getLocalFolder();
								if (!外部存储数据路径.contains("\\")) {
									error("请补全所有“本地存储文件夹”后再执行备份操作");
									backuping = false;
									return;
								}
							}
							l("=========================================================从未备份迁移到备份文件夹开始====" + DateUtil.getCurrentTime()
									+ "=====================================================================");
							for (备份记录 备份记录 : 备份数据) {
								String 未备份文件夹 = 备份记录.getUnBackupFolder();
								String 本地存储路径 = 备份记录.getLocalFolder();
								File fromFile = new File(未备份文件夹);
								File toFile = new File(本地存储路径);
								l("准备迁移数据从" + 未备份文件夹 + "到" + 本地存储路径);
								moveFile(fromFile, toFile);
							}
							l("=========================================================从未备份迁移到备份文件夹结束====" + DateUtil.getCurrentTime()
									+ "=====================================================================");
							finishSize = 0l;
							log("迁移到本地存储成功");
						} catch (Exception e1) {
							e1.printStackTrace();
							error("迁移到本地存储异常:" + e1);
						}
						backuping = false;
					}
				}.start();
			}
		});
		从未备份迁移到备份文件夹按钮.setBounds(279, 0, 195, 23);

		add(从未备份迁移到备份文件夹按钮);

		提示信息.setBounds(484, 4, 78, 15);
		add(提示信息);

		提示信息内容.setForeground(Color.BLUE);
		提示信息内容.setBounds(547, 4, 733, 15);
		add(提示信息内容);
		备份文件夹列表.getTableHeader().setReorderingAllowed(false); // 只要加上这行代码，就可以锁定列的顺序了，table为JTable的对象。

		final JMenuItem 打开选中目录 = new JMenuItem("打开选中目录"); 
		打开选中目录.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec("cmd /c start \" \" \"" + getSelectData() + "\""); 
				}  catch (Exception e1) {
					error("打开选中目录异常："+e1);
					e1.printStackTrace();
				}
			
			}
		});
		
		final JMenuItem 手动添加为备份文件夹菜单 = new JMenuItem("手动添加未备份文件夹"); 
		手动添加为备份文件夹菜单.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileFolderChooser.setCurrentDirectory(new File("c:/"));
					fileFolderChooser.changeToParentDirectory();
					fileFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int intRetVal = fileFolderChooser.showDialog(父窗口, "选择");
					if (intRetVal == JFileChooser.APPROVE_OPTION) {
						String dirStr = fileFolderChooser.getSelectedFile().getPath();
						表格模板.addData(备份文件夹列表, dirStr);
						log("修改文件夹为：" + dirStr);
					}
					fileFolderChooser.setVisible(true);
				}  catch (Exception e1) {
					error("手动添加文件夹异常："+e1);
					e1.printStackTrace();
				}
			
			}
		});
		
		final JMenuItem 删除该备份记录 = new JMenuItem("删除选中备份记录"); 
		删除该备份记录.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sr = 备份文件夹列表.getSelectedRow();
				if(sr==-1){
					error("暂无选中记录");
				}else{
					表格模板.removeDate(sr);
				}
			}
		});
		
		
		备份文件夹列表.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					JMenu 右键菜单菜单 =new JMenu("右键菜单菜单"); //表格右键 菜单
					右键菜单菜单.add(手动添加为备份文件夹菜单); 
					if(备份文件夹列表.getSelectedRow()!=-1&&备份文件夹列表.getSelectedRow()<表格模板.getData().size()){
						右键菜单菜单.add(删除该备份记录); 
						String sd = getSelectData();
						if(sd!=null&&new File(sd).isDirectory()){
							右键菜单菜单.add(打开选中目录);
						}else{
							log("sd"+sd);
						}
					}
					JPopupMenu  b=new JPopupMenu(); 
					b=右键菜单菜单.getPopupMenu();
					b.show(e.getComponent(), e.getX(), e.getY() ); 
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					try {
						// 单击鼠标左键
						if (e.getClickCount() == 2) {
							int sc = 备份文件夹列表.getSelectedColumn();
							int sr = 备份文件夹列表.getSelectedRow();
							String 当前单元格路径 = "" + 备份文件夹列表.getModel().getValueAt(sr, sc);
							if (当前单元格路径 != null && 当前单元格路径.length() != 0) {
								File 当前路径文件 = new File(当前单元格路径);
								if (!当前单元格路径.contains("\\")) {
									if(当前单元格路径.contains("未扫描")){
										fileFolderChooser.setCurrentDirectory(new File("c:/"));
										fileFolderChooser.changeToParentDirectory();
									}else{
										return;
									}
								} else {
									if (!当前路径文件.exists()) {
										当前路径文件 = new File(当前单元格路径.substring(0, 当前单元格路径.lastIndexOf("\\")));
									}
									fileFolderChooser.setCurrentDirectory(当前路径文件);
								}
								fileFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
								int intRetVal = fileFolderChooser.showDialog(父窗口, "选择");
								if (intRetVal == JFileChooser.APPROVE_OPTION) {
									String dirStr = fileFolderChooser.getSelectedFile().getPath();
									备份文件夹列表.getModel().setValueAt(dirStr, sr, sc);
									log("修改文件夹为：" + dirStr);
								}
								fileFolderChooser.setVisible(true);
							} else if (当前单元格路径 == null || 当前单元格路径.length() == 0) {
								if (sc == 0) {
									fileFolderChooser.setCurrentDirectory(new File("c:/"));
									fileFolderChooser.changeToParentDirectory();
									fileFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
									int intRetVal = fileFolderChooser.showDialog(父窗口, "选择");
									if (intRetVal == JFileChooser.APPROVE_OPTION) {
										String dirStr = fileFolderChooser.getSelectedFile().getPath();
										表格模板.addData(备份文件夹列表, dirStr);
										log("修改文件夹为：" + dirStr);
									}
									fileFolderChooser.setVisible(true);
								}
							}
						}
					} catch (Exception e1) {
						error("修改数据异常：" + e1);
						e1.printStackTrace();
					}
				}
			}

		});
		TableColumn column = null;
		for (int i = 0; i < 5; i++) {
			column = 备份文件夹列表.getColumnModel().getColumn(i);
			switch (i) {
			case 0:
			case 1:
			case 2:
				column.setPreferredWidth(400);
				break;
			case 3:
				column.setPreferredWidth(90);
				break;
			case 4:
				column.setPreferredWidth(90);
				break;
			default:
				column.setPreferredWidth(400);
				break;
			}
		}
		JScrollPane tsp = new JScrollPane(备份文件夹列表);
		tsp.setBounds(0, 33, 1262, 680);
		备份文件夹列表.setFillsViewportHeight(true);
		add(tsp);
	}

	/**
	 * 通过md5教研文件一致性
	 * @param f1
	 * @param f2
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean isSameFile(File f1,File f2) throws FileNotFoundException, IOException{
		String md5 = DigestUtils.md5DigestAsHex(new FileInputStream(f1));
		String md52 = DigestUtils.md5DigestAsHex(new FileInputStream(f2));
		return md5.equals(md52);
	}
	
	
	
	/**
	 * 更新进度信息
	 * 
	 * @param fs
	 */
	private void updateProgressInfo(String info, long fs) {
		finishSize = finishSize + fs;
		System.out.println(finishSize + ":" + totalSize);
		double pi;
		try {
			pi = ((double)(finishSize * 100)) / ((double)totalSize);
		} catch (Exception e) {
			表格模板.refreshTotalSize();
			pi = ((double)(finishSize * 100)) / ((double)totalSize);
		}
		
		log("备份完成进度:" + df.format(pi) + "% ," + info);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(200, 200, 200));
		g.drawLine(0, 26, 1280, 26);
	}

	public void log(Object log) {
		提示信息内容.setForeground(Color.BLUE);
		isLoading = false;
		提示信息内容.setText("" + log);
		实用工具.log(log);
	}

	public void l(Object log) {
		实用工具.log("" + log);
	}

	public void error(Object log) {
		isLoading = false;
		提示信息内容.setForeground(Color.RED);
		提示信息内容.setText("" + log);
		实用工具.log("[错误]:" + log);
	}

	public void loging(String log) {
		提示信息内容.setForeground(Color.BLUE);
		提示信息内容.setText(log);
		if (!isLoading) {
			isLoading = true;
			new Thread() {
				@Override
				public void run() {
					super.run();
					while (isLoading) {
						TU.s(500);
						提示信息内容.setText(提示信息内容.getText() + ".");
					}
				}

			}.start();
		}
		实用工具.log(log);
	}

	private void copyFile(File fromFile, File toFile) throws IOException {
		File flist[] = fromFile.listFiles();
		for (int i = 0; i < flist.length; i++) {
			File tf = new File(toFile.getAbsolutePath() + "/" + flist[i].getName());
			if (flist[i].isDirectory()) {
				copyFile(flist[i], tf);
			} else {
				long length = flist[i].length();
				if (!tf.exists()) {
					FileUtils.copyFile(flist[i], tf);
					updateProgressInfo("从:" + flist[i].getAbsolutePath() + "备份文件到:" + tf.getAbsolutePath() + "状态：完成", length);
				} else if (isSameFile(tf,flist[i])) {
					updateProgressInfo("从:" + flist[i].getAbsolutePath() + "备份文件到:" + tf.getAbsolutePath() + ",状态：备份文件已经存在", length);
				} else {
					tf = new File(toFile.getAbsolutePath() + "/" + flist[i].getName().replace(".", DateUtil.DateToString(new Date(), "_yyyy年MM月dd日HHmmss_SSS") + "."));
					FileUtils.copyFile(flist[i], tf);
					updateProgressInfo("从:" + flist[i].getAbsolutePath() + "备份文件到:" + tf.getAbsolutePath() + "状态：存在同名不同数据文件", length);
				}

			}
		}
	}

	private void moveFile(File fromFile, File toFile) throws Exception {
		String 源文件路径 = "null";
		String 目的文件路径 = "null";
		try {
			File flist[] = fromFile.listFiles();
			for (File 源文件 : flist) {
				源文件路径 = 源文件.getAbsolutePath();
				File 目的文件 = new File(toFile.getAbsolutePath() + "/" + 源文件.getName());
				目的文件路径 = 目的文件.getAbsolutePath();
				if (源文件.isDirectory()) {
					moveFile(源文件, 目的文件);
				} else {
					long length = 源文件.length();
					if (!目的文件.exists()) {
						源文件.renameTo(目的文件);
						updateProgressInfo("从:" + 源文件路径 + "移动文件到:" + 目的文件路径 + "状态：完成", length);
					} else if (isSameFile(目的文件,源文件)) {
						updateProgressInfo("从:" + 源文件路径 + "移动文件到:" + 目的文件路径 + ",状态：备份文件已经存在", length);
						boolean df = 源文件.exists();
					} else {
						目的文件 = new File(toFile.getAbsolutePath() + "/" + 源文件.getName().replace(".", DateUtil.DateToString(new Date(), "_yyyy年MM月dd日HHmmss_SSS") + "."));
						源文件.renameTo(目的文件);
						updateProgressInfo("从:" + 源文件路径 + "移动文件到:" + 目的文件路径 + "状态：存在同名不同数据文件", length);
					}
				}
				源文件路径 = "null";
				目的文件路径 = "null";// 防止报错之后输出的是原始数据
			}
		} catch (Exception e) {
			l("从:" + 源文件路径 + "移动文件到:" + 目的文件路径 + "时发生异常" + e);
			throw e;
		}
	}

	public String getSelectData(){
		int sc = 备份文件夹列表.getSelectedColumn();
		int sr = 备份文件夹列表.getSelectedRow();
		return "" + 备份文件夹列表.getModel().getValueAt(sr, sc);
	}

	
}

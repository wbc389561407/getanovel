package com.nobug.util;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * @author 389561407@qq.com
 * @version 1.0
 * @since 2022-11-15
 */
public class RunMain {
    private JPanel root;
    private JButton button1;
    private JTextField urlText;
    private JLabel filepath;
    private JLabel saveUrl;
    private JLabel url;
    private JButton runButton;
    private JLabel log;
    private JTextArea logArea;
    private JButton stop;

    public RunMain() {

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jFileChooser = new JFileChooser(LoadText.root);
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFileChooser.showSaveDialog(null);
                File selectedFile = jFileChooser.getSelectedFile();
                if(selectedFile ==null){
                    return;
                }
                String path = selectedFile.getPath();
                saveUrl.setText(path);

            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!flag){
                    flag = true;
                    new Thread(() -> {
                        setArea("启动>>>>>>>>>>>>>>>>>>>>>>");
                        setArea("文件存储目录：" + saveUrl.getText());
                        String urlTextText = urlText.getText();
                        setArea("从网站获取数据：" + urlTextText);
                        List<TextFile> textFileList = LoadText.ddxs(urlTextText);
                        if (textFileList == null) {
                            setArea("无法访问到：" + urlTextText);
                            return;
                        }
                        setArea("获取到目录：" + textFileList.size());
                        setArea("开始获取正文。。。。。。");

                        int num = 0;
                        for (TextFile textFile : textFileList) {
                            if (!flag) {
                                return;
                            }
                            System.out.println(saveUrl.getText());
                            String path = saveUrl.getText() + "/" + textFile.getTitle() + "/";
                            num++;
                            String name = textFile.getName();
                            String replace = name.replace("?", "");
                            name = num + "." + replace;
                            setArea(textFile.getName() + "校验文件是否存在?");

                            File file = new File(path + name + ".txt");
                            if (!file.exists()) {
                                setArea(textFile.getName() + ":false");
                                setArea("创建：" + name);
                                String text = LoadText.urlToText(textFile.getUrl());
                                LoadText.toFile(path, name, text);
                            } else {
                                setArea(textFile.getName() + ":true");
                            }

                        }
                    }).start();
                }else {
                    setArea("已经启动，无需再按");
                }


            }


        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(flag){
                    flag = false;
                    setArea("stop");
                }else {
                    setArea("未启动，无需关闭");
                }

            }
        });
    }


    private static int indexNum = 0;

    public void setArea(String toString) {
        if (++indexNum % 20 == 0) {
            sb = new StringBuilder();
        }
        System.out.println(toString);
        sb.append(toString);
        sb.append("\n");
        logArea.setText(sb.toString());
//        new Thread(() ->{
//            System.out.println(toString);
//            sb.append(toString);
//            sb.append("\n");
//            textArea1.setText(sb.toString());
//        }).start();
    }


    public static boolean flag = false;

    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        JFrame frame = new JFrame("个人测试小说爬取");
        frame.setContentPane(new RunMain().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        int lx = Toolkit.getDefaultToolkit().getScreenSize().width;

        int ly = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
        frame.setSize(lx / 3, ly / 2);// 设定窗口大小
        frame.setVisible(true);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new GridLayoutManager(5, 4, new Insets(0, 0, 0, 0), -1, -1));
        root.setBackground(new Color(-5187854));
        final Spacer spacer1 = new Spacer();
        root.add(spacer1, new GridConstraints(0, 3, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        saveUrl = new JLabel();
        saveUrl.setText("D:/");
        root.add(saveUrl, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        filepath = new JLabel();
        filepath.setText("保存目录：");
        root.add(filepath, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        url = new JLabel();
        url.setText("网址：");
        root.add(url, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button1 = new JButton();
        button1.setText("选择");
        root.add(button1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        urlText = new JTextField();
        urlText.setText("https://www.ddxs.cc/ddxs/167966/");
        root.add(urlText, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        runButton = new JButton();
        runButton.setText("启动");
        root.add(runButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        log = new JLabel();
        log.setText("日志显示：");
        root.add(log, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logArea = new JTextArea();
        logArea.setText("");
        logArea.setToolTipText("");
        root.add(logArea, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        stop = new JButton();
        stop.setText("关闭");
        root.add(stop, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }

}

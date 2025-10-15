package solution;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrendAnalyzerGUI extends JFrame {
    private TrendAnalyzer trendAnalyzer;
    private JTextField nField;
    private JTextArea trend1Area;
    private JTextArea trend2Area;
    private JTextField minField;
    private JTextField maxField;
    private JTextField countField;


    public TrendAnalyzerGUI() {
        setTitle("TrendAnalyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        setLayout(new BorderLayout());
        initializeComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        trendAnalyzer = new TrendAnalyzer();
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Параметр n:"));
        nField = new JTextField(10);
        inputPanel.add(nField);
        JPanel trendsPanel = new JPanel(new GridLayout(1, 2));
        trend1Area = new JTextArea(10, 20);
        trend2Area = new JTextArea(10, 20);
        trend1Area.setEditable(false);
        trend2Area.setEditable(false);
        trendsPanel.add(new JScrollPane(trend1Area));
        trendsPanel.add(new JScrollPane(trend2Area));
        JPanel buttonPanel = new JPanel();
        JButton generateButton = new JButton("Сгенерировать тренды");
        JButton checkButton = new JButton("Проверить стабильность");
        buttonPanel.add(generateButton);
        buttonPanel.add(checkButton);
        GenerateButtonListener generateButtonListener = new GenerateButtonListener();
        generateButton.addActionListener(generateButtonListener);
        CheckButtonListener checkButtonListener = new CheckButtonListener();
        checkButton.addActionListener(checkButtonListener);
        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Настройки");
        JMenuItem rangeItem = new JMenuItem("Задать границы");
        rangeItem.addActionListener(new RangeItemListener());
        settingsMenu.add(rangeItem);
        menuBar.add(settingsMenu);
        setJMenuBar(menuBar);
        add(inputPanel, BorderLayout.NORTH);
        add(trendsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        generateButtonListener.actionPerformed(null);
    }

    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                trendAnalyzer.generateTrends();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Произошла ошибка при генерации трендов");
                return;
            }
            trend1Area.setText("Сгенерированный тренд 1\n");
            trend2Area.setText("Сгенерированный тренд 2\n");
            List<Integer> trend1 = trendAnalyzer.getTrend1();
            List<Integer> trend2 = trendAnalyzer.getTrend2();
            for (int i = 0; i < trend1.size(); i++) {
                trend1Area.append(i + ": " + trend1.get(i) + "\n");
                trend2Area.append(i + ": " + trend2.get(i) + "\n");
            }
        }
    }

    private class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int n = Integer.parseInt(nField.getText());
                trendAnalyzer.setN(n);
                int result1 = trendAnalyzer.analyzeTrend1();
                int result2 = trendAnalyzer.analyzeTrend2();
                StringBuilder message = new StringBuilder();
                if (result1 == -1) {
                    message.append("Тренд 1 стабильный\n");
                } else {
                    message.append("Тренд 1 не стабильный, стабильность нарушена на ").append(result1).append(" измерении\n");
                }
                if (result2 == -1) {
                    message.append("Тренд 2 стабильный\n");
                } else {
                    message.append("Тренд 2 не стабильный, стабильность нарушена на ").append(result2).append(" измерении\n");
                }
                JOptionPane.showMessageDialog(null, message.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Введите корректное значение n!");
            }
        }
    }

    private class RangeItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog rangeDialog = new JDialog(TrendAnalyzerGUI.this, "Настройка границ", true);
            rangeDialog.setLayout(new GridLayout(2, 3));
            rangeDialog.add(new JLabel("Минимум:"));
            minField = new JTextField(String.valueOf(trendAnalyzer.getMinRange()));
            rangeDialog.add(minField);
            rangeDialog.add(new JLabel("Максимум:"));
            maxField = new JTextField(String.valueOf(trendAnalyzer.getMaxRange()));
            rangeDialog.add(maxField);
            rangeDialog.add(new JLabel("Количество измерений:"));
            countField = new JTextField(String.valueOf(trendAnalyzer.getCount()));
            rangeDialog.add(countField);
            JButton saveButton = new JButton("Сохранить");
            saveButton.addActionListener(ev -> {
                try {
                    trendAnalyzer.setMinRange(Integer.parseInt(minField.getText()));
                    trendAnalyzer.setMaxRange(Integer.parseInt(maxField.getText()));
                    trendAnalyzer.setCount(Integer.parseInt(maxField.getText()));
                    rangeDialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(rangeDialog, "Введите корректные числа!");
                }
            });
            rangeDialog.add(saveButton);
            rangeDialog.pack();
            rangeDialog.setLocationRelativeTo(null);
            rangeDialog.setVisible(true);
        }
    }
}

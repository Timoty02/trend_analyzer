package solution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrendAnalyzerGUI extends JFrame {
    private JTextField nField;
    private JTextArea trend1Area;
    private JTextArea trend2Area;
    private JTextField minField;
    private JTextField maxField;
    private int minRange = 0;
    private int maxRange = 100;

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
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Параметр n:"));
        nField = new JTextField(10);
        inputPanel.add(nField);
        JPanel trendsPanel = new JPanel(new GridLayout(1, 2));
        trend1Area = new JTextArea(10, 20);
        trend2Area = new JTextArea(10, 20);
        trendsPanel.add(new JScrollPane(trend1Area));
        trendsPanel.add(new JScrollPane(trend2Area));
        JPanel buttonPanel = new JPanel();
        JButton generateButton = new JButton("Сгенерировать тренды");
        JButton checkButton = new JButton("Проверить стабильность");
        buttonPanel.add(generateButton);
        buttonPanel.add(checkButton);
        generateButton.addActionListener(new GenerateButtonListener());
        checkButton.addActionListener(new CheckButtonListener());
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
    }

    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            trend1Area.setText("Сгенерированный тренд 1\n");
            trend2Area.setText("Сгенерированный тренд 2\n");
        }
    }

    private class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int n = Integer.parseInt(nField.getText());
                JOptionPane.showMessageDialog(null,
                        "Проверка стабильности с n=" + n + "\n" +
                                "Здесь будет результат проверки");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Введите корректное значение n!");
            }
        }
    }

    private class RangeItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog rangeDialog = new JDialog(TrendAnalyzerGUI.this, "Настройка границ", true);
            rangeDialog.setLayout(new GridLayout(3, 2));
            rangeDialog.add(new JLabel("Минимум:"));
            minField = new JTextField(String.valueOf(minRange));
            rangeDialog.add(minField);
            rangeDialog.add(new JLabel("Максимум:"));
            maxField = new JTextField(String.valueOf(maxRange));
            rangeDialog.add(maxField);
            JButton saveButton = new JButton("Сохранить");
            saveButton.addActionListener(ev -> {
                try {
                    minRange = Integer.parseInt(minField.getText());
                    maxRange = Integer.parseInt(maxField.getText());
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

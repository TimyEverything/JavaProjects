/**
 * TaxCalculator
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaxCalculator extends JFrame {
    protected String userIn;
    protected double userNum;
    JTextField tfInput, tfOutput;
    JLabel lb1, lb2, lb3;

    public static double calculateTax(int age, double income) {
        double tax = 0.0;
        double taxableIncome;

        // Determine tax threshold based on age
        if (age < 65) {
            taxableIncome = Math.max(0, income - 95750);
        } else if (age < 75) {
            taxableIncome = Math.max(0, income - 148217);
        } else {
            taxableIncome = Math.max(0, income - 165689);
        }

        // Calculate tax based on taxable income
        if (taxableIncome <= 237100) {
            tax = 0.18 * taxableIncome;
        } else if (taxableIncome <= 370500) {
            tax = 42678 + 0.26 * (taxableIncome - 237100);
        } else if (taxableIncome <= 512800) {
            tax = 77362 + 0.31 * (taxableIncome - 370500);
        } else if (taxableIncome <= 673000) {
            tax = 121475 + 0.36 * (taxableIncome - 512800);
        } else if (taxableIncome <= 857900) {
            tax = 179147 + 0.39 * (taxableIncome - 673000);
        } else if (taxableIncome <= 1817000) {
            tax = 251258 + 0.41 * (taxableIncome - 857900);
        } else {
            tax = 644489 + 0.45 * (taxableIncome - 1817000);
        }

        return tax;
    }
    
    public class TaxCalculatorGUI extends JFrame {
        private JTextField ageField;
        private JTextField incomeField;
        private JTextField ataxField,mtaxField;

        public TaxCalculatorGUI() {
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            add(panel);
            placeComponents(panel);
        }

        private void placeComponents(JPanel panel) {
            panel.setLayout(null);
    
            JLabel ageLabel = new JLabel("Age:");
            ageLabel.setBounds(10, 20, 150, 25);
            panel.add(ageLabel);
    
            ageField = new JTextField(20);
            ageField.setBounds(120, 20, 165, 25);
            panel.add(ageField);
    
            JLabel incomeLabel = new JLabel("Annual Income:");
            incomeLabel.setBounds(10, 50, 150, 25);
            panel.add(incomeLabel);
    
            incomeField = new JTextField(20);
            incomeField.setBounds(120, 50, 165, 25);
            panel.add(incomeField);
    
            JButton calculateButton = new JButton("Calculate Tax");
            calculateButton.setBounds(120, 80, 165, 25);
            panel.add(calculateButton);
    
            JLabel ataxLabel = new JLabel("Annual Tax:");
            ataxLabel.setBounds(10, 110, 150, 25);
            panel.add(ataxLabel);
    
            ataxField = new JTextField(20);
            ataxField.setBounds(120, 110, 165, 25);
            panel.add(ataxField);
    
            JLabel mtaxLabel = new JLabel("Monthly Tax:");
            mtaxLabel.setBounds(10, 140, 150, 25);
            panel.add(mtaxLabel);
    
            mtaxField = new JTextField(20);
            mtaxField.setBounds(120, 140, 165, 25);
            panel.add(mtaxField);
    
            calculateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int age = Integer.parseInt(ageField.getText());
                    double income = Double.parseDouble(incomeField.getText());
                    double tax = TaxCalculator.calculateTax(age, income);
                    ataxField.setText(String.valueOf("R"+tax));
                    mtaxField.setText(String.valueOf("R"+(tax/12)));
                }
            });
        }

    }
    public static void main(String[] args) throws Exception {
        TaxCalculatorGUI gui = new TaxCalculator().new TaxCalculatorGUI();
        gui.setVisible(true);
    }
}

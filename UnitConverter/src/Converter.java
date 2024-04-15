import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Converter extends JFrame {
    final private Font mainFont = new Font("Helvetica", Font.BOLD, 18);
    protected String userIn;
    protected double userNum;
    JTextField tfInput, tfOutput;
    JLabel lbOut, lbIn;
    JPanel pnInOut, pnButtons;
    JComboBox<String> cbInputUnit, cbOutputUnit;
    Map<String, String[]> unitMap;
    String[] units = {"kg", "g", "mg", "pounds", "km", "m", "cm", "mm", "miles", "yards", "feet", "inches", "°C", "°F", "°k"};
    String[] unLength = {"km", "m", "cm", "mm","inches","feet", "yards", "miles"};
    String[] unWeight= {"kg", "g", "mg","pounds"};
    String[] unTemp = {"°C", "°F", "°k"};

    public void initialize() {

        lbIn = new JLabel("Input Value");
        lbOut = new JLabel("Converted Value");
        lbIn.setFont(mainFont);
        lbOut.setFont(mainFont);

        unitMap = new HashMap<>();
        unitMap.put("kg", unWeight);
        unitMap.put("g", unWeight);
        unitMap.put("mg", unWeight);
        unitMap.put("km", unLength);
        unitMap.put("m", unLength);
        unitMap.put("cm", unLength);
        unitMap.put("mm", unLength);
        unitMap.put("°C", unTemp);
        unitMap.put("°F", unTemp);
        unitMap.put("°k", unTemp);
        unitMap.put("inches", unLength);
        unitMap.put("feet", unLength);
        unitMap.put("yards", unLength);
        unitMap.put("miles", unLength);
        unitMap.put("pounds", unWeight);

        cbInputUnit = new JComboBox<>(units);
        cbOutputUnit = new JComboBox<>(unWeight);

        cbInputUnit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUnit = (String) cbInputUnit.getSelectedItem();
                String[] correspondingUnits = unitMap.get(selectedUnit);
                cbOutputUnit.setModel(new DefaultComboBoxModel<>(correspondingUnits));
            }
        });

        tfInput = new JTextField();
        tfOutput = new JTextField();
        tfOutput.setEditable(false); // The output field should not be editable

        JButton btnCalc = new JButton("Convert");
        btnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DecimalFormat df = new DecimalFormat("#.######");
                    userIn = tfInput.getText();
                    userNum = Double.parseDouble(userIn);
                    // Add your conversion logic here based on the selected units
                    

                    String inUnit = (String) cbInputUnit.getSelectedItem();
                    String outUnit = (String) cbOutputUnit.getSelectedItem();
                    double outVal = convertUnits(userNum, inUnit, outUnit);
                    tfOutput.setText(String.valueOf(df.format(outVal)));
                } catch (NumberFormatException f) {
                    // Handle the case where the input is not a valid double
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfInput.setText("");
                tfOutput.setText("");
            }
        });

        pnInOut = new JPanel();
        pnInOut.setLayout(new GridLayout(2, 3, 5, 5));
        pnInOut.add(lbIn);
        pnInOut.add(tfInput);
        pnInOut.add(cbInputUnit);
        pnInOut.add(lbOut);
        pnInOut.add(tfOutput);
        pnInOut.add(cbOutputUnit);

        pnButtons = new JPanel();
        pnButtons.setLayout(new GridLayout(1, 2, 5, 5));
        pnButtons.add(btnCalc);
        pnButtons.add(btnClear);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(pnInOut, BorderLayout.CENTER);
        mainPanel.add(pnButtons, BorderLayout.SOUTH);
        add(mainPanel);

        setTitle("Unit Converter");
        setSize(500, 600);
        setMinimumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        
        
    }

    public double convertUnits(double inputValue, String inputUnit, String outputUnit) {
        double outputValue = 0.0;
    
        if (Arrays.asList(unTemp).contains(outputUnit)) {
            switch (inputUnit) {
                case "°C":
                    System.out.println("Input unit: "+ inputUnit);
                    switch (outputUnit) {
                        case "°C":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = inputValue;
                            break;
                        case "°F":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = inputValue * (9.0/5.0) + 32.0;
                            break;
                        case "°k":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = inputValue + 273.15;
                            break;
                        default:
                            // handle unexpected outputUnit
                            break;
                    }
                    break;
                case "°F":
                    System.out.println("Input unit: "+ inputUnit);
                    switch (outputUnit) {
                        case "°C":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = (inputValue - 32.0) * 5.0/9.0;
                            break;
                        case "°F":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = inputValue;
                            break;
                        case "°k":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = (inputValue - 32.0) * 5.0/9.0 + 273.15;
                            break;
                        default:
                            // handle unexpected outputUnit
                            break;
                    }
                    break;
                case "°k":
                    System.out.println("Input unit: "+ inputUnit);
                    switch (outputUnit) {
                        case "°C":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = inputValue + 273.15;
                            break;
                        case "°F":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = (inputValue - 273.15) * (9.0/5) + 32;
                            break;
                        case "°k":
                            System.out.println("Output unit: " + outputUnit);
                            outputValue = inputValue;
                            break;
                        default:
                            // handle unexpected outputUnit
                            break;
                    }

                    break;
                default:
                    // handle unexpected inputUnit
                    break;
            }
        } else {
            if (inputValue <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number above zero.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                double conversionFactor = getConversionFactor(inputUnit, outputUnit);
                if (conversionFactor != 0) {
                    outputValue = inputValue / conversionFactor;
                } else {
                    System.out.println("Missing con unit");
                }
            }
            
        }
    
        return outputValue;
    }
    

    public double getConversionFactor(String inputUnit, String outputUnit) {
        double conversionFactor = 0;
        switch (inputUnit) {
            // {"kg", "g", "mg","pounds"}
            case "kg":
                switch (outputUnit) {
                    case "kg":
                        conversionFactor = 1.0;
                        break;
                
                    case "g":
                        conversionFactor = 0.001;
                        break;
                
                    case "mg":
                        conversionFactor = 0.000001;
                        break;
                
                    case "pounds":
                        conversionFactor = 0.453592;
                        break;
                
                    default:
                        break;
                
                }
                break;
        
            case "g":
                switch (outputUnit) {
                    case "kg":
                        conversionFactor = 1000.0;
                        break;
                
                    case "g":
                        conversionFactor = 1.0;
                        break;
                
                    case "mg":
                        conversionFactor = 0.001;
                        break;
                
                    case "pounds":
                        conversionFactor = 453.592;
                        break;
                
                    default:
                        break;
                
                }
                break;
        
            case "mg":
                switch (outputUnit) {
                    case "kg":
                        conversionFactor = 1000000.0;
                        break;
                
                    case "g":
                        conversionFactor = 1000.0;
                        break;
                
                    case "mg":
                        conversionFactor = 1.0;
                        break;
                
                    case "pounds":
                        conversionFactor = 453592.0;
                        break;
                
                    default:
                        break;
                
                }
                break;
        
            case "pounds":
                switch (outputUnit) {
                    case "kg":
                        conversionFactor = 2.2046244202;
                        break;
                
                    case "g":
                        conversionFactor = 0.0022046244;
                        break;
                
                    case "mg":
                        conversionFactor = 0.0000022046;
                        break;
                
                    case "pounds":
                        conversionFactor = 1.0;
                        break;
                
                    default:
                        break;
                
                }
                break;
            //{"km", "m", "cm", "mm","inches","feet", "yards", "miles"}
            case "km":
                switch (outputUnit) {
                    case "km":
                        conversionFactor = 1.0;
                        break;
                
                    case "m":
                        conversionFactor = 0.001;
                        break;
                
                    case "cm":
                        conversionFactor = 0.00001;
                        break;
                
                    case "mm":
                        conversionFactor = 0.000001;
                        break;
                
                    case "inches":
                        conversionFactor = 0.0000254;
                        break;
                
                    case "feet":
                        conversionFactor = 0.0003048;
                        break;
                
                    case "yards":
                        conversionFactor = 0.0009144;
                        break;
                
                    case "miles":
                        conversionFactor = 1.60935;
                        break;
                
                    default:
                        break;
                }
                break;
 
            case "m":
                switch (outputUnit) {
                    case "km":
                        conversionFactor = 1000.0;
                        break;
                
                    case "m":
                        conversionFactor = 1.0;
                        break;
                
                    case "cm":
                        conversionFactor = 0.01;
                        break;
                
                    case "mm":
                        conversionFactor = 0.001;
                        break;
                
                    case "inches":
                        conversionFactor = 0.0254;
                        break;
                
                    case "feet":
                        conversionFactor = 0.3048;
                        break;
                
                    case "yards":
                        conversionFactor = 0.9144;
                        break;
                
                    case "miles":
                        conversionFactor = 1609.35;
                        break;
                
                    default:
                        break;
                }
                break;
 
            case "cm":
                switch (outputUnit) {
                    case "km":
                        conversionFactor = 100000.0;
                        break;
                
                    case "m":
                        conversionFactor = 100.0;
                        break;
                
                    case "cm":
                        conversionFactor = 1.0;
                        break;
                
                    case "mm":
                        conversionFactor = 0.1;
                        break;
                
                    case "inches":
                        conversionFactor = 2.54;
                        break;
                
                    case "feet":
                        conversionFactor = 30.48;
                        break;
                
                    case "yards":
                        conversionFactor = 91.44;
                        break;
                
                    case "miles":
                        conversionFactor = 160935.0;
                        break;
                
                    default:
                        break;
                }
                break;
 
            case "mm":
                switch (outputUnit) {
                    case "km":
                        conversionFactor = 1000000.0;
                        break;
                
                    case "m":
                        conversionFactor = 1000.0;
                        break;
                
                    case "cm":
                        conversionFactor = 10.0;
                        break;
                
                    case "mm":
                        conversionFactor = 1.0;
                        break;
                
                    case "inches":
                        conversionFactor = 25.4;
                        break;
                
                    case "feet":
                        conversionFactor = 304.8;
                        break;
                
                    case "yards":
                        conversionFactor = 914.4;
                        break;
                
                    case "miles":
                        conversionFactor = 1609350.0;
                        break;
                
                    default:
                        break;
                }
                break;
 
            case "inches":
                switch (outputUnit) {
                    case "km":
                        conversionFactor = 39370.07874;
                        break;
                
                    case "m":
                        conversionFactor = 39.37007874;
                        break;
                
                    case "cm":
                        conversionFactor = 0.3937007874;
                        break;
                
                    case "mm":
                        conversionFactor = 0.0393700787;
                        break;
                
                    case "inches":
                        conversionFactor = 1.0;
                        break;
                
                    case "feet":
                        conversionFactor = 12.0;
                        break;
                
                    case "yards":
                        conversionFactor = 36.0;
                        break;
                
                    case "miles":
                        conversionFactor = 63360.23622;
                        break;
                
                    default:
                        break;
                }
                break;
 
            case "feet":
                switch (outputUnit) {
                    case "km":
                        conversionFactor = 3280.839895;
                        break;
                
                    case "m":
                        conversionFactor = 3.280839895;
                        break;
                
                    case "cm":
                        conversionFactor = 0.032808399;
                        break;
                
                    case "mm":
                        conversionFactor = 0.0032808399;
                        break;
                
                    case "inches":
                        conversionFactor = 0.0833333333;
                        break;
                
                    case "feet":
                        conversionFactor = 1.0;
                        break;
                
                    case "yards":
                        conversionFactor = 3.0;
                        break;
                
                    case "miles":
                        conversionFactor = 5280.019685;
                        break;
                
                    default:
                        break;
                }
                break;
 
            case "yards":
                switch (outputUnit) {
                    case "km":
                        conversionFactor = 1093.6132983;
                        break;
                
                    case "m":
                        conversionFactor = 1.0936132983;
                        break;
                
                    case "cm":
                        conversionFactor = 0.010936133;
                        break;
                
                    case "mm":
                        conversionFactor = 0.0010936133;
                        break;
                
                    case "inches":
                        conversionFactor = 0.0277777778;
                        break;
                
                    case "feet":
                        conversionFactor = 0.3333333333;
                        break;
                
                    case "yards":
                        conversionFactor = 1.0;
                        break;
                
                    case "miles":
                        conversionFactor = 1760.0065617;
                        break;
                }
                break;

            case "miles":
                switch (outputUnit) {
                    case "km":
                        conversionFactor = 0.6213688756;
                        break;
                
                    case "m":
                        conversionFactor = 0.0006213689;
                        break;
                
                    case "cm":
                        conversionFactor = 0.0000062137;
                        break;
                
                    case "mm":
                        conversionFactor = 0.00000062137;
                        break;
                
                    case "inches":
                        conversionFactor = 0.0000157828;
                        break;
                
                    case "feet":
                        conversionFactor = 0.0001893932;
                        break;
                
                    case "yards":
                        conversionFactor = 0.0005681797;
                        break;
                
                    case "miles":
                        conversionFactor = 1.0;
                        break;
                
                    default:
                        break;
                }
                break;

            default:
                break;
        }

        return conversionFactor;
    }

    public static void main(String[] args) throws Exception {
        Converter UnitConverter = new Converter();
        UnitConverter.initialize();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PolynomialCalculatorFrame extends JFrame implements ActionListener {

    //implementation for actionPerformed method from ActionListener interface
    @Override
    public void actionPerformed ( ActionEvent actionEvent ) {
        String actionCommand = actionEvent.getActionCommand ( );

        if ( actionCommand.startsWith ( "i" ) ) {
            switch ( actionCommand.substring ( 1 ) ) {
                case "⌫" -> {
                    JTextField focusedTextField = ( PTextField.hasFocus ( ) ) ? PTextField : QTextField;
                    int pos = focusedTextField.getCaretPosition ( );
                    String firstHalf = ( pos == 0 ) ? "" : focusedTextField.getText ( ).substring ( 0, pos - 1 );
                    String secondHalf = ( focusedTextField.getText ( ).length ( ) == pos ) ? "" : focusedTextField.getText ( ).substring ( pos );
                    focusedTextField.setText ( firstHalf + secondHalf );
                    focusedTextField.setCaretPosition ( Math.max ( 0, pos - 1 ) );
                }
                case "C" -> {
                    PTextField.setText ( "" );
                    QTextField.setText ( "" );
                    RTextField.setText ( "" );
                }
                case "↵" -> {
                    Polynomial P;
                    Polynomial Q = new Polynomial ( );
                    Polynomial[] R;
                    try {
                        P = Polynomial.valueOf ( PTextField.getText ( ) );
                    }
                    catch ( Polynomial.NotAPolynomialException exception ) {
                        RTextField.setText ( "Cannot read first polynomial" );
                        return;
                    }

                    if ( selectedOperation.length ( ) == 1 ) {
                        try {
                            Q = Polynomial.valueOf ( QTextField.getText ( ) );
                            R = PolynomialCalculator.calculate ( P, selectedOperation, Q );
                        }
                        catch ( Polynomial.NotAPolynomialException exception ) {
                            RTextField.setText ( "Cannot read second polynomial" );
                            return;
                        }
                        catch ( IllegalArgumentException exception ) {
                            RTextField.setText ( "Division by zero" );
                            return;
                        }
                    }
                    else {
                        try {

                            R = new Polynomial[]{ PolynomialCalculator.calculate ( selectedOperation, P ) };
                        }
                        catch ( IllegalArgumentException exception ) {
                            return;
                        }
                    }

                    String result;
                    if ( selectedOperation.equals ( "/" ) ) {
                        if ( R[1].isZeroPolynomial ( ) )
                            result = R[0].toString ( );
                        else
                            result = R[0].toString ( ) + " + ( " + R[1].toString ( ) + " ) / ( " + Q.toString ( ) + " )";
                    }
                    else if ( selectedOperation.equals ( "∫dx" ) )
                        result = R[0].toString ( ) + " + C";
                    else
                        result = R[0].toString ( );

                    RTextField.setText ( result );

                }
                default -> {
                    JTextField focusedTextField = ( PTextField.hasFocus ( ) ) ? PTextField : QTextField;
                    int pos = focusedTextField.getCaretPosition ( );
                    String firstHalf = ( pos == 0 ) ? "" : focusedTextField.getText ( ).substring ( 0, pos );
                    String secondHalf = ( focusedTextField.getText ( ).length ( ) == pos ) ? "" : focusedTextField.getText ( ).substring ( pos );
                    focusedTextField.setText ( firstHalf + actionCommand.substring ( 1 ) + secondHalf );
                    focusedTextField.setCaretPosition ( Math.max ( 0, pos + 1 ) );
                }
            }
        }
        else if ( actionCommand.startsWith ( "op" ) ) {
            switch ( selectedOperation ) {
                case "+" -> operationButtons[0].setBackground ( Color.DARK_GRAY );
                case "-" -> operationButtons[1].setBackground ( Color.DARK_GRAY );
                case "*" -> operationButtons[2].setBackground ( Color.DARK_GRAY );
                case "/" -> operationButtons[3].setBackground ( Color.DARK_GRAY );
                case "d/dx" -> operationButtons[4].setBackground ( Color.DARK_GRAY );
                case "∫dx" -> operationButtons[5].setBackground ( Color.DARK_GRAY );
            }
            switch ( actionCommand.substring ( 2 ) ) {
                case "+" -> operationButtons[0].setBackground ( new Color ( 105, 105, 105 ) );
                case "-" -> operationButtons[1].setBackground ( new Color ( 105, 105, 105 ) );
                case "*" -> operationButtons[2].setBackground ( new Color ( 105, 105, 105 ) );
                case "/" -> operationButtons[3].setBackground ( new Color ( 105, 105, 105 ) );
                case "d/dx" -> operationButtons[4].setBackground ( new Color ( 105, 105, 105 ) );
                case "∫dx" -> operationButtons[5].setBackground ( new Color ( 105, 105, 105 ) );
            }
            selectedOperation = actionCommand.substring ( 2 );

            toggleQTextField ( selectedOperation.length ( ) == 1 );
        }
    }

    //helper function for setting up the main panel that contains all other components
    private static JPanel mainPanelBuilder ( ) {
        JPanel mainPanel = new JPanel ( new SpringLayout ( ) );
        mainPanel.setBackground ( Color.RED );

        return mainPanel;
    }

    //helper function for setting up JTextField type components
    private static JTextField textFieldBuilder ( ) {
        JTextField textField = new JTextField ( );
        textField.setPreferredSize ( new Dimension ( 300, 80 ) );
        textField.setBackground ( new Color ( 150, 150, 150 ) );
        textField.setBorder ( BorderFactory.createCompoundBorder ( BorderFactory.createMatteBorder ( 4, 4, 2, 4, new Color ( 40, 40, 40 ) ), BorderFactory.createEmptyBorder ( 0, 0, 0, 0 ) ) );
        textField.setFont ( new Font ( Font.SANS_SERIF, Font.BOLD, 18 ) );
        textField.setHorizontalAlignment ( JTextField.TRAILING );

        return textField;
    }

    //helper function for setting up JButton type components meant for selecting the operation to be performed
    private static JButton[] operationButtonsBuilder ( ) {
        JButton[] operationButtons = new JButton[6];
        for ( int i = 0; i < 6; i++ )
            operationButtons[i] = new JButton ( );

        for ( JButton operationButton : operationButtons ) {
            operationButton.setPreferredSize ( new Dimension ( 75, 70 ) );
            operationButton.setFocusable ( false );
            operationButton.setFont ( new Font ( Font.SANS_SERIF, Font.BOLD, 18 ) );
            operationButton.setBackground ( Color.DARK_GRAY );
            operationButton.setForeground ( Color.WHITE );
            operationButton.setBorder ( BorderFactory.createLineBorder ( new Color ( 40, 40, 40 ), 2 ) );
        }

        operationButtons[0].setText ( "+" );
        operationButtons[0].setActionCommand ( "op+" );
        operationButtons[1].setText ( "-" );
        operationButtons[1].setActionCommand ( "op-" );
        operationButtons[2].setText ( "*" );
        operationButtons[2].setActionCommand ( "op*" );
        operationButtons[3].setText ( "/" );
        operationButtons[3].setActionCommand ( "op/" );
        operationButtons[4].setText ( "d/dx" );
        operationButtons[4].setActionCommand ( "opd/dx" );
        operationButtons[5].setText ( "∫dx" );
        operationButtons[5].setActionCommand ( "op∫dx" );

        return operationButtons;
    }

    //helper function for setting up panel that contains all operation selection buttons
    private static JPanel operationsPanelBuilder ( ) {
        JPanel operationsPanel = new JPanel ( new GridLayout ( 1, 6, 0, 0 ) );

        operationsPanel.setPreferredSize ( new Dimension ( 400, 70 ) );
        operationsPanel.setBackground ( new Color ( 40, 40, 40 ) );

        for ( JButton operationButton : operationButtons )
            operationsPanel.add ( operationButton );

        return operationsPanel;
    }

    //helper function for setting up JButton type components for inputting
    private static JButton[] inputButtonsBuilder ( ) {
        JButton[] inputButtons = new JButton[19];
        for ( int i = 0; i < 19; i++ )
            inputButtons[i] = new JButton ( );

        for ( JButton inputButton : inputButtons ) {
            inputButton.setFocusable ( false );
            inputButton.setFont ( new Font ( Font.SANS_SERIF, Font.BOLD, 18 ) );
            inputButton.setBackground ( Color.DARK_GRAY );
            inputButton.setForeground ( Color.WHITE );
            inputButton.setBorder ( BorderFactory.createLineBorder ( new Color ( 40, 40, 40 ), 2 ) );
        }

        for ( int i = 0; i < 10; i++ ) {
            inputButtons[i].setText ( String.valueOf ( i ) );
            inputButtons[i].setActionCommand ( "i" + String.valueOf ( i ) );
            inputButtons[i].setBackground ( new Color ( 50, 50, 50 ) );
        }
        inputButtons[10].setText ( "." );
        inputButtons[10].setActionCommand ( "i." );
        inputButtons[10].setBackground ( new Color ( 50, 50, 50 ) );
        inputButtons[11].setText ( "+" );
        inputButtons[11].setActionCommand ( "i+" );
        inputButtons[12].setText ( "-" );
        inputButtons[12].setActionCommand ( "i-" );
        inputButtons[13].setText ( "*" );
        inputButtons[13].setActionCommand ( "i*" );
        inputButtons[14].setText ( "^" );
        inputButtons[14].setActionCommand ( "i^" );
        inputButtons[15].setText ( "↵" );
        inputButtons[15].setActionCommand ( "i↵" );
        inputButtons[15].setBackground ( new Color ( 95, 95, 95 ) );
        inputButtons[16].setText ( "⌫" );
        inputButtons[16].setActionCommand ( "i⌫" );
        inputButtons[16].setFont ( new Font ( Font.SANS_SERIF, Font.BOLD, 16 ) );
        inputButtons[17].setText ( "C" );
        inputButtons[17].setActionCommand ( "iC" );
        inputButtons[18].setText ( "x" );
        inputButtons[18].setActionCommand ( "ix" );
        inputButtons[18].setBackground ( new Color ( 50, 50, 50 ) );

        return inputButtons;
    }

    //helper function for setting up panel that contains all input buttons
    private static JPanel inputButtonsPanelBuilder ( ) {
        JPanel inputButtonsPanel = new JPanel ( new GridBagLayout ( ) );

        inputButtonsPanel.setPreferredSize ( new Dimension ( 400, 350 ) );
        inputButtonsPanel.setBackground ( new Color ( 40, 40, 40 ) );
        GridBagConstraints c = new GridBagConstraints ( );

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;

        inputButtonsPanel.add ( inputButtons[11], c );

        c.gridx = 1;
        inputButtonsPanel.add ( inputButtons[12], c );

        c.gridx = 2;
        inputButtonsPanel.add ( inputButtons[13], c );

        c.gridx = 3;
        inputButtonsPanel.add ( inputButtons[14], c );

        c.gridy = 1;
        inputButtonsPanel.add ( inputButtons[16], c );

        c.gridx = 2;
        inputButtonsPanel.add ( inputButtons[9], c );

        c.gridx = 1;
        inputButtonsPanel.add ( inputButtons[8], c );

        c.gridx = 0;
        inputButtonsPanel.add ( inputButtons[7], c );

        c.gridy = 2;
        inputButtonsPanel.add ( inputButtons[4], c );

        c.gridx = 1;
        inputButtonsPanel.add ( inputButtons[5], c );

        c.gridx = 2;
        inputButtonsPanel.add ( inputButtons[6], c );

        c.gridx = 3;
        inputButtonsPanel.add ( inputButtons[17], c );

        c.gridy = 3;
        c.gridheight = 2;
        inputButtonsPanel.add ( inputButtons[15], c );

        c.gridx = 2;
        c.gridheight = 1;
        inputButtonsPanel.add ( inputButtons[3], c );

        c.gridx = 1;
        inputButtonsPanel.add ( inputButtons[2], c );

        c.gridx = 0;
        inputButtonsPanel.add ( inputButtons[1], c );

        c.gridy = 4;
        inputButtonsPanel.add ( inputButtons[18], c );

        c.gridx = 1;
        inputButtonsPanel.add ( inputButtons[0], c );

        c.gridx = 2;
        inputButtonsPanel.add ( inputButtons[10], c );

        return inputButtonsPanel;
    }

    //variables meant to store components that the actionPerformed method needs access too
    private static final JPanel mainPanel = mainPanelBuilder ( );
    private static final JTextField PTextField = textFieldBuilder ( );
    private static final JTextField QTextField = textFieldBuilder ( );
    private static final JTextField RTextField = textFieldBuilder ( );
    private static final JButton[] operationButtons = operationButtonsBuilder ( );
    private static final JPanel operationsPanel = operationsPanelBuilder ( );
    private static final JButton[] inputButtons = inputButtonsBuilder ( );
    private static final JPanel inputButtonsPanel = inputButtonsPanelBuilder ( );

    //variables that stores the currently selected operation
    private static String selectedOperation = "+";

    //constructor
    PolynomialCalculatorFrame ( ) {
        setTitle ( "Polynomial Calculator" );
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        mainPanel.add ( PTextField );
        mainPanel.add ( QTextField );
        mainPanel.add ( RTextField );
        mainPanel.add ( operationsPanel );
        mainPanel.add ( inputButtonsPanel );
        add ( mainPanel );

        SpringLayout layout = ( SpringLayout ) mainPanel.getLayout ( );

        layout.putConstraint ( SpringLayout.NORTH, PTextField, 0, SpringLayout.NORTH, mainPanel );
        layout.putConstraint ( SpringLayout.WEST, PTextField, 0, SpringLayout.WEST, mainPanel );
        layout.putConstraint ( SpringLayout.EAST, PTextField, 0, SpringLayout.EAST, mainPanel );

        layout.putConstraint ( SpringLayout.NORTH, QTextField, 0, SpringLayout.SOUTH, PTextField );

        layout.putConstraint ( SpringLayout.WEST, QTextField, 0, SpringLayout.WEST, mainPanel );
        layout.putConstraint ( SpringLayout.EAST, QTextField, 0, SpringLayout.EAST, mainPanel );

        layout.putConstraint ( SpringLayout.NORTH, RTextField, 0, SpringLayout.SOUTH, QTextField );

        layout.putConstraint ( SpringLayout.WEST, RTextField, 0, SpringLayout.WEST, mainPanel );
        layout.putConstraint ( SpringLayout.EAST, RTextField, 0, SpringLayout.EAST, mainPanel );

        layout.putConstraint ( SpringLayout.NORTH, operationsPanel, 0, SpringLayout.SOUTH, RTextField );

        layout.putConstraint ( SpringLayout.WEST, operationsPanel, 0, SpringLayout.WEST, mainPanel );
        layout.putConstraint ( SpringLayout.EAST, operationsPanel, 0, SpringLayout.EAST, mainPanel );

        layout.putConstraint ( SpringLayout.NORTH, inputButtonsPanel, 0, SpringLayout.SOUTH, operationsPanel );

        layout.putConstraint ( SpringLayout.WEST, inputButtonsPanel, 0, SpringLayout.WEST, mainPanel );
        layout.putConstraint ( SpringLayout.SOUTH, mainPanel, 0, SpringLayout.SOUTH, inputButtonsPanel );
        layout.putConstraint ( SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, inputButtonsPanel );

        RTextField.setEditable ( false );
        RTextField.setHorizontalAlignment ( JTextField.LEFT );
        operationButtons[0].setBackground ( new Color ( 105, 105, 105 ) );

        for ( JButton operationButton : operationButtons )
            operationButton.addActionListener ( this );
        for ( JButton inputButton : inputButtons )
            inputButton.addActionListener ( this );

        pack ( );
        setMinimumSize ( getSize ( ) );
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( new Point ( ( screenSize.width - getWidth ( ) ) / 2, ( screenSize.height - getHeight ( ) ) / 2 ) );
        setVisible ( true );
    }

    //method for rearranging needed components accordingly
    private void toggleQTextField ( Boolean on ) {
        SpringLayout layout = ( SpringLayout ) mainPanel.getLayout ( );
        if ( on ) {
            mainPanel.add ( QTextField );

            layout.putConstraint ( SpringLayout.NORTH, QTextField, 0, SpringLayout.SOUTH, PTextField );

            layout.putConstraint ( SpringLayout.WEST, QTextField, 0, SpringLayout.WEST, mainPanel );
            layout.putConstraint ( SpringLayout.EAST, QTextField, 0, SpringLayout.EAST, mainPanel );

            layout.putConstraint ( SpringLayout.NORTH, RTextField, 0, SpringLayout.SOUTH, QTextField );
        }
        else {
            mainPanel.remove ( QTextField );

            layout.putConstraint ( SpringLayout.NORTH, RTextField, 0, SpringLayout.SOUTH, PTextField );
        }
        pack ( );
        setMinimumSize ( getSize ( ) );
        revalidate ( );
        repaint ( );
    }
}

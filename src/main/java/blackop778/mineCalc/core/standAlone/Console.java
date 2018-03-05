package blackop778.mineCalc.core.standAlone;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Console extends JPanel {

    private static final long serialVersionUID = -8126442231468996619L;
    private JDialog dialog;
    private JTextField input;
    private JTextArea output;
    private final int textWidth = 51;
    private CommandManager cmds;
    private ArrayList<PreviousInput> inputs;
    private int currentInput;

    public Console() {
        cmds = new CommandManager();
        inputs = new ArrayList<PreviousInput>();
        currentInput = 0;
        dialog = new JDialog((JDialog) null);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        input = new JTextField(textWidth);
        input.setEditable(true);
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!input.getText().equals("")) {
                    output.append("\n" + actionOccured(input.getText()));
                    if (inputs.size() > 0 && !inputs.get(inputs.size() - 1).isExecuted()) {
                        inputs.get(inputs.size() - 1).setInput(input.getText());
                        inputs.get(inputs.size() - 1).setExecuted();
                    } else {
                        inputs.add(new PreviousInput(input.getText(), true));
                    }
                    currentInput = inputs.size();
                    input.setText("");
                }
            }
        });
        InputMap keyBindings = input.getInputMap(JTextField.WHEN_FOCUSED);
        keyBindings.put(KeyStroke.getKeyStroke("pressed UP"), "UP");
        keyBindings.put(KeyStroke.getKeyStroke("pressed DOWN"), "DOWN");
        ActionMap keyActions = input.getActionMap();
        keyActions.put("UP", new AbstractAction() {
            private static final long serialVersionUID = 521728007529640141L;

            @SuppressWarnings("unused")
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<PreviousInput> inputss = inputs;
                int currentInputs = currentInput;
                if (currentInput > 0) {
                    if (currentInput == inputs.size())
                        if (inputs.get(inputs.size() - 1).isExecuted()) {
                            inputs.add(new PreviousInput(input.getText(), false));
                        } else {
                            inputs.get(currentInput - 1).setInput(input.getText());
                        }
                    input.setText(inputs.get(currentInput - 1).getInput());
                    currentInput--;
                }
            }
        });
        keyActions.put("DOWN", new AbstractAction() {
            private static final long serialVersionUID = 5622737238507085866L;

            @SuppressWarnings("unused")
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<PreviousInput> inputss = inputs;
                int currentInputs = currentInput;
                if (currentInput + 1 < inputs.size()) {
                    currentInput++;
                    input.setText(inputs.get(currentInput).getInput());
                }

            }
        });

        output = new JTextArea("Type 'help' for a list of available commands", 8, textWidth);
        output.setLineWrap(true);
        output.setEditable(false);

        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(output);
        jsp.createVerticalScrollBar();
        add(jsp);
        add(input);
        dialog.add(this);

        dialog.setTitle("MineCalc Stand Alone");
        dialog.pack();
        input.requestFocusInWindow();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private String actionOccured(String input) {
        String[] args = input.split(Pattern.quote(" "));
        String toReturn = cmds.processInput(args);
        return toReturn;
    }

    public class PreviousInput {
        private String input;
        private boolean executed;

        public PreviousInput(String text, boolean exe) {
            setInput(text);
            executed = exe;
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            if (!isExecuted()) {
                this.input = input;
            }
        }

        public boolean isExecuted() {
            return executed;
        }

        /**
         * Sets state to executed
         */
        public void setExecuted() {
            executed = true;
        }
    }
}
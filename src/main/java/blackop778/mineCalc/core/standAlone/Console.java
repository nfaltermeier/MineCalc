package blackop778.mineCalc.core.standAlone;

import javax.annotation.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Console extends JPanel {

    private static final long serialVersionUID = -8126442231468996619L;
    @Nullable
    private JDialog dialog;
    private JTextField input;
    private JTextArea output;
    private JScrollBar verticalScrollBar;
    private final int textWidth = 51;
    private CommandManager commands;
    private ArrayList<PreviousInput> inputs;
    private int currentInput;

    public Console() {
        commands = new CommandManager();
        inputs = new ArrayList<PreviousInput>();
        currentInput = 0;
        dialog = new JDialog((JDialog) null);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        input = new JTextField(textWidth);
        input.setEditable(true);
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Did they actually submit anything?
                if (!input.getText().equals("")) {
                    boolean wasAtBottom = verticalScrollBar.getValue() == verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount();
                    output.append("\n" + actionOccurred(input.getText()));
                    if(wasAtBottom)
                    {
                        // We need to scroll to the bottom later rather than now so invoke later
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                verticalScrollBar.setValue(verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount());
                            }
                        });
                    }

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

            @Override
            public void actionPerformed(ActionEvent e) {
                // If currentInput is 0 then we are scrolled all the way up, no more to go
                if (currentInput > 0) {
                    // If we are scrolled all the way down (so we can store the current input in case we want to come back)
                    if (currentInput == inputs.size()) {
                        if (inputs.get(inputs.size() - 1).isExecuted()) {
                            // If the last input was executed it is a permanent part of our history
                            // so we must add a new input to the list
                            inputs.add(new PreviousInput(input.getText(), false));
                        } else {
                            // The last input was never executed so we can overwrite it
                            inputs.get(currentInput - 1).setInput(input.getText());
                        }
                    }

                    // Set the next input in the history as the current
                    input.setText(inputs.get(currentInput - 1).getInput());
                    // Indicate we went up in the history
                    currentInput--;
                }
            }
        });

        keyActions.put("DOWN", new AbstractAction() {
            private static final long serialVersionUID = 5622737238507085866L;

            @Override
            public void actionPerformed(ActionEvent e) {
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
        verticalScrollBar = jsp.getVerticalScrollBar();

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

    private String actionOccurred(String input) {
        String[] args = input.split(Pattern.quote(" "));
        return commands.processInput(args);
    }

    public class PreviousInput {
        private String input;
        private boolean executed;

        PreviousInput(String text, boolean exe) {
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
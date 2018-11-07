package calculatorUi;
import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 * @author gfoss
 */

public class NumField extends JTextField
{
    private double value = 0;

    public NumField() {
        this.addFocusListener(new FieldFocusListener(this));
    }

    private void assignVal(NumField text)
    {
        //Creates string to hold text in field for later parsing
        String stringVal;
        stringVal = text.getText();

        //Parses to double if input isn't empty
        if (!text.getText().isEmpty()) {
            value = Double.parseDouble(stringVal);
        }
        System.out.println(value);
    }

    public double getValue () {
        return value;
    }

    public void setValue (double value) {
        this.value = value;
    }

    //Listens for a change in focus, both gaining and losing
    public class FieldFocusListener implements FocusListener {
        NumField field;

        public FieldFocusListener(NumField field) {
            this.field = field;
        }
        @Override
        public void focusGained (FocusEvent e) {
            //Accept text as usual, no special action.
        }
        @Override
        public void focusLost (FocusEvent e) {
            assignVal(field);
        }
    }
}

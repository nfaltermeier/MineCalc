package Blackop778.MineCalc.core.standAlone;

import javax.swing.JEditorPane;
import javax.swing.text.Document;

public class HTMLPane extends JEditorPane {
    private static final long serialVersionUID = 7757321520582059952L;

    public HTMLPane(String content) {
	super("text/html", content);
    }

    public void appendBeforeHTMLClose(String content) {
	Document doc = getDocument();
	doc.insertString(doc.getLength() - 6, content, a);
    }
}

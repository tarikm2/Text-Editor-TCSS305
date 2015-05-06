import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Tarik Merzouk
 * @version 4/29/2015
 *
 */
public class MyDocument extends JPanel {

    /**
     * default id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * area for text.
     */
    private JTextArea myText;
    
    /**
     * scroll bar for text area.
     */
    private JScrollPane myScroll;
    
    /**
     * the font for myText text area.
     */
    private Font myFont;
    
    /**
     * The font size for myText text area.
     */
    private int myFontSize;
    
    /**
     * screen size dimensions.
     */
    private final Dimension myScreen;
    
    /**
     * Instantiates the document.
     * @param theScreen the dimentions of current screen.
     * @custom.post MyDocument is instantiated.
     */
    public MyDocument(final Dimension theScreen) {
        myText = new JTextArea();
        myScroll = new JScrollPane(myText);
        myFontSize = 18;
        myFont = new Font("Arial", Font.PLAIN, myFontSize);
        myScreen = new Dimension(theScreen);
        formatBar();
    }

    /**
     * formats the elements for this menubar.
     * @custom.post menu bar elements are formatted.
     */
    private void formatBar() {
        myText.setFont(myFont);
        
        myText.setLineWrap(true);
        myText.setWrapStyleWord(true);
        
        myScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        myScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        myScroll.setPreferredSize(myScreen);
        
        add(myScroll);
        
    }

    /**
     * setter method for selecting text wrap option.
     * @custom.post the text wrap option is enabled.
     */
    public void setWrap() {
        myText.setLineWrap(true);
        myText.setWrapStyleWord(true);  
    }

    /**
     * setter method for unselecting the text wrap option.
     * @custom.post the text area wrapping is turned off.
     */
    public void unsetWrap() {
        myText.setLineWrap(false);
        myText.setWrapStyleWord(false);
    }

    /**
     * setter method for adjusting the font type.
     * @param theType the font to be changed to.
     * @custom.post the font selection is changed to s.
     */
    public void setFontType(final String theType) {
        //re-instantiate myFont with current size/style but new type
        myFont = new Font(theType, myFont.getStyle(), myFont.getSize());
        myText.setFont(myFont);
        
    }
}

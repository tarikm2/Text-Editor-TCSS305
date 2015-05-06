import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is the menuebar class. 
 */

/**
 * @author Tarik Merzouk
 * @version 4/29/2015
 *
 */
public class MenuBar extends JMenuBar {
    
    /**
     * The default ID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * menu bar for the program.
     */
    private final JMenuBar myBar;
    
    /**
     * file option.
     */
    private final JMenu myFileOpt;
    
    /**
     * Format option.
     */
    private final JMenu myFormatOpt;
    
    /**
     * Help option.
     */
    private final JMenu myHelpOpt;
    
    /**
     * The new jmenu option.
     */
    private final JMenuItem myNewItm;
    
    /**
     * the save jmenu item.
     */
    private final JMenuItem mySaveItm;
    
    /**
     * the open item.
     */
    private final JMenuItem myOpenItm;
   
   /**
    * the print item.
    */
    private final JMenuItem myPrintItm;
   
   /**
    * the exit item.
    */
    private final JMenuItem myExitItm;
   
   /**
    * word wrap checkbox.
    */
    private final JCheckBoxMenuItem myWrapBox;
   
   /**
    * submenu for fonts.
    */
    private final JMenu myFontSub;

    /**
     * font type field.
     */
    private final JMenuItem myType;

    /**
     * font style field.
     */
    private final JMenuItem myStyle;
    
    /**
     * font size field.
     */
    private final  JMenuItem mySize;
    
    /**
     * the about item.
     */
    private final JMenuItem myAbout;
    
    /**
     * the document containing this menu bar.
     */
    private final MyDocument myDoc;

    /**
     * the action listener for openeing and saving files.
     */
    private FileListener myFileListener;
    
    /**
     * file chooser for selecting file to open.
     */
    private JFileChooser myFileChooser;
    
    
    /**
     * This represents the menu of our basic txt editor.
     * @param theDoc the document using this menu bar.
     * @custom.post all elements added to the menu bar.
     */
    public MenuBar(final MyDocument theDoc) {
        super();
        myFileListener = new FileListener();
        myBar = new JMenuBar();
        myDoc = theDoc;
        
        myFileOpt = new JMenu("File");
        myFormatOpt = new JMenu("Format");
        myHelpOpt = new JMenu("Help");
        
        myWrapBox = new JCheckBoxMenuItem("Text-Wrap");
        myFontSub = new JMenu("Font");
        myType = new JMenuItem("Type");
        myStyle = new JMenuItem("Style");
        mySize = new JMenuItem("Size");
        
        myNewItm = new JMenuItem("New");
        mySaveItm  = new JMenuItem("Save");
        myOpenItm  = new JMenuItem("Open");
        myPrintItm  = new JMenuItem("Print");
        myExitItm  = new JMenuItem("Exit");
        
        myAbout = new JMenuItem("About");
        
        setup();
    }
    
    /**
     * this method adds the menu items to the menu.
     * @custom.post the menu items are all added.
     */
    private void setup() {
         //set up the file option
        myFileOpt.add(myNewItm);
        myFileOpt.add(myOpenItm);
        myFileOpt.add(mySaveItm);
        myFileOpt.add(myPrintItm);
        myFileOpt.add(myExitItm);
        
        myFormatOpt.add(myWrapBox);
        myFormatOpt.addSeparator();
        myFontSub.add(myType);
        myFontSub.add(myStyle);
        myFontSub.add(mySize);
        myFormatOpt.add(myFontSub);
        
        myHelpOpt.add(myAbout);
        
        myBar.add(myFileOpt);
        myBar.add(myFormatOpt);
        myBar.add(myHelpOpt);
        
        addListeners();
    }

    /**
     * this mentod adds action listeners to the menu items.
     * @custom.post action listeners are added to menu items.
     */
    private void addListeners() {
        
        //add filelistener to open and save options
        myOpenItm.addActionListener(myFileListener);
        mySaveItm.addActionListener(myFileListener);
        
        //this is the action listener for the exit button.
        myExitItm.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                System.exit(0);
            }  
        });
        
        //action listener for word wrap
        myWrapBox.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                if (myWrapBox.isSelected()) {
                    myDoc.setWrap();
                } else {
                    myDoc.unsetWrap();
                }
            }
        });
        
        //action listener for font selection
        myType.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                final GraphicsEnvironment ge = 
                                GraphicsEnvironment.getLocalGraphicsEnvironment();
                //array of font options
                final String[] possibilities = 
                                ge.getAvailableFontFamilyNames();
                
                final String s = (String) JOptionPane.showInputDialog(null, 
                           "Choose Font Type:", "Customized Dialog",
                           JOptionPane.PLAIN_MESSAGE, new ImageIcon(),
                           possibilities, "Arial");
                
                //notify if a string is returned
                if ((s != null) && (s.length() > 0)) {
                    myDoc.setFontType(s);
                }
            }
        });
    }

    /**
     * accessor method for myBar.
     * @return myBar
     */
    public JMenuBar getMyMenuBar() {
        return myBar;
    }
    
    
    /**
     * The following class acts as an actionlistener for
     * file opening and saving.
     */
    public class FileListener implements ActionListener {
        
        /**
         * actionPerformed tests the most recent action against a decision tree.
         * @param theEvent the action event which has just occured.
         * @custom.post the actionevent triggers eitner a file open or save.
         */
        public void actionPerformed(final ActionEvent theEvent) {
            
            //select holds the resulting choice given when file opened
            int select = 0;
            
            //result stores the file which was selected by open.
            File result = null;
            if (myFileChooser == null) {
                myFileChooser = new JFileChooser();
            }
            
            final Object source = theEvent.getSource();
            
            //allow file filters to be selectid in windows
            myFileChooser.resetChoosableFileFilters();
            
            if (source == myOpenItm) {
                //filter the file selection to .txt
                myFileChooser.setFileFilter(
                    new FileNameExtensionFilter("Text Documents (*.txt)", ".txt"));
                select = myFileChooser.showOpenDialog(null);
            } else {
                myFileChooser.showSaveDialog(null);
            }
            
            //decision tree for file selection
            if (select == JFileChooser.APPROVE_OPTION) {
                result = myFileChooser.getSelectedFile();
            }
            if (result != null) {
                openFile(result);
            }
            
            
        }
        
        /**
         * this method opens a file into the text editor.
         * @param theFile file to be opened.
         */
        public void openFile(final File theFile) {
            // TODO Auto-generated method stub
        }



        
    }
}

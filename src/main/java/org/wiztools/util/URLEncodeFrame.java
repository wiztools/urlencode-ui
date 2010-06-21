package org.wiztools.util;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

class URLEncodeFrame extends JFrame implements ClipboardOwner {

    private static final int JTA_WIDTH = 40;
    private static final int JTA_HEIGHT = 10;

    private static final int COMP_SPACING = 5;

    private JTextArea jta_in = new JTextArea(JTA_HEIGHT, JTA_WIDTH);
    private JTextArea jta_out = new JTextArea(JTA_HEIGHT, JTA_WIDTH);

    private JButton jb_encode = new JButton("Encode & Copy");
    private JButton jb_decode = new JButton("Decode");

    private final URLEncodeFrame me;

    private JPanel getInputPanel(){
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout(COMP_SPACING, COMP_SPACING));

        JLabel jl = new JLabel("Enter URL content: ");
        jl.setLabelFor(jta_in);
        jp.add(jl, BorderLayout.NORTH);

        JScrollPane jsp = new JScrollPane(jta_in);
        jp.add(jsp, BorderLayout.CENTER);

        JPanel jp_south = new JPanel();
        jp_south.setLayout(new FlowLayout(FlowLayout.CENTER));

        jp_south.add(jb_encode);
        jp_south.add(jb_decode);

        jp.add(jp_south, BorderLayout.SOUTH);
        
        return jp;
    }

    private JPanel getOutputPanel(){
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout(COMP_SPACING, COMP_SPACING));

        JLabel jl = new JLabel("Output: ");
        jp.add(jl, BorderLayout.NORTH);

        JScrollPane jsp = new JScrollPane(jta_out);
        jp.add(jsp, BorderLayout.CENTER);

        return jp;
    }

    URLEncodeFrame(final String title) {
        super(title);

        me = this;

        // init:
        jb_encode.setMnemonic('e');
        jb_decode.setMnemonic('d');
        jta_out.setEditable(false);

        jb_encode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inTxt = jta_in.getText();
                try{
                    String outTxt = URLEncoder.encode(inTxt, "UTF-8");
                    jta_out.setText(outTxt);

                    // Now copy to clipboard:
                    Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(outTxt), me);
                }
                catch(UnsupportedEncodingException ex){
                    assert true: "Will not come here.";
                }
            }
        });

        jb_decode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inTxt = jta_in.getText();
                try{
                    String outTxt = URLDecoder.decode(inTxt, "UTF-8");
                    jta_out.setText(outTxt);
                }
                catch(UnsupportedEncodingException ex){
                    assert true: "Will not come here.";
                }
                catch(IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(me, ex.getMessage(), "Error decoding", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Arrange content:
        Container c = getContentPane();
        c.setLayout(new BorderLayout(COMP_SPACING, COMP_SPACING));

        c.add(getInputPanel(), BorderLayout.CENTER);
        c.add(getOutputPanel(), BorderLayout.SOUTH);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // do nothing!
    }
}

package org.wiztools.util;

import javax.swing.SwingUtilities;

/**
 *
 * @author subhash
 */
public class Main {

    private static final String TITLE = "WizTools.org URLEncoder";

    public static void main(String[] arg){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new URLEncodeFrame(TITLE);
            }
        });
    }
}

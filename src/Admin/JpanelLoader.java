
package Admin;

import javax.swing.JPanel;

public class JpanelLoader {
   
   public void jPanelLoader(JPanel main, JPanel panel) {
    
    main.removeAll();
    main.setLayout(new java.awt.BorderLayout());
    main.add(panel, java.awt.BorderLayout.CENTER);
    main.revalidate();
    main.repaint();
    
    }
}

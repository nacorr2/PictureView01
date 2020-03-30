package PictureView;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Run {
    public static void main(String[] args)  {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ignored) {
        } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(PictureViewGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        PictureViewGUI v = new PictureViewGUI();
        v.InitialUI();
    }
}

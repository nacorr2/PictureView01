package PictureView;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Nahum
 */

/*ESTA CLASE CONTRALA LOS ESTILOS DE LA CLASE UI_PictureView*/
public class CreateButton extends JButton{
    
    public CreateButton(Image simple, Image rolloverSimple, String accButton){
        super(new ImageIcon(simple));
        setActionCommand(accButton);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        //setMargin(new Insets(0, 0, 0, 0));
        setRolloverIcon(new ImageIcon(rolloverSimple));
    }
}

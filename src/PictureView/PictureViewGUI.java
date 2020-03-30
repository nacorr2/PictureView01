package PictureView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Nahum
 */

public class PictureViewGUI extends JFrame {
    static final Color BRIGHTGRAY = new Color(244,244,244	);
    static final Color GRAY = new Color(5,5,5);
    private Point initialClick;
    private JPanel container;
    int state_frame = 0;
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public void InitialUI() {
        setUndecorated(true);
        setSize(750, 600);
        try {
            ClassLoader load_class = Thread.currentThread().getContextClassLoader();
            Image img_select = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/upload_1.png")));
            Image img_rollover_select = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/upload_2.png")));

            Image img_delete = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/delete_1.png")));
            Image img_rollover_delete = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/delete_2.png")));

            Image img_fullScreen = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/fullScreen_1.png")));
            Image img_rollover_fullScreen = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/fullScreen_2.png")));

            Image img_exit = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/exit_1.png")));
            Image img_rollover_exit = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/exit_2.png")));

            Image img_minus = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/minus_1.png")));
            Image img_rollover_minus = ImageIO.read(Objects.requireNonNull(load_class.getResource("img/minus_2.png")));


            /* ---------- PANEL CONTAINER ---------- */
            container = new JPanel();
            container.setLayout(new BorderLayout());
            container.setBackground(GRAY);
            /*----------------------------------------*/


            /* ---------- PANEL NORTH ---------- */
            JPanel infoImg = new JPanel();
            infoImg.setLayout(new BoxLayout(infoImg,BoxLayout.X_AXIS));

            JLabel information = new JLabel();
            information.setFont(new Font("SANS_SERIF",Font.BOLD,12));
            information.setForeground(new Color(255,255,255));
            infoImg.add(information);
            infoImg.add(Box.createHorizontalGlue());
            CreateButton btnMinusFrame = new CreateButton(img_minus, img_rollover_minus, "minus");
            CreateButton btnExitFrame = new CreateButton(img_exit,img_rollover_exit, "exit");
            infoImg.add(btnMinusFrame);
            infoImg.add(btnExitFrame);

            infoImg.setOpaque(false);
            infoImg.setBackground(GRAY);
            container.add(infoImg, BorderLayout.NORTH);
            /*----------------------------------------*/


            /* ---------- PANEL CENTER ---------- */
            JPanel visorImg = new JPanel();
            visorImg.setOpaque(false);
            container.add(visorImg, BorderLayout.CENTER);
            /* ---------------------------------------- */


            /* ---------- PANEL SUR ---------- */
            JPanel barButtons = new JPanel();
            barButtons.setBackground(BRIGHTGRAY);
            CreateButton btnSelect = new CreateButton(img_select,img_rollover_select, "select");
            CreateButton btnDelete = new CreateButton(img_delete,img_rollover_delete, "delete");
            CreateButton btnFullScreen = new CreateButton(img_fullScreen,img_rollover_fullScreen, "fullScreen");
            barButtons.add(btnSelect);
            barButtons.add(btnDelete);
            barButtons.add(btnFullScreen);
            /* ---------------------------------------- */

            /* ---------- CONTROL EVENTS ---------- */
            btnSelect.addActionListener(new ControlEvents(visorImg, information));
            btnDelete.addActionListener(new ControlEvents(visorImg, information));
            btnExitFrame.addActionListener(e -> System.exit(0));
            btnFullScreen.addActionListener(e -> {
                if(state_frame == 0){
                    infoImg.setVisible(false);
                    barButtons.setVisible(false);
                    device.setFullScreenWindow(this);
                    state_frame = 1;
                }
            });

            btnMinusFrame.addActionListener(e -> this.setExtendedState(JFrame.ICONIFIED));

            container.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    initialClick = e.getPoint();
                    container.getComponentAt(initialClick);
                }
            });

            container.addMouseMotionListener(
                    new MouseMotionAdapter() {
                        @Override
                        public void mouseDragged(MouseEvent e
                        ) {
                            // get location of Window
                            int thisX = getLocation().x;
                            int thisY = getLocation().y;

                            // Determine how much the mouse moved since the initial click
                            int xMoved = e.getX() - initialClick.x;
                            int yMoved = e.getY() - initialClick.y;

                            // Move window to this position
                            int X = thisX + xMoved;
                            int Y = thisY + yMoved;
                            setLocation(X, Y);
                        }
                    }
            );

            container.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    infoImg.setVisible(true);
                    barButtons.setVisible(true);
                    device.setFullScreenWindow(null);
                    state_frame = 0;
                }
            });
            /*-------------------------------------------------------------*/

            container.add(barButtons, BorderLayout.PAGE_END);
            getContentPane().add(container);
            setLocationRelativeTo(null);
            setVisible(true);

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
                

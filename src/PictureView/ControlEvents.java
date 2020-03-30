/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PictureView;

        import javax.imageio.ImageIO;
        import javax.swing.*;
        import javax.swing.filechooser.FileNameExtensionFilter;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.image.BufferedImage;
        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.logging.Level;
        import java.util.logging.Logger;

/**
 *
 * @author Nahum
 */

/*ESTA CLASE CONTROLA LOS EVENTOS DE LA CLASE UI_PictureView*/
public class ControlEvents implements ActionListener{
    private JPanel boxImg;
    JLabel boxInfo;
    static ArrayList<CreateImage> list_img = null;
    static int state_box = 0;


    public ControlEvents(JPanel box, JLabel info){
        boxImg = box;
        boxInfo = info;
        boxImg.setLayout(new BorderLayout());
        list_img = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if(evento.getActionCommand().equalsIgnoreCase("delete")){

            try{
                if(boxImg.getComponentCount() != 0){
                    int temp_index = list_img.size() - 1;
                    File path_img = new File(list_img.get(temp_index).getPath());

                    int userResponse = JOptionPane.showConfirmDialog(boxImg,"¿Desea eliminar esta imagen?","Confirme Eliminacion del archivo",JOptionPane.YES_NO_OPTION);
                    if(userResponse == 0){
                        Desktop desktop = Desktop.getDesktop();

                        if(desktop.isSupported(Desktop.Action.MOVE_TO_TRASH)){
                            boolean moverFile = desktop.moveToTrash(path_img);
                            if(moverFile && temp_index > 0){
                                boxImg.removeAll();
                                list_img.remove(temp_index);
                                JOptionPane.showMessageDialog(boxImg,"!Imagen Eliminada!","Informacion de eliminacion",JOptionPane.INFORMATION_MESSAGE);
                                boxImg.add(list_img.get(temp_index - 1).getCont_img());
                                boxImg.updateUI();
                            }else{
                                boxImg.removeAll();
                                list_img.remove(temp_index);
                                JOptionPane.showMessageDialog(boxImg,"!Imagen Eliminada!","Informacion de eliminacion",JOptionPane.INFORMATION_MESSAGE);
                                boxImg.updateUI();
                            }
                        }
                    }
                }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        if(evento.getActionCommand().equalsIgnoreCase("select")){
            String UserProfile = System.getenv().get("USERPROFILE");
            File DirectoryCurrent = new File(UserProfile+"\\Pictures");
            JFileChooser selector = new JFileChooser(DirectoryCurrent);
            //Restringir al usuario para seleccionar archivos cualquier archivo.
            selector.setAcceptAllFileFilterUsed(false);
            //filtro para archivos
            String descripcion = "PNG JPG GIF Images";
            FileNameExtensionFilter filter = new FileNameExtensionFilter(descripcion,"png","jpg","gif");
            selector.setFileFilter(filter);
            //agregar titulo a la ventana de seleccion de archivos
            selector.setDialogTitle("Seleccione una Imagen:");
            //mostramos una ventana de seleccion de archivos
            int seleccion = selector.showOpenDialog(null);
            //verificamos si el usuario a seleccionado o no una imgen
            if(seleccion == JFileChooser.APPROVE_OPTION){
                try {
                    File img_file = selector.getSelectedFile();
                    String path_img = img_file.getPath();
                    /*------ INFORMATION OF IMG ----------*/
                    String nom_img = img_file.getName();
                    //boolean canRead = img_file.canRead();
                    //boolean canWrite = img_file.canWrite();
                    //Date lastModified =new Date(img_file.lastModified());
                    /*int length ;
                    if(img_file.length() == 0){
                        length = 0;
                    }else{
                        length = (int) (img_file.length()/1024);
                    }
                     */
                    /*------------------------------------*/
                    BufferedImage buff_img = ImageIO.read(img_file);
                    ImageIcon form_img = new ImageIcon(buff_img);
                    ImageIcon scalar_img = new ImageIcon(form_img.getImage().getScaledInstance(boxImg.getWidth(), boxImg.getHeight(), Image.SCALE_DEFAULT));

                    if(state_box == 0){
                        boxInfo.setText(" ");
                        boxImg.removeAll(); // PRIMERO LIMPIAMOS EL MARCO DONDE IRA LA IMAGEN
                        CreateImage img = new CreateImage(path_img, scalar_img); // SE CREA LA IMAGEN
                        boxImg.add(img.getCont_img(), BorderLayout.CENTER); // SE AGREGA LA IMAGEN EN EL MARCO.
                        boxInfo.setText(nom_img);
                        boxImg.updateUI(); // POR ULTIMO ACTUALIZAMOS EL MARCO CON LA NUEVA IMAGEN
                        boxInfo.updateUI();
                        list_img.add(img);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateButton.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

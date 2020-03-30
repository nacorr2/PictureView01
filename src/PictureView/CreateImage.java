package PictureView;

import javax.swing.*;

public class CreateImage {
    private String path;
    private JLabel cont_img;

    public CreateImage(String path, ImageIcon ico){
        this.path = path;
        cont_img = new JLabel(ico);
    }

    public String getPath() {
        return path;
    }

    public JLabel getCont_img() {
        return cont_img;
    }
}

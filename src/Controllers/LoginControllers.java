package Controllers;

import Models.Usuarios;
import Models.UsuariosDao;
import Views.FrmLogin;
import Views.PanelAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginControllers implements ActionListener{
    private Usuarios us;
    private UsuariosDao usdao;
    private FrmLogin views;

    public LoginControllers(Usuarios us, UsuariosDao usdao, FrmLogin views) {
        this.us = us;
        this.usdao = usdao;
        this.views = views;
        this.views.btnlogin.addActionListener(this);
        this.views.btncancelar.addActionListener(this);
        this.views.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnlogin) {
            if (views.txtuser.getText().equals("") || String.valueOf(views.txtclave.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }else{
                String usuario = views.txtuser.getText();
                String clave = String.valueOf(views.txtclave.getPassword());
                us = usdao.login(usuario, clave);
                if (us.getUser() != null) {
                    PanelAdmin admin = new PanelAdmin();
                    admin.setVisible(true);
                    this.views.dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Credenciales invalidas");
                }
            }
        }else{
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea salir?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pregunta == 0) {
                System.exit(0);
            }
        }
    }
    
    
    
}

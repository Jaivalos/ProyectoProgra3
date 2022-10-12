
package Controllers;

import Models.Tables;
import Models.Usuarios;
import Models.UsuariosDao;
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class UsuariosController implements ActionListener, MouseListener{

    private Usuarios us;
    private UsuariosDao usDao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public UsuariosController(Usuarios us, UsuariosDao usDao, PanelAdmin views) {
        this.us = us;
        this.usDao = usDao;
        this.views = views;
        this.views.btnRegistrarUser.addActionListener(this);
        this.views.btnModificarUser.addActionListener(this);
        this.views.btnNuevoUser.addActionListener(this);
        this.views.TableUser.addMouseListener(this);
        this.views.JMenuEliminarUser.addActionListener(this);
        this.views.JMenuReingresarUser.addActionListener(this);
        this.views.JlabelUser.addMouseListener(this);
        listarUsuarios();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarUser) {
            if (views.txtUser.getText().equals("") || views.txtNombreUser.getText().equals("") || String.valueOf(views.txtClaveUser.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }else{
                us.setUser(views.txtUser.getText());
                us.setNombre(views.txtNombreUser.getText());
                us.setClave(String.valueOf(views.txtClaveUser.getPassword()));
                us.setCaja(views.cbxCajaUser.getSelectedItem().toString());
                us.setRol(views.cbxRolUser.getSelectedItem().toString());
                if (usDao.registrar(us)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario registrado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al registrar el usuario");
                }
            }
        }else if (e.getSource() == views.btnModificarUser) {
            if (views.txtUser.getText().equals("") || views.txtNombreUser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }else{
                us.setUser(views.txtUser.getText());
                us.setNombre(views.txtNombreUser.getText());
                us.setCaja(views.cbxCajaUser.getSelectedItem().toString());
                us.setRol(views.cbxRolUser.getSelectedItem().toString());
                us.setId(Integer.parseInt(views.txtIdUser.getText()));
                if (usDao.modificar(us)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario modificado con exito");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al modificar el usuario");
                }
            }
        }else if (e.getSource() == views.JMenuEliminarUser) {
            if (views.txtIdUser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            }else{
                int id = Integer.parseInt(views.txtIdUser.getText());
                if (usDao.accion("inactivo", id)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario eliminado");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al eliminar usuario");
                }
            }
        }else if (e.getSource() == views.JMenuReingresarUser) {
            if (views.txtIdUser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            }else{
                int id = Integer.parseInt(views.txtIdUser.getText());
                if (usDao.accion("Activo", id)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario reingresado");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al reingresar usuario");
                }
            }
        }else{
            limpiar();
        }
    }
    
    public void listarUsuarios(){
        Tables color = new Tables();
        views.TableUser.setDefaultRenderer(views.TableUser.getColumnClass(0), color);
        List<Usuarios> lista = usDao.ListaUsuarios();
        modelo = (DefaultTableModel) views.TableUser.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getUser();
            ob[2] = lista.get(i).getNombre();
            ob[3] = lista.get(i).getCaja();
            ob[4] = lista.get(i).getRol();
            ob[5] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.TableUser.setModel(modelo);
        JTableHeader header = views.TableUser.getTableHeader();
        header.setOpaque(false);
        header.setBackground(Color.black);
        header.setForeground(Color.white);
    }
    
    public void limpiarTable(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.TableUser) {
            int fila = views.TableUser.rowAtPoint(e.getPoint());
            views.txtIdUser.setText(views.TableUser.getValueAt(fila, 0).toString());
            views.txtUser.setText(views.TableUser.getValueAt(fila, 1).toString());
            views.txtNombreUser.setText(views.TableUser.getValueAt(fila, 2).toString());
            views.cbxCajaUser.setSelectedItem(views.TableUser.getValueAt(fila, 3).toString());
            views.cbxRolUser.setSelectedItem(views.TableUser.getValueAt(fila, 4).toString());
            views.txtClaveUser.setEnabled(false);
            views.btnRegistrarUser.setEnabled(false);
            views.btnNuevoUser.setEnabled(true);
        }else if (e.getSource() == views.JlabelUser) {
            views.jTabbedPane1.setSelectedIndex(3);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
   }
    
    private void limpiar(){
        views.txtUser.setText("");
        views.txtClaveUser.setText("");
        views.txtNombreUser.setText("");
        views.txtIdUser.setText("");
    }
    
}


package Controllers;

import Models.Proveedor;
import Models.ProveedorDao;
import Models.Tables;
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ProveedorControllers implements ActionListener, MouseListener, KeyListener{

    private final Proveedor prov;
    private final ProveedorDao provDao;
    private final PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();
    public ProveedorControllers(Proveedor prov, ProveedorDao provDao, PanelAdmin views) {
        this.prov = prov;
        this.provDao = provDao;
        this.views = views;
        this.views.btnRegistrarProv.addActionListener(this);
        this.views.btnNuevoProv.addActionListener(this);
        this.views.btnModificarProv.addActionListener(this);
        this.views.TableProv.addMouseListener(this);
        this.views.JlabelProveedores.addMouseListener(this);
        this.views.jMenuEliminarProv.addActionListener(this);
        this.views.jMenuReingresarProv.addActionListener(this);
        listarProv();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btnRegistrarProv) {
            if (views.txtNITProv.getText().equals("") || views.txtNombreProv.getText().equals("") || views.txtTelefonoProv.getText().equals("") || views.txtDireccionProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligatorios"); 
            }else{
                prov.setNit(views.txtNITProv.getText());
                prov.setNombre(views.txtNombreProv.getText());
                prov.setTelefono(views.txtTelefonoProv.getText());
                prov.setDireccion(views.txtDireccionProv.getText());
                if (provDao.registrar(prov)) {
                    Nuevo();
                    limpiarTable();
                    listarProv();
                    JOptionPane.showMessageDialog(null, "Proveedor Registrado"); 
                }else{
                    JOptionPane.showMessageDialog(null, "El proveedor ya existe");
                }
            }
        }else if (e.getSource() == views.btnModificarProv){
            if (views.txtNITProv.getText().equals("") || views.txtNombreProv.getText().equals("") || views.txtTelefonoProv.getText().equals("") || views.txtDireccionProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligatorios"); 
            }else{
                prov.setNit(views.txtNITProv.getText());
                prov.setNombre(views.txtNombreProv.getText());
                prov.setTelefono(views.txtTelefonoProv.getText());
                prov.setDireccion(views.txtDireccionProv.getText());
                prov.setId(Integer.parseInt(views.txtIdProv.getText()));
                if (provDao.modificar(prov)) {
                    Nuevo();
                    limpiarTable();
                    listarProv();
                    JOptionPane.showMessageDialog(null, "Proveedor Modificado"); 
                }
            }
        }else if (e.getSource() == views.jMenuEliminarProv){
            if (views.txtIdProv.getText().equals("") || views.txtIdProv.getText() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar"); 
            }else{
                int pregunta = JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    int id = Integer.parseInt(views.txtIdProv.getText());
                    if (provDao.accion("Inactivo", id)) {
                        Nuevo();
                        limpiarTable();
                        listarProv();
                        JOptionPane.showMessageDialog(null, "Proveedor Eliminado"); 
                    }
                }
            }
        }else if (e.getSource() == views.jMenuReingresarProv){
            if (views.txtIdProv.getText().equals("") || views.txtIdProv.getText() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar"); 
            }else{
                int pregunta = JOptionPane.showConfirmDialog(null, "Estas seguro de reingresar", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (pregunta == 0) {
                    int id = Integer.parseInt(views.txtIdProv.getText());
                    if (provDao.accion("Activo", id)) {
                        Nuevo();
                        limpiarTable();
                        listarProv();
                        JOptionPane.showMessageDialog(null, "Proveedor Reingresado"); 
                    }
                }
            }
        }else if (e.getSource() == views.btnNuevoProv){
            Nuevo();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.TableProv) {
            int fila = views.TableProv.rowAtPoint(e.getPoint());
            String estado = views.TableProv.getValueAt(fila, 5).toString();
            if (estado.equals("Inactivo")){
                views.jMenuEliminarProv.setVisible(false);
                views.jMenuReingresarProv.setVisible(true);
            }else{
                views.jMenuEliminarProv.setVisible(true);
                views.jMenuReingresarProv.setVisible(false);
            }
            views.txtIdProv.setText(views.TableProv.getValueAt(fila, 0).toString());
            views.txtNITProv.setText(views.TableProv.getValueAt(fila, 1).toString());
            views.txtNombreProv.setText(views.TableProv.getValueAt(fila, 2).toString());
            views.txtTelefonoProv.setText(views.TableProv.getValueAt(fila, 3).toString());
            views.txtDireccionProv.setText(views.TableProv.getValueAt(fila, 4).toString());
        }else if (e.getSource() == views.JlabelProveedores) {
            views.jTabbedPane1.setSelectedIndex(2);
            limpiarTable();
            listarProv();
        }
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    private void Nuevo() {
        views.txtIdProv.setText("");
        views.txtNITProv.setText("");
        views.txtNombreProv.setText("");
        views.txtTelefonoProv.setText("");
        views.txtDireccionProv.setText("");
    }
    public void listarProv() {
        Tables color = new Tables();
        views.TableProv.setDefaultRenderer(views.TableProv.getColumnClass(0), color);
        List<Proveedor> lista = provDao.ListarProveedor();
        modelo = (DefaultTableModel) views.TableProv.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNit();
            ob[2] = lista.get(i).getNombre();
            ob[3] = lista.get(i).getTelefono();
            ob[4] = lista.get(i).getDireccion();
            ob[5] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.TableProv.setModel(modelo);
        JTableHeader header = views.TableProv.getTableHeader();
        header.setOpaque(false);
        header.setBackground(Color.blue);
        header.setForeground(Color.white);
    }

    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        limpiarTable();
        listarProv();
    }
}

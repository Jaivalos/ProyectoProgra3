/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Models.Clientes;
import Models.ClientesDao;
import Models.Tables;
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

/**
 *
 * @author Jaivalos
 */
public class ClientesController implements ActionListener, MouseListener{
    private Clientes cl;
    private ClientesDao cldao;
    private PanelAdmin views;
    DefaultTableModel modelo = new DefaultTableModel();

    public ClientesController(Clientes cl, ClientesDao cldao, PanelAdmin views) {
        this.cl = cl;
        this.cldao = cldao;
        this.views = views;
        this.views.btnRegistrarCli.addActionListener(this);
        this.views.btnModificarCli.addActionListener(this);
        this.views.btnNuevoCli.addActionListener(this);
        this.views.TableCli.addMouseListener(this);
        this.views.jMenuEliminarCli.addActionListener(this);
        this.views.jMenuReingresarCli.addActionListener(this);
        this.views.btnModificarCli.addActionListener(this);
        this.views.JlabelClientes.addMouseListener(this);
        listarClientes();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==views.btnRegistrarCli) {
            if (views.txtNombreCli.getText().equals("") || views.txtTelefonoCli.getText().equals("") || views.txtDireccionCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligaotrios");
            }else{
                cl.setNombre(views.txtNombreCli.getText());
                cl.setTelefono(views.txtTelefonoCli.getText());
                cl.setDireccion(views.txtDireccionCli.getText());
                if (cldao.registrar(cl)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cliente registrado");
                }else{
                    JOptionPane.showMessageDialog(null, "Registro fallido");
                }
            }
        }else if (e.getSource()==views.btnModificarCli) {
            if (views.txtIdCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione la fila");
            }else{
                if (views.txtNombreCli.getText().equals("") || views.txtTelefonoCli.getText().equals("") || views.txtDireccionCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todo los campos son obligaotrios");
            }else{
                cl.setNombre(views.txtNombreCli.getText());
                cl.setTelefono(views.txtTelefonoCli.getText());
                cl.setDireccion(views.txtDireccionCli.getText());
                cl.setId(Integer.parseInt(views.txtIdCli.getText()));
                if (cldao.modificar(cl)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cliente Modificado");
                }else{
                    JOptionPane.showMessageDialog(null, "Modificación fallida");
                }
            }
            }
        }else if (e.getSource() == views.jMenuEliminarCli ) {
            if (views.txtIdCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            }else{
                int id = Integer.parseInt(views.txtIdCli.getText());
                if (cldao.accion("inactivo", id)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cliente eliminado");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al eliminar cliente");
                }
            }
        }else if (e.getSource() == views.jMenuReingresarCli ) {
            if (views.txtIdCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            }else{
                int id = Integer.parseInt(views.txtIdCli.getText());
                if (cldao.accion("Activo", id)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cliente Reingresado");
                }else{
                    JOptionPane.showMessageDialog(null, "Error al reingresar cliente");
                }
            }
        }else {
            limpiar();
        }
    }
    
        public void listarClientes(){
        Tables color = new Tables();
        views.TableCli.setDefaultRenderer(views.TableCli.getColumnClass(0), color);
        List<Clientes> lista = cldao.ListaClientes();
        modelo = (DefaultTableModel) views.TableCli.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getNombre();
            ob[2] = lista.get(i).getTelefono();
            ob[3] = lista.get(i).getDireccion();
            ob[4] = lista.get(i).getEstado();
            modelo.addRow(ob);
        }
        views.TableCli.setModel(modelo);
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
        if (e.getSource() == views.TableCli) {
            int fila = views.TableCli.rowAtPoint(e.getPoint());
            views.txtIdCli.setText(views.TableCli.getValueAt(fila, 0).toString());
            views.txtNombreCli.setText(views.TableCli.getValueAt(fila, 1).toString());
            views.txtTelefonoCli.setText(views.TableCli.getValueAt(fila, 2).toString());
            views.txtDireccionCli.setText(views.TableCli.getValueAt(fila, 3).toString());
        }else if (e.getSource() == views.JlabelClientes) {
            views.jTabbedPane1.setSelectedIndex(1);
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
        views.txtNombreCli.setText("");
        views.txtTelefonoCli.setText("");
        views.txtDireccionCli.setText("");
        views.txtIdCli.setText("");
    }
    
}

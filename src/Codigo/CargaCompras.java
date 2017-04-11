/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codigo;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import vista.Entrada_Informacion_Excel;
import vista.Salida_informacion_excel;
import vista.Salida_informacion_terceros;

/**
 *
 * @author USER
 */
public class CargaCompras {

    private static DefaultTableModel modelo;
    private TableRowSorter trsFiltro;
    public String DireccionArchivo;
    private final JFileChooser FileChooser = new JFileChooser();
    public Salida_informacion_excel SIE = new Salida_informacion_excel();
    public Salida_informacion_terceros SIT = new Salida_informacion_terceros();
    DefaultTableModel modelo_base_iva_excento = (DefaultTableModel) SIE.getJT_Exportacion().getModel();
    DefaultTableModel modelo_terceros = (DefaultTableModel) SIT.getJT_Terceros().getModel();
    public String[] ciudades;
    Entrada_Informacion_Excel EIE = new Entrada_Informacion_Excel();

    public void cargaModeloCompras() {
        for (int i = 0; i < EIE.getJT_Importacion().getRowCount(); i++) {
            boolean cuentaIVA;
            cuentaIVA = !(EIE.getJT_Importacion().getValueAt(i, 14).toString().isEmpty()); //System.out.println("El valor del iva en la fila " + i + " es: " + JT_Importacion.getValueAt(i, 14) + " el valor de 'cuentaIVA' es " + cuentaIVA + " y ejecutara 'cargaBase_iva'");
            //System.out.println("El valor del iva en la fila " + i + " es: " + JT_Importacion.getValueAt(i, 14) + " el valor de 'cuentaIVA' es " + cuentaIVA + " y ejecutara 'cargaExcento'");
            if (cuentaIVA == true) {
                cargaBase_iva(i);
            } else {
                cargaExcento(i);
            }
            cargaSalida(i);
            cargaTercero(i);
        }
        System.out.println("La carga de Archivos ha sido completada");
        SIE.getJT_Exportacion().setModel(modelo_base_iva_excento);
        SIE.setVisible(true);
        SIT.getJT_Terceros().setModel(modelo_terceros);
        SIT.setVisible(true);
    }

    private void cargaBase_iva(int i) {
        String nombre_empresa = EIE.getTXT_nombreEmpresa().getText();
        String nit_empresa = EIE.getTXT_nitEmpresa().getText();
        String[] base = new String[19];
        String[] IVA = new String[19];
        //------------------------------------- CARGA LA BASE PRIMERO -------------------------------------------

        base[0] = EIE.getJT_Importacion().getValueAt(i, 3).toString();
        base[1] = nombre_empresa;
        base[2] = "FC";
        base[3] = EIE.getJT_Importacion().getValueAt(i, 0).toString();
        base[4] = EIE.getJT_Importacion().getValueAt(i, 4).toString();
        base[5] = EIE.getJT_Importacion().getValueAt(i, 2).toString();
        base[6] = "FACTURA DE COMPRA";
        base[7] = EIE.getJT_Importacion().getValueAt(i, 10).toString();
        base[8] = "";
        base[9] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        base[10] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        base[11] = EIE.getJT_Importacion().getValueAt(i, 11).toString();
        base[12] = "";
        base[13] = EIE.getJT_Importacion().getValueAt(i, 3).toString();
        base[14] = "";
        base[15] = "";
        base[16] = "";
        base[17] = "0";
        base[18] = nit_empresa;
        modelo_base_iva_excento.addRow(base);
        //--------------------------------carga IVA-------------------------------------------------------------------------------------------------
        IVA[0] = EIE.getJT_Importacion().getValueAt(i, 3).toString();
        IVA[1] = nombre_empresa;
        IVA[2] = "FC";
        IVA[3] = EIE.getJT_Importacion().getValueAt(i, 0).toString();
        IVA[4] = EIE.getJT_Importacion().getValueAt(i, 4).toString();
        IVA[5] = EIE.getJT_Importacion().getValueAt(i, 13).toString();
        IVA[6] = "FACTURA DE COMPRA";
        IVA[7] = "VALOR DE IVA";
        IVA[8] = "";
        IVA[9] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        IVA[10] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        IVA[11] = EIE.getJT_Importacion().getValueAt(i, 14).toString();
        IVA[12] = "";
        IVA[13] = EIE.getJT_Importacion().getValueAt(i, 3).toString();
        IVA[14] = "";
        IVA[15] = "";
        IVA[16] = "";
        IVA[17] = "0";
        IVA[18] = nit_empresa;
        //--------------------------------------asignacion al modelo---------------------------------
        modelo_base_iva_excento.addRow(IVA);
    }

    private void cargaTercero(int i) {
        String nit_empresa = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        boolean Existe = existeEmpresa(nit_empresa);
        String[] DatosTerceros = new String[19];
        String ciudad = "Bogota D.C.";
        if (!Existe) {
            if (EIE.getJT_Importacion().getValueAt(i, 7).toString().isEmpty()) {
                DatosTerceros[0] = "CC";
                DatosTerceros[9] = "Persona Natural Regimen Comun";
                DatosTerceros[12] = "Casa";
            } else {
                DatosTerceros[0] = "NIT";
                DatosTerceros[9] = "Persona Juridica";
                DatosTerceros[12] = "Empresa/Oficina";
            }
            DatosTerceros[1] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
            DatosTerceros[2] = ciudad;
            DatosTerceros[3] = EIE.getJT_Importacion().getValueAt(i, 5).toString();
            DatosTerceros[7] = "Proveedor, Vendedor";
            DatosTerceros[8] = "-1";
            DatosTerceros[10] = "0";
            DatosTerceros[11] = EIE.getJT_Importacion().getValueAt(i, 3).toString() + " 00:00";
            DatosTerceros[13] = "-1";
            DatosTerceros[14] = EIE.getJT_Importacion().getValueAt(i, 8).toString();
            DatosTerceros[15] = ciudad;
            DatosTerceros[16] = EIE.getJT_Importacion().getValueAt(i, 9).toString();
            DatosTerceros[17] = "Normal";
            DatosTerceros[18] = "0";

            modelo_terceros.addRow(DatosTerceros);
        }

    }

    private void cargaExcento(int i) {
        String nombre_empresa = EIE.getTXT_nombreEmpresa().getText();
        String nit_empresa = EIE.getTXT_nitEmpresa().getText();
        String[] excento = new String[19];
        excento[0] = EIE.getJT_Importacion().getValueAt(i, 3).toString();
        excento[1] = nombre_empresa;
        excento[2] = "FC";
        excento[3] = EIE.getJT_Importacion().getValueAt(i, 0).toString();
        excento[4] = EIE.getJT_Importacion().getValueAt(i, 4).toString();
        excento[5] = EIE.getJT_Importacion().getValueAt(i, 2).toString();
        excento[6] = "FACTURA DE COMPRA";
        excento[7] = EIE.getJT_Importacion().getValueAt(i, 10).toString();
        excento[8] = "";
        excento[9] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        excento[10] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        excento[11] = EIE.getJT_Importacion().getValueAt(i, 16).toString();
        excento[12] = "";
        excento[13] = EIE.getJT_Importacion().getValueAt(i, 3).toString();
        excento[14] = "";
        excento[15] = "";
        excento[16] = "";
        excento[17] = "0";
        excento[18] = nit_empresa;
        modelo_base_iva_excento.addRow(excento);
    }

    private void cargaSalida(int i) {
        String nombre_empresa = EIE.getTXT_nombreEmpresa().getText();
        String nit_empresa = EIE.getTXT_nitEmpresa().getText();
        String[] salida = new String[19];
        salida[0] = EIE.getJT_Importacion().getValueAt(i, 3).toString();
        salida[1] = nombre_empresa;
        salida[2] = "FC";
        salida[3] = EIE.getJT_Importacion().getValueAt(i, 0).toString();
        salida[4] = EIE.getJT_Importacion().getValueAt(i, 4).toString();
        salida[5] = "11050501";
        salida[6] = "FACTURA DE COMPRA";
        salida[7] = "PAGO DE FACTURA";
        salida[8] = "";
        salida[9] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        salida[10] = EIE.getJT_Importacion().getValueAt(i, 6).toString();
        salida[11] = "";
        salida[12] = EIE.getJT_Importacion().getValueAt(i, 17).toString();
        salida[13] = EIE.getJT_Importacion().getValueAt(i, 3).toString();
        salida[14] = "";
        salida[15] = "";
        salida[16] = "";
        salida[17] = "0";
        salida[18] = nit_empresa;
        modelo_base_iva_excento.addRow(salida);
    }

    private boolean existeEmpresa(String nit_empresa) {
        boolean Existe = false;
        if (modelo_terceros.getRowCount() != 0) {
            //System.out.println("-----------------------------------------------inicia comparacion con el valor " + nit_empresa + "-----------------------------------------------------------------");
            for (int j = 0; j < modelo_terceros.getRowCount(); j++) {
                if (modelo_terceros.getValueAt(j, 1).toString().matches(nit_empresa)) {
                    Existe = true;
                    //System.out.println("el nit de la empresa es: " + nit_empresa + " el valor a comparar es: " + modelo_terceros.getValueAt(j, 1) + " // existencia de la empresa = " + Existe);
                    j = modelo_terceros.getRowCount() + 1;
                } else {
                    Existe = false;
                    //System.out.println("el nit de la empresa es: " + nit_empresa + " el valor a comparar es: " + modelo_terceros.getValueAt(j, 1) + " // existencia de la empresa = " + Existe);
                }
            }
        }
        return Existe;
    }
}

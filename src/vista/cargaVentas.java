/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class cargaVentas {

    CargaCompras CC = new CargaCompras();
    Entrada_Informacion_Excel EIE = new Entrada_Informacion_Excel();
    Salida_informacion_excel SIE = new Salida_informacion_excel();
    Salida_informacion_terceros SIT = new Salida_informacion_terceros();
    DefaultTableModel modeloExportacion = (DefaultTableModel) SIE.getJT_Exportacion().getModel();
    DefaultTableModel modeloTerceros = (DefaultTableModel) SIT.getJT_Terceros().getModel();

    public void cargaModelo() {
        for (int i = 0; i < EIE.getJT_Importacion().getRowCount(); i++) {
            agregabase(i);

        }

    }
}


import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author WarisArjuna
 */
public class HelperSinkronisasi {
     // --- Method untuk sinkronisasi Spinner ke JCalendar ---
    public static void sinkronSpinnerKeKalender(JSpinner spnTahun, JComboBox cbBulan, com.toedter.calendar.JCalendar jCalendar) {
        spnTahun.addChangeListener(e -> {
            int tahun = (int) spnTahun.getValue();

            // === Validasi batas atas dan bawah ===
            if (tahun == 2100) {
                JOptionPane.showMessageDialog(null,
                        "Tahun maksimum yang diperbolehkan adalah 2100!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
            } else if (tahun == 1900) {
                JOptionPane.showMessageDialog(null,
                        "Tahun minimum yang diperbolehkan adalah 1900!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
            }

            // === Sinkronisasi kalender ===
            int bulan = cbBulan.getSelectedIndex();
            if (bulan >= 0) {
                Calendar cal = jCalendar.getCalendar();
                cal.set(Calendar.YEAR, tahun);
                cal.set(Calendar.MONTH, bulan);
                jCalendar.setCalendar(cal);
            }
        });
    }

    // --- Method untuk sinkronisasi JCalendar ke Spinner & ComboBox ---
    public static void sinkronKalenderKeSpinner(com.toedter.calendar.JCalendar jCalendar, JSpinner spnTahun, JComboBox cbBulan) {
        jCalendar.addPropertyChangeListener("calendar", evt -> {
            Calendar cal = jCalendar.getCalendar();
            int tahun = cal.get(Calendar.YEAR);
            int bulan = cal.get(Calendar.MONTH);

            spnTahun.setValue(tahun);
            cbBulan.setSelectedIndex(bulan);
        });
    }

    // --- Method untuk sinkronisasi ComboBox ke JCalendar ---
    public static void sinkronComboBoxKeKalender(JComboBox cbBulan, com.toedter.calendar.JCalendar jCalendar) {
        cbBulan.addActionListener(e -> {
            int bulan = cbBulan.getSelectedIndex();
            if (bulan >= 0) {
                Calendar cal = jCalendar.getCalendar();
                cal.set(Calendar.MONTH, bulan);
                jCalendar.setCalendar(cal);
            }
        });
    }

    // --- Method untuk membuat Spinner Model Tahun ---
    public static void setModelSpinnerTahun(JSpinner spnTahun) {
        int tahunSekarang = java.time.LocalDate.now().getYear();
        SpinnerNumberModel modelTahun = new SpinnerNumberModel(
                tahunSekarang, // nilai awal
                1900,          // nilai minimum
                2100,          // nilai maksimum
                1              // langkah
        );
        spnTahun.setModel(modelTahun);
    }
}   

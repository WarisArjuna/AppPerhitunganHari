import javax.swing.*;
import com.toedter.calendar.JCalendar;
import java.awt.*;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class DialogSelisihHari extends JDialog {

    private JCalendar calTanggal1;
    private JCalendar calTanggal2;
    private JLabel lblHasilSelisih;
    private JButton btnHitung;
    private JButton btnReset;
    private JButton btnToday;
    private JButton btnTutup;
            java.util.Date now = new java.util.Date();

    public DialogSelisihHari(Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        
        java.util.Date now = new java.util.Date();

        // === Pengaturan dasar jendela ===
        setTitle("Hitung Selisih Dua Tanggal");
        setSize(580, 470);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));

        // === Panel utama untuk kalender ===
        JPanel panelMain = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        panelMain.setBackground(Color.WHITE);

        // Label penanda
        JLabel lblTgl1 = new JLabel("Tanggal 1");
        JLabel lblTgl2 = new JLabel("Tanggal 2");
        lblTgl1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTgl2.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Kalender
        calTanggal1 = new JCalendar();
        calTanggal2 = new JCalendar();

        // Warna agar tanggal terlihat
        for (JCalendar cal : new JCalendar[]{calTanggal1, calTanggal2}) {
            cal.setBackground(Color.WHITE);
            cal.getDayChooser().getDayPanel().setBackground(Color.WHITE);
            cal.getDayChooser().getDayPanel().setForeground(Color.BLACK);
            cal.getDayChooser().setDecorationBackgroundColor(Color.WHITE);
            cal.getMonthChooser().setBackground(Color.WHITE);
            cal.getYearChooser().setBackground(Color.WHITE);
        }

        // Ukuran kalender
        calTanggal1.setPreferredSize(new Dimension(250, 200));
        calTanggal2.setPreferredSize(new Dimension(250, 200));

        // Bungkus kalender
        JPanel panelCal1 = new JPanel(new BorderLayout());
        panelCal1.setBackground(Color.WHITE);
        panelCal1.add(lblTgl1, BorderLayout.NORTH);
        panelCal1.add(calTanggal1, BorderLayout.CENTER);

        JPanel panelCal2 = new JPanel(new BorderLayout());
        panelCal2.setBackground(Color.WHITE);
        panelCal2.add(lblTgl2, BorderLayout.NORTH);
        panelCal2.add(calTanggal2, BorderLayout.CENTER);

        // Tambahkan ke panel utama
        panelMain.add(panelCal1);
        panelMain.add(panelCal2);

        // === Panel bawah: tombol & hasil ===
        JPanel panelBottom = new JPanel(new GridBagLayout());
        panelBottom.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tombol utama
        btnHitung = new JButton("Hitung Selisih");
        btnReset = new JButton("Reset");
        btnToday = new JButton("Hari Ini");
        btnTutup = new JButton("Tutup");

        // Styling tombol
        styleButton(btnHitung, new Color(51, 153, 255), Color.WHITE);
        styleButton(btnReset, new Color(255, 204, 0), Color.BLACK);
        styleButton(btnToday, new Color(153, 255, 153), Color.BLACK);
        styleButton(btnTutup, new Color(230, 230, 230), Color.BLACK);

        // Label hasil
        lblHasilSelisih = new JLabel(" ");
        lblHasilSelisih.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblHasilSelisih.setForeground(new Color(0, 102, 51));

        // Penempatan tombol di grid
        gbc.gridx = 0; gbc.gridy = 0;
        panelBottom.add(btnHitung, gbc);
        gbc.gridx = 1;
        panelBottom.add(btnReset, gbc);
        gbc.gridx = 2;
        panelBottom.add(btnToday, gbc);
        gbc.gridx = 3;
        panelBottom.add(btnTutup, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4;
        panelBottom.add(lblHasilSelisih, gbc);

        // Tambahkan semua panel ke dialog
        add(panelMain, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // === Aksi tombol ===
        btnHitung.addActionListener(e -> hitungSelisih());
        btnReset.addActionListener(e -> resetKalender());
        btnToday.addActionListener(e -> setHariIni());
        btnTutup.addActionListener(e -> dispose());
    }

    // Method hitung selisih
    private void hitungSelisih() {
        java.util.Date tgl1 = calTanggal1.getDate();
        java.util.Date tgl2 = calTanggal2.getDate();

        if (tgl1 == null || tgl2 == null) {
            JOptionPane.showMessageDialog(this,
                    "Pilih kedua tanggal terlebih dahulu!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate tanggal1 = tgl1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate tanggal2 = tgl2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long selisih = ChronoUnit.DAYS.between(tanggal1, tanggal2);

        lblHasilSelisih.setText("Selisih antara kedua tanggal adalah " + Math.abs(selisih) + " hari");
    }

    // Method reset kalender
    private void resetKalender() {
        calTanggal1.setDate(now);
        calTanggal2.setDate(now);
        lblHasilSelisih.setText(" ");
    }

    // Method set ke tanggal hari ini
    private void setHariIni() {
        calTanggal1.setDate(now);
        calTanggal2.setDate(now);
    }

    // Styling tombol agar konsisten
    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efek hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(bg.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }
}

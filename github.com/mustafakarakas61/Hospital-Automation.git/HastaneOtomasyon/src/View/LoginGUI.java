package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.BasHekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doctorTc;
	private JPasswordField fld_doctorPass;
	private JPasswordField fld_hastaPass;
	private DBConnection conn = new DBConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 385);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("medicine.png")));
		lbl_logo.setBounds(197, 11, 56, 50);
		w_pane.add(lbl_logo);

		JLabel lblNewLabel = new JLabel("Hastane Y\u00F6netim Sistemine Ho\u015Fgeldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lblNewLabel.setBounds(87, 66, 276, 27);
		w_pane.add(lblNewLabel);

		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 134, 424, 206);
		w_pane.add(w_tabpane);

		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta Giriþi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);

		JLabel lblTcKimlikNo = new JLabel("T.C. Numaran\u0131z:");
		lblTcKimlikNo.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lblTcKimlikNo.setBounds(36, 24, 133, 27);
		w_hastaLogin.add(lblTcKimlikNo);

		JLabel lblifre = new JLabel("\u015Eifre:");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lblifre.setBounds(36, 63, 133, 27);
		w_hastaLogin.add(lblifre);

		fld_hastaTc = new JTextField();
		fld_hastaTc.setToolTipText("11 haneli T.C. kimlik numaran\u0131z.");
		fld_hastaTc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 17));
		fld_hastaTc.setBounds(168, 24, 218, 27);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);

		JButton btn_register = new JButton("Kay\u0131t Ol");
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_register.setBounds(36, 113, 165, 54);
		w_hastaLogin.add(btn_register);

		JButton btn_hastaLogin = new JButton("Giri\u015F Yap");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					boolean key = true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();// girilen deðerin var olup olmadýðý
						ResultSet rs = st.executeQuery("SELECT * FROM user");// sorgu

						while (rs.next()) {
							if (fld_hastaTc.getText().equals(rs.getString("tcno"))
									&& fld_hastaPass.getText().equals(rs.getString("password"))) {
								if (rs.getString("type").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword("password");
									hasta.setTcno(rs.getString("tcno"));// rs içindeki verileri bhekim setin içine
																		// atýyoruz
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);// BasHekimGUI içine bhekimi atýyoruz
									hGUI.setVisible(true);// BasHekimGUI penceresini açtýrýyoruz
									dispose();// Anasayfanýn pencersini öldürüyoruz
									key = false;
								}

							}

						}

					} catch (SQLException e1) {

						e1.printStackTrace();
					}
					if (key) {
						Helper.showMsg("Hasta bulunamadý. Lütfen Kayýt olunuz.");

					}
				}

			}
		});
		btn_hastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_hastaLogin.setBounds(221, 113, 165, 54);
		w_hastaLogin.add(btn_hastaLogin);

		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(168, 62, 218, 27);
		w_hastaLogin.add(fld_hastaPass);

		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Giriþi", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);

		JLabel lblTcKimlikNo_1 = new JLabel("T.C. Numaran\u0131z:");
		lblTcKimlikNo_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lblTcKimlikNo_1.setBounds(38, 24, 133, 27);
		w_doctorLogin.add(lblTcKimlikNo_1);

		fld_doctorTc = new JTextField();
		fld_doctorTc.setToolTipText("11 haneli T.C. kimlik numaran\u0131z.");
		fld_doctorTc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 17));
		fld_doctorTc.setColumns(10);
		fld_doctorTc.setBounds(170, 24, 218, 27);
		w_doctorLogin.add(fld_doctorTc);

		JLabel lblifre_1 = new JLabel("\u015Eifre:");
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 15));
		lblifre_1.setBounds(38, 63, 133, 27);
		w_doctorLogin.add(lblifre_1);

		JButton btn_doctorLogin = new JButton("Giri\u015F Yap");
		btn_doctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doctorTc.getText().length() == 0 || fld_doctorPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {

					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();// girilen deðerin var olup olmadýðý
						ResultSet rs = st.executeQuery("SELECT * FROM user");// sorgu
						while (rs.next()) {
							if (fld_doctorTc.getText().equals(rs.getString("tcno"))
									&& fld_doctorPass.getText().equals(rs.getString("password"))) {
								if (rs.getString("type").equals("bashekim")) {
									BasHekim bhekim = new BasHekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword("password");
									bhekim.setTcno(rs.getString("tcno"));// rs içindeki verileri bhekim setin içine
																			// atýyoruz
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									BasHekimGUI bGUI = new BasHekimGUI(bhekim);// BasHekimGUI içine bhekimi atýyoruz
									bGUI.setVisible(true);// BasHekimGUI penceresini açtýrýyoruz
									dispose();// Anasayfanýn pencersini öldürüyoruz
								}
								if (rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));// rs içindeki verileri bhekim setin içine
																			// atýyoruz
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}

						}

					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}

			}
		});
		btn_doctorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 16));
		btn_doctorLogin.setBounds(38, 113, 350, 54);
		w_doctorLogin.add(btn_doctorLogin);

		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(170, 62, 218, 27);
		w_doctorLogin.add(fld_doctorPass);
	}
}

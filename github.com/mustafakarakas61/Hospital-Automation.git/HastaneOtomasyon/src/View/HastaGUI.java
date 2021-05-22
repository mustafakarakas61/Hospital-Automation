package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.DBConnection;
import Helper.Helper;
import Helper.Item;
import Model.Appointment;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	// �nce hasta nesnemizi olu�tural�m
	private static Hasta hasta = new Hasta();
	private Clinic clinic = new Clinic();// klinikleri listelemek i�in
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private Whour whour = new Whour();// �al��ma saatlerini olu�turmak i�in nesne �rettik
	private DefaultTableModel whourModel;
	private Object[] whourData = null;// model olu�turdk
	private int selectDoctorID = 0;// bir doktor se�tik, randevu saatlerini listeledik, ama o esnada ba�ka doktoru
									// se�ip, ilk doktorun randevu saatinden yararlanmamas� i�in bunu tan�mlad�k
	private String selectDoctorName = null;// bir doktor se�tik, ..............
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	private JPopupMenu rMenu;
	private Appointment appoint = new Appointment();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);// gui i�ine hasta ekle
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public HastaGUI(Hasta hasta) throws SQLException {// cons. i�ine Hasta hasta ekle

		// doctor modeli eklioz
		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];// Object int String falan hepsini kapsad��� i�in veri t�r� Object
											// kulland�k
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];// Object int String falan hepsini kapsad��� i�in veri t�r� Object
											// kulland�k
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];

		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];// Object int String falan hepsini kapsad��� i�in veri t�r� Object
											// kulland�k
		colAppoint[0] = "ID";
		colAppoint[1] = "Doktor";
		colAppoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];

		for (int i = 0; i < appoint.getHastaList(hasta.getId()).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta.getId()).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

		setResizable(false);
		setTitle("Hastane Y�netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 380);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel label = new JLabel("Ho�geldiniz say�n " + hasta.getName());// hasta.getName ekle
		label.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		label.setBounds(10, 11, 196, 21);
		w_pane.add(label);

		JButton button = new JButton("��k�� Yap");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();

			}
		});
		button.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		button.setBounds(449, 11, 85, 21);
		w_pane.add(button);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 74, 524, 266);
		w_pane.add(w_tab);

		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tab.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 24, 180, 203);
		w_appointment.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);// table i�ine doktor modeli atmay� unutma
		w_scrollDoctor.setViewportView(table_doctor);
		table_doctor.getColumnModel().getColumn(0).setPreferredWidth(5);// column geni�li�i

		JLabel label_1 = new JLabel("Doktor Listesi");
		label_1.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label_1.setBounds(12, 8, 83, 14);
		w_appointment.add(label_1);

		JLabel label_2 = new JLabel("Poliklinik Ad\u0131");
		label_2.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label_2.setBounds(203, 8, 68, 14);
		w_appointment.add(label_2);

		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(200, 24, 120, 22);
		// burada klinikleri listelicez

		select_clinic.addItem("--Poliklinik Se�--");
		for (int i = 0; i < clinic.getList().size(); i++) {
			
			
			
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));// id
																		// ile
																												// ismi
																												// ald�k
																												// klinigin
		}
		// dinlemek laz�m se�ili olan klini�in verilerini listelemek i�in
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					// yukar�da doktor model id ve adsoyad� ald�k, burda listelicez
					// �nce tabloyu s�f�rlayal�m
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++) {
							if(clinic.getClinicDoctorList(item.getKey()).get(i).getId()>0) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} // se�enek Poliklinik se�e geri d�nd���nde doktor isimleri kaybolmas� i�in
				else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}

			}

		});

		w_appointment.add(select_clinic);

		JLabel label_4 = new JLabel("Doktor Se\u00E7");
		label_4.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label_4.setBounds(201, 69, 68, 14);
		w_appointment.add(label_4);

		JButton btn_selDoctor = new JButton("Se�");
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if (row >= 0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();// burada table_whour
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_whour.setModel(whourModel);
					// Bir doktor se�tik,..........ilk metin...........
					selectDoctorID = id;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();

				} else {
					Helper.showMsg("L�tfen bir doktor se�iniz !");
				}

			}
		});
		btn_selDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_selDoctor.setBounds(200, 86, 120, 25);
		w_appointment.add(btn_selDoctor);

		JLabel lblUygunSaatler = new JLabel("Uygun Saatler");
		lblUygunSaatler.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		lblUygunSaatler.setBounds(327, 8, 83, 14);
		w_appointment.add(lblUygunSaatler);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(327, 24, 182, 203);
		w_appointment.add(w_scrollWhour);

		table_whour = new JTable(whourModel);// ekle column geni�l�inde hata al�nca 0>=0 hatas�
		w_scrollWhour.setViewportView(table_whour);

		JLabel label_4_1 = new JLabel("Randevu");
		label_4_1.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label_4_1.setBounds(201, 134, 68, 14);
		w_appointment.add(label_4_1);

		JButton btn_addAppoint = new JButton("Randevu Al");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, hasta.getId(), selectDoctorName,
								hasta.getName(), date);
						if (control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							updateAppointModel(hasta.getId());

						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("L�tfen ge�erli bir tarih giriniz !");
				}

			}
		});
		btn_addAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_addAppoint.setBounds(200, 151, 120, 25);
		w_appointment.add(btn_addAppoint);

		JPanel w_appoint = new JPanel();
		w_tab.addTab("Randevular�m", null, w_appoint, null);
		w_appoint.setLayout(null);

		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 499, 216);
		w_appoint.add(w_scrollAppoint);

		// elle popupmenu ekliyorum
		rMenu = new JPopupMenu();
		JMenuItem cancelAppoiment = new JMenuItem("�ptal et");
		rMenu.add(cancelAppoiment);

		cancelAppoiment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (Helper.confirm("sure")) {

					int selID = Integer
							.parseInt(table_appoint.getValueAt(table_appoint.getSelectedRow(), 0).toString());

					String drname = table_appoint.getValueAt(table_appoint.getSelectedRow(), 1).toString();
					String date = table_appoint.getValueAt(table_appoint.getSelectedRow(), 2).toString();
					try {
						if (hasta.deleteAppoiment(selID, drname, date)) {
							Helper.showMsg("success");

							// updateAppointModel(selID);
							updateAppointModel(hasta.getId());

						} else {
							Helper.showMsg("error");// veritraban� i�lemi s�ras�nda hata
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("cancel");
				}

			}
		});

		table_appoint = new JTable(appointModel);
		table_appoint.setComponentPopupMenu(rMenu);

		table_appoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				Point point = e.getPoint();// t�klad��� koordinatlar� al�yoruz
				int selectedRow = table_appoint.rowAtPoint(point);
				table_appoint.setRowSelectionInterval(selectedRow, selectedRow);// nerde sa� t�klad���m�zda se�ilen �eyi
																				// onla de�i�triyiorz

			}

		});

		w_scrollAppoint.setViewportView(table_appoint);
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(5);// column geni�li�i
	}

	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}

	}

	public void updateAppointModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < appoint.getHastaList(hasta_id).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta_id).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

	}
}

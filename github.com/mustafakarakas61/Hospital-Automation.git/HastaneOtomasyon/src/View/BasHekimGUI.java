package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Helper.*;
import javax.swing.JComboBox;

public class BasHekimGUI extends JFrame {

	static BasHekim bashekim = new BasHekim();// +

	Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField fld_dName;
	private JTextField fld_dTcno;
	private JTextField fld_doctorID;
	private JPasswordField fld_dPass;
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null;
	private DefaultTableModel randevuModel = null;
	private DefaultComboBoxModel comboModel = null;
	private Object[] doctorData = null;
	private Object[] randevuData = null;
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable table_worker;
	private JTable table_randevular;
	private DefaultTableModel appointModel;
	private Appointment appoint = new Appointment();
	private Object[] appointData = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasHekimGUI frame = new BasHekimGUI(bashekim);// +
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

	public BasHekimGUI(BasHekim bashekim) throws SQLException {// +

		// comboModel

		comboModel = new DefaultComboBoxModel<>();

		// Randevular Model

		randevuModel = new DefaultTableModel();
		Object[] colrandevu = new Object[6];// Object int String falan hepsini kapsadýðý için veri türü Object
											// kullandýk
		colrandevu[0] = "ID";
		colrandevu[1] = "Doktor ID";
		colrandevu[2] = "Hasta ID";
		colrandevu[3] = "Doktor Adý";
		colrandevu[4] = "Hasta Adý";
		colrandevu[5] = "Tarih";
		randevuModel.setColumnIdentifiers(colrandevu);
		randevuData = new Object[6];
		// ************************************************************************************
		for (int i = 0; i < appoint.gethastaListrand().size(); i++) {
			randevuData[0] = appoint.gethastaListrand().get(i).getId();
			randevuData[1] = appoint.gethastaListrand().get(i).getDoctorID();
			randevuData[2] = appoint.gethastaListrand().get(i).getHastaID();
			randevuData[3] = appoint.gethastaListrand().get(i).getDoctorName();
			randevuData[4] = appoint.gethastaListrand().get(i).getHastaName();
			randevuData[5] = appoint.gethastaListrand().get(i).getAppDate();

			randevuModel.addRow(randevuData);
		}

		// Doctor Model
		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[4];// Object int String falan hepsini kapsadýðý için veri türü Object
												// kullandýk
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Ad Soyad";
		colDoctorName[2] = "TC NO";
		colDoctorName[3] = "Þifre";
		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[4];
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();

			doctorModel.addRow(doctorData);
		}

		// Clinic Model burada listeyi çekioz
		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Poliklinik Adý";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}

		// WorkerModel
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];

		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 380);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoþgeldiniz, Sayýn " + bashekim.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 196, 21);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("\u00C7\u0131k\u0131\u015F Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnNewButton.setBounds(449, 11, 85, 21);
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 74, 524, 266);
		w_pane.add(w_tab);

		JPanel w_doctor = new JPanel();
		w_doctor.setBackground(Color.WHITE);
		w_tab.addTab("Doktor Yönetimi", null, w_doctor, null);
		w_doctor.setLayout(null);

		JLabel label = new JLabel("Ad Soyad");
		label.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label.setBounds(380, 11, 58, 14);
		w_doctor.add(label);

		fld_dName = new JTextField();
		fld_dName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		fld_dName.setBounds(380, 28, 129, 20);
		w_doctor.add(fld_dName);
		fld_dName.setColumns(10);

		JLabel label_1 = new JLabel("T.C. No");
		label_1.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label_1.setBounds(380, 52, 58, 14);
		w_doctor.add(label_1);

		fld_dTcno = new JTextField();
		fld_dTcno.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		fld_dTcno.setColumns(10);
		fld_dTcno.setBounds(380, 70, 129, 20);
		w_doctor.add(fld_dTcno);

		JLabel label_2 = new JLabel("\u015Eifre");
		label_2.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label_2.setBounds(380, 93, 58, 14);
		w_doctor.add(label_2);

		JButton btn_addDoctor = new JButton("Ekle");
		btn_addDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_addDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// boþ deðer girilmemesi için;
				if (fld_dName.getText().length() == 0 || fld_dPass.getText().length() == 0
						|| fld_dTcno.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = bashekim.addDoctor(fld_dTcno.getText(), fld_dPass.getText(),
								fld_dName.getText());
						if (control) {

							Helper.showMsg("success");
							fld_dName.setText(null);// eklediðimiz yerleri silioz boþ gözüksün name tc sifre falan
							fld_dTcno.setText(null);
							fld_dPass.setText(null);
							updateDoctorModel();// güncelleme iþlemini burdan çaðýr
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		fld_dPass = new JPasswordField();
		fld_dPass.setBounds(380, 111, 129, 20);
		w_doctor.add(fld_dPass);
		btn_addDoctor.setBounds(380, 136, 129, 23);
		w_doctor.add(btn_addDoctor);

		JLabel label_3 = new JLabel("Kullan\u0131c\u0131 ID");
		label_3.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label_3.setBounds(380, 159, 58, 14);
		w_doctor.add(label_3);

		fld_doctorID = new JTextField();
		fld_doctorID.setEditable(false);
		fld_doctorID.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		fld_doctorID.setColumns(10);
		fld_doctorID.setBounds(380, 178, 129, 20);
		w_doctor.add(fld_doctorID);

		JButton btn_delDoctor = new JButton("Sil");
		btn_delDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doctorID.getText().length() == 0)// geçerli bir doktor yoksa
				{
					Helper.showMsg("Lütfen geçerli bir doktor seçiniz !");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_doctorID.getText());
						try {
							boolean control = bashekim.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("success");
								fld_doctorID.setText(null);
								updateDoctorModel();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}

			}
		});
		btn_delDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_delDoctor.setBounds(380, 204, 129, 23);
		w_doctor.add(btn_delDoctor);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 11, 367, 216);
		w_doctor.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		// veriye týklayýnca çýkan mavi þeye selectionmodel diyoruz, bir veri üzerine
		// týkla ve bunu silmeki için bu kodlarý yazacaz
		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {// birþey seçildiyse bunu
																								// bana getir

			@Override
			public void valueChanged(ListSelectionEvent e) {// seçilmiþ olan rowu seç , hangi colom, gneelde id 1 de
															// olur ve bunu toString ile getir
				try {
					fld_doctorID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch (Exception ex) {

				}

			}
		});

		table_doctor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {// seçili satýr , sütun
					int selectID = Integer
							.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectTcno = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();

					try {
						boolean control = bashekim.updateDoctor(selectID, selectTcno, selectPass, selectName);

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(Color.WHITE);
		w_tab.addTab("Poliklinikler", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 11, 181, 216);
		w_clinic.add(w_scrollClinic);
		// tablonun üstüne elle popubmenu giriyoruz;
		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {// popupmenu kapandýðýnda dinlemek için
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				});
			}
		});

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (Helper.confirm("sure")) {

					int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
					try {
						if (clinic.deleteClinic(selID)) {
							Helper.showMsg("success");
							updateClinicModel();
							// iptal edilen saati aktif yapalým

						} else {
							Helper.showMsg("error");// veritrabaný iþlemi sýrasýnda hata
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMsg("cancel");
				}

			}
		});

		table_clinic = new JTable(clinicModel);// clinic modeli buna atýyoruz

		// popup menüyü bu þekilde tablomuza ekliyoz
		table_clinic.setComponentPopupMenu(clinicMenu);

		// þimdi hangi butona týkladýðýmýzý yaani hangi mavinin yandýðýna ulaþmak için
		// bu kodu yazýyoruz
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				Point point = e.getPoint();// týkladýðý koordinatlarý alýyoruz
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);// nerde sað týkladýðýmýzda seçilen þeyi
																				// onla deðiþtriyiorz

			}

		});

		w_scrollClinic.setViewportView(table_clinic);

		JLabel lblPoliklinikAd = new JLabel("Poliklinik Ad\u0131");
		lblPoliklinikAd.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		lblPoliklinikAd.setBounds(200, 11, 68, 14);
		w_clinic.add(lblPoliklinikAd);

		fld_clinicName = new JTextField();
		fld_clinicName.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(200, 28, 117, 20);
		w_clinic.add(fld_clinicName);

		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ekleme databasei yazmak lazým ama önce bu yapýyý Clinic içerisinde
				// oluþturalým public ArrayList<User> getDoctorList yapýsý Clinic içine falan
				// falan
				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if (clinic.addClinic(fld_clinicName.getText()))
							;
						{
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btn_addClinic.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_addClinic.setBounds(200, 57, 117, 23);
		w_clinic.add(btn_addClinic);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(327, 11, 181, 216);
		w_clinic.add(w_scrollWorker);

		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);

		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(201, 170, 117, 26);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			select_doctor.addItem(
					new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));

		}

		// þimdi ise kimi seçtiysek deðerimizin o olmasý için bu kodu yazýyoruz
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " : " + item.getValue());

		});

		w_clinic.add(select_doctor);

		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// önce tablodan bir deðer seçmesi gerekiyor
				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					// seçilen poliklinikteki id yi alacam
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					Item doctorItem = (Item) select_doctor.getSelectedItem();// seçilen doktor item
					try {
						boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicID);
						if (control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
							clearModel.setRowCount(0);
							for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
								workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();// doktorun
																											// idsini
																											// yazdýrdýk
								workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();// adýný
																											// yazdýrdýk
								workerModel.addRow(workerData);// ve bunu modelimizin içine atýyoruz
							}
							table_worker.setModel(workerModel);
						} else {
							Helper.showMsg("error");
						}

					} catch (SQLException e1) {

						e1.printStackTrace();
					}

				} else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz.");
				}

			}
		});
		btn_addWorker.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_addWorker.setBounds(201, 202, 116, 25);
		w_clinic.add(btn_addWorker);

		JLabel lblPoliklinikSe = new JLabel("Poliklinik Se\u00E7");
		lblPoliklinikSe.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		lblPoliklinikSe.setBounds(202, 100, 68, 14);
		w_clinic.add(lblPoliklinikSe);

		JButton btn_workerSelect = new JButton("Ekle");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if (selRow >= 0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();// seçilen kliniði aldýk
					int selClinicID = Integer.parseInt(selClinic);// sonra id sini aldýk
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
							workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();// doktorun idsini
																										// yazdýrdýk
							workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();// adýný
																										// yazdýrdýk
							workerModel.addRow(workerData);// ve bunu modelimizin içine atýyoruz
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);// þimdi de tablomuzun içine atýyoruz modeli

				} else {
					Helper.showMsg("Lütfen bir Poliklinik seçiniz !");
				}

			}
		});
		btn_workerSelect.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_workerSelect.setBounds(201, 117, 117, 25);
		w_clinic.add(btn_workerSelect);

		JPanel w_randevular = new JPanel();
		w_randevular.setBackground(Color.WHITE);
		w_tab.addTab("Randevular", null, w_randevular, null);
		w_randevular.setLayout(null);

		JScrollPane w_scrollRandevular = new JScrollPane();
		w_scrollRandevular.setBounds(10, 11, 499, 186);
		w_randevular.add(w_scrollRandevular);

		table_randevular = new JTable(randevuModel);

		// ************************************************************************************************************
		// ***************************************************************************************************

		w_scrollRandevular.setViewportView(table_randevular);

		table_randevular.getColumnModel().getColumn(0).setPreferredWidth(5);// column geniþliði
		table_randevular.getColumnModel().getColumn(1).setPreferredWidth(5);
		table_randevular.getColumnModel().getColumn(2).setPreferredWidth(5);

		JButton btn_yenile = new JButton("Yenile");
		btn_yenile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel clearModel = (DefaultTableModel) table_randevular.getModel();
				clearModel.setRowCount(0);// tablodaki verileri sildik
				try {
					for (int i = 0; i < appoint.gethastaListrand().size(); i++) {
						randevuData[0] = appoint.gethastaListrand().get(i).getId();
						randevuData[1] = appoint.gethastaListrand().get(i).getDoctorID();
						randevuData[2] = appoint.gethastaListrand().get(i).getHastaID();
						randevuData[3] = appoint.gethastaListrand().get(i).getDoctorName();
						randevuData[4] = appoint.gethastaListrand().get(i).getHastaName();
						randevuData[5] = appoint.gethastaListrand().get(i).getAppDate();

						randevuModel.addRow(randevuData);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btn_yenile.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		btn_yenile.setBounds(213, 202, 89, 30);
		w_randevular.add(btn_yenile);
	}

	public void updateDoctorModel() throws SQLException {// tabloyu güncelleþtirmeki için
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);// tablodaki verileri sildik
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();

			doctorModel.addRow(doctorData);
		}

	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}

	}
}

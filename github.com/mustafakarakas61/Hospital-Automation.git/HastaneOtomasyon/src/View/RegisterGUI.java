package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_pass;
	private Hasta hasta= new Hasta();//kayýt olurken 
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 300);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel label = new JLabel("Ad Soyad");
		label.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		label.setBounds(10, 11, 59, 14);
		w_pane.add(label);
		
		fld_name = new JTextField();
		fld_name.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		fld_name.setColumns(10);
		fld_name.setBounds(10, 28, 254, 26);
		w_pane.add(fld_name);
		
		JLabel lblTcNumaras = new JLabel("T.C. Numaras\u0131");
		lblTcNumaras.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		lblTcNumaras.setBounds(10, 66, 83, 14);
		w_pane.add(lblTcNumaras);
		
		fld_tcno = new JTextField();
		fld_tcno.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 83, 254, 26);
		w_pane.add(fld_tcno);
		
		JLabel lblifre = new JLabel("\u015Eifre");
		lblifre.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 11));
		lblifre.setBounds(10, 120, 83, 14);
		w_pane.add(lblifre);
		
		fld_pass = new JPasswordField();
		fld_pass.setBounds(10, 141, 254, 26);
		w_pane.add(fld_pass);
		
		JButton btn_register = new JButton("Kay\u0131t Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_tcno.getText().length()==0||fld_pass.getText().length()==0||fld_name.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					try {
						boolean control =hasta.register(fld_tcno.getText(), fld_pass.getText(), fld_name.getText());
						if(control) {//baþarýlýyla tabloya eklediyse
							Helper.showMsg("success");
							LoginGUI login =new LoginGUI();//Login ekranýna atýyoruz
							login.setVisible(true);//login açýldý
							dispose();//bu ekraný kapatýyoruz
						}else {
							Helper.showMsg("error");
						}
					
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_register.setBounds(10, 178, 254, 35);
		w_pane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri D\u00F6n");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login =new LoginGUI();//Login ekranýna atýyoruz
				login.setVisible(true);//login açýldý
				dispose();//bu ekraný kapatýyoruz
				
			}
		});
		btn_backto.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		btn_backto.setBounds(10, 225, 254, 35);
		w_pane.add(btn_backto);
	}
}

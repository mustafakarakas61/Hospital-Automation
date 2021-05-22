package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.Helper;
import View.HastaGUI;

public class Hasta extends User {
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null; // hazýrlanabilen statement
	Connection con = conn.connDb();

	public Hasta() {

	}

	public Hasta(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);

	}

	public boolean register(String tcno, String password, String name) throws SQLException {// addWhour veya addDoctor
																							// dan al copy paste
		int key = 0;// veririnin eklenip eklenmediðini kontrol etmek için key oluþturdk
		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";
		boolean duplicate = false;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tcno='" + tcno + "'");// tek týrnak dikkat bu sorgu, böyle
																					// bir kullanýcý var mý yok diye

			while (rs.next()) {
				duplicate = true;
				Helper.showMsg("Bu T.C. numarasýna ait bir kayýt bulunmaktadýr.");
				break;
			}

			if (!duplicate) {

				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, tcno);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.setString(4, "hasta");
				preparedStatement.executeUpdate();
				key = 1;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}

	public boolean deleteAppoiment(int id, String dr_name, String wDate) throws SQLException {

		String query = "DELETE FROM  appointment  WHERE id = ?";
		boolean key = false;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rUpdateWhourStatus(dr_name, wDate);
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);// 1
			// pasifi tekrar aktife çeviriom

			preparedStatement.executeUpdate();

			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

	public boolean addAppointment(int doctor_id, int hasta_id, String doctor_name, String hasta_name, String appDate)
			throws SQLException {// addWhour veya addDoctor dan al copy paste
		int key = 0;// veririnin eklenip eklenmediðini kontrol etmek için key oluþturdk
		String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES"
				+ "(?,?,?,?,?)";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, doctor_id);
			preparedStatement.setString(2, doctor_name);
			preparedStatement.setInt(3, hasta_id);
			preparedStatement.setString(4, hasta_name);
			preparedStatement.setString(5, appDate);
			preparedStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}
	// seçilmiþ olan randevularý pasife çekmek için ( seçilmiþ bileti almamak için
	// güzel) aþaðýdaki kodlar

	public boolean updateWhourStatus(int doctor_id, String wDate) throws SQLException {// addWhour veya addDoctor dan al
																						// copy paste
		int key = 0;// veririnin eklenip eklenmediðini kontrol etmek için key oluþturdk
		String query = "UPDATE whour SET status = ? WHERE doctor_id= ? AND wdate= ?";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "p");
			preparedStatement.setInt(2, doctor_id);
			preparedStatement.setString(3, wDate);
			preparedStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}

	// iptal edilen randevunun durumunu p den a ya çevircem
	public boolean rUpdateWhourStatus(String doctor_name, String wDate) throws SQLException {// addWhour veya addDoctor
																								// dan al copy paste
		int key = 0;// veririnin eklenip eklenmediðini kontrol etmek için key oluþturdk
		String query = "UPDATE whour SET status = ? WHERE doctor_name= ? AND wdate= ?";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "a");
			preparedStatement.setString(2, doctor_name);
			preparedStatement.setString(3, wDate);
			preparedStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}

}

package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

//veritaban�nda yeni bir tablo olu�turduk onun modelini yap�rouz
public class Appointment {
	private int id, doctorID, hastaID;
	private String doctorName, hastaName, appDate;
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null; // haz�rlanabilen statement

	public Appointment(int id, int doctorID, int hastaID, String doctorName, String hastaName, String appDate) {
		super();
		this.id = id;
		this.doctorID = doctorID;
		this.hastaID = hastaID;
		this.doctorName = doctorName;
		this.hastaName = hastaName;
		this.appDate = appDate;
	}

	public Appointment() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public int getHastaID() {
		return hastaID;
	}

	public void setHastaID(int hastaID) {
		this.hastaID = hastaID;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getHastaName() {
		return hastaName;
	}

	public void setHastaName(String hastaName) {
		this.hastaName = hastaName;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	// Randevular�m listelemek i�in
	public ArrayList<Appointment> getHastaList(int hasta_id) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Connection con = conn.connDb();
		Appointment obj;
		try {

			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE hasta_id= " + hasta_id);
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setAppDate(rs.getString("app_date"));

				list.add(obj);

			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();// her hangi bir hata olursa ekrana bas
		} finally {
			st.close();
			rs.close();
			con.close();
		}

		return list;

	}

	// randevu listelemek mi ?
	public ArrayList<Appointment> gethastaListrand() throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Connection con = conn.connDb();
		Appointment obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment");
			while (rs.next()) {
				obj = new Appointment(rs.getInt("id"), rs.getInt("doctor_id"), rs.getInt("hasta_id"),
						rs.getString("doctor_name"), rs.getString("hasta_name"), rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} // finally yaz�nca Statement Result connection fala n null de�erleri falan
			// onlar� Arraylist<user> i�inde tan�mla

		return list;

	}

	// Randevular�m� listelemek i�in s�ra doktoru ,�stte hasta bilgi �imdi doktor
	public ArrayList<Appointment> getDoctorList(int doctor_id) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Connection con = conn.connDb();
		Appointment obj;
		try {

			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE hasta_id= " + doctor_id);
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctorID"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setAppDate(rs.getString("app_date"));

				list.add(obj);

			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();// her hangi bir hata olursa ekrana bas
		} finally {
			st.close();
			rs.close();
			con.close();
		}

		return list;

	}

}

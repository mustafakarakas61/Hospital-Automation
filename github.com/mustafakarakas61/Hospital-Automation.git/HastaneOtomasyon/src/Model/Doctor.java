package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doctor extends User {
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null; // haz�rlanabilen statement
	Connection con = conn.connDb();

	public Doctor() {
		super();
	}

	public Doctor(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
	}

	public boolean addWhour(int doctor_id, String doctor_name, String wdate) throws SQLException {
		int key = 0;// veririnin eklenip eklenmedi�ini kontrol etmek i�in key olu�turdk
		String query = "INSERT INTO whour" + "(doctor_id,doctor_name,wdate) VALUES" + "(?,?,?)";
		int count = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"SELECT * FROM whour WHERE status='a' AND doctor_id='" + doctor_id + "'AND wdate='" + wdate + "'");//tek t�rnak dikkat

			while (rs.next()) {
				count++;
				break;
			}

			if (count == 0) {

				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doctor_id);
				preparedStatement.setString(2, doctor_name);
				preparedStatement.setString(3, wdate);
				preparedStatement.executeUpdate();
			}
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

	public ArrayList<Whour> getWhourList(int doctor_id) throws SQLException {
		ArrayList<Whour> list = new ArrayList<>();

		Whour obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id= " + doctor_id);
			while (rs.next()) {
				obj = new Whour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setStatus(rs.getString("status"));
				obj.setWdate(rs.getString("wdate"));

				list.add(obj);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} // finally yaz�nca Statement Result connection fala n null de�erleri falan
			// onlar� Arraylist<user> i�inde tan�mla

		return list;

	}

	public boolean deleteWhour(int id) throws SQLException {

		String query = "DELETE FROM  whour  WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);// 1 2 3 4 soru i�aretlerin s�ras�
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

}

package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.vo.PersonVO;

public class PersonDAO {
	private static final int _CODE_GENRE_MALE 			= 1;
	private static final int _CODE_GENRE_FEMALE 		= 2;
	private static final String _STRING_GENRE_MALE 		= "Masculino";
	private static final String _STRING_GENRE_FEMALE 	= "Feminino";
	private static final String _QUERY					= "SELECT per.id as id, per.name as name, per.birth as birth, gen.name as genre, per.document as document "
														+ "FROM person as per "
														+ "INNER JOIN genres as gen on per.genre_id = gen.id";
	
	public PersonVO store(PersonVO person) {
		Connection conn = Database.getConnection();		
		String sql 		= " INSERT INTO person (name, birth, genre, document) " 
						+ " VALUES (?,?,?,?) ";
		
		PreparedStatement query = Database.getPreparedStatementWithGeneratedKeys(conn, sql);
		
		try {
			query.setString(1, person.getName());
			Date birth = java.sql.Date.valueOf(person.getBirth());
			query.setDate(2, birth);
			query.setInt(3, person.getGenre() == this._STRING_GENRE_MALE ? this._CODE_GENRE_MALE : this._CODE_GENRE_FEMALE);
			query.setString(4, person.getDocument());

			int codeReturn = query.executeUpdate();
			
			if(codeReturn == Database.CODE_RETURN_SUCCESS) {
				ResultSet rs 	= query.getGeneratedKeys();
				int keyGenerate = rs.getInt(1);
				
				person.setId(keyGenerate);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir pessoa.\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return person;
	}

	public boolean update(PersonVO person) {
		String sql 				= " UPDATE person "
								+ " SET name=?, birth=?, genre=?, document=? " 
								+ " WHERE ID=? ";
		Connection conn 		= Database.getConnection();
		PreparedStatement query = Database.getPreparedStatement(conn, sql);		
		boolean changed 		= false;
		
		try {
			query.setString(1, person.getName());
			Date birth = java.sql.Date.valueOf(person.getBirth());
			query.setDate(2, birth);
			query.setInt(3, person.getGenre() == this._STRING_GENRE_MALE ? this._CODE_GENRE_MALE : this._CODE_GENRE_FEMALE);
			query.setString(4, person.getDocument());
			query.setInt(5, person.getId());
			
			int codeReturn 	= query.executeUpdate();
			changed 		= (codeReturn == Database.CODE_RETURN_SUCCESS);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar pessoa.\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
				
		return changed;
	}

	public boolean delete(int id) {
		Connection conn 		= Database.getConnection();
		String sql 				= "DELETE FROM person WHERE id=? ";		
		PreparedStatement query = Database.getPreparedStatement(conn, sql);
		boolean excluded 		= false;
		
		try {
			query.setInt(1, id);
			
			int codeReturn 	= query.executeUpdate();
			excluded 		= (codeReturn == Database.CODE_RETURN_SUCCESS);
		} catch (SQLException e) {
			System.out.println("Erro ao excluir pessoa (id: " + id + ") .\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
				
		return excluded;
	}

	public PersonVO show(int id) {
		String sql					= this._QUERY + " WHERE per.id = ?";
		PersonVO person 			= null;
		Connection conn 			= Database.getConnection();
		PreparedStatement query 	= Database.getPreparedStatement(conn, sql);
		
		try {
			query.setInt(1, id);
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) {
				person = _buildPerson(rs);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar pessoa por ID (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		
		return person;
	}

	public List<PersonVO> all() {
		Connection conn 				= Database.getConnection();
		PreparedStatement query 		= Database.getPreparedStatement(conn, this._QUERY);
		List<PersonVO> persons 			= new ArrayList<PersonVO>();
		
		try {
			ResultSet rs = query.executeQuery();
			while(rs.next()) {
				PersonVO person = _buildPerson(rs);
				persons.add(person);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todas as pessoas .\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return persons;
	}
	
	public ArrayList<String> getGenre() {
		Connection conn 			= Database.getConnection();
		String sql 					= "SELECT name FROM genres";
		PreparedStatement query 	= Database.getPreparedStatement(conn, sql);
		ArrayList<String> genres 	= new ArrayList<String>();
		
		try {
			ResultSet rs = query.executeQuery();
			while(rs.next()) {
				genres.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os generos .\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}

		return genres;
	}

	private PersonVO _buildPerson(ResultSet rs) throws SQLException {
		PersonVO person = new PersonVO();
		
		person.setId(rs.getInt("id"));
		person.setName(rs.getString("name"));
		Date birthSQL = rs.getDate("birth");
		LocalDate birth = birthSQL.toLocalDate();
		person.setBirth(birth);
		person.setGenre(rs.getString("genre"));
		person.setDocument(rs.getString("document"));
				
		return person;
	}
}

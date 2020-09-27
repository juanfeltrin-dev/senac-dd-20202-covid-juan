package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.vo.GeneralPublicVO;
import model.vo.PersonVO;

public class GeneralPublicDAO {
	private static final String _QUERY 	= "SELECT per.id as id, per.name as name, per.birth as birth, gep.volunteers as volunteers, per.document as document "
										+ "FROM general_publics as gep "
										+ "INNER JOIN persons as per on gep.person_id = per.id";
	
	public GeneralPublicVO store(GeneralPublicVO generalPublic) {
		Connection conn 		= Database.getConnection();		
		String sql 				= "INSERT INTO general_publics (volunteers, person_id) " 
								+ "VALUES (?,?) ";	
		PreparedStatement query = Database.getPreparedStatementWithGeneratedKeys(conn, sql);
		
		try {
			PersonDAO personDAO 	= new PersonDAO();
			PersonVO personVO		= personDAO.store(generalPublic);
			
			query.setBoolean(1, generalPublic.isVolunteers());
			query.setInt(2, personVO.getId());

			int codeReturn = query.executeUpdate();
			
			if(codeReturn == Database.CODE_RETURN_SUCCESS) {
				ResultSet rs 	= query.getGeneratedKeys();
				int keyGenerate = rs.getInt(1);
				
				generalPublic.setId(keyGenerate);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir público geral/voluntário.\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return generalPublic;
	}

	public boolean update(GeneralPublicVO generalPublic) {
		String sql 				= "UPDATE general_publics "
								+ "SET volunteers=? " 
								+ "WHERE ID=? ";
		Connection conn 		= Database.getConnection();
		PreparedStatement query = Database.getPreparedStatement(conn, sql);	
		boolean changed 		= false;
		
		try {
			query.setBoolean(1, generalPublic.isVolunteers());
			
			PersonDAO personDAO = new PersonDAO();
			
			personDAO.update(generalPublic);
			
			int codeReturn = query.executeUpdate();
			changed 		= (codeReturn == Database.CODE_RETURN_SUCCESS);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar público geral/voluntário.\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return changed;
	}

	public boolean delete(int idGeneralPublic, int idPerson) {
		Connection conn 		= Database.getConnection();
		String sql 				= "DELETE FROM general_publics WHERE id=? ";		
		PreparedStatement query = Database.getPreparedStatement(conn, sql);
		boolean excluded 		= false;
		
		try {
			query.setInt(1, idGeneralPublic);
			
			int codeReturn 	= query.executeUpdate();
			excluded 		= (codeReturn == Database.CODE_RETURN_SUCCESS);
			
			if (excluded) {
				PersonDAO personDAO = new PersonDAO();
				
				personDAO.delete(idPerson);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao excluir pessoa (id: " + idGeneralPublic + ") .\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
				
		return excluded;
	}

	public GeneralPublicVO show(int id) {
		String sql						= this._QUERY + " WHERE gep.id = ?";
		GeneralPublicVO generalPublic 	= null;
		Connection conn 				= Database.getConnection();
		PreparedStatement query 		= Database.getPreparedStatement(conn, sql);
		
		try {
			query.setInt(1, id);
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) {
				generalPublic = _buildGeneralPublic(rs);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar pessoa por ID (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		
		return generalPublic;
	}

	public List<GeneralPublicVO> all() {
		Connection conn 					 = Database.getConnection();
		PreparedStatement query 			 = Database.getPreparedStatement(conn, this._QUERY);
		List<GeneralPublicVO> generalPublics = new ArrayList<GeneralPublicVO>();
		
		try {
			ResultSet rs = query.executeQuery();
			while(rs.next()) {
				GeneralPublicVO generalPublic = _buildGeneralPublic(rs);
				generalPublics.add(generalPublic);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todas as pessoas.\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return generalPublics;
	}

	private GeneralPublicVO _buildGeneralPublic(ResultSet rs) throws SQLException {
		GeneralPublicVO generalPublic = new GeneralPublicVO();
		
		generalPublic.setId(rs.getInt("id"));
		generalPublic.setName(rs.getString("name"));
		Date birthSQL = rs.getDate("birth");
		LocalDate birth = birthSQL.toLocalDate();
		generalPublic.setBirth(birth);
		generalPublic.setGenre(rs.getString("genre"));
		generalPublic.setDocument(rs.getString("document"));
		generalPublic.setVolunteers(rs.getBoolean("volunteers"));
				
		return generalPublic;
	}
}

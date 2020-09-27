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
import model.vo.ResearcherVO;

public class ResearcherDAO {
	private static final String _QUERY 	= "SELECT per.id as id, per.name as name, gen.name as genre,per.birth as birth, res.institution as institution, per.document as document "
			+ "FROM researchers as res "
			+ "INNER JOIN persons as per on res.person_id = per.id "
			+ "INNER JOIN genres as gen on per.genre_id = gen.id";

	public ResearcherVO store(ResearcherVO researcher) {
		Connection conn 		= Database.getConnection();		
		String sql 				= "INSERT INTO researchers (institution, person_id) " 
								+ "VALUES (?,?) ";	
		PreparedStatement query = Database.getPreparedStatementWithGeneratedKeys(conn, sql);
		
		try {
			PersonDAO personDAO 	= new PersonDAO();
			PersonVO personVO		= personDAO.store(researcher);
			
			query.setString(1, researcher.getInstitution());
			query.setInt(2, personVO.getId());
			
			int codeReturn = query.executeUpdate();
			
			if(codeReturn == Database.CODE_RETURN_SUCCESS) {
				ResultSet rs 	= query.getGeneratedKeys();
				int keyGenerate = rs.getInt(1);
				
				researcher.setId(keyGenerate);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir pesquisador.\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return researcher;
	}
	
	public boolean update(ResearcherVO researcher) {
		String sql 				= "UPDATE researchers "
								+ "SET institution=? " 
								+ "WHERE ID=? ";
		Connection conn 		= Database.getConnection();
		PreparedStatement query = Database.getPreparedStatement(conn, sql);	
		boolean changed 		= false;
		
		try {
			query.setString(1, researcher.getInstitution());
			
			PersonDAO personDAO = new PersonDAO();
			
			personDAO.update(researcher);
			
			int codeReturn 	= query.executeUpdate();
			changed 		= (codeReturn == Database.CODE_RETURN_SUCCESS);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar pesquisador.\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return changed;
	}
	
	public boolean delete(int idResearcher, int idPerson) {
		Connection conn 		= Database.getConnection();
		String sql 				= "DELETE FROM researchers WHERE id=? ";		
		PreparedStatement query = Database.getPreparedStatement(conn, sql);
		boolean excluded 		= false;
		
		try {
			query.setInt(1, idResearcher);
			
			int codeReturn 	= query.executeUpdate();
			excluded 		= (codeReturn == Database.CODE_RETURN_SUCCESS);
			
			if (excluded) {
				PersonDAO personDAO = new PersonDAO();
				
				personDAO.delete(idPerson);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao excluir pesquisador (id: " + idResearcher + ") .\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return excluded;
	}
	
	public ResearcherVO show(int id) {
		String sql						= this._QUERY + " WHERE res.id = ?";
		ResearcherVO researcher 		= null;
		Connection conn 				= Database.getConnection();
		PreparedStatement query 		= Database.getPreparedStatement(conn, sql);
		
		try {
			query.setInt(1, id);
			
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) {
				researcher = _buildResearcher(rs);
			}
		} catch (SQLException e) {
		System.out.println("Erro ao consultar pesquisador por ID (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		
		return researcher;
	}
	
	public List<ResearcherVO> all() {
		Connection conn 					= Database.getConnection();
		PreparedStatement query 			= Database.getPreparedStatement(conn, this._QUERY);
		List<ResearcherVO> researchers 		= new ArrayList<ResearcherVO>();
		
		try {
			ResultSet rs = query.executeQuery();
			
			while(rs.next()) {
				ResearcherVO researcher = _buildResearcher(rs);
				researchers.add(researcher);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os pesquisadores.\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return researchers;
	}
	
	public ArrayList<String> allByName() {
		Connection conn 					= Database.getConnection();
		String sql							= "SELECT per.name FROM researchers as res "
											+ "INNER JOIN persons as per on res.person_id = per.id";
		PreparedStatement query 			= Database.getPreparedStatement(conn, sql);
		ArrayList<String> researchers 		= new ArrayList<String>();
		
		try {
			ResultSet rs = query.executeQuery();
			
			while(rs.next()) {
				researchers.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os pesquisadores pelo nome.\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return researchers;
	}
	
	private ResearcherVO _buildResearcher(ResultSet rs) throws SQLException {
		ResearcherVO researcher = new ResearcherVO();
		
		researcher.setId(rs.getInt("id"));
		researcher.setName(rs.getString("name"));
		Date birthSQL = rs.getDate("birth");
		LocalDate birth = birthSQL.toLocalDate();
		researcher.setBirth(birth);
		researcher.setGenre(rs.getString("genre"));
		researcher.setDocument(rs.getString("document"));
		researcher.setInstitution(rs.getString("institution"));
		
		return researcher;
	}
}

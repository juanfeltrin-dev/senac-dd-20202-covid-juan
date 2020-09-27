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
import model.vo.ResearcherVO;
import model.vo.VaccineVO;

public class VaccineDAO {
	private static final int _STAGE_INITIAL 			= 1;
	private static final int _STAGE_TESTS 				= 2;
	private static final int _STAGE_MASS_APPLICATION 	= 3;
	private static final String _QUERY 	= "SELECT vac.id as id, vac.country as country, stg.name as stage, vac.search_start_date as start_date, per.name as researcher "
										+ "FROM vaccines as vac "
										+ "INNER JOIN stages as stg on stg.id = vac.stage_id "
										+ "INNER JOIN researchers as res on res.id = vac.researcher_id "
										+ "INNER JOIN person as per on per.id = res.person_id ";

	public VaccineVO store(VaccineVO vaccine) {
		Connection conn 		= Database.getConnection();		
		String sql 				= "INSERT INTO vaccines (country, stage_id, search_start_date, researcher_id) " 
								+ "VALUES (?,?,?,?) ";	
		PreparedStatement query = Database.getPreparedStatementWithGeneratedKeys(conn, sql);
		
		try {
			query.setString(1, vaccine.getCountry());
			query.setString(2, vaccine.getStage());
			Date startDate = java.sql.Date.valueOf(vaccine.getStartDate());
			query.setDate(3, startDate);
			query.setString(4, vaccine.getResearcher().getName());
			
			int codeReturn = query.executeUpdate();
			
			if(codeReturn == Database.CODE_RETURN_SUCCESS) {
				ResultSet rs 	= query.getGeneratedKeys();
				int keyGenerate = rs.getInt(1);
				
				vaccine.setId(keyGenerate);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir vacina.\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return vaccine;
	}
	
	public boolean update(VaccineVO vaccine) {
		String sql 				= "UPDATE vaccines "
								+ "SET country=?, stage_id=?, search_start_date=?, researcher_id=? " 
								+ "WHERE ID=? ";
		Connection conn 		= Database.getConnection();
		PreparedStatement query = Database.getPreparedStatement(conn, sql);	
		boolean changed 		= false;
		
		try {
			query.setString(1, vaccine.getCountry());
			query.setString(2, vaccine.getStage());
			Date startDate = java.sql.Date.valueOf(vaccine.getStartDate());
			query.setDate(3, startDate);
			query.setString(4, vaccine.getResearcher().getName());
			query.setInt(5, vaccine.getId());
			
			int codeReturn 	= query.executeUpdate();
			changed 		= (codeReturn == Database.CODE_RETURN_SUCCESS);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar vacina.\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return changed;
	}
	
	public boolean delete(int idVaccine) {
		Connection conn 		= Database.getConnection();
		String sql 				= "DELETE FROM vaccines WHERE id=? ";		
		PreparedStatement query = Database.getPreparedStatement(conn, sql);
		boolean excluded 		= false;
		
		try {
			query.setInt(1, idVaccine);
			
			int codeReturn 	= query.executeUpdate();
			excluded 		= (codeReturn == Database.CODE_RETURN_SUCCESS);
		} catch (SQLException e) {
			System.out.println("Erro ao excluir vacina (id: " + idVaccine + ") .\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return excluded;
	}
	
	public VaccineVO show(int id) {
		String sql						= this._QUERY + " WHERE vac.id = ?";
		VaccineVO vaccine		 		= null;
		Connection conn 				= Database.getConnection();
		PreparedStatement query 		= Database.getPreparedStatement(conn, sql);
		
		try {
			query.setInt(1, id);
			
			ResultSet rs = query.executeQuery();
			
			if(rs.next()) {
				vaccine = _buildVaccine(rs);
			}
		} catch (SQLException e) {
		System.out.println("Erro ao consultar vacina por ID (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		
		return vaccine;
	}
	
	public List<VaccineVO> all() {
		Connection conn 					= Database.getConnection();
		PreparedStatement query 			= Database.getPreparedStatement(conn, this._QUERY);
		List<VaccineVO> vaccines 			= new ArrayList<VaccineVO>();
		
		try {
			ResultSet rs = query.executeQuery();
			
			while(rs.next()) {
				VaccineVO vaccine = _buildVaccine(rs);
				vaccines.add(vaccine);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todas as vacinas.\nCausa: " + e.getMessage());
		}finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}
		
		return vaccines;
	}
	
	public ArrayList<String> getStages() {
		Connection conn 			= Database.getConnection();
		String sql 					= "SELECT name FROM stages";
		PreparedStatement query 	= Database.getPreparedStatement(conn, sql);
		ArrayList<String> stages 	= new ArrayList<String>();
		
		try {
			ResultSet rs = query.executeQuery();
			while(rs.next()) {
				stages.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os estágios .\nCausa: " + e.getMessage());
		} finally {
			Database.closeStatement(query);
			Database.closeConnection(conn);
		}

		return stages;
	}
	
	private VaccineVO _buildVaccine(ResultSet rs) throws SQLException {
		VaccineVO vaccine = new VaccineVO();
		
		vaccine.setId(rs.getInt("id"));
		vaccine.setCountry(rs.getString("country"));
		vaccine.setStage(rs.getString("stage"));
		vaccine.getResearcher().setName(rs.getString("researcher"));
		Date startDateSQL = rs.getDate("start_date");
		LocalDate startDate = startDateSQL.toLocalDate();
		vaccine.setStartDate(startDate);
		
		return vaccine;
	}
}

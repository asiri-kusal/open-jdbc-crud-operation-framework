package com.github.asirikusal.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.asirikusal.entity.JdbcTemplates;

@Repository
public class JdbcTemplateRepo {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Transactional(readOnly = true)
	public List<JdbcTemplates> findAll() {
		return jdbcTemplate.query("select * from Jdbc_template", new JdbcTemplatesRowMapper());
		
	}

	@Transactional(readOnly = true)
	public JdbcTemplates findUserById(int id) {
		String sql="select * from Jdbc_template where id=:id";
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("id",id);
		return namedParameterJdbcTemplate.queryForObject(sql, parameters, new JdbcTemplatesRowMapper());

	}
}

class JdbcTemplatesRowMapper implements RowMapper<JdbcTemplates> {
	@Override
	public JdbcTemplates mapRow(ResultSet rs, int rowNum) throws SQLException {
		JdbcTemplates jdbcTemplates = new JdbcTemplates();
		jdbcTemplates.setId(rs.getInt("id"));
		jdbcTemplates.setName(rs.getString("name"));
		jdbcTemplates.setEmail(rs.getString("email"));
		jdbcTemplates.setCreatedAt(rs.getDate("created_at"));
		return jdbcTemplates;
	}
}

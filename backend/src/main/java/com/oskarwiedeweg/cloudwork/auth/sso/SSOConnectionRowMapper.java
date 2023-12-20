package com.oskarwiedeweg.cloudwork.auth.sso;

import com.oskarwiedeweg.cloudwork.user.UserRowMapper;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Component
public class SSOConnectionRowMapper implements RowMapper<SSOConnection> {

    private final UserRowMapper userRowMapper = new UserRowMapper(true);

    @Override
    public SSOConnection mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new SSOConnection(
                resultSet.getLong("id"),
                resultSet.getString("acc_id"),
                resultSet.getString("provider"),
                resultSet.getString("email"),
                userRowMapper.mapRow(resultSet, rowNum)
        );
    }

}

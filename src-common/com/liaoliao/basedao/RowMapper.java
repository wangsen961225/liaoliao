package com.liaoliao.basedao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper
 */
public interface RowMapper
{
    public Object mapRow(ResultSet rs, int index)
            throws SQLException;
}
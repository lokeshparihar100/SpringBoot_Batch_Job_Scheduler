package com.batch.writer;


import org.springframework.batch.item.ItemWriter;

import com.batch.model.PersonDetails;
import com.batch.model.PersonJob;
import com.batch.model.PersonName;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PostgreSQLItemWriter<T> implements ItemWriter<T> {

    private DataSource dataSource;

    public PostgreSQLItemWriter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            for (T item : items) {
                if (item instanceof PersonName) {
                    insertName((PersonName) item, conn);
                } else if (item instanceof PersonDetails) {
                    insertDetails((PersonDetails) item, conn);
                } else if (item instanceof PersonJob) {
                    insertJob((PersonJob) item, conn);
                } else {
                    throw new IllegalArgumentException("Invalid item type.");
                }
            }
        }
    }

    private void insertName(PersonName personName, Connection conn) throws SQLException {
        String sql = "insert into name (first_name, last_name) values (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, personName.getFirstName());
            ps.setString(2, personName.getLastName());
            ps.executeUpdate();
        }
    }

    private void insertDetails(PersonDetails personDetails, Connection conn) throws SQLException {
        String sql = "insert into details (address, mobile_Number) values (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, personDetails.getAddress());
            ps.setLong(2, personDetails.getMobileNumber());
            ps.executeUpdate();
        }
    }

    private void insertJob(PersonJob personJob, Connection conn) throws SQLException {
        String sql = "insert into job (job_Title, company, team) values (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, personJob.getJobTitle());
            ps.setString(2, personJob.getCompany());
            ps.setString(3, personJob.getTeam());
            ps.executeUpdate();
        }
    }
}


package com.batch.reader;

import org.springframework.batch.item.ItemReader;

import com.batch.model.PersonDetails;
import com.batch.model.PersonJob;
import com.batch.model.PersonName;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLItemReader<T> implements ItemReader<T> {

	private DataSource dataSource;
	private String query;
	private Class<T> targetClass;
	private ResultSet resultSet;
	private boolean executed = false;

	public MySQLItemReader(DataSource dataSource, String query, Class<T> targetClass) {
		this.dataSource = dataSource;
		this.query = query;
		this.targetClass = targetClass;
	}

	@Override
	public T read() throws Exception {
		if (!executed) {
			resultSet = dataSource.getConnection().createStatement().executeQuery(query);
			executed = true;
		}

		if (resultSet.next()) {
			return getMappedObject();
		}

		return null;
	}

	private T getMappedObject() throws SQLException {
		T mappedObject;
		try {
			mappedObject = targetClass.getDeclaredConstructor().newInstance();
			if (targetClass.equals(PersonName.class)) {
				PersonName personName = (PersonName) mappedObject;
				personName.setFirstName(resultSet.getString("first_Name"));
				personName.setLastName(resultSet.getString("last_Name"));
			} else if (targetClass.equals(PersonDetails.class)) {
				PersonDetails personDetails = (PersonDetails) mappedObject;
				personDetails.setAddress(resultSet.getString("address"));
				personDetails.setMobileNumber(resultSet.getLong("mobile_number"));
			} else if (targetClass.equals(PersonJob.class)) {
				PersonJob personJob = (PersonJob) mappedObject;
				personJob.setJobTitle(resultSet.getString("job_title"));
				personJob.setCompany(resultSet.getString("company"));
				personJob.setTeam(resultSet.getString("team"));
			} else {
				throw new IllegalArgumentException("Invalid target class.");
			}
			return mappedObject;
		} catch (Exception e) {
			throw new RuntimeException("Error mapping object.", e);
		}
	}
}

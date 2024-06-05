package org.example.dao;

import java.sql.*;
import java.util.ArrayList;

import org.example.dto.JobFilterDto;
import org.example.models.job;


public class JobDAO {

    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\HrApiDay5\\hr.db";
    private static final String SELECT_ALL_JOBS = "select * from jobs";
    private static final String SELECT_ONE_JOB = "select * from jobs where job_id = ?";
    private static final String SELECT_JOB_WITH_MIN = "select * from jobs where min_salary = ?";
    private static final String SELECT_JOB_WITH_MIN_PAGINATION = "select * from jobs where min_salary = ? order by job_id limit ? offset ?";
    private static final String SELECT_JOB_WITH_PAGINATION = "select * from jobs order by job_id limit ? offset ?";
    private static final String INSERT_JOB = "insert into jobs values (?, ?, ?, ?)";
    private static final String UPDATE_JOB = "update jobs set job_title = ?, min_salary = ?, max_salary = ? where job_id = ?";
    private static final String DELETE_JOB = "delete from jobs where job_id = ?";

    public void insertJob(job j) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(INSERT_JOB);
        st.setInt(1, j.getJob_id());
        st.setString(2, j.getJob_title());
        st.setDouble(3, j.getMin_salary());
        st.setDouble(4, j.getMax_salary());

        st.executeUpdate();
    }

    public void updateJob(job j) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(UPDATE_JOB);
        st.setInt(4, j.getJob_id());
        st.setString(1, j.getJob_title());
        st.setDouble(2, j.getMin_salary());
        st.setDouble(3, j.getMax_salary());
        st.executeUpdate();
    }

    public void deleteJob(int job_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(DELETE_JOB);
        st.setInt(1, job_id);
        st.executeUpdate();
    }

    public job selectJob(int job_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_JOB);
        st.setInt(1, job_id);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return new job(rs);
        }
        else {
            return null;//
        }
    }

    public ArrayList<job> selectAllJobs(Double minsal, Integer limit, int offset) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st ;
        if(minsal != null && limit != null) {
            st = conn.prepareStatement(SELECT_JOB_WITH_MIN_PAGINATION);
            st.setDouble(1, minsal);
            st.setInt(2, limit);
            st.setInt(3, offset);
        }
        else if(minsal != null) {
            st = conn.prepareStatement(SELECT_JOB_WITH_MIN);
            st.setDouble(1, minsal);
        }
        else if(limit != null) {
            st = conn.prepareStatement(SELECT_JOB_WITH_PAGINATION);
            st.setInt(1, limit);
            st.setInt(2, offset);
        }
        else {
            st = conn.prepareStatement(SELECT_ALL_JOBS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<job> jobs = new ArrayList<>();
        while (rs.next()) {
            jobs.add(new job(rs));
        }

        return jobs;
    }


    public ArrayList<job> selectAllJobs(JobFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st ;
        if(filter.getMinsal() != null && filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_JOB_WITH_MIN_PAGINATION);
            st.setDouble(1,filter.getMinsal() );
            st.setInt(2, filter.getLimit());
            st.setInt(3, filter.getOffset());
        }
        else if(filter.getMinsal() != null) {
            st = conn.prepareStatement(SELECT_JOB_WITH_MIN);
            st.setDouble(1, filter.getMinsal());
        }
        else if(filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_JOB_WITH_PAGINATION);
            st.setInt(1, filter.getLimit());
            st.setInt(2, filter.getOffset());
        }
        else {
            st = conn.prepareStatement(SELECT_ALL_JOBS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<job> jobs = new ArrayList<>();
        while (rs.next()) {
            jobs.add(new job(rs));
        }

        return jobs;
    }

}

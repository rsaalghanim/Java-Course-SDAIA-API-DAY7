package org.example.dao;

import org.example.dto.EmployeeFilterDto;
import  org.example.models.Employee;


import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO {

    private static final   String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\untitled9\\src\\main\\java\\HW4\\hr.db";
    private static final String INSERT_EMP = "insert into employees values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ONE_EMP = "select * from employees where employee_id = ?";
    private static final String SELECT_EMP_WITH_DEP = "select * from employees where department_id = ?";
    private static final String SELECT_EMP_WITH_DEP_PAGINATION = "select * from employees where department_id = ? order by employee_id limit ? offset ?";
    private static final String SELECT_EMP_WITH_PAGINATION = "select * from employees order by employee_id limit ? offset ?";
    private static final String SELECT_ALL_EMPS = "select * from employees";
    private static final String UPDATE_EMP = "update employees set email = ?, salary = ? where employee_id = ?";
    private static final String DELETE_EMP = "delete from employees where employee_id = ?";


    public void insertEmp(Employee e) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(INSERT_EMP);
        st.setInt(1, e.getEmployee_id());
        st.setString(2, e.getFirst_name());
        st.setString(3, e.getLast_name());
        st.setString(4, e.getEmail());
        st.setString(5, e.getNumber());
        st.setString(6, e.getHire_date());
        st.setInt(7, e.getJob_id());
        st.setDouble(8,e.getSalary());
        st.setInt(9, e.getManager_id());
        st.setInt(10, e.getDepartment_id());
        st.executeUpdate();
    }

    public void updateEmp(Employee e) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(UPDATE_EMP);
        st.setInt(3, e.getEmployee_id());
        st.setString(1, e.getEmail());
        st.setDouble(2, e.getSalary());
        st.executeUpdate();
    }

    public void deleteEmp(int employee_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(DELETE_EMP);
        st.setInt(1, employee_id);
        st.executeUpdate();
    }//

    public Employee selectEmp(int employee_id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_EMP);
        st.setInt(1, employee_id);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return new Employee(rs);
        }
        else {
            return null;
        }
    }

    public ArrayList<Employee> selectAllEmps(Integer depId, Integer limit, int offset) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st;
        if(depId != null && limit != null) {
            st = conn.prepareStatement(SELECT_EMP_WITH_DEP_PAGINATION);
            st.setInt(1, depId);
            st.setInt(2, limit);
            st.setInt(3, offset);
        }
        else if(depId != null) {
            st = conn.prepareStatement(SELECT_EMP_WITH_DEP);
            st.setInt(1, depId);
        }
        else if(limit != null) {
            st = conn.prepareStatement(SELECT_EMP_WITH_PAGINATION);
            st.setInt(1, limit);
            st.setInt(2, offset);
        }
        else {
            st = conn.prepareStatement(SELECT_ALL_EMPS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<Employee> emps = new ArrayList<>();
        while (rs.next()) {
            emps.add(new Employee(rs));
        }

        return emps;
    }

    public ArrayList<Employee> selectAllEmps(EmployeeFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st;
        if(filter.getDepId() != null && filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_EMP_WITH_DEP_PAGINATION);
            st.setInt(1, filter.getDepId());
            st.setInt(2, filter.getLimit());
            st.setInt(3, filter.getOffset());
        }
        else if(filter.getDepId() != null) {
            st = conn.prepareStatement(SELECT_EMP_WITH_DEP);
            st.setInt(1, filter.getDepId());
        }
        else if(filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_EMP_WITH_PAGINATION);
            st.setInt(1, filter.getLimit());
            st.setInt(2, filter.getOffset());
        }
        else {
            st = conn.prepareStatement(SELECT_ALL_EMPS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<Employee> emps = new ArrayList<>();
        while (rs.next()) {
            emps.add(new Employee(rs));
        }

        return emps;
    }

}

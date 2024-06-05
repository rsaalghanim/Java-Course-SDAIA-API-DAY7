package org.example.controller;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.dao.EmployeeDAO;
import org.example.dto.EmployeeDto;
import org.example.dto.EmployeeFilterDto;
import org.example.dto.JobDto;
import org.example.models.Employee;
import org.example.models.job;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/employees")
public class EmpController {

        EmployeeDAO dao = new EmployeeDAO();
    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;

        @GET
        @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
        public Response getAllEmployee(
                @BeanParam EmployeeFilterDto filter
        ) {

            try {
                GenericEntity<ArrayList<Employee>> emps = new GenericEntity<ArrayList<Employee>>(dao.selectAllEmps(filter)) {};
                if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                    return Response
                            .ok(emps)
                            .type(MediaType.APPLICATION_XML)
                            .build();
              //  return dao.selectAllJobs(minsal, limit, offset);
                //return dao.selectAllEmps(filter);
                //return dao.selectAllEmps();
                  }
                return Response
                        .ok(emps, MediaType.APPLICATION_JSON)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @GET
        @Path("{employee_id}")
        public Response getEmployee(
                @PathParam("employee_id") int employee_id) {

            try {
                Employee emps = dao.selectEmp(employee_id);
                if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                    return Response
                            .ok(emps)
                            .type(MediaType.APPLICATION_XML)
                            .build();
                } // return dao.selectEmp(employee_id);

                EmployeeDto dto = new EmployeeDto();
                dto.setEmployee_id(emps.getEmployee_id());
                dto.setFirst_name(emps.getFirst_name());
                dto.setLast_name(emps.getLast_name());
                dto.setEmail(emps.getEmail());
                dto.setNumber(emps.getNumber());
                dto.setHire_date(emps.getHire_date());
                dto.setJob_id(emps.getJob_id());
                dto.setSalary(emps.getSalary());
                dto.setManager_id(emps.getManager_id());
                dto.setDepartment_id(emps.getDepartment_id());
                addLinks(dto);
                return Response.ok(dto).build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    private void addLinks (EmployeeDto dto){
        URI selfUri = uriInfo.getAbsolutePath();
        URI empsUri = uriInfo.getAbsolutePathBuilder().path(EmpController.class).build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(empsUri.toString(),"employees");
    }

        @DELETE
        @Path("{employee_id}")
        public void deleteEmployee(@PathParam("employee_id") int employee_id) {

            try {
                dao.deleteEmp(employee_id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @POST
        @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
        public Response insertEmployee(Employee emps) {

            try {
                dao.insertEmp(emps);
                NewCookie cookie = (new NewCookie.Builder("username")).value("00000").build();
                URI uri = uriInfo.getAbsolutePathBuilder().path(emps.getEmployee_id()+"").build();
                return Response
                        .created(uri)
                        .cookie(cookie)
                        .header("Created by", "Wael")
                        .build();
               // dao.insertEmp(emps);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @PUT
        @Path("{employee_id}")
        public void updateEmployee(@PathParam("employee_id") int employee_id, Employee emps) {

            try {
                emps.setEmployee_id(employee_id);
                dao.updateEmp(emps);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }



}

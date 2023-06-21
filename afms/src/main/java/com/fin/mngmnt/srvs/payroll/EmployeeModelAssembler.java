package com.fin.mngmnt.srvs.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    /**
     * toModel() interface method a required to override,
     * is based on converting a non-model object (Employee)
     * into a model-based object (EntityModel<Employee>)
     * @param employee
     * @return
     */
    @Override
    public EntityModel<Employee> toModel(Employee employee) {

        return EntityModel.of(
            employee,
            linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
            linkTo(methodOn(EmployeeController.class).all()).withRel("all-employees"));
    }
}

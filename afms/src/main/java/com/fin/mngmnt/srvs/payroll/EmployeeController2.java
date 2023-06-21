package com.fin.mngmnt.srvs.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * a copy of EmployeeController.class to separate methods differences between
 * {@code} @GetMapping("/employees") vs @GetMapping("/employeesV2") {@code}
 * since Ambiguous method call on all()
 */
@RestController
class EmployeeController2 {

    private final EmployeeRepository repository;

    private final EmployeeModelAssembler assembler;

    EmployeeController2(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * the same as @GetMapping("/employees"),
     * just got to replace all that EntityModel<Employee>
     * creation logic with map(assembler::toModel)
     * @return
     */
    @GetMapping("/employeesV2")
    CollectionModel<EntityModel<Employee>> all() {

        List<EntityModel<Employee>> employees = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(
            employees,
            linkTo(methodOn(EmployeeController2.class).all()).withSelfRel());
    }
}

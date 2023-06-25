package com.fin.mngmnt.srvs.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * a copy of EmployeeController.class to separate methods differences between
 * {@code} @GetMapping("/employees") vs @GetMapping("/employeesV2") {@code}
 * since Ambiguous method call on all()
 *
 * Evolvable API with bare bones links.
 * To grow your API and better serve your clients,
 * you need to embrace the concept of
 * Hypermedia as the Engine of Application State. HATEOAS
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

    /**
     * Spring MVC's ResponseEntity is used to create an HTTP 201 Created status message.
     * This type of response typically includes a Location response header,
     * and we use the URI derived from the model’s self-related link.
     * @param newEmployee
     * @return model-based version of the saved object.
     */
    @PostMapping("/employeesV2")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

        EntityModel<Employee> employeeEntityModel = assembler.toModel(repository.save(newEmployee));

        return ResponseEntity
            .created(employeeEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(employeeEntityModel);
    }
    /*
    Invocation of init method failed; nested exception is java.lang.IllegalStateException:
    Ambiguous mapping. Cannot map 'employeeController2' method

    @PostMapping("<Should_be_a_unique_string>")
     */

    /**
     * Spring MVC ResponseEntity wrapper makes response return HTTP 204 No Content response
     * @param id
     * @return
     */
    @DeleteMapping("/employeeV2/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Since we want a more detailed HTTP response code than 200 OK,
     * we will use Spring MVC’s ResponseEntity wrapper.
     * It has a handy static method created() where we can plug in the resource’s URI.
     * It’s debatable if HTTP 201 Created carries the right semantics since
     * we aren’t necessarily "creating" a new resource.
     * But it comes pre-loaded with a Location response header
     * @param newEmployee
     * @param id
     * @return
     */
    @PutMapping("/employeesV2/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        //Employee built from save() operation
        Employee updatedEmployee = repository.findById(id)
            .map(employee -> {
                employee.setName(newEmployee.getName());
                employee.setRole(newEmployee.getRole());
                return repository.save(employee);
            })
            .orElseGet(() -> {
                newEmployee.setId(id);
                return repository.save(newEmployee);
            });

        //Wrapped using EmployeeModelAssembler into EntityModel
        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity
            //returns a Link which must be turned into a URI with the toUri method.
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

}

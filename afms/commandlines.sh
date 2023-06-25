# Spring Initializr uses maven wrapper, to start app
mvn clean spring-boot:run
# will yield list of employees
curl -v -X POST localhost:8080/employeesV2 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}' | json_pp
# show employee id 2
curl -v localhost:8080/employees/2
# To create a new Employee record
curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'
# update the employee and change his role
curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
# remove employee
curl -X DELETE localhost:8080/employees/3
# check if employee id 3 successfully deleted
curl localhost:8080/employees/3
# The indicated part pipes the output to json_pp and asks it to make your JSON pretty. (Or use whatever tool you like!)
##                                  v------------------v
curl -v localhost:8080/employees/1 | json_pp
# old way / api endpoint without using Spring HATEOAS (link, href, rel-based links ..etc) and -d as parameters is still using name, still works while adding firstname and lastname
curl -v -X POST localhost:8080/employees -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'
# new way / api endpoint with using Spring HATEOAS (link, href, rel-based links ..etc) @PostMapping("/employeesV2")
curl -v -X POST localhost:8080/employeesV2 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}' | json_pp
# remove with response returns an HTTP 204 No Content response.
curl -v -X DELETE localhost:8080/employees/1
#
curl -v -X PUT localhost:8080/employees/3 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
#

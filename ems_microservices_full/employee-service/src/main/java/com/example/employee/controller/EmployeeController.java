package com.example.employee.controller;
import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins="*")
public class EmployeeController {

    @Autowired 
    private EmployeeRepository repo;

    @GetMapping 
    public List<Employee> list(){ 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public ResponseEntity<Employee> get(@PathVariable Long id){ 
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); 
    }

    @PostMapping 
    public Employee create(@RequestBody Employee e){
         return repo.save(e); 
        }
    @PutMapping("/{id}") 
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee e){ 
        return repo.findById(id).map(existing->{ 
            existing.setName(e.getName());
             existing.setEmail(e.getEmail()); 
             existing.setDepartment(e.getDepartment()); 
             existing.setDesignation(e.getDesignation()); 
             existing.setPhone(e.getPhone()); 
             existing.setSalary(e.getSalary());
              repo.save(existing); 
              return ResponseEntity.ok(existing); 
            }).orElse(ResponseEntity.notFound().build()); 
        
        }
    @DeleteMapping("/{id}") 
    public ResponseEntity<?> delete(@PathVariable Long id){ 
        return repo.findById(id).map(existing->{ repo.delete(existing); return ResponseEntity.ok(Map.of("status","deleted")); 
    }).orElse(ResponseEntity.notFound().build());
 }
}

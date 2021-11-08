package com.example.redis_18049511_hien.repository;

import com.example.redis_18049511_hien.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {
    //private HashOperations hashOperations;//crud hash
    private ListOperations listOperations;


    private RedisTemplate redisTemplate;
    public EmployeeRepository(RedisTemplate redisTemplate) {
       // this.hashOperations = redisTemplate.opsForHash();
        this.listOperations =redisTemplate.opsForList();
        this.redisTemplate = redisTemplate;

    }
    public void saveEmployee(Employee employee){

        //hashOperations.put("EMPLOYEE", employee.getId(), employee);
        listOperations.leftPush("EMPLOYEE_LIST", employee.getId(), employee);
    }
    public List<Employee> findAll(){

        //return hashOperations.values("EMPLOYEE");
        return listOperations.range("EMPLOYEE_LIST",0,listOperations.size("EMPLOYEE"));
    }
    public Employee findById(Integer id){

        //return (Employee) hashOperations.get("EMPLOYEE", id);
        List<Employee> list = listOperations.range("EMPLOYEE_LIST", 0, listOperations.size("EMPLOYEE"));
        for (Employee employee : list) {
            if(employee.getId() == id)
                return employee;
        }
        return null;
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
        //hashOperations.delete("EMPLOYEE", id);
        listOperations.remove("EMPLOYEE_LIST", 1, findById(id));
    }
}

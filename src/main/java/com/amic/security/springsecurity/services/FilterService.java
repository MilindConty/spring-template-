package com.amic.security.springsecurity.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilterService {

    public void filter(){
        Map<String, Object> obj1  = new HashMap<>();
        obj1.put("id", 1);
        obj1.put("name", "name1");
        obj1.put("dept", "IT");
        obj1.put("sex", "M");
        obj1.put("salary", 30000);

        Map<String, Object> obj2  = new HashMap<>();
        obj2.put("id", 2);
        obj2.put("name", "name2");
        obj2.put("dept", "IT");
        obj2.put("sex", "M");
        obj2.put("salary", 10000);

        Map<String, Object> obj4 = new HashMap<>();
        obj4.put("id", 4);
        obj4.put("name", "name4");
        obj4.put("dept", "IT");
        obj4.put("sex", "F");
        obj4.put("salary", 40000);

        Map<String, Object> obj3 = new HashMap<>();
        obj3.put("id", 3);
        obj3.put("name", "name3");
        obj3.put("dept", "DEV");
        obj3.put("sex", "F");
        obj3.put("salary", 40000);

        ArrayList<Map<String, Object>> employees = new ArrayList<>(Arrays.asList(obj1,obj2,obj3,obj4));

        Map<String, Object> filterCondition = new HashMap<>();
        filterCondition.put("dept", "IT");
        filterCondition.put("sex", "*");
        filterCondition.put("salary", 15000);

        List<Map<String, Object>> filteredEmployee = new ArrayList<>();

        List<Map<String, Object>> collect = employees.parallelStream().filter(employee -> isValid(filterCondition, employee)).toList();
//        for(Map<String,Object> employee:employees){
//            if(isValid(filterCondition, employee)){
//                filteredEmployee.add(employee);
//            }
//        }


//        System.out.println(filteredEmployee);
        System.out.println(collect);
    }

    private boolean isValid(Map<String, Object> filterCondition, Map<String, Object> employee) {
        for(Map.Entry<String, Object> filterEntry:filterCondition.entrySet()){
            if(employee.get(filterEntry.getKey()).getClass().equals(Integer.class)) {
                Integer valueToCompare = (Integer) employee.get(filterEntry.getKey());
                Integer valueWithCompare = (Integer) filterEntry.getValue();
                if(valueToCompare < valueWithCompare)
                    return false;
            } else if (!employee.get(filterEntry.getKey()).equals(filterEntry.getValue())){
                if(filterEntry.getValue().equals("*"))
                    continue;
                return false;
            }
        }
        return true;
    }

}

package com.amic.security.springsecurity.services;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilterService {

    public void filter(ArrayList<Map<String, Object>> employees,ArrayList<Map<String, Object>> filterCondition ){

        List<Map<String, Object>> filteredEmployee = Collections.synchronizedList(new ArrayList<>());


//        List<Map<String, Object>> collect = employees.stream().filter(employee -> isValid(filterCondition, employee,filteredEmployee)).toList();
//        for(Map<String,Object> employee:employees){
//            if(isValid(filterCondition, employee)){
//                filteredEmployee.add(employee);
//            }
//        }

        employees.parallelStream().forEach(employee -> applyRulesToList(filterCondition, employee,filteredEmployee));
//        System.out.print("FilteredEmployee");
//        System.out.println(filteredEmployee);

        System.out.println(filteredEmployee.toArray().length);
//        System.out.println(collect);
    }

    private void applyRulesToList(ArrayList<Map<String, Object>> filterConditions,
                                  Map<String, Object> employee,
                                  List<Map<String, Object>> filteredEmployee) {

        List<Map<String, Object>> rulesForApplication =
                filterConditions.stream().filter(rule -> isValidRule(rule, employee)).toList();

//                getRulesForApplication(employee, filterConditions);
//        List<Map<String, Object>> rulelist = filterConditions
//                .stream()
//                .filter(
//                        rule -> (rule.get("dept").equals(employee.get("dept")) ||
//                                rule.get("dept").equals("*"))
//                                && (rule.get("sex").equals(employee.get("sex")) ||
//                                rule.get("sex").equals("*"))
//                ).toList();

//        System.out.print("Employee :");
//        System.out.println(employee);
//        System.out.print("Rule :");
//        System.out.println(rulelist);

//        System.out.println("--------------------------------");

        boolean condition;

        for(Map<String, Object> filterCondition : rulesForApplication){
            condition = true;
            for(Map.Entry<String, Object> filterEntry:filterCondition.entrySet()) {
                if (!filterEntry.getKey().equals("ruleId")) {
                    if (employee.get(filterEntry.getKey()).getClass().equals(Integer.class)) {
                        Integer valueToCompare = (Integer) employee.get(filterEntry.getKey());
                        Integer valueWithCompare = (Integer) filterEntry.getValue();
                        if (valueToCompare < valueWithCompare) {
                            condition = false;
                        }
                    } else if ((!employee.get(filterEntry.getKey()).equals(filterEntry.getValue())) && (!filterEntry.getValue().equals("*"))) {
                        condition = false;
                    }
                }
            }

            if(condition) {
                // add to ArrayList
                if(employee.get("ruleId") == null) {
                    employee.put("ruleId", filterCondition.get("ruleId"));
                    filteredEmployee.add(employee);
                }
            }
        }
    }

    private boolean isValidRule(Map<String, Object> rule, Map<String, Object> employee) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("dept");
        columns.add("sex");

        HashSet<Map.Entry<String, Object>> collect = employee.entrySet().stream().filter(s -> columns.contains(s.getKey())).collect(Collectors.toCollection(HashSet::new));

        for(Map.Entry<String, Object> filterEntry:collect) {
                if ((!rule.get(filterEntry.getKey()).equals(filterEntry.getValue())) && (!rule.get(filterEntry.getKey()).equals("*"))) {
                    return false;
                }
        }
        return true;
    }

//    private List<Map<String, Object>> getRulesForApplication(Map<String, Object> employee, ArrayList<Map<String, Object>> filterConditions) {
//
//
//    }


//    private boolean isValid(ArrayList<Map<String, Object>> filterConditions,
//                            Map<String, Object> employee,
//                            ArrayList<Map<String, Object>> list) {
//
//        List<Map<String, Object>> rulelist = filterConditions.stream().filter(
//                rule -> (rule.get("dept").equals(employee.get("dept")) ||
//                rule.get("dept").equals("*"))
//                && (rule.get("sex").equals(employee.get("sex")) || rule.get("sex").equals("*"))
//        ).toList();
//
//        System.out.print("Employee :");
//        System.out.println(employee);
//        System.out.print("Rule :");
//        System.out.println(rulelist);
//
//        System.out.println("--------------------------------");
//
//        boolean condition = true;
//
//        for(Map<String, Object> filterCondition : rulelist){
//            condition = true;
//            for(Map.Entry<String, Object> filterEntry:filterCondition.entrySet()) {
//                if (!filterEntry.getKey().equals("ruleId")) {
//                    if (employee.get(filterEntry.getKey()).getClass().equals(Integer.class)) {
//                        Integer valueToCompare = (Integer) employee.get(filterEntry.getKey());
//                        Integer valueWithCompare = (Integer) filterEntry.getValue();
//                        if (valueToCompare < valueWithCompare) {
//                            condition = false;
//                        }
//                    } else if ((!employee.get(filterEntry.getKey()).equals(filterEntry.getValue())) && (!filterEntry.getValue().equals("*"))) {
//                        condition = false;
//                    }
//                }
//            }
//
//            if(condition) {
//                // add to ArrayList
//                if(employee.get("ruleId") == null) {
//                    employee.put("ruleId", filterCondition.get("ruleId"));
//                    list.add(employee);
//                }
//            }
//        }
//
//
////        for(Map.Entry<String, Object> filterEntry:filterCondition.entrySet()){
////            if(employee.get(filterEntry.getKey()).getClass().equals(Integer.class)) {
////                Integer valueToCompare = (Integer) employee.get(filterEntry.getKey());
////                Integer valueWithCompare = (Integer) filterEntry.getValue();
////                if(valueToCompare < valueWithCompare)
////                    return false;
////            } else if ((!employee.get(filterEntry.getKey()).equals(filterEntry.getValue())) && (!filterEntry.getValue().equals("*"))){
////                return false;
////            }
////        }
//        return true;
//    }

}

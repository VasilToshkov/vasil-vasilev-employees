package com.example.employees_backend.service;

import com.example.employees_backend.converter.LocalDateParser;
import com.example.employees_backend.exception.exceptions.CorruptedDataException;
import com.example.employees_backend.model.CommonProjectsMap;
import com.example.employees_backend.model.Employee;
import com.example.employees_backend.model.EmployeePair;
import com.example.employees_backend.model.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.DataFormatException;

@Service
public class EmployeeService {

    private final LocalDateParser parser;

    public EmployeeService(LocalDateParser parser) {
        this.parser = parser;
    }

    public ResponseEntity<?> getAllCommonProjects(MultipartFile file) throws IOException, DataFormatException {
        Map<EmployeePair, CommonProjectsMap> results = getPairCommonProjectsMapMap(file);
        return ResponseEntity.ok(toEmployeeResponseList(results));
    }

    public ResponseEntity<EmployeeResponse> getPairWithMostCommonDays(MultipartFile file) throws IOException {
        Map<EmployeePair, CommonProjectsMap> results = getPairCommonProjectsMapMap(file);
        return toEmployeeResponse(results);
    }

    private Map<EmployeePair, CommonProjectsMap> getPairCommonProjectsMapMap(MultipartFile file) throws IOException {
        List<Employee> employees = csvToList(file);
        Map<EmployeePair, CommonProjectsMap> results = new HashMap<>();
        for (int i = 0; i < employees.size(); i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                Employee firstEmployee = employees.get(i);
                Employee secondEmployee = employees.get(j);
                Long commonDays = getOverlapDays(firstEmployee, secondEmployee);
                if (firstEmployee.getProjectId().equals(secondEmployee.getProjectId()) &&  commonDays > 0
                        && !firstEmployee.equals(secondEmployee)) {
                    addEmployeePair(firstEmployee, secondEmployee, results, commonDays);
                }
            }
        }
        return results;
    }

    private Object toEmployeeResponseList(Map<EmployeePair, CommonProjectsMap> results) {
        List<EmployeeResponse> response = new ArrayList<>();
        for (Entry<EmployeePair, CommonProjectsMap> entry :results.entrySet()) {
            EmployeePair pair = entry.getKey();
            response.add(new EmployeeResponse(pair.getFirstEmployeeId(),pair.getSecondEmployeeId(),entry.getValue()));
        }
        return  response;
    }

    private ResponseEntity<EmployeeResponse> toEmployeeResponse(Map<EmployeePair, CommonProjectsMap> results) {
        EmployeeResponse bestPair = null;
        for (Entry<EmployeePair, CommonProjectsMap> entry :results.entrySet()) {
            EmployeePair pair = entry.getKey();
            int maxCommonDays = 0;
            if (entry.getValue().getTotalCommonDays() > maxCommonDays) {
                bestPair = new EmployeeResponse(pair.getFirstEmployeeId(),pair.getSecondEmployeeId(),entry.getValue());
            }
        }
        return  Optional.ofNullable(bestPair)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    private void addEmployeePair(Employee firstEmployee, Employee secondEmployee, Map<EmployeePair, CommonProjectsMap> results,
                                 Long commonDays) {
        EmployeePair pairKey;
        pairKey = createPair(firstEmployee, secondEmployee);
        if (results.containsKey(pairKey)){
            changeValue(results.get(pairKey),firstEmployee.getProjectId(), commonDays, results);
        }
        else {
            Map<String, Long> map = new HashMap<>();
            map.put(firstEmployee.getProjectId(), commonDays);
            results.put(createPair(firstEmployee, secondEmployee),
                    new CommonProjectsMap(commonDays, map));
        }
    }

    private EmployeePair createPair(Employee firstEmployee, Employee secondEmployee) {
        EmployeePair pairKey;
        if (firstEmployee.getEmplId() < secondEmployee.getEmplId()){
            pairKey = new EmployeePair(firstEmployee.getEmplId(), secondEmployee.getEmplId());
        }
        else {
            pairKey = new EmployeePair(secondEmployee.getEmplId(), firstEmployee.getEmplId());
        }
        return pairKey;
    }

    private void changeValue(CommonProjectsMap commonProjectsMap, String projectId, Long commonDays, Map<EmployeePair, CommonProjectsMap> results) {
        commonProjectsMap.setTotalCommonDays(commonProjectsMap.getTotalCommonDays() + commonDays);
        if (commonProjectsMap.getCommonProjectsAndDays().containsKey(projectId)) {
            commonProjectsMap.getCommonProjectsAndDays().compute(projectId, (key, value) -> value += commonDays);
        }
        else {
            commonProjectsMap.getCommonProjectsAndDays().put(projectId, commonDays);
        }

    }

    private Long getOverlapDays(Employee firstEmployee, Employee secondEmployee) {

        LocalDate latestStart = firstEmployee.getDateFrom().isAfter(secondEmployee.getDateFrom())
                ? firstEmployee.getDateFrom() : secondEmployee.getDateFrom();
        LocalDate earliestEnd = firstEmployee.getDateTo().isBefore(secondEmployee.getDateTo())
                ? firstEmployee.getDateTo() : secondEmployee.getDateTo();

        return latestStart.isBefore(earliestEnd) || latestStart.isEqual(earliestEnd)
                ? ChronoUnit.DAYS.between(latestStart, earliestEnd) + 1 //include the final common day
                : 0;
    }

    //parse the file to List of employees
    private List<Employee> csvToList(MultipartFile file) throws IOException {
        List<Employee> emplyees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip headers
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length >= 3) {
                    try {
                        Long employeeId = Long.parseLong(fields[0].trim());
                        String projectId = fields[1].trim();
                        LocalDate dateFrom = parser.parseLocalDate(fields[2].trim());
                        LocalDate dateTo = fields.length == 3 || fields[3].trim().equalsIgnoreCase("NULL") ? LocalDate.now() : parser.parseLocalDate(fields[3].trim());
                        emplyees.add(new Employee(employeeId, projectId, dateFrom, dateTo));
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return emplyees;
    }
}

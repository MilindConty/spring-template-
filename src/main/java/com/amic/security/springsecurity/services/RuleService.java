package com.amic.security.springsecurity.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RuleService {

    public ArrayList<Map<String, Object>> getRule() throws FileNotFoundException {
        Reader read = new FileReader("src/main/resources/static/Rule.csv");
        try (BufferedReader br = new BufferedReader(read)) {
            String[] headers = br.readLine().split(",");
            headers = new String[]{"ruleId","dept","sex","salary"};
            String[] finalHeaders = headers;
            List<Map<String, Object>> records =
                    br.lines().map(s -> s.split(","))
                            .map(t -> IntStream.range(0, t.length)
                                    .boxed()
                                    .collect(Collectors.toMap(i -> finalHeaders[i], i -> (Object)(i == 3 ? Integer.parseInt(t[i]) :t[i]))))
                            .toList();
            //            System.out.println(records);
            return new ArrayList<>(records);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

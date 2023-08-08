package com.amic.security.springsecurity.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ParserService {

    @Value("${csv.file.name}")
    public String fileName;

    public ArrayList<Map<String, Object>> getFileData() throws FileNotFoundException {
        Reader read = new FileReader("src/main/resources/static/Data.csv");
        try (BufferedReader br = new BufferedReader(read)) {
            String[] headers = br.readLine().split(",");
            headers = new String[]{"id","name","dept","sex","salary"};
            String[] finalHeaders = headers;

//            String[] splitValues = br.readLine().split(",");
//            Map<String, Object> obj = new HashMap<>();
//            obj.put(headers[0], splitValues[0]);
//            obj.put(headers[1], splitValues[1]);
//            obj.put(headers[2], splitValues[2]);
//            obj.put(headers[3], splitValues[3]);
//            obj.put(headers[4], Integer.parseInt(splitValues[4]));


            List<Map<String, Object>> records =
                    br.lines().map(s -> s.split(","))
                            .map(t -> IntStream.range(0, t.length)
                                    .boxed()
                                    .collect(Collectors.toMap(i -> finalHeaders[i], i -> (Object)(i == 4 ? Integer.parseInt(t[i]) :t[i]))))
                            .collect(Collectors.toList());
            ArrayList<Map<String, Object>> arrayList = new ArrayList<>(records);
//            System.out.println(headers);
            System.out.println(records);
            return arrayList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

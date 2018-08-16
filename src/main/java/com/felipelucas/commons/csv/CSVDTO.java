package com.felipelucas.commons.csv;

import java.util.Arrays;
import java.util.LinkedList;
import org.apache.commons.lang3.StringUtils;

public class CSVDTO {

    LinkedList<LinkedList<String>> csvData;

    private CSVDTO(){
        csvData = new LinkedList<>();
    }

    public static CSVDTO build(){
        return new CSVDTO();
    }

    public CSVDTO addNewLine(){
        this.csvData.add(new LinkedList<>());
        return this;
    }

    public CSVDTO addLineInfo(String data){
        if (StringUtils.isNotEmpty(data)) {
            String[] split = data.split(",");
            Arrays.stream(split).forEach(this.csvData.getLast()::add);
        }
        return this;
    }

    public LinkedList<LinkedList<String>> getCsvData(){
        return (LinkedList<LinkedList<String>>) this.csvData.clone();
    }

}

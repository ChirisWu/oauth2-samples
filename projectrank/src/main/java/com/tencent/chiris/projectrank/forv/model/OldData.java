package com.tencent.chiris.projectrank.forv.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OldData {
    private String companyName;
    List<Double> data = new ArrayList<>(6);
}

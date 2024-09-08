package com.lioneats.lioneats_backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MRTDTO {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private List<String> lines;
    private double distance;

}


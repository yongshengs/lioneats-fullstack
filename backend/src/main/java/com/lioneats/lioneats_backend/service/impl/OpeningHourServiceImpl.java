package com.lioneats.lioneats_backend.service.impl;

import com.lioneats.lioneats_backend.repository.OpeningHourRepository;
import com.lioneats.lioneats_backend.service.OpeningHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpeningHourServiceImpl implements OpeningHourService {

    @Autowired
    private OpeningHourRepository openingHourRepository;

}
package com.lioneats.lioneats_backend.mapper.google;

import com.lioneats.lioneats_backend.dto.google.OpeningHourDTO;
import com.lioneats.lioneats_backend.model.Shop;
import com.lioneats.lioneats_backend.model.google.OpeningHour;
import com.lioneats.lioneats_backend.model.google.WeekdayText;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OpeningHourMapper {

    public static OpeningHourDTO toDTO(List<OpeningHour> openingHours) {
        if (openingHours == null || openingHours.isEmpty()) {
            return null;
        }

        OpeningHourDTO openingHourDTO = new OpeningHourDTO();

        openingHourDTO.setWeekdayText(
                openingHours.get(0).getWeekdayText().stream()
                        .map(WeekdayText::getText)
                        .collect(Collectors.toList())
        );

        return openingHourDTO;
    }

    public static List<OpeningHour> toEntity(OpeningHourDTO openingHourDTO, Shop shop) {
        if (openingHourDTO == null) {
            return null;
        }

        OpeningHour openingHour = new OpeningHour();
        updateOpeningHourFromDto(openingHourDTO, openingHour);
        openingHour.setShop(shop);

        return List.of(openingHour);
    }

    public static void updateOpeningHourFromDto(OpeningHourDTO openingHourDTO, OpeningHour openingHour) {
        if (openingHourDTO == null || openingHour == null) {
            return;
        }

        List<WeekdayText> weekdayTextList = openingHourDTO.getWeekdayText().stream()
                .map(text -> {
                    WeekdayText weekdayText = new WeekdayText();
                    weekdayText.setText(text);
                    weekdayText.setOpeningHour(openingHour);
                    return weekdayText;
                })
                .collect(Collectors.toList());

        openingHour.setWeekdayText(weekdayTextList);
    }
}

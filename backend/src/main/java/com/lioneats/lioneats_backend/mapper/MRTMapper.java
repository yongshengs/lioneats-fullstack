package com.lioneats.lioneats_backend.mapper;

import com.lioneats.lioneats_backend.dto.MRTDTO;
import com.lioneats.lioneats_backend.model.MRT;

public class MRTMapper {

    public static MRTDTO toDTO(MRT mrt) {
        if (mrt == null) {
            return null;
        }

        MRTDTO mrtDTO = new MRTDTO();
        mrtDTO.setId(mrt.getId());
        mrtDTO.setName(mrt.getName());
        mrtDTO.setLatitude(mrt.getLatitude());
        mrtDTO.setLongitude(mrt.getLongitude());
        mrtDTO.setLines(mrt.getLines());

        return mrtDTO;
    }

    public static MRT toEntity(MRTDTO mrtDTO) {
        if (mrtDTO == null) {
            return null;
        }

        MRT mrt = new MRT();
        mrt.setId(mrtDTO.getId());
        mrt.setName(mrtDTO.getName());
        mrt.setLatitude(mrtDTO.getLatitude());
        mrt.setLongitude(mrtDTO.getLongitude());
        mrt.setLines(mrtDTO.getLines());

        return mrt;
    }
}


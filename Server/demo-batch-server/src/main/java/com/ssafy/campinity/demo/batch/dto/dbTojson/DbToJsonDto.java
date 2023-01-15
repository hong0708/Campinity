package com.ssafy.campinity.demo.batch.dto.dbTojson;


import com.ssafy.campinity.core.entity.campsite.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DbToJsonDto {

    private List<Campsite> campsite;
    private List<Amenity> amenity;
    private List<CaravanFclty> caravanFclty;
    private List<GlampFclty> glampFclty;
    private List<Industry> industry;
    private List<OpenSeason> openSeason;
    private List<Theme> theme;
    private List<CampsiteAndAmenity> campsiteAndAmenity;
    private List<CampsiteAndCaravanFclty> campsiteAndCaravanFclty;
    private List<CampsiteAndGlampFclty> campsiteAndGlampFclty;
    private List<CampsiteAndIndustry> campsiteAndIndustry;
    private List<CampsiteAndOpenSeason> campsiteAndOpenSeason;
    private List<CampsiteAndTheme> campsiteAndTheme;

    @Builder
    public DbToJsonDto(List<Campsite> campsite, List<Amenity> amenity, List<CaravanFclty> caravanFclty, List<GlampFclty> glampFclty, List<Industry> industry, List<OpenSeason> openSeason, List<Theme> theme, List<CampsiteAndAmenity> campsiteAndAmenity, List<CampsiteAndCaravanFclty> campsiteAndCaravanFclty, List<CampsiteAndGlampFclty> campsiteAndGlampFclty, List<CampsiteAndIndustry> campsiteAndIndustry, List<CampsiteAndOpenSeason> campsiteAndOpenSeason, List<CampsiteAndTheme> campsiteAndTheme) {
        this.campsite = campsite;
        this.amenity = amenity;
        this.caravanFclty = caravanFclty;
        this.glampFclty = glampFclty;
        this.industry = industry;
        this.openSeason = openSeason;
        this.theme = theme;
        this.campsiteAndAmenity = campsiteAndAmenity;
        this.campsiteAndCaravanFclty = campsiteAndCaravanFclty;
        this.campsiteAndGlampFclty = campsiteAndGlampFclty;
        this.campsiteAndIndustry = campsiteAndIndustry;
        this.campsiteAndOpenSeason = campsiteAndOpenSeason;
        this.campsiteAndTheme = campsiteAndTheme;
    }
}

package com.unipi.konlamp.rallyepulse_app.API;

import java.io.Serializable;

public class TimeKeepingid implements Serializable {
    private Long competitorid;

    public TimeKeepingid(Long competitorid, Long specialstageid) {
        this.competitorid = competitorid;
        this.specialstageid = specialstageid;
    }

    private Long specialstageid;

    public Long getCompetitorid() {
        return competitorid;
    }

    public void setCompetitorid(Long competitorid) {
        this.competitorid = competitorid;
    }

    public Long getSpecialstageid() {
        return specialstageid;
    }

    public void setSpecialstageid(Long specialstageid) {
        this.specialstageid = specialstageid;
    }
}


package org.ei.drishti.controller;

import org.ei.drishti.scheduler.router.Action;
import org.ei.drishti.scheduler.router.AlertRouter;
import org.ei.drishti.scheduler.router.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static org.ei.drishti.scheduler.DrishtiScheduleConstants.ChildScheduleConstants.*;
import static org.ei.drishti.scheduler.DrishtiScheduleConstants.MotherScheduleConstants.*;
import static org.ei.drishti.scheduler.DrishtiScheduleConstants.ECSchedulesConstants.*;
import static org.ei.drishti.scheduler.router.Matcher.*;
import static org.motechproject.scheduletracking.api.domain.WindowName.*;

@Component
public class AlertController {
    @Autowired
    public AlertController(AlertRouter router, @Qualifier("ANMGroupSMSAction") Action anmGroupSMS,
                           @Qualifier("ForceFulfillAction") Action forceFulfill, @Qualifier("AlertCreationAction") Action alertCreation) {
        router.addRoute(eq(SCHEDULE_ANC), any(), eq(max.toString()), forceFulfill);
        router.addRoute(eq(SCHEDULE_LAB), any(), eq(max.toString()), forceFulfill);
        router.addRoute(motherSchedules(), any(), anyOf(due.toString(), late.toString()), alertCreation).addExtraData("beneficiaryType", "mother");
        router.addRoute(childSchedules(), any(), anyOf(due.toString(), late.toString()), alertCreation).addExtraData("beneficiaryType", "child");
        router.addRoute(ecSchedules(), any(), anyOf(late.toString()), alertCreation).addExtraData("beneficiaryType", "ec");
        router.addRoute(any(), any(), any(), anmGroupSMS);
    }

    private Matcher childSchedules() {
        return anyOf(CHILD_SCHEDULE_BCG,

                CHILD_SCHEDULE_DPT1,
                CHILD_SCHEDULE_DPT2,
                CHILD_SCHEDULE_DPT3,
                CHILD_SCHEDULE_DPT_BOOSTER1,
                CHILD_SCHEDULE_DPT_BOOSTER2,

                CHILD_SCHEDULE_HEPATITIS,

                CHILD_SCHEDULE_MEASLES,
                CHILD_SCHEDULE_MEASLES_BOOSTER,

                CHILD_SCHEDULE_OPV);
    }

    private Matcher motherSchedules() {
        return anyOf(SCHEDULE_ANC, SCHEDULE_TT, SCHEDULE_IFA, SCHEDULE_LAB, SCHEDULE_EDD);
    }

    private Matcher ecSchedules() {
        return anyOf(EC_SCHEDULE_FP_COMPLICATION);
    }

}

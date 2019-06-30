package com.sofrecom.sofid.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by oibnchahdia on 10/04/2019
 */
public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}

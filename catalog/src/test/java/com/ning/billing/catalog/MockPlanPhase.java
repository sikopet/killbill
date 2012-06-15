/*
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.catalog;

import javax.annotation.Nullable;

import com.ning.billing.catalog.api.BillingPeriod;
import com.ning.billing.catalog.api.PhaseType;
import com.ning.billing.catalog.api.Plan;
import com.ning.billing.catalog.api.TimeUnit;

public class MockPlanPhase extends DefaultPlanPhase {

    public static MockPlanPhase create1USDMonthlyEvergreen() {
        return (MockPlanPhase) new MockPlanPhase(BillingPeriod.MONTHLY,
                                                 PhaseType.EVERGREEN,
                                                 new DefaultDuration().setUnit(TimeUnit.UNLIMITED),
                                                 MockInternationalPrice.create1USD(),
                                                 null).setPlan(MockPlan.createBicycleNoTrialEvergreen1USD());
    }

    public static MockPlanPhase createUSDMonthlyEvergreen(final String reccuringUSDPrice, final String fixedPrice) {
        return new MockPlanPhase(BillingPeriod.MONTHLY,
                                 PhaseType.EVERGREEN,
                                 new DefaultDuration().setUnit(TimeUnit.UNLIMITED),
                                 (reccuringUSDPrice == null) ? null : MockInternationalPrice.createUSD(reccuringUSDPrice),
                                 (fixedPrice == null) ? null : MockInternationalPrice.createUSD(fixedPrice));
    }

    public static MockPlanPhase createUSDMonthlyFixedTerm(final String reccuringUSDPrice, final String fixedPrice, final int durationInMonths) {
        return new MockPlanPhase(BillingPeriod.MONTHLY,
                                 PhaseType.FIXEDTERM,
                                 new DefaultDuration().setUnit(TimeUnit.MONTHS).setNumber(durationInMonths),
                                 (reccuringUSDPrice == null) ? null : MockInternationalPrice.createUSD(reccuringUSDPrice),
                                 (fixedPrice == null) ? null : MockInternationalPrice.createUSD(fixedPrice));
    }

    public static MockPlanPhase create30DayTrial() {
        return createTrial(30);
    }

    public static MockPlanPhase createTrial(final int days) {
        return new MockPlanPhase(BillingPeriod.NO_BILLING_PERIOD,
                                 PhaseType.TRIAL,
                                 new DefaultDuration().setUnit(TimeUnit.DAYS).setNumber(days),
                                 null,
                                 MockInternationalPrice.create1USD()
        );
    }

    public MockPlanPhase(
            final BillingPeriod billingPeriod,
            final PhaseType type,
            final DefaultDuration duration,
            final DefaultInternationalPrice recurringPrice,
            final DefaultInternationalPrice fixedPrice) {
        setBillingPeriod(billingPeriod);
        setPhaseType(type);
        setDuration(duration);
        setRecurringPrice(recurringPrice);
        setFixedPrice(fixedPrice);
    }


    public MockPlanPhase() {
        this(new MockInternationalPrice(), null);
    }

    public MockPlanPhase(@Nullable final MockInternationalPrice recurringPrice,
                         @Nullable final MockInternationalPrice fixedPrice) {
        this(recurringPrice, fixedPrice, BillingPeriod.MONTHLY);
    }

    public MockPlanPhase(@Nullable final MockInternationalPrice recurringPrice,
                         @Nullable final MockInternationalPrice fixedPrice,
                         final BillingPeriod billingPeriod) {
        this(recurringPrice, fixedPrice, billingPeriod, PhaseType.EVERGREEN);
    }

    public MockPlanPhase(@Nullable final MockInternationalPrice recurringPrice,
                         @Nullable final MockInternationalPrice fixedPrice,
                         final BillingPeriod billingPeriod,
                         final PhaseType phaseType) {
        setBillingPeriod(billingPeriod);
        setPhaseType(phaseType);
        setDuration(new DefaultDuration().setNumber(-1).setUnit(TimeUnit.UNLIMITED));
        setRecurringPrice(recurringPrice);
        setFixedPrice(fixedPrice);
        setPlan(new MockPlan(this));
    }

    public MockPlanPhase(final MockPlan mockPlan) {
        setBillingPeriod(BillingPeriod.MONTHLY);
        setPhaseType(PhaseType.EVERGREEN);
        setDuration(new DefaultDuration().setNumber(-1).setUnit(TimeUnit.UNLIMITED));
        setRecurringPrice(new MockInternationalPrice());
        setFixedPrice(null);
        setPlan(mockPlan);
    }

    public MockPlanPhase(final Plan plan, final PhaseType phaseType) {
        setBillingPeriod(BillingPeriod.MONTHLY);
        setPhaseType(phaseType);
        setDuration(new DefaultDuration().setNumber(-1).setUnit(TimeUnit.UNLIMITED));
        setRecurringPrice(new MockInternationalPrice());
        setFixedPrice(null);
        setPlan(plan);
    }
}

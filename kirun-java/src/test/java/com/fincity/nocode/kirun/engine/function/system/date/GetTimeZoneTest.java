package com.fincity.nocode.kirun.engine.function.system.date;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fincity.nocode.kirun.engine.repository.reactive.KIRunReactiveFunctionRepository;
import com.fincity.nocode.kirun.engine.repository.reactive.KIRunReactiveSchemaRepository;
import com.fincity.nocode.kirun.engine.runtime.reactive.ReactiveFunctionExecutionParameters;
import com.google.gson.JsonPrimitive;

import reactor.test.StepVerifier;

class GetTimeZoneTest {

    GetTimeZone gt = new GetTimeZone();
    ReactiveFunctionExecutionParameters rfep = new ReactiveFunctionExecutionParameters(
            new KIRunReactiveFunctionRepository(), new KIRunReactiveSchemaRepository());

    @Test
    void test() {

        rfep.setArguments(Map.of("isodate", new JsonPrimitive("1994-10-24T02:10:30.700+05:00")));

        StepVerifier.create(gt.execute(rfep))
                .expectNextMatches(
                        r -> r.allResults().get(0).getResult().get("timeZone").getAsString().equals("GMT+05:00"))
                .verifyComplete();

    }

    @Test
    void test2() {

        rfep.setArguments(Map.of("isodate", new JsonPrimitive("1994-10-24T02:10:30.70Z")));

        StepVerifier.create(gt.execute(rfep))
                .expectNextMatches(r -> r.allResults().get(0).getResult().get("timeZone").getAsString().equals("UTC"))
                .verifyComplete();

    }

}

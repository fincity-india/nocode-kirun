package com.fincity.nocode.kirun.engine.function.system.date;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fincity.nocode.kirun.engine.namespaces.Namespaces;
import com.fincity.nocode.kirun.engine.repository.reactive.KIRunReactiveFunctionRepository;
import com.fincity.nocode.kirun.engine.repository.reactive.KIRunReactiveSchemaRepository;
import com.fincity.nocode.kirun.engine.runtime.reactive.ReactiveFunctionExecutionParameters;
import com.google.gson.JsonPrimitive;

import reactor.test.StepVerifier;

class GetSecondsTest {

    DateFunctionRepository dfr = new DateFunctionRepository();

    ReactiveFunctionExecutionParameters fep = new ReactiveFunctionExecutionParameters(
            new KIRunReactiveFunctionRepository(), new KIRunReactiveSchemaRepository());

    @Test
    void secondsSuccessTest1() {

        fep.setArguments(Map.of("isodate", new JsonPrimitive("2023-09-07T17:35:17.000Z")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectNextMatches(
                        res -> res.next().getResult().get("seconds").getAsInt() == 17)
                .verifyComplete();

        fep.setArguments(Map.of("isodate", new JsonPrimitive("1970-01-20T15:58:57.561Z")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectNextMatches(
                        res -> res.next().getResult().get("seconds").getAsInt() == 57)
                .verifyComplete();

        fep.setArguments(Map.of("isodate", new JsonPrimitive("2023-10-19T06:44:11.615Z")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectNextMatches(
                        res -> res.next().getResult().get("seconds").getAsInt() == 11)
                .verifyComplete();

        fep.setArguments(Map.of("isodate", new JsonPrimitive("2507-08-08T11:41:50.000+00:00")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectNextMatches(
                        res -> res.next().getResult().get("seconds").getAsInt() == 50)
                .verifyComplete();

        fep.setArguments(Map.of("isodate", new JsonPrimitive("1970-01-20T15:13:51.001+12:01")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectNextMatches(
                        res -> res.next().getResult().get("seconds").getAsInt() == 51)
                .verifyComplete();

        fep.setArguments(Map.of("isodate", new JsonPrimitive("1970-01-20T15:13:51.200-12:01")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectNextMatches(
                        res -> res.next().getResult().get(
                                "seconds").getAsInt() == 51)
                .verifyComplete();

    }

    @Test
    void secondsFailTest1() {

        fep.setArguments(Map.of("isodate", new JsonPrimitive("2023-09-7T07:35:17.000Z")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectErrorMessage("Please provide the valid iso date.")
                .verify();

        fep.setArguments(Map.of("isodate", new JsonPrimitive("2023-10-19T23:24:7.615Z")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectErrorMessage("Please provide the valid iso date.")
                .verify();

        fep.setArguments(Map.of("isodate", new JsonPrimitive("abcd")));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectErrorMessage("Please provide the valid iso date.")
                .verify();

        fep.setArguments(Map.of("isodate", new JsonPrimitive(false)));

        StepVerifier.create(dfr.find(Namespaces.DATE, "GetSeconds")
                .flatMap(e -> e.execute(fep)))
                .expectError()
                .verify();

    }
}
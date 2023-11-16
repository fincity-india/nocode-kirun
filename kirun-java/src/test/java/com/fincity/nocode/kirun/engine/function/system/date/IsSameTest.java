package com.fincity.nocode.kirun.engine.function.system.date;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fincity.nocode.kirun.engine.namespaces.Namespaces;
import com.fincity.nocode.kirun.engine.repository.reactive.KIRunReactiveFunctionRepository;
import com.fincity.nocode.kirun.engine.repository.reactive.KIRunReactiveSchemaRepository;
import com.fincity.nocode.kirun.engine.runtime.reactive.ReactiveFunctionExecutionParameters;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import reactor.test.StepVerifier;

class IsSameTest {

    DateFunctionRepository dfr = new DateFunctionRepository();

    ReactiveFunctionExecutionParameters rfep = new ReactiveFunctionExecutionParameters(
            new KIRunReactiveFunctionRepository(), new KIRunReactiveSchemaRepository());

    @Test
    void yearTest() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("day");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-31T17:14:21.798Z"), "datetwo",
                new JsonPrimitive("2023-10-31T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }

    @Test
    void yearTest2() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("day");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-31T17:14:21.798Z"), "datetwo",
                new JsonPrimitive("2000-10-31T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> !r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }

    @Test
    void minuteTest() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("day");
        arr.add("minute");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-30T17:14:21.798Z"), "datetwo",
                new JsonPrimitive("2023-10-30T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }
    
    @Test
    void minuteTest2() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("day");
        arr.add("hour");;
        arr.add("minute");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-31T17:19:21.798Z"), "datetwo",
                new JsonPrimitive("2023-10-31T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> !r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }
    
    @Test
    void hourTest() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("day");
        arr.add("hour");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-30T17:19:21.798Z"), "datetwo",
                new JsonPrimitive("2023-10-30T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }
    
    @Test
    void monthTest() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("month");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-31T17:14:21.798Z"), "datetwo",
                new JsonPrimitive("2023-11-31T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> !r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }

    @Test
    void monthTest2() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("month");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-31T17:14:21.798Z"), "datetwo",
                new JsonPrimitive("2023-10-30T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }

    @Test
    void secondTest() {

        JsonArray arr = new JsonArray();

        arr.add("second");
        arr.add("year");
        arr.add("day");
        arr.add("minute");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-31T17:14:21.798Z"), "datetwo",
                new JsonPrimitive("2023-10-31T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> !r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }
    
    @Test
    void secondTest2() {

        JsonArray arr = new JsonArray();

        arr.add("second");
        arr.add("year");
        arr.add("day");
        arr.add("minute");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-31T17:14:21.789Z"), "datetwo",
                new JsonPrimitive("2023-10-31T17:14:20.799Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> !r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }
    @Test
    void DayTest() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("day");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-31T17:14:21.798Z"), "datetwo",
                new JsonPrimitive("2023-10-31T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }


    @Test
    void DayTest2() {

        JsonArray arr = new JsonArray();

        arr.add("year");
        arr.add("day");

        rfep.setArguments(Map.of("dateone", new JsonPrimitive("2023-10-30T17:14:21.798Z"), "datetwo",
                new JsonPrimitive("2023-10-31T17:14:20.789Z"), "unit", arr));

        StepVerifier.create(dfr.find(Namespaces.DATE, "IsSame").flatMap(e -> e.execute(rfep)))
                .expectNextMatches(r -> !r.next().getResult().get("result").getAsBoolean())
                .verifyComplete();
    }

    
}
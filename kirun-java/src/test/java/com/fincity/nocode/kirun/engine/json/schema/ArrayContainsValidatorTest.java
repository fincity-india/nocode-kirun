package com.fincity.nocode.kirun.engine.json.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fincity.nocode.kirun.engine.json.schema.array.ArraySchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.SchemaType;
import com.fincity.nocode.kirun.engine.json.schema.type.Type;
import com.fincity.nocode.kirun.engine.json.schema.validator.ArrayValidator;
import com.fincity.nocode.kirun.engine.json.schema.validator.exception.SchemaValidationException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

class ArrayContainsValidatorTest {
    Schema schema = new Schema();

    @Test
    void ArrayValidatorTestForContainsSchema() {

        JsonObject job = new JsonObject();
        job.addProperty("name", "jimmy mcgill");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        var result = ArrayValidator.validate(null, schema, null, element);
        assertEquals(element, result);
    }

    @Test
    void ArrayValidatorTestForContainsErrorSchema() {

        JsonObject job = new JsonObject();
        job.addProperty("name", "jimmy mcgill");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(1);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofInteger("item3"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);

        schema.setItems(ast);

        SchemaValidationException sve = assertThrows(SchemaValidationException.class,
                () -> ArrayValidator.validate(null, schema, null, element));

        assertEquals("None of the items are of type contains schema", sve.getMessage());
    }

    @Test
    void ArrayValidatorTestForMinContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));
        schema.setMinContains(2);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        var result = ArrayValidator.validate(null, schema, null, element);
        assertEquals(element, result);
    }

    @Test
    void ArrayValidatorTestForMaxContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));
        schema.setMaxContains(2);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        var result = ArrayValidator.validate(null, schema, null, element);
        assertEquals(element, result);
    }

    @Test
    void ArrayValidatorTestForMinMaxContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");
        JsonObject job3 = new JsonObject();
        job3.addProperty("city", "Alberqueue");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);
        element.add("Mcgill");
        element.add(job3);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));
        schema.setMinContains(1);
        schema.setMaxContains(3);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));
        tupleSchema.add(Schema.ofString("item6"));
        tupleSchema.add(Schema.ofObject("item7"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        var result = ArrayValidator.validate(null, schema, null, element);
        assertEquals(element, result);
    }

    @Test
    void ArrayValidatorTestForMinErrorContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");
        JsonObject job3 = new JsonObject();
        job3.addProperty("city", "Alberqueue");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);
        element.add("Mcgill");
        element.add(job3);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));
        schema.setMinContains(4);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));
        tupleSchema.add(Schema.ofString("item6"));
        tupleSchema.add(Schema.ofObject("item7"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        SchemaValidationException result = assertThrows(SchemaValidationException.class,
                () -> ArrayValidator.validate(null, schema, null, element));

        assertEquals("The minimum number of the items defined are " + schema.getMinContains()
                + " of type contains schema are not present", result.getMessage());
    }

    @Test
    void ArrayValidatorTestForMaxErrorContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");
        JsonObject job3 = new JsonObject();
        job3.addProperty("city", "Alberqueue");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);
        element.add("Mcgill");
        element.add(job3);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));
        schema.setMaxContains(1);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));
        tupleSchema.add(Schema.ofString("item6"));
        tupleSchema.add(Schema.ofObject("item7"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        SchemaValidationException result = assertThrows(SchemaValidationException.class,
                () -> ArrayValidator.validate(null, schema, null, element));

        assertEquals("The maximum number of the items defined are " + schema.getMaxContains()
                + " of type contains schema are not present", result.getMessage());
    }

    @Test
    void ArrayValidatorTestForMinMaxFErrorContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");
        JsonObject job3 = new JsonObject();
        job3.addProperty("city", "Alberqueue");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);
        element.add("Mcgill");
        element.add(job3);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));
        schema.setMaxContains(3);
        schema.setMinContains(4);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));
        tupleSchema.add(Schema.ofString("item6"));
        tupleSchema.add(Schema.ofObject("item7"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        SchemaValidationException result = assertThrows(SchemaValidationException.class,
                () -> ArrayValidator.validate(null, schema, null, element));

        assertEquals("The minimum number of the items defined are " + schema.getMinContains()
                + " of type contains schema are not present", result.getMessage());
    }

    @Test
    void ArrayValidatorTestForMinMaxSErrorContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");
        JsonObject job3 = new JsonObject();
        job3.addProperty("city", "Alberqueue");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);
        element.add("Mcgill");
        element.add(job3);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));
        schema.setMaxContains(2);
        schema.setMinContains(3);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));
        tupleSchema.add(Schema.ofString("item6"));
        tupleSchema.add(Schema.ofObject("item7"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        SchemaValidationException result = assertThrows(SchemaValidationException.class,
                () -> ArrayValidator.validate(null, schema, null, element));

        assertEquals("The maximum number of the items defined are " + schema.getMaxContains()
                + " of type contains schema are not present", result.getMessage());
    }

    @Test
    void ArrayValidatorTestForMinMaxWithoutContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");
        JsonObject job3 = new JsonObject();
        job3.addProperty("city", "Alberqueue");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);
        element.add("Mcgill");
        element.add(job3);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setMaxContains(2);
        schema.setMinContains(3);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));
        tupleSchema.add(Schema.ofString("item6"));
        tupleSchema.add(Schema.ofObject("item7"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        var result = ArrayValidator.validate(null, schema, null, element);

        assertEquals(element, result);
    }

    @Test
    void ArrayValidatorTestForMinMaxEContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");
        JsonObject job3 = new JsonObject();
        job3.addProperty("city", "Alberqueue");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);
        element.add("Mcgill");
        element.add(job3);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setContains(Schema.ofObject("object"));
        schema.setMaxContains(1);
        schema.setMinContains(0);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));
        tupleSchema.add(Schema.ofString("item6"));
        tupleSchema.add(Schema.ofObject("item7"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        SchemaValidationException result = assertThrows(SchemaValidationException.class,
                () -> ArrayValidator.validate(null, schema, null, element));

        assertEquals("The maximum number of the items defined are " + schema.getMaxContains()
                + " of type contains schema are not present", result.getMessage());
    }

    @Test
    void ArrayValidatorTestForMinMaxErrorWithoutContainsSchema() {

        JsonObject job1 = new JsonObject();
        job1.addProperty("name", "jimmy mcgill");
        JsonObject job2 = new JsonObject();
        job2.addProperty("firm", "HMM");
        JsonObject job3 = new JsonObject();
        job3.addProperty("city", "Alberqueue");

        JsonArray element = new JsonArray();
        element.add("texas");
        element.add("kentucky");
        element.add(job1);
        element.add(true);
        element.add(job2);
        element.add("Mcgill");
        element.add(job3);

        Schema schema = new Schema();
        schema.setType(Type.of(SchemaType.ARRAY));
        schema.setMaxContains(0);
        schema.setMinContains(3);

        List<Schema> tupleSchema = new ArrayList<>();
        tupleSchema.add(Schema.ofString("item1"));
        tupleSchema.add(Schema.ofString("item2"));
        tupleSchema.add(Schema.ofObject("item3"));
        tupleSchema.add(Schema.ofBoolean("item4"));
        tupleSchema.add(Schema.ofObject("item5"));
        tupleSchema.add(Schema.ofString("item6"));
        tupleSchema.add(Schema.ofObject("item7"));

        ArraySchemaType ast = new ArraySchemaType();
        ast.setTupleSchema(tupleSchema);
        schema.setItems(ast);

        var result = ArrayValidator.validate(null, schema, null, element);

        assertEquals(element, result);
    }

}

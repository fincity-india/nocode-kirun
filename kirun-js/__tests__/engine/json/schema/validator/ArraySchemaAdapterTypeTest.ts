import { KIRunSchemaRepository, SchemaType, SchemaValidator } from '../../../../../src';
import { Schema } from '../../../../../src/engine/json/schema/Schema';

const repo = new KIRunSchemaRepository();
test('schemaArray With Single Test', async () => {
    let schema = Schema.from({
        type: 'ARRAY',
        items: {
            singleSchema: {
                type: 'OBJECT',
                properties: { name: { type: 'STRING' }, age: { type: 'INTEGER' } },
            },
        },
        additionalItems: false,
    });

    let obj = [
        {
            name: 'amigo1',
        },
        {
            age: 24,
        },
        false,
        'exampleString',
    ];

    expect(SchemaValidator.validate([], schema, repo, obj)).rejects.toThrow();
});

test('schemaArray With out Single fail Test', async () => {
    let schema = Schema.from({
        type: 'ARRAY',
        items: {
            type: 'OBJECT',
            properties: { name: { type: 'STRING' }, age: { type: 'INTEGER' } },
        },

        additionalItems: false,
    });

    let obj = [
        {
            name: 'amigo1',
        },
        {
            age: 24,
        },
        false,
        'exampleString',
    ];

    expect(SchemaValidator.validate([], schema, repo, obj)).rejects.toThrow();
});

test('schemaArrayWithSingle pass Test', async () => {
    let schema = Schema.from({
        type: 'ARRAY',
        items: {
            singleSchema: {
                type: 'OBJECT',
                properties: { name: { type: 'STRING' }, age: { type: 'INTEGER' } },
            },
        },

        additionalItems: false,
    });

    let obj = [
        {
            name: 'amigo1',
        },
        {
            age: 24,
        },
    ];

    expect(await SchemaValidator.validate([], schema, repo, obj)).toBe(obj);
});

test('schemaArray With Tuple Test ', async () => {
    let schema = Schema.from({
        type: 'ARRAY',
        items: {
            tupleSchema: [
                {
                    type: 'OBJECT',
                    properties: { name: { type: 'STRING' }, age: { type: 'INTEGER' } },
                    required: ['age'],
                },
                { type: 'STRING', minLength: 2 },
                { type: 'INTEGER', minimum: 10 },
            ],
        },
        additionalItems: true,
    });

    let obj = [
        {
            name: 'amigo1',
            age: 24,
        },
        'string type',

        11,
        false,
        12.34,
        'mla',
    ];

    expect(await SchemaValidator.validate([], schema, repo, obj)).toBe(obj);
});

test('schemaArray With out Tuple Test ', async () => {
    let schema = Schema.from({
        type: 'ARRAY',
        items: [
            {
                type: 'OBJECT',
                properties: { name: { type: 'STRING' }, age: { type: 'INTEGER' } },
                required: ['age'],
            },
            { type: 'STRING', minLength: 2 },
            { type: 'ARRAY', items: { type: 'INTEGER' }, additionalItems: false },
        ],
        additionalItems: true,
    });

    let obj = [
        {
            name: 'amigo1',
            age: 21,
        },
        'second string',
        [1, 2, 31231],
        'additional items was added here with true and false',
        true,
        false,
    ];
    expect(await SchemaValidator.validate([], schema, repo, obj)).toBe(obj);
});

test('Regular JSON', async () => {
    let schema = Schema.from({
        type: 'object',
        properties: {
            productId: {
                description: 'The unique identifier for a product',
                type: 'integer',
            },
        },
    });

    expect(schema?.getType()?.contains(SchemaType.OBJECT)).toBe(true);
    expect(schema?.getType()?.contains(SchemaType.INTEGER)).toBe(false);
    expect(schema?.getProperties()?.get('productId')?.getType()?.contains(SchemaType.INTEGER)).toBe(
        true,
    );
});

test('Only Ref test', async () => {
    let schema = Schema.from({ ref: 'System.any' });

    expect(schema?.getType()).toBeUndefined();
    expect(schema?.getRef()).toBe('System.any');
});

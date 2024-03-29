import { Insert } from '../../../../../src/engine/function/system/array/Insert';
import { FunctionExecutionParameters } from '../../../../../src/engine/runtime/FunctionExecutionParameters';
import { KIRunFunctionRepository, KIRunSchemaRepository } from '../../../../../src';

test('Insert of Test 1', async () => {
    let ins: Insert = new Insert();

    let array: any[] = [];

    array.push('test');
    array.push('Driven');
    array.push('developement');
    array.push('I');
    array.push('am');
    array.push('using');
    array.push('eclipse');
    array.push('I');
    array.push('to');
    array.push('test');
    array.push('the');
    array.push('changes');
    array.push('with');
    array.push('test');
    array.push('Driven');
    array.push('developement');

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    )
        .setArguments(
            new Map<string, any>([
                [Insert.PARAMETER_ARRAY_SOURCE.getParameterName(), array],
                [Insert.PARAMETER_INT_OFFSET.getParameterName(), 4],
                [Insert.PARAMETER_ANY.getParameterName(), ['this is an array']],
            ]),
        )
        .setSteps(new Map([]))
        .setContext(new Map([]));

    let res: any[] = [];

    res.push('test');
    res.push('Driven');
    res.push('developement');
    res.push('I');
    res.push(['this is an array']);
    res.push('am');
    res.push('using');
    res.push('eclipse');
    res.push('I');
    res.push('to');
    res.push('test');
    res.push('the');
    res.push('changes');
    res.push('with');
    res.push('test');
    res.push('Driven');
    res.push('developement');

    expect((await ins.execute(fep)).allResults()[0].getResult().get('result')).toStrictEqual(res);
});

test('Insert of Test 2', async () => {
    let ins: Insert = new Insert();

    let arr: any[] = [];

    arr.push(1);
    arr.push(2);
    arr.push(3);
    arr.push(4);
    arr.push(5);
    arr.push(6);
    arr.push(7);
    arr.push(8);

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    )
        .setArguments(
            new Map<string, any>([
                [Insert.PARAMETER_ARRAY_SOURCE.getParameterName(), arr],
                [Insert.PARAMETER_INT_OFFSET.getParameterName(), 2],
                [Insert.PARAMETER_ANY.getParameterName(), ['this is an array']],
            ]),
        )
        .setSteps(new Map([]))
        .setContext(new Map([]));

    let res: any[] = [];

    res.push(1);
    res.push(2);
    res.push(['this is an array']);
    res.push(3);
    res.push(4);
    res.push(5);
    res.push(6);
    res.push(7);
    res.push(8);
    expect((await ins.execute(fep)).allResults()[0].getResult().get('result')).toStrictEqual(res);
});

test('Insert of Test 3', async () => {
    let ins: Insert = new Insert();

    let arr: any[] = [];

    arr.push(1);
    arr.push(2);
    arr.push(3);
    arr.push(4);
    arr.push(5);
    arr.push(6);
    arr.push(7);
    arr.push(8);

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    )
        .setArguments(
            new Map<string, any>([
                [Insert.PARAMETER_ARRAY_SOURCE.getParameterName(), arr],
                [Insert.PARAMETER_INT_OFFSET.getParameterName(), 0],
                [Insert.PARAMETER_ANY.getParameterName(), ['this is an array']],
            ]),
        )
        .setSteps(new Map([]))
        .setContext(new Map([]));

    let res: any[] = [];

    res.push(['this is an array']);
    res.push(1);
    res.push(2);
    res.push(3);
    res.push(4);
    res.push(5);
    res.push(6);
    res.push(7);
    res.push(8);
    expect((await ins.execute(fep)).allResults()[0].getResult().get('result')).toStrictEqual(res);
});

test('Insert of Test 4', async () => {
    let ins: Insert = new Insert();

    let arr: any[] = [];

    arr.push(1);
    arr.push(2);
    arr.push(3);
    arr.push(4);
    arr.push(5);
    arr.push(6);
    arr.push(7);
    arr.push(8);

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    )
        .setArguments(
            new Map<string, any>([
                [Insert.PARAMETER_ARRAY_SOURCE.getParameterName(), arr],
                [Insert.PARAMETER_INT_OFFSET.getParameterName(), arr.length],
                [Insert.PARAMETER_ANY.getParameterName(), ['this is an array']],
            ]),
        )
        .setSteps(new Map([]))
        .setContext(new Map([]));

    let res: any[] = [];

    res.push(1);
    res.push(2);
    res.push(3);
    res.push(4);
    res.push(5);
    res.push(6);
    res.push(7);
    res.push(8);
    res.push(['this is an array']);

    expect((await ins.execute(fep)).allResults()[0].getResult().get('result')).toStrictEqual(res);
});

test('Insert of Test 5', async () => {
    let ins: Insert = new Insert();

    let arr: any[] = [];

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    )
        .setArguments(
            new Map<string, any>([
                [Insert.PARAMETER_ARRAY_SOURCE.getParameterName(), arr],
                [Insert.PARAMETER_ANY.getParameterName(), ['this is an array']],
            ]),
        )
        .setSteps(new Map([]))
        .setContext(new Map([]));

    let res: any[] = [];

    res.push(['this is an array']);

    expect((await ins.execute(fep)).allResults()[0].getResult().get('result')).toStrictEqual(res);
});

test('Insert of Test 6', async () => {
    let ins: Insert = new Insert();

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    )
        .setArguments(
            new Map<string, any>([
                [Insert.PARAMETER_ARRAY_SOURCE.getParameterName(), null],
                [Insert.PARAMETER_INT_OFFSET.getParameterName(), 0],
                [Insert.PARAMETER_ANY.getParameterName(), ['this is an array']],
            ]),
        )
        .setSteps(new Map([]))
        .setContext(new Map([]));

    let res: any[] = [];

    res.push(['this is an array']);

    await expect(ins.execute(fep)).rejects.toThrow();
});

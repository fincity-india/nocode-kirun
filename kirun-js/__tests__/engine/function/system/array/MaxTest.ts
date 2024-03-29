import { Max } from '../../../../../src/engine/function/system/array/Max';
import { FunctionExecutionParameters } from '../../../../../src/engine/runtime/FunctionExecutionParameters';
import { KIRunFunctionRepository, KIRunSchemaRepository } from '../../../../../src';

let max: Max = new Max();

test('max test 1 ', async () => {
    let arr: any[] = [];
    arr.push(null);
    arr.push(12);

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    ).setArguments(new Map([[Max.PARAMETER_ARRAY_SOURCE_PRIMITIVE.getParameterName(), arr]]));

    expect((await max.execute(fep)).allResults()[0].getResult().get('result')).toBe(12);
});

test('max test 2 ', async () => {
    let arr: any[] = [];

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    ).setArguments(new Map([[Max.PARAMETER_ARRAY_SOURCE_PRIMITIVE.getParameterName(), arr]]));

    await expect(max.execute(fep)).rejects.toThrow();
});

test('max test 3', async () => {
    let arr: any[] = [];
    arr.push(12);
    arr.push(15);
    arr.push(null);
    arr.push(98);
    arr.push(1);

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    ).setArguments(new Map([[Max.PARAMETER_ARRAY_SOURCE_PRIMITIVE.getParameterName(), arr]]));
    expect((await max.execute(fep)).allResults()[0].getResult().get('result')).toBe(98);
});

test('Max test 4', async () => {
    let arr: any[] = [];
    arr.push('nocode');
    arr.push('NoCode');
    arr.push('platform');
    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    ).setArguments(new Map([[Max.PARAMETER_ARRAY_SOURCE_PRIMITIVE.getParameterName(), arr]]));
    expect((await max.execute(fep)).allResults()[0].getResult().get('result')).toBe('platform');
});

test('Max test 6', async () => {
    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    ).setArguments(new Map([[Max.PARAMETER_ARRAY_SOURCE_PRIMITIVE.getParameterName(), null]]));
    await expect(max.execute(fep)).rejects.toThrow();
});

test('Max test 5', async () => {
    let arr: any[] = [];

    arr.push(456);
    arr.push('nocode');

    arr.push('NoCode');
    arr.push('platform');
    arr.push(123);

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    ).setArguments(new Map([[Max.PARAMETER_ARRAY_SOURCE_PRIMITIVE.getParameterName(), arr]]));
    expect((await max.execute(fep)).allResults()[0].getResult().get('result')).toBe('platform');
});

test('Max test 7', async () => {
    let arr1: any[] = [];
    arr1.push('c');
    arr1.push('r');
    arr1.push('d');
    arr1.push('s');
    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    ).setArguments(new Map([[Max.PARAMETER_ARRAY_SOURCE_PRIMITIVE.getParameterName(), arr1]]));

    expect((await max.execute(fep)).allResults()[0].getResult().get('result')).toBe('s');
});

test('Max test 8', async () => {
    let arr: any[] = ['surendhar'];
    let fep: FunctionExecutionParameters = new FunctionExecutionParameters(
        new KIRunFunctionRepository(),
        new KIRunSchemaRepository(),
    ).setArguments(new Map([[Max.PARAMETER_ARRAY_SOURCE_PRIMITIVE.getParameterName(), arr]]));

    expect((await max.execute(fep)).allResults()[0].getResult().get('result')).toBe('surendhar');
});

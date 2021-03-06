import { AbstractArrayFunction } from '../../../../../src/engine/function/system/array/AbstractArrayFunction';
import { Copy } from '../../../../../src/engine/function/system/array/Copy';
import { FunctionOutput } from '../../../../../src/engine/model/FunctionOutput';
import { FunctionExecutionParameters } from '../../../../../src/engine/runtime/FunctionExecutionParameters';

import { MapUtil } from '../../../../../src/engine/util/MapUtil';

test('Compy Test', () => {
    let copy: Copy = new Copy();

    let source: any[] = [1, 2, 3, 4, 5];

    let fep1: FunctionExecutionParameters = new FunctionExecutionParameters().setArguments(
        MapUtil.of(
            AbstractArrayFunction.PARAMETER_ARRAY_SOURCE.getParameterName(),
            source as any,
            AbstractArrayFunction.PARAMETER_INT_SOURCE_FROM.getParameterName(),
            2,
            AbstractArrayFunction.PARAMETER_INT_LENGTH.getParameterName(),
            4,
        ),
    );

    expect(() => copy.execute(fep1)).toThrow();

    source.push(6);

    let result: any[] = [3, 4, 5, 6];

    let fep: FunctionExecutionParameters = new FunctionExecutionParameters().setArguments(
        MapUtil.of(
            Copy.PARAMETER_ARRAY_SOURCE.getParameterName(),
            source as any,
            Copy.PARAMETER_INT_SOURCE_FROM.getParameterName(),
            2,
            Copy.PARAMETER_INT_LENGTH.getParameterName(),
            4,
        ),
    );

    let fo: FunctionOutput = copy.execute(fep);
    expect(fo.allResults()[0].getResult().get(Copy.EVENT_RESULT_NAME)).toStrictEqual(result);

    source = new Array();
    source.push({ name: 'Kiran' });
    source.push({ name: 'Kumar' });

    fep = new FunctionExecutionParameters().setArguments(
        MapUtil.of(
            Copy.PARAMETER_ARRAY_SOURCE.getParameterName(),
            source as any,
            Copy.PARAMETER_INT_SOURCE_FROM.getParameterName(),
            2,
            Copy.PARAMETER_INT_LENGTH.getParameterName(),
            4,
        ),
    );

    result = new Array();
    result.push({ name: 'Kiran' });
    result.push({ name: 'Kumar' });

    fep = new FunctionExecutionParameters().setArguments(
        MapUtil.of(Copy.PARAMETER_ARRAY_SOURCE.getParameterName(), source),
    );

    fo = copy.execute(fep);

    expect(fo.allResults()[0].getResult().get(Copy.EVENT_RESULT_NAME)).toStrictEqual(result);
    expect(source[0] == result[0]).toBeFalsy();

    fep = new FunctionExecutionParameters().setArguments(
        MapUtil.of(
            Copy.PARAMETER_ARRAY_SOURCE.getParameterName(),
            source as any,
            Copy.PARAMETER_BOOLEAN_DEEP_COPY.getParameterName(),
            false,
        ),
    );

    fo = copy.execute(fep);

    result = fo.allResults()[0].getResult().get(Copy.EVENT_RESULT_NAME);

    expect(source[0] == result[0]).toBeTruthy();
});
